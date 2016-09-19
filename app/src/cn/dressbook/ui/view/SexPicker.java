package cn.dressbook.ui.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import cn.dressbook.ui.R;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.view.ScrollerNumberPicker2.OnSelectListener;

/**
 * @description: 性别选择器
 * @author:袁东华
 * @time:2015-9-21下午2:16:59
 */
public class SexPicker extends LinearLayout {
	/** 滑动控件 */
	private ScrollerNumberPicker2 sexPicker;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempdayIndex = -1;
	private Context context;
	ArrayList<String> sexList;
	public String sex = "男";

	public SexPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initData();
	}

	public SexPicker(Context context) {
		super(context);
		this.context = context;
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		sexList = new ArrayList<String>();
		sexList.add("男");
		sexList.add("女");

	}

	// 获取城市信息
	public void setData() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.sex_picker, this);
		// 获取控件引用
		sexPicker = (ScrollerNumberPicker2) findViewById(R.id.sex);
		sexPicker.setData(sexList, "");
		sex = SharedPreferenceUtils.getInstance().getSex(context);
		if ("男".equals(sex)) {
			sexPicker.setDefault(0);
		} else if ("女".equals(sex)) {
			sexPicker.setDefault(1);
		} else {
			sexPicker.setDefault(0);
			sex = "男";
		}
		sexPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (tempdayIndex != id) {

					if (id > sexList.size()) {
						sexPicker.setDefault(sexList.size() - 1);
					} else {
						sex = sexList.get(id);
					}
				}
				tempdayIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
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
