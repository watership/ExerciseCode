/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.crawler.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>名称:com.zhiyao99.daemon.crawler.parse.ParseArea.java</p>
 * <p>描述:解析单元实体类</p>
 * <p>日期:2013-4-25 下午08:02:42</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class ParseArea {
	
	private String name = null;
	private String beginStr = null;
	private String endStr = null;
	private List<ParseAreaUnit> lpau = new LinkedList<ParseAreaUnit>();
	
	public ParseArea(String name, String beginStr, String endStr) {
		this.name = name;
		this.beginStr = beginStr;
		this.endStr = endStr;
	}
	
	public boolean addAreaUnit(ParseAreaUnit pau) {
		boolean isAdded = false;

		if (pau == null) {
			return isAdded;
		}

		lpau.add(pau);

		isAdded = true;
		return isAdded;
	}

	public String getName() {
		return name;
	}

	public String getBeginStr() {
		return beginStr;
	}

	public void setEndStr(String endStr) {
		this.endStr = endStr;
	}

	public String getEndStr() {
		return endStr;
	}

	public List<ParseAreaUnit> getAreaUnits() {
		return lpau;
	}

}
