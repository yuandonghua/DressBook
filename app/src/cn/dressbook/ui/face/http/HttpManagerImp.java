package cn.dressbook.ui.face.http;

//�����ص�ʵ��
public interface HttpManagerImp {
	void onHttpResponse(byte[] resultByteArr, int taskID);

	void onHttpError(int taskID);

	void onHttpResponseIntime(int alreadyLen);
}
