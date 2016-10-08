/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.msg.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>名称:com.zhiyao99.daemon.msg.bean.UnstructedMsg4MS.java</p>
 * <p>描述:商品爬取记录实体类</p>
 * <p>日期:2013-4-30 下午01:48:12</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class UnstructedMsg4MS implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7939790470346669131L;
	
	/** 编号*/
	private int id;
	/** 来源网站编号*/
	private int siteId;
	/** 来源网站名称*/
	private String siteName;
	/** 商品名称*/
	private String productName;
	/** 商品网址*/
	private String productUrl;
	
	/** 商品指纹码 使用URL生成*/
	private String productMd5;
	/** 内容指纹码 价格+评论+邮费+销售量 生成*/
	private String contentMd5;
	/** 第一次爬取日期*/
	private Date crawlDate;
	/** 最后一次更新日期*/
	private Date updateDate;
	
	@Override
	public String toString() {
		return "UnstructedMsg4MS [siteName=" + siteName
				+ ", productName=" + productName + ", productUrl=" + productUrl + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public String getProductMd5() {
		return productMd5;
	}
	public void setProductMd5(String productMd5) {
		this.productMd5 = productMd5;
	}
	public String getContentMd5() {
		return contentMd5;
	}
	public void setContentMd5(String contentMd5) {
		this.contentMd5 = contentMd5;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	

}
