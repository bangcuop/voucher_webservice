/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class IssuerBusyException extends RuntimeException {

    /**
     * Creates a new instance of <code>IssuerBusyException</code> without detail message.
     */
    public IssuerBusyException() {
    }

    /**
     * Constructs an instance of <code>IssuerBusyException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IssuerBusyException(String msg) {
        super(msg);
    }
}
