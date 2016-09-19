package cn.dressbook.ui.net;

import java.io.File;
import java.util.ArrayList;
import org.json.JSONException;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.AddressJson;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.utils.FileSDCacher;

/**
 * @description: 收货地址
 * @author:袁东华
 * @time:2015-9-1下午12:00:00
 */
public class AddressExec {
	private static AddressExec mInstance = null;

	public static AddressExec getInstance() {
		if (mInstance == null) {
			mInstance = new AddressExec();
		}
		return mInstance;
	}

	/**
	 * @description:设置默认收货地址
	 */
	public void setDefaultAddress(final Handler handler, final String user_id,
			final String address_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.SET_DEFAULT_ADDRESS);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("address_id", address_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				handler.sendEmptyMessage(flag1);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});

	}

	/**
	 * @description:删除收货地址
	 */
	public void deleteAddress(final Handler handler, final String user_id,
			final String address_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.DELETE_ADDRESS);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("address_id", address_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				handler.sendEmptyMessage(flag1);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:添加收货地址
	 */
	public void addAddress(final Handler handler, final String user_id,
			final String state, final String consignee, final String mobile,
			final String province, final String city, final String district,
			final String address, final String zipcode, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.ADD_ADDRESS);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("state", state);
		params.addBodyParameter("consignee", consignee);
		params.addBodyParameter("mobile", mobile);
		params.addBodyParameter("province", province);
		params.addBodyParameter("city", city);
		params.addBodyParameter("district", district);
		params.addBodyParameter("address", address);
		params.addBodyParameter("zipcode", zipcode);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				handler.sendEmptyMessage(flag1);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});

	}

	/**
	 * @description:编辑收货地址
	 */
	public void editAddress(final Handler handler, final String user_id,
			final String state, final String address_id,
			final String consignee, final String mobile, final String province,
			final String city, final String district, final String address,
			final String zipcode, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.EDIT_ADDRESS);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("address_id", address_id);
		params.addBodyParameter("state", state);
		params.addBodyParameter("consignee", consignee);
		params.addBodyParameter("mobile", mobile);
		params.addBodyParameter("province", province);
		params.addBodyParameter("city", city);
		params.addBodyParameter("district", district);
		params.addBodyParameter("address", address);
		params.addBodyParameter("zipcode", zipcode);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				handler.sendEmptyMessage(flag1);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:获取默认收货地址
	 */
	public void getDefaultAddress(final Handler handler, final String user_id,
			final String state, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_ADDRESS_LIST);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("state", state);
		LogUtil.e(PathCommonDefines.GET_ADDRESS_LIST + "?user_id=" + user_id
				+ "&state=" + state);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					Address address = AddressJson.getInstance()
							.analyzeDefaultAddress(result);
					Message msg = new Message();
					Bundle bun = new Bundle();
					bun.putParcelable("address", address);
					msg.setData(bun);
					msg.what = flag1;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:从服务端获取收货地址列表
	 */
	public void getAddressListFromServer(final Handler handler,
			final String user_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_ADDRESS_LIST);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					if (result != null) {

						FileSDCacher.createFile2(handler, result,
								PathCommonDefines.JSON_FOLDER + "/" + user_id,
								"addresslist.txt", flag1);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description: 从SD卡获取收货地址列表
	 * @exception
	 */
	public void getAddressListFromSD(final Handler handler,
			final String user_id, final int flag1, final int flag2) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				File file = new File(PathCommonDefines.JSON_FOLDER + "/"
						+ user_id, "addresslist.txt");
				if (file.exists()) {
					String json = FileSDCacher.ReadData(file);
					try {
						ArrayList<Address> addresslist = AddressJson
								.getInstance().analyzeAddressList(json);
						Message msg = new Message();
						msg.what = flag1;
						Bundle bundle = new Bundle();
						bundle.putParcelableArrayList("addresslist",
								addresslist);
						msg.setData(bundle);
						handler.sendMessage(msg);

					} catch (Exception e) {
					}
				} else {
					handler.sendEmptyMessage(flag2);
				}
			}
		}).start();
	}

	/**
	 * @description:从服务端获取城市列表
	 */
	public void getCityListFromServer(final Handler handler,
			final String user_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.SERVER_IMAGE_ADDRESS + "/data/Region.json");
		params.setSaveFilePath(PathCommonDefines.JSON_FOLDER + "/" + user_id
				+ "/citylist.txt");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					if (result != null) {
						LogUtil.e("");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description: 从SD卡获取城市列表
	 * @exception
	 */
	public void getCityListFromSD(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				File file = new File(PathCommonDefines.JSON_FOLDER + "/"
						+ user_id, "citylist.txt");
				if (file.exists()) {
					String json = FileSDCacher.ReadData(file);
					try {
						AddressJson.getInstance().analyzeCityList(json);
						handler.sendEmptyMessage(flag1);

					} catch (Exception e) {
					}
				} else {
					handler.sendEmptyMessage(flag2);
				}
			}
		}).start();
	}

}
