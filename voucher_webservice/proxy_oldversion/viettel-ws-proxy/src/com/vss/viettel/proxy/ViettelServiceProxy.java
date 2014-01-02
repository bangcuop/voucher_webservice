/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.viettel.proxy;

import com.viettel.scratchcard.api.PnResponse;
import com.viettel.scratchcard.api.ScratchCardAPI;
import com.viettel.scratchcard.api.ScratchCardAPIService;
import com.vss.viettel.dto.ViettelResponse;
import com.vss.viettel.exception.ViettelServiceException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;

/**
 *
 * @author zannami
 */
public class ViettelServiceProxy {

    static ResourceBundle CONFIGURE = ResourceBundle.getBundle("WSConfig");
    static final String PARTNER_ID = CONFIGURE.getString("viettel-partnerID");
    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmss");
    static final String PASS_PHASE = CONFIGURE.getString("viettel-passphase");
    static final String LOCALPART = CONFIGURE.getString("viettel-localPart");
    static final String WSURL = CONFIGURE.getString("viettel-ws-url");
    static final String NAMESPACE_URI = CONFIGURE.getString("viettel-nameSpaceURI");
    static final String PROVIDER_NAME = CONFIGURE.getString("viettel-provider-name");
    static final String SERVICE_NAME = CONFIGURE.getString("viettel-service-name");

    public static void main(String[] args) {
//        try {
//            /**
//             * 10K useCard("91392321413", "1923422542602");
//             * 20K useCard("21001301578", "1223087963682");
//             * 50K useCard("80649720265", "1827499616507");
//             *      100K useCard("41164614133", "142629116061");
//             * 200K useCard("10862916926", "1122320463836");
//             * 500K useCard("10089115670", "5108309392784");
//             */
//            ViettelResponse result = useCard("10959206647", "1124100519343");
        ViettelResponse result = useCard("91392321413", "1923422542602");
            System.out.println(result.getErrorCode() + "|" + result.getErrorMessage() + "|" + result.getAmount() + "|" + result.getTransactionId());
//            String transactionId = DATE_FORMAT.format(new Date()) + "" + PARTNER_ID + "" + getRandomNumber();
        result = getTransactionDetail(result.getTransactionId());
//            System.out.println("checkTransaction===" + result.getErrorCode() + "|" + result.getErrorMessage() + "|" + result.getAmount() + "|" + result.getTransactionId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static ScratchCardAPI getService() {
        URL url = null;
        ScratchCardAPI service = null;
        try {
            url = new URL(WSURL);
            QName qname = new QName(NAMESPACE_URI, LOCALPART);
            service = new ScratchCardAPIService(url, qname).getScratchCardAPIPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }

    /**
     *
     * @param cardSerial
     * @param cardCode
     * @param provider
     * @return
     */
    public static ViettelResponse useCard(String cardSerial, String cardCode) {
        try {
            installMyPolicy();
            ScratchCardAPI api = getService();
            String transactionId = DATE_FORMAT.format(new Date()) + "" + PARTNER_ID + "" + getRandomNumber();

            /**
             *
             */
//            System.out.println("[topupCard] " + cardSerial + " " + cardCode + " " + provider);
//            PnResponse res = api.topupCard(PARTNER_ID, PASS_PHASE, cardSerial, cardCode, transactionId);
            PnResponse res = api.topupCard(PARTNER_ID, PASS_PHASE, cardSerial, cardCode, transactionId, PROVIDER_NAME, SERVICE_NAME);
            return getResponse(res, transactionId, false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ViettelServiceException(e);
        }
    }

    /**
     *
     * @param transactionId
     * @return
     */
    public static ViettelResponse getTransactionDetail(String transactionId) {
        try {
            installMyPolicy();
            ScratchCardAPI api = getService();
            String originTransId = DATE_FORMAT.format(new Date()) + "" + PARTNER_ID + "" + getRandomNumber();
            PnResponse res = api.queryResultTransaction(PARTNER_ID, PASS_PHASE, transactionId, originTransId);
            return getResponse(res, originTransId, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ViettelServiceException(e);
        }
    }

    static int getRandomNumber() {
        final int min = 100;
        final int max = 999;
        Random rn = new Random();
        return rn.nextInt(max - min + 1) + min;
    }

    public static void installMyPolicy() throws Exception {

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs,
                String authType) {
            // Trust always
        }

        public void checkServerTrusted(X509Certificate[] certs,
                String authType) {
            // Trust always
        }
    }};
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        // Create empty HostnameVerifier
        HostnameVerifier hv = new HostnameVerifier() {

            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private static ViettelResponse getResponse(PnResponse res, String transactionId,
            boolean getTransaction) {
        ViettelResponse response = new ViettelResponse();
        try {
            response.setErrorCode(res.getErrorCode());
            response.setErrorMessage(res.getErrorMessage());
            response.setOriginTransId(res.getOriginTransId());
            if (res.getResponseTime() != null) {
                response.setResponseTime(res.getResponseTime().toGregorianCalendar().getTime());
            }
            response.setTransId(res.getTransId());
            if (getTransaction) {
                if (res.getTransInfo() != null) {
                    response.setAmount(res.getTransInfo().getAmount());
                    response.setCardSerial(res.getTransInfo().getCardSerial());
                    response.setTransId(res.getTransInfo().getTransId());
                    response.setErrorCode(res.getTransInfo().getErrorCode());
                    response.setErrorMessage(res.getTransInfo().getErrorMessage());
                }
            } else {
                response.setAmount(res.getAmount());
                response.setCardSerial(res.getCardSerial());
            }
            response.setTransactionId(transactionId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ViettelServiceException(e);
        }
        return response;
    }
}
