package com.barbuddy;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDatabaseHelper extends SQLiteOpenHelper 
{

	public static final String TABLE_BARS = "bars";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_ADDRESS = "address";
	  public static final String COLUMN_WEBSITE = "website";
	  public static final String COLUMN_PHONENUMBER = "phoneNumber";
	  public static final String COLUMN_LOGOSRC = "logoSrc";
	  public static final String COLUMN_DRINKSLIST = "drinksList";
	  public static final String COLUMN_SPECIALSLIST = "specialsList";

	  private static final String DATABASE_NAME = "bars.db";
	  private static final int DATABASE_VERSION = 8;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_BARS + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " 
	      + COLUMN_NAME + ", "
	      + COLUMN_ADDRESS + ", "
	      + COLUMN_PHONENUMBER + ", "
	      + COLUMN_WEBSITE + ", "
	      + COLUMN_LOGOSRC + ", "
	      + COLUMN_DRINKSLIST + ", "
	      + COLUMN_SPECIALSLIST
	      + ");";

	  public MyDatabaseHelper(Context context) 
	  {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) 
	  {
		  database.execSQL("DROP TABLE IF EXISTS " + TABLE_BARS);
		  database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	  {
	    Log.w(MyDatabaseHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    onCreate(db);
	  }
} 
