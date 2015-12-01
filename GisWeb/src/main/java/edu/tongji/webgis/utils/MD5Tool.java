package edu.tongji.webgis.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Tool {

	private static String MD5_RANDOM = "py458as586_v2";

	public static String getMd5(String plainText) {
		plainText = plainText.trim();
		plainText = plainText + MD5_RANDOM;
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		System.out.println(MD5Tool.getMd5("222222"));
//		System.out.println("getd".matches("get\\w+"));
	}
}
