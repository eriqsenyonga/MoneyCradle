package com.sentayzo.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class MyPagerAdapter extends FragmentPagerAdapter {
	
	Context context;

	public MyPagerAdapter(FragmentManager fm, Context ctx) {
		super(fm);
		// TODO Auto-generated constructor stub
		context = ctx;
		Log.d("pagerAdapter", "1");
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub

		Fragment txList = new TransactionListFragment();


		Fragment reportsFrag = new ReportsFragment();

		Fragment acList = new AccountsList();


		if (position == 0) {

			return acList;
		}

		if (position == 1) {

			return txList;
		}

		if (position == 2) {

			return reportsFrag;
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d("pagerAdapter", "9");
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
			String accountsTabTitle = context.getResources().getString(R.string.accounts);
			return accountsTabTitle;
		}

		if (position == 1) {
			String historyTabTitle = context.getResources().getString(R.string.history_tab);
			return historyTabTitle;
		}
		if (position == 2) {
			String reportsTabTitle = context.getResources().getString(R.string.reports);
			return reportsTabTitle;
		}

		return super.getPageTitle(position);
	}

}