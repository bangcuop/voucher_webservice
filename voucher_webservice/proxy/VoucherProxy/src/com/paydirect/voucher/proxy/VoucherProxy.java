/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paydirect.voucher.proxy;

import com.vss.cardservice.dto.Transaction;
import java.io.File;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
//import vn.paydirect.voucherservice.VoucherService;
//import vn.paydirect.voucherservice.VoucherService_Service;
import vn.vcard.voucherservice.VoucherService;
import vn.vcard.voucherservice.VoucherService_Service;

/**
 *
 * @author zannami
 */
public class VoucherProxy {

    private static String partnerCode;
    private static String password;
    private static String secretKey;
    private static String wsdl;
    private static String namespace;
    private static String serviceName;
    private static VoucherService service;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("wsconfig"+File.separator+"VoucherProxy");
            partnerCode = bundle.getString("voucher_partnerCode");
            password = bundle.getString("voucher_password");
            secretKey = bundle.getString("voucher_secretKey");
            wsdl = bundle.getString("voucher_wsdl");
            namespace = bundle.getString("voucher_namespace");
            serviceName = bundle.getString("voucher_serviceName");
//            service = new VoucherService_Service(new URL(wsdl), new QName(namespace, serviceName)).getVoucherServicePort();
            service = new VoucherService_Service(new URL(wsdl), new QName(namespace, serviceName)).getPort(new QName(namespace, serviceName + "Port"), VoucherService.class);
        } catch (Exception ex) {
            System.out.println("VoucherProxy init Error");
            ex.printStackTrace();
        }
    }

    public Transaction useCard(Transaction tran) {
        try {
            String response = useCard(tran.getIssuer(), tran.getCardSerial(), tran.getCardCode(), tran.getTransactionId().toString());
            if (response == null || response.isEmpty()) {
                tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
            } else {
                tran.setUseCardResponse(response);
                String[] msg = response.split("\\|");
                String responseStatus = msg[0];
                if (responseStatus.equals(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO)
                        || responseStatus.equals(WebParameter.PARTNER_BI_KHOA)
                        || responseStatus.equals(WebParameter.PARTNER_KHONG_TON_TAI)
                        || responseStatus.equals(WebParameter.SAI_CHU_KY)
                        || responseStatus.equals(WebParameter.SAI_DIA_CHI_IP)
                        || responseStatus.equals(WebParameter.SAI_PASSWORD)
                        || responseStatus.equals(WebParameter.SO_HIEU_GIAO_DICH_KHONG_HOP_LE)
                        || responseStatus.equals(WebParameter.THUC_HIEN_SAI_QUA_SO_LAN_CHO_PHEP)
                        || responseStatus.equals(WebParameter.TRUNG_MA_GIAO_DICH)) {
                    responseStatus = WebParameter.LOI_GOI_HAM_PROVIDER;
                }
                tran.setResponseStatus(responseStatus);
                if (msg.length > 2) {
                    tran.setAmount(msg[2]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
        }
        tran.setResponseTime(new Date());
        return tran;
    }

    public Boolean checkConnection() {
        try {
            new URL(wsdl).openConnection().connect();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public Transaction checkCard(Transaction tran) {
        tran.setResponseStatus(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO);
        return tran;
    }

    public static String useCard(String issuer, String cardSerial, String cardCode, String transRef)
            throws Exception {
        String signature = hashData(issuer + cardCode + transRef + partnerCode + password + secretKey);
        return service.useCard(issuer, cardSerial, cardCode, transRef, partnerCode, password, signature);
    }

    public static String getTransactionDetail(String transRef) throws Exception {
        String signature = hashData(transRef + partnerCode + password + secretKey);
        return service.getTransactionDetail(transRef, partnerCode, password, signature);
    }

    public static String hashData(String data) {
        try {
            MessageDigest hash = MessageDigest.getInstance("MD5");
            hash.update(data.getBytes());

            byte[] temp = hash.digest();

            return convertToHex(temp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
            int halfbyte = data[i] >>> 4 & 0xF;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) (48 + halfbyte));
                } else {
                    buf.append((char) (97 + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0xF;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static void main(String[] args) throws Exception {
        String cardCode = "1234567890134";
        String cardSerial = "1234567810124";
        String transRef = "123456780324";
//        System.out.println(useCard("vt", "1224561890123", "1233567890123", String.valueOf(System.currentTimeMillis())));
//        System.out.println(useCard("vina", "AUB439235", "50122223128581", String.valueOf(System.currentTimeMillis())));
        System.out.println(useCard("mobi", "012181040239225", "291322225741", "t_"+String.valueOf(System.currentTimeMillis())));
//        String signature = hashData("vt" + cardCode + transRef + partnerCode + password + secretKey);

//        String signature = hashData("2166594" + partnerCode + password + secretKey);
//        System.out.println(signature);
//        System.out.println(service.getTransactionDetail("2166594", partnerCode, password, signature));
    }
}
