package cn.dressbook.ui.fragment;

import org.xutils.x;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.dressbook.ui.dialog.ProgressDialog;

/**
 * @description: 自定义基础fragment
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-6-9 上午11:28:28
 */
public abstract class BaseFragment extends Fragment {
	/**
	 * Fragment当前状态是否可见
	 */
	protected boolean isVisible;
	public ProgressDialog pbDialog;
	public Context mContext;
	/**
	 * 创建一个新的实例 BaseFragment.
	 * 
	 */
	public BaseFragment() {
		// TODO Auto-generated constructor stub
	}

	private boolean injected = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext=getActivity();
		createProgressBar(mContext);
		injected = true;
		return x.view().inject(this, inflater, container);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mContext=getActivity();
		if (!injected) {
			x.view().inject(this, this.getView());
		}
	}

	public ProgressDialog createProgressBar(Context mContext) {
		if (pbDialog == null) {
			pbDialog = new ProgressDialog(mContext);
		}

		return pbDialog;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}

	@Override
	public boolean getUserVisibleHint() {
		// TODO Auto-generated method stub
		return super.getUserVisibleHint();
	}

	/**
	 * 可见
	 */
	protected void onVisible() {
		lazyLoad();
	}

	/**
	 * 不可见
	 */
	protected void onInvisible() {

	}

	/**
	 * 延迟加载 子类必须重写此方法
	 */
	protected abstract void lazyLoad();

	/**
	 * 是否已被加载过一次，第二次就不再去请求数据了
	 */
	public boolean mHasLoadedOnce;

	public void setHasLoadedOnce(boolean mHasLoadedOnce) {
		this.mHasLoadedOnce = mHasLoadedOnce;
	}

	public boolean isFinish() {
		return !pbDialog.isShowing();
	}
}
