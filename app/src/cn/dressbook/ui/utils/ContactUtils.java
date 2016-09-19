package cn.dressbook.ui.utils;

import java.util.ArrayList;
import java.util.List;

import org.xutils.common.util.LogUtil;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.util.Log;
import cn.dressbook.ui.bean.ContactInfo;

/**
 * @description: 获取通讯录
 * @author:ydh
 * @data:2016-4-19上午10:19:43
 */
public class ContactUtils {
	private static ContactUtils instance;

	private ContactUtils() {
		// TODO Auto-generated constructor stub
	}

	public static ContactUtils getInstance() {
		if (instance == null) {
			synchronized (ContactUtils.class) {
				if (instance == null) {
					instance = new ContactUtils();
				}
			}
		}
		return instance;
	}

	public List<ContactInfo> getAllContacts(Activity context) {
		try {

			ContentResolver resolver = context.getContentResolver();
			Cursor cursor = resolver.query(Phone.CONTENT_URI, new String[] {
					Phone.DISPLAY_NAME, Phone.NUMBER, "sort_key" }, null, null,
					"sort_key COLLATE LOCALIZED ASC");
			if (cursor == null || cursor.getCount() == 0) {
				LogUtil.e("没有获取到联系人信息");
				return null;
			}
			if (cursor.getCount() > 0) {

				List<ContactInfo> list = new ArrayList<ContactInfo>();
				// 获取手机号的字段索引
				int PHONES_NUMBER_INDEX = cursor.getColumnIndex(Phone.NUMBER);
				// 获取昵称的字段索引
				int PHONES_DISPLAY_NAME_INDEX = cursor
						.getColumnIndex(Phone.DISPLAY_NAME);

				while (cursor.moveToNext()) {
					// 根据索引获取手机号
					String number = cursor.getString(PHONES_NUMBER_INDEX);
					if (TextUtils.isEmpty(number)) {
						// 手机号为空,进入下个循环
						continue;
					}
					// 根据索引获取昵称
					String name = cursor.getString(PHONES_DISPLAY_NAME_INDEX);
					ContactInfo ci = new ContactInfo(name, number);
					list.add(ci);
				}
				// 关闭
				cursor.close();
				return list;
			}
		} catch (Exception e) {
			LogUtil.e("异常:" + e.getMessage());
		}
		return null;
	}

}
