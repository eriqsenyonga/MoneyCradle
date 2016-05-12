package com.sentayzo.app;

import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent i) {
		
		
		Log.d("in AlarmReceiver", "top");
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wakeLock = pm.newWakeLock(
				PowerManager.PARTIAL_WAKE_LOCK, "WakeLock");
		wakeLock.acquire();
		


		if (i.hasExtra("end_of_free_period")) {
			// if the alarm is ending the free trial period
			
		

			SharedPreferences billingPrefs = context.getSharedPreferences(
					"my_billing_prefs", 0);
			SharedPreferences.Editor editor = billingPrefs.edit();

			if (billingPrefs.getBoolean("already_executed_end_free_period",
					false) == false) {

				// first record that the free trial period is done
				editor.putBoolean("KEY_FREE_TRIAL_PERIOD", false).commit();

				// remove extra accounts
				new DbClass(context).closeExtraAccountsIfAny();

				// show notification
				NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(
						context).setSmallIcon(R.drawable.ic_launcher)

				.setContentText(
						context.getResources().getString(
								R.string.full_version_free_trial_expired));

				// Creates an explicit intent for an Activity in your app
				Intent resultIntent = new Intent(context, StoreActivity.class);

				resultIntent.putExtra("Time Done", true);

				// The stack builder object will contain an artificial back
				// stack for the
				// started Activity.
				// This ensures that navigating backward from the Activity leads
				// out of
				// your application to the Home screen.
				TaskStackBuilder stackBuilder = TaskStackBuilder
						.create(context);
				// Adds the back stack for the Intent (but not the Intent
				// itself)
				stackBuilder.addParentStack(MainActivity.class);
				// Adds the Intent that starts the Activity to the top of the
				// stack
				stackBuilder.addNextIntent(resultIntent);
				PendingIntent resultPendingIntent = stackBuilder
						.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
				notifBuilder.setContentIntent(resultPendingIntent);

				Notification notification = notifBuilder.build();
				notification.defaults |= Notification.DEFAULT_SOUND;
				notification.defaults |= Notification.DEFAULT_VIBRATE;
				notification.flags = Notification.FLAG_AUTO_CANCEL;

				NotificationManager mNotificationManager = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);

				mNotificationManager.notify(0, notification);

				editor.putBoolean("already_executed_end_free_period", true).commit();

			}

			else {

			}

		}

		if (i.hasExtra("remindMe")) {

			// if the alarm is to remind user to enter transactions
			
			

			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(context);

			// check if the user still wants these alarms, if YES, do it...if NO
			// do nothing
			if (prefs.getBoolean("pref_daily_reminder", true)) {

				NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(
						context)
						.setSmallIcon(R.drawable.ic_launcher)
						.setContentTitle(
								context.getResources().getString(
										R.string.enter_transactions))
						.setContentText(
								context.getResources().getString(
										R.string.remind_me));

				// Creates an explicit intent for an Activity in your app
				
				Intent resultIntent = null;
				
				int numOfAccounts = new DbClass(context).getNumberOfAccounts();
				
				if(numOfAccounts <=0){
					resultIntent = new Intent(context, MainActivity.class);
					resultIntent.putExtra("zero", 0);
				}
				else{
				
				resultIntent = new Intent(context, NewTransaction.class);}

				// The stack builder object will contain an artificial back
				// stack for the
				// started Activity.
				// This ensures that navigating backward from the Activity leads
				// out of
				// your application to the Home screen.
				TaskStackBuilder stackBuilder = TaskStackBuilder
						.create(context);
				// Adds the back stack for the Intent (but not the Intent
				// itself)
				stackBuilder.addParentStack(MainActivity.class);
				// Adds the Intent that starts the Activity to the top of the
				// stack
				stackBuilder.addNextIntent(resultIntent);
				PendingIntent resultPendingIntent = stackBuilder
						.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
				notifBuilder.setContentIntent(resultPendingIntent);

				Notification notification = notifBuilder.build();
				notification.defaults |= Notification.DEFAULT_SOUND;
				notification.defaults |= Notification.DEFAULT_VIBRATE;
				notification.flags = Notification.FLAG_AUTO_CANCEL;

				NotificationManager mNotificationManager = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);

				mNotificationManager.notify(0, notification);

			}

			else {

				// do nothing

			}

		} else {

			Integer alarmId = i.getIntExtra("alarmId", 1);
			Long recurId = i.getLongExtra("recurId", 1);

			// transfer scheduled tx details to transaction table
			ConversionClass mCC = new ConversionClass(context);
			Calendar c = Calendar.getInstance();
			String displayDate = mCC.dateForDisplayFromCalendarInstance(c
					.getTime());
			String dbDate = mCC.dateForDb(displayDate);

			DbClass mDb = new DbClass(context);
			mDb.transferSchTxDetailsWithAlarmId(alarmId, dbDate);

			Bundle bundle = mDb.getNotifBundle(alarmId);

			String acName = bundle.getString("acName");

			Long amount = bundle.getLong("amount");

			String displayAmount = mCC.valueConverter(amount);

			if (recurId == 1) {
				// if recurId is "No Recur" then change DONE to true
				mDb.updateScheduleTxAtAlarmId(alarmId);
			}

			else {

				String endDate = mDb.getEndDateAt(alarmId);

				Date endDatee = mCC.returnDateObjectFromDbDateString(endDate);
				Date nowDate = mCC
						.returnDateObjectFromDisplayDateString(displayDate);

				if (nowDate.equals(endDatee) || nowDate.after(endDatee)) {
					// if today is endDate, change DONE to true and cancel the
					// alarm
					mDb.updateScheduleTxAtAlarmId(alarmId);

					cancelAlarm(alarmId, context, recurId);
				}

				updateNextDateAtAlarmId(alarmId, recurId, displayDate, mCC, mDb);

			}

			NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(
					context)
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentTitle(
							context.getResources().getString(
									R.string.scheduled_transaction))
					.setContentText(acName + " : " + displayAmount);

			// Creates an explicit intent for an Activity in your app
			Intent resultIntent = new Intent(context,
					FinishedTransactions.class);

			// The stack builder object will contain an artificial back stack
			// for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out
			// of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(FinishedTransactions.class);
			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
					0, PendingIntent.FLAG_UPDATE_CURRENT);
			notifBuilder.setContentIntent(resultPendingIntent);

			Notification notification = notifBuilder.build();
			notification.defaults |= Notification.DEFAULT_SOUND;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notification.flags = Notification.FLAG_AUTO_CANCEL;

			NotificationManager mNotificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);

			mNotificationManager.notify(alarmId, notification);

		}

		wakeLock.release();

	}

	private void updateNextDateAtAlarmId(Integer alarmId, Long recurId,
			String displayDate, ConversionClass mCC, DbClass mDb) {
		// TODO Auto-generated method stub
		// displayDate is the current date in the display format
		String nextDate = null;

		if (recurId == 2) {
			// if daily, nextDate = nowDate + 1 day

			nextDate = mCC.addTheseDaysToDateAndReturnDbDate(1, displayDate);

		}

		if (recurId == 3) {
			// if weekly, nextDate = nowDate + 7 days
			nextDate = mCC.addTheseDaysToDateAndReturnDbDate(7, displayDate);
		}

		if (recurId == 4) {
			// if monthly, nextDate = nowDate + 30 days
			nextDate = mCC.addTheseDaysToDateAndReturnDbDate(30, displayDate);
		}

		if (recurId == 5) {
			// if yearly, nextDate = nowDate + 365 days
			nextDate = mCC.addTheseDaysToDateAndReturnDbDate(365, displayDate);
		}
		Log.d("nextDate(AlarmReceiver)", nextDate);
		mDb.updateNextDateAtAlarmId(alarmId, nextDate);

	}

	public void cancelAlarm(Integer alarmId, Context context, Long recurId) {
		// TODO Auto-generated method stub
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		Intent i = new Intent(context, AlarmReceiver.class);

		i.putExtra("alarmId", alarmId);
		i.putExtra("recurId", recurId);

		PendingIntent pi = PendingIntent.getBroadcast(context, alarmId, i,
				PendingIntent.FLAG_UPDATE_CURRENT);

		am.cancel(pi);
	}

}
