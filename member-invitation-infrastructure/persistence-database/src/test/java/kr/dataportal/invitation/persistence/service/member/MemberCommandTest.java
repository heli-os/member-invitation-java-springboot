package kr.dataportal.invitation.persistence.service.member;

import kr.dataportal.invitation.model.member.MemberStatus;
import kr.dataportal.invitation.persistence.entity.member.MemberJpaEntity;
import kr.dataportal.invitation.persistence.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberCommandTest {

    private final String name = "Heli";
    private final String phoneNumber = "010-1111-2222";
    private final String email = "heli@example.com";
    private final MemberJpaEntity candidateMemberJpaEntity = MemberJpaEntity.newCandidate(name, phoneNumber, email);
    private MemberCommand sut;
    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        sut = new MemberCommand(memberRepository);
    }

    @Test
    void 후보_Member_엔티티를_생성할_수_있다() {
        when(memberRepository.save(any())).then(returnsFirstArg());

        MemberJpaEntity actual = sut.newCandidate(name, phoneNumber, email);

        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getStatus()).isEqualTo(MemberStatus.CANDIDATE);
    }

    @Test
    void 후보_멤버를_활성화_할_수_있다() {
        MemberJpaEntity actual = sut.activate(candidateMemberJpaEntity);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getStatus()).isEqualTo(MemberStatus.ACTIVATE);
    }
}
