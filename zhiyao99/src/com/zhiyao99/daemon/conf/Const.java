/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.conf;

/**
 * <p>名称:com.zhiyao99.daemon.conf.Const.java</p>
 * <p>描述:系统常量类</p>
 * <p>日期:2013-4-30 下午03:04:11</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class Const {
	
	/** 网站最大长度*/
	public static final int maxlength_url	= 1024;
	
	/** 爬取记录表*/
	public static final String TB_PREFIX_UT = "zy_ut_";
	/** 商品表*/
	public static final String TB_PRODUCT = "zy_product";
	/** 在售商品表*/
	public static final String TB_ONSALE = "zy_onsale";
	/** 淘宝网站*/
	public static final String WEBSITE_TAOBAO = "淘宝";
	/** 淘宝网站编号*/
	public static final int WEBSITE_TAOBAO_ID = 1;
	/** 天猫商城*/
	public static final String WEBSITE_TAMLL = "天猫";
	/** 天猫网站编号*/
	public static final int WEBSITE_TAMLL_ID = 2;
	/** 拍拍商城*/
	public static final String WEBSITE_PAIPAI = "拍拍";
	/** 拍拍网编号*/
	public static final int WEBSITE_PAIPAI_ID = 3;
	/** 淘宝商品单页数量*/
	public static final int QUERY_ONLY_NUMBER_PAGE = 40;
	public static final int QUERY_WITH_UTF_PAGE = 44;
	
	/** 拍拍商品单页数量*/
	public static final int QUERY_PAIPAI_PAGE = 48;
	
	public static final int QUERY_ONLY_NUMBER = 1;
	public static final int QUERY_WITH_UTF = 2;

}
