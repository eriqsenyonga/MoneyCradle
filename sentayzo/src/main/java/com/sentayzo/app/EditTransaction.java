package com.sentayzo.app;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mvc.imagepicker.ImagePicker;

public class EditTransaction extends AppCompatActivity implements
        LoaderCallbacks<Cursor>, OnClickListener {

    Button dateButton;
    ImageButton calcButton, newCatButton, newProjectButton, addPhoto;
    TextView tvPhotoPath;
    EditText et_amount, et_tx_note;
    AutoCompleteTextView et_payee;
    Spinner accountSpinner, categorySpinner, transactionTypeSpinner,
            projectSpinner;
    LoaderManager mLoaderManager = getSupportLoaderManager();
    SimpleCursorAdapter accountAdapter, catAdapter, txTypeAdapter,
            payeeAdapter, projectAdapter;
    Long accountId, categoryId, transactionTypeId, projectId;
    Tracker t;
    Long accountTypeId1, accountTypeId2;
    Calendar c = Calendar.getInstance();
    ;
    int accountLoaderId = 2, categoryLoaderId = 3,
            transactionTypeLoaderIdCash = 4, transactionTypeLoaderIdBank = 5,
            transactionTypeLoaderIdAsset = 6, payeeLoaderId = 7,
            projectLoaderId = 8;
    String buttonTextDate;
    Bundle bundle;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
            Locale.getDefault());

    SimpleDateFormat sdf2 = new SimpleDateFormat("dd - MMM - yyyy",
            Locale.getDefault());
    CharSequence stringTyped;
    Boolean userIsInTheMiddleOfTypingANumber = false;
    ConversionClass mCC;
    SharedPreferences billingPrefs;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        t = ((ApplicationClass) getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);


        t.setScreenName("EditTransaction");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);

        mCC = new ConversionClass(this);

        Intent i = getIntent();
        bundle = i.getBundleExtra("txBundle");

        tvPhotoPath = (TextView) findViewById(R.id.tv_photo_uri);
        addPhoto = (ImageButton) findViewById(R.id.ib_add_photo);

        newCatButton = (ImageButton) findViewById(R.id.b_new_cat);
        dateButton = (Button) findViewById(R.id.spinner_newTransactionDates);
        accountSpinner = (Spinner) findViewById(R.id.spinner_accounts);
        et_payee = (AutoCompleteTextView) findViewById(R.id.et_payee);
        categorySpinner = (Spinner) findViewById(R.id.spinner_categories);
        et_amount = (EditText) findViewById(R.id.et_amount);
        transactionTypeSpinner = (Spinner) findViewById(R.id.spinner_transactionType);
        calcButton = (ImageButton) findViewById(R.id.b_calculator);
        newProjectButton = (ImageButton) findViewById(R.id.b_new_project);
        et_tx_note = (EditText) findViewById(R.id.tx_note);
        projectSpinner = (Spinner) findViewById(R.id.spinner_projects);

        //set up for ads

        billingPrefs = getSharedPreferences("my_billing_prefs", 0);

        if ((billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD", true) == false)
                && (billingPrefs.getBoolean("KEY_PURCHASED_ADS", false) == false)) {


        } else {

            //   findViewById(R.id.adFragment).setVisibility(View.GONE);

        }


        //rest of form


        dateButton
                .setText(mCC.dateForDisplay(bundle.getString("tDate")));


        et_amount.setText(mCC.returnAmountEditTextString(bundle.getLong("tAmount")));

        et_payee.setText(bundle.getString("payeeName"));

        dateButton.setOnClickListener(this);
        newCatButton.setOnClickListener(this);
        addPhoto.setOnClickListener(this);
        tvPhotoPath.setOnClickListener(this);

        et_tx_note.setText(bundle.getString("note"));

        tvPhotoPath.setText(bundle.getString("photoPath"));

        // set up to populate the spinner of accounts
        mLoaderManager.initLoader(accountLoaderId, null, this);

        String[] from = new String[]{DbClass.KEY_ACCOUNT_NAME};

        int[] to = new int[]{android.R.id.text1};

        accountAdapter = new SimpleCursorAdapter(getBaseContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);

        accountAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accountSpinner.setAdapter(accountAdapter);

        accountSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                for (int pos = accountAdapter.getCount(); pos >= 0; pos--) {

                    if (accountAdapter.getItemId(pos) == bundle
                            .getLong("tAcId")) {

                        accountSpinner.setSelection(pos);

                        accountId = bundle.getLong("tAcId");

                        DbClass mDbClass = new DbClass(getBaseContext());

                        accountTypeId1 = mDbClass.getAccountTypeId(accountId);

                        if (accountTypeId1 == 1) {

                            mLoaderManager.restartLoader(
                                    transactionTypeLoaderIdCash, null,
                                    EditTransaction.this);
                        }
                        if (accountTypeId1 == 2) {

                            mLoaderManager.restartLoader(
                                    transactionTypeLoaderIdBank, null,
                                    EditTransaction.this);
                        }
                        if (accountTypeId1 == 3 || accountTypeId1 == 4) {

                            mLoaderManager.restartLoader(
                                    transactionTypeLoaderIdAsset, null,
                                    EditTransaction.this);
                        }


                        handleResult(accountTypeId1);
                        break;
                    }

                }

                accountSpinner
                        .setOnItemSelectedListener(new OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0,
                                                       View arg1, int arg2, long arg3) {
                                // TODO Auto-generated method stub
                                accountId = arg3;// this is the id of the
                                // selected account to be
                                // saved in the transaction
                                // table

                                // find out the account type id to determine
                                // what type of transaction fits this account
                                DbClass mDbClass = new DbClass(getBaseContext());

                                accountTypeId2 = mDbClass
                                        .getAccountTypeId(accountId);

                                if (accountTypeId2 == 1) {

                                    mLoaderManager.restartLoader(
                                            transactionTypeLoaderIdCash, null,
                                            EditTransaction.this);
                                }
                                if (accountTypeId2 == 2) {

                                    mLoaderManager.restartLoader(
                                            transactionTypeLoaderIdBank, null,
                                            EditTransaction.this);
                                }
                                if (accountTypeId2 == 3 || accountTypeId2 == 4) {

                                    mLoaderManager.restartLoader(
                                            transactionTypeLoaderIdAsset, null,
                                            EditTransaction.this);
                                }

                                handleResult(accountTypeId2);

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

        // set up to populate the payee auto completion

        String[] from7 = new String[]{DbClass.KEY_PAYEE_NAME};

        int[] to7 = new int[]{android.R.id.text1};

        payeeAdapter = new SimpleCursorAdapter(getBaseContext(),
                android.R.layout.simple_list_item_1, null, from7, to7, 0);

        et_payee.setAdapter(payeeAdapter);

        et_payee.setThreshold(2);

        payeeAdapter.setFilterQueryProvider(new FilterQueryProvider() {

            @Override
            public Cursor runQuery(CharSequence str) { // TODO
                // Auto-generated method stub

                return getCursor(str);
            }

            public Cursor getCursor(CharSequence str) {
                // TODO Auto-generated method stub

                String select = "" + DbClass.KEY_PAYEE_NAME + " LIKE ? ";
                String[] selectArgs = {"%" + str + "%"};
                String[] payeeProjection = new String[]{DbClass.KEY_PAYEE_ID,
                        DbClass.KEY_PAYEE_NAME};

                return getContentResolver().query(
                        Uri.parse("content://" + "SentayzoDbAuthority"
                                + "/payees2"), payeeProjection, select,
                        selectArgs, null);
            }
        });

        payeeAdapter.setCursorToStringConverter(new CursorToStringConverter() {

            @Override
            public CharSequence convertToString(Cursor cur) {
                // TODO
                // Auto-generated method stub
                int index = cur.getColumnIndex(DbClass.KEY_PAYEE_NAME);
                return cur.getString(index);
            }
        });

        // set up to populate the spinner of categories
        mLoaderManager.initLoader(categoryLoaderId, null, this);

        String[] from1 = new String[]{DbClass.KEY_CATEGORY_NAME};

        int[] to1 = new int[]{android.R.id.text1};

        catAdapter = new SimpleCursorAdapter(getBaseContext(),
                android.R.layout.simple_spinner_item, null, from1, to1, 0);

        catAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(catAdapter);

        categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                for (int pos = catAdapter.getCount(); pos >= 0; pos--) {

                    if (catAdapter.getItemId(pos) == bundle.getLong("tCatId")) {

                        categorySpinner.setSelection(pos);

                        categoryId = bundle.getLong("tCatId");
                        break;
                    }
                }

                categorySpinner
                        .setOnItemSelectedListener(new OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0,
                                                       View arg1, int arg2, long arg3) {
                                // TODO Auto-generated method stub
                                categoryId = arg3; // this is the id of the
                                // selected category to be
                                // saved in the transaction
                                // table

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

        // set up to populate the spinner of projects
        mLoaderManager.initLoader(projectLoaderId, null, this);

        String[] fromm = new String[]{DbClass.KEY_PROJECT_NAME};

        int[] tto = new int[]{android.R.id.text1};

        projectAdapter = new SimpleCursorAdapter(getBaseContext(),
                android.R.layout.simple_spinner_item, null, fromm, tto, 0);

        projectAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        projectSpinner.setAdapter(projectAdapter);

        projectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                for (int pos = projectAdapter.getCount(); pos >= 0; pos--) {

                    if (projectAdapter.getItemId(pos) == bundle.getLong("projectId", 1)) {

                        projectSpinner.setSelection(pos);

                        projectId = bundle.getLong("projectId", 1);
                        break;
                    }
                }

                projectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        projectId = arg3;
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
        calcButton.setOnClickListener(this);
        newProjectButton.setOnClickListener(this);

    }

    protected void handleResult(Long accountTypeId) {
        // TODO Auto-generated method stub
        Log.d("txspin", "0");

        String[] from = {DbClass.KEY_TRANSACTION_TYPE_NAME};
        int[] to = {android.R.id.text1};

        if (accountTypeId == 1) {
            // if the account chosen in accountSpinner is of type , Cash

            mLoaderManager.initLoader(transactionTypeLoaderIdCash, null, this);

            txTypeAdapter = new SimpleCursorAdapter(getBaseContext(),
                    android.R.layout.simple_spinner_item, null, from, to, 0);

            txTypeAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            transactionTypeSpinner.setAdapter(txTypeAdapter);

        } else if (accountTypeId == 2) {
            // if the account chosen in accountSpinner is of type , Bank

            mLoaderManager.initLoader(transactionTypeLoaderIdBank, null, this);

            txTypeAdapter = new SimpleCursorAdapter(getBaseContext(),
                    android.R.layout.simple_spinner_item, null, from, to, 0);

            txTypeAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            transactionTypeSpinner.setAdapter(txTypeAdapter);

        } else if (accountTypeId == 3 || accountTypeId == 4) {
            // if the account chosen in accountSpinner is of type , Asset or
            // Liability

            mLoaderManager.initLoader(transactionTypeLoaderIdAsset, null, this);

            txTypeAdapter = new SimpleCursorAdapter(getBaseContext(),
                    android.R.layout.simple_spinner_item, null, from, to, 0);

            txTypeAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            transactionTypeSpinner.setAdapter(txTypeAdapter);

        }

        transactionTypeSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        // TODO Auto-generated method stub

                        for (int pos = txTypeAdapter.getCount(); pos >= 0; pos--) {
                            Log.d("txspin", "1");
                            if (txTypeAdapter.getItemId(pos) == bundle
                                    .getLong("tTxTypeId")) {
                                Log.d("txspin", "2");
                                transactionTypeSpinner.setSelection(pos);
                                Log.d("txspin", "3, pos = " + pos);
                                transactionTypeId = bundle.getLong("tTxTypeId");
                                break;
                            }
                        }

                        transactionTypeSpinner
                                .setOnItemSelectedListener(new OnItemSelectedListener() {

                                    @Override
                                    public void onItemSelected(
                                            AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                                        // TODO Auto-generated method stub
                                        Log.d("txspineer", "id= " + arg3);
                                        transactionTypeId = arg3;

                                    }

                                    @Override
                                    public void onNothingSelected(
                                            AdapterView<?> arg0) {
                                        // TODO Auto-generated method stub

                                    }
                                });

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
        getMenuInflater().inflate(R.menu.new_transaction, menu);
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

        if (id == R.id.ab_saveNewTransaction) {
            try {
                DbClass updateTx;
                String txDate;
                Long accountNameId;
                String payee;
                Long categoryNameId;
                Long txTypeId;

                Log.d("1", "1");

                Long txAmount;
                Log.d("2", "2");

                txDate = mCC.dateForDb(dateButton.getText().toString());
                accountNameId = accountId;

                String txNote = et_tx_note.getText().toString();

                Log.d("3", "3");

                categoryNameId = categoryId;
                txTypeId = transactionTypeId;
                txAmount = mCC.valueConverter(et_amount.getText().toString());
                Log.d("4", "4");
                Log.d("4.5", "txTypeId = " + txTypeId);

                if (transactionTypeId == 1 || transactionTypeId == 3
                        || transactionTypeId == 5) {

                    txAmount = -(txAmount);
                }
                Log.d("5", "5");

                // constraint to make sure payee field is not empty
                if (et_payee.getText().length() <= 0) {
                    et_payee.setError("enter payee");

                    return false;

                }
                //contraint to check for invalid character '
                if (et_payee.getText().toString().contains("'")) {
                    String invalidChar = getResources().getString(R.string.invalidXter) + " " + getResources().getString(R.string.payeeField);
                    Toast.makeText(getApplicationContext(),
                            invalidChar, Toast.LENGTH_LONG)
                            .show();

                    return false;

                }

                Log.d("7", "7");

                payee = et_payee.getText().toString();

                Log.d("8", "8");

                firstInsertOrCheckForPayee(payee); // function to insert payee
                // first
                // before inserting
                // transaction


                try {
                    updateTx = new DbClass(this);

                    updateTx.open();


                    if (tvPhotoPath.getText().toString().isEmpty()) {

                        updateTx.updateTransaction(txDate, accountNameId, payee,
                                categoryNameId, txTypeId, txAmount,
                                bundle.getLong("tId"), projectId, txNote);
                    } else {

                        updateTx.updateTransaction(txDate, accountNameId, payee,
                                categoryNameId, txTypeId, txAmount,
                                bundle.getLong("tId"), projectId, txNote, tvPhotoPath.getText().toString());

                    }


                    updateTx.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),
                        "Transaction updated successfully", Toast.LENGTH_LONG)
                        .show();

                Log.d("save", "Transaction updated successfully");

                Intent intent = new Intent(this,
                        com.sentayzo.app.MainActivity.class);

                startActivity(intent);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return super.onOptionsItemSelected(item);
    }

    private void firstInsertOrCheckForPayee(String payee) {


        DbClass mDbClass = new DbClass(this);

        mDbClass.open();

        mDbClass.insertOrCheckForPayee(payee);

        mDbClass.close();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // TODO Auto-generated method stub

        Log.d("new Transaction loader", "we are in here");

        if (arg0 == accountLoaderId) {
            return new CursorLoader(this, Uri.parse("content://"
                    + "SentayzoDbAuthority" + "/accountspinner"), null, null,
                    null, null);
        }

        if (arg0 == categoryLoaderId) {

            return new CursorLoader(this, Uri.parse("content://"
                    + "SentayzoDbAuthority" + "/categories"), null, null, null,
                    null);
        }

        if (arg0 == transactionTypeLoaderIdCash) {
            return new CursorLoader(this, Uri.parse("content://"
                    + "SentayzoDbAuthority" + "/transactionTypesCash"), null,
                    null, null, null);
        }

        if (arg0 == transactionTypeLoaderIdBank) {
            return new CursorLoader(this, Uri.parse("content://"
                    + "SentayzoDbAuthority" + "/transactionTypesBank"), null,
                    null, null, null);
        }

        if (arg0 == transactionTypeLoaderIdAsset) {
            return new CursorLoader(this, Uri.parse("content://"
                    + "SentayzoDbAuthority" + "/transactionTypesAsset"), null,
                    null, null, null);
        }

        if (arg0 == payeeLoaderId) {
            return new CursorLoader(this, Uri.parse("content://"
                    + "SentayzoDbAuthority" + "/payees"), null, null, null,
                    null);
        }
        if (arg0 == projectLoaderId) {
            return new CursorLoader(this, Uri.parse("content://"
                    + "SentayzoDbAuthority" + "/projectsAll"), null, null, null,
                    null);
        }

        return null;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        // TODO Auto-generated method stub
        if (arg0.getId() == accountLoaderId) {
            accountAdapter.swapCursor(arg1);
        }

        if (arg0.getId() == categoryLoaderId) {
            catAdapter.swapCursor(arg1);
        }

        if (arg0.getId() == transactionTypeLoaderIdCash) {
            txTypeAdapter.swapCursor(arg1);
        }

        if (arg0.getId() == transactionTypeLoaderIdBank) {
            txTypeAdapter.swapCursor(arg1);
        }

        if (arg0.getId() == transactionTypeLoaderIdAsset) {
            txTypeAdapter.swapCursor(arg1);
        }
        if (arg0.getId() == projectLoaderId) {
            projectAdapter.swapCursor(arg1);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {


        if (arg0.getId() == accountLoaderId) {
            accountAdapter.swapCursor(null);
        }

        if (arg0.getId() == categoryLoaderId) {
            catAdapter.swapCursor(null);
        }

        if (arg0.getId() == transactionTypeLoaderIdCash) {
            txTypeAdapter.swapCursor(null);
        }

        if (arg0.getId() == transactionTypeLoaderIdBank) {
            txTypeAdapter.swapCursor(null);
        }

        if (arg0.getId() == transactionTypeLoaderIdAsset) {
            txTypeAdapter.swapCursor(null);
        }
        if (arg0.getId() == projectLoaderId) {
            projectAdapter.swapCursor(null);
        }

    }

    @Override
    public void onClick(View view) {

        if (view == dateButton) {
            // if the dateButton is clicked
            Log.d("datePicker", "1");
            try {
                Log.d("datePicker", "2");

                DatePickerDialog datePick = new DatePickerDialog(
                        EditTransaction.this, new OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view,
                                          int selectedYear, int selectedMonth,
                                          int selectedDay) {
                        // TODO Auto-generated method stub
                        Log.d("datePicker", "3");

                        String dateSetString = selectedYear + "-"
                                + (selectedMonth + 1) + "-"
                                + selectedDay;

                        try {
                            Log.d("datePicker", "4");
                            dateButton.setText(sdf2.format(sdf
                                    .parse(dateSetString)));
                            Log.d("datePicker", "5");
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            Log.d("datePicker", "6");
                            e.printStackTrace();
                            Log.d("datePicker", e.toString());
                        }

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));

                Log.d("datePicker", "7");
                datePick.setTitle("Select Date");
                Log.d("datePicker", "8");
                datePick.setCancelable(false);
                Log.d("datePicker", "9");
                datePick.show();
                Log.d("datePicker", "10");
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

        if (view == newCatButton) {
            // if the new Category Button is clicked

            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.new_category_dialog, null);

            final EditText catEt = (EditText) v.findViewById(R.id.et_cat_Name);

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    EditTransaction.this);
            builder.setTitle("New Category");

            builder.setView(v);

            builder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub

                        }
                    });

            builder.setPositiveButton("Save",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            String catName = catEt.getText().toString();

                            if (catEt.getText().length() <= 0) {

                                Toast.makeText(getBaseContext(),
                                        "Category name can't be empty ",
                                        Toast.LENGTH_LONG).show();

                            } else {

                                DbClass mDbClasss = new DbClass(
                                        EditTransaction.this);
                                mDbClasss.open();

                                mDbClasss.addNewCat(catEt.getText().toString());

                                mDbClasss.close();

                                mLoaderManager.restartLoader(categoryLoaderId,
                                        null, EditTransaction.this);

                                Toast.makeText(getBaseContext(),
                                        "Category: " + catName + " added ",
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                    });

            Dialog newCatDialog = builder.create();
            newCatDialog.show();

        }

        if (view == calcButton) {
            final TextView mCalculatorDisplay;

            final CalculatorBrain mCalculatorBrain;
            final String DIGITS = "0123456789.";

            final DecimalFormat df = new DecimalFormat("@###########");
            String cancel = getResources().getString(R.string.cancel);
            String ok = getResources().getString(R.string.ok);
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.activity_calculator, null);

            mCalculatorBrain = new CalculatorBrain();
            mCalculatorDisplay = (TextView) v.findViewById(R.id.textView1);

            df.setMinimumFractionDigits(0);
            df.setMinimumIntegerDigits(1);
            df.setMaximumIntegerDigits(8);

            Button zero = (Button) v.findViewById(R.id.button0);
            Button one = (Button) v.findViewById(R.id.button1);
            Button two = (Button) v.findViewById(R.id.button2);
            Button three = (Button) v.findViewById(R.id.button3);
            Button four = (Button) v.findViewById(R.id.button4);
            Button five = (Button) v.findViewById(R.id.button5);
            Button six = (Button) v.findViewById(R.id.button6);
            Button seven = (Button) v.findViewById(R.id.button7);
            Button eight = (Button) v.findViewById(R.id.button8);
            Button nine = (Button) v.findViewById(R.id.button9);

            Button add = (Button) v.findViewById(R.id.buttonAdd);
            Button subtract = (Button) v.findViewById(R.id.buttonSubtract);
            Button multiply = (Button) v.findViewById(R.id.buttonMultiply);
            Button divide = (Button) v.findViewById(R.id.buttonDivide);
            Button buttonToggleSign = (Button) v
                    .findViewById(R.id.buttonToggleSign);
            Button decimalPoint = (Button) v
                    .findViewById(R.id.buttonDecimalPoint);
            Button equals = (Button) v.findViewById(R.id.buttonEquals);
            Button clear = (Button) v.findViewById(R.id.buttonClear);
            Button clearMemory = (Button) v
                    .findViewById(R.id.buttonClearMemory);
            Button addToMemory = (Button) v
                    .findViewById(R.id.buttonAddToMemory);
            Button subtractFromMemory = (Button) v
                    .findViewById(R.id.buttonSubtractFromMemory);
            Button recallMemory = (Button) v
                    .findViewById(R.id.buttonRecallMemory);

            OnClickListener thisy = new OnClickListener() {

                @Override
                public void onClick(View view) {
                    // TODO Auto-generated method stub
                    Log.d("1", "1");
                    Button b = (Button) view;
                    Log.d("2", "2");
                    final String buttonPressed = b.getText().toString();
                    Log.d("3", "3");
                    if (DIGITS.contains(buttonPressed)) {

                        // digit was pressed
                        if (userIsInTheMiddleOfTypingANumber) {

                            if (buttonPressed.equals(".")
                                    && mCalculatorDisplay.getText().toString()
                                    .contains(".")) {
                                // ERROR PREVENTION
                                // Eliminate entering multiple decimals
                            } else {
                                mCalculatorDisplay.append(buttonPressed);
                            }

                        } else {

                            if (buttonPressed.equals(".")) {
                                // ERROR PREVENTION
                                // This will avoid error if only the decimal is
                                // hit before an operator, by placing a leading
                                // zero
                                // before the decimal
                                mCalculatorDisplay.setText(0 + buttonPressed);

                            } else {

                                Log.d("4", "4");
                                mCalculatorDisplay.setText(buttonPressed);
                                Log.d("5", "5");
                            }

                            userIsInTheMiddleOfTypingANumber = true;
                        }

                    } else {
                        // operation was pressed
                        if (userIsInTheMiddleOfTypingANumber) {

                            mCalculatorBrain.setOperand(Double
                                    .parseDouble(mCalculatorDisplay.getText()
                                            .toString()));
                            userIsInTheMiddleOfTypingANumber = false;
                        }

                        mCalculatorBrain.performOperation(buttonPressed);
                        mCalculatorDisplay.setText(df.format(mCalculatorBrain
                                .getResult()));

                    }

                }
            };

            zero.setOnClickListener(thisy);
            one.setOnClickListener(thisy);
            two.setOnClickListener(thisy);
            three.setOnClickListener(thisy);
            four.setOnClickListener(thisy);
            five.setOnClickListener(thisy);
            six.setOnClickListener(thisy);
            seven.setOnClickListener(thisy);
            eight.setOnClickListener(thisy);
            nine.setOnClickListener(thisy);

            add.setOnClickListener(thisy);
            subtract.setOnClickListener(thisy);
            multiply.setOnClickListener(thisy);
            divide.setOnClickListener(thisy);
            buttonToggleSign.setOnClickListener(thisy);
            decimalPoint.setOnClickListener(thisy);
            equals.setOnClickListener(thisy);
            clear.setOnClickListener(thisy);
            clearMemory.setOnClickListener(thisy);
            addToMemory.setOnClickListener(thisy);
            subtractFromMemory.setOnClickListener(thisy);
            recallMemory.setOnClickListener(thisy);

            AlertDialog.Builder calcBuilder = new AlertDialog.Builder(
                    EditTransaction.this);

            calcBuilder.setView(v);

            calcBuilder.setNegativeButton(cancel,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });

            calcBuilder.setPositiveButton(ok,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            et_amount.setText(mCalculatorDisplay.getText()
                                    .toString());
                        }
                    });

            Dialog newCatDialog = calcBuilder.create();
            newCatDialog.getWindow().setBackgroundDrawable(null);
            newCatDialog.show();

        }

        if (view == newProjectButton) {
            String newProjectTitle = getResources().getString(
                    R.string.newPrTitle);
            LayoutInflater inflater = getLayoutInflater();
            String cancel = getResources().getString(R.string.cancel);
            String save = getResources().getString(R.string.save);
            View newProjectDialog = inflater.inflate(
                    R.layout.new_project_dialog, null);

            final EditText etPrName;
            final EditText etPrNote;
            etPrName = (EditText) newProjectDialog
                    .findViewById(R.id.et_project_Name);
            etPrNote = (EditText) newProjectDialog
                    .findViewById(R.id.et_project_note);

            AlertDialog.Builder prBuilder = new AlertDialog.Builder(
                    EditTransaction.this);
            prBuilder.setTitle(newProjectTitle);
            prBuilder.setView(newProjectDialog);
            prBuilder.setNegativeButton(cancel,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });

            prBuilder.setPositiveButton(save,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                            if (etPrName.getText().length() <= 0) {

                                String toastText = getResources().getString(
                                        R.string.project_name)
                                        + " "
                                        + getResources().getString(
                                        R.string.cantBeEmpty);

                                Toast.makeText(getBaseContext(), toastText,
                                        Toast.LENGTH_LONG).show();

                            }// end if
                            else {
                                int open = 1;// 1 symbolizes that the project is
                                // running
                                String prName = etPrName.getText().toString();
                                String prNote = etPrNote.getText().toString();

                                DbClass mDb = new DbClass(EditTransaction.this);
                                mDb.open();
                                mDb.insertNewProject(prName, prNote, open);
                                mDb.close();

                                mLoaderManager.restartLoader(projectLoaderId,
                                        null, EditTransaction.this);

                            }// end else

                        }
                    });

            Dialog newPrDialog = prBuilder.create();
            newPrDialog.show();

        }

        if (view == addPhoto) {


            ImagePicker.pickImage(this, "Select your image:");


        }

        if (view == tvPhotoPath) {


            LayoutInflater inflater = LayoutInflater.from(this);
            View imageDialog = inflater.inflate(R.layout.dialog_image, null);
            ImageView iv = (ImageView) imageDialog.findViewById(R.id.iv_tx_image);
            iv.setImageURI(Uri.fromFile(new File(tvPhotoPath.getText().toString())));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(imageDialog);
            builder.setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });


            Dialog dialog = builder.create();
            dialog.show();


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        // TODO do something with the bitmap

        saveBitmapToGallery(bitmap);


    }

    private void saveBitmapToGallery(Bitmap bitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + NewTransaction.imageFolder);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);

        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);

        Log.i("filename", "" + file);

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();


            tvPhotoPath.setText(root + "/" + NewTransaction.imageFolder + "/" + fname);

            //  sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
