/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.zhiyao99.daemon.util.Deflater;
import com.zhiyao99.daemon.util.GZipper;


/**
 * 
 * <p>名称:com.zhiyao99.daemon.http.HtmlProcessor.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-23 下午04:16:51</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class HtmlProcessor {
	
	/** Begin tags of html */
	private static String[] tagBegins = new String[] { "<img", "<table",
			"<tbody", "<tr", "<td", "<th", "<div", "<p", "<span", "<strong",
			"<br", "<a", "<font", "<b", "<dl", "<dt", "<select", "<ul", "<li",
			"<form", "<iframe", "<frame", "<dd", "<code", "<sup", "<cite",
			"<marquee", "<th", "<thead", "<em", "<label", "<legend", "<wbr",
			"<object", "<textarea", "<embed", "<small", "<meta" };
	
	/** End tags of html */
	private static String[] tagEnds = new String[] { "</img>", "</table>",
			"</tbody>", "</tr>", "</td>", "</span>", "</strong>", "</a>",
			"</br>", "</font>", "</dl>", "</dt>", "</p>", "</select>",
			"</div>", "</b>", "</ul>", "</li>", "</form>", "</iframe>",
			"</frame>", "</dd>", "</code>", "</i>", "</sup>", "</cite>",
			"</marquee>", "</th>", "</thead>", "</em>", "</label>",
			"</legend>", "</wbr>", "</object>", "</textarea>", "</embed>",
			"</small>" };

	/** Useless tags */
	private static String[] uselessTexts = new String[] { "<i>", "<ol>", "<u>",
			"</u>", "■", "▲", "●", ">>>>", ">>>" };

	/**
	 * Remove useless blanks tags and replace by ' '
	 * @param htmlSegment
	 * @return String without tags '\s'
	 */
	public synchronized String removeObscureBlanks(String htmlSegment) {
		if (htmlSegment == null || htmlSegment.length() == 0) {
			return "";
		}

		htmlSegment = StringEscapeUtils.unescapeHtml(htmlSegment);   //un escape html String <div>cmsz</div>
		htmlSegment = htmlSegment.replaceAll("\\s+", " ");
		return htmlSegment;
	}

	/**
	 * Remove html tags 
	 * <p>For example: '<em>shenzhen&nbsp; </em>&nbsp;<br/> <div>yidong&nbsp;</div>'</p>
	 * <p>Return:'shenzhen    yidong'</p>
	 * @param htmlSegment
	 * @return String without tags
	 */
	public synchronized String removeInternalTags(String htmlSegment) {
		if (htmlSegment == null || htmlSegment.length() == 0) {
			return "";
		}

		String text = null;
		StringBuilder sb = new StringBuilder("<root>");
		sb.append(htmlSegment);
		sb.append("</root>");

		try {
			SAXReader saxReader = new SAXReader();
			ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString()
					.getBytes());
			Document doc = saxReader.read(bais);
			text = doc.getStringValue();
		} catch (DocumentException e) {
		}

		if (text != null && text.trim().length() > 0) {
			return text;
		} else {
			return htmlSegment;
		}
	}


	/**
	 * Remove useless tags 
	 * <p>For example: '<em>shenzhen&nbsp; </em>&nbsp;<br/> <div>yidong&nbsp;</div>'</p>
	 * <p>Return:'shenzhen    yidong '
	 */
	public synchronized String extractPureText(String szContent) {
		if (szContent == null) {
			return "";
		}

		if (szContent.length() == 0) {
			return szContent;
		}

		szContent = StringEscapeUtils.unescapeHtml(szContent);

		int pos1 = -1;
		int pos2 = -1;

		String tagStop = ">";
		StringBuilder sbr = new StringBuilder(szContent.toLowerCase());

		for (int i = 0; i < tagBegins.length; i++) {
			pos1 = sbr.indexOf(tagBegins[i]);
			while (pos1 >= 0) {
				sbr.delete(pos1, pos1 + tagBegins[i].length());

				pos2 = sbr.indexOf(tagStop, pos1);
				if (pos2 >= 0) {
					sbr.delete(pos1, pos2 + tagStop.length());
				}

				pos1 = sbr.indexOf(tagBegins[i]);
			}
		}

		for (int i = 0; i < tagEnds.length; i++) {
			pos1 = sbr.indexOf(tagEnds[i]);
			while (pos1 >= 0) {
				sbr.delete(pos1, pos1 + tagEnds[i].length());
				pos1 = sbr.indexOf(tagEnds[i]);
			}
		}

		for (int i = 0; i < uselessTexts.length; i++) {
			pos1 = sbr.indexOf(uselessTexts[i]);
			while (pos1 >= 0) {
				sbr.delete(pos1, pos1 + uselessTexts[i].length());
				pos1 = sbr.indexOf(uselessTexts[i]);
			}
		}

		return sbr.toString();
	}

	/**
	 * Unzip html content if necessary
	 * @param octs
	 * @param heads
	 * @return byte[] includes unzip html content 
	 */
	public synchronized byte[] unzipIfNecessary(byte[] octs, Header[] heads) {
		return unzipIfNecessary(octs, 0, octs.length, heads);
	}

	/**
	 * Unzip html content if necessary
	 * @param originalContent
	 * @param offset
	 * @param len
	 * @param httpHeaders
	 * @return byte[] includes unzip html content
	 */
	public synchronized byte[] unzipIfNecessary(byte[] originalContent,
			int offset, int len, Header[] httpHeaders) {
		if (originalContent == null || originalContent.length == 0
				|| httpHeaders == null)
			return originalContent;

		String szZipType = HttpHeaderTools.getHeaderValue(httpHeaders,
				"Content-Encoding");

		if (szZipType == null)
			return originalContent;

		szZipType = szZipType.trim();
		if (szZipType.length() == 0)
			return originalContent;

		byte[] newContent = new byte[0];
		try {
			if (szZipType.equals("gzip")) {
				newContent = GZipper.unzip(originalContent, offset, len);
			} else if (szZipType.equals("deflate")) {
				newContent = Deflater.inflate(originalContent, offset, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newContent;
	}
	
	/**
	 * For unit test
	 * @param args
	 */
	public static void main(String[] args) {
		String htmlstr = "<em>shenzhen&nbsp; </em>&nbsp;<br/> <div>yidong&nbsp;</div>";
		htmlstr = new HtmlProcessor().removeObscureBlanks(htmlstr);
		System.out.println(htmlstr);
		
		htmlstr = new HtmlProcessor().removeInternalTags(htmlstr);
		System.out.println(htmlstr);
		
		htmlstr = new HtmlProcessor().extractPureText(htmlstr);
		System.out.println(htmlstr);
	}

}
