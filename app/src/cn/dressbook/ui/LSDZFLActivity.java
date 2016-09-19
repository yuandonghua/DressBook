package cn.dressbook.ui;

import java.util.ArrayList;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.dressbook.ui.adapter.LSDZFLAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.LSDZFL;
import cn.dressbook.ui.net.LSDZExec;
import cn.dressbook.ui.recyclerview.FullyGridLayoutManager;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description 量身定制分类界面
 * @author 袁东华
 * @date 2016-1-22
 */
@ContentView(R.layout.lsdzfl)
public class LSDZFLActivity extends BaseActivity {
	private Context mContext = LSDZFLActivity.this;
	private ArrayList<LSDZFL> mList1 = new ArrayList<LSDZFL>();
	private ArrayList<LSDZFL> mList2 = new ArrayList<LSDZFL>();
	private LSDZFLAdapter mLSDZFLAdapter1;
	private LSDZFLAdapter mLSDZFLAdapter2;
	@ViewInject(R.id.recyclerview1)
	private RecyclerView recyclerview1;
	@ViewInject(R.id.recyclerview2)
	private RecyclerView recyclerview2;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 男
	 */
	@ViewInject(R.id.nan_ll)
	private LinearLayout nan_ll;
	/**
	 * 女
	 */
	@ViewInject(R.id.nv_ll)
	private LinearLayout nv_ll;
	private FullyGridLayoutManager mFullyGridLayoutManager;
	private String sex="0";

	@Override
	protected void initView() {
		title_tv.setText("定制商品分类");

	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		sex=getIntent().getStringExtra("sex");
		if(sex==null){
			sex="0";
		}
		mLSDZFLAdapter1 = new LSDZFLAdapter(LSDZFLActivity.this, mHandler);
		mFullyGridLayoutManager = new FullyGridLayoutManager(mContext, 4);
		recyclerview1.setLayoutManager(mFullyGridLayoutManager);
		recyclerview1.setAdapter(mLSDZFLAdapter1);
		mLSDZFLAdapter2 = new LSDZFLAdapter(LSDZFLActivity.this, mHandler);
		mFullyGridLayoutManager = new FullyGridLayoutManager(mContext, 4);
		recyclerview2.setLayoutManager(mFullyGridLayoutManager);
		recyclerview2.setAdapter(mLSDZFLAdapter2);
		// 获取量身定制分类列表
		LSDZExec.getInstance().getLSDZFLList(mHandler, sex,
				NetworkAsyncCommonDefines.GET_LSDZ_LIST_S,
				NetworkAsyncCommonDefines.GET_LSDZ_LIST_F);
		// 选择男装
		mLSDZFLAdapter1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, ZldzActivity.class);
				intent.putExtra("cls_id", mList1.get(position).getDzCls_id());
				intent.putExtra("title", mList1.get(position).getDzCls_name());
				startActivity(intent);
				finish();
			}
		});
		// 选择女装
		mLSDZFLAdapter2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, ZldzActivity.class);
				intent.putExtra("cls_id", mList2.get(position).getDzCls_id());
				intent.putExtra("title", mList2.get(position).getDzCls_name());
				startActivity(intent);
				finish();
			}
		});
	}

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;

		default:
			break;
		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取量身定制列表成功
			case NetworkAsyncCommonDefines.GET_LSDZ_LIST_S:
				Bundle myxData = msg.getData();
				if (myxData != null) {
					ArrayList<LSDZFL> list = myxData
							.getParcelableArrayList("list");
					LogUtil.e("分类个数:" + list.size());
					if (mList1 != null) {
						mList1.clear();
					}
					if (mList1 == null) {
						mList1 = new ArrayList<LSDZFL>();
					}
					if (mList2 != null) {
						mList2.clear();
					}
					if (mList2 == null) {
						mList2 = new ArrayList<LSDZFL>();
					}
					if (list != null) {
						LogUtil.e("sex:" + sex);
						if ("1".equals(sex)) {
							nv_ll.setVisibility(View.GONE);
							nan_ll.setVisibility(View.VISIBLE);
							mList1.addAll(list);
							mLSDZFLAdapter1.setData(mList1);
							mLSDZFLAdapter1.notifyDataSetChanged();
						} else if ("2".equals(sex)) {
							nv_ll.setVisibility(View.VISIBLE);
							nan_ll.setVisibility(View.GONE);
							mList2.addAll(list);
							mLSDZFLAdapter2.setData(mList2);
							mLSDZFLAdapter2.notifyDataSetChanged();
						} else {
							nv_ll.setVisibility(View.VISIBLE);
							nan_ll.setVisibility(View.VISIBLE);
							for (LSDZFL lsdzfl : list) {
								if ("1".equals(lsdzfl.getDzCls_sex())) {
									mList1.add(lsdzfl);
								} else {
									mList2.add(lsdzfl);
								}
							}
							mLSDZFLAdapter1.setData(mList1);
							mLSDZFLAdapter1.notifyDataSetChanged();
							mLSDZFLAdapter2.setData(mList2);
							mLSDZFLAdapter2.notifyDataSetChanged();
						}

					}
				}
				break;
			// 获取量身定制列表失败
			case NetworkAsyncCommonDefines.GET_LSDZ_LIST_F:

				break;

			default:
				break;
			}
		}

	};

}
