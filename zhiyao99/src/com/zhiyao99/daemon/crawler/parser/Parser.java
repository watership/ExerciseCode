/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.crawler.parser;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhiyao99.daemon.bean.BaseProduct;
import com.zhiyao99.daemon.conf.Const;
import com.zhiyao99.daemon.http.UrlPackage;
import com.zhiyao99.daemon.http.UrlPacker;
import com.zhiyao99.daemon.util.MD5;
import com.zhiyao99.daemon.util.StringUtil;

/**
 * <p>
 * 名称:com.zhiyao99.daemon.crawler.Parser.java
 * </p>
 * <p>
 * 描述:
 * </p>
 * <p>
 * 日期:2013-4-25 下午07:52:22
 * </p>
 * 
 * @author wangxw
 * @version 1.0
 * 
 */
public class Parser {
	
	private static Logger logger = LoggerFactory.getLogger(Parser.class);

	private ParsePattern pp = null;

	public void setPattern(ParsePattern pp) {
		this.pp = pp;
	}

	public ParsePattern getPattern() {
		return pp;
	}

	/**
	 * 解析只有数字的查询结果
	 * @param szHtml
	 * @return
	 */
	public List<BaseProduct> parse(String szHtml, int type) {
		List<BaseProduct> lbp = new LinkedList<BaseProduct>();

		if (pp == null) {
			return lbp;
		}

		if (pp.getStartStr() != null && pp.getStartStr().length() > 0) {
			int n1 = szHtml.indexOf(pp.getStartStr());
			if (n1 >= 0) {
				szHtml = szHtml.substring(n1 + pp.getStartStr().length());
			}
		}

		List<ParseArea> lpa = pp.getParseAreas();
		Iterator<ParseArea> ipa = lpa.iterator();
		while (ipa.hasNext()) {
			ParseArea pa = ipa.next();
			lbp = parseArea(pa, new StringBuilder(szHtml),type);
		}

		return lbp;
	}
	/**
	 * @param pa
	 * @param type 
	 * @param stringBuilder
	 * @return
	 */
	private List<BaseProduct> parseArea(ParseArea pa, StringBuilder strBuff, int type) {
		List<BaseProduct> lbp = new LinkedList<BaseProduct>();
		String beginStr = pa.getBeginStr();
		String endStr = pa.getEndStr();
		List<ParseAreaUnit> lpau = pa.getAreaUnits();

		int n1 = strBuff.indexOf(beginStr);

		if (n1 < 0) {
			return lbp;
		}
		int n2 = strBuff.indexOf(endStr, n1 + beginStr.length());
		int total = 0;
		while (n1 >= 0 && n2 > 0) {
			total++;
			StringBuilder unitStrBuff = new StringBuilder(strBuff.substring(n1
					+ beginStr.length(), n2));

			BaseProduct bp = parseAreaUnit(unitStrBuff, lpau,type);

			if (bp != null) {
				lbp.add(bp);
			}

			strBuff.delete(0, n2);
			n1 = strBuff.indexOf(beginStr);
			if (n1 < 0) {
				break;
			}
			if(type == Const.QUERY_ONLY_NUMBER){
				if(total == (Const.QUERY_WITH_UTF_PAGE - 1)){
					n2 = strBuff.indexOf("<div class=\"tb-supplement tb-supplement-tbalink", n1 + beginStr.length());
				}else{
					n2 = strBuff.indexOf(endStr, n1 + beginStr.length());
				}
			}else if(type == Const.QUERY_WITH_UTF){
				if(total == (Const.QUERY_WITH_UTF_PAGE - 1)){
					n2 = strBuff.indexOf("<div class=\"tbaLink-bottom", n1 + beginStr.length());
				}else{
					n2 = strBuff.indexOf(endStr, n1 + beginStr.length());
				}
			}
		}

		return lbp;
	}
	

	/**
	 * @param unitStrBuff
	 * @param lpau
	 * @param type 
	 * @return
	 */
	private BaseProduct parseAreaUnit(StringBuilder strBuff,
			List<ParseAreaUnit> lpau, int type) {
		//商品是否为淘宝 否则为天猫
		boolean isTaoBao = false;
		BaseProduct bp = new BaseProduct();
		Iterator<ParseAreaUnit> ipau = lpau.iterator();
		ParseAreaUnit pau = null;
		String sz = null;
		int n1 = -1;
		int n2 = -1;
		try {
			while (ipau.hasNext()) {
				isTaoBao = false;
				pau = ipau.next();
				if (pau.getBeginStr().length() > 0) {
					n1 = strBuff.indexOf(pau.getBeginStr());
				} else {
					n1 = 0;
				}

				if (n1 < 0) {
					if(type==Const.QUERY_ONLY_NUMBER){
						if(pau.getBeginStr().equals("<span class=\"icon-pit icon-service-tianmao")){
							//找不到天猫标志 则说明是淘宝
							isTaoBao = true;
						}else{
							logger.error("Can't find this beginStr:" + pau.getBeginStr());
							return null;
						}
					}else{
						if(pau.getBeginStr().equals("<span class=\"icon-pit icon-service-tianmao")){
						//if(pau.getBeginStr().equals("<a class=\"mall-icon")){
							//找不到天猫标志 则说明是淘宝
							isTaoBao = true;
						}else{
							logger.error("Can't find this beginStr:" + pau.getBeginStr());
							return null;
						}
					}
					
				}
				n2 = strBuff.indexOf(pau.getEndStr(), n1
						+ pau.getBeginStr().length());
				if (n2 < 0) {
					logger.error("Can't find this endStr__" + pau.getEndStr());
					return null;
				}

				sz = strBuff.substring(n1 + pau.getBeginStr().length(), n2);
				// 构建商品对象
				//System.out.println("__" + sz.trim());
				constructByParseAreaUnit(pau, sz, bp);

				strBuff.delete(0, n2 + pau.getEndStr().length());
			}
		} catch (Exception e) {
			return null;
		}
		UrlPackage up = UrlPacker.pack(bp.getProductUrl());
		bp.setProductMd5(new String(up.getUrlMd5()));
		bp.setOnSale(true);
		if(!isTaoBao){
			bp.setSiteId(Const.WEBSITE_TAMLL_ID);
			bp.setSiteName(Const.WEBSITE_TAMLL);
		}else{
			bp.setSiteId(Const.WEBSITE_TAOBAO_ID);
			bp.setSiteName(Const.WEBSITE_TAOBAO);
		}
		bp.setCrawlDate(new Date());
		return bp;
	}

	/**
	 * @param pau
	 * @param bp
	 * @param sz
	 * @return
	 */
	private boolean constructByParseAreaUnit(ParseAreaUnit pau, String sz,
			BaseProduct bp) {
		boolean isTamll = false;
		String unit = pau.getName();
		sz = sz.trim();
		if (unit != null && !"".equals(unit)) {
			if (unit.equals(ParserConst.PROP_PRODUCTURL)) {
				bp.setProductUrl(sz);
			} else if (unit.equals(ParserConst.PROP_IMAGEURL)) {
				bp.setImageUrl(sz);
			} else if (unit.equals(ParserConst.PROP_PRODUCTNAME)) {
				bp.setProductName(sz);
			} else if (unit.equals(ParserConst.PROP_PRICE)) {
				bp.setPrice(StringUtil.getDoubleValue(sz));
			} else if (unit.equals(ParserConst.PROP_FREIGHT)) {
				double freight = -1;
				if (sz.contains("免")) {
					freight = 0.0;
					bp.setFreeSend(true);
				} else {
					freight = StringUtil.getDoubleValue(sz);
				}
				bp.setFreight(freight);
			} else if (unit.equals(ParserConst.PROP_BARGAINCOUNT)) {
				bp.setBargainCount(StringUtil.getIntValue(sz));
			} else if (unit.equals(ParserConst.PROP_COMMENTCOUNT)) {
				bp.setCommentCount(StringUtil.getIntValue(sz));
			} else if (unit.equals(ParserConst.PROP_SHOPKEEPER)) {
				bp.setShopkeeper(sz);
			} else if (unit.equals(ParserConst.PROP_SHOPKEEPERURL)) {
				bp.setShopkeeperUrl(sz);
			} else if (unit.equals(ParserConst.PROP_LOCATION)) {
				bp.setLocation(sz);
			}
		}
		return isTamll;
	}

}
