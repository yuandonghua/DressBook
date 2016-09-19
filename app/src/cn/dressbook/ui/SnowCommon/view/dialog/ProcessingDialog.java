package cn.dressbook.ui.SnowCommon.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import cn.dressbook.ui.R;

public class ProcessingDialog extends Dialog {

	private Context dContext;
	private LayoutInflater inflater;
	private TextView loadText;
	private LayoutParams lp;

	public ProcessingDialog(Context context) {

		super(context, R.style.ProcessDialog);
		this.dContext = context;
		inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.processing_dialog, null);
		loadText = (TextView) layout.findViewById(R.id.process_text);
		this.setContentView(layout);
		this.setCanceledOnTouchOutside(false);
		lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0;
		lp.alpha = 1.0f;
		getWindow().setAttributes(lp);

	}

	public ProcessingDialog(Context context, String content) {
		super(context, R.style.ProcessDialog);
		this.dContext = context;
		inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.processing_dialog, null);
		loadText = (TextView) layout.findViewById(R.id.process_text);
		loadText.setText(content);
		this.setContentView(layout);
		this.setCanceledOnTouchOutside(false);
		lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0;
		lp.alpha = 1.0f;
		getWindow().setAttributes(lp);

	}

	public void setProcessingText(String text) {
		loadText.setText(text);
	}

}
