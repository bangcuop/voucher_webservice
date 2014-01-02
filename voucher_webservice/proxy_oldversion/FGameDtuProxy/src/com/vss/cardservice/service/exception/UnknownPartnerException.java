/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class UnknownPartnerException extends RuntimeException {

    /**
     * Creates a new instance of <code>UnknownPartnerException</code> without detail message.
     */
    public UnknownPartnerException() {
    }

    /**
     * Constructs an instance of <code>UnknownPartnerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public UnknownPartnerException(String msg) {
        super(msg);
    }
    
    public UnknownPartnerException(Exception ex) {
        super(ex);
    }
}
