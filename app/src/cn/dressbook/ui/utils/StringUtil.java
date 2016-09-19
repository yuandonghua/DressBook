package cn.dressbook.ui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author 熊天富
 * @des 字符串工具类
 * @time 2016-01-30 16:27:44
 *
 */
public class StringUtil {
	/**
	 *  去除字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
	public static String jieduanString(String str){
		str=str.split(" ")[0];
		return str;
	}

}
