package com.sentayzo.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CustomCurrencyPreference extends DialogPreference {

	SharedPreferences customCurrencyPrefs;

	SharedPreferences.Editor editor;

	EditText name, code;

	Spinner groupSeparator, decimalSeparator, numberOfDecimals;

	Integer groupSeparatorPos, decimalSeparatorPos, numberOfDecimalsPos;

	ArrayAdapter<String> dotComma;

	ArrayAdapter<String> commaDot;

	ArrayAdapter<String> zeroOneTwo;

	Context ctx;
	
	String summaryText;
	

	public CustomCurrencyPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
		setDialogLayoutResource(R.layout.custom_currency_dialog);
		customCurrencyPrefs = ctx.getSharedPreferences("custom_currency_prefs",
				0);

		editor = customCurrencyPrefs.edit();
	}

	@Override
	protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
		builder.setTitle(R.string.custom_currency_dialog_title);
		builder.setPositiveButton(ctx.getResources().getString(R.string.save),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						String currencyName = name.getText().toString();

						String currencyCode = code.getText().toString();

						String grpSeparator = null;

						String decSeparator = null;

						Integer numOfDecimals;

						// constraint to make sure name field is not empty
						if (name.getText().length() <= 0
								|| code.getText().length() <= 0) {
							String emptyName = ctx
									.getString(R.string.currNotSet)
									+ "! "
									+ ctx.getString(R.string.NameField)
									+ " / "
									+ ctx.getString(R.string.code)
									+ " "
									+ ctx.getString(R.string.cantBeEmpty);
							Toast.makeText(ctx, emptyName, Toast.LENGTH_LONG)
									.show();

							editor.putBoolean("currency_set", false).apply();

						}

						else {

							if (groupSeparatorPos == 0) {
								// if comma
								grpSeparator = ",";
							} else {
								// if dot
								grpSeparator = ".";
							}

							if (decimalSeparatorPos == 0) {
								// if dot
								decSeparator = ".";
							} else {
								// if comma
								decSeparator = ",";
							}

							numOfDecimals = numberOfDecimalsPos;

							editor.putString("currencyName", currencyName);
							editor.putString("currencyCode", currencyCode);
							editor.putString("groupSeparator", grpSeparator);
							editor.putString("decimalSeparator", decSeparator);
							editor.putInt("numberOfDecimals", numOfDecimals);

							editor.putBoolean("currency_set", true);
							editor.apply();
							
							 summaryText = currencyCode + " (" + currencyName + ")";
							
							

						}

					}
				});
		builder.setNegativeButton(
				ctx.getResources().getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});

		super.onPrepareDialogBuilder(builder);
	}

	@Override
	public void onBindDialogView(View view) {

		String comma = ctx.getString(R.string.comma);

		String dot = ctx.getString(R.string.dot);

		String zero = ctx.getString(R.string.zero);

		String one = ctx.getString(R.string.one);

		String two = ctx.getString(R.string.two);

		String[] commaDotArray = { comma, dot };

		String[] dotCommaArray = { dot, comma };

		String[] decimalsArray = { zero, one, two };

	

		name = (EditText) view.findViewById(R.id.custom_currency_name);
		code = (EditText) view.findViewById(R.id.custom_currency_code);
		
		
		name.setText(customCurrencyPrefs.getString("currencyName", " "));
		code.setText(customCurrencyPrefs.getString("currencyCode", " "));

		groupSeparator = (Spinner) view
				.findViewById(R.id.grouping_separator_spinner);
		decimalSeparator = (Spinner) view
				.findViewById(R.id.decimal_separator_spinner);
		numberOfDecimals = (Spinner) view
				.findViewById(R.id.decimal_number_spinner);

		commaDot = new ArrayAdapter<String>(ctx,
				android.R.layout.simple_spinner_item, commaDotArray);

		commaDot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		groupSeparator.setAdapter(commaDot);

		dotComma = new ArrayAdapter<String>(ctx,
				android.R.layout.simple_spinner_item, dotCommaArray);

		dotComma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		decimalSeparator.setAdapter(dotComma);

		zeroOneTwo = new ArrayAdapter<String>(ctx,
				android.R.layout.simple_spinner_item, decimalsArray);

		zeroOneTwo
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		numberOfDecimals.setAdapter(zeroOneTwo);

		numberOfDecimals
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						
						numberOfDecimals.setSelection((customCurrencyPrefs.getInt(
								"numberOfDecimals", 0)));
						numberOfDecimalsPos = customCurrencyPrefs.getInt("numnberOfDecimals", 0);
						
						numberOfDecimals.setOnItemSelectedListener(new OnItemSelectedListener(){

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								numberOfDecimalsPos = arg2;
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub
								
							}});
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		groupSeparator.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (customCurrencyPrefs.getString("groupSeparator", ",")
						.equals(",")) {
					groupSeparator.setSelection(0);
					groupSeparatorPos = 0;
					
					
				} else if (customCurrencyPrefs.getString("groupSeparator", ",")
						.equals(".")){
					groupSeparator.setSelection(1);
					groupSeparatorPos = 1;
				}
				
				groupSeparator.setOnItemSelectedListener(new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						groupSeparatorPos = arg2;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}});
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		decimalSeparator
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						
						
						if (customCurrencyPrefs.getString("decimalSeparator", ".").equals(
								".")) {
							
							decimalSeparator.setSelection(0);
							
							decimalSeparatorPos = 0;
							
						} else if (customCurrencyPrefs.getString("decimalSeparator", ".")
								.equals(",")) {
							decimalSeparator.setSelection(1);
							decimalSeparatorPos = 1;
						}
						
						
						decimalSeparator.setOnItemSelectedListener(new OnItemSelectedListener(){

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								
								
								decimalSeparatorPos = arg2;
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub
								
							}});
						
						
						
						
						
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		super.onBindDialogView(view);
	}

	/*
	 * @Override public Dialog getDialog() { // TODO Auto-generated method stub
	 * 
	 * 
	 * 
	 * 
	 * getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
	 * 
	 * @Override public void onShow(DialogInterface dialog) { // TODO
	 * Auto-generated method stub Button b = ((AlertDialog) getDialog())
	 * .getButton(AlertDialog.BUTTON_POSITIVE);
	 * 
	 * b.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub String currencyName = name.getText().toString();
	 * 
	 * String currencyCode = code.getText().toString();
	 * 
	 * String grpSeparator = null;
	 * 
	 * String decSeparator = null;
	 * 
	 * int numOfDecimals = (Integer) null;
	 * 
	 * // constraint to make sure name field is not empty if
	 * (name.getText().length() <= 0 || code.getText().length() <= 0) { String
	 * emptyName = ctx .getString(R.string.NameField) + " / " +
	 * ctx.getString(R.string.code) + " " + ctx.getString(R.string.cantBeEmpty);
	 * Toast.makeText(ctx, emptyName, Toast.LENGTH_LONG) .show();
	 * 
	 * }
	 * 
	 * else {
	 * 
	 * if (groupSeparatorPos == 0) { // if comma grpSeparator = ","; } else { //
	 * if dot grpSeparator = "."; }
	 * 
	 * if (decimalSeparatorPos == 0) { // if dot grpSeparator = "."; } else { //
	 * if comma grpSeparator = ","; }
	 * 
	 * numOfDecimals = numberOfDecimalsPos;
	 * 
	 * editor.putString("currencyName", currencyName);
	 * editor.putString("currencyCode", currencyCode);
	 * editor.putString("groupSeparator", grpSeparator);
	 * editor.putString("decimalSeparator", decSeparator);
	 * editor.putInt("numberOfDecimals", numOfDecimals); editor.commit();
	 * 
	 * getDialog().dismiss(); } } });
	 * 
	 * Button b2 = ((AlertDialog) getDialog())
	 * .getButton(AlertDialog.BUTTON_POSITIVE);
	 * 
	 * b2.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub
	 * 
	 * getDialog().dismiss();
	 * 
	 * }});
	 * 
	 * 
	 * } }); return super.getDialog(); }
	 */

	@Override
	public void setSummary(CharSequence summary) {
		// TODO Auto-generated method stub
		super.setSummary(summary);
	}

	@Override
	protected void onSetInitialValue(boolean restorePersistedValue,
			Object defaultValue) {
		// TODO Auto-generated method stub
		super.onSetInitialValue(restorePersistedValue, defaultValue);
		
		
		if(customCurrencyPrefs.getBoolean("currency_set", false) == true){
			
		/*	editor.putString("currencyName", currencyName);
			editor.putString("currencyCode", currencyCode);
			editor.putString("groupSeparator", grpSeparator);
			editor.putString("decimalSeparator", decSeparator);
			editor.putInt("numberOfDecimals", numOfDecimals);
			*/
			
			
		}
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// TODO Auto-generated method stub
		super.onDialogClosed(positiveResult);
		
		
		
		if(callChangeListener(summaryText)){
		setSummary(summaryText);
		persistString(summaryText);
		}
	}
	
	

}
