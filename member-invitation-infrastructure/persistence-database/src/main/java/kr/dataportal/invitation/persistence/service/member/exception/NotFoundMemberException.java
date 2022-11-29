package kr.dataportal.invitation.persistence.service.member.exception;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public class NotFoundMemberException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "can not found member [memberId=%s]";

    public NotFoundMemberException(final Long memberId) {
        super(String.format(MESSAGE_FORMAT, memberId));
    }
}
