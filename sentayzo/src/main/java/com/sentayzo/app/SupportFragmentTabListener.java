package com.sentayzo.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.util.Log;
 
public class SupportFragmentTabListener<T extends Fragment> implements TabListener {
	private Fragment mFragment;
	private final FragmentActivity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	private final int mfragmentContainerId;
	private  ViewPager mViewPager;
	
 
	public SupportFragmentTabListener(FragmentActivity activity, String tag, Class<T> clz, ViewPager vPager) {
		Log.d("tabListener", "1");
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mfragmentContainerId = android.R.id.content;
		mViewPager = vPager;
		Log.d("tabListener", "2");
		
	}
 
	public SupportFragmentTabListener(int fragmentContainerId, FragmentActivity activity, String tag, Class<T> clz, ViewPager vPager) {
		Log.d("tabListener", "3");
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mfragmentContainerId = fragmentContainerId;
		mViewPager = vPager;
		Log.d("tabListener", "4");
	}
 
	/* The following are each of the ActionBar.TabListener callbacks */
 
	public void onTabSelected(Tab tab, FragmentTransaction sft) {
		// Check if the fragment is already initialized
		Log.d("tabListener", "5");
		if (mFragment == null) {
			// If not, instantiate and add it to the activity
			Log.d("tabListener", "6");
			 
			mFragment = Fragment.instantiate(mActivity, mClass.getName());
			Log.d("tabListener", "7");
			mViewPager.setCurrentItem(tab.getPosition());
			Log.d("tabListener", "8");
			sft.add(mfragmentContainerId, mFragment, mTag);
			Log.d("tabListener", "9");
			
			
		} else {
			// If it exists, simply attach it in order to show it
			Log.d("tabListener", "9");
			sft.attach(mFragment);
			Log.d("tabListener", "10");
			mViewPager.setCurrentItem(tab.getPosition());
			Log.d("tabListener", "11");
		}
	}
 
	public void onTabUnselected(Tab tab, FragmentTransaction sft) {
		if (mFragment != null) {
			// Detach the fragment, because another one is being attached
			Log.d("tabListener", "12");
			sft.detach(mFragment);
			Log.d("tabListener", "13");
		}
	}
 
	public void onTabReselected(Tab tab, FragmentTransaction sft) {
		// User selected the already selected tab. Usually do nothing.
		Log.d("tabListener", "14");
	}
}