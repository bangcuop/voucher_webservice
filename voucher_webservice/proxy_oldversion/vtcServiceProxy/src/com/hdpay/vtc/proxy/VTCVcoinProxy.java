/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdpay.vtc.proxy;

import com.hdpay.vtc.dto.CardRequest;
import com.hdpay.vtc.dto.RequestResponse;
import com.thoughtworks.xstream.XStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import webapi.card.vtconline.CardSoap;

/**
 *
 * @author koziwa
 */
public class VTCVcoinProxy extends WebServiceProxy {

    static String CHECKCARD = "CheckCardStatus";
    static String CHECKCARDVALUE = "CheckCardValue";
    static String USECARD = "UseCard";
    public static String wsURL = configure.getString("VTC_webservice");
    public static String partnerKey = configure.getString("VTC_PartnerKey");
    public static String partnerId = configure.getString("VTC_PartnerID");
//    public static void main(String[] args) {
////        RequestResponse response = checkCardStatus(wsURL, partnerKey, partnerId, "PM0000010041", "kt@ahha.vn");
////        RequestResponse response = checkCardValue(wsURL, partnerKey, partnerId, "609788942090", "PM0000010041", "kt@ahha.vn");
//        RequestResponse response = useCard(wsURL, partnerKey, partnerId, "609788942090", "PM0000010041", "kt@ahha.vn");
//        System.out.println(response.getResponseStatus());
//        System.out.println(response.getDescription());
//    }

    /**
     * 
     * @param wsURL
     * @param partnerKey
     * @param partnerID
     * @param cardSerial
     * @param description
     * @return
     */
    public static RequestResponse checkCardStatus(String wsURL, String partnerKey, String partnerID, String cardSerial, String description) {
        RequestResponse response = submit(CHECKCARD, wsURL, partnerKey, partnerID, "", cardSerial, description);
        return response;
    }

    /**
     * 
     * @param wsURL
     * @param partnerKey
     * @param partnerID
     * @param cardCode
     * @param cardSerial
     * @param description
     * @return
     */
    public static RequestResponse checkCardValue(String wsURL, String partnerKey, String partnerID, String cardCode, String cardSerial, String description) {
        RequestResponse response = submit(CHECKCARDVALUE, wsURL, partnerKey, partnerID, cardCode, cardSerial, description);
        return response;
    }

    /**
     *
     * @param wsURL
     * @param partnerKey
     * @param partnerID
     * @param cardCode
     * @param cardSerial
     * @param description
     * @return
     */
    public static RequestResponse useCard(String wsURL, String partnerKey, String partnerID, String cardCode, String cardSerial, String description) {
        RequestResponse response = submit(USECARD, wsURL, partnerKey, partnerID, cardCode, cardSerial, description);
        return response;
    }

    /**
     * 
     * @param command
     * @param wsURL
     * @param partnerKey
     * @param partnerID
     * @param cardCode
     * @param cardSerial
     * @param description
     * @return
     */
    static RequestResponse submit(String command, String wsURL, String partnerKey, String partnerID,
            String cardCode, String cardSerial, String description) {
        try {
            CardSoap soap = getVTCServiceByWSDL(wsURL);
            System.out.println(wsURL);
            System.out.println(partnerKey);
            System.out.println(partnerID);
            CardRequest card = new CardRequest();
            card.setFunction(command);
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
            System.out.println("VTCVcoin.decrypt: response = "+response.getDescription()+ " _ "+response.getResponseStatus());
            System.out.println("VTCVcoin.decrypt: xml = "+ response.getXml());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
