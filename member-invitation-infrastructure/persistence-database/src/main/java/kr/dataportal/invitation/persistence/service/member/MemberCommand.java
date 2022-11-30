package kr.dataportal.invitation.persistence.service.member;

import kr.dataportal.invitation.model.member.InviteMember;
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

    public MemberJpaEntity newCandidate(final InviteMember inviteMember) {
        // TODO 매번 Candidate를 만드는 것 보단 name + phoneNumber + email 조합으로 동일한 계정이 있는 경우 해당 계정을 초대하는 방향으로 잡는 것이 좋아보임 / getOrCreateCandidate
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.newCandidate(inviteMember.name(), inviteMember.phoneNumber(), inviteMember.email());
        return memberRepository.save(memberJpaEntity);
    }

    public MemberJpaEntity activate(final MemberJpaEntity entity) {
        return entity.activate();
    }

    public MemberJpaEntity activate(final long memberId) {
        // TODO Exception 고도화
        MemberJpaEntity memberJpaEntity = memberRepository.findById(memberId).orElseThrow();
        return activate(memberJpaEntity);
    }
}
