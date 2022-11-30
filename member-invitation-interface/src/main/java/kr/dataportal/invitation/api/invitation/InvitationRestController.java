package kr.dataportal.invitation.api.invitation;

import kr.dataportal.invitation.api.invitation.dto.InvitationResponseDto;
import kr.dataportal.invitation.api.invitation.dto.InviteWorkspaceRequestDto;
import kr.dataportal.invitation.api.invitation.mapper.InvitationMapper;
import kr.dataportal.invitation.invitation.domain.Invitation;
import kr.dataportal.invitation.invitation.usecase.AcceptInvitationUseCase;
import kr.dataportal.invitation.invitation.usecase.InviteWorkspaceUseCase;
import kr.dataportal.invitation.invitation.usecase.QueryInvitationByCodeUseCase;
import kr.dataportal.invitation.model.member.InviteMember;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@RestController
public class InvitationRestController {

    private final InviteWorkspaceUseCase inviteWorkspaceUseCase;
    private final QueryInvitationByCodeUseCase queryInvitationByCodeUseCase;
    private final AcceptInvitationUseCase acceptInvitationUseCase;

    public InvitationRestController(final InviteWorkspaceUseCase inviteWorkspaceUseCase, final QueryInvitationByCodeUseCase queryInvitationByCodeUseCase, final AcceptInvitationUseCase acceptInvitationUseCase) {
        this.inviteWorkspaceUseCase = inviteWorkspaceUseCase;
        this.queryInvitationByCodeUseCase = queryInvitationByCodeUseCase;
        this.acceptInvitationUseCase = acceptInvitationUseCase;
    }

    @PostMapping("/api/v1/workspace/{workspaceId}/invitation")
    public InvitationResponseDto inviteWorkspace(
            @PathVariable final long workspaceId,
            @RequestBody @Validated final InviteWorkspaceRequestDto dto
    ) {
        Invitation invitation = inviteWorkspaceUseCase.command(new InviteWorkspaceUseCase.Command(
                workspaceId,
                new InviteMember(dto.inviteMemberName(), dto.inviteMemberPhoneNumber(), dto.inviteMemberEmail())
        ));

        return InvitationMapper.toResponseDto(invitation);
    }

    @GetMapping("/api/v1/invitation/{code}")
    public InvitationResponseDto invitation(
            @PathVariable final String code
    ) {
        Invitation invitation = queryInvitationByCodeUseCase.query(new QueryInvitationByCodeUseCase.Query(code));

        return InvitationMapper.toResponseDto(invitation);
    }

    @PostMapping("/api/v1/invitation/{code}/accept")
    public InvitationResponseDto acceptInvitation(
            @PathVariable final String code
    ) {
        Invitation invitation = acceptInvitationUseCase.command(new AcceptInvitationUseCase.Command(code));

        return InvitationMapper.toResponseDto(invitation);
    }
}
