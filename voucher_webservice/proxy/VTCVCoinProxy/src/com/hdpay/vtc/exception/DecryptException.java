/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdpay.vtc.exception;

/**
 *
 * @author koziwa
 */
public class DecryptException extends RuntimeException {

    public DecryptException() {
        super();
    }

    public DecryptException(String message) {
        super(message);
    }
}
