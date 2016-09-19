package cn.dressbook.ui.general.FotoCut.common.task;

import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.dressbook.ui.SnowCommon.common.MessageWhat;
import cn.dressbook.ui.SnowCommon.common.util.CameraHelper;

public class CameraTakenTask extends AsyncTask<byte[], Boolean, Boolean>{
	private Camera camera;
	private String filePath;
	private Handler handler;
	private int count;
	private boolean isBackCamera=true;
	
	public CameraTakenTask(int count, Handler handler, Camera arg1, String filePath, boolean isBack){
		this.camera=arg1;
		this.filePath=filePath;
		this.handler=handler;
		this.count=count;
		this.isBackCamera=isBack;
	}
	

	@Override
	protected Boolean doInBackground(byte[]... params) {
		try{
			CameraHelper.onPreviewFrame(params[0], camera, filePath, isBackCamera);
		}
		catch(Exception e){
			Log.e("Save Photo", "Fail to save picture:"+e.getMessage());
			return false;
		}
		return true;
	}
	@Override
	protected void onPostExecute (Boolean isSucceed) { 
		Message msg=new Message();
		msg.what=MessageWhat.WHAT_CAMERA_SAVE_TASK;
		msg.obj=count;
		handler.sendMessage(msg);
    }  
}
