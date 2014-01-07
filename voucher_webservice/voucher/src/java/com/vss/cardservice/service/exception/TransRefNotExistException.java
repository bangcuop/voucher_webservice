/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class TransRefNotExistException extends RuntimeException {

    /**
     * Creates a new instance of <code>DuplicatedRequestRefException</code> without detail message.
     */
    public TransRefNotExistException() {
    }

    /**
     * Constructs an instance of <code>DuplicatedRequestRefException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TransRefNotExistException(String msg) {
        super(msg);
    }
}
