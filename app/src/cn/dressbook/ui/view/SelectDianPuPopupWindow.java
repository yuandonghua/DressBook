package cn.dressbook.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.ui.R;

/**
 * @description 选择店铺
 * @author 袁东华
 * @date 2016-3-17
 */
public class SelectDianPuPopupWindow extends PopupWindow {

	public TextView cancle_tv, ok_tv, btn_cancel,  title_tv;
	public View mMenuView;
	public RelativeLayout dp_rl;
	public DianPuPicker dpPicker;

	public SelectDianPuPopupWindow(Activity context,
			OnClickListener itemsOnClick) {
		super(context);
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
		mMenuView = inflater.inflate(R.layout.selectdianpu_popuwindow, null);
		cancle_tv = (TextView) mMenuView.findViewById(R.id.cancle_tv);
		cancle_tv.setOnClickListener(itemsOnClick);
		ok_tv = (TextView) mMenuView.findViewById(R.id.ok_tv);
		ok_tv.setOnClickListener(itemsOnClick);
		title_tv = (TextView) mMenuView.findViewById(R.id.title_tv);
		dp_rl = (RelativeLayout) mMenuView.findViewById(R.id.dp_rl);
		dpPicker = (DianPuPicker) mMenuView.findViewById(R.id.dpPicker);
	}



}
