/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paydirect.voucher.proxy;

import com.hdpay.vtc.dto.RequestResponse;

/**
 *
 * @author koziwa
 */
public class VTCVcoinProxy extends VTCProxy {

    public static final String CHECKCARD = "CheckCardStatus";
    public static final String CHECKCARDVALUE = "CheckCardValue";

    public VTCVcoinProxy() {
        wsURL = configure.getString("VTC_webservice");
        partnerKey = configure.getString("VTC_PartnerKey");
        partnerId = configure.getString("VTC_PartnerID");
        soap = getVTCServiceByWSDL(wsURL);
    }

    public static void main(String[] args) {
//        RequestResponse response = checkCardStatus(wsURL, partnerKey, partnerId, "PM0000010041", "kt@ahha.vn");
//        RequestResponse response = checkCardValue(wsURL, partnerKey, partnerId, "609788942090", "PM0000010041", "kt@ahha.vn");
        RequestResponse response = new VTCVcoinProxy().useCard("174863592046", "PM0000020100", "test homedirect");
        System.out.println(response.getResponseStatus());
        System.out.println(response.getDescription());
    }

    public RequestResponse checkCardStatus(String cardSerial, String description) {
        RequestResponse response = submit(CHECKCARD, "", cardSerial, description);
        return response;
    }

    public RequestResponse checkCardValue(String cardCode, String cardSerial, String description) {
        RequestResponse response = submit(CHECKCARDVALUE, cardCode, cardSerial, description);
        return response;
    }

    public RequestResponse useCard(String cardCode, String cardSerial, String description) {
        RequestResponse response = submit(USECARD, cardCode, cardSerial, description);
        return response;
    }
}
