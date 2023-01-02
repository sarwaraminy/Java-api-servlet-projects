package com.example.androidhive.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	static final String KEY_SIFLAG = "siflag";
	static final String KEY_LDATE = "ldate";
	static final String KEY_LVALUE = "lvalue";
	static final String KEY_ACCT   = "acct";
	static final String KEY_NAME   = "name";
	static final String TAG = "DBAdapter";
	static final String DATABASE_NAME = "fr_adb";
	static final String DATABASE_TABLE = "linecharinfo";
	static final String DATABASE_FRPAIR="frpair";
	static final int DATABASE_VERSION = 1;
	static final String DATABASE_CREATE ="create table linecharinfo (siflag text not null,  "+
	                                           "ldate text not null, lvalue text not null);";
	static final String DATABASE_CRFRPAIR = "create table frpair (acct text not null, "+
                                             "name text not null);";
	//create a table just hold the selected acct

	final Context context;
	DatabaseHelper DBHelper;
	SQLiteDatabase db;
	public DBAdapter(Context ctx)
	{
	    this.context = ctx;
	    DBHelper = new DatabaseHelper(context);
	}
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
	    DatabaseHelper(Context context)
	    {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }    
	    @Override
	    public void onCreate(SQLiteDatabase db)
	    {
	        try {
	            db.execSQL(DATABASE_CREATE);
	            db.execSQL(DATABASE_CRFRPAIR);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	    {
	        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
	        db.execSQL("DROP TABLE IF EXISTS contacts");
	        onCreate(db);
	    }
	}
	
	 //---opens the database---
	 public DBAdapter open() throws SQLException 
	 {
	     db = DBHelper.getWritableDatabase();
	     return this;
	 }
	 //---closes the database---
	 public void close() 
	 {
	     DBHelper.close();
	 }
	 //---insert a contact into the database---
	 public long insertContact(String siflag, String ldate, String lvalue) 
	 {
	     ContentValues initialValues = new ContentValues();
	     initialValues.put(KEY_SIFLAG, siflag);
	     initialValues.put(KEY_LDATE, ldate);
	     initialValues.put(KEY_LVALUE, lvalue);
	     return db.insert(DATABASE_TABLE, null, initialValues);
	 }
	 
	 // insert accounts from xml to table
	//---insert a contact into the database---
		 public long insertFRPAIRContentt(String acct, String name) 
		 {
		     ContentValues initialValues = new ContentValues();
		     initialValues.put(KEY_ACCT, acct);
		     initialValues.put(KEY_NAME, name);
		     return db.insert(DATABASE_FRPAIR, null, initialValues);
		 }
	 
	 //---deletes a particular contact---
	 public boolean deleteContact() 
	 {
	     return db.delete(DATABASE_TABLE, "1", null) > 0;
	 }
	 
	//---deletes all accounts---
		 public boolean deleteAllAccts() 
		 {
		     return db.delete(DATABASE_FRPAIR, "1", null) > 0;
		 }
		 
	 //---retrieves all the values of sector---
	 public Cursor getAllValues(String siflag)
	 {
	     return db.query(DATABASE_TABLE, new String[] {KEY_LVALUE}, KEY_SIFLAG + "=\"" +siflag+"\"", null, null, null, null, null);
	 }
	 
	//---retrieves all the Dates of sector---
		 public Cursor getAllDates(String siflag)
		 {
		     return db.query(DATABASE_TABLE, new String[] {KEY_LDATE}, KEY_SIFLAG + "=\"" +siflag+"\"", null, null, null, null, null);
		 }
	
		//---retrieves all the values of benchmark1---
		 public Cursor getAllValuesOfIndex1(String siflag)
		 {
		     return db.query(DATABASE_TABLE, new String[] {KEY_LVALUE}, KEY_SIFLAG + "=\"" +siflag+"\"", null, null, null, null, null);
		 }
		 
		//---retrieves all the Dates of benchmark1---
			 public Cursor getAllDatesOfIndex1(String siflag)
			 {
			     return db.query(DATABASE_TABLE, new String[] {KEY_LDATE}, KEY_SIFLAG + "=\"" +siflag+"\"", null, null, null, null, null);
			 }
	
			//---retrieves all the values of benchmark2---
			 public Cursor getAllValuesOfIndex2(String siflag)
			 {
			     return db.query(DATABASE_TABLE, new String[] {KEY_LVALUE}, KEY_SIFLAG + "=\"" +siflag+"\"", null, null, null, null, null);
			 }
			 
			//---retrieves all the Dates of benchmark2---
				 public Cursor getAllDatesOfIndex2(String siflag)
				 {
				     return db.query(DATABASE_TABLE, new String[] {KEY_LDATE}, KEY_SIFLAG + "=\"" +siflag+"\"", null, null, null, null, null);
				 }
				 
	 //---retrieves search value of account---
	 public Cursor getSearchedAcct(String inputText, String MPIDs) throws SQLException 
	 {
		 Cursor mCursor;
		 if (MPIDs == "Y")
		     mCursor = db.query(true, DATABASE_FRPAIR, new String[] {KEY_NAME, KEY_ACCT},
		            		KEY_ACCT + " like '" + inputText + "%' or " + KEY_NAME + " like '" + inputText + "%'", null, null, null, null, null);
		 else
			 mCursor = db.query(true, DATABASE_FRPAIR, new String[] {KEY_NAME, KEY_ACCT},
	            		KEY_ACCT + " = '" + inputText + "'", null, null, null, null, null);
		 
		    if (mCursor != null) {
		        mCursor.moveToFirst();
		    }
		    return mCursor;
		}
		//---updates a contact---
		public boolean updateContact(String siflag, String ldate, String lvalue) 
		{
		    ContentValues args = new ContentValues();
		    args.put(KEY_LDATE, ldate);
		    args.put(KEY_LVALUE, lvalue);
		    return db.update(DATABASE_TABLE, args, KEY_SIFLAG+ "=" + siflag, null) > 0;
		}

}
