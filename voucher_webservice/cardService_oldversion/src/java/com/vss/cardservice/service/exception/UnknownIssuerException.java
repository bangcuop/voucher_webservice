/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class UnknownIssuerException extends RuntimeException {

    /**
     * Creates a new instance of <code>UnknownIssuerException</code> without detail message.
     */
    public UnknownIssuerException() {
    }

    /**
     * Constructs an instance of <code>UnknownIssuerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public UnknownIssuerException(String msg) {
        super(msg);
    }
}
