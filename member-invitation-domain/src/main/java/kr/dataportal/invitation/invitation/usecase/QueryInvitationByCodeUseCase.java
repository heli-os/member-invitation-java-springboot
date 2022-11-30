package kr.dataportal.invitation.invitation.usecase;

import kr.dataportal.invitation.invitation.domain.Invitation;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public interface QueryInvitationByCodeUseCase {

    Invitation query(final Query query);

    record Query(
            String code
    ) {
    }
}
