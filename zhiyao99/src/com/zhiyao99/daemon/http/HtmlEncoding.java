/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.http;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.http.HtmlEncoding.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-23 下午04:16:40</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class HtmlEncoding {
	
	/** Guess content length is 2048Byte[2k] */
	private static final int fixed_guess_content_length = 2048;

	/**
	 * Guess page enconding from headers or guess it from content within size of len
	 * @param url
	 * @param httpHeaders
	 * @param content
	 * @param len
	 * @return String of html Encoding for example utf-8,gbk
	 */
	public synchronized String guessHtmlEncoding(String url,
			Header[] httpHeaders, byte[] content, int len) {
		return guessHtmlEncoding(httpHeaders, content, len);
	}

	/**
	 * Guess page enconding from headers or guess it from content
	 * @param heads
	 * @param octs
	 * @return String of html Encoding for example utf-8,gbk
	 */
	public synchronized String guessHtmlEncoding(Header[] heads, byte[] octs) {
		if (octs == null) {
			return guessHtmlEncoding(heads);
		} else {
			return guessHtmlEncoding(heads, octs, octs.length);
		}
	}

	/**
	 * Guess page enconding from headers or guess it from content within size of len
	 * @param httpHeaders
	 * @param content
	 * @param len
	 * @return  String of html Encoding for example utf-8,gbk
	 */
	public synchronized String guessHtmlEncoding(Header[] httpHeaders,
			byte[] content, int len) {
		String szEncoding = "";
		szEncoding = guessHtmlEncoding(httpHeaders);

		if (szEncoding == null || szEncoding.equals("")) {
			int guessLength = fixed_guess_content_length;
			if (len < fixed_guess_content_length)
				guessLength = len;

			byte[] buff = new byte[guessLength];
			System.arraycopy(content, 0, buff, 0, guessLength);

			szEncoding = guessHtmlEncoding(buff, guessLength);
		}

		return szEncoding;
	}

	/** Define a Pattern instance to match string which can find charset from meta tags */
	private static Pattern metaPattern = Pattern.compile(
			"<meta\\s+([^>]*http-equiv=(\"|')?content-type(\"|')?[^>]*)>",
			Pattern.CASE_INSENSITIVE);

	/** Define a Pattern instance to match string which can find charset */
	private static Pattern charsetPattern = Pattern.compile(
			"charset=\\s*[\'\"]?([a-z][_\\-0-9a-z]*)", Pattern.CASE_INSENSITIVE);

	/**
	 * Guess html Encoding by Header
	 * @param httpHeaders
	 * @return String of html Encoding for example utf-8,gbk
	 */
	public synchronized String guessHtmlEncoding(Header[] httpHeaders) {
		String szEncoding = "";
		String szValue = "";

		if (httpHeaders == null)
			return szEncoding;

		for (int i = 0; i < httpHeaders.length; i++) {
			if (httpHeaders[i].getName().equals("Content-Type")) {
				szValue = httpHeaders[i].getValue();
				szValue = szValue.trim();
				szValue = szValue.toLowerCase();
				break;
			}
		}

		if (szValue.equals(""))
			return szEncoding;

		Matcher charsetMatcher = charsetPattern.matcher(szValue);
		if (charsetMatcher.find()) {
			szEncoding = new String(charsetMatcher.group(1));
			szEncoding = szEncoding.replace("\"", "");
		}

		return szEncoding;
	}

	/**
	 * Guess html encodint by content byte[] from offset to length
	 * @param octs
	 * @param offset
	 * @param len
	 * @return String of html Encoding for example utf-8,gbk
	 */
	public synchronized String guessHtmlEncoding(byte[] octs, int offset,
			int len) {
		String encoding = "";
		try {
			String szPartHtml = new String(octs, offset, len, "iso-8859-1");
			Matcher metaMatcher = metaPattern.matcher(szPartHtml);

			if (metaMatcher.find()) {
				Matcher charsetMatcher = charsetPattern.matcher(metaMatcher
						.group(1));
				if (charsetMatcher.find()) {
					encoding = new String(charsetMatcher.group());
					encoding = encoding.replace("\"", "");
				}
			}

			if (encoding == "") {
				szPartHtml = szPartHtml.toLowerCase();
				int n = szPartHtml.indexOf(("<meta"));
				if (n > 0) {
					szPartHtml = szPartHtml.substring(n);
				}
				Matcher charsetMatcher = charsetPattern.matcher(szPartHtml);
				if (charsetMatcher.find()) {
					encoding = new String(charsetMatcher.group());
					encoding = encoding.replace("\"", "");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		encoding = encoding.toLowerCase();
		if (encoding.contains("charset=")) {
			encoding = encoding.replace("charset=", "");
		}

		return encoding;
	}

	/**
	 * Guess html encodint by content byte[] from 0 to length
	 * @param content
	 * @param len
	 * @return String of html Encoding for example utf-8,gbk
	 */
	public synchronized String guessHtmlEncoding(byte[] content, int len) {
		return guessHtmlEncoding(content, 0, len);
	}
	
	public static void main(String[] args) {
		Pattern p = Pattern.compile("charset=\\s*[\'\"]?([a-z][_\\-0-9a-z]*)",Pattern.CASE_INSENSITIVE);
		String szHtml = "<meta charset=\"gbk\" />";
		
		Matcher match = p.matcher(szHtml);
		if(match.find()){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}

}
