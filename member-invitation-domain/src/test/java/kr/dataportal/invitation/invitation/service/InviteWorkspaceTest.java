package kr.dataportal.invitation.invitation.service;

import kr.dataportal.invitation.invitation.domain.Invitation;
import kr.dataportal.invitation.invitation.usecase.InviteWorkspaceUseCase;
import kr.dataportal.invitation.model.member.InviteMember;
import kr.dataportal.invitation.persistence.entity.member.MemberJpaEntity;
import kr.dataportal.invitation.persistence.service.member.MemberCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static kr.dataportal.invitation.invitation.domain.Invitation.DEFAULT_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InviteWorkspaceTest {

    private final InviteWorkspaceUseCase.Command command = new InviteWorkspaceUseCase.Command(
            1L,
            new InviteMember("Heli", "010-1111-2222", "heli@example.com")
    );
    private final Invitation.Target target = command.target(2L);
    private final Invitation invitation = Invitation.create(target, DEFAULT_CONTEXT);
    private final MemberJpaEntity memberJpaEntity = MemberJpaEntity.newCandidate("Heli", "010-1111-2222", "heli@example.com").apply(2L, LocalDateTime.now());
    @Mock
    private InvitationService invitationService;
    @Mock
    private MemberCommand memberCommand;
    private InviteWorkspaceUseCase sut;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        when(memberCommand.newCandidate(command.inviteMember())).thenReturn(memberJpaEntity);
        sut = new InviteWorkspace(invitationService, memberCommand);
    }

    @Test
    void 기존에_등록된_초대장이_있다면_만료_후_재생성한다() {
        when(invitationService.getByTarget(command.target(2L)))
                .thenReturn(Optional.of(invitation));

        Invitation actual = sut.command(command);

        assertThat(actual.code()).isNotEqualTo(invitation.code());
        assertThat(actual.expiresAt()).isNotEqualTo(invitation.expiresAt());
        assertThat(actual.target().workspaceId()).isEqualTo(1L);
        assertThat(actual.target().memberId()).isEqualTo(2L);
        verify(invitationService, times(1)).expireInvitation(invitation);
        verify(invitationService, times(1)).issueInvitation(actual);
    }

    @Test
    void 기존에_등록된_초대장이_없다면_신규_생성한다() {
        when(invitationService.getByTarget(command.target(2L)))
                .thenReturn(Optional.empty());

        Invitation actual = sut.command(command);

        assertThat(actual.code()).isNotNull();
        assertThat(actual.expiresAt()).isNotNull();
        assertThat(actual.target().workspaceId()).isEqualTo(1L);
        assertThat(actual.target().memberId()).isEqualTo(2L);
        verify(invitationService, times(0)).expireInvitation(invitation);
        verify(invitationService, times(1)).issueInvitation(actual);
    }
}
