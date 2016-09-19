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
import cn.dressbook.ui.view.ScrollerNumberPicker3.OnSelectListener;

/**
 * @description: 生日选择器
 * @author:袁东华
 * @time:2016-9-21上午11:02:49
 */
public class BirthdayPicker2 extends LinearLayout {
	/** 滑动控件 */
	private ScrollerNumberPicker3 yearPicker;
	private ScrollerNumberPicker3 monthPicker;
	private ScrollerNumberPicker3 dayPicker;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempyearIndex = -1;
	private int temmonthIndex = -1;
	private int tempdayIndex = -1;
	private Context context;
	private String month_string;
	ArrayList<String> yearList;
	ArrayList<String> monthList;
	ArrayList<String> dayList;
	public String year;
	public String month;
	public String day;
	public int position1, position2, position3;

	public BirthdayPicker2(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initData();
	}

	public BirthdayPicker2(Context context) {
		super(context);
		this.context = context;
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		yearList = new ArrayList<String>();
		monthList = new ArrayList<String>();
		dayList = new ArrayList<String>();
		for (int i = 2016; i >= 1950; i--) {
			yearList.add(i + "");
		}
		for (int i = 12; i >= 1; i--) {
			monthList.add(i + "");
		}
		for (int i = 31; i >= 1; i--) {
			dayList.add(i + "");
		}
		String birthday = SharedPreferenceUtils.getInstance().getBirthday(
				context);
		if (!birthday.equals("未设置") && birthday != null && !birthday.equals("")) {
			year = birthday.split("-")[0];
			month = birthday.split("-")[1];
			day = birthday.split("-")[2];
			position1 = 2016 - Integer.parseInt(year);
			position2 = 12 - Integer.parseInt(month);
//			dayList = getCurrentDayList(year, month);
			position3 = dayList.size() - Integer.parseInt(day);
		} else {
			birthday = "2016-12-31";
			year = birthday.split("-")[0];
			month = birthday.split("-")[1];
			day = birthday.split("-")[2];
			position1 = 2016 - Integer.parseInt(year);
			position2 = 12 - Integer.parseInt(month);
//			dayList = getCurrentDayList(year, month);
			position3 = dayList.size() - Integer.parseInt(day);
		}
	}

	public void setData(String birthday) {
		// TODO Auto-generated method stub
		if (!birthday.equals("未设置") && birthday != null 
				&& !birthday.equals("")
				&& birthday.contains("-")) {
			year = birthday.split("-")[0];
			month = birthday.split("-")[1];
			day = birthday.split("-")[2];
			position1 = 2016 - Integer.parseInt(year);
			position2 = 12 - Integer.parseInt(month);
//			dayList = getCurrentDayList(year, month);
			position3 = dayList.size() - Integer.parseInt(day);
		} else {
			birthday = "1988-6-8";
			year = birthday.split("-")[0];
			month = birthday.split("-")[1];
			day = birthday.split("-")[2];
			position1 = 2016 - Integer.parseInt(year);
			position2 = 12 - Integer.parseInt(month);
//			dayList = getCurrentDayList(year, month);
			position3 = dayList.size() - Integer.parseInt(day);
		}
		yearPicker.setData(yearList, "年");
		yearPicker.setDefault(position1);
		if (monthList != null && monthList.size() > 0) {
			monthPicker.setData(monthList, "月");
			monthPicker.setDefault(position2);
			if (dayList != null && dayList.size() > 0) {
				dayPicker.setData(dayList, "日");
				dayPicker.setDefault(position3);
			} else {
			}
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.birthday_picker2,
				this);
		// 获取控件引用
		yearPicker = (ScrollerNumberPicker3) findViewById(R.id.year);
		monthPicker = (ScrollerNumberPicker3) findViewById(R.id.month);
		dayPicker = (ScrollerNumberPicker3) findViewById(R.id.day);
		yearPicker.setData(yearList, "年");
		yearPicker.setDefault(position1);
		if (monthList != null && monthList.size() > 0) {
			monthPicker.setData(monthList, "月");
			monthPicker.setDefault(position2);
			if (dayList != null && dayList.size() > 0) {
				dayPicker.setData(dayList, "日");
				dayPicker.setDefault(position3);
			} else {
			}
		}
		// 年
		yearPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (tempyearIndex != id) {
					year = yearList.get(id);
//					if (monthList != null && monthList.size() > 0) {
//						monthPicker.setData(monthList, "月");
//						monthPicker.setDefault(0);
//						month = monthList.get(0);
//						dayList = getCurrentDayList(year, month);
//						if (dayList != null && dayList.size() > 0) {
//							dayPicker.setData(dayList, "日");
//							dayPicker.setDefault(0);
//							day = dayList.get(0);
//						} else {
//						}
//					}
					int lastDay = Integer.valueOf(yearPicker.getListSize());
					if (id > lastDay) {
						yearPicker.setDefault(lastDay - 1);
					}
				}
				tempyearIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);

			}

			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub
			}
		});
		// 月
		monthPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (temmonthIndex != id) {
					if (monthList != null && monthList.size() > id) {
						month = monthList.get(id);
//						dayList = getCurrentDayList(year, month);
//						if (dayList != null && dayList.size() > 0) {
//
//							dayPicker.setData(dayList, "日");
//							dayPicker.setDefault(0);
//							day = dayList.get(0);
//						}
					}
//					int lastDay = Integer.valueOf(monthPicker.getListSize());
//					if (id > lastDay) {
//						monthPicker.setDefault(lastDay - 1);
//					}

				}
				temmonthIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub

			}
		});
		// 日
		dayPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (tempdayIndex != id) {

					if (id > dayList.size()) {
						dayPicker.setDefault(dayList.size() - 1);
					} else {
						day = dayList.get(id);
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

	public String getmonth_string() {
		month_string = yearPicker.getSelectedText()
				+ monthPicker.getSelectedText() + dayPicker.getSelectedText();
		return month_string;
	}

	public interface OnSelectingListener {

		public void selected(boolean selected);
	}

//	private ArrayList<String> getCurrentDayList(String year, String month) {
//		ArrayList<String> daytList = new ArrayList<String>();
//		int y = Integer.parseInt(year);
//		int m = Integer.parseInt(month);
//		if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10
//				|| m == 12) {
//			for (int i = 31; i >= 1; i--) {
//				daytList.add(i + "");
//			}
//		} else if (m != 2) {
//			for (int i = 30; i >= 1; i--) {
//				daytList.add(i + "");
//			}
//		}
//
//		if (y % 4 == 0 || y % 400 == 0) {
//			if (m == 2) {
//				for (int i = 29; i >= 1; i--) {
//					daytList.add(i + "");
//				}
//			}
//		} else {
//			if (m == 2) {
//				for (int i = 28; i >= 1; i--) {
//					daytList.add(i + "");
//				}
//			}
//		}
//		return daytList;
//	}
}
