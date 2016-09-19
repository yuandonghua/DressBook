package cn.dressbook.ui.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	private static MD5Utils mInstance = null;

	public static MD5Utils getInstance() {
		if (mInstance == null) {
			mInstance = new MD5Utils();
		}
		return mInstance;
	}

	public static String getMD5String(String val) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(val.getBytes());
			return getString(md5.digest());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String getString(byte[] bytes) {
		// 首先初始化一个字符数组，用来存放每个16进制字符

		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };

		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

		char[] resultCharArray = new char[bytes.length * 2];

		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

		int index = 0;

		for (byte b : bytes) {

			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

			resultCharArray[index++] = hexDigits[b & 0xf];

		}
		// StringBuilder sb = new StringBuilder();
		// for (int i = 0; i < bytes.length; i++) {
		// String hex = Integer.toHexString(0xFF & bytes[i]);
		// sb.append(hex);
		// }
		return new String(resultCharArray);
	}
}
