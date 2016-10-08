/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * <p>
 * 名称:com.zhiyao99.daemon.util.StringUtil.java
 * </p>
 * <p>
 * 描述:
 * </p>
 * <p>
 * 日期:2013-4-25 下午11:42:58
 * </p>
 * 
 * @author wangxw
 * @version 1.0
 * 
 */
public class StringUtil {
	
	/**
	 * 获取纯文本
	 * @param content  带标签的字符串
	 * @return         返回纯文本
	 */
	public synchronized static String getPureContent(String content){
		if( content == null )return "";
		Document doc = Jsoup.parse(content);
		return doc.body().text();
	}
	
	/**
	 * 获取摘要
	 * @param content  纯文本
	 * @return         返回摘要
	 */
	public synchronized static String getAbs(String content){
		if( content == null )return "";
		if( content.length() > 200 )
			return content.substring(0, 200);
		else
			return content;
	}

	public static void main(String[] args) {
		String str = "abc345def";
		int i = getIntValue(str);
		System.out.println(str + "->" + i);

		String str2 = "abc345.678.91def";
		double d = getDoubleValue(str2);
		System.out.println(str2 + "->" + d);
	}

	/**
	 * 解析str，获得其中的整数
	 * 
	 * @param str
	 * @return
	 */
	public static int getIntValue(String str) {
		int r = 0;
		if (str != null && str.length() != 0) {
			StringBuffer bf = new StringBuffer();

			char[] chars = str.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				if (c >= '0' && c <= '9') {
					bf.append(c);
				} else if (c == ',') {
					continue;
				} else {
					if (bf.length() != 0) {
						break;
					}
				}
			}
			try {
				r = Integer.parseInt(bf.toString());
			} catch (Exception e) {
			}
		}
		return r;
	}

	/**
	 * 解析字符串获得双精度型数值，
	 * 
	 * @param str
	 * @return
	 */
	public static double getDoubleValue(String str) {
		double d = 0;

		if (str != null && str.length() != 0) {
			StringBuffer bf = new StringBuffer();

			char[] chars = str.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				if (c >= '0' && c <= '9') {
					bf.append(c);
				} else if (c == '.') {
					if (bf.length() == 0) {
						continue;
					} else if (bf.indexOf(".") != -1) {
						break;
					} else {
						bf.append(c);
					}
				} else {
					if (bf.length() != 0) {
						break;
					}
				}
			}
			try {
				d = Double.parseDouble(bf.toString());
			} catch (Exception e) {
			}
		}

		return d;
	}

}
