package com.sentayzo.app;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment implements View.OnClickListener {

    // Toolbar toolBar;
    ImageButton ibPrev, ibNext, ibPeriod;
    TextView tvCurrentPeriod;
    PopupMenu popupMenu;
    ViewPager viewPager;
    TabLayout tabLayout;
    StatPagerAdapter adapter;


    public static int PERIOD_MONTH = 0;
    public static int PERIOD_YEAR = 1;
    public static int PERIOD_CUSTOM = 2;


    int currentPeriodType;

    FloatingActionsMenu fam;
    FloatingActionButton fab;


    Calendar c;
    ConversionClass mCC;
    View root;


    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_statistics, container, false);


        ibPrev = (ImageButton) root.findViewById(R.id.b_previous);
        ibNext = (ImageButton) root.findViewById(R.id.b_next);
        ibPeriod = (ImageButton) root.findViewById(R.id.b_period);
        tvCurrentPeriod = (TextView) root.findViewById(R.id.tv_current_period);
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) root.findViewById(R.id.view_pager);


        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        currentPeriodType = PERIOD_MONTH;

        fam = (FloatingActionsMenu) getActivity().findViewById(R.id.fam_fab);
        fam.setVisibility(View.GONE);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);


        if (fab.getVisibility() == View.GONE) {

            fab.setVisibility(View.VISIBLE);
            fab.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up));

        }

        fab.setOnClickListener(this);


        c = Calendar.getInstance();
        mCC = new ConversionClass(getActivity());

        tvCurrentPeriod.setText(mCC.dateForStatDisplayFromCalendarInstance(c.getTime()));


        adapter = new StatPagerAdapter(getChildFragmentManager(), getActivity());

        viewPager.setAdapter(adapter);


        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(2);


        ibPrev.setOnClickListener(this);
        ibNext.setOnClickListener(this);
        ibPeriod.setOnClickListener(this);
        tvCurrentPeriod.setOnClickListener(this);

        setUpPopupMenu();
    }


    private void setUpPopupMenu() {

        popupMenu = new PopupMenu(getActivity(), ibPeriod);

        popupMenu.getMenuInflater().inflate(R.menu.period_filter_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.menu_month) {

                    //TODO ADD WHAT HAPPENS ON MONTH

                    currentPeriodType = PERIOD_MONTH;
                    tvCurrentPeriod.setText(mCC.dateForStatDisplayFromCalendarInstance(c.getTime()));
                    updateFragmentsInViewPager();

                    return true;

                } else if (itemId == R.id.menu_year) {

                    //TODO ADD WHAT HAPPENS ON YEAR

                    currentPeriodType = PERIOD_YEAR;
                    tvCurrentPeriod.setText(mCC.dateYearForStatDisplayFromCalendarInstance(c.getTime()));
                    updateFragmentsInViewPager();
                    return true;

                } else if (itemId == R.id.menu_custom) {

                    //TODO ADD WHAT HAPPENS ON CUSTOM
                    Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_LONG).show();
                    return true;

                }


                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {


        if (v == ibPeriod || v == tvCurrentPeriod) {

            popupMenu.show();

        }

        if (v == ibNext) {

            //add one to display stat date eg if month,year  add one month to the month

            if (currentPeriodType == PERIOD_MONTH) {


                String newStatDisplayDate = mCC.statAddOneMonth(tvCurrentPeriod.getText().toString());

                tvCurrentPeriod.setText(newStatDisplayDate);


            } else if (currentPeriodType == PERIOD_YEAR) {

                String newStatDisplayDate = mCC.statAddOneYear(tvCurrentPeriod.getText().toString());

                tvCurrentPeriod.setText(newStatDisplayDate);
            }


            updateFragmentsInViewPager();


        }

        if (v == ibPrev) {

            //add one to display stat date eg if month,year  add one month to the month

            if (currentPeriodType == PERIOD_MONTH) {


                String newStatDisplayDate = mCC.statSubtractOneMonth(tvCurrentPeriod.getText().toString());

                tvCurrentPeriod.setText(newStatDisplayDate);

                //  StatCategoryFragment page1 = (StatCategoryFragment) adapter.getItem(0);
                //  StatCategoryFragment page2 = (StatCategoryFragment) adapter.getItem(1);


            } else if (currentPeriodType == PERIOD_YEAR) {

                String newStatDisplayDate = mCC.statSubtractOneYear(tvCurrentPeriod.getText().toString());

                tvCurrentPeriod.setText(newStatDisplayDate);
            }

            updateFragmentsInViewPager();


        }

        if (v == fab) {


            showAccountsFilterDialog();


        }

    }

    private void showAccountsFilterDialog() {

        DbClass mDb = new DbClass(getActivity());

        Cursor accountsCursor = mDb.getAllAccounts();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMultiChoiceItems(accountsCursor, DbClass.KEY_ACCOUNT_OPEN, DbClass.KEY_ACCOUNT_NAME, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {




            }
        });


        builder.setTitle(R.string.accounts_filter);

        builder.setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        Dialog d = builder.create();
        d.show();


    }

    public void updateFragmentsInViewPager() {

        StatCategoryFragment page1 = (StatCategoryFragment) getChildFragmentManager().getFragments().get(0);
        StatCategoryFragment page2 = (StatCategoryFragment) getChildFragmentManager().getFragments().get(1);

        page1.periodChanged(currentPeriodType, tvCurrentPeriod.getText().toString(), "", "");
        page2.periodChanged(currentPeriodType, tvCurrentPeriod.getText().toString(), "", "");


    }
}
