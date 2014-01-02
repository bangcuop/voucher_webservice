/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.IGameAccountService;
import com.vss.cardservice.api.IPartnerService;
import com.vss.cardservice.dto.Partner;
import java.util.List;

/**
 *
 * @author thang.tranquyet
 */
public class IssuerServiceUtil {

    private static IPartnerService partnerService;
    private static IGameAccountService gameAccountService;
    private static List<String> gameAccountList;

    public static String getSessionValue(String partnerCode) {
        List<Partner> partnerList = partnerService.getPartnerList(partnerCode, true);
        return partnerList.isEmpty() ? null : partnerList.get(0).getSessionValue();
    }

    public static void updateSessionValue(String partnerCode, String sessionValue) throws Exception {
        partnerService.updateSessionValue(partnerCode, sessionValue);
    }

    public static String getRandomGameAccount() {
        if (gameAccountList == null) {
            gameAccountList = gameAccountService.getAccountList();
        }
        if (gameAccountList.isEmpty()) {
            return "homedirect" + Math.round(100 * Math.random());
        } else {
            return gameAccountList.get(Math.round(Double.valueOf(Math.random() * gameAccountList.size()).intValue()));
        }
    }

    public static String getRandomGameService(Integer partnerId) {
        return gameAccountService.getRandomGameService(partnerId);
    }
    ////////

    public void setGameAccountService(IGameAccountService gameAccountService) {
        IssuerServiceUtil.gameAccountService = gameAccountService;
    }

    public void setPartnerService(IPartnerService partnerService) {
        IssuerServiceUtil.partnerService = partnerService;
    }
}
