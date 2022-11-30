package kr.dataportal.invitation.api.invitation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public record InviteWorkspaceRequestDto(
        @NotEmpty
        String inviteMemberName,
        @NotEmpty
        String inviteMemberPhoneNumber,
        @Email
        String inviteMemberEmail
) {
}
