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

public class BarGraphExpensesProject {

	Bundle dataBundle;
	Float textSize;
	ConversionClass mCC;

	public BarGraphExpensesProject(Bundle bundle) {
		// TODO Auto-generated constructor stub

		dataBundle = bundle;

	}

	public GraphicalView getProjectsExpenseBarView(Context context) {

		textSize = context.getResources().getDimension(R.dimen.graph_text_size);
		mCC = new ConversionClass(context);
		
		
		Log.d("graphjazz", "100");
		double[] y = dataBundle.getDoubleArray("totAmount");
		String[] x = dataBundle.getStringArray("projectName");

		CategorySeries series = new CategorySeries("Project Expenses");

		for (int i = 0; i < y.length; i++) {

			series.add(x[i], y[i]);

		}

	
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());
		

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(context.getResources().getColor(R.color.graph_red));
		renderer.setDisplayChartValues(true);
		renderer.setShowLegendItem(true);
		
		renderer.setChartValuesTextAlign(Align.CENTER);
		renderer.setChartValuesTextSize(textSize);
		renderer.setChartValuesFormat(mCC.getNumberFormat());
		
	

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

		mRenderer.addSeriesRenderer(renderer);
		

		mRenderer.setChartTitle("TOTAL EXPENSES PER PROJECT");
		
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setLabelsColor(context.getResources().getColor(R.color.darkish_grey));
		mRenderer.setChartTitleTextSize(textSize);
		mRenderer.setYAxisMin(100);
		mRenderer.setXTitle("\n\n\nProjects");
		mRenderer.setYTitle("Amount of Money");
		mRenderer.setBarSpacing(0.2d);
		mRenderer.setXAxisMax(x.length + 0.8d);
		mRenderer.setXAxisMin(0.2d);
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
		//mRenderer.setBarWidth(100);
		mRenderer.setLegendTextSize(textSize);
		
		
		
		
		for (int h = 0; h < x.length; h++) {
			mRenderer.addXTextLabel(h + 1, x[h]);
		}
		
		Log.d("graphjazz", "200");

		return ChartFactory.getBarChartView(context, dataset,
				mRenderer, Type.DEFAULT);
		
	}
	
	public GraphicalView getProjectsExpensePieChartView(Context context) {

		Log.d("achart get PIE CHART", "GET PIE CHART ACOUNTS INCOME");

		textSize = context.getResources().getDimension(R.dimen.graph_text_size);
		mCC = new ConversionClass(context);

		double[] values = dataBundle.getDoubleArray("totAmount");
		String[] x = dataBundle.getStringArray("projectName");

		CategorySeries series = new CategorySeries(
				"Projects Total Expenses Pie Chart");

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

		Log.d("achart get PIE CHART", "END OF GET PIE CHART PROJECT iNCOME");

		return ChartFactory.getPieChartView(context, series, renderer);
	}

}

