/*
 * Newskysoft, All Right Reserved, 2005-2013.
 * 
 * http://www.newskysoft.com.cn/
 */
package com.zhiyao99.daemon.http;

import java.net.MalformedURLException;
import java.net.URL;

import com.zhiyao99.daemon.conf.Const;
import com.zhiyao99.daemon.util.Encoder;
import com.zhiyao99.daemon.util.MD5;


/**
 * 
 * <p>名称:cn.com.newskysoft.daemon.http.UrlPacker.java</p>
 * <p>描述:URL打包工具类</p>
 * <p>日期:2013-4-17 上午11:16:02</p>
 * 
 * @author  zhouhui
 * @version 2.0
 *
 */
public class UrlPacker {
	
	/** HTTP协议*/
	public static final String httpProtocol = "http://";

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		String[] szURLs = {
				"http://weibo.com/2032572557/ztjkvmUM1",
				"http://dict.youdao.com/search?q=%E6%89%93%E5%8C%85&ue=utf8&keyfrom=dict.index#le%3Deng%26q%3Dpacker%26tab%3Dchn%26keyfrom%3Ddict.top",
				"http://newcar.xcar.com.cn/201109/news_291446_1.html",
				"http://news.sohu.com/20111001/n321149028.shtml",
				"http://www.ittime.com.cn/column.asp?type=高端对话" };

		for (int i = 0; i < szURLs.length; i++) {
			System.out.println(pack(szURLs[i]));
		}

		String context = "http://news.sohu.com/s2011/guoqing/";
		String[] szLinks = { "http://news.sohu.com/20110927/n320712192.shtml",
				"index.html", "./abc.jsp", "../def.asp" };

		for (int i = 0; i < szLinks.length; i++) {
			System.out.println(pack(context, szLinks[i]));
		}
	}

	public static UrlPackage pack(String currentURL, String link) {
		if (currentURL == null)
			return null;

		currentURL = currentURL.trim();
		if (currentURL.length() == 0)
			return null;

		if (!currentURL.startsWith(httpProtocol))
			currentURL = httpProtocol + currentURL;

		URL url = null;

		try {
			url = new URL(currentURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return pack(url, link);
	}

	public static UrlPackage pack(URL u, String link) {
		if (u == null || link == null || link.trim().length() == 0)
			return null;

		URL url = null;

		try {
			url = new URL(u, link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return pack(url);
	}

	public static synchronized UrlPackage pack(String u) {
		if (u == null)
			return null;

		u = u.trim();
		if (u.length() == 0)
			return null;

		URL url = null;
		try {
			if (!u.startsWith(httpProtocol)) {
				u = httpProtocol + u;
			}
			url = new URL(u);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return pack(url);
	}

	public static UrlPackage pack(URL u) {
		if (u == null)
			return null;

		String szURL = u.toString();
		if (szURL == null || szURL.length() == 0)
			return null;

		// substract http://
		//
		if (szURL.startsWith(httpProtocol)) {
			szURL = szURL.substring(httpProtocol.length());
		}

		UrlPackage up = new UrlPackage();
		up.setUrl(u);
		if (szURL != null && szURL.length() > Const.maxlength_url) {
			szURL = szURL.substring(0, Const.maxlength_url);
		}
		szURL = Encoder.UTF82Hex(szURL);
		up.setUrlString(szURL);
		up.setHost(u.getHost());
		up.setUrlMd5(MD5.EncodeByMd5(szURL));

		return up;
	}
}
