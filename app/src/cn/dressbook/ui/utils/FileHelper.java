package cn.dressbook.ui.utils;

import java.io.File;

/**
 * 文件名称 :  
 * 文件描述 : 主要是对文件系统的一些操作，包括文件的读写操作,主要是完成下载中的保存下载文件的功能
 * @author 袁东华
 */
public class FileHelper {
	
	/**
	 * @param filePath 文件路径
	 * @return true 删除成功、false删除失败
	 */
	public static boolean deleteDownloadFile(String filePath){
		return deleteFile(filePath);
	}
	
	/**
	 * 删除路径指向的文件
	 * @param fileName
	 *            文件的名称
	 * @return true删除成功，false删除失败
	 */
	private static boolean deleteFile(final String filePath){
		boolean isComplete = false;
		
		if(filePath == null)
			return isComplete;
		File file = new File(filePath);
		
		if(file != null && file.exists()){
			isComplete = file.delete();
		}else{
			isComplete = true;
		}
		return isComplete;
	}
	
	/**
	 * 内部递归调用，进行子目录的更名
	 * @param path
	 *            路径
	 * @param from
	 *            原始的后缀名
	 * @param to
	 *            改名的后缀
	 *            /storage/emulated/0/ChuanYue/ChuanYueStore/photo_cache
	 */
	public static void  extBatchRename(String path, String from, String to) {
		File f = new File(path);
		File[] fs = f.listFiles();
		for (int i = 0; i < fs.length; ++i) {
			File f2 = fs[i];
			if (f2.isDirectory()) {
				extBatchRename(f2.getPath(), from, to);
			} else {
				String name = f2.getName();
				if (name.endsWith(from)) {
					f2.renameTo(new File(f2.getParent() + "/"
							+ name.substring(0, name.indexOf(from)) + to));
				}
			}
		}
	}
	
	
	/**
	 * 在程序第一次安装的时候就在sd卡的主目录下新建一个系统所需的目录
	 * 
	 * @return 创建目录是否成功
	 */
	public static boolean makeDir(String path) {
		boolean isComplete = true;
		// 首次启动创建photo_cache文件夹
		File file = new File(path);
		if (!isFileExist(file)) {
			isComplete = file.mkdirs();
		}
		file = null;
		return isComplete;
	}
	
	/**
	 * 是否存在此文件
	 * 
	 * @param file
	 *            判断是否存在的文件
	 * @return 存在返回true，否则返回false
	 */
	public static boolean isFileExist(final File file) {
		boolean isExist = false;
		// 在无SD卡时file会为空
		if (file == null) {
			return false;
		}
		if (file.exists()) {
			isExist = true;
		} else {
			isExist = false;
		}
		return isExist;
	}
}
