package com.icld.mt.autoupdate.infrastructure.wapapi.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icld.mt.autoupdate.exceptions.AppException;
import com.icld.mt.autoupdate.modul.update.app.AppVersion;

public class ApiClient {

	private static final String TAG = ApiClient.class.getSimpleName();

	public static TResponse<AppVersion> checkUpdate(Context context, String url)
			throws AppException {

		String jsonRsp = NetClientFactory.getClient().doGet(context, url, null);

		Gson gson = new Gson();
		return gson.fromJson(jsonRsp, new TypeToken<TResponse<AppVersion>>() {
		}.getType());
	}

}
