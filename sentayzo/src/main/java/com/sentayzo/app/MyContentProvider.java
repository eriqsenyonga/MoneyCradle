package com.sentayzo.app;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/* A custom Content Provider to do the database operations */
public class MyContentProvider extends ContentProvider {
	public MyContentProvider() {
	}

	DbClass mDbClass; // object used to do operations on the SQLite database
	public static final String PROVIDER_NAME = "SentayzoDbAuthority";

	//

	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/contents");

	public static final Uri ACCOUNT_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/accounts");

	public static final Uri ACCOUNT_SPINNER_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/accountspinner");

	public static final Uri CATEGORIES_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/categories");

	public static final Uri TRANSACTIONS_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/transactions");

	public static final Uri TRANSACTION_TYPES_CASH_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/transactionTypesCash");

	public static final Uri TRANSACTION_TYPES_BANK_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/transactionTypesBank");

	public static final Uri TRANSACTION_TYPES_ASSET_URI = Uri
			.parse("content://" + PROVIDER_NAME + "/transactionTypesAsset");

	public static final Uri TRANSACTIONS_AC_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/transactionsAc");

	public static final Uri PAYEES_URI = Uri.parse("content://" + PROVIDER_NAME
			+ "/payees");

	public static final Uri PAYEES2_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/payees2");

	public static final Uri PROJECTS_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/projects");
	
	public static final Uri PROJECTS_ALL_OPEN_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/projectsAllOpen");
	
	public static final Uri TRANSACTIONS_CAT_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/transactionsCat");
	
	public static final Uri TRANSACTIONS_PAYEE_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/transactionsPayee");
	
	public static final Uri CATEGORY_TOTALS_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/categoryTotals");
	
	public static final Uri MY_CURRENCIES_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/myCurrencies");
	
	public static final Uri RECUR_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/recur");
	
	public static final Uri PENDING_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/pending");
	
	public static final Uri FINISHED_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/finished");
	
	public static final Uri CLOSED_ACCOUNTS_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/closedAccounts");
	
	public static final Uri TRANSACTIONS_PR_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/transactionsPr");
	
	public static final Uri PROJECTS_ALL_PLUS_CLOSED_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/projectsAll");
	
	//recurSpinner
	
	

	/* Constants to identify the requested operations */
	private static final int accountTypes = 1;
	private static final int accounts = 2;
	private static final int accountspinner = 3;
	private static final int categories = 4;
	private static final int transactionTypesCash = 5;
	private static final int transactionTypesBank = 6;
	private static final int transactionTypesAsset = 7;
	private static final int transactions = 8;
	private static final int transactionsAc = 9;
	private static final int payees = 10;
	private static final int payees2 = 11;
	private static final int projects = 12;
	private static final int projectsAllOpen = 13;
	private static final int transactionsCat = 14;
	private static final int transactionsPayee = 15;
	private static final int categoryTotals = 16;
	private static final int myCurrencies = 17;
	private static final int recur = 18;
	private static final int pending = 19;
	private static final int finished = 20;
	private static final int closedAccounts = 21;
	private static final int transactionsPr = 22;
	private static final int projectsAll = 23;
	
	private static UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "contents", accountTypes);
		uriMatcher.addURI(PROVIDER_NAME, "accounts", accounts);
		uriMatcher.addURI(PROVIDER_NAME, "accountspinner", accountspinner);
		uriMatcher.addURI(PROVIDER_NAME, "categories", categories);
		uriMatcher.addURI(PROVIDER_NAME, "transactionTypesCash",
				transactionTypesCash);
		uriMatcher.addURI(PROVIDER_NAME, "transactionTypesBank",
				transactionTypesBank);
		uriMatcher.addURI(PROVIDER_NAME, "transactionTypesAsset",
				transactionTypesAsset);
		uriMatcher.addURI(PROVIDER_NAME, "transactions", transactions);
		uriMatcher.addURI(PROVIDER_NAME, "transactionsAc", transactionsAc);
		uriMatcher.addURI(PROVIDER_NAME, "payees", payees);
		uriMatcher.addURI(PROVIDER_NAME, "payees2", payees2);
		uriMatcher.addURI(PROVIDER_NAME, "projects", projects);
		uriMatcher.addURI(PROVIDER_NAME, "projectsAllOpen", projectsAllOpen);
		uriMatcher.addURI(PROVIDER_NAME, "transactionsCat", transactionsCat);
		uriMatcher.addURI(PROVIDER_NAME, "transactionsPayee", transactionsPayee);
		uriMatcher.addURI(PROVIDER_NAME, "categoryTotals", categoryTotals);
		uriMatcher.addURI(PROVIDER_NAME, "myCurrencies", myCurrencies);
		uriMatcher.addURI(PROVIDER_NAME, "recur", recur);
		uriMatcher.addURI(PROVIDER_NAME, "pending", pending);
		uriMatcher.addURI(PROVIDER_NAME, "finished", finished);
		uriMatcher.addURI(PROVIDER_NAME, "closedAccounts", closedAccounts);
		uriMatcher.addURI(PROVIDER_NAME, "transactionsPr", transactionsPr);
		uriMatcher.addURI(PROVIDER_NAME, "projectsAll", projectsAll);
		
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Implement this to handle requests to delete one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public String getType(Uri uri) {
		// TODO: Implement this to handle requests for the MIME type of the data
		// at the given URI.
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO: Implement this to handle requests to insert a new row.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public boolean onCreate() {
		// TODO: Implement this to initialize your content provider on startup.
		mDbClass = new DbClass(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO: Implement this to handle query requests from clients.

		if (uriMatcher.match(uri) == accounts) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for accounts");
			return mDbClass.getAllOpenAccounts();
		}

		if (uriMatcher.match(uri) == projects) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for projects");
			return mDbClass.getProjects();
		}

		if (uriMatcher.match(uri) == projectsAllOpen) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for projectsAll");
			return mDbClass.getAllOpenProjects();
		}

		if (uriMatcher.match(uri) == accountTypes) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for accountTypes");
			return mDbClass.getAccountTypes();
		}

		if (uriMatcher.match(uri) == accountspinner) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for accountspinner");

			return mDbClass.getAccounts();
		}

		if (uriMatcher.match(uri) == categories) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for categories");

			return mDbClass.getCategories();

		}

		if (uriMatcher.match(uri) == transactionTypesCash) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for cash tx type");
			return mDbClass.getCashTransactionTypes();
		}

		if (uriMatcher.match(uri) == transactionTypesBank) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for Bank tx type");
			return mDbClass.getBankTransactionTypes();
		}

		if (uriMatcher.match(uri) == transactionTypesAsset) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for Asset/Liability tx type");
			return mDbClass.getAssetTransactionTypes();
		}

		if (uriMatcher.match(uri) == transactions) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for transactions");
			return mDbClass.getAllTransactions();
		}

		if (uriMatcher.match(uri) == transactionsAc) {
			Log.d("transactionsAc", "in content provider");
			return mDbClass.getAcTransactions();
		}

		if (uriMatcher.match(uri) == payees) {
			Log.d("payees", "in content provider");
			return mDbClass.getPayeeName();
		}
		
		if (uriMatcher.match(uri) == transactionsCat) {
			Log.d("transactionsCat", "in content provider");
			return mDbClass.getCatTransactions();
		}
		if (uriMatcher.match(uri) == transactionsPayee) {
			Log.d("transactionsPayee", "in content provider");
			return mDbClass.getPayeeTransactions();
		}
		
		if (uriMatcher.match(uri) == categoryTotals) {
			Log.d("categoryTotals", "in content provider");
			return mDbClass.getCategoryTotals();
		}
		
		if (uriMatcher.match(uri) == myCurrencies) {
			Log.d("myCurrencies", "in content provider");
			return mDbClass.getMyCurrencies();
		}
		
		if (uriMatcher.match(uri) == recur) {
			Log.d("recur", "in content provider");
			return mDbClass.getRecurOptions();
		}
		
		if (uriMatcher.match(uri) == pending) {
			Log.d("pending", "in content provider");
			return mDbClass.getPendingTransactions();
		}
		
		if (uriMatcher.match(uri) == finished) {
			Log.d("finished", "in content provider");
			return mDbClass.getFinishedTransactions();
		}
		
		if (uriMatcher.match(uri) == closedAccounts) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for accounts");
			return mDbClass.getClosedAccounts();
		}
		
		if (uriMatcher.match(uri) == transactionsPr) {
			Log.d("transactionsCat", "in content provider");
			return mDbClass.getPrTransactions();
		}
		
		if (uriMatcher.match(uri) == projectsAll) {
			Log.i("MyContentProvider.java",
					"inside the cursor querry thing for projects");
			return mDbClass.getAllProjects();
		}
		
		

		if (uriMatcher.match(uri) == payees2) {
			Log.d("payees2", "in content provider");

			// SQLiteQueryBuilder is a helper class that creates the
			// proper SQL syntax for us.
			SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

			// Set the table we're querying.
			qBuilder.setTables(DbClass.DATABASE_TABLE_PAYEE);

			// Make the query.
			Cursor c = qBuilder.query(DbClass.ourDatabase, projection,
					selection, selectionArgs, null, null, sortOrder);
			c.setNotificationUri(getContext().getContentResolver(), uri);
			return c;

		}

		else {
			return null;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO: Implement this to handle requests to update one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
