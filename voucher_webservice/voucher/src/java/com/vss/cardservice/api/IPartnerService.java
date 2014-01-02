package com.vss.cardservice.api;

import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Partner;
import java.util.List;

/**
 *
 * @author zannami
 *
 * Jul 13, 2011 11:22:55 AM
 */
public interface IPartnerService {

    public Partner checkPartnerRequest(String partnerCode, String password, String ip);

    public Partner getProcessPartner(int issuerId);

    public List<Partner> getPartnerList(String partnerCode, Boolean isProvider, Boolean keepSession, Boolean isLock);

    public List<Partner> getPartnerList(String partnerCode, Boolean isProvider, Boolean keepSession);

    public List<Partner> getPartnerList(String partnerCode, Boolean isProvider);

    public List<Partner> getPartnerList();

    public boolean updatePartner(Partner partner);

    public void updateSessionValue(String partnerCode, String sessionValue) throws Exception;

    public void checkLockIssuerPartner(Issuer issuer, Partner provider) throws Exception;
}
