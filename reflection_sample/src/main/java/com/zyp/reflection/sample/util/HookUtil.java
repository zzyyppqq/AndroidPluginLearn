package com.zyp.reflection.sample.util;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.zyp.host.core.activity.ProxyActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Hook工具类
 */
public class HookUtil {

	private static final String TARGET_INTENT = "target_intent";

	/**
	 * 使用代理的Activity替换需要启动的未注册的Activity
	 * 在启动startActivity和AMS检测启动的页面之间，将未注册的Activity替换为已注册的Activity
	 */
	public static void hookAMS() {
		// Android10之前要适配
		try {
			//反射
			Class<?> clazz = Class.forName("android.app.ActivityTaskManager");
			Field singletonField = clazz.getDeclaredField("IActivityTaskManagerSingleton");

			singletonField.setAccessible(true);
			Object singleton = singletonField.get(null);

			Class<?> singletonClass = Class.forName("android.util.Singleton");
			Field mInstanceField = singletonClass.getDeclaredField("mInstance");
			mInstanceField.setAccessible(true);
			Method getMethod = singletonClass.getMethod("get");
			Object mInstance = getMethod.invoke(singleton);

			Class IActivityTaskManagerClass = Class.forName("android.app.IActivityTaskManager");

			//动态代理
			Object mInstanceProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{IActivityTaskManagerClass}, new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

							if ("startActivity".equals(method.getName())) {
								int index = -1;

								// 获取 Intent 参数在 args 数组中的index值
								for (int i = 0; i < args.length; i++) {
									if (args[i] instanceof Intent) {
										index = i;
										break;
									}
								}
								// 生成代理proxyIntent -- 孙悟空（代理）的Intent
								Intent proxyIntent = new Intent();
								// 这个包名是宿主的
								proxyIntent.setClassName("com.zyp.reflection",
										ProxyActivity.class.getName());

								// 原始Intent能丢掉吗？保存原始的Intent对象
								Intent intent = (Intent) args[index];
								proxyIntent.putExtra(TARGET_INTENT, intent);

								// 使用proxyIntent替换数组中的Intent
								args[index] = proxyIntent;
							}

							// 原来流程
							return method.invoke(mInstance, args);
						}
					});

			// 用代理的对象替换系统的对象
			mInstanceField.set(singleton, mInstanceProxy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 需要启动的未注册的Activity 替换回来  ProxyActivity
	 * 在AMS检测启动的Activity之后，Activity执行生命周期之前，将已注册页面替换成未注册的页面即可
	 */
	public static void hookHandler() {
		try {
			Class<?> clazz = Class.forName("android.app.ActivityThread");

			Field activityThreadField = clazz.getDeclaredField("sCurrentActivityThread");
			activityThreadField.setAccessible(true);
			Object activityThread = activityThreadField.get(null);

			Field mHField = clazz.getDeclaredField("mH");
			mHField.setAccessible(true);
			final Handler mH = (Handler) mHField.get(activityThread);

			Field mCallbackField = Handler.class.getDeclaredField("mCallback");
			mCallbackField.setAccessible(true);

			mCallbackField.set(mH, new Handler.Callback() {

				@Override
				public boolean handleMessage(Message msg) {
					switch (msg.what) {
						case 159:
							// msg.obj = ClientTransaction
							try {
								// 获取 List<ClientTransactionItem> mActivityCallbacks 对象
								Field mActivityCallbacksField = msg.obj.getClass()
										.getDeclaredField("mActivityCallbacks");
								mActivityCallbacksField.setAccessible(true);
								List mActivityCallbacks = (List) mActivityCallbacksField.get(msg.obj);

								for (int i = 0; i < mActivityCallbacks.size(); i++) {
									// 打印 mActivityCallbacks 的所有item:
									//android.app.servertransaction.WindowVisibilityItem
									//android.app.servertransaction.LaunchActivityItem

									// 如果是 LaunchActivityItem，则获取该类中的 mIntent 值，即 proxyIntent
									if (mActivityCallbacks.get(i).getClass().getName()
											.equals("android.app.servertransaction.LaunchActivityItem")) {
										Object launchActivityItem = mActivityCallbacks.get(i);
										Field mIntentField = launchActivityItem.getClass()
												.getDeclaredField("mIntent");
										mIntentField.setAccessible(true);
										Intent proxyIntent = (Intent) mIntentField.get(launchActivityItem);

										// 获取启动插件的 Intent，并替换回来
										Intent intent = proxyIntent.getParcelableExtra(TARGET_INTENT);
										if (intent != null) {
											mIntentField.set(launchActivityItem, intent);
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
					}
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
