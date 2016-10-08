/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.bean;

import java.io.Serializable;

/**
 * <p>名称:com.zhiyao99.daemon.bean.WebSite.java</p>
 * <p>描述:网站实体类</p>
 * <p>日期:2013-4-24 上午10:14:45</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class WebSite implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5401734183676706604L;
	
	/** 网站编号*/
	private int id;
	/** 网站名称*/
	private String name;
	/** 网站URL*/
	private String url;
	/** 爬取URL入口*/
	private String startUrl;
	/** 域名*/
	private String domain;
	
	/**
	 * 打印站点信息
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("("+id+","+name+") ");
		sb.append("U:"+url+",E:"+startUrl);
		return sb.toString();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStartUrl() {
		return startUrl;
	}
	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}

}
