package com.sentayzo.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class BudgetFragment extends Fragment {
	
	View rootView;
	ProgressBar progressBar;
	 int progressStatus = 0;
	
	  Handler handler = new Handler();

	public BudgetFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_budget, container, false);
		
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
		
		return rootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		 new Thread(new Runnable() {
		     public void run() {
		        while (progressStatus < 100) {
		           progressStatus += 1;
		    // Update the progress bar and display the 
		                         //current value in the text view
		    handler.post(new Runnable() {
		    public void run() {
		       progressBar.setProgress(progressStatus);
		      
		    }
		        });
		        try {
		           // Sleep for 200 milliseconds. 
		                         //Just to display the progress slowly
		           Thread.sleep(100);
		        } catch (InterruptedException e) {
		           e.printStackTrace();
		        }
		     }
		  }
		  }).start();
	}
	
	

}
