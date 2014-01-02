/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.dto.PartnerInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author zannami
 */
public class PropertiesUtil {

    private String propertiesFile;
    private Properties p;

    /**
     * Initialise this class and load properties file
     * at the same time
     * @param file to load
     */
    public PropertiesUtil(String file) {
        File f = new File(file);
        boolean create = false;
        if (!f.exists()) {
            try {
//                f.mkdir();
                f.createNewFile();
                create = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.propertiesFile = file;
        p = new Properties();
        this.loadProperties();
        if(create){
            writeProperty("/*current_time_milisecond","$partnerCode|$date_time|$issuer|$cardCode|$transRef|$responseStatus|$responseDescription*/");
        }
    }

    /**
     * Load content of properties file into memory
     */
    public void loadProperties() {
        try {
            p.load(new FileInputStream(propertiesFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read all value in properties file into ArrayList object
     * @return ArrayList object containing all values in properties file
     */
    public ArrayList readAllValues() {
        ArrayList values = new ArrayList();
        Enumeration e = p.elements();
        while (e.hasMoreElements()) {
            values.add(e.nextElement());
        }
        return values;
    }

    /**
     * Read value that matched the key
     * @param key use to search in properties file
     * @return value that matched key
     */
    public String readValue(String key) {
        return p.getProperty(key);
    }

    /**
     * Write key/value pair into properties file
     * @param key
     * @param value
     */
    public void writeProperty(String key, String value) {
        p.setProperty(key, value);
        try {
            p.store(new FileOutputStream(propertiesFile), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write key/value pair into properties file
     * @param key
     * @param value
     */
    public void writeProperty(PartnerInfo info, String issuer, String cardCode, String transRef,String response) {
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        p.setProperty(String.valueOf(System.currentTimeMillis()), formater.format(new Date()) + "|" +
                        info.getPartnerCode() + "|" + issuer + "|" + cardCode + "|" + transRef + "|" + response);
        try {
            p.store(new FileOutputStream(propertiesFile), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Example starting method, demonstrate how PropertiesIO class is used
     * @param args
     */
    public static void main(String[] args) {
//        PropertiesUtil pio = new PropertiesUtil("logs/info.properties");
//        System.out.println(pio.readValue("expression"));
////        System.out.println(pio.readValue("variabletwo"));
//        pio.writeProperty(String.valueOf(System.currentTimeMillis()), new Date()+ "|vss|MOBI|12345678|1235342|00|50000");
    }
}
