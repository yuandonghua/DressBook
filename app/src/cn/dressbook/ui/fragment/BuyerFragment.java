package cn.dressbook.ui.fragment;


import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.LoginActivity;
import cn.dressbook.ui.MainActivity;
import cn.dressbook.ui.PublishRequirementActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.adapter.BuyerAdapter;
import cn.dressbook.ui.adapter.BuyerAdapter.OnClickWhat;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.BuyerListener;
import cn.dressbook.ui.model.CustomService;
import cn.dressbook.ui.model.Requirement;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;

/**
 * @description: 买手
 * @author:袁东华
 * @time:2015-9-25上午9:19:20
 */
@ContentView(R.layout.buyer_layout)
public class BuyerFragment extends BaseFragment {
	private String buyerRespTotalNum = "0", buyerRequTotalNum = "0";
	private int page1 = 1, page2 = 1, size = 10;
	private int lastVisibleItem;
	private LinearLayoutManager mLinearLayoutManager;
	/**
	 * 下拉刷新view
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	private Context mContext;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	private BuyerAdapter mBuyerAdapter;
	/**
	 * 全部已解决需求的个数
	 */
	private String mCount;
	private static BuyerFragment mBuyerFragment;
	/**
	 * 需求列表集合
	 */
	private ArrayList<Requirement> mRequirementList = new ArrayList<Requirement>();
	/**
	 * 帮我买集合
	 */
	private ArrayList<Requirement> mBWMList = new ArrayList<Requirement>();
	/**
	 * 我帮人买集合
	 */
	private ArrayList<Requirement> mWBRMList = new ArrayList<Requirement>();
	private boolean flag = false;

	public static BuyerFragment getInstance() {
		if (mBuyerFragment == null) {
			mBuyerFragment = new BuyerFragment();
		}
		return mBuyerFragment;
	}

	public BuyerFragment() {
		// TODO Auto-generated constructor stub
	}

	private View mBuyserView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		initView();

		MainActivity.setBuyerListener(new BuyerListener() {

			@Override
			public void onLazyLoad() {
				// TODO Auto-generated method stub
				if (swiperefreshlayout != null) {
					swiperefreshlayout.setRefreshing(false);
				}
				if (mBuyerAdapter != null) {

					mBuyerAdapter.setFlag(flag);
					if (flag) {
						mBuyerAdapter.setData(mBWMList);
						mBuyerAdapter.notifyDataSetChanged();
					} else {
						mBuyerAdapter.setData(mWBRMList);
						mBuyerAdapter.notifyDataSetChanged();
					}
				}
			}
		});
	}

	/**
	 * @description: 初始化数据
	 * @exception
	 */
	private void initData() {
		// TODO Auto-generated method stub
		if (ManagerUtils.getInstance().isLogin(mContext)) {
			page1 = 1;
			mBWMList = null;
			mBWMList = new ArrayList<Requirement>();
			page2 = 1;
			mWBRMList = null;
			mWBRMList = new ArrayList<Requirement>();
			// 获取帮我买列表
			RequirementExec.getInstance().getRequirementListFromServer(
					mHandler, ManagerUtils.getInstance().getUser_id(mContext),
					"0", 1, 20,
					NetworkAsyncCommonDefines.REFRESH_REQUIREMENT_S,
					NetworkAsyncCommonDefines.REFRESH_REQUIREMENT_F);
			// 获取我帮人买列表
			RequirementExec.getInstance().getOtherRequirementListFromServer(
					mHandler, ManagerUtils.getInstance().getUser_id(mContext),
					1, 20, NetworkAsyncCommonDefines.GET_OTHER_REQUIREMENT_S,
					NetworkAsyncCommonDefines.GET_OTHER_REQUIREMENT_F);
		} else {
			mBWMList.clear();
			mWBRMList.clear();
			mBWMList.add(0, null);
			mWBRMList.add(0, null);
			mBuyerAdapter.setFlag(flag);
			mBuyerAdapter.setData(mBWMList);
			mBuyerAdapter.notifyDataSetChanged();
		}
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		// TODO Auto-generated method stub
		mContext = getActivity();
		mBuyerAdapter = new BuyerAdapter(mContext, mHandler);
		mBuyerAdapter.setOnClickWhat(new OnClickWhat() {
			@Override
			public void clickWhat(boolean isImage) {
				BuyerFragment.this.isImage = isImage;
			}
		});
		recyclerview.setHasFixedSize(true);
		mLinearLayoutManager = new LinearLayoutManager(mContext);
		mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerview.setLayoutManager(mLinearLayoutManager);
		recyclerview.setAdapter(mBuyerAdapter);
		// 分割线
		recyclerview
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						mContext)
						.color(getResources().getColor(R.color.black15))
						.size(getResources().getDimensionPixelSize(
								R.dimen.divider4))
						.margin(getResources().getDimensionPixelSize(
								R.dimen.leftmargin_no),
								getResources().getDimensionPixelSize(
										R.dimen.rightmargin_no)).build());

		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				if (ManagerUtils.getInstance().isLogin(mContext)) {
					// 下拉刷新
					if (flag) {
						page1 = 1;
						mBWMList = null;
						mBWMList = new ArrayList<Requirement>();
						// 获取需求列表
						RequirementExec
								.getInstance()
								.getRequirementListFromServer(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										"0",
										page1,
										size,
										NetworkAsyncCommonDefines.REFRESH_REQUIREMENT_S,
										NetworkAsyncCommonDefines.REFRESH_REQUIREMENT_F);
					} else {
						page2 = 1;
						mWBRMList = null;
						mWBRMList = new ArrayList<Requirement>();
						// 获取其他需求列表
						RequirementExec
								.getInstance()
								.getOtherRequirementListFromServer(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										page2,
										size,
										NetworkAsyncCommonDefines.GET_OTHER_REQUIREMENT_S,
										NetworkAsyncCommonDefines.GET_OTHER_REQUIREMENT_F);
					}
				} else {
					swiperefreshlayout.setRefreshing(false);
					Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT).show();
				}
			}
		});
		recyclerview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				// TODO Auto-generated method stub
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE
						&& lastVisibleItem + 1 == mBuyerAdapter.getItemCount()) {
					swiperefreshlayout.setRefreshing(true);
					// 上拉加载
					if (flag) {
						page1++;
						// 获取需求列表
						RequirementExec
								.getInstance()
								.getRequirementListFromServer(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										"0",
										page1,
										size,
										NetworkAsyncCommonDefines.REFRESH_REQUIREMENT_S,
										NetworkAsyncCommonDefines.REFRESH_REQUIREMENT_F);
					} else {
						page2++;
						// 获取其他需求列表
						RequirementExec
								.getInstance()
								.getOtherRequirementListFromServer(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										page2,
										size,
										NetworkAsyncCommonDefines.GET_OTHER_REQUIREMENT_S,
										NetworkAsyncCommonDefines.GET_OTHER_REQUIREMENT_F);
					}
				}
				switch (newState) {
				// 滑动停止
				case RecyclerView.SCROLL_STATE_IDLE:
					break;
				// 拖拽
				case RecyclerView.SCROLL_STATE_DRAGGING:
					break;
				// 滑动
				case RecyclerView.SCROLL_STATE_SETTLING:
					break;
				default:
					break;
				}

			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mLinearLayoutManager
						.findLastVisibleItemPosition();
			}
		});

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/**
		 * 点击图片，则不需要更新
		 */
		Log.i("xx", isImage + "");
		if (isImage) {
			isImage = false;
		} else {

			initData();
		}
	}

	private boolean isImage = false;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 点击够了成功
			case NetworkAsyncCommonDefines.CLICK_GL_S:
				Bundle glData = msg.getData();
				if (glData != null) {
					String redesc = glData.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
				}
				initData();
				break;
			// 点击够了失败
			case NetworkAsyncCommonDefines.CLICK_GL_F:
				// lazyLoad();
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				initData();
				break;
			// 找买手成功
			case NetworkAsyncCommonDefines.GET_BUYER_S:
				break;
			// 找买手失败
			case NetworkAsyncCommonDefines.GET_BUYER_F:
				Toast.makeText(mContext, "没有找到买手", Toast.LENGTH_SHORT).show();
				break;
			// 此需求暂无推荐结果
			case NetworkAsyncCommonDefines.RECOMEND_NULL:
				Toast.makeText(mContext, "此需求暂无推荐结果", Toast.LENGTH_SHORT)
						.show();
				break;
			// 不允许终止需求
			case NetworkAsyncCommonDefines.DONOT_FINISH_REQUIREMENT_F:
				Toast.makeText(mContext, "此需求暂时无法操作", Toast.LENGTH_SHORT)
						.show();
				break;
			// 终止需求成功
			case NetworkAsyncCommonDefines.FINISH_REQUIREMENT_S:
				lazyLoad();
				Toast.makeText(mContext, "此需已被终止", Toast.LENGTH_SHORT).show();
				break;
			// 终止需求失败
			case NetworkAsyncCommonDefines.FINISH_REQUIREMENT_F:
				lazyLoad();
				Toast.makeText(mContext, "此需终止失败", Toast.LENGTH_SHORT).show();
				break;
			// 获取所有已完成需求个数成功
			case NetworkAsyncCommonDefines.GET_ALLFINISH_REQUIREMENT_S:
				Bundle countBundle = msg.getData();
				if (countBundle != null) {
					mCount = countBundle.getString("count");
				}
				break;
			// 获取所有已完成需求个数失败
			case NetworkAsyncCommonDefines.GET_ALLFINISH_REQUIREMENT_F:

				break;
			// 点击发布需求
			case NetworkAsyncCommonDefines.CLICK_PUBLISH_REQUIREMENT:
				if (ManagerUtils.getInstance().isLogin(mContext)) {

					Intent publishRequirementActivity = new Intent(mContext,
							PublishRequirementActivity.class);
					mContext.startActivity(publishRequirementActivity);
				} else {
					Intent loginActivity = new Intent(mContext,
							LoginActivity.class);
					mContext.startActivity(loginActivity);
				}
				break;
			// 获取需求列表成功
			case NetworkAsyncCommonDefines.GET_REQUIREMENT_S:
				Bundle requirementListBun = msg.getData();
				if (requirementListBun != null) {
					mRequirementList = null;
					mRequirementList = new ArrayList<Requirement>();
					mBWMList = null;
					mBWMList = new ArrayList<Requirement>();
					mWBRMList = null;
					mWBRMList = new ArrayList<Requirement>();
					ArrayList<Requirement> list = requirementListBun
							.getParcelableArrayList("requirementList");
					mRequirementList.addAll(list);
					disposalData();
					swiperefreshlayout.setRefreshing(false);
				}

				break;
			// 获取需求列表失败
			case NetworkAsyncCommonDefines.GET_REQUIREMENT_F:
				break;
			// 点击帮我买
			case NetworkAsyncCommonDefines.CLICK_UNDERWAY:
				flag = true;
				mBuyerAdapter.setFlag(flag);
				if (flag) {
					LogUtil.e("帮我买:"+mBWMList.size());
					mBuyerAdapter.setData(mBWMList);
					mBuyerAdapter.notifyDataSetChanged();
				} else {
					LogUtil.e("我帮人买:"+mWBRMList.size());
					mBuyerAdapter.setData(mWBRMList);
					mBuyerAdapter.notifyDataSetChanged();
				}
				break;
			// 点击我帮人买
			case NetworkAsyncCommonDefines.CLICK_FINISH:
				flag = false;
				mBuyerAdapter.setFlag(flag);
				if (flag) {
					LogUtil.e("帮我买:"+mBWMList.size());
					mBuyerAdapter.setData(mBWMList);
					mBuyerAdapter.notifyDataSetChanged();
				} else {
					LogUtil.e("我帮人买:"+mWBRMList.size());
					mBuyerAdapter.setData(mWBRMList);
					mBuyerAdapter.notifyDataSetChanged();
				}
				break;
			// 刷新需求列表成功
			case NetworkAsyncCommonDefines.REFRESH_REQUIREMENT_S:
				Bundle refreshBun = msg.getData();
				if (refreshBun != null) {
					buyerRespTotalNum = refreshBun
							.getString("buyerRespTotalNum");
					ArrayList<Requirement> list = refreshBun
							.getParcelableArrayList("requirementList");
					if (list != null && list.size() > 0) {
						mBWMList.addAll(list);
					}
					if (mBWMList.size() > 0) {
						if (mBWMList.get(0) != null) {
							mBWMList.add(0, null);
						}
					} else {
						mBWMList.add(0, null);
					}
				}
				mBuyerAdapter.setFlag(flag);
				mBuyerAdapter.setNum1(buyerRespTotalNum);
				if (flag) {
					mBuyerAdapter.setData(mBWMList);
					mBuyerAdapter.notifyDataSetChanged();
				}
				swiperefreshlayout.setRefreshing(false);
				break;
			// 刷新需求列表失败
			case NetworkAsyncCommonDefines.REFRESH_REQUIREMENT_F:
				swiperefreshlayout.setRefreshing(false);
				if (mBWMList.size() > 0) {
					if (mBWMList.get(0) != null) {
						mBWMList.add(0, null);
					}
				} else {
					mBWMList.add(0, null);
				}
				if (flag) {
					mBuyerAdapter.setData(mBWMList);
					mBuyerAdapter.notifyDataSetChanged();
				}
				break;
			// 获取其他需求成功
			case NetworkAsyncCommonDefines.GET_OTHER_REQUIREMENT_S:
				Bundle otherReqBun = msg.getData();
				if (otherReqBun != null) {
					buyerRequTotalNum = otherReqBun
							.getString("buyerRequTotalNum");
					ArrayList<Requirement> list = otherReqBun
							.getParcelableArrayList("list");
					if (list != null && list.size() > 0) {
						mWBRMList.addAll(list);
					}
					if (mWBRMList.size() > 0) {
						if (mWBRMList.get(0) != null) {
							mWBRMList.add(0, null);
						}

					} else {
						mWBRMList.add(0, null);
					}
				}
				mBuyerAdapter.setFlag(flag);
				mBuyerAdapter.setNum2(buyerRequTotalNum);
				if (!flag) {
					mBuyerAdapter.setData(mWBRMList);
					mBuyerAdapter.notifyDataSetChanged();
				}
				swiperefreshlayout.setRefreshing(false);
				break;
			// 获取其他需求失败
			case NetworkAsyncCommonDefines.GET_OTHER_REQUIREMENT_F:
				swiperefreshlayout.setRefreshing(false);

				if (mWBRMList.size() > 0) {
					if (mWBRMList.get(0) != null) {
						mWBRMList.add(0, null);
					}

				} else {
					mWBRMList.add(0, null);
				}
				if (!flag) {

					mBuyerAdapter.setData(mWBRMList);
					mBuyerAdapter.notifyDataSetChanged();
				}
				break;
			default:
				break;
			}
		}

	};

	

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		if (mBuyserView != null) {
		}

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (isStop) {
			isStop = false;
			lazyLoad();
		}
	}

	private boolean isStop;

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isStop = true;
	}

	/**
	 * @description:整理数据
	 */
	private void disposalData() {
		if (mRequirementList != null && mRequirementList.size() > 0) {
			for (Requirement req : mRequirementList) {
				if ("1".equals(req.getState()) || "2".equals(req.getState())
						|| "3".equals(req.getState())
						|| "6".equals(req.getState())
						|| "7".equals(req.getState())
						|| "8".equals(req.getState())) {
					mBWMList.add(req);
				} else {
					mWBRMList.add(req);
				}
			}
			mBWMList.add(0, null);
			mWBRMList.add(0, null);
		}
		mBuyerAdapter.setData(null);
		mBuyerAdapter.notifyDataSetChanged();
	}

}
