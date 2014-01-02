/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class InvalidCardLengthException extends RuntimeException {

    /**
     * Creates a new instance of <code>InvalidCardLengthException</code> without detail message.
     */
    public InvalidCardLengthException() {
    }

    /**
     * Constructs an instance of <code>InvalidCardLengthException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidCardLengthException(String msg) {
        super(msg);
    }
}
