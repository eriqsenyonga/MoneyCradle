package com.sentayzo.app;

import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;

public class BarGraphIncomeAccounts {

	Bundle dataBundle;
	float textSize;
	ConversionClass mCC;

	public BarGraphIncomeAccounts(Bundle bundle) {
		// TODO Auto-generated constructor stub

		dataBundle = bundle;

	}

	public GraphicalView getAccountsIncomeBarView(Context context) {

		textSize = context.getResources().getDimension(R.dimen.graph_text_size);
		mCC = new ConversionClass(context);

		double[] y = dataBundle.getDoubleArray("totAmount");
		String[] x = dataBundle.getStringArray("accountName");

		CategorySeries series = new CategorySeries("Accounts Total Income");

		for (int i = 0; i < y.length; i++) {

			series.add(x[i], y[i]);

		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(context.getResources().getColor(R.color.graph_green));
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesTextAlign(Align.CENTER);
		renderer.setShowLegendItem(true);
		renderer.setChartValuesFormat(mCC.getNumberFormat());
		renderer.setChartValuesTextSize(textSize);

		/*
		 * XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		 * renderer2.setColor(Color.CYAN);
		 */

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

		mRenderer.addSeriesRenderer(renderer);
		/* mRenderer.addSeriesRenderer(renderer2); */

		mRenderer.setChartTitle("TOTAL INCOME PER ACCOUNT");
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setChartTitleTextSize(textSize);
		mRenderer.setYAxisMin(100);
		mRenderer.setXTitle("\n\n\nAccounts");
		mRenderer.setYTitle("Amount of Money");
		mRenderer.setBarSpacing(0.2);

		mRenderer.setXAxisMax(x.length + 0.8);
		mRenderer.setXAxisMin(-0.2);
		mRenderer.setLabelsTextSize(textSize);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.WHITE);
		mRenderer.setXLabels(0);
		mRenderer.setAxisTitleTextSize(textSize);

		mRenderer.setScale(1);
		mRenderer.setPanEnabled(true, true);

		mRenderer.setInScroll(true);
		mRenderer.setXLabelsAngle(-60);
		mRenderer.setXLabelsAlign(Align.CENTER);
		mRenderer.setMargins(new int[] { 50, 30, 150, 0 });
		mRenderer.setXLabelsColor(Color.BLUE);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setMarginsColor(Color.WHITE);
		mRenderer.setFitLegend(true);
		// mRenderer.setBarWidth(100);
		mRenderer.setLegendTextSize(textSize);
		mRenderer.setAxesColor(Color.BLACK);

		for (int h = 0; h < x.length; h++) {
			mRenderer.addXTextLabel(h + 1, x[h]);
		}

		return ChartFactory.getBarChartView(context, dataset, mRenderer,
				Type.DEFAULT);

	}

	public GraphicalView getAccountIncomeVsExpensesBarView(Context context) {

		textSize = context.getResources().getDimension(R.dimen.graph_text_size);
		mCC = new ConversionClass(context);

		double[] totalIncome = dataBundle.getDoubleArray("totIncome");
		double[] totalExpense = dataBundle.getDoubleArray("totExpense");
		String[] accountNames = dataBundle.getStringArray("accountNames");

		int i = 0;

		CategorySeries totIncomeSeries = new CategorySeries("Total Income");

		for (double totIncome : totalIncome) {

			totIncomeSeries.add(accountNames[i], totIncome);
			i = i + 1;

		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(totIncomeSeries.toXYSeries());

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(context.getResources().getColor(R.color.graph_green));
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesTextAlign(Align.LEFT);
		renderer.setShowLegendItem(true);
		renderer.setChartValuesFormat(mCC.getNumberFormat());
		renderer.setChartValuesTextSize(textSize);

		int k = 0;

		CategorySeries totExpenseSeries = new CategorySeries("Total Expenses");

		for (double totExpense : totalExpense) {

			totExpenseSeries.add(accountNames[k], totExpense);
			k = k + 1;

		}

		dataset.addSeries(totExpenseSeries.toXYSeries());

		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor(context.getResources().getColor(R.color.graph_red));
		renderer2.setDisplayChartValues(true);
		renderer2.setChartValuesTextAlign(Align.RIGHT);
		renderer2.setShowLegendItem(true);
		renderer2.setChartValuesFormat(mCC.getNumberFormat());
		renderer2.setChartValuesTextSize(textSize);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

		mRenderer.addSeriesRenderer(renderer);
		mRenderer.addSeriesRenderer(renderer2);

		mRenderer.setChartTitle("INCOME VS EXPENSES");
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setChartTitleTextSize(textSize);

		mRenderer.setYAxisMin(100);
		mRenderer.setXTitle("\n\n\nAccounts");
		mRenderer.setYTitle("Amount of Money");
		mRenderer.setBarSpacing(0.2);
		mRenderer.setXAxisMax(accountNames.length + 0.8);
		mRenderer.setXAxisMin(0.2);
		mRenderer.setLabelsTextSize(textSize);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.WHITE);
		mRenderer.setXLabels(0);
		mRenderer.setAxisTitleTextSize(textSize);
		mRenderer.setAxesColor(Color.BLACK);
		mRenderer.setScale(2);
		mRenderer.setPanEnabled(true, true);
		mRenderer.setInScroll(true);
		mRenderer.setXLabelsAngle(-60);
		mRenderer.setXLabelsAlign(Align.CENTER);
		mRenderer.setMargins(new int[] { 50, 30, 150, 0 });
		mRenderer.setXLabelsColor(Color.BLUE);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setMarginsColor(Color.WHITE);
		mRenderer.setFitLegend(true);
		// mRenderer.setBarWidth(100);
		mRenderer.setLabelsColor(context.getResources().getColor(
				R.color.darkish_grey));

		mRenderer.setLegendTextSize(textSize);

		for (int h = 0; h < accountNames.length; h++) {
			mRenderer.addXTextLabel(h + 1, accountNames[h]);
		}

		return ChartFactory.getBarChartView(context, dataset, mRenderer,
				Type.DEFAULT);

	}

	public GraphicalView getAccountsIncomePieChartView(Context context) {

		Log.d("achart get PIE CHART", "GET PIE CHART ACOUNTS INCOME");

		textSize = context.getResources().getDimension(R.dimen.graph_text_size);
		mCC = new ConversionClass(context);

		double[] values = dataBundle.getDoubleArray("totAmount");
		String[] x = dataBundle.getStringArray("accountName");

		CategorySeries series = new CategorySeries(
				"Accounts Total Income Pie Chart");

		int i = 0;

		for (double value : values) {

			series.add(x[i], value);
			i = i + 1;
		}

		Random random = new Random();
		
		int lengthValues = values.length;

		

		DefaultRenderer renderer = new DefaultRenderer();

		for (int t = 0; t < lengthValues; t++) {

			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));

			r.setChartValuesFormat(mCC.getNumberFormat());
			r.setShowLegendItem(true);
			renderer.addSeriesRenderer(r);
		}
		
		renderer.setDisplayValues(true);
		renderer.setShowLegend(true);
		renderer.setLegendTextSize(textSize);
		renderer.setLabelsColor(Color.BLUE);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(context.getResources().getColor(
				R.color.white));
		renderer.setFitLegend(true);
		// renderer.setDisplayValues(true);
		renderer.setInScroll(false);
		// renderer.setShowLabels(true);
		renderer.setScale(1.2f);

		Log.d("achart get PIE CHART", "END OF GET PIE CHART ACCOUNT iNCOME");

		return ChartFactory.getPieChartView(context, series, renderer);
	}

}
