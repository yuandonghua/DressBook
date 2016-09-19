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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.XuanZeDiZhiAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.net.AddressExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;

/**
 * @description:选择收货地址
 * @author:袁东华
 * @time:2015-11-27上午10:03:04
 */
@ContentView(R.layout.manageaddress)
public class XuanZeDiZhiActivity extends BaseActivity {
	private Context mContext = XuanZeDiZhiActivity.this;
	private RecyclerView recyclerview;
	private XuanZeDiZhiAdapter mXuanZeDiZhiAdapter;
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
	 * 管理
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;

	/**
	 * 提示
	 */
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;
	private ArrayList<Address> mAddressList;
	/**
	 * 上一次选择的position
	 */
	private int lastPosition = 0;


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("收货地址");
		operate_tv.setVisibility(View.VISIBLE);
		operate_tv.setText("管理");
		recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
		recyclerview.setHasFixedSize(true);
		recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
		mXuanZeDiZhiAdapter = new XuanZeDiZhiAdapter(mContext, mHandler);
		recyclerview.setAdapter(mXuanZeDiZhiAdapter);
		// 添加分割线
		recyclerview
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						this)
						.color(getResources().getColor(R.color.black12))
						.size(getResources().getDimensionPixelSize(
								R.dimen.divider))
						.margin(getResources().getDimensionPixelSize(
								R.dimen.leftmargin),
								getResources().getDimensionPixelSize(
										R.dimen.rightmargin)).build());
		// 点击条目
		mXuanZeDiZhiAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				mAddressList.get(lastPosition).setState("0");
				mXuanZeDiZhiAdapter.setData(mAddressList);
				mXuanZeDiZhiAdapter.notifyItemChanged(lastPosition);
				mAddressList.get(position).setState("1");
				mXuanZeDiZhiAdapter.notifyItemChanged(position);
				lastPosition = position;
				ManagerUtils.getInstance().setAddress(mAddressList.get(position));
				finish();
			}
		});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		pbDialog.show();

		AddressExec.getInstance().getAddressListFromSD(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SD_S,
				NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SD_F);

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		pbDialog.show();
		AddressExec.getInstance().getAddressListFromServer(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SERVER_S,
				NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SERVER_F);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			pbDialog.dismiss();
			switch (msg.what) {
			// 设置默认收货地址成功
			case NetworkAsyncCommonDefines.SET_DEFAULT_ADDRESS_S:
				AddressExec.getInstance().getAddressListFromServer(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SERVER_S,
						NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SERVER_F);
				break;
			// 设置默认收货地址成功
			case NetworkAsyncCommonDefines.SET_DEFAULT_ADDRESS_F:
				AddressExec.getInstance().getAddressListFromServer(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SERVER_S,
						NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SERVER_F);
				break;
			// 从SD卡获取收货列表成功
			case NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SD_S:
				Bundle addressBun = msg.getData();
				if (addressBun != null) {
					mAddressList = addressBun
							.getParcelableArrayList("addresslist");

					if (mAddressList == null || mAddressList.size() == 0) {
						hint_tv.setVisibility(View.VISIBLE);
					} else {
						for (int i = 0; i < mAddressList.size(); i++) {
							mAddressList.get(i).setState("0");
						}
						hint_tv.setVisibility(View.GONE);
					}
					mXuanZeDiZhiAdapter.setData(mAddressList);
					mXuanZeDiZhiAdapter.notifyDataSetChanged();
				}
				break;
			// 从SD卡获取收货列表失败
			case NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SD_F:
				AddressExec.getInstance().getAddressListFromServer(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SERVER_S,
						NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SERVER_F);
				break;
			// 从服务端获取收货列表成功
			case NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SERVER_S:
				AddressExec.getInstance().getAddressListFromSD(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SD_S,
						NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SD_F);
				break;
			// 从服务端获取收货列表失败
			case NetworkAsyncCommonDefines.GET_CITYLIST_FROM_SERVER_F:
				if (mAddressList == null || mAddressList.size() == 0) {
					hint_tv.setVisibility(View.VISIBLE);
				} else {
					hint_tv.setVisibility(View.GONE);
				}
				break;
			default:
				break;
			}
		}

	};

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击添加
		case R.id.operate_tv:
			Intent manageAddress = new Intent(mContext, GuanLiShouHuoDiZhi.class);
			startActivity(manageAddress);
			break;
		default:
			break;
		}
	};
}
