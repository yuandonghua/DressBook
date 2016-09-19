package cn.dressbook.ui.general.FotoCut.common.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.SnowCommon.common.AppProperties;
import cn.dressbook.ui.SnowCommon.common.MessageWhat;

public class LoadResultImagesTask extends AsyncTask<String, Boolean, List<Bitmap>>{

	private Handler handler;
	private Context context;
	public LoadResultImagesTask(Context context, Handler handler){

		this.handler=handler;
		this.context=context;
	}
	
	@Override
	protected List<Bitmap> doInBackground(String... params) {
		String imageDir=params[0];
		int i=0;
		List<Bitmap> resultList=new ArrayList<Bitmap>();
		while(i<=AppProperties.getPhotoCount()){
			String filePath=imageDir+File.separator+"IMG_"+i+".jpg";
			File imageFile=new File(filePath);
			if(!imageFile.exists())
				break;
			else{
				Bitmap image=BitmapFactory.decodeFile(filePath);
				resultList.add(image);
				
			}
			i++;
		}
		return resultList;
	}
	@Override
	protected void onPostExecute (List<Bitmap> imageData) { 
		Message msg=new Message();
		msg.what=MessageWhat.WHAT_IMAGE_LOAD_TASK;
		msg.obj=imageData;
		handler.handleMessage(msg);
    }
	
}
