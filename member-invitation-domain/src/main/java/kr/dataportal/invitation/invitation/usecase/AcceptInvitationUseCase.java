package kr.dataportal.invitation.invitation.usecase;

import kr.dataportal.invitation.invitation.domain.Invitation;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public interface AcceptInvitationUseCase {


    Invitation command(final Command command);

    record Command(
            String invitationCode
    ) {
    }
}
