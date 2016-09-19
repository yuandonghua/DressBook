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

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * TODO 字符工具类
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-15 下午5:35:51
 * @since
 * @version
 */
public class SU {

	/**
	 * TODO 判断是否为电子邮箱地址
	 * 
	 * @author LiShen
	 * @date 2015-6-15 上午11:48:26
	 * @see
	 */
	public static boolean isEmailAddress(String str) {
		String strPattern = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * TODO 判断是否为手机号码
	 * 
	 * @author LiShen
	 * @date 2015-6-15 上午11:48:26
	 * @see
	 */
	public static boolean isPhoneNumber(String str) {
		Pattern pattern = Pattern
				.compile("((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * TODO 判断是否为用户名
	 * 
	 * @author LiShen
	 * @date 2015-6-15 上午11:48:26
	 * @see
	 */
	public static boolean isUserame(String str) {
		Pattern pattern = Pattern.compile("[a-zA-z0-9\u4E00-\u9FA5]*");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * TODO 验证是否为6至16位的密码
	 * 
	 * @author LiShen
	 * @date 2015-6-15 下午12:02:51
	 * @see
	 */
	public static boolean isPassword(String str) {
		Pattern pattern = Pattern.compile("^[a-z0-9_-]{6,11}$");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * TODO 验证是否为货物单号
	 * 
	 * @author LiShen
	 * @date 2015-6-15 下午12:02:51
	 * @see
	 */
	public static boolean isGoodsNum(String str) {
		Pattern pattern = Pattern.compile("[a-zA-z0-9]*");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * TODO 非空判断
	 * 
	 * @author LiShen
	 * @date 2015-8-20 上午11:55:56
	 * @param s
	 * @return
	 * @see
	 */
	public static boolean n(String s) {
		if (s != null && s.length() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * TODO 判断字符是否为汉字
	 * 
	 * @author LiShen
	 * @date 2015-8-28 下午5:10:07
	 * @param a
	 * @return
	 * @see
	 */
	public static boolean isChinese(char a) {
		int v = (int) a;
		return (v >= 19968 && v <= 171941);
	}

	/**
	 * 
	 * TODO 将double类型的gps经纬度转化为长度为12的String
	 * 
	 * @author LiShen
	 * @date 2015-10-14 下午1:25:02
	 * @param value
	 *            double类型的lat或者lng值
	 * @return 长度为12的string类型的lat或者lng值
	 * @see
	 */
	public static String gpsDoubleToString(double value) {
		String result = "";
		try {
			DecimalFormat df = new DecimalFormat("#000.000000000");
			result = df.format(value).replace(".", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
