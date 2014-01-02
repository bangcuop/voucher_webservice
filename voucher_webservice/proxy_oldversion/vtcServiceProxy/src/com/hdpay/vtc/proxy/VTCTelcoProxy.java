/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdpay.vtc.proxy;

import com.hdpay.vtc.dto.CardRequest;
import com.hdpay.vtc.dto.RequestResponse;
import com.hdpay.vtc.exception.VTCServiceException;
import com.thoughtworks.xstream.XStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import webapi.card.vtconline.CardSoap;

/**
 *
 * @author koziwa
 */
public class VTCTelcoProxy extends WebServiceProxy {

    public static String VTC_COIN = "VTC";
    public static String MOBIPHONE = "VMS";
    public static String VIETTEL = "VTEL";
    public static String VINAPHONE = "GPC";
    public static String SFONE = "SFONE";
    public static String VIETNAM_MOBILE = "VNM";

//    public static void main(String[] args) {
////        String[][] vcoin = new String[][]{{"PM0000015076", "230815621092"}, {"PM0000015077", "596794015838"}, {"PM0000015078", "636790822792"}};
//        RequestResponse response = request(MOBIPHONE, "5841000080695", "10791130194736", 12345, String.valueOf(System.currentTimeMillis()));
//        System.out.println(response.getResponseStatus());
//        System.out.println(response.getDescription());
////        System.out.println(10 % 1000);
//    }

    /**
     * 
     * @param provider
     * @param cardSerial
     * @param cardCode
     * @param transactionId
     * @param accountName
     * @return
     */
    public static RequestResponse request(String provider, String cardSerial, String cardCode, long transactionId, String accountName) {
        try {
            String description = provider + "|" + transactionId + "|" + accountName;
            String partnerKey = "", partnerID = "";
            String wsURL = "";
            if (provider.equals(MOBIPHONE) || provider.equals(VINAPHONE) || provider.equals(VIETTEL)) {
                partnerKey = configure.getString("VTC_VMS_PartnerKey");
                partnerID = configure.getString("VTC_VMS_PartnerID");
                wsURL = configure.getString("VTC_VMS_webservice");
            } 
            CardSoap soap = getVTCServiceByWSDL(wsURL);
            System.out.println(wsURL);
            System.out.println(partnerKey);
            System.out.println(partnerID);
            CardRequest card = new CardRequest();
            card.setFunction("UseCard");
            card.setCardCode(cardCode);
            card.setCardID(cardSerial);
            card.setDescription(description);
            XStream xml = new XStream();
            xml.alias("CardRequest", CardRequest.class);
            String requestData = xml.toXML(card);

            requestData = VTCEncrypt(partnerKey, requestData);

            String res = VTCDecrypt(partnerKey, soap.request(Integer.valueOf(partnerID), requestData));

            Pattern pStatus = Pattern.compile("<ResponseStatus>(.*)</ResponseStatus>");

            Matcher m = pStatus.matcher(res);
            RequestResponse response = new RequestResponse();
            response.setXml(res);
            if (m.find()) {
                try {
                    response.setResponseStatus(Integer.valueOf(m.group(1)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Pattern pDesc = Pattern.compile("<Descripton>(.*)</Descripton>");
            m = pDesc.matcher(res);
            if (m.find()) {
                response.setDescription(m.group(1));
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new VTCServiceException();
        }
    }
}
