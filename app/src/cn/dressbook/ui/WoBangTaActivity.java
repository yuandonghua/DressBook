package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.PLAdapter;
import cn.dressbook.ui.adapter.XLAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.PL;
import cn.dressbook.ui.model.XL;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.view.CircleImageView;
import cn.dressbook.ui.view.MyScrollView;

/**
 * @description: 我帮TA界面
 * @author:ydh
 * @data:2016-5-5下午5:55:06
 */
@ContentView(R.layout.searchclothing)
public class WoBangTaActivity extends BaseActivity {
	private ImageOptions mImageOptions= ManagerUtils.getInstance()
			.getImageOptions();
	private Context mContext = WoBangTaActivity.this;
	private ArrayList<PL> list = new ArrayList<PL>();
	private ArrayList<XL> xllist = new ArrayList<XL>();
	private ArrayList<PL> bqList = new ArrayList<PL>();
	private String reqdesc, addtime, username, occation, category, pricemax,
			pricemin, useravatar, sex, categoryid, mPrice, mOccation, id, jw;
	private String cid = "", attr_tags = "", keyword = "";
	private ArrayList<PL> list2;
	private int mPosition;
	/**
	 * 品类
	 */
	private PLAdapter plAdapter;
	/**
	 * 标签品类
	 */
	private PLAdapter bqPLAdapter;
	/**
	 * 细类
	 */
	private XLAdapter xlAdapter;
	/**
	 * 标签细类
	 */
	private XLAdapter bqXLAdapter;
	/**
	 * 头像
	 */
	@ViewInject(R.id.circleimageview)
	private CircleImageView circleimageview;
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
	/**
	 * 昵称
	 */
	@ViewInject(R.id.name_tv)
	private TextView name_tv;
	/**
	 * 时间
	 */
	@ViewInject(R.id.time_tv)
	private TextView time_tv;
	/**
	 * 场合
	 */
	@ViewInject(R.id.ch_value)
	private TextView ch_value;
	/**
	 * 品类
	 */
	@ViewInject(R.id.pl_value)
	private TextView pl_value;
	/**
	 * 价位
	 */
	@ViewInject(R.id.jw_value)
	private TextView jw_value;
	/**
	 * 一句话
	 */
	@ViewInject(R.id.yjh_tv)
	private TextView yjh_tv;
	/**
	 * 职场
	 */
	@ViewInject(R.id.zc_rl)
	private RelativeLayout zc_rl;
	/**
	 * 度假
	 */
	@ViewInject(R.id.dj_rl)
	private RelativeLayout dj_rl;
	/**
	 * 商务
	 */
	@ViewInject(R.id.sw_rl)
	private RelativeLayout sw_rl;
	/**
	 * 运动
	 */
	@ViewInject(R.id.yd_rl)
	private RelativeLayout yd_rl;
	/**
	 * 休闲聚会
	 */
	@ViewInject(R.id.xx_rl)
	private RelativeLayout xx_rl;
	/**
	 * 价位
	 */
	@ViewInject(R.id.jw1_tv)
	private TextView jw1_tv;
	@ViewInject(R.id.jw2_tv)
	private TextView jw2_tv;
	@ViewInject(R.id.jw3_tv)
	private TextView jw3_tv;
	@ViewInject(R.id.jw4_tv)
	private TextView jw4_tv;
	@ViewInject(R.id.jw5_tv)
	private TextView jw5_tv;
	@ViewInject(R.id.bq1_rl)
	private RelativeLayout bq1_rl;
	@ViewInject(R.id.bq2_rl)
	private RelativeLayout bq2_rl;
	@ViewInject(R.id.bq3_rl)
	private RelativeLayout bq3_rl;
	@ViewInject(R.id.bq4_rl)
	private RelativeLayout bq4_rl;
	@ViewInject(R.id.bq5_rl)
	private RelativeLayout bq5_rl;
	/**
	 * 品类
	 */
	@ViewInject(R.id.recyclerview1)
	private RecyclerView recyclerview1;
	/**
	 * 细类
	 */
	@ViewInject(R.id.recyclerview2)
	private RecyclerView recyclerview2;
	/**
	 * 标签品类
	 */
	@ViewInject(R.id.recyclerview3)
	private RecyclerView recyclerview3;
	/**
	 * 标签小类
	 */
	@ViewInject(R.id.recyclerview4)
	private RecyclerView recyclerview4;
	/**
	 * 关键字
	 */
	@ViewInject(R.id.search_et)
	private EditText search_et;
	@ViewInject(R.id.myscrollview)
	private MyScrollView myscrollview;
	/**
	 * 5个标签
	 */
	@ViewInject(R.id.bq1_tv)
	private TextView bq1_tv;
	@ViewInject(R.id.bq2_tv)
	private TextView bq2_tv;
	@ViewInject(R.id.bq3_tv)
	private TextView bq3_tv;
	@ViewInject(R.id.bq4_tv)
	private TextView bq4_tv;
	@ViewInject(R.id.bq5_tv)
	private TextView bq5_tv;


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("搜索服装");
		plAdapter = new PLAdapter();
		recyclerview1.setLayoutManager(new GridLayoutManager(mContext, 3));
		recyclerview1.setAdapter(plAdapter);
		xlAdapter = new XLAdapter();
		recyclerview2.setLayoutManager(new GridLayoutManager(mContext, 3));
		recyclerview2.setAdapter(xlAdapter);
		// 点击品类条目
		plAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				mPosition = position;
				xlAdapter.getPosition().clear();
				bqs = null;
				bqs = new ArrayList<XL>();
				bq1_rl.setVisibility(View.GONE);
				bq2_rl.setVisibility(View.GONE);
				bq3_rl.setVisibility(View.GONE);
				bq4_rl.setVisibility(View.GONE);
				bq5_rl.setVisibility(View.GONE);
				list2 = plAdapter.getData();
				categoryid = list2.get(position).getId();
				for (int i = 0; i < list2.size(); i++) {
					if (i == position) {
						list2.get(i).setIsSelected(1);
					} else {
						list2.get(i).setIsSelected(0);
					}
				}
				plAdapter.setData(list2);
				plAdapter.notifyDataSetChanged();
				int line2 = list2.get(position).getXls().size() / 3;
				int count2 = list2.get(position).getXls().size() % 3;
				if (count2 > 0) {
					line2++;
				}
				RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) recyclerview2
						.getLayoutParams();
				lp2.height = 86 * line2;
				recyclerview2.setLayoutParams(lp2);
				xlAdapter.setData(list2.get(position).getXls());
				xlAdapter.notifyDataSetChanged();
				// 获取标签
				// RequirementExec.getInstance().getBQ(mHandler,
				// list2.get(position).getId(),
				// NetworkAsyncCommonDefines.GET_BQ_S,
				// NetworkAsyncCommonDefines.GET_BQ_F);

			}
		});
		// 标签品类适配器
		bqPLAdapter = new PLAdapter();
		recyclerview3.setLayoutManager(new LinearLayoutManager(mContext,
				LinearLayoutManager.VERTICAL, false));
		recyclerview3.setAdapter(bqPLAdapter);
		// 标签细类适配器
		bqXLAdapter = new XLAdapter();
		bqXLAdapter.setHandler(mHandler);
		recyclerview4.setLayoutManager(new GridLayoutManager(mContext, 3));
		recyclerview4.setAdapter(bqXLAdapter);
		// 点击标签品类条目
		bqPLAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				// position1 = position;
				ArrayList<PL> list = bqPLAdapter.getData();
				for (int i = 0; i < list.size(); i++) {
					if (i == position) {
						list.get(i).setIsSelected(1);
					} else {
						list.get(i).setIsSelected(0);
					}
				}
				bqPLAdapter.setData(list);
				bqPLAdapter.notifyDataSetChanged();
				int line4 = list.get(position).getXls().size() / 3;
				int count4 = list.get(position).getXls().size() % 3;
				if (count4 > 0) {
					line4++;
				}
				bqXLAdapter.setData(list.get(position).getXls());
				bqXLAdapter.notifyDataSetChanged();

			}
		});
		bqXLAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
			}
		});
		search_et.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (inputMethodManager.isActive()) {
					myscrollview.scrollTo(0, search_et.getBottom());
				}
			}
		});
		search_et.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (inputMethodManager.isActive()) {
					myscrollview.scrollTo(0, search_et.getBottom());
				}
			}
		});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			cid = intent.getStringExtra("childid");
			id = intent.getStringExtra("id");
			reqdesc = intent.getStringExtra("reqdesc");
			addtime = intent.getStringExtra("addtime");
			username = intent.getStringExtra("username");
			occation = intent.getStringExtra("occation");
			category = intent.getStringExtra("category");
			pricemax = intent.getStringExtra("pricemax");
			pricemin = intent.getStringExtra("pricemin");
			useravatar = intent.getStringExtra("useravatar");
			sex = intent.getStringExtra("sex");
			categoryid = intent.getStringExtra("categoryid");

		}
		resetState();
		yjh_tv.setText(reqdesc);
		time_tv.setText(addtime);
		name_tv.setText(username);
		mOccation = occation;
		ch_value.setText(occation);
		pl_value.setText(category);
		xl=category;
		if (!"0".equals(pricemax)) {
			mPrice = pricemin + "-" + pricemax;
			jw_value.setText(mPrice);
			jw = mPrice;
		} else {
			mPrice = "1000-0";
			jw = mPrice;
			jw_value.setText(pricemin + "以上");
		}
		// 绑定图片
				x.image().bind(circleimageview, useravatar, mImageOptions,
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
		// 获取品类
		RequirementExec.getInstance().getPL(mHandler,
				NetworkAsyncCommonDefines.GET_PL_S,
				NetworkAsyncCommonDefines.GET_PL_F);
	}

	/**
	 * @description:清除价位状态
	 */
	private void clearJWState() {
		// TODO Auto-generated method stub
		jw1_tv.setBackgroundResource(R.drawable.textview_bg_5);
		jw2_tv.setBackgroundResource(R.drawable.textview_bg_5);
		jw3_tv.setBackgroundResource(R.drawable.textview_bg_5);
		jw4_tv.setBackgroundResource(R.drawable.textview_bg_5);
		jw5_tv.setBackgroundResource(R.drawable.textview_bg_5);
	}

	/**
	 * @description:清除场合状态
	 */
	private void clearOccationState() {
		// TODO Auto-generated method stub
		zc_rl.setBackgroundDrawable(null);
		dj_rl.setBackgroundDrawable(null);
		sw_rl.setBackgroundDrawable(null);
		yd_rl.setBackgroundDrawable(null);
		xx_rl.setBackgroundDrawable(null);
	}

	@Event({ R.id.back_rl, R.id.cz_tv, R.id.ok_tv, R.id.zc_rl, R.id.dj_rl,
			R.id.sw_rl, R.id.yd_rl, R.id.xx_rl, R.id.jw1_tv, R.id.jw2_tv,
			R.id.jw3_tv, R.id.jw4_tv, R.id.jw5_tv, R.id.bq1_rl, R.id.bq2_rl,
			R.id.bq3_rl, R.id.bq4_rl, R.id.bq5_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击重置
		case R.id.cz_tv:
			resetState();
			break;
		// 点击搜索
		case R.id.ok_tv:
			if (isFinish()) {
				list2 = plAdapter.getData();
				String pl="";
				for (int i = 0; i < list2.size(); i++) {
					if (list2.get(i).getIsSelected()==1) {
						pl=list2.get(i).getName();
						LogUtil.e("plName:"+pl);
					}
				}
				cid = "";
				ArrayList<String> positionList = xlAdapter.getPosition();
				if (positionList != null || positionList.size() > 0) {
					xl="";
					for (int i = 0; i < positionList.size(); i++) {
						if (i == positionList.size() - 1) {
							if (list2.get(mPosition).getXls() != null) {

								cid += list2
										.get(mPosition)
										.getXls()
										.get(Integer.parseInt(positionList
												.get(i))).getId();
								xl += list2
										.get(mPosition)
										.getXls()
										.get(Integer.parseInt(positionList
												.get(i))).getName();
								
							}
						} else {
							if (list2.get(mPosition).getXls() != null) {
								cid += list2
										.get(mPosition)
										.getXls()
										.get(Integer.parseInt(positionList
												.get(i))).getId()
										+ ",";
								xl += list2
										.get(mPosition)
										.getXls()
										.get(Integer.parseInt(positionList
												.get(i))).getName()
												+ " ";
							}
						}
					}
				}
				// attr_tags = "";
				// if (bqs != null) {
				// for (int j = 0; j < bqList.size(); j++) {
				// String attr = "";
				// String id = bqList.get(j).getId();
				// String values = "";
				// String name = bqList.get(j).getName();
				// boolean boo = false;
				// for (int i = 0; i < bqs.size(); i++) {
				// String value = bqs.get(i).getName();
				// if (bqs.get(i).getId().equals(id)) {
				// values += value + "@";
				//
				// boo = true;
				//
				// }
				// }
				// if (boo) {
				// if (values.endsWith("@")) {
				// values = values.substring(0,
				// values.length() - 1);
				// }
				// attr = id + ":" + values + ":" + name;
				// LogUtils.e("attr:" + attr);
				// attr_tags += attr + ",";
				// }
				// }
				// if (attr_tags.endsWith(",")) {
				// attr_tags = attr_tags.substring(0,
				// attr_tags.length() - 1);
				// }
				// LogUtils.e("attr_tags:" + attr_tags);
				// }
				keyword = search_et.getText().toString();
				if (keyword != null) {
					keyword = keyword.trim().replaceAll("\\s", "");
					keyword = keyword.replaceAll("\t", "");
					keyword = keyword.replaceAll("\r", "");
					keyword = keyword.replaceAll("\n", "");
				}
				pbDialog.show();
				Intent intent = new Intent(mContext, SearchResultActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("reqdesc", reqdesc);
				intent.putExtra("addtime", addtime);
				intent.putExtra("username", username);
				intent.putExtra("occation", occation);
				intent.putExtra("category", category);
				intent.putExtra("price", mPrice);
				intent.putExtra("useravatar", useravatar);
				intent.putExtra("sex", sex);
				intent.putExtra("occation2", mOccation);
				intent.putExtra("categoryid", categoryid);
				intent.putExtra("cid", cid);
				intent.putExtra("attr_tags", attr_tags);
				intent.putExtra("jw", jw);
				intent.putExtra("pl", pl);
				intent.putExtra("xl", xl);
				intent.putExtra("keyword", keyword);
				startActivity(intent);
				pbDialog.dismiss();
				finish();

			} else {
				Toast.makeText(mContext, "正在处理请稍后", Toast.LENGTH_SHORT).show();
			}
			// } catch (Exception e) {
			// // TODO: handle exception
			// LogUtils.e("异常:" + e.getMessage());
			// }
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击职场
		case R.id.zc_rl:
			mOccation = "职场";
			clearOccationState();
			zc_rl.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击度假
		case R.id.dj_rl:
			mOccation = "度假";
			clearOccationState();
			dj_rl.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击商务
		case R.id.sw_rl:
			mOccation = "商务";
			clearOccationState();
			sw_rl.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击运动
		case R.id.yd_rl:
			mOccation = "运动";
			clearOccationState();
			yd_rl.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击休闲聚会
		case R.id.xx_rl:
			mOccation = "休闲聚会";
			clearOccationState();
			xx_rl.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击价位
		case R.id.jw1_tv:
			jw = "0-100";
			clearJWState();
			jw1_tv.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击价位
		case R.id.jw2_tv:
			jw = "100-200";
			clearJWState();
			jw2_tv.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击价位
		case R.id.jw3_tv:
			jw = "200-500";
			clearJWState();
			jw3_tv.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击价位
		case R.id.jw4_tv:
			jw = "500-1000";
			clearJWState();
			jw4_tv.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击价位
		case R.id.jw5_tv:
			jw = "1000-0";
			clearJWState();
			jw5_tv.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击标签
		case R.id.bq1_rl:
			String bq1 = bq1_tv.getText().toString();
			if (bqs.contains(bq1)) {
				bqs.remove(bq1);
			}
			bq1_rl.setVisibility(View.GONE);
			break;
		// 点击标签
		case R.id.bq2_rl:
			String bq2 = bq2_tv.getText().toString();
			if (bqs.contains(bq2)) {
				bqs.remove(bq2);
			}
			bq2_rl.setVisibility(View.GONE);
			break;
		// 点击标签
		case R.id.bq3_rl:
			String bq3 = bq3_tv.getText().toString();
			if (bqs.contains(bq3)) {
				bqs.remove(bq3);
			}
			bq3_rl.setVisibility(View.GONE);
			break;
		// 点击标签
		case R.id.bq4_rl:
			String bq4 = bq4_tv.getText().toString();
			if (bqs.contains(bq4)) {
				bqs.remove(bq4);
			}
			bq4_rl.setVisibility(View.GONE);
			break;
		// 点击标签
		case R.id.bq5_rl:
			String bq5 = bq5_tv.getText().toString();
			if (bqs.contains(bq5)) {
				bqs.remove(bq5);
			}
			bq5_rl.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	/**
	 * @description:重置搜索条件
	 */
	private void resetState() {
		// TODO Auto-generated method stub
		if ("职场".equals(occation)) {
			clearOccationState();
			zc_rl.setBackgroundResource(R.drawable.textview_bg_6);
		} else if ("度假".equals(occation)) {
			clearOccationState();
			dj_rl.setBackgroundResource(R.drawable.textview_bg_6);
		} else if ("商务".equals(occation)) {
			clearOccationState();
			sw_rl.setBackgroundResource(R.drawable.textview_bg_6);
		} else if ("运动".equals(occation)) {
			clearOccationState();
			yd_rl.setBackgroundResource(R.drawable.textview_bg_6);
		} else if ("休闲聚会".equals(occation)) {
			clearOccationState();
			xx_rl.setBackgroundResource(R.drawable.textview_bg_6);
		} else {
		}
		if ("0".equals(pricemin)) {
			clearJWState();
			jw1_tv.setBackgroundResource(R.drawable.textview_bg_6);
		} else if ("100".equals(pricemin)) {
			clearJWState();
			jw2_tv.setBackgroundResource(R.drawable.textview_bg_6);
		} else if ("200".equals(pricemin)) {
			clearJWState();
			jw3_tv.setBackgroundResource(R.drawable.textview_bg_6);
		} else if ("500".equals(pricemin)) {
			clearJWState();
			jw4_tv.setBackgroundResource(R.drawable.textview_bg_6);
		} else if ("1000".equals(pricemin)) {
			clearJWState();
			jw5_tv.setBackgroundResource(R.drawable.textview_bg_6);
		}
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (categoryid.equals(list.get(i).getId())) {
					mPosition = i;
					list.get(i).setIsSelected(1);
					xlAdapter.setData(list.get(i).getXls());
					int line2 = list.get(i).getXls().size() / 3;
					int count2 = list.get(i).getXls().size() % 3;
					if (count2 > 0) {
						line2++;
					}
					RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) recyclerview2
							.getLayoutParams();
					lp2.height = 86 * line2;
					recyclerview2.setLayoutParams(lp2);

				}

			}
			plAdapter.setData(list);
			plAdapter.notifyDataSetChanged();
			xlAdapter.notifyDataSetChanged();
		}
	}

	private ArrayList<XL> bqs = new ArrayList<XL>();
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {

			// 更新5个标签内容
			case NetworkAsyncCommonDefines.UPDATE_BQ:
				Bundle bqBun = msg.getData();
				if (bqBun != null) {
					XL bq = bqBun.getParcelable("xl");
					if (bqs.contains(bq)) {
						int i = bqs.indexOf(bq);
						switch (i) {
						case 0:
							bq1_rl.setVisibility(View.GONE);
							break;
						case 1:
							bq2_rl.setVisibility(View.GONE);
							break;
						case 2:
							bq3_rl.setVisibility(View.GONE);
							break;
						case 3:
							bq4_rl.setVisibility(View.GONE);
							break;
						case 4:
							bq5_rl.setVisibility(View.GONE);
							break;
						default:
							break;
						}
						bqs.remove(bq);
					} else {
						if (bqs.size() >= 5) {
							bqs.remove(0);
							bqs.add(bq);

						} else {

							bqs.add(bq);

						}
					}
					if (bqs.size() > 0) {

						for (int i = 0; i < bqs.size(); i++) {
							switch (i) {
							case 0:
								bq1_tv.setText(bqs.get(i).getName());
								bq1_rl.setVisibility(View.VISIBLE);
								break;
							case 1:
								bq2_tv.setText(bqs.get(i).getName());
								bq2_rl.setVisibility(View.VISIBLE);
								break;
							case 2:
								bq3_tv.setText(bqs.get(i).getName());
								bq3_rl.setVisibility(View.VISIBLE);
								break;
							case 3:
								bq4_tv.setText(bqs.get(i).getName());
								bq4_rl.setVisibility(View.VISIBLE);
								break;
							case 4:
								bq5_tv.setText(bqs.get(i).getName());
								bq5_rl.setVisibility(View.VISIBLE);
								break;
							default:
								break;
							}
						}
					}
				}
				break;
			// 获取标签成功
			case NetworkAsyncCommonDefines.GET_BQ_S:
				Bundle bqdata = msg.getData();
				if (bqdata != null) {
					bqList = bqdata.getParcelableArrayList("list");

					if (bqList != null && bqList.size() > 0) {
						int line3 = bqList.size();
						LayoutParams lp3 = (LayoutParams) recyclerview3
								.getLayoutParams();
						lp3.height = 92 * line3;
						recyclerview3.setLayoutParams(lp3);
						for (int i = 0; i < bqList.size(); i++) {
							if (categoryid.equals(bqList.get(i).getId())) {

								bqList.get(i).setIsSelected(1);
								xlAdapter.setData(bqList.get(i).getXls());
								int line4 = bqList.get(i).getXls().size() / 3;
								int count4 = bqList.get(i).getXls().size() % 3;
								if (count4 > 0) {
									line4++;
								}
								LayoutParams lp4 = (LayoutParams) recyclerview4
										.getLayoutParams();
								lp4.height = 86 * line4;
								recyclerview4.setLayoutParams(lp4);
							}

						}
						bqPLAdapter.setData(bqList);
						bqPLAdapter.notifyDataSetChanged();

					} else {
					}
				}
				break;
			// 获取标签失败
			case NetworkAsyncCommonDefines.GET_BQ_F:
				break;
			// 获取服装分类成功
			case NetworkAsyncCommonDefines.GET_PL_S:
				Bundle data = msg.getData();
				if (data != null) {
					if ("1".equals(sex)) {

						list = data.getParcelableArrayList("nan");
					} else {
						list = data.getParcelableArrayList("nv");

					}
					if (list != null && list.size() > 0) {

						int line = list.size() / 3;
						int count = list.size() % 3;
						if (count > 0) {
							line++;
						}
						RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) recyclerview1
								.getLayoutParams();
						lp.height = 86 * line;
						recyclerview1.setLayoutParams(lp);
						for (int i = 0; i < list.size(); i++) {
							if (categoryid.equals(list.get(i).getId())) {
								list.get(i).setIsSelected(1);
								mPosition = i;
								xllist = list.get(i).getXls();
								if (xllist != null && xllist.size() > 0) {
									for (int j = 0; j < xllist.size(); j++) {
										if (!"".equals(cid)
												&& cid.contains(xllist.get(j)
														.getId())) {
											xllist.get(j).setIsSelected(1);
										} else {
										}
									}
								}

								int line2 = list.get(i).getXls().size() / 3;
								int count2 = list.get(i).getXls().size() % 3;
								if (count2 > 0) {
									line2++;
								}
								RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) recyclerview2
										.getLayoutParams();
								lp2.height = 86 * line2;
								recyclerview2.setLayoutParams(lp2);
							}

						}
						plAdapter.setData(list);
						plAdapter.notifyDataSetChanged();
						xlAdapter.setData(xllist);
						xlAdapter.notifyDataSetChanged();

					}
				}
				break;
			// 获取服装分类失败
			case NetworkAsyncCommonDefines.GET_PL_F:
				break;

			default:
				break;
			}
		};
	};
	//细类的标签
	private String xl="";
	//品类的标签
}
