package com.icld.mt.autoupdate.infrastructure.repository;

import android.content.Context;
import android.util.Log;

import com.icld.mt.autoupdate.exceptions.AppException;
import com.icld.mt.autoupdate.infrastructure.wapapi.net.ApiClient;
import com.icld.mt.autoupdate.infrastructure.wapapi.net.TResponse;
import com.icld.mt.autoupdate.modul.update.app.AppVersion;
import com.icld.mt.autoupdate.modul.update.app.UpdateRepository;

public class UpdateRepositoryImpl implements UpdateRepository {

	private static final String TAG = UpdateRepositoryImpl.class.getSimpleName();
	
	@Override
	public AppVersion checkUpdate(Context context, String url) throws AppException {

		AppVersion update = new AppVersion();

		TResponse<AppVersion> response = ApiClient.checkUpdate(context, url);

		
		
		if (response.isSuccess()) {
			update = response.getResult();

		}else{
			Log.e(TAG, "结果为空");

		}

		return update;
	}

}
