package cn.dressbook.ui;

import java.util.ArrayList;
import java.util.List;

import org.xutils.common.util.LogUtil;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;
import cn.dressbook.ui.bean.ContactInfo;
import cn.dressbook.ui.utils.ContactUtils;

public class TongXunLuActivity extends BaseActivity {
	final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		// checkPermission();
		insertDummyContactWrapper();
	}

	private void insertDummyContactWrapper() {
		int hasWriteContactsPermission = ContextCompat.checkSelfPermission(
				activity, android.Manifest.permission.WRITE_CONTACTS);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,
					android.Manifest.permission.WRITE_CONTACTS)) {
				showMessageOKCancel("亲,请允许获取联系人权限",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ActivityCompat
										.requestPermissions(
												activity,
												new String[] { android.Manifest.permission.WRITE_CONTACTS },
												REQUEST_CODE_ASK_PERMISSIONS);
							}
						});
				return;
			}
			ActivityCompat
					.requestPermissions(
							activity,
							new String[] { android.Manifest.permission.WRITE_CONTACTS },
							REQUEST_CODE_ASK_PERMISSIONS);
			return;
		}
		uploadTXL();
	}

	private void showMessageOKCancel(String message,
			DialogInterface.OnClickListener okListener) {
		new AlertDialog.Builder(activity).setMessage(message)
				.setPositiveButton("确定", okListener)
				.setNegativeButton("取消", null).create().show();
	}

	private static final String TAG = "Contacts";

	private void insertDummyContact() {
		// Two operations are needed to insert a new contact.
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(
				2);

		// First, set up a new raw contact.
		ContentProviderOperation.Builder op = ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
		operations.add(op.build());

		// Next, set the name for the contact.
		op = ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
						"aaaaaaaaaa");
		operations.add(op.build());

		// Apply the operations.
		ContentResolver resolver = getContentResolver();
		try {
			resolver.applyBatch(ContactsContract.AUTHORITY, operations);
		} catch (RemoteException e) {
			Log.e(TAG, "Could not add a new contact: " + e.getMessage());
		} catch (OperationApplicationException e) {
			Log.e(TAG, "Could not add a new contact: " + e.getMessage());
		}
	}

	private void checkPermission() {
		// TODO Auto-generated method stub
		// if (ManagerUtils.getInstance().isLogin(activity)) {
		// // 登陆用户
		// boolean b = mSharedPreferenceUtils.getTXL(activity);
		// if (b) {
		// // 如果获取过通讯录，不处理
		// LogUtil.e("不处理通讯录");
		// return;
		// }
		int hasWriteContactsPermission = ContextCompat.checkSelfPermission(
				activity, android.Manifest.permission.READ_CONTACTS);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity,
					new String[] { android.Manifest.permission.READ_CONTACTS },
					REQUEST_CODE_ASK_PERMISSIONS);
			return;
		}

		uploadTXL();
		// }
	}

	private void uploadTXL() {

		LogUtil.e("uploadTXL");
		List<ContactInfo> list = ContactUtils.getInstance().getAllContacts(
				activity);
		if (list == null) {
			LogUtil.e("联系人的集合为空----------------");
		} else {
			String pbk = "";
			for (ContactInfo info : list) {
				if (info != null && info.name != null && info.phone != null) {

					pbk += info.name + "," + info.phone + ";";
				}

			}
			if (!"".equals(pbk) && pbk.length() > 0) {
				pbk = pbk.substring(0, pbk.length() - 1);
			}
			LogUtil.e("开始上传:" + pbk);

			// UserExec.getInstance().uploadTXL(mHandler,
			// ManagerUtils.getInstance().getUser_id(activity), pbk,
			// NetworkAsyncCommonDefines.UPLOAD_TXL_S,
			// NetworkAsyncCommonDefines.UPLOAD_TXL_F);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] grantResults) {
		LogUtil.e("requestCode:" + requestCode);
		switch (requestCode) {
		case REQUEST_CODE_ASK_PERMISSIONS:
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				LogUtil.e("允许权限了");
				// checkPermission();
				uploadTXL();
			} else {
				// Permission Denied
				Toast.makeText(activity, "权限被拒绝", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			super.onRequestPermissionsResult(requestCode, permissions,
					grantResults);
		}
	}
}
