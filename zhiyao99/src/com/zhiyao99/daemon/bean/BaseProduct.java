/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>名称:com.zhiyao99.daemon.bean.BaseProduct.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-24 上午09:57:49</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class BaseProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7878615798351054237L;
	
	/** 商品编号*/
	private int id;
	/** 名称*/
	private String productName;
	/** 商品对应的URL*/
	private String productUrl;
	/** 图片URL*/
	private String imageUrl;
	/** 商品价格*/
	private double price;
	/** 运费*/
	private double freight;
	/** 是否免运费*/
	private boolean isFreeSend;
	/** 店主名称*/
	private String shopkeeper;
	/** 店主URL*/
	private String shopkeeperUrl;
	/** 商品所在位置*/
	private String location;
	/** 成交数量*/
	private int bargainCount;
	/** 评价数量*/
	private int commentCount;
	/** 是否在售*/
	private boolean isOnSale;
	
	/** 爬取日期*/
	private Date crawlDate;
	/** 更新日期*/
	private Date updateDate;
	/** 产品指纹码*/
	private String productMd5;
	/** 来自网站编号*/
	private int siteId;
	/** 来自网站*/
	private String siteName;
	
	
	
	@Override
	public String toString() {
		return "BaseProduct [id=" + id + ", productName=" + productName
				+ ", productUrl=" + productUrl + ", imageUrl=" + imageUrl
				+ ", price=" + price + ", freight=" + freight + ", isFreeSend="
				+ isFreeSend + ", shopkeeper=" + shopkeeper
				+ ", shopkeeperUrl=" + shopkeeperUrl + ", location=" + location
				+ ", bargainCount=" + bargainCount + ", commentCount="
				+ commentCount + ", isOnSale=" + isOnSale + ", crawlDate="
				+ crawlDate + ", updateDate=" + updateDate + ", productMd5="
				+ productMd5 + ", siteId=" + siteId + ", siteName=" + siteName
				+ "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public boolean isFreeSend() {
		return isFreeSend;
	}
	public void setFreeSend(boolean isFreeSend) {
		this.isFreeSend = isFreeSend;
	}
	public String getShopkeeper() {
		return shopkeeper;
	}
	public void setShopkeeper(String shopkeeper) {
		this.shopkeeper = shopkeeper;
	}
	public String getShopkeeperUrl() {
		return shopkeeperUrl;
	}
	public void setShopkeeperUrl(String shopkeeperUrl) {
		this.shopkeeperUrl = shopkeeperUrl;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getBargainCount() {
		return bargainCount;
	}
	public void setBargainCount(int bargainCount) {
		this.bargainCount = bargainCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public boolean isOnSale() {
		return isOnSale;
	}
	public void setOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
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
	public String getProductMd5() {
		return productMd5;
	}
	public void setProductMd5(String productMd5) {
		this.productMd5 = productMd5;
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

}
