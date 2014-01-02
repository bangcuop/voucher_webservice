/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.clientws.util.exception;

/**
 *
 * @author zannami
 */
public class ClientWSClassNotFoundException extends RuntimeException {

    public ClientWSClassNotFoundException() {
        super();
    }

    public ClientWSClassNotFoundException(String msg) {
        super(msg);
    }

    public ClientWSClassNotFoundException(Exception ex) {
        super(ex);
    }
}
