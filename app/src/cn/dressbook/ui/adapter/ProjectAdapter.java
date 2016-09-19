package cn.dressbook.ui.adapter;

import java.util.List;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.LoginActivity;
import cn.dressbook.ui.ProjectDetailActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.bean.AixinjuanyiBeanProject;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.AiXinJuanYiExec;

/**
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-30 上午11:35:15
 * @since
 * @version
 */
@SuppressLint("InflateParams")
@SuppressWarnings("deprecation")
public class ProjectAdapter extends BaseAdapter {

	private Activity mContext;
	private List<AixinjuanyiBeanProject> data;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
	private boolean supportCooldown = false;

	private Handler mHandler;

	public ProjectAdapter(Activity activity, Handler handler) {
		this.mContext = activity;
		mHandler = handler;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-30 上午11:35:15
	 * @return
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return data != null ? data.size() : 0;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-30 上午11:35:16
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public AixinjuanyiBeanProject getItem(int position) {
		return data.get(position);
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-30 上午11:35:16
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return data.get(position).hashCode();
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-30 上午11:35:16
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 * @see android.widget.Adapter#getView(int, View, ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = mContext.getLayoutInflater().inflate(
					R.layout.item_listview_project, null);
			holder.tvItemProjectTitle = (TextView) convertView
					.findViewById(R.id.tvItemProjectTitle);
			holder.ivItemProjectPicture = (ImageView) convertView
					.findViewById(R.id.ivItemProjectPicture);
			holder.tvItemProjectStatus = (TextView) convertView
					.findViewById(R.id.tvItemProjectStatus);
			holder.tvItemProjectTime = (TextView) convertView
					.findViewById(R.id.tvItemProjectTime);
			holder.tvItemProjectJoinNum = (TextView) convertView
					.findViewById(R.id.tvItemProjectJoinNum);
			holder.tvItemProjectSupportNum = (TextView) convertView
					.findViewById(R.id.tvItemProjectSupportNum);
			holder.tvItemProjectSponsor = (TextView) convertView
					.findViewById(R.id.tvItemProjectSponsor);
			holder.tvItemProjectDescription = (TextView) convertView
					.findViewById(R.id.tvItemProjectDescription);
			holder.btnItemProjectSupport = (Button) convertView
					.findViewById(R.id.btnItemProjectSupport);
			holder.btnItemProjectDetail = (Button) convertView
					.findViewById(R.id.btnItemProjectDetail);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final AixinjuanyiBeanProject project = data.get(position);
		holder.tvItemProjectTitle.setText(project.getTitle());
		// 绑定图片
		x.image().bind(holder.ivItemProjectPicture, project.getPic(),
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

		holder.tvItemProjectTime.setText("启动：" + project.getAddTimeShow());
		if (project.getState() == 2) {
			holder.tvItemProjectStatus.setText("进行中");
		} else if (project.getState() == 9) {
			holder.tvItemProjectStatus.setText("已结束");
		}
		holder.tvItemProjectSupportNum.setText("支持" + project.getPraiseNum());
		holder.tvItemProjectJoinNum.setText("参加" + project.getJoinNum());
		holder.tvItemProjectSponsor.setText("项目发起：" + project.getSponsor());
		holder.tvItemProjectDescription.setText(project.getContent());
		// 支持
		holder.btnItemProjectSupport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!ManagerUtils.getInstance().isLogin(mContext)) {
					if (loadState != null) {
						loadState.loadState(true);
					}
					// 跳转到登录页
					Intent intent = new Intent(mContext, LoginActivity.class);
					mContext.startActivity(intent);
				} else {
					// 支持
					if (project.getIsPraise() != 1 && !supportCooldown)
						toSupportProject(project.getId() + "", position);

				}
			}
		});
		if (project.getIsPraise() == 0) {
			holder.btnItemProjectSupport.setText("支持");
			holder.btnItemProjectSupport
					.setBackgroundDrawable(mContext.getResources().getDrawable(
							R.drawable.selector_red_button));
			holder.btnItemProjectSupport.setFocusable(true);
		} else if (project.getIsPraise() == 1) {
			holder.btnItemProjectSupport.setText("已支持");
			holder.btnItemProjectSupport.setBackgroundDrawable(mContext
					.getResources().getDrawable(
							R.drawable.selector_unavailable_button));
			holder.btnItemProjectSupport.setFocusable(false);
		} else {
			holder.btnItemProjectSupport.setText("支持");
			holder.btnItemProjectSupport
					.setBackgroundDrawable(mContext.getResources().getDrawable(
							R.drawable.selector_red_button));
			holder.btnItemProjectSupport.setFocusable(true);

		}
		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击详情
				mContext.startActivity(new Intent(mContext,
						ProjectDetailActivity.class).putExtra("PROJECT_ID",
						project.getId() + ""));
			}
		};
		holder.btnItemProjectDetail.setOnClickListener(l);
		holder.ivItemProjectPicture.setOnClickListener(l);
		return convertView;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @param l
	 * @date 2015-10-30 下午3:20:17
	 * @see
	 */
	private void toSupportProject(String projectId, final int position) {
		supportCooldown = true;
		// 支持爱心捐衣
		AiXinJuanYiExec.getInstance().zhiChiXiangMu(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext), projectId,
				NetworkAsyncCommonDefines.ZHICHI_AXJY_S,
				NetworkAsyncCommonDefines.ZHICHI_AXJY_F);
		// 支持成功
		data.get(position).setIsPraise(1);
		data.get(position).setPraiseNum(data.get(position).getPraiseNum() + 1);
		notifyDataSetChanged();
		supportCooldown = false;
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @company Gifted Youngs Workshop
	 * @date 2015-10-30 上午11:52:56
	 * @since
	 * @version
	 */
	private class ViewHolder {
		private TextView tvItemProjectTitle;
		private ImageView ivItemProjectPicture;
		private TextView tvItemProjectStatus;
		private TextView tvItemProjectTime;
		private TextView tvItemProjectJoinNum;
		private TextView tvItemProjectSupportNum;
		private TextView tvItemProjectSponsor;
		private TextView tvItemProjectDescription;
		private Button btnItemProjectSupport;
		private Button btnItemProjectDetail;
	}

	/**
	 * TODO 设置 data 的值
	 */
	public void setData(List<AixinjuanyiBeanProject> data) {
		this.data = data;
	}

	OnLoadState loadState;

	public void setOnLoadState(OnLoadState loadState) {
		this.loadState = loadState;
	}

	/**
	 * 判断是否登录
	 */
	public interface OnLoadState {
		void loadState(boolean isLoad);
	}
}
