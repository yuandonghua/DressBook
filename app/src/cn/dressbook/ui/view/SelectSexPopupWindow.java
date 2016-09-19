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
 * @description: 选择性别弹出框
 * @author:袁东华
 * @time:2015-9-21上午9:25:25
 */
public class SelectSexPopupWindow extends PopupWindow {

	public TextView cancle_tv, ok_tv, btn_cancel, man_tv, woman_tv,title_tv;
	public View mMenuView;
	public ImageView line_1, line_3;
	public RelativeLayout birthday_rl, height_rl, weight_rl,sex_rl;
	public BirthdayPicker birthdaypicker;
	public HeightPicker heightpicker;
	public WeightPicker weightpicker;
	public SexPicker sexpicker;
	public SelectSexPopupWindow(Activity context, OnClickListener itemsOnClick) {
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
		mMenuView = inflater.inflate(R.layout.selectsex_popuwindow, null);
		cancle_tv = (TextView) mMenuView.findViewById(R.id.cancle_tv);
		cancle_tv.setOnClickListener(itemsOnClick);
		ok_tv = (TextView) mMenuView.findViewById(R.id.ok_tv);
		ok_tv.setOnClickListener(itemsOnClick);
		title_tv = (TextView) mMenuView.findViewById(R.id.title_tv);
		// ////////////////////////////性别//////////////////////////////////////
		line_1 = (ImageView) mMenuView.findViewById(R.id.line_1);
		line_3 = (ImageView) mMenuView.findViewById(R.id.line_3);
		sex_rl = (RelativeLayout) mMenuView.findViewById(R.id.sex_rl);
		sexpicker= (SexPicker) mMenuView.findViewById(R.id.sexpicker);
		// ////////////////////////////生日//////////////////////////////////////
		birthday_rl = (RelativeLayout) mMenuView.findViewById(R.id.birthday_rl);
		birthdaypicker= (BirthdayPicker) mMenuView.findViewById(R.id.birthdaypicker);
		
		// ////////////////////////////身高//////////////////////////////////////
		height_rl = (RelativeLayout) mMenuView.findViewById(R.id.height_rl);
		heightpicker= (HeightPicker) mMenuView.findViewById(R.id.heightpicker);
		// ////////////////////////////体重//////////////////////////////////////
		weight_rl = (RelativeLayout) mMenuView.findViewById(R.id.weight_rl);
		weightpicker= (WeightPicker) mMenuView.findViewById(R.id.weightpicker);
	}

	public void setContentShow(int i) {
		sex_rl.setVisibility(View.GONE);
		birthday_rl.setVisibility(View.GONE);
		height_rl.setVisibility(View.GONE);
		weight_rl.setVisibility(View.GONE);
		switch (i) {
		// 性别
		case 0:
			sex_rl.setVisibility(View.VISIBLE);
			title_tv.setText("性别");
			break;
		// 生日
		case 1:
			birthday_rl.setVisibility(View.VISIBLE);
			title_tv.setText("生日");
			break;
		// 身高
		case 2:
			height_rl.setVisibility(View.VISIBLE);
			title_tv.setText("身高");
			break;
		// 体重
		case 3:
			weight_rl.setVisibility(View.VISIBLE);
			title_tv.setText("体重");
			break;

		default:
			break;
		}

	}

}
