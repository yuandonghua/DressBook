package cn.dressbook.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.SubmitOrderAdapter;
import cn.dressbook.ui.alipay.AlipayUtils;
import cn.dressbook.ui.alipay.PayResult;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.model.MSTJData;
import cn.dressbook.ui.model.OrderForm;
import cn.dressbook.ui.net.AddressExec;
import cn.dressbook.ui.net.OrderExec;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.net.YJTCExec;
import cn.dressbook.ui.recyclerview.FullyLinearLayoutManager;
import cn.dressbook.ui.utils.MD5Utils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description: 确认订单界面
 * @author:袁东华
 * @time:2015-9-11下午1:15:59
 */
@ContentView(R.layout.submitorder)
public class QueRenDingDanActivity extends BaseActivity {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private Context mContext = QueRenDingDanActivity.this;
	private SubmitOrderAdapter mSubmitOrderAdapter;
	private ArrayList<MSTJData> mstjList = null;
	private String order_id, yqTotal = "0", priceTotal, freightShow,
			users_yqPoints, users_availabe;
	/** 商品展示view */
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	/** 返回按钮 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/** 标题 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 收货信息 */
	@ViewInject(R.id.shoppingaddress_rl)
	private RelativeLayout shoppingaddress_rl;
	/** 支付方式 */
	@ViewInject(R.id.payment_rl)
	private RelativeLayout payment_rl;
	/** 总额 */
	@ViewInject(R.id.total_value)
	private TextView total_value;
	/** 确认 */
	@ViewInject(R.id.ok_tv)
	private TextView ok_tv;
	/**
	 * 余额
	 */
	@ViewInject(R.id.ybj_tv)
	private TextView ybj_tv;
	/** 密码 */
	@ViewInject(R.id.password_et)
	private TextView password_et;
	/** 备注 */
	@ViewInject(R.id.remark_et)
	private TextView remark_et;
	/** 收货人 */
	@ViewInject(R.id.name_tv)
	private TextView name_tv;
	/** 手机号 */
	@ViewInject(R.id.phone_tv)
	private TextView phone_tv;
	/** 地址 */
	@ViewInject(R.id.address_tv)
	private TextView address_tv;
	/**
	 * 运费
	 */
	@ViewInject(R.id.yf_tv)
	private TextView yf_tv;
	/**
	 * 总金额
	 */
	@ViewInject(R.id.all_value)
	private TextView all_value;

	/**
	 * 元
	 */
	@ViewInject(R.id.ybj_rl)
	private RelativeLayout ybj_rl;
	@ViewInject(R.id.password_rl)
	private RelativeLayout password_rl;
	private double currentPrice;
	private String currentYBJ = "", mes_user = "";

	@ViewInject(R.id.ybj_cb)
	private CheckBox ybj_cb;
	private boolean zfResult = true;

	// public static Address currentAddress = null;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("确认订单");
		recyclerview.setHasFixedSize(true);
		FullyLinearLayoutManager mll = new FullyLinearLayoutManager(mContext);
		mll.setOrientation(LinearLayout.VERTICAL);
		recyclerview.setLayoutManager(mll);
		ybj_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (currentPrice < Double.parseDouble(users_availabe)) {
						currentYBJ = currentPrice + "";
						currentPrice = 0;
					} else {
						currentYBJ = users_availabe;
						currentPrice = currentPrice
								- Double.parseDouble(users_availabe);
					}
				} else {
					currentPrice = currentPrice
							+ Double.parseDouble(currentYBJ);
					currentYBJ = "0";
				}
				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				String money = decimalFormat.format(currentPrice);
				total_value.setText("￥" + money);
			}
		});
	}

	private void initIntent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			mstjList = intent.getParcelableArrayListExtra("mstjList");
			order_id = intent.getStringExtra("order_id");
			yqTotal = intent.getStringExtra("yqTotal");
			priceTotal = intent.getStringExtra("priceTotal");
			users_yqPoints = intent.getStringExtra("users_yqPoints");
			users_availabe = intent.getStringExtra("users_availabe");
			freightShow = intent.getStringExtra("freightShow");
			currentPrice = Double.parseDouble(priceTotal);
		}
	}

	@Event({ R.id.back_rl, R.id.shoppingaddress_rl, R.id.payment_rl, R.id.ok_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			ManagerUtils.getInstance().setAddress(null);
			finish();
			break;
		// 点击收货信息
		case R.id.shoppingaddress_rl:
			Intent xuanZeDiZhiActivity = new Intent(mContext,
					XuanZeDiZhiActivity.class);
			startActivity(xuanZeDiZhiActivity);
			break;
		// 点击确认
		case R.id.ok_tv:
			if (isFinish()) {

				mes_user = remark_et.getText().toString();
				if (ManagerUtils.getInstance().getAddress() == null) {
					Toast.makeText(mContext, "收货信息不能为空", Toast.LENGTH_SHORT)
							.show();
					break;
				}

				pbDialog.show();
				// 支付宝付款
				if (currentPrice > 0) {

					String spName = order_id + "_"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "_"
							+ ManagerUtils.getInstance().getPhoneNum(mContext);
					AlipayUtils.getInstance(QueRenDingDanActivity.this,
							mHandler).pay(spName,
							"支付" + mstjList.size() + "件商品", currentPrice + "");
				} else {
					// 无需支付宝付款
					// 提交订单
					OrderExec.getInstance().submitOrder(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							ManagerUtils.getInstance().getAddress().getId(),
							order_id, mes_user,
							NetworkAsyncCommonDefines.SUBMIT_ORDER_S,
							NetworkAsyncCommonDefines.SUBMIT_ORDER_F);
				}

			}
			break;
		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 提交订单成功
			case NetworkAsyncCommonDefines.SUBMIT_ORDER_S:
				Bundle submitData = msg.getData();
				if (submitData != null) {
					String recode = submitData.getString("recode");
					String redesc = submitData.getString("redesc");
					if (zfResult) {

						// 告诉服务端支付成功了
						OrderExec.getInstance()
								.paymentSuccess(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										order_id,
										currentPrice + "",
										currentYBJ,
										ManagerUtils.getInstance()
												.getOut_trade_no(),
										NetworkAsyncCommonDefines.PAYMENT_S,
										NetworkAsyncCommonDefines.PAYMENT_F);
					} else {
						// 跳转订单详情
						Intent intent = new Intent(mContext,
								OrderInfoActivity.class);
						intent.putExtra("order_id", order_id);
						startActivity(intent);
						pbDialog.dismiss();
						ManagerUtils.getInstance().setAddress(null);
						finish();
					}
				}
				break;
			// 提交订单失败
			case NetworkAsyncCommonDefines.SUBMIT_ORDER_F:

				break;
			// 告诉服务端支付成功了-成功
			case NetworkAsyncCommonDefines.PAYMENT_S:
				Bundle paymentBun = msg.getData();
				if (paymentBun != null) {
					String recode = paymentBun.getString("recode");
					String redesc = paymentBun.getString("redesc");

					// 获取用户财富信息
					YJTCExec.getInstance().getYJTCList(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							NetworkAsyncCommonDefines.GET_USER_PROPERTY_S,
							NetworkAsyncCommonDefines.GET_USER_PROPERTY_F);
				}
				break;
			// 告诉服务端支付成功了-失败
			case NetworkAsyncCommonDefines.PAYMENT_F:
				pbDialog.dismiss();
				break;
			// 支付结果
			case NetworkAsyncCommonDefines.SDK_PAY_FLAG:
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
					// 提交订单
					OrderExec.getInstance().submitOrder(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							ManagerUtils.getInstance().getAddress().getId(),
							order_id, mes_user,
							NetworkAsyncCommonDefines.SUBMIT_ORDER_S,
							NetworkAsyncCommonDefines.SUBMIT_ORDER_F);

				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT)
								.show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT)
								.show();
						zfResult = false;
						// 提交订单
						OrderExec
								.getInstance()
								.submitOrder(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										ManagerUtils.getInstance().getAddress()
												.getId(),
										order_id,
										mes_user,
										NetworkAsyncCommonDefines.SUBMIT_ORDER_S,
										NetworkAsyncCommonDefines.SUBMIT_ORDER_F);

					}
				}
				break;
			// 获取用户的资产成功
			case NetworkAsyncCommonDefines.GET_USER_PROPERTY_S:
				// 跳转订单详情
				Intent intent = new Intent(mContext, OrderInfoActivity.class);
				intent.putExtra("order_id", order_id);
				startActivity(intent);
				pbDialog.dismiss();
				ManagerUtils.getInstance().setAddress(null);
				finish();
				break;
			// 获取默认收货地址成功
			case NetworkAsyncCommonDefines.GET_DEFAULT_ADDRESS_S:
				Bundle addressBun = msg.getData();
				if (addressBun != null) {
					Address mAddress = addressBun.getParcelable("address");
					ManagerUtils.getInstance().setAddress(mAddress);
					if (mAddress != null) {

						name_tv.setText(mAddress.getConsignee());
						phone_tv.setText(mAddress.getMobile());
						address_tv.setText(mAddress.getProvince()
								+ mAddress.getCity() + mAddress.getDistrict()
								+ mAddress.getAddress());
					} else {
						name_tv.setText("收货人名字");
						phone_tv.setText("收货人手机号");
						address_tv.setText("收货地址");
					}
				}
				break;
			// 获取默认收货地址失败
			case NetworkAsyncCommonDefines.GET_DEFAULT_ADDRESS_F:

				break;
			// 刷新购物车总金额
			case NetworkAsyncCommonDefines.REFRESH_SHOPPINGCART:

				break;

			default:
				break;
			}

		}

	};

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		initIntent();
		if ("0".equals(users_availabe)) {
			ybj_rl.setVisibility(View.GONE);
		} else if (currentPrice < Double.parseDouble(users_availabe)) {
			ybj_rl.setVisibility(View.VISIBLE);
			ybj_tv.setText("可用" + currentPrice + "元" + "抵" + currentPrice + "元");
		} else {
			ybj_rl.setVisibility(View.VISIBLE);
			ybj_tv.setText("可用" + users_availabe + "元" + "抵" + users_availabe
					+ "元");
		}
		all_value.setText("￥" + priceTotal);
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String money = decimalFormat.format(currentPrice);
		total_value.setText("￥" + money);
		yf_tv.setText("￥" + freightShow);
		mSubmitOrderAdapter = new SubmitOrderAdapter(mContext, mHandler);
		mSubmitOrderAdapter.setData(mstjList);
		recyclerview.setAdapter(mSubmitOrderAdapter);
		if (ManagerUtils.getInstance().getAddress() == null) {

			// 获取默认收货地址
			AddressExec.getInstance().getDefaultAddress(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), "1",
					NetworkAsyncCommonDefines.GET_DEFAULT_ADDRESS_S,
					NetworkAsyncCommonDefines.GET_DEFAULT_ADDRESS_F);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (ManagerUtils.getInstance().getAddress() != null) {

			name_tv.setText(ManagerUtils.getInstance().getAddress()
					.getConsignee());
			phone_tv.setText(ManagerUtils.getInstance().getAddress()
					.getMobile());
			address_tv.setText(ManagerUtils.getInstance().getAddress()
					.getProvince()
					+ ManagerUtils.getInstance().getAddress().getCity()
					+ ManagerUtils.getInstance().getAddress().getDistrict()
					+ ManagerUtils.getInstance().getAddress().getAddress());
		} else {
			name_tv.setText("收货人名字");
			phone_tv.setText("收货人手机号");
			address_tv.setText("收货地址");
		}
	}
}
