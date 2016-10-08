/*
 * Newskysoft, All Right Reserved, 2005-2013.
 * 
 * http://www.newskysoft.com.cn/
 */
package com.zhiyao99.daemon.http;

import java.net.URL;

/**
 * 
 * <p>名称:cn.com.newskysoft.daemon.http.UrlPackage.java</p>
 * <p>描述:URL打包实体类</p>
 * <p>日期:2013-4-17 上午11:13:43</p>
 * 
 * @author  zhouhui
 * @version 2.0
 *
 */
public class UrlPackage {
	
	/** URL对象*/
	private URL url = null;
	/** URL网址字符串*/
	private String urlString = "";
	/** URL网址指纹码*/
	private char[] UrlMd5 = null;
	/** 主机*/
	private String host = "";

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public char[] getUrlMd5() {
		return UrlMd5;
	}

	public void setUrlMd5(char[] urlMd5) {
		UrlMd5 = urlMd5;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		if (host != null) {
			this.host = host;
		}
	}

	public String toString() {
		String sz = "";

		if (host.trim().length() == 0)
			return sz;
		StringBuilder sb = new StringBuilder();
		sb.append("[HOST:");
		sb.append(host);
		sb.append("] ");
		sb.append(urlString);
		sb.append(" ");
		sb.append(UrlMd5);
		sb.append(" ");

		sz = sb.toString();
		return sz;
	}
}
