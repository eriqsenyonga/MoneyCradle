package com.sentayzo.app;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Eriq on 12/15/2015.
 */
public class TransactionListAdapter extends CursorAdapter {

    private LayoutInflater inflater;
    Typeface robotoMedium;
    Typeface robotoThin;
    ConversionClass mCC;

    public TransactionListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
        robotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        mCC = new ConversionClass(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return inflater.inflate(R.layout.new_tx_list_item, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvDate = (TextView) view.findViewById(R.id.tv_new_date);
        TextView tvCategory = (TextView) view.findViewById(R.id.tv_new_category);
        TextView tvPayee = (TextView) view.findViewById(R.id.tv_new_payee);
        TextView tvAccount = (TextView) view.findViewById(R.id.tv_new_account);
        TextView tvAmount = (TextView) view.findViewById(R.id.tv_new_amount);
        View separatorView = (View) view.findViewById(R.id.view_separator);


        /* Setting up fonts and appearances of the textViews */
        tvDate.setTypeface(robotoMedium);
        tvDate.setTextAppearance(context, R.style.boldText);
        tvCategory.setTypeface(robotoMedium);
        tvCategory.setTextAppearance(context, R.style.boldText);
        tvPayee.setTypeface(robotoMedium);
        tvPayee.setTextAppearance(context, R.style.normalText);
        tvAccount.setTypeface(robotoMedium);
        tvAccount.setTextAppearance(context, R.style.boldText);
        tvAmount.setTypeface(robotoThin);
        tvAmount.setTextAppearance(context, R.style.boldText);


        /*Date*/
        String dateFromDb = cursor.getString(cursor.getColumnIndex(DbClass.KEY_TRANSACTION_DATE));
        String dateForDisp = mCC.dateForDisplayNew(dateFromDb);
        tvDate.setText(dateForDisp);

        /*Category*/
        String category = cursor.getString(cursor.getColumnIndex(DbClass.DATABASE_TABLE_CATEGORY + "."
                + DbClass.KEY_CATEGORY_NAME));
        tvCategory.setText(category);

        /*Payee*/
        String payee = cursor.getString(cursor.getColumnIndex(DbClass.DATABASE_TABLE_PAYEE + "." + DbClass.KEY_PAYEE_NAME));
        tvPayee.setText(payee);

        /*Account*/
        String account = cursor.getString(cursor.getColumnIndex(DbClass.DATABASE_TABLE_ACCOUNT + "." + DbClass.KEY_ACCOUNT_NAME));
        tvAccount.setText(account);

        /*Amount*/
        Long amount = cursor.getLong(cursor.getColumnIndex(DbClass.KEY_TRANSACTION_AMOUNT));
        String amountText = mCC.valueConverter(amount);

        tvAmount.setText(amountText);

        if (amount < 0) {
            tvAmount.setTextColor(Color.RED);
            separatorView.setBackgroundColor(Color.RED);

        }

        if (amount > 0) {
            tvAmount.setTextColor(Color.rgb(49, 144, 4));
            separatorView.setBackgroundColor(Color.rgb(2, 139, 23));
            //separatorView.back
        }

        if (amount == 0) {
            tvAmount.setTextColor(Color.BLACK);
            separatorView.setBackgroundColor(Color.GRAY);
        }

    }
}
