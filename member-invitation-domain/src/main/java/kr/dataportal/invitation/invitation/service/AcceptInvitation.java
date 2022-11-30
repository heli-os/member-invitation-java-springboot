package kr.dataportal.invitation.invitation.service;

import kr.dataportal.invitation.invitation.domain.Invitation;
import kr.dataportal.invitation.invitation.usecase.AcceptInvitationUseCase;
import kr.dataportal.invitation.persistence.service.member.MemberCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Service
@Transactional
public class AcceptInvitation implements AcceptInvitationUseCase {

    private final InvitationService invitationService;
    private final MemberCommand memberCommand;

    public AcceptInvitation(final InvitationService invitationService, final MemberCommand memberCommand) {
        this.invitationService = invitationService;
        this.memberCommand = memberCommand;
    }

    @Override
    public Invitation command(final Command command) {
        Invitation invitation = invitationService.getByInvitationCode(command.invitationCode());
        Invitation.Target target = invitation.target();

        memberCommand.activate(target.memberId());
        invitationService.expireInvitation(invitation);
        return invitation;
    }
}
