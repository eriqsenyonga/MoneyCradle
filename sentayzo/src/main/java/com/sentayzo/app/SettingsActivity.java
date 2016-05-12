package com.sentayzo.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SettingsActivity extends PreferenceActivity {

	ListPreference currencyListPreference;
	String[] entries, entryValues;
	DbClass mDb;
	Handler mHandler;
	Toolbar toolBar;

	EditTextPreference PINentry;

	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String stringValue = value.toString();

			if (preference instanceof ListPreference) {
				// For list preferences, look up the correct display value in
				// the preference's 'entries' list.
				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);

				// Set the summary to reflect the new value.
				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: null);

			}

			else {
				// For all other preferences, set the summary to the value's
				// simple string representation.
				preference.setSummary(stringValue);
			}
			return true;
		}
	};

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	/*	final Toolbar toolbar=getToolbar();
	    toolbar.setTitle(R.string.settings);
	    setEnabledActionBarShadow(true);
	    */

		addPreferencesFromResource(R.xml.preferences);

		mHandler = new Handler();

		mDb = new DbClass(SettingsActivity.this);
		entryValues = mDb.getDistinctCurrencyCodes();
		entries = mDb.getDistinctCurrencyNames();

		currencyListPreference = (ListPreference) getPreferenceManager()
				.findPreference("pref_default_currency");

		currencyListPreference.setEntries(entries);
		currencyListPreference.setEntryValues(entryValues);

		

		PINentry = (EditTextPreference) getPreferenceManager().findPreference(
				"pref_PIN_entry");

		// PINentry.setDialogLayoutResource(R.layout.pin_entry_setting);

		bindPreferenceSummaryToValue(findPreference("pref_default_country"));
		bindPreferenceSummaryToValue(findPreference("pref_default_currency"));
		bindPreferenceSummaryToValue(findPreference("pref_set_time"));
		bindPreferenceSummaryToValue(findPreference("pref_start_date"));

	}

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 * 
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	private static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference
				.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				preference,
				PreferenceManager.getDefaultSharedPreferences(
						preference.getContext()).getString(preference.getKey(),
						""));
	}
	
	 @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        Toolbar bar;

	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
	            LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
	            bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
	            root.addView(bar, 0); // insert at top
	        } else {
	            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
	            ListView content = (ListView) root.getChildAt(0);

	            root.removeAllViews();

	            bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);


	            int height;
	            TypedValue tv = new TypedValue();
	            if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
	                height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
	            }else{
	                height = bar.getHeight();
	            }

	            content.setPadding(0, height, 0, 0);

	            root.addView(content);
	            root.addView(bar);
	        }

	        bar.setNavigationOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Intent i = new Intent(SettingsActivity.this, MainActivity.class);
	    			startActivity(i);
	            	
	                finish();
	            }
	        });
	    }
/*
	@Override
	protected int getPreferencesXmlId() {
		// TODO Auto-generated method stub
		return R.xml.preferences;
	}
	
	*/
}
