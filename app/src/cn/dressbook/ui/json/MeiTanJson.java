package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import cn.dressbook.ui.bean.MeitanBeanArticle;
import cn.dressbook.ui.bean.MeitanBeanArticleComment;
import cn.dressbook.ui.bean.MeitanBeanArticlePhotosUrl;
import cn.dressbook.ui.common.PathCommonDefines;

/**
 * @description 解析美谈数据
 * @author 袁东华
 * @time 2015-12-9下午1:31:01
 */
public class MeiTanJson {
	private static MeiTanJson mAdviserJson;

	private MeiTanJson() {
	}

	public static MeiTanJson getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new MeiTanJson();
		}
		return mAdviserJson;
	}

	/**
	 * 解析评论
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<MeitanBeanArticleComment> analysisCmtReplys(String json)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<MeitanBeanArticleComment> mtList = null;
		JSONObject obj = new JSONObject(json);
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		if (info.isNull("cmtReplys")) {
			return null;
		}
		JSONArray cmtReplys = info.getJSONArray("cmtReplys");

		mtList = new ArrayList<MeitanBeanArticleComment>();
		for (int i = 0; i < cmtReplys.length(); i++) {
			JSONObject obji = cmtReplys.getJSONObject(i);
			if (obji == null) {
				return null;
			}
			MeitanBeanArticleComment mt = new MeitanBeanArticleComment();
			mt.setIsPraise(obji.optInt("isPraise"));
			mt.setId(obji.optLong("id"));
			mt.setTitle(obji.optString("title"));
			mt.setContent(obji.optString("content"));
			mt.setAddTimeShow(obji.optString("addTimeShow"));
			mt.setPraiseNum(obji.optLong("praiseNum"));
			mt.setUserId(obji.optLong("userId"));
			mt.setUserName(obji.optString("userName"));
			mt.setUserAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
					+ obji.optString("userAvatar"));
			mt.setUserLevel(obji.optString("userLevel"));
			if (!obji.isNull("cmtReplyImgs")) {
				JSONArray cmtReplyImgs = obji.getJSONArray("cmtReplyImgs");
				ArrayList<MeitanBeanArticlePhotosUrl> list = new ArrayList<MeitanBeanArticlePhotosUrl>();
				for (int j = 0; j < cmtReplyImgs.length(); j++) {

					JSONObject objj = cmtReplyImgs.getJSONObject(j);
					MeitanBeanArticlePhotosUrl url = new MeitanBeanArticlePhotosUrl();
					url.setUrl(PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ objj.optString("url"));
					list.add(url);
				}
				mt.setCmtReplyImgs(list);
			}

			mtList.add(mt);
		}
		return mtList;
	}

	/**
	 * 解析一篇博文
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public MeitanBeanArticle analysisBoWen(String json) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject(json);
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		if (info.isNull("cmtPosts")) {
			return null;
		}
		JSONObject cmtPosts = info.getJSONObject("cmtPosts");

		MeitanBeanArticle mt = new MeitanBeanArticle();
		mt.setIsPraise(cmtPosts.optInt("isPraise"));
		mt.setIsFollow(cmtPosts.optInt("isFollow"));
		mt.setId(cmtPosts.optLong("id"));
		mt.setTitle(cmtPosts.optString("title"));
		mt.setSubjectTags(cmtPosts.optString("subjectTags"));
		mt.setContent(cmtPosts.optString("content"));
		mt.setAddTimeShow(cmtPosts.optString("addTimeShow"));
		mt.setPraiseNum(cmtPosts.optLong("praiseNum"));
		mt.setCmdNum(cmtPosts.optLong("cmdNum"));
		mt.setUserId(cmtPosts.optLong("userId"));
		mt.setUserName(cmtPosts.optString("userName"));
		mt.setUserAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
				+ cmtPosts.optString("userAvatar"));
		mt.setUserLevel(cmtPosts.optString("userLevel"));
		if (!cmtPosts.isNull("cmtPostsImgs")) {
			JSONArray cmtPostsImgs = cmtPosts.getJSONArray("cmtPostsImgs");
			ArrayList<MeitanBeanArticlePhotosUrl> list = new ArrayList<MeitanBeanArticlePhotosUrl>();
			for (int j = 0; j < cmtPostsImgs.length(); j++) {

				JSONObject objj = cmtPostsImgs.getJSONObject(j);
				MeitanBeanArticlePhotosUrl url = new MeitanBeanArticlePhotosUrl();
				url.setUrl(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ objj.optString("url"));
				list.add(url);
			}
			mt.setCmtPostsImgs(list);
		}

		return mt;
	}

	/**
	 * 解析我的关注列表
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<MeitanBeanArticle> analysisWDGZList(String json)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<MeitanBeanArticle> mtList = null;
		JSONObject obj = new JSONObject(json);
		if (obj.isNull("info")) {
			return null;
		}
		JSONObject info = obj.getJSONObject("info");
		if (info.isNull("cmtPosts")) {
			return null;
		}
		JSONArray cmtPosts = info.getJSONArray("cmtPosts");

		mtList = new ArrayList<MeitanBeanArticle>();
		for (int i = 0; i < cmtPosts.length(); i++) {
			JSONObject obji = cmtPosts.getJSONObject(i);
			if (obji == null) {
				return null;
			}
			MeitanBeanArticle mt = new MeitanBeanArticle();
			mt.setIsPraise(obji.optInt("isPraise"));
			mt.setIsFollow(obji.optInt("isFollow"));
			mt.setId(obji.optLong("id"));
			mt.setTitle(obji.optString("title"));
			mt.setSubjectTags(obji.optString("subjectTags"));
			mt.setContent(obji.optString("content"));
			mt.setAddTimeShow(obji.optString("addTimeShow"));
			mt.setPraiseNum(obji.optLong("praiseNum"));
			mt.setCmdNum(obji.optLong("cmdNum"));
			mt.setUserId(obji.optLong("userId"));
			mt.setUserName(obji.optString("userName"));
			mt.setUserAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
					+ obji.optString("userAvatar"));
			mt.setUserLevel(obji.optString("userLevel"));
			if (!obji.isNull("cmtPostsImgs")) {
				JSONArray cmtPostsImgs = obji.getJSONArray("cmtPostsImgs");
				ArrayList<MeitanBeanArticlePhotosUrl> list = new ArrayList<MeitanBeanArticlePhotosUrl>();
				for (int j = 0; j < cmtPostsImgs.length(); j++) {

					JSONObject objj = cmtPostsImgs.getJSONObject(j);
					MeitanBeanArticlePhotosUrl url = new MeitanBeanArticlePhotosUrl();
					url.setUrl(PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ objj.optString("url"));
					list.add(url);
				}
				mt.setCmtPostsImgs(list);
			}

			mtList.add(mt);
		}
		return mtList;
	}

}
