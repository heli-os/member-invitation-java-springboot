package kr.dataportal.invitation.member.service;

import kr.dataportal.invitation.member.domain.Member;
import kr.dataportal.invitation.member.usecase.QueryMemberByIdUseCase;
import kr.dataportal.invitation.persistence.entity.member.MemberJpaEntity;
import kr.dataportal.invitation.persistence.service.member.MemberQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Service
@Transactional(readOnly = true)
public class QueryMemberById implements QueryMemberByIdUseCase {

    private final MemberQuery memberQuery;

    public QueryMemberById(final MemberQuery memberQuery) {
        this.memberQuery = memberQuery;
    }

    @Override
    public Member query(final Query query) {
        MemberJpaEntity memberJpaEntity = memberQuery.findById(query.memberId());
        return new Member(
                memberJpaEntity.getId(),
                memberJpaEntity.getCreatedAt(),
                memberJpaEntity.getLastModifiedAt(),
                memberJpaEntity.getName(),
                memberJpaEntity.getPhoneNumber(),
                memberJpaEntity.getEmail(),
                memberJpaEntity.getStatus()
        );
    }
}
