/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.crawler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhiyao99.daemon.bean.BaseProduct;
import com.zhiyao99.daemon.conf.Const;
import com.zhiyao99.daemon.http.HttpSimpleDownloader;
import com.zhiyao99.daemon.paipai.Paipai;

/**
 * <p>名称:com.zhiyao99.daemon.crawler.SearchEnginePaipai.java</p>
 * <p>描述:</p>
 * <p>日期:2013-5-5 上午06:35:41</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class SearchEnginePaipai {
	
	private static Logger logger = LoggerFactory.getLogger(SearchEnginePaipai.class);
	
	private HttpSimpleDownloader httpDowner = new HttpSimpleDownloader();

	public static void main(String[] args) {
		Paipai pp = new Paipai();
		List<BaseProduct> lbp = pp.downAndParse("9.9包邮", 1);
		if (lbp == null || lbp.size() == 0) {

		} else {
			System.out.println("总共：" + lbp.size());
			for (BaseProduct bp : lbp) {
				System.out.println(bp.toString());
			}
		}
		
	}

	/**
	 * @param rule
	 * @param page
	 * @return
	 */
	public String getResultPage(String rule, int page) {
		String szUrl = makeURL(rule, page);
		logger.info("[paipai request URL]: " + szUrl);
		if (szUrl == null) {
			return null;
		}
		String szHtml = httpDowner.downloadHtmlString(szUrl);
		return szHtml;
	}

	/**
	 * @param rule
	 * @param page
	 * @return
	 */
	private String makeURL(String rule, int page) {
		boolean isUTFQuery  = findQueryType(rule);
		StringBuffer sb = new StringBuffer();
		//纯数字查询 9.9
		if(isUTFQuery){
			//9.9第一页
			//http://sse1.paipai.com/s-kh1gv--1-48-77---3-4-3----2-2-512-128-1-0-PTAG,20084.2.2-as,1.html
			//9.9第二页
			//http://sse1.paipai.com/s-kh1gv--49-48-77---3-4-3----2-2-512-128-1-0-PTAG,20084.2.2-as,1.html#newHand
			//第三页
			//http://sse1.paipai.com/s-kh1gv--97-48-77---3-4-3----2-2-512-128-1-0-PTAG,20084.2.2-as,1.html#newHand
			//9.9包邮
			//http://sse1.paipai.com/s-kh1gvlit9wm6x--1--80---3-4-3----2-2--128-0-0-PTAG,20084.2.2-as,1.html
			sb.append("http://sse1.paipai.com/");
			if(page >= 0){
				sb.append("s-kh1gv--"+ (Const.QUERY_PAIPAI_PAGE*(page-1)+1) +"-48-77---3-4-3----2-2-512-128-1-0-PTAG,20084.2.2-as,1.html#newHand");
			}
		}else{//9.9包邮
			//第一页
			//http://sse1.paipai.com/s-kh1gwpk64sia--1-48-77---3-4-3----2-2--128-0-0-PTAG,20084.2.2-as,1.html
			//第二页
			//http://sse1.paipai.com/s-kh1gwpk64sia--49-48-77---3-4-3----2-2-512-128-1-0-PTAG,20084.2.2-as,1.html#newHand
			sb.append("http://sse1.paipai.com/");
			if(page > 0){
				sb.append("s-kh1gwpk64sia--"+ (Const.QUERY_PAIPAI_PAGE*(page-1)+1) +"-48-77---3-4-3----2-2-512-128-1-0-PTAG,20084.2.2-as,1.html#newHand");
			}
		}
		
		return sb.toString();
	}

	/**
	 * @param rule
	 * @return
	 */
	private boolean findQueryType(String rule) {
		boolean isNumQuery = false;
		Pattern p = Pattern.compile("^\\d.\\d$");
		Matcher match = p.matcher(rule);
		if(match.find()){
			isNumQuery = true;
		}
		return isNumQuery;
	}

}
