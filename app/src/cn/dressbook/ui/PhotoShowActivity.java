package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import cn.dressbook.ui.adapter.PhotoShowAdapter;

/**
 * @description 大图展示
 * @author 袁东华
 * @time 2015-12-14下午1:41:42
 */
@ContentView(R.layout.activity_photo_show)
public class PhotoShowActivity extends BaseActivity {
	private ViewPager vpPhotoShow;

	private String[] photoUri;
	/**
	 * TODO 第一次显示哪张照片
	 */
	private int firstShow;
	/**
	 * TODO 照片路径类型
	 */
	private int type;

	private PhotoShowAdapter photoShowAdapter;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		vpPhotoShow = (ViewPager) findViewById(R.id.vpPhotoShow);
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		// 获取照片数据
		Intent data = getIntent();
		firstShow = data.getIntExtra("SHOW_WHICH", 0);
		type = data.getIntExtra("URL_TYPE", 3306);
		int flag = data.getIntExtra("BitmapUtilsFlag", 0);
		photoUri = data.getStringArrayExtra("PHOTO_URI_DATA");
		// 传入adapter
		photoShowAdapter = new PhotoShowAdapter(this, photoUri, type);
		if (flag == 0) {

		} else {
		}
		vpPhotoShow.setAdapter(photoShowAdapter);
		vpPhotoShow.setCurrentItem(firstShow, true);
	}

}
