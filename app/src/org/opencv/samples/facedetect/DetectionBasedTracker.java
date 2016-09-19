package org.opencv.samples.facedetect;

/**
 * @description 处理头像
 * @author 袁东华
 * @date 2014-8-27 下午9:32:57
 */
public class DetectionBasedTracker {
	/** 扣头 */
	public static native long nativeMattingHead(String headpath);

	/** 形象穿衣 */
	public static native long nativeMergeBody(String headpath, String bodypath,
			String outpath);

	/** 预处理头像，给头像画线 */
	public static native long nativeMattingHeadPreProcess(String headpath1,
			String headpath2);

	/** 预处理头像，描绘处理结果 */
	public static native long nativeMattingHeadOutline(String headpath1,
			String headpath2, int x1, int x2, int x3, int x4);

	/** 变身 */
	public static native long nativeHumanWarps(String filePath1,
			String filePath2, int height, int fat);

	/**
	 * @description:检测头
	 * @exception
	 */
	public static native long nativeHeadDetect(String inputPath,
			String outputPath);

	/**
	 * @description:磨边
	 * @exception
	 */
	public static native long nativeHeadMatting(String srcPath, String matPath,
			String outputPath);

	/**
	 * @description:调节身体高矮和胖瘦(0-5)
	 * @exception
	 */
	public static native long nativeSetWarpParam(String srcPath,
			int fattenLevel, int shortLevel);

	/**
	 * @description:调节头部位置(-5-5)
	 * @exception
	 */
	public static native long nativeChangePosition(String srcPath, int position);

	/**
	 * @description:调节头部大小(0.9-1.1)
	 * @exception
	 */
	public static native long nativeScaleHead(String srcPath, float scale);

}
