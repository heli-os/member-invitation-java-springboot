package kr.dataportal.invitation.member.domain;

import kr.dataportal.invitation.model.member.MemberStatus;

import java.time.LocalDateTime;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public record Member(
        long id,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        String name,
        String phoneNumber,
        String email,
        MemberStatus status
) {
}
