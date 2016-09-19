/*
 * Copyright 2010-2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package cn.dressbook.ui.SnowCommon.common.util;

import java.util.HashMap;

import cn.dressbook.ui.R;

public class Constants {
	// You should replace these values with your own
	public static final String AWS_ACCOUNT_ID = "YOUR_ACCOUNT_ID";
	public static final String COGNITO_POOL_ID = "YOUR_COGNITO_POOL_ID";
	public static final String COGNITO_ROLE_UNAUTH = "YOUR_COGNITO_UNAUTH_ROLE";
	public static final String BUCKET_NAME = "YOUR_BUCKET_NAME";

	public static final String GET_COVER = "1";
	public static final String GET_JPEG = "2";
	public static final String GET_USER_PROFILE = "3";

	public static String UNFOLLOW = "unfollow";
	public static String FOLLOW = "follow";
	public static String BLOCK = "block";
	public static String UNBLOCK = "unblock";
	public static String APPROVE = "approve";
	public static String IGNORE = "ignore";
	public static String UNREQUEST = "unrequest";

	public static String REQUESTED = "requested";
	public static String FOLLOWS = "follows";

	public static String REQUESTED_BY = "requested_by";
	public static String FOLLOWED_BY = "followed_by";
	public static String BLOCKED_BY_YOU = "blocked_by_you";

	public static int COMMENT_SIZE = 20;
	public static int COMMENT_MAX_SIZE = 60;

	public static int DOWNLOAD_SUCCESS = 100;
	public static int DOWNLOAD_FAIL = 99;
	public static int COVER_GONE = 98;

	public static int IMAGE_VIEW = 1;
	public static int GIF_VIEW = 2;
	public static int PROFILE_VIEW = 3;

	public static int BITMAP = 0;
	public static int BYTE = 1;

	public static int USER_DETAIL = 2;

	public static String USER = "1";
	public static String TAGS = "2";
	public static String TAGSEARCH = "3";

	public static String USER_PAGE_COUNT = "25";

	public static String NOT_FRIEND = "+Follow";
	public static String FRIEND = "Followed";

	public static int REGISTER = 50;
	public static int REGISTER_ERROR = 51;

	public static final int FILTER_NORMAL = 0;
	public static final int FILTER_REMEMBER = 1;
	public static final int FILTER_NAGETIVE = 2;
	public static final int FILTER_RELIEF = 3;
	public static final int FILTER_BRIGHT = 4;
	public static final int FILTER_DARK = 5;
	public static final int FILTER_FUZZY = 6;
	public static final int FILTER_SHARPEN = 7;
	public static final int FILTER_SHARPEN1 = 8;
	public static final int FILTER_BUTTERFLY = 9;
	public static final int FILTER_RUNLIGHT = 10;
	public static final int FILTER_PAINT = 11;
	public static final int FILTER_CASTING = 12;
	public static final int FILTER_BLUR = 13;
	public static final int FILTER_GRAY = 14;
	public static final int FILTER_WHITE = 15;

	public static final int COUNT_FRAME_8 = 10;
	public static final int COUNT_FRAME_9 = 3;

	public static HashMap<String, String> effectCoverTypes = new HashMap<String, String>() {
		{
			put("snow1", "qingwu_1");
			put("snow2", "feiyang_2");
			put("snow3", "weimei_3");
			put("snow4", "langman_4");
			put("rain", "leiyu");
			put("water", "yudi");
			// put("leiyu","leiyu");
			put("star", "xingxing");
			put("light1", "liuguang_1");
			put("light2", "yicai_2");
			put("suiji1", "suiji_1");
			put("suiji2", "suiji_2");
			put("yanhua", "suiji_2");
			put("Normal", "");
			put("ColorPant", "color_in");
			put("Color2", "carton");
			put("Color1", "color_pencil");
			put("Bloor", "blur");
			put("White", "white");
			put("Remember", "remember");
			put("Gray", "gray");
			put("Sharpen", "sharpen");
			put("Pencil", "color_pencil");

			// put(R.string.swirl,"qingwu_1");
			// put(R.string.tempest,"feiyang_2");
			// put(R.string.wonderland,"weimei_3");
			// put(R.string.romance,"langman_4");
			// put(R.string.petrichor,"leiyu");
			// put(R.string.raindrop,"yudi");
			// //put("leiyu","leiyu");
			// put(R.string.starlight,"xingxing");
			// put(R.string.moonrise,"liuguang_1");
			// put(R.string.splendor,"yicai_2");
			// put(R.string.random1,"suiji_1");
			// put(R.string.random2,"suiji_2");
		}
	};

	public static HashMap<String, String> filterCoverTypes = new HashMap<String, String>() {
		{
			put("1", "f3");
			put("2", "f1");
			put("3", "f2");
			put("4", "f7");
			put("5", "f6");
			put("6", "f5");
			put("7", "f4");
			put("8", "f8");
			put("9", "f9");

		}
	};

	public static void setMap() {
		// effectCoverTypes.put("snow1","qingwu_1");
		// effectCoverTypes.put("snow2","feiyang_2");
		// effectCoverTypes.put("snow3","weimei_3");
		// effectCoverTypes.put("snpw4","langman_4");
		// effectCoverTypes.put("rain","yudi");
		// effectCoverTypes.put("leiyu","leiyu");
		// effectCoverTypes.put("star","xingxing");
		// effectCoverTypes.put("light1","liuguang_1");
		// effectCoverTypes.put("light2","yicai_2");
		// effectCoverTypes.put("suiji1","suiji_1");
		// effectCoverTypes.put("suiji2","suiji_2");
		// effectCoverTypes.put("Normal","");
		//
		// filterCoverTypes.put("1","xmascandy_480");
		// filterCoverTypes.put("2","xmasgift_480");
		// filterCoverTypes.put("3","xmasginger_480");
		// filterCoverTypes.put("4","kuang_3");
		// filterCoverTypes.put("5","kuang_6");
		// filterCoverTypes.put("6","kuang_7");
		// filterCoverTypes.put("7","kuang_8");
	}

	public static int getEffectName(String name) {
		switch (name) {
		case "snow1":
			return R.string.swirl;
		case "snow2":
			return R.string.tempest;
		case "snow3":
			return R.string.wonderland;
		case "snow4":
			return R.string.romance;
		case "rain":
			return R.string.petrichor;
		case "water":
			return R.string.raindrop;
		case "star":
			return R.string.starlight;
		case "light1":
			return R.string.moonrise;
		case "light2":
			return R.string.splendor;
		case "suiji1":
			return R.string.joy;
		case "suiji2":
			return R.string.random2;
		case "yanhua":
			return R.string.yanhua;
		case "Normal":
			return R.string.effect_no;
		case "snow5":
			return R.string.romance;
		case "ColorPant":
			return R.string.colorpant;
		case "Color2":
			return R.string.color2;
		case "Color1":
			return R.string.color1;
		case "Bloor":
			return R.string.bloor;
		case "White":
			return R.string.white;
		case "Remember":
			return R.string.remember;
		case "Gray":
			return R.string.gray;
		case "Sharpen":
			return R.string.sharpen;
		case "Pencil":
			return R.string.pencil;

		}
		return 0;

		// put(R.string.swirl,"qingwu_1");
		// put(R.string.tempest,"feiyang_2");
		// put(R.string.wonderland,"weimei_3");
		// put(R.string.romance,"langman_4");
		// put(R.string.petrichor,"leiyu");
		// put(R.string.raindrop,"yudi");
		// //put("leiyu","leiyu");
		// put(R.string.starlight,"xingxing");
		// put(R.string.moonrise,"liuguang_1");
		// put(R.string.splendor,"yicai_2");
		// put(R.string.random1,"suiji_1");
		// put(R.string.random2,"suiji_2");

		// return null;

	}
}
