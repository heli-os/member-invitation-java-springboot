package kr.dataportal.invitation.invitation.exception;

import kr.dataportal.invitation.invitation.domain.Invitation;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public class ExpiredInvitationException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "만료된 Invitation [invitation=%s]";

    public ExpiredInvitationException(final Invitation invitation) {
        super(String.format(MESSAGE_FORMAT, invitation));
    }
}
