package cn.dressbook.ui.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Path;

import cn.dressbook.ui.bean.AixinjuanyiBeanDonateAddress;
import cn.dressbook.ui.bean.AixinjuanyiBeanProject;
import cn.dressbook.ui.bean.AixinjuanyiBeanProjectProgress;
import cn.dressbook.ui.bean.AixinjuanyiBeanRecord;
import cn.dressbook.ui.bean.AixinjuanyiBeanRecordProject;
import cn.dressbook.ui.common.PathCommonDefines;

/**
 * @description 解析爱心捐衣数据
 * @author 袁东华
 * @time 2015-12-10下午1:49:54
 */
public class AiXinJuanYiJson {
	private static AiXinJuanYiJson mAdviserJson;

	private AiXinJuanYiJson() {
	}

	public static AiXinJuanYiJson getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new AiXinJuanYiJson();
		}
		return mAdviserJson;
	}

	/**
	 * 解析爱心捐衣项目进度
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<AixinjuanyiBeanProjectProgress> analysisDonateProgres(
			String json) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject(json);
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		if (!info.isNull("donateProgres")) {
			JSONArray donateProgres = info.getJSONArray("donateProgres");
			ArrayList<AixinjuanyiBeanProjectProgress> listProjectProgress = new ArrayList<AixinjuanyiBeanProjectProgress>();
			for (int i = 0; i < donateProgres.length(); i++) {
				JSONObject obji = donateProgres.getJSONObject(i);
				AixinjuanyiBeanProjectProgress progress = new AixinjuanyiBeanProjectProgress();
				progress.setId(obji.optLong("id"));
				progress.setTitle(obji.optString("title"));
				progress.setContent(obji.optString("content"));
				progress.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ obji.optString("pic"));
				progress.setAddTimeShow(obji.optString("addTimeShow"));
				progress.setOccTimeShow(obji.optString("occTimeShow"));
				listProjectProgress.add(progress);
			}
			return listProjectProgress;

		}
		return null;
	}

	/**
	 * 解析爱心捐衣项目地址
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public AixinjuanyiBeanDonateAddress analysisDonateAddress(String json)
			throws JSONException {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject(json);
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		if (!info.isNull("donateAddress")) {
			JSONObject donateAddress = info.getJSONObject("donateAddress");
			AixinjuanyiBeanDonateAddress address = new AixinjuanyiBeanDonateAddress();
			address.setId(donateAddress.optLong("id"));
			address.setName(donateAddress.optString("name"));
			address.setPostcode(donateAddress.optString("postcode"));
			address.setTel(donateAddress.optString("tel"));
			address.setProvince(donateAddress.optString("province"));
			address.setCity(donateAddress.optString("city"));
			address.setDistrict(donateAddress.optString("district"));
			address.setAddr(donateAddress.optString("addr"));
			address.setAddrShow(donateAddress.optString("addrShow"));
			return address;

		}
		return null;
	}

	/**
	 * 解析爱心捐衣项目详情
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public AixinjuanyiBeanProject analysisDonateProject(String json)
			throws JSONException {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject(json);
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		if (!info.isNull("donateProject")) {
			JSONObject donateProject = info.getJSONObject("donateProject");
			AixinjuanyiBeanProject axjy = new AixinjuanyiBeanProject();
			axjy.setId(donateProject.optLong("id"));
			axjy.setTitle(donateProject.optString("title"));
			axjy.setContent(donateProject.optString("content"));
			axjy.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
					+ donateProject.optString("pic"));
			axjy.setSponsor(donateProject.optString("sponsor"));
			axjy.setSurl(donateProject.optString("surl"));
			axjy.setAddTimeShow(donateProject.optString("addTimeShow"));
			axjy.setIsPraise(donateProject.optInt("isPraise"));
			axjy.setIsJoin(donateProject.optInt("isJoin"));
			axjy.setPraiseNum(donateProject.optLong("praiseNum"));
			axjy.setJoinNum(donateProject.optLong("joinNum"));
			axjy.setState(donateProject.optInt("state"));
			return axjy;

		}
		return null;
	}

	/**
	 * 解析爱心记录列表
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<AixinjuanyiBeanRecord> analysisAXJLList(String json)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<AixinjuanyiBeanRecord> newProjectList = null;
		JSONObject obj = new JSONObject(json);
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		if (info.isNull("listDonateJoin")) {
			return null;
		}
		JSONArray listDonateJoin = info.getJSONArray("listDonateJoin");

		newProjectList = new ArrayList<AixinjuanyiBeanRecord>();
		for (int i = 0; i < listDonateJoin.length(); i++) {
			JSONObject obji = listDonateJoin.getJSONObject(i);
			if (obji == null) {
				return null;
			}
			AixinjuanyiBeanRecord axjy = new AixinjuanyiBeanRecord();
			axjy.setId(obji.optLong("id"));
			axjy.setLogistics(obji.optString("logistics"));
			axjy.setYq(obji.optInt("yq"));
			axjy.setAddTimeShow(obji.optString("addTimeShow"));
			axjy.setYqTimeShow(obji.optString("yqTimeShow"));
			axjy.setState(obji.optInt("state"));
			if (!obji.isNull("donateProjectShow")) {
				AixinjuanyiBeanRecordProject rp = new AixinjuanyiBeanRecordProject();
				JSONObject donateProjectShow = obji
						.getJSONObject("donateProjectShow");
				rp.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ donateProjectShow.optString("pic"));
				rp.setId(donateProjectShow.optLong("id"));
				rp.setTitle(donateProjectShow.optString("title"));
				axjy.setDonateProjectShow(rp);
			}
			newProjectList.add(axjy);
		}
		return newProjectList;
	}

	/**
	 * 解析爱心捐衣列表
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<AixinjuanyiBeanProject> analysisAXJYList(String json)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<AixinjuanyiBeanProject> newProjectList = null;
		JSONObject obj = new JSONObject(json);
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		if (info.isNull("listDonateProject")) {
			return null;
		}
		JSONArray listDonateProject = info.getJSONArray("listDonateProject");

		newProjectList = new ArrayList<AixinjuanyiBeanProject>();
		for (int i = 0; i < listDonateProject.length(); i++) {
			JSONObject obji = listDonateProject.getJSONObject(i);
			if (obji == null) {
				return null;
			}
			AixinjuanyiBeanProject axjy = new AixinjuanyiBeanProject();
			axjy.setId(obji.optLong("id"));
			axjy.setTitle(obji.optString("title"));
			axjy.setContent(obji.optString("content"));
			axjy.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
					+ obji.optString("pic"));
			axjy.setSponsor(obji.optString("sponsor"));
			axjy.setSurl(obji.optString("surl"));
			axjy.setAddTimeShow(obji.optString("addTimeShow"));
			axjy.setIsPraise(obji.optInt("isPraise"));
			axjy.setIsJoin(obji.optInt("isJoin"));
			axjy.setPraiseNum(obji.optLong("praiseNum"));
			axjy.setJoinNum(obji.optLong("joinNum"));
			axjy.setState(obji.optInt("state"));
			newProjectList.add(axjy);
		}
		return newProjectList;
	}

}
