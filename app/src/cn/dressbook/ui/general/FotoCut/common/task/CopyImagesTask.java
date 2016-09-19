package cn.dressbook.ui.general.FotoCut.common.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.SnowCommon.common.MessageWhat;
import cn.dressbook.ui.SnowCommon.common.util.MediaHelper;

public class CopyImagesTask extends AsyncTask<String, Void, String>{
	private Handler handler;
	private int startFrame;
	private int endFrame;
	
	public CopyImagesTask(Handler handler, int start, int end){
		this.handler=handler;
		this.startFrame=start;
		this.endFrame=end;
	}

	@Override
	protected String doInBackground(String... params) {
		String path=MediaHelper.copyImagesToResult(params[0], startFrame, endFrame);
		return path;
	
	}
	
	@Override
	protected void onPostExecute (String result) { 
		Message msg=new Message();
		msg.what=MessageWhat.WHAT_IMAGES_COPY;
		msg.obj=result;
		handler.sendMessage(msg);
    }  
	
	
}
