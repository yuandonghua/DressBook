package cn.dressbook.ui.utils;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressWarnings("deprecation")
@SuppressLint({ "WorldWriteableFiles", "WorldReadableFiles" })
public class SharedPreferenceUtils {
	private static final String SHARED_PREFERENCES_NAME = "shared_preference_main_config";
	private static SharedPreferenceUtils mSharedPreferenceUtils = null;

	private SharedPreferenceUtils() {

	}
	/**
	 * 设置是否获取过通讯录
	 * 
	 * @param context
	 * @param cyd_dpposition
	 */
	public void setTXL(Activity context, boolean cyd_txl) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("cyd_txl", cyd_txl);
		editor.commit();
	}

	/**
	 * 获取是否获取过通讯录
	 * 
	 * @param context
	 * @return
	 */
	public boolean getTXL(Activity context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		boolean cyd_txl = preferences.getBoolean("cyd_txl",false);
		return cyd_txl;
	}
	/**
	 * 设置选中的店铺的索引
	 * 
	 * @param context
	 * @param cyd_dpposition
	 */
	public void setDPPosition(Activity context, int cyd_dpposition) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("cyd_dpposition", cyd_dpposition);
		editor.commit();
	}

	/**
	 * 获取选中的店铺的索引
	 * 
	 * @param context
	 * @return
	 */
	public int getDPPosition(Activity context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		int cyd_dpposition = preferences.getInt("cyd_dpposition", -1);
		return cyd_dpposition;
	}

	/**
	 * 设置消息时间
	 * 
	 * @param context
	 * @param cyd_xxtime
	 */
	public void setXXTime(Context context, String cyd_xxtime) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_xxtime", cyd_xxtime);
		editor.commit();
	}

	/**
	 * 获取消息时间
	 * 
	 * @param context
	 * @return
	 */
	public String getXXTime(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		String cyd_xxtime = preferences.getString("cyd_xxtime", "");
		return cyd_xxtime;
	}

	/**
	 * @description 设置联系人数量
	 * @param context
	 * @param contactsNum
	 */
	public void setContactsNum(Context context, int contactsNum) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("contacts_num", contactsNum);
		editor.commit();
	}

	/**
	 * @description 获取联系人数量
	 * @param context
	 */
	public int getContactsNum(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		int contacts_num = preferences.getInt("contacts_num", 0);
		return contacts_num;
	}

	/**
	 * 设置签到日期
	 */
	public void setQianDaoRiQi(Context context, String cyd_qdrq) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_qdrq", cyd_qdrq);
		editor.commit();
	}

	/**
	 * 得到签到日期
	 */
	public String getQianDaoRiQi(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_qdrq = preferences.getString("cyd_qdrq", null);
		return cyd_qdrq;
	}

	/**
	 * 设置签到id
	 */
	public void setQianDaoId(Context context, String cyd_qdid) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_qdid", cyd_qdid);
		editor.commit();
	}

	/**
	 * 得到签到id
	 */
	public String getQianDaoId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_qdid = preferences.getString("cyd_qdid", "0");
		return cyd_qdid;
	}

	/**
	 * 设置用户的token
	 */
	public void setToken(Context context, String cyd_token) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_token", cyd_token);
		editor.commit();
	}

	/**
	 * 得到用户的token
	 */
	public String getToken(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_token = preferences.getString("cyd_token", null);
		return cyd_token;
	}

	/**
	 * 设置是否展示我穿图片提示
	 */
	public void setWCShow(Context context, boolean b) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("wc_show", b);
		editor.commit();
	}

	/**
	 * 得到是否展示我穿图片提示
	 */
	public boolean getWCShow(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		boolean b = preferences.getBoolean("wc_show", true);
		return b;
	}

	/**
	 * 设置更新衣柜文件的时间
	 * 
	 * @param context
	 * @param cyd_updateTime_MT
	 */
	public void setUpdataTimeWardrobe(Context context,
			String cyd_updateTime_wardrobe) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_updateTime_wardrobe", cyd_updateTime_wardrobe);
		editor.commit();
	}

	/**
	 * 得到更新衣柜文件的时间
	 * 
	 * @param context
	 * @return
	 */
	public String getUpdataTimeWardrobe(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_updateTime_wardrobe = preferences.getString(
				"cyd_updateTime_wardrobe", "");
		return cyd_updateTime_wardrobe;
	}

	/**
	 * 设置更新模特文件的时间
	 * 
	 * @param context
	 * @param cyd_updateTime_MT
	 */
	public void setUpdataTimeMT(Context context, String cyd_updateTime_MT) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_updateTime_MT", cyd_updateTime_MT);
		editor.commit();
	}

	/**
	 * 得到更新模特文件的时间
	 * 
	 * @param context
	 * @return
	 */
	public String getUpdataTimeMT(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_updateTime_MT = preferences.getString("cyd_updateTime_MT",
				"");
		return cyd_updateTime_MT;
	}

	/**
	 * 设置是否第一次发布需求
	 */
	public void setFirstFBXQ(Context context, boolean firstFBXQ) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("FirstFBXQ", firstFBXQ);
		editor.commit();
	}

	/**
	 * 得到是否第一次发布需求
	 */
	public boolean getFirstFBXQ(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		boolean FirstFBXQ = preferences.getBoolean("FirstFBXQ", true);
		return FirstFBXQ;
	}

	public static synchronized SharedPreferenceUtils getInstance() {
		if (mSharedPreferenceUtils == null) {
			mSharedPreferenceUtils = new SharedPreferenceUtils();
		}
		return mSharedPreferenceUtils;
	}

	/**
	 * 设置是否第一次进入穿衣典界面
	 */
	public void setFirstGetIntoCYD(Context context, boolean FirstGetIntoCYD) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("FirstGetIntoCYD", FirstGetIntoCYD);
		editor.commit();
	}

	/**
	 * 得到是否第一次进入穿衣典界面
	 */
	public boolean getFirstGetIntoCYD(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		boolean FirstGetIntoCYD = preferences.getBoolean("FirstGetIntoCYD",
				true);
		return FirstGetIntoCYD;
	}

	/**
	 * 设置是否第一次进入方案
	 */
	public void setFirstGetIntoFangAn(Context context,
			boolean FirstGetIntoFangAn) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("FirstGetIntoFangAn", FirstGetIntoFangAn);
		editor.commit();
	}

	/**
	 * 得到是否第一次进入方案
	 */
	public boolean getFirstGetIntoFangAn(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		boolean FirstGetIntoFangAn = preferences.getBoolean(
				"FirstGetIntoFangAn", true);
		return FirstGetIntoFangAn;
	}

	/**
	 * @description 设置性别
	 * @parameters
	 */
	public void setSex(Context context, String cyd_sex) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_sex", cyd_sex);
		editor.commit();
	}

	/**
	 * @description: 获取性别
	 * @exception
	 */
	public String getSex(Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_sex = preferences.getString("cyd_sex", "未设置");
		return cyd_sex;
	}

	/**
	 * 设置测试分享结果
	 */
	public void setCeShiFenXiang(Context context, boolean csjg_fenxiang) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("csjg_fenxiang", csjg_fenxiang);
		editor.commit();
	}

	/**
	 * 获取测试分享结果
	 */
	public boolean getCeShiFenXiang(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		boolean csjg_url = preferences.getBoolean("csjg_fenxiang", false);
		return csjg_url;
	}

	/**
	 * 设置测试结果
	 */
	public void setCeShiJieGuo(Context context, String csjg_url) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("csjg_url", csjg_url);
		editor.commit();
	}

	/**
	 * 获取测试结果
	 */
	public String getCeShiJieGuo(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String csjg_url = preferences.getString("csjg_url", null);
		return csjg_url;
	}

	/**
	 * 是否分享过
	 */
	public void setIsShare(Context context, int user_id) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("isShare", user_id);
		editor.commit();
	}

	/**
	 * 是否分享过
	 */
	public int getIsShare(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		int user_id = preferences.getInt("isShare", 0);
		return user_id;
	}

	/**
	 * @description:设置生日
	 */
	public void setBirthday(Context context, String cyd_birthday) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_birthday", cyd_birthday);
		editor.commit();
	}

	/**
	 * @description:获取生日
	 */
	public String getBirthday(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_birthday = preferences.getString("cyd_birthday", "未设置");
		return cyd_birthday;
	}

	/**
	 * @description:设置身高
	 */
	public void setHeight(Context context, String cyd_height) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_height", cyd_height);
		editor.commit();
	}

	/**
	 * @description:获取身高
	 */
	public String getHeight(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_height = preferences.getString("cyd_height", "未设置");
		return cyd_height;
	}

	/**
	 * @description:设置体重
	 */
	public void setWeight(Context context, String cyd_weight) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_weight", cyd_weight);
		editor.commit();
	}

	/**
	 * @description:获取体重
	 */
	public String getWeight(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_weight = preferences.getString("cyd_weight", "未设置");
		return cyd_weight;
	}

	/**
	 * 是否是第一次登陆应用
	 */
	public void setIsFirst(Context context, boolean isFirst) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("is_first", isFirst);
		editor.commit();
	}

	/**
	 * 是否是第一次登陆应用
	 */
	public boolean getIsFirst(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		boolean b = preferences.getBoolean("is_first", true);
		return b;
	}

	/**
	 * @description:设置胸围
	 */
	public void setChest(Context context, String cyd_chest) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_chest", cyd_chest);
		editor.commit();
	}

	/**
	 * @description:获取胸围
	 */
	public String getChest(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_chest = preferences.getString("cyd_chest", "未设置");
		return cyd_chest;
	}

	/**
	 * @description:设置腰围
	 */
	public void setWaist(Context context, String cyd_waist) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_waist", cyd_waist);
		editor.commit();
	}

	/**
	 * @description:获取腰围
	 */
	public String getWaist(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_waist = preferences.getString("cyd_waist", "未设置");
		return cyd_waist;
	}

	/**
	 * @description:设置臀围
	 */
	public void setHipline(Context context, String cyd_hipline) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_hipline", cyd_hipline);
		editor.commit();
	}

	/**
	 * @description:获取臀围
	 */
	public String getHipline(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_hipline = preferences.getString("cyd_hipline", "未设置");
		return cyd_hipline;
	}

	/**
	 * @description:设置肩宽
	 */
	public void setShoulder(Context context, String cyd_shoulder) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_shoulder", cyd_shoulder);
		editor.commit();
	}

	/**
	 * @description:获取肩宽
	 */
	public String getShoulder(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_shoulder = preferences.getString("cyd_shoulder", "未设置");
		return cyd_shoulder;
	}

	/**
	 * 设置支付宝帐号
	 */
	public void setZFBAccount(Context context, String zfbaccount) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("zfbaccount", zfbaccount);
		editor.commit();
	}

	/**
	 * 获取支付宝帐号
	 */
	public String getZFBAccount(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String zfbaccount = preferences.getString("zfbaccount", null);
		return zfbaccount;
	}

	/**
	 * 设置支付宝姓名
	 */
	public void setZFBName(Context context, String zfbname) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("zfbname", zfbname);
		editor.commit();
	}

	/**
	 * 获取支付宝姓名
	 */
	public String getZFBName(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String zfbname = preferences.getString("zfbname", null);
		return zfbname;
	}

	/**
	 * @description:设置臂长
	 */
	public void setArm(Context context, String cyd_arm) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_arm", cyd_arm);
		editor.commit();
	}

	/**
	 * @description:获取臂长
	 */
	public String getArm(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_arm = preferences.getString("cyd_arm", "未设置");
		return cyd_arm;
	}

	/**
	 * 用户昵称
	 */
	public void setUserName(Context context, String userName) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_user_name", userName);
		editor.commit();
	}

	/**
	 * 获取用户昵称
	 */
	public String getUserName(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String userName = preferences.getString("cyd_user_name", null);
		return userName;
	}

	/**
	 * 设置用户等手机号
	 * 
	 * @param context
	 * @param phoneNum
	 */
	public void setUserPhoneNum(Context context, String cyd_user_phone) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_user_phone", cyd_user_phone);
		editor.commit();
	}

	/**
	 * 获取用户等手机号
	 */
	public String getUserPhoneNum(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_user_phone = preferences.getString("cyd_user_phone", null);
		return cyd_user_phone;
	}

	/**
	 * 设置用户手机的设备号
	 * 
	 * @param context
	 * @param phoneNum
	 */
	public void setSheBeiHao(Context context, String cyd_user_deviceCode) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_user_deviceCode", cyd_user_deviceCode);
		editor.commit();
	}

	/**
	 * 获取用户手机的设备号
	 */
	public String getSheBeiHao(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_user_deviceCode = preferences.getString(
				"cyd_user_deviceCode", null);
		return cyd_user_deviceCode;
	}

	/**
	 * @description:设置腿长
	 */
	public void setLeg(Context context, String cyd_leg) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_leg", cyd_leg);
		editor.commit();
	}

	/**
	 * @description:获取腿长
	 */
	public String getLeg(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_leg = preferences.getString("cyd_leg", "未设置");
		return cyd_leg;
	}

	/**
	 * @description:设置颈围
	 */
	public void setNeck(Context context, String cyd_neck) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_neck", cyd_neck);
		editor.commit();
	}

	/**
	 * @description:获取颈围
	 */
	public String getNeck(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_neck = preferences.getString("cyd_neck", "未设置");
		return cyd_neck;
	}

	/**
	 * @description:设置腕围
	 */
	public void setWrist(Context context, String cyd_wrist) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_wrist", cyd_wrist);
		editor.commit();
	}

	/**
	 * @description:获取腕围
	 */
	public String getWrist(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_wrist = preferences.getString("cyd_wrist", "未设置");
		return cyd_wrist;
	}

	/**
	 * @description:设置脚长
	 */
	public void setFoot(Context context, String cyd_foot) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_foot", cyd_foot);
		editor.commit();
	}

	/**
	 * @description:获取脚长
	 */
	public String getFoot(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_foot = preferences.getString("cyd_foot", "未设置");
		return cyd_foot;
	}

	/**
	 * @description:获取订单提示对话框是否显示
	 */
	public boolean getShowOrderHintDialog(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		boolean cyd_orderhint = preferences.getBoolean("cyd_orderhint", true);
		return cyd_orderhint;
	}

	/**
	 * @description:设置订单提示对话框是否显示
	 */
	public void setShowOrderHintDialog(Context context, boolean cyd_orderhint) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("cyd_orderhint", cyd_orderhint);
		editor.commit();
	}

	/**
	 * @description:设置余额
	 */
	public void setYue(Context context, String cyd_ybbj) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_yue", cyd_ybbj);
		editor.commit();
	}

	/**
	 * @description:获取余额
	 */
	public String getYue(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_ybbj = preferences.getString("cyd_yue", "0.00");
		return cyd_ybbj;
	}

	/**
	 * @description:设置衣扣
	 */
	public void setYK(Context context, String cyd_yk) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_yk", cyd_yk);
		editor.commit();
	}

	/**
	 * @description:获取衣扣
	 */
	public String getYK(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_yk = preferences.getString("cyd_yk", "0");
		return cyd_yk;
	}

	/**
	 * 保存wardrobe的json数据更新时间
	 * */
	public void setAllWardrobeUpdateTime(Context context, int time) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("all_wardrobe_time", time);
		editor.commit();
	}

	/**
	 * 得到wardrobe的json数据更新时间
	 * */
	public int getAllWardrobeUpdateTime(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		int time = preferences.getInt("all_wardrobe_time", 1);
		return time;
	}

	/**
	 * 保存某个的json数据更新时间
	 * */
	public void setTheWardrobeUpdateTime(Context context, int time) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("the_wardrobe_time", time);
		editor.commit();
	}

	/**
	 * 得到某个的json数据更新时间
	 * */
	public int getTheWardrobeUpdateTime(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		int time = preferences.getInt("the_wardrobe_time", 0);
		return time;
	}

	/**
	 * @description:设置用户衣柜ID
	 */
	public void setWardrobeID(Context context, String cyd_user_wardrobe_id) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_user_wardrobe_id", cyd_user_wardrobe_id);
		editor.commit();
	}

	/**
	 * @description:获取用户衣柜ID
	 */
	public String getWardrobeID(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		String cyd_user_wardrobe_id = preferences.getString(
				"cyd_user_wardrobe_id", null);
		return cyd_user_wardrobe_id;
	}

	/**
	 * @description:设置用户衣柜文件目录
	 */
	public void setWardrobePhoto(Context context, String cyd_wardrobe_photo) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_wardrobe_photo", cyd_wardrobe_photo);
		editor.commit();
	}

	/**
	 * @description:获取用户衣柜文件目录
	 */
	public String getWardrobePhoto(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		String cyd_wardrobe_photo = preferences.getString("cyd_wardrobe_photo",
				null);
		return cyd_wardrobe_photo;
	}

	/**
	 * @description:设置体型
	 */
	public void setMid(Context context, String cyd_mid) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_mid", cyd_mid);
		editor.commit();
	}

	/**
	 * @description:获取体型
	 */
	public String getMid(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		String cyd_mid = preferences.getString("cyd_mid", null);
		return cyd_mid;
	}

	/**
	 * 保存创建形象的合成形象的URL
	 * */
	public void setCreateImageXingXiang(Context context, String imageUrl) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("the_wardrobe_xingxiang", imageUrl);
		editor.commit();
	}

	/**
	 * 得到创建形象的合成形象的URL
	 * */
	public String getCreateImageXingXiang(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		String imageUrl = preferences.getString("the_wardrobe_xingxiang", null);
		return imageUrl;
	}

	/**
	 * 保存创建形象的身体数据
	 * */
	public void setCreateImageData(Context context, int age, int height,
			int weight, int xiongwei, int yaowei, int tunwei, int jiankuan,
			int yaoweigao, int bichang, int jingwei, int wanwei) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("the_wardrobe_age", age);
		editor.putInt("the_wardrobe_height", height);
		editor.putInt("the_wardrobe_weight", weight);
		editor.putInt("the_wardrobe_xiongwei", xiongwei);
		editor.putInt("the_wardrobe_yaowei", yaowei);
		editor.putInt("the_wardrobe_tunwei", tunwei);
		editor.putInt("the_wardrobe_jiankuan", jiankuan);
		editor.putInt("the_wardrobe_yaoweigao", yaoweigao);
		editor.putInt("the_wardrobe_jingwei", jingwei);
		editor.putInt("the_wardrobe_bichang", bichang);
		editor.putInt("the_wardrobe_wanwei", wanwei);
		editor.commit();
	}

	/**
	 * 得到创建形象的身体数据
	 * */
	public HashMap<String, Integer> getCreateImageData(Context context) {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		int age = preferences.getInt("the_wardrobe_age", 0);
		int height = preferences.getInt("the_wardrobe_height", 0);
		int weight = preferences.getInt("the_wardrobe_weight", 0);
		int xiongwei = preferences.getInt("the_wardrobe_xiongwei", 0);
		int yaowei = preferences.getInt("the_wardrobe_yaowei", 0);
		int tunwei = preferences.getInt("the_wardrobe_tunwei", 0);
		int jiankuan = preferences.getInt("the_wardrobe_jiankuan", 0);
		int yaoweigao = preferences.getInt("the_wardrobe_yaoweigao", 0);
		int bichang = preferences.getInt("the_wardrobe_bichang", 0);
		int jingwei = preferences.getInt("the_wardrobe_jingwei", 0);
		int wanwei = preferences.getInt("the_wardrobe_wanwei", 0);
		data.put("age", age);
		data.put("height", height);
		data.put("weight", weight);
		data.put("xiongwei", xiongwei);
		data.put("yaowei", yaowei);
		data.put("tunwei", tunwei);
		data.put("jiankuan", jiankuan);
		data.put("yaoweigao", yaoweigao);
		data.put("bichang", bichang);
		data.put("jingwei", jingwei);
		data.put("wanwei", wanwei);
		return data;
	}

	/**
	 * @param mContext
	 * @param user_id
	 * @description 设置用户的ID
	 * @version
	 * @author
	 * @update 2013-12-2 上午09:43:11
	 */
	public void setUserId(Context context, String user_id) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_user_id", user_id);
		editor.commit();
	}

	/**
	 * @param mContext
	 * @param user_id
	 * @description 得到用户的ID
	 * @version
	 * @author
	 * @update 2013-12-2 上午09:43:11
	 */
	public String getUserId(Context context) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		String user_id = preferences.getString("cyd_user_id", "");
		return user_id;
	}

	/**
	 * @param mContext
	 * @description 设置用户是否登录的标识
	 * @version
	 * @author
	 * @update 2013-12-2 下午02:41:21
	 */
	public void setIsLogin(Context mContext, boolean isLogin) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("isLogin", isLogin);
		editor.commit();
	}

	/**
	 * @param mContext
	 * @description 得到用户已登录的标识
	 * @version
	 * @author
	 * @update 2013-12-2 下午02:41:21
	 */
	public boolean getIsLogin(Context mContext) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		boolean isLogin = preferences.getBoolean("isLogin", false);
		return isLogin;
	}

	/**
	 * @param mContext
	 * @description 设置创建形象的--形象名称
	 * @version
	 * @author
	 * @update 2013-12-2 下午02:41:21
	 */
	public void setCreateImageXingXiangName(Context mContext,
			String xingxiangname) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("create_xingxiang_name", xingxiangname);
		editor.commit();
	}

	/**
	 * @param mContext
	 * @description 得到创建形象的--形象名称
	 * @version
	 * @author
	 * @update 2013-12-2 下午02:41:21
	 */
	public String getCreateImageXingXiangName(Context mContext) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		String xingxiangname = preferences.getString("create_xingxiang_name",
				null);
		return xingxiangname;
	}

	/**
	 * @description:设置默认支付方式
	 */
	public void setDefaultPayment(Context mContext, String cyd_defaultpayment) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_defaultpayment", cyd_defaultpayment);
		editor.commit();
	}

	/**
	 * @description: 获取默认支付方式
	 */
	public String getDefaultPayment(Context mContext) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		String cyd_defaultpayment = preferences.getString("cyd_defaultpayment",
				"支付宝");
		return cyd_defaultpayment;
	}

	/**
	 * @param mContext
	 * @param mima
	 * @description 设置密码
	 * @version
	 * @author
	 * @update 2013-12-25 上午10:10:53
	 */
	public void setUserPassword(Context mContext, String mima) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_password", mima);
		editor.commit();
	}

	/**
	 * @param mContext
	 * @description 得到密码
	 * @version
	 * @author
	 * @update 2013-12-2 下午02:41:21
	 */
	public String getUserPassword(Context mContext) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		String password = preferences.getString("cyd_password", "000000");
		return password;
	}

	/**
	 * 设置上传试穿形象的路径
	 * 
	 * @param context
	 * @param mrxx_name
	 */
	public void setTryResultSave(Context context, String cyd_try_result_save) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_try_result_save", cyd_try_result_save);
		editor.commit();
	}

	/**
	 * 获取上传试穿形象的路径
	 * 
	 * @param context
	 * @return
	 */
	public String getTryResultSave(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_try_result_save = preferences.getString(
				"cyd_try_result_save", "");
		return cyd_try_result_save;
	}

	/** 获取验证码 */
	public String getYZM(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String yzm = preferences.getString("yzm", null);
		return yzm;
	}

	/**
	 * 设置验证码
	 */
	public void setYZM(Context context, String yzm) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("yzm", yzm);
		editor.commit();
	}

	/** 获取未参与集结号 */
	public int getWCYJJH(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		int wcyjjh = preferences.getInt("wcyjjh", 0);
		return wcyjjh;
	}

	/**
	 * 设置验证码
	 */
	public void setWCYJJH(Context context, int wcyjjh) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("wcyjjh", wcyjjh);
		editor.commit();
	}

	/**
	 * @description 设置用户是否免分享
	 * @parameters
	 */
	public void setIsMianFenXiang(Context context, boolean b) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("isMFX", b);
		editor.commit();
	}

	/** 获取用户是否免分享 */
	public boolean getIsMianFenXiang(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		boolean isMFX = preferences.getBoolean("isMFX", false);
		return isMFX;
	}

	/**
	 * @description 设置用户头像地址
	 * @parameters
	 */
	public void setUserHead(Context context, String user_head) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("user_head", user_head);
		editor.commit();
	}

	/** 获取用户的头像地址 */
	public String getUserHead(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String isMFX = preferences.getString("user_head", null);
		return isMFX;
	}

	/**
	 * @description 设置是否第一次裁剪图片
	 * @parameters
	 */
	public void setIsFirstCutImage(Context context, boolean isFirstCutImage) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("isFirstCutImage", isFirstCutImage);
		editor.commit();
	}

	/**
	 * @description: 得到裁剪图片的状态
	 * @exception
	 */
	public Boolean getIsFirstCutImage(Context mContext) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		Boolean isFirstCutImage = preferences.getBoolean("isFirstCutImage",
				false);
		return isFirstCutImage;
	}

	/**
	 * @description 设置是否第一次编辑形象
	 * @parameters
	 */
	public void setisFirstEditImage(Context context, boolean isFirstEditImage) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("isFirstEditImage", isFirstEditImage);
		editor.commit();
	}

	/**
	 * @description: 得到是否第一次编辑形象
	 * @exception
	 */
	public Boolean getisFirstEditImage(Context mContext) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		Boolean isFirstEditImage = preferences.getBoolean("isFirstEditImage",
				true);
		return isFirstEditImage;
	}

	/**
	 * @description 获取可提现金额
	 */
	public String getMoneyCash(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_moneyCash = preferences.getString("cyd_moneyCash", null);
		return cyd_moneyCash;
	}

	/**
	 * @description 设置可提现金额
	 */
	public void setMoneyCash(Context context, String cyd_moneyCash) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_moneyCash", cyd_moneyCash);
		editor.commit();
	}

	/**
	 * @description 获取会员ID
	 */
	public String getHuId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_hyId = preferences.getString("cyd_hyId", null);
		return cyd_hyId;
	}

	/**
	 * @description 设置会员ID
	 */
	public void setHuId(Context context, String cyd_hyId) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_hyId", cyd_hyId);
		editor.commit();
	}

	/**
	 * @description 获取量体师的标识
	 */
	public String getMeasurer(Context context) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_measurer = preferences.getString("cyd_measurer", null);
		return cyd_measurer;
	}

	/**
	 * @description 设置量体师的标识
	 */
	public void setMeasurer(Context context, String cyd_measurer) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_measurer", cyd_measurer);
		editor.commit();
	}

	/**
	 * @description 获取身份
	 */
	public String getShenFen(Context context) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_shenfen = preferences.getString("cyd_shenfen", "用户");
		return cyd_shenfen;
	}

	/**
	 * @description 设置身份
	 */
	public void setShenFen(Context context, String cyd_shenfen) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_shenfen", cyd_shenfen);
		editor.commit();
	}

	/**
	 * @description 获取上线ID
	 */
	public String getShangXianID(Context context) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String cyd_shenfen = preferences.getString("cyd_shangxianid", "");
		return cyd_shenfen;
	}

	/**
	 * @description 设置上线ID
	 */
	public void setShangXianID(Context context, String cyd_shangxianid) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("cyd_shangxianid", cyd_shangxianid);
		editor.commit();
	}
}
