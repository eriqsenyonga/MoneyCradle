package com.sentayzo.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView txList;
    TransactionRecyclerAdapter adapter;
    TxListInteraction txListInteraction;
    TextView tvViewAllTx, tvViewMoreStats;
    Toolbar toolBar;
    TextView tv_totalAmount_in, tv_totalAmount_out, tv_balanceAmount;
    static Long idOfEntity; // this is the id of the account/project/category/payee to be viewed in overview
    String history, title;
    SharedPreferences billingPrefs;
    ConversionClass mCC;
    Tracker t;
    Typeface robotoMedium;
    Typeface robotoThin;
    static int KEY_ACCOUNT_OVERVIEW = 1;
    static int KEY_PROJECT_OVERVIEW = 2;

    PieChart pieChart;
    ArrayList<PieEntry> yEntries;
    ArrayList<String> xEntries;

    int whichOverview;
    Cursor relevantCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        yEntries = new ArrayList<>();
        xEntries = new ArrayList<>();

        t = ((ApplicationClass) getApplication())
                .getTracker(ApplicationClass.TrackerName.APP_TRACKER);

        t.setScreenName("OverviewActivity");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        //establish which overview we want. whether for an account, a project, category, or payee

        idOfEntity = getIntent().getLongExtra("Id", 1);

        if (getIntent().getIntExtra("whichOverview", 1) == KEY_ACCOUNT_OVERVIEW) {

            whichOverview = KEY_ACCOUNT_OVERVIEW;
            relevantCursor = getContentResolver().query(Uri.parse("content://"
                            + "SentayzoDbAuthority" + "/transactionsAc"), null, null, null,
                    null);

        } else if (getIntent().getIntExtra("whichOverview", 1) == KEY_PROJECT_OVERVIEW) {

            whichOverview = KEY_PROJECT_OVERVIEW;
            relevantCursor = getContentResolver().query(Uri.parse("content://"
                            + "SentayzoDbAuthority" + "/transactionsPr"), null, null, null,
                    null);
        }


        DbClass mDb = new DbClass(this);
        mDb.open();
        title = mDb.getTitle(idOfEntity, whichOverview);
        mDb.close();
        history = getResources().getString(R.string.overview);
        getSupportActionBar().setTitle(history + ":" + title);


        mCC = new ConversionClass(this);

        tvViewMoreStats = (TextView) findViewById(R.id.tv_more_stats);
        tv_totalAmount_in = (TextView) findViewById(R.id.tv_total_in);
        tv_totalAmount_out = (TextView) findViewById(R.id.tv_total_out);
        tv_balanceAmount = (TextView) findViewById(R.id.tv_balance_amount);

        robotoThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        robotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");


        setupFonts();


        pieChart = (PieChart) findViewById(R.id.pc_overview);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(40f);
        pieChart.setUsePercentValues(true);

        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelTypeface(robotoMedium);
        pieChart.setDrawEntryLabels(false);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setTransparentCircleColor(Color.WHITE);


        setValuesForTextViews();
        tvViewAllTx = (TextView) findViewById(R.id.tv_view_all_tx);

        tvViewAllTx.setOnClickListener(this);

        tvViewMoreStats.setOnClickListener(this);

        txList = (RecyclerView) findViewById(R.id.recycler_view);

        txListInteraction = new TxListInteraction(this, TxListInteraction.ACCOUNT_TRANSACTIONS);

        adapter = new TransactionRecyclerAdapter(relevantCursor, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position, long id) {
                txListInteraction.start(id, adapter);
            }
        });

        txList.setAdapter(adapter);

        txList.setLayoutManager(new LinearLayoutManager(this));

        txList.addItemDecoration(new ListDividerDecoration(this));

    }


    private void setValuesForTextViews() {
        // TODO Auto-generated method stub

        DbClass mDbClass = new DbClass(this);

        Bundle bundle = mDbClass.getIncomeAndExpenseTotals(idOfEntity, whichOverview);

        Long totIncome = bundle.getLong("totIncome", 0);

        Long totExpense = bundle.getLong("totExpense", 0);

        Long totBalance = bundle.getLong("totBalance", 0);


        setPieChartData(totIncome, totExpense);

        tv_totalAmount_in.setText(mCC.valueConverter(totIncome));
        tv_totalAmount_in.setTextColor(getResources().getColor(
                R.color.display_green));
        tv_totalAmount_out.setText(mCC.valueConverter(totExpense));
        tv_totalAmount_out.setTextColor(getResources().getColor(
                R.color.display_red));
        tv_balanceAmount.setText(mCC.valueConverter(totBalance));

        //   animateTextView(0, 322000000, tv_balanceAmount);


    }

    private void setPieChartData(Long totIncome, Long totExpense) {


        yEntries.add(new PieEntry(mCC.valueConverterReturnFloat(totExpense * (-1)), "Expense"));
        yEntries.add(new PieEntry(mCC.valueConverterReturnFloat(totIncome), "Income"));


        xEntries.add("Income");
        xEntries.add("Expense");

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(ContextCompat.getColor(OverviewActivity.this, R.color.graph_red));
        colors.add(ContextCompat.getColor(OverviewActivity.this, R.color.graph_green));

        PieDataSet dataSet = new PieDataSet(yEntries, "Income vs Expense");
        dataSet.setSliceSpace(2);

        //dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieChart.setData(pieData);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        pieChart.invalidate();


    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) initialValue, (int) finalValue);
        valueAnimator.setDuration(1500);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                // textview.setText(valueAnimator.getAnimatedValue().toString());

                textview.setText(mCC.valueConverter(Long.valueOf(valueAnimator.getAnimatedValue().toString())));


            }
        });
        valueAnimator.start();

    }


    public void setupFonts() {

/* Setting up fonts and appearances of the textViews */

        if (Build.VERSION.SDK_INT < 23) {
            //if api below 23

            tv_totalAmount_in.setTypeface(robotoMedium);
            // tv_totalAmount_in.setTextAppearance(this, R.style.boldText);

            tv_totalAmount_out.setTypeface(robotoMedium);
            //  tv_totalAmount_out.setTextAppearance(this, R.style.boldText);

            tv_balanceAmount.setTypeface(robotoMedium);
            tv_balanceAmount.setTextAppearance(this, R.style.boldText);

        } else {
            //if api above 23
            tv_totalAmount_in.setTypeface(robotoThin);
            tv_totalAmount_in.setTextAppearance(R.style.boldText);

            tv_totalAmount_out.setTypeface(robotoThin);
            tv_totalAmount_out.setTextAppearance(R.style.boldText);

            tv_balanceAmount.setTypeface(robotoThin);
            tv_balanceAmount.setTextAppearance(R.style.boldText);

        }

    }


    @Override
    public void onClick(View v) {

        if (v == tvViewMoreStats) {

            Intent i = new Intent(OverviewActivity.this, StatisticsActivity.class);
            i.putExtra("singleEntity", true);
            i.putExtra("Id", idOfEntity);
            i.putExtra("whichOverview", whichOverview);

            startActivity(i);


        }

        if (v == tvViewAllTx) {

            Intent i = new Intent(OverviewActivity.this,
                    TransactionHistoryActivity.class);
            i.putExtra("Id", idOfEntity);
            i.putExtra("whichOverview", whichOverview);
            startActivity(i);


        }
    }
}
