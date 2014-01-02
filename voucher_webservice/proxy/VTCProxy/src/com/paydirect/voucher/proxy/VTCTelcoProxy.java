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
public class VTCTelcoProxy extends VTCProxy {

    public VTCTelcoProxy() {
        wsURL = configure.getString("VTC_VMS_webservice");
        partnerKey = configure.getString("VTC_VMS_PartnerKey");
        partnerId = configure.getString("VTC_VMS_PartnerID");
        soap = getVTCServiceByWSDL(wsURL);
    }
//    public static void main(String[] args) {
////        String[][] vcoin = new String[][]{{"PM0000015076", "230815621092"}, {"PM0000015077", "596794015838"}, {"PM0000015078", "636790822792"}};
//        RequestResponse response = request(MOBIPHONE, "5841000080695", "10791130194736", 12345, String.valueOf(System.currentTimeMillis()));
//        System.out.println(response.getResponseStatus());
//        System.out.println(response.getDescription());
////        System.out.println(10 % 1000);
//    }

    public RequestResponse useCard(String provider, String cardSerial, String cardCode, long transactionId, String accountName) {
        return submit(USECARD, cardCode, cardSerial, provider + "|" + transactionId + "|" + accountName);
    }
}
