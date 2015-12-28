package com.jwapps.krisclients;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "clientList";
	private static final String DATABASE_TABLE = "clients";
	private static final String DATABASE_TABLE_2 = "massage";
	
	public static final String KEY_CLIENT_NAME ="clientName";
	public static final String KEY_DATE = "date";
	
	private static final String TAG = "DatabaseHandler";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private final Context mCtx;

	private static final String CREATE_TABLE_1 = " create table " + DATABASE_TABLE + " (_id integer primary key autoincrement," + "  name text, address text, phone text, mobilePhone text, email text );";
	
	private static final String CREATE_TABLE_2 = " create table " + DATABASE_TABLE_2 + " (_id integer primary key autoincrement," + "  date text, clientName text, areasOfConcern text, massageType text, massageStyle text, massageLen text, notes text );";
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			Log.w(TAG, CREATE_TABLE_1);
			Log.v(TAG, CREATE_TABLE_2);
			
			db.execSQL(CREATE_TABLE_1);
			db.execSQL(CREATE_TABLE_2);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE + DATABASE_TABLE_2);
			onCreate(db);
		}
	}

	public DatabaseHandler(Context ctx) {
		this.mCtx = ctx;
	}

	public DatabaseHandler open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	
	// Insert client's massage info to the database
	public void insertMassageInfo(String clientName, String sDate, String clientConcern, String length, String type, String style, String notesStr) {
		
		Log.i("Name: ",clientName);
		Log.i("Concern: ", clientConcern);
		Log.i("Length: ", length);
		Log.i("Type: ", type);
		Log.i("Style: ", style);
		
		open();
		
		ContentValues values = new ContentValues();
		values.put("date", sDate);
		values.put("clientName", clientName); 
		values.put("areasOfConcern", clientConcern); 
		values.put("massageType", type);
		values.put("massageStyle", style);
		values.put("massageLen", length);
		values.put("notes", notesStr);

		// Inserting Row
		mDb.insert(DATABASE_TABLE_2, null, values);
		mDb.close(); // Closing database connection
		
	}
	
	// Insert client to database
	public void insertClientInfo(String clientName, String clientAddress, String clientPhone, String clientMobilePhone, String clientEmail) {
		
		Log.i("Name: ",clientName);
		Log.i("Address: ", clientAddress);
		Log.i("Phone: ", clientPhone);
		Log.i("Mobile Phone: ", clientMobilePhone);
		Log.i("Email: ", clientEmail);
		
		open();
		
		ContentValues values = new ContentValues();
		values.put("name", clientName);
		values.put("address", clientAddress); 
		values.put("phone", clientPhone); 
		values.put("mobilePhone", clientMobilePhone);
		values.put("email", clientEmail);

		// Inserting Row
		mDb.insert(DATABASE_TABLE, null, values);
		mDb.close(); // Closing database connection
		
	}
	
	// Get all Clients in the db
	public Cursor getAllClients() {
		
		return mDb.rawQuery("SELECT  * FROM massage GROUP BY clientName", new String[] { });

	}
	
	// Get all Clients in the db
	public Cursor getAllMassages(String clientName) {
		
		Log.i("name: ", clientName);
		return mDb.rawQuery("SELECT * FROM massage WHERE clientName=? GROUP BY date ", new String[] { clientName });

		}
	
	// Getting client's chair massage count
	public int getChairCount(String name) {
		
		Log.i("getChairCount name: ", name);
		
		Cursor mCount= mDb.rawQuery("select count(*) from massage where clientName=? AND massageType=?", new String[] { name, "Chair" });
		mCount.moveToFirst();
		int count= mCount.getInt(0);
		mCount.close();
		
		return count;

	}
	
	// Getting client's table massage count
		public int getTableCount(String name) {
			
			Log.i("getTableCount name: ", name);
			
			Cursor mCount= mDb.rawQuery("select count(*) from massage where clientName=? AND massageType=?", new String[] { name, "Table" });
			mCount.moveToFirst();
			int count= mCount.getInt(0);
			mCount.close();
			
			return count;

		}
		
		// Getting the client's last massage date
		public Cursor getLastDate(String name) {
			
			  Cursor mCursor =  mDb.rawQuery("SELECT MAX(date) FROM massage Where clientName=?", new String[] { name });
		       if (mCursor != null) {
		       			mCursor.moveToFirst();
	           		}
               		return mCursor;
		}      
		
		// Getting client's last worked on area
		public Cursor getLastAreaWorked(String date, String name) {
			
			Cursor mCursor =  mDb.rawQuery("SELECT areasOfConcern FROM massage Where clientName=? AND date=?", new String[] { name,date });
		       if (mCursor != null) {
		       			mCursor.moveToFirst();
	           		}
            		return mCursor;
		}
		
		// Getting all client entries to check for duplicates
		public List<ClientEntries> getAllEntries() {
			List<ClientEntries> clientsList = new ArrayList<ClientEntries>();
			// Select All Query
			String selectQuery = "SELECT * FROM clients" ;

			open();
			
			Cursor cursor = mDb.rawQuery(selectQuery, null);
			
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					ClientEntries entries = new ClientEntries();

					entries.setName(cursor.getString(1));

					// Adding contact to list
					clientsList.add(entries);
				} while (cursor.moveToNext());
			}

			// return contact list
			return clientsList;

		}
		
		// Getting All Entries
		public List<ClientEntries> getClientInfo(String clientName) {
			List<ClientEntries> entriesList = new ArrayList<ClientEntries>();

			open();
			Cursor cursor = mDb.rawQuery("SELECT * FROM clients Where name=?", new String[] { clientName });
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					ClientEntries entries = new ClientEntries();
					entries.setID(Integer.parseInt(cursor.getString(0)));
					entries.setName(cursor.getString(1));
					entries.setAddress(cursor.getString(2));
					entries.setPhone(cursor.getString(3));
					entries.setMobilePhone(cursor.getString(4));
					entries.setEmail(cursor.getString(5));

					// Adding contact to list
					entriesList.add(entries);
				} while (cursor.moveToNext());
			}

			// return contact list
			return entriesList;

		}
		
		// Getting all massage info for the client
				public List<ClientEntries> getMassageInfo(String clientName, String date) {
					List<ClientEntries> massageList = new ArrayList<ClientEntries>();
					
					open();
					
					Cursor cursor = mDb.rawQuery("SELECT * FROM massage WHERE clientName=? and date=?", new String[] { clientName, date }) ;
					
					// looping through all rows and adding to list
					if (cursor.moveToFirst()) {
						do {
							ClientEntries entries = new ClientEntries();
							
							entries.setName(cursor.getString(2));
							entries.setArea(cursor.getString(3));
							entries.setType(cursor.getString(4));
							entries.setStyle(cursor.getString(5));
							entries.setLength(cursor.getString(6));
							entries.setNotes(cursor.getString(7));
							
							// Adding contact to list
							massageList.add(entries);
						} while (cursor.moveToNext());
					}

					// return contact list
					return massageList;

				}
	 
		
		// Delete the client from the database
		public void deleteClient(String clientName)  {
			
		      open(); 
		      mDb.delete("clients", "name=?", new String[] { clientName });
		      close();
		      
		 }
}