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
 * Created by Eric on 3/11/2017.
 */

public class PendingTxRecyclerAdapter extends CursorRecyclerAdapter<PendingTxRecyclerAdapter.ViewHolder> {

    CustomItemClickListener listener;

    public PendingTxRecyclerAdapter(Cursor c, CustomItemClickListener listener) {
        super(c);

        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.bindData(cursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View singleRowLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_sch_tx, parent, false);
        final PendingTxRecyclerAdapter.ViewHolder viewHolder = new PendingTxRecyclerAdapter.ViewHolder(singleRowLayout, parent.getContext());

        singleRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition(), viewHolder.getItemId());
            }
        });

        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        Context context;
        TextView tvDate, tvRecur, tvAccount, tvPayee, tvAmount, tvCategory;
        ConversionClass mCC;
        Typeface robotoMedium;
        Typeface robotoThin;


        public ViewHolder(View itemView, Context c) {
            super(itemView);

            context = c;

            tvDate = (TextView) itemView.findViewById(R.id.tv_pen_tx_date);
            tvRecur = (TextView) itemView.findViewById(R.id.tv_pen_recur);
            tvAccount = (TextView) itemView.findViewById(R.id.tv_pen_tx_account);
            tvPayee = (TextView) itemView.findViewById(R.id.tv_pen_tx_payee);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_pen_tx_amount);
            tvCategory = (TextView) itemView.findViewById(R.id.tv_pen_tx_category);


            robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
            robotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");

            mCC = new ConversionClass(context);

             /* Setting up fonts and appearances of the textViews */

            if (Build.VERSION.SDK_INT < 23) {
                //if api below 23
                tvDate.setTypeface(robotoMedium);
                tvDate.setTextAppearance(context, R.style.boldText);
                tvRecur.setTypeface(robotoThin);
                tvRecur.setTextAppearance(context, R.style.boldText);
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
                tvDate.setTextAppearance(R.style.boldText);
                tvRecur.setTypeface(robotoMedium);
                tvRecur.setTextAppearance(R.style.boldText);
                tvCategory.setTypeface(robotoMedium);
                tvCategory.setTextAppearance(R.style.boldText);
                tvPayee.setTypeface(robotoMedium);
                tvPayee.setTextAppearance(R.style.normalText);
                tvAccount.setTypeface(robotoMedium);
                tvAccount.setTextAppearance(R.style.boldText);
                tvAmount.setTypeface(robotoThin);
                tvAmount.setTextAppearance(R.style.boldText);
            }
        }

        public void bindData(final Cursor cursor) {

            /*Date*/
            String dateFromDb = cursor.getString(cursor.getColumnIndex(DbClass.KEY_SCHEDULED_START_DATE));
            String dateForDisp = mCC.dateForDisplayNew(dateFromDb);
            tvDate.setText(dateForDisp);


            /*Recurrence*/
            String recurrence = cursor.getString(cursor.getColumnIndex(DbClass.DATABASE_TABLE_SCHEDULED_RECURRENCE + "."
                    + DbClass.KEY_SCHEDULED_RECURRENCE_NAME));
            tvRecur.setText(recurrence);


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
            Long amount = cursor.getLong(cursor.getColumnIndex(DbClass.KEY_SCHEDULED_AMOUNT));
            String amountText = mCC.valueConverter(amount);

            tvAmount.setText(amountText);

            if (amount < 0) {
                tvAmount.setTextColor(Color.RED);


            }

            if (amount > 0) {
                tvAmount.setTextColor(Color.rgb(49, 144, 4));

                //separatorView.back
            }

            if (amount == 0) {
                tvAmount.setTextColor(Color.BLACK);

            }
        }

    }
}
