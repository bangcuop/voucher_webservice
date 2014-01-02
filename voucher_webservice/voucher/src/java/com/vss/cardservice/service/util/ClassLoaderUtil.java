/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author thang.tranquyet
 */
public class ClassLoaderUtil {

    public static final String USE_CARD_METHOD = "useCard";
    public static final String CHECK_CONNECTION_METHOD = "checkConnection";
    public static final String KEEP_SESSION_METHOD = "keepSession";
    private static final Class[] parameters = new Class[]{URL.class};
    public static URLClassLoader sysloader;
    public static String classPath;

    static {
        URL url = Thread.currentThread().getContextClassLoader().getResource("/");
        File f = new File((new File(url.getPath())).getParent());
        classPath = f.getParent() + File.separator + "WEB-INF" + File.separator + "lib" + File.separator;
        sysloader = (URLClassLoader) Transaction.class.getClassLoader(); // lay ra classloader de load lai transaction o goi jar
    }

    public static void addURL(ClassLoader classLoader, String classPath) throws Exception {
        Class sysclass = URLClassLoader.class;
        try {
            System.out.println("******* ClassLoaderUtil.addURL : " + classPath);
            Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(classLoader, new Object[]{new File(classPath).toURI().toURL()});
            System.out.println("******* ClassLoaderUtil.addURL : " + classPath+ " SUCCESS");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }
    }

    private static Class loadClass(Partner partner) throws Exception {
        String jarPath = classPath + partner.getJarName();
        String className = partner.getClassName();
//        System.out.println("className = " + className);
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
//        ClassLoader classLoader = ClassLoaderUtil.class.getClassLoader();
//        Class loadedClass = classLoader.loadClass(className);
//        Class loadedClass = Class.forName(className, true, classLoader);
//        Class loadedClass;
//        URLClassLoader sysloader = (URLClassLoader) Transaction.class.getClassLoader(); // lay ra classloader de load lai transaction o goi jar
        try {
            return Class.forName(className, true, sysloader);
        } catch (Exception ex) {
            ex.printStackTrace();
            addURL(sysloader, jarPath);
            try {
                return Class.forName(className, true, sysloader);
            } catch (Exception ex1) {
                ex1.printStackTrace();
                throw new CardServiceException("Khong ton tai " + jarPath + " " + className);
            }
        }
    }

    public static Transaction useCard(Partner partner, Transaction transaction) throws Exception {
        Class loadedClass = loadClass(partner);
        Method method = loadedClass.getDeclaredMethod(USE_CARD_METHOD, Class.forName(Transaction.class.getName(), true, sysloader));
        if (Modifier.isStatic(method.getModifiers())) {
            return (Transaction) method.invoke(loadedClass, transaction);
        } else {
            return (Transaction) method.invoke(loadedClass.newInstance(), transaction);
        }
    }
    
    public static String keepSession(Partner partner) throws Exception {
        Class loadedClass = loadClass(partner);
        Method method = loadedClass.getDeclaredMethod(KEEP_SESSION_METHOD);
        if (Modifier.isStatic(method.getModifiers())) {
            return (String) method.invoke(loadedClass);
        } else {
            return (String) method.invoke(loadedClass.newInstance());
        }
    }
    
    public static Boolean checkConnection(Partner partner) throws Exception {
        Class loadedClass = loadClass(partner);
        Method method = loadedClass.getDeclaredMethod(CHECK_CONNECTION_METHOD);
        if (Modifier.isStatic(method.getModifiers())) {
            return (Boolean) method.invoke(loadedClass);
        } else {
            return (Boolean) method.invoke(loadedClass.newInstance());
        }
    }
}
