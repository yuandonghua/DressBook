/**   
 * @title ListViewUtils.java
 * @package cn.dressbook.ui.utils
 * @description 
 * @author 袁东华   
 * @update 2013-12-26 下午08:06:28
 * @version    
 */
package cn.dressbook.ui.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @descriptionScrollView中嵌套ListView
 * @version
 * @author 袁东华
 * @update 2013-12-26 下午08:06:28
 */

public class ListViewUtils {
	private static final String TAG = ListViewUtils.class.getSimpleName();
	private static ListViewUtils mListViewUtils;

	private ListViewUtils() {
	}

	public static ListViewUtils getInstance() {
		if (mListViewUtils == null) {
			mListViewUtils = new ListViewUtils();
		}
		return mListViewUtils;
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {

		// 获取ListView对应的Adapter

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {

			return;

		}

		int totalHeight = 0;

		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0); // 计算子项View 的宽高
				totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount()));

//		获取子项间分隔符占用的高度
//		 listView.getDividerHeight();

		// params.height最后得到整个ListView完整显示需要的高度

		listView.setLayoutParams(params);

	}

}
