/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class InvalidSignatureException extends RuntimeException {

    /**
     * Creates a new instance of <code>InvalidSignatureException</code> without detail message.
     */
    public InvalidSignatureException() {
    }

    /**
     * Constructs an instance of <code>InvalidSignatureException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidSignatureException(String msg) {
        super(msg);
    }
}
