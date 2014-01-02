/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paydirect.voucher.proxy;

import java.net.URL;

/**
 *
 * @author thang.tranquyet
 */
public class CheckConnectionThread extends Thread {
    private String url;
    public CheckConnectionThread() {
        super();
    }

    public CheckConnectionThread(String url) {
        super();
        this.url = url;
    }

    @Override
    public void run() {
        try {
            new URL(url).openConnection().connect();
            VTCProxy.checkConnectionMap.put(url, true);
        } catch (Exception ex) {
            ex.printStackTrace();
            VTCProxy.checkConnectionMap.put(url, false);
        }
    }
}
