package cn.dressbook.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.ShengHuoZhaoAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.LifePhoto;
import cn.dressbook.ui.net.LifePhotosExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;
import cn.dressbook.ui.utils.BitmapTool;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.utils.UriUtils;

/**
 * @description: 生活照
 * @author:袁东华
 * @time:2015-9-22下午2:46:45
 */
@ContentView(R.layout.shenghuozhao)
public class ShengHuoZhaoActivity extends BaseActivity {
	private Context mContext = ShengHuoZhaoActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	/**
	 * 添加
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/** 标题 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	/**
	 * 提示
	 */
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;
	private String  wardrobe_id;
	private ArrayList<LifePhoto> mList;
	private ShengHuoZhaoAdapter mShengHuoZhaoAdapter;
	private boolean isShangChuan, deleteFlag;
	private int deletePosition;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("生活照");
		operate_tv.setText("添加");
		operate_tv.setVisibility(View.VISIBLE);
		LinearLayoutManager llm = new LinearLayoutManager(mContext);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerview.setLayoutManager(llm);
		mShengHuoZhaoAdapter = new ShengHuoZhaoAdapter(mContext, mHandler);
		recyclerview.setAdapter(mShengHuoZhaoAdapter);
		// 添加分割线
		recyclerview
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						this)
						.color(getResources().getColor(R.color.touming))
						.size(getResources().getDimensionPixelSize(
								R.dimen.divider5))
						.margin(getResources().getDimensionPixelSize(
								R.dimen.leftmargin),
								getResources().getDimensionPixelSize(
										R.dimen.rightmargin)).build());
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				if (wardrobe_id != null) {
					LifePhotosExec.getInstance().getLifePhoto(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							wardrobe_id,
							NetworkAsyncCommonDefines.GET_LIFEPHOTO_S,
							NetworkAsyncCommonDefines.GET_LIFEPHOTO_F);
				} else {
					hint_tv.setVisibility(View.VISIBLE);
				}

			}
		});
		mShengHuoZhaoAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				LogUtil.e("position:" + position);
				if (isFinish()) {
					pbDialog.show();
					deletePosition = position;
					// 删除生活照
					LifePhotosExec.getInstance().deleteLifePhoto(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							wardrobe_id, mList.get(position).getId(),
							NetworkAsyncCommonDefines.delete_LIFEPHOTO_S,
							NetworkAsyncCommonDefines.delete_LIFEPHOTO_F);
				}
			}
		});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		File paizhao = new File(PathCommonDefines.PAIZHAO);
		if (!paizhao.exists()) {
			paizhao.mkdirs();
		}
		wardrobe_id = mSharedPreferenceUtils.getWardrobeID(mContext);
		if (wardrobe_id != null) {
			LifePhotosExec.getInstance().getLifePhoto(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext),
					wardrobe_id, NetworkAsyncCommonDefines.GET_LIFEPHOTO_S,
					NetworkAsyncCommonDefines.GET_LIFEPHOTO_F);
		} else {
			hint_tv.setVisibility(View.VISIBLE);
		}
	}

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击添加
		case R.id.operate_tv:
			if (isFinish()) {
				if (wardrobe_id != null) {

					if (mList == null || mList.size() < 3) {
						openXiangCe();
					} else {
						Toast.makeText(mContext, "最多添加三张生活照",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Intent loginActivity = new Intent(mContext,
							LoginActivity.class);
					startActivity(loginActivity);
				}

			} else {

			}
			break;
		default:
			break;
		}
	}

	public void openXiangCe() {
		// 从相簿中获得照片
		Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
		mIntent.addCategory(Intent.CATEGORY_OPENABLE);
		mIntent.setType("image/*");
		startActivityForResult(mIntent, NetworkAsyncCommonDefines.ENTER_ALBUM);
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		// 进入相册返回
		case NetworkAsyncCommonDefines.ENTER_ALBUM:
			if (data != null) {
				Uri uri = data.getData(); // 返回的是一个Uri
				if (uri != null) {
					String path = UriUtils.getInstance().getImageAbsolutePath(
							this, uri);
					if (path == null || path.equals("")) {
						Toast.makeText(mContext, "没找到图片", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					File s = new File(path);
					if (s != null && s.exists()) {
						String mUrl1 = PathCommonDefines.PAIZHAO
								+ "/image1.jpg";
						dealImage(path, mUrl1);
						pbDialog.show();
						LifePhotosExec
								.getInstance()
								.uploadLifePhotos(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										wardrobe_id,
										mUrl1,
										NetworkAsyncCommonDefines.UPLOAD_LIFEPHOTOS_S,
										NetworkAsyncCommonDefines.UPLOAD_LIFEPHOTOS_F);
					}

				} else {
					Toast.makeText(mContext, "没找到图片", Toast.LENGTH_SHORT)
							.show();
				}
			}

			break;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 删除生活照成功
			case NetworkAsyncCommonDefines.delete_LIFEPHOTO_S:
				LifePhotosExec.getInstance().getLifePhoto(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						wardrobe_id, NetworkAsyncCommonDefines.GET_LIFEPHOTO_S,
						NetworkAsyncCommonDefines.GET_LIFEPHOTO_F);
				break;
			// 删除生活照失败
			case NetworkAsyncCommonDefines.delete_LIFEPHOTO_F:
				Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
				deleteFlag = false;
				LifePhotosExec.getInstance().getLifePhoto(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						wardrobe_id, NetworkAsyncCommonDefines.GET_LIFEPHOTO_S,
						NetworkAsyncCommonDefines.GET_LIFEPHOTO_F);
				break;
			// 获取生活照成功
			case NetworkAsyncCommonDefines.GET_LIFEPHOTO_S:
				Bundle listData = msg.getData();
				if (listData != null) {
					mList = listData.getParcelableArrayList("list");
					if (mList == null || mList.size() == 0) {
						hint_tv.setVisibility(View.VISIBLE);
					} else {
						hint_tv.setVisibility(View.GONE);
					}
					if (isShangChuan) {
						mShengHuoZhaoAdapter
								.notifyItemInserted(mList.size() - 1);
						mShengHuoZhaoAdapter.setData(mList);
						isShangChuan = false;
					}
					if (deleteFlag) {
						mShengHuoZhaoAdapter.notifyItemRemoved(deletePosition);
						mShengHuoZhaoAdapter.setData(mList);
						deleteFlag = false;
					} else {
						mShengHuoZhaoAdapter.setData(mList);
						mShengHuoZhaoAdapter.notifyDataSetChanged();
					}
				}
				pbDialog.dismiss();
				swiperefreshlayout.setRefreshing(false);
				break;
			// 获取生活照失败
			case NetworkAsyncCommonDefines.GET_LIFEPHOTO_F:

				break;
			// 上传生活照成功
			case NetworkAsyncCommonDefines.UPLOAD_LIFEPHOTOS_S:
				isShangChuan = true;
				LifePhotosExec.getInstance().getLifePhoto(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						wardrobe_id, NetworkAsyncCommonDefines.GET_LIFEPHOTO_S,
						NetworkAsyncCommonDefines.GET_LIFEPHOTO_F);
				break;
			// 上传生活照失败
			case NetworkAsyncCommonDefines.UPLOAD_LIFEPHOTOS_F:
				isShangChuan = false;
				Toast.makeText(mContext, "上传失败", Toast.LENGTH_SHORT).show();
				LifePhotosExec.getInstance().getLifePhoto(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						wardrobe_id, NetworkAsyncCommonDefines.GET_LIFEPHOTO_S,
						NetworkAsyncCommonDefines.GET_LIFEPHOTO_F);
				break;
			default:
				break;
			}
		};

	};

	/**
	 * @description:缩放图片
	 */
	private void dealImage(String fileName, String fileName2) {
		// TODO Auto-generated method stub
		try {
			Bitmap bitmap = BitmapTool.getInstance().createImage(fileName);
			if (bitmap != null) {
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				if (width > 800) {
					float scal = width / 800;
					height = (int) (height / scal);
					width = 800;

				}
				File file = new File(fileName2);
				if (file.exists()) {
					file.delete();
				}
				Bitmap bitmap2 = ThumbnailUtils.extractThumbnail(bitmap, width,
						height);
				File file2 = new File(fileName2);
				FileOutputStream out = new FileOutputStream(file2);
				if (bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
					if (bitmap != null) {
						bitmap.recycle();
					}
					if (bitmap2 != null) {
						bitmap2.recycle();
					}
					out.flush();
					out.close();
					if (file2.exists()) {
					} else {
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		FileSDCacher.deleteDirectory2(new File(PathCommonDefines.PAIZHAO));
		super.finish();
	}
}
