package kr.dataportal.invitation.member.usecase;

import kr.dataportal.invitation.member.domain.Member;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public interface QueryMemberByIdUseCase {

    Member query(final Query query);

    record Query(
            long memberId
    ) {
    }
}
