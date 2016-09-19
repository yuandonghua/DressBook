package cn.dressbook.ui.fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONException;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.ui.FindActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.adapter.FindResultAdapter;
import cn.dressbook.ui.bean.FindBwBean;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.fragment.counselor.BaseCounselorFragment;
import cn.dressbook.ui.json.AdviserJson;
import cn.dressbook.ui.json.AttireSchemeJson;
import cn.dressbook.ui.json.FindBwJson;
import cn.dressbook.ui.model.Adviser;
import cn.dressbook.ui.model.AlbcBean;
import cn.dressbook.ui.model.AlbcBean;
import cn.dressbook.ui.utils.ToastUtils;

/**
 * 搜索结果的fragment
 * 
 * @author 熊天富
 * 
 */
public class SearchFragment extends BaseCounselorFragment {
	private int model;
	private String keyWord;
	// 判断是否正在加载更多
	private boolean isRefreshed = false, isLoadingMore = false;
	private List<Adviser> listAdviser;
	private List<FindBwBean> listFindBw;
	private List<AlbcBean> listAlbc;

	public SearchFragment() {
	}

	public SearchFragment(int model, String keyWord) {
		this.model = model;
		this.keyWord = keyWord;
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (isRefreshed) {
				findResultSrl.setRefreshing(false);
				isRefreshed = false;
			}
			switch (msg.what) {
			// 第一次请求数据
			case NetworkAsyncCommonDefines.GET_FIND_F:
				Object findBean1 =  msg.obj;
				if (findBean1 != null) {
					switch (model) {
					case FindActivity.TYPE_BLOG:
						ArrayList<FindBwBean> findbw=(ArrayList<FindBwBean>) msg.obj;
						if (findbw!=null&&findbw.size()>0) {
							adapter.setData(findbw);
							adapter.notifyDataSetChanged();
						} else {
							frameLayout.getFailBtn().setText("亲，你搜索的数据保存在火星！请重新搜索");
						}
						break;
					case FindActivity.TYPE_BRAND:
						ArrayList<AlbcBean> albclist=(ArrayList<AlbcBean>) msg.obj;
						if (albclist!=null&&albclist.size()>0) {
							adapter.setData(albclist);
							adapter.notifyDataSetChanged();
						} else {
							frameLayout.getFailBtn().setText("亲，你搜索的数据保存在火星！请重新搜索");
						}
						break;
					case FindActivity.TYPE_COUNSELOR:
						ArrayList<Adviser> adviser=(ArrayList<Adviser>) msg.obj;
						if (adviser!=null&&adviser.size()>0) {
							adapter.setData(adviser);
							adapter.notifyDataSetChanged();
						} else {
							frameLayout.getFailBtn().setText("亲，你搜索的数据保存在火星！请重新搜索");
						}
						break;
					case FindActivity.TYPE_GOODS:
						ArrayList<AlbcBean> attiregoods=(ArrayList<AlbcBean>) msg.obj;
						if (attiregoods!=null&&attiregoods.size()>0) {
							adapter.setData(attiregoods);
							adapter.notifyDataSetChanged();
						} else {
							frameLayout.getFailBtn().setText("亲，你搜索的数据保存在火星！请重新搜索");
						}
						break;

					
					}
					/*if (findBean1.info != null && findBean1.info.size() > 0) {
						listInfo.addAll(findBean1.info);
						adapter.notifyDataSetChanged();
					} else {
						frameLayout.getFailBtn().setText("亲，你搜索的数据保存在火星！请重新搜索");
						frameLayout.getFailImg().setVisibility(View.GONE);
					}*/

				}else{
					frameLayout.getFailBtn().setVisibility(View.VISIBLE);
					frameLayout.getFailBtn().setText("亲，你搜索的数据保存在火星！请重新搜索");
				}
				break;
			// 加载更多数据
			case NetworkAsyncCommonDefines.GET_FIND_S:
				isLoadingMore = false;
				loadmoreRl.setVisibility(View.GONE);
				Object findBean2 =  msg.obj;
				if (findBean2 != null) {
					switch (model) {
					case FindActivity.TYPE_BLOG:
						ArrayList<FindBwBean> findbw=(ArrayList<FindBwBean>) msg.obj;
						if (findbw!=null&&findbw.size()>0) {
							adapter.setData(findbw);
							adapter.notifyDataSetChanged();
						} else {
							ToastUtils.getInstance().showToast(mContext, "没有更多数据了",
									300);
						}
						break;
					case FindActivity.TYPE_BRAND:
						ArrayList<AlbcBean> attireSchemes=(ArrayList<AlbcBean>) msg.obj;
						if (attireSchemes!=null&&attireSchemes.size()>0) {
							adapter.setData(attireSchemes);
							adapter.notifyDataSetChanged();
						} else {
							ToastUtils.getInstance().showToast(mContext, "没有更多数据了",
									300);
						}
						break;
					case FindActivity.TYPE_COUNSELOR:
						ArrayList<Adviser> adviser=(ArrayList<Adviser>) msg.obj;
						if (adviser!=null&&adviser.size()>0) {
							adapter.setData(adviser);
							adapter.notifyDataSetChanged();
						} else {
							ToastUtils.getInstance().showToast(mContext, "没有更多数据了",
									300);
						}
						break;
					case FindActivity.TYPE_GOODS:
						ArrayList<AlbcBean> attiregoods=(ArrayList<AlbcBean>) msg.obj;
						if (attiregoods!=null&&attiregoods.size()>0) {
							adapter.setData(attiregoods);
							adapter.notifyDataSetChanged();
						} else {
							ToastUtils.getInstance().showToast(mContext, "没有更多数据了",
									300);
						}
						break;

					/*if (findBean2.info != null && findBean2.info.size() > 0) {
						listInfo.addAll(findBean2.info);
						adapter.notifyDataSetChanged();
					} else {
						ToastUtils.getInstance().showToast(mContext, "没有更多数据了",
								300);
					}*/

				}}
				break;

			}
		};
	};

	private FindResultAdapter adapter;

	private RecyclerView findResultRv;
	private SwipeRefreshLayout findResultSrl;
	private LinearLayoutManager llm;
	private int pageindex = 0;
	private TextView loadmoreTv;
	private ProgressBar loadmorePb;
	private RelativeLayout loadmoreRl;

	/**
	 * 获取成功的view
	 */
	@Override
	protected View getSuccessView() {
		View view = View.inflate(mContext, R.layout.find_result, null);
		findResultRv = (RecyclerView) view.findViewById(R.id.find_result_rv);
		findResultSrl = (SwipeRefreshLayout) view
				.findViewById(R.id.find_result_srl);
		loadmoreTv = (TextView) view.findViewById(R.id.tvViewLoadingMore);
		loadmorePb = (ProgressBar) view.findViewById(R.id.pbViewLoadingMore);
		loadmoreRl = (RelativeLayout) view.findViewById(R.id.loadmore_rl);
		setSrl(findResultSrl);
		listAdviser=new ArrayList<Adviser>();
		listAlbc=new ArrayList<AlbcBean>();
		listFindBw=new ArrayList<FindBwBean>();
		switch (model) {
		case FindActivity.TYPE_BLOG:
			adapter = new FindResultAdapter(model, mContext,getActivity());
			break;
		case FindActivity.TYPE_BRAND:
			adapter = new FindResultAdapter(model, mContext,getActivity());
			break;
		case FindActivity.TYPE_COUNSELOR:
			adapter = new FindResultAdapter(model, mContext,getActivity());
			break;
		case FindActivity.TYPE_GOODS:
			adapter = new FindResultAdapter(model, mContext,getActivity());
			break;
		}
		findResultRv.setAdapter(adapter);
		llm = new LinearLayoutManager(mContext);
		findResultRv.setLayoutManager(llm);
		setPullupRefresh();
		return view;
	}

	/**
	 * 实现上拉加载更多
	 */
	@SuppressWarnings("deprecation")
	private void setPullupRefresh() {
		findResultRv.setOnScrollListener(new OnScrollListener() {
			private int lastVisibleItem;

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (lastVisibleItem + 1 == adapter.getItemCount()) {
					if (isLoadingMore) {
						return;
					}
					loadmoreRl.setVisibility(View.VISIBLE);
					loadmoreTv.setText("努力加载中");
					isLoadingMore = true;
					new Thread() {
						public void run() {
							Object data = getData();
							Message msg = new Message();
							msg.obj = data;
							msg.what = NetworkAsyncCommonDefines.GET_FIND_S;
							handler.sendMessage(msg);

						};
					}.start();
				}
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = llm.findLastVisibleItemPosition();
			}

		});

	}
	private boolean isLoading=true;
	private String findData="";
	private boolean isGetData=false;
	/**
	 * 获取数据
	 */
	private Object getData() {
		pageindex++;
		RequestParams params = new RequestParams(
				PathCommonDefines.GOLABLE_SEARCH);
		params.addBodyParameter("model", model+"");
		params.addBodyParameter("keyword", keyWord);
		params.addBodyParameter("page_index", pageindex+"");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				findData=result;
				isLoading=false;
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				isLoading=false;
			}

			@Override
			public void onCancelled(CancelledException cex) {
				isLoading=false;
			}

			@Override
			public void onFinished() {
				isLoading=false;

			}
		});
		/**
		 * 控制使两个线程同步
		 */
		while (isLoading) {
			
		}
		isLoading=true;
		if (findData!=null) {
			isGetData=true;
		}
		switch (model) {
		case FindActivity.TYPE_BRAND:
			ArrayList<AlbcBean> AlbcList1;
			try {
				AlbcList1 = AttireSchemeJson
						.getInstance().getAlbcFind(findData);
				return AlbcList1;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case FindActivity.TYPE_BLOG:
			ArrayList<FindBwBean> findBwList;
			try {
				findBwList = FindBwJson.getInstace()
						.analyzeFindBwList(findData);
				return findBwList;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case FindActivity.TYPE_COUNSELOR:
			ArrayList<Adviser> advisersList;
			try {
				advisersList = AdviserJson.getInstance()
						.analyzeAdviserList(findData);
				return advisersList;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case FindActivity.TYPE_GOODS:
			ArrayList<AlbcBean> albcList2;
			try {
				albcList2 = AttireSchemeJson
						.getInstance().getAlbcFind(findData);
				return albcList2;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}
		return null;
	}

	/**
	 * 获取数据
	 */
	@Override
	protected Object getServiceData() {
		try {
			Object data = getData();
			Message msg = new Message();
			msg.obj = data;
			msg.what = NetworkAsyncCommonDefines.GET_FIND_F;
			handler.sendMessage(msg);
			if (data != null) {
				return data;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isGetData) {
			isGetData=false;
			return "getData";
		}
		return null;

	}

	/**
	 * 设置SwipeRefreshLayout
	 * 
	 * @param srl
	 */
	private void setSrl(SwipeRefreshLayout srl) {
		srl.setColorSchemeResources(R.color.red1);
		srl.setSize(SwipeRefreshLayout.DEFAULT);
		srl.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				isRefreshed = true;
				getServiceData();
			}
		});

	}

}
