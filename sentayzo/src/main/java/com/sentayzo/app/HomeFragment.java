package com.sentayzo.app;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * A simple {@link Fragment} subclass. Use the {@link HomeFragment#newInstance}
 * factory method to create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // com.sentayzo.app.SlidingTabLayout mSlidingTabLayout;
    MyPagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    View rootView;
    TabLayout tabLayout;
    Tracker t;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        mViewPager = (ViewPager) rootView.findViewById(R.id.pagerz);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        t = ((ApplicationClass) getActivity().getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);

        t.setScreenName("HomeFragment");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        mPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), getActivity());

        mViewPager.setAdapter(mPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorHeight(15);


        mViewPager.setPageTransformer(true, new DepthPageTransformer());



        //	mSlidingTabLayout.setCustomTabView(R.layout.custom_tab_title,
        //			R.id.tabtext);

        //	mSlidingTabLayout.setViewPager(mViewPager);
        setRetainInstance(true);



    }

}
