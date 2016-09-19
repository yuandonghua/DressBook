package cn.dressbook.ui.face;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import cn.dressbook.ui.face.data.SingletonDataMgr;

public class ShowPicActivity extends Activity {

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			final Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			intent.putExtra("putUserbyhua", "test");
			this.setResult(100);
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent mIntent = getIntent();
		// Bitmap bm = (Bitmap) mIntent.getExtras().getSerializable("IMG");
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		Bitmap bm = mgr.getUpdateBmp();
		ImageView img = new ImageView(this);
		img.setImageBitmap(bm);
		setContentView(img);
		// mUser = (User) mIntent.getSerializableExtra(UserConst.USER_DATA);
	}
}
