package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cn.dressbook.ui.model.CydMessage;

public class MessageJson {
	private final static String TAG = MessageJson.class.getSimpleName();
	private static MessageJson mInstance = null;

	private MessageJson() {
	}

	public static MessageJson getInstance() {
		if (mInstance == null) {
			mInstance = new MessageJson();
		}
		return mInstance;
	}

	/**
	 * @description:解析消息
	 */
	public CydMessage analysisMessage(String string) {
		CydMessage msg=null;
		try {
			JSONObject jsonObj = new JSONObject(string);
			msg=new CydMessage();
			msg.setFileName(jsonObj.optString("fileName"));
			msg.settext(jsonObj.optString("text"));
			msg.setTitle(jsonObj.optString("title"));
			msg.settime(jsonObj.optString("time"));
			msg.settype(jsonObj.optString("type"));

		} catch (Exception e) {
		}
		return msg;
	}

	
}
