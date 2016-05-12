package com.sentayzo.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class HelpDetailsFragment extends Fragment {

	TextView details, title;
	int mTextResourceId, titleTextId;

	View rootView;
	Tracker t;
	
	public HelpDetailsFragment(){
		//required empty constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_help_topic_details, container,
				false);
		
		details = (TextView) rootView.findViewById(R.id.tv_details);
		title = (TextView) rootView.findViewById(R.id.tv_title);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
t = ((ApplicationClass) getActivity().getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);
		
		t.setScreenName("HelpDetailsFragment");
	    t.send(new HitBuilders.ScreenViewBuilder().build());
		title.setText(getActivity().getResources().getString(titleTextId));
		
		details.setMovementMethod (LinkMovementMethod.getInstance());
		details.setText (Html.fromHtml (getString (mTextResourceId)));
	}
	
	
	public void changeStringResourceId(int textResId, int titleId){
		
		
		mTextResourceId = textResId;
		titleTextId = titleId;
		
		
	}
	
	

}
