/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class LockedPartnerException extends RuntimeException {

    /**
     * Creates a new instance of <code>LockedPartnerException</code> without detail message.
     */
    public LockedPartnerException() {
    }

    /**
     * Constructs an instance of <code>LockedPartnerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public LockedPartnerException(String msg) {
        super(msg);
    }
}
