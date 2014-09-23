package com.icld.mt.autoupdate.modul.update.app;

import android.content.Context;

import com.icld.mt.autoupdate.ServerLocator;
import com.icld.mt.autoupdate.exceptions.AppException;

public class UpdateServiceImpl implements UpdateService {

	
	private UpdateRepository repository;
	
	public UpdateServiceImpl(){
		repository = ServerLocator.getInstance(UpdateRepository.class);
	}
	
	
	
	@Override
	public AppVersion checkUpdate(Context context, String url) throws AppException {
		return repository.checkUpdate(context, url);
	}

}


