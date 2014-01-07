/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.IPartnerService;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.PartnerInfo;
import com.vss.cardservice.service.exception.InvalidSignatureException;

/**
 *
 * @author thang.tranquyet
 */
public class PartnerServiceUtil {

    private static IPartnerService partnerService;

    public static Partner loadPartnerById(int partnerId) throws Exception {
        return partnerService.loadPartnerById(partnerId);
    }

    public static Partner getProcessPartner(String issuerCode) {
        return partnerService.getProcessPartner(issuerCode);
    }

    public static Partner validateInfo(PartnerInfo partnerInfo, String ip, String issuer, String cardCode, String transRef) throws InvalidSignatureException {
        return partnerService.validateInfo(partnerInfo, ip, issuer, cardCode, transRef);
    }

    public static void checkLockIssuerPartner(Issuer issuer, Partner provider) {
        try {
            partnerService.checkLockIssuerPartner(issuer, provider);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //////////////

    public static IPartnerService getPartnerService() {
        return partnerService;
    }

    public void setPartnerService(IPartnerService partnerService) {
        PartnerServiceUtil.partnerService = partnerService;
    }
}
