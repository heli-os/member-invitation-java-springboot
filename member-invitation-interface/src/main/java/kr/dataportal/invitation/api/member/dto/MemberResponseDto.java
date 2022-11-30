package kr.dataportal.invitation.api.member.dto;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public record MemberResponseDto(
        long id,
        long createdAt,
        long lastModifiedAt,
        String name,
        String phoneNumber,
        String email,
        String status
) {
}
