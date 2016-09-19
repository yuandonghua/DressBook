package cn.dressbook.ui;

import java.io.InputStream;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.json.AddressJson;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.net.AddressExec;
import cn.dressbook.ui.view.SelectAddressPopupWindow;

/**
 * @description: 编辑收货地址
 * @author:袁东华
 * @time:2015-9-2上午10:56:51
 */
@ContentView(R.layout.editaddress)
public class EditAddressActivity extends BaseActivity {
	private Context mContext = EditAddressActivity.this;
	@ViewInject(R.id.save_tv)
	private TextView save_tv;
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 收货人
	 */
	@ViewInject(R.id.consignee_et)
	private EditText consignee_et;
	/**
	 * 手机号
	 */
	@ViewInject(R.id.phone_et)
	private EditText phone_et;

	/**
	 * 收货地区
	 */
	@ViewInject(R.id.shdq_et)
	private EditText shdq_et;
	/**
	 * 详细地址
	 */
	@ViewInject(R.id.address_et)
	private EditText address_et;
	/**
	 * 邮政编码
	 */
	@ViewInject(R.id.code_et)
	private EditText code_et;
	/**
	 * 我的邀请码
	 */
	@ViewInject(R.id.defaultaddress_iv)
	private ImageView defaultaddress_iv;
	private InputMethodManager imm;
	private String defaultAddress = "0";
	private SelectAddressPopupWindow mSelectAddressPopupWindow;
	private String province;
	private String city;
	private String district;
	private Address mAddress;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("编辑收货地址");

	}

	@Event({ R.id.save_tv, R.id.back_rl, R.id.defaultaddress_iv, R.id.shdq_et })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击保存
		case R.id.save_tv:
			String consignee = consignee_et.getText().toString();
			String phone = phone_et.getText().toString();
			String shdq = shdq_et.getText().toString();
			String address = address_et.getText().toString();
			String zipcode = code_et.getText().toString();
			if (consignee == null || "".equals(consignee)) {
				Toast.makeText(mContext, "请输入收货人名字", Toast.LENGTH_SHORT).show();
			} else if (phone == null || "".equals(phone)) {
				Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
			} else if (phone.length() != 11) {
				Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT)
						.show();
			} else if (shdq == null || "".equals(shdq)) {
				Toast.makeText(mContext, "请输入收货地区", Toast.LENGTH_SHORT).show();
			} else if (address == null || "".equals(address)) {
				Toast.makeText(mContext, "请输入收货详细地址", Toast.LENGTH_SHORT)
						.show();
			} else {
				AddressExec.getInstance().editAddress(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						defaultAddress, mAddress.getId(), consignee, phone,
						province, city, district, address, zipcode,
						NetworkAsyncCommonDefines.EDIT_ADDRESS_S,
						NetworkAsyncCommonDefines.EDIT_ADDRESS_F);
			}

			break;
		// 点击默认收货地址
		case R.id.defaultaddress_iv:
			if (defaultAddress.equals("1")) {
				defaultAddress = "0";
				defaultaddress_iv
						.setImageResource(R.drawable.defaultaddress_unselected);
			} else {
				defaultAddress = "1";
				defaultaddress_iv
						.setImageResource(R.drawable.defaultaddress_selected);
			}

			break;
		// 点击收货地区
		case R.id.shdq_et:
			imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(shdq_et.getWindowToken(), 0);
			if (mSelectAddressPopupWindow == null) {
				mSelectAddressPopupWindow = new SelectAddressPopupWindow(this,
						itemsOnClick);

			}
			// 显示窗口
			mSelectAddressPopupWindow.showAtLocation(shdq_et, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;

		}

	}

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			mSelectAddressPopupWindow.dismiss();
			switch (v.getId()) {
			case R.id.cancle_tv:
				break;
			case R.id.ok_tv:
				province = ManagerUtils.getInstance().getProvice();
				city = ManagerUtils.getInstance().getCity();
				district = ManagerUtils.getInstance().getDistrict();
				shdq_et.setText(province + city + district);
				break;
			default:
				break;
			}

		}

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 * 
	 * @Description TODO
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void initData() {
		initIntent();
		consignee_et.setText(mAddress.getConsignee());
		phone_et.setText(mAddress.getMobile());
		province = mAddress.getProvince();
		city = mAddress.getCity();
		district = mAddress.getDistrict();
		shdq_et.setText(province + city + district);
		address_et.setText(mAddress.getAddress());
		code_et.setText(mAddress.getZipcode());
		defaultAddress = mAddress.getState();
		if (defaultAddress.equals("1")) {
			defaultaddress_iv
					.setImageResource(R.drawable.defaultaddress_selected);
		} else {
			defaultaddress_iv
					.setImageResource(R.drawable.defaultaddress_unselected);
		}
		getCityList();
	}

	private void getCityList() {
		// TODO Auto-generated method stub
		try {
			// Return an AssetManager instance for your application's package

			InputStream is = getAssets().open("Region.json");
			int size = is.available();

			// Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();

			// Convert the buffer into a string.
			String json = new String(buffer, "GB2312");
			AddressJson.getInstance().analyzeCityList(json);

			// Finally stick the string into the text view.
		} catch (Exception e) {
			// Should never happen!
			throw new RuntimeException(e);
		}
	}

	private void initIntent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			mAddress = intent.getParcelableExtra("address");
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 添加地址成功
			case NetworkAsyncCommonDefines.EDIT_ADDRESS_S:
				Toast.makeText(mContext, "编辑地址成功", Toast.LENGTH_SHORT).show();
				finish();
				break;
			// 添加地址失败
			case NetworkAsyncCommonDefines.EDIT_ADDRESS_F:
				Toast.makeText(mContext, "编辑地址失败", Toast.LENGTH_SHORT).show();
				finish();
				break;
			default:
				break;
			}
		}

	};
}
