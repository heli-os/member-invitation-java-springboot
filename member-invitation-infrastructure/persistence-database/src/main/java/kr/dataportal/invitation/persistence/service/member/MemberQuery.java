package kr.dataportal.invitation.persistence.service.member;

import kr.dataportal.invitation.persistence.entity.member.MemberJpaEntity;
import kr.dataportal.invitation.persistence.repository.member.MemberRepository;
import kr.dataportal.invitation.persistence.service.member.exception.NotFoundMemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Service
@Transactional(readOnly = true)
public class MemberQuery {
    private final MemberRepository memberRepository;

    public MemberQuery(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberJpaEntity findById(final long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
    }
}
