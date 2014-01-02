/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class InvalidIpException extends RuntimeException {

    /**
     * Creates a new instance of <code>InvalidIpException</code> without detail message.
     */
    public InvalidIpException() {
    }

    /**
     * Constructs an instance of <code>InvalidIpException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidIpException(String msg) {
        super(msg);
    }
}
