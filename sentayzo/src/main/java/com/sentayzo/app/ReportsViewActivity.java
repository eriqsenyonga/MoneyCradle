package com.sentayzo.app;

import java.util.Calendar;

import org.achartengine.GraphicalView;

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
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ReportsViewActivity extends AppCompatActivity implements
		LoaderCallbacks<Cursor>, OnClickListener {

	
	Toolbar toolBar;
	SharedPreferences billingPrefs;

	String[] configNumberString;

	Spinner specificEntitySpinner, graphTypeSpinner,
			expenseOrIncomeBothSpinner, periodSpinner;

	TextView from, to, groupBy, specificEntity;

	String defaultString, noData;

	FrameLayout graphViewArea;

	ArrayAdapter<String> graphTypeAdapter, expenseOrIncomeBothAdapter,
			periodAdapter;

	SimpleCursorAdapter specificEntityAdapter;

	Button fromDateSpinner, toDateSpinner;

	Integer accountLoaderId = 5, categoryLoaderId = 6, payeeLoaderId = 7,
			projectLoaderId = 8;

	LoaderManager mLoaderManager;

	Integer configNumber, generalConfig = 0, accountConfig = 1,
			categoryConfig = 2, payeeConfig = 3, projectConfig = 4,
			accountPeriodConfigNumber = 5, categoryPeriodConfigNumber = 6,
			payeePeriodConfigNumber = 7, projectPeriodConfigNumber = 8;

	Intent i;

	Calendar c;

	ConversionClass mCC;

	String accountString, categoryString, payeeString, projectString;

	int time = 0;

	int graphTypeSpinnerPosition, periodSpinnerPosition,
			expenseOrIncomeBothSpinnerPosition;

	long specificEntitySpinnerId;

	String[] graphTypes;
	String[] period;
	String[] expenseOrIncomeBoth;

	
	Tracker t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reports_fragment);
		
		toolBar = (Toolbar) findViewById(R.id.app_bar);
		setSupportActionBar(toolBar);
		
		t = ((ApplicationClass) getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);
		
		t.setScreenName("ReportsViewActivity");
	    t.send(new HitBuilders.ScreenViewBuilder().build());
		
		i = getIntent();
		configNumber = i.getIntExtra("configNumber", 0);

		configNumberString = getResources().getStringArray(
				R.array.reportsFragmentList);

		getSupportActionBar().setTitle(configNumberString[configNumber]);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		billingPrefs = getSharedPreferences("my_billing_prefs", MODE_PRIVATE);

		c = Calendar.getInstance();

		mCC = new ConversionClass(this);
		
		noData = getResources().getString(R.string.no_data);

		specificEntitySpinner = (Spinner) findViewById(R.id.spinner_specific_entity);
		graphTypeSpinner = (Spinner) findViewById(R.id.spinner_graph_type);
		expenseOrIncomeBothSpinner = (Spinner) findViewById(R.id.spinner_expense_income_both);

		periodSpinner = (Spinner) findViewById(R.id.spinner_period);
		fromDateSpinner = (Button) findViewById(R.id.spinner_from_date);
		toDateSpinner = (Button) findViewById(R.id.spinner_to_date);

		graphViewArea = (FrameLayout) findViewById(R.id.graph_view_area);

		from = (TextView) findViewById(R.id.tv_from);
		to = (TextView) findViewById(R.id.tv_to);
		groupBy = (TextView) findViewById(R.id.tv_groupBy);
		specificEntity = (TextView) findViewById(R.id.tv_specific_entity);

		accountString = getResources().getString(R.string.account) + " :";
		categoryString = getResources().getString(R.string.category) + " :";
		payeeString = getResources().getString(R.string.payee) + " :";
		projectString = getResources().getString(R.string.project) + " :";

		graphTypes = getResources()
				.getStringArray(R.array.reportsSpinner3Items);
		period = getResources().getStringArray(R.array.reportsSpinner4Items);
		expenseOrIncomeBoth = getResources().getStringArray(
				R.array.reportsSpinner2Items);

		mLoaderManager = getSupportLoaderManager();

		configureLayoutSetup(configNumber);

		populateSpinners();

		defaultString = getResources().getString(R.string.default_string);

		fromDateSpinner.setText(defaultString);
		toDateSpinner.setText(defaultString);

		fromDateSpinner.setOnClickListener(this);
		toDateSpinner.setOnClickListener(this);

		specificEntitySpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						specificEntitySpinnerId = arg3;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		graphTypeSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						graphTypeSpinnerPosition = arg2;

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		expenseOrIncomeBothSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						expenseOrIncomeBothSpinnerPosition = arg2;

						if (arg2 == 2) {

							graphTypeSpinner.setEnabled(false);
						} else {
							graphTypeSpinner.setEnabled(true);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		graphTypeSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						graphTypeSpinnerPosition = arg2;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		periodSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				periodSpinnerPosition = arg2;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		Log.d("configNumber", "" + configNumber);

		setUpGraph(configNumber);
	}

	private void setUpGraph(Integer configNumber2) {
		// TODO Auto-generated method stub
		final DbClass mDb = new DbClass(this);

		Boolean freePeriod = billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD",
				true);
		Boolean unlocked = billingPrefs.getBoolean("KEY_PURCHASED_UNLOCK",
				false);

		String fromText = fromDateSpinner.getText().toString();
		String toText = toDateSpinner.getText().toString();

		if (configNumber2 == generalConfig) {
			Bundle totalsBundle = null;

			if (fromText.equals(defaultString) && toText.equals(defaultString)) {
				totalsBundle = mDb.getTotalExpensesAndTotalIncome();

			}

			else if (!fromText.equals(defaultString)
					&& toText.equals(defaultString)) {

				String fromDateInDb = mCC.dateForDb(fromText);

				totalsBundle = mDb
						.getTotalExpensesAndTotalIncomeAfterFromDate(fromDateInDb);
			}

			else if (fromText.equals(defaultString)
					&& !toText.equals(defaultString)) {

				String toDateInDb = mCC.dateForDb(toText);

				totalsBundle = mDb
						.getTotalExpensesAndTotalIncomeBeforeFromDate(toDateInDb);
			}

			else if (!fromText.equals(defaultString)
					&& !toText.equals(defaultString)) {

				String toDateInDb = mCC.dateForDb(toText);
				String fromDateInDb = mCC.dateForDb(fromText);

				totalsBundle = mDb.getTotalExpensesAndTotalIncomeBetweenDates(
						fromDateInDb, toDateInDb);
			}

			if (totalsBundle == null) {

				// if there is no data for graph

				Toast.makeText(ReportsViewActivity.this,
						getResources().getString(R.string.no_data),
						Toast.LENGTH_LONG).show();
			} else {

				// if data is available for graph
				GeneralReportChart grc = new GeneralReportChart(totalsBundle);
				GraphicalView gView = null;

				if (graphTypeSpinnerPosition == 0) {

					// if user wants bar graph
					gView = grc.getGeneralBarView(ReportsViewActivity.this);

					graphViewArea.addView(gView);

				}

				else if (graphTypeSpinnerPosition == 1) {
					// if user wants pie chart

					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						gView = grc
								.getGeneralPieChartView(ReportsViewActivity.this);

						graphViewArea.addView(gView);
					} else {

						showPaymentDialog(ReportsViewActivity.this);

					}

				}
			}

		}

		if (configNumber == accountConfig) {

			// if user wants accounts performance charts

			Bundle bundle = null;

			if (fromText.equals(defaultString) && toText.equals(defaultString)) {
				// if both are default

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses
					bundle = mDb.getAccountExpenseTotals();

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpenseAccounts acBarGraph = new BarGraphExpenseAccounts(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getAccountsExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart

							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getAccountsExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income
					bundle = mDb.getAccountIncomeTotals();

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeAccounts acBarGraph = new BarGraphIncomeAccounts(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getAccountsIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {

							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								// if pie chart
								gView = acBarGraph
										.getAccountsIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb.getBothAccountIncomeAndExpenseTotals();

						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {
							BarGraphIncomeAccounts acBarGraph = new BarGraphIncomeAccounts(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getAccountIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if both defaults

			if (!fromText.equals(defaultString)
					&& !toText.equals(defaultString)) {
				// if both are custom

				String fromDate = mCC.dateForDb(fromText);
				String toDate = mCC.dateForDb(toText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses between dates
					bundle = mDb.getAccountExpenseTotalsBetweenDates(fromDate,
							toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpenseAccounts acBarGraph = new BarGraphExpenseAccounts(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getAccountsExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getAccountsExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income
					bundle = mDb.getAccountIncomeTotalsBetweenDates(fromDate,
							toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeAccounts acBarGraph = new BarGraphIncomeAccounts(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getAccountsIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getAccountsIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothAccountIncomeAndExpenseTotalsBetweenDates(
										fromDate, toDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomeAccounts acBarGraph = new BarGraphIncomeAccounts(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getAccountIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if both custom

			if (!fromText.equals(defaultString) && toText.equals(defaultString)) {
				// if we want from a certain date

				String fromDate = mCC.dateForDb(fromText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses from date
					bundle = mDb.getAccountExpenseTotalsFromDate(fromDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpenseAccounts acBarGraph = new BarGraphExpenseAccounts(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getAccountsExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								// if pie chart
								gView = acBarGraph
										.getAccountsExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income
					bundle = mDb.getAccountIncomeTotalsFromDate(fromDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeAccounts acBarGraph = new BarGraphIncomeAccounts(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getAccountsIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getAccountsIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothAccountIncomeAndExpenseTotalsFromDate(fromDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomeAccounts acBarGraph = new BarGraphIncomeAccounts(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getAccountIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if FROM DATE

			if (fromText.equals(defaultString) && !toText.equals(defaultString)) {
				// if we want before a certain date

				String toDate = mCC.dateForDb(toText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses before date
					bundle = mDb.getAccountExpenseTotalsBeforeDate(toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpenseAccounts acBarGraph = new BarGraphExpenseAccounts(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getAccountsExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getAccountsExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income before date
					bundle = mDb.getAccountIncomeTotalsBeforeDate(toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeAccounts acBarGraph = new BarGraphIncomeAccounts(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getAccountsIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getAccountsIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					// before date
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothAccountIncomeAndExpenseTotalsBeforeDate(toDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomeAccounts acBarGraph = new BarGraphIncomeAccounts(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getAccountIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if TO DATE

		}// end of ACCOUNTScONFIG

		if (configNumber == categoryConfig) {

			// if user wants category performance charts

			Bundle bundle = null;

			if (fromText.equals(defaultString) && toText.equals(defaultString)) {
				// if both are default

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want CATEGORY total expenses
					bundle = mDb.getCategoryExpenseTotals();

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesCategories acBarGraph = new BarGraphExpensesCategories(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getCategoryExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getCategoryExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income
					bundle = mDb.getCategoryIncomeTotals();

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeCategories acBarGraph = new BarGraphIncomeCategories(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getCategoryIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getCategoryIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb.getBothCategoryIncomeAndExpenseTotals();

						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {
							BarGraphIncomeCategories acBarGraph = new BarGraphIncomeCategories(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getCategoryIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if both defaults

			if (!fromText.equals(defaultString)
					&& !toText.equals(defaultString)) {
				// if both are custom

				String fromDate = mCC.dateForDb(fromText);
				String toDate = mCC.dateForDb(toText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses between dates
					bundle = mDb.getCategoryExpenseTotalsBetweenDates(fromDate,
							toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesCategories acBarGraph = new BarGraphExpensesCategories(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getCategoryExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getCategoryExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want Categories total income
					bundle = mDb.getCategoryIncomeTotalsBetweenDates(fromDate,
							toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeCategories acBarGraph = new BarGraphIncomeCategories(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getCategoryIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getCategoryIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothCategoryIncomeAndExpenseTotalsBetweenDates(
										fromDate, toDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomeCategories acBarGraph = new BarGraphIncomeCategories(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getCategoryIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if both custom

			if (!fromText.equals(defaultString) && toText.equals(defaultString)) {
				// if we want from a certain date

				String fromDate = mCC.dateForDb(fromText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses from date
					bundle = mDb.getCategoryExpenseTotalsFromDate(fromDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesCategories acBarGraph = new BarGraphExpensesCategories(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getCategoryExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getCategoryExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income
					bundle = mDb.getCategoryIncomeTotalsFromDate(fromDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeCategories acBarGraph = new BarGraphIncomeCategories(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getCategoryIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getCategoryIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);

							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothCategoryIncomeAndExpenseTotalsFromDate(fromDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomeCategories acBarGraph = new BarGraphIncomeCategories(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getCategoryIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if FROM DATE

			if (fromText.equals(defaultString) && !toText.equals(defaultString)) {
				// if we want before a certain date

				String toDate = mCC.dateForDb(toText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses before date
					bundle = mDb.getCategoryExpenseTotalsBeforeDate(toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesCategories acBarGraph = new BarGraphExpensesCategories(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getCategoryExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getCategoryExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income before date
					bundle = mDb.getCategoryIncomeTotalsBeforeDate(toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeCategories acBarGraph = new BarGraphIncomeCategories(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getCategoryIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getCategoryIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					// before date
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothCategoryIncomeAndExpenseTotalsBeforeDate(toDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomeCategories acBarGraph = new BarGraphIncomeCategories(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getCategoryIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if TO DATE

		}// END OF IF categoryConfig
			// *******************************************************************
		if (configNumber == payeeConfig) {

			// if user wants payee performance charts

			Bundle bundle = null;

			if (fromText.equals(defaultString) && toText.equals(defaultString)) {
				// if both are default

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want PAYEE total expenses
					bundle = mDb.getPayeeExpenseTotals();

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesPayee acBarGraph = new BarGraphExpensesPayee(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getPayeesExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								// if pie chart
								gView = acBarGraph
										.getPayeesExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income
					bundle = mDb.getPayeeIncomeTotals();

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomePayee acBarGraph = new BarGraphIncomePayee(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getPayeesIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getPayeesIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb.getBothPayeeIncomeAndExpenseTotals();

						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {
							BarGraphIncomePayee acBarGraph = new BarGraphIncomePayee(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getPayeeIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if both defaults

			if (!fromText.equals(defaultString)
					&& !toText.equals(defaultString)) {
				// if both are custom

				String fromDate = mCC.dateForDb(fromText);
				String toDate = mCC.dateForDb(toText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses between dates
					bundle = mDb.getPayeeExpenseTotalsBetweenDates(fromDate,
							toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesPayee acBarGraph = new BarGraphExpensesPayee(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getPayeesExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getPayeesExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want Categories total income
					bundle = mDb.getPayeeIncomeTotalsBetweenDates(fromDate,
							toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomePayee acBarGraph = new BarGraphIncomePayee(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getPayeesIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getPayeesIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothPayeeIncomeAndExpenseTotalsBetweenDates(
										fromDate, toDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomePayee acBarGraph = new BarGraphIncomePayee(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getPayeeIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if both custom

			if (!fromText.equals(defaultString) && toText.equals(defaultString)) {
				// if we want from a certain date

				String fromDate = mCC.dateForDb(fromText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses from date
					bundle = mDb.getPayeeExpenseTotalsFromDate(fromDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesPayee acBarGraph = new BarGraphExpensesPayee(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getPayeesExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getPayeesExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income
					bundle = mDb.getPayeeIncomeTotalsFromDate(fromDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomePayee acBarGraph = new BarGraphIncomePayee(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getPayeesIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getPayeesIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothPayeeIncomeAndExpenseTotalsFromDate(fromDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomePayee acBarGraph = new BarGraphIncomePayee(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getPayeeIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if FROM DATE

			if (fromText.equals(defaultString) && !toText.equals(defaultString)) {
				// if we want before a certain date

				String toDate = mCC.dateForDb(toText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses before date
					bundle = mDb.getPayeeExpenseTotalsBeforeDate(toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesPayee acBarGraph = new BarGraphExpensesPayee(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getPayeesExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getPayeesExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income before date
					bundle = mDb.getPayeeIncomeTotalsBeforeDate(toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomePayee acBarGraph = new BarGraphIncomePayee(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getPayeesIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getPayeesIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					// before date
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothPayeeIncomeAndExpenseTotalsBeforeDate(toDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomePayee acBarGraph = new BarGraphIncomePayee(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getPayeeIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if TO DATE

		}// END OF IF payeeConfig

		// ****************************************************************

		if (configNumber == projectConfig) {

			// if user wants projects performance charts

			Bundle bundle = null;

			if (fromText.equals(defaultString) && toText.equals(defaultString)) {
				// if both are default

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want PROJECT total expenses
					bundle = mDb.getProjectExpenseTotals();

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesProject acBarGraph = new BarGraphExpensesProject(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getProjectsExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getProjectsExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income
					bundle = mDb.getProjectIncomeTotals();

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeProject acBarGraph = new BarGraphIncomeProject(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getProjectsIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getProjectsIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb.getBothProjectIncomeAndExpenseTotals();

						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {
							BarGraphIncomeProject acBarGraph = new BarGraphIncomeProject(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getProjectIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if both defaults

			if (!fromText.equals(defaultString)
					&& !toText.equals(defaultString)) {
				// if both are custom

				String fromDate = mCC.dateForDb(fromText);
				String toDate = mCC.dateForDb(toText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses between dates
					bundle = mDb.getProjectExpenseTotalsBetweenDates(fromDate,
							toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesProject acBarGraph = new BarGraphExpensesProject(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getProjectsExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getProjectsExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want Categories total income
					bundle = mDb.getProjectIncomeTotalsBetweenDates(fromDate,
							toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeProject acBarGraph = new BarGraphIncomeProject(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getProjectsIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getProjectsIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothProjectIncomeAndExpenseTotalsBetweenDates(
										fromDate, toDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomeProject acBarGraph = new BarGraphIncomeProject(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getProjectIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if both custom

			if (!fromText.equals(defaultString) && toText.equals(defaultString)) {
				// if we want from a certain date

				String fromDate = mCC.dateForDb(fromText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses from date
					bundle = mDb.getProjectExpenseTotalsFromDate(fromDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesProject acBarGraph = new BarGraphExpensesProject(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getProjectsExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getProjectsExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income
					bundle = mDb.getProjectIncomeTotalsFromDate(fromDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeProject acBarGraph = new BarGraphIncomeProject(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getProjectsIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getProjectsIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothProjectIncomeAndExpenseTotalsFromDate(fromDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomeProject acBarGraph = new BarGraphIncomeProject(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getProjectIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if FROM DATE

			if (fromText.equals(defaultString) && !toText.equals(defaultString)) {
				// if we want before a certain date

				String toDate = mCC.dateForDb(toText);

				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want account total expenses before date
					bundle = mDb.getProjectExpenseTotalsBeforeDate(toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphExpensesProject acBarGraph = new BarGraphExpensesProject(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getProjectsExpenseBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getProjectsExpensePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want account total income before date
					bundle = mDb.getProjectIncomeTotalsBeforeDate(toDate);

					if (bundle == null) {

						Toast.makeText(ReportsViewActivity.this, noData,
								Toast.LENGTH_LONG).show();

					} else {

						BarGraphIncomeProject acBarGraph = new BarGraphIncomeProject(
								bundle);

						GraphicalView gView = null;

						if (graphTypeSpinnerPosition == 0) {
							// if bar graph
							gView = acBarGraph
									.getProjectsIncomeBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}

						if (graphTypeSpinnerPosition == 1) {
							// if pie chart
							if (freePeriod == true || unlocked == true) {
								// if freeperiod is still on or guy paid
								gView = acBarGraph
										.getProjectsIncomePieChartView(ReportsViewActivity.this);

								graphViewArea.addView(gView);
							} else {
								showPaymentDialog(ReportsViewActivity.this);
							}
						}
					}
				}

				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want BOTH account total income & total expenses

					// before date
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid
						bundle = mDb
								.getBothProjectIncomeAndExpenseTotalsBeforeDate(toDate);
						if (bundle == null) {

							Toast.makeText(ReportsViewActivity.this, noData,
									Toast.LENGTH_LONG).show();

						} else {

							BarGraphIncomeProject acBarGraph = new BarGraphIncomeProject(
									bundle);

							GraphicalView gView = null;

							gView = acBarGraph
									.getProjectIncomeVsExpensesBarView(ReportsViewActivity.this);

							graphViewArea.addView(gView);
						}
					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}
				}

			}// end of if TO DATE

		}// END OF IF projectConfig

		if (configNumber >= accountPeriodConfigNumber
				&& configNumber <= projectPeriodConfigNumber) {
			// for these we will get one bundle and manipulate it according to
			// the situation

			Bundle bundle;
			bundle = mDb.getEntityPeriodBundle(configNumber,
					periodSpinnerPosition, specificEntitySpinnerId);

			if (bundle != null) {

				GraphEntityPeriod gEP = new GraphEntityPeriod(
						ReportsViewActivity.this, bundle);
				GraphicalView gView = null;
				if (expenseOrIncomeBothSpinnerPosition == 1) {
					// if we want expenses

					gView = gEP.getBarChartExpenses();

				}

				if (expenseOrIncomeBothSpinnerPosition == 0) {
					// if we want income

					gView = gEP.getBarChartIncome();

				}
				if (expenseOrIncomeBothSpinnerPosition == 2) {
					// if we want both
					if (freePeriod == true || unlocked == true) {
						// if freeperiod is still on or guy paid

						gView = gEP.getBarChartBothIncomeAndExpenses();

					} else {
						showPaymentDialog(ReportsViewActivity.this);
					}

				}

				if (gView != null) {

					graphViewArea.addView(gView);

				}

			} else {

			}

		}

	}

	private void showPaymentDialog(final Context context) {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);

		builder.setMessage(context.getResources().getString(
				R.string.payment_dialog_message)
				+ "\n\n"
				+ context.getResources().getString(R.string.unlock_all_features) + " ?");
		
		

		builder.setNegativeButton(context.getResources().getString(R.string.no),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		
		builder.setPositiveButton(context.getResources().getString(R.string.yes),
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

	private void populateSpinners() {
		// TODO Auto-generated method stub

		// populate graphtype spinner

		graphTypeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, graphTypes);

		graphTypeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		graphTypeSpinner.setAdapter(graphTypeAdapter);

		// populate periodType spinner

		periodAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, period);

		periodAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		periodSpinner.setAdapter(periodAdapter);

		// populate expense or Inme Both spinner

		expenseOrIncomeBothAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, expenseOrIncomeBoth);

		expenseOrIncomeBothAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		expenseOrIncomeBothSpinner.setAdapter(expenseOrIncomeBothAdapter);

	}

	private void configureLayoutSetup(Integer configNumber2) {
		// TODO Auto-generated method stub

		if (configNumber2 == generalConfig) {

			graphTypeSpinner.setVisibility(View.VISIBLE);
			fromDateSpinner.setVisibility(View.VISIBLE);
			toDateSpinner.setVisibility(View.VISIBLE);
			from.setVisibility(View.VISIBLE);
			to.setVisibility(View.VISIBLE);
			specificEntity.setVisibility(View.GONE);

			expenseOrIncomeBothSpinner.setVisibility(View.GONE);
			specificEntitySpinner.setVisibility(View.GONE);
			periodSpinner.setVisibility(View.GONE);
			groupBy.setVisibility(View.GONE);

		}

		if (configNumber2 >= 1 && configNumber2 <= 4) {

			graphTypeSpinner.setVisibility(View.VISIBLE);
			fromDateSpinner.setVisibility(View.VISIBLE);
			toDateSpinner.setVisibility(View.VISIBLE);
			from.setVisibility(View.VISIBLE);
			to.setVisibility(View.VISIBLE);
			specificEntity.setVisibility(View.GONE);

			expenseOrIncomeBothSpinner.setVisibility(View.VISIBLE);
			specificEntitySpinner.setVisibility(View.GONE);
			periodSpinner.setVisibility(View.GONE);
			groupBy.setVisibility(View.GONE);

		}

		if (configNumber2 >= 5 && configNumber2 <= 8) {

			graphTypeSpinner.setVisibility(View.GONE);
			fromDateSpinner.setVisibility(View.GONE);
			toDateSpinner.setVisibility(View.GONE);
			from.setVisibility(View.GONE);
			to.setVisibility(View.GONE);

			expenseOrIncomeBothSpinner.setVisibility(View.VISIBLE);
			specificEntitySpinner.setVisibility(View.VISIBLE);
			periodSpinner.setVisibility(View.VISIBLE);
			groupBy.setVisibility(View.VISIBLE);
			specificEntity.setVisibility(View.VISIBLE);

			loadSpecificEntitySpinner(configNumber2);

		}

	}

	private void loadSpecificEntitySpinner(Integer configNumber2) {
		// TODO Auto-generated method stub
		int[] to = new int[] { android.R.id.text1 };

		if (configNumber2 == accountLoaderId) {
			// if Account / Period is selected
			specificEntity.setText(accountString);

			mLoaderManager.initLoader(accountLoaderId, null,
					ReportsViewActivity.this);

			String[] from = new String[] { DbClass.KEY_ACCOUNT_NAME };

			specificEntityAdapter = new SimpleCursorAdapter(getBaseContext(),
					android.R.layout.simple_spinner_item, null, from, to, 0);

			specificEntityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			specificEntitySpinner.setAdapter(specificEntityAdapter);
		}

		if (configNumber2 == categoryLoaderId) {
			// if Category / Period is selected

			specificEntity.setText(categoryString);

			mLoaderManager.initLoader(categoryLoaderId, null,
					ReportsViewActivity.this);

			String[] from = new String[] { DbClass.KEY_CATEGORY_NAME };

			specificEntityAdapter = new SimpleCursorAdapter(getBaseContext(),
					android.R.layout.simple_spinner_item, null, from, to, 0);

			specificEntityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			specificEntitySpinner.setAdapter(specificEntityAdapter);
		}

		if (configNumber2 == payeeLoaderId) {
			// if Payee / Period is selected
			specificEntity.setText(payeeString);

			mLoaderManager.initLoader(payeeLoaderId, null,
					ReportsViewActivity.this);

			String[] from = new String[] { DbClass.KEY_PAYEE_NAME };

			specificEntityAdapter = new SimpleCursorAdapter(getBaseContext(),
					android.R.layout.simple_spinner_item, null, from, to, 0);

			specificEntityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			specificEntitySpinner.setAdapter(specificEntityAdapter);
		}

		if (configNumber2 == projectLoaderId) {
			// if Project / Period is selected
			specificEntity.setText(projectString);

			mLoaderManager.initLoader(projectLoaderId, null,
					ReportsViewActivity.this);

			String[] from = new String[] { DbClass.KEY_PROJECT_NAME };

			specificEntityAdapter = new SimpleCursorAdapter(getBaseContext(),
					android.R.layout.simple_spinner_item, null, from, to, 0);

			specificEntityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			specificEntitySpinner.setAdapter(specificEntityAdapter);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reports_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}

		if (id == R.id.action_refresh) {

			setUpGraph(configNumber);

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub

		if (arg0 == accountLoaderId) {
			return new CursorLoader(this, Uri.parse("content://"
					+ "SentayzoDbAuthority" + "/accountspinner"), null, null,
					null, null);
		}

		if (arg0 == categoryLoaderId) {

			return new CursorLoader(this, Uri.parse("content://"
					+ "SentayzoDbAuthority" + "/categories"), null, null, null,
					null);
		}

		if (arg0 == projectLoaderId) {
			return new CursorLoader(this, Uri.parse("content://"
					+ "SentayzoDbAuthority" + "/projects"), null, null, null,
					null);
		}

		if (arg0 == payeeLoaderId) {
			return new CursorLoader(this, Uri.parse("content://"
					+ "SentayzoDbAuthority" + "/payees"), null, null, null,
					null);
		}

		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub

		if (arg0.getId() == accountLoaderId) {
			specificEntityAdapter.swapCursor(arg1);
		}

		if (arg0.getId() == categoryLoaderId) {
			specificEntityAdapter.swapCursor(arg1);
		}
		if (arg0.getId() == payeeLoaderId) {
			specificEntityAdapter.swapCursor(arg1);
		}
		if (arg0.getId() == projectLoaderId) {
			specificEntityAdapter.swapCursor(arg1);
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

		if (arg0.getId() == accountLoaderId) {
			specificEntityAdapter.swapCursor(null);
		}

		if (arg0.getId() == categoryLoaderId) {
			specificEntityAdapter.swapCursor(null);
		}
		if (arg0.getId() == payeeLoaderId) {
			specificEntityAdapter.swapCursor(null);
		}
		if (arg0.getId() == projectLoaderId) {
			specificEntityAdapter.swapCursor(null);
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		final String[] items = {
				getResources().getString(R.string.default_string),
				getResources().getString(R.string.custom_date) };

		if (view == fromDateSpinner) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					ReportsViewActivity.this);
			builder.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					if (which == 0) {
						fromDateSpinner.setText(items[0]);
					}
					if (which == 1) {

						String dateTitle = getResources().getString(
								R.string.select_date);
						DatePickerDialog datePicker = new DatePickerDialog(
								ReportsViewActivity.this,
								new OnDateSetListener() {

									@Override
									public void onDateSet(DatePicker view,
											int selectedYear,
											int selectedMonth, int selectedDay) {
										// TODO Auto-generated method stub

										String dateSetString = selectedYear
												+ "-" + (selectedMonth + 1)
												+ "-" + selectedDay;

										String displayDate = mCC
												.dateForDisplay(dateSetString);

										fromDateSpinner.setText(displayDate);

									}
								}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
								c.get(Calendar.DAY_OF_MONTH));

						datePicker.setTitle(dateTitle);
						datePicker.setCancelable(false);
						datePicker.show();
					}

				}
			});

			Dialog d = builder.create();
			d.show();
		}

		if (view == toDateSpinner) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					ReportsViewActivity.this);
			builder.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					if (which == 0) {
						toDateSpinner.setText(items[0]);
					}
					if (which == 1) {

						String dateTitle = getResources().getString(
								R.string.select_date);
						DatePickerDialog datePicker = new DatePickerDialog(
								ReportsViewActivity.this,
								new OnDateSetListener() {

									@Override
									public void onDateSet(DatePicker view,
											int selectedYear,
											int selectedMonth, int selectedDay) {
										// TODO Auto-generated method stub

										String dateSetString = selectedYear
												+ "-" + (selectedMonth + 1)
												+ "-" + selectedDay;

										String displayDate = mCC
												.dateForDisplay(dateSetString);

										toDateSpinner.setText(displayDate);

									}
								}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
								c.get(Calendar.DAY_OF_MONTH));

						datePicker.setTitle(dateTitle);
						datePicker.setCancelable(false);
						datePicker.show();
					}

				}
			});

			Dialog d = builder.create();
			d.show();
		}

	}

	
	
}
