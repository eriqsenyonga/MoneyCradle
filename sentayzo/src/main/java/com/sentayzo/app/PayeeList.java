package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

public class PayeeList extends ListFragment implements LoaderCallbacks<Cursor>,
		OnItemClickListener, OnItemLongClickListener {

	ListView payeeList;
	LoaderManager mLoaderManager;
	SimpleCursorAdapter payeeAdapter;
	int payeeLoaderId;
	DbClass mDbClass;
	View rootView;
	Tracker t;
	FloatingActionsMenu fam;
	FloatingActionButton fab;

	public PayeeList() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
t = ((ApplicationClass) getActivity().getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);
		
		t.setScreenName("PayeeList");
	    t.send(new HitBuilders.ScreenViewBuilder().build());

		rootView = inflater.inflate(R.layout.activity_payee_list, container,
				false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mDbClass = new DbClass(getActivity());

		payeeList = getListView();

		mLoaderManager = getLoaderManager();

		String[] from = new String[] { DbClass.KEY_PAYEE_NAME };
		int[] to = new int[] { android.R.id.text1 };

		payeeAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_1, null, from, to, 0);

		payeeList.setAdapter(payeeAdapter);

		mLoaderManager.initLoader(payeeLoaderId, null, this);

		payeeList.setOnItemClickListener(this);

		payeeList.setOnItemLongClickListener(this);

		fam = (FloatingActionsMenu) getActivity().findViewById(R.id.fam_fab);
		fam.setVisibility(View.GONE);

		fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);


		fab.setVisibility(View.GONE);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new CursorLoader(getActivity(), Uri.parse("content://"
				+ "SentayzoDbAuthority" + "/payees"), null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub

		payeeAdapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		payeeAdapter.swapCursor(null);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		// TODO Auto-generated method stub

		TextView temp = (TextView) v;
		String payeeName = temp.getText().toString();
		long payeeId = id;

		Intent i = new Intent(getActivity(), PayeeTxView.class);

		i.putExtra("payeeName", payeeName);
		i.putExtra("payeeId", payeeId);

		startActivity(i);

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, final View v, int arg2,
			final long id) {
		// TODO Auto-generated method stub
		final TextView temp = (TextView) v;
		String[] items = { "Rename", "Delete", "Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int position) {
				// TODO Auto-generated method stub

				if (position == 0) {
					// if 'Rename' is clicked
					if (id == 1) {

						Toast.makeText(getActivity(),
								"Can't rename 'open account' ",
								Toast.LENGTH_LONG).show();
					} else {

						String payeeName = temp.getText().toString();

						LayoutInflater inflater = getActivity()
								.getLayoutInflater();
						View v = inflater.inflate(R.layout.new_category_dialog,
								null);
						TextView entryName = (TextView) v
								.findViewById(R.id.tv_entry_name);
						final EditText payeeEt = (EditText) v
								.findViewById(R.id.et_cat_Name);

						entryName.setText("Payee Name");
						payeeEt.setText(payeeName);

						AlertDialog.Builder builder = new AlertDialog.Builder(
								getActivity());
						builder.setTitle("Edit Payee");

						builder.setView(v);

						builder.setNegativeButton(
								getResources().getString(R.string.cancel),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub

									}
								});

						builder.setPositiveButton(
								getResources().getString(R.string.save),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										String payeeName = payeeEt.getText()
												.toString();

										if (payeeEt.getText().length() <= 0) {

											Toast.makeText(
													getActivity(),
													"Payee name can't be empty ",
													Toast.LENGTH_LONG).show();

										}

										else {

											DbClass mDbClasss = new DbClass(
													getActivity());
											mDbClasss.open();

											mDbClasss
													.updatePayee(payeeName, id);

											mDbClasss.close();

											mLoaderManager.restartLoader(
													payeeLoaderId, null,
													PayeeList.this);

											Toast.makeText(
													getActivity(),
													"Payee: " + payeeName
															+ " edited ",
													Toast.LENGTH_LONG).show();
										}
									}
								});

						Dialog newCatDialog = builder.create();
						newCatDialog.show();

					}

				}

				if (position == 1) {
					// if "Delete" is clicked
					if (id == 1) {
						// if item is open account
						String toastText = getResources().getString(
								R.string.cantDelete)
								+ " '" + temp.getText().toString() + "'";
						Toast.makeText(getActivity(), toastText,
								Toast.LENGTH_LONG).show();
					}

					else if (id == 2) {
						// if item is no payee
						String toastText = getResources().getString(
								R.string.cantDelete)
								+ " '" + temp.getText().toString() + "'";
						Toast.makeText(getActivity(), toastText,
								Toast.LENGTH_LONG).show();
					} else {
						String dTitle = getResources().getString(
								R.string.delete)
								+ " "
								+ getResources().getString(R.string.payee)
								+ "?";
						AlertDialog.Builder builder2 = new AlertDialog.Builder(
								getActivity());
						builder2.setTitle(dTitle);
						builder2.setNegativeButton(
								getResources().getString(R.string.no),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								});

						builder2.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

										mDbClass.open();
										mDbClass.deletePayeeyWithId(id);
										mDbClass.close();

										mLoaderManager.restartLoader(
												payeeLoaderId, null,
												PayeeList.this);

									}
								});

						builder2.setMessage("Payee will be deleted and all its transactions set to 'No Payee' \n\n Proceed?");

						Dialog deleteCatDialog = builder2.create();
						deleteCatDialog.show();
					}
				}

				if (position == 2) {
					// if "Cancel" is clicked

				}

			}
		});

		Dialog catListDialog = builder.create();
		catListDialog.show();

		return true;
	}
}
