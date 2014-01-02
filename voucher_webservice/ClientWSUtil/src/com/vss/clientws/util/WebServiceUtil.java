package com.vss.clientws.util;

import com.vss.clientws.dto.LoginInfo;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

/**
 *
 * @author zannami
 *
 * Jul 12, 2011 11:07:25 AM
 */
public class WebServiceUtil {

    protected Call callService;
    private String endPointAddress;
    private String userName;
    private String password;
    boolean requiredLogin;
    protected LoginInfo login;

    public void init() {
        System.out.println("**************** WebServiceUtil.INIT");
        try {
            if (endPointAddress != null && !endPointAddress.isEmpty()) {
                callService = null;
                callService = (Call) (new Service()).createCall();
                callService.setTargetEndpointAddress(new URL(endPointAddress));
                if (requiredLogin) {
                    callService.setUsername(userName);
                    callService.setPassword(password);
                }
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (ServiceException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    // getter setter
    public LoginInfo getLogin() {
        return login;
    }

    public void setLogin(LoginInfo login) {
        this.login = login;
    }

    public void setEndPointAddress(String endPointAddress) {
        this.endPointAddress = endPointAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isRequiredLogin() {
        return requiredLogin;
    }

    public void setRequiredLogin(boolean requiredLogin) {
        this.requiredLogin = requiredLogin;
    }

    public Call getCallService() {
        if (callService == null) {
            init();
        }
        return callService;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setCallService(Call callService) {
        this.callService = callService;
    }

    public String getEndPointAddress() {
        return endPointAddress;
    }
    private static ResourceBundle conf = ResourceBundle.getBundle("configure");

    public static String getString(String key) {
        try {
            return conf.getString(key);
        } catch (Exception e) {
            return "";
        }
    }

    private void registerTypeMapping(Class cls, QName xmlType) {
        if (callService != null) {
            callService.registerTypeMapping(cls, xmlType,
                    new BeanSerializerFactory(cls, xmlType),
                    new BeanDeserializerFactory(cls, xmlType), true);
        }
    }

    public void registerTypeMapping(ClassTypeMapping... typeMapping) {
        for (ClassTypeMapping cls : typeMapping) {
            try {
                QName xmlType = new QName(cls.getNameSpaceURI(), cls.getLocalPart());
                registerTypeMapping(cls.getCls(), xmlType);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
