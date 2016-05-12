package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.sentayzo.app.util.IabHelper;
import com.sentayzo.app.util.IabResult;
import com.sentayzo.app.util.Purchase;

public class StoreActivity extends AppCompatActivity {

	String TAG = "StoreActivity";
	IabHelper mHelper;
	Toolbar toolBar;
	static final String ITEM_SKU = "android.test.purchased";

	static String SKU_REMOVE_ADS = "sku_remove_ads";

	static String SKU_UNLOCK_FEATURES = "sku_unlock_features";

	static String SKU_FULL_UNLOCK = "sku_both_unlock_and_remove_ads";

	static String SKU_LIMITED_TIME_FULL_UNLOCK;

	SharedPreferences billingPrefs;

	Intent i;

	Tracker t;

	SharedPreferences.Editor editor;

	Button removeAds, unlockFeatures, limitedTimeOffer;
	Button fullUnlock;

	LinearLayout linlay_removeAds, linlay_unlockFeatures;

	RelativeLayout linlay_fullUnlock;

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			if (result.isFailure()) {
				// Handle error
				Log.d(TAG, "failed purchase");

				return;
			} else if (purchase.getSku().equals(SKU_REMOVE_ADS)) {
				Log.d(TAG, "successful purchase");
				editor.putBoolean("KEY_PURCHASED_ADS", true).apply();

				removeAds.setEnabled(false);

				t.send(new HitBuilders.EventBuilder().setCategory("Purchase")
						.setAction("Finished").setLabel("$1.99 finished")
						.build());
			}

			else if (purchase.getSku().equals(SKU_UNLOCK_FEATURES)) {
				Log.d(TAG, "successful purchase");

				editor.putBoolean("KEY_PURCHASED_UNLOCK", true).apply();
				unlockFeatures.setEnabled(false);
				t.send(new HitBuilders.EventBuilder().setCategory("Purchase")
						.setAction("Finished").setLabel("$2.86 finished")
						.build());

			}

			else if (purchase.getSku().equals(SKU_FULL_UNLOCK)) {
				Log.d(TAG, "successful purchase");

				editor.putBoolean("KEY_PURCHASED_ADS", true);
				editor.putBoolean("KEY_PURCHASED_UNLOCK", true);
				editor.apply();
				fullUnlock.setEnabled(false);

				t.send(new HitBuilders.EventBuilder().setCategory("Purchase")
						.setAction("Finished").setLabel("$2.99 finished")
						.build());

			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);

		t = ((ApplicationClass) getApplication())
				.getTracker(ApplicationClass.TrackerName.APP_TRACKER);

		t.setScreenName("StoreActivity");
		t.send(new HitBuilders.ScreenViewBuilder().build());

		toolBar = (Toolbar) findViewById(R.id.app_bar);
		setSupportActionBar(toolBar);

		removeAds = (Button) findViewById(R.id.b_removeAds);
		unlockFeatures = (Button) findViewById(R.id.b_unlockFeatures);

		fullUnlock = (Button) findViewById(R.id.b_boths);

		linlay_removeAds = (LinearLayout) findViewById(R.id.linlay_removeAds);
		linlay_unlockFeatures = (LinearLayout) findViewById(R.id.linlay_unlockFeatures);
		linlay_fullUnlock = (RelativeLayout) findViewById(R.id.linlay_fullUnlock);

		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm9VH1PgRA++1BRkW5LmT1o9pk1D2n1/700fuo1L016Wg1ymwQt4hyw/GvOud0tu1s8K8nuFmy7TCv7C0k7uhF+CuMFG1e5lcFE5pF5KqiZO8cLOPe5X6KG5iw42wvWWNVxw8n+moNZcYVeXbR5wNs6RyTfunT1TyNIY1KVUuboWId9OzNjrrnHf3GZCBAeVVbgbR3iBjuPxipJUY+YGAIUJs+uDb6HH4iXEdj3ES4+5at2lGRIT/mtpxwx7mlXQAhQxWm51y0N3ue6lOwAjhN/kMoeU/RX3ppU2E4yVef4XUp5vBHSB6Zjxp3Es7RdufM/T62kZT3IrKPJ3prnfzHQIDAQAB";

		// compute your public key and store it in base64EncodedPublicKey
		mHelper = new IabHelper(this, base64EncodedPublicKey);

		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					Log.d(TAG, "Problem setting up In-app Billing: " + result);
				} else {
					// Hooray, IAB is fully set up!
					Log.d(TAG, "Hooray, IAB is fully set up!");

					// mHelper.queryInventoryAsync(mGotInventoryListener);
				}

			}
		});

		billingPrefs = getSharedPreferences("my_billing_prefs", MODE_PRIVATE);

		editor = billingPrefs.edit();

		if (billingPrefs.getBoolean("KEY_PURCHASED_ADS", false) == true) {

			removeAds.setEnabled(false);
			fullUnlock.setEnabled(false);

		}

		if (billingPrefs.getBoolean("KEY_PURCHASED_UNLOCK", false) == true) {

			unlockFeatures.setEnabled(false);
			fullUnlock.setEnabled(false);

		}

		if (billingPrefs.getBoolean("KEY_PURCHASED_ADS", false) == true
				&& billingPrefs.getBoolean("KEY_PURCHASED_UNLOCK", false) == true) {

			removeAds.setEnabled(false);
			unlockFeatures.setEnabled(false);
			fullUnlock.setEnabled(false);

		}

		i = getIntent();

		if (i.hasExtra("Time Done")) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getResources().getString(
					R.string.free_trial_expired));
			builder.setMessage(getResources().getString(
					R.string.message_expired));

			builder.setNeutralButton(getResources().getString(R.string.ok),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});

			Dialog d = builder.create();
			d.show();

		}

		removeAds.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				t.send(new HitBuilders.EventBuilder().setCategory("Button")
						.setAction("Click").setLabel("$1.99 clicked").build());

				mHelper.launchPurchaseFlow(StoreActivity.this, SKU_REMOVE_ADS,
						10001, mPurchaseFinishedListener, "removeAdsToken");

			}
		});

		unlockFeatures.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				t.send(new HitBuilders.EventBuilder().setCategory("Button")
						.setAction("Click").setLabel("$2.86 clicked").build());

				mHelper.launchPurchaseFlow(StoreActivity.this,
						SKU_UNLOCK_FEATURES, 10002, mPurchaseFinishedListener,
						"unlockFeaturesToken");

			}
		});

		fullUnlock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				t.send(new HitBuilders.EventBuilder().setCategory("Button")
						.setAction("Click").setLabel("$2.99 clicked").build());

				mHelper.launchPurchaseFlow(StoreActivity.this, SKU_FULL_UNLOCK,
						10003, mPurchaseFinishedListener, "fullUnlockToken");

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.store, menu);
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

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null)
			mHelper.dispose();
		mHelper = null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}
