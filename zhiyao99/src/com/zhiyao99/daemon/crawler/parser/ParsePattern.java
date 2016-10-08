/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.crawler.parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>名称:com.zhiyao99.daemon.crawler.parse.ParsePattern.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-25 下午08:01:47</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class ParsePattern {
	
	private Map<String, ParseArea> areas = new HashMap<String, ParseArea>(1);
	
	private String startStr = null;
	
	private String predefinedSrcUrl = null;
	
	public String getPredefinedSrcUrl() {
		return predefinedSrcUrl;
	}

	public void setPredefinedSrcUrl(String predefinedSrcUrl) {
		this.predefinedSrcUrl = predefinedSrcUrl;
	}
	
	public int getAreaCount() {
		return areas.size();
	}

	public void setStartStr(String startStr) {
		this.startStr = startStr;
	}

	public String getStartStr() {
		return startStr;
	}
	
	public List<ParseArea> getParseAreas() {
		List<ParseArea> lpa = new LinkedList<ParseArea>();

		Collection<ParseArea> cpa = areas.values();
		Iterator<ParseArea> ipa = cpa.iterator();
		while (ipa.hasNext()) {
			ParseArea pa = ipa.next();
			lpa.add(pa);
		}

		return lpa;
	}

	public boolean addArea(String name, String beginStr, String endStr) {
		boolean isAdded = false;

		if (name == null || name.trim().length() == 0 || beginStr == null
				|| beginStr.trim().length() == 0 || endStr == null
				|| endStr.trim().length() == 0) {
			return isAdded;
		}

		ParseArea pa = new ParseArea(name, beginStr, endStr);
		areas.put(name, pa);

		isAdded = true;
		return isAdded;
	}

	public boolean addAreaUnit(String areaName, String unitName,
			String beginStr, String endStr) {
		boolean isAdded = false;

		ParseArea pa = areas.get(areaName);
		if (pa == null) {
			return isAdded;
		}

		if (unitName == null || unitName.trim().length() == 0
				|| beginStr == null || endStr == null) {
			return isAdded;
		}

		ParseAreaUnit pau = new ParseAreaUnit(unitName, beginStr, endStr);
		pa.addAreaUnit(pau);

		isAdded = true;
		return isAdded;
	}

}
