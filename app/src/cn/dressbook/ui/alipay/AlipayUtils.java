package cn.dressbook.ui.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.xutils.common.util.LogUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;

import com.alipay.sdk.app.PayTask;

/**
 * @description: 支付宝工具类
 * @author:袁东华
 * @time:2015-9-14下午3:37:01
 */
public class AlipayUtils {
	// 商户PID
	public static final String PARTNER = "2088312053643665";
	// 商户收款账号
	public static final String SELLER = "dress_adv@163.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANBDurJvr99klWD403l8h61AjEKvPklFe8b/XwDN9zKh+xKcU5NNqo85Je524ibG5R8VyHUVE+WVLlrWV9F4catiaxvYXheycLko9uab4EQ3vOgRP7h2eqhd/GVrBn+2f+2UNoMV5W8Fnmaq8Cdx+GhRljiqTINO7AyMgiDYnqErAgMBAAECgYA04p4MJbRhY+dS4pzA4tCJPDTh0iJc9NoDpGF5kNPO9bcN+Mh4RWakS/zH20R+WE9iX2Ox55JP4FwPERLnzFAAocjQUR8SXZQOW/UYIKoVMR40+PwuYHU5OsOePQ1kw1N9hVJAa5AoNHDuIF/No5iIqC5u4lryHRSKRH5HOoSnIQJBAPcs/LQ94iMEhoCbi9baQCZnnhOa5MC/J+WjLI5rsa283wwKMb5gFeglFr5Rqe4TpgdsEUS/OEv9uFm/W0pOCYkCQQDXsx8UoR/sDRpCp4daQ4yWMB90Wa02XoiABuDM64Fyn0tP6A2ReWomaMBp7Z55bj8IbsaA/1Vo6J3Vx2vsdYwTAkB7pXWEdLB68/iUvTrm76bHj230QLfN89Hxff77/8OSNbkePLtFkKtkxajq688l2Y5kiBrbWQzAdtuRibgRI5iBAkAtLDEdI+VIjTHrtaQODkl6Bpf/yOwSj9esYERiBgw1EhE2qdV1muTVKGjrhj0DF0iSHojp6Q9dCiJL7Bi5C6ELAkEAlWk6W7fwrXgHnUaMDzR6yCmNV2eTJRhwjebr/OeUWstTqYuYlEDPYwtpd/feMgXIODQsi0RMCifSOMB0nGrc7w==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	private static AlipayUtils mAlipayUtils;
	private static Activity mContext;
	private static Handler mHandler;

	private AlipayUtils() {

	};

	public static AlipayUtils getInstance(Activity context, Handler handler) {
		if (mAlipayUtils == null) {
			mAlipayUtils = new AlipayUtils();
		}
		mContext = context;
		mHandler = handler;
		return mAlipayUtils;
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(String spTitle, String desc, String price) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(mContext)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									mContext.finish();
								}
							}).show();
			return;
		}
		// 订单
		String orderInfo = getOrderInfo(spTitle, desc, price);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(mContext);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);
				Message msg = new Message();
				msg.what = NetworkAsyncCommonDefines.SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(mContext);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = NetworkAsyncCommonDefines.SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(mContext);
		String version = payTask.getVersion();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		Random r = new Random();
		key = key + Math.abs(r.nextInt());
		key = key.substring(0, 15);
		ManagerUtils.getInstance().setOut_trade_no(key);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
