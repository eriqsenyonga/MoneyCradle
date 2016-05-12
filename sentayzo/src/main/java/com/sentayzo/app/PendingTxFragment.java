package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link PendingTxFragment#newInstance} factory method to create an instance of
 * this fragment.
 * 
 */
public class PendingTxFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

	ListView pendingList;
	LoaderManager mLoaderManager;
	SimpleCursorAdapter pendingAdapter;
	int pendingLoaderId = 1;

	String tag = "pendingList";
	DbClass mDb;
	ConversionClass mCC;

	public PendingTxFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View v = inflater.inflate(R.layout.fragment_pending_tx, container,
				false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mCC = new ConversionClass(getActivity());
		pendingList = getListView();
		mLoaderManager = getLoaderManager();
		mLoaderManager.initLoader(pendingLoaderId, null, this);

		String[] from = {
				DbClass.KEY_SCHEDULED_START_DATE,
				DbClass.DATABASE_TABLE_SCHEDULED_RECURRENCE + "."
						+ DbClass.KEY_SCHEDULED_RECURRENCE_NAME,
				DbClass.DATABASE_TABLE_ACCOUNT + "." + DbClass.KEY_ACCOUNT_NAME,
				DbClass.DATABASE_TABLE_PAYEE + "." + DbClass.KEY_PAYEE_NAME,
				DbClass.DATABASE_TABLE_CATEGORY + "."
						+ DbClass.KEY_CATEGORY_NAME,
				DbClass.KEY_SCHEDULED_AMOUNT };

		int[] to = { R.id.tv_pen_tx_date, R.id.tv_pen_recur,
				R.id.tv_pen_tx_account, R.id.tv_pen_tx_payee,
				R.id.tv_pen_tx_category, R.id.tv_pen_tx_amount };

		pendingAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.pending_sch_tx, null, from, to, 0);

		pendingAdapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View arg0, Cursor cursor,
					int columnIndex) {
				// TODO Auto-generated method stub
				if (columnIndex == 1) {
					// columnIndex 1 contains the date

					TextView tvdate = (TextView) arg0
							.findViewById(R.id.tv_pen_tx_date);
					String dateFromDb = cursor.getString(columnIndex);
					String dateForDisp = mCC.dateForDisplay(dateFromDb);

					tvdate.setText(dateForDisp);

					return true;
				}

				if (columnIndex == 6) {
					// columnIndex 6 contains the amount for this transaction

					TextView tv = (TextView) arg0
							.findViewById(R.id.tv_pen_tx_amount);
					Long amount = cursor.getLong(columnIndex);
					String amountText = mCC.valueConverter(amount);

					tv.setText(amountText);

					if (amount < 0) {
						tv.setTextColor(Color.RED);

					}

					if (amount > 0) {
						tv.setTextColor(Color.rgb(49, 144, 4));
					}

					if (amount == 0) {
						tv.setTextColor(Color.BLACK);
					}

					return true;
				}
				return false;
			}
		});

		pendingList.setAdapter(pendingAdapter);

		pendingList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, final long pendingId) {
				// TODO Auto-generated method stub

				String[] pendTxDialogItems = getResources().getStringArray(
						R.array.pendingTxListDialog);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());

				builder.setItems(pendTxDialogItems,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

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

													mLoaderManager
															.restartLoader(
																	pendingLoaderId,
																	null,
																	PendingTxFragment.this);
												}
											});

									Dialog delTxDialog = builder1.create();
									delTxDialog.show();

								}
								
								if(which == 1){
									//if edit is clicked
									
									DbClass myDb = new DbClass(getActivity());
									Bundle bundle = myDb.getScheduledDetailsAtId(pendingId);
									
									bundle.putLong("pendingId", pendingId);
									Intent i = new Intent(getActivity(), EditScheduledTx.class );
									i.putExtra("bundle", bundle);
									startActivity(i);
									
								}

							}
						});
				Dialog txDialog = builder.create();
				txDialog.show();

			}
		});
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new CursorLoader(getActivity(), Uri.parse("content://"
				+ "SentayzoDbAuthority" + "/pending"), null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		pendingAdapter.swapCursor(arg1);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

		pendingAdapter.swapCursor(null);
	}

}
