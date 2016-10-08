/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.http.HttpClientManager.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-23 下午04:17:01</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class HttpClientManager {
	
	private SimpleHttpConnectionManager connectionManager = null;
	private int timeout = 60000;               //The max time to connecte if out then give up

	/**
	 * Init HttpClientManager
	 * Set soTimeout and connectionTimeout
	 */
	private void init() {
		if (connectionManager != null)
			return;

		connectionManager = new SimpleHttpConnectionManager();
		connectionManager.getParams().setSoTimeout(timeout);
		connectionManager.getParams().setConnectionTimeout(timeout);
	}

	/**
	 * Get httpClient
	 * @return HttpClient
	 */
	public HttpClient getHttpClient() {
		init();

		HttpClient httpclient = new HttpClient(connectionManager);
//		httpclient.getHostConfiguration().setProxy("wsg.cmszmail.ad", 8083);
//		
//        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("", "");        
//        httpclient.getState().setProxyCredentials(AuthScope.ANY, creds);
		return httpclient;
	}

	/**
	 * Default constructor 
	 */
	public HttpClientManager() {
		init();
	}
}
