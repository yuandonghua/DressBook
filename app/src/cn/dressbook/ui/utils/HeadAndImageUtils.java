/**
 *@name HeadAndImageUtils.java
 *@description
 *@author 袁东华
 *@data 2014-8-26上午9:29:56
 */
package cn.dressbook.ui.utils;

import java.io.File;

import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.common.util.LogUtil;

import android.content.Context;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.PathCommonDefines;


/**
 * @description 头像处理
 * @author 袁东华
 * @date 2014-8-26 上午9:29:56
 */
public class HeadAndImageUtils {
	private static HeadAndImageUtils mHeadAndImageUtils;

	public static HeadAndImageUtils getInstance() {
		if (mHeadAndImageUtils == null) {
			mHeadAndImageUtils = new HeadAndImageUtils();
		}
		return mHeadAndImageUtils;

	}

	/**
	 * 私有化
	 */
	private HeadAndImageUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @description: 合成形象
	 * @exception
	 */
	public long heChengImage1(final Context mContext, final int xingxiangId,
			final String motePath, final String xingxiangPath) {
		long xingxiangResult = 0;
		final File xmlFile = new File(PathCommonDefines.APP_FOLDER_ON_SD + "/"
				+ motePath + ".xml");
		final File moteFile = new File(PathCommonDefines.APP_FOLDER_ON_SD + "/"
				+ motePath);
		File headfile = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiangId + "/head/camerahead.0");
		if (headfile.exists() && moteFile.exists() && xmlFile.exists()) {

			try {
				long time1 = System.currentTimeMillis();

				xingxiangResult = DetectionBasedTracker.nativeMergeBody(
						"photo_cache/"
								+ ManagerUtils.getInstance().getUser_id(
										mContext) + "/" + xingxiangId
								+ "/head/" + "camerahead.0", motePath,
						xingxiangPath);
				long time2 = System.currentTimeMillis();
				LogUtil.e("此次合成结果：" + xingxiangResult + "用时:"
						+ (time2 - time1) / 1000 + "秒");
				if (xingxiangResult == 0) {
					File XXFile = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + xingxiangPath);
					if (XXFile != null && XXFile.exists()) {

						XXFile.renameTo(new File(
								PathCommonDefines.APP_FOLDER_ON_SD + "/"
										+ xingxiangPath.replace(".jpeg", ".0")));
						LogUtil.e("形象重命名成功--------------------");
					}
					File XXSFile = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + xingxiangPath.replace(".jpeg", "s.png"));
					if (XXSFile != null && XXSFile.exists()) {

						XXSFile.renameTo(new File(
								PathCommonDefines.APP_FOLDER_ON_SD + "/"
										+ xingxiangPath.replace(".jpeg", "s.0")));
						LogUtil.e("形象S重命名成功--------------------");
						if (xmlFile != null && xmlFile.exists()) {
							xmlFile.delete();
							LogUtil.e("模特的XML文件删除成功--------------------");
						}
						if (moteFile != null && moteFile.exists()) {
							moteFile.delete();
							LogUtil.e("模特的图片文件删除成功--------------------");
						}
					}
				} else {
					if (xmlFile != null && xmlFile.exists()) {
						xmlFile.delete();
						LogUtil.e("模特的XML文件删除成功--------------------");
					}
					if (moteFile != null && moteFile.exists()) {
						moteFile.delete();
						LogUtil.e("模特的图片文件删除成功--------------------");
					}
				}

			} catch (Exception e) {
				LogUtil.e("合成形象异常-------------------");
				String motePNGFilePath = PathCommonDefines.APP_FOLDER_ON_SD
						+ "/" + motePath;
				File moteFile2 = new File(motePNGFilePath);
				if (moteFile2.exists()) {
					if (moteFile2.delete()) {
						LogUtil.e("合成形象异常，模特png图片，删除-----------------------");

					}
				}
				String moteXMLPath = PathCommonDefines.APP_FOLDER_ON_SD + "/"
						+ motePath + ".xml";
				File moteXmlFile = new File(moteXMLPath);
				if (moteXmlFile.exists()) {
					if (moteXmlFile.delete()) {
						LogUtil.e("合成形象异常，模特XML文件，删除-----------------------");
					}
				}
				String xingxiangFilePath = PathCommonDefines.APP_FOLDER_ON_SD
						+ "/" + xingxiangPath;
				File xingxiangFile = new File(xingxiangFilePath);
				if (xingxiangFile.exists()) {
					if (xingxiangFile.delete()) {
						LogUtil.e("合成形象异常，合成的形象图片，删除-----------------------");
					}
				}

			}

		} else {
			// if (!headfile.exists()) {
			// // 下载头像
			// LogUtil.e("头像地址不为空----------------------");
			// ImageExec.getInstance().downloadHeadImage(
			// null,
			// xingxiangZhao,
			// xingxiangId,
			// PathCommonDefines.PHOTOCACHE_FOLDER + "/"
			// + ManagerUtils.getInstance().getUser_id()
			// + "/" + xingxiangId + "/head", "camerahead.0",
			// NetworkAsyncCommonDefines.DELETE_WARDROBE_S, 1);
			// }
		}
		return xingxiangResult;

	}

	/**
	 * @description 合成单个形象
	 * @parameters
	 */
	public void dressOneAttireScheme(final Context mContext, String modpic,
			final String attire_id, final int xingxiang_id) {
		// TODO Auto-generated method stub
		// // 本地的模特图片路径
		String mote_path = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiang_id + "/模特/" + attire_id + ".png";
		// 本地的模特XML路径
		String mote_xml_path = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiang_id + "/模特/" + attire_id + ".png.xml";
		// 合成时，模特的路径
		final String mote_path2 = "photo_cache/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiang_id + "/模特/" + attire_id + ".png";
		// 合成时，形象的路径
		final String xingxiang_path = "photo_cache/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiang_id + "/形象/" + attire_id + ".jpeg";
		final File mote = new File(mote_path);
		final File xml = new File(mote_xml_path);

		File moteTXT = new File(mote_path + ".txt");
		String moteLength = FileSDCacher.ReadData(moteTXT);
		File xmlTXT = new File(mote_xml_path + ".txt");
		String xmlLength = FileSDCacher.ReadData(xmlTXT);

		final File head = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiang_id + "/head/" + "camerahead.0");
		//
		// // 模特图片存在和XML文件存在并且和服务端一致，头像存在
		// if (mote.exists() && xml.exists() && moteLength != null
		// && !"".equals(moteLength)
		// && moteLength.equals(mote.length() + "") && xmlLength != null
		// && !"".equals(xmlLength) && xmlLength.equals(xml.length() + "")) {
		// LogUtil.e("模特图片和XML文件都存在----------------------");
		// try {
		// // 合成的形象地址
		// String xingxiangPath = PathCommonDefines.PHOTOCACHE_FOLDER
		// + "/" + ManagerUtils.getInstance().getUser_id()
		// + "/" + xingxiang_id + "/形象/" + attire_id + ".0";
		// String xingxiangsPath = PathCommonDefines.PHOTOCACHE_FOLDER
		// + "/" + ManagerUtils.getInstance().getUser_id()
		// + "/" + xingxiang_id + "/形象/" + attire_id + "s.0";
		// File xingxiangFile = new File(xingxiangPath);
		// File xingxiangsFile = new File(xingxiangsPath);
		// if (xingxiangFile.exists() && xingxiangsFile.exists()) {
		// LogUtil.e("本地形象图片已经存在，不用合成------------------");
		// } else {
		// LogUtil.e("本地没有此形象图片，需要合成------------------");
		//
		// ManagerUtils.getInstance().getExecutorService2()
		// .execute(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// final long time1 = System
		// .currentTimeMillis();
		// long xingxiangResult = 0;
		// if (head.exists()) {
		// xingxiangResult = DetectionBasedTracker
		// .nativeMergeBody("photo_cache/"
		// + ManagerUtils
		// .getInstance()
		// .getUser_id()
		// + "/" + xingxiang_id
		// + "/head/"
		// + "camerahead.0",
		// mote_path2,
		// xingxiang_path);
		// } else {
		// // 头像不存在,去下载
		// }
		// long time2 = System.currentTimeMillis();
		// LogUtil.e("此次合成结果：" + xingxiangResult);
		// if (xingxiangResult == 0) {
		// File XXFile = new File(
		// PathCommonDefines.PHOTOCACHE_FOLDER
		// + "/"
		// + ManagerUtils
		// .getInstance()
		// .getUser_id()
		// + "/"
		// + xingxiang_id
		// + "/形象/"
		// + attire_id
		// + ".jpeg");
		// if (XXFile != null && XXFile.exists()) {
		//
		// XXFile.renameTo(new File(
		// PathCommonDefines.PHOTOCACHE_FOLDER
		// + "/"
		// + ManagerUtils
		// .getInstance()
		// .getUser_id()
		// + "/"
		// + xingxiang_id
		// + "/形象/"
		// + attire_id + ".0"));
		// // LogUtil.e("形象重命名成功--------------------");
		// }
		// File XXSFile = new File(
		// PathCommonDefines.PHOTOCACHE_FOLDER
		// + "/"
		// + ManagerUtils
		// .getInstance()
		// .getUser_id()
		// + "/"
		// + xingxiang_id
		// + "/形象/"
		// + attire_id
		// + "s.png");
		// if (XXSFile != null && XXSFile.exists()) {
		//
		// XXSFile.renameTo(new File(
		// PathCommonDefines.PHOTOCACHE_FOLDER
		// + "/"
		// + ManagerUtils
		// .getInstance()
		// .getUser_id()
		// + "/"
		// + xingxiang_id
		// + "/形象/"
		// + attire_id + "s.0"));
		// // LogUtil.e("形象S重命名成功--------------------");
		// }
		// deleteMoTeData(xingxiang_id, attire_id);
		// LogUtil.e("合成形象--成功--耗时："
		// + (time2 - time1) / 1000);
		// } else {
		//
		// LogUtil.e("合成形象--失败--耗时："
		// + (time2 - time1) / 1000);
		// deleteMoTeData(xingxiang_id, attire_id);
		// }
		// }
		// });
		// }
		//
		// } catch (Exception e) {
		// // LogUtil.e("合成形象异常-------------------");
		// if (mote.exists()) {
		// if (mote.delete()) {
		// // LogUtil.e("合成形象异常，模特png图片，删除-----------------------");
		//
		// }
		// }
		// if (xml.exists()) {
		// if (xml.delete()) {
		// // LogUtil.e("合成形象异常，模特XML文件，删除-----------------------");
		// }
		// }
		// // 合成的形象地址
		// String xingxiangPath = PathCommonDefines.PHOTOCACHE_FOLDER
		// + "/" + ManagerUtils.getInstance().getUser_id()
		// + "/" + xingxiang_id + "/形象/" + attire_id + ".jpeg";
		//
		// File xingxiangFile = new File(xingxiangPath);
		// if (xingxiangFile.exists()) {
		// if (xingxiangFile.delete()) {
		// // LogUtil.e("合成形象异常，合成的形象图片，删除-----------------------");
		// }
		// }
		// }
		// } else {
		if (modpic != null) {
			if (head.exists()) {

				if (mote.exists()) {
					mote.delete();
				}
				if (moteTXT.exists()) {

					moteTXT.delete();
				}
				if (xml.exists()) {
					xml.delete();
				}
				if (xmlTXT.exists()) {

					xmlTXT.delete();
				}
				// 开始下载图片和xml
			} else {
				LogUtil.e("照片不存在--------------------");
			}
		} else {
			LogUtil.e("下载链接不存在--------------------");
		}
	}
}
