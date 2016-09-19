package cn.dressbook.ui.common;

import android.os.Environment;

/**
 * 与路径相关的常量
 * 
 * @author 袁东华
 * 
 */
public class PathCommonDefines {
	/**
	 * 阿里百川图片地址
	 */
	public final static String ALBC_IMAGE = "http://gd2.alicdn.com/bao/uploaded/";
	/**
	 * 模特地址
	 */
	public final static String SERVER_MOTE = "http://st.dressbook.cn/data/model/";
	/**
	 * java服务端地址
	 */
	public final static String SERVER2 = "http://ifc.dressbook.cn";
	public final static String SERVER_ADDRESS = "http://st.dressbook.cn/";
	public final static String DIZHI = "http://st.dressbook.cn/";
	// 服务器端图片地址
	public final static String SERVER_IMAGE_ADDRESS = "http://st.dressbook.cn/";
	/**
	 * 店家收钱
	 */
	public final static String DJSQ = SERVER2 + "/wtDzShowCollectNew.json";
	/**
	 * 获取店家收钱列表
	 */
	public final static String GET_DJSQ_LIST = SERVER2
			+ "/mbShopCollectList.json";
	/**
	 * 获取提现记录列表
	 */
	public final static String GETTIXIANJILULIST = SERVER2
			+ "/mbTransList.json";
	/**
	 * 申请余额提现
	 */
	public final static String SHENQINYUETIXIAN = SERVER2
			+ "/wtApplayTransMoney.json";
	/**
	 * 获取客户列表
	 */
	public final static String GET_KEHU_LIST = SERVER2 + "/mbTree.json";
	/**
	 * 获取预期收入列表
	 */
	public final static String GET_YQSR_LIST = SERVER2 + "/mbExpectIncome.json";
	/**
	 * 获取交易记录
	 */
	public final static String GET_JYJL_LIST = SERVER2 + "/mbLog.json";
	/**
	 * 获取店铺收钱记录
	 */
	public final static String GET_DPSQ_LIST = SERVER2
			+ "/dzFranchiseesIncomeGet.json";
	/**
	 * 获取交易记录标识
	 */
	public final static String GET_JYJLBZ_LIST = SERVER2 + "/mbLogReason.json";
	/**
	 * 获取店铺列表
	 */
	public final static String GET_DP_LIST = SERVER2
			+ "/dzFranchiseesByEmpGet.json";
	/**
	 * 阿里百川数据记录
	 */
	public final static String ALBCLOGREC = SERVER2 + "/albcLogRec.json";
	/**
	 * 删除试衣文件
	 */
	public final static String DELETE_SY_FILE = SERVER2 + "/tryResultDel.json";
	/**
	 * 上传试衣文件
	 */
	public final static String UPDATELOAD_SY_FILE = SERVER2
			+ "/tryResultUpload.json";
	/**
	 * 上传量体数据
	 */
	public final static String UPLOAD_LTSJ = SERVER2
			+ "/wtMbFigureDataSubmit.json";
	/**
	 * 刷新
	 */
	public final static String INITUSER = SERVER2 + "/wtAppInit.json";
	/**
	 * 获取广告信息
	 */
	public static String GET_GG_INFO = SERVER2 + "/vipBaoAd.json";
	/**
	 * 获取广告轮播信息
	 */
	public static String GET_GG_LBT_INFO = SERVER2 + "/slideAd.json";

	/**
	 * 上传试衣图片
	 */
	public static String UPLOAD_TRYON_IMAGE = SERVER2
			+ "/uploadUserFigure.json";
	/**
	 * 获取分享试衣内容
	 */
	public static String GET_SHARE_TRYON = SERVER2
			+ "/getUploadUserFigurePath.json";
	/**
	 * 上传联系人
	 */
	public final static String CONTACTS = SERVER2 + "/wtUpdUserPhonebook.json";
	/**
	 * 获取配置内容
	 */
	public static String PEIZHI = SERVER2 + "/configGet.json";
	/**
	 * 参加项目
	 */
	public static String CANJIA_PROJECT = SERVER2 + "/wtDonateProjectJoin.json";
	/**
	 * 获取爱心捐衣列表
	 */
	public static String GET_AIXINJUANYI_LIST = SERVER2
			+ "/donateProjectList.json";
	/**
	 * 获取财富信息
	 */
	public static String GET_YJTCLIST = SERVER2 + "/mbInfo.json";
	/**
	 * 获取我的数据列表
	 */
	public static String GET_LTSJLIST = SERVER2 + "/mbFigureDataGet.json";
	/**
	 * 领取
	 */
	public static String LINGQU = SERVER2 + "/wtDonateYqApply.json";
	/**
	 * 获取爱心记录列表
	 */
	public static String GET_AXJL_LIST = SERVER2 + "/donateProjectListMy.json";
	/**
	 * 获取项目详情
	 */
	public static String GET_AXJY_PROJECT = SERVER2
			+ "/donateProjectDetail.json";
	/**
	 * 发评论
	 */
	public static String FA_PINGLUN = SERVER2 + "/wtCmtReplySubmit.json";
	/**
	 * 获取博文详情
	 */
	public static String GET_BOWEN_INFO = SERVER2 + "/cmtPostsDetail.json";
	/**
	 * 获取美谈社区广场
	 */
	public static String GET_MEITAN_SQGC = SERVER2 + "/cmtPostsList.json";
	/**
	 * 获取我的关注列表
	 */
	public static String GET_WDGZ_LIST = SERVER2 + "/cmtFollowPostsList.json";
	/**
	 * 获取用户主页信息
	 */
	public static String GET_USER_ZHUYE_INFO = SERVER2 + "/cmtHomepageGet.json";
	/**
	 * 关注用户
	 */
	public static String GUANZHU_USER = SERVER2 + "/wtCmtFollow.json";
	/**
	 * 取消关注用户
	 */
	public static String QUXIAO_GUANZHU_USER = SERVER2
			+ "/wtCmtFollowCancel.json";
	/**
	 * 举报博文
	 */
	public static String JUBAOBOWEN = SERVER2
			+ "/wtComplaintsReportSubmit.json";
	/**
	 * 取消点赞
	 */
	public static String QUXIAODIANZAN = SERVER2
			+ "/wtCmtPostsPraiseCancel.json";
	/**
	 * 点赞
	 */
	public static String DIANZAN = SERVER2 + "/wtCmtPostsPraise.json";

	/**
	 * 对评论点赞
	 */
	public static String DIANZAN_PINGLUN = SERVER2 + "/wtCmtReplyPraise.json";

	/**
	 * 对评论取消点赞
	 */
	public static String QUXIAO_DIANZAN_PINGLUN = SERVER2
			+ "/wtCmtReplyPraiseCancel.json";
	/**
	 * 支持项目
	 */
	public static String ZHICHI_XIANGMU = SERVER2
			+ "/wtDonateProjectPraise.json";
	/**
	 * 获取用户信息
	 */
	public final static String GET_USER_INFO = SERVER2 + "/userInfoGet.json";
	/**
	 * 根据用户手机号获取用户信息
	 */
	public final static String GETUSERBYPHONE = SERVER2
			+ "/getUserByPhone.json";
	/**
	 * 获取今日推荐顾问师
	 */
	public final static String GET_RECOMMEND_INFO = SERVER2
			+ "/adviserStar.json";
	/**
	 * 获取搜索百川改造
	 */
	public final static String GOLABLE_SEARCH = SERVER2 + "/globeSearch.json?";
	/**
	 * 发送评论
	 */
	public final static String SEND_COMMENT = SERVER2
			+ "/wtBuyerResponseComment.json";
	/**
	 * 获取评论列表
	 */
	public final static String GET_COMMENTLIST = SERVER2
			+ "/buyerResponseDetail.json";
	/**
	 * 获取推荐详情
	 */
	public final static String GET_RECOMMENDLIST = SERVER2
			+ "/buyerResponseDetail.json";
	/**
	 * 喜欢响应
	 */
	public final static String XH_RECOMMEND = SERVER2
			+ "/wtBuyerResponsePraise.json";
	/**
	 * 喜欢评论
	 */
	public final static String XH_RECOMMEND2 = SERVER2
			+ "/wtBuyerResponseCommentPraise.json";
	/**
	 * 举报评论
	 */
	public final static String JB_RECOMMEND2 = SERVER2
			+ "/wtComplaintsReportSubmit.json";
	/**
	 * 获取需求详情
	 */
	public final static String GET_REQUIREMENTINFO = SERVER2
			+ "/buyerRequestDetail.json";

	/**
	 * 推荐服装商品
	 */
	public final static String RECOMMEND_CLOTHING_ALBC = SERVER2
			+ "/wtBuyerResp.json";
	/**
	 * 搜索服装方案阿里百川
	 */
	public final static String SEARCH_CLOTHING_ALBC = SERVER2
			+ "/buyerSearch.json";
	/**
	 * 获取标签
	 */
	public static String GET_BQ = SERVER2 + "/catAttrGet.json";
	/**
	 * 够了
	 */
	public final static String GL = SERVER2 + "/wtBuyerRequestEnough.json";
	/**
	 * 获取其他需求列表
	 */
	public final static String GET_OTHERREQUIREMENTLIST_FROM_SERVER = SERVER2
			+ "/buyerRequestList.json";
	/**
	 * 获取服装品类
	 */
	public static String GET_PL = SERVER2 + "/catTreeRbdGet.json";
	/**
	 * 获取顾问模块更新信息
	 */
	public static String GET_ADVISER_UPDATE_INFO = SERVER2 + "/getGlmTemp.json";
	/**
	 * 获取生活照
	 */
	public static String GET_LIFEPHOTO = SERVER2 + "/photoList.json";
	/**
	 * 删除生活照
	 */
	public static String DELETE_LIFEPHOTO = SERVER2 + "/wtPhotoDelete.json";
	/**
	 * 获取美衣讯
	 */
	public static String MYX = SERVER2 + "/getBtArts.json";
	/**
	 * 获取量身定制分类列表
	 */
	public static String LSDZFL_LIST = SERVER2 + "/dzClsList.json";
	/**
	 * 获取定制商品详情
	 */
	public static String GET_DZSP_DETAILS = SERVER2 + "/dzAttireDetail.json";
	/**
	 * 获取定制商品刺绣信息
	 */
	public static String GET_DZSP_CX = SERVER2 + "/dzEmbdGet.json";
	/**
	 * 获取定制商品的补充信息
	 */
	public static String GET_DZSP_BCXX = SERVER2 + "/wtDzSubmit.json";
	/**
	 * 获取定制商品的补充信息
	 */
	public static String TJ_BCXX = SERVER2 + "/wtDzEmbdSubmit.json";
	/**
	 * 获取量身定制列表
	 */
	public static String LSDZ_LIST = SERVER2 + "/dzList.json";
	/**
	 * 获取种类定制列表
	 */
	public static String ZLDZ_LIST = SERVER2 + "/dzListByCls.json";
	/**
	 * 获取DIY列表
	 */
	public static String DIY_LIST = SERVER2 + "/dzAttireDetailDiyNew.json";
	/**
	 * 上传DIY列表
	 */
	public static String PULL_UP_DIY_LIST = SERVER2 + "/wtDzSubmit.json";
	/**
	 * 衣保会员
	 */
	public static String YBHY = SERVER2 + "/vipAttiresGet.json";
	/**
	 * 上传生活照
	 */
	public static String UPLOAD_LIFEPHOTOS = SERVER2 + "/wtPhotoUpload.json";
	/**
	 * 上传衣柜文件
	 */
	public static String UPLOAD_WARDROBE_FILE = SERVER2
			+ "/wtNewWardrobeUpload.json";
	/**
	 * 发博文
	 */
	public static String FA_BOWEN = SERVER2 + "/wtCmtPostsSubmit.json";
	/**
	 * 获取诊断结果
	 */
	public static String GET_DIAGNOSE_RESULT = SERVER2 + "/TestResultGet.json";
	/**
	 * 获取顾问师列表
	 */
	public static String GET_COUNSELOR_LIST = SERVER2 + "/adviserList.json";
	/**
	 * 修改衣柜信息
	 */
	public static String EDIT_WARDROBE = SERVER2 + "/wtNewWardrobeUpdate.json";
	/**
	 * 获取衣柜信息
	 */
	public static String GET_WARDROBE = SERVER2 + "/wtNewWardrobeGet.json";
	/**
	 * 创建衣柜
	 */
	public static String CREATE_WARDROBE = SERVER2
			+ "/wtNewWardrobeCreate.json";
	/**
	 * 申请售后
	 */
	public static String APPLYAFTERSALE = SERVER2
			+ "/wtOrderAfterSaleApply.json";
	/**
	 * 支付成功-定制商品
	 */
	public static String PAYMENT_SUCCESS_DZSP = SERVER2
			+ "/wtDzOrderPaymentSucc.json";
	/**
	 * 确认收货
	 */
	public static String RECEIPT_GOODS = SERVER2 + "/wtOrderReceipt.json";
	/**
	 * 申请退货
	 */
	public static String SHENQINGTUIHUO = SERVER2 + "/wtOrderRefunds.json";
	/**
	 * 取消订单
	 */
	public static String CANCEL_ORDER = SERVER2 + "/wtOrderCancel.json";
	/**
	 * 获取订单详情
	 */
	public static String GET_ORDER_INFO = SERVER2 + "/orderGet.json";
	/**
	 * 提交订单
	 */
	public static String SUBMIT_ORDER = SERVER2 + "/wtOrderSubmit.json";
	/**
	 * 编辑购物车中的商品
	 */
	public static String EDIT_SHOPPINGCART = SERVER2 + "/wtCartUpd.json";
	/**
	 * 提交购物车中的商品
	 */
	public static String SUBMIT_SHOPPINGCART = SERVER2 + "/wtCartBatchUpd.json";
	/**
	 * 删除购物车中的商品
	 */
	public static String DELETE_SHOPPINGCART = SERVER2 + "/wtCartDel.json";
	/**
	 * 获取订单列表
	 */
	public static String GET_ORDERLIST = SERVER2 + "/orderListGroup.json";
	/**
	 * 加入购物车
	 */
	public static String ADD_SHOPPINGCART = SERVER2 + "/wtCartAdd.json";
	/**
	 * 上传用户头像
	 */
	public static String EDIT_USERHEADIMAGE = SERVER2 + "/wtAvatarUpd.json";
	/**
	 * 删除收货地址
	 */
	public final static String DELETE_ADDRESS = SERVER2 + "/wtAddressDel.json";
	/**
	 * 设置默认收货地址
	 */
	public final static String SET_DEFAULT_ADDRESS = SERVER2
			+ "/wtAddressStateUpd.json";
	/**
	 * 编辑收货地址
	 */
	public final static String EDIT_ADDRESS = SERVER2 + "/wtAddressUpd.json";
	/**
	 * 添加收货地址
	 */
	public final static String ADD_ADDRESS = SERVER2 + "/wtAddressSet.json";
	/**
	 * 收货地址
	 */
	public final static String GET_ADDRESS_LIST = SERVER2
			+ "/addressListForUser.json";
	/**
	 * 确认收货
	 */
	public final static String CONFIRMATION_RECEIPT = SERVER2
			+ "/wtShoppingReceipt.json";
	/**
	 * 评价买手
	 */
	public final static String EVALUATE_BUYER = "/wtShoppingCommentSubmit.json";
	/**
	 * 获取所有已完成需求个数
	 */
	public final static String GET_ALLFINISHREQUIREMENTLIST_FROM_SERVER = "/shoppingFinishedCnt.json";
	/**
	 * 获取需求列表
	 */
	public final static String GET_REQUIREMENTLIST_FROM_SERVER = SERVER2
			+ "/buyerRequestListMy.json";
	/**
	 * 扫描邀请码
	 */
	public final static String SAO_YQM = SERVER2 + "/wtMbRelationCheck.json";
	/**
	 * 获取用户的支付宝信息
	 */
	public final static String GET_USER_ZFB = SERVER2 + "/userAlipayGet.json";
	/**
	 * 获取用户的衣保计划
	 */
	public final static String GET_YBPLAN = SERVER2 + "/userWealthGet.json";
	/**
	 * 获取消费记录
	 */
	public final static String GET_EXPENSE_RECORD = SERVER2
			+ "/userWealthLog.json";
	/**
	 * 退保
	 */
	public final static String QUIT_YBJ = "/wtYbStop.json";

	/**
	 * 用户签到的接口
	 */
	public final static String SIGNIN = SERVER2 + "/wtUserSignIn.json";
	/**
	 * 绑定支付宝信息
	 */
	public final static String BIND_ZFB = SERVER2 + "/wtAlipayBind.json";
	/**
	 * 获取买手信息
	 */
	public final static String GET_BUYER = "/shoppingBuyerGet.json";
	/**
	 * 获取客服列表的接口
	 */
	public final static String GET_CUSTOMSERVICE_LIST = "/kfListGet.json";
	/**
	 * 获取一个客服的接口
	 */
	public final static String GET_CUSTOMSERVICE = SERVER2
			+ "/kfCurrentGet.json";
	/**
	 * 获取员工列表
	 */
	public final static String GET_YUANGONG_LIST = SERVER2
			+ "/dzMyEmpsGet.json";
	/**
	 * 添加员工
	 */
	public final static String ADDYUANGONG = SERVER2 + "/wtDzEmpAdd.json";
	/**
	 * 获取融云token
	 */
	public final static String GET_TOKEN = SERVER2 + "/rongTokenGet.json";

	/** 选择推荐商品 */
	public final static String SELECT_RECOMENDPRODUCT = "/wtShoppingAttSel.json";
	/** 完成需求 */
	public final static String COMPLETE_REQUIREMENT = SERVER2
			+ "/wtShoppingFinish.json";
	/** 终止需求 */
	public final static String STOP_REQUIREMENT = SERVER2
			+ "/wtShoppingEnd.json";
	/** 发布需求 */
	public final static String PUBLISHREQUIREMENT = SERVER2
			+ "/wtBuyerRequestAdd.json";
	/** 获取分享页面 */
	public final static String SHAREGETCONTENT = "/shareGetContent.json";
	/** 提交用户反馈 */
	public final static String SUBMIT_USER_FEEDBACK = SERVER2
			+ "/wtUserCommentSend.json";
	/** 登陆 */
	public final static String LOGIN = SERVER2 + "/wtUserLogin.json";
	/** 注册 */
	public final static String REGISTER = SERVER2 + "/wtRegister.json";
	/** 获取token */
	public final static String GETTOKEN = "/rongTokenGet.json";
	/** 关于 */
	public final static String API_GET_ABOUT = "/aboutImg.json";
	/** 注册 */
	public final static String ZHUCE = "/wtRegister.json";
	/** 获取更多方案 */
	public final static String GET_MORE_ATTIRE_LIST = SERVER2
			+ "/wtWardrobeCapacityAdd.json";
	/** 获取验证码 */
	public final static String FSYZM = SERVER2 + "/secCodeSend.json";
	/**
	 * 获取顾问师详情
	 */
	public final static String GET_ADVISERDETAIL = SERVER2 + "/adviserGet.json";
	/** 绑定手机号 */
	public final static String BANGDING = "/wtUserPhoneBind.json";
	/** 上传衣柜的形象照 */
	public final static String UPLOAD_WARDROBE_XINGXIANGZHAO = "/wtUpdateWardrobeCover.json";
	/** 上传衣柜的头像 */
	public final static String UPLOAD_WARDROBE_HEAD = "/wtUserHeadUpload.json";
	/**
	 * 获取用户积分
	 * */
	public final static String GETJIFEN = "/usersGetRankPoints.json";
	/**
	 * 获取邀请注册短信内容
	 * */
	public final static String GETDXNR = "/shareGetInviteSms.json";
	/**
	 * 获取穿衣典界面的图片文件
	 */
	public final static String GETFILE = "/data/dbcache/applogo/applogo.txt";
	/**
	 * 验证用户是否创建
	 */
	public final static String YANZHENGUSER = "/wtCreateUser.json";
	/**
	 * 验证手机号绑定情况
	 */
	public final static String YANZHENGSJH = "/wtUserVerify.json";
	/**
	 * 升级衣柜
	 */
	public final static String UPDATE_WARDROBE = "/wtWadrobeClientSet.json";
	/**
	 * 获取分享内容
	 */
	public final static String SHARECONTENT = "/shareGetContent.json";
	/**
	 * 检测是否下载
	 */
	public final static String CHECK_DOWNLOAD = SERVER2
			+ "/cacheFilesTimeGet.json";
	/**
	 * 获取设计师列表
	 */
	public final static String GET_ADVISER_LIST_FROM_SERVER = SERVER2
			+ "/adviserList.json";
	/**
	 * 赞
	 */
	public final static String ZAN_SHEJISHI = "/wtAdviserzanAdd.json";
	/**
	 * 删除衣柜
	 */
	public final static String API_DELETE_WARDROBE = "/wtWardrobeDel.json";
	/**
	 * 提交用户分享结果
	 */
	public final static String TIJIAOFENXIANG = "/TestResultGet.json";
	/**
	 * 获取用户着装指南
	 */
	public final static String GET_ZHUOZHUANGZHINAN = "/wdbKnowledgeGet.json";
	/**
	 * 获取知识库
	 */
	public final static String GETZHISHIKU = "/knowlegeGet.json";
	/**
	 * 发起集结
	 */
	public final static String FAQIJIJIE = "/wtNjjStart.json";
	/**
	 * 确认集结号
	 */
	public final static String CONFIRM_JJH = "/wtNjjConfirm.json";
	/**
	 * 获取全部集结号
	 */
	public final static String GET_JJH_LIST = "/njjList.json";
	/**
	 * 丢弃集结号
	 */
	public final static String DISCARD_JJH = "/wtNjjCancel.json";
	/**
	 * 已发起
	 */
	public final static String YFQ = "/jjhGetAsMember.json";
	/**
	 * 被邀请
	 */
	public final static String BYQ = "/jjhGetAsMember.json";
	/**
	 * 参与集结号
	 */
	public final static String CY = "/wtJjhJoin.json";
	/**
	 * 发送评论
	 */
	public final static String FSPL = "/wtJjhCommentPost.json";
	/**
	 * 查询评论
	 */
	public final static String CXPL = "/jjhGetComments.json";
	/**
	 * 删除集结号
	 */
	public final static String DELETE_JJH = "/wtJjhMemberDelete.json";
	/**
	 * 获取一个模特体型
	 */
	public final static String GETMOTE = "/shapeInputGetModel.json";
	/**
	 * 模特体型的目录
	 */
	public final static String GETMOTEPATH = "/data/model";
	/**
	 * 创建形象第二步
	 */
	public final static String CJXX2 = "/shapeInput.json";
	/**
	 * 创建形象第三步
	 */
	public final static String CJXX3 = "/wtWardrobeCreate.json";
	/**
	 * 删除创建形象
	 */
	public final static String SHANCHU_CJXX = "/tempPhotoDelete.json";
	/**
	 * 发送邀请
	 */
	public final static String YQZC = "/wtInviteRec.json";

	/**
	 * 修改用户名称
	 */
	public final static String API_GET_MODIFY_USER_NAME = SERVER2
			+ "/wtNicknameSet.json";
	/**
	 * 修改用户密码
	 */
	public final static String MODIFY_PASSWORD = SERVER2
			+ "/wtPasswordSet.json";
	/**
	 * 上传用户头像
	 */
	public final static String UPLOAD_HEAD = "/headPhotoUpload.json";
	/** 应用在SD卡下的路径 */
	public static final String APP_FOLDER_ON_SD = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/DressBook";
	/** 用户图片保存目录 */
	public static final String CYD = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/穿衣典";
	/** 头像目录 */
	public static final String HEAD = APP_FOLDER_ON_SD + "/head";
	/** 图片缓存文件夹 */
	public static final String PHOTOCACHE_FOLDER = APP_FOLDER_ON_SD
			+ "/photo_cache";
	/** 临时形象 */
	public static final String XINGXIANG = APP_FOLDER_ON_SD + "/xingxiang";
	/** 形象照的模特文件夹 */
	public static final String MOTE = APP_FOLDER_ON_SD + "/mote";
	/** 明星头像文件夹 */
	public static final String MINGXING = APP_FOLDER_ON_SD + "/mingxing";
	/** 明星头像文件夹 */
	public static final String MINGXINGZHAO = APP_FOLDER_ON_SD
			+ "/mingxingzhao";
	/** 拍照 */
	public static final String PAIZHAO = APP_FOLDER_ON_SD + "/paizhao";
	/** 生活照 */
	public static final String LIFEPHOTOS = APP_FOLDER_ON_SD + "/lifephotos";
	/** 图片默认文件夹（出错的情况下的保存文件夹） */
	public static final String PHOTOERROE = APP_FOLDER_ON_SD + "/moren";
	/** 相册 */
	public static final String XIANGCE = APP_FOLDER_ON_SD + "/xiangce";
	/** 存放json数据 */
	public static final String JSON_FOLDER = APP_FOLDER_ON_SD + "/json_cache";
	public static final String MY_FAVOURITE_FOLDER = APP_FOLDER_ON_SD
			+ "/my_favourite";
	/**
	 * 创建用户时的头像缓存
	 */
	public static final String USER_AVATAR_FOLDER = APP_FOLDER_ON_SD
			+ "/avatar";

	public final static String FOTOCACHE_MAPPING_FILE = "fotocache_mapping";
	/**
	 * 衣柜目录
	 */
	public static final String WARDROBE = APP_FOLDER_ON_SD + "/wardrobe";
	/**
	 * 买手目录
	 */
	public static final String BUYER = APP_FOLDER_ON_SD + "/buyer";
	/**
	 * 衣柜头像目录
	 */
	public static final String WARDROBE_HEAD = WARDROBE + "/head";
	/**
	 * 量身推荐
	 */
	public static final String LSTJ = APP_FOLDER_ON_SD + "/LSTJ";
	/**
	 * 衣保会员
	 */
	public static final String YBHY_FOLDER = APP_FOLDER_ON_SD + "/YBHY";
	/**
	 * 试穿
	 */
	public static final String WARDROBE_TRYON = WARDROBE + "/tryOn";
	/**
	 * 试穿模特
	 */
	public static final String WARDROBE_MOTE = WARDROBE + "/mote";
	/**
	 * 评论
	 */
	public static final String PINGLUN = APP_FOLDER_ON_SD + "/pinglun";
	/**
	 * 美谈
	 */
	public static final String MEITAN = APP_FOLDER_ON_SD + "/meitan";
	/**
	 * 顾问师
	 */
	public static final String COUNSELOR = APP_FOLDER_ON_SD + "/counselor";
	/**
	 * 分享目录
	 */
	public static final String SHARE = APP_FOLDER_ON_SD + "/share";
	/**
	 * 消息目录
	 */
	public static final String MESSAGE = APP_FOLDER_ON_SD + "/message";
	/**
	 * 订单
	 */
	public static final String ORDER = APP_FOLDER_ON_SD + "/order";
	/**
	 * 二维码
	 */
	public static final String ERWEIMA = APP_FOLDER_ON_SD + "/qr";

}
