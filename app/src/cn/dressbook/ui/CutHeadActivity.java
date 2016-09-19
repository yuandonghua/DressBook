package cn.dressbook.ui;

import java.io.File;

import org.xutils.x;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.net.UploadExec;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.view.ImageCut;

/**
 * @description: 裁剪头像
 * @author: 袁东华
 * @time: 2015-8-17下午3:13:43
 */
@ContentView(R.layout.cuthead)
public class CutHeadActivity extends BaseActivity implements OnClickListener {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.MATRIX);
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 完成
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	@ViewInject(R.id.cuthead)
	private ImageCut cuthead;

	public void onClick(View v) {
		switch (v.getId()) {
		// 点击完成
		case R.id.operate_tv:
			if (isFinish()) {
				pbDialog.show();
				Bitmap bitmap = cuthead.onClip();
				if (bitmap == null) {
					Toast.makeText(this, "头像处理失败,请换张图片", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				if (FileSDCacher.createBitmapFile(bitmap, CompressFormat.PNG,
						PathCommonDefines.HEAD, "/head.0")) {
					// // 上传头像
					UploadExec.getInstance().uploadUserHead(this, mHandler,
							ManagerUtils.getInstance().getUser_id(null),
							PathCommonDefines.HEAD + "/head.0",
							NetworkAsyncCommonDefines.UPLOAD_HEADIMAGE_S,
							NetworkAsyncCommonDefines.UPLOAD_HEADIMAGE_F);
				} else {
					Toast.makeText(this, "头像处理失败,请重新操作", Toast.LENGTH_SHORT)
							.show();
					finish();
				}

			} else {
				Toast.makeText(this, "正在处理...", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.back_rl:
			if (isFinish()) {
				finish();
			} else {
				Toast.makeText(this, "正在处理...", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("裁剪头像");
		operate_tv.setText("完成");
		operate_tv.setVisibility(View.VISIBLE);
		cuthead = (ImageCut) findViewById(R.id.cuthead);

		operate_tv.setOnClickListener(this);
		back_rl.setOnClickListener(this);
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			String path = intent.getStringExtra("path");

			// 绑定图片
			x.image().bind(cuthead, path, mImageOptions);
		}
		// cuthead.setImageURI(Uri.parse(PathCommonDefines.HEAD + "/head1.0"));

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 上传头像成功
			case NetworkAsyncCommonDefines.UPLOAD_HEADIMAGE_S:
				if (FileSDCacher.deleteDirectory2(new File(
						PathCommonDefines.HEAD))) {

					pbDialog.dismiss();
					finish();
				} else {
					Toast.makeText(CutHeadActivity.this, "操作失败",
							Toast.LENGTH_SHORT).show();
				}
				break;
			// 上传头像失败
			case NetworkAsyncCommonDefines.UPLOAD_HEADIMAGE_F:
				pbDialog.dismiss();
				break;
			default:
				break;
			}
		}

	};
}
