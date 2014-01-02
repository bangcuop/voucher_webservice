/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class InvalidRequestRefException extends RuntimeException {

    /**
     * Creates a new instance of <code>InvalidRequestRefException</code> without detail message.
     */
    public InvalidRequestRefException() {
    }

    /**
     * Constructs an instance of <code>InvalidRequestRefException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidRequestRefException(String msg) {
        super(msg);
    }
}
