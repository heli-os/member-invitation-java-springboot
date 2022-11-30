package kr.dataportal.invitation.api.invitation.dto;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public record InvitationResponseDto(
        long workspaceId,
        long memberId,
        long expiresAt,
        String code
) {
}
