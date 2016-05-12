package com.sentayzo.app;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultXAxisValueFormatter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestChartFragment extends Fragment {

    View rootView;
    LineChart chart;


    public TestChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_test_chart, container, false);

        chart = (LineChart) rootView.findViewById(R.id.linechart);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        //    xAxis.setValueFormatter(new DefaultXAxisValueFormatter());

        YAxis yAxis = chart.getAxisLeft();
        //   yAxis.setTypeface(...); // set a different font
        yAxis.setTextSize(12f); // set the textsize
        yAxis.setAxisMaxValue(150f); // the axis maximum is 100
        yAxis.setAxisMinValue(50f);
        yAxis.setTextColor(Color.BLACK);
        //   yAxis.setValueFormatter(new MyValueFormatter());
        yAxis.setLabelCount(6, false);

/*this arrayList<Entry> is the container that holds values of the yaxis
 * as in you add each entry to this arraylist */
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<Entry> valsComp2 = new ArrayList<Entry>();



        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        Entry c1e3 = new Entry(75.000f,2); //2 == quarter 3 ...
        valsComp1.add(c1e3);
        Entry c1e4 = new Entry(80.000f, 3); //3 == quarter 4
        valsComp1.add(c1e4);
        // and so on ...

        Entry c2e1 = new Entry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        Entry c2e3 = new Entry(100.000f, 2);
        valsComp2.add(c2e3);
        Entry c2e4 = new Entry(130.000f, 3);
        valsComp2.add(c2e4);

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(Color.RED);

        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp2.setColor(Color.GREEN);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q"); xVals.add("2.Q"); xVals.add("3.Q"); xVals.add("4.Q");

        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);
        chart.invalidate(); // refresh

    }
}
