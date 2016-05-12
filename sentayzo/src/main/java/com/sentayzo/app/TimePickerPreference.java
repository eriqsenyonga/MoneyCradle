package com.sentayzo.app;

import java.text.NumberFormat;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerPreference extends DialogPreference {
	 private int lastHour=0;
	  private int lastMinute=0;
	  private TimePicker picker=null;
	  Context context;
	  
	  public static int getHour(String time) {
	    String[] pieces=time.split(":");
	    
	    return(Integer.parseInt(pieces[0]));
	  }

	  public static int getMinute(String time) {
	    String[] pieces=time.split(":");
	    
	    return(Integer.parseInt(pieces[1]));
	  }

	  public TimePickerPreference(Context ctxt, AttributeSet attrs) {
	    super(ctxt, attrs);
	    context = ctxt;
	    setPositiveButtonText(ctxt.getResources().getString(R.string.set));
	    setNegativeButtonText(ctxt.getResources().getString(R.string.cancel));
	  }

	  @Override
	  protected View onCreateDialogView() {
	    picker=new TimePicker(getContext());
	    
	    return(picker);
	  }
	  
	  @Override
	  protected void onBindDialogView(View v) {
	    super.onBindDialogView(v);
	    
	    picker.setCurrentHour(lastHour);
	    picker.setCurrentMinute(lastMinute);
	  }
	  
	  @Override
	  protected void onDialogClosed(boolean positiveResult) {
	    super.onDialogClosed(positiveResult);

	    if (positiveResult) {
	      lastHour=picker.getCurrentHour();
	      lastMinute=picker.getCurrentMinute();
	      
	      
	      
	      NumberFormat nf = NumberFormat.getIntegerInstance();
	     
	      nf.setMinimumIntegerDigits(2);
	      
	     Log.d( "lastminute format", nf.format(lastMinute));
	      
	      String time=String.valueOf(lastHour)+":"+nf.format(lastMinute);
	      
	      if (callChangeListener(time)) {
	    	  
	    	  Log.d("timeSaved", time);
	    	  setSummary(time);
	        persistString(time);
	        
	        Intent i = new Intent(context, AlarmService.class);
	        i.putExtra("remindMe", true);
	        i.putExtra("lastHour", lastHour);
	        i.putExtra("lastMinute", lastMinute);
	        context.startService(i);
	        
	      }
	    }
	  }

	  @Override
	public void setSummary(CharSequence summary) {
		// TODO Auto-generated method stub
		super.setSummary(summary);
	}

	@Override
	  protected Object onGetDefaultValue(TypedArray a, int index) {
	    return(a.getString(index));
	  }

	  @Override
	  protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
	    String time=null;
	    
	    if (restoreValue) {
	      if (defaultValue==null) {
	        time=getPersistedString("08:00");
	      }
	      else {
	        time=getPersistedString(defaultValue.toString());
	      }
	    }
	    else {
	      time=defaultValue.toString();
	    }
	    
	    lastHour=getHour(time);
	    lastMinute=getMinute(time);
	  }

	
	  
	  
	}