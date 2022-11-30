package kr.dataportal.invitation.persistence.entity.member;

import kr.dataportal.invitation.model.member.MemberStatus;
import kr.dataportal.invitation.persistence.config.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Entity
@Table(name = "member")
public class MemberJpaEntity extends BaseEntity<MemberJpaEntity> {

    private String name;
    private String phoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    protected MemberJpaEntity() {
    }

    private MemberJpaEntity(final String name, final String phoneNumber, final String email, final MemberStatus status) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
    }

    public static MemberJpaEntity newCandidate(final String name, final String phoneNumber, final String email) {
        return new MemberJpaEntity(name, phoneNumber, email, MemberStatus.CANDIDATE);
    }

    public MemberJpaEntity activate() {
        this.status = MemberStatus.ACTIVATE;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public MemberStatus getStatus() {
        return status;
    }
}
