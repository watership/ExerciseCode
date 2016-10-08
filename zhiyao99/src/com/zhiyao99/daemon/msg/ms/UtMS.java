/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.msg.ms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhiyao99.daemon.bean.BaseProduct;
import com.zhiyao99.daemon.conf.Const;
import com.zhiyao99.daemon.http.UrlPackage;
import com.zhiyao99.daemon.http.UrlPacker;
import com.zhiyao99.daemon.msg.bean.UnstructedMsg4MS;
import com.zhiyao99.daemon.util.DBHelper;
import com.zhiyao99.daemon.util.MD5;

/**
 * <p>名称:com.zhiyao99.daemon.msg.ms.UtMS.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-30 下午01:36:59</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class UtMS {
	
	private static Logger logger = LoggerFactory.getLogger(UtMS.class);
	
	public UnstructedMsg4MS findByUrl(String url){
		UrlPackage up = UrlPacker.pack(url);
		if (up == null || up.getUrlMd5() == null)
			return null;

		return findByMd5(new String(up.getUrlMd5()));
	}

	/**
	 * @param urlmd5
	 * @return
	 */
	private UnstructedMsg4MS findByMd5(String urlmd5) {
		UnstructedMsg4MS um = null;
		if (urlmd5 == null)
			return um;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String tableName = Const.TB_PREFIX_UT + urlmd5.substring(0, 2);
		String szSQL = "SELECT * FROM " + tableName +" WHERE productMd5=?";
		try{
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(szSQL);
			pstmt.setString(1, urlmd5);
			rs = pstmt.executeQuery();
			if(rs.next()){
				um = new UnstructedMsg4MS();
				um.setId(rs.getInt("id"));
				um.setProductMd5(urlmd5);
				um.setContentMd5(rs.getString("contentMd5"));
				um.setProductName(rs.getString("productName"));
				um.setProductUrl(rs.getString("productUrl"));
				um.setSiteId(rs.getInt("siteId"));
				um.setSiteName(rs.getString("siteName"));
				um.setCrawlDate(rs.getTimestamp("crawlDate"));
				um.setUpdateDate(rs.getTimestamp("updateDate"));
			}
		}catch(SQLException e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			DBHelper.close(pstmt, conn);
		}
		return um;
	}
	
	/**
	 * 
	 * @param bp
	 */
	public void add(BaseProduct bp){
		Connection conn = null;
		PreparedStatement pstmt = null;
		String tableName = Const.TB_PREFIX_UT + bp.getProductMd5().substring(0, 2);
		String newProductMd5 = getNewContentMd5(bp);
		
		String szSQL = "INSERT INTO " + Const.TB_PRODUCT + "(productMd5,productName,productUrl,imageUrl,price" +
				",freight,isFreeSend,shopkeeper,shopkeeperUrl,location,bargainCount,commentCount,isOnSale" +
				",siteId,siteName,crawlDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String utSQL = "INSERT INTO " + tableName + "(productName,productUrl,siteId,siteName,productMd5,contentMd5," +
				"crawlDate,updateDate) VALUES (?,?,?,?,?,?,?,?)";
//		logger.info("Insert to Product:"+szSQL);
//		logger.info("Insert to UT:"+utSQL);
		try{
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(szSQL);
			pstmt.setString(1, bp.getProductMd5());
			pstmt.setString(2, bp.getProductName());
			pstmt.setString(3, bp.getProductUrl());
			pstmt.setString(4, bp.getImageUrl());
			pstmt.setDouble(5, bp.getPrice());
			pstmt.setDouble(6, bp.getFreight());
			pstmt.setInt(7, bp.isFreeSend() ? 1:0);
			pstmt.setString(8, bp.getShopkeeper());
			pstmt.setString(9, bp.getShopkeeperUrl());
			pstmt.setString(10, bp.getLocation());
			pstmt.setInt(11, bp.getBargainCount());
			pstmt.setInt(12, bp.getCommentCount());
			pstmt.setInt(13, 1);//默认是正在销售
			pstmt.setInt(14, bp.getSiteId());
			pstmt.setString(15, bp.getSiteName());
			pstmt.setTimestamp(16, new Timestamp(bp.getCrawlDate().getTime()));
			pstmt.executeUpdate();
		
			pstmt = conn.prepareStatement(utSQL);
			pstmt.setString(1, bp.getProductName());
			pstmt.setString(2, bp.getProductUrl());
			pstmt.setInt(3, bp.getSiteId());
			pstmt.setString(4, bp.getSiteName());
			pstmt.setString(5, bp.getProductMd5());
			pstmt.setString(6, newProductMd5);
			pstmt.setTimestamp(7, new Timestamp(bp.getCrawlDate().getTime()));
			pstmt.setTimestamp(8, new Timestamp(bp.getCrawlDate().getTime()));
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			DBHelper.close(pstmt, conn);
		}
		
		
	}

	/**
	 * 商品的信息是否已经发生了变化
	 * 使用成交数量+评论数量+价格+运费+是否免费生成的指纹码
	 * @param ut4m  数据库读出的商品快照
	 * @param bp    新爬取的商品信息
	 */
	public boolean needChage(UnstructedMsg4MS ut4m, BaseProduct bp) {
		boolean isChanged = false;
		String contentMd5 = ut4m.getContentMd5();
		String newProductMd5 = getNewContentMd5(bp);
		if(!newProductMd5.equals(contentMd5)){
			isChanged = true;
		}
		
		return isChanged;
		
	}

	/**
	 * @param bp
	 * @return
	 */
	private String getNewContentMd5(BaseProduct bp) {
		StringBuffer productSB = new StringBuffer();
		productSB.append(bp.getProductName());
		productSB.append(bp.getBargainCount());
		productSB.append(bp.getCommentCount());
		productSB.append(String.valueOf(bp.getPrice()));
		productSB.append(String.valueOf(bp.getFreight()));
		productSB.append(bp.isFreeSend());
		String newProductMd5 = new String(MD5.EncodeByMd5(productSB.toString()));
		return newProductMd5;
	}

	/**
	 * @param bp
	 */
	public void update(BaseProduct bp) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String tableName = Const.TB_PREFIX_UT + bp.getProductMd5().substring(0, 2);
		String newProductMd5 = getNewContentMd5(bp);
		//String productMd5 = bp.getProductMd5();
		String szSQL = "UPDATE "+tableName + " SET productName=?,contentMd5=?,updateDate=? WHERE productMd5=?";
		String upSQL = "UPDATE " + Const.TB_PRODUCT + " SET productName=?,price=?,freight=?,isFreeSend=?,bargainCount=?,commentCount=?,crawlDate=? WHERE productMd5=?";
//		logger.info(upSQL);
		String onsaleSQL = "SELECT COUNT(*) FROM " + Const.TB_ONSALE + " WHERE productMd5=?";
		boolean isExistOnSale = false;
		String onsaleSQL2 = "UPDATE " + Const.TB_ONSALE + " SET productName=?,price=?,freight=?,isFreeSend=?,bargainCount=? WHERE productMd5=?";

		try{
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(szSQL);
			pstmt.setString(1, bp.getProductName());
			pstmt.setString(2, newProductMd5);
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(4, bp.getProductMd5());
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(upSQL);
			pstmt.setString(1, bp.getProductName());
			pstmt.setDouble(2, bp.getPrice());
			pstmt.setDouble(3, bp.getFreight());
			pstmt.setInt(4, bp.isFreeSend() ? 1:0);
			pstmt.setInt(5, bp.getBargainCount());
			pstmt.setInt(6, bp.getCommentCount());
			pstmt.setTimestamp(7, new Timestamp(bp.getCrawlDate().getTime()));
			pstmt.setString(8, bp.getProductMd5());
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(onsaleSQL);
			pstmt.setString(1, bp.getProductMd5());
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getInt(1) > 0){
					isExistOnSale = true;
				}
			}
			if(isExistOnSale){
				pstmt = conn.prepareStatement(onsaleSQL2);
				pstmt.setString(1, bp.getProductName());
				pstmt.setDouble(2, bp.getPrice());
				pstmt.setDouble(3, bp.getFreight());
				pstmt.setInt(4, bp.isFreeSend() ? 1:0);
				pstmt.setInt(5, bp.getBargainCount());
				pstmt.setString(6, bp.getProductMd5());
				pstmt.executeUpdate();
			}
			
		}catch(SQLException e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			DBHelper.close(pstmt, conn);
		}
		
	}
	
	
	

}
