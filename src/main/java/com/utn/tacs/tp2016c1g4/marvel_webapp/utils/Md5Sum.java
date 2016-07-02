package com.utn.tacs.tp2016c1g4.marvel_webapp.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Sum {

	public static String getMD5(String input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	public static String getMarvelHash() {
		String publicKey = "b1b35d57fc130504f737b14e581d523b";
		String privateKey = "0d43f3ee357f2419224ae291150a52d8db17c082";
		String ts = Long.toString(System.currentTimeMillis());

		String input = "";
		input += ts;
		input += privateKey;
		input += publicKey;

		return getMD5(input);
	}

}
