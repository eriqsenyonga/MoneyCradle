package com.sentayzo.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ScheduledPagerAdapter extends FragmentPagerAdapter {
	Context context;

	public ScheduledPagerAdapter(FragmentManager fm, Context ctx) {
		super(fm);
		// TODO Auto-generated constructor stub
		context = ctx;

	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub

		Fragment pendingList = new PendingTxFragment();

		Fragment finishedList = new FinishedTxFragment();

		if (position == 0) {

			return pendingList;
		}

		if (position == 1) {

			return finishedList;
		}

		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		if (position == 0) {
			String pendingTabTitle = context.getResources().getString(
					R.string.pending);
			return pendingTabTitle;
		}

		if (position == 1) {

			String finishedTabTitle = context.getResources().getString(
					R.string.finished);
			return finishedTabTitle;
		}

		return super.getPageTitle(position);
	}

}
