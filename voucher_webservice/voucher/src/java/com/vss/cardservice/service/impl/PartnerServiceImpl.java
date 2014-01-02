package com.vss.cardservice.service.impl;

import java.util.List;

import com.vss.cardservice.api.IPartnerService;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.service.exception.CardServiceDBException;
import com.vss.cardservice.service.exception.IncorrectPasswordException;
import com.vss.cardservice.service.exception.InvalidIpException;
import com.vss.cardservice.service.exception.LockedPartnerException;
import com.vss.cardservice.service.exception.UnknownPartnerException;
import com.vss.cardservice.service.util.BaseService;
import com.vss.cardservice.service.util.MailServiceUtil;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.message.util.LoggingUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import vcard.service.proxy.VcardServiceProxy;

/**
 *
 * @author zannami
 *
 * Jul 13, 2011 4:56:01 PM
 */
public class PartnerServiceImpl extends BaseService implements IPartnerService {

    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static String LOCK_ISSUER_PARTNER_SUBJECT = ServiceUtil.serverIp + "Tu dong khoa nha cung cap ";
    private static String LOCK_ISSUER_PARTNER_MAIL = "Tam thoi khoa nha cung cap telco loai the issuer.";
    private static String FOUND_ISSUER_PARTNER_MAIL = "Mo khoa nha cung cap du phong telco.";
    private static String NOT_FOUND_ISSUER_PARTNER_MAIL = "Khong tim thay nha cung cap du phong.";

    /**
     *
     * @return @throws Exception
     */
    public List<Partner> getPartnerList() {
        return getPartnerList(null, false);
    }

    public List<Partner> getPartnerList(String partnerCode, Boolean isProvider) {
        return getPartnerList(partnerCode, isProvider, null);
    }

    public List<Partner> getPartnerList(String partnerCode, Boolean isProvider, Boolean keepSession) {
        return getPartnerList(partnerCode, isProvider, keepSession, null);
    }

    public List<Partner> getPartnerList(String partnerCode, Boolean isProvider, Boolean keepSession, Boolean isLock) {
        try {
            Map map = new HashMap();
            map.put("partnerCode", partnerCode);
            map.put("isProvider", isProvider);
            map.put("keepSession", keepSession);
            map.put("isLock", isLock);
            return mysqlMap.queryForList("partner.getPartnerList", map);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CardServiceDBException(ex);
        }
    }

    /**
     *
     * @param wsc
     * @return
     */
    public Partner checkPartnerRequest(String partnerCode, String password, String ip) {
        Partner partner = ServiceUtil.partnerCollection.get(partnerCode);

        if (partner == null) {
            List<Partner> list = getPartnerList(partnerCode, false);
            if (!list.isEmpty()) {
                partner = list.get(0);
            }
        }

        if (partner == null) {
            throw new UnknownPartnerException();
        }
        String partnerHashedPassword = ServiceUtil.hashData(password);
        if (!partner.getPassword().equalsIgnoreCase(partnerHashedPassword)) {
            LoggingUtil.log("[ERROR][" + partnerCode + "] Sai mat khau : sys_pass=" + partner.getPassword() + ", partner_pass=" + partnerHashedPassword, "useCard_transaction");
            throw new IncorrectPasswordException();
        }

        if (partner.getIsLock().equals("1")) {
            Date now = new Date();
            Long unlockTime = partner.getUnlockTime();
            if (unlockTime == null) {
                throw new LockedPartnerException("2020-01-01 00:00:00"); // khoa vinh vien
            } else if (now.getTime() > unlockTime) {
                partner.setIsLock("0");
                partner.setFailedCount(0);
                LoggingUtil.log("[WARN] UNLOCK partner " + partnerCode + " : " + df.format(now), "useCard_transaction");
            } else {
                throw new LockedPartnerException(df.format(new Date(partner.getUnlockTime())));
            }
        }
        ServiceUtil.partnerCollection.put(partnerCode, partner);
        String ips = partner.getValidIps();
        if (ips == null || (!ips.equals("%") && ips.indexOf(ip) == -1)) {
            throw new InvalidIpException();
        } else {
            return partner;
        }
    }

    public boolean updatePartner(Partner partner) {
        try {
            mysqlMap.update("partner.updatePartner", partner);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }

    public Partner getProcessPartner(int issuerId) {
        try {
            return (Partner) mysqlMap.queryForObject("partner.getProcessPartner", issuerId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }

    public void updateSessionValue(String partnerCode, String sessionValue) throws Exception {
        Map map = new HashMap();
        map.put("partnerCode", partnerCode);
        map.put("sessionValue", sessionValue);
        mysqlMap.update("partner.updateSessionValue", map);
    }

    public void checkLockIssuerPartner(Issuer issuer, Partner provider) throws Exception {
        Map map = new HashMap();
        map.put("issuerId", issuer.getIssuerId());
        map.put("providerId", provider.getPartnerId());
        Integer alternateProviderId = (Integer) mysqlMap.queryForObject("partner.checkLockIssuerPartner", map);
        if (alternateProviderId != null) { // khoa nha cung cap hien tai
            VcardServiceProxy.changeLockStatus(provider.getPartnerId(), issuer.getIssuerId(), true);
            String message = ServiceUtil.serverIp + LOCK_ISSUER_PARTNER_MAIL.replaceAll("telco", provider.getPartnerCode()).replaceAll("issuer", issuer.getIssuer());

            if (alternateProviderId == 0) { // Van ton tai nha cung cap khac dang chay
            } else if (alternateProviderId == -1) { // khong tim thay nha cung cap du phong
                message += NOT_FOUND_ISSUER_PARTNER_MAIL;
            } else { // Mo khoa nha cung cap du phong
                message += FOUND_ISSUER_PARTNER_MAIL.replaceAll("telco", ServiceUtil.providerCollection.get(alternateProviderId).getPartnerCode());
                VcardServiceProxy.changeLockStatus(alternateProviderId, issuer.getIssuerId(), false);
            }
            MailServiceUtil.sendAlert(LOCK_ISSUER_PARTNER_SUBJECT + provider.getPartnerCode(), message, message, null, false);
        }
    }
}
