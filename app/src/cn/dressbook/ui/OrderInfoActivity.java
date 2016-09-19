package cn.dressbook.ui;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Locale;
import java.util.Random;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.DingDanTZCSAdapter;
import cn.dressbook.ui.adapter.OrderInfoAdapter;
import cn.dressbook.ui.adapter.TiaoZhengXiJieAdapter;
import cn.dressbook.ui.alipay.AlipayUtils;
import cn.dressbook.ui.alipay.PayResult;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.dialog.EWMDialog;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.model.CustomService;
import cn.dressbook.ui.model.OrderForm;
import cn.dressbook.ui.model.TiaoZhenCanShu;
import cn.dressbook.ui.net.CustomSerciceExec;
import cn.dressbook.ui.net.OrderExec;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.net.YJTCExec;
import cn.dressbook.ui.recyclerview.FullyLinearLayoutManager;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description: 订单详情界面
 * @author:袁东华
 * @time:2015-9-11下午1:15:59
 */
@ContentView(R.layout.orderinfo)
public class OrderInfoActivity extends BaseActivity {
	private Context mContext = OrderInfoActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private OrderForm mOrderForm = null;
	private Address mAddress;
	private String order_id;
	/** 订单状态 */
	@ViewInject(R.id.state_value)
	private TextView state_value;
	/** 订单号 */
	@ViewInject(R.id.number_value)
	private TextView number_value;

	/** 标题 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 实付款 */
	@ViewInject(R.id.all_value)
	private TextView all_value;
	/** 备注 */
	@ViewInject(R.id.remark_value)
	private TextView remark_value;
	/** 收货人 */
	@ViewInject(R.id.name_tv)
	private TextView name_tv;
	/** 手机号 */
	@ViewInject(R.id.phone_tv)
	private TextView phone_tv;
	/** 地址 */
	@ViewInject(R.id.address_tv)
	private TextView address_tv;
	/** 下单时间 */
	@ViewInject(R.id.time_value)
	private TextView time_value;
	/** 取消订单 */
	@ViewInject(R.id.qxdd_rl)
	private RelativeLayout qxdd_rl;
	/** 付款 */
	@ViewInject(R.id.fk_rl)
	private RelativeLayout fk_rl;
	/** 申请售后 */
	@ViewInject(R.id.sqsh_rl)
	private RelativeLayout sqsh_rl;
	/** 确认收货 */
	@ViewInject(R.id.qrsh_rl)
	private RelativeLayout qrsh_rl;
	/**
	 * 支付宝支付
	 */
	@ViewInject(R.id.zfbzf_value)
	private TextView zfbzf_value;
	/**
	 * 衣保金支付
	 */
	@ViewInject(R.id.ybj_value)
	private TextView ybj_value;
	/**
	 * 运费
	 */
	@ViewInject(R.id.freight_value)
	private TextView freight_value;

	/**
	 * 支付方式
	 */
	@ViewInject(R.id.payment_rl)
	private RelativeLayout payment_rl;
	/**
	 * 衣保金
	 */
	@ViewInject(R.id.ybj_rl)
	private RelativeLayout ybj_rl;
	/**
	 * 衣保金
	 */
	@ViewInject(R.id.ybj_tv)
	private TextView ybj_tv;
	@ViewInject(R.id.ybj_cb)
	private CheckBox ybj_cb;
	/**
	 * 支付宝支付
	 */
	@ViewInject(R.id.zfbzf_rl)
	private RelativeLayout zfbzf_rl;
	/**
	 * 衣保金支付
	 */
	@ViewInject(R.id.ybjzf_rl)
	private RelativeLayout ybjzf_rl;
	@ViewInject(R.id.all_key)
	private TextView all_key;
	private double currentPrice;
	private String currentYBJ = "00.00", users_availabe = "00.00",
			users_yqPoints = "0";
	@ViewInject(R.id.ewm_tv)
	private TextView ewm_tv;
	@ViewInject(R.id.ewm_rl)
	private RelativeLayout ewm_rl;
	@ViewInject(R.id.sqtk_rl)
	private RelativeLayout sqtk_rl;
	@ViewInject(R.id.imageview)
	private ImageView imageview;
	private DingDanTZCSAdapter mDingDanTZCSAdapter;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("订单详情");
		recyclerview.setHasFixedSize(true);
		FullyLinearLayoutManager mll = new FullyLinearLayoutManager(mContext);
		mll.setOrientation(LinearLayout.VERTICAL);
		recyclerview.setLayoutManager(mll);
		// 添加分割线
		recyclerview
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						this)
						.color(getResources().getColor(
								android.R.color.transparent)).size(10)
						.margin(0, 0).build());

		mDingDanTZCSAdapter = new DingDanTZCSAdapter();
		recyclerview.setAdapter(mDingDanTZCSAdapter);
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
						currentYBJ = users_availabe + "";
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
				all_value.setText("￥" + money);
			}
		});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		initIntent();
		users_yqPoints = mSharedPreferenceUtils.getYK(mContext);
		if (ManagerUtils.getInstance().yjtc != null) {
			users_availabe = ManagerUtils.getInstance().yjtc
					.getMoneyAvailable();
		}
		mHandler.sendEmptyMessage(0);
	}

	private void initIntent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			order_id = intent.getStringExtra("order_id");
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private EWMDialog mEWMDialog;

	@Event({ R.id.sqtk_rl, R.id.back_rl, R.id.shoppingaddress_rl, R.id.back,
			R.id.qxdd_rl, R.id.fk_rl, R.id.sqsh_rl, R.id.qrsh_rl, R.id.ewm_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击申请退款
		case R.id.sqtk_rl:
			Toast.makeText(activity, "正在处理", Toast.LENGTH_SHORT).show();
			pbDialog.show();
			OrderExec.getInstance().shenQingTuiHuo(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext),
					mOrderForm.getId(), NetworkAsyncCommonDefines.SQTH_S,
					NetworkAsyncCommonDefines.SQTH_F);

			break;
		// 点击消费码
		case R.id.ewm_tv:
			if (mOrderForm.getxfm() != null) {

				if (mEWMDialog == null) {
					mEWMDialog = new EWMDialog(activity);
				}
				mEWMDialog.show();
				mEWMDialog.setEWM("消费码", mOrderForm.getxfm());
			}
			break;
		// 取消订单
		case R.id.qxdd_rl:
			OrderExec.getInstance().cancelOrder(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), order_id,
					NetworkAsyncCommonDefines.CANCEL_ORDER_S,
					NetworkAsyncCommonDefines.CANCEL_ORDER_F);
			break;
		// 点击付款
		case R.id.fk_rl:
			if (isFinish()) {
				// 支付宝付款
				if (currentPrice > 0) {

					String spName = mOrderForm.getId() + "_"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "_"
							+ ManagerUtils.getInstance().getPhoneNum(mContext);
					AlipayUtils.getInstance(OrderInfoActivity.this, mHandler)
							.pay(spName, "支付1件商品", currentPrice + "");
				} else {
					SimpleDateFormat format = new SimpleDateFormat(
							"MMddHHmmss", Locale.getDefault());
					Date date = new Date();
					String key = format.format(date);
					Random r = new Random();
					key = key + Math.abs(r.nextInt());
					key = key.substring(0, 15);
					// 无需支付宝付款
					// 告诉服务端支付成功了
					OrderExec.getInstance().paymentSuccess(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							order_id, currentPrice + "", currentYBJ,
							"cyd" + key, NetworkAsyncCommonDefines.PAYMENT_S,
							NetworkAsyncCommonDefines.PAYMENT_F);
				}

			}
			break;
		// 申请售后
		case R.id.sqsh_rl:
			Intent intent = new Intent(mContext, ApplyAfterSaleActivity.class);
			intent.putExtra("order_id", mOrderForm.getId());
			intent.putParcelableArrayListExtra("list",
					mOrderForm.getOrderAttite());
			mContext.startActivity(intent);
			break;
		// 确认收货
		case R.id.qrsh_rl:

			OrderExec.getInstance().receiptGoods(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext),
					mOrderForm.getId(),
					NetworkAsyncCommonDefines.RECEOPT_GOODS_S,
					NetworkAsyncCommonDefines.RECEOPT_GOODS_F);
			break;

		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击收货信息
		case R.id.shoppingaddress_rl:
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
			case 0:
				// 获取订单详情
				OrderExec.getInstance().getOrderInfo(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						order_id, NetworkAsyncCommonDefines.GET_ORDERINFO_S,
						NetworkAsyncCommonDefines.GET_ORDERINFO_F);
				break;
			// 申请退款成功
			case NetworkAsyncCommonDefines.SQTH_S:
				Bundle sqthData = msg.getData();
				if (sqthData != null) {
					String recode = sqthData.getString("recode");
					if ("0".equals(recode)) {
						Toast.makeText(activity, "申请退款成功,请等待审核",
								Toast.LENGTH_SHORT).show();
					} else if ("1".equals(recode)) {
						Toast.makeText(activity, "用户不存在", Toast.LENGTH_SHORT)
								.show();
					} else if ("2".equals(recode)) {
						Toast.makeText(activity, "订单不存在", Toast.LENGTH_SHORT)
								.show();
					} else if ("3".equals(recode)) {
						Toast.makeText(activity, "订单状态不正确", Toast.LENGTH_SHORT)
								.show();
					} else if ("4".equals(recode)) {
						Toast.makeText(activity, "用户与订单不对应", Toast.LENGTH_SHORT)
								.show();
					}
				}
				pbDialog.dismiss();
				mHandler.sendEmptyMessage(0);
				break;
			// 申请退款失败
			case NetworkAsyncCommonDefines.SQTH_F:
				Toast.makeText(activity, "服务器或网络异常", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
				mHandler.sendEmptyMessage(0);
				break;
			// 确认收货成功
			case NetworkAsyncCommonDefines.RECEOPT_GOODS_S:
				Bundle receiptBun = msg.getData();
				if (receiptBun != null) {
					String recode = receiptBun.getString("recode");
					String redesc = receiptBun.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
					finish();
				}
				pbDialog.dismiss();
				break;
			// 失败确认收货
			case NetworkAsyncCommonDefines.RECEOPT_GOODS_F:
				Toast.makeText(mContext, "订单操作失败", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
				break;
			// 获取一个客服成功
			case NetworkAsyncCommonDefines.GET_ONE_CUSTOMSERVICE_S:
				break;
			// 获取一个客服失败
			case NetworkAsyncCommonDefines.GET_ONE_CUSTOMSERVICE_F:

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
					// 支付宝付款成功
					// 告诉服务端支付成功了
					OrderExec.getInstance().paymentSuccess(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							order_id, currentPrice + "", currentYBJ,
							ManagerUtils.getInstance().getOut_trade_no(),
							NetworkAsyncCommonDefines.PAYMENT_S,
							NetworkAsyncCommonDefines.PAYMENT_F);

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

					}
				}
				break;
			// 获取用户的资产成功
			case NetworkAsyncCommonDefines.GET_USER_PROPERTY_S:
				finish();
				break;
			// 取消订单成功
			case NetworkAsyncCommonDefines.CANCEL_ORDER_S:
				Bundle deleteBun = msg.getData();
				if (deleteBun != null) {
					String recode = deleteBun.getString("recode");
					String redesc = deleteBun.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
					finish();
				}
				pbDialog.dismiss();
				break;
			// 取消订单失败
			case NetworkAsyncCommonDefines.CANCEL_ORDER_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "订单操作失败", Toast.LENGTH_SHORT).show();
				finish();
				break;
			// 提交订单成功
			case NetworkAsyncCommonDefines.SUBMIT_ORDER_S:
				Bundle orderBun = msg.getData();
				if (orderBun != null) {
					String redesc = orderBun.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
				}
				pbDialog.dismiss();
				break;
			// 提交订单失败
			case NetworkAsyncCommonDefines.SUBMIT_ORDER_F:

				break;
			// 刷新购物车总金额
			case NetworkAsyncCommonDefines.REFRESH_SHOPPINGCART:

				break;
			// 获取订单详情成功
			case NetworkAsyncCommonDefines.GET_ORDERINFO_S:
				Bundle bun = msg.getData();
				if (bun != null) {
					mOrderForm = bun.getParcelable("order");
					if (mOrderForm != null) {
						setSPXX();
						setTZCS();
						setCX();
						if (mOrderForm.getxfm() != null
								&& !"".equals(mOrderForm.getxfm())
								&& !"null".equals(mOrderForm.getxfm())) {
							number_value.setText(mOrderForm.getId());
							ewm_rl.setVisibility(View.VISIBLE);
						} else {
							number_value.setText(mOrderForm.getId());
							ewm_rl.setVisibility(View.GONE);
						}
						name_tv.setText(mOrderForm.getAddrConsignee());
						phone_tv.setText(mOrderForm.getAddrMobile());
						address_tv.setText(mOrderForm.getAddrCompose());
						zfbzf_value.setText("￥" + mOrderForm.getPriceNet());
						ybj_value.setText("￥" + mOrderForm.getpayYb());
						freight_value.setText("￥" + mOrderForm.getFreight());
						remark_value.setText(mOrderForm.getMesUser());
						time_value.setText("下单时间:" + mOrderForm.getAddTime());
						all_value.setText("￥" + mOrderForm.getPriceTotal());
						state_value.setText(mOrderForm.getstateShow());
						if ("2".equals(mOrderForm.getState())
								|| "3".equals(mOrderForm.getState())
								|| "102".equals(mOrderForm.getState())) {

							sqtk_rl.setVisibility(View.GONE);
							qxdd_rl.setVisibility(View.VISIBLE);
							qrsh_rl.setVisibility(View.GONE);
							fk_rl.setVisibility(View.VISIBLE);
							sqsh_rl.setVisibility(View.GONE);
							// 显示付款方式(支付宝,衣保金,衣扣)
							payment_rl.setVisibility(View.VISIBLE);
							// 隐藏支付情况(支付宝,衣保金,衣扣)
							zfbzf_rl.setVisibility(View.GONE);
							ybjzf_rl.setVisibility(View.GONE);
							all_key.setText("应付款:");
							currentPrice = Double.parseDouble(mOrderForm
									.getPriceTotal());
							if ("00.00".equals(users_availabe)) {
								ybj_rl.setVisibility(View.GONE);
							} else if (currentPrice < Float
									.parseFloat(users_availabe)) {
								ybj_rl.setVisibility(View.VISIBLE);
								ybj_tv.setText("可用" + currentPrice + "衣保金"
										+ "抵" + currentPrice + "元");
							} else {
								ybj_rl.setVisibility(View.VISIBLE);
								ybj_tv.setText("可用" + users_availabe + "衣保金"
										+ "抵" + users_availabe + "元");
							}
							DecimalFormat decimalFormat = new DecimalFormat(
									"0.00");
							String money = decimalFormat.format(currentPrice);
							all_value.setText("￥" + money);
						} else if ("4".equals(mOrderForm.getState())
								|| "103".equals(mOrderForm.getState())) {
							qxdd_rl.setVisibility(View.GONE);
							qrsh_rl.setVisibility(View.GONE);
							fk_rl.setVisibility(View.GONE);
							sqsh_rl.setVisibility(View.GONE);
							sqtk_rl.setVisibility(View.GONE);
						} else if ("5".equals(mOrderForm.getState())
								|| "104".equals(mOrderForm.getState())) {
							qxdd_rl.setVisibility(View.GONE);
							qrsh_rl.setVisibility(View.GONE);
							fk_rl.setVisibility(View.GONE);
							sqsh_rl.setVisibility(View.GONE);
							sqtk_rl.setVisibility(View.VISIBLE);
						} else if ("6".equals(mOrderForm.getState())
								|| "104".equals(mOrderForm.getState())) {
							qxdd_rl.setVisibility(View.GONE);
							qrsh_rl.setVisibility(View.GONE);
							fk_rl.setVisibility(View.GONE);
							sqsh_rl.setVisibility(View.GONE);
							sqtk_rl.setVisibility(View.GONE);
						} else if ("7".equals(mOrderForm.getState())
								|| "107".equals(mOrderForm.getState())) {
							qxdd_rl.setVisibility(View.GONE);
							qrsh_rl.setVisibility(View.VISIBLE);
							fk_rl.setVisibility(View.GONE);
							sqsh_rl.setVisibility(View.GONE);
							sqtk_rl.setVisibility(View.GONE);
						} else if ("8".equals(mOrderForm.getState())) {
							qxdd_rl.setVisibility(View.GONE);
							qrsh_rl.setVisibility(View.GONE);
							fk_rl.setVisibility(View.GONE);
							sqsh_rl.setVisibility(View.VISIBLE);
						} else if ("9".equals(mOrderForm.getState())
								|| "108".equals(mOrderForm.getState())
								|| "109".equals(mOrderForm.getState())) {
							qxdd_rl.setVisibility(View.GONE);
							qrsh_rl.setVisibility(View.GONE);
							fk_rl.setVisibility(View.GONE);
							sqsh_rl.setVisibility(View.VISIBLE);
							sqtk_rl.setVisibility(View.GONE);
						} else if ("10".equals(mOrderForm.getState())
								|| "110".equals(mOrderForm.getState())) {
							qxdd_rl.setVisibility(View.GONE);
							qrsh_rl.setVisibility(View.GONE);
							fk_rl.setVisibility(View.GONE);
							sqsh_rl.setVisibility(View.GONE);
						} else if ("11".equals(mOrderForm.getState())
								|| "111".equals(mOrderForm.getState())) {
							qxdd_rl.setVisibility(View.GONE);
							qrsh_rl.setVisibility(View.GONE);
							fk_rl.setVisibility(View.GONE);
							sqsh_rl.setVisibility(View.VISIBLE);
							sqtk_rl.setVisibility(View.GONE);
						} else if ("12".equals(mOrderForm.getState())
								|| "112".equals(mOrderForm.getState())) {
							qxdd_rl.setVisibility(View.GONE);
							qrsh_rl.setVisibility(View.GONE);
							fk_rl.setVisibility(View.GONE);
							sqsh_rl.setVisibility(View.GONE);
							sqtk_rl.setVisibility(View.GONE);
						} else if ("113".equals(mOrderForm.getState())
								|| "114".equals(mOrderForm.getState())) {
							state_value.setText("退款中");
							qxdd_rl.setVisibility(View.GONE);
							qrsh_rl.setVisibility(View.GONE);
							fk_rl.setVisibility(View.GONE);
							sqsh_rl.setVisibility(View.GONE);
							sqtk_rl.setVisibility(View.GONE);
						}

					}
				}
				break;
			// 获取订单详情失败
			case NetworkAsyncCommonDefines.GET_ORDERINFO_F:
				break;
			default:
				break;
			}

		}

	};
	@ViewInject(R.id.spbt_tv)
	private TextView spbt_tv;
	@ViewInject(R.id.jg_tv)
	private TextView jg_tv;

	/**
	 * 设置商品信息
	 */
	protected void setSPXX() {
		// TODO Auto-generated method stub
		x.image().bind(imageview, mOrderForm.getDzOda().getPic());
		spbt_tv.setText(mOrderForm.getDzOda().getTitle());

		jg_tv.setText("￥" + mOrderForm.getPriceTotal());
	}

	@ViewInject(R.id.dzxjts_rl)
	private RelativeLayout dzxjts_rl;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;

	/**
	 * 设置调整参数
	 */
	protected void setTZCS() {
		// TODO Auto-generated method stub
		if (mOrderForm.getTzcsList() != null
				&& mOrderForm.getTzcsList().size() > 0) {
			dzxjts_rl.setVisibility(View.VISIBLE);
			recyclerview.setVisibility(View.VISIBLE);
			mDingDanTZCSAdapter.setData(mOrderForm.getTzcsList());
			mDingDanTZCSAdapter.notifyDataSetChanged();
		} else {
			dzxjts_rl.setVisibility(View.GONE);
			recyclerview.setVisibility(View.GONE);
		}
	}

	@ViewInject(R.id.ys_iv)
	private ImageView ys_iv;
	@ViewInject(R.id.zt_iv)
	private ImageView zt_iv;
	@ViewInject(R.id.cxwz_tv)
	private TextView cxwz_tv;

	/**
	 * 设置刺绣信息
	 */
	private void setCX() {
		if(mOrderForm.getDingDanCiXiu()!=null){
			if("".equals(mOrderForm.getDingDanCiXiu().getWords())){
				cxwz_tv.setText("没有刺绣信息");

			}else{
				x.image().bind(ys_iv, mOrderForm.getDingDanCiXiu().getColorPic());
				x.image().bind(zt_iv, mOrderForm.getDingDanCiXiu().getFontPic());
				cxwz_tv.setText(mOrderForm.getDingDanCiXiu().getWords());
			}
			
		}
	}
}
