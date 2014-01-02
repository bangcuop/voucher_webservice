/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class IssuerConnectionException extends RuntimeException {

    /**
     * Creates a new instance of <code>IssuerConnectionException</code> without detail message.
     */
    public IssuerConnectionException() {
    }

    /**
     * Constructs an instance of <code>IssuerConnectionException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IssuerConnectionException(String msg) {
        super(msg);
    }
}
