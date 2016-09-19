package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.utils.MD5Utils;

/**
 * @description: 编辑昵称
 * @author:袁东华
 * @time:2015-8-31下午1:51:59
 */
@ContentView(R.layout.editname_layout)
public class EditNameActivity extends BaseActivity {
	private Context mContext = EditNameActivity.this;
	private ProgressBar pbLoading;
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
	/**
	 * 操作按钮
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/**
	 * 昵称
	 */
	private EditText name_et;

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击保存
		case R.id.operate_tv:
			if (isClick()) {

				String newName = name_et.getText().toString();
				if (newName == null || "".equals(newName)) {
					Toast.makeText(mContext, "请输入新昵称", Toast.LENGTH_LONG)
							.show();

				} else if (newName.equals(ManagerUtils.getInstance()
						.getUserName(mContext))) {
					Toast.makeText(mContext, "新昵称和原昵称不能相同", Toast.LENGTH_LONG)
							.show();

				} else if (ManagerUtils.getInstance().getUser_id(mContext) == null
						|| "".equals(ManagerUtils.getInstance().getUser_id(
								mContext))) {
					Toast.makeText(mContext, "请先绑定手机号", Toast.LENGTH_LONG)
							.show();
				} else if (newName.length() > 8) {
					Toast.makeText(mContext, "昵称最长为8个字符", Toast.LENGTH_SHORT)
							.show();
				} else {
					String md5 = MD5Utils.getMD5String(ManagerUtils
							.getInstance().getPassword(mContext));
					pbLoading.setVisibility(View.VISIBLE);
					UserExec.getInstance().xiuGaiUserName(mContext, mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							ManagerUtils.getInstance().getPhoneNum(mContext),
							md5, newName,
							NetworkAsyncCommonDefines.MODIFY_USERNAME_S,
							NetworkAsyncCommonDefines.MODIFY_USERNAME_F);
				}
			}
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;

		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 修改名称成功
			case NetworkAsyncCommonDefines.MODIFY_USERNAME_S:
				Toast.makeText(mContext, "昵称修改成功", Toast.LENGTH_LONG).show();
				finish();
				break;
			// 修改名称失败
			case NetworkAsyncCommonDefines.MODIFY_USERNAME_F:
				pbLoading.setVisibility(View.GONE);
				Bundle bundle = msg.getData();
				if (bundle != null) {
					// String recode = bundle.getString("recode");
					String redesc = bundle.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();

				}
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("修改昵称");
		operate_tv.setText("保存");
		operate_tv.setVisibility(View.VISIBLE);
		pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
		name_et = (EditText) findViewById(R.id.name_et);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		if (ManagerUtils.getInstance().getUserName(mContext) != null) {
			name_et.setText(ManagerUtils.getInstance().getUserName(mContext));
		}
		// name_et.addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before,
		// int count) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// // TODO Auto-generated method stub
		// // 设置新的光标所在位置
		// name_et.setSelection(s.length());
		//
		// }
		// });

	}

	private boolean isClick() {

		return pbLoading.getVisibility() == View.GONE;
	}
}
