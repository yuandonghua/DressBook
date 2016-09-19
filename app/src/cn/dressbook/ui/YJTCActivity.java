package cn.dressbook.ui;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.HuiYuanExec;
import cn.dressbook.ui.net.YJTCExec;
import cn.dressbook.ui.utils.ToastUtils;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @description 业绩提成界面
 * @author 袁东华
 * @date 2016-2-23
 */
@ContentView(R.layout.activity_yjtc)
public class YJTCActivity extends BaseActivity {
	@ViewInject(R.id.qrcode_iv)
	private ImageView qrcode_iv;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	@ViewInject(R.id.operate_tv)
	TextView operate_tv;
	/** 累计提成收入 */
	@ViewInject(R.id.tv_leiji)
	private TextView tv_leiji;
	/** 余额描述 */
	@ViewInject(R.id.yue_value)
	private TextView yue_value;
	/** 预期收入描述 */
	@ViewInject(R.id.yqsr_value)
	private TextView yqsr_value;
	/** 提现描述 */
	@ViewInject(R.id.tx_value)
	private TextView tx_value;
	/** 支付宝描述 */
	@ViewInject(R.id.zfb_value)
	private TextView zfb_value;
	/** 我的客户 */
	@ViewInject(R.id.rl_wdkh)
	private RelativeLayout rl_wdkh;
	/** 我的客户描述 */
	@ViewInject(R.id.tv_wdkh_desc)
	private TextView tv_wdkh_desc;
	/** 邀请码 */
	@ViewInject(R.id.rl_yqm)
	private RelativeLayout rl_yqm;
	@ViewInject(R.id.yqm_tv)
	private TextView yqm_tv;
	private Context mContext;

	@Override
	protected void initView() {
		title_tv.setText("业绩提成");
		mContext = this;
		operate_tv.setVisibility(View.VISIBLE);
		operate_tv.setText("帮助");

	}

	@Override
	protected void initData() throws Exception {

	}

	@Event({ R.id.back_rl, R.id.yue_rl, R.id.yusr_rl, R.id.tx_rl, R.id.zfb_rl,
			R.id.rl_wdkh, R.id.operate_tv })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击余额
		case R.id.yue_rl:
			Intent yueIntent = new Intent(mContext, JiaoYiJiLuActivity.class);
			startActivity(yueIntent);
			break;
		// 点击预期收入
		case R.id.yusr_rl:
			Intent ybj = new Intent(mContext, YQSRActivity.class);
			startActivity(ybj);
			break;
		// 点击提现
		case R.id.tx_rl:
			Intent tx = new Intent(mContext, TXActivity.class);
			startActivity(tx);

			break;
		// 点击支付宝
		case R.id.zfb_rl:
			startActivity(new Intent(YJTCActivity.this, BJZFBActivity.class));

			break;
		// 点击我的客户
		case R.id.rl_wdkh:
			if (ManagerUtils.getInstance().yjtc != null) {
				Intent intent = new Intent(mContext, WDKHActivity.class);
				intent.putExtra("wdkh",
						ManagerUtils.getInstance().yjtc.getMembers());
				startActivity(intent);
			} else {
				ToastUtils.getInstance().showToast(mContext, "您还没有客户", 200);
			}

			break;
		// 点击帮助
		case R.id.operate_tv:
			pbDialog.show();
			HuiYuanExec.getInstance().getHuiYuanHTML(mHandler, "ggcx_mbabout",
					1, -1);

			break;

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		pbDialog.show();
		// 获取业绩提成列表
		YJTCExec.getInstance().getYJTCList(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_YJTC_S,
				NetworkAsyncCommonDefines.GET_YJTC_F);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 获取帮助成功
			case 1:
				Bundle bangZhu = msg.getData();
				if (bangZhu != null) {
					String url = bangZhu.getString("url");

					Intent productIntent = new Intent(mContext,
							WangYeActivity.class);
					productIntent.putExtra("title", "会员");
					productIntent.putExtra("url", url);
					mContext.startActivity(productIntent);
				}
				pbDialog.dismiss();
				break;
			// 获取帮助失败
			case -1:
				pbDialog.dismiss();
				break;
			/* 获取业绩提成列表成功 */
			case NetworkAsyncCommonDefines.GET_YJTC_S:
				setHint();
				pbDialog.dismiss();
				break;
			/* 获取业绩提成列表失败 */
			case NetworkAsyncCommonDefines.GET_YJTC_F:
				pbDialog.dismiss();
				setHint();
				break;

			}
		};
	};

	private void setHint() {
		if (ManagerUtils.getInstance().yjtc != null) {
			if (ManagerUtils.getInstance().yjtc.getTotalIncom() != null
					&& !"".equals(ManagerUtils.getInstance().yjtc
							.getTotalIncom())) {
				tv_leiji.setText("累计提成收入"
						+ ManagerUtils.getInstance().yjtc.getTotalIncom() + "元");
			} else {
				tv_leiji.setText("您当前还不是会员");
			}
			if (ManagerUtils.getInstance().yjtc.getMembers() != null) {
				tv_wdkh_desc.setText(ManagerUtils.getInstance().yjtc
						.getMembers());
			} else {
				tv_wdkh_desc.setText("成为会员，拥有客户，乐享奖励");
			}

			if (ManagerUtils.getInstance().yjtc.getMoneyCash() != null) {
				tx_value.setText(ManagerUtils.getInstance().yjtc.getMoneyCash()
						+ "元");
			} else {
				tx_value.setText("0.00元");
			}
			if (ManagerUtils.getInstance().yjtc.getMoneyOrder() != null) {

				yqsr_value.setText(ManagerUtils.getInstance().yjtc
						.getMoneyOrder() + "元");
			} else {
				yqsr_value.setText("0.00元");
			}
			if (ManagerUtils.getInstance().yjtc.getMoneyAvailable() != null
					&& !"".equals(ManagerUtils.getInstance().yjtc
							.getMoneyAvailable())) {

				double yue = Double.parseDouble(ManagerUtils.getInstance().yjtc
						.getMoneyAvailable());
				if (yue < 10000) {
					yue_value.setText(ManagerUtils.getInstance().yjtc
							.getMoneyAvailable() + "元");

				} else {
					yue_value.setText(yue / 10000 + "万元");
				}
			} else {
				yue_value.setText("0.00元");
			}
			if (!"".equals(ManagerUtils.getInstance().yjtc.getAlipay())
					&& !"".equals(ManagerUtils.getInstance().yjtc
							.getAlipayName())) {
				zfb_value.setText("已绑定");
			} else {
				zfb_value.setText("未绑定");

			}
			if (ManagerUtils.getInstance().yjtc.getShareCode() != null) {
				rl_yqm.setVisibility(View.VISIBLE);
				yqm_tv.setText(ManagerUtils.getInstance().yjtc.getShareCode());
				// 加载已存在的二维码
				x.image()
						.bind(qrcode_iv,
								ManagerUtils.getInstance().getQrPath2(
										activity,
										ManagerUtils.getInstance().yjtc
												.getShareCode()),
								ManagerUtils.getInstance().getImageOptions(
										ImageView.ScaleType.CENTER_INSIDE,
										false), new CommonCallback<Drawable>() {

									@Override
									public void onSuccess(Drawable arg0) {
										// TODO Auto-generated method stub
										LogUtil.e("onSuccess");
									}

									@Override
									public void onFinished() {
										// TODO Auto-generated method stub
										LogUtil.e("onFinished");
									}

									@Override
									public void onError(Throwable arg0,
											boolean arg1) {
										// TODO Auto-generated method stub
										LogUtil.e("onError:"
												+ arg0.getMessage());
									}

									@Override
									public void onCancelled(
											CancelledException arg0) {
										// TODO Auto-generated method stub
										LogUtil.e("onCancelled");
									}
								});
			} else {
				rl_yqm.setVisibility(View.GONE);
			}

		} else {
		}

	}
}
