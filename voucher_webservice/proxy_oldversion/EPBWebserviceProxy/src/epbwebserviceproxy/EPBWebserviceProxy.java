/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package epbwebserviceproxy;

import com.homedirect.voucher.exception.ConnectWebserviceException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import vn.proonline.secondtelcoapi.ws.LoginResponse;
import vn.proonline.secondtelcoapi.ws.TelcoWebService;
import vn.proonline.secondtelcoapi.ws.TelcoWebServiceImplService;
import vn.proonline.secondtelcoapi.ws.ChargeResponse;

/**
 *
 * @author hoangha2503
 */
public class EPBWebserviceProxy {

    static TelcoWebService service;
    static String urlString = "http://123.30.169.75:8080/webservice/TelcoAPI?wsdl";
    static int merchantId = 7;
    static String userName = "homedirect";
    static String password = "homedirect@123";
    static String mpin = "homedi.vn.mpin";
    static String target = "target_test";
    static String targetMail = "do.truongduc@homdirect.vn";
    static String targetPhone = "0915080385";
    static String sessionId;
    public static String PROVIDER_MOBIPHONE = "VMS";
    public static String PROVIDER_VINAPHONE = "VNP";
    public static String PROVIDER_VIETEL = "VTT";
    public static String MOBIPHONE = "MOBI";
    public static String VIETTEL = "VT";
    public static String VINAPHONE = "VINA";
    static String prefix = "[epbwebserviceproxy.EPBWebserviceProxy]";
    static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    static void init() {
        if (service != null) {
            return;
        }

        try {
            URL url = new URL(urlString);
            service = new TelcoWebServiceImplService(url, new QName("http://ws.secondtelcoAPI.proonline.vn/", "TelcoWebServiceImplService")).getTelcoWebServiceImplPort();
        } catch (Exception ex) {
            throw new ConnectWebserviceException(ex);
        }

        try {
            urlString = ResourceBundle.getBundle("WSConfig").getString("ebp.webservice.url");
            merchantId = new Integer(ResourceBundle.getBundle("WSConfig").getString("ebp.webservice.id")).intValue();
            userName = ResourceBundle.getBundle("WSConfig").getString("ebp.webservice.userName");
            password = ResourceBundle.getBundle("WSConfig").getString("ebp.webservice.password");
            mpin = ResourceBundle.getBundle("WSConfig").getString("ebp.webservice.mpin");
            target = ResourceBundle.getBundle("WSConfig").getString("ebp.webservice.target");
            targetMail = ResourceBundle.getBundle("WSConfig").getString("ebp.webservice.target.mail");
            targetPhone = ResourceBundle.getBundle("WSConfig").getString("ebp.webservice.target.phone");
        } catch (Exception ex) {
        }

        login();
    }

    static void login() {
        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
        prefixBuilder.append(prefix);
        prefixBuilder.append("[logIn - userName:");
        prefixBuilder.append(userName);
        prefixBuilder.append("][password:");
        prefixBuilder.append(password);
        prefixBuilder.append("]");
        prefixBuilder.append("][merchantId:");
        prefixBuilder.append(merchantId);
        prefixBuilder.append("]");
        try {
            LoginResponse loginResponse = service.logIn(userName, StringUtil.encrypt(password, "SHA"), merchantId);
            sessionId = loginResponse.getSessionid();
            prefixBuilder.append("[result:");
            prefixBuilder.append(sessionId);
            prefixBuilder.append("]");
            System.out.println(prefixBuilder.toString());
        } catch (Exception ex) {
            throw new ConnectWebserviceException(ex);
        }
    }

    public static ChargeResponse useCard(String cardCode, String cardSerial, String issuerId) {
        init();
        if (sessionId == null || sessionId.isEmpty()) {
            login();
        }

        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
        prefixBuilder.append(prefix);
        prefixBuilder.append("[userCardChargeResponse - sessionId:");
        prefixBuilder.append(sessionId);
        prefixBuilder.append("][userName:");
        prefixBuilder.append(userName);
        prefixBuilder.append("][cardCode:");
        prefixBuilder.append(cardCode);
        prefixBuilder.append("][cardSerial:");
        prefixBuilder.append(cardSerial);
        prefixBuilder.append("][issuerId:");
        prefixBuilder.append(issuerId);
        prefixBuilder.append("][merchantId:");
        prefixBuilder.append(merchantId);
        prefixBuilder.append("]");
        System.out.println(prefixBuilder.toString());

        String issuerName = PROVIDER_MOBIPHONE;
        if (VIETTEL.equalsIgnoreCase(issuerId)) {
            issuerName = PROVIDER_VIETEL;
        } else if (VINAPHONE.equalsIgnoreCase(issuerId)) {
            issuerName = PROVIDER_VINAPHONE;
        } else {
            issuerName = PROVIDER_MOBIPHONE;
        }

        try {
            String mpinEncrypt = TripleDESEPB.Encrypt(sessionId, mpin);
            String dataCardEncrypt = TripleDESEPB.Encrypt(sessionId, cardSerial + ":" +
                    cardCode + "::" + issuerName);
            ChargeResponse chargeResponse = service.cardCharge(userName, merchantId,
                    mpinEncrypt, dataCardEncrypt, target, targetMail, targetPhone);

            Integer chargeResponseStatus = chargeResponse.getStatus();
            StringBuilder resultBuilder = new StringBuilder(prefixBuilder);
            resultBuilder.append("[result - status:");
            resultBuilder.append(chargeResponseStatus);
            resultBuilder.append("|getTransid:");
            resultBuilder.append(chargeResponse.getTransid());
            resultBuilder.append("|mesage:");
            resultBuilder.append(chargeResponse.getMessage());
            resultBuilder.append("|getDRemainAmount:");
            resultBuilder.append(chargeResponse.getDRemainAmount());
            resultBuilder.append("]");
            System.out.println(resultBuilder.toString());

            if (chargeResponseStatus != null &&
                    chargeResponseStatus.intValue() == 2) {
                login();
                resultBuilder = new StringBuilder(prefixBuilder);
                resultBuilder.append("[Reused card - new sessionId:");
                resultBuilder.append(sessionId);
                resultBuilder.append("][issuerName:");
                resultBuilder.append(issuerName);
                resultBuilder.append("]");
                mpinEncrypt = TripleDESEPB.Encrypt(sessionId, mpin);
                dataCardEncrypt = TripleDESEPB.Encrypt(sessionId, cardSerial + ":" +
                        cardCode + "::" + issuerName);
                chargeResponse = service.cardCharge(userName, merchantId,
                        mpinEncrypt, dataCardEncrypt, target, targetMail, targetPhone);

                chargeResponseStatus = chargeResponse.getStatus();
                resultBuilder = new StringBuilder(prefixBuilder);
                resultBuilder.append("[Recall when session expri result - status:");
                resultBuilder.append(chargeResponseStatus);
                resultBuilder.append("|getTransid:");
                resultBuilder.append(chargeResponse.getTransid());
                resultBuilder.append("|mesage:");
                resultBuilder.append(chargeResponse.getMessage());
                resultBuilder.append("|getDRemainAmount:");
                resultBuilder.append(chargeResponse.getDRemainAmount());
                resultBuilder.append("]");
                System.out.println(resultBuilder.toString());
            }

            if ((chargeResponse != null) && (chargeResponse.getDRemainAmount() != null)) {
                try {
                    chargeResponse.setDRemainAmount(
                            TripleDESEPB.Decrypt(sessionId, chargeResponse.getDRemainAmount()));
                    resultBuilder.append("[Decrypt amount:");
                    resultBuilder.append(chargeResponse.getDRemainAmount());
                    resultBuilder.append("]");
                    System.out.println(resultBuilder.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return chargeResponse;
        } catch (Exception ex) {
            throw new ConnectWebserviceException();
        }
    }
}

 