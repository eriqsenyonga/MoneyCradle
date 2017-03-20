package com.sentayzo.app;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
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


public class NewTransaction extends AppCompatActivity implements
        LoaderCallbacks<Cursor>, OnClickListener {

    String imageFolder = "money_cradle";

    int REQUEST_CODE_PICKER = 1;
    Button dateButton;
    ImageButton calcButton, newCatButton, newProjectButton, addPhoto;
    EditText et_amount, et_tx_note;
    AutoCompleteTextView et_payee;
    Spinner accountSpinner, categorySpinner, transactionTypeSpinner,
            projectSpinner;
    LoaderManager mLoaderManager = getSupportLoaderManager();
    SimpleCursorAdapter accountAdapter, catAdapter, txTypeAdapter,
            payeeAdapter, projectAdapter;
    Long accountId, categoryId, transactionTypeId, projectId, accTypeId;
    Calendar c;
    int accountLoaderId = 2, categoryLoaderId = 3,
            transactionTypeLoaderIdCash = 4, transactionTypeLoaderIdBank = 5,
            transactionTypeLoaderIdAsset = 6, payeeLoaderId = 7,
            projectLoaderId = 8;
    String buttonTextDate;
    Intent i;
    CharSequence stringTyped;
    Boolean userIsInTheMiddleOfTypingANumber = false;
    ConversionClass mCC;
    SharedPreferences billingPrefs;
    Tracker t;
    Toolbar toolBar;
    Long creditCardTrnAmtAllowed;
    TextInputLayout tilPayee;
    TextView tvPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        t = ((ApplicationClass) getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);

        t.setScreenName("CategoryList");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);

        i = getIntent();
        tvPhotoPath = (TextView) findViewById(R.id.tv_photo_uri);
        addPhoto = (ImageButton) findViewById(R.id.ib_add_photo);
        newProjectButton = (ImageButton) findViewById(R.id.b_new_project);
        newCatButton = (ImageButton) findViewById(R.id.b_new_cat);
        dateButton = (Button) findViewById(R.id.spinner_newTransactionDates);
        accountSpinner = (Spinner) findViewById(R.id.spinner_accounts);
        et_payee = (AutoCompleteTextView) findViewById(R.id.et_payee);
        categorySpinner = (Spinner) findViewById(R.id.spinner_categories);
        et_amount = (EditText) findViewById(R.id.et_amount);
        transactionTypeSpinner = (Spinner) findViewById(R.id.spinner_transactionType);
        et_tx_note = (EditText) findViewById(R.id.tx_note);
        projectSpinner = (Spinner) findViewById(R.id.spinner_projects);
        calcButton = (ImageButton) findViewById(R.id.b_calculator);
        tilPayee = (TextInputLayout) findViewById(R.id.til_payee);

        // rest of form
        mCC = new ConversionClass(NewTransaction.this);

        c = Calendar.getInstance();

        buttonTextDate = mCC.dateForDisplayFromCalendarInstance(c.getTime());

        dateButton.setText(buttonTextDate);

        dateButton.setOnClickListener(this);

        addPhoto.setOnClickListener(this);

        tvPhotoPath.setOnClickListener(this);

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

                if (i.hasExtra("accountId")) {

                    Long defaultAccountId = i.getLongExtra("accountId", arg3);

                    for (int pos = accountAdapter.getCount(); pos >= 0; pos--) {

                        if (accountAdapter.getItemId(pos) == defaultAccountId) {

                            accountSpinner.setSelection(pos);

                            accountId = defaultAccountId;

                            DbClass mDbClass = new DbClass(getBaseContext());

                            Long accountTypeId = mDbClass
                                    .getAccountTypeId(accountId);

                            if (accountTypeId == 1 || accountTypeId == 5) {

                                mLoaderManager.restartLoader(
                                        transactionTypeLoaderIdCash, null,
                                        NewTransaction.this);
                            }
                            if (accountTypeId == 2) {

                                mLoaderManager.restartLoader(
                                        transactionTypeLoaderIdBank, null,
                                        NewTransaction.this);
                            }
                            if (accountTypeId == 3 || accountTypeId == 4) {

                                mLoaderManager.restartLoader(
                                        transactionTypeLoaderIdAsset, null,
                                        NewTransaction.this);
                            }

                            handleResult(accountTypeId);

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
                                    // what type of transaction fits this
                                    // account
                                    DbClass mDbClass = new DbClass(
                                            getBaseContext());

                                    Long accountTypeId = mDbClass
                                            .getAccountTypeId(accountId);

                                    if (accountTypeId == 1 || accountTypeId == 5) {

                                        mLoaderManager.restartLoader(
                                                transactionTypeLoaderIdCash,
                                                null, NewTransaction.this);
                                    }
                                    if (accountTypeId == 2) {

                                        mLoaderManager.restartLoader(
                                                transactionTypeLoaderIdBank,
                                                null, NewTransaction.this);
                                    }
                                    if (accountTypeId == 3
                                            || accountTypeId == 4) {

                                        mLoaderManager.restartLoader(
                                                transactionTypeLoaderIdAsset,
                                                null, NewTransaction.this);
                                    }

                                    handleResult(accountTypeId);

                                }

                                @Override
                                public void onNothingSelected(
                                        AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub

                                }
                            });

                } else {

                    accountId = arg3;// this is the id of the selected account
                    // to be
                    // saved in the transaction table

                    // find out the account type id to determine what type of
                    // transaction fits this account
                    DbClass mDbClass = new DbClass(getBaseContext());

                    Long accountTypeId = mDbClass.getAccountTypeId(accountId);

                    if (accountTypeId == 1 || accountTypeId == 5) {

                        mLoaderManager.restartLoader(
                                transactionTypeLoaderIdCash, null,
                                NewTransaction.this);
                    }
                    if (accountTypeId == 2) {

                        mLoaderManager.restartLoader(
                                transactionTypeLoaderIdBank, null,
                                NewTransaction.this);
                    }
                    if (accountTypeId == 3 || accountTypeId == 4) {

                        mLoaderManager.restartLoader(
                                transactionTypeLoaderIdAsset, null,
                                NewTransaction.this);
                    }

                    handleResult(accountTypeId);

                }

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
                stringTyped = str;
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

                categoryId = arg3; // this is the id of the selected category to
                // be saved in the transaction table

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        newCatButton.setOnClickListener(this);

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

                if (i.hasExtra("projectId")) {

                    Long defaultProjectId = i.getLongExtra("projectId", arg3);

                    for (int pos = projectAdapter.getCount(); pos >= 0; pos--) {

                        if (projectAdapter.getItemId(pos) == defaultProjectId) {

                            projectSpinner.setSelection(pos);

                            projectId = defaultProjectId;


                            break;
                        }

                    }

                } else {

                    projectId = arg3;
                }
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

        accTypeId = accountTypeId;

        String[] from = {DbClass.KEY_TRANSACTION_TYPE_NAME};
        int[] to = {android.R.id.text1};

        if (accountTypeId == 1 || accountTypeId == 5) {
            // if the account chosen in accountSpinner is of type , Cash or Credit card

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

                        transactionTypeId = arg3;

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });


        if (accountTypeId == 5) {
            //special scenario if account is a credit card
            /*
            *       Logic here;
            *       1. Get the credit limit and current total amount from db
            *       2. Calculate maximum that can be used in this transaction
            *       3. if transaction amount > maximum for this transaction, error --> dont save else go ahead
            * */


            new MyAsyncGetAllowedAmt().execute(accountId);


        }

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


        if (id == R.id.ab_saveNewTransaction) {

            DbClass newTx;
            String txDate;
            Long accountNameId;
            String payee;
            Long categoryNameId;
            Long txTypeId;
            Long prId;
            String txNote;
            String amountString;

            Long txAmount;

            txDate = mCC.dateForDb(dateButton.getText().toString());

            accountNameId = accountId;

            categoryNameId = categoryId;
            txTypeId = transactionTypeId;

            prId = projectId;

            txNote = et_tx_note.getText().toString();

            amountString = et_amount.getText().toString();

            txAmount = mCC.valueConverter(amountString);

            //if it is a spending and it is also a credit card
            if ((txTypeId == 5 || txTypeId == 3 || txTypeId == 1) && accTypeId == 5) {

                //check whether the spending to be spent is more than the credit trn amount allowed
                if (txAmount > creditCardTrnAmtAllowed) {

                    Toast.makeText(NewTransaction.this, "Cant spend more than the limit", Toast.LENGTH_LONG).show();

                    return false;
                }


            }


            if (txTypeId == 1 || txTypeId == 3 || txTypeId == 5) {

                txAmount = -(txAmount);
            }


            // constraint to make sure payee field is not empty
            if (et_payee.getText().length() <= 0) {

                et_payee.setError("enter payee");


                return false;

            }
            // constraint to check for invalid character '
            if (et_payee.getText().toString().contains("'")) {
                String invalidChar = getResources().getString(
                        R.string.invalidXter)
                        + " " + getResources().getString(R.string.payeeField);
                Toast.makeText(getApplicationContext(), invalidChar,
                        Toast.LENGTH_LONG).show();

                return false;

            }

            payee = et_payee.getText().toString();

            firstInsertPayee(payee); // function to insert payee first before
            // inserting transaction

            newTx = new DbClass(this);

            newTx.open();

            if (tvPhotoPath.getText().toString().isEmpty()) {

                newTx.insertNewTransaction(txDate, accountNameId, payee,
                        categoryNameId, txTypeId, txAmount, prId, txNote);
            } else {

                newTx.insertNewTransaction(txDate, accountNameId, payee,
                        categoryNameId, txTypeId, txAmount, prId, txNote, tvPhotoPath.getText().toString());

            }

            newTx.close();

            Toast.makeText(getApplicationContext(),
                    "Transaction added successfully", Toast.LENGTH_LONG).show();

            Log.d("save", "Transaction added successfully");

            Intent intent = new Intent(this,
                    com.sentayzo.app.MainActivity.class);

            startActivity(intent);
            finish();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    // method to insert Payee into payee table in the database
    private void firstInsertPayee(String payee) {
        // TODO Auto-generated method stub

        DbClass mDbClass = new DbClass(this);

        mDbClass.open();

        mDbClass.insertPayee(payee);

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

            String select = "(" + DbClass.KEY_PAYEE_NAME + " LIKE ? ";
            String[] selectArgs = {"%" + stringTyped + "%"};
            String[] payeeProjection = new String[]{DbClass.KEY_PAYEE_ID,
                    DbClass.KEY_PAYEE_NAME};

            CursorLoader payeeCursorLoader = new CursorLoader(
                    this,
                    Uri.parse("content://" + "SentayzoDbAuthority" + "/payees2"),
                    payeeProjection, select, selectArgs, null);

            return payeeCursorLoader;
        }

        if (arg0 == projectLoaderId) {
            return new CursorLoader(this, Uri.parse("content://"
                    + "SentayzoDbAuthority" + "/projects"), null, null, null,
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

        if (arg0.getId() == payeeLoaderId) {
            payeeAdapter.swapCursor(arg1);
        }
        if (arg0.getId() == projectLoaderId) {
            projectAdapter.swapCursor(arg1);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub

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

        if (arg0.getId() == payeeLoaderId) {
            payeeAdapter.swapCursor(null);
        }

        if (arg0.getId() == projectLoaderId) {
            projectAdapter.swapCursor(null);
        }

    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub

        if (view == dateButton) {
            // if the dateButton is clicked

            String dateTitle = getResources().getString(R.string.select_date);
            DatePickerDialog datePicker = new DatePickerDialog(
                    NewTransaction.this,
                    new OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view,
                                              int selectedYear, int selectedMonth,
                                              int selectedDay) {
                            // TODO Auto-generated method stub

                            String dateSetString = selectedYear + "-"
                                    + (selectedMonth + 1) + "-" + selectedDay;

                            String displayDate = mCC
                                    .dateForDisplay(dateSetString);

                            dateButton.setText(displayDate);

                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH));

            datePicker.setTitle(dateTitle);
            datePicker.setCancelable(false);
            datePicker.show();

        }

        if (view == newCatButton) {
            // if the new Category Button is clicked
            String newCategoryTitle = getResources().getString(
                    R.string.newCatTitle);
            LayoutInflater inflater = getLayoutInflater();
            String cancel = getResources().getString(R.string.cancel);
            String save = getResources().getString(R.string.save);
            View v = inflater.inflate(R.layout.new_category_dialog, null);

            final EditText catEt = (EditText) v.findViewById(R.id.et_cat_Name);

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    NewTransaction.this);
            builder.setTitle(newCategoryTitle);

            builder.setView(v);

            builder.setNegativeButton(cancel,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub

                        }
                    });

            builder.setPositiveButton(save,
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

                                String newCatString = catEt.getText()
                                        .toString();
                                DbClass mDbClasss = new DbClass(
                                        NewTransaction.this);
                                mDbClasss.open();

                                mDbClasss.addNewCat(newCatString);

                                // Long newCatId =
                                // mDbClasss.getNewCatId(newCatString);

                                mDbClasss.close();

                                mLoaderManager.restartLoader(categoryLoaderId,
                                        null, NewTransaction.this);

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
                    NewTransaction.this);

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
                    NewTransaction.this);
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

                                DbClass mDb = new DbClass(NewTransaction.this);
                                mDb.open();
                                mDb.insertNewProject(prName, prNote, open);
                                mDb.close();

                                mLoaderManager.restartLoader(projectLoaderId,
                                        null, NewTransaction.this);

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
        File myDir = new File(root + "/" + imageFolder);
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


            tvPhotoPath.setText(root + "/" + imageFolder + "/" + fname);

            //  sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    class MyAsyncGetAllowedAmt extends AsyncTask<Long, Void, Long> {
        //get the allowed amount

        ConversionClass myCC = new ConversionClass(NewTransaction.this);

        @Override
        protected Long doInBackground(Long... params) {

            Long accID = params[0];

            DbClass ccDb = new DbClass(NewTransaction.this);
            ccDb.open();
            Long allowedAmt = ccDb.getCreditLimitAndCurrentTotal(accID);
            ccDb.close();


            return allowedAmt;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);

            Log.d("ALLOWEDAMT", myCC.valueConverter(aLong));
            creditCardTrnAmtAllowed = aLong;

            Toast.makeText(NewTransaction.this, myCC.valueConverter(aLong), Toast.LENGTH_LONG).show();


        }
    }
}
