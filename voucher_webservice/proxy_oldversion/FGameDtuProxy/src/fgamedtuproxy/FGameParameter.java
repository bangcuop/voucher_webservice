/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fgamedtuproxy;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

/**
 *
 * @author thibt
 */
public class FGameParameter {

    public static String VTC_COIN = "VTC";
    public static String GATE = "GATE";
    public static String MOBIPHONE = "VMS";
    public static String VIETTEL = "VTT";
    public static String VINAPHONE = "VNP";
    private String provider;
    private String port;
    private String partnerId;
    private String type;
    private String cardCode;
    private String serial;
    private String transId;
    private String key;
    private String url;
    private ResourceBundle configure = ResourceBundle.getBundle("FGameDtuConfig");

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getKey() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String secretKey = configure.getString("SecretKey");
        StringBuilder signature = new StringBuilder(partnerId);
        if (type != null) {
            signature.append(type);
        }
        signature.append(transId);
        signature.append(cardCode);
        signature.append(secretKey);
        key = signature.toString();
        return MD5.hash(key);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPartnerId() {
        if (partnerId == null) {
            partnerId = configure.getString("TxtPartnerID");
        }
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPort() {
        if (type != null) {
            if (port == null) {
                port = configure.getString("TxtPort");
            }
            String[] portArray = port.split(",");
            port = portArray[Long.valueOf(Math.round((portArray.length - 1) * Math.random())).intValue()];
        }
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        url = configure.getString(getProvider());
        if (type != null) {
            url = url.replace("TxtPort", getPort()).replace("TYPE", type);
        }
        url = url.replace("PNID", getPartnerId()).replace("MATHE", cardCode).replace("SERI", serial).replace("TRANSID", transId).replace("KEY", getKey());
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
