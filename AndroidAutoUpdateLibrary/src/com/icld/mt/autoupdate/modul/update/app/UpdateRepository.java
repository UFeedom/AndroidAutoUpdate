
package com.icld.mt.autoupdate.modul.update.app;

import android.content.Context;

import com.icld.mt.autoupdate.exceptions.AppException;


public interface UpdateRepository {
	
	public AppVersion checkUpdate(Context context,String url) throws AppException;
	
	
}

