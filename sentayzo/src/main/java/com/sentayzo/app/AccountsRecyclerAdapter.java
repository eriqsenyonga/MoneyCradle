package com.sentayzo.app;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Eric on 3/10/2017.
 */

public class AccountsRecyclerAdapter extends CursorRecyclerAdapter<AccountsRecyclerAdapter.ViewHolder> {


    CustomItemClickListener listener;


    public AccountsRecyclerAdapter(Cursor c, CustomItemClickListener listener) {
        super(c);

        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.bindData(cursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View singleRowLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_account_row_new, parent, false);
        final AccountsRecyclerAdapter.ViewHolder viewHolder = new AccountsRecyclerAdapter.ViewHolder(singleRowLayout, parent.getContext());

        singleRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition(), viewHolder.getItemId());
            }
        });

        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView accountTypeImage;
        TextView tv_accountTypeName;
        TextView tv_accountName;
        TextView tv_accountBalance;
        TextView tv_creditLimit;
        ConversionClass mCC;
        Typeface robotoMedium;
        Typeface robotoThin;
        Context context;

        public ViewHolder(View itemView, Context c) {
            super(itemView);

            context = c;

            accountTypeImage = (ImageView) itemView
                    .findViewById(R.id.account_type_icon);
            tv_accountTypeName = (TextView) itemView
                    .findViewById(R.id.ac_type);
            tv_accountName = (TextView) itemView
                    .findViewById(R.id.ac_name);
            tv_accountBalance = (TextView) itemView
                    .findViewById(R.id.ac_balance);
            tv_creditLimit = (TextView) itemView.findViewById(R.id.tv_creditLimit);

            robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
            robotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");

            mCC = new ConversionClass(context);

            if (Build.VERSION.SDK_INT < 23) {
                //if api below 23

                tv_accountTypeName.setTypeface(robotoMedium);
                tv_accountTypeName.setTextAppearance(context, R.style.normalText);
                tv_accountName.setTypeface(robotoMedium);
                tv_accountName.setTextAppearance(context, R.style.boldText);
                tv_accountBalance.setTypeface(robotoThin);
                tv_accountBalance.setTextAppearance(context, R.style.boldText);
                tv_creditLimit.setTypeface(robotoMedium);
                tv_creditLimit.setTextAppearance(context, R.style.boldText);
            } else {
                //if api above 23
                tv_accountTypeName.setTypeface(robotoMedium);
                tv_accountTypeName.setTextAppearance(R.style.normalText);
                tv_accountName.setTypeface(robotoMedium);
                tv_accountName.setTextAppearance(R.style.boldText);
                tv_accountBalance.setTypeface(robotoThin);
                tv_accountBalance.setTextAppearance(R.style.boldText);
                tv_creditLimit.setTypeface(robotoMedium);
                tv_creditLimit.setTextAppearance(R.style.boldText);
            }
        }

        public void bindData(final Cursor cursor) {

            String accountTypeName = cursor.getString(cursor
                    .getColumnIndex(DbClass.DATABASE_TABLE_ACCOUNT_TYPE + "."
                            + DbClass.KEY_ACCOUNT_TYPE_NAME));

            String accountName = cursor.getString(cursor
                    .getColumnIndex(DbClass.KEY_ACCOUNT_NAME));

            Long amount = cursor.getLong(cursor.getColumnIndex("acTotal"));

            String amountString = mCC.valueConverter(amount);

            tv_accountTypeName.setText(accountTypeName);

            if (accountTypeName.equals("Cash")) {

                accountTypeImage.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_cash_coins));

                tv_creditLimit.setVisibility(View.GONE);
            } else if (accountTypeName.equals("Bank")) {

                accountTypeImage.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_bank_icon_6));

                tv_creditLimit.setVisibility(View.GONE);
            } else if (accountTypeName.equals("Asset")) {

                accountTypeImage.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_asset));
                tv_creditLimit.setVisibility(View.GONE);
            } else if (accountTypeName.equals("Liability")) {

                accountTypeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_liability));


                tv_creditLimit.setVisibility(View.GONE);
            } else if (accountTypeName.equals("Credit Card")) {

                String cardIssuer = cursor.getString(cursor
                        .getColumnIndex(DbClass.KEY_CARD_ISSUER_NAME));

                String creditProvider = cursor.getString(cursor
                        .getColumnIndex(DbClass.KEY_ACCOUNT_CREDIT_PROVIDER));

                Long creditLimit = cursor.getLong(cursor.getColumnIndex(DbClass.KEY_ACCOUNT_CREDIT_LIMIT));

                String limitAmountString = mCC.valueConverter(creditLimit);

                tv_creditLimit.setVisibility(View.VISIBLE);

                tv_creditLimit.setText(context.getString(R.string.credLimit) + limitAmountString);  //set credit limit

                if (creditLimit == (amount * (-1))) {

                    tv_creditLimit.setText(R.string.maxed_out_status);
                }

                if (creditProvider == "") {

                    tv_accountTypeName.setText(accountTypeName);

                } else {

                    tv_accountTypeName.setText(creditProvider);

                }


                if (cardIssuer.equals("Visa")) {

                    accountTypeImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_card_visa, null));
                }

                if (cardIssuer.equals("MasterCard")) {

                    accountTypeImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_mastercard, null));
                }

                if (cardIssuer.equals("American Express")) {

                    accountTypeImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_card_amex, null));
                }

                if (cardIssuer.equals("Discover")) {

                    accountTypeImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_card_discover, null));
                }

                if (cardIssuer.equals("Diners Club")) {

                    accountTypeImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_card_diners_club, null));
                }

                if (cardIssuer.equals("Union Pay")) {

                    accountTypeImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_card_union_pay, null));
                }

                if (cardIssuer.equals("JCB")) {

                    accountTypeImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_card_jcb, null));
                }

                if (cardIssuer.equals("Maestro")) {

                    accountTypeImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_card_maestro, null));
                }

                if (cardIssuer.equals("Other")) {

                    accountTypeImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_card_visa, null));
                }

            }


            tv_accountName.setText(accountName);

            tv_accountBalance.setText(amountString);

            if (amount < 0) {

                tv_accountBalance.setTextColor(Color.RED);

            }

            if (amount > 0) {

                tv_accountBalance.setTextColor(Color.rgb(49, 144, 4));
            }


        }
    }
}
