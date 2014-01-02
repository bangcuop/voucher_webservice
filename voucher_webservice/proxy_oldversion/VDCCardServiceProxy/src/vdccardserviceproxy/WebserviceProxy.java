/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vdccardserviceproxy;

import java.net.URL;
import javax.xml.namespace.QName;
import vn.vdconline.secondtelcoapi.ws.TelcoWebService;
import vn.vdconline.secondtelcoapi.ws.TelcoWebServiceImplService;

/**
 *
 * @author thibt
 */
public class WebserviceProxy {

    private static TelcoWebService telcoWebService;

    public static TelcoWebService getTelcoWebService(String urlEnpoint) {
        if (telcoWebService == null) {
            try {
                return new TelcoWebServiceImplService(new URL(urlEnpoint), new QName("http://ws.secondtelcoAPI.vdconline.vn/", "TelcoWebServiceImplService")).getTelcoWebServiceImplPort();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return telcoWebService;
    }
}
