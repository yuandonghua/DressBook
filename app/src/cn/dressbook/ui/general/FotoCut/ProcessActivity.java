package cn.dressbook.ui.general.FotoCut;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.SnowCommon.common.RuntimeCache;
/**
 * @description:图片结果展示
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-5-26 下午2:53:59
 */
public class ProcessActivity extends Activity implements
		OnClickListener{




	protected View backBtn;
	protected TextView nextBtn;



	private ProcessActivity self = this;




	protected static Bitmap bgBmp;
	protected ImageView bgImage;

	

	protected String grabFilePath;
	protected ImageView floatImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process);

		Bundle bundle = getIntent().getExtras();

		grabFilePath = bundle.getString("imagePath");
		Log.e("","图片路径:"+grabFilePath);
		backBtn = findViewById(R.id.process_back);
		nextBtn = (TextView) findViewById(R.id.process_next);
		// effectTab.setOnClickListener(this);

		backBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
		nextBtn.setVisibility(View.INVISIBLE);




		floatImageView = (ImageView) findViewById(R.id.float_image);



		// initFilter();
		// extraImageView.setVisibility(View.INVISIBLE);

		// showFilterFontColor();

//		RelativeLayout adLay = (RelativeLayout) findViewById(R.id.ad_lay);
//		
//		 adView = BaiduBanner.CreateBaiduBanner(this);
//		adLay.addView(adView);//,rllp);

		Bitmap grubBitmap = BitmapFactory.decodeFile(grabFilePath);

		
		RelativeLayout.LayoutParams clp = (RelativeLayout.LayoutParams) floatImageView
				.getLayoutParams();

		clp.height = RuntimeCache.getScreenW();
		clp.width = RuntimeCache.getScreenW();
		


		BitmapDrawable bmpDrawable = new BitmapDrawable(this.getResources(),
				grubBitmap);
		floatImageView.setImageDrawable(bmpDrawable);
		


	}
	


	
	
	public Bitmap grubBitmap;


	@Override
	public void onClick(View v) {
		if (v == backBtn) {
			Log.e("日志:", "点击返回:"+grabFilePath);
			File s=new File(grabFilePath);
			File t=new File(Environment.getExternalStorageDirectory()+"/dressbook/image.jpg");
			if(fuZhiFile(s, t)){
				
				this.finish();
			}
			// RuntimeCache.removeActivity(this);
		} 

	}
	/**
	 * @description复制文件
	 * @parameters
	 */
	public static boolean fuZhiFile(File s, File t) {
		if (s != null && s.exists()) {
			if (t.exists()) {
				t.delete();
			}
			FileInputStream fi = null;

			FileOutputStream fo = null;

			FileChannel in = null;

			FileChannel out = null;

			try {

				fi = new FileInputStream(s);

				fo = new FileOutputStream(t);

				in = fi.getChannel();// 得到对应的文件通道

				out = fo.getChannel();// 得到对应的文件通道

				long bytes = in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
				if (t.exists()) {
					return true;
				} else {
					return false;
				}
			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {
					if (fi != null) {

						fi.close();
					}
					if (in != null) {

						in.close();
					}

					if (fo != null) {

						fo.close();
					}
					if (out != null) {

						out.close();
					}

				} catch (IOException e) {

					e.printStackTrace();

				}

			}
		} else {
		}
		return false;
	}
	@Override
	public void finish(){
	
		super.finish();
		if(grubBitmap!=null){
			grubBitmap.recycle();
			grubBitmap=null;
		}
		if(bgBmp!=null){
			bgBmp.recycle();
			bgBmp=null;
		}
		
		System.gc();
		
	}
	



	public Activity getActivity() {

		return self;
	}



	


}
