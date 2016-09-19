package cn.dressbook.ui.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.CiXiu;
import cn.dressbook.ui.model.DingDanCiXiu;
import cn.dressbook.ui.model.DzOda;
import cn.dressbook.ui.model.LogisticsInfo;
import cn.dressbook.ui.model.LogisticsInfo2;
import cn.dressbook.ui.model.MSTJData;
import cn.dressbook.ui.model.OrderForm;
import cn.dressbook.ui.model.TiaoZhenCanShu;

/**
 * @description: 订单json解析
 * @author:袁东华
 * @time:2015-9-10下午3:45:12
 */
public class OrderJson {
	private static OrderJson mmstjJson;

	private OrderJson() {
	}

	public static OrderJson getInstance() {

		if (mmstjJson == null) {
			mmstjJson = new OrderJson();
		}
		return mmstjJson;
	}

	/**
	 * @description:解析订单列表
	 */
	public HashMap<String, ArrayList<OrderForm>> analyzeOrderList(String json)
			throws JSONException {
		// TODO Auto-generated method stub
		JSONObject jsonObj = new JSONObject(json);
		if (jsonObj.isNull("info")) {
			return null;
		}
		HashMap<String, ArrayList<OrderForm>> map = new HashMap<String, ArrayList<OrderForm>>();
		JSONObject info = jsonObj.getJSONObject("info");
		Iterator<String> keys = info.keys();
		ArrayList<OrderForm> alllist = new ArrayList<OrderForm>();
		while (keys.hasNext()) {
			String key = keys.next();
			if (!"".equals(key)) {
				if (!info.isNull(key)) {
					ArrayList<OrderForm> list = new ArrayList<OrderForm>();
					JSONArray values = info.getJSONArray(key);
					for (int i = 0; i < values.length(); i++) {
						JSONObject value = values.getJSONObject(i);
						OrderForm of = analyzeOrderInfo(value);
						list.add(of);
						alllist.add(of);
					}
					map.put(key, list);
				}
			}

		}
		map.put("全部", alllist);
		return map;
	}

	/**
	 * @description:解析订单详情
	 */
	public OrderForm analyzeOrderInfo(JSONObject order) throws JSONException {
		// TODO Auto-generated method stub
		if (order == null) {
			return null;
		}
		OrderForm of = new OrderForm();

		of.setId(order.optString("id"));
		int state = order.optInt("state");
		of.setState(state + "");
		of.setPriceTotal(order.optString("priceTotalShow"));
		of.setPriceNet(order.optString("priceNetShow"));
		of.setAddTime(order.optString("addTimeShow"));
		of.setAddrConsignee(order.optString("addrConsignee"));
		of.setAddrCompose(order.optString("addrCompose"));
		of.setAddrMobile(order.optString("addrMobile"));
		of.setOrderFormTime(order.optString("orderFormTime"));
		of.setAddrZipcode(order.optString("addrZipcode"));
		of.setxfm(order.optString("consumeCode"));
		of.setstateShow(order.optString("stateShow"));
		of.setFreight(order.optString("freightShow"));
		of.setpayYb(order.optString("payYb"));
		of.setPayMoney(order.optString("payMoney"));
		if (!order.isNull("dzOda")) {

			JSONObject dzOda = order.getJSONObject("dzOda");
			DzOda d = new DzOda();

			d.setId(dzOda.optString("id"));
			d.setTitle(dzOda.optString("title"));
			d.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS + "/"
					+ dzOda.optString("pic"));
			d.setDzattireId(dzOda.optString("dzattireId"));
			d.setTemplateId(order.optString("templateId"));
			of.setDzOda(d);
			if (!dzOda.isNull("odaParams")) {
				ArrayList<TiaoZhenCanShu> tzcsList = new ArrayList<TiaoZhenCanShu>();
				JSONArray odaParams = dzOda.getJSONArray("odaParams");
				for (int n = 0; n < odaParams.length(); n++) {
					JSONObject objn = odaParams.getJSONObject(n);
					TiaoZhenCanShu tzcs = new TiaoZhenCanShu();
					tzcs.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ objn.optString("pic"));
					tzcs.setId(objn.optString("paramId"));
					tzcs.setcontent(objn.optString("paramValue"));
					tzcs.settitle(objn.optString("paramName"));
					tzcs.setMaterial(objn.optString("paramMaterial"));
					tzcsList.add(tzcs);
					
				}
				of.setTzcsList(tzcsList);
			}
			if(!dzOda.isNull("dzOdaEbd")){
				JSONObject dzOdaEbd = dzOda.getJSONObject("dzOdaEbd");
				DingDanCiXiu ddcx=new DingDanCiXiu();
				ddcx.setColorPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ dzOdaEbd.optString("colorPic"));
				ddcx.setFontPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ dzOdaEbd.optString("fontPic"));
				ddcx.setWords(dzOdaEbd.optString("words"));
				of.setDingDanCiXiu(ddcx);
				
			}
		}
		return of;
	}

	/**
	 * @description:解析物流信息
	 */
	public LogisticsInfo analyzeLogisticInfo(String json) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return null;
		}
		LogisticsInfo logisticsInfo = new LogisticsInfo();
		logisticsInfo.setCompanytype(obj.optString("companytype"));
		logisticsInfo.setCodenumber(obj.optString("codenumber"));
		JSONArray data = obj.getJSONArray("data");
		if (data != null && data.length() > 0) {
			ArrayList<LogisticsInfo2> logisticsInfo2list = new ArrayList<LogisticsInfo2>();
			for (int i = 0; i < data.length(); i++) {
				JSONObject obji = data.getJSONObject(i);
				if (obji == null) {
					return null;
				}

				LogisticsInfo2 logisticsInfo2 = new LogisticsInfo2();
				logisticsInfo2.setTime(obji.optString("time"));
				logisticsInfo2.setContext(obji.optString("context"));
				logisticsInfo2list.add(logisticsInfo2);
			}
			logisticsInfo.setData(logisticsInfo2list);
		}
		return logisticsInfo;
	}

	/**
	 * @description:解析购物车商品列表
	 */
	public ArrayList<MSTJData> analyzeShoppingList(JSONArray orderAttire)
			throws JSONException {
		// TODO Auto-generated method stub
		if (orderAttire == null) {
			return null;
		}
		ArrayList<MSTJData> addressList = new ArrayList<MSTJData>();
		for (int i = 0; i < orderAttire.length(); i++) {
			JSONObject obji = orderAttire.getJSONObject(i);
			if (obji == null) {
				return null;
			}
			MSTJData mstj = new MSTJData();
			mstj.setId(obji.optString("id"));
			mstj.setAtid(obji.optString("attireId"));
			mstj.setAttireFrom(obji.optString("attireFrom"));
			mstj.setAdviserId(obji.optString("adviserId"));
			mstj.setPicUrl(PathCommonDefines.SERVER_IMAGE_ADDRESS + "/"
					+ obji.optString("pic"));
			mstj.setShopPrice(obji.optString("price"));
			mstj.setSize(obji.optString("size"));
			mstj.setColor(obji.optString("color"));
			mstj.setNum(obji.optString("num"));
			mstj.setYk(obji.optString("yq"));
			mstj.setPriceCur(obji.optString("priceCur"));
			mstj.setPriceNet(obji.optString("priceNet"));
			mstj.setAdesc(obji.optString("sdesc"));
			addressList.add(mstj);
		}
		return addressList;
	}

}
