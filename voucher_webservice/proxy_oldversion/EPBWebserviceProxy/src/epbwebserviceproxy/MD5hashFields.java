package epbwebserviceproxy;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.fss.util:
//            BASE64Encoder

public class MD5hashFields {
	static final char[] HEX_TABLE = new char[] { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	@SuppressWarnings("unchecked")
	public static String hashAllFields(Map fields, String secret_key) {
		// create a list and sort it
		List fieldNames = new ArrayList(fields.keySet());
		Collections.sort(fieldNames);

		// create a buffer for the md5 input and add the secure secret first
		StringBuffer buf = new StringBuffer();
		buf.append(secret_key);

		// iterate through the list and add the remaining field values
		Iterator itr = fieldNames.iterator();

		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) fields.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				buf.append(fieldValue);
			}
		}

		MessageDigest md5 = null;
		byte[] ba = null;

		// create the md5 hash and UTF-8 encode it
		try {
			md5 = MessageDigest.getInstance("MD5");
			ba = md5.digest(buf.toString().getBytes("UTF-8"));
		} catch (Exception e) {
		} // wont happen
		// return buf.toString();
		return hex(ba);

	}

	public static String hex(byte[] input) {
		// create a StringBuffer 2x the size of the hash array
		StringBuffer sb = new StringBuffer(input.length * 2);

		// retrieve the byte array data, convert it to hex
		// and add it to the StringBuffer
		for (int i = 0; i < input.length; i++) {
			sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
			sb.append(HEX_TABLE[input[i] & 0xf]);
		}
		return sb.toString();
	}

	// Method for creating a URL query string
	@SuppressWarnings( { "unchecked", "deprecation" })
	public static void appendQueryFields(StringBuffer buf, Map fields) {

		// create a list
		List fieldNames = new ArrayList(fields.keySet());
		Iterator itr = fieldNames.iterator();

		// move through the list and create a series of URL key/value pairs
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) fields.get(fieldName);

			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// append the URL parameters
				buf.append(URLEncoder.encode(fieldName));
				buf.append('=');
				buf.append(URLEncoder.encode(fieldValue));
			}
			// add a '&' to the end if we have more fields coming.
			if (itr.hasNext()) {
				buf.append('&');
			}
		}
	}

	// Ma hoa MD5:
	public static String getMD5(String sMessage) {
		byte[] defaultBytes = sMessage.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException nsae) {
			return null;
		}
	}
}
