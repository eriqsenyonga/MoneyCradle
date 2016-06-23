package com.sentayzo.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class NewAccount extends AppCompatActivity implements
        LoaderCallbacks<Cursor>, OnClickListener {

    Spinner accountTypeSpinner, myCurrenciesSpinner, cardIssuerSpinner;
    EditText nameEditText, openAmountEditText, noteEditText, creditProviderEditText, creditLimitEditText;
    TextInputLayout tilCreditProvider, tilCreditLimit, tilAccountTitle;
    TextView cardIssuerLabel;
    View dividerView;
    String tag = "in on create ";
    Long idOfSelectedSpinnerItem, myCurrencyId, cardIssuerId;
    SimpleCursorAdapter accountTypeAdpater, myCurrencyAdapter;
    Button dateButton, button_addCurrencyToListOfMyCurrencies;
    Calendar c;
    String buttonTextDate;
    LoaderManager mLoaderManager;
    CheckBox includeCheckBox;
    int checkBoxNumber;// either 1 for checked or 0 for not checked
    ConversionClass mCC;
    int accountTypeLoaderId = 0, myCurrenciesLoaderId = 1;
    Tracker t;
    SharedPreferences billingPrefs;
    Toolbar toolBar;
    DbClass mDb;
    MyIssuersAdapter myIssuersAdapter;
    Cursor credIssuersCursor;
    Boolean isNewAccount = false;
    Boolean isEditAccount = false;
    Long accEditId; //id of account we are editing
    Long accEditPrevCreditLimit;
    Long accEditPrevCardIssuer;
    String accEditPrevCreditProvider;
    String accEditPrevName;
    String accEditPrevNote;
    int accEditPrevAccountTypeId;
    Long accEditPrevOpeningBalance;
    int accEditPrevCheckboxNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);

        t = ((ApplicationClass) getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);

        t.setScreenName("NewAccount");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);

        mCC = new ConversionClass(NewAccount.this);

        //   dateTextView = (TextView) findViewById(R.id.tv_newAccountDate);
        dateButton = (Button) findViewById(R.id.spinner_newAccountDates);

        tilAccountTitle = (TextInputLayout) findViewById(R.id.til_accountTitle);
        nameEditText = (EditText) findViewById(R.id.et_name);
        accountTypeSpinner = (Spinner) findViewById(R.id.spinner_accountTypes);
        openAmountEditText = (EditText) findViewById(R.id.openingAmount);
        noteEditText = (EditText) findViewById(R.id.note);

        includeCheckBox = (CheckBox) findViewById(R.id.checkBox_include_in_totals);
        myCurrenciesSpinner = (Spinner) findViewById(R.id.spinner_my_currencies);
        button_addCurrencyToListOfMyCurrencies = (Button) findViewById(R.id.b_new_currency_to_my_currencies);

        cardIssuerLabel = (TextView) findViewById(R.id.tv_cardIssuer);
        cardIssuerSpinner = (Spinner) findViewById(R.id.spinner_cardIssuers);
        tilCreditProvider = (TextInputLayout) findViewById(R.id.til_creditProvider);
        tilCreditLimit = (TextInputLayout) findViewById(R.id.til_creditlLimit);
        creditLimitEditText = (EditText) findViewById(R.id.et_creditLimit);
        creditProviderEditText = (EditText) findViewById(R.id.et_creditProvider);

        //  tilCreditProvider.getEditText().setHint(getString(R.string.credit_provider));
        //  tilCreditLimit.getEditText().setHint(getString(R.string.credit_limit));

        //set up for ads

        billingPrefs = getSharedPreferences("my_billing_prefs", 0);


        if ((billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD", true) == false)
                && (billingPrefs.getBoolean("KEY_PURCHASED_ADS", false) == false)) {


        } else {
//            findViewById(R.id.adFragment).setVisibility(View.GONE);

        }


        /*CHECK WHETHER IT
         IS A NEW ACCOUNT
         OR EDIT ACCOUNT */

        if (getIntent().hasExtra("infoBundle")) {
            //if we want to edit an account
            isEditAccount = true;

            //set Activity Title to Edit Account
            getSupportActionBar().setTitle("Edit Account");

            //get the bundle embedded in the intent for edit account
            Bundle bundle = getIntent().getBundleExtra("infoBundle");

            //extract the different values from the bundle to populate the fields

            accEditPrevName = bundle.getString("acName");
            accEditPrevNote = bundle.getString("acNote");
            accEditPrevAccountTypeId = bundle.getInt("acType");
            accEditPrevOpeningBalance = bundle.getLong("acOpenAmount");
            accEditPrevCheckboxNumber = bundle.getInt("acInclude");
            accEditId = bundle.getLong("acId");

            if (accEditPrevAccountTypeId == 5) {
                //if it is a credit card account to be edited
                accEditPrevCreditLimit = bundle.getLong("acCreditLimit");
                accEditPrevCardIssuer = bundle.getLong("acCardIssuerId");
                accEditPrevCreditProvider = bundle.getString("acCreditProvider");

                creditLimitEditText.setText(mCC.returnAmountEditTextString(accEditPrevCreditLimit));
                creditProviderEditText.setText(accEditPrevCreditProvider);
            }

            //remove date view as it is not needed for EDIT ACCOUNT
            dateButton.setVisibility(View.GONE);


            //populate the EditText fields with extracted data
            populateEditTexts();

        } else {

            isNewAccount = true;

        }

        //the form itself setup


        c = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yyyy",
                Locale.getDefault());

        buttonTextDate = dateFormat.format(c.getTime());

        dateButton.setText(buttonTextDate);

        dateButton.setOnClickListener(this);

        mLoaderManager = getSupportLoaderManager();

        mLoaderManager.initLoader(accountTypeLoaderId, null, this);

        String[] from = new String[]{DbClass.KEY_ACCOUNT_TYPE_NAME};

        int[] to = new int[]{android.R.id.text1};

        accountTypeAdpater = new SimpleCursorAdapter(getBaseContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);

        accountTypeAdpater
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        accountTypeSpinner.setAdapter(accountTypeAdpater);


        accountTypeSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        // TODO Auto-generated method stub


                        if (isNewAccount) {

                            idOfSelectedSpinnerItem = arg3;

                            if (idOfSelectedSpinnerItem == 5) {
                                //If Credit Card is selected

                                showCreditCardFields();

                                mDb = new DbClass(NewAccount.this);
                                credIssuersCursor = mDb.getCardIssuers();

                                myIssuersAdapter = new MyIssuersAdapter(NewAccount.this, credIssuersCursor, 0);

                                cardIssuerSpinner.setAdapter(myIssuersAdapter);

                                cardIssuerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        cardIssuerId = id;

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                            } else {

                                hideCreditCardFields();

                            }

                        } else {

                            //if it is edit account

                            // to set the previous selection of the spinner
                            accountTypeSpinner.setSelection(accEditPrevAccountTypeId - 1);


                            if (accountTypeSpinner.getSelectedItemId() == 5) {

                                //if the account being edited is a credit card account

                                showCreditCardFields();

                                mDb = new DbClass(NewAccount.this);
                                credIssuersCursor = mDb.getCardIssuers();

                                myIssuersAdapter = new MyIssuersAdapter(NewAccount.this, credIssuersCursor, 0);

                                cardIssuerSpinner.setAdapter(myIssuersAdapter);

                                cardIssuerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        //set previous card issuer
                                        cardIssuerSpinner.setSelection((int) (accEditPrevCardIssuer - 1));


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                            } else {
                                hideCreditCardFields();
                            }

                            // to allow setting of different selection
                            accountTypeSpinner
                                    .setOnItemSelectedListener(new OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(
                                                AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {
                                            // TODO Auto-generated method stub
                                            idOfSelectedSpinnerItem = arg3;

                                            if (arg3 == 5) {
                                                showCreditCardFields();

                                            } else {
                                                hideCreditCardFields();
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(
                                                AdapterView<?> arg0) {
                                            // TODO Auto-generated method stub

                                        }
                                    });
                            idOfSelectedSpinnerItem = arg3;


                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });

        button_addCurrencyToListOfMyCurrencies.setOnClickListener(this);

        // set up for the myCUrrency spinner
        mLoaderManager.initLoader(myCurrenciesLoaderId, null, this);

        String[] from2 = new String[]{DbClass.KEY_MY_CURRENCY_CURRENCY_CODE};

        int[] to2 = new int[]{android.R.id.text1};

        myCurrencyAdapter = new SimpleCursorAdapter(getBaseContext(),
                android.R.layout.simple_spinner_item, null, from2, to2, 0);

        myCurrencyAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myCurrenciesSpinner.setAdapter(myCurrencyAdapter);

        myCurrenciesSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        // TODO Auto-generated method stub

                        myCurrencyId = arg3;

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

        mLoaderManager.initLoader(myCurrenciesLoaderId, null, this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.ab_saveNewAccount) {
            // what happens when the save button is clicked

            String acDate = getDateTime();

            String acNote = noteEditText.getText().toString();
            String openAmount = openAmountEditText.getText().toString();
            Long acType = accountTypeSpinner.getSelectedItemId();
            Long currencyId = myCurrencyId;
            String acName = nameEditText.getText().toString();

            if (includeCheckBox.isChecked()) {
                checkBoxNumber = 1;
            } else {
                checkBoxNumber = 0;
            }

            int isOpen = 1;

            if (openAmount.isEmpty()) {

                openAmount = "0";
            }


            Long oA = mCC.valueConverter(openAmount);

            String creditProvider = creditProviderEditText.getText().toString();

            String cardLimit = creditLimitEditText.getText().toString();

            if (cardLimit.isEmpty()) {

                cardLimit = "0";
            }

            Long cardLimitForDb = mCC.valueConverter(cardLimit);


            // constraint to make sure name field is not empty
            if (acName.isEmpty()) {

                nameEditText.setError("enter account title");

                return false;

            }
            // constraint to check for invalid character '
            if (acName.contains("'")) {
                String invalidChar = getResources().getString(
                        R.string.invalidXter)
                        + " " + getResources().getString(R.string.NameField);

                nameEditText.setError(invalidChar);

                return false;

            }


            if (includeCheckBox.isChecked()) {
                checkBoxNumber = 1;
            } else {
                checkBoxNumber = 0;
            }


            //if liability or credit card is chosen then make oA negative
            if (acType == 4 || acType == 5) {

                oA = -(oA);
            }

            //if it is a credit card account
            if (acType == 5) {

                Long cardIssuer = cardIssuerSpinner.getSelectedItemId();

                if (creditProvider.isEmpty()) {

                    creditProviderEditText.setError("enter credit provider");
                    return false;
                }


                if (creditLimitEditText.getText().toString().isEmpty()) {

                    creditLimitEditText.setError("enter credit limit");
                    return false;
                }

                // section to enter data into tables if credit card

                if (isNewAccount) {

                    DbClass creditCardEntry = new DbClass(NewAccount.this);
                    creditCardEntry.open();

                    creditCardEntry.newCreditCardEntry(acDate, acName, acType, oA, acNote,
                            checkBoxNumber, isOpen, currencyId, cardLimitForDb, cardIssuer, creditProvider);

                    creditCardEntry.newAccountEntryInTxTable(acDate, acName, oA, acNote);
                    creditCardEntry.close();

                    Toast.makeText(getApplicationContext(),
                            "Account: " + acName + " created", Toast.LENGTH_SHORT)
                            .show();

                } else if (isEditAccount) {

                    DbClass creditCardUpdate = new DbClass(NewAccount.this);
                    creditCardUpdate.open();

                    creditCardUpdate.updateAccount(acName, acType, oA, acNote, accEditId,
                            checkBoxNumber, cardLimitForDb, cardIssuer, creditProvider);

                    creditCardUpdate.close();

                    Toast.makeText(getApplicationContext(),
                            "Account: " + acName + " updated", Toast.LENGTH_SHORT)
                            .show();

                }

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();

                return true;


            }

            // section to enter data into tables if not credit card

            if (isNewAccount) {

                DbClass entry = new DbClass(NewAccount.this);
                entry.open();

                entry.newAccountEntry(acDate, acName, acType, oA, acNote,
                        checkBoxNumber, isOpen, currencyId);

                entry.newAccountEntryInTxTable(acDate, acName, oA, acNote);
                entry.close();
                Log.d("acNote", acNote);
                Toast.makeText(getApplicationContext(),
                        "Account: " + acName + " created", Toast.LENGTH_SHORT)
                        .show();


                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();

            } else if (isEditAccount) {

                DbClass entry = new DbClass(NewAccount.this);
                entry.open();

                entry.updateAccount(acName, acType, oA, acNote, accEditId,
                        checkBoxNumber);

                entry.close();

                Toast.makeText(getApplicationContext(),
                        "Account: " + acName + " updated", Toast.LENGTH_SHORT)
                        .show();

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();

            }

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd - MMM - yyyy",
                Locale.getDefault());

        try {
            return (sdf.format(sdf2.parse(dateButton.getText().toString())))
                    .toString();

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("datePicker", e.toString());
        }

        return null;
    }

    /**
     * A callback method invoked by the loader when initLoader() is called
     */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // TODO Auto-generated method stub
        if (arg0 == accountTypeLoaderId) {
            return new CursorLoader(this, Uri.parse("content://"
                    + "SentayzoDbAuthority" + "/contents"), null, null, null,
                    null);
        }

        if (arg0 == myCurrenciesLoaderId) {

            return new CursorLoader(this, Uri.parse("content://"
                    + "SentayzoDbAuthority" + "/myCurrencies"), null, null,
                    null, null);

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        // TODO Auto-generated method stub
        if (arg0.getId() == accountTypeLoaderId) {
            accountTypeAdpater.swapCursor(arg1);
        }

        if (arg0.getId() == myCurrenciesLoaderId) {

            myCurrencyAdapter.swapCursor(arg1);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
        if (arg0.getId() == accountTypeLoaderId) {
            accountTypeAdpater.swapCursor(null);
        }

        if (arg0.getId() == myCurrenciesLoaderId) {

            myCurrencyAdapter.swapCursor(null);

        }

    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub

        if (view == dateButton) {
            // if the dateButton is clicked

            try {
                DatePickerDialog datePicker = new DatePickerDialog(
                        NewAccount.this, new OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view,
                                          int selectedYear, int selectedMonth,
                                          int selectedDay) {
                        // TODO Auto-generated method stub

                        String dateSetString = selectedYear + "-"
                                + (selectedMonth + 1) + "-"
                                + selectedDay;

                        SimpleDateFormat sdf = new SimpleDateFormat(
                                "yyyy-MM-dd", Locale.getDefault());

                        SimpleDateFormat sdf2 = new SimpleDateFormat(
                                "dd - MMM - yyyy", Locale.getDefault());

                        try {
                            dateButton.setText(sdf2.format(sdf
                                    .parse(dateSetString)));

                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.d("datePicker", e.toString());
                        }

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH));

                datePicker.setTitle("Select Date");
                datePicker.setCancelable(false);
                datePicker.show();
            } catch (Exception e) {
                e.getStackTrace();
                Log.d("Exception", e.toString());
            }

        }

        if (view == button_addCurrencyToListOfMyCurrencies) {

            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    String accountCurrencyTitle = getResources().getString(R.string.currencyAdd);
                    String cancel = getResources().getString(R.string.cancel);
                    final DbClass mDb = new DbClass(NewAccount.this);
                    String[] currencies = mDb.getDistinctCurrencyNames();
                    final String[] currencyValues = mDb.getDistinctCurrencyCodes();

                    AlertDialog.Builder builder = new AlertDialog.Builder(NewAccount.this);
                    builder.setTitle(accountCurrencyTitle);
                    builder.setItems(currencies, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            // TODO Auto-generated method stub

                            String toAdd = currencyValues[position];
                            mDb.addNewMyCurrency(toAdd);

                            mLoaderManager.restartLoader(myCurrenciesLoaderId, null, NewAccount.this);

                        }
                    });

                    builder.setNeutralButton(cancel, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });


                    Dialog allCurrenciesDialog = builder.create();

                    allCurrenciesDialog.show();

                }
            });


        }

    }

    private void showCreditCardFields() {
        cardIssuerLabel.setVisibility(View.VISIBLE);
        cardIssuerSpinner.setVisibility(View.VISIBLE);
        tilCreditProvider.setVisibility(View.VISIBLE);
        tilCreditLimit.setVisibility(View.VISIBLE);
        creditLimitEditText.setVisibility(View.VISIBLE);
        creditProviderEditText.setVisibility(View.VISIBLE);
    }

    private void hideCreditCardFields() {
        cardIssuerLabel.setVisibility(View.GONE);
        cardIssuerSpinner.setVisibility(View.GONE);
        tilCreditProvider.setVisibility(View.GONE);
        tilCreditLimit.setVisibility(View.GONE);
        creditLimitEditText.setVisibility(View.GONE);
        creditProviderEditText.setVisibility(View.GONE);

    }

    private void populateEditTexts() {

        nameEditText.setText(accEditPrevName);
        openAmountEditText.setText(mCC.returnAmountEditTextString(accEditPrevOpeningBalance));
        noteEditText.setText(accEditPrevNote);


        if (accEditPrevCheckboxNumber == 1) {
            includeCheckBox.setChecked(true);
        } else {

            includeCheckBox.setChecked(false);
        }

    }


    private class MyIssuersAdapter extends CursorAdapter {
        //Adapter for the credit card issuers spinner

        private LayoutInflater inflater;


        public MyIssuersAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            View view = inflater.inflate(R.layout.spinner_row_credit_issuers, parent, false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            ImageView ivIssuerIcon = (ImageView) view.findViewById(R.id.iv_spinner_issuerImage);
            TextView tvIssuerName = (TextView) view.findViewById(R.id.tv_spinner_issuerName);

            String cardIssuer = cursor.getString(cursor.getColumnIndex(DbClass.KEY_CARD_ISSUER_NAME));


            tvIssuerName.setText(cardIssuer);

            if (cardIssuer.equals("Visa")) {

                ivIssuerIcon.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_card_visa));
            }

            if (cardIssuer.equals("MasterCard")) {

                ivIssuerIcon.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_mastercard));
            }

            if (cardIssuer.equals("American Express")) {

                ivIssuerIcon.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_card_amex));
            }

            if (cardIssuer.equals("Discover")) {

                ivIssuerIcon.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_card_discover));
            }

            if (cardIssuer.equals("Diners Club")) {

                ivIssuerIcon.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_card_diners_club));
            }

            if (cardIssuer.equals("Union Pay")) {

                ivIssuerIcon.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_card_union_pay));
            }

            if (cardIssuer.equals("JCB")) {

                ivIssuerIcon.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_card_jcb));
            }

            if (cardIssuer.equals("Maestro")) {

                ivIssuerIcon.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_card_maestro));
            }

            if (cardIssuer.equals("Other")) {

                ivIssuerIcon.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_card_visa));
            }

        }
    }


}


