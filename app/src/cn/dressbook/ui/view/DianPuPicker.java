package cn.dressbook.ui.view;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;

import u.aly.dp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import cn.dressbook.ui.R;
import cn.dressbook.ui.model.DianPu;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.view.ScrollerNumberPicker2.OnSelectListener;

/**
 * @description 店铺选择器
 * @author 袁东华
 * @date 2016-3-17
 */
public class DianPuPicker extends LinearLayout {
	/** 滑动控件 */
	private ScrollerNumberPicker2 sexPicker;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	public int tempdayIndex = -1;
	private Context context;
	public ArrayList<DianPu> dpList;
	public ArrayList<String> list = new ArrayList<String>();

	public DianPuPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public DianPuPicker(Context context) {
		super(context);
		this.context = context;
	}

	public void setData(ArrayList<DianPu> dpList) {
		// TODO Auto-generated method stub

		this.dpList = dpList;
		for (int i = 0; i < dpList.size(); i++) {
			list.add(dpList.get(i).getfranchiseeName());
		}
		sexPicker.setData(list, "");
		sexPicker.setDefault(0);
		tempdayIndex=0;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.sex_picker, this);
		// 获取控件引用
		sexPicker = (ScrollerNumberPicker2) findViewById(R.id.sex);
		
		sexPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (tempdayIndex != id) {

					if (id > list.size()) {
						sexPicker.setDefault(list.size() - 1);
					} else {
					}
				}
				tempdayIndex = id;
//				Message message = new Message();
//				message.what = REFRESH_VIEW;
//				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub

			}
		});
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_VIEW:
				// if (onSelectingListener != null)
				// onSelectingListener.selected(true);
				break;
			default:
				break;
			}
		}

	};

	public interface OnSelectingListener {

		public void selected(boolean selected);
	}

	private ArrayList<String> getCurrentDayList(String sex, String month) {
		ArrayList<String> daytList = new ArrayList<String>();
		int y = Integer.parseInt(sex);
		int m = Integer.parseInt(month);
		if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10
				|| m == 12) {
			for (int i = 31; i >= 1; i--) {
				daytList.add(i + "");
			}
		} else if (m != 2) {
			for (int i = 30; i >= 1; i--) {
				daytList.add(i + "");
			}
		}

		if (y % 4 == 0 || y % 400 == 0) {
			if (m == 2) {
				for (int i = 29; i >= 1; i--) {
					daytList.add(i + "");
				}
			}
		} else {
			if (m == 2) {
				for (int i = 28; i >= 1; i--) {
					daytList.add(i + "");
				}
			}
		}
		return daytList;
	}
}
