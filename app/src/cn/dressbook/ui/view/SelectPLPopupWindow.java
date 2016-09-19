package cn.dressbook.ui.view;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.adapter.PLAdapter;
import cn.dressbook.ui.adapter.XLAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.PL;

public class SelectPLPopupWindow extends PopupWindow {
	private ArrayList<PL> nanList = new ArrayList<PL>();
	private ArrayList<PL> nvList = new ArrayList<PL>();
	private Handler mhandler;
	private Context mContext;
	private PLAdapter plAdapter;
	private XLAdapter xlAdapter;
	public int sex = 1;
	public int position1;
	public ArrayList<String> position2 = new ArrayList<String>();
	/**
	 * 确定按钮
	 */
	@ViewInject(R.id.ok_tv)
	private TextView ok_tv;
	/**
	 * 男装
	 */
	@ViewInject(R.id.nz_tv)
	private TextView nz_tv;
	/**
	 * 女装
	 */
	@ViewInject(R.id.nvz_tv)
	private TextView nvz_tv;
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
	private View view;

	public SelectPLPopupWindow(Activity context, Handler handler) {
		super(context);
		mContext = context;
		mhandler = handler;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.selectpl_popuwindow, null);
		// 设置SelectPicPopupWindow的View
		this.setContentView(view);
		x.view().inject(this,view);

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
		// view添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		view.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = view.findViewById(R.id.popu_rl).getTop();
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


	@Event({ R.id.nz_tv, R.id.nvz_tv, R.id.ok_tv })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_tv:
			if (xlAdapter != null) {
				position2 = xlAdapter.getPosition();
			}
			mhandler.sendEmptyMessage(NetworkAsyncCommonDefines.SELECT_PL);
			break;
		// 点击男装
		case R.id.nz_tv:
			plAdapter.setData(null);
			plAdapter.notifyDataSetChanged();
			sex = 1;
			position1 = 0;
			position2 = null;
			nz_tv.setBackgroundResource(R.drawable.textview_bg_6);
			nvz_tv.setBackgroundResource(R.drawable.textview_bg_5);
			plAdapter.setData(nanList);
			plAdapter.notifyDataSetChanged();
			break;
		// 点击女装
		case R.id.nvz_tv:
			plAdapter.setData(null);
			plAdapter.notifyDataSetChanged();
			sex = 2;
			position1 = 0;
			position2 = null;
			nvz_tv.setBackgroundResource(R.drawable.textview_bg_6);
			nz_tv.setBackgroundResource(R.drawable.textview_bg_5);
			nvList.get(0).setIsSelected(1);
			plAdapter.setData(nvList);
			plAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	private int plPosition;

	public void setData(ArrayList<PL> nanList, ArrayList<PL> nvList, int sex) {
		this.sex = sex;
		this.nanList = nanList;
		this.nvList = nvList;
		if (plAdapter == null) {

			plAdapter = new PLAdapter();

			if (sex == 1 && nanList != null && nanList.size() > 0) {
				sex = 1;
				position1 = 0;
				position2 = null;
				nanList.get(0).setIsSelected(1);
				nz_tv.setBackgroundResource(R.drawable.textview_bg_6);
				nvz_tv.setBackgroundResource(R.drawable.textview_bg_5);
				plAdapter.setData(nanList);
				plAdapter.notifyDataSetChanged();
			} else if (sex == 2 && nvList != null && nvList.size() > 0) {
				sex = 2;
				position1 = 0;
				position2 = null;
				nvList.get(0).setIsSelected(1);
				nvz_tv.setBackgroundResource(R.drawable.textview_bg_6);
				nz_tv.setBackgroundResource(R.drawable.textview_bg_5);
				plAdapter.setData(nvList);
				plAdapter.notifyDataSetChanged();
			}

			recyclerview1.setLayoutManager(new GridLayoutManager(mContext, 3));
			recyclerview1.setAdapter(plAdapter);

			plAdapter.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(View view, int position) {
					// TODO Auto-generated method stub
					position1 = position;
					ArrayList<PL> list = plAdapter.getData();
					for (int i = 0; i < list.size(); i++) {
						if (i == position) {
							list.get(i).setIsSelected(1);
						} else {
							list.get(i).setIsSelected(0);
						}
					}
					plAdapter.setData(list);
					plAdapter.notifyDataSetChanged();
					xlAdapter.setData(list.get(position).getXls());
					xlAdapter.notifyDataSetChanged();
				}
			});

		} else {
			plAdapter.notifyDataSetChanged();
		}
		if (xlAdapter == null) {

			xlAdapter = new XLAdapter();

			if (nanList != null && nanList.size() > 0) {
				xlAdapter.setData(nanList.get(0).getXls());
			}

			recyclerview2.setLayoutManager(new GridLayoutManager(mContext, 3));
			recyclerview2.setAdapter(xlAdapter);

			// xlAdapter.setOnItemClickListener(new OnItemClickListener() {
			//
			// @Override
			// public void onItemClick(View view, int position) {
			// // TODO Auto-generated method stub
			// position2 = position;
			// ArrayList<XL> list = xlAdapter.getData();
			// for (int i = 0; i < list.size(); i++) {
			// if (i == position) {
			// list.get(i).setIsSelected(1);
			// } else {
			// list.get(i).setIsSelected(0);
			// }
			// }
			// xlAdapter.setData(list);
			// xlAdapter.notifyDataSetChanged();
			// }
			// });

		} else {
			xlAdapter.notifyDataSetChanged();
		}

	}
}
