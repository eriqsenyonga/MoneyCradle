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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class CategoryList extends ListFragment implements
        LoaderCallbacks<Cursor>, OnItemLongClickListener, OnItemClickListener {

    ListView catList;

    SimpleCursorAdapter catAdapter;
    LoaderManager loaderManager;
    int catLoaderId = 9;

    DbClass mDbClass;
    View rootView;
    Tracker t;

    FloatingActionsMenu fam;
    FloatingActionButton fab;

    public CategoryList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.activity_category_list, container,
                false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        t = ((ApplicationClass) getActivity().getApplication()).getTracker(ApplicationClass.TrackerName.APP_TRACKER);

        t.setScreenName("CategoryList");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        mDbClass = new DbClass(getActivity());

        catList = getListView();

        loaderManager = getLoaderManager();

        loaderManager.initLoader(catLoaderId, null, this);

        String[] from1 = new String[]{DbClass.KEY_CATEGORY_NAME};

        int[] to1 = new int[]{android.R.id.text1};

        catAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, null, from1, to1, 0);

        catList.setAdapter(catAdapter);

        catList.setOnItemLongClickListener(this);

        catList.setOnItemClickListener(this);

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


                LayoutInflater inflater = getActivity().getLayoutInflater();
                View newCatView = inflater.inflate(R.layout.new_category_dialog, null);

                final EditText catEt = (EditText) newCatView.findViewById(R.id.et_cat_Name);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.newCatTitle));

                builder.setView(newCatView);

                builder.setNegativeButton(
                        getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub

                            }
                        });

                builder.setPositiveButton(getResources().getString(R.string.save),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub
                                String catName = catEt.getText().toString();

                                if (catEt.getText().length() <= 0) {

                                    Toast.makeText(
                                            getActivity(),
                                            getResources().getString(
                                                    R.string.category_name)
                                                    + " "
                                                    + getResources().getString(
                                                    R.string.cantBeEmpty),
                                            Toast.LENGTH_LONG).show();

                                } else {

                                    DbClass mDbClasss = new DbClass(getActivity());
                                    mDbClasss.open();

                                    mDbClasss.addNewCat(catEt.getText().toString());

                                    mDbClasss.close();

                                    loaderManager.restartLoader(catLoaderId, null,
                                            CategoryList.this);

                                    Toast.makeText(getActivity(),
                                            "Category: " + catName + " added ",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                Dialog newCatDialog = builder.create();
                newCatDialog.show();
            }
        });

    }





    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // TODO Auto-generated method stub

        return new CursorLoader(getActivity(), Uri.parse("content://"
                + "SentayzoDbAuthority" + "/categories"), null, null, null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        // TODO Auto-generated method stub
        catAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
        catAdapter.swapCursor(null);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, final View arg1,
                                   int position, final long id) {
        // TODO Auto-generated method stub
        String[] items = getResources().getStringArray(
                R.array.categoryListItems);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int position) {
                // TODO Auto-generated method stub

                if (position == 0) {
                    // if 'Rename' is clicked
                    if (id == 1) {

                        Toast.makeText(getActivity(),
                                "Can't rename 'Undefined' ", Toast.LENGTH_LONG)
                                .show();
                    } else {

                        mDbClass.open();
                        String catName = mDbClass.getCategoryNameAtId(id);
                        mDbClass.close();

                        LayoutInflater inflater = getActivity()
                                .getLayoutInflater();
                        View v = inflater.inflate(R.layout.new_category_dialog,
                                null);

                        final EditText catEt = (EditText) v
                                .findViewById(R.id.et_cat_Name);

                        catEt.setText(catName);

                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                getActivity());
                        builder.setTitle(getResources()
                                .getString(R.string.edit)
                                + " "
                                + getResources().getString(R.string.category));

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
                                        String catName = catEt.getText()
                                                .toString();

                                        if (catEt.getText().length() <= 0) {

                                            Toast.makeText(
                                                    getActivity(),
                                                    getResources()
                                                            .getString(
                                                                    R.string.category_name)
                                                            + " "
                                                            + getResources()
                                                            .getString(
                                                                    R.string.cantBeEmpty),
                                                    Toast.LENGTH_LONG).show();

                                        } else {

                                            DbClass mDbClasss = new DbClass(
                                                    getActivity());
                                            mDbClasss.open();

                                            mDbClasss.updateCat(catName, id);

                                            mDbClasss.close();

                                            loaderManager.restartLoader(
                                                    catLoaderId, null,
                                                    CategoryList.this);

                                            Toast.makeText(
                                                    getActivity(),
                                                    getResources().getString(
                                                            R.string.category1)
                                                            + " "
                                                            + catName
                                                            + getResources()
                                                            .getString(
                                                                    R.string.edited),
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
                        TextView temp = (TextView) arg1;
                        String toastText = getResources().getString(
                                R.string.cantDelete)
                                + " '" + temp.getText().toString() + "'";
                        Toast.makeText(getActivity(), toastText,
                                Toast.LENGTH_LONG).show();
                    } else {
                        String dTitle = getResources().getString(
                                R.string.delete)
                                + " "
                                + getResources().getString(R.string.category)
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
                                        mDbClass.deleteCategoryWithId(id);
                                        mDbClass.close();

                                        loaderManager.restartLoader(
                                                catLoaderId, null,
                                                CategoryList.this);

                                    }
                                });

                        builder2.setMessage(getResources().getString(
                                R.string.deleteCategory)
                                + " \n\n"
                                + getResources().getString(R.string.proceed)
                                + "?");

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

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        // TODO Auto-generated method stub
        TextView temp = (TextView) v;
        String catName = temp.getText().toString();
        long catId = id;

        Intent i = new Intent(getActivity(), CategoryTxView.class);

        i.putExtra("catName", catName);
        i.putExtra("catId", catId);

        startActivity(i);

    }
}
