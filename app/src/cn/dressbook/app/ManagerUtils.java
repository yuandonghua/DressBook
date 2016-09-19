package cn.dressbook.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.alibaba.sdk.android.session.model.Session;
import com.google.zxing.WriterException;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.PushAgent;

import org.opencv.core.Mat;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.dressbook.ui.R;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.general.FotoCut.view.ui.MaskCanvasDelegate;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.model.Adviser;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.CityInfo;
import cn.dressbook.ui.model.CustomService;
import cn.dressbook.ui.model.CydMessage;
import cn.dressbook.ui.model.YJTCMolder;
import cn.dressbook.ui.net.DownloadExec;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.FileUtils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.zxing.QRCodeEncoder;

/**
 * @description: app数据管理工具类
 * @author:袁东华
 * @time:2015-11-2上午9:33:27
 */
public class ManagerUtils {

	private static ManagerUtils mManagerUtils;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	// 上线id
	private String shangXianId = "";

	public String getShangXianId(Activity activity) {
		if ("".equals(shangXianId)) {
			shangXianId = mSharedPreferenceUtils.getShangXianID(activity);
		}
		return shangXianId;
	}

	public void setShangXianId(Context activity, String shangXianId) {
		LogUtil.e("shangXianId:" + shangXianId);
		this.shangXianId = shangXianId;
		mSharedPreferenceUtils.setShangXianID(activity, shangXianId);
	}

	// 消息个数
	private int xxSize;

	public int getXxSize(Activity activity) {
		if (xxSize == 0) {

			final File folder = new File(PathCommonDefines.MESSAGE);
			if (folder.exists()) {
				File[] files = folder.listFiles();
				if (files != null && files.length > 0) {
					String time = mSharedPreferenceUtils.getXXTime(activity);
					LogUtil.e("time:" + time);
					for (int i = 0; i < files.length; i++) {
						File file = files[i];
						if (file.exists()) {
							String name = file.getName();
							if ("".equals(time)) {
								return xxSize = files.length;
							} else {
								name = name.replace(".txt", "");
								if (Long.parseLong(name) > Long.parseLong(time)) {
									LogUtil.e("name:" + name);
									xxSize++;
								}
							}

						}
					}
				}
			}
		}
		return xxSize;
	}

	public void setXxSize(int size) {
		xxSize = size;
	}

	// // 我的二维码
	private String qrPath;

	// 获取二维码
	public String getQrPath(Activity activity) {
		File folder = new File(PathCommonDefines.ERWEIMA);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File qrFile = new File(PathCommonDefines.ERWEIMA, getUser_id(activity)
				+ ".png");
		if (!qrFile.exists()) {
			try {
				QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(activity,
						getUser_id(activity), 600, false);
				Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
				if (bitmap == null) {
					qrPath = null;
				}

				if (FileSDCacher.createBitmapFile(bitmap,
						Bitmap.CompressFormat.PNG, PathCommonDefines.ERWEIMA,
						getUser_id(activity) + ".png")) {
					qrPath = PathCommonDefines.ERWEIMA + "/"
							+ getUser_id(activity) + ".png";

				} else {
					qrPath = null;

				}

			} catch (WriterException e) {
				qrPath = null;

			}

		} else {
			qrPath = PathCommonDefines.ERWEIMA + "/" + getUser_id(activity)
					+ ".png";
		}

		return qrPath;
	}

	// 获取二维码
	public String getQrPath2(Activity activity, String ewm) {
		String qrPath = null;
		File folder = new File(PathCommonDefines.ERWEIMA);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File qrFile = new File(PathCommonDefines.ERWEIMA, ewm + ".png");
		if (!qrFile.exists()) {
			try {
				QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(activity, ewm,
						600, false);
				Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
				if (bitmap == null) {
					qrPath = null;
				}

				if (FileSDCacher.createBitmapFile(bitmap,
						Bitmap.CompressFormat.PNG, PathCommonDefines.ERWEIMA,
						ewm + ".png")) {
					qrPath = PathCommonDefines.ERWEIMA + "/" + ewm + ".png";

				} else {
					qrPath = null;

				}

			} catch (WriterException e) {
				qrPath = null;

			}

		} else {
			qrPath = PathCommonDefines.ERWEIMA + "/" + ewm + ".png";
		}

		return qrPath;
	}

	/**
	 * 店家ID
	 */
	private String djId;

	public String getDjId() {
		return djId;
	}

	public void setDjId(String djId) {
		this.djId = djId;
	}

	/**
	 * 用户身份:@股东会员@会员@渠道会员@量体师@店家@ 检查是否包含身份:contains("@店家@")
	 */
	private String shenFen;

	public String getShenFen(Context context) {
		if (shenFen == null) {
			shenFen = mSharedPreferenceUtils.getShenFen(context);
		}
		return shenFen;
	}

	public void setShenFen(Context context, String shenFen) {
		this.shenFen = shenFen;
		mSharedPreferenceUtils.setShenFen(context, shenFen);
	}

	/**
	 * 用户的财富信息
	 */
	public YJTCMolder yjtc;

	public YJTCMolder getYjtc() {
		return yjtc;
	}

	public void setYjtc(YJTCMolder yjtc) {
		this.yjtc = yjtc;
	}

	/**
	 * 可提现金额
	 */
	private String moneyCash;

	public String getMoneyCash(Context context) {
		if (moneyCash == null) {
			moneyCash = mSharedPreferenceUtils.getMoneyCash(context);
		}
		return moneyCash;
	}

	public void setMoneyCash(Context context, String moneyCash) {
		this.moneyCash = moneyCash;
		mSharedPreferenceUtils.setMoneyCash(context, moneyCash);
	}

	// 默认收货地址
	private Address mAddress;

	public Address getAddress() {
		return mAddress;
	}

	public void setAddress(Address mAddress) {
		this.mAddress = mAddress;
	}

	private Session mSession;

	public Session getSession() {
		return mSession;
	}

	public void setSession(Session mSession) {
		this.mSession = mSession;
	}

	private int mCreateXXID;
	private String mShouJiHao, miaoshu, biaoti, faqiren, canyu, wdmz;
	private boolean msgFlag = true;
	private String tzStr;
	private int tzId;
	private String tzName;
	private int xxzxFlag = 1;
	private int xxgs = 0;
	private int jjhFlag = 0;
	private int attire_id;
	private String attire_urlString;
	private int sexFlag = 0;
	private ExecutorService mExecutorService1;
	private ExecutorService mExecutorService2;
	// 未参与集结号的个数
	private int WCYJJH;
	private String headurl;
	private String mSheBeiHao;
	private String mUserId;
	private int mMoRenXXId;
	private String mMoRenXXName;
	private String mBodyUrl;
	// 创建形象时从服务端下载的模特图片保存到客户端的相对路径
	private String mCreateXX;
	// 扣头结果
	private int mKouTouResult = 0;
	// 传递的方案列表集合个数
	private ArrayList<AttireScheme> mAttireSchemeList;
	// 用户名
	private String mUserName;
	// 积分
	private int mJiFen;
	// 模特数据
	private ArrayList<Integer> mMoteList;
	// 开启的下载任务
	private HashMap<String, Thread> mThreads;
	// 是否分享过
	private int mShareUser;
	// 版本
	private String mVersionName;
	private String mUserHead;
	private ArrayList<AttireScheme> mJingPinGuanList;
	// 是否注册社区
	private boolean isZheCe;
	// 社区密码
	private String mSheQuMiMa;
	/**
	 * 默认衣柜ID
	 */
	private String mWardrobeId;

	public String getWardrobeId(Context context) {
		if (mWardrobeId == null) {
			mWardrobeId = mSharedPreferenceUtils.getWardrobeID(context);
		}
		return mWardrobeId;
	}

	public void setWardrobeId(Context context, String mWardrobeId) {
		this.mWardrobeId = mWardrobeId;
		mSharedPreferenceUtils.setWardrobeID(context, mWardrobeId);
	}

	// 当前界面状态，是否开启侧滑菜单
	private int mIsOpenedState = 1;
	/**
	 * 要刷新关键字的衣柜
	 */
	private String mKeyWardrobe;
	/**
	 * 要刷新选项卡位置
	 */
	private int mColumn;
	/**
	 * 是否获取到相似头1=获取到相似头2=没有获取到相似头
	 */
	private int isXST;
	// 融云连接状态
	private boolean rongYun;
	private PushAgent mPushAgent;
	/**
	 * 商户订单号
	 */
	private String out_trade_no = "";

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * 顾问师列表集合
	 */
	private ArrayList<Adviser> mAdviserList;

	public ArrayList<Adviser> getAdviserList() {
		return mAdviserList;
	}

	public void setAdviserList(ArrayList<Adviser> adviserList) {
		mAdviserList = adviserList;
	}

	public static final String CALLBACK_RECEIVER_ACTION = "callback_receiver_action";

	public static IUmengRegisterCallback mRegisterCallback;

	public static IUmengUnregisterCallback mUnregisterCallback;
	/**
	 * 穿衣典界面的导航0=我穿1=明星穿2=逛逛
	 */
	private int cydFlag = 2;


	public int getCydFlag() {
		return cydFlag;
	}

	public void setCydFlag(int cydFlag) {
		this.cydFlag = cydFlag;
	}

	/**
	 * 正在沟通的客服
	 */
	private CustomService mCustomService;

	public CustomService getCustomService() {
		return mCustomService;
	}

	public void setCustomService(CustomService mCustomService) {
		this.mCustomService = mCustomService;
	}

	public int getIsXST() {
		return isXST;
	}

	public void setIsXST(int isXST) {
		this.isXST = isXST;
	}

	public String getKeyWardrobe() {
		return mKeyWardrobe;
	}

	public void setKeyWardrobe(String mKeyWardrobe) {
		this.mKeyWardrobe = mKeyWardrobe;
	}

	public int getColumn() {
		return mColumn;
	}

	public void setColumn(int mColumn) {
		this.mColumn = mColumn;
	}

	/**
	 * @return the mIsOpenedState
	 */
	public int getIsOpenedState() {
		return mIsOpenedState;
	}

	/**
	 * @param mIsOpenedState
	 *            the mIsOpenedState to set
	 */
	public void setIsOpenedState(int mIsOpenedState) {
		this.mIsOpenedState = mIsOpenedState;
	}

	/**
	 * @return the mVersionName
	 */
	public String getVersionName(Context mContext) {
		if (mVersionName == null || "".equals(mVersionName)) {
			try {
				// 获取packagemanager的实例
				PackageManager packageManager = mContext.getPackageManager();
				PackageInfo packInfo = packageManager.getPackageInfo(
						mContext.getPackageName(), 0);
				mVersionName = packInfo.versionName;

				// getPackageName()是你当前类的包名，0代表是获取版本信息
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mVersionName;
	}

	/**
	 * @param mVersionName
	 *            the mVersionName to set
	 */
	public void setVersionName(String mVersionName) {
		this.mVersionName = mVersionName;
	}

	/**
	 * @return the mShare
	 */
	public int getShare(Context mContext) {
		if (mShareUser == 0) {
			mShareUser = mSharedPreferenceUtils.getInstance().getIsShare(
					mContext);
		}
		return mShareUser;
	}

	public void setShare(Context mContext, int userid) {
		this.mShareUser = userid;
		mSharedPreferenceUtils.getInstance().setIsShare(mContext, userid);
	}

	private boolean isRun = true;

	public void setIsRun(boolean isRun) {
		this.isRun = isRun;
	}

	public boolean getIsRun() {
		return isRun;
	}

	/**
	 * @return the mThreads
	 */
	public HashMap<String, Thread> getThreads() {
		if (mThreads == null) {
			mThreads = new HashMap<String, Thread>();
		}
		return mThreads;
	}

	/**
	 * @param mThreads
	 *            the mThreads to set
	 */
	public void setThreads(HashMap<String, Thread> mThreads) {
		this.mThreads = mThreads;
	}

	/**
	 * @return the mMoteList
	 */
	public ArrayList<Integer> getMoteList() {
		if (mMoteList == null) {
			mMoteList = new ArrayList<Integer>();
			// mMoteList.add(11);
			mMoteList.add(12);
			mMoteList.add(13);
			mMoteList.add(14);
			// mMoteList.add(15);
			// mMoteList.add(21);
			mMoteList.add(22);
			mMoteList.add(23);
			mMoteList.add(24);
			// mMoteList.add(25);
		}
		return mMoteList;
	}

	/**
	 * @return the mUserName
	 */
	public String getUserName(Context mContext) {
		if (mUserName == null || "".equals(mUserName)) {
			mUserName = mSharedPreferenceUtils.getUserName(mContext);
		}
		return mUserName;
	}

	/**
	 * @param mUserName
	 *            the mUserName to set
	 */
	public void setUserName(Context mContext, String mUserName) {
		this.mUserName = mUserName;
		mSharedPreferenceUtils.setUserName(mContext, mUserName);
	}

	/**
	 * @description 得到用户的头像地址
	 * @parameters
	 */
	public String getUserHead(Context mContext) {
		if (mUserHead == null || "".equals(mUserHead)) {
			mUserHead = mSharedPreferenceUtils.getUserHead(mContext);
		}
		return mUserHead;
	}

	/**
	 * @param mUserHead
	 *            the mUserName to set
	 */
	public void setUserHead(Context mContext, String mUserHead) {
		this.mUserHead = mUserHead;
		mSharedPreferenceUtils.setUserHead(mContext, mUserHead);
	}

	private String try_result_save;

	/**
	 * 上传试衣形象的路径
	 * 
	 * @return
	 */
	public String getTry_result_save(Context mContext) {
		if (try_result_save == null) {
			try_result_save = mSharedPreferenceUtils.getTryResultSave(mContext);
		}
		return try_result_save;
	}

	/**
	 * 上传试衣形象的路径
	 * 
	 * @param try_result_save
	 */
	public void setTry_result_save(Context mContext, String try_result_save) {
		this.try_result_save = try_result_save;
		mSharedPreferenceUtils.setTryResultSave(mContext, try_result_save);
	}

	/**
	 * @return the mAttireSchemeList
	 */
	public ArrayList<AttireScheme> getAttireSchemeList() {
		return mAttireSchemeList;
	}

	/**
	 * @param mAttireSchemeList
	 *            the mAttireSchemeList to set
	 */
	public void setAttireSchemeList(ArrayList<AttireScheme> mAttireSchemeList) {
		this.mAttireSchemeList = mAttireSchemeList;
	}

	/**
	 * @return the mKouTouResult
	 */
	public int getKouTouResult() {
		return mKouTouResult;
	}

	/**
	 * @param mKouTouResult
	 *            the mKouTouResult to set
	 */
	public void setKouTouResult(int mKouTouResult) {
		this.mKouTouResult = mKouTouResult;
	}

	/**
	 * @return the mCreateXX
	 */
	public String getCreateXX() {
		return mCreateXX;
	}

	/**
	 * @param mCreateXX
	 *            the mCreateXX to set
	 */
	public void setCreateXX(String mCreateXX) {
		this.mCreateXX = mCreateXX;
	}

	/**
	 * @return the mBodyUrl
	 */
	public String getBodyUrl() {
		return mBodyUrl;
	}

	/**
	 * @param mBodyUrl
	 *            the mBodyUrl to set
	 */
	public void setBodyUrl(String mBodyUrl) {
		this.mBodyUrl = mBodyUrl;
	}

	/**
	 * @throws
	 * @description:暂停合成穿衣任务
	 */
	public void stopTask() {
		isRun = false;
		getExecutorService().shutdown();
		getExecutorService().shutdownNow();
		setExecutorService(null);
		getExecutorService2().shutdown();
		getExecutorService2().shutdownNow();
		setExecutorService2(null);
		if (mThreads != null && mThreads.size() > 0) {
			Iterator<Entry<String, Thread>> it = mThreads.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Thread> entry = it.next();
				Thread t = entry.getValue();
				if (t != null) {
					t.interrupt();
				}
			}
			setThreads(null);
		} else {

		}
		isRun = true;
	}

	/**
	 * @throws
	 * @description:穿衣线程池 测试:2=6-15秒,1=4-8秒
	 */
	public ExecutorService getExecutorService2() {
		if (mExecutorService2 == null) {
			mExecutorService2 = Executors.newFixedThreadPool(1);
		}
		return mExecutorService2;
	}

	public void setExecutorService2(ExecutorService es) {
		mExecutorService2 = es;
	}

	/**
	 * @throws
	 * @description:下载线程池
	 */
	public ExecutorService getExecutorService() {
		if (mExecutorService1 == null) {
			mExecutorService1 = Executors.newFixedThreadPool(2);
		}
		return mExecutorService1;
	}

	public void setExecutorService(ExecutorService es) {
		mExecutorService1 = es;
	}

	public int getCreateXXID() {
		return mCreateXXID;
	}

	public void setCreateXXID(int mCreateXXID) {
		this.mCreateXXID = mCreateXXID;
	}

	public int getWCYJJH(Context mContext) {
		if (WCYJJH == 0) {
			WCYJJH = mSharedPreferenceUtils.getWCYJJH(mContext);
		}
		return WCYJJH;
	}

	public void setWCYJJH(Context mContext, int wcyjjh) {
		this.WCYJJH = wcyjjh;
		mSharedPreferenceUtils.setWCYJJH(mContext, wcyjjh);
	}

	public int getSexFlag() {
		return sexFlag;
	}

	public void setSexFlag(int sexFlag) {
		this.sexFlag = sexFlag;
	}

	public String getWdmz() {
		return wdmz;
	}

	public void setWdmz(String wdmz) {
		this.wdmz = wdmz;
	}

	public String getCanyu() {
		return canyu;
	}

	public void setCanyu(String canyu) {
		this.canyu = canyu;
	}

	public String getAttire_urlString() {
		return attire_urlString;
	}

	public void setAttire_urlString(String attire_urlString) {
		this.attire_urlString = attire_urlString;
	}

	public String getMiaoshu() {
		return miaoshu;
	}

	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}

	public String getBiaoti() {
		return biaoti;
	}

	public void setBiaoti(String biaoti) {
		this.biaoti = biaoti;
	}

	public String getFaqiren() {
		return faqiren;
	}

	public void setFaqiren(String faqiren) {
		this.faqiren = faqiren;
	}

	public int getAttire_id() {
		return attire_id;
	}

	public void setAttire_id(int attire_id) {
		this.attire_id = attire_id;
	}

	public String getPhoneNum(Context mContext) {
		if (mShouJiHao == null || "".equals(mShouJiHao)) {
			mShouJiHao = mSharedPreferenceUtils.getUserPhoneNum(mContext);
		}
		return mShouJiHao;
	}

	public void setPhoneNum(Context mContext, String phoneNum) {
		this.mShouJiHao = phoneNum;
		mSharedPreferenceUtils.setUserPhoneNum(mContext, mShouJiHao);
	}

	/**
	 * @description:获取用户ID
	 */
	public String getUser_id(Context mContext) {
		if (mUserId == null || "".equals(mUserId)) {
			mUserId = mSharedPreferenceUtils.getUserId(mContext);
		}
		return mUserId;
	}

	/**
	 * @description:设置用户ID
	 */
	public void setUser_id(Context mContext, String user_id) {
		this.mUserId = user_id;
		mSharedPreferenceUtils.setUserId(mContext, user_id);

	}

	public int getJjhFlag() {
		return jjhFlag;
	}

	public void setJjhFlag(int jjhFlag) {
		this.jjhFlag = jjhFlag;
	}

	public static ManagerUtils getInstance() {
		if (mManagerUtils == null) {
			mManagerUtils = new ManagerUtils();
		}
		return mManagerUtils;
	}

	public int getXxgs() {
		return xxgs;
	}

	public void setXxgs(int xxgs) {
		this.xxgs = xxgs;
	}

	public int getXxzxFlag() {
		return xxzxFlag;
	}

	public void setXxzxFlag(int xxzxFlag) {
		this.xxzxFlag = xxzxFlag;
	}

	public String getMessageName() {
		return tzName;
	}

	public int getMessageId() {
		return tzId;
	}

	public String getMessage() {
		return tzStr;
	}

	public int xx_id;
	private String mToken;
	private int mTuiChang;

	public void setXXID(int id) {
		xx_id = id;
	}

	/**
	 * @description 腿长
	 * @parameters
	 */
	public void setTuiChang(int mTuiChang) {
		// TODO Auto-generated method stub
		this.mTuiChang = mTuiChang;
	}

	public int getTuiChang() {
		return mTuiChang;

	}

	private int mMingXingSizeF = 0;
	private int mMingXingSizeM = 0;

	/**
	 * 描绘面部和脖子的bitmap
	 */
	private Bitmap mBitmap1;

	public void setFaceNeckBitmap(Bitmap bitmap1) {
		// TODO Auto-generated method stub
		mBitmap1 = bitmap1;
	}

	public Bitmap getFaceNeckBitmap() {
		return mBitmap1;
	}

	/**
	 * 描绘的mat集合
	 */
	private List<Mat> lastShowMatList;

	public void setLastShowMatList(List<Mat> lastShowMatList) {
		// TODO Auto-generated method stub
		this.lastShowMatList = lastShowMatList;
	}

	public List<Mat> getLastShowMatList() {
		return lastShowMatList;
	}

	/**
	 * 整个mat
	 */
	private Mat wholeDrawMat;

	public void setWholeDrawMat(Mat wholeDrawMat) {
		// TODO Auto-generated method stub
		this.wholeDrawMat = wholeDrawMat;
	}

	public Mat getWholeDrawMat() {

		return wholeDrawMat;
	}

	/**
	 * 撤销bitmap
	 */
	private Bitmap backBitmap;

	public void setBackBitmap(Bitmap backBitmap) {
		this.backBitmap = backBitmap;
	}

	public Bitmap getBackBitmap() {
		return backBitmap;
	}

	/**
	 * 照片的bitmap
	 */
	private Bitmap mBitmap;

	public void setPicBitmap(Bitmap bitmap) {
		// TODO Auto-generated method stub
		mBitmap = bitmap;
	}

	public Bitmap getPicBitmap() {
		return mBitmap;
	}

	/**
	 * 画布的宽度
	 */
	private int canvasWidth;

	public int getCanvasWidth() {
		return canvasWidth;
	}

	public void setCanvasWidth(int canvasWidth) {
		this.canvasWidth = canvasWidth;
	}

	/**
	 * 画布的高度
	 */
	private int canvasHeight;

	public int getCanvasHeight() {
		return canvasHeight;
	}

	public void setCanvasHeight(int canvasHeight) {
		this.canvasHeight = canvasHeight;
	}

	/**
	 * 背景mat
	 */
	private Mat bgdModel;

	public Mat getBgdModel() {
		return bgdModel;
	}

	public void setBgdModel(Mat bgdModel) {
		this.bgdModel = bgdModel;
	}

	/**
	 * 前景mat
	 */
	private Mat foreModel;

	public Mat getForeModel() {
		return foreModel;
	}

	public void setForeModel(Mat foreModel) {
		this.foreModel = foreModel;
	}

	/**
	 * 最后一个mat
	 */
	private Mat lastMaskMat;

	public Mat getLastMaskMat() {
		return lastMaskMat;
	}

	public void setLastMaskMat(Mat lastMaskMat) {
		this.lastMaskMat = lastMaskMat;
	}

	/**
	 * 当前画布
	 */
	private Canvas imgCanvas;

	public Canvas getImgCanvas() {
		return imgCanvas;
	}

	public void setImgCanvas(Canvas imgCanvas) {
		this.imgCanvas = imgCanvas;
	}

	/**
	 * 画笔的画布
	 */
	private Canvas backCanvas;

	public Canvas getBackCanvas() {
		return backCanvas;
	}

	public void setBackCanvas(Canvas backCanvas) {
		this.backCanvas = backCanvas;
	}

	private MaskCanvasDelegate delegate;

	public MaskCanvasDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(MaskCanvasDelegate delegate) {
		this.delegate = delegate;
	}

	/**
	 * @throws
	 * @description: 回收图片
	 */
	public void clearBitmap(Bitmap bit) {
		if (bit != null) {
			bit.recycle();
			bit = null;
		}
	}

	private boolean isCreateImage;

	public boolean getIsCreateImage() {
		return isCreateImage;
	}

	;

	public void setIsCreateImage(boolean b) {
		// TODO Auto-generated method stub
		isCreateImage = b;
	}

	private String password;

	/**
	 * @description:设置密码
	 */
	public void setPassword(Context mContext, String password) {
		// TODO Auto-generated method stub
		this.password = password;
		mSharedPreferenceUtils.setUserPassword(mContext, password);
	}

	public String getPassword(Context mContext) {
		if (password == null) {
			password = mSharedPreferenceUtils.getUserPassword(mContext);
		}
		return password;
	}

	ArrayList<CityInfo> provinceList = null;
	ArrayList<CityInfo> cityList = null;
	ArrayList<CityInfo> districtList = null;

	public ArrayList<CityInfo> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(ArrayList<CityInfo> provinceList) {
		this.provinceList = provinceList;
	}

	public ArrayList<CityInfo> getCityList() {
		return cityList;
	}

	public void setCityList(ArrayList<CityInfo> cityList) {
		this.cityList = cityList;
	}

	public ArrayList<CityInfo> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(ArrayList<CityInfo> districtList) {
		this.districtList = districtList;
	}

	private String provice;
	private String city;
	private String district;

	/**
	 * @description:获取省份
	 */
	public String getProvice() {
		return provice;
	}

	/**
	 * @description:设置省份
	 */
	public void setProvice(String provice) {
		this.provice = provice;
	}

	/**
	 * @description:获取城市
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @description:设置城市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @description:获取地区
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @description:设置地区
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @description:是否登陆
	 */
	public boolean isLogin(Context mContext) {
		// TODO Auto-generated method stub
		if (getUser_id(mContext) != null && getPhoneNum(mContext) != null
				&& getPhoneNum(mContext).length() == 11) {
			return true;
		}
		return false;
	}

	private ArrayList<String> adviserIdList;

	/**
	 * @description:获取顾问id集合
	 */
	public ArrayList<String> getAdviserIdList() {
		return adviserIdList;
	}

	/**
	 * @description:设置顾问id集合
	 */
	public void setAdviserIdList(ArrayList<String> adviserIdList) {
		this.adviserIdList = adviserIdList;
	}

	/**
	 * @description:清除用户数据
	 */
	public void clearData(Context mContext) {
		mAddress=null;
		setShenFen(mContext, null);
		mSharedPreferenceUtils.setUserId(mContext, null);
		setPhoneNum(mContext, null);
		setUser_id(mContext, null);
		setUserName(mContext, null);
		setUserHead(mContext, null);
		mSharedPreferenceUtils.setWardrobeID(mContext, null);
		mSharedPreferenceUtils.setSex(mContext, "未设置");
		mSharedPreferenceUtils.setBirthday(mContext, "未设置");
		mSharedPreferenceUtils.setHeight(mContext, "未设置");
		mSharedPreferenceUtils.setWeight(mContext, "未设置");
		mSharedPreferenceUtils.setChest(mContext, "未设置");
		mSharedPreferenceUtils.setWaist(mContext, "未设置");
		mSharedPreferenceUtils.setHipline(mContext, "未设置");
		mSharedPreferenceUtils.setShoulder(mContext, "未设置");
		mSharedPreferenceUtils.setArm(mContext, "未设置");
		mSharedPreferenceUtils.setLeg(mContext, "未设置");
		mSharedPreferenceUtils.setNeck(mContext, "未设置");
		mSharedPreferenceUtils.setWrist(mContext, "未设置");
		mSharedPreferenceUtils.setFoot(mContext, "未设置");
		yjtc = null;
		FileSDCacher
				.deleteDirectory2(new File(PathCommonDefines.WARDROBE_HEAD));
		FileSDCacher
				.deleteDirectory2(new File(PathCommonDefines.WARDROBE_TRYON));
		FileSDCacher
				.deleteDirectory2(new File(PathCommonDefines.WARDROBE_MOTE));

	}

	/**
	 * @description:下载模特文件
	 */
	public void downloadMTFile() {
		// TODO Auto-generated method stub
		File mtFolder = new File(PathCommonDefines.MOTE);
		if (!mtFolder.exists()) {
			mtFolder.mkdirs();
		}
		ArrayList<String> list = new ArrayList<String>();
		list.add("12");
		list.add("13");
		list.add("14");
		list.add("22");
		list.add("23");
		list.add("24");
		for (int i = 0; i < list.size(); i++) {
			String shap = list.get(i);
			File pngFile = new File(PathCommonDefines.MOTE, shap + ".png");
			if (!pngFile.exists()) {
				DownloadExec.getInstance().downloadFile(null,
						PathCommonDefines.SERVER_MOTE + shap + ".png",
						PathCommonDefines.MOTE + "/" + shap + ".png", 0, 0);
			}
			File xmlFile = new File(PathCommonDefines.MOTE, shap + ".png.xml");
			if (!xmlFile.exists()) {
				DownloadExec.getInstance().downloadFile(null,
						PathCommonDefines.SERVER_MOTE + shap + ".png.xml",
						PathCommonDefines.MOTE + "/" + shap + ".png.xml", 0, 0);
			}
		}

	}

	public ImageOptions getImageOptions() {
		ImageOptions imageOptions = new ImageOptions.Builder()
				// .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
				// .setRadius(DensityUtil.dip2px(5))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 加载中或错误图片的ScaleType
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				// .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.pic_grey)
				.setFailureDrawableId(R.drawable.pic_grey).build();
		return imageOptions;
	}

	public ImageOptions getImageOptionsCircle(Boolean isCircle) {
		ImageOptions imageOptions = new ImageOptions.Builder()
				// .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
				// .setRadius(DensityUtil.dip2px(2))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 加载中或错误图片的ScaleType
				.setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
				.setCircular(isCircle)
				// .setImageScaleType(type)
				.setLoadingDrawableId(R.drawable.pic_grey)
				.setFailureDrawableId(R.drawable.pic_grey).build();
		return imageOptions;
	}

	public ImageOptions getImageOptions(ScaleType type) {
		ImageOptions imageOptions = new ImageOptions.Builder()
				// .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
				// .setRadius(DensityUtil.dip2px(2))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 加载中或错误图片的ScaleType
				.setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
				.setImageScaleType(type)
				.setLoadingDrawableId(R.drawable.pic_grey)
				.setFailureDrawableId(R.drawable.pic_grey).build();
		return imageOptions;
	}

	public ImageOptions getImageOptions(ScaleType type, boolean userMemCache) {
		ImageOptions imageOptions = new ImageOptions.Builder()
				.setUseMemCache(userMemCache)
				// .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
				// .setRadius(DensityUtil.dip2px(2))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 加载中或错误图片的ScaleType
				.setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
				.setImageScaleType(type)
				.setLoadingDrawableId(R.drawable.pic_grey)
				.setFailureDrawableId(R.drawable.pic_grey).build();
		return imageOptions;
	}

	public ImageOptions getImageOptions(ScaleType type, int radius) {
		ImageOptions imageOptions = new ImageOptions.Builder()
				// .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
				.setRadius(DensityUtil.dip2px(radius))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 加载中或错误图片的ScaleType
				.setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
				.setImageScaleType(type)
				.setLoadingDrawableId(R.drawable.pic_grey)
				.setFailureDrawableId(R.drawable.pic_grey).build();
		return imageOptions;
	}
}