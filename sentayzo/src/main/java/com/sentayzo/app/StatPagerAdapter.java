package com.sentayzo.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Eric on 3/27/2017.
 */

public class StatPagerAdapter extends FragmentPagerAdapter {

    Context context;
    public static int EXPENSES = 0;
    public static int INCOME = 1;

    Long idOfEntity;
    int whichOverview;
    boolean isSingleEntity = false;




    public StatPagerAdapter(FragmentManager fm, Context c) {
        super(fm);
        context = c;
    }

    public StatPagerAdapter(FragmentManager fm, Context c, int whichOverview, boolean isSingleEntity, Long idOfEntity) {
        super(fm);
        context = c;
        this.idOfEntity = idOfEntity;
        this.whichOverview = whichOverview;
        this.isSingleEntity = isSingleEntity;
    }



    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();



        if (position == 0) {

            StatCategoryFragment fragment = new StatCategoryFragment();

            bundle.putInt("which", EXPENSES);

            if(isSingleEntity){

                bundle.putLong("idOfEntity", idOfEntity);
                bundle.putInt("whichOverview", whichOverview);
                bundle.putBoolean("isSingleEntity", isSingleEntity);
            }


            fragment.setArguments(bundle);

            return fragment;

        } else if (position == 1) {

            StatCategoryFragment fragment = new StatCategoryFragment();


            bundle.putInt("which", INCOME);

            if(isSingleEntity){

                bundle.putLong("idOfEntity", idOfEntity);
                bundle.putInt("whichOverview", whichOverview);
                bundle.putBoolean("isSingleEntity", isSingleEntity);
            }

            fragment.setArguments(bundle);

            return fragment;
        } else if (position == 2) {

            IncExpenseFragment fragment = new IncExpenseFragment();


            return fragment;


        }


        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return context.getResources().getString(R.string.expenses);

        }

        if (position == 1) {
            return context.getResources().getString(R.string.income);

        }
        if (position == 2) {
            return context.getResources().getString(R.string.income_vs_expenses);

        }

        return super.getPageTitle(position);
    }
}
