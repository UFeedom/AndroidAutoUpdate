package com.icld.mt.autoupdate.modul.update.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.icld.mt.autoupdate.ServerLocator;
import com.icld.mt.autoupdate.exceptions.AppException;
import com.icld.mt.autoupdate.util.StorageUtils;
import com.icld.mt.autoupdate.util.Util;

public class UpdateManager {

	private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K

	private static final String TAG = UpdateManager.class.getSimpleName();

	public static final String KEY_DEFAULT_CHECK_URL = "com.icld.autoupdate.checkupdate.app.url";

//	public static final String KEY_DEFAULT_UPDATE_URL = "com.icld.update.url";

	private Context context;
	private String checkUrl;

	private UpdateService updateService;
	private AppVersion appVersion;
	private DownloadListener downloadListener;

	private InternalHander handler;
	
	
	private float totalSieze;
	private float updateProgress;
	private String path;

	private UpdateManager() {
		updateService = ServerLocator.getInstance(UpdateService.class);

	}

	public UpdateManager(Context context) {
		this();
		this.context = context;
		checkUrl = Util.getAppinfo(context, KEY_DEFAULT_CHECK_URL);
	}

	public UpdateManager(Context context, String url) {
		this();
		this.context = context;
		this.checkUrl = url;
	}

	public AppVersion checkUpdate() {

		appVersion = new AppVersion();

		if (checkUrl.length() == 0) {

			Log.e(TAG, "检测地址为空");

		} else {

			try {
				appVersion = updateService.checkUpdate(context, checkUrl);

				if (appVersion.getVerCode() > Util.getVersionCode(context)) {

					appVersion.setNeedUpdate(true);
				}

			} catch (AppException e) {
				e.printStackTrace();
			}

		}

		return appVersion;

	}

	public void testUrl() {
		Log.e(TAG, "CheckUrl:" + checkUrl);
	}

	private void updateTask() {

		InputStream in = null;
		FileOutputStream out = null;

		try {

			URL url = new URL(appVersion.getUpdateUrl());
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(false);
			urlConnection.setConnectTimeout(10 * 1000);
			urlConnection.setReadTimeout(10 * 1000);
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Charset", "UTF-8");
			urlConnection
					.setRequestProperty("Accept-Encoding", "gzip, deflate");

			urlConnection.connect();

			long bytetotal = urlConnection.getContentLength();
			
			totalSieze = bytetotal;
		
			handler.sendEmptyMessage(InternalHander.MSG_UPDATE_CALLBACK_START);

			long bytesum = 0;
			int byteread = 0;

			in = urlConnection.getInputStream();

			File dir = StorageUtils.getCacheDirectory(context);
			
			String apkName = context.getPackageName();

			StringBuilder builder = new StringBuilder(apkName);
			
			File apkFile = new File(dir, builder.append(".apk").toString());

			out = new FileOutputStream(apkFile);

			byte[] buffer = new byte[BUFFER_SIZE];

			int oldProgress = 0;
			while ((byteread = in.read(buffer)) != -1) {

				bytesum += byteread;
				out.write(buffer, 0, byteread);

				int progress = (int) (bytesum * 100L / bytetotal);

				
				if (progress != oldProgress) {
					
					updateProgress = progress;
					handler.sendEmptyMessage(InternalHander.MSG_UPDATE_CALLBACK_PROGRESS);

					
				}			
				
				oldProgress = progress;
			}
			
			path = apkFile.getAbsolutePath();			
			handler.sendEmptyMessage(InternalHander.MSG_UPDATE_CALLBACK_FINISH);


		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (in != null) {
					in.close();
				}

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public void update(final DownloadListener listener) {

		downloadListener = listener;

		handler = new InternalHander(context.getMainLooper());

		if (appVersion.getUpdateUrl().length() == 0) {
			Log.e(TAG, "下载地址为空");

		} else {


			handler.sendEmptyMessage(InternalHander.MSG_UPDATE);

		}

	}

	@SuppressLint("HandlerLeak")
	private class InternalHander extends Handler {

		public static final int MSG_UPDATE = 1;

		public static final int MSG_UPDATE_CALLBACK_START = 2;
		public static final int MSG_UPDATE_CALLBACK_PROGRESS = 3;
		public static final int MSG_UPDATE_CALLBACK_FINISH = 4;

		public InternalHander(Looper looper) {
			super(looper);

		}

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

				case MSG_UPDATE :
					new Thread() {

						@Override
						public void run() {
							updateTask();						
						}

					}.start();

					break;

				case MSG_UPDATE_CALLBACK_START :
					
					if(downloadListener!= null)
						downloadListener.startUpdate(totalSieze);

					break;
					
				case MSG_UPDATE_CALLBACK_PROGRESS :
					

					if(downloadListener!= null)
						downloadListener.updateProgress(updateProgress, totalSieze);

					break;
				case MSG_UPDATE_CALLBACK_FINISH :
					
					if(downloadListener!= null)
						downloadListener.finshUpdate(path);
					
					break;

				default :
					break;
			}


		}

	}
	
	
	public void installApk(String path){
			
		Intent installAPKIntent = new Intent(Intent.ACTION_VIEW);
		installAPKIntent.setDataAndType(Uri.fromFile(new File(path)),
				"application/vnd.android.package-archive");
		context.startActivity(installAPKIntent);
		
	}
	

	public interface DownloadListener {

		public void startUpdate(float totalSize);

		public void updateProgress(float progress, float totalSize);

		public void finshUpdate(String filePath);

	}

}
