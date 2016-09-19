package cn.dressbook.ui.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.adapter.SelectImageAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;


/**
 * @description:发送消息view
 * @author:袁东华
 * @time:2015-10-20下午4:39:41
 */
public class SendPopupWindow extends PopupWindow {
	private Context mContext;
	public String words;
	private SelectImageAdapter mSelectImageAdapter;
	public ArrayList<String> list = new ArrayList<String>();
	private ArrayList<String> list2 = new ArrayList<String>();
	private RecyclerView recyclerview;
	private ImageView tp_iv, bq_iv;
	private EditText xx_et;
	private TextView fs_tv;
	private ViewPager viewpager;
	private InputMethodManager manager;
	private List<String> reslist;
	private Drawable[] micImages;
	private int chatType;
	private File cameraFile;
	static int resendPos;
	public View mMenuView;
	private Handler mHandler;

	public SendPopupWindow(Activity context, OnClickListener itemsOnClick,
			Handler mHandler) {
		super(context);
		mContext = context;
		this.mHandler = mHandler;
		initView(context, itemsOnClick);

		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为透明
		ColorDrawable dw = new ColorDrawable(context.getResources().getColor(
				R.color.touming));
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.popu_rl).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}

	private void initView(Activity context, OnClickListener itemsOnClick) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.send_popuwindow, null);
		recyclerview = (RecyclerView) mMenuView.findViewById(R.id.recyclerview);
		mSelectImageAdapter = new SelectImageAdapter();
		getImageData();
		mSelectImageAdapter.setData(list);
		recyclerview.setHasFixedSize(true);
		recyclerview.setLayoutManager(new GridLayoutManager(mContext, 5));
		recyclerview.setAdapter(mSelectImageAdapter);
		xx_et = (EditText) mMenuView.findViewById(R.id.xx_et);
		fs_tv = (TextView) mMenuView.findViewById(R.id.fs_tv);
		viewpager = (ViewPager) mMenuView.findViewById(R.id.viewpager);
		tp_iv = (ImageView) mMenuView.findViewById(R.id.tp_iv);
		bq_iv = (ImageView) mMenuView.findViewById(R.id.bq_iv);
		// 点击发送
		fs_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				words = xx_et.getEditableText().toString();
				if (words != null) {
					if (words != null) {
						words = words.trim().replaceAll("\\s", "");
						words = words.replaceAll("\t", "");
						words = words.replaceAll("\r", "");
						words = words.replaceAll("\n", "");
					}
				}
				if (words.length() < 2 || words.length() > 144) {
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putString("hint", "请输入2-144个字符");
					msg.setData(data);
					msg.what = NetworkAsyncCommonDefines.PL_HINT;
					mHandler.sendMessage(msg);
					return;
				}
				mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.SEND_COMMENT);

			}
		});
		// 点击图片
		tp_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				recyclerview.setVisibility(View.VISIBLE);
				viewpager.setVisibility(View.GONE);
			}
		});
		// 点击表情
		bq_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				recyclerview.setVisibility(View.GONE);
				viewpager.setVisibility(View.VISIBLE);
			}
		});
		mSelectImageAdapter
				.setOnItemClickListener(new cn.dressbook.ui.listener.OnItemClickListener() {

					@Override
					public void onItemClick(View view, int position) {
						// TODO Auto-generated method stub
						if (position == 0) {
							// 拍照
							for (int i = 2; i < list2.size(); i++) {
								File file = new File(list2.get(i));
								if (!file.exists()) {
									fileName = list2.get(i);
									i = list2.size();
									toTakePic(file);
								} else {
									if (i == list2.size() - 1) {
										mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.ADD_IMAGE_F);
									}
								}
							}

						} else if (position == 1) {
							// 相册
							for (int i = 2; i < list2.size(); i++) {
								File file = new File(list2.get(i));
								if (!file.exists()) {
									fileName = list2.get(i);
									i = list2.size();
									xiangCe();
								} else {
									if (i == list2.size() - 1) {
										mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.ADD_IMAGE_F);
									}
								}
							}
						} else {
							if (list.size() > position) {
								File file = new File(list.get(position));
								if (file.exists()) {
									file.delete();
								}
								list.remove(position);
								mSelectImageAdapter.notifyItemRemoved(position);
							}
						}
					}
				});
		// // 表情list
		// reslist = getExpressionRes(35);
		// // 初始化表情viewpager
		// List<View> views = new ArrayList<View>();
		// View gv1 = getGridChildView(1);
		// View gv2 = getGridChildView(2);
		// views.add(gv1);
		// views.add(gv2);
		// viewpager.setAdapter(new ExpressionPagerAdapter(views));
		xx_et.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// if (hasFocus) {
				// edittext_layout
				// .setBackgroundResource(R.drawable.input_bar_bg_active);
				// } else {
				// edittext_layout
				// .setBackgroundResource(R.drawable.input_bar_bg_normal);
				// }

			}
		});
		// xx_et.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // edittext_layout
		// // .setBackgroundResource(R.drawable.input_bar_bg_active);
		// // more.setVisibility(View.GONE);
		// // iv_emoticons_normal.setVisibility(View.VISIBLE);
		// // expressionContainer.setVisibility(View.GONE);
		// // btnContainer.setVisibility(View.GONE);
		// }
		// });
		// 监听文字框
		xx_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (xx_et.getText().toString().length() == 144) {
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putString("hint", "最多输入144个字符");
					msg.setData(data);
					msg.what = NetworkAsyncCommonDefines.PL_HINT;
					mHandler.sendMessage(msg);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		// xx_et.setOnEditorActionListener(new OnEditorActionListener() {
		//
		// @Override
		// public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)
		// {
		// // TODO Auto-generated method stub
		// if (arg1 == EditorInfo.IME_ACTION_UNSPECIFIED) {
		// // String s = xx_et.getText().toString();
		// // sendText(s);
		// // xx_et.setText("");
		// // Toast.makeText(ChatActivity.this, "你点了软键盘回车按钮",
		// // Toast.LENGTH_SHORT).show();
		// }
		//
		// return false;
		// }
		//
		// });
	}

	private void getImageData() {
		// TODO Auto-generated method stub
		list.add("pz");
		list.add("add");
		list2.add("pz");
		list2.add("add");
		list2.add(PathCommonDefines.PAIZHAO + "/image1.jpg");
		list2.add(PathCommonDefines.PAIZHAO + "/image2.jpg");
		list2.add(PathCommonDefines.PAIZHAO + "/image3.jpg");
		list2.add(PathCommonDefines.PAIZHAO + "/image4.jpg");
		list2.add(PathCommonDefines.PAIZHAO + "/image5.jpg");
		list2.add(PathCommonDefines.PAIZHAO + "/image6.jpg");
		list2.add(PathCommonDefines.PAIZHAO + "/image7.jpg");
		list2.add(PathCommonDefines.PAIZHAO + "/image8.jpg");
		list2.add(PathCommonDefines.PAIZHAO + "/image9.jpg");
	}

	public List<String> getExpressionRes(int getSum) {
		List<String> reslist = new ArrayList<String>();
		for (int x = 1; x <= getSum; x++) {
			String filename = "ee_" + x;

			reslist.add(filename);

		}
		return reslist;

	}

	

	/**
	 * 用来标识请求照相功能
	 */
	private static final int CAMERA_WITH_DATA = 2015;
	/**
	 * 拍照的照片存储位置
	 */
	private File mPhotoDir;
	/**
	 * 照相机拍照得到的图片
	 */
	private File mCameraHead;

	/**
	 * 创建照片目录，调用相机
	 * 
	 */
	protected void toTakePic(File file) {
		try {
			mPhotoDir = new File(PathCommonDefines.PAIZHAO);
			if (mPhotoDir.exists()) {
			} else {
				boolean iscreat = mPhotoDir.mkdirs();// 创建照片的存储目录

			}

			final Intent intent = getTakePickIntent(file);
			((Activity) mContext).startActivityForResult(intent,
					CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
		}
	}

	public String fileName;

	/**
	 * 获取图片的intent
	 * 
	 * @param f
	 * @return
	 */
	public Intent getTakePickIntent(File file) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		return intent;
	}

	public ArrayList<String> getList() {

		return list;
	}

	public void refreshData() {
		// TODO Auto-generated method stub
		if (list.size() < list2.size()) {

			for (int i = 2; i < list2.size(); i++) {
				if (!list.contains(list2.get(i))) {
					File file = new File(list2.get(i));
					if (file.exists()) {
						list.add(list2.get(i));
					} else {
					}
				}
			}
			mSelectImageAdapter.setData(list);
			mSelectImageAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * @description:打开相册
	 */
	private void xiangCe() {
		// TODO Auto-generated method stub
		// 从相簿中获得照片
		Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
		mIntent.addCategory(Intent.CATEGORY_OPENABLE);
		mIntent.setType("image/*");
		((Activity) mContext).startActivityForResult(mIntent,
				NetworkAsyncCommonDefines.ENTER_ALBUM);
	}
}
