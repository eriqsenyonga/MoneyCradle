package com.sentayzo.app;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;

public class GraphEntityPeriod {

	Bundle bundle;
	Context context;
	ConversionClass mCC;
	float textSize;

	public GraphEntityPeriod(Context ctx, Bundle dataBundle) {

		context = ctx;
		bundle = dataBundle;

		mCC = new ConversionClass(context);
		textSize = context.getResources().getDimension(R.dimen.graph_text_size);

	}

	public GraphicalView getBarChartExpenses() {
		// TODO put in stuff here BarChartExpenses

		double[] y = bundle.getDoubleArray("expenseTotals");
		String[] x = bundle.getStringArray("dates");

		CategorySeries series = new CategorySeries(context.getResources()
				.getString(R.string.tot_expenses)
				+ " "
				+ context.getResources().getString(R.string.over_time));

		for (int i = 0; i < y.length; i++) {

			series.add(x[i], y[i]);

		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(context.getResources().getColor(R.color.graph_red));
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
		mRenderer.setXTitle("\n\n\nTime");
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

	public GraphicalView getBarChartIncome() {
		// TODO put in stuff here BarChartIncome

		double[] y = bundle.getDoubleArray("incomeTotals");
		String[] x = bundle.getStringArray("dates");

		CategorySeries series = new CategorySeries(context.getResources()
				.getString(R.string.tot_income)
				+ " "
				+ context.getResources().getString(R.string.over_time));

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

		mRenderer.setChartTitle("TOTAL INCOME OVER TIME");
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setChartTitleTextSize(textSize);
		mRenderer.setYAxisMin(100);
		mRenderer.setXTitle("\n\n\nTime");
		mRenderer.setYTitle("Amount of Money");
		mRenderer.setBarSpacing(0.2);
		mRenderer.setXRoundedLabels(true);

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

	public GraphicalView getBarChartBothIncomeAndExpenses() {
		// TODO put in stuff here BarChartBothIncomeAndExpenses

		double[] totalIncome = bundle.getDoubleArray("incomeTotals");
		double[] totalExpense = bundle.getDoubleArray("expenseTotals");
		String[] accountNames = bundle.getStringArray("dates");

		int i = 0;

		CategorySeries totIncomeSeries = new CategorySeries(context.getResources()
				.getString(R.string.tot_income)
				);

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

		CategorySeries totExpenseSeries = new CategorySeries(context.getResources()
				.getString(R.string.tot_expenses)
				);

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
		mRenderer.setXTitle("\n\n\nTime");
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

}
