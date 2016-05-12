package com.sentayzo.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

			Intent i = new Intent(context, AlarmService.class);
			i.putExtra("reSchedule_alarms", true);
			context.startService(i);

		}
	}

}
