/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.taobao;

import java.io.FileOutputStream;
import java.util.List;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhiyao99.daemon.bean.BaseProduct;
import com.zhiyao99.daemon.conf.Const;
import com.zhiyao99.daemon.crawler.SearchEngineTaobao;
import com.zhiyao99.daemon.crawler.parser.ParsePattern;
import com.zhiyao99.daemon.crawler.parser.Parser;
import com.zhiyao99.daemon.crawler.parser.ParserConst;

/**
 * <p>
 * 名称:com.zhiyao99.daemon.taobao.Taobao.java
 * </p>
 * <p>
 * 描述:
 * </p>
 * <p>
 * 日期:2013-4-24 下午04:40:57
 * </p>
 * 
 * @author wangxw
 * @version 1.0
 * 
 */
public class Taobao {

	private static Logger logger = LoggerFactory.getLogger(Taobao.class);

	private SearchEngineTaobao taobao = new SearchEngineTaobao();

	private Parser p = null;

	public Taobao() {
		p = new Parser();

		ParsePattern pp = new ParsePattern();

		pp.addArea("item", "<div class=\"item-box\"",
				"div class=\"col item icon-datalink\"");
		pp.addAreaUnit("item", ParserConst.PROP_PRODUCTURL, "href=\"", "\"");
		pp.addAreaUnit("item", ParserConst.PROP_IMAGEURL, "src=\"", "\"");
		pp.addAreaUnit("item", ParserConst.PROP_TIP, "title=\"", "\"");
		pp.addAreaUnit("item", ParserConst.PROP_PRODUCTNAME, "title=\"", "\"");
		pp.addAreaUnit("item", ParserConst.PROP_PRICE,
				"<div class=\"col price\">", "<em>");
		pp.addAreaUnit("item", ParserConst.PROP_FREIGHT,
				"<div class=\"col end shipping\">", "</div>");
		pp.addAreaUnit("item", ParserConst.PROP_BARGAINCOUNT,
				"<div class=\"col dealing\">", "</div>");
		pp.addAreaUnit("item", ParserConst.PROP_COMMENTCOUNT,
				"target=\"_blank\">", "</a>");
		pp.addAreaUnit("item", ParserConst.PROP_SHOPKEEPERURL,
				" target=\"_blank\" href=\"", "\"");
		pp.addAreaUnit("item", ParserConst.PROP_SHOPKEEPER, ">", "</a>");
		pp.addAreaUnit("item", ParserConst.PROP_LOCATION,
				"<div class=\"col end loc\">", "</div>");
		pp.addAreaUnit("item", ParserConst.PROP_ISTAMLL,
				"<span class=\"icon-pit icon-service-tianmao", "</span>");

		p.setPattern(pp);
	}

	/**
	 * @param page
	 * @return
	 */
	public List<BaseProduct> downAndParse(String rule, int page) {
		String szHtml = taobao.getResultPage(rule, page);
//		 try {
//		 byte[] buff = new byte[] {};
//		 FileOutputStream output = new FileOutputStream("D://taobao.html");
//		 buff = szHtml.getBytes();
//		 output.write(buff, 0, buff.length);
//		 output.close();
//		 } catch (Exception e) {
//		 e.printStackTrace();
//		 }
		if (szHtml == null) {
			return null;
		}
		//只包含数字的查询 用第一种解析就行
		List<BaseProduct> lbp = p.parse(szHtml, Const.QUERY_ONLY_NUMBER);
		if (lbp == null || lbp.size() == 0) {
			logger.warn("淘宝网数据解释失败，Please Mail TO CTO：kansuny@163.com");
			return null;
		}

//		if (lbp == null || lbp.size() == 0) {
//			logger.warn("第一种解析方式失败，启用第二种解析程序");
//			p = new Parser();
//
//			ParsePattern pp = new ParsePattern();
//
//			pp.addArea("item2", "><li class=\"list-item\"",
//					"</li><li class=\"list-item");
//
//			pp.addAreaUnit("item2", ParserConst.PROP_PRODUCTNAME, "title=\"",
//					"\"");
//			pp.addAreaUnit("item2", ParserConst.PROP_PRODUCTURL, "href=\"",
//					"\"");
//			pp.addAreaUnit("item2", ParserConst.PROP_IMAGEURL,
//					"<img data-ks-lazyload=\"", "\"");
//			pp.addAreaUnit("item2", ParserConst.PROP_PRICE, "><em>", "</em>");
//			pp.addAreaUnit("item2", ParserConst.PROP_BARGAINCOUNT, "<span >",
//					"</span></li>");
////			pp.addAreaUnit("item", ParserConst.PROP_COMMENTCOUNT,
////					"class=\"comment\">", "</a>");
//			pp.addAreaUnit("item2", ParserConst.PROP_FREIGHT,
//					"<span class=\"fee\">", "</span>");
//			pp.addAreaUnit("item2", ParserConst.PROP_LOCATION,
//					"<span class=\"loc\">", "</span></li>");
//			pp.addAreaUnit("item2", ParserConst.PROP_SHOPKEEPERURL, "href=\"",
//					"\"");
//			pp.addAreaUnit("item2", ParserConst.PROP_SHOPKEEPER, ">", "</a>");
//			pp.addAreaUnit("item2", ParserConst.PROP_ISTAMLL, "<a class=\"mall-icon", "</a>");
//
//			p.setPattern(pp);
//			List<BaseProduct> lbp2 = p.parse(szHtml, Const.QUERY_WITH_UTF);
//			if (lbp2 == null || lbp2.size() == 0) {
//				logger.error("第二种解析程序也不管用了，Please Mail TO CTO：kansuny@163.com");
//				return null;
//			}
//			return lbp2;
//		}
		return lbp;
	}

}
