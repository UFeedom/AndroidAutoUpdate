package com.icld.mt.autoupdate;

import java.util.HashMap;
import java.util.Map;

import com.icld.mt.autoupdate.modul.update.app.UpdateRepository;
import com.icld.mt.autoupdate.modul.update.app.UpdateService;

public class ServerLocator {

	private static Map<Object, Object> objects;

	static {
		objects = new HashMap<Object, Object>();

		init();

	}

	private static void init() {
	
		objects.put(UpdateRepository.class, new com.icld.mt.autoupdate.infrastructure.repository.UpdateRepositoryImpl());
		objects.put(UpdateService.class, new com.icld.mt.autoupdate.modul.update.app.UpdateServiceImpl());
		
	}

	
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<?> cls) {

		return (T) objects.get(cls);
	}
}
