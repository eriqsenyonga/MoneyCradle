package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AccountsList extends ListFragment {

    FloatingActionsMenu fab;
    FloatingActionButton fabNewAccount;
    FloatingActionButton fabNewTrn;
    FloatingActionButton fabNewTransfer;
    ListView accountsListView;
    LoaderManager mLoaderManager;
    SimpleCursorAdapter mAdapter;
    String tag = "AccountsList Fragment";
    TextView tv_totalAmount;
    DbClass mDbClass;
    View rootView;
    int accountsLoaderId = 1;
    String proceed;
    ConversionClass mCC;
    MyAdapter myAdapter;
    Cursor mCursor;
    Typeface robotoMedium;
    Typeface robotoThin;
    SharedPreferences billingPrefs;

    public AccountsList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_accounts_list, container,
                false);

        fab = (FloatingActionsMenu) rootView.findViewById(R.id.fam_fab);
        fabNewAccount = (FloatingActionButton) rootView.findViewById(R.id.fab_newAccount);
        fabNewTrn = (FloatingActionButton) rootView.findViewById(R.id.fab_newTransaction);
        fabNewTransfer = (FloatingActionButton) rootView.findViewById(R.id.fab_newTransfer);
        tv_totalAmount = (TextView) rootView.findViewById(R.id.tv_totalView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        //  Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.app_bar);
        //  ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        accountsListView = getListView();
        mCC = new ConversionClass(getActivity());
        mDbClass = new DbClass(getActivity());
        proceed = getResources().getString(R.string.proceed);

        billingPrefs = getActivity().getSharedPreferences("my_billing_prefs", 0);


        robotoThin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
        robotoMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");

        mCursor = mDbClass.getAllAccounts();


        myAdapter = new MyAdapter(getActivity(), mCursor, 0);

        accountsListView.setAdapter(myAdapter);


        // accountsListView.setAdapter(mAdapter);
        Log.d(tag, "16");
        // procedure for the total
        getTotal();
        Log.d(tag, "17");


        accountsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (mLastFirstVisibleItem < firstVisibleItem) {

                    //   fab.animate().translationY(fab.getHeight() + 16).setInterpolator(new AccelerateInterpolator(2)).start();
                    if (fab.getVisibility() == View.GONE) {
                    } else {
                        if (fab.isExpanded()) fab.collapse();

                        AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0F);
                        alpha.setDuration(500); // Make animation instant
                        alpha.setFillAfter(false); // Tell it to persist after the animation ends
                        fab.startAnimation(alpha);

                        fab.setVisibility(View.GONE);
                        //  fab.clearAnimation();
                    }

                }
                if (mLastFirstVisibleItem > firstVisibleItem) {
                    // fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

                    if (fab.getVisibility() == View.VISIBLE) {
                    } else {
                        fab.setVisibility(View.VISIBLE);
                        AlphaAnimation alpha = new AlphaAnimation(0.0F, 1.0F);
                        alpha.setDuration(500); // Make animation instant
                        alpha.setFillAfter(true); // Tell it to persist after the animation ends
                        fab.startAnimation(alpha);


                    }


                }
                mLastFirstVisibleItem = firstVisibleItem;

            }
        });


        fabNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean freePeriod = billingPrefs.getBoolean(
                        "KEY_FREE_TRIAL_PERIOD", true);
                Boolean unlocked = billingPrefs.getBoolean("KEY_PURCHASED_UNLOCK",
                        false);

                if (freePeriod == true || unlocked == true) {

                    Intent i = new Intent(getActivity(), NewAccount.class);
                    startActivity(i);
                } else if (freePeriod == false && unlocked == false) {
                    // if free trial has expired and nigga hasnt paid for shit
                    int numberOfAccounts = new DbClass(getActivity())
                            .getNumberOfAccounts();

                    if (numberOfAccounts < 2) {
                        Intent i = new Intent(getActivity(), NewAccount.class);
                        startActivity(i);

                    } else {

                        showPaymentDialog(getActivity());

                    }

                }

            }
        });

        fabNewTrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbClass mDbClass = new DbClass(getActivity());
                mDbClass.open();
                Boolean accountsAvailable = mDbClass.checkForAccounts();
                mDbClass.close();

                if (accountsAvailable == false) {

                    String createAccountFirst = getResources().getString(
                            R.string.createAccountFirst);
                    // TODO Auto-generated method stub

                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            getActivity());
                    builder.setMessage(createAccountFirst);
                    builder.setNeutralButton(getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                }
                            });

                    Dialog d = builder.create();
                    d.show();

                } else {
                    Intent i = new Intent(getActivity(), NewTransaction.class);
                    startActivity(i);
                }
            }
        });

        fabNewTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfAccounts = new DbClass(getActivity())
                        .getNumberOfAccounts();


                fab.collapse();

                if (numberOfAccounts < 2) {

                    // String msg = getResources().getString(R.string.accounts_two);

                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            getActivity());
                    builder.setMessage("You need to have atleast 2 accounts open to use this feature.");

                    builder.setNeutralButton(getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                }
                            });

                    Dialog d = builder.create();
                    d.show();
                } else {

                    Intent i = new Intent(getActivity(), NewTransfer.class);
                    startActivity(i);
                }
            }
        });


        accountsListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    final long accountId) {


                if (fab.isExpanded()) fab.collapse();
                final String[] dialogList = getResources().getStringArray(
                        R.array.accountsListDialog);

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity());

                builder.setItems(dialogList,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int position) {


                                Log.d("in alertdialog", "here");

                                if (position == 0) {
                                    // if "View Info" is clicked --show dialog
                                    // with the account details

                                    AlertDialog.Builder builder3 = new AlertDialog.Builder(
                                            getActivity());
                                    builder3.setTitle("Account Details");

                                    LayoutInflater inflater = getActivity()
                                            .getLayoutInflater();

                                    View view = inflater.inflate(
                                            R.layout.account_view, null);

                                    TextView tvDate = (TextView) view
                                            .findViewById(R.id.tv_date);
                                    TextView tvAcc = (TextView) view
                                            .findViewById(R.id.tv_accName);
                                    TextView tvType = (TextView) view
                                            .findViewById(R.id.tv_accType);
                                    TextView tvOpBal = (TextView) view
                                            .findViewById(R.id.tv_accOA);
                                    TextView tvNote = (TextView) view
                                            .findViewById(R.id.tv_accNote);
                                    TextView tvBal = (TextView) view
                                            .findViewById(R.id.tv_accBal);

                                    DbClass nDbClass = new DbClass(
                                            getActivity());

                                    nDbClass.open();

                                    Bundle infoBundle = nDbClass
                                            .getTheInfoOfAccountWithId(accountId);

                                    Long curBal = nDbClass
                                            .getCurrentBalance(accountId);

                                    nDbClass.close();

                                    tvDate.setText(mCC
                                            .dateForDisplay(infoBundle
                                                    .getString("acDate")));
                                    tvAcc.setText(infoBundle
                                            .getString("acName"));
                                    tvNote.setText(infoBundle
                                            .getString("acNote"));

                                    String opBal = mCC
                                            .valueConverter(infoBundle
                                                    .getLong("acOpenAmount"));
                                    String currBal = mCC.valueConverter(curBal);

                                    tvOpBal.setText(opBal);
                                    tvBal.setText(currBal);

                                    // values 1,2,3,4 below represent the _id of
                                    // the account type table in the Database
                                    if (infoBundle.getInt("acType") == 1) {
                                        tvType.setText("Cash");
                                    }
                                    if (infoBundle.getInt("acType") == 2) {
                                        tvType.setText("Bank");
                                    }
                                    if (infoBundle.getInt("acType") == 3) {
                                        tvType.setText("Asset");
                                    }
                                    if (infoBundle.getInt("acType") == 4) {
                                        tvType.setText("Liability");
                                    }

                                    builder3.setView(view);
                                    builder3.setPositiveButton(
                                            getResources().getString(
                                                    R.string.edit),
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    // TODO Auto-generated
                                                    // method stub
                                                    if (getAccountType(accountId) == 5) {
                                                        //if the accountType is Credit Card
                                                        DbClass myDbClass = new DbClass(
                                                                getActivity());
                                                        myDbClass.open();
                                                        Bundle bundle = myDbClass
                                                                .getCreditAccountInfoWithId(accountId);
                                                        myDbClass.close();
                                                        bundle.putLong("acId", accountId);

                                                        Intent i = new Intent(getActivity(),
                                                                NewAccount.class);
                                                        i.putExtra("infoBundle", bundle);
                                                        startActivity(i);


                                                    } else {
                                                        //if the accountType is NOT CREDIT CARD

                                                        DbClass myDbClass = new DbClass(
                                                                getActivity());
                                                        myDbClass.open();
                                                        Bundle bundle = myDbClass
                                                                .getTheInfoOfAccountWithId(accountId);
                                                        myDbClass.close();
                                                        bundle.putLong("acId",
                                                                accountId);

                                                        Intent i = new Intent(
                                                                getActivity(),
                                                                NewAccount.class);
                                                        i.putExtra("infoBundle",
                                                                bundle);
                                                        startActivity(i);
                                                    }

                                                }
                                            });
                                    builder3.setNeutralButton(
                                            getResources().getString(
                                                    R.string.ok),
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface arg0,
                                                        int arg1) {
                                                    // TODO Auto-generated
                                                    // method stub

                                                }
                                            });

                                    Dialog acViewDialog = builder3.create();

                                    acViewDialog.show();

                                }

                                if (position == 1) {
                                    // if "New Transaction" is clicked
                                    Intent i = new Intent(getActivity(),
                                            NewTransaction.class);
                                    i.putExtra("accountId", accountId);
                                    startActivity(i);

                                }

                                if (position == 3) {
                                    // if "Account History" is clicked
                                    Intent i = new Intent(getActivity(),
                                            OverviewActivity.class);
                                    i.putExtra("Id", accountId);
                                    i.putExtra("whichOverview", OverviewActivity.KEY_ACCOUNT_OVERVIEW);
                                    startActivity(i);

                                }

                                if (position == 4) {
                                    // if "Edit" is clicked

                                    if (getAccountType(accountId) == 5) {
                                        //if the accountType is Credit Card
                                        DbClass myDbClass = new DbClass(
                                                getActivity());
                                        myDbClass.open();
                                        Bundle bundle = myDbClass
                                                .getCreditAccountInfoWithId(accountId);
                                        myDbClass.close();
                                        bundle.putLong("acId", accountId);

                                        Intent i = new Intent(getActivity(),
                                                NewAccount.class);
                                        i.putExtra("infoBundle", bundle);
                                        startActivity(i);


                                    } else {
                                        //if the accountType is NOT CREDIT CARD
                                        DbClass myDbClass = new DbClass(
                                                getActivity());
                                        myDbClass.open();
                                        Bundle bundle = myDbClass
                                                .getTheInfoOfAccountWithId(accountId);
                                        myDbClass.close();
                                        bundle.putLong("acId", accountId);

                                        Intent i = new Intent(getActivity(),
                                                NewAccount.class);
                                        i.putExtra("infoBundle", bundle);
                                        startActivity(i);
                                    }

                                }

                                if (position == 5) {
                                    // if close account is clicked
                                    String closeDialogTitle = getResources()
                                            .getString(
                                                    R.string.closeDialogTitle);
                                    String closeDialogMessage = getResources()
                                            .getString(
                                                    R.string.closeDialogMessage);
                                    AlertDialog.Builder closeBuilder = new AlertDialog.Builder(
                                            getActivity());
                                    closeBuilder.setTitle(closeDialogTitle);
                                    closeBuilder.setMessage(closeDialogMessage
                                            + " \n\n" + proceed);

                                    closeBuilder
                                            .setNegativeButton(
                                                    getResources().getString(
                                                            R.string.no),
                                                    new DialogInterface.OnClickListener() {

                                                        @Override
                                                        public void onClick(
                                                                DialogInterface arg0,
                                                                int arg1) {
                                                            // TODO
                                                            // Auto-generated
                                                            // method stub

                                                        }
                                                    });

                                    closeBuilder
                                            .setPositiveButton(
                                                    getResources().getString(
                                                            R.string.yes),
                                                    new DialogInterface.OnClickListener() {

                                                        @Override
                                                        public void onClick(
                                                                DialogInterface arg0,
                                                                int arg1) {
                                                            // TODO
                                                            // Auto-generated
                                                            // method stub

                                                            DbClass mDbClass = new DbClass(
                                                                    getActivity());

                                                            mDbClass.open();

                                                            mDbClass.closeAccountWithId(accountId);

                                                            mDbClass.close();

                                                            myAdapter
                                                                    .swapCursor(mDbClass
                                                                            .getAllAccounts());
                                                        }
                                                    });

                                    Dialog closeDialog = closeBuilder.create();
                                    closeDialog.show();

                                }

                                if (position == 6) {
                                    // if "Delete" is clicked
                                    String deleteDialogTitle = getResources()
                                            .getString(
                                                    R.string.deleteDialogTitle);
                                    String deleteDialogMessage = getResources()
                                            .getString(
                                                    R.string.deleteDialogMessage);

                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(
                                            getActivity());
                                    builder2.setTitle(deleteDialogTitle);
                                    builder2.setMessage(deleteDialogMessage
                                            + " \n\n" + proceed);
                                    builder2.setNegativeButton(
                                            getResources().getString(
                                                    R.string.no),
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface arg0,
                                                        int arg1) {
                                                    // TODO Auto-generated
                                                    // method stub

                                                }
                                            });

                                    builder2.setPositiveButton(
                                            getResources().getString(
                                                    R.string.yes),
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface arg0,
                                                        int arg1) {
                                                    // TODO Auto-generated
                                                    // method stub

                                                    DbClass aDbClass = new DbClass(
                                                            getActivity());
                                                    aDbClass.open();
                                                    aDbClass.deleteAccountWithId(accountId);
                                                    aDbClass.close();

                                                    getTotal();
                                                    myAdapter
                                                            .swapCursor(mDbClass
                                                                    .getAllAccounts());

//                                                    Snackbar.make(rootView.findViewById(R.id.coordinator), "Account deleted", Snackbar.LENGTH_SHORT).show();
                                                }
                                            });

                                    Dialog delDialog = builder2.create();
                                    delDialog.show();

                                }

                                if (position == 2) {
                                    // if "New Transfer" is clicked

                                    Intent i = new Intent(getActivity(),
                                            NewTransfer.class);
                                    i.putExtra("accountId", accountId);
                                    startActivity(i);

                                }

                            }
                        });

                Dialog d = builder.create();

                d.show();

            }
        });

    }

    private void getTotal() {
        // TODO Auto-generated method stub

        Long totalAmount = mDbClass.totalTransactionAmount();

        ConversionClass mCC = new ConversionClass(getActivity());

        Log.d(tag, "totalAmount = " + totalAmount);

        String totAmt = mCC.valueConverter(totalAmount);

        // totAmt =
        // NumberFormat.getNumberInstance(Locale.US).format(totalAmount);

        Log.d(tag, totAmt);

        if (totalAmount < 0) {

            tv_totalAmount.setTextColor(Color.RED);
        }

        if (totalAmount >= 0) {

            tv_totalAmount.setTextColor(Color.parseColor("#08ad14"));
        }

        try {
            tv_totalAmount.setText(totAmt);
        } catch (Exception e) {

            Log.d(tag, e.toString());
        }
    }

    public void listItemPositionClick(int position) {
        // TODO Auto-generated method stub
        Log.d("in accountsList", "here");
        Toast.makeText(getActivity(), "Position clicked: " + position,
                Toast.LENGTH_SHORT).show();

    }

    public long getAccountType(long accountId) {

        DbClass dbc = new DbClass(getActivity());

        dbc.open();
        long accountTypeId = dbc.getAccountTypeId(accountId);
        dbc.close();


        return accountTypeId;
    }

    private class MyAdapter extends CursorAdapter {

        private LayoutInflater inflater;
        private ConversionClass mCC;


        public MyAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            // TODO Auto-generated constructor stub
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mCC = new ConversionClass(context);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // TODO Auto-generated method stub


            ImageView accountTypeImage = (ImageView) view
                    .findViewById(R.id.account_type_icon);
            TextView tv_accountTypeName = (TextView) view
                    .findViewById(R.id.ac_type);
            TextView tv_accountName = (TextView) view
                    .findViewById(R.id.ac_name);
            TextView tv_accountBalance = (TextView) view
                    .findViewById(R.id.ac_balance);
            TextView tv_creditLimit = (TextView) view.findViewById(R.id.tv_creditLimit);

            if (Build.VERSION.SDK_INT < 23) {
                //if api below 23

                tv_accountTypeName.setTypeface(robotoMedium);
                tv_accountTypeName.setTextAppearance(context, R.style.normalText);
                tv_accountName.setTypeface(robotoMedium);
                tv_accountName.setTextAppearance(context, R.style.boldText);
                tv_accountBalance.setTypeface(robotoThin);
                tv_accountBalance.setTextAppearance(context, R.style.boldText);
                tv_creditLimit.setTypeface(robotoMedium);
                tv_creditLimit.setTextAppearance( context, R.style.boldText);
            } else {
                //if api above 23
                tv_accountTypeName.setTypeface(robotoMedium);
                tv_accountTypeName.setTextAppearance( R.style.normalText);
                tv_accountName.setTypeface(robotoMedium);
                tv_accountName.setTextAppearance(R.style.boldText);
                tv_accountBalance.setTypeface(robotoThin);
                tv_accountBalance.setTextAppearance(R.style.boldText);
                tv_creditLimit.setTypeface(robotoMedium);
                tv_creditLimit.setTextAppearance( R.style.boldText);
            }

            String accountTypeName = cursor.getString(cursor
                    .getColumnIndex(DbClass.DATABASE_TABLE_ACCOUNT_TYPE + "."
                            + DbClass.KEY_ACCOUNT_TYPE_NAME));

            String accountName = cursor.getString(cursor
                    .getColumnIndex(DbClass.KEY_ACCOUNT_NAME));

            Long amount = cursor.getLong(cursor.getColumnIndex("acTotal"));

            String amountString = mCC.valueConverter(amount);

            tv_accountTypeName.setText(accountTypeName);

            if (accountTypeName.equals("Cash")) {

                accountTypeImage.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_cash_coins));

                tv_creditLimit.setVisibility(View.GONE);
            } else if (accountTypeName.equals("Bank")) {

                accountTypeImage.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_bank_icon_6));

                tv_creditLimit.setVisibility(View.GONE);
            } else if (accountTypeName.equals("Asset")) {

                accountTypeImage.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_asset));
                tv_creditLimit.setVisibility(View.GONE);
            } else if (accountTypeName.equals("Liability")) {

                accountTypeImage.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_liability));

                tv_creditLimit.setVisibility(View.GONE);
            } else if (accountTypeName.equals("Credit Card")) {

                String cardIssuer = cursor.getString(cursor
                        .getColumnIndex(DbClass.KEY_CARD_ISSUER_NAME));

                String creditProvider = cursor.getString(cursor
                        .getColumnIndex(DbClass.KEY_ACCOUNT_CREDIT_PROVIDER));

                Long creditLimit = cursor.getLong(cursor.getColumnIndex(DbClass.KEY_ACCOUNT_CREDIT_LIMIT));

                String limitAmountString = mCC.valueConverter(creditLimit);

                tv_creditLimit.setVisibility(View.VISIBLE);

                tv_creditLimit.setText(context.getString(R.string.credLimit) + limitAmountString);  //set credit limit

                if (creditProvider == "") {

                    tv_accountTypeName.setText(accountTypeName);

                } else {

                    tv_accountTypeName.setText(creditProvider);

                }


                if (cardIssuer.equals("Visa")) {

                    accountTypeImage.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.ic_card_visa));
                }

                if (cardIssuer.equals("MasterCard")) {

                    accountTypeImage.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.ic_mastercard));
                }

                if (cardIssuer.equals("American Express")) {

                    accountTypeImage.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.ic_card_amex));
                }

                if (cardIssuer.equals("Discover")) {

                    accountTypeImage.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.ic_card_discover));
                }

                if (cardIssuer.equals("Diners Club")) {

                    accountTypeImage.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.ic_card_diners_club));
                }

                if (cardIssuer.equals("Union Pay")) {

                    accountTypeImage.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.ic_card_union_pay));
                }

                if (cardIssuer.equals("JCB")) {

                    accountTypeImage.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.ic_card_jcb));
                }

                if (cardIssuer.equals("Maestro")) {

                    accountTypeImage.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.ic_card_maestro));
                }

                if (cardIssuer.equals("Other")) {

                    accountTypeImage.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.ic_card_visa));
                }

            }


            tv_accountName.setText(accountName);

            tv_accountBalance.setText(amountString);

            if (amount < 0) {

                tv_accountBalance.setTextColor(Color.RED);

            }

            if (amount > 0) {

                tv_accountBalance.setTextColor(Color.rgb(49, 144, 4));
            }
        }

        @Override
        public View newView(Context arg0, Cursor cursor, ViewGroup parent) {
            // TODO Auto-generated method stub

            View view = inflater.inflate(R.layout.single_account_row_new,
                    parent, false);

            return view;
        }
    }

    private void showPaymentDialog(final Context context) {
        // TODO Auto-generated method stub

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(context.getResources().getString(
                R.string.payment_dialog_message)
                + "\n\n"
                + context.getResources()
                .getString(R.string.unlock_all_features) + " ?");

        builder.setNegativeButton(
                context.getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });

        builder.setPositiveButton(context.getResources()
                        .getString(R.string.yes),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        Intent i = new Intent(context, StoreActivity.class);
                        startActivity(i);
                    }
                });

        Dialog paymentDialog = builder.create();
        paymentDialog.show();

    }
}
