package cn.dressbook.ui.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.xutils.common.util.LogUtil;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Handler;
import cn.dressbook.ui.common.PathCommonDefines;

public class FileSDCacher {
	private static Handler mHandler;

	public static boolean copyFile(File s, File t) {
		if (s != null && s.exists()) {
			if (t.exists()) {
				t.delete();
			}
			FileInputStream fi = null;

			FileOutputStream fo = null;

			FileChannel in = null;

			FileChannel out = null;

			try {

				fi = new FileInputStream(s);

				fo = new FileOutputStream(t);

				in = fi.getChannel();// 得到对应的文件通道

				out = fo.getChannel();// 得到对应的文件通道

				long bytes = in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
				LogUtil.e("复制的文件大小：" + bytes);
				if (t.exists()) {
					LogUtil.e("文件复制成功：" + t.getAbsolutePath());
					return true;
				} else {
					LogUtil.e("文件复制失败---------------------");
					return false;
				}
			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {
					if (fi != null) {

						fi.close();
					}
					if (in != null) {

						in.close();
					}

					if (fo != null) {

						fo.close();
					}
					if (out != null) {

						out.close();
					}

				} catch (IOException e) {

					e.printStackTrace();

				}

			}
		} else {
			LogUtil.e("要复制的文件不存在------------------------");
		}
		return false;
	}

	/**
	 * @description 把bitmap保存成文件
	 * @parameters
	 */
	public static boolean createBitmapFile(Bitmap bmp, CompressFormat format,
			String path, String suffix) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = null;
		try {

			baos = new ByteArrayOutputStream();
			if (bmp != null) {
				bmp.compress(format, 100, baos);
				return createBytesFile(baos.toByteArray(), path, suffix);
			} else {
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (baos != null) {
					baos.close();
					baos = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * @description 把字节数组保存成文件
	 */
	public static boolean createBytesFile(byte[] data, String path,
			String suffix) {
		// TODO Auto-generated method stub

		BufferedOutputStream stream = null;
		try {
			File file = new File(path + "/" + suffix);
			if (file != null && file.exists()) {
				file.delete();
			}

			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(data, 0, data.length);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (stream != null) {
					stream.close();
					stream = null;
				}
				if (data != null) {
					data = null;
				}
				return true;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;

	}

	/**
	 * @description:解压缩单个文件
	 * @exception
	 */
	public static void unZipFile(String srcPath, String outPath)
			throws Exception {
		// List fileList = getSubFiles(new File(baseDir));
		ZipFile zfile = new ZipFile(srcPath);
		ZipEntry ze = null;
		byte[] buf = new byte[1024];

		OutputStream os = new BufferedOutputStream(
				new FileOutputStream(srcPath));
		InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
		int readLen = 0;
		while ((readLen = is.read(buf, 0, 1024)) != -1) {
			os.write(buf, 0, readLen);
		}
		is.close();
		os.close();
		zfile.close();
	}

	/**
	 * @description:压缩单个文件
	 * @exception
	 */
	public static boolean zipFile(String srcPath, String outPath)
			throws Exception {
		// List fileList = getSubFiles(new File(baseDir));
		File srcFile = new File(srcPath);
		if (!srcFile.exists()) {
			return false;
		}
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(srcFile));
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		int readLen = 0;

		ze = new ZipEntry(srcPath);
		ze.setSize(srcFile.length());
		ze.setTime(srcFile.lastModified());
		zos.putNextEntry(ze);
		InputStream is = new BufferedInputStream(new FileInputStream(srcFile));
		while ((readLen = is.read(buf, 0, 1024)) != -1) {
			zos.write(buf, 0, readLen);
		}
		is.close();
		zos.close();
		return true;
	}

	public static void CreateAllDanPinFile(Handler handler, String str,
			String user_id, int xingxiang_id, int flag) {
		File file = new File(PathCommonDefines.JSON_FOLDER);
		if (!file.exists()) {
			// 创建json文件夹
			file.mkdirs();
		}
		File json;
		json = new File(PathCommonDefines.JSON_FOLDER + File.separator + "u_"
				+ user_id + "xx_" + xingxiang_id + "dp.txt");
		if (json.exists()) {
			json.delete();
		}
		try {
			json.createNewFile();
			if (json.exists()) {
			}
			WriteData(handler, str, json, flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 衣柜列表
	public static void CreateFile(Handler handler, String str, String user_id,
			int flag) {

		File file = new File(PathCommonDefines.JSON_FOLDER);
		if (!file.exists()) {
			file.mkdirs();
		}
		File json;
		json = new File(PathCommonDefines.JSON_FOLDER + File.separator + "u_"
				+ user_id + "_wardrobe_list.txt");
		if (json.exists()) {
			json.delete();
		}
		try {
			json.createNewFile();
			if (json.exists()) {
			}
			WriteData(handler, str, json, flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void CreateJingPinGuanFile(Handler handler, String str,
			String user_id, int wardrobe_id, int flag) {

		File file = new File(PathCommonDefines.JSON_FOLDER);
		if (!file.exists()) {
			// 创建json文件夹
			file.mkdirs();
		}
		File json;
		json = new File(PathCommonDefines.JSON_FOLDER + File.separator + "u_"
				+ user_id + "w_" + wardrobe_id + ".txt");
		if (json.exists()) {
			json.delete();
		}
		try {
			json.createNewFile();
			if (json.exists()) {
			}
			WriteData(handler, str, json, flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createFile(Handler handler, String str, String suffix,
			String user_id, int flag) {

		File file = new File(PathCommonDefines.JSON_FOLDER);
		if (!file.exists()) {
			// 创建json文件夹
			file.mkdirs();
		}
		File json;
		json = new File(file.getAbsolutePath() + File.separator + suffix);
		if (json.exists()) {
			json.delete();
		}
		try {
			json.createNewFile();
			if (json.exists()) {
			}
			WriteData(handler, str, json, flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createFile(Handler handler, String str, String suffix,
			int flag) {

		File file = new File(PathCommonDefines.JSON_FOLDER);
		if (!file.exists()) {
			// 创建json文件夹
			file.mkdirs();
		}
		File json;
		json = new File(file.getAbsolutePath() + File.separator + suffix);
		if (json.exists()) {
			json.delete();
		}
		try {
			json.createNewFile();
			if (json.exists()) {
			}
			WriteData(handler, str, json, flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createFile2(Handler handler, String str, String dir,
			String suffix, int flag) {
		// LogUtil.e("开始创建文件---------------------");
		File f = new File(dir, suffix);
		if (f.exists()) {
			f.delete();
		}
		File file = new File(dir);
		if (!file.exists()) {
			// 创建json文件夹
			file.mkdirs();
		}
		File json;
		json = new File(file.getAbsolutePath() + "/" + suffix);
		if (json.exists()) {
			json.delete();
		}
		try {
			WriteData(handler, str, json, flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @description:把字符串保存成文件
	 */
	public static void createFile(Handler handler, String str, String dir,
			String suffix, int flag1, int flag2) {
		File folder = new File(dir);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File file = new File(dir, suffix);
		if (file.exists()) {
			file.delete();
		}
		FileOutputStream fos = null;
		try {
			if (file != null) {

				fos = new FileOutputStream(file);
				fos.write(str.getBytes());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (handler != null) {
				handler.sendEmptyMessage(flag2);

			}
		} finally {
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
					if (handler != null) {
						handler.sendEmptyMessage(flag1);

					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void deleteFile(Handler handler, String suffix,
			String user_id, int flag) {

		File file = new File(PathCommonDefines.JSON_FOLDER + File.separator
				+ user_id + File.separator + suffix);
		File file2 = new File(PathCommonDefines.JSON_FOLDER + File.separator
				+ user_id);
		if (file2.exists()) {
			file2.mkdirs();
		}
		if (file.exists()) {
			file.delete();
		}
	}

	public static void CreateDiQuFile(Handler handler, String str, int type,
			int parent, int flag) {

		File file = new File(PathCommonDefines.JSON_FOLDER);
		if (!file.exists()) {
			// 创建json文件夹
			file.mkdirs();
		}
		File json;
		json = new File(PathCommonDefines.JSON_FOLDER + File.separator
				+ "type_" + type + "parent_" + parent + ".txt");
		if (json.exists()) {
			json.delete();
		}
		try {
			json.createNewFile();
			if (json.exists()) {
			}
			WriteData(handler, str, json, flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 往文件中写入数据
	 * */
	public static void WriteData(Handler handle, String str, File file, int flag) {
		// LogUtil.e("开始写入文件数据---------------------");
		FileOutputStream fos = null;
		try {
			if (file != null) {

				fos = new FileOutputStream(file);
				fos.write(str.getBytes());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
					if (handle != null) {
						handle.sendEmptyMessage(flag);

					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 往文件中写入数据
	 * */
	public static long WriteData2(InputStream is, File file) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			if (fos != null && is != null) {
				// LogUtil.e("FileOutputStream不为空-----------");
				byte[] buffer = new byte[100];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				// LogUtil.e("InputStream数据保存到了SD卡-----------");
			} else {
				// LogUtil.e("FileOutputStream为空-----------");

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			file.delete();
		} finally {
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
				}
				if (is != null) {
					is.close();

				}
				if (file.length() > 0) {

					LogUtil.e("文件创建成功---------------");
				} else {
					LogUtil.e("文件创建失败---------------");
				}
				return file.length();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LogUtil.e("文件读取失败------------------------");
		return file.length();
	}

	/**
	 * 从SD卡读入数据
	 * */
	public static String ReadData(File file) {
		if (file != null && file.exists()) {

			String result = "";
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
				String s = null;
				while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
					result = result + s;
				}
				br.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			return result;
		} else {
			return null;
		}

	}

	/**
	 * 判断文件是否存在
	 * */
	public static boolean isExisted(File file) {
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * @description复制文件
	 * @parameters
	 */
	public static boolean fuZhiFile(File s, File t) {
		if (s != null && s.exists()) {
			if (t.exists()) {
				t.delete();
			}
			FileInputStream fi = null;

			FileOutputStream fo = null;

			FileChannel in = null;

			FileChannel out = null;

			try {

				fi = new FileInputStream(s);

				fo = new FileOutputStream(t);

				in = fi.getChannel();// 得到对应的文件通道

				out = fo.getChannel();// 得到对应的文件通道

				long bytes = in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
				LogUtil.e("复制的文件大小：" + bytes);
				if (t.exists()) {
					LogUtil.e("文件复制成功：" + t.getAbsolutePath());
					return true;
				} else {
					LogUtil.e("文件复制失败---------------------");
					return false;
				}
			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {
					if (fi != null) {

						fi.close();
					}
					if (in != null) {

						in.close();
					}

					if (fo != null) {

						fo.close();
					}
					if (out != null) {

						out.close();
					}

				} catch (IOException e) {

					e.printStackTrace();

				}

			}
		} else {
			LogUtil.e("要复制的文件不存在------------------------");
		}
		return false;
	}

	public static long createFile3(InputStream is, String dir, String suffix) {
		if (is != null) {
			LogUtil.e("输入流不为空----------------------");
			File file = new File(dir);
			if (!file.exists()) {
				// LogUtil.e( "文件夹不存在--------------------");
				// 创建json文件夹
				file.mkdirs();
			} else {
				// LogUtil.e( "文件夹存在--------------------");
			}
			File json;
			json = new File(file.getAbsolutePath() + "/" + suffix);
			return WriteData2(is, json);
		} else {
			LogUtil.e("输入流为空------------------------");
		}
		return 0;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(File file) {
		boolean flag = true;
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!file.exists() || !file.isDirectory()) {
			flag = false;
			return flag;
		}
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i]);
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i]);
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		if (file.delete()) {
			LogUtil.e("删除文件夹-----------------------");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除目录下的文件(不包括目录)
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory2(File file) {
		boolean flag = true;
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (file == null || !file.exists() || !file.isDirectory()) {
			flag = false;
			return flag;
		}
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = file.listFiles();
		if (files != null) {

			for (int i = 0; i < files.length; i++) {
				// 删除子文件
				if (files[i].isFile()) {
					flag = deleteFile(files[i]);
					if (!flag)
						break;
				} // 删除子目录
				else {
					flag = deleteDirectory(files[i]);
					if (!flag)
						break;
				}
			}
		}
		if (!flag) {
			return false;
		}
		return flag;

	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(File file) {
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			// LogUtil.e("删除文件-----------------------");
			return true;
		}
		return false;
	}

	/**
	 * 追加文件：使用FileWriter
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void appendContent(String fileName, String content) {

		FileWriter writer = null;
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}

			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(fileName, true);
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.flush();
					writer.close();
					LogUtil.e("追加内容成功-------------------");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param mHandler
	 * @param j
	 * @description 把字节数组保存成文件
	 * @parameters
	 */
	public static void createFile4(Handler mHandler, byte[] data, String path,
			String suffix, int i, int j) {
		// TODO Auto-generated method stub

		BufferedOutputStream stream = null;
		try {
			File file = new File(path + "/" + suffix);
			if (file != null && file.exists()) {
				file.delete();
			}

			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(data, 0, data.length);
		} catch (Exception e) {
			e.printStackTrace();
			mHandler.sendEmptyMessage(j);

		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
				if (data != null) {
					data = null;
				}
				if (mHandler != null) {
					mHandler.sendEmptyMessageDelayed(i, 1000);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * @description 把bitmap保存成文件
	 * @parameters
	 */
	public static void createFile5(Handler mHandler2, Bitmap bmp, String path,
			String suffix, int i, int j) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (bmp != null) {
			bmp.compress(CompressFormat.JPEG, 100, baos);
			createFile4(mHandler2, baos.toByteArray(), path, suffix, i, j);
		} else {
			mHandler2.sendEmptyMessage(j);
		}

	}
}
