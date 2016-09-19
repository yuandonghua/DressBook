package cn.dressbook.ui;

import java.io.File;
import java.util.ArrayList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.TryOnCollectAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.utils.ToastUtils;


/**
 * @description: 试衣收藏界面
 * @author:袁东华
 * @time:2015-9-24下午7:14:36
 */
@ContentView(R.layout.tryoncollect)
public class TryOnCollectActivity extends BaseActivity {
	private Context mContext = TryOnCollectActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private TryOnCollectAdapter mTryOnCollectAdapter;
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
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	private String mWardrobeId;
	private ArrayList<String> mList = new ArrayList<String>();
	private ArrayList<String> mList2 = new ArrayList<String>();
	/**
	 * 提示
	 */
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("试衣收藏");
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub

		mTryOnCollectAdapter = new TryOnCollectAdapter(this, mHandler);
		recyclerview.setHasFixedSize(true);
		recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
		// 设置item之间的间隔
		// SpacesItemDecoration decoration = new SpacesItemDecoration(8);
		// recyclerview.addItemDecoration(decoration);
		recyclerview.setAdapter(mTryOnCollectAdapter);
		// 点击条目
		mTryOnCollectAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, TryOnInfo2Activity.class);
				intent.putExtra("position", position);
				intent.putStringArrayListExtra("list", mList);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		pbDialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (ManagerUtils.getInstance().isLogin(mContext)) {
					if (mList != null && mList.size() > 0) {
						mList.clear();
					}
					File tryOnFolder = new File(
							PathCommonDefines.WARDROBE_TRYON);
					if (tryOnFolder.exists()) {
						String[] fileName = tryOnFolder.list();
						if (fileName != null) {

							for (int i = 0; i < fileName.length; i++) {
								if (fileName[i].contains("_")) {
									mList.add(PathCommonDefines.WARDROBE_TRYON
											+ "/" + fileName[i]);
								} else if (!fileName[i].contains("_")
										&& fileName[i].contains(".a")) {
									mList2.add(PathCommonDefines.WARDROBE_TRYON
											+ "/" + fileName[i]);
								}
							}
							if (mList2.size() > 0) {

								for (int i = 0; i < mList2.size(); i++) {
									String name = mList2.get(i).replace(".a",
											"");
									boolean boo = false;
									for (int j = 0; j < mList.size(); j++) {
										if (mList.get(j).contains(name)) {
											boo = true;
										}
									}
									if (!boo) {
										mList.add(mList2.get(i));
									}

								}
							}
						} else {
						}

					} else {
						tryOnFolder.mkdirs();
					}
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DISPLAY_SYSC_LIST);
				}
			}
		}).start();

	}

	@Event(R.id.back_rl)
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

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			// 展示试衣收藏列表
			case NetworkAsyncCommonDefines.DISPLAY_SYSC_LIST:

				if (mList == null || mList.size() == 0) {
					hint_tv.setVisibility(View.VISIBLE);
					ToastUtils.getInstance().showToast(mContext, "暂无试衣记录", 200);
				} else {
					hint_tv.setVisibility(View.GONE);
					mTryOnCollectAdapter.setData(mList);
					mTryOnCollectAdapter.notifyDataSetChanged();
				}
				pbDialog.dismiss();
				break;
			// 从SD卡获取量身推荐失败
			case NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_F:
				break;
			// 从服务端获取量身推荐列表成功
			case NetworkAsyncCommonDefines.GET_ATTIRESCHEME_LIST_S:
				break;
			// 从服务端获取量身推荐列表成功
			case NetworkAsyncCommonDefines.GET_ATTIRESCHEME_LIST_F:

				break;
			default:
				break;
			}
		};
	};

}
