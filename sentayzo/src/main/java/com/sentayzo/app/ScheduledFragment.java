package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ScheduledFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match


    ViewPager mScheduledViewPager;
    View rootView;
    ScheduledPagerAdapter mScheduledPagerAdapter;
    TabLayout tabLayout;
    Tracker t;

    SharedPreferences billingPrefs;

    public static ScheduledFragment newInstance() {
        // Required empty public constructor
        return new ScheduledFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_scheduled, container,
                false);

        mScheduledViewPager = (ViewPager) rootView
                .findViewById(R.id.scheduled_pagerz);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorHeight(15);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        t = ((ApplicationClass) getActivity().getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);

        t.setScreenName("ScheduledFragment");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        mScheduledPagerAdapter = new ScheduledPagerAdapter(
                getChildFragmentManager(), getActivity());

        mScheduledViewPager.setAdapter(mScheduledPagerAdapter);

        tabLayout.setupWithViewPager(mScheduledViewPager);

        billingPrefs = getActivity()
                .getSharedPreferences("my_billing_prefs", 0);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        inflater.inflate(R.menu.category_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Boolean freePeriod = billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD",
                true);
        Boolean unlocked = billingPrefs.getBoolean("KEY_PURCHASED_UNLOCK",
                false);

        int id = item.getItemId();
        if (id == R.id.action_new_category) {

            if (freePeriod == true || unlocked == true) {
                Intent i = new Intent(getActivity(),
                        NewScheduledTransactionActivity.class);
                startActivity(i);
            } else {
                // if free trial has expired and nigga hasnt paid for shit
                int numberOfSch = new DbClass(getActivity())
                        .getNumberOfProjects();

                if (numberOfSch < 1) {
                    Intent i = new Intent(getActivity(),
                            NewScheduledTransactionActivity.class);
                    startActivity(i);

                } else {

                    showPaymentDialog(getActivity());

                }

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPaymentDialog(final Context context) {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(context.getResources().getString(
                R.string.payment_dialog_message)
                + "\n\n"
                + context.getResources()
                .getString(R.string.unlock_all_features) + " ?");

        builder.setNegativeButton(
                context.getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });

        builder.setPositiveButton(context.getResources()
                        .getString(R.string.yes),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        Intent i = new Intent(context, StoreActivity.class);
                        startActivity(i);
                    }
                });

        Dialog paymentDialog = builder.create();
        paymentDialog.show();

    }

}
