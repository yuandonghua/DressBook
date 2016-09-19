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

import android.content.Context;
import android.widget.Toast;

/**
 * TODO Toast工具类
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-6-5 上午10:27:06
 * @since
 * @version
 */
public class T {
	public static final String SUPPORT_SUCCESS = "支持成功~";
	public static final String WRONG_PHONE = "请输入正确的手机号码~~";
	public static final String SUBMIT_ZIP_CODE_SUCCESS = "包裹单号提交成功，审核完成您就可以收到衣扣啦~~";
	public static final String NO_NEED_JOIN_AGAIN = "您已参加，无需再次参加~~";
	public static final String NO_NEED_SUPPORT_AGAIN = "感谢您的支持~~";
	public static final String WRONG_ARTICLE_TITLE = "请输入博文标题哦~";
	public static final String WRONG_ARTICLE_CONTENT = "客官多写点字嘛~";
	public static final String SUBMIT_SUCCESS = "提交成功~";
	public static final String CROP_PICK_ERROR = "没有发现可用的图片哦~";
	public static final String GET_TOPICS_ERROR = "获取话题标签失败~";
	public static final String NO_MORE_PHOTOS = "最多只能上传9张照片哟~";
	public static final String PORTRAIT_CROP_ERROR = "发生未知错误，图片裁剪失败！";
	public static final String REFRESH_COMPLETE = "刷新完成~~";
	public static final String FOCUS_SUCCESS = "关注成功~~";
	public static final String FOCUS_FAILED = "关注失败~~";
	public static final String CANCLE_FOCUS_SUCCESS = "取消关注成功~~";
	public static final String LIKE_SUCCESS = "喜欢成功~~";
	public static final String CANCLE_LIKE_SUCCESS = "取消喜欢成功~~";
	public static final String REPORT_SUCCESS = "举报成功~~";
	public static final String REPORT_FAILED = "对不起，举报失败~~";

	/**
	 * TODO 显示长Toast
	 * 
	 * @author LiShen
	 * @date 2015-6-5 上午10:31:13
	 * @see
	 */
	public static void l(String text, Context context) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * TODO 显示短Toast
	 * 
	 * @author LiShen
	 * @date 2015-6-5 上午10:30:52
	 * @see
	 */
	public static void s(String text, Context context) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * TODO 服务器请求错误
	 * 
	 * @author LiShen
	 * @date 2015-6-16 上午11:18:44
	 * @see
	 */
	public static void se(Context context) {
		Toast.makeText(context, "抱歉，网络有点不通畅，请求失败~", Toast.LENGTH_SHORT).show();
	}
}
