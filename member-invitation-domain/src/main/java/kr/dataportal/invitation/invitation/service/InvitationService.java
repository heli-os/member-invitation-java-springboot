package kr.dataportal.invitation.invitation.service;

import kr.dataportal.invitation.invitation.domain.Invitation;
import kr.dataportal.invitation.invitation.exception.ExpiredInvitationException;
import kr.dataportal.invitation.invitation.exception.NotFoundInvitationException;
import kr.dataportal.invitation.persistence.service.RedisService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Service
public class InvitationService {

    private final RedisService redisService;

    public InvitationService(final RedisService redisService) {
        this.redisService = redisService;
    }

    public void issueInvitation(final Invitation invitation) {
        saveInvitation(invitation);
    }

    public void useInvitation(final Invitation invitation) {
        if (!invitation.isUsableAt(LocalDateTime.now())) {
            throw new ExpiredInvitationException(invitation);
        }
        deleteInvitation(invitation);
    }

    public void expireInvitation(final Invitation invitation) {
        deleteInvitation(invitation);
    }

    public Optional<Invitation> getByTarget(final Invitation.Target target) {
        String invitationRedisKey = invitationRedisKey(target);
        return redisService.get(invitationRedisKey, Invitation.class)
                .filter(invitation -> invitation.isUsableAt(LocalDateTime.now()));
    }

    public Invitation getByInvitationCode(final String invitationCode) {
        String targetRedisKey = targetRedisKey(invitationCode);
        // TODO Exception 고도화
        Invitation.Target target = redisService.get(targetRedisKey, Invitation.Target.class).orElseThrow();
        return getByTarget(target).orElseThrow(() -> new NotFoundInvitationException(invitationCode));
    }

    private void saveInvitation(final Invitation invitation) {
        Duration ttl = invitation.remainDuration(LocalDateTime.now());

        String invitationRedisKey = invitationRedisKey(invitation.target());
        String targetRedisKey = targetRedisKey(invitation.code());

        redisService.set(invitationRedisKey, invitation, ttl);
        redisService.set(targetRedisKey, invitation.target(), ttl);
    }

    private void deleteInvitation(final Invitation invitation) {
        String invitationRedisKey = invitationRedisKey(invitation.target());
        String targetRedisKey = targetRedisKey(invitation.code());

        redisService.delete(invitationRedisKey);
        redisService.delete(targetRedisKey);
    }

    /**
     * Target(WorkspaceId + MemberId) -> Invitation 조회를 위한 키
     */
    private String invitationRedisKey(final Invitation.Target target) {
        return "invitation:target:" + target;
    }

    /**
     * InvitationCode -> Target(WorkspaceId + MemberId) 조회를 위한 키
     */
    private String targetRedisKey(final String invitationCode) {
        return "invitation:code:" + invitationCode;
    }
}
