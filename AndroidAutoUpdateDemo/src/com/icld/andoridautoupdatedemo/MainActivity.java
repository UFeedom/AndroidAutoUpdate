package com.icld.andoridautoupdatedemo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.icld.mt.autoupdate.modul.update.app.AppVersion;
import com.icld.mt.autoupdate.modul.update.app.UpdateManager;
import com.icld.mt.autoupdate.modul.update.app.UpdateManager.DownloadListener;


public class MainActivity extends Activity {

	protected static final String TAG = MainActivity.class.getSimpleName();

	private View vUpdate;

	private TextView vStart;
	private TextView vProgress;
	private TextView vFinish;
	private Context context;
	private TextView vCheckResult;

	UpdateManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		vUpdate = findViewById(R.id.vCheckUpdate);

		vStart = (TextView) findViewById(R.id.vStart);
		vProgress = (TextView) findViewById(R.id.vProgress);
		vFinish = (TextView) findViewById(R.id.vFinish);
		vCheckResult = (TextView) findViewById(R.id.vCheckResult);
		context = this;

		vUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				
				new AsyncTask<Void, Void, Void>() {

					AppVersion appVersion;

					@Override
					protected Void doInBackground(Void... params) {

						
						manager = new UpdateManager(context);
						appVersion = manager.checkUpdate();

						return null;
					}

					@Override
					protected void onPostExecute(Void result) {

						if (appVersion.isNeedUpdate()) {

							vCheckResult.post(new Runnable() {
								public void run() {
									
									vCheckResult.setText(appVersion.getMessage());

									
									
								}
							});

						} else {

							vCheckResult.post(new Runnable() {
								public void run() {
									vCheckResult.setText("不需要更新,已经是最新版");

								}
							});

						}

						super.onPostExecute(result);
					}

				}.execute();

			}

		});

		findViewById(R.id.vStartUpdate).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						manager.update(new DownloadListener() {

							@Override
							public void updateProgress(float progress,
									float totalSize) {

					//			Log.e(TAG, "进度:" + progress);
								

								String p = context.getResources().getString(R.string.lb_progress, progress,totalSize);
							    
								
								vProgress.setText(p);	
								
							}

							@Override
							public void startUpdate(float totalSize) {

								String start = context.getResources().getString(R.string.lb_total, totalSize);
														    
								vStart.setText(start);		

							}

							@Override
							public void finshUpdate(String filePath) {

								String finsh = context.getResources().getString(R.string.lb_finish, filePath);

								vFinish.setText(finsh);
								
								manager.installApk(filePath);
								
							}
						});
					}
				});

	}

}
