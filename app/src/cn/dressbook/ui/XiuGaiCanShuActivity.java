package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.dressbook.ui.adapter.XiuGaiCanShuAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.TiaoZhenCanShu;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @description: 修改细节
 * @author:ydh
 * @data:2016-4-21下午1:45:29
 */
@ContentView(R.layout.xiugaicanshu)
public class XiuGaiCanShuActivity extends BaseActivity {
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	@ViewInject(R.id.editText)
	private TextView editText;
	private ArrayList<TiaoZhenCanShu> xgList,
			xgList2 = new ArrayList<TiaoZhenCanShu>();
	private LinearLayoutManager mLinearLayoutManager;
	private XiuGaiCanShuAdapter xiuGaiCanShuAdapter;
	private int mPosition = -1;
	private String title;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		title_tv.setText(title);
		operate_tv.setText("确定");
		operate_tv.setVisibility(View.VISIBLE);
		xgList = intent.getParcelableArrayListExtra("xgList");
		// 添加分割线
		recyclerview
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						this).color(getResources().getColor(R.color.touming))
						.size(10).margin(0, 0).build());
		mLinearLayoutManager = new LinearLayoutManager(activity);
		recyclerview.setLayoutManager(mLinearLayoutManager);
		xiuGaiCanShuAdapter = new XiuGaiCanShuAdapter(activity, mHandler);
		xiuGaiCanShuAdapter.setData(xgList);
		recyclerview.setAdapter(xiuGaiCanShuAdapter);

		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				final String str = s.toString();
				new Thread(new Runnable() {

					@Override
					public void run() {
						synchronized (xgList2) {
							mPosition = -1;
							xiuGaiCanShuAdapter.setPosition(mPosition);
							if (!"".equals("str") && str.length() > 0
									&& xgList != null) {
								if (xgList2 != null && xgList2.size() > 0) {
									xgList2.clear();
								}
								for (TiaoZhenCanShu tzCanShu : xgList) {
									if (tzCanShu.gettitle().contains(str)
											|| tzCanShu.getcontent().contains(
													str)
											|| tzCanShu.getMaterial().contains(
															str)) {
										if (!xgList2.contains(tzCanShu)) {
											xgList2.add(tzCanShu);
										}
									}
								}
								mHandler.sendEmptyMessage(1);

							} else {
								if (xgList2 != null && xgList2.size() > 0) {
									xgList2.clear();
								}
								mHandler.sendEmptyMessage(-1);
							}
						}
					}
				}).start();

			}
		});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub

	}

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击确定
		case R.id.operate_tv:
			if (mPosition == -1) {
				Toast.makeText(activity, "请选择" + title, Toast.LENGTH_SHORT)
						.show();
				break;
			}
			if (mXuanZeListener != null) {
				mXuanZeListener.onXZ(xiuGaiCanShuAdapter.getData().get(
						mPosition));
			}
			finish();
			break;
		default:
			break;
		}

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				xiuGaiCanShuAdapter.setData(xgList2);
				xiuGaiCanShuAdapter.notifyDataSetChanged();
				break;
			case -1:
				xiuGaiCanShuAdapter.setData(xgList);
				xiuGaiCanShuAdapter.notifyDataSetChanged();
				break;
			case NetworkAsyncCommonDefines.CLICK_XIUGAI:
				Bundle data = msg.getData();
				if (data != null) {
					if (mPosition != -1) {
						xiuGaiCanShuAdapter.notifyItemChanged(mPosition);
					}
					mPosition = data.getInt("position");

				}
				break;

			default:
				break;
			}
		};

	};
	private static XuanZeListener mXuanZeListener;

	public static void setXuanZeListener(XuanZeListener xuanZeListener) {
		mXuanZeListener = xuanZeListener;
	}

	public interface XuanZeListener {
		void onXZ(TiaoZhenCanShu tzcs);
	}
}
