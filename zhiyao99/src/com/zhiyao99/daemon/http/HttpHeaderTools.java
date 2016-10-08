/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.http;

import org.apache.commons.httpclient.Header;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.http.HttpHeaderTools.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-23 下午04:17:12</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class HttpHeaderTools {
	
	/**
	 * [static method]
	 * 
	 * Return the header value if found, otherwise return "".
	 */
	public static synchronized String getHeaderValue(Header[] httpHeaders,
			String propName) {
		String pv = "";

		if (httpHeaders == null)
			return pv;

		if (propName == null || propName.length() == 0)
			return pv;

		for (Header h : httpHeaders) {
			if (propName.equalsIgnoreCase(h.getName())) {
				pv = h.getValue();
				break;
			}
		}

		return pv;
	}
}
