package com.sentayzo.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


public class DbClass {

    // details for the credit card issuer table
    public static final String KEY_CARD_ISSUER_ID = "_id";
    public static final String KEY_CARD_ISSUER_NAME = "issuerName";
    public static final String DATABASE_TABLE_CREDIT_CARD_ISSUER = "creditCardIssuerTable";

    // SQL statement to create the credit card issuer table
    private static final String CREATE_CREDIT_CARD_ISSUER_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_CREDIT_CARD_ISSUER
            + " ("
            + KEY_CARD_ISSUER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CARD_ISSUER_NAME + " TEXT NOT NULL);";


    // details for the scheduled recurrence table
    public static final String KEY_SCHEDULED_RECURRENCE_ID = "_id";
    public static final String KEY_SCHEDULED_RECURRENCE_NAME = "sch_r_name";
    public static final String DATABASE_TABLE_SCHEDULED_RECURRENCE = "scheduledRecurrenceTable";

    // SQL statement to create the account type table
    private static final String CREATE_SCHEDULED_RECURRENCE_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_SCHEDULED_RECURRENCE
            + " ("
            + KEY_SCHEDULED_RECURRENCE_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SCHEDULED_RECURRENCE_NAME + " TEXT NOT NULL);";

    // details for the account type table
    public static final String KEY_ACCOUNT_TYPE_ID = "_id";
    public static final String KEY_ACCOUNT_TYPE_NAME = "ac_t_name";
    public static final String DATABASE_TABLE_ACCOUNT_TYPE = "accountTypeTable";

    // SQL statement to create the account type table
    private static final String CREATE_ACCOUNT_TYPE_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_ACCOUNT_TYPE + " (" + KEY_ACCOUNT_TYPE_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ACCOUNT_TYPE_NAME
            + " TEXT NOT NULL);";

    // details for the my currency table
    public static final String KEY_MY_CURRENCY_ID = "_id";
    public static final String KEY_MY_CURRENCY_CURRENCY_CODE = "my_currency_code";
    public static final String KEY_MY_CURRENCY_HOME = "home_currency";
    public static final String DATABASE_TABLE_MY_CURRENCY = "myCurrencyTable";

    // SQL statement to create the account type table
    private static final String CREATE_MY_CURRENCY_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_MY_CURRENCY + " (" + KEY_MY_CURRENCY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MY_CURRENCY_CURRENCY_CODE + " TEXT NOT NULL,"
            + KEY_MY_CURRENCY_HOME + " INTEGER);";

    // details for the project table
    public static final String KEY_PROJECT_ID = "_id";
    public static final String KEY_PROJECT_NAME = "pr_name";
    public static final String KEY_PROJECT_NOTE = "pr_note";
    public static final String KEY_PROJECT_OPEN = "pr_open";

    public static final String DATABASE_TABLE_PROJECT = "projectTable";

    // SQL statement to create the project table
    private static final String CREATE_PROJECT_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_PROJECT + " (" + KEY_PROJECT_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PROJECT_NAME
            + " TEXT NOT NULL," + KEY_PROJECT_NOTE + " TEXT, "
            + KEY_PROJECT_OPEN + " INTEGER NOT NULL);";

    // details for the country-currency table
    public static final String KEY_COUNTRY_CURRENCY_ID = "_id";
    public static final String KEY_COUNTRY_NAME = "country_name";
    public static final String KEY_COUNTRY_CODE = "country_code";
    public static final String KEY_CURRENCY_NAME = "currency_name";
    public static final String KEY_CURRENCY_CODE = "currency_code";

    public static final String DATABASE_TABLE_COUNTRY_CURRENCY = "countryCurrencyTable";

    // SQL statement to create the country-currency table
    private static final String CREATE_COUNTRY_CURRENCY_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_COUNTRY_CURRENCY + " (" + KEY_COUNTRY_CURRENCY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_COUNTRY_NAME
            + " TEXT NOT NULL, " + KEY_COUNTRY_CODE + " TEXT NOT NULL, "
            + KEY_CURRENCY_NAME + " TEXT NOT NULL, " + KEY_CURRENCY_CODE
            + " TEXT NOT NULL);";

    // details for the payee table
    public static final String KEY_PAYEE_ID = "_id";
    public static final String KEY_PAYEE_NAME = "p_name";
    public static final String DATABASE_TABLE_PAYEE = "payeeTable";

    // SQL statement to create the payee table
    private static final String CREATE_PAYEE_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_PAYEE + " (" + KEY_PAYEE_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PAYEE_NAME
            + " TEXT NOT NULL);";

    // details for the transaction type account
    public static final String KEY_TRANSACTION_TYPE_ID = "_id";
    public static final String KEY_TRANSACTION_TYPE_NAME = "tx_t_name";
    public static final String DATABASE_TABLE_TRANSACTION_TYPE = "transactionTypeTable";

    // SQL statement to create the transaction type table
    private static final String CREATE_TRANSACTION_TYPE_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_TRANSACTION_TYPE + " (" + KEY_TRANSACTION_TYPE_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TRANSACTION_TYPE_NAME + " TEXT NOT NULL);";

    // details for the category table
    public static final String KEY_CATEGORY_ID = "_id";
    public static final String KEY_CATEGORY_NAME = "cat_name";
    public static final String DATABASE_TABLE_CATEGORY = "categoryTable";

    // SQL statement to create the category table
    private static final String CREATE_CATEGORY_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_CATEGORY + " (" + KEY_CATEGORY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CATEGORY_NAME
            + " TEXT NOT NULL);";

    // details for the subcategory table
    public static final String KEY_SUB_CATEGORY_ID = "s_cat_id";
    public static final String KEY_SUB_CATEGORY_NAME = "s_cat_name";
    public static final String KEY_SUB_CATEGORY_CATEGORY_ID = "cat_id";
    public static final String DATABASE_TABLE_SUB_CATEGORY = "subCategoryTable";

    // SQL statement to create the subcategory table
    private static final String CREATE_SUB_CATEGORY_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_SUB_CATEGORY + " (" + KEY_SUB_CATEGORY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SUB_CATEGORY_NAME
            + " TEXT NOT NULL, " + KEY_SUB_CATEGORY_CATEGORY_ID
            + " INTEGER NOT NULL," + "FOREIGN KEY (" + KEY_SUB_CATEGORY_ID
            + ") REFERENCES " + DATABASE_TABLE_CATEGORY + "(" + KEY_CATEGORY_ID
            + "));";

    // details of the account table
    public static final String KEY_ACCOUNT_ID = "_id";
    public static final String KEY_ACCOUNT_NAME = "ac_name";
    public static final String KEY_ACCOUNT_DATE = "ac_date";
    public static final String KEY_ACCOUNT_NOTE = "ac_note";
    public static final String KEY_ACCOUNT_TYPE = "ac_t_id";
    public static final String KEY_ACCOUNT_INCLUDE_IN_TOTALS = "ac_include";// 1
    // for
    // yes
    // or
    // 0
    // for
    // no
    public static final String KEY_ACCOUNT_OPEN = "ac_open";// 1 for yes or 0
    // for no
    public static final String KEY_ACCOUNT_OPEN_AMOUNT = "ac_open_amount";

    public static final String KEY_ACCOUNT_CREDIT_CARD_ISSUER_ID = "ac_card_issuer_id";

    public static final String KEY_ACCOUNT_CREDIT_LIMIT = "ac_credit_limit";

    public static final String KEY_ACCOUNT_CREDIT_PROVIDER = "ac_credit_provider";

    public static final String KEY_ACCOUNT_CURRENCY_ID = "ac_currency_id";

    public static final String DATABASE_TABLE_ACCOUNT = "accountTable";

    // SQL statement to create the account table
    private static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_ACCOUNT + "(" + KEY_ACCOUNT_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ACCOUNT_NAME
            + " TEXT NOT NULL, " + KEY_ACCOUNT_DATE + " TEXT NOT NULL, "
            + KEY_ACCOUNT_TYPE + " INTEGER, " + KEY_ACCOUNT_NOTE + " TEXT, "
            + KEY_ACCOUNT_OPEN_AMOUNT + " INTEGER, "
            + KEY_ACCOUNT_INCLUDE_IN_TOTALS + " INTEGER, " + KEY_ACCOUNT_OPEN
            + " INTEGER, " + KEY_ACCOUNT_CREDIT_CARD_ISSUER_ID + " INTEGER , " + KEY_ACCOUNT_CREDIT_LIMIT + " INTEGER, " +
            KEY_ACCOUNT_CREDIT_PROVIDER + " TEXT, " +
            KEY_ACCOUNT_CURRENCY_ID + " INTEGER ,  FOREIGN KEY (" + KEY_ACCOUNT_TYPE + ") REFERENCES "
            + DATABASE_TABLE_ACCOUNT_TYPE + "(" + KEY_ACCOUNT_TYPE_ID + ")," +
            " FOREIGN KEY (" + KEY_ACCOUNT_CREDIT_CARD_ISSUER_ID +
            ") REFERENCES " + DATABASE_TABLE_CREDIT_CARD_ISSUER +
            "(" + KEY_CARD_ISSUER_ID +
            "), " +
            " FOREIGN KEY ("
            + KEY_ACCOUNT_CURRENCY_ID + ") REFERENCES "
            + DATABASE_TABLE_MY_CURRENCY + "(" + KEY_MY_CURRENCY_ID + "));";

    // details of the transaction table
    public static final String KEY_TRANSACTION_ID = "_id";
    public static final String KEY_TRANSACTION_DATE = "tx_date";
    public static final String KEY_TRANSACTION_ACCOUNT_ID = "ac_id";
    public static final String KEY_TRANSACTION_PAYEE_ID = "p_id";
    public static final String KEY_TRANSACTION_TRANSACTION_TYPE_ID = "tx_t_id";
    public static final String KEY_TRANSACTION_CATEGORY_ID = "cat_id";
    public static final String KEY_TRANSACTION_AMOUNT = "tx_amount";
    public static final String KEY_TRANSACTION_NOTE = "tx_note";
    public static final String KEY_TRANSACTION_TRANSFER_ID = "tx_tr_id";
    public static final String KEY_TRANSACTION_PROJECT_ID = "pr_id";
    public static final String KEY_TRANSACTION_PHOTO = "tx_photo";

    public static final String DATABASE_TABLE_TRANSACTION = "transactionTable";

    // SQL statement to create the transaction table
    private static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_TRANSACTION + "(" + KEY_TRANSACTION_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TRANSACTION_DATE
            + " TEXT NOT NULL, " + KEY_TRANSACTION_ACCOUNT_ID
            + " INTEGER NOT NULL, " + KEY_TRANSACTION_PAYEE_ID + " INTEGER, "
            + KEY_TRANSACTION_TRANSACTION_TYPE_ID + " INTEGER NOT NULL, "
            + KEY_TRANSACTION_CATEGORY_ID + " INTEGER NOT NULL, "
            + KEY_TRANSACTION_AMOUNT + " INTEGER NOT NULL, "
            + KEY_TRANSACTION_NOTE + " TEXT, "
            + KEY_TRANSACTION_TRANSFER_ID
            + " INTEGER, "
            + KEY_TRANSACTION_PHOTO + " TEXT,"
            + KEY_TRANSACTION_PROJECT_ID + " INTEGER, "
            + "FOREIGN KEY (" + KEY_TRANSACTION_ACCOUNT_ID + ") REFERENCES "
            + DATABASE_TABLE_ACCOUNT + "(" + KEY_ACCOUNT_ID + ")," + "FOREIGN KEY ("
            + KEY_TRANSACTION_PAYEE_ID + ") REFERENCES " + DATABASE_TABLE_PAYEE
            + "(" + KEY_PAYEE_ID + ")," + "FOREIGN KEY ("
            + KEY_TRANSACTION_TRANSACTION_TYPE_ID + ") REFERENCES "
            + DATABASE_TABLE_TRANSACTION_TYPE + "(" + KEY_TRANSACTION_TYPE_ID
            + ")," + "FOREIGN KEY (" + KEY_TRANSACTION_CATEGORY_ID
            + ") REFERENCES " + DATABASE_TABLE_CATEGORY + "(" + KEY_CATEGORY_ID
            + "),FOREIGN KEY (" + KEY_TRANSACTION_PROJECT_ID + ") REFERENCES "
            + DATABASE_TABLE_PROJECT + "(" + KEY_PROJECT_ID + "));";

    // details of the scheduled transaction table
    public static final String KEY_SCHEDULED_ID = "_id";
    public static final String KEY_SCHEDULED_START_DATE = "tx_start_date";
    public static final String KEY_SCHEDULED_END_DATE = "tx_end_date";
    public static final String KEY_SCHEDULED_ACCOUNT_ID = "ac_id";
    public static final String KEY_SCHEDULED_PAYEE_ID = "p_id";
    public static final String KEY_SCHEDULED_TRANSACTION_TYPE_ID = "tx_t_id";
    public static final String KEY_SCHEDULED_CATEGORY_ID = "cat_id";
    public static final String KEY_SCHEDULED_AMOUNT = "tx_amount";
    public static final String KEY_SCHEDULED_NOTE = "tx_note";
    public static final String KEY_SCHEDULED_TRANSFER_ID = "tx_tr_id";
    public static final String KEY_SCHEDULED_PROJECT_ID = "pr_id";
    public static final String KEY_SCHEDULED_SCH_RECURRENCE_ID = "sch_rec_id";
    public static final String KEY_SCHEDULED_DONE = "sch_done";
    public static final String KEY_SCHEDULED_ALARM_ID = "sch_alarm_id";
    public static final String KEY_SCHEDULED_NEXT_DATE = "sch_next_date";

    public static final String DATABASE_TABLE_SCHEDULED = "scheduledTable";

    // SQL statement to create the SCHEDULED table
    private static final String CREATE_SCHEDULED_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_SCHEDULED + "(" + KEY_SCHEDULED_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SCHEDULED_START_DATE
            + " TEXT NOT NULL, " + KEY_SCHEDULED_END_DATE + " TEXT, "
            + KEY_SCHEDULED_ACCOUNT_ID + " INTEGER NOT NULL, "
            + KEY_SCHEDULED_PAYEE_ID + " INTEGER, "
            + KEY_SCHEDULED_TRANSACTION_TYPE_ID + " INTEGER NOT NULL, "
            + KEY_SCHEDULED_CATEGORY_ID + " INTEGER NOT NULL, "
            + KEY_SCHEDULED_AMOUNT + " INTEGER NOT NULL, " + KEY_SCHEDULED_NOTE
            + " TEXT, " + KEY_SCHEDULED_TRANSFER_ID + " INTEGER, "
            + KEY_SCHEDULED_PROJECT_ID + " INTEGER, "
            + KEY_SCHEDULED_SCH_RECURRENCE_ID + " INTEGER NOT NULL, "
            + KEY_SCHEDULED_DONE + " INTEGER NOT NULL, "
            + KEY_SCHEDULED_ALARM_ID + " INTEGER NOT NULL, "
            + KEY_SCHEDULED_NEXT_DATE + " TEXT, " + "FOREIGN KEY ("
            + KEY_SCHEDULED_ACCOUNT_ID + ") REFERENCES "
            + DATABASE_TABLE_ACCOUNT + "(" + KEY_ACCOUNT_ID + ")," + "FOREIGN KEY ("
            + KEY_SCHEDULED_PAYEE_ID + ") REFERENCES " + DATABASE_TABLE_PAYEE
            + "(" + KEY_PAYEE_ID + ")," + "FOREIGN KEY ("
            + KEY_SCHEDULED_TRANSACTION_TYPE_ID + ") REFERENCES "
            + DATABASE_TABLE_TRANSACTION_TYPE + "(" + KEY_TRANSACTION_TYPE_ID
            + ")," + "FOREIGN KEY (" + KEY_SCHEDULED_CATEGORY_ID
            + ") REFERENCES " + DATABASE_TABLE_CATEGORY + "(" + KEY_CATEGORY_ID
            + "),FOREIGN KEY (" + KEY_SCHEDULED_PROJECT_ID + ") REFERENCES "
            + DATABASE_TABLE_PROJECT + "(" + KEY_PROJECT_ID
            + "), FOREIGN KEY (" + KEY_SCHEDULED_SCH_RECURRENCE_ID
            + ") REFERENCES " + DATABASE_TABLE_SCHEDULED_RECURRENCE + "("
            + KEY_SCHEDULED_RECURRENCE_ID + "));";

    // details of the finished transaction table
    public static final String KEY_FIN_TRANSACTION_ID = "_id";
    public static final String KEY_FIN_TRANSACTION_DATE = "fin_tx_date";
    public static final String KEY_FIN_TRANSACTION_ACCOUNT_ID = "fin_ac_id";
    public static final String KEY_FIN_TRANSACTION_PAYEE_ID = "fin_p_id";
    public static final String KEY_FIN_TRANSACTION_TRANSACTION_TYPE_ID = "fin_tx_t_id";
    public static final String KEY_FIN_TRANSACTION_CATEGORY_ID = "fin_cat_id";
    public static final String KEY_FIN_TRANSACTION_AMOUNT = "fin_tx_amount";
    public static final String KEY_FIN_TRANSACTION_NOTE = "fin_tx_note";
    public static final String KEY_FIN_TRANSACTION_TRANSFER_ID = "fin_tx_tr_id";
    public static final String KEY_FIN_TRANSACTION_PROJECT_ID = "fin_pr_id";

    public static final String DATABASE_TABLE_FINISHED_TRANSACTION = "finishedTransactionTable";

    // SQL statement to create the transaction table
    private static final String CREATE_FINISHED_TRANSACTION_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_FINISHED_TRANSACTION
            + "("
            + KEY_FIN_TRANSACTION_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_FIN_TRANSACTION_DATE
            + " TEXT NOT NULL, "
            + KEY_FIN_TRANSACTION_ACCOUNT_ID
            + " INTEGER NOT NULL, "
            + KEY_FIN_TRANSACTION_PAYEE_ID
            + " INTEGER, "
            + KEY_FIN_TRANSACTION_TRANSACTION_TYPE_ID
            + " INTEGER NOT NULL, "
            + KEY_FIN_TRANSACTION_CATEGORY_ID
            + " INTEGER NOT NULL, "
            + KEY_FIN_TRANSACTION_AMOUNT
            + " INTEGER NOT NULL, "
            + KEY_FIN_TRANSACTION_NOTE
            + " TEXT, "
            + KEY_FIN_TRANSACTION_TRANSFER_ID
            + " INTEGER, "
            + KEY_FIN_TRANSACTION_PROJECT_ID
            + " INTEGER, "
            + "FOREIGN KEY ("
            + KEY_FIN_TRANSACTION_ACCOUNT_ID
            + ") REFERENCES "
            + DATABASE_TABLE_ACCOUNT
            + "("
            + KEY_ACCOUNT_ID
            + "),"
            + "FOREIGN KEY ("
            + KEY_FIN_TRANSACTION_PAYEE_ID
            + ") REFERENCES "
            + DATABASE_TABLE_PAYEE
            + "("
            + KEY_PAYEE_ID
            + "),"
            + "FOREIGN KEY ("
            + KEY_FIN_TRANSACTION_TRANSACTION_TYPE_ID
            + ") REFERENCES "
            + DATABASE_TABLE_TRANSACTION_TYPE
            + "("
            + KEY_TRANSACTION_TYPE_ID
            + "),"
            + "FOREIGN KEY ("
            + KEY_FIN_TRANSACTION_CATEGORY_ID
            + ") REFERENCES "
            + DATABASE_TABLE_CATEGORY
            + "("
            + KEY_CATEGORY_ID
            + "),FOREIGN KEY ("
            + KEY_FIN_TRANSACTION_PROJECT_ID
            + ") REFERENCES "
            + DATABASE_TABLE_PROJECT
            + "("
            + KEY_PROJECT_ID
            + "));";

    // details for the BUDGET table
    public static final String KEY_BUDGET_ID = "_id";
    public static final String KEY_BUDGET_TITLE = "budget_title";
    public static final String KEY_BUDGET_ACCOUNTS = "budget_accounts";
    public static final String KEY_BUDGET_CATEGORIES = "budget_categories";
    public static final String KEY_BUDGET_PROJECTS = "budget_projects";
    public static final String KEY_BUDGET_RECUR_PERIOD = "budget_recur_period";
    public static final String KEY_BUDGET_START_DATE = "budget_start_date";
    public static final String KEY_BUDGET_STOP_DATE = "budget_stop_date";
    public static final String KEY_BUDGET_AMOUNT = "budget_amount";

    public static final String DATABASE_TABLE_BUDGET = "budgetTable";

    // SQL statement to create the BUDGET table
    private static final String CREATE_BUDGET_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_BUDGET + " (" + KEY_BUDGET_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BUDGET_TITLE
            + " TEXT NOT NULL, " + KEY_BUDGET_ACCOUNTS + " TEXT NOT NULL, "
            + KEY_BUDGET_CATEGORIES + " TEXT NOT NULL, " + KEY_BUDGET_PROJECTS
            + " TEXT NOT NULL, " + KEY_BUDGET_RECUR_PERIOD + " TEXT NOT NULL, "
            + KEY_BUDGET_START_DATE + " TEXT NOT NULL, " + KEY_BUDGET_STOP_DATE
            + " TEXT, " + KEY_BUDGET_AMOUNT + " INTEGER NOT NULL);";

    // details of the database that is, database name and version
    private static final String DATABASE_NAME = "sentayzoDb.db";
    private static final int DATABASE_VERSION = 23;

    private DbHelper ourHelper; // instance of the DbHelper class
    private final Context ourContext;
    static SQLiteDatabase ourDatabase; // instance of the SQLitedatabase class


    private static class DbHelper extends SQLiteOpenHelper {
        Context ctx;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            ctx = context;

        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            // TODO Auto-generated method stub

            db.execSQL(CREATE_CREDIT_CARD_ISSUER_TABLE);
            db.execSQL(CREATE_MY_CURRENCY_TABLE);
            db.execSQL(CREATE_PROJECT_TABLE);
            db.execSQL(CREATE_CATEGORY_TABLE);
            db.execSQL(CREATE_SUB_CATEGORY_TABLE);
            db.execSQL(CREATE_ACCOUNT_TYPE_TABLE);
            db.execSQL(CREATE_PAYEE_TABLE);
            db.execSQL(CREATE_TRANSACTION_TYPE_TABLE);
            db.execSQL(CREATE_ACCOUNT_TABLE);
            db.execSQL(CREATE_TRANSACTION_TABLE);
            db.execSQL(CREATE_SCHEDULED_RECURRENCE_TABLE);
            db.execSQL(CREATE_COUNTRY_CURRENCY_TABLE);
            db.execSQL(CREATE_SCHEDULED_TABLE);
            db.execSQL(CREATE_FINISHED_TRANSACTION_TABLE);


            Log.d("DbClass.java", "all tables created");

            ContentValues cv = new ContentValues();

            // inserting default values of the category table
            cv.put(KEY_CATEGORY_NAME, " Undefined");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Opening Balance");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Dining");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Utilities");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Clothing");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Public Transport");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Phone");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Internet");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);


            cv.put(KEY_CATEGORY_NAME, "Entertainment");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Charity");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Income (Other)");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "School Dues");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Rent");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Auto");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Miscellaneous");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Health Care");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Insurance");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Bank Charges");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);
            Log.d("in DbClass", "all CATEGORY inserts done");

            cv.put(KEY_CATEGORY_NAME, "Laundry");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);
            Log.d("in DbClass", "all CATEGORY inserts done");

            cv.put(KEY_CATEGORY_NAME, "Salary");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Pocket Money");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Dividend");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Profit");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);

            cv.put(KEY_CATEGORY_NAME, "Transfer");
            db.insert(DATABASE_TABLE_CATEGORY, null, cv);
            Log.d("in DbClass", "all CATEGORY inserts done");

            // inserting default values for the account type table
            db.execSQL("INSERT INTO " + DATABASE_TABLE_ACCOUNT_TYPE + "("
                    + KEY_ACCOUNT_TYPE_NAME + ") " + "VALUES ('Cash');");
            Log.d("in DbClass", "1st ACCOUNT TYPE insert done using execSQL");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_ACCOUNT_TYPE + "("
                    + KEY_ACCOUNT_TYPE_NAME + ") " + "VALUES ('Bank');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_ACCOUNT_TYPE + "("
                    + KEY_ACCOUNT_TYPE_NAME + ") " + "VALUES ('Asset');");
            Log.d("in DbClass", "all ACCOUNT TYPE inserts done using execSQL");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_ACCOUNT_TYPE + "("
                    + KEY_ACCOUNT_TYPE_NAME + ") " + "VALUES ('Liability');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_ACCOUNT_TYPE + "("
                    + KEY_ACCOUNT_TYPE_NAME + ") " + "VALUES ('Credit Card');");

            //inserting values into credit card issuers table
            db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                    + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Visa');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                    + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('MasterCard');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                    + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('American Express');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                    + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Discover');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                    + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Diners Club');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                    + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Union Pay');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                    + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('JCB');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                    + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Maestro');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                    + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Other');");

            // inserting default values for the transaction type table
            db.execSQL("INSERT INTO " + DATABASE_TABLE_TRANSACTION_TYPE + "("
                    + KEY_TRANSACTION_TYPE_NAME + ") " + "VALUES ('Spend');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_TRANSACTION_TYPE + "("
                    + KEY_TRANSACTION_TYPE_NAME + ") " + "VALUES ('Receive');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_TRANSACTION_TYPE + "("
                    + KEY_TRANSACTION_TYPE_NAME + ") " + "VALUES ('Withdraw');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_TRANSACTION_TYPE + "("
                    + KEY_TRANSACTION_TYPE_NAME + ") " + "VALUES ('Deposit');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_TRANSACTION_TYPE + "("
                    + KEY_TRANSACTION_TYPE_NAME + ") " + "VALUES ('Credit');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_TRANSACTION_TYPE + "("
                    + KEY_TRANSACTION_TYPE_NAME + ") " + "VALUES ('Debit');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_TRANSACTION_TYPE + "("
                    + KEY_TRANSACTION_TYPE_NAME + ") "
                    + "VALUES ('New Account');");

            Log.d("in DbClass",
                    "all TRANSACTION TYPE inserts done using execSQL");

            // inserting value for new account payee in payee table
            db.execSQL("INSERT INTO " + DATABASE_TABLE_PAYEE + "("
                    + KEY_PAYEE_NAME + ") " + "VALUES ('open account');");

            db.execSQL("INSERT INTO " + DATABASE_TABLE_PAYEE + "("
                    + KEY_PAYEE_NAME + ") " + "VALUES ('No Payee');");

            Log.d("onCreate() in dbclass", "successful");

            // inserting value for "No Project" in Project Table
            db.execSQL("INSERT INTO " + DATABASE_TABLE_PROJECT + "("
                    + KEY_PROJECT_NAME + "," + KEY_PROJECT_OPEN
                    + ") VALUES (' No Project', 1);");

            // inserting in the currencies table
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    String[] countryName = DbClass.getCountryNames(ctx);

                    String[] countryCode = DbClass.getCountryCode(ctx);

                    String[] currencyName = DbClass.getCurrencyNames(ctx);
                    String[] currencyCode = DbClass.getCurrencyCodes(ctx);

                    // for(String item1:countryName String item2:countryCode){}

                    int i = countryName.length;

                    ContentValues cvs = new ContentValues();

                    for (int b = 0; b < i; b++) {

                        cvs.put(KEY_COUNTRY_NAME, countryName[b]);
                        cvs.put(KEY_COUNTRY_CODE, countryCode[b]);
                        cvs.put(KEY_CURRENCY_CODE, currencyCode[b]);
                        cvs.put(KEY_CURRENCY_NAME, currencyName[b]);

                        db.insert(DATABASE_TABLE_COUNTRY_CURRENCY, null, cvs);
                        Log.d("progress", "" + b);
                    }

                }
            });

            thread.start();

            // inserting values into scheduled recurrence table

            ContentValues values = new ContentValues();

            values.put(KEY_SCHEDULED_RECURRENCE_NAME, "No Recur");
            db.insert(DATABASE_TABLE_SCHEDULED_RECURRENCE, null, values);

            values.put(KEY_SCHEDULED_RECURRENCE_NAME, "Daily");
            db.insert(DATABASE_TABLE_SCHEDULED_RECURRENCE, null, values);

            values.put(KEY_SCHEDULED_RECURRENCE_NAME, "Weekly");
            db.insert(DATABASE_TABLE_SCHEDULED_RECURRENCE, null, values);

            values.put(KEY_SCHEDULED_RECURRENCE_NAME, "Monthly");
            db.insert(DATABASE_TABLE_SCHEDULED_RECURRENCE, null, values);

            values.put(KEY_SCHEDULED_RECURRENCE_NAME, "Yearly");
            db.insert(DATABASE_TABLE_SCHEDULED_RECURRENCE, null, values);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


            if (oldVersion == 21) {
                /*
                * this update of the db moves to newVersion 22 which now includes Credit Card Processing
                *
                *
                * */
                //1.     rename the old accountTable to accountTableOld

                String DATABASE_TABLE_ACCOUNT_OLD = "accountTableOld";
                db.execSQL("ALTER TABLE " + DATABASE_TABLE_ACCOUNT + " RENAME TO " + DATABASE_TABLE_ACCOUNT_OLD + ";");


                //2.    create the credit_card_issuer_table
                db.execSQL(CREATE_CREDIT_CARD_ISSUER_TABLE);


                //3.    create new accountTable with credit card columns and foreign keys

                db.execSQL(CREATE_ACCOUNT_TABLE);


                //4.    copy all data from accountTableOld into accountTable

                db.execSQL("INSERT INTO " + DATABASE_TABLE_ACCOUNT
                        + "("
                        + KEY_ACCOUNT_ID + ", "
                        + KEY_ACCOUNT_NAME + ", "
                        + KEY_ACCOUNT_TYPE + ", "
                        + KEY_ACCOUNT_OPEN + ", "
                        + KEY_ACCOUNT_OPEN_AMOUNT + ", "
                        + KEY_ACCOUNT_DATE + ", "
                        + KEY_ACCOUNT_NOTE + ", "
                        + KEY_ACCOUNT_INCLUDE_IN_TOTALS
                        + ")"

                        + " SELECT "
                        + KEY_ACCOUNT_ID + ", "
                        + KEY_ACCOUNT_NAME + ", "
                        + KEY_ACCOUNT_TYPE + ", "
                        + KEY_ACCOUNT_OPEN + ", "
                        + KEY_ACCOUNT_OPEN_AMOUNT + ", "
                        + KEY_ACCOUNT_DATE + ", "
                        + KEY_ACCOUNT_NOTE + ", "
                        + KEY_ACCOUNT_INCLUDE_IN_TOTALS

                        + " FROM " + DATABASE_TABLE_ACCOUNT_OLD + ";");


                //5.    insert credit card as account type in AccountType Table
                db.execSQL("INSERT INTO " + DATABASE_TABLE_ACCOUNT_TYPE + "("
                        + KEY_ACCOUNT_TYPE_NAME + ") " + "VALUES ('Credit Card');");

                //6.    inserting values into credit card issuers table
                db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                        + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Visa');");

                db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                        + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('MasterCard');");

                db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                        + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('American Express');");

                db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                        + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Discover');");

                db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                        + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Diners Club');");

                db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                        + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Union Pay');");

                db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                        + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('JCB');");

                db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                        + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Maestro');");

                db.execSQL("INSERT INTO " + DATABASE_TABLE_CREDIT_CARD_ISSUER + "("
                        + KEY_CARD_ISSUER_NAME + ") " + "VALUES ('Other');");


                //7.   drop accountTableOld
                db.execSQL("DROP TABLE " + DATABASE_TABLE_ACCOUNT_OLD);




	/*upgrading to Database Version 23

		version 23 includes a column to save a photo in the transaction table

	*/

                //step 1. rename the transactionTable to transactionTableOld

                String DATABASE_TABLE_TRANSACTION_OLD = "transactionTableOld";

                db.execSQL("ALTER TABLE " + DATABASE_TABLE_TRANSACTION + " RENAME TO " + DATABASE_TABLE_TRANSACTION_OLD + ";");

                //step 2. create new table called transactionTable but with a column called photo with type TEXT

                db.execSQL(CREATE_TRANSACTION_TABLE);

                //step 3. copy all data from transactionTableOld to transactionTable

                db.execSQL("INSERT INTO " + DATABASE_TABLE_TRANSACTION
                        + "("
                        + KEY_TRANSACTION_ID + ", "
                        + KEY_TRANSACTION_DATE + ", "
                        + KEY_TRANSACTION_ACCOUNT_ID + ", "
                        + KEY_TRANSACTION_PAYEE_ID + ", "
                        + KEY_TRANSACTION_TRANSACTION_TYPE_ID + ", "
                        + KEY_TRANSACTION_CATEGORY_ID + ", "
                        + KEY_TRANSACTION_AMOUNT + ", "
                        + KEY_TRANSACTION_NOTE + ", "
                        + KEY_TRANSACTION_TRANSFER_ID + ", "
                        + KEY_TRANSACTION_PROJECT_ID + " "

                        + ")"

                        + " SELECT "
                        + KEY_TRANSACTION_ID + ", "
                        + KEY_TRANSACTION_DATE + ", "
                        + KEY_TRANSACTION_ACCOUNT_ID + ", "
                        + KEY_TRANSACTION_PAYEE_ID + ", "
                        + KEY_TRANSACTION_TRANSACTION_TYPE_ID + ", "
                        + KEY_TRANSACTION_CATEGORY_ID + ", "
                        + KEY_TRANSACTION_AMOUNT + ", "
                        + KEY_TRANSACTION_NOTE + ", "
                        + KEY_TRANSACTION_TRANSFER_ID + ", "
                        + KEY_TRANSACTION_PROJECT_ID + " "

                        + " FROM " + DATABASE_TABLE_TRANSACTION_OLD + ";");


                //step 4. Drop transactionTableOld

                db.execSQL("DROP TABLE " + DATABASE_TABLE_TRANSACTION_OLD);

            } else if (oldVersion == 22) {


	/*upgrading to Database Version 23 from version 22

		version 23 includes a column to save a photo in the transaction table

	*/

                //step 1. rename the transactionTable to transactionTableOld

                String DATABASE_TABLE_TRANSACTION_OLD = "transactionTableOld";

                db.execSQL("ALTER TABLE " + DATABASE_TABLE_TRANSACTION + " RENAME TO " + DATABASE_TABLE_TRANSACTION_OLD + ";");

                //step 2. create new table called transactionTable but with a column called photo with type TEXT

                db.execSQL(CREATE_TRANSACTION_TABLE);

                //step 3. copy all data from transactionTableOld to transactionTable

                db.execSQL("INSERT INTO " + DATABASE_TABLE_TRANSACTION
                        + "("
                        + KEY_TRANSACTION_ID + ", "
                        + KEY_TRANSACTION_DATE + ", "
                        + KEY_TRANSACTION_ACCOUNT_ID + ", "
                        + KEY_TRANSACTION_PAYEE_ID + ", "
                        + KEY_TRANSACTION_TRANSACTION_TYPE_ID + ", "
                        + KEY_TRANSACTION_CATEGORY_ID + ", "
                        + KEY_TRANSACTION_AMOUNT + ", "
                        + KEY_TRANSACTION_NOTE + ", "
                        + KEY_TRANSACTION_TRANSFER_ID + ", "
                        + KEY_TRANSACTION_PROJECT_ID

                        + ")"

                        + " SELECT "
                        + KEY_TRANSACTION_ID + ", "
                        + KEY_TRANSACTION_DATE + ", "
                        + KEY_TRANSACTION_ACCOUNT_ID + ", "
                        + KEY_TRANSACTION_PAYEE_ID + ", "
                        + KEY_TRANSACTION_TRANSACTION_TYPE_ID + ", "
                        + KEY_TRANSACTION_CATEGORY_ID + ", "
                        + KEY_TRANSACTION_AMOUNT + ", "
                        + KEY_TRANSACTION_NOTE + ", "
                        + KEY_TRANSACTION_TRANSFER_ID + ", "
                        + KEY_TRANSACTION_PROJECT_ID

                        + " FROM " + DATABASE_TABLE_TRANSACTION_OLD + ";");


                //step 4. Drop transactionTableOld

                db.execSQL("DROP TABLE " + DATABASE_TABLE_TRANSACTION_OLD);
            } else {

            }

        }
    }

    public DbClass(Context c) {
        // this is constructor for the DbClass
        ourContext = c; // here we want to have the context passed into the
        // class to be usable
        // within the class
    }

    public static String[] getCurrencyCodes(Context ctx) {
        // TODO Auto-generated method stub
        String[] currCodes = ctx.getResources().getStringArray(
                R.array.currency_code);

        return currCodes;

    }

    public static String[] getCurrencyNames(Context ctx) {
        // TODO Auto-generated method stub
        String[] currNames = ctx.getResources().getStringArray(
                R.array.currency_name);

        return currNames;
    }

    public static String[] getCountryCode(Context ctx) {
        // TODO Auto-generated method stub
        String[] couCodes = ctx.getResources().getStringArray(
                R.array.country_code);

        return couCodes;
    }

    public static String[] getCountryNames(Context ctx) {
        // TODO Auto-generated method stub
        String[] couNames = ctx.getResources().getStringArray(
                R.array.country_name);

        return couNames;
    }

    public DbClass open() {

        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();


        return this;
    }

    public void close() {

        ourHelper.close();

    }

    public long newAccountEntry(String acDate, String acName, Long acType,
                                Long oA, String acNote, int checkBoxNumber, int isOpen,
                                Long currencyId) {
        // TODO Auto-generated method stub

        // Method called to insert new account
        ContentValues cv = new ContentValues();
        Log.d("acNote", acNote);
        cv.put(KEY_ACCOUNT_DATE, acDate);
        cv.put(KEY_ACCOUNT_NAME, acName);
        cv.put(KEY_ACCOUNT_OPEN_AMOUNT, oA);
        cv.put(KEY_ACCOUNT_NOTE, acNote);
        cv.put(KEY_ACCOUNT_TYPE, acType);
        cv.put(KEY_ACCOUNT_INCLUDE_IN_TOTALS, checkBoxNumber);
        cv.put(KEY_ACCOUNT_OPEN, isOpen);
        cv.put(KEY_ACCOUNT_CURRENCY_ID, currencyId);

        Log.d("in the insert", "the content values were put de");

        return ourDatabase.insert(DATABASE_TABLE_ACCOUNT, null, cv);

    }

    public Cursor getAccountTypes() {
        // TODO Auto-generated method stub
        Log.d("in DbClass", "getAccountTypes is called");
        open();
        String[] columns = {KEY_ACCOUNT_TYPE_ID, KEY_ACCOUNT_TYPE_NAME};

        Cursor c = ourDatabase.query(DATABASE_TABLE_ACCOUNT_TYPE, columns,
                null, null, null, null, null);
        Log.d("in DbClass", "cursor grabbed");

        return c;

    }

    public Cursor getAllAccounts() {
        // TODO Auto-generated method stub
        Log.d("in DbClass", "getAllAccounts() is called");

        String query = "SELECT " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + ", "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME + ", "
                + DATABASE_TABLE_ACCOUNT_TYPE + "." + KEY_ACCOUNT_TYPE_NAME + ", "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_CREDIT_LIMIT + ", "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_CREDIT_PROVIDER + ", "
                + DATABASE_TABLE_CREDIT_CARD_ISSUER + "." + KEY_CARD_ISSUER_NAME
                + ", SUM(" + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + ") AS acTotal FROM "
                + DATABASE_TABLE_ACCOUNT + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT_TYPE + " ON " + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_TYPE + "=" + DATABASE_TABLE_ACCOUNT_TYPE
                + "." + KEY_ACCOUNT_TYPE_ID +
                " LEFT JOIN " + DATABASE_TABLE_CREDIT_CARD_ISSUER
                + " ON " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_CREDIT_CARD_ISSUER_ID + "="
                + DATABASE_TABLE_CREDIT_CARD_ISSUER + "." + KEY_CARD_ISSUER_ID
                + " INNER JOIN " + DATABASE_TABLE_TRANSACTION
                + " ON " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + "="
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + " WHERE " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_OPEN
                + " = 1" + " GROUP BY " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID
                + " ORDER BY " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor accountsCursor = ourDatabase.rawQuery(query, null);


        return accountsCursor;
    }

    public Cursor getAccounts() {
        // TODO Auto-generated method stub
        String[] columns = {KEY_ACCOUNT_ID, KEY_ACCOUNT_NAME};
        String selection = "" + KEY_ACCOUNT_OPEN + "=?";
        String[] selectionArgs = {"1"};
        open();
        Cursor c = ourDatabase.query(DATABASE_TABLE_ACCOUNT, columns,
                selection, selectionArgs, null, null, null);

        return c;
    }

    public Cursor getCategories() {
        // TODO Auto-generated method stub

        String[] columns = {KEY_CATEGORY_ID, KEY_CATEGORY_NAME};

        open();

        Cursor c = ourDatabase.query(DATABASE_TABLE_CATEGORY, columns, null,
                null, null, null, KEY_CATEGORY_NAME);

        return c;
    }

    public Long getAccountTypeId(Long accountId) {

        Long acTypeId;
        String myQuery = "SELECT " + KEY_ACCOUNT_TYPE + " FROM "
                + DATABASE_TABLE_ACCOUNT + " WHERE " + KEY_ACCOUNT_ID + " = " + accountId;

        open();

        Cursor c = ourDatabase.rawQuery(myQuery, null);

        if (c.getCount() > 0) {

            c.moveToFirst();
            acTypeId = c.getLong(c.getColumnIndex(KEY_ACCOUNT_TYPE));
            c.close();

            return acTypeId;
        }

        c.close();

        return null;
    }

    public Cursor getCashTransactionTypes() {


        String myQuery = "SELECT " + KEY_TRANSACTION_TYPE_ID + ", "
                + KEY_TRANSACTION_TYPE_NAME + " FROM "
                + DATABASE_TABLE_TRANSACTION_TYPE + " WHERE "
                + KEY_TRANSACTION_TYPE_ID + " IN ( 1, 2 )";

        open();

        Cursor c = ourDatabase.rawQuery(myQuery, null);

        return c;
    }

    public Cursor getBankTransactionTypes() {
        // TODO Auto-generated method stub
        String myQuery = "SELECT " + KEY_TRANSACTION_TYPE_ID + ", "
                + KEY_TRANSACTION_TYPE_NAME + " FROM "
                + DATABASE_TABLE_TRANSACTION_TYPE + " WHERE "
                + KEY_TRANSACTION_TYPE_ID + " IN ( 3, 4 )";

        open();

        Cursor c = ourDatabase.rawQuery(myQuery, null);

        return c;
    }

    public Cursor getAssetTransactionTypes() {
        // TODO Auto-generated method stub
        String myQuery = "SELECT " + KEY_TRANSACTION_TYPE_ID + ", "
                + KEY_TRANSACTION_TYPE_NAME + " FROM "
                + DATABASE_TABLE_TRANSACTION_TYPE + " WHERE "
                + KEY_TRANSACTION_TYPE_ID + " IN ( 5, 6 )";

        open();

        Cursor c = ourDatabase.rawQuery(myQuery, null);

        return c;
    }

    public void insertNewTransaction(String txDate, Long accountNameId,
                                     String payee, Long categoryNameId, Long txTypeId, Long txAmount,
                                     Long prId, String txNote) {
        // TODO Auto-generated method stub

        String sql = "SELECT " + KEY_PAYEE_ID + " FROM " + DATABASE_TABLE_PAYEE
                + " WHERE " + KEY_PAYEE_NAME + " = '" + payee + "'";

        // we get the id of the inserted payee name from the payee table

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Long payeeId = c.getLong(c.getColumnIndex(KEY_PAYEE_ID));

        c.close();

        ContentValues cv = new ContentValues();
        cv.put(KEY_TRANSACTION_DATE, txDate);
        cv.put(KEY_TRANSACTION_ACCOUNT_ID, accountNameId);
        cv.put(KEY_TRANSACTION_PAYEE_ID, payeeId);
        cv.put(KEY_TRANSACTION_CATEGORY_ID, categoryNameId);
        cv.put(KEY_TRANSACTION_TRANSACTION_TYPE_ID, txTypeId);
        cv.put(KEY_TRANSACTION_AMOUNT, txAmount);
        cv.put(KEY_TRANSACTION_NOTE, txNote);
        cv.put(KEY_TRANSACTION_PROJECT_ID, prId);

        ourDatabase.insert(DATABASE_TABLE_TRANSACTION, null, cv);
    }

    public void insertNewTransaction(String txDate, Long accountNameId,
                                     String payee, Long categoryNameId, Long txTypeId, Long txAmount,
                                     Long prId, String txNote, String photoPath) {
        // TODO Auto-generated method stub

        String sql = "SELECT " + KEY_PAYEE_ID + " FROM " + DATABASE_TABLE_PAYEE
                + " WHERE " + KEY_PAYEE_NAME + " = '" + payee + "'";

        // we get the id of the inserted payee name from the payee table

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Long payeeId = c.getLong(c.getColumnIndex(KEY_PAYEE_ID));

        c.close();

        ContentValues cv = new ContentValues();
        cv.put(KEY_TRANSACTION_DATE, txDate);
        cv.put(KEY_TRANSACTION_ACCOUNT_ID, accountNameId);
        cv.put(KEY_TRANSACTION_PAYEE_ID, payeeId);
        cv.put(KEY_TRANSACTION_CATEGORY_ID, categoryNameId);
        cv.put(KEY_TRANSACTION_TRANSACTION_TYPE_ID, txTypeId);
        cv.put(KEY_TRANSACTION_AMOUNT, txAmount);
        cv.put(KEY_TRANSACTION_NOTE, txNote);
        cv.put(KEY_TRANSACTION_PROJECT_ID, prId);
        cv.put(KEY_TRANSACTION_PHOTO, photoPath);

        ourDatabase.insert(DATABASE_TABLE_TRANSACTION, null, cv);
    }

    public void insertPayee(String payee) {
        // TODO Auto-generated method stub

        // first check if payee exists

        String checkPayee = "SELECT * FROM " + DATABASE_TABLE_PAYEE + " WHERE "
                + KEY_PAYEE_NAME + "='" + payee + "'";

        Cursor c = ourDatabase.rawQuery(checkPayee, null);

        if (c.getCount() > 0) {
            // if payee exists, do nothing
            c.close();
        } else {
            // if payee doesnt exist, add the payee to the table
            c.close();
            ContentValues cv = new ContentValues();

            cv.put(KEY_PAYEE_NAME, payee);

            ourDatabase.insert(DATABASE_TABLE_PAYEE, null, cv);
        }

    }

    public void newAccountEntryInTxTable(String acDate, String acName, Long oA,
                                         String acNote) {
        // TODO Auto-generated method stub

        Long acNameId;
        Long openingBalanceCatId = (long) 2;
        Long transactionTypeId = (long) 7;
        Long payeeId = (long) 1;

        String sql = "SELECT " + KEY_ACCOUNT_ID + " FROM " + DATABASE_TABLE_ACCOUNT
                + " WHERE " + KEY_ACCOUNT_NAME + "= '" + acName + "'";

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        acNameId = c.getLong(c.getColumnIndex(KEY_ACCOUNT_ID));

        c.close();

        ContentValues cv = new ContentValues();

        cv.put(KEY_TRANSACTION_DATE, acDate);
        cv.put(KEY_TRANSACTION_ACCOUNT_ID, acNameId);
        cv.put(KEY_TRANSACTION_AMOUNT, oA);
        cv.put(KEY_TRANSACTION_CATEGORY_ID, openingBalanceCatId);
        cv.put(KEY_TRANSACTION_TRANSACTION_TYPE_ID, transactionTypeId);
        cv.put(KEY_TRANSACTION_PAYEE_ID, payeeId);
        cv.put(KEY_TRANSACTION_NOTE, acNote);

        ourDatabase.insert(DATABASE_TABLE_TRANSACTION, null, cv);

        Log.d("in DbClass", "new account put in tx_table");

    }

    public Long totalTransactionAmount() {
        // TODO Auto-generated method stub

        String totalBalance = "totBalance";
        Long tAmount;

        final String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_INCLUDE_IN_TOTALS + ", sum("
                + KEY_TRANSACTION_AMOUNT + ") AS " + totalBalance + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "="
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_INCLUDE_IN_TOTALS
                + "=1";
        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        tAmount = c.getLong(c.getColumnIndex(totalBalance));

        c.close();

        close();

        return tAmount;
    }

    public Cursor getAllTransactions() {
        // TODO Auto-generated method stub
        Log.d("in DbClass", "getAllTransactions() is called");

        String sql = null;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ourContext);

        if (prefs.getBoolean("pref_history_start_date", false)) {

            ConversionClass mCC = new ConversionClass(ourContext);
            String startDate = prefs.getString("pref_start_date", "now");
            startDate = mCC.dateForDb(startDate);

            sql = "SELECT " + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_ID + ", " + DATABASE_TABLE_TRANSACTION
                    + "." + KEY_TRANSACTION_DATE + ", "
                    + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME + ", "
                    + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME + ", "
                    + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_NAME + ", "
                    + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                    + " FROM " + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                    + DATABASE_TABLE_ACCOUNT + " ON "
                    + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_ACCOUNT_ID + "=" + DATABASE_TABLE_ACCOUNT
                    + "." + KEY_ACCOUNT_ID + " INNER JOIN " + DATABASE_TABLE_PAYEE
                    + " ON " + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                    + "." + KEY_PAYEE_ID + " INNER JOIN "
                    + DATABASE_TABLE_CATEGORY + " ON "
                    + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_CATEGORY_ID + "="
                    + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID
                    + " WHERE " + KEY_TRANSACTION_DATE + " BETWEEN DATE('"
                    + startDate + "') AND DATE('now')" + " ORDER BY DATE("
                    + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                    + ") desc," + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_ID + " desc";

        } else {

            sql = "SELECT " + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_ID + ", " + DATABASE_TABLE_TRANSACTION
                    + "." + KEY_TRANSACTION_DATE + ", "
                    + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME + ", "
                    + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME + ", "
                    + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_NAME + ", "
                    + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                    + " FROM " + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                    + DATABASE_TABLE_ACCOUNT + " ON "
                    + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_ACCOUNT_ID + "=" + DATABASE_TABLE_ACCOUNT
                    + "." + KEY_ACCOUNT_ID + " INNER JOIN " + DATABASE_TABLE_PAYEE
                    + " ON " + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                    + "." + KEY_PAYEE_ID + " INNER JOIN "
                    + DATABASE_TABLE_CATEGORY + " ON "
                    + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_CATEGORY_ID + "="
                    + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID
                    + " ORDER BY DATE(" + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_DATE + ") desc,"
                    + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ID
                    + " desc";

        }

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        return c;
    }

    public void deleteAccountWithId(long accountId) {
        // TODO Auto-generated method stub

        String sqlDelete = "DELETE FROM " + DATABASE_TABLE_ACCOUNT + " WHERE "
                + KEY_ACCOUNT_ID + " = " + accountId + ";";

        String sqlDelFromTx = "DELETE FROM " + DATABASE_TABLE_TRANSACTION
                + " WHERE " + KEY_TRANSACTION_ACCOUNT_ID + " = " + accountId
                + ";";

        ourDatabase.execSQL(sqlDelFromTx);
        ourDatabase.execSQL(sqlDelete);

    }

    public Bundle getTheInfoOfAccountWithId(long accountId) {


        String sql = "SELECT * FROM " + DATABASE_TABLE_ACCOUNT + " WHERE "
                + KEY_ACCOUNT_ID + " = " + accountId;

        Bundle bundle = new Bundle();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        String acName = c.getString(c.getColumnIndex(KEY_ACCOUNT_NAME));
        int acType = c.getInt(c.getColumnIndex(KEY_ACCOUNT_TYPE));
        String acNote = c.getString(c.getColumnIndex(KEY_ACCOUNT_NOTE));
        Long acOpenAmount = c
                .getLong(c.getColumnIndex(KEY_ACCOUNT_OPEN_AMOUNT));
        String acDate = c.getString(c.getColumnIndex(KEY_ACCOUNT_DATE));
        int acInclude = c.getInt(c
                .getColumnIndex(KEY_ACCOUNT_INCLUDE_IN_TOTALS));

        c.close();

        bundle.putInt("acType", acType);
        bundle.putLong("acOpenAmount", acOpenAmount);
        bundle.putString("acName", acName);
        bundle.putString("acNote", acNote);
        bundle.putString("acDate", acDate);
        bundle.putInt("acInclude", acInclude);

        return bundle;

    }

    public Bundle getCreditAccountInfoWithId(long accountId) {
        String sql = "SELECT * FROM " + DATABASE_TABLE_ACCOUNT + " WHERE "
                + KEY_ACCOUNT_ID + " = " + accountId;

        Bundle bundle = new Bundle();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        String acName = c.getString(c.getColumnIndex(KEY_ACCOUNT_NAME));
        int acType = c.getInt(c.getColumnIndex(KEY_ACCOUNT_TYPE));
        String acNote = c.getString(c.getColumnIndex(KEY_ACCOUNT_NOTE));
        Long acOpenAmount = c
                .getLong(c.getColumnIndex(KEY_ACCOUNT_OPEN_AMOUNT));
        String acDate = c.getString(c.getColumnIndex(KEY_ACCOUNT_DATE));
        int acInclude = c.getInt(c
                .getColumnIndex(KEY_ACCOUNT_INCLUDE_IN_TOTALS));

        Long acCreditLimit = c.getLong(c.getColumnIndex(KEY_ACCOUNT_CREDIT_LIMIT));
        Long acCardIssuerId = c.getLong(c.getColumnIndex(KEY_ACCOUNT_CREDIT_CARD_ISSUER_ID));
        String acCreditProvider = c.getString(c.getColumnIndex(KEY_ACCOUNT_CREDIT_PROVIDER));


        c.close();

        bundle.putLong("acCreditLimit", acCreditLimit);
        bundle.putLong("acCardIssuerId", acCardIssuerId);
        bundle.putString("acCreditProvider", acCreditProvider);

        bundle.putInt("acType", acType);
        bundle.putLong("acOpenAmount", acOpenAmount);
        bundle.putString("acName", acName);
        bundle.putString("acNote", acNote);
        bundle.putString("acDate", acDate);
        bundle.putInt("acInclude", acInclude);

        return bundle;
    }

    public Cursor getAcTransactions() {

        Log.d("ViewAc", "inside the DBCLASS");

        String sql = "SELECT " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_ID + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + ", " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "="
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " INNER JOIN " + DATABASE_TABLE_CATEGORY
                + " ON " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + OverviewActivity.idOfEntity + " ORDER BY DATE("
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + ") desc," + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_ID + " desc";

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        return c;
    }

    public String getTitle(Long idOfEntity, int whichOverview) {

        String nameColumn = KEY_ACCOUNT_NAME;
        String table = DATABASE_TABLE_ACCOUNT;
        String idColumn = KEY_ACCOUNT_ID;

        if (whichOverview == OverviewActivity.KEY_ACCOUNT_OVERVIEW) {

            nameColumn = KEY_ACCOUNT_NAME;
            table = DATABASE_TABLE_ACCOUNT;
            idColumn = KEY_ACCOUNT_ID;
        } else if (whichOverview == OverviewActivity.KEY_PROJECT_OVERVIEW) {

            nameColumn = KEY_PROJECT_NAME;
            table = DATABASE_TABLE_PROJECT;
            idColumn = KEY_PROJECT_ID;
        }

        String sql = "SELECT " + nameColumn + " FROM "
                + table + " WHERE " + idColumn + "=" + idOfEntity;

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        String title = c.getString(c.getColumnIndex(nameColumn));
        c.close();
        return title;

    }

    public void updateAccount(String acName, Long acType, Long oA,
                              String acNote, Long accId, int checkBoxNumberEdit) {
        // TODO Auto-generated method stub
        String sql = "UPDATE " + DATABASE_TABLE_ACCOUNT + " SET "
                + KEY_ACCOUNT_NAME + "='" + acName + "', " + KEY_ACCOUNT_TYPE
                + "=" + acType + ", " + KEY_ACCOUNT_OPEN_AMOUNT + "=" + oA
                + ", " + KEY_ACCOUNT_NOTE + "='" + acNote + "', "
                + KEY_ACCOUNT_INCLUDE_IN_TOTALS + "=" + checkBoxNumberEdit
                + " WHERE " + KEY_ACCOUNT_ID + "=" + accId + ";";

        String sqlTx = "UPDATE " + DATABASE_TABLE_TRANSACTION + " SET "
                + KEY_TRANSACTION_AMOUNT + "=" + oA + " WHERE "
                + KEY_TRANSACTION_ACCOUNT_ID + "=" + accId + " AND "
                + KEY_TRANSACTION_CATEGORY_ID + "= 2;";

        ourDatabase.execSQL(sql);
        ourDatabase.execSQL(sqlTx);

    }

    public void updateAccount(String acName, Long acType, Long oA,
                              String acNote, Long accEditId, int checkBoxNumber,
                              Long cardLimitForDb, Long cardIssuer, String creditProvider) {
        //update account if it is a CREDIT CARD TYPE

        ContentValues cv = new ContentValues();

        cv.put(KEY_ACCOUNT_NAME, acName);
        cv.put(KEY_ACCOUNT_TYPE, acType);
        cv.put(KEY_ACCOUNT_OPEN_AMOUNT, oA);
        cv.put(KEY_ACCOUNT_NOTE, acNote);
        cv.put(KEY_ACCOUNT_INCLUDE_IN_TOTALS, checkBoxNumber);
        cv.put(KEY_ACCOUNT_CREDIT_LIMIT, cardLimitForDb);
        cv.put(KEY_ACCOUNT_CREDIT_CARD_ISSUER_ID, cardIssuer);
        cv.put(KEY_ACCOUNT_CREDIT_PROVIDER, creditProvider);

        ourDatabase.update(DATABASE_TABLE_ACCOUNT, cv, " " + KEY_ACCOUNT_ID + " = " + accEditId, null);


        ContentValues cvTx = new ContentValues();
        cvTx.put(KEY_TRANSACTION_AMOUNT, oA);

        ourDatabase.update(DATABASE_TABLE_TRANSACTION, cvTx, " " + KEY_TRANSACTION_ACCOUNT_ID + " = " + accEditId + " AND "
                + KEY_TRANSACTION_CATEGORY_ID + "= 2", null);
    }

    public Long getCurrentBalance(long accountId) {
        // TODO Auto-generated method stub

        String sql = "SELECT SUM(" + KEY_TRANSACTION_AMOUNT + ") AS TOTAL"
                + " FROM " + DATABASE_TABLE_TRANSACTION + " WHERE "
                + KEY_TRANSACTION_ACCOUNT_ID + "=" + accountId;

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Long f = c.getLong(c.getColumnIndex("TOTAL"));

        c.close();
        return f;
    }

    public Bundle getTransactionWithId(long transactionId) {
        // TODO Auto-generated method stub

        String txQuery = "SELECT * FROM " + DATABASE_TABLE_TRANSACTION
                + " WHERE " + KEY_TRANSACTION_ID + "=" + transactionId;

        Cursor c = ourDatabase.rawQuery(txQuery, null);

        c.moveToFirst();

        String tDate = c.getString(c.getColumnIndex(KEY_TRANSACTION_DATE));
        Long tAcId = c.getLong(c.getColumnIndex(KEY_TRANSACTION_ACCOUNT_ID));
        Long tPayeeId = c.getLong(c.getColumnIndex(KEY_TRANSACTION_PAYEE_ID));
        Long tTxTypeId = c.getLong(c
                .getColumnIndex(KEY_TRANSACTION_TRANSACTION_TYPE_ID));
        Long tAmount = c.getLong(c.getColumnIndex(KEY_TRANSACTION_AMOUNT));
        Long tCatId = c.getLong(c.getColumnIndex(KEY_TRANSACTION_CATEGORY_ID));
        String note = c.getString(c.getColumnIndex(KEY_TRANSACTION_NOTE));
        Long projectId = c
                .getLong(c.getColumnIndex(KEY_TRANSACTION_PROJECT_ID));
        Long transferId = c.getLong(c
                .getColumnIndex(KEY_TRANSACTION_TRANSFER_ID));
        String photoPath = c.getString(c.getColumnIndex(KEY_TRANSACTION_PHOTO));

        c.close();

        Bundle bundle = new Bundle();
        String payeeQuery = "SELECT " + KEY_PAYEE_NAME + " FROM "
                + DATABASE_TABLE_PAYEE + " WHERE " + KEY_PAYEE_ID + "="
                + tPayeeId;

        Cursor cursor = ourDatabase.rawQuery(payeeQuery, null);

        cursor.moveToFirst();
        String payeeName = cursor.getString(cursor
                .getColumnIndex(KEY_PAYEE_NAME));
        cursor.close();

        if (tAmount < 0) {
            bundle.putBoolean("amountIsNegative", true);
            tAmount = tAmount + (-2 * tAmount);
        } else {
            bundle.putBoolean("amountIsNegative", false);
        }

        bundle.putString("tDate", tDate);
        bundle.putLong("tAcId", tAcId);
        bundle.putString("payeeName", payeeName);
        bundle.putLong("tTxTypeId", tTxTypeId);
        bundle.putLong("tCatId", tCatId);
        bundle.putLong("tAmount", tAmount);
        bundle.putLong("projectId", projectId);
        bundle.putString("note", note);
        bundle.putLong("transferId", transferId);
        bundle.putString("photoPath", photoPath);

        return bundle;
    }

    public void deleteTxWithId(long transactionId) {
        // TODO Auto-generated method stub

        // first check if this was a transfer
        String transferCheck = "SELECT " + KEY_TRANSACTION_TRANSFER_ID
                + " FROM " + DATABASE_TABLE_TRANSACTION + " WHERE "
                + KEY_TRANSACTION_ID + " = " + transactionId;

        Cursor c = ourDatabase.rawQuery(transferCheck, null);
        Long transferId;

        c.moveToFirst();

        transferId = c.getLong(c.getColumnIndex(KEY_TRANSACTION_TRANSFER_ID));

        c.close();
        if (transferId != null && transferId > 0) {

            String sqlDel = "DELETE FROM " + DATABASE_TABLE_TRANSACTION
                    + " WHERE " + KEY_TRANSACTION_TRANSFER_ID + " = "
                    + transferId;

            ourDatabase.execSQL(sqlDel);

        } else {

            String sql = "DELETE FROM " + DATABASE_TABLE_TRANSACTION
                    + " WHERE " + KEY_TRANSACTION_ID + "=" + transactionId
                    + ";";

            ourDatabase.execSQL(sql);
        }

    }

    public void updateTransaction(String txDate, Long accountNameId,
                                  String payee, Long categoryNameId, Long txTypeId, Long txAmount,
                                  long long1, Long projectId, String txNote) {


        String sql = "SELECT DISTINCT " + KEY_PAYEE_NAME + "," + KEY_PAYEE_ID
                + " FROM " + DATABASE_TABLE_PAYEE + " WHERE " + KEY_PAYEE_NAME
                + " = '" + payee + "'";

        // we get the id of the inserted payee name from the payee table

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Long payeeId = c.getLong(c.getColumnIndex(KEY_PAYEE_ID));

        c.close();

        String updateSql = "UPDATE " + DATABASE_TABLE_TRANSACTION + " SET "
                + KEY_TRANSACTION_DATE + "='" + txDate + "', "
                + KEY_TRANSACTION_ACCOUNT_ID + "=" + accountNameId + ", "
                + KEY_TRANSACTION_PAYEE_ID + "=" + payeeId + ", "
                + KEY_TRANSACTION_CATEGORY_ID + "=" + categoryNameId + ", "
                + KEY_TRANSACTION_TRANSACTION_TYPE_ID + "=" + txTypeId + ", "
                + KEY_TRANSACTION_AMOUNT + "=" + txAmount + ", "
                + KEY_TRANSACTION_PROJECT_ID + "= " + projectId + ", "
                + KEY_TRANSACTION_NOTE + "='" + txNote + "' WHERE "
                + KEY_TRANSACTION_ID + "=" + long1 + ";";

        ourDatabase.execSQL(updateSql);


    }

    public void updateTransaction(String txDate, Long accountNameId,
                                  String payee, Long categoryNameId, Long txTypeId, Long txAmount,
                                  long long1, Long projectId, String txNote, String photoPath) {
        // TODO Auto-generated method stub

        String sql = "SELECT DISTINCT " + KEY_PAYEE_NAME + "," + KEY_PAYEE_ID
                + " FROM " + DATABASE_TABLE_PAYEE + " WHERE " + KEY_PAYEE_NAME
                + " = '" + payee + "'";

        // we get the id of the inserted payee name from the payee table

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Long payeeId = c.getLong(c.getColumnIndex(KEY_PAYEE_ID));

        c.close();

        String updateSql = "UPDATE " + DATABASE_TABLE_TRANSACTION + " SET "
                + KEY_TRANSACTION_DATE + "='" + txDate + "', "
                + KEY_TRANSACTION_ACCOUNT_ID + "=" + accountNameId + ", "
                + KEY_TRANSACTION_PAYEE_ID + "=" + payeeId + ", "
                + KEY_TRANSACTION_CATEGORY_ID + "=" + categoryNameId + ", "
                + KEY_TRANSACTION_TRANSACTION_TYPE_ID + "=" + txTypeId + ", "
                + KEY_TRANSACTION_AMOUNT + "=" + txAmount + ", "
                + KEY_TRANSACTION_PHOTO + "='" + photoPath + "', "
                + KEY_TRANSACTION_PROJECT_ID + "= " + projectId + ", "
                + KEY_TRANSACTION_NOTE + "='" + txNote + "' WHERE "
                + KEY_TRANSACTION_ID + "=" + long1 + ";";

        ourDatabase.execSQL(updateSql);


    }

    public void insertOrCheckForPayee(String payee) {
        // TODO Auto-generated method stub
        String sqlCheck = "SELECT " + KEY_PAYEE_NAME + " FROM "
                + DATABASE_TABLE_PAYEE + " WHERE " + KEY_PAYEE_NAME + "='"
                + payee + "'";
        open();
        Log.d("insertOrCheck", "reached here");
        Cursor c = ourDatabase.rawQuery(sqlCheck, null);

        if (c.getCount() > 0) {

        } else {
            ContentValues cv = new ContentValues();

            cv.put(KEY_PAYEE_NAME, payee);

            ourDatabase.insert(DATABASE_TABLE_PAYEE, null, cv);
        }

        c.close();
        close();

    }

    public Bundle getTxInfo(long transactionId) {
        // TODO Auto-generated method stub
        String sql = "SELECT " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_ID + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + ", " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + ", " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_NOTE + ", " + DATABASE_TABLE_PROJECT
                + "." + KEY_PROJECT_NAME + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "="
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " INNER JOIN " + DATABASE_TABLE_CATEGORY
                + " ON " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " INNER JOIN "
                + DATABASE_TABLE_PROJECT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PROJECT_ID + " = "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ID + "="
                + transactionId;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Bundle bundle = new Bundle();

        bundle.putString(
                "txdate",
                c.getString(c.getColumnIndex(DATABASE_TABLE_TRANSACTION + "."
                        + KEY_TRANSACTION_DATE)));
        bundle.putString(
                "txacc",
                c.getString(c.getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                        + KEY_ACCOUNT_NAME)));
        bundle.putString(
                "txpayee",
                c.getString(c.getColumnIndex(DATABASE_TABLE_PAYEE + "."
                        + KEY_PAYEE_NAME)));
        bundle.putString(
                "txcat",
                c.getString(c.getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                        + KEY_CATEGORY_NAME)));
        bundle.putLong(
                "txamt",
                c.getLong(c.getColumnIndex(DATABASE_TABLE_TRANSACTION + "."
                        + KEY_TRANSACTION_AMOUNT)));

        bundle.putString(
                "txnote",
                c.getString(c.getColumnIndex(DATABASE_TABLE_TRANSACTION + "."
                        + KEY_TRANSACTION_NOTE)));

        String project = c.getString(c.getColumnIndex(DATABASE_TABLE_PROJECT
                + "." + KEY_PROJECT_NAME));

        bundle.putString("txproject", project);

        c.close();
        return bundle;
    }

    public Bundle getAccountIncomeTotals() {

        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + KEY_TRANSACTION_AMOUNT + ">= 0" + " GROUP BY "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] accountName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                accountName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("accountName", accountName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getAccountExpenseTotals() {

        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + KEY_TRANSACTION_AMOUNT + "<= 0" + " GROUP BY "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] accountName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                Log.d("checking",
                        "accountName = "
                                + c.getString(c
                                .getColumnIndex(DATABASE_TABLE_ACCOUNT
                                        + "." + KEY_ACCOUNT_NAME))
                                + "|| AcAmount = "
                                + c.getFloat(c.getColumnIndex("acAmount")));

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                accountName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("accountName", accountName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getCategoryExpenseTotals() {

        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_CATEGORY + " ON "
                + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + "<= 0" + " GROUP BY " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] categoryName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                categoryName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("categoryName", categoryName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getCategoryIncomeTotals() {

        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_CATEGORY + " ON "
                + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + ">= 0" + " GROUP BY " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] categoryName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                categoryName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("categoryName", categoryName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getPayeeExpenseTotals() {
        // TODO Auto-generated method stub

        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", SUM(" + KEY_TRANSACTION_AMOUNT + ") AS acAmount FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + "<= 0" + " GROUP BY " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] payeeName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                payeeName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("payeeName", payeeName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getPayeeIncomeTotals() {
        // TODO Auto-generated method stub

        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", SUM(" + KEY_TRANSACTION_AMOUNT + ") AS acAmount FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + ">= 0" + " GROUP BY " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] payeeName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                payeeName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("payeeName", payeeName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public void getAmountBtnDates() {

        String sql = "SELECT SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS totDate FROM " + DATABASE_TABLE_TRANSACTION + " WHERE "
                + KEY_TRANSACTION_DATE + "= date('now')";

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c != null) {
            c.moveToFirst();

            Log.d("dateQuery",
                    Float.toString(c.getFloat(c.getColumnIndex("totDate"))));
        }

        c.close();

    }

    public boolean checkForAccounts() {
        // TODO Auto-generated method stub

        String sql = "SELECT * FROM " + DATABASE_TABLE_ACCOUNT + " WHERE "
                + KEY_ACCOUNT_OPEN + "= 1";

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Bundle getInfoForReport() {
        // TODO Auto-generated method stub

        Bundle bundle = new Bundle();
        // SQL statement to get total income
        String totIncSql = "SELECT SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS totInc FROM " + DATABASE_TABLE_TRANSACTION + " WHERE "
                + KEY_TRANSACTION_AMOUNT + ">0";

        // SQL statement to get total expenses
        String totExpSql = "SELECT SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS totExp FROM " + DATABASE_TABLE_TRANSACTION + " WHERE "
                + KEY_TRANSACTION_AMOUNT + "<0";

        // SQL statement to get total amount for assets
        String totAssetsSql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_TYPE + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS totAssets FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_TYPE + "= 3";

        // SQL statement to get total amount for liabilites
        String totLiabilitiesSql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_TYPE + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS totLiabilities FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_TYPE + "= 4";

        // SQL statement to get total amount for cash
        String totCashSql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_TYPE + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS totCash FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_TYPE + "= 1";

        // SQL statement to get total amount for bank
        String totBankSql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_TYPE + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS totBank FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_TYPE + "= 2";

        // SQL statement to get total amount for bank
        String totSql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_TYPE + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS tot FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID;

        Cursor totIncCursor = ourDatabase.rawQuery(totIncSql, null);
        if (totIncCursor.getCount() > 0) {

            totIncCursor.moveToFirst();

            bundle.putLong("totInc",
                    totIncCursor.getLong(totIncCursor.getColumnIndex("totInc")));

        } else {
            bundle.putLong("totInc", 0);
        }

        totIncCursor.close();

        Cursor totExpCursor = ourDatabase.rawQuery(totExpSql, null);
        if (totExpCursor.getCount() > 0) {

            totExpCursor.moveToFirst();

            bundle.putLong("totExp",
                    totExpCursor.getLong(totExpCursor.getColumnIndex("totExp"))
                            * (-1));

        } else {
            bundle.putLong("totExp", 0);
        }

        totExpCursor.close();

        Cursor totAssetsCursor = ourDatabase.rawQuery(totAssetsSql, null);
        if (totAssetsCursor.getCount() > 0) {

            totAssetsCursor.moveToFirst();

            bundle.putLong("totAssets", totAssetsCursor.getLong(totAssetsCursor
                    .getColumnIndex("totAssets")));

        } else {
            bundle.putLong("totAssets", 0);
        }

        totAssetsCursor.close();

        Cursor totLiabilitiesCursor = ourDatabase.rawQuery(totLiabilitiesSql,
                null);
        if (totLiabilitiesCursor.getCount() > 0) {

            totLiabilitiesCursor.moveToFirst();

            bundle.putLong("totLiabilities", totLiabilitiesCursor
                    .getLong(totLiabilitiesCursor
                            .getColumnIndex("totLiabilities")));

        } else {
            bundle.putLong("totLiabilities", 0);
        }

        totLiabilitiesCursor.close();

        Cursor totCashCursor = ourDatabase.rawQuery(totCashSql, null);
        if (totCashCursor.getCount() > 0) {

            totCashCursor.moveToFirst();

            bundle.putLong("totCash", totCashCursor.getLong(totCashCursor
                    .getColumnIndex("totCash")));

        } else {
            bundle.putLong("totCash", 0);
        }

        totCashCursor.close();

        Cursor totBankCursor = ourDatabase.rawQuery(totBankSql, null);
        if (totBankCursor.getCount() > 0) {

            totBankCursor.moveToFirst();

            bundle.putLong("totBank", totBankCursor.getLong(totBankCursor
                    .getColumnIndex("totBank")));

        } else {
            bundle.putLong("totBank", 0);
        }

        totBankCursor.close();

        Cursor totCursor = ourDatabase.rawQuery(totSql, null);
        if (totCursor.getCount() > 0) {

            totCursor.moveToFirst();

            bundle.putLong("tot",
                    totCursor.getLong(totCursor.getColumnIndex("tot")));

        } else {
            bundle.putLong("tot", 0);
        }

        totBankCursor.close();

        return bundle;
    }

    public void addNewCat(String catName) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();

        cv.put(KEY_CATEGORY_NAME, catName);
        ourDatabase.insert(DATABASE_TABLE_CATEGORY, null, cv);

    }

    public Cursor getPayeeName() {
        // TODO Auto-generated method stub

        String sql = "SELECT " + KEY_PAYEE_NAME + ", " + KEY_PAYEE_ID
                + " FROM " + DATABASE_TABLE_PAYEE + " ORDER BY "
                + KEY_PAYEE_NAME;
        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            return c;
        } else
            return null;

    }

    public String getCategoryNameAtId(long id) {
        // TODO Auto-generated method stub

        String catName;
        String sql = "SELECT " + KEY_CATEGORY_NAME + " FROM "
                + DATABASE_TABLE_CATEGORY + " WHERE " + KEY_CATEGORY_ID + " = "
                + id;

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            c.moveToFirst();
            catName = c.getString(c.getColumnIndex(KEY_CATEGORY_NAME));
            c.close();
            return catName;
        } else

            return null;
    }

    public void updateCat(String catName, long id) {
        // TODO Auto-generated method stub
        String sql = "UPDATE " + DATABASE_TABLE_CATEGORY + " SET "
                + KEY_CATEGORY_NAME + "='" + catName + "' WHERE "
                + KEY_CATEGORY_ID + "=" + id;

        ourDatabase.execSQL(sql);

    }

    public void deleteCategoryWithId(long id) {
        // TODO Auto-generated method stub

        String updateSql = "UPDATE " + DATABASE_TABLE_TRANSACTION + " SET "
                + KEY_TRANSACTION_CATEGORY_ID + "= 1 WHERE "
                + KEY_TRANSACTION_CATEGORY_ID + "=" + id;

        String deleteSql = "DELETE FROM " + DATABASE_TABLE_CATEGORY + " WHERE "
                + KEY_CATEGORY_ID + "=" + id;

        ourDatabase.execSQL(updateSql);
        ourDatabase.execSQL(deleteSql);

    }

    public Cursor getPayee2Name() {
        // TODO Auto-generated method stub

        String sql = "SELECT * FROM " + DATABASE_TABLE_PAYEE;// + " WHERE " +
        // KEY_PAYEE_NAME
        // + " LIKE %" +
        // str +"%";

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            return c;
        } else
            return null;

    }

    public Long getNewCatId(String newCatNem) {
        // TODO Auto-generated method stub

        Long id;
        String sql = "SELECT " + KEY_CATEGORY_ID + " FROM "
                + DATABASE_TABLE_CATEGORY + " WHERE " + KEY_CATEGORY_NAME
                + "='" + newCatNem + "'";

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        id = c.getLong(c.getColumnIndex(KEY_CATEGORY_ID));

        c.close();

        return id;
    }

    public void insertNewTransferTransaction(Long accountFromId,
                                             String accountFromName, Long accountToId, String accountToName,
                                             String dateString, Float trAmount, int transferTxLink) {
        // TODO Auto-generated method stub

    }

    public void closeAccountWithId(long accountId) {
        // TODO Auto-generated method stub

        String sql = "UPDATE " + DATABASE_TABLE_ACCOUNT + " SET "
                + KEY_ACCOUNT_OPEN + " = 0 WHERE " + KEY_ACCOUNT_ID + "=" + accountId
                + ";";
        ourDatabase.execSQL(sql);
    }

    public Cursor getProjects() {
        // TODO Auto-generated method stub

        String projectSql = "SELECT * FROM " + DATABASE_TABLE_PROJECT
                + " WHERE " + KEY_PROJECT_OPEN + "=1";
        open();
        Cursor c = ourDatabase.rawQuery(projectSql, null);

        if (c.getCount() > 0) {
            return c;
        } else
            return null;
    }

    public void insertNewProject(String prName, String prNote, int open) {
        // TODO Auto-generated method stub

        // if(prNote == null){prNote = "_";}
        ContentValues cv = new ContentValues();

        cv.put(KEY_PROJECT_NAME, prName);
        cv.put(KEY_PROJECT_NOTE, prNote);
        cv.put(KEY_PROJECT_OPEN, open);

        ourDatabase.insert(DATABASE_TABLE_PROJECT, null, cv);

    }

    public Cursor getAllOpenProjects() {
        // TODO Auto-generated method stub

        String projectSql = "SELECT * FROM " + DATABASE_TABLE_PROJECT
                + " WHERE " + KEY_PROJECT_OPEN + " = 1 " + " ORDER BY "
                + KEY_PROJECT_NAME;
        open();
        Cursor c = ourDatabase.rawQuery(projectSql, null);

        if (c.getCount() > 0) {
            return c;
        } else
            return null;
    }

    public Cursor getCatTransactions() {
        // TODO Auto-generated method stub
        Log.d("CatTxView", "inside the DBCLASS");

        String sql = "SELECT " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_ID + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + ", " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "="
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " INNER JOIN " + DATABASE_TABLE_CATEGORY
                + " ON " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + CategoryTxView.catViewId
                + " ORDER BY DATE(" + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + ") desc," + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ID + " desc";

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        return c;
    }

    public Long totalTransactionAmountOfCat(Long catViewId) {
        // TODO Auto-generated method stub
        String totalBalance = "totBalance";
        Long tAmount;

        final String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_INCLUDE_IN_TOTALS + ", sum("
                + KEY_TRANSACTION_AMOUNT + ") AS " + totalBalance + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "="
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_INCLUDE_IN_TOTALS
                + "=1 AND " + KEY_TRANSACTION_CATEGORY_ID + "=" + catViewId;
        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        tAmount = c.getLong(c.getColumnIndex(totalBalance));

        c.close();

        return tAmount;
    }

    public Long totalTransactionAmountOfPayee(Long payeeViewId) {
        // TODO Auto-generated method stub
        String totalBalance = "totBalance";
        Long tAmount;

        final String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_INCLUDE_IN_TOTALS + ", sum("
                + KEY_TRANSACTION_AMOUNT + ") AS " + totalBalance + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "="
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_INCLUDE_IN_TOTALS
                + "=1 AND " + KEY_TRANSACTION_PAYEE_ID + "=" + payeeViewId;
        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        tAmount = c.getLong(c.getColumnIndex(totalBalance));

        c.close();

        return tAmount;
    }

    public void updatePayee(String payeeName, long id) {
        // TODO Auto-generated method stub

        String sql = "UPDATE " + DATABASE_TABLE_PAYEE + " SET "
                + KEY_PAYEE_NAME + "='" + payeeName + "' WHERE " + KEY_PAYEE_ID
                + "=" + id;

        ourDatabase.execSQL(sql);

    }

    public void deletePayeeyWithId(long id) {
        // TODO Auto-generated method stub
        String updateSql = "UPDATE " + DATABASE_TABLE_TRANSACTION + " SET "
                + KEY_TRANSACTION_PAYEE_ID + "= 2 WHERE "
                + KEY_TRANSACTION_PAYEE_ID + "=" + id;

        String deleteSql = "DELETE FROM " + DATABASE_TABLE_PAYEE + " WHERE "
                + KEY_PAYEE_ID + "=" + id;

        ourDatabase.execSQL(updateSql);
        ourDatabase.execSQL(deleteSql);

    }

    public Cursor getPayeeTransactions() {
        // TODO Auto-generated method stub
        String sql = "SELECT " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_ID + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + ", " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "="
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " INNER JOIN " + DATABASE_TABLE_CATEGORY
                + " ON " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_PAYEE_ID
                + "=" + PayeeTxView.payeeViewId + " ORDER BY DATE("
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + ") desc," + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_ID + " desc";

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        return c;
    }

    public String[] getDistinctCurrencyCodes() {
        // TODO Auto-generated method stub

        String sql = "SELECT DISTINCT " + KEY_CURRENCY_NAME + ","
                + KEY_CURRENCY_CODE + " FROM "
                + DATABASE_TABLE_COUNTRY_CURRENCY + " ORDER BY "
                + KEY_CURRENCY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        int size = c.getCount();
        int count;

        String[] currCodes = new String[size];

        for (c.moveToFirst(), count = 0; !c.isAfterLast(); c.moveToNext(), count++) {

            currCodes[count] = c.getString(c.getColumnIndex(KEY_CURRENCY_CODE));

        }

        c.close();

        close();
        return currCodes;

    }

    public String[] getDistinctCurrencyNames() {
        // TODO Auto-generated method stub

        String sql = "SELECT DISTINCT " + KEY_CURRENCY_NAME + ","
                + KEY_CURRENCY_CODE + " FROM "
                + DATABASE_TABLE_COUNTRY_CURRENCY + " ORDER BY "
                + KEY_CURRENCY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        int size = c.getCount();
        int count;

        String[] currNames = new String[size];

        for (c.moveToFirst(), count = 0; !c.isAfterLast(); c.moveToNext(), count++) {

            Log.d("in for2", "in for2" + count);
            currNames[count] = c.getString(c.getColumnIndex(KEY_CURRENCY_CODE))
                    + " (" + c.getString(c.getColumnIndex(KEY_CURRENCY_NAME))
                    + ")";

        }

        c.close();
        return currNames;

    }

    public Cursor getCategoryTotals() {
        // TODO Auto-generated method stub

        String catExpenseTotal = "catExpenseTotal", catIncomeTotal = "catIncomeTotal";

        String sql3 = "SELECT " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_ID + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " < 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + catExpenseTotal
                + ",  SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + "> 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END ) AS " + catIncomeTotal + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_CATEGORY + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_CATEGORY_ID + " = "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID
                + " GROUP BY " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME;

        Cursor c = ourDatabase.rawQuery(sql3, null);

        Log.d("col_index catExpTot", "" + c.getColumnIndex(catExpenseTotal));
        Log.d("col_index catIncTot", "" + c.getColumnIndex(catIncomeTotal));

        Log.d("col_index catName",
                ""
                        + c.getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                        + KEY_CATEGORY_NAME));

        if (c.getCount() > 0) {

            String catName;
            Float expenseTotal, incomeTotal;

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                catName = c.getString(c.getColumnIndex(DATABASE_TABLE_CATEGORY
                        + "." + KEY_CATEGORY_NAME));
                expenseTotal = c.getFloat(c.getColumnIndex(catExpenseTotal));
                incomeTotal = c.getFloat(c.getColumnIndex(catIncomeTotal));

                Log.d("category Name", catName);
                Log.d("expense Total", "" + expenseTotal);
                Log.d("income Total", "" + incomeTotal);

                Log.d("=====================",
                        "=======================================");

            }

            return c;

        } else
            return null;
    }

    public Cursor getMyCurrencies() {
        // TODO Auto-generated method stub

        open();

        String sql = "SELECT " + KEY_MY_CURRENCY_ID + ", "
                + KEY_MY_CURRENCY_CURRENCY_CODE + " FROM "
                + DATABASE_TABLE_MY_CURRENCY;

        Cursor c = ourDatabase.rawQuery(sql, null);

        return c;
    }

    public void addNewMyCurrency(String toAdd) {
        // TODO Auto-generated method stub
        open();

        ContentValues cv = new ContentValues();

        cv.put(KEY_MY_CURRENCY_CURRENCY_CODE, toAdd);

        ourDatabase.insert(DATABASE_TABLE_MY_CURRENCY, null, cv);

        close();

    }

    public void newTransferFromThisAccount(String dateString,
                                           String accountFromName, String accountToName, Long accountFromId,
                                           Long accountToId, Long trAmountMinus, Long trAmountPlus,
                                           Long spend, Long receive, Long categoryId, String transferNote,
                                           Long transferNumber) {
        // TODO Auto-generated method stub

        //
        // we start with the account losing
        //

        String sql = "SELECT " + KEY_PAYEE_ID + " FROM " + DATABASE_TABLE_PAYEE
                + " WHERE " + KEY_PAYEE_NAME + " = '" + accountToName + "'";

        // we get the id of the inserted payee name from the payee table

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Long payee_AccountToPayeeId = c.getLong(c.getColumnIndex(KEY_PAYEE_ID));

        c.close();

        open();

        ContentValues cv = new ContentValues();

        cv.put(KEY_TRANSACTION_DATE, dateString);
        cv.put(KEY_TRANSACTION_ACCOUNT_ID, accountFromId);
        cv.put(KEY_TRANSACTION_PAYEE_ID, payee_AccountToPayeeId);
        cv.put(KEY_TRANSACTION_TRANSACTION_TYPE_ID, spend);
        cv.put(KEY_TRANSACTION_CATEGORY_ID, categoryId);
        cv.put(KEY_TRANSACTION_AMOUNT, trAmountMinus);
        cv.put(KEY_TRANSACTION_NOTE, transferNote);
        cv.put(KEY_TRANSACTION_TRANSFER_ID, transferNumber);

        ourDatabase.insert(DATABASE_TABLE_TRANSACTION, null, cv);

        //
        // now with the account gaining
        //

        String sql2 = "SELECT " + KEY_PAYEE_ID + " FROM "
                + DATABASE_TABLE_PAYEE + " WHERE " + KEY_PAYEE_NAME + " = '"
                + accountFromName + "'";

        // we get the id of the inserted payee name from the payee table

        Cursor cu = ourDatabase.rawQuery(sql2, null);

        cu.moveToFirst();

        Long payee_AccountFromPayeeId = cu.getLong(cu
                .getColumnIndex(KEY_PAYEE_ID));

        cu.close();

        open();

        ContentValues cvs = new ContentValues();

        cvs.put(KEY_TRANSACTION_DATE, dateString);
        cvs.put(KEY_TRANSACTION_ACCOUNT_ID, accountToId);
        cvs.put(KEY_TRANSACTION_PAYEE_ID, payee_AccountFromPayeeId);
        cvs.put(KEY_TRANSACTION_TRANSACTION_TYPE_ID, receive);
        cvs.put(KEY_TRANSACTION_CATEGORY_ID, categoryId);
        cvs.put(KEY_TRANSACTION_AMOUNT, trAmountPlus);
        cvs.put(KEY_TRANSACTION_NOTE, transferNote);
        cvs.put(KEY_TRANSACTION_TRANSFER_ID, transferNumber);

        ourDatabase.insert(DATABASE_TABLE_TRANSACTION, null, cvs);

        close();
    }

    public Cursor getRecurOptions() {
        // TODO Auto-generated method stub
        open();

        String sql = "SELECT * FROM " + DATABASE_TABLE_SCHEDULED_RECURRENCE;

        Cursor c = ourDatabase.rawQuery(sql, null);

        return c;
    }

    public void addNewSchTx(String startDate, Long recurId, String endDate,
                            Long accountId, String payee, Long categoryId,
                            Long transactionTypeId, Long amount, String note, Long projectId,
                            int doneNo, Integer alarmId) {
        // TODO Auto-generated method stub

        String sql = "SELECT " + KEY_PAYEE_ID + " FROM " + DATABASE_TABLE_PAYEE
                + " WHERE " + KEY_PAYEE_NAME + " = '" + payee + "'";

        // we get the id of the inserted payee name from the payee table
        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Long payeeId = c.getLong(c.getColumnIndex(KEY_PAYEE_ID));

        c.close();

        ContentValues cv = new ContentValues();

        cv.put(KEY_SCHEDULED_START_DATE, startDate);
        cv.put(KEY_SCHEDULED_SCH_RECURRENCE_ID, recurId);
        cv.put(KEY_SCHEDULED_END_DATE, endDate);
        cv.put(KEY_SCHEDULED_ACCOUNT_ID, accountId);
        cv.put(KEY_SCHEDULED_TRANSACTION_TYPE_ID, transactionTypeId);
        cv.put(KEY_SCHEDULED_CATEGORY_ID, categoryId);
        cv.put(KEY_SCHEDULED_AMOUNT, amount);
        cv.put(KEY_SCHEDULED_NOTE, note);
        cv.put(KEY_SCHEDULED_PROJECT_ID, projectId);
        cv.put(KEY_SCHEDULED_DONE, doneNo);
        cv.put(KEY_SCHEDULED_ALARM_ID, alarmId);
        cv.put(KEY_SCHEDULED_PAYEE_ID, payeeId);

        ourDatabase.insert(DATABASE_TABLE_SCHEDULED, null, cv);

        close();

    }

    public void transferSchTxDetailsWithAlarmId(Integer alarmId, String dbDate) {
        // TODO Auto-generated method stub

        open();

        String sql = "SELECT * FROM " + DATABASE_TABLE_SCHEDULED + " WHERE "
                + KEY_SCHEDULED_ALARM_ID + " = " + alarmId;

        Cursor c = ourDatabase.rawQuery(sql, null);
        c.moveToFirst();

        Long payeeId = c.getLong(c.getColumnIndex(KEY_SCHEDULED_PAYEE_ID));
        Long accountId = c.getLong(c.getColumnIndex(KEY_SCHEDULED_ACCOUNT_ID));
        Long categoryId = c
                .getLong(c.getColumnIndex(KEY_SCHEDULED_CATEGORY_ID));
        Long txTypeId = c.getLong(c
                .getColumnIndex(KEY_SCHEDULED_TRANSACTION_TYPE_ID));
        Long projectId = c.getLong(c.getColumnIndex(KEY_SCHEDULED_PROJECT_ID));
        Long amount = c.getLong(c.getColumnIndex(KEY_SCHEDULED_AMOUNT));
        String note = c.getString(c.getColumnIndex(KEY_SCHEDULED_NOTE));

        c.close();

        ContentValues cv = new ContentValues();
        cv.put(KEY_TRANSACTION_DATE, dbDate);
        cv.put(KEY_TRANSACTION_ACCOUNT_ID, accountId);
        cv.put(KEY_TRANSACTION_PAYEE_ID, payeeId);
        cv.put(KEY_TRANSACTION_CATEGORY_ID, categoryId);
        cv.put(KEY_TRANSACTION_TRANSACTION_TYPE_ID, txTypeId);
        cv.put(KEY_TRANSACTION_AMOUNT, amount);
        cv.put(KEY_TRANSACTION_NOTE, note);
        cv.put(KEY_TRANSACTION_PROJECT_ID, projectId);

        ourDatabase.insert(DATABASE_TABLE_TRANSACTION, null, cv);

        ContentValues cvs = new ContentValues();

        cvs.put(KEY_FIN_TRANSACTION_DATE, dbDate);
        cvs.put(KEY_FIN_TRANSACTION_ACCOUNT_ID, accountId);
        cvs.put(KEY_FIN_TRANSACTION_PAYEE_ID, payeeId);
        cvs.put(KEY_FIN_TRANSACTION_CATEGORY_ID, categoryId);
        cvs.put(KEY_FIN_TRANSACTION_TRANSACTION_TYPE_ID, txTypeId);
        cvs.put(KEY_FIN_TRANSACTION_AMOUNT, amount);
        cvs.put(KEY_FIN_TRANSACTION_NOTE, note);
        cvs.put(KEY_FIN_TRANSACTION_PROJECT_ID, projectId);

        ourDatabase.insert(DATABASE_TABLE_FINISHED_TRANSACTION, null, cvs);

        close();

    }

    public Cursor getPendingTransactions() {
        // TODO Auto-generated method stub

        String sql = "SELECT " + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_ID + ", " + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_START_DATE + ", "
                + DATABASE_TABLE_SCHEDULED_RECURRENCE + "."
                + KEY_SCHEDULED_RECURRENCE_NAME + ", " + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_NAME + ", " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_AMOUNT + " FROM " + DATABASE_TABLE_SCHEDULED
                + " INNER JOIN " + DATABASE_TABLE_SCHEDULED_RECURRENCE + " ON "
                + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_SCH_RECURRENCE_ID + " = "
                + DATABASE_TABLE_SCHEDULED_RECURRENCE + "."
                + KEY_SCHEDULED_RECURRENCE_ID + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_SCHEDULED
                + "." + KEY_SCHEDULED_ACCOUNT_ID + "=" + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_ID + " INNER JOIN " + DATABASE_TABLE_PAYEE + " ON "
                + DATABASE_TABLE_SCHEDULED + "." + KEY_SCHEDULED_PAYEE_ID + "="
                + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_ID + " INNER JOIN "
                + DATABASE_TABLE_CATEGORY + " ON " + DATABASE_TABLE_SCHEDULED
                + "." + KEY_SCHEDULED_CATEGORY_ID + "="
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID + " WHERE "
                + DATABASE_TABLE_SCHEDULED + "." + KEY_SCHEDULED_DONE + "= 0"
                + " ORDER BY " + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_ID + " DESC";

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

		/*
         * int cIAmount = c.getColumnIndex(DATABASE_TABLE_SCHEDULED + "." +
		 * KEY_SCHEDULED_AMOUNT); int cIDate =
		 * c.getColumnIndex(DATABASE_TABLE_SCHEDULED + "." +
		 * KEY_SCHEDULED_START_DATE); int cIRecurString = c
		 * .getColumnIndex(DATABASE_TABLE_SCHEDULED_RECURRENCE + "." +
		 * KEY_SCHEDULED_RECURRENCE_NAME);
		 * 
		 * Log.d("amountIndex", "" + cIAmount); Log.d("recurIndex", "" +
		 * cIRecurString); Log.d("dateIndex", "" + cIDate);
		 */
        return c;
    }

    public void updateScheduleTxAtAlarmId(Integer alarmId) {
        // TODO Auto-generated method stub
        String sql = "UPDATE " + DATABASE_TABLE_SCHEDULED + " SET "
                + KEY_SCHEDULED_DONE + " = 1 WHERE " + KEY_SCHEDULED_ALARM_ID
                + " = " + alarmId;
        open();
        ourDatabase.execSQL(sql);
        close();
    }

    public Cursor getFinishedTransactions() {
        // TODO Auto-generated method stub
        Log.d("in DbClass", "getAllTransactions() is called");

        String sql = "SELECT " + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_ID + ", "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_DATE + ", " + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_NAME + ", " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_AMOUNT + " FROM "
                + DATABASE_TABLE_FINISHED_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_ACCOUNT_ID + "=" + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_ID + " INNER JOIN " + DATABASE_TABLE_PAYEE + " ON "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " INNER JOIN " + DATABASE_TABLE_CATEGORY
                + " ON " + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_CATEGORY_ID + "="
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID
                + " ORDER BY DATE(" + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_DATE + ") desc,"
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_ID + " desc";

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        Log.d("columnIndex for date",
                "" + c.getColumnIndex(KEY_FIN_TRANSACTION_DATE));

        return c;
    }

    public String getEndDateAt(Integer alarmId) {
        // TODO Auto-generated method stub
        open();
        String sql = "SELECT " + KEY_SCHEDULED_END_DATE + " FROM "
                + DATABASE_TABLE_SCHEDULED + " WHERE " + KEY_SCHEDULED_ALARM_ID
                + "=" + alarmId;

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        String endDate = c.getString(c.getColumnIndex(KEY_SCHEDULED_END_DATE));

        c.close();

        close();

        return endDate;
    }

    public void updateNextDateAtAlarmId(Integer alarmId, String nextDate) {
        // TODO Auto-generated method stub

        Log.d("nextDate in DBCLASS", nextDate);
        String sql = "UPDATE " + DATABASE_TABLE_SCHEDULED + " SET "
                + KEY_SCHEDULED_NEXT_DATE + " = '" + nextDate + "' WHERE "
                + KEY_SCHEDULED_ALARM_ID + " = " + alarmId;
        open();
        ourDatabase.execSQL(sql);
        close();

    }

    public Bundle[] getAllPendingSchDetails() {
        // TODO Auto-generated method stub

        String sql = "SELECT " + KEY_SCHEDULED_START_DATE + ", "
                + KEY_SCHEDULED_SCH_RECURRENCE_ID + ", "
                + KEY_SCHEDULED_ALARM_ID + ", " + KEY_SCHEDULED_NEXT_DATE
                + " FROM " + DATABASE_TABLE_SCHEDULED + " WHERE "
                + KEY_SCHEDULED_DONE + " = 0";

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            int totalNumberOfRows = c.getCount();
            Log.d("totNumberOfRows", "" + totalNumberOfRows);

            Bundle bundle[] = new Bundle[totalNumberOfRows];

            Log.d("bundle", "" + bundle);
            int counter;
            String startDate, nextDate;
            Long recurId;
            Integer alarmId;

            for (c.moveToFirst(), counter = 0; !c.isAfterLast(); c.moveToNext(), counter++) {

                startDate = c.getString(c
                        .getColumnIndex(KEY_SCHEDULED_START_DATE));

                Log.d("startDate", startDate);

                nextDate = c.getString(c
                        .getColumnIndex(KEY_SCHEDULED_NEXT_DATE));

                Log.d("nextDate", nextDate);
                recurId = c.getLong(c
                        .getColumnIndex(KEY_SCHEDULED_SCH_RECURRENCE_ID));

                Log.d("recurId", "" + recurId);
                alarmId = c.getInt(c.getColumnIndex(KEY_SCHEDULED_ALARM_ID));

                Log.d("alarmId", "" + alarmId);

                Log.d("inGETALLpENDING", "" + counter);

                bundle[counter] = new Bundle();

                Log.d("bundle[" + counter + "]", "" + bundle[counter]);

                bundle[counter].putString("nextDate", nextDate);
                bundle[counter].putLong("recurId", recurId);
                bundle[counter].putInt("alarmId", alarmId);
                bundle[counter].putString("startDate", startDate);

                Log.e("after puts", "***************************************");

                Log.d("nextDate", bundle[counter].getString("nextDate"));
                Log.d("startDate", bundle[counter].getString("startDate"));

                Log.d("recurId", "" + bundle[counter].getLong("recurId"));

                Log.d("alarmId", "" + bundle[counter].getInt("alarmId"));

            }

            c.close();
            close();
            return bundle;

        }
        c.close();
        close();

        return null;
    }

    public Bundle getFinTxInfo(long id) {
        // TODO Auto-generated method stub
        String sql = "SELECT " + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_ID + ", "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_DATE + ", " + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_NAME + ", " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_AMOUNT + ", "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_NOTE + ", " + DATABASE_TABLE_PROJECT
                + "." + KEY_PROJECT_NAME + " FROM "
                + DATABASE_TABLE_FINISHED_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_ACCOUNT_ID + "=" + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_ID + " INNER JOIN " + DATABASE_TABLE_PAYEE + " ON "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " INNER JOIN " + DATABASE_TABLE_CATEGORY
                + " ON " + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_CATEGORY_ID + "="
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID
                + " INNER JOIN " + DATABASE_TABLE_PROJECT + " ON "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_PROJECT_ID + " = "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID + " WHERE "
                + DATABASE_TABLE_FINISHED_TRANSACTION + "."
                + KEY_FIN_TRANSACTION_ID + "=" + id;

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Bundle bundle = new Bundle();

        bundle.putString("txdate", c.getString(c
                .getColumnIndex(DATABASE_TABLE_FINISHED_TRANSACTION + "."
                        + KEY_FIN_TRANSACTION_DATE)));
        bundle.putString(
                "txacc",
                c.getString(c.getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                        + KEY_ACCOUNT_NAME)));
        bundle.putString(
                "txpayee",
                c.getString(c.getColumnIndex(DATABASE_TABLE_PAYEE + "."
                        + KEY_PAYEE_NAME)));
        bundle.putString(
                "txcat",
                c.getString(c.getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                        + KEY_CATEGORY_NAME)));
        bundle.putLong(
                "txamt",
                c.getLong(c.getColumnIndex(DATABASE_TABLE_FINISHED_TRANSACTION
                        + "." + KEY_FIN_TRANSACTION_AMOUNT)));

        bundle.putString("txnote", c.getString(c
                .getColumnIndex(DATABASE_TABLE_FINISHED_TRANSACTION + "."
                        + KEY_FIN_TRANSACTION_NOTE)));

        bundle.putString(
                "txproject",
                c.getString(c.getColumnIndex(DATABASE_TABLE_PROJECT + "."
                        + KEY_PROJECT_NAME)));

        c.close();
        return bundle;
    }

    public void deleteFinTxWithId(long id) {
        // TODO Auto-generated method stub
        String sql = "DELETE FROM " + DATABASE_TABLE_FINISHED_TRANSACTION
                + " WHERE " + KEY_FIN_TRANSACTION_ID + "=" + id + ";";

        ourDatabase.execSQL(sql);
    }

    public Bundle getPendTxInfo(long pendingId) {
        // TODO Auto-generated method stub

        String sql = "SELECT " + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_ID + ", " + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_START_DATE + ", "
                + DATABASE_TABLE_SCHEDULED_RECURRENCE + "."
                + KEY_SCHEDULED_RECURRENCE_NAME + ", " + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_NAME + ", " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_AMOUNT + ", " + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_NOTE + ", " + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_END_DATE + ", " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + " FROM " + DATABASE_TABLE_SCHEDULED
                + " INNER JOIN " + DATABASE_TABLE_SCHEDULED_RECURRENCE + " ON "
                + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_SCH_RECURRENCE_ID + " = "
                + DATABASE_TABLE_SCHEDULED_RECURRENCE + "."
                + KEY_SCHEDULED_RECURRENCE_ID + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_SCHEDULED
                + "." + KEY_SCHEDULED_ACCOUNT_ID + "=" + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_ID + " INNER JOIN " + DATABASE_TABLE_PAYEE + " ON "
                + DATABASE_TABLE_SCHEDULED + "." + KEY_SCHEDULED_PAYEE_ID + "="
                + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_ID + " INNER JOIN "
                + DATABASE_TABLE_CATEGORY + " ON " + DATABASE_TABLE_SCHEDULED
                + "." + KEY_SCHEDULED_CATEGORY_ID + "="
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID
                + " INNER JOIN " + DATABASE_TABLE_PROJECT + " ON "
                + DATABASE_TABLE_SCHEDULED + "." + KEY_SCHEDULED_PROJECT_ID
                + "=" + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID
                + " WHERE " + DATABASE_TABLE_SCHEDULED + "." + KEY_SCHEDULED_ID
                + " = " + pendingId;

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Bundle bundle = new Bundle();

        String startDate = c.getString(c
                .getColumnIndex(DATABASE_TABLE_SCHEDULED + "."
                        + KEY_SCHEDULED_START_DATE));
        String endDate = c.getString(c.getColumnIndex(DATABASE_TABLE_SCHEDULED
                + "." + KEY_SCHEDULED_END_DATE));
        String recur = c.getString(c
                .getColumnIndex(DATABASE_TABLE_SCHEDULED_RECURRENCE + "."
                        + KEY_SCHEDULED_RECURRENCE_NAME));
        String account = c.getString(c.getColumnIndex(DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_NAME));
        String payee = c.getString(c.getColumnIndex(DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME));
        String category = c.getString(c.getColumnIndex(DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_NAME));

        Long amount = c.getLong(c.getColumnIndex(DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_AMOUNT));

        String project = c.getString(c.getColumnIndex(DATABASE_TABLE_PROJECT
                + "." + KEY_PROJECT_NAME));

        String note = c.getString(c.getColumnIndex(DATABASE_TABLE_SCHEDULED
                + "." + KEY_SCHEDULED_NOTE));

        c.close();

        bundle.putString("startDate", startDate);
        bundle.putString("endDate", endDate);
        bundle.putString("recur", recur);
        bundle.putString("account", account);
        bundle.putString("payee", payee);
        bundle.putString("category", category);
        bundle.putString("project", project);
        bundle.putString("note", note);
        bundle.putLong("amount", amount);

        return bundle;
    }

    public void deletePendTxWithId(long pendingId, Context context) {
        // TODO Auto-generated method stub
        // here we cancel alarm and delete the entry from table

        String sql = "SELECT " + KEY_SCHEDULED_ALARM_ID + ","
                + KEY_SCHEDULED_SCH_RECURRENCE_ID + " FROM "
                + DATABASE_TABLE_SCHEDULED + " WHERE " + KEY_SCHEDULED_ID
                + " = " + pendingId;

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Integer alarmId = c.getInt(c.getColumnIndex(KEY_SCHEDULED_ALARM_ID));
        Long recurId = c.getLong(c
                .getColumnIndex(KEY_SCHEDULED_SCH_RECURRENCE_ID));

        c.close();

        AlarmReceiver alarmReceiver = new AlarmReceiver();

        alarmReceiver.cancelAlarm(alarmId, context, recurId);

        String sqlDelete = "DELETE FROM " + DATABASE_TABLE_SCHEDULED
                + " WHERE " + KEY_SCHEDULED_ID + "=" + pendingId + ";";

        ourDatabase.execSQL(sqlDelete);

    }

    public Bundle getScheduledDetailsAtId(long pendingId) {
        // TODO Auto-generated method stub
        String sql = "SELECT * FROM " + DATABASE_TABLE_SCHEDULED + " WHERE "
                + KEY_SCHEDULED_ID + " = " + pendingId;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        String startDate = c.getString(c
                .getColumnIndex(KEY_SCHEDULED_START_DATE));
        String endDate = c.getString(c.getColumnIndex(KEY_SCHEDULED_END_DATE));
        String note = c.getString(c.getColumnIndex(KEY_SCHEDULED_NOTE));
        Integer alarmId = c.getInt(c.getColumnIndex(KEY_SCHEDULED_ALARM_ID));

        Long accountId = c.getLong(c.getColumnIndex(KEY_SCHEDULED_ACCOUNT_ID));
        Long categoryId = c
                .getLong(c.getColumnIndex(KEY_SCHEDULED_CATEGORY_ID));
        Long recurId = c.getLong(c
                .getColumnIndex(KEY_SCHEDULED_SCH_RECURRENCE_ID));
        Long payeeId = c.getLong(c.getColumnIndex(KEY_SCHEDULED_PAYEE_ID));
        Long projectId = c.getLong(c.getColumnIndex(KEY_SCHEDULED_PROJECT_ID));
        Long amount = c.getLong(c.getColumnIndex(KEY_SCHEDULED_AMOUNT));
        Long txTypeId = c.getLong(c
                .getColumnIndex(KEY_SCHEDULED_TRANSACTION_TYPE_ID));

        c.close();

        close();

        if (amount < 0) {

            amount = amount + (-2 * amount);
        }

        Bundle bundle = new Bundle();

        bundle.putString("startDate", startDate);
        bundle.putString("endDate", endDate);
        bundle.putLong("recurId", recurId);
        bundle.putLong("accountId", accountId);
        bundle.putLong("payeeId", payeeId);
        bundle.putLong("categoryId", categoryId);
        bundle.putLong("projectId", projectId);
        bundle.putString("note", note);
        bundle.putLong("amount", amount);
        bundle.putInt("alarmId", alarmId);
        bundle.putLong("txTypeId", txTypeId);

        return bundle;
    }

    public String getPayeeNameAtId(Long payeId) {
        // TODO Auto-generated method stub
        String sql = "SELECT " + KEY_PAYEE_NAME + " FROM "
                + DATABASE_TABLE_PAYEE + " WHERE " + KEY_PAYEE_ID + " = "
                + payeId;

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        String payeeName = c.getString(c.getColumnIndex(KEY_PAYEE_NAME));

        c.close();

        close();

        return payeeName;

    }

    public void updateSchTx(Long pendingId, String startDate, Long recurId,
                            String endDate, Long accountId, String payee, Long categoryId,
                            Long transactionTypeId, Long amount, String note, Long projectId,
                            int doneNo, Integer alarmId) {
        // TODO Auto-generated method stub

        String sql = "SELECT " + KEY_PAYEE_ID + " FROM " + DATABASE_TABLE_PAYEE
                + " WHERE " + KEY_PAYEE_NAME + " = '" + payee + "'";

        // we get the id of the inserted payee name from the payee table
        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Long payeeId = c.getLong(c.getColumnIndex(KEY_PAYEE_ID));

        c.close();

        ContentValues cv = new ContentValues();

        cv.put(KEY_SCHEDULED_START_DATE, startDate);
        cv.put(KEY_SCHEDULED_SCH_RECURRENCE_ID, recurId);
        cv.put(KEY_SCHEDULED_END_DATE, endDate);
        cv.put(KEY_SCHEDULED_ACCOUNT_ID, accountId);
        cv.put(KEY_SCHEDULED_TRANSACTION_TYPE_ID, transactionTypeId);
        cv.put(KEY_SCHEDULED_CATEGORY_ID, categoryId);
        cv.put(KEY_SCHEDULED_AMOUNT, amount);
        cv.put(KEY_SCHEDULED_NOTE, note);
        cv.put(KEY_SCHEDULED_PROJECT_ID, projectId);
        cv.put(KEY_SCHEDULED_DONE, doneNo);
        cv.put(KEY_SCHEDULED_ALARM_ID, alarmId);
        cv.put(KEY_SCHEDULED_PAYEE_ID, payeeId);

        ourDatabase.update(DATABASE_TABLE_SCHEDULED, cv, " " + KEY_SCHEDULED_ID
                + " = " + pendingId, null);

        close();

    }

    public Long getAccountIdWithPayeeName(String payeeName) {
        // TODO Auto-generated method stub
        String sql = "SELECT " + KEY_ACCOUNT_ID + " FROM " + DATABASE_TABLE_ACCOUNT
                + " WHERE " + KEY_ACCOUNT_NAME + " = '" + payeeName + "'";
        open();
        Cursor c = ourDatabase.rawQuery(sql, null);
        c.moveToFirst();
        Long id = c.getLong(c.getColumnIndex(KEY_ACCOUNT_ID));
        c.close();

        close();

        return id;
    }

    public void updateTransferFromThisAccount(String dateString,
                                              String accountFromName, String accountToName, Long accountFromId,
                                              Long accountToId, Long trAmountMinus, Long trAmountPlus,
                                              Long spend, Long receive, Long categoryId, String transferNote,
                                              Long transferNumber) {

        // TODO Auto-generated method stub

        // first get the transaction_ids with transfer_id = transferNumber

        String sqlIds = "SELECT " + KEY_TRANSACTION_ID + " FROM "
                + DATABASE_TABLE_TRANSACTION + " WHERE "
                + KEY_TRANSACTION_TRANSFER_ID + " = " + transferNumber;
        open();

        Cursor cIds = ourDatabase.rawQuery(sqlIds, null);

        cIds.moveToFirst();

        Long losingTxId = cIds.getLong(cIds.getColumnIndex(KEY_TRANSACTION_ID));

        cIds.moveToNext();

        Long gainingTxId = cIds
                .getLong(cIds.getColumnIndex(KEY_TRANSACTION_ID));

        cIds.close();

        //
        // we start with the account losing
        //

        String sql = "SELECT " + KEY_PAYEE_ID + " FROM " + DATABASE_TABLE_PAYEE
                + " WHERE " + KEY_PAYEE_NAME + " = '" + accountToName + "'";

        // we get the id of the inserted payee name from the payee table

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Long payee_AccountToPayeeId = c.getLong(c.getColumnIndex(KEY_PAYEE_ID));

        c.close();

        open();

        ContentValues cv = new ContentValues();

        cv.put(KEY_TRANSACTION_DATE, dateString);
        cv.put(KEY_TRANSACTION_ACCOUNT_ID, accountFromId);
        cv.put(KEY_TRANSACTION_PAYEE_ID, payee_AccountToPayeeId);
        cv.put(KEY_TRANSACTION_TRANSACTION_TYPE_ID, spend);
        cv.put(KEY_TRANSACTION_CATEGORY_ID, categoryId);
        cv.put(KEY_TRANSACTION_AMOUNT, trAmountMinus);
        cv.put(KEY_TRANSACTION_NOTE, transferNote);
        cv.put(KEY_TRANSACTION_TRANSFER_ID, transferNumber);

        ourDatabase.update(DATABASE_TABLE_TRANSACTION, cv, " "
                + KEY_TRANSACTION_ID + "=" + losingTxId, null);

        //
        // now with the account gaining
        //

        String sql2 = "SELECT " + KEY_PAYEE_ID + " FROM "
                + DATABASE_TABLE_PAYEE + " WHERE " + KEY_PAYEE_NAME + " = '"
                + accountFromName + "'";

        // we get the id of the inserted payee name from the payee table

        Cursor cu = ourDatabase.rawQuery(sql2, null);

        cu.moveToFirst();

        Long payee_AccountFromPayeeId = cu.getLong(cu
                .getColumnIndex(KEY_PAYEE_ID));

        cu.close();

        open();

        ContentValues cvs = new ContentValues();

        cvs.put(KEY_TRANSACTION_DATE, dateString);
        cvs.put(KEY_TRANSACTION_ACCOUNT_ID, accountToId);
        cvs.put(KEY_TRANSACTION_PAYEE_ID, payee_AccountFromPayeeId);
        cvs.put(KEY_TRANSACTION_TRANSACTION_TYPE_ID, receive);
        cvs.put(KEY_TRANSACTION_CATEGORY_ID, categoryId);
        cvs.put(KEY_TRANSACTION_AMOUNT, trAmountPlus);
        cvs.put(KEY_TRANSACTION_NOTE, transferNote);
        cvs.put(KEY_TRANSACTION_TRANSFER_ID, transferNumber);

        ourDatabase.update(DATABASE_TABLE_TRANSACTION, cvs, " "
                + KEY_TRANSACTION_ID + "=" + gainingTxId, null);

        close();

    }

    public Cursor getClosedAccounts() {
        // TODO Auto-generated method stub
        String query = "SELECT " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + ", "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME + ", "
                + DATABASE_TABLE_ACCOUNT_TYPE + "." + KEY_ACCOUNT_TYPE_NAME
                + ", SUM(" + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + ") AS acTotal FROM "
                + DATABASE_TABLE_ACCOUNT + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT_TYPE + " ON " + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_TYPE + "=" + DATABASE_TABLE_ACCOUNT_TYPE
                + "." + KEY_ACCOUNT_TYPE_ID + " INNER JOIN " + DATABASE_TABLE_TRANSACTION
                + " ON " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + "="
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + " WHERE " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_OPEN
                + " = 0" + " GROUP BY " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID
                + " ORDER BY " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor accountsCursor = ourDatabase.rawQuery(query, null);

        Log.d("columnIndex acTotal",
                "" + accountsCursor.getColumnIndex("acTotal"));

        return accountsCursor;
    }

    public void reopenAccountWithId(long accountId) {
        // TODO Auto-generated method stub
        String sql = "UPDATE " + DATABASE_TABLE_ACCOUNT + " SET "
                + KEY_ACCOUNT_OPEN + " = 1 WHERE " + KEY_ACCOUNT_ID + "=" + accountId
                + ";";
        ourDatabase.execSQL(sql);
    }

    public Cursor getPrTransactions() {
        // TODO Auto-generated method stub
        String sql = "SELECT " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_ID + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + ", " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "="
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " INNER JOIN " + DATABASE_TABLE_CATEGORY
                + " ON " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_PROJECT_ID
                + "=" + OverviewActivity.idOfEntity + " ORDER BY DATE("
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + ") desc," + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_ID + " desc";

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        return c;
    }

    public Bundle getProjectIncomeAndExpenseTotals(Long prViewId2) {
        // TODO Auto-generated method stub

        Bundle bundle = new Bundle();

        String sqlTotIncome = "SELECT " + KEY_TRANSACTION_PROJECT_ID + ", SUM("
                + KEY_TRANSACTION_AMOUNT + ") AS totIncome FROM "
                + DATABASE_TABLE_TRANSACTION + " WHERE "
                + KEY_TRANSACTION_PROJECT_ID + "=" + prViewId2 + " AND "
                + KEY_TRANSACTION_AMOUNT + " > 0";

        open();

        Cursor cTotIncome = ourDatabase.rawQuery(sqlTotIncome, null);

        if (cTotIncome.getCount() > 0) {
            cTotIncome.moveToFirst();
            Long totIncome = cTotIncome.getLong(cTotIncome
                    .getColumnIndex("totIncome"));
            bundle.putLong("totIncome", totIncome);
        } else {
            bundle.putLong("totIncome", 0);
        }

        cTotIncome.close();

        String sqlTotExpense = "SELECT " + KEY_TRANSACTION_PROJECT_ID
                + ", SUM(" + KEY_TRANSACTION_AMOUNT + ") AS totExpense FROM "
                + DATABASE_TABLE_TRANSACTION + " WHERE "
                + KEY_TRANSACTION_PROJECT_ID + "=" + prViewId2 + " AND "
                + KEY_TRANSACTION_AMOUNT + " < 0";

        open();

        Cursor cTotExpense = ourDatabase.rawQuery(sqlTotExpense, null);

        if (cTotExpense.getCount() > 0) {
            cTotExpense.moveToFirst();
            Long totExpense = cTotExpense.getLong(cTotExpense
                    .getColumnIndex("totExpense"));
            bundle.putLong("totExpense", totExpense);
        } else {
            bundle.putLong("totExpense", 0);
        }

        cTotExpense.close();

        close();
        return bundle;
    }

    public String getProjectNote(long prId) {
        // TODO Auto-generated method stub
        String note = " ";

        open();

        Cursor c = ourDatabase.query(DATABASE_TABLE_PROJECT,
                new String[]{KEY_PROJECT_NOTE}, KEY_PROJECT_ID + " = "
                        + prId, null, null, null, null);

        if (c.getCount() > 0) {

            c.moveToFirst();
            note = c.getString(c.getColumnIndex(KEY_PROJECT_NOTE));
            c.close();
        }
        close();
        return note;
    }

    public void updateProject(String prName, String prNote, long prId) {
        // TODO Auto-generated method stub

        open();

        ContentValues values = new ContentValues();

        values.put(KEY_PROJECT_NAME, prName);
        values.put(KEY_PROJECT_NOTE, prNote);

        ourDatabase.update(DATABASE_TABLE_PROJECT, values, KEY_PROJECT_ID + "="
                + prId, null);

        close();
    }

    public void deleteProjectWithId(long prId) {
        // TODO Auto-generated method stub

        // first we update transaction table
        open();

        ContentValues values = new ContentValues();

        values.put(KEY_TRANSACTION_PROJECT_ID, 1);// 1 is the id for
        // "No Project"

        ourDatabase.update(DATABASE_TABLE_TRANSACTION, values,
                KEY_TRANSACTION_PROJECT_ID + "=" + prId, null);

        // then we delete the project from project table

        ourDatabase.delete(DATABASE_TABLE_PROJECT, KEY_PROJECT_ID + "=" + prId,
                null);

        close();

        close();
    }

    public void endProject(long prId) {
        // TODO Auto-generated method stub
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_PROJECT_OPEN, 0);

        ourDatabase.update(DATABASE_TABLE_PROJECT, values, KEY_PROJECT_ID + "="
                + prId, null);

        close();
    }

    public Cursor getAllProjects() {
        // TODO Auto-generated method stub
        String projectSql = "SELECT * FROM " + DATABASE_TABLE_PROJECT;

        open();
        Cursor c = ourDatabase.rawQuery(projectSql, null);

        if (c.getCount() > 0) {
            return c;
        } else
            return null;
    }

    public Long getCategoryId(Long transactionId) {
        // TODO Auto-generated method stub
        Long categId;
        open();

        Cursor c = ourDatabase.query(DATABASE_TABLE_TRANSACTION,
                new String[]{KEY_TRANSACTION_CATEGORY_ID},
                KEY_TRANSACTION_ID + "=" + transactionId, null, null, null,
                null);

        c.moveToFirst();

        categId = c.getLong(c.getColumnIndex(KEY_TRANSACTION_CATEGORY_ID));

        close();

        return categId;
    }

    public Bundle getTxInfoWithoutProject(Long transactionId) {
        // TODO Auto-generated method stub
        String sql = "SELECT " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_ID + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + ", " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", " + DATABASE_TABLE_PAYEE + "."
                + KEY_PAYEE_NAME + ", " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + ", " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_NOTE + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "="
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " INNER JOIN " + DATABASE_TABLE_CATEGORY
                + " ON " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ID + "="
                + transactionId;

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Bundle bundle = new Bundle();

        bundle.putString(
                "txdate",
                c.getString(c.getColumnIndex(DATABASE_TABLE_TRANSACTION + "."
                        + KEY_TRANSACTION_DATE)));
        bundle.putString(
                "txacc",
                c.getString(c.getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                        + KEY_ACCOUNT_NAME)));
        bundle.putString(
                "txpayee",
                c.getString(c.getColumnIndex(DATABASE_TABLE_PAYEE + "."
                        + KEY_PAYEE_NAME)));
        bundle.putString(
                "txcat",
                c.getString(c.getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                        + KEY_CATEGORY_NAME)));
        bundle.putLong(
                "txamt",
                c.getLong(c.getColumnIndex(DATABASE_TABLE_TRANSACTION + "."
                        + KEY_TRANSACTION_AMOUNT)));

        bundle.putString(
                "txnote",
                c.getString(c.getColumnIndex(DATABASE_TABLE_TRANSACTION + "."
                        + KEY_TRANSACTION_NOTE)));

        String project = " ";

        bundle.putString("txproject", project);

        c.close();
        return bundle;
    }

    public Bundle getNotifBundle(Integer alarmId) {
        // TODO Auto-generated method stub

        Bundle bundle = new Bundle();

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + "," + DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_AMOUNT + " FROM " + DATABASE_TABLE_SCHEDULED
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_SCHEDULED + "." + KEY_SCHEDULED_ACCOUNT_ID
                + " = " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_SCHEDULED + "." + KEY_SCHEDULED_ALARM_ID
                + " = " + alarmId;
        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        String acName = c.getString(c.getColumnIndex(DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_NAME));

        Long amount = c.getLong(c.getColumnIndex(DATABASE_TABLE_SCHEDULED + "."
                + KEY_SCHEDULED_AMOUNT));

        c.close();

        bundle.putString("acName", acName);
        bundle.putLong("amount", amount);

        close();

        return bundle;
    }

    public void testDateQuery() {

        String sql = "SELECT " + KEY_TRANSACTION_DATE + " FROM "
                + DATABASE_TABLE_TRANSACTION + " WHERE ("
                + KEY_TRANSACTION_DATE
                + " BETWEEN DATE('2014-11-09') AND DATE('now')) AND ("
                + KEY_TRANSACTION_AMOUNT + " = 685300)";
        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            c.moveToFirst();

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                Log.d("date",
                        c.getString(c.getColumnIndex(KEY_TRANSACTION_DATE)));
            }
        } else {
            Log.d("no tx in range", "no tx n range");
        }

        close();
    }

    public Long totalTransactionAmountHistory() {
        // TODO Auto-generated method stub

        String totalBalance = "totBalance";
        Long tAmount;

        String sql = null;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ourContext);

        if (prefs.getBoolean("pref_history_start_date", false)) {

            ConversionClass mCC = new ConversionClass(ourContext);
            String startDate = prefs.getString("pref_start_date", "now");
            startDate = mCC.dateForDb(startDate);

            sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                    + KEY_ACCOUNT_INCLUDE_IN_TOTALS + ", sum("
                    + KEY_TRANSACTION_AMOUNT + ") AS " + totalBalance
                    + " FROM " + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                    + DATABASE_TABLE_ACCOUNT + " ON "
                    + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_ACCOUNT_ID + "=" + DATABASE_TABLE_ACCOUNT
                    + "." + KEY_ACCOUNT_ID + " WHERE (" + DATABASE_TABLE_ACCOUNT + "."
                    + KEY_ACCOUNT_INCLUDE_IN_TOTALS + "=1) AND ( "
                    + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                    + " BETWEEN DATE('" + startDate + "') AND DATE('now'))";

        } else {

            sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                    + KEY_ACCOUNT_INCLUDE_IN_TOTALS + ", sum("
                    + KEY_TRANSACTION_AMOUNT + ") AS " + totalBalance
                    + " FROM " + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                    + DATABASE_TABLE_ACCOUNT + " ON "
                    + DATABASE_TABLE_TRANSACTION + "."
                    + KEY_TRANSACTION_ACCOUNT_ID + "=" + DATABASE_TABLE_ACCOUNT
                    + "." + KEY_ACCOUNT_ID + " WHERE " + DATABASE_TABLE_ACCOUNT + "."
                    + KEY_ACCOUNT_INCLUDE_IN_TOTALS + "=1";
        }
        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        tAmount = c.getLong(c.getColumnIndex(totalBalance));

        c.close();

        Log.d("in DbClass", "we reached here");

        return tAmount;
    }

    public Bundle getTotalExpensesAndTotalIncome() {
        // TODO Auto-generated method stub

        ConversionClass mCC = new ConversionClass(ourContext);
        Double totIncome, totExpense;
        Bundle bundle = new Bundle();

        String totalIncome = "totIncome", totalExpense = "totExpense";

        String sql = "SELECT  SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT
                + " < 0 THEN " + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS "
                + totalExpense + ", SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT
                + " >= 0 THEN " + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS "
                + totalIncome + " FROM " + DATABASE_TABLE_TRANSACTION;

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            c.moveToFirst();

            Long incTotal = c.getLong(c.getColumnIndex(totalIncome));
            Long expTotal = c.getLong(c.getColumnIndex(totalExpense));

            c.close();

            expTotal = expTotal * (-1);

            close();

            totIncome = mCC.valueConverterReturnDouble(incTotal);
            totExpense = mCC.valueConverterReturnDouble(expTotal);

            double[] totals = {totIncome, totExpense};

            bundle.putDoubleArray("totals", totals);

            return bundle;
        }

        c.close();
        close();

        return null;
    }

    public Bundle getTotalExpensesAndTotalIncomeAfterFromDate(
            String fromDateInDb) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Double totIncome, totExpense;
        Bundle bundle = new Bundle();

        String totalIncome = "totIncome", totalExpense = "totExpense";

        String sql = "SELECT  SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT
                + " < 0 THEN " + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS "
                + totalExpense + ", SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT
                + " >= 0 THEN " + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS "
                + totalIncome + " FROM " + DATABASE_TABLE_TRANSACTION
                + " WHERE " + KEY_TRANSACTION_DATE + " >= DATE('"
                + fromDateInDb + "')";

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            c.moveToFirst();

            Long incTotal = c.getLong(c.getColumnIndex(totalIncome));
            Long expTotal = c.getLong(c.getColumnIndex(totalExpense));

            c.close();

            expTotal = expTotal * (-1);

            close();

            totIncome = mCC.valueConverterReturnDouble(incTotal);
            totExpense = mCC.valueConverterReturnDouble(expTotal);

            double[] totals = {totIncome, totExpense};

            bundle.putDoubleArray("totals", totals);

            return bundle;
        }

        c.close();
        close();

        return null;
    }

    public Bundle getTotalExpensesAndTotalIncomeBeforeFromDate(String toDateInDb) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Double totIncome, totExpense;
        Bundle bundle = new Bundle();

        String totalIncome = "totIncome", totalExpense = "totExpense";

        String sql = "SELECT  SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT
                + " < 0 THEN " + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS "
                + totalExpense + ", SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT
                + " >= 0 THEN " + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS "
                + totalIncome + " FROM " + DATABASE_TABLE_TRANSACTION
                + " WHERE " + KEY_TRANSACTION_DATE + " <= DATE('" + toDateInDb
                + "')";

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            c.moveToFirst();

            Long incTotal = c.getLong(c.getColumnIndex(totalIncome));
            Long expTotal = c.getLong(c.getColumnIndex(totalExpense));

            c.close();

            expTotal = expTotal * (-1);

            close();

            totIncome = mCC.valueConverterReturnDouble(incTotal);
            totExpense = mCC.valueConverterReturnDouble(expTotal);

            double[] totals = {totIncome, totExpense};

            bundle.putDoubleArray("totals", totals);

            return bundle;
        }

        c.close();
        close();

        return null;
    }

    public Bundle getTotalExpensesAndTotalIncomeBetweenDates(
            String fromDateInDb, String toDateInDb) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Double totIncome, totExpense;
        Bundle bundle = new Bundle();

        String totalIncome = "totIncome", totalExpense = "totExpense";

        String sql = "SELECT  SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT
                + " < 0 THEN " + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS "
                + totalExpense + ", SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT
                + " >= 0 THEN " + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS "
                + totalIncome + " FROM " + DATABASE_TABLE_TRANSACTION
                + " WHERE " + KEY_TRANSACTION_DATE + " BETWEEN DATE('"
                + fromDateInDb + "') AND DATE('" + toDateInDb + "')";

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            c.moveToFirst();

            Long incTotal = c.getLong(c.getColumnIndex(totalIncome));
            Long expTotal = c.getLong(c.getColumnIndex(totalExpense));

            c.close();

            expTotal = expTotal * (-1);

            close();

            totIncome = mCC.valueConverterReturnDouble(incTotal);
            totExpense = mCC.valueConverterReturnDouble(expTotal);

            double[] totals = {totIncome, totExpense};

            bundle.putDoubleArray("totals", totals);

            return bundle;
        }

        c.close();
        close();

        return null;
    }

    public Bundle getBothAccountIncomeAndExpenseTotals() {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + " = "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " GROUP BY "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] accountNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                accountNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("accountNames", accountNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getAccountExpenseTotalsBetweenDates(String fromDate,
                                                      String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + KEY_TRANSACTION_AMOUNT + "<= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " BETWEEN DATE('" + fromDate + "') AND DATE('" + toDate
                + "') " + " GROUP BY " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] accountName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                Log.d("checking",
                        "accountName = "
                                + c.getString(c
                                .getColumnIndex(DATABASE_TABLE_ACCOUNT
                                        + "." + KEY_ACCOUNT_NAME))
                                + "|| AcAmount = "
                                + c.getFloat(c.getColumnIndex("acAmount")));

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                accountName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("accountName", accountName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getAccountIncomeTotalsBetweenDates(String fromDate,
                                                     String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + KEY_TRANSACTION_AMOUNT + ">= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " BETWEEN DATE('" + fromDate + "') AND DATE('" + toDate
                + "') " + " GROUP BY " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] accountName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                accountName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("accountName", accountName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothAccountIncomeAndExpenseTotalsBetweenDates(
            String fromDate, String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + " = "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " BETWEEN DATE('" + fromDate + "') AND DATE('" + toDate
                + "') " + " GROUP BY " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] accountNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                accountNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("accountNames", accountNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getAccountExpenseTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + KEY_TRANSACTION_AMOUNT + "<= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " >= DATE('" + fromDate + "')" + " GROUP BY "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] accountName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                Log.d("checking",
                        "accountName = "
                                + c.getString(c
                                .getColumnIndex(DATABASE_TABLE_ACCOUNT
                                        + "." + KEY_ACCOUNT_NAME))
                                + "|| AcAmount = "
                                + c.getFloat(c.getColumnIndex("acAmount")));

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                accountName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("accountName", accountName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getAccountIncomeTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + KEY_TRANSACTION_AMOUNT + ">= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " >= DATE('" + fromDate + "')" + " GROUP BY "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] accountName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                accountName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("accountName", accountName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothAccountIncomeAndExpenseTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + " = "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " >= DATE('" + fromDate + "')" + " GROUP BY "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] accountNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                accountNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("accountNames", accountNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getAccountExpenseTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + KEY_TRANSACTION_AMOUNT + "<= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " <= DATE('" + toDate + "')" + " GROUP BY "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] accountName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                Log.d("checking",
                        "accountName = "
                                + c.getString(c
                                .getColumnIndex(DATABASE_TABLE_ACCOUNT
                                        + "." + KEY_ACCOUNT_NAME))
                                + "|| AcAmount = "
                                + c.getFloat(c.getColumnIndex("acAmount")));

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                accountName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("accountName", accountName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getAccountIncomeTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_ACCOUNT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + KEY_TRANSACTION_AMOUNT + ">= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " <= DATE('" + toDate + "')" + " GROUP BY "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] accountName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                accountName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("accountName", accountName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothAccountIncomeAndExpenseTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_ACCOUNT + "."
                + KEY_ACCOUNT_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + " = "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " <= DATE('" + toDate + "')" + " GROUP BY "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] accountNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                accountNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_ACCOUNT + "."
                                + KEY_ACCOUNT_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("accountNames", accountNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getBothCategoryIncomeAndExpenseTotals() {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_CATEGORY + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_CATEGORY_ID + " = "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID
                + " GROUP BY " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] categoryNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                categoryNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("categoryNames", categoryNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getCategoryExpenseTotalsBetweenDates(String fromDate,
                                                       String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_CATEGORY + " ON "
                + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + "<= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " BETWEEN DATE('" + fromDate
                + "') AND DATE('" + toDate + "') " + " GROUP BY "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] categoryName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                categoryName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("categoryName", categoryName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getCategoryIncomeTotalsBetweenDates(String fromDate,
                                                      String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_CATEGORY + " ON "
                + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + ">= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " BETWEEN DATE('" + fromDate
                + "') AND DATE('" + toDate + "') " + " GROUP BY "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] categoryName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                categoryName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("categoryName", categoryName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothCategoryIncomeAndExpenseTotalsBetweenDates(
            String fromDate, String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_CATEGORY + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_CATEGORY_ID + " = "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " BETWEEN DATE('" + fromDate + "') AND DATE('" + toDate
                + "') " + " GROUP BY " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] categoryNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                categoryNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("categoryNames", categoryNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getCategoryExpenseTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_CATEGORY + " ON "
                + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + "<= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " >= DATE('" + fromDate + "')"
                + " GROUP BY " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] categoryName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                categoryName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("categoryName", categoryName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getCategoryIncomeTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_CATEGORY + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID
                + " WHERE " + KEY_TRANSACTION_AMOUNT + ">= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " >= DATE('" + fromDate + "')" + " GROUP BY "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] categoryName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                categoryName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("categoryName", categoryName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothCategoryIncomeAndExpenseTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_CATEGORY + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_CATEGORY_ID + " = "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " >= DATE('" + fromDate + "')" + " GROUP BY "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] categoryNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                categoryNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("categoryNames", categoryNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getCategoryExpenseTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_CATEGORY + " ON "
                + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + "<= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " <= DATE('" + toDate + "')"
                + " GROUP BY " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] categoryName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                Log.d("checking",
                        "categoryName = "
                                + c.getString(c
                                .getColumnIndex(DATABASE_TABLE_CATEGORY
                                        + "." + KEY_CATEGORY_NAME))
                                + "|| AcAmount = "
                                + c.getFloat(c.getColumnIndex("acAmount")));

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                categoryName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("categoryName", categoryName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getCategoryIncomeTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_CATEGORY + " ON "
                + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + ">= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " <= DATE('" + toDate + "')"
                + " GROUP BY " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] categoryName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                categoryName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("categoryName", categoryName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothCategoryIncomeAndExpenseTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_CATEGORY + "."
                + KEY_CATEGORY_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_CATEGORY + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_CATEGORY_ID + " = "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " <= DATE('" + toDate + "')" + " GROUP BY "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] categoryNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                categoryNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_CATEGORY + "."
                                + KEY_CATEGORY_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("categoryNames", categoryNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getBothPayeeIncomeAndExpenseTotals() {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " > 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalIncome + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " < 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalExpenses
                + " FROM " + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + " = " + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " GROUP BY " + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] payeeNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                payeeNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("payeeNames", payeeNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getPayeeExpenseTotalsBetweenDates(String fromDate,
                                                    String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", SUM(" + KEY_TRANSACTION_AMOUNT + ") AS acAmount FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + "<= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " BETWEEN DATE('" + fromDate
                + "') AND DATE('" + toDate + "') " + " GROUP BY "
                + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] payeeName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                payeeName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("payeeName", payeeName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getPayeeIncomeTotalsBetweenDates(String fromDate,
                                                   String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", SUM(" + KEY_TRANSACTION_AMOUNT + ") AS acAmount FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + ">= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " BETWEEN DATE('" + fromDate
                + "') AND DATE('" + toDate + "') " + " GROUP BY "
                + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] payeeName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                payeeName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("payeeName", payeeName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothPayeeIncomeAndExpenseTotalsBetweenDates(
            String fromDate, String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " > 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalIncome + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " < 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalExpenses
                + " FROM " + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + " = " + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_DATE + " BETWEEN DATE('" + fromDate
                + "') AND DATE('" + toDate + "') " + " GROUP BY "
                + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] payeeNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                payeeNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("payeeNames", payeeNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getPayeeExpenseTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", SUM(" + KEY_TRANSACTION_AMOUNT + ") AS acAmount FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + "<= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " >= DATE('" + fromDate + "')"
                + " GROUP BY " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] payeeName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                payeeName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("payeeName", payeeName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getPayeeIncomeTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", SUM(" + KEY_TRANSACTION_AMOUNT + ") AS acAmount FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + ">= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " >= DATE('" + fromDate + "')"
                + " GROUP BY " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] payeeName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                payeeName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("payeeName", payeeName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothPayeeIncomeAndExpenseTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " > 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalIncome + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " < 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalExpenses
                + " FROM " + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + " = " + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_DATE + " >= DATE('" + fromDate + "')"
                + " GROUP BY " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] payeeNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                payeeNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("payeeNames", payeeNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getPayeeExpenseTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", SUM(" + KEY_TRANSACTION_AMOUNT + ") AS acAmount FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + "<= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " <= DATE('" + toDate + "')"
                + " GROUP BY " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] payeeName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                Log.d("checking",
                        "payeeName = "
                                + c.getString(c
                                .getColumnIndex(DATABASE_TABLE_PAYEE
                                        + "." + KEY_PAYEE_NAME))
                                + "|| AcAmount = "
                                + c.getFloat(c.getColumnIndex("acAmount")));

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                payeeName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("payeeName", payeeName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getPayeeIncomeTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", SUM(" + KEY_TRANSACTION_AMOUNT + ") AS acAmount FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + KEY_TRANSACTION_AMOUNT
                + ">= 0 AND " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " <= DATE('" + toDate + "')"
                + " GROUP BY " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] payeeName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                payeeName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("payeeName", payeeName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothPayeeIncomeAndExpenseTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " > 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalIncome + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " < 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalExpenses
                + " FROM " + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + " = " + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " WHERE " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_DATE + " <= DATE('" + toDate + "')"
                + " GROUP BY " + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] payeeNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                payeeNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PAYEE + "."
                                + KEY_PAYEE_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("payeeNames", payeeNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getProjectExpenseTotals() {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_PROJECT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_PROJECT_ID
                + "=" + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID
                + " WHERE " + KEY_TRANSACTION_AMOUNT + "<= 0" + " GROUP BY "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] projectName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                projectName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("projectName", projectName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getProjectIncomeTotals() {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_PROJECT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_PROJECT_ID
                + "=" + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID
                + " WHERE " + KEY_TRANSACTION_AMOUNT + ">= 0" + " GROUP BY "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] projectName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                projectName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("projectName", projectName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothProjectIncomeAndExpenseTotals() {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PROJECT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PROJECT_ID + " = "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID + " GROUP BY "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] projectNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                projectNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("projectNames", projectNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getProjectExpenseTotalsBetweenDates(String fromDate,
                                                      String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_PROJECT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_PROJECT_ID
                + "=" + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID
                + " WHERE " + KEY_TRANSACTION_AMOUNT + "<= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " BETWEEN DATE('" + fromDate + "') AND DATE('" + toDate
                + "') " + " GROUP BY " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] projectName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                projectName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("projectName", projectName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getProjectIncomeTotalsBetweenDates(String fromDate,
                                                     String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_PROJECT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_PROJECT_ID
                + "=" + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID
                + " WHERE " + KEY_TRANSACTION_AMOUNT + ">= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " BETWEEN DATE('" + fromDate + "') AND DATE('" + toDate
                + "') " + " GROUP BY " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] projectName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                projectName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("projectName", projectName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothProjectIncomeAndExpenseTotalsBetweenDates(
            String fromDate, String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PROJECT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PROJECT_ID + " = "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " BETWEEN DATE('" + fromDate + "') AND DATE('" + toDate
                + "') " + " GROUP BY " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] projectNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                projectNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("projectNames", projectNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getProjectExpenseTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_PROJECT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_PROJECT_ID
                + "=" + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID
                + " WHERE " + KEY_TRANSACTION_AMOUNT + "<= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " >= DATE('" + fromDate + "')" + " GROUP BY "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] projectName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                projectName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("projectName", projectName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getProjectIncomeTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_PROJECT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + "=" + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID
                + " WHERE " + KEY_TRANSACTION_AMOUNT + ">= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " >= DATE('" + fromDate + "')" + " GROUP BY "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] projectName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                projectName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("projectName", projectName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothProjectIncomeAndExpenseTotalsFromDate(String fromDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PROJECT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PROJECT_ID + " = "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " >= DATE('" + fromDate + "')" + " GROUP BY "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] projectNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                projectNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("projectNames", projectNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getProjectExpenseTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_PROJECT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_PROJECT_ID
                + "=" + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID
                + " WHERE " + KEY_TRANSACTION_AMOUNT + "<= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " <= DATE('" + toDate + "')" + " GROUP BY "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {

            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] projectName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")) * (-1));
                ;

                projectName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();

            Bundle bundle = new Bundle();

            bundle.putStringArray("projectName", projectName);

            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getProjectIncomeTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", SUM(" + KEY_TRANSACTION_AMOUNT
                + ") AS acAmount FROM " + DATABASE_TABLE_TRANSACTION
                + " INNER JOIN " + DATABASE_TABLE_PROJECT + " ON "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_PROJECT_ID
                + "=" + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID
                + " WHERE " + KEY_TRANSACTION_AMOUNT + ">= 0 AND "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " <= DATE('" + toDate + "')" + " GROUP BY "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() != 0) {
            double[] totAmount = new double[c.getCount()];
            int i = 0;

            String[] projectName = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                totAmount[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex("acAmount")));

                projectName[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            }// end of for loop

            c.close();
            Bundle bundle = new Bundle();
            bundle.putStringArray("projectName", projectName);
            bundle.putDoubleArray("totAmount", totAmount);

            return bundle;

        }// end of if statement

        else {
            return null;
        }
    }

    public Bundle getBothProjectIncomeAndExpenseTotalsBeforeDate(String toDate) {
        // TODO Auto-generated method stub
        ConversionClass mCC = new ConversionClass(ourContext);
        Bundle bundle = null;
        String totalIncome = "totIncome", totalExpenses = "totExpenses";

        String sql = "SELECT " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + ", " + " SUM( CASE WHEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " > 0 THEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", " + " SUM( CASE WHEN " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT
                + " ELSE 0 END) AS " + totalExpenses + " FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_PROJECT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PROJECT_ID + " = "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID + " WHERE "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_DATE
                + " <= DATE('" + toDate + "')" + " GROUP BY "
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_NAME;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {
            double[] totIncome = new double[c.getCount()];
            double[] totExpense = new double[c.getCount()];
            int i = 0;

            String[] projectNames = new String[c.getCount()];

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                totIncome[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                totExpense[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpenses)) * (-1));

                projectNames[i] = c.getString(c
                        .getColumnIndex(DATABASE_TABLE_PROJECT + "."
                                + KEY_PROJECT_NAME));

                i = i + 1;

            } // end of for loop
            bundle = new Bundle();
            bundle.putDoubleArray("totIncome", totIncome);
            bundle.putDoubleArray("totExpense", totExpense);
            bundle.putStringArray("projectNames", projectNames);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public Bundle getEntityPeriodBundle(Integer configNumber,
                                        int periodSpinnerPosition, long specificEntitySpinnerId) {
        // TODO Auto-generated method stub

        ConversionClass mCC = new ConversionClass(ourContext);

        Bundle bundle = null;
        String entity = null;
        String period = null;

        if (configNumber == 5) {
            // accountPeriod

            entity = KEY_TRANSACTION_ACCOUNT_ID;

        }

        if (configNumber == 6) {
            // categoryPeriod

            entity = KEY_TRANSACTION_CATEGORY_ID;

        }

        if (configNumber == 7) {
            // payeePeriod

            entity = KEY_TRANSACTION_PAYEE_ID;

        }

        if (configNumber == 8) {
            // projectPeriod

            entity = KEY_TRANSACTION_PROJECT_ID;

        }

        if (periodSpinnerPosition == 0) {
            // if monthly

            period = "%Y - %m";

        } else if (periodSpinnerPosition == 1) {
            // if yearly
            period = "%Y";
        }

        String monthYear = "month_year";
        String totalIncome = "totIncome", totalExpense = "totExpense";

        String sql = "SELECT strftime('" + period + "', "
                + KEY_TRANSACTION_DATE + ") AS " + monthYear
                + ", SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT + " > 0 THEN "
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalIncome
                + ", SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT + " < 0 THEN "
                + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS " + totalExpense
                + " FROM " + DATABASE_TABLE_TRANSACTION + " WHERE " + entity
                + " = " + specificEntitySpinnerId + " GROUP BY " + monthYear;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            double[] incomeTotals = new double[c.getCount()];
            double[] expenseTotals = new double[c.getCount()];
            String[] dates = new String[c.getCount()];

            int i = 0;

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // put the values in the arrays

                incomeTotals[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalIncome)));

                expenseTotals[i] = mCC.valueConverterReturnDouble(c.getLong(c
                        .getColumnIndex(totalExpense)) * (-1));

                dates[i] = c.getString(c.getColumnIndex(monthYear));

                i = i + 1;

            } // end of for loop

            Log.d("date", "" + dates[0]);
            Log.d("incomeTotal", "" + incomeTotals[0]);
            Log.d("expenseTotal", "" + expenseTotals[0]);

            bundle = new Bundle();
            bundle.putDoubleArray("incomeTotals", incomeTotals);
            bundle.putDoubleArray("expenseTotals", expenseTotals);
            bundle.putStringArray("dates", dates);

            return bundle;
        } else {
            c.close();
            close();
            return null;
        }
    }

    public int getNumberOfAccounts() {
        // TODO Auto-generated method stub

        String sql = "SELECT " + KEY_ACCOUNT_NAME + " FROM "
                + DATABASE_TABLE_ACCOUNT + " WHERE " + KEY_ACCOUNT_OPEN
                + " = 1";

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        int numberOfAccounts = c.getCount();

        c.close();

        close();

        return numberOfAccounts;
    }

    public int getNumberOfScheduled() {

        String sql = "SELECT " + KEY_SCHEDULED_ALARM_ID + " FROM "
                + DATABASE_TABLE_SCHEDULED + " WHERE " + KEY_SCHEDULED_DONE
                + " = 0";

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        int numberOfSch = c.getCount();

        c.close();

        close();

        return numberOfSch;

    }

    public int getNumberOfProjects() {
        // TODO Auto-generated method stub
        String sql = "SELECT " + KEY_PROJECT_NAME + " FROM "
                + DATABASE_TABLE_PROJECT + " WHERE " + KEY_PROJECT_OPEN
                + " = 1";

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        int numberOfProjects = c.getCount();

        c.close();

        close();

        return numberOfProjects;
    }

    public void closeExtraAccountsIfAny() {
        // TODO Auto-generated method stub

        int numberOfAccounts = getNumberOfAccounts();

        if (numberOfAccounts > 2) {

            String sql = "SELECT " + KEY_ACCOUNT_ID + " FROM " + DATABASE_TABLE_ACCOUNT
                    + " WHERE " + KEY_ACCOUNT_OPEN + " = 1";

            open();
            Cursor c = ourDatabase.rawQuery(sql, null);

            c.moveToFirst();

            Long idAtPositionOne = c.getLong(c.getColumnIndex(KEY_ACCOUNT_ID));

            c.moveToNext();

            Long idAtPositionTwo = c.getLong(c.getColumnIndex(KEY_ACCOUNT_ID));

            c.close();

            String sql2 = "UPDATE " + DATABASE_TABLE_ACCOUNT + " SET "
                    + KEY_ACCOUNT_OPEN + " = 0 WHERE " + KEY_ACCOUNT_ID + " != "
                    + idAtPositionOne + " AND " + KEY_ACCOUNT_ID + " != "
                    + idAtPositionTwo;

            ourDatabase.execSQL(sql2);

            close();

        }

    }

    public void updateEntries() {


        String sql = "SELECT " + KEY_ACCOUNT_ID + " FROM  accountTableOld  WHERE " + KEY_ACCOUNT_NAME + " LIKE '%Mutability%'";


        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        Long muta = c.getLong(c.getColumnIndex(KEY_ACCOUNT_ID));

        c.close();


        close();


    }

    public Cursor getAllTransactionDetails() {

        String sql = "SELECT " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + " as Date, " + DATABASE_TABLE_ACCOUNT
                + "." + KEY_ACCOUNT_NAME + " as Account, "
                + DATABASE_TABLE_PAYEE + "." + KEY_PAYEE_NAME + " as Payee, "
                + DATABASE_TABLE_CATEGORY + "." + KEY_CATEGORY_NAME
                + " as Category, " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_AMOUNT + " as Amount, "
                + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_NOTE
                + " as Note, " + DATABASE_TABLE_PROJECT + "."
                + KEY_PROJECT_NAME + " as Project FROM "
                + DATABASE_TABLE_TRANSACTION + " INNER JOIN "
                + DATABASE_TABLE_ACCOUNT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ACCOUNT_ID + "="
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " INNER JOIN "
                + DATABASE_TABLE_PAYEE + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PAYEE_ID + "=" + DATABASE_TABLE_PAYEE
                + "." + KEY_PAYEE_ID + " INNER JOIN " + DATABASE_TABLE_CATEGORY
                + " ON " + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_CATEGORY_ID + "=" + DATABASE_TABLE_CATEGORY
                + "." + KEY_CATEGORY_ID + " INNER JOIN "
                + DATABASE_TABLE_PROJECT + " ON " + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_PROJECT_ID + "="
                + DATABASE_TABLE_PROJECT + "." + KEY_PROJECT_ID
                + " ORDER BY DATE(" + DATABASE_TABLE_TRANSACTION + "."
                + KEY_TRANSACTION_DATE + ") desc," + DATABASE_TABLE_TRANSACTION
                + "." + KEY_TRANSACTION_ID + " desc";

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        return c;
    }

    public Long getHighestTransferNumber() {

        Long trNumber = (long) 1;

        String sql = "SELECT MAX(" + KEY_TRANSACTION_TRANSFER_ID + ") AS maxTrId FROM " + DATABASE_TABLE_TRANSACTION;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            c.moveToFirst();

            trNumber = c.getLong(c.getColumnIndex("maxTrId"));
        }

        c.close();

        close();


        return trNumber;
    }

    public Integer getHighestScheduledAlarmId() {

        Integer schId = 1;

        String sql = "SELECT MAX(" + KEY_SCHEDULED_ALARM_ID + ") AS maxSchAlarmId FROM " + DATABASE_TABLE_SCHEDULED;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        if (c.getCount() > 0) {

            c.moveToFirst();

            schId = c.getInt(c.getColumnIndex("maxSchAlarmId"));
        }

        c.close();

        close();


        return schId;
    }

    public Cursor getCardIssuers() {

        Log.d("getCardIssuers", "getCardIssuers");

        String sql = "SELECT " + KEY_CARD_ISSUER_ID + ", " + KEY_CARD_ISSUER_NAME + " FROM " + DATABASE_TABLE_CREDIT_CARD_ISSUER;

        open();

        Cursor c = ourDatabase.rawQuery(sql, null);

        return c;


    }


    public Long newCreditCardEntry(String acDate, String acName,
                                   Long acType, Long oA, String acNote,
                                   int checkBoxNumber, int isOpen, Long currencyId,
                                   Long cardLimitForDb, Long cardIssuer, String creditProvider) {


        // Method called to insert new credit card
        ContentValues cv = new ContentValues();

        cv.put(KEY_ACCOUNT_DATE, acDate);
        cv.put(KEY_ACCOUNT_NAME, acName);
        cv.put(KEY_ACCOUNT_OPEN_AMOUNT, oA);
        cv.put(KEY_ACCOUNT_NOTE, acNote);
        cv.put(KEY_ACCOUNT_TYPE, acType);
        cv.put(KEY_ACCOUNT_INCLUDE_IN_TOTALS, checkBoxNumber);
        cv.put(KEY_ACCOUNT_OPEN, isOpen);
        cv.put(KEY_ACCOUNT_CURRENCY_ID, currencyId);
        cv.put(KEY_ACCOUNT_CREDIT_CARD_ISSUER_ID, cardIssuer);
        cv.put(KEY_ACCOUNT_CREDIT_LIMIT, cardLimitForDb);
        cv.put(KEY_ACCOUNT_CREDIT_PROVIDER, creditProvider);

        Log.d("insertCreditCard", "the content values were put de");

        return ourDatabase.insert(DATABASE_TABLE_ACCOUNT, null, cv);


    }

    public Long getCreditLimitAndCurrentTotal(Long accountId) {

        String sql = "SELECT "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_CREDIT_LIMIT + ", "
                + "SUM(" + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT + ") as credTot"
                + " FROM "
                + DATABASE_TABLE_ACCOUNT
                + " INNER JOIN " + DATABASE_TABLE_TRANSACTION
                + " ON " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " = " + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + " WHERE " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " = " + accountId;

        Long credLimit, currentTotal, allowedAmt;

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        credLimit = c.getLong(c.getColumnIndex(DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_CREDIT_LIMIT));
        currentTotal = c.getLong(c.getColumnIndex("credTot"));

        c.close();

        close();

        allowedAmt = currentTotal + credLimit;

        return allowedAmt;
    }

    public Long getCreditLimitAndCurrentTotal(Long accountId, Long tId) {

        String sql = "SELECT "
                + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_CREDIT_LIMIT + ", "
                + "SUM(" + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_AMOUNT + ") as credTot"
                + " FROM "
                + DATABASE_TABLE_ACCOUNT
                + " INNER JOIN " + DATABASE_TABLE_TRANSACTION
                + " ON " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " = " + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ACCOUNT_ID
                + " WHERE " + DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_ID + " = " + accountId
                + " AND " + DATABASE_TABLE_TRANSACTION + "." + KEY_TRANSACTION_ID + " != " + tId;

        Long credLimit, currentTotal, allowedAmt;

        open();
        Cursor c = ourDatabase.rawQuery(sql, null);

        c.moveToFirst();

        credLimit = c.getLong(c.getColumnIndex(DATABASE_TABLE_ACCOUNT + "." + KEY_ACCOUNT_CREDIT_LIMIT));
        currentTotal = c.getLong(c.getColumnIndex("credTot"));

        c.close();

        close();

        allowedAmt = currentTotal + credLimit;

        return allowedAmt;
    }

    public Bundle getIncomeAndExpenseTotals(Long idOfEntity, int whichOverview) {

        String entityIdColumn = KEY_TRANSACTION_ACCOUNT_ID;

        if (whichOverview == OverviewActivity.KEY_ACCOUNT_OVERVIEW) {

            entityIdColumn = KEY_TRANSACTION_ACCOUNT_ID;

        } else if (whichOverview == OverviewActivity.KEY_PROJECT_OVERVIEW) {

            entityIdColumn = KEY_TRANSACTION_PROJECT_ID;

        }


        Bundle bundle = new Bundle();

        String sqlIncomeExpenseTotals = "SELECT " + entityIdColumn
                + ", SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT + " > 0 THEN " + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS totIncome "
                + ", SUM( CASE WHEN " + KEY_TRANSACTION_AMOUNT + " < 0 THEN " + KEY_TRANSACTION_AMOUNT + " ELSE 0 END) AS totExpense "
                + ", SUM( " + KEY_TRANSACTION_AMOUNT + ") AS totBalance"
                + " FROM "
                + DATABASE_TABLE_TRANSACTION + " WHERE "
                + entityIdColumn + " = " + idOfEntity;


        open();

        Cursor c = ourDatabase.rawQuery(sqlIncomeExpenseTotals, null);

        if (c.getCount() > 0) {

            c.moveToFirst();


            Long totIncome = c.getLong(c
                    .getColumnIndex("totIncome"));

            Long totExpense = c.getLong(c.getColumnIndex("totExpense"));

            Long totBalance = c.getLong(c.getColumnIndex("totBalance"));

            bundle.putLong("totIncome", totIncome);
            bundle.putLong("totExpense", totExpense);
            bundle.putLong("totBalance", totBalance);
        }


        c.close();

        return bundle;
    }


}
