package com.sentayzo.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

public class HelpActivityNew extends AppCompatActivity implements Communicator {

	FrameLayout container;
	FragmentManager fm;
	Toolbar toolBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_activity_new);

		container = (FrameLayout) findViewById(R.id.fragments_here);

		toolBar = (Toolbar) findViewById(R.id.app_bar);
		setSupportActionBar(toolBar);

		Fragment mainHelp = new HelpMainFragment();

		fm = getSupportFragmentManager();

		fm.beginTransaction().replace(R.id.fragments_here, mainHelp).commit();

	}

	@Override
	public void respond(int stringResourceId, int titleStringId) {
		// TODO Auto-generated method stub

		Fragment detailsHelp = new HelpDetailsFragment();

		((HelpDetailsFragment) detailsHelp).changeStringResourceId(
				stringResourceId, titleStringId);

		fm.beginTransaction().replace(R.id.fragments_here, detailsHelp)
				.addToBackStack(null).commit();

	}

}
