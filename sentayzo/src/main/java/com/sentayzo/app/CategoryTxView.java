package com.sentayzo.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class CategoryTxView extends AppCompatActivity {

    RecyclerView txList;
    TransactionRecyclerAdapter adapter;
    TxListInteraction txListInteraction;
    Toolbar toolBar;


    String catViewName, title, history; // Name of category whose transactions
    // are to be viewed
    static Long catViewId; // Id of category whose transactions are to be viewed

    TextView tv_totalAmount, tv_dummy;
    Intent i;
    ConversionClass mCC;
    SharedPreferences billingPrefs;
    Tracker t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_tx_view);
        t = ((ApplicationClass) getApplication())
                .getTracker(ApplicationClass.TrackerName.APP_TRACKER);

        t.setScreenName("CategoryTxView");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        mCC = new ConversionClass(this);
        i = getIntent();

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        catViewId = i.getLongExtra("catId", 1);
        catViewName = i.getStringExtra("catName");
        history = getResources().getString(R.string.history);
        title = history + " " + catViewName;


        getSupportActionBar().setTitle(title);

        tv_totalAmount = (TextView) findViewById(R.id.tv_totalView);
        tv_dummy = (TextView) findViewById(R.id.tv_dummy);



        // rest of form
        txList = (RecyclerView) findViewById(R.id.recycler_view);

        txListInteraction = new TxListInteraction(this, TxListInteraction.CATEGORY_TRANSACTIONS);

        adapter = new TransactionRecyclerAdapter(getContentResolver().query(Uri.parse("content://"
                        + "SentayzoDbAuthority" + "/transactionsCat"), null, null, null,
                null), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position, long id, boolean isLongClick) {
                txListInteraction.start(id, adapter);
            }
        });

        txList.setAdapter(adapter);

        txList.setLayoutManager(new LinearLayoutManager(this));

        txList.addItemDecoration(new ListDividerDecoration(this));

        getTotal();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category_tx_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getTotal() {
        // TODO Auto-generated method stub

        DbClass mDbClass = new DbClass(CategoryTxView.this);
        Long totalAmount = mDbClass.totalTransactionAmountOfCat(catViewId);

        String totAmt;

        totAmt = mCC.valueConverter(totalAmount);

        if (totalAmount < 0) {

            tv_totalAmount.setTextColor(Color.RED);
        }

        if (totalAmount >= 0) {

            tv_totalAmount.setTextColor(Color.parseColor("#08ad14"));
        }

        tv_totalAmount.setText(totAmt);

        Log.d("catId", "" + catViewId);

        Log.d("totalAmount", totAmt);

    }

}
