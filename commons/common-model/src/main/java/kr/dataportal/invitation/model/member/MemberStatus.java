package kr.dataportal.invitation.model.member;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public enum MemberStatus {
    CANDIDATE("그룹 초대 등을 통한 임시 가입 상태"),
    ACTIVATE("계정 활성화 상태"),
    DEACTIVATE("계정 탈퇴 상태");

    private final String description;

    MemberStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
