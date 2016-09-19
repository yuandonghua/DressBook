package cn.dressbook.ui.view.crop;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;


/**
 * 
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-17 上午10:42:08
 * @since
 * @version
 */
public class Crop {

	public static final int REQUEST_CROP = 6709;
	public static final int REQUEST_PICK = 9162;
	public static final int RESULT_ERROR = 404;

	static interface Extra {
		String ASPECT_X = "aspect_x";
		String ASPECT_Y = "aspect_y";
		String MAX_X = "max_x";
		String MAX_Y = "max_y";
		String ERROR = "error";
	}

	private Intent cropIntent;

	/**
	 * Create a crop Intent builder with source and destination image Uris
	 * 
	 * @param source
	 *            Uri for image to crop
	 * @param destination
	 *            Uri for saving the cropped image
	 */
	public static Crop of(Uri source, Uri destination) {
		return new Crop(source, destination);
	}

	private Crop(Uri source, Uri destination) {
		cropIntent = new Intent();
		cropIntent.setData(source);
		cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, destination);
	}

	/**
	 * Set fixed aspect ratio for crop area
	 * 
	 * @param x
	 *            Aspect X
	 * @param y
	 *            Aspect Y
	 */
	public Crop withAspect(int x, int y) {
		cropIntent.putExtra(Extra.ASPECT_X, x);
		cropIntent.putExtra(Extra.ASPECT_Y, y);
		return this;
	}

	/**
	 * Crop area with fixed 1:1 aspect ratio
	 */
	public Crop asSquare() {
		cropIntent.putExtra(Extra.ASPECT_X, 1);
		cropIntent.putExtra(Extra.ASPECT_Y, 1);
		return this;
	}

	/**
	 * Set maximum crop size
	 * 
	 * @param width
	 *            Max width
	 * @param height
	 *            Max height
	 */
	public Crop withMaxSize(int width, int height) {
		cropIntent.putExtra(Extra.MAX_X, width);
		cropIntent.putExtra(Extra.MAX_Y, height);
		return this;
	}



	/**
	 * Retrieve URI for cropped image, as set in the Intent builder
	 * 
	 * @param result
	 *            Output Image URI
	 */
	public static Uri getOutput(Intent result) {
		return result.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
	}

	/**
	 * Retrieve error that caused crop to fail
	 * 
	 * @param result
	 *            Result Intent
	 * @return Throwable handled in CropImageActivity
	 */
	public static Throwable getError(Intent result) {
		return (Throwable) result.getSerializableExtra(Extra.ERROR);
	}

	/**
	 * Utility to start an image picker
	 * 
	 * @param activity
	 *            Activity that will receive result
	 */
	public static void pickImage(Activity activity) {
		pickImage(activity, REQUEST_PICK);
	}

	/**
	 * Utility to start an image picker with request code
	 * 
	 * @param activity
	 *            Activity that will receive result
	 * @param requestCode
	 *            requestCode for result
	 */
	public static void pickImage(Activity activity, int requestCode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
				.setType("image/*");
		try {
			activity.startActivityForResult(intent, requestCode);
		} catch (ActivityNotFoundException e) {
		}
	}

}
