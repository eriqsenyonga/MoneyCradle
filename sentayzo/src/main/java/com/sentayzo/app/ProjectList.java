package com.sentayzo.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ProjectList extends ListFragment implements
        LoaderCallbacks<Cursor>, OnItemClickListener, OnItemLongClickListener {

    ListView projectList;
    LoaderManager mLoaderManager;
    SimpleCursorAdapter projectsAdapter;
    int projectLoaderId = 1;
    View rootView;
    SharedPreferences billingPrefs;
    Tracker t;

    public ProjectList() {
        // required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.activity_project_list, container,
                false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        t = ((ApplicationClass) getActivity().getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);

        t.setScreenName("CategoryList");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        billingPrefs = getActivity()
                .getSharedPreferences("my_billing_prefs", 0);

        projectList = getListView();

        mLoaderManager = getLoaderManager();

        mLoaderManager.initLoader(projectLoaderId, null, this);

        String[] from = new String[]{DbClass.KEY_PROJECT_NAME};
        int[] to = new int[]{android.R.id.text1};

        projectsAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, null, from, to, 0);
        projectList.setAdapter(projectsAdapter);

        projectList.setOnItemClickListener(this);
        projectList.setOnItemLongClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        inflater.inflate(R.menu.project_list, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Boolean freePeriod = billingPrefs.getBoolean("KEY_FREE_TRIAL_PERIOD",
                true);
        Boolean unlocked = billingPrefs.getBoolean("KEY_PURCHASED_UNLOCK",
                false);


        int id = item.getItemId();
        if (id == R.id.action_new_project) {

            Log.d("munda", "munca");

            if (freePeriod == true || unlocked == true) {
                Log.d("if free trial or unlocked",
                        "in if free trial or unlocked");
                newProjectDialog();
                return true;
            } else {

                // if free trial has expired and nigga hasnt paid for shit
                int numberOfProjects = new DbClass(getActivity())
                        .getNumberOfProjects();

                if (numberOfProjects < 2) {

                    Log.d("if NOT free trial or unlocked BUT LESS THAN 2",
                            "in if NOT free trial or unlocked BUT LESS THAN 2");
                    newProjectDialog();

                } else {
                    Log.d("if NOT free trial or unlocked AT ALL",
                            "in if NOT free trial or unlocked AT ALL");
                    showPaymentDialog(getActivity());

                }


            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // TODO Auto-generated method stub
        return new CursorLoader(getActivity(), Uri.parse("content://"
                + "SentayzoDbAuthority" + "/projectsAllOpen"), null, null,
                null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        // TODO Auto-generated method stub
        projectsAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
        projectsAdapter.swapCursor(null);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view, int arg2,
                                   final long prId) {
        // TODO Auto-generated method stub

        TextView temp = (TextView) view;

        final String projectName = temp.getText().toString();

        String[] items = getResources()
                .getStringArray(R.array.projectListItems);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                if (which == 0) {
                    // view info
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(
                            getActivity());
                    builder3.setTitle("Project Details");

                    LayoutInflater inflater = getActivity().getLayoutInflater();

                    View view = inflater
                            .inflate(R.layout.project_details, null);

                    TextView tvPrName = (TextView) view
                            .findViewById(R.id.tv_projectName);
                    TextView tvPrNote = (TextView) view
                            .findViewById(R.id.tv_projectNote);

                    tvPrName.setText(projectName);

                    DbClass mDb = new DbClass(getActivity());
                    String note = mDb.getProjectNote(prId);
                    tvPrNote.setText(note);

                    builder3.setView(view);
                    builder3.setPositiveButton(
                            getResources().getString(R.string.edit),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    editProject(projectName, prId);

                                }
                            });

                    builder3.setNeutralButton(
                            getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                }
                            });

                    Dialog projectDetails = builder3.create();

                    projectDetails.show();

                }

                if (which == 1) {
                    // edit project

                    editProject(projectName, prId);

                }

                if (which == 2) {
                    // end project

                    String closeDialogTitle = getResources().getString(
                            R.string.end)
                            + " " + getResources().getString(R.string.project);
                    String closeDialogMessage = getResources().getString(
                            R.string.closeProjectMessage);
                    AlertDialog.Builder closeBuilder = new AlertDialog.Builder(
                            getActivity());
                    closeBuilder.setTitle(closeDialogTitle);
                    closeBuilder.setMessage(closeDialogMessage + " \n\n"
                            + getResources().getString(R.string.proceed) + "?");

                    closeBuilder.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    // TODO
                                    // Auto-generated
                                    // method stub

                                }
                            });

                    closeBuilder.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    // TODO
                                    // Auto-generated
                                    // method stub

                                    DbClass mDbClass = new DbClass(
                                            getActivity());

                                    mDbClass.endProject(prId);

                                    mLoaderManager.restartLoader(
                                            projectLoaderId, null,
                                            ProjectList.this);

                                }
                            });

                    Dialog closeDialog = closeBuilder.create();
                    closeDialog.show();

                }

                if (which == 3) {
                    // delete
                    if (prId == 1) {

                        String toastText = getResources().getString(
                                R.string.cantDelete)
                                + " '" + projectName + "'";
                        Toast.makeText(getActivity(), toastText,
                                Toast.LENGTH_LONG).show();
                    } else {
                        String dTitle = getResources().getString(
                                R.string.delete)
                                + " "
                                + getResources().getString(R.string.project)
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
                                        DbClass mDbClass = new DbClass(
                                                getActivity());

                                        mDbClass.deleteProjectWithId(prId);

                                        mLoaderManager.restartLoader(
                                                projectLoaderId, null,
                                                ProjectList.this);

                                    }
                                });

                        builder2.setMessage(getResources().getString(
                                R.string.deleteProject)
                                + " \n\n"
                                + getResources().getString(R.string.proceed)
                                + "?");

                        Dialog deleteCatDialog = builder2.create();
                        deleteCatDialog.show();
                    }
                }

            }

        });

        Dialog options = builder.create();
        options.show();

        return true;
    }

    protected void editProject(String projectName, final Long prId) {
        // TODO Auto-generated method stub
        String editProjectTitle = getResources().getString(R.string.edit) + " "
                + getResources().getString(R.string.project);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        String cancel = getResources().getString(R.string.cancel);
        String save = getResources().getString(R.string.save);
        View newProjectDialog = inflater.inflate(R.layout.new_project_dialog,
                null);

        final EditText etPrName;
        final EditText etPrNote;
        etPrName = (EditText) newProjectDialog
                .findViewById(R.id.et_project_Name);
        etPrNote = (EditText) newProjectDialog
                .findViewById(R.id.et_project_note);

        etPrName.setText(projectName);
        DbClass mDb = new DbClass(getActivity());
        String note = mDb.getProjectNote(prId);
        etPrNote.setText(note);

        AlertDialog.Builder prBuilder = new AlertDialog.Builder(getActivity());
        prBuilder.setTitle(editProjectTitle);
        prBuilder.setView(newProjectDialog);
        prBuilder.setNegativeButton(cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });

        prBuilder.setPositiveButton(save,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        if (etPrName.getText().length() <= 0) {

                            String toastText = getResources().getString(
                                    R.string.project_name)
                                    + " "
                                    + getResources().getString(
                                    R.string.cantBeEmpty);

                            Toast.makeText(getActivity(), toastText,
                                    Toast.LENGTH_LONG).show();

                        }// end if
                        else {

                            String prName = etPrName.getText().toString();
                            String prNote = etPrNote.getText().toString();

                            DbClass mDb = new DbClass(getActivity());
                            mDb.open();
                            mDb.updateProject(prName, prNote, prId);
                            mDb.close();

                            mLoaderManager.restartLoader(projectLoaderId, null,
                                    ProjectList.this);

                        }// end else

                    }
                });

        Dialog newPrDialog = prBuilder.create();
        newPrDialog.show();

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        // TODO Auto-generated method stub

        TextView temp = (TextView) v;
        String prName = temp.getText().toString();
        long prId = id;

        Intent i = new Intent(getActivity(), OverviewActivity.class);


        i.putExtra("Id", prId);
        i.putExtra("whichOverview", OverviewActivity.KEY_PROJECT_OVERVIEW);

        startActivity(i);

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

    private void newProjectDialog() {

        String newProjectTitle = getResources().getString(R.string.newPrTitle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        String cancel = getResources().getString(R.string.cancel);
        String save = getResources().getString(R.string.save);
        View newProjectDialog = inflater.inflate(R.layout.new_project_dialog,
                null);

        final EditText etPrName;
        final EditText etPrNote;
        etPrName = (EditText) newProjectDialog
                .findViewById(R.id.et_project_Name);
        etPrNote = (EditText) newProjectDialog
                .findViewById(R.id.et_project_note);

        AlertDialog.Builder prBuilder = new AlertDialog.Builder(getActivity());
        prBuilder.setTitle(newProjectTitle);
        prBuilder.setView(newProjectDialog);
        prBuilder.setNegativeButton(cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });

        prBuilder.setPositiveButton(save,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        if (etPrName.getText().length() <= 0) {

                            String toastText = getResources().getString(
                                    R.string.project_name)
                                    + " "
                                    + getResources().getString(
                                    R.string.cantBeEmpty);

                            Toast.makeText(getActivity(), toastText,
                                    Toast.LENGTH_LONG).show();

                        }// end if
                        else {
                            int open = 1;// 1 symbolizes that the project is
                            // running
                            String prName = etPrName.getText().toString();
                            String prNote = etPrNote.getText().toString();

                            DbClass mDb = new DbClass(getActivity());
                            mDb.open();
                            mDb.insertNewProject(prName, prNote, open);
                            mDb.close();

                            mLoaderManager.restartLoader(projectLoaderId, null,
                                    ProjectList.this);

                        }// end else

                    }
                });

        Dialog newPrDialog = prBuilder.create();
        newPrDialog.show();

    }
}
