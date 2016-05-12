package com.sentayzo.app;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class AlarmService extends IntentService {

	public AlarmService() {
		super("AlarmService");

	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.d("in AlarmService", "alarm Service");
		ConversionClass mCC = new ConversionClass(this);
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(this, AlarmReceiver.class);

		if (intent.hasExtra("endTime")) {

			Long endTime = intent.getLongExtra("endTime",
					(System.currentTimeMillis() + (5 * 24 * 60 * 60 * 1000)));

			i.putExtra("end_of_free_period", true);

			PendingIntent pi = PendingIntent.getBroadcast(this, 1001, i,
					PendingIntent.FLAG_UPDATE_CURRENT);

			setAlarm(endTime, pi, (long) 1, am);
			
			
			//set up remindMe for the first Time
			
			Calendar c = Calendar.getInstance();
			Calendar cal = Calendar.getInstance();

			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());

			String remindTime = prefs.getString("pref_set_time", "20:00");

			Integer lastHour = TimePickerPreference.getHour(remindTime);
			Integer lastMinute = TimePickerPreference.getMinute(remindTime);

			cal.setTimeInMillis(System.currentTimeMillis());
			cal.set(Calendar.HOUR_OF_DAY, lastHour);
			cal.set(Calendar.MINUTE, lastMinute);

			Long dailyTime = c.getTimeInMillis();

			Intent remindMeIntent = new Intent(this, AlarmReceiver.class);
			remindMeIntent.putExtra("remindMe", true);

			PendingIntent piRemindMe = PendingIntent.getBroadcast(this, 0,
					remindMeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, dailyTime,
					AlarmManager.INTERVAL_DAY, piRemindMe);

		}

		else if (intent.hasExtra("remindMe")) {
			// this is to set alarm for reminder
			Integer lastHour = intent.getIntExtra("lastHour", 20);
			Integer lastMinute = intent.getIntExtra("lastMinute", 0);

			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			c.set(Calendar.HOUR_OF_DAY, lastHour);
			c.set(Calendar.MINUTE, lastMinute);

			Long dailyTime = c.getTimeInMillis();

			i.putExtra("remindMe", true);

			PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,
					PendingIntent.FLAG_UPDATE_CURRENT);

			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, dailyTime,
					AlarmManager.INTERVAL_DAY, pi);

		}

		else if (intent.hasExtra("reSchedule_alarms")) {
			// check if the intent is for a boot
			// here we get all alarms from schedule table and reset them

			// set up for trial period

			SharedPreferences billingPrefs = this.getSharedPreferences(
					"my_billing_prefs", 0);

			if (billingPrefs.getBoolean("already_executed_end_free_period",
					false) == false) {
				Intent tIntent = new Intent(this, AlarmReceiver.class);

				tIntent.putExtra("end_of_free_period", true);

				Long endTime = billingPrefs.getLong(
						"KEY_END_OF_FREE_TRIAL_PERIOD",
						System.currentTimeMillis() + (5 * 24 * 60 * 60 * 1000));

				PendingIntent pInte = PendingIntent.getBroadcast(this, 1001,
						tIntent, PendingIntent.FLAG_UPDATE_CURRENT);

				setAlarm(endTime, pInte, (long) 1, am);

			}

			// set up for remindME

			Calendar c = Calendar.getInstance();
			Calendar cal = Calendar.getInstance();

			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());

			String remindTime = prefs.getString("pref_set_time", "20:00");

			Integer lastHour = TimePickerPreference.getHour(remindTime);
			Integer lastMinute = TimePickerPreference.getMinute(remindTime);

			cal.setTimeInMillis(System.currentTimeMillis());
			cal.set(Calendar.HOUR_OF_DAY, lastHour);
			cal.set(Calendar.MINUTE, lastMinute);

			Long dailyTime = c.getTimeInMillis();

			Intent remindMeIntent = new Intent(this, AlarmReceiver.class);
			remindMeIntent.putExtra("remindMe", true);

			PendingIntent piRemindMe = PendingIntent.getBroadcast(this, 0,
					remindMeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, dailyTime,
					AlarmManager.INTERVAL_DAY, piRemindMe);

			// reset the scheduled transactions
			String displayDate = mCC.dateForDisplayFromCalendarInstance(c
					.getTime());
			Date nowDate = mCC
					.returnDateObjectFromDisplayDateString(displayDate);

			DbClass mDb = new DbClass(this);
			Bundle[] schDetails = mDb.getAllPendingSchDetails();
			if (schDetails == null) {
				// do nothing
			} else {

				String startDate, nextDate;
				Long recurId;
				Integer alarmId;
				Date startDatee;
				Long startDateMillis, nextDateMillis;

				for (Bundle bundle : schDetails) {
					startDate = bundle.getString("startDate");
					nextDate = bundle.getString("nextDate");
					recurId = bundle.getLong("recurId");
					alarmId = bundle.getInt("alarmId");

					i.putExtra("alarmId", alarmId);
					i.putExtra("recurId", recurId);

					startDatee = mCC
							.returnDateObjectFromDbDateString(startDate);

					if (nowDate.after(startDatee)) {
						nextDateMillis = mCC.dateForAlarmManager(nextDate);
						PendingIntent pi = PendingIntent.getBroadcast(this,
								alarmId, i, PendingIntent.FLAG_UPDATE_CURRENT);

						setAlarm(nextDateMillis, pi, recurId, am);

					} else {
						startDateMillis = mCC.dateForAlarmManager(nextDate);
						PendingIntent pi = PendingIntent.getBroadcast(this,
								alarmId, i, PendingIntent.FLAG_UPDATE_CURRENT);

						setAlarm(startDateMillis, pi, recurId, am);

					}

				}
			}

		} else if(intent.hasExtra("new_sch_tx_t")) {
			// if intent is from new schedule activity
			String startDate = intent.getStringExtra("startDate");

			Integer alarmId = intent.getIntExtra("alarmId", 1);
			Long recurId = intent.getLongExtra("recurId", 1);

			Long startDateMills;
			// Long endDateMills;

			startDateMills = mCC.dateForAlarmManager(startDate);

			i.putExtra("alarmId", alarmId);
			i.putExtra("recurId", recurId);

			PendingIntent pi = PendingIntent.getBroadcast(this, alarmId, i,
					PendingIntent.FLAG_UPDATE_CURRENT);

			setAlarm(startDateMills, pi, recurId, am);

			Log.d("in AlarmService", "alarm Service end");

		}
	}

	private void setAlarm(Long startDateMills, PendingIntent pi, Long recurId,
			AlarmManager am) {
		// TODO Auto-generated method stub
		if (recurId == 1) {
			// if no recur
			am.set(AlarmManager.RTC_WAKEUP, startDateMills, pi);
		}

		if (recurId == 2) {
			// if daily
			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, startDateMills,
					AlarmManager.INTERVAL_DAY, pi);
		}

		if (recurId == 3) {
			// if weekly
			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, startDateMills,
					AlarmManager.INTERVAL_DAY * 7, pi);
		}

		if (recurId == 4) {
			// if monthly
			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, startDateMills,
					AlarmManager.INTERVAL_DAY * 30, pi);
		}

		if (recurId == 5) {
			// if yearly
			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, startDateMills,
					AlarmManager.INTERVAL_DAY * 365, pi);
		}

	}

}
