package cn.dressbook.ui.fragment.counselor;

import java.util.ArrayList;
import org.json.JSONException;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.LoginActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.UserHomepageActivity;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.AdviserJson;
import cn.dressbook.ui.model.Adviser;
import cn.dressbook.ui.view.CircleImageView2;

/**
 * 
 * @description: 顾问师列表的类
 * @author:熊天富
 * @modifier:熊天富
 * @remarks:
 * @2015-6-9 上午11:28:28
 * 
 */

public class CounselorFragment extends BaseCounselorFragment {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	ArrayList<Adviser> AdviserList;
	private boolean isLoading=true;
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NetworkAsyncCommonDefines.GET_COUNSELOR_INFO_F:
				AdviserList = (ArrayList<Adviser>) msg.obj;
				adapter.notifyDataSetChanged();
				break;

			case NetworkAsyncCommonDefines.GET_COUNSELOR_INFO_S:
				counselorSrl.setRefreshing(false);
				AdviserList = (ArrayList<Adviser>) msg.obj;
				adapter.notifyDataSetChanged();
				break;
			}

		};
	};
	private MyAdapter adapter;
	private View view;
	private SwipeRefreshLayout counselorSrl;

	/**
	 * 继承基类 成功后返回的view
	 */
	@Override
	protected View getSuccessView() {

		view = View.inflate(mContext, R.layout.counselor_recyclerview, null);

		RecyclerView counselorRv = (RecyclerView) view
				.findViewById(R.id.counselor_rv);
		LayoutManager layoutManager = new LinearLayoutManager(mContext);
		counselorRv.setLayoutManager(layoutManager);
		adapter = new MyAdapter();
		counselorRv.setAdapter(adapter);
		// 给counselorRv设置点击事件
		initSwipeRefreshLayout();
		return view;
	}

	private void initSwipeRefreshLayout() {
		counselorSrl = (SwipeRefreshLayout) view
				.findViewById(R.id.counselorSrl);
		counselorSrl.setSize(SwipeRefreshLayout.DEFAULT);
		counselorSrl.setColorSchemeResources(R.color.red1);
		counselorSrl.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 从服务端获取更多顾问师的资料
				getmore();

			}
		});

	}

	protected void getmore() {
		new Thread() {
			public void run() {

				ArrayList<Adviser> counselorListBean = getData();
				Message msg = new Message();
				msg.obj = counselorListBean;
				msg.what = NetworkAsyncCommonDefines.GET_COUNSELOR_INFO_S;
				handler.sendMessage(msg);
			};
		}.start();
	}


	private String counselorListData = "";
	private ArrayList<Adviser> getData() {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_COUNSELOR_LIST);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				counselorListData = result;
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
		 * 使两个线程同步
		 */
		while (isLoading) {
			
		}
		isLoading=true;
		ArrayList<Adviser> analyzeAdviserList;
		try {
			analyzeAdviserList = AdviserJson.getInstance().analyzeAdviserList(
					counselorListData);
			return analyzeAdviserList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 从服务器上获取数据
	 */
	@Override
	protected Object getServiceData() {

		AdviserList = getData();
		if (AdviserList != null) {
			Message msg = new Message();
			msg.obj = AdviserList;
			msg.what = NetworkAsyncCommonDefines.GET_COUNSELOR_INFO_F;
			handler.sendMessage(msg);
		}
		return AdviserList;
	}

	/**
	 * recyclerView的adapter
	 */
	class MyAdapter extends Adapter<MyViewHolder> implements OnClickListener {

		@Override
		public int getItemCount() {
			return AdviserList == null ? 0 : AdviserList.size();
		}

		@Override
		public void onBindViewHolder(MyViewHolder holder, int position) {
			Adviser adviser = AdviserList.get(position);
			holder.nameTv.setText(adviser.getName());
			holder.fansTv.setText("粉丝" + adviser.getGuanzhu());
			holder.indentityTv.setText(adviser.getTitle());
			holder.ideaTv.setText("" + adviser.getPostsTitle());
			// 绑定图片
			x.image().bind(holder.counselorHeadIv, adviser.getAutographPath(),
					mImageOptions, new CommonCallback<Drawable>() {

						@Override
						public void onSuccess(Drawable arg0) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onFinished() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onError(Throwable arg0, boolean arg1) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onCancelled(CancelledException arg0) {
							// TODO Auto-generated method stub
						}
					});

			holder.itemView.setTag(adviser);
		}

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.item_counselor, parent, false);
			MyViewHolder holder = new MyViewHolder(view);
			view.setOnClickListener(this);
			return holder;
		}

		@Override
		public void onClick(View v) {
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				// 跳转到顾问师主页
				Intent intent = new Intent(mContext, UserHomepageActivity.class);
				Adviser info1 = (Adviser) v.getTag();
				intent.putExtra("USER_ID", info1.getUserId() + "");
				mContext.startActivity(intent);
			} else {
				// 跳转到登录页
				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				mContext.startActivity(bindPhone);
			}

		}
	}

	class MyViewHolder extends ViewHolder {
		View view;
		private CircleImageView2 counselorHeadIv;
		private TextView nameTv;
		private TextView indentityTv;
		private TextView fansTv;
		private TextView ideaTv;

		public MyViewHolder(View view) {
			super(view);
			this.view = view;
			counselorHeadIv = (CircleImageView2) view
					.findViewById(R.id.counselor_head_iv);
			nameTv = (TextView) view.findViewById(R.id.name_tv);
			indentityTv = (TextView) view.findViewById(R.id.identity_tv);
			fansTv = (TextView) view.findViewById(R.id.fans_tv);
			ideaTv = (TextView) view.findViewById(R.id.idea_tv);
		}

	}

}
