package com.sentayzo.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class FinancialStatement extends AppCompatActivity {

	TextView tvTotInc, tvTotExp, tvDefOrSup, tvDiff, tvConclusion, tvTotAssets,
			tvTotLiabilities, tvTotCash, tvTotBank, tvTotTotal, tvConclusion2;

	String deficit;
	Tracker t;
	String surplus;
	String conclusionNegative;
	String conclusionPositive;

	String debtString;
	String noDebt;
	ConversionClass mCC;
	Toolbar toolBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_financial_statement);
		
		toolBar = (Toolbar) findViewById(R.id.app_bar);
		setSupportActionBar(toolBar);

		t = ((ApplicationClass) getApplication())
				.getTracker(ApplicationClass.TrackerName.APP_TRACKER);

		t.setScreenName("FinancialStatement");
		t.send(new HitBuilders.ScreenViewBuilder().build());

		mCC = new ConversionClass(this);

		getSupportActionBar().setTitle(
				getResources().getString(R.string.full_report));

		conclusionNegative = getResources().getString(
				R.string.conclusion_negative);
		conclusionPositive = getResources().getString(
				R.string.conclusion_positive);
		surplus = getResources().getString(R.string.sup);
		deficit = getResources().getString(R.string.deficit);
		debtString = getResources().getString(R.string.debt_string);
		noDebt = getResources().getString(R.string.no_debt);

		tvTotInc = (TextView) findViewById(R.id.tv_totInc);
		tvTotExp = (TextView) findViewById(R.id.tv_totExp);
		tvDefOrSup = (TextView) findViewById(R.id.tv_DefOrSup);
		tvDiff = (TextView) findViewById(R.id.tv_diff);
		tvConclusion = (TextView) findViewById(R.id.tv_conclusion);
		tvTotAssets = (TextView) findViewById(R.id.tv_totAssets);
		tvTotLiabilities = (TextView) findViewById(R.id.tv_totLiabilities);
		tvTotCash = (TextView) findViewById(R.id.tv_totCash);
		tvTotBank = (TextView) findViewById(R.id.tv_totBank);
		tvTotTotal = (TextView) findViewById(R.id.tv_totTotal);
		tvConclusion2 = (TextView) findViewById(R.id.tv_conclusion2);

		Bundle bundle = getValues();

		tvTotInc.setText(mCC.valueConverter(bundle.getLong("totInc")));
		tvTotExp.setText(mCC.valueConverter(bundle.getLong("totExp")));

		if (bundle.getLong("totInc") >= bundle.getLong("totExp")) {
			tvDefOrSup.setText(surplus);
			tvDiff.setText(mCC.valueConverter(bundle.getLong("totInc")
					- bundle.getLong("totExp")));
			tvConclusion.setText(conclusionPositive);
			tvConclusion.setTextColor(Color.rgb(49, 144, 4));

		} else {
			tvDefOrSup.setText(deficit);
			tvDiff.setText(mCC.valueConverter(bundle.getLong("totExp")
					- bundle.getLong("totInc")));
			tvConclusion.setText(conclusionNegative);
			tvConclusion.setTextColor(Color.RED);

		}

		tvTotAssets.setText(mCC.valueConverter(bundle.getLong("totAssets")));
		tvTotLiabilities.setText(mCC.valueConverter(bundle
				.getLong("totLiabilities")));
		tvTotCash.setText(mCC.valueConverter(bundle.getLong("totCash")));
		tvTotBank.setText(mCC.valueConverter(bundle.getLong("totBank")));

		Long total = bundle.getLong("tot");

		tvTotTotal.setText(mCC.valueConverter(total));

		if (total >= 0) {
			tvConclusion2.setText(noDebt);
			tvConclusion2.setTextColor(Color.rgb(49, 144, 4));

		} else {
			tvConclusion2.setText(debtString);
			tvConclusion2.setTextColor(Color.RED);
		}

	}

	

	private Bundle getValues() {
		// TODO Auto-generated method stub

		DbClass mDbClass = new DbClass(this);
		mDbClass.open();
		Bundle bundle = mDbClass.getInfoForReport();
		mDbClass.close();
		return bundle;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.financial_statement, menu);
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
		return super.onOptionsItemSelected(item);
	}

}
