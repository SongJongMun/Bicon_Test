package com.example.bicon_test;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.Region;

public class BeaconDetactorService extends Service implements IBeaconConsumer {

	private IBeaconManager iBeaconManager = IBeaconManager.getInstanceForApplication(this);

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		iBeaconManager.bind(this);

		final Handler handler = new Handler();
		final Runnable runnable = new Runnable() {

			@Override
			public void run() {
				stopSelf();
			}
		};
		handler.postDelayed(runnable, 10000);
	}

	@Override
	public void onDestroy() {
		iBeaconManager.unBind(this);
		super.onDestroy();
	}

	@Override
	public void onIBeaconServiceConnect() {
		

		try {
			iBeaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

}