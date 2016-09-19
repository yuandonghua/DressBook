/*
 * Copyright by Gifted Youngs Workshop and the original author or authors.
 * 
 * This document only allow internal use , any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Gifted Youngs Workshop from
 * 
 * giftedyoungs@163.com
 * 
 *
 * 此代码由天才少年工作小组开发完成, 仅限内部使用 
 * 外部使用该代码将负相应的法律责任
 * 更多信息请致信天才少年工作小组
 * 
 * giftedyoungs@163.com
 *
 */
package cn.dressbook.ui.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-15 下午5:32:35
 * @since
 * @version
 */
@SuppressLint("SimpleDateFormat")
public class DateTimeUtils {
	/**
	 * 
	 * TODO 转换string成时间格式
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:20:48
	 * @param dateTime
	 * @return
	 * @see
	 */
	public static final String toDateMMddHHmm(String dateTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		SimpleDateFormat df1 = new SimpleDateFormat("MM-dd HH:mm");
		Date d;
		try {
			d = df.parse(dateTime);
			return df1.format(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * TODO 转换string成时间格式
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:20:48
	 * @param dateTime
	 * @return
	 * @see
	 */
	public static final String toDateTimeHHmm(String dateTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
		Date d;
		try {
			d = df.parse(dateTime);
			return df1.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * TODO 转换string成时间格式
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:20:48
	 * @param dateTime
	 * @return
	 * @see
	 */
	public static final String toDateTimeMMdd(String dateTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		SimpleDateFormat df1 = new SimpleDateFormat("MM/dd");
		Date d;
		try {
			d = df.parse(dateTime);
			return df1.format(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * TODO 转换string成时间格式
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:20:48
	 * @param dateTime
	 * @return
	 * @see
	 */
	public static final String toDateTimeMMddHHmm(String dateTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		SimpleDateFormat df1 = new SimpleDateFormat("MM.dd HH:mm");
		Date d;
		try {
			d = df.parse(dateTime);
			return df1.format(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * TODO 转换string成时间格式
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:20:48
	 * @param dateTime
	 * @return
	 * @see
	 */
	public static final String toDateTimeyyyyMMddHHmmss(String str) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		try {
			Date d = df.parse(str);
			return df1.format(d);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * TODO 转换string成时间格式
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:20:48
	 * @param dateTime
	 * @return
	 * @see
	 */
	public static final String toDateTimeyyyyMMddHHmmssChinese(String dateTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		SimpleDateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		Date d;
		try {
			d = df.parse(dateTime);
			return df1.format(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * TODO 得到目前时间精确到月份
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:13:02
	 * @return
	 * @see
	 */
	public static final String getCurrentMonth() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");// 设置日期格式
		return df.format(new Date());
	}

	/**
	 * 
	 * TODO 得到目前时间精确到天
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:13:02
	 * @return
	 * @see
	 */
	public static final String getCurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		return df.format(new Date());
	}

	/**
	 * 
	 * TODO 得到目前时间精确到秒
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:13:02
	 * @return
	 * @see
	 */
	public static final String getCurrentDateTimeSecond() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		return df.format(new Date());
	}

	/**
	 * 
	 * TODO 由生日日期得到年纪
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:08:35
	 * @param 生日日期
	 *            ，格式为yyyymmdd
	 * @return
	 * @see
	 */
	public static final int getAge(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 得到当前的年份
		String cYear = sdf.format(new Date()).substring(0, 4);
		// 得到生日年份
		String birthYear = dateStr.substring(0, 4);
		int age = Integer.parseInt(cYear) - Integer.parseInt(birthYear);
		return age;
	}

	/**
	 * 
	 * TODO 计算两个日期之间相差了多少天
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:07:20
	 * @param str1
	 *            较小日期，格式为yyyyMMdd
	 * @param str2
	 *            较大日期，格式为yyyyMMdd
	 * @return
	 * @throws Exception
	 * @see
	 */
	public static long getDistanceDays(String str1, String str2)
			throws Exception {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date one;
		Date two;
		long days = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			days = diff / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 
	 * TODO 根据当前时间和传入的天数加减算出结果的日期时间
	 * 
	 * @author LiShen
	 * @date 2015-6-29 下午3:22:56
	 * @param 加减的天数
	 * @return
	 * @see
	 */
	public static String dateChange(int days) {
		Calendar canlendar = Calendar.getInstance();
		canlendar.add(Calendar.DATE, days);
		Date date = canlendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(date);
	}
}
