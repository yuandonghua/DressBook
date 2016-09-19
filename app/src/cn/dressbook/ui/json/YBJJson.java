package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.dressbook.ui.model.YBJ;

public class YBJJson {
	private static YBJJson mAdviserJson;

	private YBJJson() {
	}

	public static YBJJson getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new YBJJson();
		}
		return mAdviserJson;
	}

	/**
	 * @throws JSONException
	 * @description:解析衣保金消费记录
	 * @exception
	 */
	public ArrayList<YBJ> analyzeYBJ(String json) throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<YBJ> ybjList = null;
		if (json == null) {
			return null;
		}
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return null;
		}
		JSONArray arr = obj.getJSONArray("info");
		if (arr == null) {
			return null;
		}
		ybjList = new ArrayList<YBJ>();
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obji = arr.getJSONObject(i);
			if (obji == null) {
				return null;
			}
			YBJ ybj = new YBJ();
			ybj.setId(obji.optString("id"));
			ybj.setBaseValue(obji.optString("baseValue"));
			ybj.setYbValue(obji.optString("ybValue"));
			ybj.setOccurTime(obji.optString("occurTimeShow"));
			ybj.setMemo(obji.optString("memo"));
			ybj.setOper(obji.optString("oper"));
			ybj.setState(obji.optString("state"));
			ybjList.add(ybj);
		}
		return ybjList;
	}

}
