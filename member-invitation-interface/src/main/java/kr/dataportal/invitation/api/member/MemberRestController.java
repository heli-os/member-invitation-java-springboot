package kr.dataportal.invitation.api.member;

import kr.dataportal.invitation.api.member.dto.MemberResponseDto;
import kr.dataportal.invitation.api.member.mapper.MemberMapper;
import kr.dataportal.invitation.member.domain.Member;
import kr.dataportal.invitation.member.usecase.QueryMemberByIdUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@RestController
public class MemberRestController {

    private final QueryMemberByIdUseCase queryMemberByIdUseCase;

    public MemberRestController(final QueryMemberByIdUseCase queryMemberByIdUseCase) {
        this.queryMemberByIdUseCase = queryMemberByIdUseCase;
    }

    @GetMapping("/api/v1/member/{memberId}")
    public MemberResponseDto member(
            @PathVariable final long memberId
    ) {
        Member member = queryMemberByIdUseCase.query(new QueryMemberByIdUseCase.Query(
                memberId
        ));

        return MemberMapper.toResponseDto(member);
    }
}
