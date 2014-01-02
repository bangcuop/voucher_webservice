package vcardserverproxy;

import java.security.MessageDigest;
import sun.misc.BASE64Encoder;

public class StringUtil {

    public static String encrypt(String strValue, String strAlgorithm)
            throws Exception {
        return encrypt(strValue.getBytes(), strAlgorithm);
    }

    public static String encrypt(byte[] btValue, String strAlgorithm)
            throws Exception {
        BASE64Encoder enc = new BASE64Encoder();
        MessageDigest md = MessageDigest.getInstance(strAlgorithm);
        return enc.encodeBuffer(md.digest(btValue));
    }
}
