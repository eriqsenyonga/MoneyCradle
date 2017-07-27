package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {

    boolean accountsAvailable;
    Toolbar toolBar;
    DrawerLayout drawerLayout;
    FragmentManager fm;
    String mTitle;
    String mDrawerTitle;
    TextView button;
    DbClass mDbClass;
    SharedPreferences sharedPrefs, billingPrefs, mPositionSavedPrefs;
    SharedPreferences.Editor editor, billingEditor, posSavedEditor;
    private InterstitialAd interstitial;
    NavigationView navigationView;
ConversionClass mCC;
    FloatingActionsMenu fam;
    FloatingActionButton fabNewAccount;
    FloatingActionButton fabNewTrn;
    FloatingActionButton fabNewTransfer;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbClass = new DbClass(this);
        mCC = new ConversionClass(this);

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);

        ActionBar actionBar = getSupportActionBar();


        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_menu);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        fam = (FloatingActionsMenu) findViewById(R.id.fam_fab);
        fabNewAccount = (FloatingActionButton) findViewById(R.id.fab_newAccount);
        fabNewTrn = (FloatingActionButton) findViewById(R.id.fab_newTransaction);
        fabNewTransfer = (FloatingActionButton) findViewById(R.id.fab_newTransfer);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        mPositionSavedPrefs = getSharedPreferences("mPositionSaved",
                Context.MODE_PRIVATE);
        posSavedEditor = mPositionSavedPrefs.edit();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        fm = getSupportFragmentManager();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                int menuItemId = menuItem.getItemId();
                CharSequence title = menuItem.getTitle();

                Fragment fragment = null;

                if (menuItemId == R.id.navigation_item_home) {
                    // if home is clicked

                    fragment = HomeFragment.newInstance();


                } else if (menuItemId == R.id.navigation_item_statistics) {
                    // if home is clicked

                    fragment = new StatisticsFragment();


                } else if (menuItemId == R.id.navigation_item_categories) {

                    // if "Categories" is clicked

                    fragment = new CategoryList();

                } else if (menuItemId == R.id.navigation_item_payees) {

                    // if "Payees" is clicked

                    fragment = new PayeeList();


                } else if (menuItemId == R.id.navigation_item_projects) {

                    // if "Projects" is clicked

                    fragment = new ProjectList();


                } else if (menuItemId == R.id.navigation_item_scheduled) {
                    // if scheduled transaction is clicked
                    fragment = ScheduledFragment.newInstance();


                } else if (menuItemId == R.id.navigation_item_closed_accounts) {
                    // if closed accounts is clicked

                    fragment = new ClosedAccountsListFragment();

                } else if (menuItemId == R.id.navigation_item_store) {
                    //if store is clicked

                    // Intent mi = new Intent( MainActivity.this, Temp_activity.class);
                    //startActivity(mi);

                    fragment = new UpgradeFragment();

                } else if (menuItemId == R.id.navigation_item_make_a_suggestion) {
                    // if "send feedback is clicked"
                    sendFeedbackEmail();

                } else if (menuItemId == R.id.navigation_item_tell_a_friend) {
                    // if "tell a friend is clicked"
                    tellAFriend();

                } else if (menuItemId == R.id.navigation_item_settings) {
                    // if "settings" is clicked
                    Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(i);

                } else if (menuItemId == R.id.navigation_item_help) {
                    // if "help" is clicked
                    Intent i = new Intent(MainActivity.this, HelpActivityNew.class);
                    startActivity(i);
                }


                if (fragment != null) {

                    // FragmentManager fragmentManager = getFragmentManager();

                    setTitle(title);

                    posSavedEditor.putInt("last_main_position", menuItemId).apply();

                    fm.beginTransaction()
                            .replace(R.id.flContent, fragment).commit();

                }

                // Highlight the selected item, update the title, and close the drawer


                drawerLayout.closeDrawers();

                return true;
            }
        });

        mTitle = getResources().getString(R.string.app_name);
        mDrawerTitle = getResources().getString(R.string.app_name);


       View navHeader =  navigationView.getHeaderView(0);
        TextView navBal = (TextView) navHeader.findViewById(R.id.tv_balance);


        navBal.setText("Balance: " + mCC.valueConverter(mDbClass.totalTransactionAmount()));



        // toolBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        billingPrefs = getSharedPreferences("my_billing_prefs", MODE_PRIVATE);

        billingEditor = billingPrefs.edit();

        //    fm = getSupportFragmentManager();


        // set up for interstitial ad

        billingPrefs = getSharedPreferences("my_billing_prefs", 0);

        billingEditor.putBoolean("KEY_FREE_TRIAL_PERIOD", true).apply();
        if ((billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD", true) == false)
                && (billingPrefs.getBoolean("KEY_PURCHASED_ADS", false) == false)) {

            interstitial = new InterstitialAd(MainActivity.this);

            interstitial.setAdUnitId(getResources().getString(
                    R.string.interstitial_ad_id));

            AdRequest adRequest = new AdRequest.Builder().build();

            // Load ads into Interstitial Ads
            interstitial.loadAd(adRequest);

            Long counter = billingPrefs.getLong("KEY_COUNTER", 0);
            if (counter == 2) {
                // Prepare an Interstitial Ad Listener
                interstitial.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        // Call displayInterstitial() function
                        if (interstitial.isLoaded()) {
                            interstitial.show();
                        }
                    }
                });

                billingEditor.putLong("KEY_COUNTER", 0).commit();

            } else {

                billingEditor.putLong("KEY_COUNTER", counter + 1).commit();

            }
        }

        getSupportActionBar().setDisplayShowTitleEnabled(true);

//        AppRater.app_launched(this);


        Intent i = getIntent();

        if (i.hasExtra("zero")) {

            setTitle("Home");
            Fragment fragment = HomeFragment.newInstance();


            fm.beginTransaction()
                    .replace(R.id.flContent, fragment).commit();

            posSavedEditor.putInt("last_main_position", R.id.navigation_item_home).apply();


        } else {

            Fragment fragment = null;
            CharSequence title = null;
            int itemIndex = 0;

            int menuItemId = mPositionSavedPrefs.getInt(
                    "last_main_position", 1);

            if (menuItemId == R.id.navigation_item_home) {
                // if home is clicked

                fragment = HomeFragment.newInstance();
                title = getString(R.string.home);

                itemIndex = 0;


            } else if (menuItemId == R.id.navigation_item_statistics) {

                // if "Statistics" is clicked

                fragment = new StatisticsFragment();
                title = getString(R.string.statistics);
                itemIndex = 1;

            } else if (menuItemId == R.id.navigation_item_categories) {

                // if "Categories" is clicked

                fragment = new CategoryList();
                title = getString(R.string.categories);
                itemIndex = 3;

            } else if (menuItemId == R.id.navigation_item_payees) {

                // if "Payees" is clicked

                fragment = new PayeeList();
                title = getString(R.string.payees);
                itemIndex = 4;

            } else if (menuItemId == R.id.navigation_item_projects) {

                // if "Projects" is clicked

                fragment = new ProjectList();
                title = getString(R.string.projects_drawer);
                itemIndex = 5;


            } else if (menuItemId == R.id.navigation_item_scheduled) {
                // if scheduled transaction is clicked
                fragment = ScheduledFragment.newInstance();
                title = getString(R.string.scheduled);
                itemIndex = 2;


            } else if (menuItemId == R.id.navigation_item_closed_accounts) {
                // if closed accounts is clicked

                fragment = new ClosedAccountsListFragment();
                title = getString(R.string.closed_accounts);
                itemIndex = 6;

            } else if (menuItemId == R.id.navigation_item_store) {
                // if upgrade is clicked

                fragment = new UpgradeFragment();
                title = getString(R.string.upgrade);
                itemIndex = 7;
            }


            if (fragment != null) {

                // FragmentManager fragmentManager = getFragmentManager();

                setTitle(title);
                navigationView.getMenu().getItem(itemIndex).setChecked(true);


                fm.beginTransaction()
                        .replace(R.id.flContent, fragment).commit();

            }

        }


        //  showPaymentDialog(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call supportInvalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content
        // view

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == android.R.id.home) {

            drawerLayout.openDrawer(GravityCompat.START);

        }

        if (id == R.id.action_settings) {

            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);

            return true;

        }


        if (id == R.id.action_Backup) {

            BackupDatabase mBdB = new BackupDatabase(getApplicationContext());
            mBdB.callThem(BackupDatabase.EXPORT_DB);

        }

        if (id == R.id.action_Restore) {

            BackupDatabase mBdB = new BackupDatabase(getApplicationContext());
            mBdB.callThem(BackupDatabase.IMPORT_DB);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }
        if (id == R.id.action_Help) {
       /*     Intent i = new Intent(this, HelpActivityNew.class);
            startActivity(i);
*/
            Intent i = new Intent(this, TestOut.class);
            startActivity(i);

        }


        return super.onOptionsItemSelected(item);
    }

    private void showPaymentDialog(final Context context) {
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

                        //   Intent i = new Intent(context, StoreActivity.class);
                        //   startActivity(i);


                        setTitle(getString(R.string.upgrade));
                        navigationView.getMenu().getItem(6).setChecked(true);
                        fm.beginTransaction()
                                .replace(R.id.flContent, new UpgradeFragment()).commit();


                    }
                });

        Dialog paymentDialog = builder.create();
        paymentDialog.show();

    }


    @Override
    public void setTitle(CharSequence title) {


        getSupportActionBar().setTitle(title.toString());
    }

    private void tellAFriend() {
        // Auto-generated method stub

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app");
        shareIntent.setType("text/plain");
        shareIntent
                .putExtra(
                        Intent.EXTRA_TEXT,
                        "Hey, check out this awesome new money manager app,"
                                + "https://play.google.com/store/apps/details?id=com.sentayzo.app");

        try {
            startActivity(Intent.createChooser(shareIntent, "Tell a friend..."));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private void sendFeedbackEmail() {
        // TODO Auto-generated method stub


        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + "Money Cradle feedback"
                + "&to=" + "senyonga@gmail.com");
        testIntent.setData(data);
        startActivity(testIntent);
    }

}
