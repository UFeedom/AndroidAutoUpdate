package com.icld.mt.autoupdate.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class Util {

	private static final int NETWORK_TYPE_NONE = 0;
	private static final int NETWORK_TYPE_WIFI = 1;
	private static final int NETWORK_TYPE_MOBILE = 2;

	
	
	public static String getAppinfo(Context context,String key){
		
		String appInfo = null;
		ApplicationInfo applicationInfo;
		try {
			applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
			appInfo = applicationInfo.metaData.getString(key);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return appInfo;	
	}
	
	
	public static String getVersionName(Context context) {

		String verName = null;

		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info;
			info = manager.getPackageInfo(context.getPackageName(), 0);
			verName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return verName;

	}

	public static int getVersionCode(Context context) {

		int versionCode = 0;
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			versionCode = info.versionCode;

		} catch (Exception e) {
			e.printStackTrace();

		}

		return versionCode;
	}

	
	

}
