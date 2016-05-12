package com.sentayzo.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class ExportToCSV extends AsyncTask<String, Void, Boolean> {

	private final ProgressDialog dialog;
	Context ctx;
	ConversionClass mCC;

	public ExportToCSV(Context context) {

		ctx = context;

		dialog = new ProgressDialog(ctx);

		mCC = new ConversionClass(ctx);
	}

	// can use UI thread here

	@Override
	protected void onPreExecute()

	{

		this.dialog.setMessage("Exporting reports...");

		this.dialog.show();

	}

	// automatically done on worker thread (separate from UI thread)

	protected Boolean doInBackground(final String... args)

	{

		// File dbFile = getDatabasePath("excerDB.db");

		DbClass DBob = new DbClass(ctx);

		File moneyCradleFolder = new File(
				Environment.getExternalStorageDirectory(), "Money Cradle");

		if (!moneyCradleFolder.exists()) {

			moneyCradleFolder.mkdirs();
			Log.d("folder created", "folder created");
		}

		File csv = new File(moneyCradleFolder,
				ctx.getString(R.string.spreadsheet_reports));
		if (!csv.exists()) {

			csv.mkdirs();
		}

		File file = new File(csv, "myTransactions" + new Date().toString()
				+ ".csv");

		try

		{

			file.createNewFile();

			CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

			Cursor curCSV = DBob.getAllTransactionDetails();

			csvWrite.writeNext(curCSV.getColumnNames());

			while (curCSV.moveToNext())

			{

				String arrStr[] = { mCC.dateForDisplay(curCSV.getString(0)), curCSV.getString(1),

				curCSV.getString(2), curCSV.getString(3),

				mCC.returnAmountForCSVString(curCSV.getLong(4)),

				curCSV.getString(5), curCSV.getString(6) };

				csvWrite.writeNext(arrStr);

			}

			csvWrite.close();

			curCSV.close();

			return true;

		}

		catch (SQLException sqlEx)

		{

			Log.e("ExportToCSV", sqlEx.getMessage(), sqlEx);

			return false;

		}

		catch (IOException e)

		{

			Log.e("ExportToCSV", e.getMessage(), e);

			return false;

		}

	}

	// can use UI thread here

	@Override
	protected void onPostExecute(final Boolean success)

	{

		if (this.dialog.isShowing())

		{

			this.dialog.dismiss();

		}

		if (success)

		{

			Toast.makeText(ctx, ctx.getString(R.string.export_successful),
					Toast.LENGTH_LONG).show();

		}

		else

		{

			Toast.makeText(ctx, ctx.getString(R.string.export_failed),
					Toast.LENGTH_LONG).show();

		}

	}

}