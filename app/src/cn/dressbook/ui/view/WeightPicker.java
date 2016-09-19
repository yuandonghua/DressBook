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
 * @description: 体重选择器
 * @author:袁东华
 * @time:2015-9-21下午2:16:59
 */
public class WeightPicker extends LinearLayout {
	/** 滑动控件 */
	private ScrollerNumberPicker2 weightPicker;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempdayIndex = -1;
	private Context context;
	ArrayList<String> weightList;
	public String weight="60";
	public int position;

	public WeightPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initData();
	}

	public WeightPicker(Context context) {
		super(context);
		this.context = context;
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		weight = SharedPreferenceUtils.getInstance().getWeight(context);
		weightList = new ArrayList<String>();
		for (int i = 120; i >= 35; i--) {
			weightList.add(i + "");
		}
		if (!weight.equals("未设置")&&!weight.equals("")) {

			position = 120 - Integer.parseInt(weight);
		} else {
			position=60;
			weight="60";
		}
	}

	// 获取城市信息
	public void setData() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.weight_picker, this);
		// 获取控件引用
		weightPicker = (ScrollerNumberPicker2) findViewById(R.id.weight);

		weightPicker.setData(weightList, "kg");
		weightPicker.setDefault(position);
		weightPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (tempdayIndex != id) {

					if (id > weightList.size()) {
						weightPicker.setDefault(weightList.size() - 1);
					} else {
						weight = weightList.get(id);
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

	private ArrayList<String> getCurrentDayList(String weight, String month) {
		ArrayList<String> daytList = new ArrayList<String>();
		int y = Integer.parseInt(weight);
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
