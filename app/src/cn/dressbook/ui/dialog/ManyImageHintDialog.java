package cn.dressbook.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.adapter.ManyImageHintAdapter;
import cn.dressbook.ui.view.MyViewPager;

/**
 * @description: 多张图片提示对话框
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-6-15 下午3:39:27
 */
public class ManyImageHintDialog extends Dialog implements
		View.OnClickListener {
	private Context mContext;
	/**
	 * 说明提示
	 */
	private ImageView imagehint_iv;
	/**
	 * 明白按钮
	 */
	private ImageView clear_iv;
	private MyViewPager myviewpager;
	private List<View> views;
	private ManyImageHintAdapter mManyImageHintAdapter;
	private Window window = null;
	/**
	 * 向左
	 */
	private ImageView left_iv;
	/**
	 * 向右
	 */
	private ImageView right_iv;
	/**
	 * 当前索引
	 */
	private int mPosition;

	public ManyImageHintDialog(Context context, int theme) {
		super(context, R.style.Translucent_NoTitle);
		// TODO Auto-generated constructor stub

	}

	// 设置窗口显示
	public void windowDeploy(int x, int y) {
		window = getWindow(); // 得到对话框
		window.setWindowAnimations(R.style.dialogWindowAnim1); // 设置窗口弹出动画
		window.setBackgroundDrawableResource(R.color.touming); // 设置对话框背景为透明
		WindowManager.LayoutParams wl1 = window.getAttributes();
		// 根据x，y坐标设置窗口需要显示的位置
		// wl1.x = x; //x小于0左移，大于0右移
		// wl1.y = y; //y小于0上移，大于0下移
		// wl.alpha = 0.6f; //设置透明度
		// wl.gravity = Gravity.BOTTOM; //设置重力
		window.setAttributes(wl1);
	}

	public ManyImageHintDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.setContentView(R.layout.manyimagehint_dialog);
		// setCanceledOnTouchOutside(true);
		initView();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
	}

	public void initData(int resource) {
		// TODO Auto-generated method stub
		if (imagehint_iv != null) {
			imagehint_iv.setImageResource(resource);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		// imagehint_iv.setOnClickListener(this);
		left_iv = (ImageView) findViewById(R.id.left_iv);
		left_iv.setOnClickListener(this);
		right_iv = (ImageView) findViewById(R.id.right_iv);
		right_iv.setOnClickListener(this);
		clear_iv = (ImageView) findViewById(R.id.clear_iv);
		clear_iv.setOnClickListener(this);
		myviewpager = (MyViewPager) findViewById(R.id.myviewpager);
		views = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(mContext);
		// 初始化引导图片列表
		views.add(inflater.inflate(R.layout.imagehint_1_layout, null));
		views.add(inflater.inflate(R.layout.imagehint_2_layout, null));
		views.add(inflater.inflate(R.layout.imagehint_3_layout, null));
		views.add(inflater.inflate(R.layout.imagehint_4_layout, null));
		views.add(inflater.inflate(R.layout.imagehint_5_layout, null));
		mManyImageHintAdapter = new ManyImageHintAdapter(mContext, views);
		myviewpager.setAdapter(mManyImageHintAdapter);
		// 绑定回调
		myviewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				mPosition = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * 
	 * @Description TODO
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击明白
		case R.id.clear_iv:
			dismiss();
			break;
		// 点击向右
		case R.id.right_iv:
			if (mPosition + 1 <= views.size() - 1) {
				mPosition++;
				myviewpager.setCurrentItem(mPosition, true);
			}
			break;
		// 点击向左
		case R.id.left_iv:
			if (mPosition - 1 >= 0) {
				mPosition--;
				myviewpager.setCurrentItem(mPosition, true);
			}
			break;
		default:
			break;
		}
	}
}
