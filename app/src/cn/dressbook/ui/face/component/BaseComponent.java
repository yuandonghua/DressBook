package cn.dressbook.ui.face.component;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import cn.dressbook.ui.face.MainActivity;

public abstract class BaseComponent {

	public static final int HEAD_COMPONENT = 0;
	public static final int HAIR_COMPONENT = 1;
	public static final int CAMERA_COMPONENT = 2;
	public static final int MERGE_COMPONENT = 3;

	protected RelativeLayout comLayout = null;
	protected Context context;
	protected MainActivity mainActivity;

	public BaseComponent(Context context2, MainActivity mainActivity) {
		// TODO Auto-generated constructor stub
		this.context = context2;
		this.mainActivity = mainActivity;
	}

	public abstract View show(Context con);

	public void clearBodyBmp() {

	}

	public void refreshComponent() {

	}

}
