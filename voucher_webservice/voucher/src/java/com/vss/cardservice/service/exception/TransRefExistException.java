/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class TransRefExistException extends RuntimeException {

    /**
     * Creates a new instance of <code>DuplicatedRequestRefException</code> without detail message.
     */
    public TransRefExistException() {
    }

    /**
     * Constructs an instance of <code>DuplicatedRequestRefException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TransRefExistException(String msg) {
        super(msg);
    }
}
