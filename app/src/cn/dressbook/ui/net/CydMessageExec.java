package cn.dressbook.ui.net;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.MessageJson;
import cn.dressbook.ui.model.CydMessage;
import cn.dressbook.ui.utils.FileSDCacher;
/**
 * @description: 消息处理
 * @author:袁东华
 * @time:2015-11-24下午3:30:58
 */
public class CydMessageExec {
	private static CydMessageExec mInstance = null;

	private CydMessageExec() {
	};

	public static CydMessageExec getInstance() {
		if (mInstance == null) {
			mInstance = new CydMessageExec();
		}
		return mInstance;
	}

	public void getMessage(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				final File folder = new File(PathCommonDefines.MESSAGE);
				if (folder.exists()) {
					File[] files = folder.listFiles();
					if (files != null && files.length > 0) {
						ArrayList<CydMessage> messageList = new ArrayList<CydMessage>();
						for (int i = 0; i < files.length; i++) {
							File file = files[i];
							if (file.exists()) {

								String messageString = FileSDCacher
										.ReadData(file);
								try {
									CydMessage cydMessage = MessageJson
											.getInstance().analysisMessage(
													messageString);
									if (cydMessage != null) {
										messageList.add(cydMessage);
									}

								} catch (Exception e) {
									// TODO Auto-generated catch block
								}
							} else {
							}
						}
						Message msg = new Message();
						Bundle bundle = new Bundle();
						bundle.putParcelableArrayList("messageList",
								messageList);
						msg.setData(bundle);
						msg.what = flag1;
						handler.sendMessage(msg);
					}else{
						handler.sendEmptyMessage(flag2);
					}
				}
			}
		}).start();
	}

}
