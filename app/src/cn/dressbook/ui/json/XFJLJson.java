package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.dressbook.ui.model.XFJL;

/**
 * @description: 消费记录
 * @author:袁东华
 * @time:2015-11-9下午3:18:08
 */
public class XFJLJson {
	private static XFJLJson mAdviserJson;

	private XFJLJson() {
	}

	public static XFJLJson getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new XFJLJson();
		}
		return mAdviserJson;
	}

	/**
	 * @throws JSONException
	 * @description:解析衣保金消费记录
	 * @exception
	 */
	public ArrayList<XFJL> analysisXFJL(JSONArray info) throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<XFJL> xfjlList = new ArrayList<XFJL>();
		for (int i = 0; i < info.length(); i++) {
			JSONObject obji = info.getJSONObject(i);
			if (obji != null) {
				XFJL xfjl = new XFJL();
				xfjl.setoccurTimeShow(obji.optString("occurTimeShow"));
				xfjl.setmemo(obji.optString("memo"));
				xfjl.setvalueShow(obji.optString("valueShow"));
				xfjlList.add(xfjl);
			}
		}
		return xfjlList;
	}

}
