package com.sentayzo.app;

import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolBar;
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


    int whichOverview;
    Long idOfEntity;
    boolean singleEntity = false;

    Calendar c;
    ConversionClass mCC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DbClass mDb = new DbClass(this);


        ibPrev = (ImageButton) findViewById(R.id.b_previous);
        ibNext = (ImageButton) findViewById(R.id.b_next);
        ibPeriod = (ImageButton) findViewById(R.id.b_period);
        tvCurrentPeriod = (TextView) findViewById(R.id.tv_current_period);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        currentPeriodType = PERIOD_MONTH;


        if (getIntent().hasExtra("singleEntity")) {

            singleEntity = getIntent().getBooleanExtra("singleEntity", false);
            whichOverview = getIntent().getIntExtra("whichOverview", OverviewActivity.KEY_ACCOUNT_OVERVIEW);
            idOfEntity = getIntent().getLongExtra("Id", 1);

            getSupportActionBar().setTitle(getString(R.string.stats) + ": " + mDb.getTitle(idOfEntity, whichOverview));

        }

        c = Calendar.getInstance();
        mCC = new ConversionClass(this);

        tvCurrentPeriod.setText(mCC.dateForStatDisplayFromCalendarInstance(c.getTime()));


        if (singleEntity == true) {

            adapter = new StatPagerAdapter(getSupportFragmentManager(), this, whichOverview, true, idOfEntity);
        } else {

            adapter = new StatPagerAdapter(getSupportFragmentManager(), this);
        }
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

        popupMenu = new PopupMenu(this, ibPeriod);

        popupMenu.getMenuInflater().inflate(R.menu.period_filter_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.menu_month) {

                    //TODO ADD WHAT HAPPENS ON MONTH
                    Toast.makeText(StatisticsActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();

                    currentPeriodType = PERIOD_MONTH;
                    tvCurrentPeriod.setText(mCC.dateForStatDisplayFromCalendarInstance(c.getTime()));
                    updateFragmentsInViewPager();

                    return true;

                } else if (itemId == R.id.menu_year) {

                    //TODO ADD WHAT HAPPENS ON YEAR
                    Toast.makeText(StatisticsActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();

                    currentPeriodType = PERIOD_YEAR;
                    tvCurrentPeriod.setText(mCC.dateYearForStatDisplayFromCalendarInstance(c.getTime()));
                    updateFragmentsInViewPager();
                    return true;

                } else if (itemId == R.id.menu_custom) {

                    //TODO ADD WHAT HAPPENS ON CUSTOM
                    Toast.makeText(StatisticsActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                    return true;

                }


                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
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


    }


    public void updateFragmentsInViewPager() {

        StatCategoryFragment page1 = (StatCategoryFragment) getSupportFragmentManager().getFragments().get(0);
        StatCategoryFragment page2 = (StatCategoryFragment) getSupportFragmentManager().getFragments().get(1);

        page1.periodChanged(currentPeriodType, tvCurrentPeriod.getText().toString(), "", "");
        page2.periodChanged(currentPeriodType, tvCurrentPeriod.getText().toString(), "", "");


    }
}
