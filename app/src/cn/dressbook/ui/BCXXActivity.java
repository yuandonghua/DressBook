package cn.dressbook.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.CiXiuColorAdapter;
import cn.dressbook.ui.adapter.CiXiuFontAdapter;
import cn.dressbook.ui.alipay.AlipayUtils;
import cn.dressbook.ui.alipay.PayResult;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.model.CiXiu;
import cn.dressbook.ui.model.LiangTiShuJu;
import cn.dressbook.ui.net.AddressExec;
import cn.dressbook.ui.net.LSDZExec;
import cn.dressbook.ui.net.LTSJExec;
import cn.dressbook.ui.net.OrderExec;
import cn.dressbook.ui.net.YJTCExec;
import cn.dressbook.ui.recyclerview.FullyGridLayoutManager;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description 补充信息界面
 * @author 袁东华
 * @date 2016-1-22
 */
@ContentView(R.layout.bcxx)
public class BCXXActivity extends BaseActivity {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private Context mContext = BCXXActivity.this;
	ArrayList<CiXiu> colorList = new ArrayList<CiXiu>();
	ArrayList<CiXiu> fontFamilyList = new ArrayList<CiXiu>();
	private CiXiuColorAdapter mCiXiuColorAdapter;
	private CiXiuFontAdapter mCiXiuFontAdapter;
	@ViewInject(R.id.recyclerview1)
	private RecyclerView recyclerview1;
	@ViewInject(R.id.recyclerview2)
	private RecyclerView recyclerview2;
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 收货人 */
	@ViewInject(R.id.name_tv)
	private TextView name_tv;
	/** 手机号 */
	@ViewInject(R.id.phone_tv)
	private TextView phone_tv;
	/** 地址 */
	@ViewInject(R.id.address_tv)
	private TextView address_tv;

	@ViewInject(R.id.ybj_rl)
	private RelativeLayout ybj_rl;

	@ViewInject(R.id.ybj_tv)
	private TextView ybj_tv;

	@ViewInject(R.id.cxwz_et)
	private TextView cxwz_et;
	private RelativeLayout ok_rl;
	@ViewInject(R.id.ybj_cb)
	private CheckBox ybj_cb;
	private int colorPosition = -1, fontPosition = -1;
	private String yue="0.00", currentPrice = "0.00", usedYBJ="0", spTitle;
	/**
	 * 刺绣文字
	 */
	@ViewInject(R.id.cxwz_ll)
	private LinearLayout cxwz_ll;
	@ViewInject(R.id.cxwz_rl)
	private RelativeLayout cxwz_rl;
	/**
	 * 刺绣颜色
	 */
	@ViewInject(R.id.cxys_ll)
	private LinearLayout cxys_ll;
	@ViewInject(R.id.cxys_rl)
	private RelativeLayout cxys_rl;
	/**
	 * 刺绣字体
	 */
	@ViewInject(R.id.cxzt_ll)
	private LinearLayout cxzt_ll;
	@ViewInject(R.id.cxzt_rl)
	private RelativeLayout cxzt_rl;
	// 付款按钮
	@ViewInject(R.id.fk_tv)
	private TextView fk_tv;
	private String lastOper, sex1, sex2;
	private AlertDialog mAlertDialog1, mAlertDialog2;

	@Override
	protected void initView() {
		title_tv.setText("补充信息");
		mCiXiuColorAdapter = new CiXiuColorAdapter(this);
		recyclerview1.setLayoutManager(new FullyGridLayoutManager(mContext, 5));
		recyclerview1.setAdapter(mCiXiuColorAdapter);
		mCiXiuFontAdapter = new CiXiuFontAdapter();
		recyclerview2.setLayoutManager(new FullyGridLayoutManager(mContext, 3));
		recyclerview2.setAdapter(mCiXiuFontAdapter);
		// 选择颜色
		mCiXiuColorAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				String cxwz = cxwz_et.getEditableText().toString();
				if (cxwz != null && cxwz.length() > 0) {

					if (colorPosition != position) {
						if (colorPosition != -1) {
							colorList.get(colorPosition).setState("0.00");
							mCiXiuColorAdapter.setData(colorList);
							mCiXiuColorAdapter.notifyItemChanged(colorPosition);
						}
						colorList.get(position).setState("1");
						mCiXiuColorAdapter.setData(colorList);
						mCiXiuColorAdapter.notifyItemChanged(position);

						colorPosition = position;
					} else {
						if (colorPosition != -1) {
							colorList.get(colorPosition).setState("0.00");
							mCiXiuColorAdapter.setData(colorList);
							mCiXiuColorAdapter.notifyItemChanged(colorPosition);
						}
						colorPosition = -1;
					}
				} else {
					Toast.makeText(mContext, "请输入刺绣文字", Toast.LENGTH_SHORT)
							.show();
				}
				LogUtil.e("colorPosition:"+colorPosition);
			}
		});
		// 选择字体
		mCiXiuFontAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				String cxwz = cxwz_et.getEditableText().toString();
				if (cxwz != null && cxwz.length() > 0) {
					if (fontPosition != position) {
						if (fontPosition != -1) {
							fontFamilyList.get(fontPosition).setState("0.00");
							mCiXiuFontAdapter.setData(fontFamilyList);
							mCiXiuFontAdapter.notifyItemChanged(fontPosition);
						}
						fontFamilyList.get(position).setState("2");
						mCiXiuFontAdapter.setData(fontFamilyList);
						mCiXiuFontAdapter.notifyItemChanged(position);

						fontPosition = position;
					} else {
						if (fontPosition != -1) {
							fontFamilyList.get(fontPosition).setState("0.00");
							mCiXiuFontAdapter.setData(fontFamilyList);
							mCiXiuFontAdapter.notifyItemChanged(fontPosition);
						}
						fontPosition = -1;
					}
				} else {
					Toast.makeText(mContext, "请输入刺绣文字", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
		cxwz_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s == null || s.length() == 0) {
					if (colorPosition != -1) {
						colorList.get(colorPosition).setState("0.00");
						mCiXiuColorAdapter.setData(colorList);
						mCiXiuColorAdapter.notifyItemChanged(colorPosition);
					}
					colorPosition = -1;
					if (fontPosition != -1) {
						fontFamilyList.get(fontPosition).setState("0.00");
						mCiXiuFontAdapter.setData(fontFamilyList);
						mCiXiuFontAdapter.notifyItemChanged(fontPosition);
					}
					fontPosition = -1;
				}
			}
		});
		ok_rl = (RelativeLayout) findViewById(R.id.ok_rl);
		// 点击付款
		ok_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkLiangTi();

			}
		});
		// 选择余额
		ybj_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				// 确定支付宝付款金额
				if (isChecked) {
					if ("0.00".equals(yue)) {
						usedYBJ = "0.00";
						currentPrice = order_priceTotal;
					} else if (Double.parseDouble(order_priceTotal) <= Double
							.parseDouble(yue)) {
						currentPrice = "0.00";
						usedYBJ = order_priceTotal;
					} else if (Double.parseDouble(order_priceTotal) > Double
							.parseDouble(yue)) {
						usedYBJ = yue;
						currentPrice = Double.parseDouble(order_priceTotal)
								- Double.parseDouble(yue) + "";
					}
				} else {
					usedYBJ = "0.00";
					currentPrice = order_priceTotal;
				}
				fk_tv.setText("付款(现金￥" + currentPrice + ")");
			}
		});

	}

	/**
	 * 检查是否被量体过
	 */
	protected void checkLiangTi() {
		if ("无".equals(lastOper)) {
			// 未被量体
			if (mAlertDialog1 == null) {
				mAlertDialog1 = new AlertDialog.Builder(activity)
						.setTitle("下单提醒")
						.setMessage(
								"你当前尚未量体，可取消或继续付款。付款并不保证订单有效。请联系客服010-67578889")
						.setNegativeButton("取消", null)
						.setPositiveButton(
								"继续",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										checkSex();

									}

								}).create();
			}
			mAlertDialog1.show();
		} else {
			// 量过体型
			checkSex();
		}
	}

	private void checkSex() {
		// TODO Auto-generated method stub
		//性别不相符
		if (sex2==null||!sex2.equals(sex1)) {
			if (mAlertDialog2 == null) {
				mAlertDialog2 = new AlertDialog.Builder(activity)
						.setTitle("下单提醒")
						.setMessage("非用户本人定制服装，可能会因量体数据不符导致订单无效或定制失败，请审慎操作。")
						.setNegativeButton("取消", null)
						.setPositiveButton(
								"继续",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										fuKuan();
									}

								}).create();
			}
			mAlertDialog2.show();
		} else {
			//性别相符
			fuKuan();
		}
	}

	/**
	 * 付款
	 */
	protected void fuKuan() {
		String cxwz = cxwz_et.getEditableText().toString();
		if (ManagerUtils.getInstance().getAddress() == null) {
			Toast.makeText(mContext, "请填写收货人信息", Toast.LENGTH_SHORT).show();
			return;
		}
		String addressId = ManagerUtils.getInstance().getAddress().getId();
		String fontName = "";
		if (fontPosition != -1) {
			fontName = fontFamilyList.get(fontPosition).getName();
		}
		String colorName = "";
		if (colorPosition != -1) {
			colorName = colorList.get(colorPosition).getName();
		}
		LogUtil.e("colorPosition:"+colorPosition);
		if (cxwz != null && cxwz.length() > 0) {
			if ("".equals(colorName)) {
				Toast.makeText(mContext, "请选择刺绣颜色", Toast.LENGTH_SHORT).show();
				return;
			}
			if ("".equals(fontName)) {
				Toast.makeText(mContext, "请选择刺绣字体", Toast.LENGTH_SHORT).show();
				return;
			}

		}
		pbDialog.show();
		// 提交补充信息
		LSDZExec.getInstance().getTJBCXX(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				order_dz_attire_id, addressId, colorName, fontName, cxwz,
				NetworkAsyncCommonDefines.TJ_DZSP_BCXX_S,
				NetworkAsyncCommonDefines.TJ_DZSP_BCXX_F);
	}

	private String order_id, order_dz_attire_id, template_id, order_priceTotal;

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		sex2 = getIntent().getStringExtra("sex");
		order_id = getIntent().getStringExtra("order_id");
		order_dz_attire_id = getIntent().getStringExtra("order_dz_attire_id");
		template_id = getIntent().getStringExtra("template_id");
		order_priceTotal = getIntent().getStringExtra("order_priceTotal");
		currentPrice = order_priceTotal;
		if (ManagerUtils.getInstance().getAddress() == null) {

			// 获取默认收货地址
			AddressExec.getInstance().getDefaultAddress(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), "1",
					NetworkAsyncCommonDefines.GET_DEFAULT_ADDRESS_S,
					NetworkAsyncCommonDefines.GET_DEFAULT_ADDRESS_F);
		}
		// 获取定制商品刺绣信息
		LSDZExec.getInstance().getDZSPCX(mHandler, template_id,
				NetworkAsyncCommonDefines.GET_DZSP_CX_S,
				NetworkAsyncCommonDefines.GET_DZSP_CX_F);
		if (ManagerUtils.getInstance().yjtc == null) {
			ybj_rl.setVisibility(View.GONE);
			// 获取用户财富信息
			YJTCExec.getInstance().getYJTCList(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), 1, -1);
		} else {

			yue = ManagerUtils.getInstance().yjtc.getMoneyAvailable();
			if ("0.00".equals(yue)) {
				ybj_rl.setVisibility(View.GONE);
			} else if (Double.parseDouble(order_priceTotal) <= Double
					.parseDouble(yue)) {
				ybj_rl.setVisibility(View.VISIBLE);
				ybj_tv.setText("可用" + order_priceTotal + "元" + "抵"
						+ order_priceTotal + "元");
			} else if (Double.parseDouble(order_priceTotal) > Double
					.parseDouble(yue)) {
				ybj_rl.setVisibility(View.VISIBLE);
				ybj_tv.setText("可用" + yue + "元" + "抵" + yue + "元");
			}
			if (ManagerUtils.getInstance().yjtc.getId() != null) {
				ybj_rl.setVisibility(View.VISIBLE);
			} else {
				ybj_rl.setVisibility(View.GONE);
			}
			fk_tv.setText("付款(现金￥" + currentPrice + ")");
		}
		// 获取量体数据
		LTSJExec.getInstance().getLTSJList(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_LTSJ_S_KH,
				NetworkAsyncCommonDefines.GET_LTSJ_F_KH);
	}

	@Event({ R.id.back_rl, R.id.ok_tv, R.id.shxx_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			ManagerUtils.getInstance().setAddress(null);
			finish();
			break;
		// 点击收货信息
		case R.id.shxx_rl:
			Intent xuanZeDiZhiActivity = new Intent(mContext,
					XuanZeDiZhiActivity.class);
			startActivity(xuanZeDiZhiActivity);
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
			// 获取量体数据成功
			case NetworkAsyncCommonDefines.GET_LTSJ_S_KH:
				Bundle ltsjData = msg.getData();
				if (ltsjData != null) {
					lastOper = ltsjData.getString("lastOper");
					LogUtil.e("lastOper:" + lastOper);
					if (!"无".equals(lastOper)) {
						// 被量体过
						ArrayList<LiangTiShuJu> list = ltsjData
								.getParcelableArrayList("ltsj");
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								if ("性别".equals(list.get(i).getName())) {
									mSharedPreferenceUtils.setSex(mContext,
											list.get(i).getValue());
									sex1 = mSharedPreferenceUtils
											.getSex(mContext);
								}
							}

						} else {
							// 没有被量体

						}
					}
				}
				pbDialog.dismiss();
				break;
			// 获取量体数据失败
			case NetworkAsyncCommonDefines.GET_LTSJ_F_KH:
				pbDialog.dismiss();
				break;
			// 获取到财富信息
			case 1:
				if (ManagerUtils.getInstance().yjtc == null) {
					ybj_rl.setVisibility(View.GONE);
				} else {

					yue = ManagerUtils.getInstance().yjtc.getMoneyAvailable();
					if ("0.00".equals(yue)) {
						ybj_rl.setVisibility(View.GONE);
					} else if (Double.parseDouble(order_priceTotal) <= Double
							.parseDouble(yue)) {
						ybj_rl.setVisibility(View.VISIBLE);
						ybj_tv.setText("可用" + order_priceTotal + "元" + "抵"
								+ order_priceTotal + "元");
					} else if (Double.parseDouble(order_priceTotal) > Double
							.parseDouble(yue)) {
						ybj_rl.setVisibility(View.VISIBLE);
						ybj_tv.setText("可用" + yue + "元" + "抵" + yue + "元");
					}
					if (ManagerUtils.getInstance().yjtc.getId() != null) {
						ybj_rl.setVisibility(View.VISIBLE);
					} else {
						ybj_rl.setVisibility(View.GONE);
					}
				}
				fk_tv.setText("付款(现金￥" + currentPrice + ")");
				break;
			// 提交刺绣信息成功
			case NetworkAsyncCommonDefines.TJ_DZSP_BCXX_S:
				// 支付宝付款
				if (Double.parseDouble(currentPrice) > 0) {

					Toast.makeText(mContext, "正在付款...", Toast.LENGTH_SHORT)
							.show();
					spTitle = order_id + "_"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "_"
							+ ManagerUtils.getInstance().getPhoneNum(mContext);
					AlipayUtils.getInstance(BCXXActivity.this, mHandler).pay(
							spTitle, "支付1件商品", currentPrice + "");

				} else {
					Toast.makeText(mContext, "付款成功", Toast.LENGTH_SHORT).show();
					SimpleDateFormat format = new SimpleDateFormat(
							"MMddHHmmss", Locale.getDefault());
					Date date = new Date();
					String key = format.format(date);
					Random r = new Random();
					key = key + Math.abs(r.nextInt());
					key = key.substring(0, 15);
					// 无需支付宝付款-元支付
					// 告诉服务端支付成功了
					OrderExec.getInstance().paymentSuccess(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							order_id, currentPrice, usedYBJ, "cyd" + key,
							NetworkAsyncCommonDefines.PAYMENT_S,
							NetworkAsyncCommonDefines.PAYMENT_F);
				}
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
					// 支付宝支付成功了
					// 告诉服务端支付成功了
					OrderExec.getInstance().paymentSuccess(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							order_id, currentPrice, usedYBJ,
							ManagerUtils.getInstance().getOut_trade_no(),
							NetworkAsyncCommonDefines.PAYMENT_S,
							NetworkAsyncCommonDefines.PAYMENT_F);
				} else {
					pbDialog.dismiss();
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT)
								.show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(mContext, "支付失败，您可去订单中心重新支付",
								Toast.LENGTH_SHORT).show();
						finish();

					}
				}
				break;
			// 告诉服务端支付成功了-成功
			case NetworkAsyncCommonDefines.PAYMENT_S:
				Bundle paymentBun = msg.getData();
				if (paymentBun != null) {
					// 获取用户财富信息
					YJTCExec.getInstance().getYJTCList(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							NetworkAsyncCommonDefines.GET_USER_PROPERTY_S,
							NetworkAsyncCommonDefines.GET_USER_PROPERTY_F);
				}
				break;
			// 告诉服务端支付成功了-失败
			case NetworkAsyncCommonDefines.PAYMENT_F:
				Toast.makeText(mContext, "支付成功,服务器出现异常,请联系客服",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;
			// 获取用户的资产成功
			case NetworkAsyncCommonDefines.GET_USER_PROPERTY_S:
				pbDialog.dismiss();
				finish();
				break;
			case NetworkAsyncCommonDefines.GET_USER_PROPERTY_F:
				pbDialog.dismiss();
				finish();
				break;
			// 提交刺绣信息失败
			case NetworkAsyncCommonDefines.TJ_DZSP_BCXX_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "刺绣信息提交失败!", Toast.LENGTH_SHORT)
						.show();
				finish();
				break;
			// 获取定制商品刺绣信息成功
			case NetworkAsyncCommonDefines.GET_DZSP_CX_S:
				Bundle cxBundle = msg.getData();
				if (cxBundle != null) {
					colorList = cxBundle.getParcelableArrayList("colorList");
					fontFamilyList = cxBundle
							.getParcelableArrayList("fontFamilyList");
					mCiXiuColorAdapter.setData(colorList);
					mCiXiuColorAdapter.notifyDataSetChanged();
					mCiXiuFontAdapter.setData(fontFamilyList);
					mCiXiuFontAdapter.notifyDataSetChanged();
					if (colorList == null || fontFamilyList == null
							|| colorList.size() == 0
							|| fontFamilyList.size() == 0) {
						// 不支持刺绣信息
						xsCiXiu(false);
					} else {
						// 支持刺绣信息
						xsCiXiu(true);
					}
				}
				break;
			// 获取定制商品刺绣信息失败
			case NetworkAsyncCommonDefines.GET_DZSP_CX_F:

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

			default:
				break;
			}
		}

	};

	/**
	 * 设置显示刺绣相关View
	 */
	public void xsCiXiu(boolean b) {
		if (b) {
			cxwz_ll.setVisibility(View.VISIBLE);
			cxwz_rl.setVisibility(View.VISIBLE);
			cxys_ll.setVisibility(View.VISIBLE);
			cxys_rl.setVisibility(View.VISIBLE);
			cxzt_ll.setVisibility(View.VISIBLE);
			cxzt_rl.setVisibility(View.VISIBLE);
		} else {
			cxwz_ll.setVisibility(View.GONE);
			cxwz_rl.setVisibility(View.GONE);
			cxys_ll.setVisibility(View.GONE);
			cxys_rl.setVisibility(View.GONE);
			cxzt_ll.setVisibility(View.GONE);
			cxzt_rl.setVisibility(View.GONE);
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
