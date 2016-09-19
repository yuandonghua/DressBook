package cn.dressbook.ui.general.FotoCut.opencvImpl;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import android.graphics.Bitmap;
import cn.dressbook.ui.SnowCommon.common.util.MediaHelper;

public class TestOpencv {
	public void initA(){
		long start=System.currentTimeMillis();
		Mat roi=new Mat();
		Bitmap roiBm = MediaHelper.getBitmap("roi.jpg");
		//Mat test=new Mat (roiBm.getHeight(), roiBm.getWidth(), CvType.CV_8U, new Scalar(0));
		Utils.bitmapToMat(roiBm, roi);
	
		
		long end=System.currentTimeMillis();
	//	TextView timeView=(TextView) this.findViewById(R.id.timeView);
	//	timeView.setText(String.valueOf(end-start));
		
	}
	
}
