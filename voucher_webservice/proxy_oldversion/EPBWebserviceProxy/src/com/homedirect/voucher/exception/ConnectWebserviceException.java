/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homedirect.voucher.exception;

/**
 *
 * @author hoangha2503
 */
public class ConnectWebserviceException extends RuntimeException {

    /**
     * Creates a new instance of <code>ConnectWebserviceException</code> without detail message.
     */
    public ConnectWebserviceException() {
    }

    /**
     * Constructs an instance of <code>ConnectWebserviceException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ConnectWebserviceException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>ConnectWebserviceException</code> with the specified detail message.
     * @param ex the detail Throwable.
     */
    public ConnectWebserviceException(Throwable ex) {
        super(ex);
    }
}
