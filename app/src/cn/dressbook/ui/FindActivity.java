package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.ui.fragment.SearchFragment;

/**
 * 
 * 搜索的activity
 * 
 * @author 熊天富
 */
@ContentView(R.layout.activity_find)
public class FindActivity extends BaseFragmentActivity {
	@ViewInject(R.id.find_et)
	EditText findEt;
	@ViewInject(R.id.cloth1)
	TextView clothTv1;
	@ViewInject(R.id.cloth2)
	TextView clothTv2;
	@ViewInject(R.id.cloth3)
	TextView clothTv3;
	@ViewInject(R.id.cloth4)
	TextView clothTv4;
	@ViewInject(R.id.find_click_ll)
	LinearLayout findClickLl;
	@ViewInject(R.id.center_ll)
	LinearLayout centerLl;

	@ViewInject(R.id.title_tv)
	TextView titleTv;
	@ViewInject(R.id.find1)
	RelativeLayout find1;
	@ViewInject(R.id.find2)
	RelativeLayout find2;
	@ViewInject(R.id.find3)
	RelativeLayout find3;
	@ViewInject(R.id.find4)
	RelativeLayout find4;
	@ViewInject(R.id.result_fl)
	FrameLayout resultFl;
	@ViewInject(R.id.delete_find)
	ImageButton delete_find;
	private Context mContext;
	public static final int TYPE_BRAND = 1;
	public static final int TYPE_GOODS = 2;
	public static final int TYPE_COUNSELOR = 3;
	public static final int TYPE_BLOG = 4;
	private String etText;
	private InputMethodManager imm;

	/**
	 * 给搜索输入设置内容监听
	 */
	private void initEditText() {
		TextWatcher tw = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				clothTv1.setText(s.toString());
				clothTv2.setText(s.toString());
				clothTv3.setText(s.toString());
				clothTv4.setText(s.toString());
				if ("".equals(s.toString().trim())) {
					findClickLl.setVisibility(View.GONE);
					centerLl.setVisibility(View.VISIBLE);
					delete_find.setVisibility(View.INVISIBLE);

				} else {
					findClickLl.setVisibility(View.VISIBLE);
					centerLl.setVisibility(View.GONE);
					delete_find.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				findClickLl.setVisibility(View.VISIBLE);
				centerLl.setVisibility(View.GONE);
				resultFl.setVisibility(View.GONE);

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		};
		findEt.addTextChangedListener(tw);
		delete_find.getParent().requestDisallowInterceptTouchEvent(true);

	}

	@Event({ R.id.find1, R.id.back_rl, R.id.find2, R.id.find3, R.id.find4,
			R.id.delete_find })
	private void onClick(View v) {

		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			if (isFinish()) {

				finish();
			}
			break;
		case R.id.delete_find:
			findEt.setText("");
			break;
		/**
		 * 搜索品牌
		 */
		case R.id.find1:
			input(TYPE_BRAND);
			getResult(TYPE_BRAND, etText);
			break;
		/**
		 * 搜索商品
		 */
		case R.id.find2:
			input(TYPE_GOODS);
			getResult(TYPE_GOODS, etText);
			break;
		/**
		 * 点击顾问师
		 */
		case R.id.find3:
			input(TYPE_COUNSELOR);
			getResult(TYPE_COUNSELOR, etText);
			break;
		/**
		 * 搜索博客
		 */
		case R.id.find4:
			input(TYPE_BLOG);
			getResult(TYPE_BLOG, etText);
			break;

		}
	}

	/**
	 * 判断搜索内容是否为空
	 */
	private void input(int type) {
		resultFl.setVisibility(View.VISIBLE);
		findClickLl.setVisibility(View.GONE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		etText = findEt.getText().toString().trim();

	}


	/**
	 * 获取搜索结果
	 */
	private void getResult(int type, String keyWord) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.result_fl, new SearchFragment(type, keyWord));
		transaction.commit();
	}

	@Override
	protected void initView() {

		imm = (InputMethodManager) getApplicationContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		mContext = this;
		titleTv.setVisibility(View.VISIBLE);
		titleTv.setText("搜索");
		initEditText();

	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub

	}

}
