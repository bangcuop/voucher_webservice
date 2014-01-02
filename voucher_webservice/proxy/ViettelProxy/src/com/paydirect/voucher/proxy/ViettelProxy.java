/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paydirect.voucher.proxy;

import com.paydirect.voucher.exception.ConnectWebserviceException;
import com.paydirect.voucher.exception.ViettelServiceException;
import com.viettel.scratchcard.api.PnResponse;
import com.viettel.scratchcard.api.ScratchCardAPI;
import com.viettel.scratchcard.api.ScratchCardAPIService;
import com.vss.cardservice.dto.Transaction;
import java.io.File;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
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

public class ViettelProxy {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmss");
    private static final String DELIMITER = "|";
    private static final String PARTNER_ID;
    private static final String PASS_PHASE;
    private static final String LOCALPART;
    private static final String WSURL;
    private static final String NAMESPACE_URI;
    private static final String PROVIDER_NAME;
    private static final String SERVICE_NAME;
    private static final String CHECKCONNECTION_URL;
    private static String prefix = "[ViettelProxy]";
    private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static ScratchCardAPI service = null;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("wsconfig" + File.separator + "ViettelProxy");
        PARTNER_ID = bundle.getString("viettel_partnerID").trim();
        PASS_PHASE = bundle.getString("viettel_passphase").trim();
        LOCALPART = bundle.getString("viettel_localPart").trim();
        WSURL = bundle.getString("viettel_ws_url").trim();
        NAMESPACE_URI = bundle.getString("viettel_nameSpaceURI").trim();
        PROVIDER_NAME = bundle.getString("viettel_provider_name").trim();
        SERVICE_NAME = bundle.getString("viettel_service_name").trim();
        try {
            URL url = new URL(WSURL);
            QName qname = new QName(NAMESPACE_URI, LOCALPART);
            service = new ScratchCardAPIService(url, qname).getScratchCardAPIPort();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectWebserviceException(e);
        }
        CHECKCONNECTION_URL = WSURL.replaceAll("https", "http");
    }

    public Transaction useCard(Transaction tran) {
        try {
            String transactionId = DATE_FORMAT.format(new Date()) + PARTNER_ID + getRandomNumber();
            tran.setTelcoTransRefId(transactionId);
            PnResponse response = useCard(tran.getCardSerial(), tran.getCardCode(), transactionId);
            tran.setResponseTime(new Date());
            if (response == null || response.getErrorCode() == null) {
                tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
                return tran;
            }
            String errorCode = response.getErrorCode();
            String msg = response.getErrorMessage();
            tran.setUseCardResponse(errorCode + "|" + msg);
            if (errorCode.equals("00")) {
                String amount = response.getAmount();
                tran.setAmount(amount);
                tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
                tran.setUseCardResponse(errorCode + "|" + msg + "|" + amount);
            } else {
                tran.setResponseStatus(processViettelCardService(errorCode));
            }
        } catch (Exception e) {
            e.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
        }
        return tran;
    }

    public Boolean checkConnection() {
        boolean result = true;
        try {
            new URL(CHECKCONNECTION_URL).openConnection().connect();
        } catch (Exception ex) {
            System.out.println("VIETTELProxy.checkConnection : ERROR");
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    public static PnResponse useCard(String cardSerial, String cardCode, String transactionId) {
//        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
//        prefixBuilder.append(prefix);
//        prefixBuilder.append("[useCard");
//        prefixBuilder.append("][cardCode:");
//        prefixBuilder.append(cardCode);
//        prefixBuilder.append("][cardSerial:");
//        prefixBuilder.append(cardSerial);
//        prefixBuilder.append("]");
//        System.out.println(prefixBuilder.toString());
        try {
            installMyPolicy();
            return service.topupCard(PARTNER_ID, PASS_PHASE, cardSerial, cardCode, transactionId, PROVIDER_NAME, SERVICE_NAME);
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
    public static PnResponse getTransactionDetail(String transactionId) {
        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
        prefixBuilder.append(prefix);
        prefixBuilder.append("[getTransactionDetail]");
        System.out.println(prefixBuilder.toString());
        try {
            installMyPolicy();
            String originTransId = DATE_FORMAT.format(new Date()) + "" + PARTNER_ID + "" + getRandomNumber();
//            System.out.println("-------PARTNER_ID: " + PARTNER_ID + "|PASS_PHASE: " + PASS_PHASE + "|transactionId: " + transactionId + "|originTransId: " + originTransId);
            PnResponse res = service.queryResultTransaction(PARTNER_ID, PASS_PHASE, transactionId, originTransId);
//            System.out.println("--------------errorCode:" + res.getErrorCode());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ViettelServiceException(e);
        }
    }

    static int getRandomNumber() {
        return new Random().nextInt(900) + 100;
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

    private String processViettelCardService(String status) {
        if (status == null) {
            return WebParameter.GIAO_DICH_NGHI_VAN;
        }
        if ("01".equals(status)) {
            return WebParameter.THE_DA_DUOC_SU_DUNG;
        } else if ("02".equals(status)) {
            return WebParameter.THE_KHONG_TON_TAI;
        } else if ("03".equals(status)) {
            return WebParameter.MATHE_SERIAL_KHONG_KHOP;
        } else if ("04".equals(status)) {
            return WebParameter.MATHE_SERIAL_KHONG_KHOP;
        } else if ("05".equals(status)) {
            return WebParameter.THE_CHUA_DUOC_KICH_HOAT;
        } else if ("06".equals(status)) {
            return WebParameter.THE_CHUA_DUOC_KICH_HOAT;
        } else if ("07".equals(status)) {
            return WebParameter.THE_HET_HAN_SU_DUNG;
        } else if ("08".equals(status)) {
            return WebParameter.THE_KHONG_DUNG_DINH_DANG;
        } else if ("50".equals(status)) {
            return WebParameter.LOI_KET_NOI_PROVIDER;
        } else if ("51".equals(status)) {
            return WebParameter.LOI_KET_NOI_PROVIDER;
        } else if ("52".equals(status)) {
            return WebParameter.TRUNG_MA_GIAO_DICH;
        } else if ("53".equals(status)) {
            return WebParameter.MA_GIAO_DICH_KHONG_TON_TAI;
        } else if ("54".equals(status)) {
            return WebParameter.GIAO_DICH_NGHI_VAN;
        } else if ("55".equals(status)) {
            return WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if ("56".equals(status)) {
            return WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if ("57".equals(status)) {
            return WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if ("58".equals(status)) {
            return WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if ("59".equals(status)) {
            return WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if ("97".equals(status)) {
            return WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if ("98".equals(status)) {
            return WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if ("99".equals(status)) {
            return WebParameter.LOI_GOI_HAM_PROVIDER;
        } else {
            return WebParameter.LOI_KHONG_XAC_DINH;
        }
    }

    public static void main(String[] args) {
        try {
            String transactionId = args[0];
            System.out.println("-------------------transactionId:" + transactionId);
            PnResponse result = getTransactionDetail(transactionId);
//            System.out.println(result.getErrorCode() + "|" + result.getErrorMessage() + "|" + result.getAmount() + "|" + result.getTransId());
            System.out.println(result.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
