/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.hdpay.vnptepay.proxy.vnptepayProxy;
import com.vss.cardservice.service.util.CardServiceLocalFactory;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import com.vss.mobicard.service.MobiCardProxy;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import vcardserverproxy.VCardServerProxy;

/**
 *
 * @author zannami
 */
@WebService(serviceName = "ManageSessionService", targetNamespace = "http://vcard.vn/sessionservice")
public class ManageSessionService extends SpringBeanAutowiringSupport {

    public static final int VINAPHONE = 0;
    public static final int MOBIPHONE = 1;
    public static final int VNPTEPAY = 2;
    @Autowired
    CardServiceLocalFactory cardServiceLocalFactory;
    public static List lst = new ArrayList();
    final String KEY = "ManageSessionService";

    @WebMethod(operationName = "getSessionStatus")
    public List<String> getSessionStatus(boolean clear) {
        if (clear) {
            lst.clear();
        }
        lst.add(VINAPHONE, WebParameter.vinaSessionValue);
        lst.add(MOBIPHONE, WebParameter.mobiSessionValue);
        lst.add(VNPTEPAY, WebParameter.vnptEpaySessionValue);
        return lst;
    }

    @WebMethod(operationName = "logout")
    public void logout(@WebParam(name = "issuer") Integer issuer, @WebParam(name = "signature") String signature) {
        if (ServiceUtil.hashData("logout" + KEY).equals(signature)) {
            switch (issuer) {
                case VINAPHONE:
                    try {
                        VCardServerProxy.logout(WebParameter.vinaSessionValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    WebParameter.vinaSessionValue = null;
                    break;
                case MOBIPHONE:
                    try {
                        MobiCardProxy.logout(WebParameter.mobiSessionValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    WebParameter.mobiSessionValue = null;
                    break;
                case VNPTEPAY:
                    try {
                        vnptepayProxy.logout(WebParameter.vnptEpaySessionValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    WebParameter.vnptEpaySessionValue = null;
                    break;
            }
            getSessionStatus(true);
        }
    }

    @WebMethod(operationName = "login")
    public void login(@WebParam(name = "issuer") Integer issuer, @WebParam(name = "signature") String signature) {
        if (ServiceUtil.hashData("login" + KEY).equals(signature)) {
            String service = "";
            try {
                switch (issuer) {
                    case VINAPHONE:
                        service = "VINA";
                        break;
                    case MOBIPHONE:
                        service = "MOVI";
                        break;
                    case VNPTEPAY:
                        service = "EPAY";
                        break;
                }
                String session = cardServiceLocalFactory.getCardService(service.toUpperCase()).loginToTelco();
                if(lst.size() > issuer){
                    lst.remove(issuer);
                }
                lst.add(issuer, session);
                getSessionStatus(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
