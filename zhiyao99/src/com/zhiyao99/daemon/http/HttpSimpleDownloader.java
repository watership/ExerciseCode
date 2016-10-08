/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.http;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.http.HttpSimpleDownloader.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-23 下午04:17:30</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class HttpSimpleDownloader {
	
	/** Create logs for HttpSimpleDownloader.class */
	private static Logger logger = LoggerFactory
			.getLogger(HttpSimpleDownloader.class);

	/** Set a html size is 2M when crawl a bigger one then drop it */
	private int max_buff_size = 2 * 1024 * 1024;  
	
	/***/ 
	private byte[] byteBuff = null;
	
	/** Set http connection timeout is 30seconds */
	private int httpTimeout = 30000;
	
	/** HttpClientManager which can provide a Manager to manage HttpClient instance */
	private HttpClientManager hcManager = null;
	
	/** HttpClient which can make a virtual browser to access website */
	private HttpClient httpClient = null;
	
	/** HttpProcessor which can process html content such as drop useless html tags */
	private HtmlProcessor hp = null;
	
	/** HttpEncodint which can guess or analysis pageEncoding */
	private HtmlEncoding he = null;

	/**
	 * Default constructor
	 */
	public HttpSimpleDownloader() {
		initHttpClient();
	}

	/**
	 * Constructor with parameter buffsize  then init it
	 * @param buffsize
	 */
	public HttpSimpleDownloader(int buffsize) {
		if (buffsize > 0) {
			max_buff_size = buffsize;
		}
		initHttpClient();
	}

	/**
	 * Constructor with parameter buffsize and timeout then init it
	 * @param buffsize
	 * @param httptimeout
	 */
	public HttpSimpleDownloader(int buffsize, int httptimeout) {
		if (buffsize > 0) {
			max_buff_size = buffsize;
		}
		if (httptimeout > 0) {
			httpTimeout = httptimeout;
		}
		initHttpClient();
	}

	/**
	 * Init httpClient instatce which includes set timeout
	 * and creates new instance of HtmlProcessor and HtmlEncoding
	 */
	@SuppressWarnings("deprecation")
	private void initHttpClient() {
		hcManager = new HttpClientManager();
		httpClient = hcManager.getHttpClient();
		httpClient.setConnectionTimeout(httpTimeout);
		hp = new HtmlProcessor();
		he = new HtmlEncoding();

		byteBuff = new byte[max_buff_size];
	}

	/**
	 * If url is available
	 * @param url
	 * @return true:avaliable false:unavailable
	 */
	private boolean isUrlValid(String url) {
		try {
			URL u = new URL(url);
			u.getHost();
		} catch (MalformedURLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Download html string through url parameter
	 * @param url
	 * @return String of html
	 */
	public String downloadHtmlString(String url) {
		String szHtml = null;
		String charset = null;

		if (!isUrlValid(url)) {
			return szHtml;
		}

		try {
			GetMethod httpGet = BrowserLikeHttpGet.newHttpGet(url);
			httpGet.setFollowRedirects(true);
			httpClient.executeMethod(httpGet);                 //excute getMethod

			Header[] headers = httpGet.getResponseHeaders();   //get response headers

			int cLen = 0;
			String cl = HttpHeaderTools.getHeaderValue(headers,           
					"Content-Length");                         //get content-Length
			if (cl != null && cl.length() > 0) {
				cLen = Integer.parseInt(cl);
			}

			if (cLen > max_buff_size) {
				return null;
			}

			InputStream ist = httpGet.getResponseBodyAsStream();
			BufferedInputStream bis = new BufferedInputStream(ist);

			int pos = 0;
			int len = 0;
			int totalLen = 0;

			len = bis.read(byteBuff, pos, max_buff_size);
			while (totalLen < cLen || len > 0) {
				pos += len;
				totalLen += len;
				len = bis.read(byteBuff, pos, max_buff_size - totalLen);
			}

			bis.close();
			ist.close();
			httpGet.releaseConnection();

			if (totalLen == 0) {
				return szHtml;
			}

			byte[] htmlOcts = new byte[totalLen];
			System.arraycopy(byteBuff, 0, htmlOcts, 0, totalLen);

			//Unzip html content if necessary
			byte[] htmlOcts2 = hp.unzipIfNecessary(htmlOcts, headers); 
			charset = he.guessHtmlEncoding(headers, htmlOcts2);
//			System.out.println("charset is >>"+ charset);

			szHtml = new String(htmlOcts2, charset);
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage() + " | " + charset + " " + url);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage() + " | " + url);
			e.printStackTrace();
		} catch (UnknownHostException e) {
			logger.warn(e.getMessage() + " | " + url);
			e.printStackTrace();
		} catch (HttpException e) {
			logger.warn(e.getMessage() + " | " + url);
			e.printStackTrace();
		} catch (ConnectException e) {
			logger.warn(e.getMessage() + " | " + url);
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			logger.warn(e.getMessage() + " | " + url);
			e.printStackTrace();
		} catch (SocketException e) {
			logger.warn(e.getMessage() + " | " + url);
			e.printStackTrace();
		} catch (Exception e) {
			logger.warn(e.getMessage() + " | " + url);
			e.printStackTrace();
		}

		return szHtml;
	}

	/**
	 * Download Object from website through url parameter
	 * @param url
	 * @return HttpObject instance
	 */
	public HttpObject downloadHttpObject(String url) {
		HttpObject ho = null;
		byte[] octs = null;
		Header[] headers = null;

		if (!isUrlValid(url)) {
			return ho;
		}

		try {
			GetMethod httpGet = BrowserLikeHttpGet.newHttpGet(url);
			httpClient.executeMethod(httpGet);

			headers = httpGet.getResponseHeaders();

			int cLen = 0;
			String cl = HttpHeaderTools.getHeaderValue(headers,
					"Content-Length");
			if (cl != null && cl.length() > 0) {
				cLen = Integer.parseInt(cl);
			}

			if (cLen > max_buff_size) {
				return null;
			}

			InputStream ist = httpGet.getResponseBodyAsStream();
			BufferedInputStream bis = new BufferedInputStream(ist);

			int pos = 0;
			int len = 0;
			int totalLen = 0;

			len = bis.read(byteBuff, pos, max_buff_size);
			while (totalLen < cLen || len > 0) {
				pos += len;
				totalLen += len;
				len = bis.read(byteBuff, pos, max_buff_size - totalLen);
			}

			bis.close();
			ist.close();
			httpGet.releaseConnection();

			octs = new byte[totalLen];
			System.arraycopy(byteBuff, 0, octs, 0, totalLen);
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage() + " | " + url);
			return ho;
		} catch (UnknownHostException e) {
			logger.warn(e.getMessage() + " | " + url);
			return ho;
		} catch (HttpException e) {
			logger.warn(e.getMessage() + " | " + url);
			return ho;
		} catch (ConnectException e) {
			logger.warn(e.getMessage() + " | " + url);
			return ho;
		} catch (SocketTimeoutException e) {
			logger.warn(e.getMessage() + " | " + url);
			return ho;
		} catch (SocketException e) {
			logger.warn(e.getMessage() + " | " + url);
			return ho;
		} catch (Exception e) {
			logger.warn(e.getMessage() + " | " + url);
			return ho;
		}

		ho = new HttpObject();
		ho.setContent(octs);
		ho.setHeaders(headers);
		ho.setUrl(url);
		return ho;
	}

	/**
	 * For unit test
	 * @param args
	 */
	public static void main(String[] args) {
		HttpSimpleDownloader hsd = new HttpSimpleDownloader();
		HttpObject ho = hsd
				.downloadHttpObject("http://img.itxinwen.com/2011/1221/20111221084553847.jpg");

		String htmlStr = hsd.downloadHtmlString("http://www.csdn.net/article/2012-09-17/2809999-steve-ballmer-surface");
		if (ho == null) {
			return;
		}

		Header[] heads = ho.getHeaders();
		for (Header h : heads) {
			System.out.print(h.toString());
		}
		System.out.println("----------------------------------------------------------");
		System.out.println(htmlStr);
	}
}
