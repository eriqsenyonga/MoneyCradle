package com.sentayzo.app;

import org.codechimp.apprater.AppRater;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbClass = new DbClass(this);

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);

        ActionBar actionBar = getSupportActionBar();


        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_menu);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mPositionSavedPrefs = getSharedPreferences("mPositionSaved",
                Context.MODE_PRIVATE);
        posSavedEditor = mPositionSavedPrefs.edit();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        fm = getSupportFragmentManager();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
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

        // toolBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        billingPrefs = getSharedPreferences("my_billing_prefs", MODE_PRIVATE);

        billingEditor = billingPrefs.edit();

        //    fm = getSupportFragmentManager();


        // set up for interstitial ad

        billingPrefs = getSharedPreferences("my_billing_prefs", 0);

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

            int menuItemId = mPositionSavedPrefs.getInt(
                    "last_main_position", 1);

            if (menuItemId == R.id.navigation_item_home) {
                // if home is clicked

                fragment = HomeFragment.newInstance();
                title = getString(R.string.home);


            } else if (menuItemId == R.id.navigation_item_categories) {

                // if "Categories" is clicked

                fragment = new CategoryList();
                title = getString(R.string.categories);

            } else if (menuItemId == R.id.navigation_item_payees) {

                // if "Payees" is clicked

                fragment = new PayeeList();
                title = getString(R.string.payees);


            } else if (menuItemId == R.id.navigation_item_projects) {

                // if "Projects" is clicked

                fragment = new ProjectList();
                title = getString(R.string.projects_drawer);


            } else if (menuItemId == R.id.navigation_item_scheduled) {
                // if scheduled transaction is clicked
                fragment = ScheduledFragment.newInstance();
                title = getString(R.string.scheduled);


            } else if (menuItemId == R.id.navigation_item_closed_accounts) {
                // if closed accounts is clicked

                fragment = new ClosedAccountsListFragment();
                title = getString(R.string.closed_accounts);

            } else if (menuItemId == R.id.navigation_item_store) {
                // if upgrade is clicked

                fragment = new UpgradeFragment();
                title = getString(R.string.upgrade);
            }


            if (fragment != null) {

                // FragmentManager fragmentManager = getFragmentManager();

                setTitle(title);


                fm.beginTransaction()
                        .replace(R.id.flContent, fragment).commit();

            }

        }


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

        if (id == R.id.ab_newAccount) {
            // open the new account form when the new account button is clicked

            Boolean freePeriod = billingPrefs.getBoolean(
                    "KEY_FREE_TRIAL_PERIOD", true);
            Boolean unlocked = billingPrefs.getBoolean("KEY_PURCHASED_UNLOCK",
                    false);

            if (freePeriod == true || unlocked == true) {

                Intent i = new Intent(this, NewAccount.class);
                startActivity(i);
            } else if (freePeriod == false && unlocked == false) {
                // if free trial has expired and nigga hasnt paid for shit
                int numberOfAccounts = new DbClass(MainActivity.this)
                        .getNumberOfAccounts();

                if (numberOfAccounts < 2) {
                    Intent i = new Intent(this, NewAccount.class);
                    startActivity(i);

                } else {

                    showPaymentDialog(MainActivity.this);

                }

            }
        }

        if (id == R.id.ab_newTransaction) {
            // open the New Transaction Form when the new Transaction button is
            // clicked

            DbClass mDbClass = new DbClass(MainActivity.this);
            mDbClass.open();
            accountsAvailable = mDbClass.checkForAccounts();
            mDbClass.close();

            if (accountsAvailable == false) {

                String createAccountFirst = getResources().getString(
                        R.string.createAccountFirst);
                // TODO Auto-generated method stub

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                builder.setMessage(createAccountFirst);
                builder.setNeutralButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub

                            }
                        });

                Dialog d = builder.create();
                d.show();

            } else {
                Intent i = new Intent(MainActivity.this, NewTransaction.class);
                startActivity(i);
            }

        }

        if (id == R.id.action_Backup) {

            BackupDatabase mBdB = new BackupDatabase(getApplicationContext());
            mBdB.callThem(1);

        }

        if (id == R.id.action_Restore) {

            BackupDatabase mBdB = new BackupDatabase(getApplicationContext());
            mBdB.callThem(0);
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

        if (id == R.id.ab_newTranfer) {
            int numberOfAccounts = new DbClass(MainActivity.this)
                    .getNumberOfAccounts();

            if (numberOfAccounts < 2) {

                // String msg = getResources().getString(R.string.accounts_two);

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                builder.setMessage("You need to have atleast 2 accounts open to use this feature.");

                builder.setNeutralButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub

                            }
                        });

                Dialog d = builder.create();
                d.show();
            } else {

                Intent i = new Intent(this, NewTransfer.class);
                startActivity(i);
            }

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

                        Intent i = new Intent(context, StoreActivity.class);
                        startActivity(i);
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
