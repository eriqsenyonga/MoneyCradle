package com.sentayzo.app;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatCategoryFragment extends Fragment {

    ConversionClass mCC;
    View root;
    RecyclerView recyclerView;
    StatsRecyclerAdapter adapter;
    int whichStatCategory; //expense or income
    int periodType;
    String fromDate;
    String toDate;
    String specificPeriod; //specific period contains date of format MM-yyyy... we extract these in the db
    DbClass mDb;
    Cursor cursor;
    Calendar c;

    Long idOfEntity;
    int whichOverview;
    boolean isSingleEntity = false;


    public StatCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        whichStatCategory = this.getArguments().getInt("which", 0);
        periodType = StatisticsActivity.PERIOD_MONTH;

        if (this.getArguments().getBoolean("isSingleEntity", false)) {

            isSingleEntity = true;
            idOfEntity = this.getArguments().getLong("idOfEntity");
            whichOverview = this.getArguments().getInt("whichOverview");

        }


        root = inflater.inflate(R.layout.fragment_stat_category, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCC = new ConversionClass(getActivity());

        c = Calendar.getInstance();

        // specificPeriod = new String();

        specificPeriod = mCC.dateForStatistics(c.getTime());

        //  Log.d("specific_period_current", specificPeriod);

        mDb = new DbClass(getActivity());

        setUpAdapter();

        adapter = new StatsRecyclerAdapter(cursor, getContext(), whichStatCategory);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addItemDecoration(new ListDividerDecoration(getActivity()));


    }


    public void setUpAdapter() {

        if (whichStatCategory == StatPagerAdapter.EXPENSES) {


            if (periodType == StatisticsActivity.PERIOD_MONTH || periodType == StatisticsActivity.PERIOD_YEAR) {

                if (isSingleEntity) {

                    cursor = mDb.getExpenseCategoryTotals(periodType, specificPeriod, whichOverview, idOfEntity);
                } else {

                    cursor = mDb.getExpenseCategoryTotals(periodType, specificPeriod);
                }

            } else if (periodType == StatisticsActivity.PERIOD_CUSTOM) {

//// TODO: 3/27/2017 what happens for the custom period
                // cursor = mDb.getExpenseCategoryTotals(fromDate, toDate);

            }


        } else if (whichStatCategory == StatPagerAdapter.INCOME) {
            if (periodType == StatisticsActivity.PERIOD_MONTH || periodType == StatisticsActivity.PERIOD_YEAR) {

                if (isSingleEntity) {
                    cursor = mDb.getIncomeCategoryTotals(periodType, specificPeriod, whichOverview, idOfEntity);

                } else {

                    cursor = mDb.getIncomeCategoryTotals(periodType, specificPeriod);
                }

            } else if (periodType == StatisticsActivity.PERIOD_CUSTOM) {

                // TODO: 3/27/2017 what happens for custom period
                //   cursor = mDb.getIncomeCategoryTotals(fromDate, toDate);

            }

        }


    }


    public void periodChanged(int periodType, String specificPeriod, String fromDate, String toDate) {

        // Log.d("period_type_sent", "" + periodType);
        //  Log.d("period_type_current", "" + this.periodType);


        this.periodType = periodType;
        this.specificPeriod = mCC.dateForStatUseFromDisplay(specificPeriod);


        if (periodType == StatisticsActivity.PERIOD_CUSTOM) {
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        setUpAdapter();

        adapter.changeCursor(cursor);

        adapter.notifyDataSetChanged();

    }
}
