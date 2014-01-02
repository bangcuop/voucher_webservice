/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class IssuerInvalidProcessingException extends RuntimeException {

    /**
     * Creates a new instance of <code>IssuerInvalidProcessingException</code> without detail message.
     */
    public IssuerInvalidProcessingException() {
    }

    /**
     * Constructs an instance of <code>IssuerInvalidProcessingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IssuerInvalidProcessingException(String msg) {
        super(msg);
    }
}
