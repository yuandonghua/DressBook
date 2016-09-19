package cn.dressbook.ui.tb;

import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.alipay.AlipayUtils;
import cn.dressbook.ui.common.Constants;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ResultCode;
import com.alibaba.sdk.android.login.LoginService;
import com.alibaba.sdk.android.login.callback.LoginCallback;
import com.alibaba.sdk.android.login.callback.LogoutCallback;
import com.alibaba.sdk.android.session.model.Session;
import com.alibaba.sdk.android.trade.CartService;
import com.alibaba.sdk.android.trade.ItemService;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TaokeParams;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.ItemDetailPage;
import com.alibaba.sdk.android.trade.page.MyCardCouponsPage;
import com.alibaba.sdk.android.trade.page.MyOrdersPage;
import com.alibaba.sdk.android.trade.page.PromotionsPage;
import com.taobao.tae.sdk.webview.TaeWebViewUiSettings;

public class TaoBaoUI {
	private static TaoBaoUI mTaoBaoUI = null;

	private TaoBaoUI() {
	}

	public static TaoBaoUI getInstance() {
		if (mTaoBaoUI == null) {
			mTaoBaoUI = new TaoBaoUI();
		}
		return mTaoBaoUI;
	}

	/**
	 * 获取订单信息
	 * 
	 * @param activity
	 */
	public void showMyOrdersPage(final Activity activity) {
		TradeService tradeService = AlibabaSDK.getService(TradeService.class);
		MyOrdersPage myOrdersPage = new MyOrdersPage(0, false);
		tradeService.show(myOrdersPage, null, activity, null,
				new TradeProcessCallback() {

					@Override
					public void onFailure(int code, String msg) {
						Toast.makeText(activity, "失败 " + code + msg,
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onPaySuccess(TradeResult tradeResult) {
						Toast.makeText(activity, "成功", Toast.LENGTH_SHORT)
								.show();

					}
				});
	}

	/**
	 * 根据openiid展示商品详情
	 * 
	 * @param activity
	 * @param openiid
	 */
	public void showItemDetailPage(final Activity activity, String openiid) {
		TradeService tradeService = AlibabaSDK.getService(TradeService.class);
		ItemDetailPage itemDetailPage = new ItemDetailPage(openiid, null);
		TaokeParams taokeParams = new TaokeParams(); // 若非淘客taokeParams设置为null即可
		taokeParams.pid = Constants.TB_PID;
		tradeService.show(itemDetailPage, null, activity, null,
				new TradeProcessCallback() {
					@Override
					public void onFailure(int code, String msg) {
					}

					@Override
					public void onPaySuccess(TradeResult tradeResult) {

						List<Long> orderList = tradeResult.paySuccessOrders;
						if (orderList != null && orderList.size() > 0) {
							String content = "";
							for (int i = 0; i < orderList.size(); i++) {
								content += orderList.get(i) + ",";
							}
							albcLogRec(null, ManagerUtils.getInstance()
									.getUser_id(activity), content, 0, 0);
						}

					}
				});
	}

	/**
	 * 通过真实id打开详情页
	 * 
	 * @param activity
	 * @param auction_id
	 */
	public void showTaokeItemDetailByItemId(final Activity activity,
		String auction_id) {
		TaeWebViewUiSettings taeWebViewUiSettings = new TaeWebViewUiSettings();
		TaokeParams taokeParams = new TaokeParams();
		taokeParams.pid = Constants.TB_PID;
		taokeParams.unionId = "null";
		ItemService itemService = AlibabaSDK.getService(ItemService.class);
		itemService.showTaokeItemDetailByItemId(activity,
				new TradeProcessCallback() {

					@Override
					public void onPaySuccess(TradeResult tradeResult) {
						List<Long> orderList = tradeResult.paySuccessOrders;
						if (orderList != null && orderList.size() > 0) {
							String content = "";
							for (int i = 0; i < orderList.size(); i++) {
								content += orderList.get(i) + ",";
							}
							albcLogRec(null, ManagerUtils.getInstance()
									.getUser_id(activity), content, 0, 0);
						}

					}

					@Override
					public void onFailure(int code, String msg) {
						if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
						} else {
						}
					}
				}, taeWebViewUiSettings, Long.valueOf(auction_id), 1, null, taokeParams);
	}

	/**
	 * 登陆淘宝
	 * 
	 * @param activity
	 */
	public void showLogin(final Activity activity, final Handler handler) {
		// 调用getService方法来获取服务
		LoginService loginService = AlibabaSDK.getService(LoginService.class);
		loginService.showLogin(activity, new LoginCallback() {
			@Override
			public void onSuccess(Session session) {
				// 当前是否登录状态 boolean isLogin();
				// 登录授权时间 long getLoginTime();
				// 当前用户ID String getUserId();
				// 用户其他属性 User getUser();
				// User中：淘宝用户名 淘宝用户ID 淘宝用户头像地址

				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("id", session.getUserId());
				data.putString("nick", session.getUser().nick);
				data.putString("avatarUrl", session.getUser().avatarUrl);
				data.putString("isLogin", session.isLogin() + "");
				data.putLong("getLoginTime", session.getLoginTime());
				msg.setData(data);
				msg.what = NetworkAsyncCommonDefines.DL_TB_CALLBACK;
				handler.sendMessage(msg);

			}

			@Override
			public void onFailure(int code, String message) {
				Toast.makeText(activity, "授权取消" + code + message,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 退出淘宝
	 * 
	 * @param activity
	 */
	public void logout(final Activity activity) {
		LoginService loginService = AlibabaSDK.getService(LoginService.class);
		loginService.logout(activity, new LogoutCallback() {

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(activity, "登出失败", Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onSuccess() {
				Toast.makeText(activity, "登出成功", Toast.LENGTH_SHORT).show();

			}
		});
	}

	/**
	 * 进入淘宝首页
	 * 
	 * @param activity
	 */
	public void showPage(final Activity activity) {
		ItemService itemService = AlibabaSDK.getService(ItemService.class);
		itemService.showPage(activity, new TradeProcessCallback() {

			@Override
			public void onPaySuccess(TradeResult tradeResult) {
				Toast.makeText(
						activity,
						"支付成功" + tradeResult.paySuccessOrders + "   "
								+ tradeResult.payFailedOrders,
						Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onFailure(int code, String msg) {
				if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
					Toast.makeText(activity, "确认交易订单失败", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(activity, "交易异常", Toast.LENGTH_SHORT).show();
				}
			}
		}, null, "http://m.taobao.com");
	}

	/**
	 * 淘宝购物车
	 * 
	 * @param activity
	 */
	public void showCart(final Activity activity) {
		CartService cartService = AlibabaSDK.getService(CartService.class);
		cartService.showCart(activity, new TradeProcessCallback() {

			@Override
			public void onPaySuccess(TradeResult tradeResult) {
				Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onFailure(int code, String msg) {
				if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
					Toast.makeText(activity, "确认交易订单失败", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(activity, "交易异常", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/**
	 * 我的卡券包
	 * 
	 * @param activity
	 */
	public void showMyCardCouponsPage(final Activity activity) {
		TradeService tradeService = AlibabaSDK.getService(TradeService.class);
		MyCardCouponsPage myCardCouponsPage = new MyCardCouponsPage();
		tradeService.show(myCardCouponsPage, null, activity, null,
				new TradeProcessCallback() {

					@Override
					public void onFailure(int code, String msg) {
						Toast.makeText(activity, "失败 " + code + msg,
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onPaySuccess(TradeResult tradeResult) {
						Toast.makeText(activity, "成功", Toast.LENGTH_SHORT)
								.show();

					}
				});
	}

	/**
	 * 优惠券
	 * 
	 * @param type
	 *              优惠券类型。值为“shop”和“auction”两种
	 * @param param
	 *            shop时，param传递为卖家nick type：auction时，param传递为商品的混淆id
	 */
	public void showPromotionsPage(final Activity activity) {
		TradeService tradeService = AlibabaSDK.getService(TradeService.class);
		PromotionsPage promotionsPage = new PromotionsPage("shop",
				AlipayUtils.SELLER);
		tradeService.show(promotionsPage, null, activity, null,
				new TradeProcessCallback() {

					@Override
					public void onFailure(int code, String msg) {
						Toast.makeText(activity, "失败 " + code + msg,
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onPaySuccess(TradeResult tradeResult) {
						Toast.makeText(activity, "成功", Toast.LENGTH_SHORT)
								.show();

					}
				});
	}

	/**
	 * 记录阿里百川数据
	 * 
	 * @param handler
	 * @param user_id
	 * @param content
	 * @param flag1
	 * @param flag2
	 */
	private void albcLogRec(final Handler handler, final String user_id,
			final String content, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.ALBCLOGREC);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("content", content);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (handler != null) {
					handler.sendEmptyMessage(flag1);
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (handler != null) {
					handler.sendEmptyMessage(flag2);
				}
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});

	}
}
