package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.dressbook.ui.model.CustomService;




public class CustomServiceJson {
	private static CustomServiceJson mCustomServiceJson;
	

	private CustomServiceJson() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static CustomServiceJson getInstance(){
		if(mCustomServiceJson==null){
			mCustomServiceJson=new CustomServiceJson();
		}
		return mCustomServiceJson;
	}
	public ArrayList<CustomService> analyzeCustomServiceJson(JSONArray jsonArray) {
		ArrayList<CustomService> mList=null;
		try {
			if (jsonArray != null) {
				mList=new ArrayList<CustomService>();
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObj=(JSONObject)jsonArray.get(i);
					if(jsonObj!=null){
						CustomService cs=new CustomService();
						cs.setId(jsonObj.optString("id"));
						cs.setName(jsonObj.optString("nickName"));
						cs.setOnline(jsonObj.optString("online"));
						cs.setInTime(jsonObj.optString("inTime"));
						cs.setOutTime(jsonObj.optString("outTime"));
						mList.add(cs);
					}
				}
				
			}
		} catch (Exception e) {
		}
		return mList;
	}
}
