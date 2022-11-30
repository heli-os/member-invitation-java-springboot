package kr.dataportal.invitation.invitation.service;

import kr.dataportal.invitation.invitation.domain.Invitation;
import kr.dataportal.invitation.invitation.usecase.InviteWorkspaceUseCase;
import kr.dataportal.invitation.persistence.entity.member.MemberJpaEntity;
import kr.dataportal.invitation.persistence.service.member.MemberCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Service
@Transactional
public class InviteWorkspace implements InviteWorkspaceUseCase {

    private final InvitationService invitationService;
    private final MemberCommand memberCommand;

    public InviteWorkspace(final InvitationService invitationService, final MemberCommand memberCommand) {
        this.invitationService = invitationService;
        this.memberCommand = memberCommand;
    }

    @Override
    public Invitation command(final Command command) {
        // TODO member in workspace 검증 (Spring Security 레벨에서 인증/인가 처리 -> Workspace/Collaborator/Member Auditing ContextHolder)

        MemberJpaEntity candidateMemberJpaEntity = memberCommand.newCandidate(command.inviteMember());
        Invitation.Target target = command.target(candidateMemberJpaEntity.getId());

        invitationService.getByTarget(target)
                .ifPresent(invitationService::expireInvitation);

        Invitation invitation = Invitation.create(target, Invitation.DEFAULT_CONTEXT);
        invitationService.issueInvitation(invitation);
        return invitation;
    }
}
