package cn.dressbook.ui.general.FotoCut.view.ui;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.SnowCommon.common.CanvasModel;

public class DrawRect {
	private View frameView;
	private LayoutParams lp;

	//private ImageView framePlayBtn;
	//private ImageView topRightBtn;
	//private ImageView bottomLeftBtn;
	private ImageView bottomRightBtn;

	//private ImageView positionBtn;
	private int width;
	private int height;
	private boolean isPlay = true;

	public DrawRect(View frameView) {
		this.frameView = frameView;
		initView();
	}

	public void setParentSize(int width, int height) {
		this.width = width;
		this.height = height;
		int x = (width / 2 - lp.width / 2);
		int y = (height / 2 - lp.height / 2);

		lp.leftMargin = x;
		lp.topMargin = y;
		frameView.setLayoutParams(lp);
	}

	public void initView() {
//		framePlayBtn = (ImageView) frameView.findViewById(R.id.frame_play_btn);
//		topRightBtn = (ImageView) frameView.findViewById(R.id.top_right);
//		bottomLeftBtn = (ImageView) frameView.findViewById(R.id.bottom_left);
		bottomRightBtn = (ImageView) frameView.findViewById(R.id.bottom_right);
//		positionBtn = (ImageView) frameView.findViewById(R.id.frame_position);
//		topRightBtn.setVisibility(View.GONE);
//		bottomLeftBtn.setVisibility(View.GONE);
//		framePlayBtn.setVisibility(View.GONE);
		// framePlayBtn.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// if (framePlayBtn.isSelected()) {
		// framePlayBtn.setSelected(false);
		// //editPage.stopRectArea();
		// isPlay = false;
		// } else {
		// framePlayBtn.setSelected(true);
		// //editPage.playRectArea();
		// isPlay = true;
		// }
		// }
		// });

		lp = (LayoutParams) frameView.getLayoutParams();
	}

	public void setOnTouchListener(OnTouchListener listener) {
//		topRightBtn.setOnTouchListener(listener);
//		bottomLeftBtn.setOnTouchListener(listener);
		bottomRightBtn.setOnTouchListener(listener);
		frameView.setOnTouchListener(listener);
	}

	public View getView() {
		return this.frameView;
	}

	private float startX;
	private float startY;
	private float clickX;
	private float clickY;

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			startX = event.getX();
			startY = event.getY();
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// if (isPlay) {
			// return true;
			// }
			clickX = event.getX();
			clickY = event.getY();
			float xD = clickX - startX;
			float yD = clickY - startY;
//			if (v == topRightBtn) {
//				topRightBtnSizeChange(xD, yD);
//			} else if (v == bottomLeftBtn) {
//				bottomLeftBtnSizeChange(xD, yD);
//			} else
			if (v == bottomRightBtn) {
				bottomRightBtnSizeChange(xD, yD);
			} else if (v == frameView) {
				positionChange(xD, yD);
			}
			startX = clickX;
			startY = clickY;
			return true;
		}

		return true;
	}

	public void topLeftBtnSizeChange(float xDis, float yDis) {
		float mlX = lp.leftMargin + xDis;
		lp.leftMargin = (int) mlX;
		float mlY = lp.topMargin + yDis;
		lp.topMargin = (int) mlY;
		lp.width = (int) (lp.width - xDis);
		lp.height = (int) (lp.height - yDis);
		frameView.setLayoutParams(lp);
	}

	public void topRightBtnSizeChange(float xDis, float yDis) {
		float mlY = lp.topMargin + yDis;
		lp.topMargin = (int) mlY;
		lp.width = (int) (lp.width + xDis);
		lp.height = (int) (lp.height - yDis);
		frameView.setLayoutParams(lp);
	}

	public void bottomLeftBtnSizeChange(float xDis, float yDis) {
		float tmpW = lp.width - xDis;
		float tmpH = lp.height + yDis;
		if (tmpW < 20) {
			tmpW = 20;
		}
		if (tmpH < 20) {
			tmpH = 20;
		}

		float XCdis = lp.width - tmpW;

		float mlX = lp.leftMargin + XCdis;
		if (mlX < 0) {
			lp.leftMargin = 0;
		} else {
			lp.leftMargin = (int) mlX;
		}

		if ((tmpH + lp.topMargin) > height) {
			lp.height = height - lp.topMargin;
		}

		lp.leftMargin = (int) mlX;
		lp.width = (int) (tmpW);

		frameView.setLayoutParams(lp);
	}

	public void bottomRightBtnSizeChange(float xDis, float yDis) {
		float tmpW = lp.width + xDis;
		float tmpH = lp.height + yDis;
		if (tmpW < 20) {
			tmpW = 20;
		}

		if (tmpH < 20) {
			tmpH = 20;
		}
		if ((tmpW + lp.leftMargin) > width) {
			lp.width = width - lp.leftMargin;
		} else {
			lp.width = (int) tmpW;
		}

		if ((tmpH + lp.topMargin) > height) {
			lp.height = height - lp.topMargin;
		} else {
			lp.height = (int) tmpH;
		}

		frameView.setLayoutParams(lp);
	}

	public void positionChange(float xDis, float yDis) {
		float mlX = lp.leftMargin + xDis;
		mlX = (mlX > 0 ? mlX : 0);
		if ((mlX + lp.width) > width)
			mlX = width - lp.width;
		lp.leftMargin = (int) mlX;
		float mlY = lp.topMargin + yDis;
		mlY = (mlY > 0 ? mlY : 0);
		if ((mlY + lp.height) >= height)
			mlY = height - lp.height;
		lp.topMargin = (int) mlY;
		frameView.setLayoutParams(lp);
	}

	public Rect getRect() {
		Rect rect = new Rect();
		rect.top = lp.topMargin;
		rect.left = lp.leftMargin;
		rect.right = lp.leftMargin + lp.width;
		rect.bottom = lp.topMargin + lp.height;
		return rect;
	}

	public CanvasModel getMaskResult() {
		if (isPlay) {
//			Bitmap rectBitmap = Bitmap.createBitmap(lp.width, lp.height,
//					Config.ARGB_8888);
//			Canvas canvas = new Canvas(rectBitmap);
//			Paint paint = new Paint();
//			paint.setStyle(Style.STROKE);
//			paint.setAntiAlias(true);
//			paint.setColor(Color.WHITE);
//			paint.setStrokeWidth(2.0f);
//
//			canvas.drawRect(1,1, canvas.getWidth()-2, canvas.getHeight()-2, paint);
//			canvas.save(Canvas.ALL_SAVE_FLAG);
//			canvas.restore();
			Rect rect = getRect();
			CanvasModel model = new CanvasModel(width, height, rect.left,
					rect.right, rect.top, rect.bottom);
			model.setoMask(null);
			//model.setCanvasBitmap(rectBitmap);
			return model;
		}
		return null;
	}

}
