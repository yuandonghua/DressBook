package cn.dressbook.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.XiuGaiCanShuActivity.XuanZeListener;
import cn.dressbook.ui.adapter.TiaoZhengXiJieAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.DZSPDetails;
import cn.dressbook.ui.model.DZSPDetailsParams;
import cn.dressbook.ui.model.Mianliao;
import cn.dressbook.ui.model.Param;
import cn.dressbook.ui.model.Preval;
import cn.dressbook.ui.model.TiaoZhenCanShu;
import cn.dressbook.ui.net.LSDZExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;
import cn.dressbook.ui.utils.ToastUtils;

/**
 * @description: 调整细节界面
 * @author:ydh
 * @data:2016-4-25上午11:39:04
 */
@ContentView(R.layout.activity_diy)
public class TiaoZhengXiJieActivity extends BaseActivity {
	private Context mContext;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 品类的id
	 */
	private String attiteId;
	private LinearLayoutManager mLinearLayoutManager;
	private TiaoZhengXiJieAdapter tiaoZhengXiJieAdapter;
	@ViewInject(R.id.qd_tv)
	private TextView qd_tv;
	private ArrayList<TiaoZhenCanShu> mlist = new ArrayList<TiaoZhenCanShu>();
	private DZSPDetails dzspDetails;
	private int mPosition;
	private double priceDJ, priceCJ;

	@Override
	protected void initView() {
		mContext = this;
		Intent intent = getIntent();
		attiteId = intent.getStringExtra("attire_id");
		title_tv.setText("调整细节");
		// 添加分割线
		recyclerview
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						this).color(getResources().getColor(R.color.touming))
						.size(10).margin(0, 0).build());
		mLinearLayoutManager = new LinearLayoutManager(mContext);
		recyclerview.setLayoutManager(mLinearLayoutManager);
		// 确定修改
		XiuGaiCanShuActivity.setXuanZeListener(new XuanZeListener() {

			@Override
			public void onXZ(TiaoZhenCanShu tzcs) {
				// TODO Auto-generated method stub
				mlist.set(mPosition, tzcs);
				tiaoZhengXiJieAdapter.notifyItemChanged(mPosition);
				if (!"null".equals(tzcs.getMaterialPrice())
						&& Double.parseDouble(tzcs.getMaterialPrice()) > 0) {
					LogUtil.e("priceCJ:" + priceCJ);
					LogUtil.e("priceDJ:" + priceDJ);
					priceDJ = priceCJ
							+ Double.parseDouble(tzcs.getMaterialPrice());

					qd_tv.setText("确定(￥" + priceDJ + ")");
				}
			}
		});

	}

	// 访问数据是否结束

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		tiaoZhengXiJieAdapter = new TiaoZhengXiJieAdapter(mContext, mHandler);

		recyclerview.setAdapter(tiaoZhengXiJieAdapter);
		String userId = ManagerUtils.getInstance().getUser_id(mContext);
		pbDialog.show();
		// 获取种类定制列表
		LSDZExec.getInstance().getDIYList(mHandler, attiteId, userId,
				NetworkAsyncCommonDefines.GET_DIY_S,
				NetworkAsyncCommonDefines.GET_DIY_F);

	}

	@Event({ R.id.back_rl, R.id.qx_tv, R.id.qd_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击取消
		case R.id.qx_tv:
			finish();
			break;
		// 点击确定
		case R.id.qd_tv:
			pbDialog.show();
			String mes = tiaoZhengXiJieAdapter.getLiuYan();
			LogUtil.e("mes:" + mes);
			JSONArray paramjson = new JSONArray();
			for (int i = 1; i < mlist.size() - 1; i++) {
				TiaoZhenCanShu tiaoZhenCanShu = mlist.get(i);
				JSONObject obj = new JSONObject();
				try {
					obj.put("param_id", tiaoZhenCanShu.getId());
					obj.put("param_name", tiaoZhenCanShu.gettitle());
					obj.put("param_value", tiaoZhenCanShu.getcontent());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				paramjson.put(obj);
			}
			// 上传数据
			LSDZExec.getInstance().pullUpDIYList(mHandler,
					ManagerUtils.getInstance().getUser_id(activity), "1",
					dzspDetails.getId(), dzspDetails.getTemplateId(), mes,
					paramjson.toString(),
					NetworkAsyncCommonDefines.GET_DZSP_BCXX_S,
					NetworkAsyncCommonDefines.GET_DZSP_BCXX_F);
			break;
		default:
			break;
		}

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 点击了修改
			case NetworkAsyncCommonDefines.CLICK_XIUGAI:
				Bundle xgData = msg.getData();
				if (xgData != null) {
					pbDialog.show();
					mPosition = xgData.getInt("position");
					if (mPosition != 0) {
						ArrayList<TiaoZhenCanShu> xgList = new ArrayList<TiaoZhenCanShu>();
						String title = "";
						TiaoZhenCanShu tiaoZhenCanShu = mlist.get(mPosition);
						if (tiaoZhenCanShu != null && dzspDetails != null) {
							String id = tiaoZhenCanShu.getId();
							ArrayList<DZSPDetailsParams> params = dzspDetails
									.getParams();
							if (params != null) {
								// 参数
								for (DZSPDetailsParams dzspDetailsParams : params) {
									if (id.equals(dzspDetailsParams
											.getparamId())
											&& dzspDetailsParams
													.getPrevalList() != null) {
										title = dzspDetailsParams
												.getparamName();
										for (TiaoZhenCanShu p : dzspDetailsParams
												.getPrevalList()) {
											xgList.add(p);
										}

									}

								}
							}
							Intent intent = new Intent(activity,
									XiuGaiCanShuActivity.class);
							intent.putParcelableArrayListExtra("xgList", xgList);
							intent.putExtra("title", title);
							startActivity(intent);
						}
					}
					pbDialog.dismiss();
				}
				break;
			// 获取种类定制列表成功
			case NetworkAsyncCommonDefines.GET_DIY_S:
				Bundle myxData = msg.getData();
				if (myxData != null) {
					dzspDetails = myxData.getParcelable("dzspDetails");
					if (dzspDetails != null) {
						priceDJ = Double.parseDouble(dzspDetails.getPrice());
						TiaoZhenCanShu tiaoZhenCanShu0 = new TiaoZhenCanShu();
						tiaoZhenCanShu0.settitle("参与设计，定制独特");
						tiaoZhenCanShu0.setcontent("参与布料、工艺的调整可能会引起价格的调整，请知悉。");
						tiaoZhenCanShu0.setPic(dzspDetails.getTitlePic());
						mlist.add(0, tiaoZhenCanShu0);
						ArrayList<DZSPDetailsParams> params = dzspDetails
								.getParams();
						if (params != null) {
							// 参数
							for (DZSPDetailsParams dzspDetailsParams : params) {
								TiaoZhenCanShu tiaoZhenCanShu = new TiaoZhenCanShu();
								if ("面料号".equals(dzspDetailsParams
										.getparamName())) {
									if (dzspDetailsParams.getPrevalList() != null) {
										for (TiaoZhenCanShu tzcs : dzspDetailsParams
												.getPrevalList()) {
											if (dzspDetailsParams
													.getparamValue().equals(
															tzcs.getcontent())) {
												priceCJ = priceDJ
														- Double.parseDouble(tzcs
																.getMaterialPrice());
												LogUtil.e("priceCJ:" + priceCJ);
											}
										}
									}
								}
								tiaoZhenCanShu.settitle(dzspDetailsParams
										.getparamName());
								tiaoZhenCanShu.setcontent(dzspDetailsParams
										.getparamValue());
								tiaoZhenCanShu.setPic(dzspDetailsParams
										.getParamPic());
								tiaoZhenCanShu.setId(dzspDetailsParams
										.getparamId());
								mlist.add(tiaoZhenCanShu);

							}
						}

					}
					mlist.add(null);
					tiaoZhengXiJieAdapter.setData(mlist);
					tiaoZhengXiJieAdapter.notifyDataSetChanged();

					qd_tv.setText("确定(￥" + priceDJ + ")");
				}
				pbDialog.dismiss();
				break;
			// 获取种类定制列表失败
			case NetworkAsyncCommonDefines.GET_DIY_F:
				pbDialog.dismiss();
				break;

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
					LogUtil.e("order_priceTotal:" + order_priceTotal);
					Intent BCXXActivity = new Intent(
							TiaoZhengXiJieActivity.this, BCXXActivity.class);
					BCXXActivity.putExtra("order_id", order_id);
					BCXXActivity.putExtra("order_dz_attire_id",
							order_dz_attire_id);
					BCXXActivity.putExtra("template_id", template_id);
					BCXXActivity.putExtra("order_priceTotal", order_priceTotal);
					BCXXActivity.putExtra("sex", sex);
					startActivity(BCXXActivity);
					pbDialog.dismiss();
					finish();
				}
				break;
			// 获取定制商品补充信息失败
			case NetworkAsyncCommonDefines.GET_DZSP_BCXX_F:
				pbDialog.dismiss();
				break;

			}

		};
	};

}
