package cn.dressbook.ui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.model.CityInfo;
import cn.dressbook.ui.view.ScrollerNumberPicker.OnSelectListener;


/**
 * @description: 城市的选择view
 * @author:袁东华
 * @time:2015-9-1下午9:53:08
 */
public class CityPicker extends LinearLayout {
	/** 滑动控件 */
	private ScrollerNumberPicker provincePicker;
	private ScrollerNumberPicker cityPicker;
	private ScrollerNumberPicker counyPicker;
	/** 选择监听 */
	private OnSelectingListener onSelectingListener;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempProvinceIndex = -1;
	private int temCityIndex = -1;
	private int tempCounyIndex = -1;
	private Context context;
	private List<CityInfo> province_list = new ArrayList<CityInfo>();
	private HashMap<String, List<CityInfo>> city_map = new HashMap<String, List<CityInfo>>();
	private HashMap<String, List<CityInfo>> couny_map = new HashMap<String, List<CityInfo>>();
	private String city_code_string;
	private String city_string;
	ArrayList<CityInfo> provinceList = null;
	ArrayList<CityInfo> cityList = null;
	ArrayList<CityInfo> districtList = null;
	ArrayList<CityInfo> currentCityList = new ArrayList<CityInfo>();
	ArrayList<CityInfo> currentDistrictList = new ArrayList<CityInfo>();
	private String provice;
	private String city;
	private String district;

	public CityPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		getaddressinfo();
		// TODO Auto-generated constructor stub
	}

	public CityPicker(Context context) {
		super(context);
		this.context = context;
		getaddressinfo();
		// TODO Auto-generated constructor stub
	}

	// 获取城市信息
	private void getaddressinfo() {
		// TODO Auto-generated method stub
		provinceList = ManagerUtils.getInstance().getProvinceList();
		cityList = ManagerUtils.getInstance().getCityList();
		districtList = ManagerUtils.getInstance().getDistrictList();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.city_picker, this);
		// 获取控件引用
		provincePicker = (ScrollerNumberPicker) findViewById(R.id.province);

		cityPicker = (ScrollerNumberPicker) findViewById(R.id.city);
		counyPicker = (ScrollerNumberPicker) findViewById(R.id.couny);
		provincePicker.setData(provinceList);
		provincePicker.setDefault(0);
		provice = provinceList.get(0).getName();
		currentCityList = getCurrentCityList(provinceList.get(0), cityList);
		if (currentCityList != null && currentCityList.size() > 0) {
			cityPicker.setData(currentCityList);
			cityPicker.setDefault(0);
			city = currentCityList.get(0).getName();
			currentDistrictList = getCurrentCityList(currentCityList.get(0),
					districtList);
			if (currentDistrictList != null && currentDistrictList.size() > 0) {
				counyPicker.setData(currentDistrictList);
				counyPicker.setDefault(0);
				district = currentDistrictList.get(0).getName();
			} else {
			}
		}
		ManagerUtils.getInstance().setProvice(provice);
		ManagerUtils.getInstance().setCity(city);
		ManagerUtils.getInstance().setDistrict(district);
		provincePicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (tempProvinceIndex != id) {
					// String selectDay = cityPicker.getSelectedText();
					// if (selectDay == null || selectDay.equals(""))
					// return;
					// String selectMonth = counyPicker.getSelectedText();
					// if (selectMonth == null || selectMonth.equals(""))
					// return;
					// 城市数组
					provice = provinceList.get(id).getName();
					currentCityList = getCurrentCityList(provinceList.get(id),
							cityList);
					if (currentCityList != null && currentCityList.size() > 0) {
						cityPicker.setData(currentCityList);
						cityPicker.setDefault(0);
						city = currentCityList.get(0).getName();
						currentDistrictList = getCurrentCityList(
								currentCityList.get(0), districtList);
						if (currentDistrictList != null
								&& currentDistrictList.size() > 0) {
							counyPicker.setData(currentDistrictList);
							counyPicker.setDefault(0);
							district = currentDistrictList.get(0).getName();
						}else{
						}
					}
					int lastDay = Integer.valueOf(provincePicker.getListSize());
					if (id > lastDay) {
						provincePicker.setDefault(lastDay - 1);
					}
				}
				tempProvinceIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub
			}
		});
		cityPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (temCityIndex != id) {
//					String selectDay = provincePicker.getSelectedText();
//					if (selectDay == null || selectDay.equals(""))
//						return;
//					String selectMonth = counyPicker.getSelectedText();
//					if (selectMonth == null || selectMonth.equals(""))
//						return;
					if (currentCityList != null && currentCityList.size() > id) {
						city = currentCityList.get(id).getName();
						currentDistrictList = getCurrentCityList(
								currentCityList.get(id), districtList);
						if (currentDistrictList != null
								&& currentDistrictList.size() > 0) {

							counyPicker.setData(currentDistrictList);
							counyPicker.setDefault(0);
							district = currentDistrictList.get(0).getName();
						}
					}
					int lastDay = Integer.valueOf(cityPicker.getListSize());
					if (id > lastDay) {
						cityPicker.setDefault(lastDay - 1);
					}
					
				}
				temCityIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub

			}
		});
		counyPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (tempCounyIndex != id) {
//					String selectDay = provincePicker.getSelectedText();
//					if (selectDay == null || selectDay.equals(""))
//						return;
//					String selectMonth = cityPicker.getSelectedText();
//					if (selectMonth == null || selectMonth.equals(""))
//						return;
					
					if (id > currentDistrictList.size()) {
						counyPicker.setDefault(currentDistrictList.size() - 1);
					}else{
						district = currentDistrictList.get(id).getName();
					}
				}
				tempCounyIndex = id;
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
//				if (onSelectingListener != null)
//					onSelectingListener.selected(true);
				ManagerUtils.getInstance().setProvice(provice);
				ManagerUtils.getInstance().setCity(city);
				ManagerUtils.getInstance().setDistrict(district);
				break;
			default:
				break;
			}
		}

	};

	public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
		this.onSelectingListener = onSelectingListener;
	}

	public String getCity_string() {
		city_string = provincePicker.getSelectedText()
				+ cityPicker.getSelectedText() + counyPicker.getSelectedText();
		return city_string;
	}

	public interface OnSelectingListener {

		public void selected(boolean selected);
	}

	private ArrayList<CityInfo> getCurrentCityList(CityInfo cityInfo,
			ArrayList<CityInfo> cityInfoList) {
		ArrayList<CityInfo> currentList = new ArrayList<CityInfo>();
		for (int i = 0; i < cityInfoList.size(); i++) {
			if (cityInfo.getId().equals(cityInfoList.get(i).getParentId())) {
				currentList.add(cityInfoList.get(i));
			}
		}
		return currentList;
	}
}
