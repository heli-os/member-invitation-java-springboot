package kr.dataportal.invitation.invitation.service;

import kr.dataportal.invitation.invitation.domain.Invitation;
import kr.dataportal.invitation.invitation.exception.ExpiredInvitationException;
import kr.dataportal.invitation.persistence.service.RedisService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InvitationServiceTest {

    private final Invitation DEFAULT_INVITATION = Invitation.create(new Invitation.Target(1L, 2L), Invitation.DEFAULT_CONTEXT);
    private final String DEFAULT_INVITATION_REDIS_KEY = "invitation:target:" + DEFAULT_INVITATION.target();
    private final String DEFAULT_TARGET_REDIS_KEY = "invitation:code:" + DEFAULT_INVITATION.code();
    @Mock
    private RedisService redisService;

    private InvitationService sut;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        sut = new InvitationService(redisService);
    }

    @Test
    void 초대장을_발행할_수_있다() {
        sut.issueInvitation(DEFAULT_INVITATION);
        verify(redisService, times(1)).set(eq(DEFAULT_INVITATION_REDIS_KEY), eq(DEFAULT_INVITATION), any(Duration.class));
        verify(redisService, times(1)).set(eq(DEFAULT_TARGET_REDIS_KEY), eq(DEFAULT_INVITATION.target()), any(Duration.class));
    }

    @Test
    void 만료된_초대장_사용_시도_시_ExpiredInvitationException_예외_발생() {
        Invitation zeroDurationInvitation = Invitation.create(new Invitation.Target(1L, 2L), new Invitation.Context(Duration.ZERO));
        Assertions.assertThrows(
                ExpiredInvitationException.class,
                () -> sut.useInvitation(zeroDurationInvitation)
        );
    }

    @Test
    void 유효한_초대장_사용_시도_시_초대장_만료() {
        sut.useInvitation(DEFAULT_INVITATION);
        verify(redisService, times(1)).delete(eq(DEFAULT_INVITATION_REDIS_KEY));
        verify(redisService, times(1)).delete(eq(DEFAULT_TARGET_REDIS_KEY));
    }

    @Test
    void 초대장을_만료_시킬_수_있다() {
        sut.expireInvitation(DEFAULT_INVITATION);
        verify(redisService, times(1)).delete(eq(DEFAULT_INVITATION_REDIS_KEY));
        verify(redisService, times(1)).delete(eq(DEFAULT_TARGET_REDIS_KEY));
    }

    @Test
    void Invitation_Target_으로_Invitation_을_얻어올_수_있다() {
        when(redisService.get(DEFAULT_INVITATION_REDIS_KEY, Invitation.class)).thenReturn(Optional.of(DEFAULT_INVITATION));

        Invitation actual = sut.getByTarget(DEFAULT_INVITATION.target())
                .orElseGet(() -> fail("Invitation must not be null"));

        assertThat(actual.code()).isEqualTo(DEFAULT_INVITATION.code());
        assertThat(actual.target().workspaceId()).isEqualTo(1L);
        assertThat(actual.target().memberId()).isEqualTo(2L);
        assertThat(actual.isUsableAt(LocalDateTime.now())).isTrue();
        verify(redisService, times(1)).get(eq(DEFAULT_INVITATION_REDIS_KEY), eq(Invitation.class));
    }

    @Test
    void invitationCode_로_Invitation_Target_을_얻어올_수_있다() {
        when(redisService.get(DEFAULT_TARGET_REDIS_KEY, Invitation.Target.class)).thenReturn(Optional.of(DEFAULT_INVITATION.target()));
        when(redisService.get(DEFAULT_INVITATION_REDIS_KEY, Invitation.class)).thenReturn(Optional.of(DEFAULT_INVITATION));

        Invitation actual = sut.getByInvitationCode(DEFAULT_INVITATION.code());

        assertThat(actual.code()).isEqualTo(DEFAULT_INVITATION.code());
        assertThat(actual.target().workspaceId()).isEqualTo(1L);
        assertThat(actual.target().memberId()).isEqualTo(2L);
        assertThat(actual.isUsableAt(LocalDateTime.now())).isTrue();
        verify(redisService, times(1)).get(eq(DEFAULT_TARGET_REDIS_KEY), eq(Invitation.Target.class));
    }
}
