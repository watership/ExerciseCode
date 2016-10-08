/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.http;

import java.net.URL;

import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.http.BrowserLikeHttpGet.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-23 下午04:16:21</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class BrowserLikeHttpGet {
	
	/** Create logs for BrowserLikeHttpGet.class */
	private static Logger logger = LoggerFactory
			.getLogger(BrowserLikeHttpGet.class);

	/**
	 * Create get method instance through url String parameter
	 * @param url
	 * @return Instance of GetMethod
	 */
	public static synchronized GetMethod newHttpGet(String url) {
		GetMethod get = null;
		try {
			get = new GetMethod(url);
			get.setFollowRedirects(true);
			get.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			get.setRequestHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
			get.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
			get.setRequestHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.20 (KHTML, like Gecko) Chrome/11.0.672.0 Safari/534.20");
		} catch (Exception ex) {
			logger.warn(ex.getMessage());
		}

		return get;
	}

	/**
	 * Create get method instance through URL parameter
	 * @param u
	 * @return New instance of GetMethod
	 */
	static public GetMethod newHttpGet(URL u) {
		try {
			return newHttpGet(u.toString());
		} catch (Exception ex) {
			logger.warn(ex.getMessage());
			return null;
		}
	}

	/**
	 * Create a new GetMethod instance with parameter url,referer
	 * @param url
	 * @param referer
	 * @return A new Instance of GetMethod
	 */
	static public GetMethod newHttpGet(String url, String referer) {
		GetMethod get = newHttpGet(url);
		if (get == null)
			return get;

		get.setRequestHeader("Referer", referer);
		return get;
	}
}
