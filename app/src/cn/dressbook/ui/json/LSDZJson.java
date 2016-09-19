package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.model.CiXiu;
import cn.dressbook.ui.model.CityInfo;
import cn.dressbook.ui.model.DZSP;
import cn.dressbook.ui.model.DZSPDetails;
import cn.dressbook.ui.model.DZSPDetailsImgs;
import cn.dressbook.ui.model.DZSPDetailsParams;
import cn.dressbook.ui.model.DZSPFL;
import cn.dressbook.ui.model.LSDZFL;
import cn.dressbook.ui.model.Mianliao;
import cn.dressbook.ui.model.Preval;
import cn.dressbook.ui.model.TiaoZhenCanShu;

/**
 * @description 解析量身定制数据
 * @author 袁东华
 * @date 2016-1-22
 */
public class LSDZJson {
	private static LSDZJson mAdviserJson;

	private LSDZJson() {
	}

	public static LSDZJson getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new LSDZJson();
		}
		return mAdviserJson;
	}

	/**
	 * @description:解析定制商品详情
	 */
	public DZSPDetails analyzeDZSPDetails(String json) throws JSONException {
		// TODO Auto-generated method stub
		if (json == null) {
			return null;
		}
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return null;
		}
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		DZSPDetails mDZSPDetails = new DZSPDetails();
		mDZSPDetails.setId(info.optString("id"));
		mDZSPDetails.setTitle(info.optString("title"));
		mDZSPDetails.setPrice(info.optString("price"));
		mDZSPDetails.setPriceVip(info.optString("priceVip"));
		mDZSPDetails.setState(info.optString("state"));
		mDZSPDetails.setExtinf(info.optString("extinf"));
		mDZSPDetails.setTitlePic(PathCommonDefines.SERVER_IMAGE_ADDRESS
				+ info.optString("titlePic"));
		// 解析参数
		if (!info.isNull("dzAttireParams")) {
			JSONArray dzAttireParams = info.getJSONArray("dzAttireParams");
			ArrayList<DZSPDetailsParams> mDZSPDetailsParamsList = new ArrayList<DZSPDetailsParams>();
			for (int i = 0; i < dzAttireParams.length(); i++) {
				JSONObject obji = dzAttireParams.getJSONObject(i);
				if (obji == null) {
					return null;
				}
				DZSPDetailsParams mDZSPDetailsParams = new DZSPDetailsParams();
				mDZSPDetailsParams.setId(obji.optString("id"));
				mDZSPDetailsParams.setparamId(obji.optString("paramId"));
				mDZSPDetailsParams.setparamName(obji.optString("paramName"));
				mDZSPDetailsParams.setparamValue(obji.optString("paramValue"));
				mDZSPDetailsParams.setcustom(obji.optString("custom"));
				mDZSPDetailsParamsList.add(mDZSPDetailsParams);
			}
			mDZSPDetails.setParams(mDZSPDetailsParamsList);
		}
		// 解析图片
		if (!info.isNull("dzAttireImgs")) {
			JSONArray dzAttireImgs = info.getJSONArray("dzAttireImgs");
			ArrayList<DZSPDetailsImgs> mDZSPDetailsImgsList = new ArrayList<DZSPDetailsImgs>();
			for (int i = 0; i < dzAttireImgs.length(); i++) {
				JSONObject obji = dzAttireImgs.getJSONObject(i);
				if (obji == null) {
					return null;
				}
				DZSPDetailsImgs mDZSPDetailsImgs = new DZSPDetailsImgs();
				mDZSPDetailsImgs.setId(obji.optString("id"));
				mDZSPDetailsImgs.setUrl(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ obji.optString("url"));
				mDZSPDetailsImgs.setPath(obji.optString("path"));
				mDZSPDetailsImgs.setSuffix(obji.optString("suffix"));
				mDZSPDetailsImgs.setWidth(obji.optString("width"));
				mDZSPDetailsImgs.setHeight(obji.optString("height"));
				mDZSPDetailsImgs.setSort(obji.optString("sort"));
				mDZSPDetailsImgs.setAddTimeShow(obji.optString("addTimeShow"));
				mDZSPDetailsImgs.setAddTime(obji.optString("addTime"));
				mDZSPDetailsImgsList.add(mDZSPDetailsImgs);
			}
			mDZSPDetails.setImages(mDZSPDetailsImgsList);
		}
		return mDZSPDetails;
	}

	/**
	 * @description:解析量身定制分类列表
	 */
	public ArrayList<LSDZFL> analyzeLSDZFLList(String json)
			throws JSONException {
		// TODO Auto-generated method stub
		if (json == null) {
			return null;
		}
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return null;
		}
		if (obj.isNull("info")) {
			return null;
		}
		JSONArray info = obj.getJSONArray("info");
		// 解析量身定制分类
		ArrayList<LSDZFL> dzspflList = new ArrayList<LSDZFL>();
		for (int i = 0; i < info.length(); i++) {
			JSONObject obji = info.getJSONObject(i);
			if (obji == null) {
				return null;
			}
			LSDZFL lsdzfl = new LSDZFL();
			lsdzfl.setDzCls_id(obji.optString("DzCls_id"));
			lsdzfl.setDzCls_name(obji.optString("DzCls_name"));
			lsdzfl.setDzCls_pic(PathCommonDefines.SERVER_IMAGE_ADDRESS
					+ obji.optString("DzCls_url"));
			lsdzfl.setDzCls_sex(obji.optString("DzCls_sex"));

			dzspflList.add(lsdzfl);
		}
		return dzspflList;
	}

	/**
	 * @description:解析量身定制列表
	 */
	public ArrayList<DZSPFL> analyzeLSDZList(String json) throws JSONException {
		// TODO Auto-generated method stub
		if (json == null) {
			return null;
		}
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return null;
		}
		if (obj.isNull("info")) {
			return null;
		}
		JSONArray info = obj.getJSONArray("info");
		// 解析定制商品分类
		ArrayList<DZSPFL> dzspflList = new ArrayList<DZSPFL>();
		for (int i = 0; i < info.length(); i++) {
			JSONObject obji = info.getJSONObject(i);
			if (obji == null) {
				return null;
			}
			DZSPFL dzspfl = new DZSPFL();
			dzspfl.setDzCls_id(obji.optString("DzCls_id"));
			dzspfl.setDzCls_name(obji.optString("DzCls_name"));
			dzspfl.setDzCls_pic(PathCommonDefines.SERVER_IMAGE_ADDRESS
					+ obji.optString("DzCls_pic"));
			dzspfl.setDzCls_sex(obji.optString("DzCls_sex"));
			// 解析定制商品
			if (!obji.isNull("DzAttire")) {
				JSONArray DzAttire = obji.getJSONArray("DzAttire");
				ArrayList<DZSP> dzspList = new ArrayList<DZSP>();
				for (int j = 0; j < DzAttire.length(); j++) {
					JSONObject objj = DzAttire.getJSONObject(j);
					if (objj == null) {
						return null;
					}
					DZSP dzsp = new DZSP();
					dzsp.setDzAttire_id(objj.optString("DzAttire_id"));
					dzsp.setDzAttire_title(objj.optString("DzAttire_title"));
					dzsp.setDzAttire_price(objj.optString("DzAttire_price"));
					dzsp.setDzAttire_priceVip(objj
							.optString("DzAttire_priceVip"));
					dzsp.setDzAttire_titlePic(PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ objj.optString("DzAttire_titlePic"));
					dzspList.add(dzsp);
				}
				dzspfl.setDzAttire(dzspList);
			}

			dzspflList.add(dzspfl);
		}
		return dzspflList;
	}

	/**
	 * @description:解析种类定制列表
	 */
	public DZSPFL analyzeZlDZList(String json) throws JSONException {
		// TODO Auto-generated method stub
		if (json == null) {
			return null;
		}
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return null;
		}
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		// 解析定制商品分类
		DZSPFL dzspfl = new DZSPFL();
		dzspfl.setDzCls_id(info.optString("DzCls_id"));
		dzspfl.setDzCls_name(info.optString("DzCls_name"));
		dzspfl.setDzCls_pic(PathCommonDefines.SERVER_IMAGE_ADDRESS
				+ info.optString("DzCls_pic"));
		dzspfl.setDzCls_sex(info.optString("DzCls_sex"));
		// 解析定制商品
		if (!info.isNull("DzAttire")) {
			JSONArray DzAttire = info.getJSONArray("DzAttire");
			ArrayList<DZSP> dzspList = new ArrayList<DZSP>();
			for (int j = 0; j < DzAttire.length(); j++) {
				JSONObject objj = DzAttire.getJSONObject(j);
				if (objj == null) {
					return null;
				}
				DZSP dzsp = new DZSP();
				dzsp.setDzAttire_id(objj.optString("DzAttire_id"));
				dzsp.setDzAttire_title(objj.optString("DzAttire_title"));
				dzsp.setDzAttire_price(objj.optString("DzAttire_price"));
				dzsp.setDzAttire_priceVip(objj.optString("DzAttire_priceVip"));
				dzsp.setDzAttire_titlePic(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ objj.optString("DzAttire_titlePic"));
				dzspList.add(dzsp);
			}
			dzspfl.setDzAttire(dzspList);
		}

		return dzspfl;
	}


	/**
	 * @description:解析DIY列表
	 */
	public DZSPDetails analyzeDIYDetails(String json) throws JSONException {
		// TODO Auto-generated method stub
		if (json == null) {
			return null;
		}
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return null;
		}
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		DZSPDetails mDZSPDetails = new DZSPDetails();
		mDZSPDetails.setId(info.optString("DzAttire_id"));
		mDZSPDetails.setTitle(info.optString("DzAttire_title"));
		mDZSPDetails.setPrice(info.optInt("DzAttire_prieNet") + "");
		mDZSPDetails.setTemplateId(info.optString("template_id"));
		mDZSPDetails.setTitlePic(PathCommonDefines.SERVER_IMAGE_ADDRESS
				+ info.optString("DzAttire_titlePic"));
		// 解析参数
		if (!info.isNull("DzAttireParam")) {
			JSONArray dzAttireParams = info.getJSONArray("DzAttireParam");
			ArrayList<DZSPDetailsParams> mDZSPDetailsParamsList = new ArrayList<DZSPDetailsParams>();
			for (int i = 0; i < dzAttireParams.length(); i++) {
				JSONObject obji = dzAttireParams.getJSONObject(i);
				if (obji == null) {
					return null;
				}
				DZSPDetailsParams mDZSPDetailsParams = new DZSPDetailsParams();
				mDZSPDetailsParams.setId(obji.optString("id"));
				mDZSPDetailsParams.setparamId(obji.optString("paramId"));
				mDZSPDetailsParams.setparamName(obji.optString("paramName"));
				mDZSPDetailsParams.setparamValue(obji.optString("paramValue"));
				mDZSPDetailsParams.setcustom(obji.optString("custom"));
				mDZSPDetailsParams
						.setParamPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
								+ obji.optString("pic"));
				if (!obji.isNull("prevalList")) {

					JSONArray jsonPre = obji.getJSONArray("prevalList");
					ArrayList<TiaoZhenCanShu> preList = new ArrayList<TiaoZhenCanShu>();
					for (int j = 0; j < jsonPre.length(); j++) {
						JSONObject jsonobj = jsonPre.getJSONObject(j);
						TiaoZhenCanShu tzCanShu = new TiaoZhenCanShu();
						tzCanShu.setId(jsonobj.optString("paramId"));
						tzCanShu.setMaterialPrice(jsonobj
								.optString("materialPrice"));
						tzCanShu.settitle(obji.optString("paramName"));
						tzCanShu.setcontent(jsonobj.optString("value"));
						tzCanShu.setMaterial(jsonobj.optString("material"));
						tzCanShu.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
								+ jsonobj.optString("pic"));
						preList.add(tzCanShu);
					}
					mDZSPDetailsParams.setPrevalList(preList);
				}
				mDZSPDetailsParamsList.add(mDZSPDetailsParams);
			}
			mDZSPDetails.setParams(mDZSPDetailsParamsList);
		}
		return mDZSPDetails;
	}

	/**
	 * @description 解析定制商品刺绣信息
	 */
	public ArrayList<CiXiu> analyzeDZSPCX(JSONArray color) {
		// TODO Auto-generated method stub
		try {
			ArrayList<CiXiu> cxList = new ArrayList<CiXiu>();
			for (int i = 0; i < color.length(); i++) {
				JSONObject obj_i = color.getJSONObject(i);
				CiXiu cx = new CiXiu();
				cx.setName(obj_i.optString("value"));
				cx.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ obj_i.optString("pic"));
				cxList.add(cx);
			}
			return cxList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
