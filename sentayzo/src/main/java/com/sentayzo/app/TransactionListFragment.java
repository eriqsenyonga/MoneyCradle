package com.sentayzo.app;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

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
    FloatingActionsMenu fam;
    FloatingActionButton fab;
    Animation animScaleUp, animScaleDown;

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

        fam = (FloatingActionsMenu) getActivity().findViewById(R.id.fam_fab);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);


        fab.setVisibility(View.GONE);

        if (fam.getVisibility() == View.GONE) {

            fam.setVisibility(View.VISIBLE);
            fam.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up));
        }


        animScaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);
        animScaleUp = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);

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



        txList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    if (fam.getVisibility() == View.GONE) {
                    } else {
                        fam.collapse();
                        fam.startAnimation(animScaleDown);
                        fam.setVisibility(View.GONE);
                    }
                } else if (dy < 0) {

                    if (fam.getVisibility() == View.VISIBLE) {
                    } else {
                        fam.setVisibility(View.VISIBLE);
                        fam.startAnimation(animScaleUp);
                    }
                }
            }
        });


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
