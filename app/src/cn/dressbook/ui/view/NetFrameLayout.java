package cn.dressbook.ui.view;




import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import cn.dressbook.ui.R;

public abstract class NetFrameLayout extends FrameLayout {
	private View viewLoding;
	private View viewFail;
	private View viewSuccess;
	/**
	 * 加载的状态
	 */
	private LoadStatus status = LoadStatus.STATUS_LODING;
	/**
	 * handler处理
	 */
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.i("xx", "执行到主线程刷新ui");
			switchResultView();
		}
	};
	private Button btnFail;
	/**
	 * 枚举加载的状态
	 * @author 15712
	 *
	 */
	enum LoadStatus {
		STATUS_LODING(0), STATUS_SUCCESS(1), STATUS_FAIL(2);
		int vaule;

		LoadStatus(int status) {
			this.vaule = status;
		}

		public int getValue() {
			return vaule;
		}
	}
	/**
	 * 构造方法
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public NetFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	/**
	 * 构造方法
	 * @param context
	 * @param attrs
	 */
	public NetFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	/**
	 * 构造方法
	 * @param context
	 */
	public NetFrameLayout(Context context) {
		super(context);
		initView();
	}
	/**
	 * 初始化view
	 */
	private void initView() {
		Log.i("xx", "创建了view");
		if (viewLoding == null) {
			viewLoding = View
					.inflate(getContext(), R.layout.page_loading, null);
		}
		if (viewFail == null) {
			viewFail = View.inflate(getContext(), R.layout.page_error, null);
			btnFail = (Button) viewFail.findViewById(R.id.btn_reload);
			btnFail.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					status=LoadStatus.STATUS_LODING;
					switchResultView();
					refreshData();
				}
			});
		}
		if (viewSuccess == null) {
			viewSuccess = getSuccessView();
		}
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		addView(viewFail, params);
		addView(viewLoding, params);
		/**
		 * 如果获取数据成功，则添加成功的view
		 */
		if (viewSuccess != null) {
			addView(viewSuccess, params);
		} else {
			throw new IllegalArgumentException("successView不能为空");
		}

		switchResultView();

		// 从网络上下载数据并进行更新
		refreshData();

	}
	/**
	 * 更新数据
	 */
	public void refreshData() {
		new Thread() {
			public void run() {
				Object data = getServiceData();
				switchState(data);
				handler.sendEmptyMessage(0);
			};
		}.start();

	}
	/**
	 * 根据数据来改变状态
	 * @param data
	 */
	private void switchState(Object data) {
		if (data == null) {
			status = LoadStatus.STATUS_FAIL;
		} else {
			status = LoadStatus.STATUS_SUCCESS;
		}
	}
	/**
	 * 根据状态来改变UI
	 */
	private void switchResultView() {
		viewFail.setVisibility(View.INVISIBLE);
		viewLoding.setVisibility(View.INVISIBLE);
		viewSuccess.setVisibility(View.INVISIBLE);
		switch (status.getValue()) {
		case 0:
			viewLoding.setVisibility(View.VISIBLE);
			break;
		case 1:
			viewSuccess.setVisibility(View.VISIBLE);
			break;
		case 2:
			viewFail.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

	}
	/**
	 * 获取失败界面的button
	 * @return
	 */
	public Button getFailBtn(){
		return btnFail;
	}
	/**
	 * 获取数据成功返回的界面
	 * @return
	 */
	protected abstract View getSuccessView();
	/**
	 * 从服务器上获取数据
	 * @return
	 */
	protected abstract Object getServiceData();

}
