package com.sentayzo.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class LoginActivity extends AppCompatActivity {
	
	SharedPreferences sharedPrefs ;
	EditText ed1, ed2, ed3, ed4;
	String p1 = "0", p2 = "0", p3 = "0", p4 = "0";
	String PINstring;
	String savedPIN;
	Tracker t;
	Toolbar toolBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		toolBar = (Toolbar) findViewById(R.id.app_bar);
		setSupportActionBar(toolBar);
t = ((ApplicationClass) getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);
		
		t.setScreenName("LoginActivity");
	    t.send(new HitBuilders.ScreenViewBuilder().build());
		
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		savedPIN= sharedPrefs.getString("pref_PIN_entry", "0001");
		
		Log.d("savedPIN is",savedPIN);
		
		ed1 = (EditText) findViewById(R.id.et_PIN1);
		ed2 = (EditText) findViewById(R.id.et_PIN2);
		ed3 = (EditText) findViewById(R.id.et_PIN3);
		ed4 = (EditText) findViewById(R.id.et_PIN4);
		
		ed1.requestFocus();
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	
		
		
		ed1.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
				if(count == 1){
					
					ed2.requestFocus();
				}
				
			}});
		
		ed2.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
				if(count == 1){
					
					ed3.requestFocus();
				}
				
			}});
		
		ed3.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
				if(count == 1){
					
					ed4.requestFocus();
				}
				
			}});
		
		ed4.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
				if(count == 1){
					
					
					String e1 = ed1.getText().toString();
					String e2 = ed2.getText().toString();
					String e3 = ed3.getText().toString();
					String e4 = ed4.getText().toString();
					
					PINstring = e1 + e2 + e3 + e4;
					
					Log.d("PINstring is ", PINstring);
					
				if(PINstring.equals(savedPIN)){
						
						Intent i = new Intent(LoginActivity.this, MainActivity.class);
						i.putExtra("zero", 0);
						startActivity(i);
						
						finish();
					
					}
				
			
				/*	if(e1.equals(p1) && e2.equals(p2) && e3.equals(p3) &&
							e4.equals(p4))	{
						
						Toast.makeText(LoginActivity.this, "Correct", Toast.LENGTH_LONG).show();
						
						Intent i = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(i);
						
						
						
						finish();
					}		*/
					else{
						
						Toast.makeText(LoginActivity.this, "Wrong...retype", Toast.LENGTH_LONG).show();
						
						ed1.setText("");
						ed2.setText("");
						ed3.setText("");
						ed4.setText("");
						
						ed1.requestFocus();
						
					}
					
					
					
				}
				
			}});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
