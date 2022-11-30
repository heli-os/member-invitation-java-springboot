package kr.dataportal.invitation.api.invitation.mapper;

import kr.dataportal.invitation.api.invitation.dto.InvitationResponseDto;
import kr.dataportal.invitation.invitation.domain.Invitation;
import kr.dataportal.invitation.util.DateTimeUtils;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public class InvitationMapper {

    public static InvitationResponseDto toResponseDto(final Invitation invitation) {
        return new InvitationResponseDto(
                invitation.target().workspaceId(),
                invitation.target().memberId(),
                DateTimeUtils.toEpochMillis(invitation.expiresAt()),
                invitation.code()
        );
    }
}
