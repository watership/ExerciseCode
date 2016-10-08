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
 * <p>名称:com.zhiyao99.daemon.http.HttpObject.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-23 下午04:17:24</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class HttpObject {
	
	/** URL of HttpObject */
	private String url = null;
	
	/** Headers of HttpObject */
	private Header[] headers = null;
	
	/** Content of HttpObject */
	private byte[] content = null;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Header[] getHeaders() {
		return headers;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
