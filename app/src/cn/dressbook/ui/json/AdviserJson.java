package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.Adviser;

public class AdviserJson {
	private static AdviserJson mAdviserJson;

	private AdviserJson() {
	}

	public static AdviserJson getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new AdviserJson();
		}
		return mAdviserJson;
	}

	/**
	 * @throws JSONException
	 * @description:解析顾问师列表
	 * @exception
	 */
	public ArrayList<Adviser> analyzeAdviserList(String json)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<Adviser> adviserList = null;
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
		adviserList = new ArrayList<Adviser>();
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obji = arr.getJSONObject(i);
			if (obji == null) {
				return null;
			}
			Adviser adviser = new Adviser();
			adviser.setAdviserId(obji.optString("adviserId"));
			adviser.setAttireSex(obji.optString("attireSex"));
			adviser.setAutographPath(PathCommonDefines.SERVER_IMAGE_ADDRESS
					+ "/" + obji.optString("autographPath"));
			adviser.setGuanzhu(obji.optString("guanzhu"));
			adviser.setIdea(obji.optString("idea"));
			adviser.setName(obji.optString("name"));
			adviser.setPhotoPath(PathCommonDefines.SERVER_IMAGE_ADDRESS + "/"
					+ obji.optString("photoPath"));
			adviser.setZan(obji.optString("zan"));
			adviser.setOnline(obji.optString("online"));
			adviser.setKfId(obji.optString("kfId"));
			adviser.setPostsId(obji.optString("postsId"));
			adviser.setPostsTime(obji.optString("postsTime"));
			adviser.setTitle(obji.optString("title"));
			adviser.setResume(obji.optString("resume"));
			adviser.setPostsTitle(obji.optString("postsTitle"));
			adviser.setUserId(obji.optString("userId"));
			adviserList.add(adviser);
		}
		return adviserList;
	}

	/**
	 * @throws JSONException
	 * @description:解析顾问师详情
	 * @exception
	 */
	public Adviser analyzeAdviserDetail(String json) throws JSONException {
		// TODO Auto-generated method stub
		Adviser adviser = null;
		if (json == null) {
			return null;
		}
		JSONObject obj = new JSONObject(json);
		JSONObject info = obj.getJSONObject("info");
		adviser = new Adviser();
		adviser.setAdviserId(info.optString("adviserId"));
		adviser.setName(info.optString("name"));
		adviser.setAttireSex(info.optString("attireSex"));
		adviser.setAutographPath(PathCommonDefines.SERVER_IMAGE_ADDRESS + "/"
				+ info.optString("autographPath"));
		adviser.setIdea(info.optString("idea"));
		adviser.setPhotoPath(PathCommonDefines.SERVER_IMAGE_ADDRESS + "/"
				+ info.optString("photoPath"));
		adviser.setSchool(info.optString("school"));
		adviser.setMajor(info.optString("major"));
		adviser.setStyle(info.optString("style"));
		adviser.setPostsId(info.optString("postsId"));
		adviser.setPostsTime(info.optString("postsTime"));
		adviser.setUserId(info.optString("userId"));
		adviser.setTitle(info.optString("title"));
		adviser.setResume(info.optString("resume"));
		adviser.setPostsTitle(info.optString("postsTitle"));
		JSONArray advshowsarr = info.getJSONArray("showpic");
		if (advshowsarr != null && advshowsarr.length() > 0) {
			ArrayList<String> showpicList = new ArrayList<String>();
			for (int j = 0; j < advshowsarr.length(); j++) {
				showpicList.add(PathCommonDefines.SERVER_IMAGE_ADDRESS 
						+"/"+ advshowsarr.optString(j));
			
			}
			adviser.setShowpic(showpicList);
		}
		return adviser;
	}
}
