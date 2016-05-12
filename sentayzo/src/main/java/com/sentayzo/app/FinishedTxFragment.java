package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;


public class FinishedTxFragment extends ListFragment {

    ListView finishedList;

    FinishedTxListAdapter transactionAdapter;


    String tag = "txList";
    DbClass mDbClass;
    ConversionClass mCC;

    public FinishedTxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_finished_tx, container, false);
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mCC = new ConversionClass(getActivity());

        finishedList = getListView();

        transactionAdapter = new FinishedTxListAdapter(
                getActivity(),
                getActivity().getContentResolver().query(Uri.parse("content://"
                                + "SentayzoDbAuthority" + "/finished"), null, null, null,
                        null),
                0);

        finishedList.setAdapter(transactionAdapter);

        finishedList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    final long id) {

                String[] finTxDialogItems = getResources().getStringArray(R.array.finTxListDialog);

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity());

                builder.setItems(finTxDialogItems, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if (which == 0) {
                            //if View Details is clicked
                            viewTransactionDetails(id,
                                    getActivity());
                        }
                        if (which == 1) {
                            //if delete is clicked
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(
                                    getActivity());
                            builder1.setTitle(getResources().getString(R.string.deleteDialogTitle));
                            builder1.setNegativeButton(
                                    getResources().getString(R.string.no),
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface arg0,
                                                int arg1) {

                                            // method stub

                                        }
                                    });
                            builder1.setPositiveButton(
                                    getResources().getString(R.string.yes),
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface arg0,
                                                int arg1) {

                                            // method stub
                                            DbClass mDbClass = new DbClass(
                                                    getActivity());
                                            mDbClass.open();
                                            mDbClass.deleteFinTxWithId(id);
                                            mDbClass.close();

                                            transactionAdapter.changeCursor(getActivity().getContentResolver().query(Uri.parse("content://"
                                                            + "SentayzoDbAuthority" + "/finished"), null, null, null,
                                                    null));
                                        }
                                    });

                            Dialog delTxDialog = builder1.create();
                            delTxDialog.show();

                        }

                    }
                });
                Dialog txDialog = builder.create();
                txDialog.show();
            }
        });


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

        DbClass miDbClass = new DbClass(context);

        miDbClass.open();

        Bundle bundle = miDbClass.getFinTxInfo(transactionId);

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

                        // method stub

                    }
                });

        Dialog txViewDialog = builder.create();
        txViewDialog.show();

    }

}
