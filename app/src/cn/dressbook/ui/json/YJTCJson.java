package cn.dressbook.ui.json;

import org.json.JSONException;
import org.json.JSONObject;

import cn.dressbook.ui.model.YJTCMolder;

/**
 * 
 * @author 熊天富
 * @描述 解析业绩提成列表
 * @时间 2016年2月16日16:43:49
 * 
 */
public class YJTCJson {
	private static YJTCJson mAdviserJson;

	private YJTCJson() {
	}

	public static YJTCJson getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new YJTCJson();
		}
		return mAdviserJson;
	}
	public YJTCMolder getYJTC(String json) throws JSONException {
		YJTCMolder molder = new YJTCMolder();
		if (json == null) {
			return null;
		}
		JSONObject object = new JSONObject(json);
		if (object == null) {
			return null;
		}
		JSONObject jsonInfo = object.getJSONObject("info");
		if (jsonInfo == null) {
			return null;
		}
		if (jsonInfo.isNull("mbInfo")) {
			return null;
		}
		JSONObject jsonMbInfo = jsonInfo.getJSONObject("mbInfo");
		molder.setTotalIncom(jsonInfo.optString("totalIncom"));
		molder.setId(jsonMbInfo.optString("id"));
		molder.setCurTimeShow(jsonMbInfo.optString("curTimeShow"));
		molder.setMbTimeShow(jsonMbInfo.optString("mbTimeShow"));
		molder.setMoneyAvailable(jsonMbInfo.optString("moneyAvailable"));
		molder.setMoneyCash(jsonMbInfo.optString("moneyCash"));
		molder.setMoneyConting(jsonMbInfo.optString("moneyConting"));
		molder.setMoneyOrder(jsonMbInfo.optString("moneyOrder"));
		molder.setShareCode(jsonMbInfo.optString("shareCode"));
		molder.setMembers(jsonInfo.optString("members"));
		if (jsonInfo.isNull("alipayInfo")) {
			return null;
		}
		JSONObject jsonAlipay=jsonInfo.getJSONObject("alipayInfo");
		molder.setAlipay(jsonAlipay.optString("alipay"));
		molder.setAlipayName(jsonAlipay.optString("alipayName"));
		molder.setFlag(jsonAlipay.optString("flag"));
		molder.setUser_id(jsonAlipay.optString("user_id"));
		molder.setMobile(jsonAlipay.optString("molder"));
		return molder;
	}

}
