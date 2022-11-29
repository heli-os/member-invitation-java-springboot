package kr.dataportal.invitation.persistence.service.member;

import kr.dataportal.invitation.model.member.MemberStatus;
import kr.dataportal.invitation.persistence.entity.member.MemberJpaEntity;
import kr.dataportal.invitation.persistence.repository.member.MemberRepository;
import kr.dataportal.invitation.persistence.service.member.exception.NotFoundMemberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberQueryTest {

    private final MemberJpaEntity candidateMemberJpaEntity = MemberJpaEntity.newCandidate("Heli", "010-1111-2222", "heli@example.com");
    @Mock
    private MemberRepository memberRepository;
    private MemberQuery sut;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        sut = new MemberQuery(memberRepository);
    }

    @Test
    void id_를_이용해_엔티티_조회_실패_시_NotFoundMemberException_예외_발생() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundMemberException.class,
                () -> sut.findById(1L)
        );
    }

    @Test
    void id_를_이용해_엔티티_조회_성공_시_엔티티_응답() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(candidateMemberJpaEntity));

        MemberJpaEntity actual = sut.findById(1L);

        assertThat(actual.getName()).isEqualTo(actual.getName());
        assertThat(actual.getPhoneNumber()).isEqualTo(actual.getPhoneNumber());
        assertThat(actual.getEmail()).isEqualTo(actual.getEmail());
        assertThat(actual.getStatus()).isEqualTo(MemberStatus.CANDIDATE);
    }
}
