package cn.dressbook.ui;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.tv.TvContract.Channels.Logo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.DonateAddressAdapter;
import cn.dressbook.ui.adapter.ProjectProgressAdapter;
import cn.dressbook.ui.bean.AixinjuanyiBeanDonateAddress;
import cn.dressbook.ui.bean.AixinjuanyiBeanProject;
import cn.dressbook.ui.bean.AixinjuanyiBeanProjectProgress;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.AiXinJuanYiExec;
import cn.dressbook.ui.view.HyperListView;
import cn.dressbook.ui.view.JoinSuccessDialog;

/**
 * @description: 项目详情
 * @author:袁东华
 * @time:2015-11-4上午10:28:11
 */
@ContentView(R.layout.activity_project_detail)
public class ProjectDetailActivity extends BaseActivity {
	private Context mContext = ProjectDetailActivity.this;
	private Activity activity;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
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
	private TextView tvProjectDetailTitle;
	private ImageView ivProjectDetailPicture;
	private TextView tvProjectDetailStatus;
	private TextView tvProjectDetailTime;
	private TextView tvProjectDetailJoinNum;
	private TextView tvProjectDetailSupportNum;
	private TextView tvProjectDetailSponsor;
	private TextView tvProjectDetailDescription;
	private HyperListView lvProjectDetailDonateAddress;
	private HyperListView lvProjectDetailProgress;
	private RelativeLayout rlProjectDetailSupport;
	private ImageView ivProjectDetailSupport;
	private TextView tvProjectDetailSupport;
	private RelativeLayout rlProjectDetailJoin;
	private ImageView ivProjectDetailJoin;
	private TextView tvProjectDetailJoin;

	private List<AixinjuanyiBeanDonateAddress> listDonateAddress;
	private List<AixinjuanyiBeanProjectProgress> listProjectProgress;
	private AixinjuanyiBeanProject project;

	private DonateAddressAdapter donateAddressAdapter;
	private ProjectProgressAdapter projectProgressAdapter;

	private String projectId;

	private boolean isSupport = false;
	private boolean isJoin = false;

	private boolean joinCooldown = false;
	private boolean supportCooldown = false;

	@SuppressWarnings("unused")
	private JoinSuccessDialog joinSuccessDialog;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("项目详情");
		tvProjectDetailTitle = (TextView) findViewById(R.id.tvProjectDetailTitle);
		ivProjectDetailPicture = (ImageView) findViewById(R.id.ivProjectDetailPicture);
		tvProjectDetailStatus = (TextView) findViewById(R.id.tvProjectDetailStatus);
		tvProjectDetailTime = (TextView) findViewById(R.id.tvProjectDetailTime);
		tvProjectDetailJoinNum = (TextView) findViewById(R.id.tvProjectDetailJoinNum);
		tvProjectDetailSupportNum = (TextView) findViewById(R.id.tvProjectDetailSupportNum);
		tvProjectDetailSponsor = (TextView) findViewById(R.id.tvProjectDetailSponsor);
		tvProjectDetailDescription = (TextView) findViewById(R.id.tvProjectDetailDescription);
		lvProjectDetailDonateAddress = (HyperListView) findViewById(R.id.lvProjectDetailDonateAddress);
		lvProjectDetailProgress = (HyperListView) findViewById(R.id.lvProjectDetailProgress);
		rlProjectDetailSupport = (RelativeLayout) findViewById(R.id.rlProjectDetailSupport);
		ivProjectDetailSupport = (ImageView) findViewById(R.id.ivProjectDetailSupport);
		tvProjectDetailSupport = (TextView) findViewById(R.id.tvProjectDetailSupport);
		rlProjectDetailJoin = (RelativeLayout) findViewById(R.id.rlProjectDetailJoin);
		ivProjectDetailJoin = (ImageView) findViewById(R.id.ivProjectDetailJoin);
		tvProjectDetailJoin = (TextView) findViewById(R.id.tvProjectDetailJoin);
		setListener();
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		activity = this;
		// intent
		projectId = getIntent().getStringExtra("PROJECT_ID");
		// 项目
		project = new AixinjuanyiBeanProject();
		// 收件地址
		listDonateAddress = new ArrayList<AixinjuanyiBeanDonateAddress>();
		donateAddressAdapter = new DonateAddressAdapter(activity,
				listDonateAddress);
		lvProjectDetailDonateAddress.setAdapter(donateAddressAdapter);
		// 项目进展
		listProjectProgress = new ArrayList<AixinjuanyiBeanProjectProgress>();
		projectProgressAdapter = new ProjectProgressAdapter(activity,
				listProjectProgress);
		lvProjectDetailProgress.setAdapter(projectProgressAdapter);
		performTask();
	}

	@Event({ R.id.back_rl })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		default:
			break;
		}
	}

	protected void setListener() {
		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				// 点击支持
				case R.id.rlProjectDetailSupport:
					if (ManagerUtils.getInstance().isLogin(mContext)) {

						if (!isSupport && !supportCooldown) {
							toSupportProjectDetail();
						} else {
						}
					} else {
						startActivity(new Intent(ProjectDetailActivity.this,
								LoginActivity.class));
					}
					break;
				// 点击参加
				case R.id.rlProjectDetailJoin:
					if (ManagerUtils.getInstance().isLogin(mContext)) {
						if (!isJoin && !joinCooldown) {
							joinProjectDetail("");
						} else {
						}
					} else {
						Intent bindPhone = new Intent(mContext,
								LoginActivity.class);
						startActivity(bindPhone);
						((Activity) mContext).overridePendingTransition(
								R.anim.back_enter, R.anim.anim_exit);
					}

					break;
				// case R.id.btnProjectDetailAddressSend:
				// String num = etProjectDetailAddressUserPhone
				// .getEditableText().toString();
				// if (SU.isPhoneNumber(num) && !joinCooldown) {
				// joinProjectDetail(num);
				// } else {
				// T.s(T.WRONG_PHONE, activity);
				// }
				// break;
				}
			}
		};
		rlProjectDetailJoin.setOnClickListener(l);
		rlProjectDetailSupport.setOnClickListener(l);
		// 号码输入框监听
		// etProjectDetailAddressUserPhone
		// .addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start,
		// int before, int count) {
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start,
		// int count, int after) {
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// if (s.toString().length() > 0) {
		// btnProjectDetailAddressSend
		// .setBackgroundDrawable(getResources()
		// .getDrawable(
		// R.drawable.selector_red_button));
		// btnProjectDetailAddressSend.setClickable(true);
		// } else {
		// btnProjectDetailAddressSend
		// .setBackgroundDrawable(getResources()
		// .getDrawable(
		// R.drawable.selector_unavailable_button));
		// btnProjectDetailAddressSend.setClickable(false);
		// }
		// }
		// });
	}

	protected void performTask() {
		queryDataProjectDetail();
	}

	/**
	 * TODO 发起请求查询项目具体数据
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午2:03:49
	 * @see
	 */
	private void queryDataProjectDetail() {
		// 获取项目详情
		AiXinJuanYiExec.getInstance().getAXJYProject(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext), projectId,
				NetworkAsyncCommonDefines.GET_AXJY_PROJECT_S,
				NetworkAsyncCommonDefines.GET_AXJY_PROJECT_F);

	}

	/**
	 * 
	 * TODO 参加此项目
	 * 
	 * @author LiShen
	 * @date 2015-11-1 下午10:42:23
	 * @see
	 */
	private void joinProjectDetail(String phoneNum) {
		joinCooldown = true;
		// 参加项目
		AiXinJuanYiExec.getInstance().canJiaAXJYProject(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext), projectId,
				phoneNum, NetworkAsyncCommonDefines.CANJIA_PROJIECT_S,
				NetworkAsyncCommonDefines.CANJIA_PROJIECT_F);
		isJoin = true;
		tvProjectDetailJoin.setTextColor(getResources().getColor(
				R.color.main_text_orange));
		tvProjectDetailJoin.setText("已参加");
		ivProjectDetailJoin.setImageDrawable(getResources().getDrawable(
				R.drawable.ic_orange_join));
		// 弹框提示
		joinSuccessDialog = new JoinSuccessDialog(activity);

		joinCooldown = false;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @param l
	 * @date 2015-10-30 下午3:20:17
	 * @see
	 */
	private void toSupportProjectDetail() {
		supportCooldown = true;

		// 支持项目
		AiXinJuanYiExec.getInstance().zhiChiXiangMu(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext), projectId,
				NetworkAsyncCommonDefines.ZHICHI_AXJY_S,
				NetworkAsyncCommonDefines.ZHICHI_AXJY_F);

		// 支持成功
		isSupport = true;
		tvProjectDetailSupport.setTextColor(getResources().getColor(
				R.color.main_text_orange));
		tvProjectDetailSupport.setText("已支持");
		ivProjectDetailSupport.setImageDrawable(getResources().getDrawable(
				R.drawable.ic_orange_praise));
		supportCooldown = false;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 获取项目详情成功
			case NetworkAsyncCommonDefines.GET_AXJY_PROJECT_S:
				Bundle projectData = msg.getData();
				if (projectData != null) {
					project = projectData.getParcelable("project");
					AixinjuanyiBeanDonateAddress tempAddress = projectData
							.getParcelable("address");
					listDonateAddress = new ArrayList<AixinjuanyiBeanDonateAddress>();
					listDonateAddress.add(tempAddress);
					listProjectProgress = projectData
							.getParcelableArrayList("progress");
				}
				// 显示项目
				if (project != null) {
					tvProjectDetailTitle.setText(project.getTitle());
					// 绑定图片
					x.image().bind(ivProjectDetailPicture, project.getPic(),
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
					tvProjectDetailTime.setText("启动："
							+ project.getAddTimeShow());
					if (project.getState() == 2) {
						tvProjectDetailStatus.setText("进行中");
					} else if (project.getState() == 9) {
						tvProjectDetailStatus.setText("已结束");
					}
					tvProjectDetailSupportNum.setText("支持"
							+ project.getPraiseNum());
					tvProjectDetailJoinNum.setText("参加" + project.getJoinNum());
					tvProjectDetailSponsor.setText("项目发起："
							+ project.getSponsor());
					tvProjectDetailDescription.setText(project.getContent());
					if (project.getIsJoin() == 1) {
						isJoin = true;
						tvProjectDetailJoin.setTextColor(getResources()
								.getColor(R.color.main_text_orange));
						tvProjectDetailJoin.setText("已参加");
						ivProjectDetailJoin.setImageDrawable(getResources()
								.getDrawable(R.drawable.ic_orange_join));
					} else {
						isJoin = false;
						tvProjectDetailJoin.setTextColor(getResources()
								.getColor(R.color.main_text_grey));
						tvProjectDetailJoin.setText("参加");
						ivProjectDetailJoin.setImageDrawable(getResources()
								.getDrawable(R.drawable.ic_grey_join));
					}
					if (project.getIsPraise() == 1) {
						isSupport = true;
						tvProjectDetailSupport.setTextColor(getResources()
								.getColor(R.color.main_text_orange));
						tvProjectDetailSupport.setText("已支持");
						ivProjectDetailSupport.setImageDrawable(getResources()
								.getDrawable(R.drawable.ic_orange_praise));
					} else {
						isSupport = false;
						tvProjectDetailSupport.setTextColor(getResources()
								.getColor(R.color.main_text_grey));
						tvProjectDetailSupport.setText("支持");
						ivProjectDetailSupport.setImageDrawable(getResources()
								.getDrawable(R.drawable.ic_grey_praise));
					}
				}
				// 显示地址
				if (listDonateAddress != null && listDonateAddress.size() > 0) {
					donateAddressAdapter.setData(listDonateAddress);
				}
				// 显示进程
				if (listProjectProgress != null
						&& listProjectProgress.size() > 0) {
					projectProgressAdapter.setData(listProjectProgress);
				}
				break;
			// 获取项目详情
			case NetworkAsyncCommonDefines.GET_AXJY_PROJECT_F:

				break;
			default:
				break;
			}
		};

	};

}
