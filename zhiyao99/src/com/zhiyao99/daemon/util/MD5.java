/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.util.MD5.java</p>
 * <p>描述:MD5指纹码生成工具类</p>
 * <p>日期:2013-4-30 下午03:01:19</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class MD5 {

	private static char hexDigits[] = new char[] { '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static void main(String[] args) {

		System.out.println("MD5 algorithm demos: ");
		System.out.println();

		String sz[] = { "admin",
				"http://gb.cri.cn/27824/2010/09/02/2165s2977849.htm",
				"万科8月销售收入破百亿 楼市将迎来供应量高峰",
				"ئەتىدىن باشلاپ يانفۇنغا ھەقىقىي ئىسىم تۈزۈمى ",
				"chinamobilesz.com" };

		for (int i = 0; i < sz.length; i++) {
			System.out.println("  [" + i + "]" + " SOURCE: " + sz[i]);
			System.out.println("     " + " RESULT: "
					+ new String(MD5.EncodeByMd5(sz[i])).toLowerCase());
			System.out.println();
		}

		System.out.println("");
	}

	private static char[] byteToHexString(byte[] tmp) {
		char str[] = new char[32];
		int k = 0;
		byte byte0;

		for (int i = 0; i < 16; i++) {
			byte0 = tmp[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}

		return str;
	}

	private static char[] zero32chars() {
		char zeroCHARs[] = new char[32];

		for (int i = 0; i < 32; i++)
			zeroCHARs[i] = '0';

		return zeroCHARs;
	}

	public static char[] EncodeByMd5(String str) {
		return EncodeByMd5(str, "utf-8");
	}

	public static char[] EncodeByMd5(String str, String encoding) {
		if (str == null)
			return zero32chars();

		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(str.getBytes(encoding));

			if (digest == null || digest.length != 16)
				return zero32chars();

			//Bytes turn into Hex String
			char[] md5chars = byteToHexString(digest);
			return md5chars;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return zero32chars();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return zero32chars();
		}
	}

	public static char[] EncodeByMd5(byte[] bytes, int offset, int len) {
		byte[] buff = new byte[len];
		System.arraycopy(bytes, offset, buff, 0, len);
		return EncodeByMd5(buff);
	}
	
	public static char[] EncodeByMd5(byte[] bytes) {
		if (bytes == null)
			return zero32chars();

		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(bytes);

			if (digest == null || digest.length != 16)
				return zero32chars();

			char[] md5chars = byteToHexString(digest);
			return md5chars;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return zero32chars();
		}
	}

}
