package cn.dressbook.ui.face.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import cn.dressbook.ui.face.data.FCBaseXmlItem;
import cn.dressbook.ui.face.data.FCModelXmlItem;
import cn.dressbook.ui.face.data.Headcls;

public class FCXmlParser {

	private FCXmlParser() {

	}

	public static String deleteXMlHead(String str, String tag) {
		int index = str.indexOf(tag);

		if (index < 0) {
			return null;
		}

		String res = str.substring(index);
		// Log.e("NEW XMl", res);
		return res;
	}

	public static ArrayList<Headcls> parserHeadcls(String str) {
		ArrayList<Headcls> res = new ArrayList<Headcls>();

		String httpData = FCXmlParser.deleteXMlHead(str, "<mhead>");

		if (httpData == null) {
			return null;
		}

		XmlPullParserFactory factoryPull = null;
		try {
			factoryPull = XmlPullParserFactory.newInstance();
			factoryPull.setNamespaceAware(true);
			XmlPullParser pullParser = factoryPull.newPullParser();
			pullParser.setInput(new StringReader(httpData));

			int event = pullParser.getEventType();// ������һ���¼�

			String tag = "";
			StringBuffer tagValue = null;

			Headcls aHeadcls = null;
			FCBaseXmlItem pic = null;

			while (event != XmlPullParser.END_DOCUMENT) {

				boolean error = false;

				switch (event) {
				case XmlPullParser.START_DOCUMENT: {
					break;
				}
				case XmlPullParser.START_TAG: {
					tag = pullParser.getName();
					if ("headcls".equals(pullParser.getName())) {
						aHeadcls = new Headcls();
						String v = pullParser.getAttributeValue(0);
						aHeadcls.value = v;
					} else if ("pic".equals(pullParser.getName())) {
						pic = new FCBaseXmlItem();
						String v = pullParser.getAttributeValue(0);
						pic.itemID = v;
					}
					if (tagValue != null) {
						tagValue = null;
					}
					break;
				}
				case XmlPullParser.TEXT: {

					if (pullParser.getText() != null) {
						if (tagValue == null) {
							tagValue = new StringBuffer();
						}
						tagValue.append(pullParser.getText());
					}
					break;
				}
				case XmlPullParser.END_TAG:
					// Log.e("TAG End", "<" + pullParser.getName() + "/>");

					if ("headcls".equals(pullParser.getName())) {
						res.add(aHeadcls);
						aHeadcls = null;
					} else if ("pic".equals(pullParser.getName())) {
						aHeadcls.headList.add(pic);
						pic = null;
					} else if ("vurl".equals(pullParser.getName())) {
						pic.vurl = tagValue.toString();
					} else if ("iurl".equals(pullParser.getName())) {
						pic.iurl = tagValue.toString();
					}
					tagValue = null;
					break;
				}
				event = pullParser.next();

			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
			res = null;
		} catch (IOException e) {
			e.printStackTrace();
			res = null;
		} finally {

		}

		return res;
	}

	public static FCBaseXmlItem parserStep1Data(String httpData2) {
		FCBaseXmlItem res = new FCBaseXmlItem();

		String httpData = FCXmlParser.deleteXMlHead(httpData2, "<model>");

		if (httpData == null) {
			return null;
		}

		XmlPullParserFactory factoryPull = null;
		try {
			factoryPull = XmlPullParserFactory.newInstance();
			factoryPull.setNamespaceAware(true);
			XmlPullParser pullParser = factoryPull.newPullParser();
			pullParser.setInput(new StringReader(httpData));

			int event = pullParser.getEventType();// ������һ���¼�

			String tag = "";
			StringBuffer tagValue = null;

			while (event != XmlPullParser.END_DOCUMENT) {

				boolean error = false;

				switch (event) {
				case XmlPullParser.START_DOCUMENT: {
					break;
				}
				case XmlPullParser.START_TAG: {
					tag = pullParser.getName();
					if (tagValue != null) {
						tagValue = null;
					}
					break;
				}
				case XmlPullParser.TEXT: {

					if (pullParser.getText() != null) {
						if (tagValue == null) {
							tagValue = new StringBuffer();
						}
						tagValue.append(pullParser.getText());
					}
					break;
				}
				case XmlPullParser.END_TAG:
					Log.e("TAG End", "<" + pullParser.getName() + "/>");

					if ("id".equals(pullParser.getName())) {
						res.itemID = tagValue.toString();
					} else if ("vurl".equals(pullParser.getName())) {
						res.vurl = tagValue.toString();
					}
					tagValue = null;
					break;
				}
				event = pullParser.next();

			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
			res = null;
		} catch (IOException e) {
			e.printStackTrace();
			res = null;
		} finally {

		}

		return res;
	}

	public static FCModelXmlItem parserModelXml(String s) {
		// TODO Auto-generated method stub
		FCModelXmlItem res = new FCModelXmlItem();

		String httpData = FCXmlParser.deleteXMlHead(s, "<headmodel>");

		if (httpData == null) {
			return null;
		}

		XmlPullParserFactory factoryPull = null;
		try {
			factoryPull = XmlPullParserFactory.newInstance();
			factoryPull.setNamespaceAware(true);
			XmlPullParser pullParser = factoryPull.newPullParser();
			pullParser.setInput(new StringReader(httpData));

			int event = pullParser.getEventType();// ������һ���¼�

			String tag = "";
			StringBuffer tagValue = null;

			while (event != XmlPullParser.END_DOCUMENT) {

				switch (event) {
				case XmlPullParser.START_DOCUMENT: {
					break;
				}
				case XmlPullParser.START_TAG: {

					if ("eye1".equals(pullParser.getName())) {
						String x = pullParser.getAttributeValue(0);
						String y = pullParser.getAttributeValue(1);

						res.setLeftP(x, y);
					} else if ("eye2".equals(pullParser.getName())) {
						String x = pullParser.getAttributeValue(0);
						String y = pullParser.getAttributeValue(1);

						res.setRightP(x, y);
					}

					if (tagValue != null) {
						tagValue = null;
					}
					break;
				}
				case XmlPullParser.TEXT: {

					if (pullParser.getText() != null) {
						if (tagValue == null) {
							tagValue = new StringBuffer();
						}
						tagValue.append(pullParser.getText());
					}
					break;
				}
				case XmlPullParser.END_TAG:
					if ("surl".equals(pullParser.getName())) {
						res.surl = tagValue.toString();
					} else if ("vurl".equals(pullParser.getName())) {
						res.vurl = tagValue.toString();
					} else if ("furl".equals(pullParser.getName())) {
						res.furl = tagValue.toString();
					}
					tagValue = null;
					break;
				}
				event = pullParser.next();

			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
			res = null;
		} catch (IOException e) {
			e.printStackTrace();
			res = null;
		} finally {

		}

		return res;
	}

	public static ArrayList<FCBaseXmlItem> parserHairXml(String s) {
		ArrayList<FCBaseXmlItem> res = new ArrayList<FCBaseXmlItem>();

		String httpData = FCXmlParser.deleteXMlHead(s, "<mhair>");

		if (httpData == null) {
			return null;
		}

		FCBaseXmlItem item = null;

		XmlPullParserFactory factoryPull = null;
		try {
			factoryPull = XmlPullParserFactory.newInstance();
			factoryPull.setNamespaceAware(true);
			XmlPullParser pullParser = factoryPull.newPullParser();
			pullParser.setInput(new StringReader(httpData));

			int event = pullParser.getEventType();// ������һ���¼�

			String tag = "";
			StringBuffer tagValue = null;

			while (event != XmlPullParser.END_DOCUMENT) {

				boolean error = false;

				switch (event) {
				case XmlPullParser.START_DOCUMENT: {
					break;
				}
				case XmlPullParser.START_TAG: {

					if ("pic".equals(pullParser.getName())) {
						item = new FCBaseXmlItem();
					}

					if (tagValue != null) {
						tagValue = null;
					}
					break;
				}
				case XmlPullParser.TEXT: {

					if (pullParser.getText() != null) {
						if (tagValue == null) {
							tagValue = new StringBuffer();
						}
						tagValue.append(pullParser.getText());
					}
					break;
				}
				case XmlPullParser.END_TAG:
					// Log.e("TAG End", "<" + pullParser.getName() + "/>");

					if ("pic".equals(pullParser.getName())) {
						res.add(item);
						item = null;
					} else if ("url".equals(pullParser.getName())) {
						item.vurl = tagValue.toString();
					}
					tagValue = null;
					break;
				}
				event = pullParser.next();

			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
			res = null;
		} catch (IOException e) {
			e.printStackTrace();
			res = null;
		} finally {

		}

		return res;

	}
}
