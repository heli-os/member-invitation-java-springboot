package kr.dataportal.invitation.invitation.exception;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public class NotFoundInvitationException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Invitation 조회 실패 [invitationCode=%s]";

    public NotFoundInvitationException(final String invitationCode) {
        super(String.format(MESSAGE_FORMAT, invitationCode));
    }
}
