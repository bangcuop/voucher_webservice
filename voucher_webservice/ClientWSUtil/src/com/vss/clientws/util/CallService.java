package com.vss.clientws.util;

import com.vss.clientws.dto.Param;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author zannami
 *
 */
public class CallService extends WebServiceUtil {
    public CallService() {
    }

    public CallService(String endPointAddress) {
        setEndPointAddress(endPointAddress);
        init();
    }

    /**
     * @param args
     */
    public Object invokeMethod(
            String method, String returnType, Param... paramInput) throws Exception {

//		String endpoint = "http://10.151.9.130:8000/Voucher";
//		Service service = new Service();
//		Call call = null;
//		try {
//			call = (Call) service.createCall();
//
//			call.setTargetEndpointAddress(new java.net.URL(endpoint));
//		} catch (MalformedURLException e1) {
//			e1.printStackTrace();
//		} catch (ServiceException e1) {
//			e1.printStackTrace();
//		}

        callService.setOperationName(method);

//		call.addParameter("partner_id", XMLType.XSD_STRING, ParameterMode.IN);
//		call.addParameter("login", XMLType.XSD_STRING, ParameterMode.IN);
//		call.addParameter("password", XMLType.XSD_STRING, ParameterMode.IN);
//		call.addParameter("description", XMLType.XSD_STRING, ParameterMode.OUT);
//		call.addParameter("responseStatus", XMLType.XSD_STRING, ParameterMode.OUT);
//		call.addParameter("sessionId", XMLType.XSD_STRING, ParameterMode.OUT);

//		call.addParameter("sessionID", XMLType.XSD_STRING, ParameterMode.IN);
//		call.addParameter("cardID", XMLType.XSD_STRING, ParameterMode.IN);
//		call.addParameter("cardCode", XMLType.XSD_STRING, ParameterMode.IN);
        for (Param p : paramInput) {
            callService.addParameter(p.getParamName(), p.getXmlType(), p.getParamMode());
        }

        try {
            callService.setReturnClass(Class.forName(returnType));
            // sessionID kFs0/6YOUE4=
//			Object o = call.invoke(new Object[] {
//					Encrypt("abfad4e8f51bf292fdfd6bed",new String("kFs0/6YOUE4=")),
//					Encrypt("abfad4e8f51bf292fdfd6bed", new String("42839269738912")),
//					""});
            return callService.invoke(toObject(paramInput));
//			Map map = call.getOutputParams();
//			Iterator iterator = map.keySet().iterator();
//			System.out.println("=====Output Parameters=====");
//			while (iterator.hasNext()) {
//				System.out.println(iterator.next() + " "
//						+ map.get(iterator.next()));
//			}
        } catch (RemoteException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param args
     */
    public Map getOutputParams(
            String method, String returnType, Param... paramInput) throws Exception {
        callService.setOperationName(method);
        for (Param p : paramInput) {
            callService.addParameter(p.getParamName(), p.getXmlType(), p.getParamMode());
        }

        try {
            callService.setReturnClass(Class.forName(returnType));
            callService.invoke(toObject(paramInput));
            Map result = callService.getOutputParams();
            return result;
        } catch (RemoteException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            init();
        }
    }

    public List getReturnValues(
            String method, String returnType, Param... paramInput) throws Exception {
        callService.setOperationName(method);
        for (Param p : paramInput) {
            callService.addParameter(p.getParamName(), p.getXmlType(), p.getParamMode());
        }
        try {
            callService.setReturnClass(Class.forName(returnType));
            callService.invoke(toObject(paramInput));
            return callService.getOutputValues();
        } catch (RemoteException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 0947280048: Khanh 
     * @param param
     * @return
     * @throws Exception 
     */
    public Object[] toObject(Param[] param) throws Exception {
        Object[] obj = new Object[param.length];
        System.out.print("*** CallService.toObject : ");
        for (int i = 0; i < param.length; i++) {
            obj[i] = param[i].getValue();
            System.out.print(obj[i] + " , ");
        }
        System.out.println();
        return obj;
    }

    /**
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String Encrypt(
            String key, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("TripleDES");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(key.getBytes(), 0, key.length());
        String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(
                0, 24);
        SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");
        cipher.init(Cipher.ENCRYPT_MODE, keyspec);
        byte[] stringBytes = data.getBytes();
        byte[] raw = cipher.doFinal(stringBytes);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(raw);
    }

    /**
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String Decrypt(
            String key, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("TripleDES");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(key.getBytes(), 0, key.length());
        String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(0, 24);
        SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");
        cipher.init(Cipher.DECRYPT_MODE, keyspec);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] raw = decoder.decodeBuffer(data);
        byte[] stringBytes = cipher.doFinal(raw);
        return new String(stringBytes);
    }

    /**
     *
     * @param key
     * @return
     * @throws Exception
     */
    public String getKeyValue(
            String key) throws Exception {
//        return CallService.Decrypt(rsBundle.getString("key"), rsBundle.getString(key));
        return "";
    }
}
