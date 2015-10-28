package com.itheima.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class SecurityUtils {

	public static String encodePassword(String password){
		String algorithm="MD5";
		
		//声明一个变量接收十六进制加密后的字符数组
		char[] encodeHex =null;
		try {
			MessageDigest instance = MessageDigest.getInstance(algorithm);
			byte[] digest = instance.digest(password.getBytes());
						//十六进制加密
			encodeHex = Hex.encodeHex(digest);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(encodeHex);
	}
	
}
