package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.model.CityInfo;

public class AddressJson {
	private static AddressJson mAdviserJson;

	private AddressJson() {
	}

	public static AddressJson getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new AddressJson();
		}
		return mAdviserJson;
	}

	/**
	 * @description:解析默认收货地址
	 */
	public Address analyzeDefaultAddress(String json) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return null;
		}
		JSONArray info = obj.getJSONArray("info");
		if (info == null || info.length() != 1) {
			return null;
		}
		JSONObject obji = info.getJSONObject(0);
		Address adviser = new Address();
		adviser.setId(obji.optString("id"));
		adviser.setConsignee(obji.optString("consignee"));
		adviser.setMobile(obji.optString("mobile"));
		adviser.setZipcode(obji.optString("zipcode"));
		adviser.setProvince(obji.optString("province"));
		adviser.setCity(obji.optString("city"));
		adviser.setDistrict(obji.optString("district"));
		adviser.setAddress(obji.optString("address"));
		adviser.setState(obji.optString("state"));
		return adviser;
	}

	/**
	 * @description:解析收货地址列表
	 */
	public ArrayList<Address> analyzeAddressList(String json)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<Address> addressList = null;
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
		addressList = new ArrayList<Address>();
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obji = arr.getJSONObject(i);
			if (obji == null) {
				return null;
			}
			Address adviser = new Address();
			adviser.setId(obji.optString("id"));
			adviser.setConsignee(obji.optString("consignee"));
			adviser.setMobile(obji.optString("mobile"));
			adviser.setZipcode(obji.optString("zipcode"));
			adviser.setProvince(obji.optString("province"));
			adviser.setCity(obji.optString("city"));
			adviser.setDistrict(obji.optString("district"));
			adviser.setAddress(obji.optString("address"));
			adviser.setState(obji.optString("state"));
			addressList.add(adviser);
		}
		return addressList;
	}

	/**
	 * @description:解析城市列表
	 */
	public void analyzeCityList(String json) throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<CityInfo> provinceList = new ArrayList<CityInfo>();
		ArrayList<CityInfo> cityList = new ArrayList<CityInfo>();
		ArrayList<CityInfo> districtList = new ArrayList<CityInfo>();
		if (json == null) {
			return;
		}
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return;
		}
		JSONArray arr = obj.getJSONArray("info");
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obji = arr.getJSONObject(i);
			if (obji != null) {
				CityInfo province = new CityInfo();
				province.setId(obji.optString("regionId"));
				province.setParentId(obji.optString("parentId"));
				province.setName(obji.optString("regionName"));
				provinceList.add(province);
				if (!obji.isNull("city")) {

					JSONArray cityArr = obji.getJSONArray("city");
					if (cityArr != null || !"[]".equals(cityArr)) {
						for (int j = 0; j < cityArr.length(); j++) {
							JSONObject objj = cityArr.getJSONObject(j);
							if (objj != null) {
								CityInfo city = new CityInfo();
								city.setId(objj.optString("regionId"));
								city.setParentId(objj.optString("parentId"));
								city.setName(objj.optString("regionName"));
								cityList.add(city);
								if (!objj.isNull("district")) {
									//
									JSONArray districtArr = objj
											.getJSONArray("district");
									if (districtArr != null
											&& !"[]".equals(districtArr)
											&& districtArr.length() > 0) {
										//
										for (int k = 0; k < districtArr
												.length(); k++) {
											JSONObject objk = districtArr
													.getJSONObject(k);
											if (objk != null) {
												CityInfo district = new CityInfo();
												district.setParentId(objk
														.optString("parentId"));
												district.setName(objk
														.optString("regionNames"));
												districtList.add(district);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		ManagerUtils.getInstance().setProvinceList(provinceList);
		ManagerUtils.getInstance().setCityList(cityList);
		ManagerUtils.getInstance().setDistrictList(districtList);
	}
}
