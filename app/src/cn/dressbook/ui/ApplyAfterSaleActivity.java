package cn.dressbook.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.ApplyAfterSaleAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.model.MSTJData;
import cn.dressbook.ui.net.OrderExec;
import cn.dressbook.ui.recyclerview.FullyLinearLayoutManager;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description: 申请售后
 * @author:袁东华
 * @time:2015-9-15上午11:53:21
 */
@ContentView(R.layout.applyaftersale)
public class ApplyAfterSaleActivity extends BaseActivity {
	private Context mContext = ApplyAfterSaleActivity.this;
	private ApplyAfterSaleAdapter mApplyAfterSaleAdapter;
	private ArrayList<MSTJData> orderAttireList = null;
	private String order_id;
	/** 退货原因 */
	@ViewInject(R.id.remark_et)
	private EditText remark_et;
	/**
	 * 提交按钮
	 */
	@ViewInject(R.id.tj_tv)
	private TextView tj_tv;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 商品展示view */
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		recyclerview.setHasFixedSize(true);
		title_tv.setText("申请售后");
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
		mApplyAfterSaleAdapter = new ApplyAfterSaleAdapter(this, mHandler);
		mApplyAfterSaleAdapter.setData(orderAttireList);
		recyclerview.setAdapter(mApplyAfterSaleAdapter);
		// 获取购物车商品列表
		// OrderExec.getInstance().getOrderInfo(mHandler,
		// ManagerUtils.getInstance().getUser_id(), order_id,
		// NetworkAsyncCommonDefines.GET_ORDERINFO_S,
		// NetworkAsyncCommonDefines.GET_ORDERINFO_F);
	}

	private void initIntent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			orderAttireList = intent.getParcelableArrayListExtra("list");
			order_id = intent.getStringExtra("order_id");
		} else {
		}
	}

	@Event({ R.id.back_rl, R.id.tj_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击操作按钮
		case R.id.tj_tv:
			if (isFinish()) {

				String desc = remark_et.getText().toString();
				if (desc == null || "".equals(desc)) {
					Toast.makeText(mContext, "原因描述不能为空", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				String orderAttire_id = "";
				ArrayList<MSTJData> list = mApplyAfterSaleAdapter.getData();
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						if ("1".equals(list.get(i).getIsChecked())) {
							orderAttire_id += list.get(i).getId() + ",";
						}
					}
				}
				if (orderAttire_id.length() > 0) {
					orderAttire_id = orderAttire_id.substring(0,
							orderAttire_id.length() - 2);
				} else {
					Toast.makeText(mContext, "请选择商品", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				pbDialog.show();
				// 申请售后
				OrderExec.getInstance().applyAfterSale(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						order_id, orderAttire_id, desc,
						NetworkAsyncCommonDefines.APPLYAFTERSALE_S,
						NetworkAsyncCommonDefines.APPLYAFTERSALE_F);
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
			case NetworkAsyncCommonDefines.APPLYAFTERSALE_S:
				pbDialog.dismiss();
				Bundle bun = msg.getData();
				if (bun != null) {
					String desc = bun.getString("redesc");
					Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
			case NetworkAsyncCommonDefines.APPLYAFTERSALE_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}

		}

	};
}
