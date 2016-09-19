package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.ui.adapter.LogisticsInfoAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.LogisticsInfo;
import cn.dressbook.ui.net.OrderExec;
import cn.dressbook.ui.recyclerview.FullyLinearLayoutManager;

/**
 * @description: 物流信息
 * @author:袁东华
 * @time:2015-9-15下午2:43:43
 */
@ContentView(R.layout.logisticsinfo)
public class LogisticsInfoActivity extends BaseActivity {
	private LogisticsInfoAdapter mLogisticsInfoAdapter;
	private Context mContext;
	private String mUrl;
	/** 物流展示view */
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	/** 返回按钮 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/** 标题 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 物流名称 */
	@ViewInject(R.id.wlgs_tv)
	private TextView wlgs_tv;
	/** 物流单号 */
	@ViewInject(R.id.wldh_tv)
	private TextView wldh_tv;
	private LogisticsInfo mLogisticsInfo;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("物流详情");
		recyclerview.setHasFixedSize(true);
		FullyLinearLayoutManager mll = new FullyLinearLayoutManager(mContext);
		mll.setOrientation(LinearLayout.VERTICAL);
		recyclerview.setLayoutManager(mll);
		// 设置Item增加、移除动画
		recyclerview.setItemAnimator(new DefaultItemAnimator());
		// 添加分割线
		recyclerview.addItemDecoration(new ItemDecoration() {

		});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		initIntent();
		OrderExec.getInstance().getLogisticsInfo(mHandler, mUrl,
				NetworkAsyncCommonDefines.GET_LOGISTICS_S,
				NetworkAsyncCommonDefines.GET_LOGISTICS_F);

	}

	private void initIntent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			mUrl = intent.getStringExtra("url");
		}
	}

	@Event(R.id.back_rl)
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
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
			// 获取物流信息成功
			case NetworkAsyncCommonDefines.GET_LOGISTICS_S:
				Bundle data = msg.getData();
				if (data != null) {
					mLogisticsInfo = data.getParcelable("logisticsInfo");
					String name = getLogisticsName(mLogisticsInfo
							.getCompanytype());

					wlgs_tv.setText("物流公司:" + name);
					wldh_tv.setText("物流单号:" + mLogisticsInfo.getCodenumber());
					mLogisticsInfoAdapter = new LogisticsInfoAdapter(
							LogisticsInfoActivity.this, mHandler);
					mLogisticsInfoAdapter.setData(mLogisticsInfo.getData());
					recyclerview.setAdapter(mLogisticsInfoAdapter);
				} else {
				}
				break;
			// 获取物流信息失败
			case NetworkAsyncCommonDefines.GET_LOGISTICS_F:
				break;
			default:
				break;
			}

		}

	};

	private String getLogisticsName(String name) {
		return name = name.replace("shentong", "申通").replace("ems", "EMS")
				.replace("shunfeng", "顺丰").replace("yuantong", "圆通")
				.replace("zhongtong", "中通").replace("yunda", "韵达")
				.replace("tiantian", "天天").replace("huitongkuaidi", "汇通")
				.replace("quanfengkuaidi", "全峰").replace("debangwuliu", "德邦")
				.replace("zhaijisong", "宅急送");
	}
}
