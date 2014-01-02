/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class DuplicatedRequestRefException extends RuntimeException {

    /**
     * Creates a new instance of <code>DuplicatedRequestRefException</code> without detail message.
     */
    public DuplicatedRequestRefException() {
    }

    /**
     * Constructs an instance of <code>DuplicatedRequestRefException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DuplicatedRequestRefException(String msg) {
        super(msg);
    }
}
