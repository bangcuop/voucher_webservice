/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fgamedtuproxy;

import com.vss.cardservice.service.exception.ProccessFGameDtu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author thibt
 */
public class FGameDtuProxy {

    public static String request(String provider, String cardSerial, String cardCode, String transactionId) throws MalformedURLException, IOException, NoSuchAlgorithmException, Exception {
        System.out.println("---------FGameDtuProxy-------provider:" + provider + "|cardSerial:" + cardSerial + "|cardCode:" + cardCode + "|transactionId:" + transactionId);
        FGameParameter fGameParameter = new FGameParameter();
        fGameParameter.setProvider(provider);
        fGameParameter.setCardCode(cardCode);
        fGameParameter.setSerial(cardSerial);
        fGameParameter.setTransId(transactionId);
        if (provider.equals(FGameParameter.MOBIPHONE) || provider.equals(FGameParameter.VIETTEL) || provider.equals(FGameParameter.VINAPHONE)) {
            fGameParameter.setType(provider);
            fGameParameter.setProvider("TELCO");
        }
        System.out.println("Fgame proxyyyyyyyyyy  URL-------------" + fGameParameter.getUrl());
        URL url = new URL(fGameParameter.getUrl());
        URLConnection conn = url.openConnection();
        conn.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = in.readLine();
        in.close();
        System.out.println("response FGameProxy--------->" + response);
        String[] responseArray = response.split("@");
        String status = responseArray[0].replace("RESULT:", "");
        ProccessFGameDtu.throwExceptionFromErrorCode(status);
        return responseArray[1];
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, IOException, NoSuchAlgorithmException, Exception {
        // TODO code application logic here
//        String provider = "VTC";
//        String cardSerial = "12345678912345";
//        String cardCode = "123456789012";
//        String transactionId = "1";
//        System.out.println(request(provider, cardSerial, cardCode, transactionId));
        String s = "http://api2.cbviet.net:64986/?TxtPartnerID=PNID&TxtMaThe=MATHE&TxtSeri=SERI&TxtTransId=TRANSID&TxtKey=KEY";
        String[] strArray = s.split("\\/");
        String str = "";
        for (int i = 0; i < strArray.length; i++) {
            String st = strArray[i];
            if (i < 3) {
                if (i == 0) {
                    str = st;
                } else {
                    str = str + "/" + st;
                }
            }
        }
        System.out.println(str);
    }
}
