package cn.dressbook.ui.SnowCommon.common;

import java.io.File;
import java.util.Comparator;

public class FileComparator implements Comparator{

	@Override
	public int compare(Object lhs, Object rhs) {
		String str1=((File)lhs).getName().replace("IMG_", "");
		String str2=((File)rhs).getName().replace("IMG_", "");
		int number1=Integer.parseInt(str1.replace(".jpg", ""));
		int number2=Integer.parseInt(str2.replace(".jpg", ""));
		return (number1-number2);
	}
}