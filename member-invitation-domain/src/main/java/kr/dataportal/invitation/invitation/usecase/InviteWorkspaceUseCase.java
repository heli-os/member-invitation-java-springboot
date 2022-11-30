package kr.dataportal.invitation.invitation.usecase;

import kr.dataportal.invitation.invitation.domain.Invitation;
import kr.dataportal.invitation.model.member.InviteMember;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public interface InviteWorkspaceUseCase {


    Invitation command(final Command command);

    record Command(
            long workspaceId,
            InviteMember inviteMember
    ) {
        public Invitation.Target target(final long memberId) {
            return new Invitation.Target(workspaceId, memberId);
        }
    }
}
