 package vcardserverproxy;
 
 import java.io.IOException;
 import java.io.PrintStream;
 import java.io.RandomAccessFile;
 import java.security.AlgorithmParameters;
 import javax.crypto.Cipher;
 import javax.crypto.SecretKey;
 import javax.crypto.spec.IvParameterSpec;
 import javax.crypto.spec.SecretKeySpec;
 
 public class tripleDES
 {
   public static SecretKey key = null;
   public static AlgorithmParameters desparam = null;
   public static final String Hex = "0123456789ABCDEF";
   public static String mstrDefaultKey = "101010101010101010101010101010101010101010101010";
 
   public static final byte[] Hex2Byte = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
 
   public static byte[] encrypt0(byte[] bInputData)
     throws Exception
   {
     Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding", "SunJCE");
     cipher.init(1, key, desparam);
     return cipher.doFinal(fillBlock(bInputData));
   }
 
   public static byte[] encrypt0(String strmsg)
     throws Exception
   {
     byte[] data = fillBlock(strmsg);
     return encrypt0(data);
   }
 
   public static String encrypt(String strmsg)
     throws Exception
   {
     byte[] data = fillBlock(strmsg);
     return getHexDump(encrypt0(data));
   }
 
   private static byte[] fillBlock(String strmsg) {
     byte[] dataTemp = strmsg.getBytes();
     return fillBlock(dataTemp);
   }
 
   private static byte[] fillBlock(byte[] dataTemp) {
     int i = dataTemp.length % 8;
     int length = i == 0 ? dataTemp.length : dataTemp.length - i + 8;
     byte[] data = new byte[length];
     if (i != 0) {
       for (int j = 0; j < length; j++) {
         if (j < dataTemp.length)
           data[j] = dataTemp[j];
         else
           data[j] = -1;
       }
     }
     else {
       data = dataTemp;
     }
     return data;
   }
 
   public static byte[] decrypt0(byte[] inpBytes) throws Exception {
     Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding", "SunJCE");
     cipher.init(2, key, desparam);
     return cipher.doFinal(inpBytes);
   }
 
   public static String decrypt(byte[] inpBytes) throws Exception {
     byte[] result = decrypt0(inpBytes);
     String strHex = getHexDump(result);
     strHex = strHex.toLowerCase();
     strHex = strHex.replaceAll("ff", "");
     return new String(convertHexToByte(strHex));
   }
 
   public static String getHexDump(byte[] data) {
     String dump = "";
     try {
       int dataLen = data.length;
       for (int i = 0; i < dataLen; i++) {
         dump = dump + Character.forDigit(data[i] >> 4 & 0xF, 16);
         dump = dump + Character.forDigit(data[i] & 0xF, 16);
       }
     } catch (Throwable t) {
       dump = "Throwable caught when dumping = " + t;
     }
     return dump;
   }
 
   public static byte[] convertHexToByte(String strHex) throws Exception {
     String strTemp = "";
     strTemp = strHex.toUpperCase();
     if (strTemp.length() % 2 != 0) {
       throw new Exception("Invalid length");
     }
     int length = strTemp.length() / 2;
     byte[] keySec = new byte[length];
     for (int i = 0; i < length; i++) {
       char ch0 = strTemp.charAt(2 * i);
       char ch1 = strTemp.charAt(2 * i + 1);
       int loByte = "0123456789ABCDEF".indexOf(ch0);
       int hiByte = "0123456789ABCDEF".indexOf(ch1);
       byte lo = Hex2Byte[loByte];
       byte hi = Hex2Byte[hiByte];
       Byte byelo = new Byte(lo);
       Byte byehi = new Byte(hi);
 
       int inlo = byelo.intValue();
       int inhi = byehi.intValue();
       int value = inlo * 16 + inhi;
       keySec[i] = (byte)value;
     }
 
     return keySec;
   }
 
   public static SecretKey createKey(String key) throws Exception
   {
     byte[] keyData = convertHexToByte(key);
     SecretKey keyReturn = new SecretKeySpec(keyData, "DESede");
     return keyReturn;
   }
 
   public static String createRadomKey()
   {
     String strKey = "";
     for (int i = 0; i < 48; i++) {
       double dbkey = Math.random() * 1000.0D;
       int intkey = (int)Math.round(dbkey);
       intkey %= 16;
       strKey = strKey + "0123456789ABCDEF".charAt(intkey);
     }
     return strKey;
   }
 
   public static void encryptFile(String strDir, String strFileIn, String strFileOut, boolean isEncrypt)
   {
     String strSeparator = System.getProperty("file.separator");
     String vstrIn = strDir + strSeparator + strFileIn;
     String vstrOut = strDir + strSeparator + strFileOut;
     RandomAccessFile fileIn = null;
     RandomAccessFile fileOut = null;
     try {
       fileIn = new RandomAccessFile(vstrIn, "r");
       fileOut = new RandomAccessFile(vstrOut, "rw");
       String strLine = "";
       long intLength = fileIn.length();
       byte[] b = new byte[(int)intLength];
       fileIn.read(b);
       if (isEncrypt)
         fileOut.write(encrypt0(b));
       else {
         fileOut.write(decrypt0(b));
       }
       fileIn.close();
       fileOut.close();
     } catch (Exception ex1) {
       ex1.printStackTrace();
     } finally {
       try {
         fileIn.close();
         fileOut.close();
       }
       catch (IOException ex1) {
       }
     }
   }
 
   public static void main(String[] args) {
     try {
       tripleDES app = new tripleDES();
 
       key = createKey("46C7A785D515E680133EFE61D6C83B62AB76349E08406446");
       String en = encrypt("123456");
       System.out.println("EN:" + en);
       String de = decrypt(convertHexToByte(en));
       System.out.println("DE:" + de);
     }
     catch (Exception ex)
     {
       ex.printStackTrace();
     }
   }
 
   static
   {
     try
     {
       key = createKey(mstrDefaultKey);
 
       byte[] myIv = { 0, 0, 0, 0, 0, 0, 0, 0 };
 
       IvParameterSpec desparamspec = new IvParameterSpec(myIv);
 
       desparam = AlgorithmParameters.getInstance("TripleDES");
 
       desparam.init(desparamspec);
     } catch (Exception e) {
       e.printStackTrace();
     }
   }
 }

/* Location:           /media/sda2/Projects/cardService/lib/vcardServerProxy.jar
 * Qualified Name:     vcardserverproxy.tripleDES
 * JD-Core Version:    0.6.0
 */