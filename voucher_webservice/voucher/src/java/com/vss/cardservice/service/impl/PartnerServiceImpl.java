package com.vss.cardservice.service.impl;

import java.util.List;

import com.vss.cardservice.api.IPartnerService;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.PartnerInfo;
import com.vss.cardservice.service.exception.*;
import com.vss.cardservice.service.util.BaseService;
import com.vss.cardservice.service.util.MailServiceUtil;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.thread.AlertThread;
import com.vss.cardservice.thread.ThreadManager;
import com.vss.vcard.dto.IssuerPartner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import vcard.service.proxy.VcardServiceProxy;

/**
 *
 * @author zannami
 *
 * Jul 13, 2011 4:56:01 PM
 */
public class PartnerServiceImpl extends BaseService implements IPartnerService {

    private static final Logger logger = Logger.getLogger("PartnerService");
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static String LOCK_ISSUER_PARTNER_SUBJECT = ServiceUtil.serverIp + "Tu dong khoa nha cung cap ";
    private static String LOCK_ISSUER_PARTNER_MAIL = "Tam thoi khoa nha cung cap telco loai the issuer.";
    private static String FOUND_ISSUER_PARTNER_MAIL = "Mo khoa nha cung cap du phong telco.";
    private static String NOT_FOUND_ISSUER_PARTNER_MAIL = "Khong tim thay nha cung cap du phong.";
    private static String PROPORTION_MAIL = "Ty trong NCC telco_percent";
    private static String APPEND = ": ";
    private static String APPEND_2 = ", ";
    private static String ALERT_CARD_PENDING = "Canh bao giao dich tre nha cung cap ";

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
            logger.error(partnerCode + " Sai mat khau : sys_pass=" + partner.getPassword() + ", partner_pass=" + partnerHashedPassword);
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
                logger.warn(" UNLOCK partner " + partnerCode + " : " + df.format(now));
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

    public Partner getProcessPartner(String issuerCode) {
        try {
            Partner partner = (Partner) mysqlMap.queryForObject("partner.getProcessPartner", ServiceUtil.issuerMap.get(issuerCode));
            if (partner.getPartnerName() != null && !partner.getPartnerName().isEmpty()) {
                String phones = MailServiceUtil.getString("alert_card_pending");
                StringBuilder smsContent = new StringBuilder();
                Calendar cal = Calendar.getInstance();
                smsContent.append("Canh bao: Vao thoi diem ");
                smsContent.append(df.format(cal.getTime()));
                smsContent.append(" he thong ");
                smsContent.append(partner.getPartnerName());
                smsContent.append(" co nhieu giao dich dang xu ly voi loai the ");
                smsContent.append(issuerCode);
                String message = smsContent.toString();
                ThreadManager.execute(new AlertThread(ALERT_CARD_PENDING + partner.getPartnerCode(), message, message, phones, false));
            }
            return partner;
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

            //lay danh sach nha cung cap dang mo voi loai the nay 
            StringBuilder tytrongBuilder = new StringBuilder();
            List<IssuerPartner> listIssuerPartners = VcardServiceProxy.getIssuerPartnerList(issuer.getIssuerId(), Boolean.FALSE, null, null, Boolean.FALSE, 0);
            if (listIssuerPartners.size() > 0) {
                for (int i = 0; i < listIssuerPartners.size(); i++) {
                    IssuerPartner issuerPartner = listIssuerPartners.get(i);
                    tytrongBuilder.append(issuerPartner.getPartner());
                    tytrongBuilder.append(APPEND);
                    tytrongBuilder.append(issuerPartner.getApplySalesPercent());
                    if (i != (listIssuerPartners.size() - 1)) {
                        tytrongBuilder.append(APPEND_2);
                    }
                }
                message += PROPORTION_MAIL.replaceAll("telco_percent", tytrongBuilder.toString());
            }
            ThreadManager.execute(new AlertThread(LOCK_ISSUER_PARTNER_SUBJECT + provider.getPartnerCode(), message, message, null, false));
        }
    }

    public Partner validateInfo(PartnerInfo partnerInfo, String ip, String issuer, String cardCode, String transRef) throws InvalidSignatureException {
        Partner validPartner = checkPartnerRequest(partnerInfo.getPartnerCode(), partnerInfo.getPassword(), ip);
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
            logger.error(partnerInfo.getPartnerCode() + " Sai chu ky : sys_sign=" + validData + ", partner_sign=" + signature);
            throw new InvalidSignatureException();
        }
        return validPartner;
    }

    public Partner loadPartnerById(Integer partnerId) throws Exception {
        return (Partner) mysqlMap.queryForObject("partner.loadPartnerById", partnerId);
    }
}
