package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class PayeeTxView extends AppCompatActivity {

    RecyclerView txList;
    TransactionRecyclerAdapter adapter;
    TxListInteraction txListInteraction;

    Toolbar toolBar;

    String payeeViewName, title, history; // Name of category whose transactions
    // are to be viewed
    static Long payeeViewId; // Id of category whose transactions are to be
    // viewed

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

        t.setScreenName("PayeeTxView");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        i = getIntent();
        mCC = new ConversionClass(this);
        payeeViewId = i.getLongExtra("payeeId", 1);
        payeeViewName = i.getStringExtra("payeeName");
        history = getResources().getString(R.string.history);
        title = history + " " + payeeViewName;

        // set up for ads

        billingPrefs = getSharedPreferences("my_billing_prefs", 0);

        if ((billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD", true) == false)
                && (billingPrefs.getBoolean("KEY_PURCHASED_ADS", false) == false)) {


        } else {

//            findViewById(R.id.adFragment).setVisibility(View.GONE);

        }

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(title);

        tv_totalAmount = (TextView) findViewById(R.id.tv_totalView);

        txList = (RecyclerView) findViewById(R.id.recycler_view);

        txListInteraction = new TxListInteraction(this, TxListInteraction.PAYEE_TRANSACTIONS);

        adapter = new TransactionRecyclerAdapter(getContentResolver().query(Uri.parse("content://"
                        + "SentayzoDbAuthority" + "/transactionsPayee"), null, null, null,
                null), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position, long id) {
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
        getMenuInflater().inflate(R.menu.payee_tx_view, menu);
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
        ConversionClass mCC = new ConversionClass(this);
        DbClass mDbClass = new DbClass(PayeeTxView.this);
        Long totalAmount = mDbClass.totalTransactionAmountOfPayee(payeeViewId);

        Log.d("Payeetxview", "totalAmount = " + totalAmount);

        String totAmt;

        totAmt = mCC.valueConverter(totalAmount);

        Log.d("payeetxview", totAmt);

        if (totalAmount < 0) {

            tv_totalAmount.setTextColor(Color.RED);
        }

        if (totalAmount >= 0) {

            tv_totalAmount.setTextColor(Color.parseColor("#08ad14"));
        }

        tv_totalAmount.setText(totAmt);

    }

    public void viewTransactionDetails(Long transactionId, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getResources().getString(R.string.transaction_details));

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.transaction_view, null);

        TextView tvDate = (TextView) view.findViewById(R.id.tv_txdate);
        TextView tvAcc = (TextView) view.findViewById(R.id.tv_txaccName);
        TextView tvPayee = (TextView) view.findViewById(R.id.tv_txpayee);
        TextView tvCat = (TextView) view.findViewById(R.id.tv_txcategory);
        TextView tvAmt = (TextView) view.findViewById(R.id.tv_txamount);
        TextView tvNote = (TextView) view.findViewById(R.id.tv_txnote);
        TextView tvProject = (TextView) view.findViewById(R.id.tv_txproject);

        Bundle bundle = null;

        DbClass miDbClass = new DbClass(context);

        Long categId = miDbClass.getCategoryId(transactionId);

        miDbClass.open();

        if (categId == 24 || categId == 2) {
            // 24 is catId for transfer
            // 2 is catId for opening balance
            bundle = miDbClass.getTxInfoWithoutProject(transactionId);

            TextView tvProjectTitle = (TextView) view
                    .findViewById(R.id.tv_txproj);
            tvProjectTitle.setVisibility(View.GONE);
            tvProject.setVisibility(View.GONE);
        } else {
            bundle = miDbClass.getTxInfo(transactionId);
        }

        miDbClass.close();

        tvDate.setText(mCC.dateForDisplay(bundle.getString("txdate")));
        tvAcc.setText(bundle.getString("txacc"));
        tvPayee.setText(bundle.getString("txpayee"));
        tvCat.setText(bundle.getString("txcat"));
        tvAmt.setText(mCC.valueConverter(bundle.getLong("txamt")));
        tvNote.setText(bundle.getString("txnote"));
        tvProject.setText(bundle.getString("txproject"));

        builder.setView(view);

        builder.setNeutralButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated
                        // method stub

                    }
                });

        Dialog txViewDialog = builder.create();
        txViewDialog.show();

    }
}