/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vdccardserviceproxy;

import java.util.ResourceBundle;

/**
 *
 * @author thibt
 */
public class Config {

    private static ResourceBundle bundle = ResourceBundle.getBundle("configure");
    public static final String END_POINT = bundle.getString("CARD_SERVICE_VDC0_END_POINT").trim();
    public static final String USER_NAME = bundle.getString("CARD_SERVICE_VDC0_USER_NAME").trim();
    public static final String PASSWORD = bundle.getString("CARD_SERVICE_VDC0_PASSWORD").trim();
    public static final int PARTNER_ID = Integer.parseInt(bundle.getString("CARD_SERVICE_VDC0_PARTNER_ID").trim());
    public static final String MPIN = bundle.getString("CARD_SERVICE_VDC0_MPIN").trim();
    public static final String PROVIDER_MOBIPHONE = bundle.getString("CARD_SERVICE_VDC0_PROVIDER_MOBIPHONE").trim();
    public static final String PROVIDER_VINAPHONE = bundle.getString("CARD_SERVICE_VDC0_PROVIDER_VINAPHONE").trim();
    public static final String EMAIL = bundle.getString("CARD_SERVICE_VDC0_EMAIL").trim();
    public static final String PHONE = bundle.getString("CARD_SERVICE_VDC0_PROVIDER_VINAPHONE").trim();
}
