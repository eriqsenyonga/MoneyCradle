package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

/**
 * A simple {@link Fragment} subclass. Use the
 * {
 * this fragment.
 */
public class PendingTxFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    RecyclerView rvPending;
    PendingTxRecyclerAdapter adapter;
    FloatingActionsMenu fam;
    FloatingActionButton fab;

    String tag = "pendingList";
    DbClass mDb;
    ConversionClass mCC;

    SharedPreferences billingPrefs;

    public PendingTxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_pending_tx, container,
                false);

        rvPending = (RecyclerView) v.findViewById(R.id.rv_tx_list);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        mCC = new ConversionClass(getActivity());

        billingPrefs = getActivity()
                .getSharedPreferences("my_billing_prefs", 0);

        fam = (FloatingActionsMenu) getActivity().findViewById(R.id.fam_fab);
        fam.setVisibility(View.GONE);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);


        if (fab.getVisibility() == View.GONE) {

            fab.setVisibility(View.VISIBLE);
            fab.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up));

        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean freePeriod = billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD",
                        true);
                Boolean unlocked = billingPrefs.getBoolean("KEY_PURCHASED_UNLOCK",
                        false);


                if (freePeriod == true || unlocked == true) {
                    Intent i = new Intent(getActivity(),
                            NewScheduledTransactionActivity.class);
                    startActivity(i);
                } else {
                    // if free trial has expired and nigga hasnt paid for shit
                    int numberOfSch = new DbClass(getActivity())
                            .getNumberOfProjects();

                    if (numberOfSch < 1) {
                        Intent i = new Intent(getActivity(),
                                NewScheduledTransactionActivity.class);
                        startActivity(i);

                    } else {

                        showPaymentDialog(getActivity());

                    }

                }


            }
        });

        adapter = new PendingTxRecyclerAdapter(getActivity().getContentResolver().query(Uri.parse("content://"
                        + "SentayzoDbAuthority" + "/pending"), null, null, null,
                null), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position, final long pendingId) {
                //   txListInteraction.start(id, adapter);


                String[] pendTxDialogItems = getResources().getStringArray(
                        R.array.pendingTxListDialog);

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity());

                builder.setItems(pendTxDialogItems,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {


                                if (which == 0) {
                                    // if View Details is clicked
                                    AlertDialog.Builder builder0 = new AlertDialog.Builder(
                                            getActivity());
                                    builder0.setTitle(getResources().getString(
                                            R.string.transaction_details));

                                    LayoutInflater inflater = getActivity()
                                            .getLayoutInflater();
                                    View view = inflater.inflate(
                                            R.layout.dialog_pending_details, null);

                                    TextView tvStartDate = (TextView) view
                                            .findViewById(R.id.pend_tv_txdateStart);
                                    TextView tvEndDate = (TextView) view
                                            .findViewById(R.id.pend_tv_txdateEnd);
                                    TextView tvRecur = (TextView) view
                                            .findViewById(R.id.pend_tv_txRecur);
                                    TextView tvNote = (TextView) view
                                            .findViewById(R.id.pend_tv_txNote);
                                    TextView tvProject = (TextView) view
                                            .findViewById(R.id.pend_tv_txProject);
                                    TextView tvAccount = (TextView) view
                                            .findViewById(R.id.pend_tv_txaccName);
                                    TextView tvPayee = (TextView) view
                                            .findViewById(R.id.tv_txpayee);
                                    TextView tvCategory = (TextView) view
                                            .findViewById(R.id.tv_txcategory);
                                    TextView tvAmount = (TextView) view
                                            .findViewById(R.id.tv_txamount);

                                    DbClass miDbClass = new DbClass(
                                            getActivity());

                                    miDbClass.open();

                                    Bundle bundle = miDbClass
                                            .getPendTxInfo(pendingId);

                                    miDbClass.close();

                                    String startDate = bundle.getString("startDate");
                                    String endDate = bundle.getString("endDate");
                                    String recur = bundle.getString("recur");
                                    String account = bundle.getString("account");
                                    String payee = bundle.getString("payee");
                                    String category = bundle.getString("category");
                                    String project = bundle.getString("project");
                                    String note = bundle.getString("note");
                                    Long amount = bundle.getLong("amount");

                                    tvStartDate.setText(mCC.dateForDisplay(startDate));
                                    tvEndDate.setText(mCC.dateForDisplay(endDate));
                                    tvRecur.setText(recur);
                                    tvAmount.setText(mCC.valueConverter(amount));
                                    tvProject.setText(project);
                                    tvPayee.setText(payee);
                                    tvCategory.setText(category);
                                    tvAccount.setText(account);
                                    tvNote.setText(note);


                                    builder0.setView(view);

                                    builder0.setNeutralButton(
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

                                    Dialog txViewDialog = builder0.create();
                                    txViewDialog.show();

                                }
                                if (which == 2) {
                                    // if delete is clicked
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                                            getActivity());
                                    builder1.setTitle(getResources().getString(
                                            R.string.deleteDialogTitle));
                                    builder1.setMessage(getResources().getString(R.string.delete_scheduled));
                                    builder1.setNegativeButton(
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
                                    builder1.setPositiveButton(
                                            getResources().getString(
                                                    R.string.yes),
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface arg0,
                                                        int arg1) {
                                                    // TODO Auto-generated
                                                    // method stub
                                                    DbClass mDbClass = new DbClass(
                                                            getActivity());
                                                    mDbClass.open();
                                                    mDbClass.deletePendTxWithId(pendingId, getActivity());
                                                    mDbClass.close();

                                                    adapter.changeCursor(getActivity().getContentResolver().query(Uri.parse("content://"
                                                                    + "SentayzoDbAuthority" + "/pending"), null, null, null,
                                                            null));

                                                    adapter.notifyDataSetChanged();
                                                }
                                            });

                                    Dialog delTxDialog = builder1.create();
                                    delTxDialog.show();

                                }

                                if (which == 1) {
                                    //if edit is clicked

                                    DbClass myDb = new DbClass(getActivity());
                                    Bundle bundle = myDb.getScheduledDetailsAtId(pendingId);

                                    bundle.putLong("pendingId", pendingId);
                                    Intent i = new Intent(getActivity(), EditScheduledTx.class);
                                    i.putExtra("bundle", bundle);
                                    startActivity(i);

                                }

                            }
                        });
                Dialog txDialog = builder.create();
                txDialog.show();
            }
        });

        rvPending.setAdapter(adapter);

        rvPending.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvPending.addItemDecoration(new ListDividerDecoration(getActivity()));


    }

    private void showPaymentDialog(final Context context) {
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
                        //

                    }
                });

        builder.setPositiveButton(context.getResources()
                        .getString(R.string.yes),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent i = new Intent(context, StoreActivity.class);
                        startActivity(i);
                    }
                });

        Dialog paymentDialog = builder.create();
        paymentDialog.show();


    }


}
