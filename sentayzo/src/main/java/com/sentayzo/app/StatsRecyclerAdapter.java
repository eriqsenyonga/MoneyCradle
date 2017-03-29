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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Eric on 3/27/2017.
 */

public class StatsRecyclerAdapter extends CursorRecyclerAdapterWithHeader<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    Context context;
    static ConversionClass mCC;
    int whichStatCategory;


    public StatsRecyclerAdapter(Cursor c, Context ctx, int whichStatCategory) {
        super(c);

        context = ctx;
        mCC = new ConversionClass(context);
        this.whichStatCategory = whichStatCategory;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stat_header, parent, false);
            return new HeaderViewHolder(v, context, whichStatCategory);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stat_item, parent, false);
            return new ItemViewHolder(v, context);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.bindData(cursor);

        } else if (holder instanceof ItemViewHolder) {


            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.bindData(cursor);

        }

    }


    @Override
    public int getItemViewType(int position) {

        if (isPositionHeader(position)) {

            return TYPE_HEADER;

        } else {

            return TYPE_ITEM;
        }

    }

    private boolean isPositionHeader(int position) {

        if (position == 0) {

            return true;
        } else {
            return false;
        }


    }


    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        PieChart pieChart;
        Context context;
        int whichStatCategory;

        public HeaderViewHolder(View itemView, Context c, int whichStatCategory) {
            super(itemView);
            context = c;
            pieChart = (PieChart) itemView.findViewById(R.id.piechart);
            pieChart.setRotationEnabled(true);
            pieChart.setHoleRadius(40f);
            pieChart.setUsePercentValues(true);
            pieChart.setDrawEntryLabels(false);
            pieChart.setTransparentCircleRadius(45f);
            pieChart.setTransparentCircleAlpha(110);
            pieChart.setTransparentCircleColor(Color.WHITE);


            pieChart.getDescription().setEnabled(false);
            //  pieChart.setEntryLabelTypeface(robotoMedium);

            this.whichStatCategory = whichStatCategory;


        }

        public void bindData(final Cursor cursor) {

            ArrayList<PieEntry> yEntries = new ArrayList<>();
            //  ArrayList<String> xEntries = new ArrayList<>();

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                yEntries.add(
                        new PieEntry(Math.abs(mCC.valueConverterReturnFloat((cursor.getLong(cursor.getColumnIndex("totalExpense"))))),
                                cursor.getString(cursor.getColumnIndex(DbClass.KEY_CATEGORY_NAME))));


            }


            String stats = null;

            if (whichStatCategory == StatPagerAdapter.INCOME) {
                stats = context.getResources().getString(R.string.income);
            } else if (whichStatCategory == StatPagerAdapter.EXPENSES) {
                stats = context.getResources().getString(R.string.expenses);
            }

            PieDataSet dataSet = new PieDataSet(yEntries, stats);
            dataSet.setSliceSpace(2f);

            ArrayList<Integer> colors = new ArrayList<Integer>();

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);


            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);


            if (whichStatCategory == StatPagerAdapter.INCOME) {
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            } else if (whichStatCategory == StatPagerAdapter.EXPENSES) {
                dataSet.setColors(colors);
            }
            //       dataSet.setValueLinePart1OffsetPercentage(80.f);
            //       dataSet.setValueLinePart1Length(0.2f);
            //       dataSet.setValueLinePart2Length(0.4f);
            //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            //       dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            PieData pieData = new PieData(dataSet);
            pieData.setValueFormatter(new PercentFormatter());
            pieData.setValueTextSize(11f);
            pieChart.setData(pieData);
            pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
            pieChart.highlightValues(null);
            pieChart.invalidate();


        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {


        TextView tvCategory, tvAmount;
        Context context;
        Typeface robotoThin, robotoMedium;


        public ItemViewHolder(View itemView, Context c) {
            super(itemView);
            context = c;
            tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
            robotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");


        }

        public void bindData(final Cursor cursor) {

            tvCategory.setText(cursor.getString(cursor.getColumnIndex(DbClass.KEY_CATEGORY_NAME)));
            tvAmount.setText(mCC.valueConverter(cursor.getLong(cursor.getColumnIndex("totalExpense"))));

            if (Build.VERSION.SDK_INT < 23) {
                //if api below 23

                tvCategory.setTypeface(robotoMedium);
                tvCategory.setTextAppearance(context, R.style.boldText);
                tvAmount.setTypeface(robotoThin);
                tvAmount.setTextAppearance(context, R.style.normalText);

            } else {
                //if api above 23
                tvCategory.setTypeface(robotoMedium);
                tvCategory.setTextAppearance(R.style.boldText);
                tvAmount.setTypeface(robotoThin);
                tvAmount.setTextAppearance(R.style.normalText);

            }


        }
    }

}
