package kr.dataportal.invitation.persistence.repository.member;

import kr.dataportal.invitation.persistence.entity.member.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public interface MemberRepository extends JpaRepository<MemberJpaEntity, Long> {
}
