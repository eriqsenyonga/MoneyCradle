package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Eriq on 12/24/2015.
 */
public class TxListInteraction {

    Context context;

    int whichList;

    static int ALL_TRANSACTIONS = 1;
    static int ACCOUNT_TRANSACTIONS = 2;
    static int CATEGORY_TRANSACTIONS = 3;
    static int PAYEE_TRANSACTIONS = 4;
    static int PROJECT_TRANSACTIONS = 5;

    ConversionClass mCC;

    public TxListInteraction(Context context, int whichList) {

        this.context = context;
        this.whichList = whichList;
        mCC = new ConversionClass(context);

    }

    public void start(long id, final TransactionRecyclerAdapter adapter) {

        final long transactionId = id;

        String[] txDialogItems = context.getResources().getStringArray(
                R.array.transactionsListDialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setItems(txDialogItems,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0,
                                        int position) {
                        // TODO Auto-generated method stub

                        if (position == 0) {
                            // if View Details is clicked

                            // viewTransactionDetails(transactionId, Temp_activity.this);

                            viewTransactionDetails(transactionId);

                        }

                        if (position == 1) {
                            // if Edit is clicked

                            editTransaction(transactionId);

                        }
                        if (position == 2) {
                            // if Delete is clicked

                            deleteTransaction(transactionId, adapter);

                        }
                        if (position == 3) {
                            // if Cancel is clicked
                        }
                    }


                });

        Dialog txDialog = builder.create();
        txDialog.show();
    }

    public void viewTransactionDetails(Long transactionId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setTitle(context.getResources().getString(
                R.string.transaction_details));

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(
                R.layout.transaction_view, null);

        TextView tvDate = (TextView) view
                .findViewById(R.id.tv_txdate);
        TextView tvAcc = (TextView) view
                .findViewById(R.id.tv_txaccName);
        TextView tvPayee = (TextView) view
                .findViewById(R.id.tv_txpayee);
        TextView tvCat = (TextView) view
                .findViewById(R.id.tv_txcategory);
        TextView tvAmt = (TextView) view
                .findViewById(R.id.tv_txamount);
        TextView tvNote = (TextView) view
                .findViewById(R.id.tv_txnote);
        TextView tvProject = (TextView) view
                .findViewById(R.id.tv_txproject);


        Bundle bundle = null;

        DbClass miDbClass = new DbClass(
                context);

        Long categId = miDbClass.getCategoryId(transactionId);

        miDbClass.open();

        if (categId == 24 || categId == 2) {
            // 24 is catId for transfer
            //2 is catId for opening balance
            bundle = miDbClass.getTxInfoWithoutProject(transactionId);

            TextView tvProjectTitle = (TextView) view.findViewById(R.id.tv_txproj);
            tvProjectTitle.setVisibility(View.GONE);
            tvProject.setVisibility(View.GONE);

        } else {
            bundle = miDbClass
                    .getTxInfo(transactionId);
        }

        miDbClass.close();

        tvDate.setText(mCC.dateForDisplay(bundle
                .getString("txdate")));
        tvAcc.setText(bundle.getString("txacc"));
        tvPayee.setText(bundle.getString("txpayee"));
        tvCat.setText(bundle.getString("txcat"));
        tvAmt.setText(mCC.valueConverter(bundle
                .getLong("txamt")));
        tvNote.setText(bundle.getString("txnote"));
        tvProject.setText(bundle.getString("txproject"));


        builder.setView(view);

        builder.setNeutralButton(context.
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

        Dialog txViewDialog = builder.create();
        txViewDialog.show();


    }

    public void editTransaction(long transactionId) {
        DbClass myDbClass = new DbClass(
                context);
        myDbClass.open();
        Bundle bundle = myDbClass
                .getTransactionWithId(transactionId);
        myDbClass.close();

        bundle.putLong("tId", transactionId);

        Log.d("Info", "transactionId" + bundle.getLong("tId") + "\n" +
                "accountId" + bundle.getLong("tAcId"));

        if (bundle.getLong("tTxTypeId") == 7
                && bundle.getLong("tCatId") == 2) {
            //if the transaction is for opening an account


            if (getAccountType(bundle
                    .getLong("tAcId")) == 5) {
                //if the accountType is Credit Card
                DbClass jDbClass = new DbClass(
                        context);
                jDbClass.open();
                Bundle wbundle = myDbClass
                        .getCreditAccountInfoWithId(bundle
                                .getLong("tAcId"));
                jDbClass.close();
                wbundle.putLong("acId", bundle
                        .getLong("tAcId"));

                Intent i = new Intent(context,
                        NewAccount.class);
                i.putExtra("infoBundle", wbundle);
                context.startActivity(i);


            } else {
                //if the accountType is NOT CREDIT CARD

                DbClass oDbClass = new DbClass(
                        context);
                oDbClass.open();
                Bundle kbundle = myDbClass
                        .getTheInfoOfAccountWithId(bundle
                                .getLong("tAcId"));
                oDbClass.close();
                kbundle.putLong("acId",
                        bundle
                                .getLong("tAcId"));

                Intent i = new Intent(
                        context,
                        NewAccount.class);
                i.putExtra("infoBundle",
                        kbundle);
                context.startActivity(i);
            }

        } else if (bundle.getLong("tCatId") == 24) {
            //if the transaction is a transfer

            Intent i = new Intent(context,
                    EditTransfer.class);
            i.putExtra("bundle", bundle);
            context.startActivity(i);

        } else {
            // if transaction is normal
            Intent i = new Intent(context,
                    EditTransaction.class);
            i.putExtra("txBundle", bundle);
            context.startActivity(i);

        }
    }

    public void deleteTransaction(final long transactionId, final TransactionRecyclerAdapter adapter) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(
                context);
        builder1.setTitle(context.getResources().getString(
                R.string.deleteDialogTitle));
        builder1.setNegativeButton(
                context.getResources().getString(
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
        builder1.setPositiveButton(
                context.getString(
                        R.string.yes),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface arg0,
                            int arg1) {
                        // TODO Auto-generated
                        // method stub
                        DbClass mDbClass = new DbClass(
                                context);
                        mDbClass.open();
                        mDbClass.deleteTxWithId(transactionId);
                        mDbClass.close();


                        adapter.changeCursor(getParticularCursor());

                        adapter.notifyDataSetChanged();

                    }
                });

        Dialog delTxDialog = builder1.create();
        delTxDialog.show();
    }


    private Cursor getParticularCursor() {

        if (whichList == ALL_TRANSACTIONS) {

            return context.getContentResolver().query(Uri.parse("content://"
                            + "SentayzoDbAuthority" + "/transactions"), null, null, null,
                    null);

        } else if (whichList == ACCOUNT_TRANSACTIONS) {

            return context.getContentResolver().query(Uri.parse("content://"
                            + "SentayzoDbAuthority" + "/transactionsAc"), null, null, null,
                    null);

        } else if (whichList == CATEGORY_TRANSACTIONS) {


            return context.getContentResolver().query(Uri.parse("content://"
                            + "SentayzoDbAuthority" + "/transactionsCat"), null, null, null,
                    null);
        } else if (whichList == PAYEE_TRANSACTIONS) {

            return context.getContentResolver().query(Uri.parse("content://"
                            + "SentayzoDbAuthority" + "/transactionsPayee"), null, null, null,
                    null);
        } else if (whichList == PROJECT_TRANSACTIONS) {

            return context.getContentResolver().query(Uri.parse("content://"
                            + "SentayzoDbAuthority" + "/transactionsPr"), null, null, null,
                    null);
        }

        return null;


    }

    public long getAccountType(long accountId) {


        DbClass dbc = new DbClass(context);

        dbc.open();
        long accountTypeId = dbc.getAccountTypeId(accountId);
        dbc.close();

        Log.d("aacountTypeId", "" + accountTypeId);

        return accountTypeId;
    }
}
