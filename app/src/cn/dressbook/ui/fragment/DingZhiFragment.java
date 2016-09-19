package cn.dressbook.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.BaseActivity;
import cn.dressbook.ui.LSDZFLActivity;
import cn.dressbook.ui.MainActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.ZldzActivity;
import cn.dressbook.ui.adapter.LSDZAdapter;
import cn.dressbook.ui.adapter.LSDZFLAdapter;
import cn.dressbook.ui.adapter.LbtVpAdapter;
import cn.dressbook.ui.bean.ContactInfo;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.DingZhiListener;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.DZSPFL;
import cn.dressbook.ui.model.GuangGao;
import cn.dressbook.ui.model.LSDZFL;
import cn.dressbook.ui.net.LSDZExec;
import cn.dressbook.ui.net.MYXExec;
import cn.dressbook.ui.net.PeiZhiExec;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.net.SchemeExec;
import cn.dressbook.ui.recyclerview.FullyGridLayoutManager;
import cn.dressbook.ui.recyclerview.FullyLinearLayoutManager;
import cn.dressbook.ui.utils.ContactUtils;
import cn.dressbook.ui.utils.ScreenUtils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description 量身定制界面
 * @author 袁东华
 * @date 2016-1-22
 */
@ContentView(R.layout.lsdz)
public class DingZhiFragment extends BaseFragment {

	private ArrayList<DZSPFL> mList = new ArrayList<DZSPFL>();
	private LSDZAdapter mLSDZAdapter;
	@ViewInject(R.id.recyclerview1)
	private RecyclerView recyclerview1;
	@ViewInject(R.id.recyclerview2)
	private RecyclerView recyclerview2;
	@ViewInject(R.id.viewPager)
	private ViewPager viewPager;
	@ViewInject(R.id.lbt_ll)
	private LinearLayout lbt_ll;
	@ViewInject(R.id.rl1)
	private RelativeLayout rl1;
	private LbtVpAdapter mLbtVpAdapter;
	private LSDZFLAdapter mLSDZFLAdapter;
	private FullyLinearLayoutManager mLinearLayoutManager;
	private FullyGridLayoutManager mFullyGridLayoutManager;
	private String sex;
	private List<GuangGao> ggList = new ArrayList<GuangGao>();
	private ArrayList<LSDZFL> flList = new ArrayList<LSDZFL>();
	private int lbtdot, currentPosition;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		try {
			initView();
			initData();
			initListener();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initListener() {
		// TODO Auto-generated method stub
		MainActivity.setDingZhiListener(new DingZhiListener() {

			@Override
			public void onLazyLoad() {
				// TODO Auto-generated method stub
				try {
					initData();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	protected void initView() {
		mLSDZAdapter = new LSDZAdapter(getActivity(), mHandler);
		mLinearLayoutManager = new FullyLinearLayoutManager(getActivity());
		recyclerview2.setLayoutManager(mLinearLayoutManager);
		recyclerview2.setAdapter(mLSDZAdapter);
		mLSDZFLAdapter = new LSDZFLAdapter(getActivity(), mHandler);
		mFullyGridLayoutManager = new FullyGridLayoutManager(mContext, 5);
		recyclerview1.setLayoutManager(mFullyGridLayoutManager);
		recyclerview1.setAdapter(mLSDZFLAdapter);
		mLSDZFLAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				if (flList != null && flList.size() > 0) {
					if (position == 4) {
						Intent intent = new Intent(mContext,
								LSDZFLActivity.class);
						intent.putExtra("sex", "1");
						startActivity(intent);
					} else if (position == flList.size() - 1) {
						Intent intent = new Intent(mContext,
								LSDZFLActivity.class);
						intent.putExtra("sex", "2");
						startActivity(intent);
					} else {
						Intent intent = new Intent(mContext, ZldzActivity.class);
						intent.putExtra("cls_id", flList.get(position)
								.getDzCls_id());
						intent.putExtra("title", flList.get(position)
								.getDzCls_name());
						startActivity(intent);
					}

				}
			}
		});

		int width = ScreenUtils.getScreenWidth(getActivity());
		int height = width * 230 / 720;
		android.widget.LinearLayout.LayoutParams paramsvp = (android.widget.LinearLayout.LayoutParams) rl1
				.getLayoutParams();
		paramsvp.height = height;
		rl1.setLayoutParams(paramsvp);
		mLbtVpAdapter = new LbtVpAdapter(getActivity());
		mLbtVpAdapter.setData(ggList);
		viewPager.setAdapter(mLbtVpAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				if (ggList != null && ggList.size() > 0) {
					mHandler.removeCallbacks(run);
					int newposition = position % ggList.size();
					lbt_ll.getChildAt(currentPosition).setEnabled(false);
					lbt_ll.getChildAt(newposition).setEnabled(true);
					currentPosition = newposition;
					mHandler.postDelayed(run, 3000);
				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		sex = SharedPreferenceUtils.getInstance().getSex(getActivity());
		if ("未设置".equals(sex) || "".equals(sex)) {
			sex = "0";
		} else if ("男".equals(sex)) {
			sex = "1";
		} else if ("女".equals(sex)) {
			sex = "2";
		}
		LogUtil.e("sex:" + sex);

		// 获取量身定制列表
		LSDZExec.getInstance().getLSDZList(mHandler, "0", 0, 0,
				NetworkAsyncCommonDefines.GET_LSDZ_LIST_S,
				NetworkAsyncCommonDefines.GET_LSDZ_LIST_F);
		mHandler.removeCallbacks(run);
		// 获取广告信息
		SchemeExec.getInstance().getGuangGao(mHandler,
				NetworkAsyncCommonDefines.GET_DINGZHI_GG_INFO_S,
				NetworkAsyncCommonDefines.GET_DINGZHI_GG_INFO_F);
		// 获取量身定制分类列表
		LSDZExec.getInstance().getLSDZFLList(mHandler, "0",
				NetworkAsyncCommonDefines.GET_LSDZ_FL_LIST_S,
				NetworkAsyncCommonDefines.GET_LSDZ_FL_LIST_F);

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取按钮成功
			case NetworkAsyncCommonDefines.GET_ANNIU_S:
				Bundle anniuData = msg.getData();
				if (anniuData != null) {
					ArrayList<LSDZFL> anniuList = anniuData
							.getParcelableArrayList("list");
					if (anniuList != null && anniuList.size() == 2) {
						// 获取两个全部按钮
						flList.add(4, anniuList.get(0));
						flList.add(anniuList.get(1));
						mLSDZFLAdapter.setData(flList);
						mLSDZFLAdapter.notifyDataSetChanged();
					}
				}
				break;
			// 获取按钮失败
			case NetworkAsyncCommonDefines.GET_ANNIU_F:

				break;
			// 获取量身定制分类成功
			case NetworkAsyncCommonDefines.GET_LSDZ_FL_LIST_S:
				Bundle fenleiData = msg.getData();
				if (fenleiData != null) {
					flList = fenleiData.getParcelableArrayList("list");
					if (flList != null && flList.size() > 0) {
						recyclerview1.setVisibility(View.VISIBLE);
						// 获取按钮
						PeiZhiExec.getInstance().getAnNiu(mHandler,
								NetworkAsyncCommonDefines.GET_ANNIU_S,
								NetworkAsyncCommonDefines.GET_ANNIU_F);

					} else {
						recyclerview1.setVisibility(View.GONE);
					}

				}
				break;
			// 获取广告信息成功
			case NetworkAsyncCommonDefines.GET_DINGZHI_GG_INFO_S:
				Bundle ggData = msg.getData();
				if (ggData != null) {
					ggList = ggData.getParcelableArrayList("list");

					if (ggList != null && ggList.size() > 0) {
						rl1.setVisibility(View.VISIBLE);
						lbt_ll.removeAllViews();
						// 增加滑动的点
						for (int i = 0; i < ggList.size(); i++) {
							ImageView iv1 = new ImageView(mContext);
							iv1.setBackgroundResource(R.drawable.point_selector);
							lbtdot = (int) mContext.getResources()
									.getDimension(R.dimen.lbt_dot);
							LayoutParams params = new LayoutParams(lbtdot,
									lbtdot);
							if (i != 0) {
								params.leftMargin = lbtdot;
							}
							iv1.setLayoutParams(params);
							iv1.setEnabled(false);
							lbt_ll.addView(iv1);
						}
						LogUtil.e("集合不为空:" + ggList.size());
						mHandler.postDelayed(run, 3000);
						mLbtVpAdapter.setData(ggList);
						mLbtVpAdapter.notifyDataSetChanged();
						lbt_ll.getChildAt(0).setEnabled(true);
					} else {
						LogUtil.e("集合为空");
						rl1.setVisibility(View.GONE);
					}
				} else {
				}

				break;
			// 轮播
			case NetworkAsyncCommonDefines.LUNBO:
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
				break;
			// 获取广告信失败
			case NetworkAsyncCommonDefines.GET_DINGZHI_GG_INFO_F:
				LogUtil.e("获取广告信失败");
				rl1.setVisibility(View.GONE);
				break;
			// 获取量身定制列表成功
			case NetworkAsyncCommonDefines.GET_LSDZ_LIST_S:
				Bundle myxData = msg.getData();
				if (myxData != null) {
					ArrayList<DZSPFL> list = myxData
							.getParcelableArrayList("list");
					if (mList != null) {
						mList.clear();
					}
					if (mList == null) {
						mList = new ArrayList<DZSPFL>();
					}
					if (list != null) {
						mList.addAll(list);
					}
					mLSDZAdapter.setData(mList);
					LogUtil.e("list:" + mList.size());
				}
				mLSDZAdapter.notifyDataSetChanged();
				break;
			// 获取量身定制列表失败
			case NetworkAsyncCommonDefines.GET_LSDZ_LIST_F:
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
	}

	private Runnable run = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.LUNBO);
		}
	};
}