/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.proxy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author thang.tranquyet
 */
public class TestUseCard implements Runnable {

    static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss SSS");
    private int testId;
    private String issuer;
    private String cardCode;
    private String cardSerial;
    private String transRef;
    public TestUseCard() {
        super();
    }

    public TestUseCard(int testId, String issuer, String cardCode, String cardSerial, String transRef) {
        this.testId = testId;
        this.issuer = issuer;
        this.cardCode = cardCode;
        this.cardSerial = cardSerial;
        this.transRef = transRef;
    }

    public void run() {
        String start = df.format(new Date());
        System.out.println("test " + testId + ". " + transRef + " . start " + start + " - ");
        String response = VoucherServiceProxy.useCard(issuer, cardSerial, cardCode, transRef);
        System.out.println("test " + testId + ". " + transRef + " . end " + df.format(new Date()) + " : " + response);
    }

    public static void main(String[] args) {
        try {
            String issuer = "vt";
            int count = 5;
            for (int i = 0; i < count; i++) {
                Thread.sleep(1000);
                String currentTime = String.valueOf(System.currentTimeMillis()); // 13 ky tu
//                pool.execute(new TestUseCard(i, issuer, currentTime, currentTime, currentTime));
                new Thread(new TestUseCard(i, issuer, currentTime, currentTime, currentTime)).start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
