/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.paydirect.voucher.proxy.ViettelProxy;
import com.viettel.scratchcard.api.PnResponse;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author dodongduc
 */
@WebService(serviceName = "WebserviceVcard", targetNamespace = "http://paydirect.vn/WebserviceVcard")
public class WebserviceVcard {

    @WebMethod(operationName = "queryResultTransaction")
    public PnResponse queryResultTransaction(String transactionId) {
        try {
            return ViettelProxy.getTransactionDetail(transactionId);
        } catch (Exception ex) {
            return null;
        }
    }
}
