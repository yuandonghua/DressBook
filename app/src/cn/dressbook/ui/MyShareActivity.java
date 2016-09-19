package cn.dressbook.ui;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import cn.dressbook.ui.utils.ToastUtils;

import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.APMediaMessage;
import com.alipay.share.sdk.openapi.APWebPageObject;
import com.alipay.share.sdk.openapi.IAPApi;
import com.alipay.share.sdk.openapi.SendMessageToZFB;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 分享 需要传入参数 否则使用默认参数
 * 
 * @author 熊天富
 * 
 */
@ContentView(R.layout.activity_share)
public class MyShareActivity extends BaseActivity {
	public static final int RES = 0, LOCAL = 1;
	private Context mContext = MyShareActivity.this;
	private IAPApi api;
	private String title;
	private String mContent, mTargetUrl;
	private String pic = "";
	private String appId;
	private String appKey;

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() throws Exception {
		// 接收参数
		Intent intent = getIntent();
		pic = intent.getStringExtra("pic");
		mContent = intent.getStringExtra("content");
		mTargetUrl = intent.getStringExtra("targeturl");
		title = intent.getStringExtra("title");
		if (pic == null || mContent == null || mTargetUrl == null
				|| title == null) {
			Toast.makeText(mContext, "没有获取到分享内容", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		LogUtil.e("targeturl:" + mTargetUrl);
		LogUtil.e("content:" + mContent);
		LogUtil.e("title:" + title);
		LogUtil.e("pic:" + pic);
		appId = "1101319295";
		appKey = "4QBqAmzsb3Fdtq7u";
		// 初始化平台
		// 微信 appid appsecret
		PlatformConfig.setWeixin("wx43d29cfbc12c8601",
				"f1068561f8636786d3a2bb08423ae712");

		// 新浪微博 appkey appsecret
		/*
		 * PlatformConfig.setSinaWeibo("3921700954",
		 * "04b48b094faeb16683c32669824ebdad");
		 */
		// QQ和Qzone appid appkey
		PlatformConfig.setQQZone("1101319295", "4QBqAmzsb3Fdtq7u");
		// 初始化支付宝
		initApay();

	}

	/**
	 * 设置分享内容
	 */
	/*
	 * private void setWeiboShareContent() { // 设置分享内容
	 * mController.setShareContent(mContent + mTargetUrl);
	 * 
	 * // 设置分享图片, 参数2为图片的url地址 // if (imageType == LOCAL) { //
	 * mController.setShareMedia(new UMImage(mContext, new File( //
	 * imageUriLocal, imageName))); // } else { // mController.setShareMedia(new
	 * UMImage(mContext, imageUri)); mController.setShareMedia(new
	 * UMImage(mContext, pic)); // }
	 * 
	 * }
	 */

	/**
	 * 初始化支付宝
	 */
	private void initApay() {
		String appId = "2015082400229361";
		api = APAPIFactory.createZFBApi(getApplicationContext(), appId, false);

	}

	public void click(View v) {
		switch (v.getId()) {
		case R.id.share_ib:
			finish();
			break;
		// 点击QQ
		case R.id.ll_qq:
			/*
			 * // 添加QQ支持, 并且设置QQ分享内容的target url UMQQSsoHandler qqSsoHandler =
			 * new UMQQSsoHandler( MyShareActivity.this, appId, appKey); if
			 * (qqSsoHandler.isClientInstalled()) { QQShareContent
			 * qqShareContent = new QQShareContent(); // if (imageType == LOCAL)
			 * { // qqShareContent.setShareImage(new UMImage(mContext, // new
			 * File(imageUriLocal, imageName))); // } else {
			 * qqShareContent.setShareImage(new UMImage(mContext, pic));
			 * 
			 * // } qqShareContent.setTargetUrl(mTargetUrl);
			 * qqShareContent.setShareContent(mContent);
			 * qqShareContent.setTitle(title);
			 * mController.setShareMedia(qqShareContent);
			 * qqSsoHandler.addToSocialSDK(); performShare(SHARE_MEDIA.QQ); }
			 * else { ToastUtils.getInstance().showToast(mContext, "请安装QQ",
			 * 200); }
			 */
			sharePlatform(SHARE_MEDIA.QQ);
			finish();
			break;
		// 点击QQ空间
		case R.id.ll_qq_ozen:
			/*
			 * QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
			 * MyShareActivity.this, appId, appKey); if
			 * (qZoneSsoHandler.isClientInstalled()) { QZoneShareContent
			 * qZoneShareContent = new QZoneShareContent(); // if (imageType ==
			 * LOCAL) { // qZoneShareContent.setShareImage(new UMImage(mContext,
			 * // new File(imageUriLocal, imageName))); // } else {
			 * qZoneShareContent.setShareImage(new UMImage(mContext, pic));
			 * 
			 * // } qZoneShareContent.setTargetUrl(mTargetUrl);
			 * qZoneShareContent.setShareContent(mContent);
			 * qZoneShareContent.setTitle(title);
			 * mController.setShareMedia(qZoneShareContent);
			 * qZoneSsoHandler.addToSocialSDK();
			 * performShare(SHARE_MEDIA.QZONE);
			 * 
			 * } else { ToastUtils.getInstance().showToast(mContext, "请安装QQ",
			 * 200); }
			 */
			sharePlatform(SHARE_MEDIA.QZONE);
			finish();
			break;
		// 点击微信
		case R.id.ll_weixin:
			/*
			 * UMWXHandler wxHandler = new UMWXHandler(mContext,
			 * Constants.APP_ID, Constants.AppSecret); if
			 * (wxHandler.isClientInstalled()) { WeiXinShareContent
			 * weiXinShareContent = new WeiXinShareContent(); // if (imageType
			 * == LOCAL) { // weiXinShareContent.setShareImage(new
			 * UMImage(mContext, // new File(imageUriLocal, imageName))); // }
			 * else { weiXinShareContent.setShareImage(new UMImage(mContext,
			 * pic)); // } weiXinShareContent.setTargetUrl(mTargetUrl);
			 * weiXinShareContent.setShareContent(mContent);
			 * weiXinShareContent.setTitle(title);
			 * mController.setShareMedia(weiXinShareContent);
			 * wxHandler.addToSocialSDK(); performShare(SHARE_MEDIA.WEIXIN); }
			 * else { Toast.makeText(mContext, "请安装微信",
			 * Toast.LENGTH_LONG).show(); }
			 */
			sharePlatform(SHARE_MEDIA.WEIXIN);
			finish();
			break;
		// 点击新浪微博
		/*
		 * case R.id.ll_xinlang: setWeiboShareContent();
		 * performShare(SHARE_MEDIA.SINA); sharePlatform(SHARE_MEDIA.SINA);
		 * break;
		 */
		// 点击支付宝
		case R.id.ll_zhifubao:
			if (api.isZFBAppInstalled()) {
				if (api.isZFBSupportAPI()) {
					// 初始化一个APWebPageObject并填充网页链接地址
					APWebPageObject webPageObject = new APWebPageObject();
					webPageObject.webpageUrl = mTargetUrl;

					// 构造一个APMediaMessage对象，并填充APWebPageObject
					APMediaMessage webMessage = new APMediaMessage();
					webMessage.mediaObject = webPageObject;

					// 填充网页分享必需参数，开发者需按照自己的数据进行填充
					webMessage.title = title;
					webMessage.description = mContent;
					// webMessage.thumbUrl =
					// "https://t.alipayobjects.com/images/rmsweb/T1vs0gXXhlXXXXXXXX.jpg";
					// if (imageType == LOCAL) {
					// // 初始化一个APImageObject
					// webMessage.setThumbImage(BitmapFactory
					// .decodeFile(imageUriLocal + "/" + imageName));
					// } else {
					// 网页分享的缩略图也可以使用bitmap形式传输
					webMessage.thumbUrl = pic;
					// }

					// 构造一个Req
					SendMessageToZFB.Req webReq = new SendMessageToZFB.Req();
					webReq.message = webMessage;
					webReq.transaction = buildTransaction("webpage");

					// 调用api接口发送消息到支付宝
					api.sendReq(webReq);
				} else {
					ToastUtils.getInstance().showToast(mContext,
							"您的支付宝版本不支持分享功能！", 200);
				}
			} else {
				ToastUtils.getInstance().showToast(mContext, "您没有安装支付宝", 200);
			}
			break;
		// 点击腾讯微博
		/*
		 * case R.id.ll_tencent_weibo: performShare(SHARE_MEDIA.TENCENT); break;
		 */
		// 点击朋友圈
		case R.id.ll_friend:
			/*
			 * // 支持微信朋友圈 UMWXHandler wxCircleHandler = new
			 * UMWXHandler(mContext, Constants.APP_ID, Constants.AppSecret); if
			 * (wxCircleHandler.isClientInstalled()) { CircleShareContent
			 * circleMedia = new CircleShareContent(); UMImage image; // if
			 * (imageType == LOCAL) { // image = new UMImage(this, // new
			 * File(imageUriLocal, imageName)); // } else { image = new
			 * UMImage(this, pic); // } circleMedia.setShareImage(image);
			 * circleMedia.setTargetUrl(mTargetUrl);
			 * circleMedia.setShareContent(mContent);
			 * circleMedia.setTitle(title);
			 * mController.setShareMedia(circleMedia);
			 * wxCircleHandler.setToCircle(true);
			 * wxCircleHandler.addToSocialSDK();
			 * performShare(SHARE_MEDIA.WEIXIN_CIRCLE); } else {
			 * Toast.makeText(mContext, "请安装微信", Toast.LENGTH_LONG).show(); }
			 */
			sharePlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
			break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		UMShareAPI.get(mContext)
				.onActivityResult(requestCode, resultCode, data);
		finish();

	}

	/*
	 * private void performShare(SHARE_MEDIA platform) {
	 * 
	 * mController.postShare(MyShareActivity.this, platform, new
	 * SnsPostListener() {
	 * 
	 * @Override public void onStart() {
	 * ToastUtils.getInstance().showToast(mContext, "开始分享", 200); }
	 * 
	 * @Override public void onComplete(SHARE_MEDIA platform, int eCode,
	 * SocializeEntity entity) {
	 * 
	 * String showText = platform.toString(); if (eCode ==
	 * StatusCode.ST_CODE_SUCCESSED) { finish(); } else { }
	 * 
	 * } }); // finish(); }
	 */
	/**
	 * 分享到单独平台
	 * 
	 * @param platform
	 */
	private void sharePlatform(SHARE_MEDIA platform) {
		UMImage umImage = new UMImage(mContext, pic);
		ShareContent shareContent = new ShareContent();
		shareContent.mTargetUrl = mTargetUrl;
		shareContent.mText = mContent;
		shareContent.mTitle = title;
		shareContent.mMedia = umImage;
		new ShareAction(MyShareActivity.this).setPlatform(platform)
				.setCallback(umShareListener).setShareContent(shareContent)
				.share();

	}

	private UMShareListener umShareListener = new UMShareListener() {

		@Override
		public void onResult(SHARE_MEDIA platform) {
			// TODO Auto-generated method stub
			ToastUtils.getInstance().showToast(mContext, platform + "分享成功！",
					100);

		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable th) {
			// TODO Auto-generated method stub
			ToastUtils.getInstance().showToast(mContext, platform + "分享失败！",
					100);

		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			// TODO Auto-generated method stub
			ToastUtils.getInstance().showToast(mContext, platform + "分享取消！",
					100);

		}
	};

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	private boolean isFirst = true;

}
