package com.sentayzo.app;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class NewTransfer extends AppCompatActivity implements
        LoaderCallbacks<Cursor>, OnClickListener {


    Spinner accountFrom, accountTo;
    Button dateButton;
    EditText transferAmount, et_transferNote;
    SimpleCursorAdapter accountsAdapter;
    LoaderManager mLoaderManager;
    int transferTxLinkId, accountFromPosition, accountToPosition,
            transferTxLink = 1;
    int accountLoaderId = 1;
    Fragment bannerAd;
    Calendar c;
    String buttonTextDate;
    Long accountFromId, accountToId;
    ConversionClass mCC;
    Toolbar toolBar;

    RelativeLayout relativeLayout;
    Tracker t;
    SharedPreferences sharedPrefs, billingPrefs;
    String sharedPreferenceName = "mySharedPrefs";
    String KEY_TRANSFER_NUMBER = "transfer number";

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transfer);
        t = ((ApplicationClass) getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);

        t.setScreenName("NewTransfer");
        t.send(new HitBuilders.ScreenViewBuilder().build());
        i = getIntent();

        mCC = new ConversionClass(NewTransfer.this);

        sharedPrefs = getSharedPreferences(sharedPreferenceName,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor mEditor = sharedPrefs.edit();

        if (sharedPrefs.getBoolean("first_time", true)) {

            mEditor.putLong(KEY_TRANSFER_NUMBER, 1);

            mEditor.putBoolean("first_time", false);

            mEditor.commit();

        }

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(R.string.new_transfer_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);

        dateButton = (Button) findViewById(R.id.spinner_newTransferDates);
        accountFrom = (Spinner) findViewById(R.id.spinner_account_from);
        accountTo = (Spinner) findViewById(R.id.spinner_account_to);
        transferAmount = (EditText) findViewById(R.id.et_amount_transfer);
        et_transferNote = (EditText) findViewById(R.id.tr_note);


        billingPrefs = getSharedPreferences("my_billing_prefs", 0);

        boolean freePeriod = billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD",
                true);

        boolean noAds = billingPrefs.getBoolean("KEY_PURCHASED_ADS", false);

        if ((billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD", true) == false)
                && (billingPrefs.getBoolean("KEY_PURCHASED_ADS", false) == false)) {

            Log.d("1stssssssrrrttt", "111ssstttttt");


        } else {
            Log.d("2222nnnnnnddddd", "2222222nnnnnnddddd");
            findViewById(R.id.adFragment).setVisibility(View.GONE);

        }
        c = Calendar.getInstance();

        buttonTextDate = mCC.dateForDisplayFromCalendarInstance(c.getTime());

        dateButton.setText(buttonTextDate);

        dateButton.setOnClickListener(this);

        mLoaderManager = getSupportLoaderManager();

        mLoaderManager.initLoader(accountLoaderId, null, this);

        String[] from = new String[]{DbClass.KEY_ACCOUNT_NAME};

        int[] to = new int[]{android.R.id.text1};

        accountsAdapter = new SimpleCursorAdapter(getBaseContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);

        accountsAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accountFrom.setAdapter(accountsAdapter);
        accountTo.setAdapter(accountsAdapter);

        accountFrom.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (i.hasExtra("accountId")) {
                    Log.d("hasExtra", "accountId");
                    Long accountId = i.getLongExtra("accountId", 1);

                    for (int pos = accountsAdapter.getCount(); pos >= 0; pos--) {

                        if (accountsAdapter.getItemId(pos) == accountId) {

                            accountFrom.setSelection(pos);

                            accountFromId = accountId;
                            accountFromPosition = pos;
                            break;
                        }
                    }

                } else {

                    accountFromId = arg3;
                }

                accountFrom
                        .setOnItemSelectedListener(new OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0,
                                                       View arg1, int arg2, long arg3) {
                                // TODO Auto-generated method stub
                                accountFromId = arg3;
                                accountFromPosition = arg2;

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub

                            }
                        });

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        accountTo.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                accountToId = arg3;
                accountToPosition = arg2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_transfer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ConversionClass mCC = new ConversionClass(NewTransfer.this);

        if (id == R.id.ab_saveNewTransfer) {

            String dateString = mCC.dateForDb(dateButton.getText().toString());

            if (transferAmount.getText().length() < 1) {
                String emptyAmount = getResources().getString(
                        R.string.amountField)
                        + " " + getResources().getString(R.string.cantBeEmpty);
                Toast.makeText(getApplicationContext(), emptyAmount,
                        Toast.LENGTH_LONG).show();

                return false;

            }

            String transferNote = et_transferNote.getText().toString();

            Long spend = (long) 1, receive = (long) 2;
            String amountString = transferAmount.getText().toString();

            Long trAmountPlus = mCC.valueConverter(amountString);

            Long trAmountMinus = (-1) * trAmountPlus;

            Log.d("dateString", dateString);

            if (accountFromId == accountToId) {
                // if account from and account to are the same, do nothing

                String sameAccount = getResources().getString(
                        R.string.accountCantBeSame);
                Toast.makeText(getApplicationContext(), sameAccount,
                        Toast.LENGTH_LONG).show();

                return false;
            }

            Long transferNumber = sharedPrefs.getLong(KEY_TRANSFER_NUMBER, (long) 1);
            Log.d("TransferNumber", "" + transferNumber);

            Long categoryId = (long) 24;

            Cursor c = accountsAdapter.getCursor();
            c.moveToPosition(accountFromPosition);
            String accountFromName = c.getString(c
                    .getColumnIndex(DbClass.KEY_ACCOUNT_NAME));
            Log.d("FROM:", "" + accountFromName);

            c.moveToPosition(accountToPosition);
            String accountToName = c.getString(c
                    .getColumnIndex(DbClass.KEY_ACCOUNT_NAME));
            Log.d("TO:", "" + accountToName);
            Log.d("AmountPlus", "" + trAmountPlus);
            Log.d("AmountMinus", "" + trAmountMinus);

            DbClass mDb = new DbClass(NewTransfer.this);

            mDb.open();

            mDb.insertOrCheckForPayee(accountToName);
            mDb.insertOrCheckForPayee(accountFromName);

            mDb.newTransferFromThisAccount(dateString, accountFromName,
                    accountToName, accountFromId, accountToId, trAmountMinus,
                    trAmountPlus, spend, receive, categoryId, transferNote,
                    transferNumber);

            Long nextTransferNumber = transferNumber + 1;
            Log.d("nextTransferNumber", "" + nextTransferNumber);
            SharedPreferences.Editor editor = sharedPrefs.edit();

            editor.putLong(KEY_TRANSFER_NUMBER, nextTransferNumber);

            editor.commit();

            Intent i = new Intent(NewTransfer.this, MainActivity.class);

            startActivity(i);

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // TODO Auto-generated method stub

        return new CursorLoader(this, Uri.parse("content://"
                + "SentayzoDbAuthority" + "/accountspinner"), null, null, null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        // TODO Auto-generated method stub
        accountsAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
        accountsAdapter.swapCursor(null);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub

        if (view == dateButton) {
            // if the dateButton is clicked

            try {
                DatePickerDialog datePicker = new DatePickerDialog(
                        NewTransfer.this,
                        new OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view,
                                                  int selectedYear, int selectedMonth,
                                                  int selectedDay) {
                                // TODO Auto-generated method stub

                                String dateSetString = selectedYear + "-"
                                        + (selectedMonth + 1) + "-"
                                        + selectedDay;

                                String displayDate = mCC
                                        .dateForDisplay(dateSetString);

                                dateButton.setText(displayDate);

                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));

                datePicker.setTitle("Select Date");
                datePicker.setCancelable(false);
                datePicker.show();
            } catch (Exception e) {
                e.getStackTrace();
                Log.d("Exception", e.toString());
            }

        }

    }

}
