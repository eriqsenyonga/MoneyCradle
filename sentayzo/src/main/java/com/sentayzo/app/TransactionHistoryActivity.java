package com.sentayzo.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * THIS CLASS IS ONLY FOR THE TRANSACTION HISTORY OF EITHER AN ACCOUNT OR A PROJECT, PERIOD !!!!
 */

public class TransactionHistoryActivity extends AppCompatActivity {


    RecyclerView txList;
    TransactionRecyclerAdapter adapter;
    TxListInteraction txListInteraction;

    Toolbar toolBar;

    static Long idOfEntity; // this is the id of the account to be viewed
    String history, title;
    SharedPreferences billingPrefs;
    ConversionClass mCC;
    Tracker t;


    int whichOverview;
    Cursor relevantCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);



        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        t = ((ApplicationClass) getApplication())
                .getTracker(ApplicationClass.TrackerName.APP_TRACKER);

        t.setScreenName("TransactionHistoryList");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        //establish which overview we want. whether for an account, a project, category, or payee

        idOfEntity = getIntent().getLongExtra("Id", 1);

        if (getIntent().getIntExtra("whichOverview", 1) == OverviewActivity.KEY_ACCOUNT_OVERVIEW) {

            whichOverview = OverviewActivity.KEY_ACCOUNT_OVERVIEW;
            relevantCursor = getContentResolver().query(Uri.parse("content://"
                            + "SentayzoDbAuthority" + "/transactionsAc"), null, null, null,
                    null);

        } else if (getIntent().getIntExtra("whichOverview", 1) == OverviewActivity.KEY_PROJECT_OVERVIEW) {

            whichOverview = OverviewActivity.KEY_PROJECT_OVERVIEW;
            relevantCursor = getContentResolver().query(Uri.parse("content://"
                            + "SentayzoDbAuthority" + "/transactionsPr"), null, null, null,
                    null);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TransactionHistoryActivity.this, NewTransaction.class);

                if (whichOverview == OverviewActivity.KEY_ACCOUNT_OVERVIEW) {
                    i.putExtra("accountId", idOfEntity);
                } else {
                    i.putExtra("projectId", idOfEntity);
                }
                startActivity(i);
            }
        });

        DbClass mDb = new DbClass(this);
        mDb.open();
        title = mDb.getTitle(idOfEntity, whichOverview);
        mDb.close();
        history = getResources().getString(R.string.history);
        getSupportActionBar().setTitle(history + title);

        // set up for ads

        billingPrefs = getSharedPreferences("my_billing_prefs", 0);

        boolean freePeriod = billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD",
                true);

        boolean noAds = billingPrefs.getBoolean("KEY_PURCHASED_ADS", false);

        if ((billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD", true) == false)
                && (billingPrefs.getBoolean("KEY_PURCHASED_ADS", false) == false)) {


        } else {

//            findViewById(R.id.adFragment).setVisibility(View.GONE);

        }

        mCC = new ConversionClass(this);

        txList = (RecyclerView) findViewById(R.id.recycler_view);

        txListInteraction = new TxListInteraction(this, TxListInteraction.ACCOUNT_TRANSACTIONS);

        adapter = new TransactionRecyclerAdapter(relevantCursor, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position, long id, boolean isLongClick) {
                txListInteraction.start(id, adapter);
            }
        });

        txList.setAdapter(adapter);

        txList.setLayoutManager(new LinearLayoutManager(this));

        txList.addItemDecoration(new ListDividerDecoration(this));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
