/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.crawler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhiyao99.daemon.bean.BaseProduct;
import com.zhiyao99.daemon.conf.Const;
import com.zhiyao99.daemon.http.HttpSimpleDownloader;
import com.zhiyao99.daemon.taobao.Taobao;
import com.zhiyao99.daemon.util.Encoder;

/**
 * <p>
 * 名称:com.zhiyao99.daemon.crawler.SearchEngineTaobao.java
 * </p>
 * <p>
 * 描述:
 * </p>
 * <p>
 * 日期:2013-4-24 下午04:42:41
 * </p>
 * 
 * @author wangxw
 * @version 1.0
 * 
 */
public class SearchEngineTaobao {

	private static Logger logger = LoggerFactory
			.getLogger(SearchEngineTaobao.class);

	private HttpSimpleDownloader httpDowner = new HttpSimpleDownloader();

	public static void main(String[] args) {
		Taobao tb = new Taobao();
		List<BaseProduct> lbp = tb.downAndParse("9.9包邮", 1);
		if (lbp == null || lbp.size() == 0) {

		} else {
			System.out.println("总共：" + lbp.size());
			for (BaseProduct bp : lbp) {
				System.out.println(bp.toString());
			}
		}
		// System.out.println(taobao.makeURL("9.9 包邮", 1));

	}

	/**
	 * 获取搜索结果页面
	 * 
	 * @param rule
	 *            查询条件
	 * @param page
	 *            页数
	 * @return 返回搜索结果页面字符串
	 */
	public String getResultPage(String rule, int page) {
		String szUrl = makeURL(rule, page);
		logger.info("[taobao request URL]: " + szUrl);
		if (szUrl == null) {
			return null;
		}
		String szHtml = httpDowner.downloadHtmlString(szUrl);
		return szHtml;
	}

	/**
	 * 拼接查询的URL
	 * 
	 * @param rule
	 *            规则
	 * @param page
	 *            页数
	 * @return 返回拼接的URL
	 */
	private String makeURL(String rule, int page) {
		boolean isUTFQuery  = findQueryType(rule);
		StringBuffer sb = new StringBuffer();
		if(isUTFQuery){
			//第一页
			//http://s.taobao.com/search?q=9.9&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&initiative_id=tbindexz_20130504
			//第二页
			//http://s.taobao.com/search?promote=0&initiative_id=tbindexz_20130504&tab=all&q=9.9&bcoffset=1&s=40#J_relative
			//从第二页跳到第一页
			//http://s.taobao.com/search?promote=0&initiative_id=tbindexz_20130504&tab=all&q=9.9&s=0#J_relative
			sb.append("http://s.taobao.com/search?");
			sb.append("q=");
			sb.append(makeKeyWords(rule));
			sb.append("&tab=all&promote=0&initiative_id=tbindexz_");
			sb.append(nowDate());
			if (page > 0) {
				sb.append("&s=" + (Const.QUERY_ONLY_NUMBER_PAGE * (page -1))
						+ "#J_relative");
			}
		}else{
			//第一页
			//http://s.taobao.com/search?q=9.9%B0%FC%D3%CA&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&initiative_id=tbindexz_20130504
			//第二页
			//http://s.taobao.com/search?spm=a230r.1.10.397.udh2QI&q=9.9%B0%FC%D3%CA&initiative_id=tbindexz_20130504&commend=all&ssid=s5-e&newpre=null&s=44#J_FilterTabBar
			sb.append("http://s.taobao.com/search?");
			sb.append("q=");
			sb.append(makeKeyWords(rule));
			sb.append("&commend=all&ssid=s5-e&newpre=null&initiative_id=tbindexz_");
			sb.append(nowDate());
			if (page > 0) {
				sb.append("&s=" + (Const.QUERY_WITH_UTF_PAGE * (page -1))
						+ "#J_FilterTabBar");
			}
			
		}
		
		return sb.toString();
	}
	

	/**
	 * 查找关键词中是否只包含数字
	 * @param rule   关键词
	 * @return       true:纯数字搜索 false:其他搜索
	 */
	private boolean findQueryType(String rule) {
		boolean isNumQuery = false;
		Pattern p = Pattern.compile("^\\d.\\d$");
		Matcher match = p.matcher(rule);
		if(match.find()){
			isNumQuery = true;
		}
		return isNumQuery;
	}

	/**
	 * 拼接关键词查询条件
	 * 
	 * @param rule
	 * @return
	 */
	private String makeKeyWords(String rule) {
		StringBuilder key = new StringBuilder();
		if (rule.indexOf(" ") > 0) {
			String[] array = rule.split(" ");
			int i = 0;
			while (i < array.length) {
				if (array[i].trim().length() > 0) {
					key.append(Encoder.UTF82Hex(array[i], "gb2312"));
					if (i < array.length - 1) {
						key.append("+");
					}
				}
				i++;
			}
			return key.toString();
		} else {
			key.append(Encoder.UTF82Hex(rule, "gb2312"));
			return key.toString();
		}
	}

	/**
	 * 返回当前日期
	 * 
	 * @return
	 */
	private String nowDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(new Date());
	}

}
