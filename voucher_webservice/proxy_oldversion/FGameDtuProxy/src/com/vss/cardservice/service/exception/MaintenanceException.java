/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author linhnh
 */
public class MaintenanceException extends RuntimeException {

    /**
     * Creates a new instance of <code>MaintenanceException</code> without detail message.
     */
    public MaintenanceException() {
    }

    /**
     * Constructs an instance of <code>MaintenanceException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public MaintenanceException(String msg) {
        super(msg);
    }
}
