package cn.dressbook.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @description: 聚合后的会话列表
 * @author:袁东华
 * @time:2015-8-24上午10:43:03
 */
public class SubConversationListActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subconversationlist); // 加载 conversationlist
	}
}
