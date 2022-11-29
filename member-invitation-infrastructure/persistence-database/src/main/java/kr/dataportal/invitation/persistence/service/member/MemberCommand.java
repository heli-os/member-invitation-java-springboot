package kr.dataportal.invitation.persistence.service.member;

import kr.dataportal.invitation.persistence.entity.member.MemberJpaEntity;
import kr.dataportal.invitation.persistence.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Service
@Transactional
public class MemberCommand {
    private final MemberRepository memberRepository;

    public MemberCommand(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberJpaEntity newCandidate(final String name, final String phoneNumber, final String email) {
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.newCandidate(name, phoneNumber, email);
        return memberRepository.save(memberJpaEntity);
    }

    public MemberJpaEntity activate(final MemberJpaEntity entity) {
        return entity.activate();
    }
}
