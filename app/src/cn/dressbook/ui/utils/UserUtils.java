package cn.dressbook.ui.utils;

import java.math.BigDecimal;

import cn.dressbook.ui.common.PathCommonDefines;

/**
 * @description 获取用户相关信息的工具类
 * @author 袁东华
 * @date 2014-10-24 上午10:23:20
 */
public class UserUtils {
	private static UserUtils mInstance = null;

	public static UserUtils getInstance() {
		if (mInstance == null) {
			mInstance = new UserUtils();
		}
		return mInstance;
	}

	/**
	 * @description 获取用户衣柜列表
	 * @parameters
	 */
	public String getUserPathOfServer(String user_id, String suffix) {

		String a = "0", b = "0", c = "0", d = "0";
		if (user_id != null&&!"0".equals(user_id)) {
			int user_id2=Integer.parseInt(user_id);
			double j = 500000d;// 50万，每个根目录下可存50万用户
			double m = user_id2 / j;
			BigDecimal p = new BigDecimal(m);
			String pString = p.toString();
			// System.out.println("AttireUtil->dbCachePathNew.pString="+pString);
			String[] str = pString.split("\\.");
			a = str[0];
			b = str[1].substring(0, 1);
			c = str[1].substring(1, 2);
			d = str[1].substring(2, 3);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(PathCommonDefines.SERVER_ADDRESS);
		sb.append("/data/dbcachebig");
		sb.append("/" + a);
		sb.append("/" + b);
		sb.append("/" + c);
		sb.append("/" + d);
		sb.append("/u_" + user_id);
		sb.append(suffix);
		return sb.toString();

	}

	/**
	 * @description 获取用户某个衣柜
	 * @parameters
	 */
	public String getWardrobeUrl(String user_id, int  wardrobe_id) {

		String a = "0", b = "0", c = "0", d = "0";
		if (user_id != null&&!user_id.equals("0")) {
			int user_id2=Integer.parseInt(user_id);
			double j = 500000d;// 50万，每个根目录下可存50万用户
			double m = user_id2 / j;
			BigDecimal p = new BigDecimal(m);
			String pString = p.toString();
			// System.out.println("AttireUtil->dbCachePathNew.pString="+pString);
			String[] str = pString.split("\\.");
			a = str[0];
			b = str[1].substring(0, 1);
			c = str[1].substring(1, 2);
			d = str[1].substring(2, 3);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(PathCommonDefines.SERVER_ADDRESS);
		sb.append("/data/dbcachebig");
		sb.append("/" + a);
		sb.append("/" + b);
		sb.append("/" + c);
		sb.append("/" + d);
		sb.append("/u_" + user_id);
		sb.append("/attires.json");

		return sb.toString();

	}
}
