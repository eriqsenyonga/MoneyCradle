package com.sentayzo.app;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReportsFragment extends ListFragment {

	String[] options;
	ListView listView;
	

	public ReportsFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.fragment_reports, container,
				false);
		
		

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		listView = getListView();
		
		options = getResources().getStringArray(R.array.reportsFragmentList);

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, options);
		
		
		listView.setAdapter(adapter1);
		
		
		
		
		

		listView.setAdapter(adapter1);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				
				
				if(arg2 == 0){
					//if general is clicked
					
					Intent i =  new Intent(getActivity(), ReportsViewActivity.class);
					i.putExtra("configNumber", arg2);
					startActivity(i);
					
					
					
				}

				if (arg2 == 1) {
					// if "By Account is clicked"
					
					Intent i =  new Intent(getActivity(), ReportsViewActivity.class);
					i.putExtra("configNumber", arg2);
					startActivity(i);
					
					

				}

				else if (arg2 == 2) {
					// if "By Category" is clicked
					Intent i =  new Intent(getActivity(), ReportsViewActivity.class);
					i.putExtra("configNumber", arg2);
					startActivity(i);
				}

				else if (arg2 == 3) {
					// if Payee is clicked
					Intent i =  new Intent(getActivity(), ReportsViewActivity.class);
					i.putExtra("configNumber", arg2);
					startActivity(i);
				}
				
				else if (arg2 == 4 ) {
					// if Payee is clicked
					Intent i =  new Intent(getActivity(), ReportsViewActivity.class);
					i.putExtra("configNumber", arg2);
					startActivity(i);
				}
				
				else if (arg2 == 9) {
					// if Full Report is clicked
					Intent i = new Intent(getActivity(),
							FinancialStatement.class);

					startActivity(i);

				}
				
				else if(arg2 == 10){
					File moneyCradleFolder = new File(Environment.getExternalStorageDirectory(), "Money Cradle");
					
					if(!moneyCradleFolder.exists()){
						
						moneyCradleFolder.mkdirs(); 
						Log.d("folder created", "folder created");
					}
					
					File csv = new File(moneyCradleFolder, "CSV Reports");
					 if(!csv.exists()){
						 
						 csv.mkdirs();
					 }
					 
					 File database = new File(moneyCradleFolder, "Database backup");
					 if(!database.exists()){
						 
						 database.mkdirs();
					 }
					
					 ExportToCSV csvExp = new ExportToCSV(getActivity());
					 
					 csvExp.execute();
					
					
				}
				else{
					Intent i =  new Intent(getActivity(), ReportsViewActivity.class);
					i.putExtra("configNumber", arg2);
					startActivity(i);
					
					
				}

			}
		});
		
		
	}

}
