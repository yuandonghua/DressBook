package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.dressbook.ui.bean.FindBwBean;

public class FindBwJson {
	private FindBwJson() {
	}

	private static FindBwJson mFindBwJson;

	public static FindBwJson getInstace() {
		if (mFindBwJson == null) {
			mFindBwJson = new FindBwJson();
		}
		return mFindBwJson;
	}

	/**
	 * 解析博文列表
	 */
	public ArrayList<FindBwBean> analyzeFindBwList(String json)
			throws JSONException {
		ArrayList<FindBwBean> findBwList=null;
		if (json==null) {
			return null;
		}
		JSONObject job=new JSONObject(json);
		if (job==null) {
			return null;
		}
		JSONArray jarry=job.getJSONArray("info");
		findBwList=new ArrayList<FindBwBean>();
		if (jarry==null) {
			return null;
		}FindBwBean bean;
		for (int i = 0; i < jarry.length(); i++) {
			JSONObject object=jarry.getJSONObject(i);
			if (object==null) {
				return null;
			}
			 bean=new FindBwBean();
			 bean.setAddTimeShow(object.optString("addTimeShow"));
			 bean.setId(object.optString("id"));
			 bean.setTitle(object.optString("title"));
			 bean.setUserAvatar(object.optString("userAvatar"));
			 bean.setUserId(object.optString("userId"));
			 bean.setUserLevel(object.optString("userLevel"));
			 bean.setUserName(object.optString("userName"));
			 findBwList.add(bean);
			 bean=null;
		}
		return findBwList;

	}

}
