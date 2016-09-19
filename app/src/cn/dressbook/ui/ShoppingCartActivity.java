package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.ShoppingCartAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.MSTJData;
import cn.dressbook.ui.net.OrderExec;


/**
 * @description: 购物车
 * @author:袁东华
 * @time:2015-9-8下午4:01:34
 */
@ContentView(R.layout.shoppingcart)
public class ShoppingCartActivity extends BaseActivity {
	private Context mContext = ShoppingCartActivity.this;
	private ShoppingCartAdapter mShoppingCartAdapter;
	private ArrayList<MSTJData> orderAttireList = null;
	private boolean isEdit = false;
	/** 商品展示view */
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	/** 编辑按钮 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/** 标题 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 返回按钮 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/** 全选 */
	@ViewInject(R.id.selectall_cb)
	private CheckBox selectall_cb;
	/** 结算 */
	@ViewInject(R.id.payment_tv)
	private TextView payment_tv;
	/** 总额 */
	@ViewInject(R.id.total_value)
	private TextView total_value;


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("购物车");
		operate_tv.setText("编辑");
		operate_tv.setVisibility(View.VISIBLE);
		selectall_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (orderAttireList != null) {

					if (isChecked) {
						for (MSTJData mstj : orderAttireList) {
							mstj.setIsChecked("1");
						}

					} else {
						for (MSTJData mstj : orderAttireList) {
							mstj.setIsChecked("0");
						}

					}
				}
				mShoppingCartAdapter.setData(orderAttireList);
				mShoppingCartAdapter.notifyDataSetChanged();
				total_value.setText("￥" + mShoppingCartAdapter.getSum());
			}
		});
		recyclerview.setHasFixedSize(true);
		recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		mShoppingCartAdapter = new ShoppingCartAdapter(
				ShoppingCartActivity.this, mHandler);
		String suffix = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/order";
		recyclerview.setAdapter(mShoppingCartAdapter);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// 获取购物车商品列表
		OrderExec.getInstance().getShoppingCartInfo(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext), "1",
				NetworkAsyncCommonDefines.GET_SHOPPINGCART_INFO_S,
				NetworkAsyncCommonDefines.GET_SHOPPINGCART_INFO_F);
	}

	@Event({ R.id.operate_tv, R.id.back_rl, R.id.payment_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击操作
		case R.id.operate_tv:
			if (isFinish()) {
				if (isEdit) {
					isEdit = false;
					operate_tv.setText("编辑");
					ArrayList<MSTJData> mstjlist = mShoppingCartAdapter
							.getData();
					if (mstjlist != null && mstjlist.size() > 0) {

						pbDialog.show();
						String orderAttire_id = "";
						for (MSTJData mstj : mstjlist) {
							orderAttire_id += mstj.getId() + ",";
						}
						orderAttire_id = orderAttire_id.substring(0,
								orderAttire_id.length() - 1);
						String num = "";
						for (MSTJData mstj : mstjlist) {
							num += mstj.getNum() + ",";
						}
						num = num.substring(0, num.length() - 1);
						mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.REFRESH_SHOPPINGCART);
						// 编辑购物车
						OrderExec
								.getInstance()
								.editShoppingCartInfo(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										orderAttire_id,
										num,
										NetworkAsyncCommonDefines.EDIT_SHOPPINGCART_S,
										NetworkAsyncCommonDefines.EDIT_SHOPPINGCART_F);
					} else {
					}
				} else {
					isEdit = true;
					operate_tv.setText("完成");

				}
				selectall_cb.setChecked(false);
				mShoppingCartAdapter.setEdit(isEdit);
				mShoppingCartAdapter.notifyDataSetChanged();
			}
			break;
		// 点击结算
		case R.id.payment_tv:

			if (orderAttireList != null && orderAttireList.size() > 0) {
				String orderAttire_id = "";
				for (MSTJData mstj : orderAttireList) {
					if ("1".equals(mstj.getIsChecked())) {
						orderAttire_id += mstj.getId() + ",";
					}
				}
				if (orderAttire_id != "") {
					if (orderAttire_id.lastIndexOf(",") == orderAttire_id
							.length() - 1) {
						orderAttire_id = orderAttire_id.substring(0,
								orderAttire_id.length() - 1);
						pbDialog.show();
						// 提交购物车商品
						OrderExec
								.getInstance()
								.submitShoppingCartInfo(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										orderAttire_id,
										NetworkAsyncCommonDefines.SUBMIT_SHOPPINGCART_S,
										NetworkAsyncCommonDefines.SUBMIT_SHOPPINGCART_F);
					}
				} else {
					Toast.makeText(mContext, "请选择商品,无法结算", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Toast.makeText(mContext, "暂无商品,无法结算", Toast.LENGTH_SHORT)
						.show();
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
			// 编辑购物车成功
			case NetworkAsyncCommonDefines.EDIT_SHOPPINGCART_S:
				Bundle editBun = msg.getData();
				if (editBun != null) {
					String recode = editBun.getString("recode");
					String redesc = editBun.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
				}

				pbDialog.dismiss();
				break;
			// 编辑购物车失败
			case NetworkAsyncCommonDefines.EDIT_SHOPPINGCART_F:
				pbDialog.dismiss();
				break;
			// 提交成功
			case NetworkAsyncCommonDefines.SUBMIT_SHOPPINGCART_S:
				Bundle resultBun = msg.getData();
				if (resultBun != null) {
					String recode = resultBun.getString("recode");
					String redesc = resultBun.getString("redesc");
					String order_id = resultBun.getString("order_id");
					String yqTotal = resultBun.getString("yqTotal");
					String freightShow = resultBun.getString("freightShow");
					String users_yqPoints = resultBun
							.getString("users_yqPoints");
					String users_availabe = resultBun
							.getString("users_availabe");
					String priceTotal = resultBun.getString("priceTotal");
					ArrayList<MSTJData> mstjList = resultBun
							.getParcelableArrayList("mstjList");
					if ("0".equals(recode) && mstjList != null
							&& order_id != null) {
						Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT)
								.show();
						Intent submitOrderActivity = new Intent(mContext,
								QueRenDingDanActivity.class);
						submitOrderActivity
								.putExtra("freightShow", freightShow);
						submitOrderActivity.putExtra("users_yqPoints",
								users_yqPoints);
						submitOrderActivity.putExtra("users_availabe",
								users_availabe);
						submitOrderActivity.putExtra("order_id", order_id);
						submitOrderActivity.putExtra("yqTotal", yqTotal);
						submitOrderActivity.putExtra("priceTotal", priceTotal);
						submitOrderActivity.putParcelableArrayListExtra(
								"mstjList", mstjList);
						startActivity(submitOrderActivity);
					} else {
						Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT)
								.show();
					}
					pbDialog.dismiss();
					finish();
				}
				break;
			// 提交失败
			case NetworkAsyncCommonDefines.SUBMIT_SHOPPINGCART_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				break;
			// 删除商品成功
			case NetworkAsyncCommonDefines.DELETE_SHOPPINGCART_S:
				Bundle shoppingBun = msg.getData();
				if (shoppingBun != null) {
					String recode = shoppingBun.getString("recode");
					String redesc = shoppingBun.getString("redesc");
					if ("0".equals(recode)) {
						mShoppingCartAdapter.notifyDataSetChanged();
					} else {

						OrderExec
								.getInstance()
								.getShoppingCartInfo(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										"1",
										NetworkAsyncCommonDefines.GET_SHOPPINGCART_INFO_S,
										NetworkAsyncCommonDefines.GET_SHOPPINGCART_INFO_F);
					}
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
				}
				total_value.setText("￥" + mShoppingCartAdapter.getSum());
				break;
			// 删除商品失败
			case NetworkAsyncCommonDefines.DELETE_SHOPPINGCART_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				break;
			// 刷新购物车总金额
			case NetworkAsyncCommonDefines.REFRESH_SHOPPINGCART:
				total_value.setText("￥" + mShoppingCartAdapter.getSum());
				break;
			// 获取用户购物车信息成功
			case NetworkAsyncCommonDefines.GET_SHOPPINGCART_INFO_S:
				Bundle bun = msg.getData();
				if (bun != null) {
					orderAttireList = bun.getParcelableArrayList("list");
					String recode = bun.getString("recode");
					String redesc = bun.getString("redesc");
					if ("2".equals(recode)) {
						Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT)
								.show();
						mShoppingCartAdapter.setData(null);
						mShoppingCartAdapter.notifyDataSetChanged();
					}
					mShoppingCartAdapter.setData(orderAttireList);
					mShoppingCartAdapter.notifyDataSetChanged();
				} else {
					mShoppingCartAdapter.setData(null);
					mShoppingCartAdapter.notifyDataSetChanged();
				}
				break;
			// 获取用户购物车信息失败
			case NetworkAsyncCommonDefines.GET_SHOPPINGCART_INFO_F:
				mShoppingCartAdapter.setData(null);
				mShoppingCartAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}

		}

	};
}
