package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.dressbook.ui.model.LiangTiShuJu;
import cn.dressbook.ui.model.YJTCMolder;
import cn.dressbook.ui.utils.StringUtil;

/**
 * 
 * @author 熊天富
 * @描述 解析业绩提成列表
 * @时间 2016年2月16日16:43:49
 * 
 */
public class LTSJJson {
	private static LTSJJson mAdviserJson;

	private LTSJJson() {
	}

	public static LTSJJson getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new LTSJJson();
		}
		return mAdviserJson;
	}
	public ArrayList<LiangTiShuJu> getLTSJList(String json) throws JSONException {
		if (json == null) {
			return null;
		}
		JSONObject object = new JSONObject(json);
		if (object == null) {
			return null;
		}
		JSONObject jsonInfo=object.getJSONObject("info");
		if (jsonInfo == null) {
			return null;
		}
		String lastOper=jsonInfo.optString("lastOper");
		String lastOpTime=jsonInfo.optString("lastOpTime");
		String time=StringUtil.jieduanString(lastOpTime);
		if (jsonInfo.isNull("mbFigureDataList")) {
			return null;
		}
		JSONArray jsonList=jsonInfo.getJSONArray("mbFigureDataList");
		ArrayList<LiangTiShuJu> arrayList=new ArrayList<LiangTiShuJu>();
		LiangTiShuJu shuJu;
		for (int i = 0; i < jsonList.length(); i++) {
			JSONObject jsoni=jsonList.getJSONObject(i);
			shuJu=new LiangTiShuJu();
			shuJu.setLastOper(lastOper);
			shuJu.setLastOpTime(time);
			shuJu.setId(jsoni.optString("id"));
			shuJu.setName(jsoni.optString("name"));
			shuJu.setCls(jsoni.optString("cls"));
			shuJu.setDescr(jsoni.optString("descr"));
			shuJu.setPic(jsoni.optString("pic"));
			shuJu.setCeliangValue("");
			shuJu.setValue(jsoni.optString("value"));
			shuJu.setUnit(jsoni.optString("unit"));
			ArrayList<String> arrayStr=new ArrayList<String>();
			JSONArray jsonArray=jsoni.getJSONArray("prevalArr");
			for (int j = 0;j< jsonArray.length(); j++) {
				arrayStr.add(jsonArray.optString(j));
			}
			shuJu.setPrevalArr(arrayStr);
			arrayList.add(shuJu);
			shuJu=null;
			
		}
		return arrayList;
	}

}
