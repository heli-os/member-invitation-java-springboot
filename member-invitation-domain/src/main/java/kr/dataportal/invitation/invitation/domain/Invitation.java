package kr.dataportal.invitation.invitation.domain;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public record Invitation(
        Target target,
        LocalDateTime expiresAt,
        String code
) {

    private static final long DEFAULT_EXPIRED_DAYS = 1L;
    public static final Context DEFAULT_CONTEXT = new Context(Duration.ofDays(DEFAULT_EXPIRED_DAYS));
    private static final int INVITE_CODE_LENGTH = 32;

    public static Invitation create(final Target target, final Context context) {
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(context.duration.toMinutes());
        String code = RandomStringUtils.randomAlphanumeric(INVITE_CODE_LENGTH);
        return new Invitation(target, expiresAt, code);
    }

    public boolean isUsableAt(final LocalDateTime now) {
        return now.isBefore(expiresAt);
    }

    public Duration remainDuration(final LocalDateTime now) {
        return Duration.between(now, expiresAt);
    }

    public record Target(
            long workspaceId,
            long memberId
    ) {
    }

    /**
     * Invitation 관련 여러가지 설정을 묶어 관리하는 Context
     * 초대 유효 기간, 초대 승인을 위한 패스워드, 역할 등을 관리
     */
    public record Context(
            Duration duration
    ) {
    }
}
