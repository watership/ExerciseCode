/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.crawler.parser;

/**
 * <p>名称:com.zhiyao99.daemon.crawler.parser.ParseAreaUnit.java</p>
 * <p>描述:解析最小单元实体类</p>
 * <p>日期:2013-4-25 下午08:10:12</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class ParseAreaUnit {
	
	/** */
	private String name = null;
	/** 开始字符串*/
	private String beginStr = null;
	/** 结束字符串*/
	private String endStr = null;
	
	
	/**
	 * 带所有参数的构造方法
	 * @param name
	 * @param beginStr
	 * @param endStr
	 */
	public ParseAreaUnit(String name, String beginStr, String endStr) {
		this.name = name;
		this.beginStr = beginStr;
		this.endStr = endStr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBeginStr() {
		return beginStr;
	}
	public void setBeginStr(String beginStr) {
		this.beginStr = beginStr;
	}
	public String getEndStr() {
		return endStr;
	}
	public void setEndStr(String endStr) {
		this.endStr = endStr;
	}

}
