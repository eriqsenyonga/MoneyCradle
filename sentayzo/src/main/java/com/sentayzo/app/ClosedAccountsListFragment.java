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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link ClosedAccountsListFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class ClosedAccountsListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	SharedPreferences billingPrefs;
	ListView closedAccountsList;
	LoaderManager mLoaderManager;
	SimpleCursorAdapter mAdapter;
	String tag = "CLosedAccountsListFragment";

	DbClass mDbClass;
	View rootView;
	int accountsLoaderId = 1;
	String proceed;
	ConversionClass mCC;
	Tracker t;

	public ClosedAccountsListFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		rootView = inflater.inflate(R.layout.fragment_closed_accounts_list,
				container, false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
t = ((ApplicationClass) getActivity().getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);
		
		t.setScreenName("ClosedAccountsListFragment");
	    t.send(new HitBuilders.ScreenViewBuilder().build());

		billingPrefs = getActivity()
				.getSharedPreferences("my_billing_prefs", 0);

		proceed = getResources().getString(R.string.proceed);

		closedAccountsList = getListView();

		mLoaderManager = getLoaderManager();

		mLoaderManager.initLoader(accountsLoaderId, null, this);

		String[] from = {
				DbClass.DATABASE_TABLE_ACCOUNT_TYPE + "."
						+ DbClass.KEY_ACCOUNT_TYPE_NAME,
				DbClass.KEY_ACCOUNT_NAME, "acTotal" };

		int[] to = { R.id.ac_type, R.id.ac_name, R.id.ac_balance };

		mAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.single_account_row, null, from, to, 0);
		Log.d(tag, "8");
		mAdapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View arg0, Cursor cursor,
					int columnIndex) {
				// TODO Auto-generated method stub
				Log.d(tag, "9");
				if (columnIndex == 3) {
					// columnIndex 3 contains the total amount for the account
					Log.d(tag, "10");
					TextView tv = (TextView) arg0.findViewById(R.id.ac_balance);

					Long amount = cursor.getLong(columnIndex);
					String amountText = new ConversionClass(getActivity())
							.valueConverter(amount);

					tv.setText(amountText);

					if (cursor.getFloat(columnIndex) < 0) {
						Log.d(tag, "11");
						tv.setTextColor(Color.RED);

					}

					if (cursor.getFloat(columnIndex) > 0) {
						Log.d(tag, "12");
						tv.setTextColor(Color.rgb(49, 144, 4));
					}
					Log.d(tag, "13");
					return true;
				}
				Log.d(tag, "14");
				return false;
			}
		});
		Log.d(tag, "15");
		closedAccountsList.setAdapter(mAdapter);

		closedAccountsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					final long accountId) {
				// TODO Auto-generated method stub

				final Boolean freePeriod = billingPrefs.getBoolean(
						"KEY_FREE_TRIAL_PERIOD", true);
				final Boolean unlocked = billingPrefs.getBoolean(
						"KEY_PURCHASED_UNLOCK", false);

				String[] items = { getResources().getString(R.string.reopen),
						getResources().getString(R.string.delete) };

				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						if (which == 0) {
							// if reopen is clicked

							if (freePeriod == true || unlocked == true) {

								showReopenDialog(accountId);
							} else {

								showPaymentDialog(getActivity());
							}

						}

						if (which == 1) {
							// if delete is clicked
							String deleteDialogTitle = getResources()
									.getString(R.string.deleteDialogTitle);
							String deleteDialogMessage = getResources()
									.getString(R.string.deleteDialogMessage);

							AlertDialog.Builder builder2 = new AlertDialog.Builder(
									getActivity());
							builder2.setTitle(deleteDialogTitle);
							builder2.setMessage(deleteDialogMessage + " \n\n"
									+ proceed);
							builder2.setNegativeButton(getResources()
									.getString(R.string.no),
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface arg0, int arg1) {
											// TODO Auto-generated
											// method stub

										}
									});

							builder2.setPositiveButton(getResources()
									.getString(R.string.yes),
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface arg0, int arg1) {
											// TODO Auto-generated
											// method stub

											DbClass aDbClass = new DbClass(
													getActivity());
											aDbClass.open();
											aDbClass.deleteAccountWithId(accountId);
											aDbClass.close();

											mLoaderManager
													.restartLoader(
															accountsLoaderId,
															null,
															ClosedAccountsListFragment.this);

										}
									});

							Dialog delDialog = builder2.create();
							delDialog.show();

						}

					}
				});

				Dialog closedDialog = builder.create();
				closedDialog.show();

			}
		});
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new CursorLoader(getActivity(), Uri.parse("content://"
				+ "SentayzoDbAuthority" + "/closedAccounts"), null, null, null,
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}

	public void showReopenDialog(final Long accountId) {
		String reopenDialogTitle = getResources().getString(
				R.string.reopenDialog_title);
		String reopenDialogMessage = getResources().getString(
				R.string.reopenDialog_message);
		AlertDialog.Builder closeBuilder = new AlertDialog.Builder(
				getActivity());
		closeBuilder.setTitle(reopenDialogTitle);
		closeBuilder.setMessage(reopenDialogMessage + " \n\n" + proceed);

		closeBuilder.setNegativeButton(getResources().getString(R.string.no),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated
						// method stub

					}
				});

		closeBuilder.setPositiveButton(getResources().getString(R.string.yes),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated
						// method stub

						DbClass mDbClass = new DbClass(getActivity());

						mDbClass.open();

						mDbClass.reopenAccountWithId(accountId);

						mDbClass.close();

						mLoaderManager.restartLoader(accountsLoaderId, null,
								ClosedAccountsListFragment.this);

					}
				});

		Dialog closeDialog = closeBuilder.create();
		closeDialog.show();
	}

	public void showPaymentDialog(final Context context) {
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
