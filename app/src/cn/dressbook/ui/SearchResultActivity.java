package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.SearchResultAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.dialog.ProgressDialog;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.AlbcBean;
import cn.dressbook.ui.model.AlbcBean;
import cn.dressbook.ui.net.RequirementExec;

/**
 * @description: 搜索结果
 * @author:袁东华
 * @time:2015-10-16上午10:41:00
 */
@ContentView(R.layout.searchresult)
public class SearchResultActivity extends BaseActivity {
	private Context mContext = SearchResultActivity.this;
	private SearchResultAdapter mSearchResultAdapter;
	private String reqdesc, addtime, username, occation, category, price,
			useravatar, id;
	private boolean flag;
	private InputMethodManager inputMethodManager;
	public ProgressDialog pbDialog;
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_l;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 推荐语
	 */
	@ViewInject(R.id.tj_et)
	private EditText tj_et;
	/**
	 * 推荐按钮
	 */
	@ViewInject(R.id.ok_tv)
	private TextView ok_tv;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	/**
	 * 下拉刷新view
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	private ArrayList<String> list2 = new ArrayList<String>();

	private String sex, pl = "", occation2, jw = "", categoryid, cid,
			attr_tags, keyword = "";
	private int page = 1, size = 50;
	private int lastVisibleItem;
	private LinearLayoutManager mLinearLayoutManager;
	private ArrayList<AlbcBean> mList = new ArrayList<AlbcBean>();

	@SuppressWarnings("deprecation")
	protected void initView() {
		// TODO Auto-generated method stub
		pbDialog = new ProgressDialog(this);
		title_tv.setText("推荐服装");
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mSearchResultAdapter = new SearchResultAdapter(mContext, mHandler);
		recyclerview.setHasFixedSize(true);
		mLinearLayoutManager = new LinearLayoutManager(mContext);
		mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerview.setLayoutManager(mLinearLayoutManager);
		recyclerview.setAdapter(mSearchResultAdapter);
		// 点击条目
		mSearchResultAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				if (mList.get(position) == null) {
					return;
				}
				if (mList.get(position).getIsView() == 0) {
					mList.get(position).setIsView(1);
					String attireid = mList.get(position).getAuction_id();
					if (!list2.contains(attireid)) {

						list2.add(attireid);
					}
				} else {
					mList.get(position).setIsView(0);

					String attireid = mList.get(position).getAuction_id();
					if (list2.contains(attireid)) {
						list2.remove(attireid);
					}
				}
				mSearchResultAdapter.notifyItemChanged(position);
			}
		});
		// 上拉加载更多
		recyclerview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				// TODO Auto-generated method stub
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE
						&& lastVisibleItem + 1 == mSearchResultAdapter
								.getItemCount()) {
					swiperefreshlayout.setRefreshing(true);
					// 上拉加载
					page++;
					RequirementExec.getInstance().searchClothing(mHandler, sex,
							jw, xl, attr_tags, keyword, page, size,
							NetworkAsyncCommonDefines.GET_SEARCHCLOTHING_S,
							NetworkAsyncCommonDefines.GET_SEARCHCLOTHING_F);
				}

			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mLinearLayoutManager
						.findLastVisibleItemPosition();
			}
		});
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				page = 1;
				RequirementExec.getInstance().searchClothing(mHandler, sex, jw,
						xl, attr_tags, keyword, page, size,
						NetworkAsyncCommonDefines.GET_SEARCHCLOTHING_S,
						NetworkAsyncCommonDefines.GET_SEARCHCLOTHING_F);
			}
		});
	}

	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			sex = intent.getStringExtra("sex");
			occation2 = intent.getStringExtra("occation2");
			categoryid = intent.getStringExtra("categoryid");
			cid = intent.getStringExtra("cid");
			attr_tags = intent.getStringExtra("attr_tags");
			id = intent.getStringExtra("id");
			reqdesc = intent.getStringExtra("reqdesc");
			addtime = intent.getStringExtra("addtime");
			username = intent.getStringExtra("username");
			occation = intent.getStringExtra("occation");
			category = intent.getStringExtra("category");
			price = intent.getStringExtra("price");
			useravatar = intent.getStringExtra("useravatar");
			jw = intent.getStringExtra("jw");
			pl = intent.getStringExtra("pl");
			xl = intent.getStringExtra("xl");
			keyword = intent.getStringExtra("keyword");
			if("".equals(xl)){
				xl=pl;
			}
			LogUtil.e("xl:"+xl);
			mSearchResultAdapter.setRequirement(reqdesc, addtime, username,
					occation, category, price, useravatar);
		}
		/**
		 * 获取搜索服装数据列表
		 */
		RequirementExec.getInstance().searchClothing(mHandler, sex, jw, xl,
				attr_tags, keyword, page, size,
				NetworkAsyncCommonDefines.GET_SEARCHCLOTHING_S,
				NetworkAsyncCommonDefines.GET_SEARCHCLOTHING_F);
	}

	@Event({ R.id.back_rl, R.id.tj_et, R.id.ok_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击推荐按钮
		case R.id.ok_tv:
			if (isFinish()) {
				String yjh = tj_et.getText().toString();
				if (yjh == null || "".equals(yjh)) {
					Toast.makeText(mContext, "请写一句推荐语", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				yjh = yjh.trim().replaceAll("\\s", "");
				yjh = yjh.replaceAll("\t", "");
				yjh = yjh.replaceAll("\r", "");
				yjh = yjh.replaceAll("\n", "");
				String mat_id = "";
				if (list2 != null && list2.size() > 0) {
					for (int i = 0; i < list2.size(); i++) {
						if (i == list2.size() - 1) {
							mat_id += list2.get(i);
						} else {
							mat_id += list2.get(i) + ",";
						}
					}
				} else {
					Toast.makeText(mContext, "请选择服装", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				pbDialog.show();
				// 推荐服装
				RequirementExec.getInstance().recomendClothing(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext), id,
						mat_id, yjh,
						NetworkAsyncCommonDefines.RECOMMEND_CLOTHING_S,
						NetworkAsyncCommonDefines.RECOMMEND_CLOTHING_F);
			}

			break;
		default:
			break;
		}
	}

	private int mVisibleHeight;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 搜索服装方案成功
			case NetworkAsyncCommonDefines.GET_SEARCHCLOTHING_S:
				Bundle searchData = msg.getData();
				if (searchData != null) {
					ArrayList<AlbcBean> list = searchData
							.getParcelableArrayList("list");
					if (list != null && list.size() > 0) {
						mList.addAll(list);
					} else {
						if (mList == null || mList.size() < 1) {
							Toast.makeText(mContext, "没有找到符合的服装！",
									Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(mContext, "亲，没有更多服装了！",
									Toast.LENGTH_SHORT).show();
						}

					}
					if (mList.size() > 0) {
						if (mList.get(0) != null) {
							mList.add(0, null);
						}
					} else {
						mList.add(0, null);
					}
					mSearchResultAdapter.setData(mList);
					mSearchResultAdapter.notifyDataSetChanged();
				}
				pbDialog.dismiss();
				swiperefreshlayout.setRefreshing(false);
				break;
			// 搜索服装方案失败
			case NetworkAsyncCommonDefines.GET_SEARCHCLOTHING_F:
				Toast.makeText(mContext, "搜索衣服失败", Toast.LENGTH_SHORT).show();
				swiperefreshlayout.setRefreshing(false);
				pbDialog.dismiss();
				finish();
				break;
			// 推荐成功
			case NetworkAsyncCommonDefines.RECOMMEND_CLOTHING_S:
				Toast.makeText(mContext, "操作成功", Toast.LENGTH_SHORT).show();
				finish();
				pbDialog.dismiss();
				break;
			// 推荐失败
			case NetworkAsyncCommonDefines.RECOMMEND_CLOTHING_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
				break;
			default:
				break;
			}
		};
	};
	private String xl="";

	public boolean isFinish() {
		return !pbDialog.isShowing();
	}
}
