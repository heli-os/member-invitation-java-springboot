package kr.dataportal.invitation.invitation.service;

import kr.dataportal.invitation.invitation.domain.Invitation;
import kr.dataportal.invitation.invitation.usecase.QueryInvitationByCodeUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@Service
@Transactional(readOnly = true)
public class QueryInvitationByCode implements QueryInvitationByCodeUseCase {

    private final InvitationService invitationService;

    public QueryInvitationByCode(final InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @Override
    public Invitation query(final Query query) {
        return invitationService.getByInvitationCode(query.code());
    }
}
