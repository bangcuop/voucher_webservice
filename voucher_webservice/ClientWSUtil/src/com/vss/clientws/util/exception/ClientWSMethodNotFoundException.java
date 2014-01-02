/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.clientws.util.exception;

/**
 *
 * @author zannami
 */
public class ClientWSMethodNotFoundException extends RuntimeException {

    public ClientWSMethodNotFoundException() {
        super();
    }

    public ClientWSMethodNotFoundException(String msg) {
        super(msg);
    }

    public ClientWSMethodNotFoundException(Exception ex) {
        super(ex);
    }
}
