package cn.dressbook.ui.face;

import android.graphics.BitmapFactory;
import android.util.Log;
import cn.dressbook.ui.face.component.BaseComponent;
import cn.dressbook.ui.face.data.FCBaseXmlItem;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.data.UserHairData;
import cn.dressbook.ui.face.http.HttpManager;
import cn.dressbook.ui.face.http.HttpManagerImp;
import cn.dressbook.ui.face.xml.FCXmlParser;

/**
 * �����������ݿ�����, Ϊ�˱���MainActivity��ȥ�Ӵ󣬵�����ȡ����
 * 
 * @author SKY
 * 
 */
public class HttpController implements HttpManagerImp {

	int curHttpMode = Const.E_NONOE_OPERATE;

	MainActivity iMainActivity;
	HttpManager iHttpManager = null;

	public HttpController(MainActivity aMainActivity) {
		this.iMainActivity = aMainActivity;
		iHttpManager = new HttpManager(this);
	}

	public void startStep1(String url) {
		curHttpMode = Const.E_GETSTEP1_XML_DATA;
		iHttpManager.addTask(url, "", true, curHttpMode);
	}

	// @Override
	// public void onHttpResponse(String httpData, int taskID) {
	// // TODO Auto-generated method stub
	// Log.e("HTTP", httpData);
	// }

	@Override
	public void onHttpResponseIntime(int alreadyLen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHttpError(int taskID) {
		// TODO Auto-generated method stub
		iMainActivity.showHttpErr(curHttpMode);
	}

	@Override
	public void onHttpResponse(byte[] resultByteArr, int taskID) {
		// TODO Auto-generated method stub
		switch (taskID) {
		case Const.E_GETSTEP1_XML_DATA: {
			String s = new String(resultByteArr);
			parserStep1(s);
			Log.e("HTTP", s);
			break;
		}
		case Const.E_GETSTEP1_XML_IMG: {
			if (resultByteArr.length == 0) {
				iMainActivity.showHttpErr(curHttpMode);
			} else {
				SingletonDataMgr mgr = SingletonDataMgr.getInstance();
				mgr.iStep1Item.vurlImg = BitmapFactory.decodeByteArray(
						resultByteArr, 0, resultByteArr.length);
				String url = Const.getStep2Url(mgr.iStep1Item.itemID);
				curHttpMode = Const.E_GETSTEP2_XML_HEAD_DATA;
				iHttpManager.addTask(url, "", true, curHttpMode);
			}
			break;
		}
		case Const.E_GETSTEP2_XML_HEAD_DATA: {
			String s = new String(resultByteArr);
			SingletonDataMgr mgr = SingletonDataMgr.getInstance();
			mgr.iHeadClsList = FCXmlParser.parserHeadcls(s);

			if (mgr.iHeadClsList == null || mgr.iHeadClsList.size() == 0) {
				iMainActivity.showHttpErr(curHttpMode);
			} else {
				String url = mgr.getDefaultHeadImgUrl();
				curHttpMode = Const.E_GETDEFAULT_HEAD;
				iHttpManager.addTask(url, "", true, curHttpMode);
			}

			break;
		}
		case Const.E_GETDEFAULT_HEAD: {

			if (resultByteArr.length == 0) {
				iMainActivity.showHttpErr(curHttpMode);
			} else {
				SingletonDataMgr mgr = SingletonDataMgr.getInstance();
				mgr.iDefaultHeadItem.vurlImg = BitmapFactory.decodeByteArray(
						resultByteArr, 0, resultByteArr.length);
				iMainActivity.showComponnet(BaseComponent.HEAD_COMPONENT);
			}
			break;
		}
		case Const.E_GET_MODEL_XML_DATA: {
			String s = new String(resultByteArr);
			Log.e("HTTP", s);
			SingletonDataMgr mgr = SingletonDataMgr.getInstance();
			mgr.iModelBodyItem = FCXmlParser.parserModelXml(s);

			if (mgr.iModelBodyItem == null) {
				iMainActivity.showHttpErr(curHttpMode);
			} else {
				curHttpMode = Const.E_GET_MODEL_XML_SURL_IMG;
				iHttpManager.addTask(mgr.iModelBodyItem.surl, "", true,
						curHttpMode);
			}
			break;
		}
		case Const.E_GET_MODEL_XML_SURL_IMG: {
			if (resultByteArr.length == 0) {
				iMainActivity.showHttpErr(curHttpMode);
			} else {
				SingletonDataMgr mgr = SingletonDataMgr.getInstance();
				mgr.iModelBodyItem.surlImg = BitmapFactory.decodeByteArray(
						resultByteArr, 0, resultByteArr.length);
				curHttpMode = Const.E_GET_MODEL_XML_VURL_IMG;
				iHttpManager.addTask(mgr.iModelBodyItem.vurl, "", true,
						curHttpMode);
			}
			break;
		}
		case Const.E_GET_MODEL_XML_VURL_IMG: {
			if (resultByteArr.length == 0) {
				iMainActivity.showHttpErr(curHttpMode);
			} else {
				SingletonDataMgr mgr = SingletonDataMgr.getInstance();
				mgr.iModelBodyItem.vurlImg = BitmapFactory.decodeByteArray(
						resultByteArr, 0, resultByteArr.length);
				curHttpMode = Const.E_GET_MODEL_XML_FURL_IMG;
				iHttpManager.addTask(mgr.iModelBodyItem.furl, "", true,
						curHttpMode);
			}
			break;
		}
		case Const.E_GET_MODEL_XML_FURL_IMG: {
			if (resultByteArr.length == 0) {
				iMainActivity.showHttpErr(curHttpMode);
			} else {
				SingletonDataMgr mgr = SingletonDataMgr.getInstance();
				mgr.iModelBodyItem.furlImg = BitmapFactory.decodeByteArray(
						resultByteArr, 0, resultByteArr.length);
				curHttpMode = Const.E_GET_DEFAULT_HAIR_XML_DATA;
				String url = Const
						.getHairXmlDataUrl(mgr.iUserSelectHeadItem.itemID);
				Log.e("E_GET_DEFAULT_HAIR_XML_DATA", url);
				iHttpManager.addTask(url, "", true, curHttpMode);
			}
			break;
		}
		case Const.E_GET_DEFAULT_HAIR_XML_DATA: {
			String s = new String(resultByteArr);
			SingletonDataMgr mgr = SingletonDataMgr.getInstance();
			mgr.iHairItemList = FCXmlParser.parserHairXml(s);

			if (mgr.iHairItemList.size() == 0) {
				iMainActivity.showHttpErr(curHttpMode);
			} else {
				FCBaseXmlItem item = mgr.iHairItemList.get(0);
				UserHairData data = new UserHairData();
				data.iUserSelectHair = item;
				mgr.iUserHairData = data;
				curHttpMode = Const.E_GET_DEFAULT_HAIR_IMG;
				iHttpManager.addTask(item.vurl, "", true, curHttpMode);
			}

			break;
		}
		case Const.E_GET_DEFAULT_HAIR_IMG: {
			if (resultByteArr.length == 0) {
				iMainActivity.showHttpErr(curHttpMode);
			} else {
				SingletonDataMgr mgr = SingletonDataMgr.getInstance();
				mgr.iUserHairData.iUserSelectHair.vurlImg = BitmapFactory
						.decodeByteArray(resultByteArr, 0, resultByteArr.length);
				iMainActivity.doFreshComponent();
			}
			break;
		}
		default:
			break;
		}
	}

	private void parserStep1(String s) {
		// TODO Auto-generated method stub
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		mgr.iStep1Item = FCXmlParser.parserStep1Data(s);

		if (mgr.iStep1Item == null) {
			iMainActivity.showHttpErr(curHttpMode);
		} else {
			// String url = Const.getStep2Url(mgr.iStep1Item.itemID);
			String url = mgr.iStep1Item.vurl;
			curHttpMode = Const.E_GETSTEP1_XML_IMG;
			iHttpManager.addTask(url, "", true, curHttpMode);
		}
	}

	/**
	 * ��ʼ���� ģ���������
	 * 
	 * @author SKY
	 */
	public void startDownModelImg() {
		// TODO Auto-generated method stub
		iMainActivity.showLoading();
		curHttpMode = Const.E_GET_MODEL_XML_DATA;
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		String url = Const.getDownModelUrl(mgr.iUserSelectHeadItem.itemID,
				mgr.iStep1Item.itemID);
		iHttpManager.addTask(url, "", true, curHttpMode);
	}
}
