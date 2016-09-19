package cn.dressbook.ui.SnowCommon.common;

import java.util.Comparator;

public class AppImageComparator implements Comparator{

	@Override
	public int compare(Object lhs, Object rhs) {
		String str1=((AppImage)lhs).getFileName().replace("IMG_", "");
		String str2=((AppImage)rhs).getFileName().replace("IMG_", "");
		int number1=Integer.parseInt(str1.replace(".jpg", ""));
		int number2=Integer.parseInt(str2.replace(".jpg", ""));
		return (number1-number2);
	}

}
