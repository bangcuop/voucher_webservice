/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author dodongduc
 */
@WebService(serviceName = "CheckConnection", name="CheckConnection", portName="CheckConnection", targetNamespace = "http://homedirect/checkconnection" )
public class CheckConnectionWebService {

    @WebMethod(operationName = "getConnection")
    public String getConnection() {
        return "OK";
    }
}
