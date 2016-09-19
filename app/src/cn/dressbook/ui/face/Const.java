package cn.dressbook.ui.face;

//#define K_FORM_POST_IMG_URL @"http://admin.trimview.com/iface.php?"
//
//#define K_URL_STEP_1 @"http://admin.trimview.com/iface.php?target=model&wid=%@"
//
//#define K_URL_HEAD_STEP2 @"http://admin.trimview.com/iface.php?target=mhead&headcls=0&mid=%@"
//
//#define K_URL_MODEL_BODY_XML @"http://admin.trimview.com/iface.php?target=headmodel&head_pic_id=%@&mid=%@"
//
//
//#define K_URL_ALL_HAIR_XML_DATA @"http://admin.trimview.com/iface.php?target=hairbas&head_pic_id=%@"

public class Const {

	public static final int E_NONOE_OPERATE = 1;
	public static final int E_GETSTEP1_XML_DATA = E_NONOE_OPERATE << 10;
	public static final int E_GETSTEP1_XML_IMG = E_NONOE_OPERATE << 1;
	public static final int E_GETSTEP2_XML_HEAD_DATA = E_NONOE_OPERATE << 2;
	// ��ȡĬ����ʾͷ��
	public static final int E_GETDEFAULT_HEAD = E_NONOE_OPERATE << 3;
	public static final int E_GET_MODEL_XML_DATA = E_NONOE_OPERATE << 4;
	public static final int E_GET_MODEL_XML_SURL_IMG = E_NONOE_OPERATE << 5;
	public static final int E_GET_MODEL_XML_VURL_IMG = E_NONOE_OPERATE << 6;
	public static final int E_GET_MODEL_XML_FURL_IMG = E_NONOE_OPERATE << 7;
	// /�õ���Ӧ��Ĭ�Ϸ���xml
	public static final int E_GET_DEFAULT_HAIR_XML_DATA = E_NONOE_OPERATE << 8;
	// �õ�һ��Ĭ�ϵķ���url
	public static final int E_GET_DEFAULT_HAIR_IMG = E_NONOE_OPERATE << 9;

	// E_Nonoe_operate,
	// E_GetStep1_Xml_Data,
	// E_GetStep1_Xml_Img,
	// E_GetStep2_Xml_Head_Data,
	// //��ȡĬ����ʾͷ��
	// E_GetDefault_Head,
	// E_Get_Model_XML_Data,
	// E_Get_Model_XML_Surl_Img,
	// E_Get_Model_XML_Vurl_Img,
	// E_Get_Model_XML_Furl_Img,
	// //�õ���Ӧ��Ĭ�Ϸ���xml
	// E_Get_Default_Hair_Xml_Data,
	// //�õ�һ��Ĭ�ϵķ���url
	// E_Get_Default_Hair_Img,

	public static final int MODEL_BODY_IMAGE_SIZE_W = 720;
	public static final int MODEL_HEAD_IMAGE_SIZE_W = 200;
	public static final int MODEL_HEAD_IMAGE_ORIG_X = 265;
	public static final int MODEL_HEAD_IMAGE_ORIG_Y = 60;

	// public static final int MODEL_HEAD_IMAGE_SIZE_W = 200;

	private Const() {

	}

	public static String getDownModelUrl(String head_pic_id, String mid) {
		return "http://admin.trimview.com/iface.php?target=headmodel&head_pic_id="
				+ head_pic_id + "&mid=" + mid;
	}

	public static String getHairXmlDataUrl(String head_pic_id) {
		return "http://admin.trimview.com/iface.php?target=hairbas&head_pic_id="
				+ head_pic_id;
	}

	public static String getStep2Url(String mid) {
		return "http://admin.trimview.com/iface.php?target=mhead&headcls=0&mid="
				+ mid;
	}

	public static String getStep1Url(String wid) {
		return "http://admin.trimview.com/iface.php?target=model&wid=" + wid;
	}

}
