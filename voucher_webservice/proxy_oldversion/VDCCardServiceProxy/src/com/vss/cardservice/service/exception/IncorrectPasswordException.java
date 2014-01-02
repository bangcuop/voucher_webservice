/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class IncorrectPasswordException extends RuntimeException {

    /**
     * Creates a new instance of <code>IncorrectPasswordException</code> without detail message.
     */
    public IncorrectPasswordException() {
    }

    /**
     * Constructs an instance of <code>IncorrectPasswordException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IncorrectPasswordException(String msg) {
        super(msg);
    }
}
