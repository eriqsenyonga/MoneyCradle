package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class TransactionListFragment extends Fragment {

    RecyclerView txList;
    TransactionRecyclerAdapter adapter;
    TxListInteraction txListInteraction;

    TextView tv_totalAmount;
    String tag = "txList";
    DbClass mDbClass;
    ConversionClass mCC;

    public TransactionListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_transaction_list,
                container, false);

        txList = (RecyclerView) view.findViewById(R.id.rv_tx_list);

        tv_totalAmount = (TextView) view.findViewById(R.id.tv_totalView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        mCC = new ConversionClass(getActivity());

        txListInteraction = new TxListInteraction(getActivity(), TxListInteraction.ALL_TRANSACTIONS);

        adapter = new TransactionRecyclerAdapter(getActivity().getContentResolver().query(Uri.parse("content://"
                        + "SentayzoDbAuthority" + "/transactions"), null, null, null,
                null), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position, long id) {
                txListInteraction.start(id, adapter);
            }
        });

        txList.setAdapter(adapter);

        txList.setLayoutManager(new LinearLayoutManager(getActivity()));

        txList.addItemDecoration(new ListDividerDecoration(getActivity()));

        getTotal();


    }

    private void getTotal() {


        Log.d("tx_list_frag", "in getTotal");
        mDbClass = new DbClass(getActivity());
        Long totalAmount = mDbClass.totalTransactionAmountHistory();

        ConversionClass mCC = new ConversionClass(getActivity());

        Log.d(tag, "totalAmount = " + totalAmount);

        String totAmt = mCC.valueConverter(totalAmount);

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


}
