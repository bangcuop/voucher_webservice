package epbwebserviceproxy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TripleDESEPB
{
  public static String getMD5(String sMessage)
  {
    byte[] defaultBytes = sMessage.getBytes();
    try {
      MessageDigest algorithm = MessageDigest.getInstance("MD5");

      algorithm.reset();
      algorithm.update(defaultBytes);
      byte[] messageDigest = algorithm.digest();
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < messageDigest.length; i++) {
        String hex = Integer.toHexString(0xFF & messageDigest[i]);

        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }
      return hexString.toString(); } catch (NoSuchAlgorithmException nsae) {
    }
    return null;
  }

  public static String Encrypt(String key, String data)
    throws Exception
  {
    Cipher cipher = Cipher.getInstance("TripleDES");
    String keymd5 = getMD5(key).substring(0, 24);
    SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");
    cipher.init(1, keyspec);
    byte[] stringBytes = data.getBytes();
    byte[] raw = cipher.doFinal(stringBytes);
    BASE64Encoder encoder = new BASE64Encoder();
    String base64 = encoder.encode(raw);
    return base64;
  }

  public static String Decrypt(String key, String data) throws Exception
  {
    Cipher cipher = Cipher.getInstance("TripleDES");
    String keymd5 = getMD5(key).substring(0, 24);
    SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");
    cipher.init(2, keyspec);
    BASE64Decoder decoder = new BASE64Decoder();
    byte[] raw = decoder.decodeBuffer(data);
    byte[] stringBytes = cipher.doFinal(raw);
    String result = new String(stringBytes);
    return result;
  }

  public static String sha1(String input) throws Exception
  {
    MessageDigest md = MessageDigest.getInstance("SHA1");
    md.update(input.getBytes());
    byte[] output = md.digest();
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encode(output);
  }
}