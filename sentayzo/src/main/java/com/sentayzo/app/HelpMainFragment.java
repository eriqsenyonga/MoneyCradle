package com.sentayzo.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class HelpMainFragment extends Fragment implements View.OnClickListener {

	View rootView;
	TextView whatsNew, helpTopicOne, helpTopicTwo, helpTopicThree, helpQtnOne,
			helpQtnTwo, helpQtnThree, helpQtnFour, helpQtnFive;
	int mTextResourceId;
	Communicator comm;
	android.app.ActionBar ab;
	Tracker t;

	public HelpMainFragment() {
		// required empty constructor
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_help_main, container,
				false);

		helpTopicOne = (TextView) rootView
				.findViewById(R.id.tv_about_money_cradle);
		helpTopicTwo = (TextView) rootView.findViewById(R.id.tv_quick_overview);
		helpTopicThree = (TextView) rootView
				.findViewById(R.id.tv_additional_features);

		helpQtnOne = (TextView) rootView
				.findViewById(R.id.tv_what_is_an_account);
		helpQtnTwo = (TextView) rootView
				.findViewById(R.id.tv_how_do_i_change_currency);
		helpQtnThree = (TextView) rootView
				.findViewById(R.id.tv_what_is_a_payee);
		helpQtnFour = (TextView) rootView
				.findViewById(R.id.tv_what_is_account_type);
		helpQtnFive = (TextView) rootView
				.findViewById(R.id.tv_features_nolonger_working);
		
		whatsNew = (TextView) rootView.findViewById(R.id.tv_whatsnew);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		t = ((ApplicationClass) getActivity().getApplication())
				.getTracker(ApplicationClass.TrackerName.APP_TRACKER);

		t.setScreenName("HelpMainFragment");
		t.send(new HitBuilders.ScreenViewBuilder().build());

		comm = (Communicator) getActivity();

		helpTopicOne.setOnClickListener(this);
		helpTopicTwo.setOnClickListener(this);
		helpTopicThree.setOnClickListener(this);
		helpQtnOne.setOnClickListener(this);
		helpQtnTwo.setOnClickListener(this);
		helpQtnThree.setOnClickListener(this);
		helpQtnFour.setOnClickListener(this);
		helpQtnFive.setOnClickListener(this);
		whatsNew.setOnClickListener(this);
		//helpQtnFive.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == helpTopicOne) {

			comm.respond(R.string.help_topic_about_money_cradle,
					R.string.help_topic_one);
		}
		if (v == helpTopicTwo) {
			comm.respond(R.string.help_topic_quick_overview,
					R.string.help_topic_two);

		}
		if (v == helpTopicThree) {
			comm.respond(R.string.help_topic_additional_features,
					R.string.help_topic_three);
		}
		if (v == helpQtnOne) {
			comm.respond(R.string.help_question_what_is_an_account,
					R.string.help_question_one);

		}
		if (v == helpQtnTwo) {
			comm.respond(R.string.help_question_currency_change,
					R.string.help_question_two);

		}
		if (v == helpQtnThree) {
			comm.respond(R.string.help_question_what_is_a_payee,
					R.string.help_question_three);
		}
		if (v == helpQtnFour) {
			comm.respond(R.string.help_question_what_are_account_types,
					R.string.help_question_four);

		}
		if (v == helpQtnFive) {
			
			comm.respond(R.string.help_question_some_features_no_longer_work,
					R.string.help_question_five);
		}
		
		if (v == whatsNew) {
			
			comm.respond(R.string.whatsnewDetails,
					R.string.whatsnew);
		}

	}

	
	
}
