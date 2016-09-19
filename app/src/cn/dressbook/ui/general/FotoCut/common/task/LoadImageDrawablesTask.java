package cn.dressbook.ui.general.FotoCut.common.task;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.SnowCommon.common.AnimationDrawableModel;
import cn.dressbook.ui.SnowCommon.common.AppImage;
import cn.dressbook.ui.SnowCommon.common.MessageWhat;
import cn.dressbook.ui.SnowCommon.common.util.MediaHelper;

public class LoadImageDrawablesTask extends
		AsyncTask<Integer, Void, AnimationDrawableModel> {

	private Context context;
	private Handler handler;

	private String dir;
	private int playback;

	public LoadImageDrawablesTask(Handler handler, String imageDir,
			Context context, int playback) {
		this.dir = imageDir;
		this.context = context;
		this.handler = handler;
		this.playback = playback;

	}

	@Override
	protected AnimationDrawableModel doInBackground(Integer... params) {
		AnimationDrawable drawables = new AnimationDrawable();
		int doration = params[0];
		List<AppImage> list = MediaHelper.getAllImagesByDir(dir);

		for (int i = 0; i < list.size(); i++) {
			BitmapDrawable drawable = new BitmapDrawable(
					context.getResources(), list.get(i).getBitmap());
			drawables.addFrame(drawable, doration);
		}
		if (playback == 0) {
			for (int i = list.size() - 1; i >= 0; i--) {
				BitmapDrawable drawable = new BitmapDrawable(
						context.getResources(), list.get(i).getBitmap());
				drawables.addFrame(drawable, doration);
			}
		}
		drawables.setOneShot(false);
		AnimationDrawableModel model = new AnimationDrawableModel();
		model.setFrameCount(list.size());
		model.setAnimation(drawables);
		return model;
	}

	@Override
	protected void onPostExecute(AnimationDrawableModel model) {
		Message msg = new Message();
		msg.what = MessageWhat.WHAT_ANIMATION_LOAD_TASK;
		msg.obj = model;
		handler.sendMessage(msg);
	}
}
