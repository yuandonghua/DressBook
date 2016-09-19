package cn.dressbook.ui.face.component;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.dressbook.ui.R;
import cn.dressbook.ui.face.MainActivity;
import cn.dressbook.ui.face.view.BodyImageView;
import cn.dressbook.ui.face.view.FaceImageView;

public class TestComponent extends BaseComponent {

	ImageView img = null;
	Bitmap back = null;

	FaceImageView head;
	BodyImageView body;

	public TestComponent(Context context, MainActivity mainActivity) {
		// TODO Auto-generated constructor stub
		super(context, mainActivity);

		InputStream is = null;
		try {
			// String s = imagesStr[position];

			// int positionIndex = position % iStrArrayList.size();

			String s = "body.jpg";
			is = context.getAssets().open(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

		back = BitmapFactory.decodeStream(is);

		Log.e("aaaa", "bmp w = " + back.getWidth());

	}

	@Override
	public View show(Context con) {
		this.context = con;
		if (comLayout == null) {
			LayoutInflater mInflater = LayoutInflater.from(con);
			comLayout = (RelativeLayout) mInflater.inflate(R.layout.testlayout,
					null);

			// img = (ImageView) comLayout.findViewById(R.id.imageView1);
			// img.setImageBitmap(back);

			head = (FaceImageView) comLayout.findViewById(R.id.imageCache);
			body = (BodyImageView) comLayout.findViewById(R.id.imageBody);

		}
		return comLayout;

	}

	public void moveImg() {
		head.test();
	}
}
