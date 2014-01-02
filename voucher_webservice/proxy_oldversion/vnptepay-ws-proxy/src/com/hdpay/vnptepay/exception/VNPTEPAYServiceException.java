/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdpay.vnptepay.exception;

/**
 *
 * @author zannami
 */
public class VNPTEPAYServiceException extends RuntimeException {

    public VNPTEPAYServiceException() {
        super();
    }

    public VNPTEPAYServiceException(String msg) {
        super(msg);
    }

    public VNPTEPAYServiceException(Exception ex) {
        super(ex);
    }
}
