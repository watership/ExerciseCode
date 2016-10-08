/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhiyao99.daemon.bean.BaseProduct;
import com.zhiyao99.daemon.msg.bean.UnstructedMsg4MS;
import com.zhiyao99.daemon.msg.ms.UtMS;
import com.zhiyao99.daemon.paipai.Paipai;
import com.zhiyao99.daemon.taobao.Taobao;
import com.zhiyao99.daemon.util.DateTools;

/**
 * 
 * <p>
 * 名称:com.zhiyao99.daemon.taobao.Manager.java
 * </p>
 * <p>
 * 描述:
 * </p>
 * <p>
 * 日期:2013-4-23 下午04:12:53
 * </p>
 * 
 * @author wangxw
 * @version 1.0
 * 
 */
public class Manager {

	private static Logger logger = LoggerFactory.getLogger(Manager.class);

	private static final int SLEEP_INTERVAL = 3000;
	
	private static final int sleepInterval = 60000;	// 1 minute

	private static final int LOOP_PAGE = 50;
	
	private UtMS utms = new UtMS();
	
	private static int updateCount = 0;
	private static int addCount = 0;

	public static void main(String[] args) {
		Manager mng = new Manager();
		mng.run();
	}

	/**
	 * 开始爬取信息
	 */
	private void run() {

		while (true) {
			
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);;
			
			if(hour == 0 || hour == 8 || hour == 12 || hour == 18 || hour > 0){
				Date startTime = new Date(System.currentTimeMillis());
				//List<BaseProduct> result = new LinkedList<BaseProduct>();
				List<BaseProduct> ltb = null;
				for (int page = LOOP_PAGE; page >=1; page--) {
//					addResult(result, ltb);
					ltb = paipai("9.9 包邮", page);
					//addResult(result, ltb);
					insertOrUpdate(ltb);
					ltb = taobao("9.9 包邮", page);
					insertOrUpdate(ltb);
				}

//				insertOrUpdate(result);

				Date endTime = new Date(System.currentTimeMillis());
				logger.info("START TIME: " + startTime);
				logger.info("END TIME: " + endTime);
				logger.info("Elapse: "
						+ DateTools.getRelativeDateTime(endTime, startTime));

				for (int i = 0; i < 1200; i++) {
					sleepCurrentThread();
				}
			}else{
				sleepAwhile();
				continue;
			}
			
			
		}
	}

	/**
	 * 线程休息
	 */
	private void sleepAwhile() {
		try 
		{
			Thread.sleep(sleepInterval);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拍拍
	 * @param string
	 * @param page
	 * @return
	 */
	private List<BaseProduct> paipai(String rule, int page) {
		List<BaseProduct> ltb = new LinkedList<BaseProduct>();
		Paipai paipai = new Paipai();
		ltb = paipai.downAndParse(rule, page);
		if (ltb.size() > 0) {
			return ltb;
		}
		return null;
	}

	/**
	 * 存储商品信息到数据库
	 * 
	 * @param result
	 *            商品信息集合
	 */
	private void insertOrUpdate(List<BaseProduct> result) {

		if (result == null || result.size() == 0) {
			return;
		}

		for (int i = 0; i < result.size(); i++) {
			BaseProduct bp = result.get(i);
			UnstructedMsg4MS ut4m = utms.findByUrl(bp.getProductUrl());
			if(ut4m != null){
				updateCount++;
				if(utms.needChage(ut4m,bp)){
					utms.update(bp);
					logger.info("["+updateCount+"] Md5["+ut4m.getProductMd5()+"]Need Update:"+ ut4m.toString());
				}else{
					logger.info("["+updateCount+"] Md5["+ut4m.getProductMd5()+"] No Need to Update");
				}
			}else{
				addCount++;
				logger.info("["+addCount+"]Need Add:"+ bp);
				utms.add(bp);
			}
		}

	}


	/**
	 * 当前线程休息
	 */
	private void sleepCurrentThread() {
		try {
			Thread.sleep(SLEEP_INTERVAL);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加爬取的商品数据到集合中
	 * 
	 * @param result
	 *            总数据集合
	 * @param ltb
	 *            爬虫获取的数据
	 */
	private void addResult(List<BaseProduct> result, List<BaseProduct> ltb) {
		if (result == null || ltb == null ) {
			return;
		}
		result.addAll(result.size(), ltb);
	}

	/**
	 * 获取淘宝数据
	 * 
	 * @param string
	 * @return
	 */
	private List<BaseProduct> taobao(String rule, int page) {
		List<BaseProduct> ltb = new LinkedList<BaseProduct>();
		Taobao taobao = new Taobao();
		ltb = taobao.downAndParse(rule, page);
		if (ltb.size() > 0) {
			return ltb;
		}
		return null;
	}

}
