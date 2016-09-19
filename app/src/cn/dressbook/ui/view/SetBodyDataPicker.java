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
 * @description: 身体数据选择器
 * @author:袁东华
 * @time:2015-9-22上午11:50:05
 */
public class SetBodyDataPicker extends LinearLayout {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	/** 滑动控件 */
	private ScrollerNumberPicker2 dataPicker;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempdayIndex = -1;
	private Context context;
	ArrayList<String> mList;
	public String data;
	public int position;
	public String currentNum;

	public SetBodyDataPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public SetBodyDataPicker(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.weight_picker, this);
		// 获取控件引用
		dataPicker = (ScrollerNumberPicker2) findViewById(R.id.weight);

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

	public void setData() {
		// TODO Auto-generated method stub
		dataPicker.setData(mList, "cm");
		if (!"未设置".equals(currentNum) && !"0".equals(currentNum)) {
			for (int i = 0; i < mList.size(); i++) {
				if (currentNum.equals(mList.get(i))) {
					dataPicker.setDefault(i);
					data = mList.get(i);
				}
			}
		} else {
			dataPicker.setDefault(0);
			data = mList.get(0);

		}
		dataPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (tempdayIndex != id) {

					if (id > mList.size()) {
						dataPicker.setDefault(mList.size() - 1);
					} else {
						data = mList.get(id);
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

	/**
	 * @description:设置类型和内容
	 */
	public void setType(String type) {
		// TODO Auto-generated method stub
		if (mList != null) {
			mList.clear();
		}
		if (mList == null) {
			mList = new ArrayList<String>();
		}
		switch (type) {
		case "胸围":
			currentNum = mSharedPreferenceUtils.getChest(getContext());
			for (int i = 65; i <= 125; i++) {
				mList.add(i + "");
			}
			break;
		case "腰围":
			currentNum = mSharedPreferenceUtils.getWaist(getContext());
			for (int i = 55; i <= 115; i++) {
				mList.add(i + "");
			}
			break;
		case "臀围":
			currentNum = mSharedPreferenceUtils.getHipline(getContext());
			for (int i = 65; i <= 125; i++) {
				mList.add(i + "");
			}
			break;
		case "肩宽":
			currentNum = mSharedPreferenceUtils.getShoulder(getContext());
			for (int i = 32; i <= 46; i++) {
				mList.add(i + "");
			}
			break;
		case "臂长":
			currentNum = mSharedPreferenceUtils.getArm(getContext());
			for (int i = 46; i <= 60; i++) {
				mList.add(i + "");
			}
			break;

		case "腿长":
			currentNum = mSharedPreferenceUtils.getLeg(getContext());
			for (int i = 84; i <= 110; i++) {
				mList.add(i + "");
			}
			break;
		case "颈围":
			currentNum = mSharedPreferenceUtils.getNeck(getContext());
			for (int i = 29; i <= 41; i++) {
				mList.add(i + "");
			}
			break;
		case "腕围":
			currentNum = mSharedPreferenceUtils.getWrist(getContext());
			for (int i = 14; i <= 30; i++) {
				mList.add(i + "");
			}
			break;
		case "脚长":
			currentNum = mSharedPreferenceUtils.getFoot(getContext());
			mList.add("20");
			mList.add("20.5");
			mList.add("21");
			mList.add("21.5");
			mList.add("22");
			mList.add("22.5");
			mList.add("23");
			mList.add("23.5");
			mList.add("24");
			mList.add("24.5");
			mList.add("25");
			mList.add("25.5");
			mList.add("26");
			mList.add("26.5");
			mList.add("27");
			mList.add("27.5");
			mList.add("28");
			mList.add("28.5");
			mList.add("29");
			mList.add("29.5");
			mList.add("30");
			mList.add("30.5");
			mList.add("31");
			mList.add("31.5");
			mList.add("32");

			break;

		default:
			break;
		}
		setData();

	}
}
