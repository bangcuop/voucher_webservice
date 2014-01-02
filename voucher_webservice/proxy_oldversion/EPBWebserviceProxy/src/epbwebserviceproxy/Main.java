/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package epbwebserviceproxy;

import vn.proonline.secondtelcoapi.ws.ChargeResponse;

/**
 *
 * @author hoangha2503
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String cardCode = "123456789012";
        String cardSerial = "123456789012";
        String issuerId = "MOBI";
        ChargeResponse chargeResponse = EPBWebserviceProxy.useCard(cardCode, cardSerial, issuerId);
        System.out.println("--->> chargeResponse:" + chargeResponse.getStatus());
        System.out.println("--->> chargeResponse:" + chargeResponse.getMessage());
    }
}
