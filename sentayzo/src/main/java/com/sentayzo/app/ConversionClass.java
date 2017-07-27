package com.sentayzo.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class ConversionClass {

    SharedPreferences mSharedPrefs, customCurrencyPrefs;

    Locale mLocale;

    String mLocaleCountryString;

    Currency mCurrency;

    String mCurrencyString;

    Context context;

    SimpleDateFormat sdf4Db = new SimpleDateFormat("yyyy-MM-dd",
            Locale.getDefault());

    SimpleDateFormat sdf4Display = new SimpleDateFormat("dd - MMM - yyyy",
            Locale.getDefault());

    SimpleDateFormat sdf4DisplayNew = new SimpleDateFormat("dd MMM yyyy",
            Locale.getDefault());

    SimpleDateFormat sdfStatMonthYear = new SimpleDateFormat("MM-yyyy", Locale.getDefault());

    SimpleDateFormat sdfDisplayStatMonthYear = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

    SimpleDateFormat sdfStatMonth = new SimpleDateFormat("MM", Locale.getDefault());

    SimpleDateFormat sdfStatYear = new SimpleDateFormat("yyyy", Locale.getDefault());

    public ConversionClass(Context c) {

        context = c;

    }

    public Long valueConverter(String inString) {

        BigDecimal bd = new BigDecimal(inString);

        BigDecimal multiplicand = new BigDecimal(100);

        bd = bd.multiply(multiplicand);

        Long lValue = bd.longValue();

        return lValue;

    }

    public String valueConverter(Long dbValue) {

        BigDecimal bd = new BigDecimal(dbValue);

        Log.d("bdLong", "" + bd);
        BigDecimal divisor = new BigDecimal(100);

        bd = bd.divide(divisor, 2, RoundingMode.HALF_EVEN);

        Log.d("bdAfterDivide", "" + bd);
        String r = bd.toString();
        Log.d("r", "" + r);

        final Double d = bd.doubleValue();
        Log.d("d", "" + d);

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        customCurrencyPrefs = context.getSharedPreferences("custom_currency_prefs", 0);


        Boolean useCustomCurrency = mSharedPrefs.getBoolean("pref_use_custom_currency", false);


        if (useCustomCurrency == true) {


            NumberFormat df = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setCurrencySymbol(customCurrencyPrefs.getString("currencyCode",
                    "AED") + " ");

            Log.d("groupSeparator", customCurrencyPrefs.getString("groupSeparator", ","));

            if (customCurrencyPrefs.getString("groupSeparator", ",")
                    .equals(",")) {
                dfs.setGroupingSeparator(',');
            } else if (customCurrencyPrefs.getString("groupSeparator", ",")
                    .equals(".")) {
                dfs.setGroupingSeparator('.');
            }

            if (customCurrencyPrefs.getString("decimalSeparator", ".").equals(
                    ".")) {
                dfs.setMonetaryDecimalSeparator('.');
            } else if (customCurrencyPrefs.getString("decimalSeparator", ".")
                    .equals(",")) {
                dfs.setMonetaryDecimalSeparator(',');
            }

            df.setMaximumFractionDigits(customCurrencyPrefs.getInt(
                    "numberOfDecimals", 2));
            df.setRoundingMode(RoundingMode.HALF_EVEN);
            ((DecimalFormat) df).setDecimalFormatSymbols(dfs);

            String displayString = df.format(d);

            return displayString;
        } else {

            mLocaleCountryString = mSharedPrefs.getString("pref_default_country",
                    "US");

            mCurrencyString = mSharedPrefs
                    .getString("pref_default_currency", "USD");

            mLocale = new Locale("", mLocaleCountryString);

            mCurrency = Currency.getInstance(mCurrencyString);

            NumberFormat nf = NumberFormat.getCurrencyInstance(mLocale);

            nf.setCurrency(mCurrency);
            nf.setMaximumFractionDigits(mCurrency.getDefaultFractionDigits());

            String display9 = nf.format(d);

            Log.d(mLocaleCountryString, mLocaleCountryString + "= " + display9);

            return display9;

        }
    }

    public String dateForDb(String unformatted) {

        String formatted = null;

        Date unformattedNew;

        try {
            unformattedNew = sdf4Display.parse(unformatted);

            formatted = sdf4Db.format(unformattedNew);

            return formatted;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("datePicker", e.toString());
        }

        return formatted;

    }


    public String dateStatMonth(String dateStatYear) {

        String formatted = null;

        Date unformattedNew;

        try {

            unformattedNew = sdfStatMonthYear.parse(dateStatYear);


            formatted = sdfStatMonth.format(unformattedNew);

            return formatted;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return formatted;


    }

    public String dateStatYear(String dateStatYear) {

        String formatted = null;

        Date unformattedNew;

        try {

            unformattedNew = sdfStatMonthYear.parse(dateStatYear);

            formatted = sdfStatYear.format(unformattedNew);

            return formatted;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return formatted;


    }


    public String dateForDisplay(String unformatted) {

        String formatted = null;

        Date unformattedNew;
        try {
            unformattedNew = sdf4Db.parse(unformatted);

            formatted = sdf4Display.format(unformattedNew);

            return formatted;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("datePicker", e.toString());
        }

        return formatted;
    }

    public String dateForDisplayNew(String unformatted) {

        String formatted = null;

        Date unformattedNew;
        try {
            unformattedNew = sdf4Db.parse(unformatted);

            formatted = sdf4DisplayNew.format(unformattedNew);

            return formatted;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // Log.d("datePicker", e.toString());
        }

        return formatted;
    }

    public String dateForStatUseFromDisplay(String unformatted) {

        String formatted = null;

        Date unformattedNew;
        try {
            unformattedNew = sdfDisplayStatMonthYear.parse(unformatted);

            formatted = sdfStatMonthYear.format(unformattedNew);

            return formatted;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            /// Log.d("datePicker", e.toString());
        }

        return formatted;
    }

    public String dateForDisplayFromCalendarInstance(Date unformatted) {

        String formatted = null;

        try {

            formatted = sdf4Display.format(unformatted);

            return formatted;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //	Log.d("datePicker", e.toString());
        }

        return formatted;
    }

    public String dateForStatDisplayFromCalendarInstance(Date unformatted) {

        String formatted = null;

        try {

            formatted = sdfDisplayStatMonthYear.format(unformatted);

            return formatted;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //	Log.d("datePicker", e.toString());
        }

        return formatted;
    }

    public String dateYearForStatDisplayFromCalendarInstance(Date unformatted) {

        String formatted = null;

        try {

            formatted = sdfStatYear.format(unformatted);

            return formatted;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //	Log.d("datePicker", e.toString());
        }

        return formatted;
    }

    public String dateForStatistics(Date unformatted) {

        String formatted = null;

        try {

            formatted = sdfStatMonthYear.format(unformatted);

            return formatted;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //	Log.d("datePicker", e.toString());
        }

        return formatted;
    }

    public Long dateForAlarmManager(String date) {

        Long dateInMillis = null;
        try {
            dateInMillis = sdf4Db.parse(date).getTime();
            return dateInMillis;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dateInMillis;

    }

    public Date returnDateObjectFromDbDateString(String endDate) {
        // TODO Auto-generated method stub

        Date neededDate;
        try {
            neededDate = sdf4Db.parse(endDate);
            return neededDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public Date returnDateObjectFromDisplayDateString(String displayDate) {
        // TODO Auto-generated method stub
        Date neededDate;
        try {
            neededDate = sdf4Display.parse(displayDate);
            return neededDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public String addTheseDaysToDateAndReturnDbDate(int numberOfDays,
                                                    String displayDate) {
        // TODO Auto-generated method stub
        // display date is current date

        Calendar c = Calendar.getInstance();

        try {
            Log.d("displayDate", displayDate);
            Date dt = sdf4Display.parse(displayDate);
            Log.d("dt", "" + dt);

            c.setTime(dt);
            c.add(Calendar.DAY_OF_MONTH, numberOfDays);

            String dateNew = dateForDisplayFromCalendarInstance(c.getTime());
            Log.d("dateNew", dateNew);
            String nextDate = dateForDb(dateNew);
            Log.d("nextDate", nextDate);

            return nextDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public String returnAmountEditTextString(Long dbAmount) {

        if (dbAmount < 0) {

            dbAmount = dbAmount * (-1);
        }

        BigDecimal bd = new BigDecimal(dbAmount);

        Log.d("bdLong", "" + bd);
        BigDecimal divisor = new BigDecimal(100);

        bd = bd.divide(divisor, 2, RoundingMode.HALF_EVEN);

        Log.d("bdAfterDivide", "" + bd);
        String r = bd.toString();

        return r;

    }

    public Double valueConverterReturnDouble(Long dbValue) {

        BigDecimal bd = new BigDecimal(dbValue);

        Log.d("bdLong", "" + bd);
        BigDecimal divisor = new BigDecimal(100);

        bd = bd.divide(divisor, 2, RoundingMode.HALF_EVEN);

        Log.d("bdAfterDivide", "" + bd);
        String r = bd.toString();
        Log.d("r", "" + r);

        final Double d = bd.doubleValue();
        Log.d("d", "" + d);

        return d;


    }

    public Float valueConverterReturnFloat(Long dbValue) {

        BigDecimal bd = new BigDecimal(dbValue);

        Log.d("bdLong", "" + bd);
        BigDecimal divisor = new BigDecimal(100);

        bd = bd.divide(divisor, 2, RoundingMode.HALF_EVEN);

        Log.d("bdAfterDivide", "" + bd);
        String r = bd.toString();
        Log.d("r", "" + r);

        final Float d = bd.floatValue();
        Log.d("d", "" + d);

        return d;


    }

    public NumberFormat getNumberFormat() {
        // TODO Auto-generated method stub


        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        mLocaleCountryString = mSharedPrefs.getString("pref_default_country",
                "US");

        mCurrencyString = mSharedPrefs
                .getString("pref_default_currency", "USD");

        mLocale = new Locale("", mLocaleCountryString);

        mCurrency = Currency.getInstance(mCurrencyString);

        NumberFormat nf = NumberFormat.getCurrencyInstance(mLocale);

        nf.setCurrency(mCurrency);
        nf.setMaximumFractionDigits(mCurrency.getDefaultFractionDigits());

        return nf;

    }

    public String returnAmountForCSVString(Long dbAmount) {


        BigDecimal bd = new BigDecimal(dbAmount);

        Log.d("bdLong", "" + bd);
        BigDecimal divisor = new BigDecimal(100);

        bd = bd.divide(divisor, 2, RoundingMode.HALF_EVEN);

        Log.d("bdAfterDivide", "" + bd);
        String r = bd.toString();

        return r;

    }

    public String statAddOneMonth(String dateStatDisplay) {


        String dateNew = null;

        Calendar c = Calendar.getInstance();


        try {

            Date dt = sdfDisplayStatMonthYear.parse(dateStatDisplay);
            Log.d("dt", "" + dt);

            c.setTime(dt);
            c.add(Calendar.MONTH, 1);


            dateNew = dateForStatDisplayFromCalendarInstance(c.getTime());
            //Log.d("dateNew", dateNew);
            // String nextDate = dateForDb(dateNew);
            //	Log.d("nextDate", nextDate);

            return dateNew;

        } catch (Exception e) {

            e.printStackTrace();
        }


        return dateNew;
    }

    public String statAddOneYear(String dateStatDisplay) {


        String dateNew = null;

        Calendar c = Calendar.getInstance();


        try {

            Date dt = sdfStatYear.parse(dateStatDisplay);
            //      Log.d("dt", "" + dt);

            c.setTime(dt);
            c.add(Calendar.YEAR, 1);


            dateNew = dateYearForStatDisplayFromCalendarInstance(c.getTime());
            //Log.d("dateNew", dateNew);
            // String nextDate = dateForDb(dateNew);
            //	Log.d("nextDate", nextDate);

            return dateNew;

        } catch (Exception e) {

            e.printStackTrace();
        }


        return dateNew;
    }

    public String statSubtractOneYear(String dateStatDisplay) {


        String dateNew = null;

        Calendar c = Calendar.getInstance();


        try {

            Date dt = sdfStatYear.parse(dateStatDisplay);
            //      Log.d("dt", "" + dt);

            c.setTime(dt);
            c.add(Calendar.YEAR, -1);


            dateNew = dateYearForStatDisplayFromCalendarInstance(c.getTime());
            //Log.d("dateNew", dateNew);
            // String nextDate = dateForDb(dateNew);
            //	Log.d("nextDate", nextDate);

            return dateNew;

        } catch (Exception e) {

            e.printStackTrace();
        }


        return dateNew;
    }


    public String statSubtractOneMonth(String dateStatDisplay) {


        String dateNew = null;

        Calendar c = Calendar.getInstance();


        try {

            Date dt = sdfDisplayStatMonthYear.parse(dateStatDisplay);
            Log.d("dt", "" + dt);

            c.setTime(dt);
            c.add(Calendar.MONTH, -1);


            dateNew = dateForStatDisplayFromCalendarInstance(c.getTime());
            //Log.d("dateNew", dateNew);
            // String nextDate = dateForDb(dateNew);
            //	Log.d("nextDate", nextDate);

            return dateNew;

        } catch (Exception e) {

            e.printStackTrace();
        }


        return dateNew;
    }


}
