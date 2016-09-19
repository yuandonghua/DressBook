package cn.dressbook.ui.SnowCommon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import cn.dressbook.ui.R;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.activity_splash, null);
		setContentView(view);
		
		 AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
	        aa.setDuration(2000);
	        view.startAnimation(aa);
	        aa.setAnimationListener(new AnimationListener()
	        {
	            @Override
	            public void onAnimationEnd(Animation arg0) {
//	    			Log.e("日志:", classHome.getName());
//	    			Intent i=new Intent(SplashActivity.this,classHome);
//	    			
//	    			SplashActivity.this.startActivity(i);
//	    			SplashActivity.this.finish();
	            }
	            @Override
	            public void onAnimationRepeat(Animation animation) {}
	            @Override
	            public void onAnimationStart(Animation animation) {}
	                                                                          
	        });
	
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			Class classHome=((SnapCommonApplication)SplashActivity.this.getApplication()).getHomeClass();
//			
//			Intent i=new Intent(SplashActivity.this,classHome);
//			
//			SplashActivity.this.startActivity(i);
//			SplashActivity.this.finish();
//			
//			}
//		}).start();
	}
}
