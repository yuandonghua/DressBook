package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.DZSPDetailsImagesAdapter;
import cn.dressbook.ui.adapter.DZSPDetailsParamAdapter;
import cn.dressbook.ui.adapter.LSDZFLAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.DZSPDetails;
import cn.dressbook.ui.model.LSDZFL;
import cn.dressbook.ui.net.LSDZExec;
import cn.dressbook.ui.net.MYXExec;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.recyclerview.FullyGridLayoutManager;
import cn.dressbook.ui.recyclerview.FullyLinearLayoutManager;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description 定制商品界面
 * @author 袁东华
 * @date 2016-1-22
 */
@ContentView(R.layout.dzsp)
public class DZSPActivity extends BaseActivity {
	private Context mContext = DZSPActivity.this;
	private ArrayList<LSDZFL> mList1 = new ArrayList<LSDZFL>();
	private ArrayList<LSDZFL> mList2 = new ArrayList<LSDZFL>();
	private DZSPDetailsParamAdapter mDZSPDetailsParamAdapter;
	private DZSPDetailsImagesAdapter mDZSPDetailsImagesAdapter;
	@ViewInject(R.id.recyclerview1)
	private RecyclerView recyclerview1;
	@ViewInject(R.id.recyclerview2)
	private RecyclerView recyclerview2;
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 标题图片
	 */
	@ViewInject(R.id.title_iv)
	private ImageView title_iv;
	/**
	 * 标题
	 */
	@ViewInject(R.id.ms_tv)
	private TextView ms_tv;
	/**
	 * vip价格
	 */
	@ViewInject(R.id.price2_tv)
	private TextView price2_tv;
	@ViewInject(R.id.bz_iv)
	private ImageView bz_iv;
	@ViewInject(R.id.twxq_iv)
	private ImageView twxq_iv;
	private String attire_id;

	@SuppressWarnings("deprecation")
	@Override
	protected void initView() {
		title_tv.setText("定制商品");
		LayoutParams lp1 = (LayoutParams) bz_iv.getLayoutParams();
		lp1.height = 144;
		bz_iv.setLayoutParams(lp1);

		LayoutParams lp2 = (LayoutParams) twxq_iv.getLayoutParams();
		lp2.height = 89;
		twxq_iv.setLayoutParams(lp2);
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		attire_id = getIntent().getStringExtra("attire_id");
		LogUtil.e("attire_id:" + attire_id);
		// 获取量身定制分类列表
		LSDZExec.getInstance().getDZSPDetails(mHandler, attire_id,
				NetworkAsyncCommonDefines.GET_LSDZ_LIST_S,
				NetworkAsyncCommonDefines.GET_LSDZ_LIST_F);

	}

	@Event({ R.id.back_rl, R.id.sc_tv, R.id.diy_tv, R.id.gm_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击试穿
		case R.id.sc_tv:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				Toast.makeText(mContext, "试穿功能暂未开放", Toast.LENGTH_SHORT).show();
			} else {
				Intent loginIntent = new Intent(mContext, LoginActivity.class);
				startActivity(loginIntent);
			}
			break;
		// 点击调整细节
		case R.id.diy_tv:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				Intent DIYActivity = new Intent(DZSPActivity.this,
						TiaoZhengXiJieActivity.class);
				DIYActivity.putExtra("attire_id", attire_id);
				startActivity(DIYActivity);
			} else {
				Intent loginIntent = new Intent(mContext, LoginActivity.class);
				startActivity(loginIntent);
			}
			break;
		// 点击购买
		case R.id.gm_tv:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				pbDialog.show();
				if (ManagerUtils.getInstance().getUser_id(mContext) != null) {

					// 获取定制商品补充信息
					LSDZExec.getInstance().getDZSPBCXX(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							attire_id,
							NetworkAsyncCommonDefines.GET_DZSP_BCXX_S,
							NetworkAsyncCommonDefines.GET_DZSP_BCXX_F);
				} else {
					Intent bindPhone = new Intent(mContext, LoginActivity.class);
					startActivity(bindPhone);
					((Activity) mContext).overridePendingTransition(
							R.anim.back_enter, R.anim.anim_exit);
					pbDialog.dismiss();
				}
			} else {
				Intent loginIntent = new Intent(mContext, LoginActivity.class);
				startActivity(loginIntent);
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
			// 获取定制商品补充信息成功
			case NetworkAsyncCommonDefines.GET_DZSP_BCXX_S:
				Bundle buxxBundle = msg.getData();
				if (buxxBundle != null) {
					String sex = buxxBundle.getString("sex");
					String order_id = buxxBundle.getString("order_id");
					String order_dz_attire_id = buxxBundle
							.getString("order_dz_attire_id");
					String template_id = buxxBundle.getString("template_id");
					String order_priceTotal = buxxBundle
							.getString("order_priceTotal");
					Intent BCXXActivity = new Intent(DZSPActivity.this,
							BCXXActivity.class);
					BCXXActivity.putExtra("order_id", order_id);
					BCXXActivity.putExtra("sex", sex);
					BCXXActivity.putExtra("order_dz_attire_id",
							order_dz_attire_id);
					BCXXActivity.putExtra("template_id", template_id);
					BCXXActivity.putExtra("order_priceTotal", order_priceTotal);
					startActivity(BCXXActivity);
				}
				pbDialog.dismiss();
				break;
			// 获取定制商品补充信息失败
			case NetworkAsyncCommonDefines.GET_DZSP_BCXX_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
				break;
			// 获取量身定制列表成功
			case NetworkAsyncCommonDefines.GET_LSDZ_LIST_S:
				Bundle myxData = msg.getData();
				if (myxData != null) {
					DZSPDetails mDZSPDetails = myxData
							.getParcelable("DZSPDetails");
					if (mDZSPDetails != null) {
						// 设置标题图片
						x.image().bind(
								title_iv,
								mDZSPDetails.getTitlePic(),
								ManagerUtils.getInstance().getImageOptions(
										ImageView.ScaleType.CENTER_INSIDE));
						ms_tv.setText(mDZSPDetails.getTitle());
						price2_tv.setText("￥" + mDZSPDetails.getPriceVip());
						mDZSPDetailsParamAdapter = new DZSPDetailsParamAdapter(
								DZSPActivity.this, mHandler);
						mDZSPDetailsParamAdapter.setData(mDZSPDetails
								.getParams());
						FullyGridLayoutManager mFullyGridLayoutManager = new FullyGridLayoutManager(
								mContext, 2);
						recyclerview1.setLayoutManager(mFullyGridLayoutManager);
						recyclerview1.setAdapter(mDZSPDetailsParamAdapter);

						mDZSPDetailsImagesAdapter = new DZSPDetailsImagesAdapter(
								DZSPActivity.this, mHandler);
						mDZSPDetailsImagesAdapter.setData(mDZSPDetails
								.getImages());
						FullyLinearLayoutManager mFullyLinearLayoutManager = new FullyLinearLayoutManager(
								mContext);
						recyclerview2
								.setLayoutManager(mFullyLinearLayoutManager);
						recyclerview2.setAdapter(mDZSPDetailsImagesAdapter);
					}
				}
				break;
			// 获取量身定制列表失败
			case NetworkAsyncCommonDefines.GET_LSDZ_LIST_F:

				break;

			default:
				break;
			}
		}

	};

}
