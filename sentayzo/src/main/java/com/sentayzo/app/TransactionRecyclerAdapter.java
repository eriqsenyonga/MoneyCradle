package com.sentayzo.app;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Eriq on 12/22/2015.
 */
public class TransactionRecyclerAdapter extends CursorRecyclerAdapter<TransactionRecyclerAdapter.TransactionRecyclerHolder> {

    CustomItemClickListener listener;

    public TransactionRecyclerAdapter(Cursor c, CustomItemClickListener listener) {
        super(c);

        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(TransactionRecyclerHolder holder, Cursor cursor) {
        holder.bindData(cursor);
    }

    @Override
    public TransactionRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View singleRowLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_tx_list_item, parent, false);
        final TransactionRecyclerHolder viewHolder = new TransactionRecyclerHolder(singleRowLayout, parent.getContext());

        singleRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition(), viewHolder.getItemId(), false);
            }
        });

        return viewHolder;
    }

    public static class TransactionRecyclerHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvCategory;
        TextView tvPayee;
        TextView tvAccount;
        TextView tvAmount;
        View separatorView;
        ConversionClass mCC;
        Typeface robotoMedium;
        Typeface robotoThin;


        public TransactionRecyclerHolder(View itemView, Context context) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_new_date);
            tvCategory = (TextView) itemView.findViewById(R.id.tv_new_category);
            tvPayee = (TextView) itemView.findViewById(R.id.tv_new_payee);
            tvAccount = (TextView) itemView.findViewById(R.id.tv_new_account);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_new_amount);
            separatorView = (View) itemView.findViewById(R.id.view_separator);

            robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
            robotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");

            mCC = new ConversionClass(context);

             /* Setting up fonts and appearances of the textViews */

            if (Build.VERSION.SDK_INT < 23) {
                //if api below 23
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
            } else {
                //if api above 23
                tvDate.setTypeface(robotoMedium);
                tvDate.setTextAppearance( R.style.boldText);
                tvCategory.setTypeface(robotoMedium);
                tvCategory.setTextAppearance( R.style.boldText);
                tvPayee.setTypeface(robotoMedium);
                tvPayee.setTextAppearance(R.style.normalText);
                tvAccount.setTypeface(robotoMedium);
                tvAccount.setTextAppearance( R.style.boldText);
                tvAmount.setTypeface(robotoThin);
                tvAmount.setTextAppearance(R.style.boldText);
            }


        }

        public void bindData(final Cursor cursor) {
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
}
