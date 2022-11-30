package kr.dataportal.invitation.api.member.mapper;

import kr.dataportal.invitation.api.member.dto.MemberResponseDto;
import kr.dataportal.invitation.member.domain.Member;
import kr.dataportal.invitation.util.DateTimeUtils;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public class MemberMapper {

    public static MemberResponseDto toResponseDto(final Member member) {
        return new MemberResponseDto(
                member.id(),
                DateTimeUtils.toEpochMillis(member.createdAt()),
                DateTimeUtils.toEpochMillis(member.lastModifiedAt()),
                member.name(),
                member.phoneNumber(),
                member.email(),
                member.status().name()
        );
    }
}
