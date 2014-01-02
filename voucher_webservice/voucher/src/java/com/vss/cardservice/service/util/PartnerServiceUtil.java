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
import com.vss.message.util.LoggingUtil;

/**
 *
 * @author thang.tranquyet
 */
public class PartnerServiceUtil {

    private static IPartnerService partnerService;

    public static Partner getProcessPartner(int issuerId) {
        return partnerService.getProcessPartner(issuerId);
    }

    public static Partner validateSignature(PartnerInfo partnerInfo, String ip, String issuer, String cardCode, String transRef) throws InvalidSignatureException {
        Partner validPartner = partnerService.checkPartnerRequest(partnerInfo.getPartnerCode(), partnerInfo.getPassword(), ip);
        String secretKey = validPartner.getSecretKey();
        String signature = partnerInfo.getSignature();
        String validData;
        if (cardCode == null) {
            // signature for getTransactionDetail
            validData = ServiceUtil.hashData(transRef + partnerInfo.getPartnerCode() + partnerInfo.getPassword() + secretKey);
        } else {
            // signature for useCard
//            if (ServiceUtil.PPCARD_GATEWAY_IP.indexOf(ip) == -1) {
            validData = ServiceUtil.hashData(issuer + cardCode + transRef + partnerInfo.getPartnerCode() + partnerInfo.getPassword() + secretKey);
//            } else {
//            validData = ServiceUtil.hashData(cardCode + transRef + partnerInfo.getPartnerCode() + partnerInfo.getPassword() + secretKey);
//            }
        }
        if (!validData.equals(signature)) {
            LoggingUtil.log("[ERROR][" + partnerInfo.getPartnerCode() + "] Sai chu ky : sys_sign=" + validData + ", partner_sign=" + signature, "useCard_transaction");
            throw new InvalidSignatureException();
        }
        return validPartner;
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
