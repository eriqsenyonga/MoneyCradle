package com.sentayzo.app;

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

public class GeneralReportChart {

	Bundle dataBundle;
	Float textSize;
	ConversionClass mCC;

	public GeneralReportChart(Bundle bundle) {
		// TODO Auto-generated constructor stub

		dataBundle = bundle;

	}

	public GraphicalView getGeneralBarView(Context context) {

		Log.d("achart get BAR CHART", "GET BAR CHART");

		textSize = context.getResources().getDimension(R.dimen.graph_text_size);
		mCC = new ConversionClass(context);

		Log.d("graphjazz", "100");
		double[] y = dataBundle.getDoubleArray("totals");
		String[] x = { context.getResources().getString(R.string.income),
				context.getResources().getString(R.string.expenses) };
		
		
		double[] income = new double[]{y[0]};
		double[] expenses = new double[]{y[1]};
		
		
		CategorySeries series1 = new CategorySeries("total income");
		series1.add(income[0]);
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series1.toXYSeries());
		XYSeriesRenderer renderer1 = new XYSeriesRenderer();
		renderer1.setColor(context.getResources().getColor(R.color.graph_green));
		renderer1.setDisplayChartValues(true);
		renderer1.setShowLegendItem(true);
		renderer1.setChartValuesFormat(mCC.getNumberFormat());
		
		renderer1.setChartValuesTextAlign(Align.RIGHT);
		renderer1.setChartValuesTextSize(textSize);
		
		
		CategorySeries series2 = new CategorySeries("total expenses");
		series2.add(expenses[0]);
		
		dataset.addSeries(series2.toXYSeries());
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor(context.getResources().getColor(R.color.graph_red));
		renderer2.setDisplayChartValues(true);
		renderer2.setShowLegendItem(true);
		renderer2.setChartValuesFormat(mCC.getNumberFormat());

		renderer2.setChartValuesTextAlign(Align.LEFT);
		renderer2.setChartValuesTextSize(textSize);
		
		
		

/*		CategorySeries series = new CategorySeries("Account Expenses");
		
		

		for (int i = 0; i < y.length; i++) {

			series.add(x[i], y[i]);

		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(context.getResources().getColor(R.color.graph_red));
		renderer.setDisplayChartValues(true);
		renderer.setShowLegendItem(true);
		renderer.setChartValuesFormat(mCC.getNumberFormat());

		renderer.setChartValuesTextAlign(Align.RIGHT);
		renderer.setChartValuesTextSize(textSize);

*/		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

		mRenderer.addSeriesRenderer(renderer1);
		mRenderer.addSeriesRenderer(renderer2);

		mRenderer.setChartTitle("INCOME VS EXPENSES");
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setChartTitleTextSize(textSize);
		mRenderer.setYAxisMin(100);
		mRenderer.setXTitle("\n\n\nAccounts");
		mRenderer.setYTitle("Amount of Money");
		mRenderer.setBarSpacing(0.2);
		mRenderer.setXAxisMax(x.length + 0.8);
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
		mRenderer.setMargins(new int[] { 50, 30, 15, 0 });
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setMarginsColor(Color.WHITE);
		mRenderer.setFitLegend(true);
		mRenderer.setBarWidth(100);
		mRenderer.setLabelsColor(context.getResources().getColor(R.color.darkish_grey));
		

		mRenderer.setLegendTextSize(textSize);

		

		Log.d("graphjazz", "200");
		
		

		return ChartFactory.getBarChartView(context, dataset, mRenderer,
				Type.DEFAULT);

	}

	public GraphicalView getGeneralPieChartView(Context context) {

		Log.d("achart get PIE CHART", "GET PIE CHART");

		textSize = context.getResources().getDimension(R.dimen.graph_text_size);
		mCC = new ConversionClass(context);

		double[] values = dataBundle.getDoubleArray("totals");

		CategorySeries series = new CategorySeries("General Pie Chart");

		series.add(context.getResources().getString(R.string.income), values[0]);
		series.add(context.getResources().getString(R.string.expenses),
				values[1]);

		int[] colors = { context.getResources().getColor(R.color.graph_green),
				context.getResources().getColor(R.color.graph_red) };

		DefaultRenderer renderer = new DefaultRenderer();

		for (int color : colors) {

			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);

			r.setChartValuesFormat(mCC.getNumberFormat());
			r.setShowLegendItem(true);
			renderer.addSeriesRenderer(r);
		}
		
		
		renderer.setShowLegend(true);
		renderer.setDisplayValues(true);
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

		Log.d("achart get PIE CHART", "END OF GET PIE CHART");

		return ChartFactory.getPieChartView(context, series, renderer);
	}

}
