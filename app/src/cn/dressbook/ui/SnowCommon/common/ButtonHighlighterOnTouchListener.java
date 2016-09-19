package cn.dressbook.ui.SnowCommon.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.R.color;

public class ButtonHighlighterOnTouchListener implements OnTouchListener {

	private static final int TRANSPARENT_GREY =Color.argb(0, 185, 185, 185);
	private static final int FILTERED_GREY =color.orange; //Color.argb(155, 185, 185, 185);

	ImageView imageView;
	TextView textView;
	Button button;
	Context context;

	public ButtonHighlighterOnTouchListener(final Button button) {
		super();
		this.button = button;
	}

	public ButtonHighlighterOnTouchListener(final ImageView imageView) {
		super();
		this.imageView = imageView;
	}

	public ButtonHighlighterOnTouchListener(final TextView textView,
			Context context) {
		super();
		this.textView = textView;
		this.context = context;
	}

	public ButtonHighlighterOnTouchListener(final TextView textView,
			final ImageView button, Context context) {
		super();
		this.textView = textView;
		this.imageView = button;
		this.context = context;
	}

	private static final int TRANSPARENT_GREY_t =color.orange; //Color.argb(0, 230, 144, 53);
	public final static float[] BT_SELECTED_T = new float[] { 1, 0, 0, 0, 230,
			0, 1, 0, 0, 230, 0, 0, 1, 0, 230, 0, 0, 0, 1, 0 };
	public final static float[] BT_NOT_SELECTED_T = new float[] { 1, 0, 0, 0,
			0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

	public final static float[] BT_SELECTED = new float[] { 2, 0, 0, 0, 2, 0,
			2, 0, 0, 2, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0 };
	public final static float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
			0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

	public boolean onTouch(final View view, final MotionEvent motionEvent) {
		  if (textView != null && imageView != null) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				textView.setTextColor(context.getResources().getColor(
						color.weibo_font_color));
				imageView.setColorFilter(context.getResources().getColor(FILTERED_GREY));
			} else {
				textView.setTextColor(context.getResources().getColor(
						color.gray_time));
				imageView.setColorFilter(TRANSPARENT_GREY);
			}
			
			
		} else if (textView != null) {
			// PorterDuff.Mode.SCREEN;
			// for (final Drawable compoundDrawable :
			// textView.getCompoundDrawables()) {
			// if (compoundDrawable != null) {
			// if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			// // we use PorterDuff.Mode. SRC_ATOP as our filter color is
			// already transparent
			// // we should have use PorterDuff.Mode.LIGHTEN with a non
			// transparent color
			// compoundDrawable.setColorFilter(TRANSPARENT_GREY_t,
			// PorterDuff.Mode.SRC_ATOP);
			// } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			// compoundDrawable.setColorFilter(TRANSPARENT_GREY,
			// PorterDuff.Mode.SRC_ATOP); // or null
			// }
			// }
			// }

			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				textView.setTextColor(context.getResources().getColor(
						color.weibo_font_color));
				// textView.getBackground().setColorFilter(new
				// PorterDuffColorFilter(0xffff00,PorterDuff.Mode.LIGHTEN));
				// textView.getBackground().setColorFilter(new
				// ColorMatrixColorFilter(BT_SELECTED_T));
				// textView.setBackgroundDrawable(textView.getBackground());
			} else {
				textView.setTextColor(context.getResources().getColor(
						color.gray_time));
				// textView.getBackground().setColorFilter(new
				// PorterDuffColorFilter(0x0000ff,PorterDuff.Mode.LIGHTEN));
				// textView.getBackground().setColorFilter(new
				// ColorMatrixColorFilter(BT_NOT_SELECTED));
				// textView.setBackgroundDrawable(v.getBackground());
			}

			// for (final Drawable compoundDrawable :
			// textView.getCompoundDrawables()) {
			// if (compoundDrawable != null) {
			// if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			// // we use PorterDuff.Mode. SRC_ATOP as our filter color is
			// already transparent
			// // we should have use PorterDuff.Mode.LIGHTEN with a non
			// transparent color
			// compoundDrawable.setColorFilter(FILTERED_GREY,
			// PorterDuff.Mode.SRC_ATOP);
			// } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			// compoundDrawable.setColorFilter(TRANSPARENT_GREY,
			// PorterDuff.Mode.SRC_ATOP); // or null
			// }
			// }
			// }
		} else if (button != null) {

			// for (final Drawable compoundDrawable : button
			// .getCompoundDrawables()) {
			// if (compoundDrawable != null) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				button.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_SELECTED));
				// we use PorterDuff.Mode. SRC_ATOP as our filter color
				// is already transparent
				// we should have use PorterDuff.Mode.LIGHTEN with a non
				// transparent color
				// compoundDrawable.setColorFilter(FILTERED_GREY,
				// PorterDuff.Mode.SRC_ATOP);
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				button.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_NOT_SELECTED));
				// compoundDrawable.setColorFilter(TRANSPARENT_GREY,
				// PorterDuff.Mode.SRC_ATOP); // or null
			}
			// }
			// }

		}else if (imageView != null) {

			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				imageView.setColorFilter(FILTERED_GREY);
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				imageView.setColorFilter(TRANSPARENT_GREY); // or null
			}
		}
		return false;
	}

}
