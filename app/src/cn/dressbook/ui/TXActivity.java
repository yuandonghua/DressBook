package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.JiaoYiJiLuExec;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @description 提现
 * @author 袁东华
 * @date 2016-2-22
 */
@ContentView(R.layout.activity_tx)
public class TXActivity extends BaseActivity {
	private Context mContext = TXActivity.this;
	/**
	 * 申请按钮
	 */
	@ViewInject(R.id.shenqing_tv)
	private TextView shenqing_tv;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 提现记录
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/**
	 * 可提现余额
	 */
	@ViewInject(R.id.ktxye_tv)
	private TextView ktxye_tv;
	/**
	 * 提现时间
	 */
	@ViewInject(R.id.tv_tx_time)
	private TextView tv_tx_time;
	@ViewInject(R.id.zfb_name_tv)
	private TextView zfb_name_tv;
	/**
	 * 支付宝账号
	 */
	@ViewInject(R.id.zfb_zh_tv)
	private TextView zfb_zh_tv;
	/**
	 * 输入提现金额
	 */
	@ViewInject(R.id.txje_et)
	private EditText txje_et;

	@Event({ R.id.back_rl, R.id.shenqing_tv, R.id.operate_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击提现记录
		case R.id.operate_tv:
			startActivity(new Intent(mContext, TiXianJiLuActivity.class));
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击申请
		case R.id.shenqing_tv:
			try {

				String amount = txje_et.getText().toString().trim();
				if (ManagerUtils.getInstance().yjtc.getAlipay() == null
						|| ManagerUtils.getInstance().yjtc.getAlipayName() == null) {
					Toast.makeText(mContext, "未绑定提现支付宝账户", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				if (Double.parseDouble(ManagerUtils.getInstance().yjtc
						.getMoneyCash()) <= 0) {
					Toast.makeText(mContext, "可提现余额为0元", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				if (amount == null || "".endsWith(amount)) {
					Toast.makeText(mContext, "提现金额不能为空", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				if (amount.startsWith(".") || amount.endsWith(".")
						|| amount.indexOf(".") != amount.lastIndexOf(".")) {
					Toast.makeText(mContext, "格式错误,请修改提现金额", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				if (amount.contains(".")) {
					int index = amount.indexOf(".");
					if (amount.length() - 1 - index > 2) {
						Toast.makeText(mContext, "最多保留2位小数,请修改提现金额",
								Toast.LENGTH_SHORT).show();
						break;
					}
				}
				if (Double.parseDouble(amount) < 50) {
					Toast.makeText(mContext, "提现金额不能小于50元", Toast.LENGTH_SHORT)
							.show();
					break;
				}

				if (Double.parseDouble(ManagerUtils.getInstance().yjtc
						.getMoneyCash()) < Double.parseDouble(amount)) {
					Toast.makeText(mContext, "提现金额不能大于可提现余额",
							Toast.LENGTH_SHORT).show();
					break;
				}
				pbDialog.show();
				// 申请余额提现
				JiaoYiJiLuExec.getInstance().shenQinYuETiXian(mHandler,
						ManagerUtils.getInstance().yjtc.getId(),
						ManagerUtils.getInstance().yjtc.getAlipayName(),
						ManagerUtils.getInstance().yjtc.getAlipay(),
						amount, NetworkAsyncCommonDefines.SQTX_S,
						NetworkAsyncCommonDefines.SQTX_F);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;

		}

	}

	@Override
	protected void initView() {
		title_tv.setText("提现");
		operate_tv.setText("提现记录");
		operate_tv.setVisibility(View.VISIBLE);
		

	}

	@Override
	protected void initData() {
		if (Double.parseDouble(ManagerUtils.getInstance().yjtc.getMoneyCash()) <= 0) {
			txje_et.setEnabled(false);
			shenqing_tv.setBackgroundResource(R.drawable.txsq_unselected);
			ktxye_tv.setText("￥0.00元");
		} else {
			ktxye_tv.setText("￥"
					+ ManagerUtils.getInstance().yjtc.getMoneyCash() + "元");
		}

		if (ManagerUtils.getInstance().yjtc.getAlipay() == null
				|| ManagerUtils.getInstance().yjtc.getAlipayName() == null) {
			shenqing_tv.setBackgroundResource(R.drawable.txsq_unselected);
		} else {
			zfb_name_tv
					.setText(ManagerUtils.getInstance().yjtc.getAlipay());
			zfb_zh_tv.setText(ManagerUtils.getInstance()
					.yjtc.getAlipayName());
		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			pbDialog.dismiss();
			switch (msg.what) {
			// 申请余额提现成功
			case NetworkAsyncCommonDefines.SQTX_S:
				Toast.makeText(mContext, "操作成功", Toast.LENGTH_SHORT).show();
				finish();
				break;
			// 申请余额提现失败
			case NetworkAsyncCommonDefines.SQTX_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();

				break;
			}
		}

	};
}
