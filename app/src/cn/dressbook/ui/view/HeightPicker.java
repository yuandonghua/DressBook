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
 * @description: 身高选择器
 * @author:袁东华
 * @time:2015-9-21下午2:16:59
 */
public class HeightPicker extends LinearLayout {
	/** 滑动控件 */
	private ScrollerNumberPicker2 heightPicker;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempdayIndex = -1;
	private Context context;
	ArrayList<String> heightList;
	public String height = "170";
	public int position;

	public HeightPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initData();
	}

	public HeightPicker(Context context) {
		super(context);
		this.context = context;
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		height = SharedPreferenceUtils.getInstance().getHeight(context);
		heightList = new ArrayList<String>();
		for (int i = 199; i >= 122; i--) {
			heightList.add(i + "");
		}
		if (!height.equals("未设置")&&!height.equals("")) {

			position = 199 - Integer.parseInt(height);
		} else {
			position = 29;
			height = "170";
		}
	}

	// 获取城市信息
	public void setData() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.height_picker, this);
		// 获取控件引用
		heightPicker = (ScrollerNumberPicker2) findViewById(R.id.height);
		heightPicker.setData(heightList, "cm");
		heightPicker.setDefault(position);
		heightPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (tempdayIndex != id) {

					if (id > heightList.size()) {
						heightPicker.setDefault(heightList.size() - 1);
					} else {
						height = heightList.get(id);
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

	private ArrayList<String> getCurrentDayList(String height, String month) {
		ArrayList<String> daytList = new ArrayList<String>();
		int y = Integer.parseInt(height);
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
