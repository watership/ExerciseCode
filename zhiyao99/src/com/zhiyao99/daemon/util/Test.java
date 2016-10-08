/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>名称:com.zhiyao99.daemon.util.Test.java</p>
 * <p>描述:</p>
 * <p>日期:2013-5-1 下午11:09:39</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class Test {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		
		List<String> list2 = new ArrayList<String>();
		list2.add("4");
		list2.add("5");
		list2.add("6");
		
		list.addAll(list.size()-1, list2);
		for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i));
		}
		
	}

}
