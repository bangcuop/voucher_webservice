package com.vss.cardservice.service.impl;

import java.util.List;

import com.vss.cardservice.api.IPartnerService;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.service.exception.CardServiceDBException;
import com.vss.cardservice.service.exception.IncorrectPasswordException;
import com.vss.cardservice.service.exception.InvalidIpException;
import com.vss.cardservice.service.exception.LockedPartnerException;
import com.vss.cardservice.service.exception.UnknownPartnerException;
import com.vss.cardservice.service.util.BaseService;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import com.vss.message.util.LoggingUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * @author zannami
 * 
 *         Jul 13, 2011 4:56:01 PM
 */
public class PartnerServiceImpl extends BaseService implements IPartnerService {
    public static int lockDuration = Integer.parseInt(ResourceBundle.getBundle("WSConfig").getString("lockDuration"));


    /**
     * 
     * @return
     * @throws Exception
     */
    public List<Partner> getListPartner() {
        try {
            return mysqlMap.queryForList("partner.getListPartner");
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
        Partner p = null;
        try {
            boolean validCode = false, validPwd = false;
            Partner p12 = ServiceUtil.partnerCollection.get(partnerCode);
            if (p12 != null) {
                if (partnerCode.equalsIgnoreCase(p12.getPartnerCode())) {
                    validCode = true;
                    if (p12.getPassword().equalsIgnoreCase(ServiceUtil.hashData(password))) {
                        validPwd = true;
                        p = p12;
                    }
                }
            } else {
                WebParameter.LIST_PARTNER = this.getListPartner();
                for (Partner p1 : WebParameter.LIST_PARTNER) {
                    if (p1.getPartnerCode().equals(partnerCode)) {
                        validCode = true;
                        System.out.println("-------------------->>> p1.getPassword():" + p1.getPassword());
                        System.out.println("-------------------->>> password:" + password);
                        System.out.println("-------------------->>> ServiceUtil.hashData(password)):" + ServiceUtil.hashData(password));
                        
                        if (p1.getPassword().equalsIgnoreCase(ServiceUtil.hashData(password))) {
                            validPwd = true;
                            p = p1;
                        }
                        break;
                    }
                }
            }
            if (!validCode) {
                throw new UnknownPartnerException();
            }
            if (!validPwd) {
                throw new IncorrectPasswordException();
            }
            if (!p.getIsLock().equals("0")) {
                long nowTime = System.currentTimeMillis();
                long unlockTime = p.getStartLockTime() + lockDuration;
                if (p.getStartLockTime() != -1 && (nowTime < unlockTime)) {
                    throw new LockedPartnerException(String.valueOf(unlockTime - nowTime));
                }
                p.setStartLockTime(-1);
                p.setIsLock("0");
                LoggingUtil.log("[WARN] UNLOCK partner " + p.getPartnerCode() +
                        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), "useCard_transaction");
            }
            if (p.getPartnerCode().equalsIgnoreCase("vss")) {
                return p;
            } else {
                String ips = p.getValidIps();
                if ((ips != null || ip != null) || (ips.indexOf(ip.trim()) > -1)) {
                    return p;
                }
                throw new InvalidIpException();
            }
        } catch (UnknownPartnerException e) {
            throw e;
        } catch (IncorrectPasswordException e) {
            throw e;
        } catch (InvalidIpException e) {
            throw e;
        } catch (LockedPartnerException e) {
//            e.printStackTrace();
            System.out.println("*************** partner is LOCKED : "+partnerCode);
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }

    public boolean updatePartner(String partnerCode, String validips) {
        try {
            Map map = new HashMap();
            map.put("validips", validips);
            map.put("partnercode", partnerCode);
            mysqlMap.update("partner.updatePartner", map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }

    public Partner getPartner(String partnerCode) {
        try {
            Map map = new HashMap();
            map.put("partnercode", partnerCode);
            return (Partner) mysqlMap.queryForObject("partner.getListPartner", map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }
}
