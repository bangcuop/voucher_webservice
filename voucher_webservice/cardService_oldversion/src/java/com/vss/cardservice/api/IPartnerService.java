package com.vss.cardservice.api;


import com.vss.cardservice.dto.Partner;
import java.util.List;

/**
 *
 * @author zannami
 *
 * Jul 13, 2011 11:22:55 AM
 */
public interface IPartnerService {

    List<Partner> getListPartner();

    Partner checkPartnerRequest(String partnerCode, String password, String ip);

    boolean updatePartner(String partnerCode, String validips);

    Partner getPartner(String partnerCode);
}
