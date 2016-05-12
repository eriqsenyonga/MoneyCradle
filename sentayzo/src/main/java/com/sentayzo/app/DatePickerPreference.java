package com.sentayzo.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

public class DatePickerPreference extends DialogPreference {

	Calendar c;
	String setDateString;
	DatePicker datePicker;
	ConversionClass mCC;

	private int lastYear = 2014;
	private int lastMonth = 00;
	private int lastDate = 01;

	public DatePickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		mCC = new ConversionClass(context);
		c = Calendar.getInstance();
		setPositiveButtonText(context.getString(R.string.set));
		setNegativeButtonText(context.getResources().getString(R.string.cancel));

		setDialogIcon(null);
		
		
	}

	@Override
	protected View onCreateDialogView() {
		datePicker = new DatePicker(getContext());

		return (datePicker);

	}

	
	
	@SuppressLint("NewApi")
	@Override
	protected void onBindDialogView(View v) {
		super.onBindDialogView(v);
		
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ){
		datePicker.setCalendarViewShown(false);}
		
		datePicker.updateDate(lastYear, lastMonth, lastDate);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// TODO Auto-generated method stub
		super.onDialogClosed(positiveResult);

		if (positiveResult) {

			lastYear = datePicker.getYear();
			lastMonth = datePicker.getMonth();
			lastDate = datePicker.getDayOfMonth();

			String setDate = lastYear + "-" + (lastMonth + 1) + "-" + lastDate;

			setDateString = mCC.dateForDisplay(setDate);

			if (callChangeListener(setDateString)) {

				Log.d("dateSaved", setDateString);
				setSummary(setDateString);
				persistString(setDateString);
			}
		}
	}

	@Override
	public void setSummary(CharSequence summary) {
		// TODO Auto-generated method stub
		super.setSummary(summary);
	}

	@Override
	protected void onSetInitialValue(boolean restorePersistedValue,
			Object defaultValue) {
		// TODO Auto-generated method stub

		String dateString = null;

		if (restorePersistedValue) {

			if (defaultValue == null) {
				dateString = getPersistedString(mCC
						.dateForDisplay("2014-01-01"));
			} else {
				dateString = getPersistedString(defaultValue.toString());
			}

		} else {

			dateString = defaultValue.toString();
		}

		lastYear = getYear(dateString);
		lastMonth = getMonth(dateString);
		lastDate = getDate(dateString);

	}

	private int getDate(String dateString) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd - MMM - yyyy",
				Locale.getDefault());

		Date unformatted;

		try {
			unformatted = sdf.parse(dateString);

			c.setTime(unformatted);

			int date = c.get(Calendar.DATE);

			return date;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	private int getMonth(String dateString) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd - MMM - yyyy",
				Locale.getDefault());

		Date unformatted;

		try {
			unformatted = sdf.parse(dateString);

			c.setTime(unformatted);

			int month = c.get(Calendar.MONTH);

			return month;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	private int getYear(String dateString) {
		// TODO Auto-generated method stub

		SimpleDateFormat sdf = new SimpleDateFormat("dd - MMM - yyyy",
				Locale.getDefault());

		Date unformatted;

		try {
			unformatted = sdf.parse(dateString);

			c.setTime(unformatted);

			int year = c.get(Calendar.YEAR);

			return year;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

}
