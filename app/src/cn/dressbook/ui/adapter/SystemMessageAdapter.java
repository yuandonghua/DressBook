package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import cn.dressbook.ui.R;
import cn.dressbook.ui.model.CydMessage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * @description:系统消息适配器
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-6-10 下午9:56:55
 */
public class SystemMessageAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<CydMessage> mMessageList;
	private LayoutInflater mLayoutInflater;

	public SystemMessageAdapter(Context context,
			ArrayList<CydMessage> mMessageList) {
		mContext = context;
		this.mMessageList = mMessageList;
		if (mLayoutInflater == null) {
			mLayoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
	}

	public int getCount() {
		return mMessageList != null ? mMessageList.size() : 0;
	}

	public Object getItem(int position) {
		return mMessageList != null ? mMessageList.get(position) : null;
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 加载相应模块的xml文件

		XTTZHolder xTTZHolder = null;
		if (convertView == null) {
			xTTZHolder = new XTTZHolder();
			convertView = mLayoutInflater.inflate(R.layout.xxzx_xttz_layout,
					null);
			xTTZHolder.systemmessage_time = (TextView) convertView
					.findViewById(R.id.systemmessage_time);
			xTTZHolder.systemmessage_content = (TextView) convertView
					.findViewById(R.id.systemmessage_content);

			convertView.setTag(xTTZHolder);
		} else {
			xTTZHolder = (XTTZHolder) convertView.getTag();
		}
		setContet(convertView, xTTZHolder, position);
		return convertView;
	}

	private void setContet(View convertView, XTTZHolder xTTZHolder, int position) {
		// TODO Auto-generated method stub
		if (mMessageList != null && mMessageList.size() > 0) {

			CydMessage message = mMessageList.get(position);

			if (message != null) {

				String time = message.gettime();
				xTTZHolder.systemmessage_time.setText(time);

				String messageContent = message.gettext();
				xTTZHolder.systemmessage_content.setText(messageContent);

			}
		}
	}

	/**
	 * 系统通知视图类
	 */
	public class XTTZHolder {
		public TextView systemmessage_time;
		public TextView systemmessage_content;
	}

}
