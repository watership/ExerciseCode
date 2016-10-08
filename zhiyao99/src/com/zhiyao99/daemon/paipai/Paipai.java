/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.paipai;

import java.io.FileOutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhiyao99.daemon.bean.BaseProduct;
import com.zhiyao99.daemon.conf.Const;
import com.zhiyao99.daemon.crawler.SearchEnginePaipai;
import com.zhiyao99.daemon.crawler.parser.ParsePattern;
import com.zhiyao99.daemon.crawler.parser.Parser;
import com.zhiyao99.daemon.crawler.parser.ParserConst;
import com.zhiyao99.daemon.crawler.parser.ParserForPaipai;

/**
 * <p>名称:com.zhiyao99.daemon.paipai.Paipai.java</p>
 * <p>描述:</p>
 * <p>日期:2013-5-5 上午06:36:20</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class Paipai {
	
	private static Logger logger = LoggerFactory.getLogger(Paipai.class);

	private SearchEnginePaipai paipai = new SearchEnginePaipai();

	private ParserForPaipai p = null;
	
	public Paipai(){
		p = new ParserForPaipai();

		ParsePattern pp = new ParsePattern();
		pp.addArea("item","<div class=\"item-show","</a></p>");
		pp.addAreaUnit("item",ParserConst.PROP_PRODUCTURL,"href=\"","\"");
		pp.addAreaUnit("item",ParserConst.PROP_IMAGEURL,"<img init_src=\"","\"");
		pp.addAreaUnit("item",ParserConst.PROP_PRODUCTNAME,"itemprop=\"name\" title=\"","\"");
		pp.addAreaUnit("item",ParserConst.PROP_PRICE,"<em class=\"pp_price\">","</em>");
		pp.addAreaUnit("item",ParserConst.PROP_FREIGHT,"<ins>","</ins>");
		pp.addAreaUnit("item",ParserConst.PROP_QQ,"<a href=\"#h\" uin=\"","\"");
		pp.addAreaUnit("item",ParserConst.PROP_BARGAINCOUNT,"<span class=\"total \">","</span>");
		pp.addAreaUnit("item",ParserConst.PROP_SHOPKEEPER,"<p><span>","</span>");
		pp.addAreaUnit("item",ParserConst.PROP_SHOPKEEPERURL,"<a href=\"","\"");
		p.setPattern(pp);
	}
	/**
	 * @param string
	 * @param i
	 * @return
	 */
	public List<BaseProduct> downAndParse(String rule, int page) {
		String szHtml = paipai.getResultPage(rule, page);
//		 try {
//		 byte[] buff = new byte[] {};
//		 FileOutputStream output = new FileOutputStream("D://paipai.html");
//		 buff = szHtml.getBytes();
//		 output.write(buff, 0, buff.length);
//		 output.close();
//		 } catch (Exception e) {
//		 e.printStackTrace();
//		 }
		if (szHtml == null) {
			return null;
		}
		List<BaseProduct> lbp = p.parse(szHtml);
		if(lbp == null || lbp.size() == 0){
			logger.warn("拍拍网数据解释失败，Please Mail TO CTO：kansuny@163.com");
			return null;
		}
		return lbp;
	}

}
