
package com.icld.mt.autoupdate.infrastructure.wapapi.net;

import java.util.Map;

import android.content.Context;

import com.icld.mt.autoupdate.exceptions.AppException;



public interface NetClient {
	
	public String doGet(Context context ,String url,Map<String,Object> params) throws AppException;

	public String doPost(Context context,String url,Map<String,Object> params) throws AppException;


}

