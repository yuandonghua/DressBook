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
import cn.dressbook.ui.adapter.ManageAddressAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.net.AddressExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;


/**
 * @description: 管理收货地址
 * @author:袁东华
 * @time:2015-9-1上午10:26:01
 */
@ContentView(R.layout.manageaddress)
public class GuanLiShouHuoDiZhi extends BaseActivity {
	private Context mContext = GuanLiShouHuoDiZhi.this;
	private RecyclerView recyclerview;
	private ManageAddressAdapter mManageAddressAdapter;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	@ViewInject(R.id.operate_iv)
	private ImageView operate_iv;

	/**
	 * 提示
	 */
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;
	private ArrayList<Address> mAddressList;


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("管理收货地址");
		operate_iv.setVisibility(View.VISIBLE);
		recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
		recyclerview.setHasFixedSize(true);
		recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
		mManageAddressAdapter = new ManageAddressAdapter(mContext, mHandler);
		recyclerview.setAdapter(mManageAddressAdapter);
		//添加分割线
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

					mManageAddressAdapter.setData(mAddressList);
					mManageAddressAdapter.notifyDataSetChanged();
					if (mAddressList == null || mAddressList.size() == 0) {
						hint_tv.setVisibility(View.VISIBLE);
					} else {
						hint_tv.setVisibility(View.GONE);
					}
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

	@Event({ R.id.back_rl, R.id.operate_iv })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击添加
		case R.id.operate_iv:
			Intent addAddressActivity = new Intent(mContext,
					AddAddressActivity.class);
			startActivity(addAddressActivity);
			overridePendingTransition(R.anim.back_enter, R.anim.anim_exit);
			break;
		default:
			break;
		}
	};
}
