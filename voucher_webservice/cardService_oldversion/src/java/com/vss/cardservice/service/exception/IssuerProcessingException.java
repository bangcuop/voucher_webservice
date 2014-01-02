/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class IssuerProcessingException extends RuntimeException {

    /**
     * Creates a new instance of <code>IssuerProcessingException</code> without detail message.
     */
    public IssuerProcessingException() {
    }

    /**
     * Constructs an instance of <code>IssuerProcessingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IssuerProcessingException(String msg) {
        super(msg);
    }
}
