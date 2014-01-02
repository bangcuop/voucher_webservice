/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.clientws.util;

/**
 *
 * @author Huy Pham huyp@vietpay.vn
 */
import com.vss.clientws.util.exception.ClientWSClassNotFoundException;
import com.vss.clientws.util.exception.ClientWSMethodNotFoundException;
import com.vss.clientws.util.exception.ClientWSServiceException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;

public class InvokeClass {

    private static Logger _log = Logger.getLogger("com.vss.dtugate.util.ClassInvoke.class");

    public static Object invoke(
            String className,
            String methodName,
            Object[] valueInput) {
        try {
            ClassLoader classLoader = InvokeClass.class.getClassLoader();
            Class cls = classLoader.loadClass(className);
            if (cls == null) {
                throw new ClientWSClassNotFoundException();
            }
            Method method = null;
            Class[] paramTypes = null;
            int count = 0;
            Method[] methods = cls.getMethods();
            for (Method m : methods) {
                if (m.getName().equals(methodName)) {
                    method = cls.getMethod(m.getName(), m.getParameterTypes());
                    paramTypes = m.getParameterTypes();
                    count++;
                    break;
                }
            }
            if (count == 0) {
                throw new ClientWSMethodNotFoundException();
            }
            _log.debug("invoke method\n" + cls.getMethod(methodName, paramTypes));
            return method.invoke(cls.newInstance(), valueInput);
        } catch (SecurityException e) {
            e.printStackTrace();
            throw new ClientWSServiceException(e);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ClientWSServiceException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClientWSServiceException(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new ClientWSServiceException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new ClientWSServiceException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new ClientWSServiceException(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new ClientWSServiceException(e);
        }
    }
}
