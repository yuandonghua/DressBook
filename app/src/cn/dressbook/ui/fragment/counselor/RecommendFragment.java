package cn.dressbook.ui.fragment.counselor;

import java.util.List;

import org.json.JSONException;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.AdviserJson;
import cn.dressbook.ui.model.Adviser;
import cn.dressbook.ui.utils.ScreenUtil;
import cn.dressbook.ui.view.CircleImageView2;

/**
 * 熊天富 今日推荐的的fragment
 * 
 * @author 15712
 * 
 */
public class RecommendFragment extends BaseCounselorFragment {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();

		};
	};
	private RecyclerView recommendRv;
	private View view;
	private List<String> showPicUrl;
	private MyAdapter adapter;
	int width;
	private Adviser adviser;

	/**
	 * 获取数据成功时的view
	 */
	@Override
	protected View getSuccessView() {
		width = ScreenUtil.getScreenWidthPix(mContext);
		view = View.inflate(mContext, R.layout.recommend_recyclerview, null);
		recommendRv = (RecyclerView) view.findViewById(R.id.recommend_lv);
		LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recommendRv.setLayoutManager(layoutManager);
		recommendRv.setHasFixedSize(true);
		adapter = new MyAdapter();
		recommendRv.setAdapter(adapter);
		return view;
	}

	private String counselorJson = "";
	private boolean isloading=true;
	/**
	 * 从服务器上获取数据
	 */
	@Override
	protected Object getServiceData() {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_RECOMMEND_INFO);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				counselorJson = result;
				isloading=false;
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				isloading=false;
			}

			@Override
			public void onCancelled(CancelledException cex) {
				isloading=false;
			}

			@Override
			public void onFinished() {
				isloading=false;

			}
		});
		/**
		 * 使两个线程同步
		 */
		while(isloading){
			
		}
		isloading=true;
		try {
			adviser = AdviserJson.getInstance().analyzeAdviserDetail(
					counselorJson);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (adviser != null) {
			showPicUrl = adviser.getShowpic();
			handler.sendEmptyMessage(0);
		}
		return adviser;
	}

	class MyAdapter extends Adapter<MyViewHolder> {

		@Override
		public int getItemCount() {
			// TODO Auto-generated method stub
			if (adviser != null) {
				if (showPicUrl != null && showPicUrl.get(0) != null) {
					showPicUrl.add(0, null);
				}
				LogUtil.i("长度:" + showPicUrl.size());
				return showPicUrl == null ? 1 : showPicUrl.size();
			} else {
				return 0;
			}
		}

		@Override
		public void onBindViewHolder(final MyViewHolder holder, int position) {
			if (adviser != null) {
				if (position == 0) {
					holder.headLl.setVisibility(View.VISIBLE);
					holder.showpic_iv.setVisibility(View.GONE);
					holder.nameTv.setText(adviser.getName() + "");
					holder.identityTv.setText(adviser.getTitle() + "");
					holder.descTv.setText(adviser.getResume() + "");
					holder.ideaTv.setText("理念: " + adviser.getIdea() + "");
					LogUtil.i("图片地址：" + adviser.getAutographPath());
					// 绑定图片
					x.image().bind(holder.circleImageView,
							adviser.getAutographPath(), mImageOptions,
							new CommonCallback<Drawable>() {

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
				} else {
					holder.headLl.setVisibility(View.GONE);
					holder.showpic_iv.setVisibility(View.VISIBLE);
					// 绑定图片
					x.image().bind(holder.showpic_iv,showPicUrl.get(position),
							mImageOptions);
				}
			}
		}

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.item_rv_showpic, parent, false);
			MyViewHolder holder = new MyViewHolder(v);
			return holder;
		}
	}

	class MyViewHolder extends RecyclerView.ViewHolder {
		TextView nameTv, identityTv, descTv, ideaTv;
		CircleImageView2 circleImageView;
		ImageView showpic_iv;
		LinearLayout headLl;

		public MyViewHolder(View view) {
			super(view);
			headLl = (LinearLayout) view.findViewById(R.id.recommend_head);
			circleImageView = (CircleImageView2) view
					.findViewById(R.id.recommend_iv_head);
			nameTv = (TextView) view.findViewById(R.id.name);
			identityTv = (TextView) view.findViewById(R.id.identity);
			descTv = (TextView) view.findViewById(R.id.recommend_tv_desc);
			ideaTv = (TextView) view.findViewById(R.id.idea);
			showpic_iv = (ImageView) view.findViewById(R.id.showpic_iv);
			WindowManager wm=(WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			int width1=wm.getDefaultDisplay().getWidth();
			int height=width1*3/2;
			LayoutParams params=(LayoutParams) showpic_iv.getLayoutParams();
			params.height=height;
			params.width=width1;
			showpic_iv.setLayoutParams(params);

		}
	}

}
