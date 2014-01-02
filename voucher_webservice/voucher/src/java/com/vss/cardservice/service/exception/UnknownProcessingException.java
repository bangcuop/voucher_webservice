/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class UnknownProcessingException extends RuntimeException {

    /**
     * Creates a new instance of <code>UnknownProcessingException</code> without detail message.
     */
    public UnknownProcessingException() {
    }

    /**
     * Constructs an instance of <code>UnknownProcessingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public UnknownProcessingException(String msg) {
        super(msg);
    }
}
