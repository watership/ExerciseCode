/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.util.Encoder.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-25 下午05:36:19</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class Encoder {

	public static String GBK2Hex(String sz){
		byte[] bytes = null;
		String szRETURN = "";
		String hex;

		try {
			bytes = sz.getBytes("gb2312");
			if(bytes==null)
				return szRETURN;

			for (int i = 0; i < bytes.length; i++) {
				if(bytes[i]>='0' && bytes[i]<='9' || bytes[i]>='a' && bytes[i]<='z' || bytes[i]>='A' && bytes[i]<='Z')
					hex = "" + (char)bytes[i];
				else
					hex = "%" + Integer.toHexString(bytes[i] & 0xFF).toUpperCase();
				szRETURN += hex;
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return szRETURN;
	}
	
	public static String UTF82Hex(String sz, String encode){
		byte[] bytes = null;
		String szRETURN = "";
		String hex;

		try {
			bytes = sz.getBytes(encode);
			if(bytes==null)
				return szRETURN;

			for (int i = 0; i < bytes.length; i++) {
				if( bytes[i]>='0' && bytes[i]<='9' 
					|| bytes[i]>='a' && bytes[i]<='z' 
					|| bytes[i]>='A' && bytes[i]<='Z' || bytes[i] == '.')
					hex = "" + (char)bytes[i];
				else
					hex = "%" + Integer.toHexString(bytes[i] & 0xFF).toUpperCase();
				szRETURN += hex;
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return szRETURN;
	}
	
	public static synchronized String UTF82Hex(String sz) {
		byte[] bytes = null;
		String szRETURN = "";
		String hex;

		try {
			bytes = sz.getBytes("utf-8");
			if (bytes == null)
				return szRETURN;

			for (int i = 0; i < bytes.length; i++) {
				if (bytes[i] >= '0' && bytes[i] <= '9' || bytes[i] >= 'a'
						&& bytes[i] <= 'z' || bytes[i] >= 'A'
						&& bytes[i] <= 'Z' || bytes[i] >= 33 && bytes[i] <= 126)
					hex = "" + (char) bytes[i];
				else
					hex = "%"
							+ Integer.toHexString(bytes[i] & 0xFF)
									.toUpperCase();
				szRETURN += hex;
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return szRETURN;
	}

	public static void main(String[] args) {
		System.out.println(Encoder.UTF82Hex("9.9包邮", "gb2312"));
	}

}
