package com.barbuddy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class BarsDataSource 
{
	// Database fields
	  private SQLiteDatabase database;
	  private MyDatabaseHelper dbHelper;
	  private String[] allColumns = { MyDatabaseHelper.COLUMN_ID,
			  MyDatabaseHelper.COLUMN_NAME, MyDatabaseHelper.COLUMN_ADDRESS,
			  MyDatabaseHelper.COLUMN_PHONENUMBER, MyDatabaseHelper.COLUMN_WEBSITE,
			  MyDatabaseHelper.COLUMN_LOGOSRC, MyDatabaseHelper.COLUMN_DRINKSLIST, 
			  MyDatabaseHelper.COLUMN_SPECIALSLIST};

	  public BarsDataSource(Context context) 
	  {
	    dbHelper = new MyDatabaseHelper(context);
	  }

	  public void open() throws SQLException 
	  {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() 
	  {
	    dbHelper.close();
	  }

	  public void createBar(Bar bar) 
	  {
		  ContentValues values = new ContentValues();
		  values.put(MyDatabaseHelper.COLUMN_NAME, bar.name);
		  values.put(MyDatabaseHelper.COLUMN_ADDRESS, bar.address);
		  values.put(MyDatabaseHelper.COLUMN_PHONENUMBER, bar.phoneNumber);
		  values.put(MyDatabaseHelper.COLUMN_WEBSITE, bar.website);
		  values.put(MyDatabaseHelper.COLUMN_LOGOSRC, bar.logoSrc);
		  
		  String specialsArray = Arrays.toString(bar.specials.toArray());
		  String drinksArray = Arrays.toString(bar.drinks.toArray());
		  
		  values.put(MyDatabaseHelper.COLUMN_DRINKSLIST, drinksArray);
		  values.put(MyDatabaseHelper.COLUMN_SPECIALSLIST, specialsArray);
		  
		  Cursor cursor = database.query(MyDatabaseHelper.TABLE_BARS,
			        allColumns, MyDatabaseHelper.COLUMN_NAME + "=?", new String[] { bar.name }, null, null, null);
		  
		  if (cursor.moveToFirst())
		  {
			  long insertId = database.insert(MyDatabaseHelper.TABLE_BARS, null,
					  values);
		  }
		  else
		  {
			  String strFilter = MyDatabaseHelper.COLUMN_NAME + "=?";
			  ContentValues args = values;
			  String[] selectArgs = {bar.name};
			  database.update(MyDatabaseHelper.TABLE_BARS, args, strFilter, selectArgs);
		  }
		  
	  }

	  public ArrayList<Bar> getAllBars() 
	  {
		  ArrayList<Bar> bars = new ArrayList<Bar>();

	    Cursor cursor = database.query(MyDatabaseHelper.TABLE_BARS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      Bar bar = cursorToBar(cursor);
	      bars.add(bar);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return bars;
	  }

	  private Bar cursorToBar(Cursor cursor) 
	  {
	    Bar bar = new Bar();
	    bar.name = cursor.getString(1);
	    bar.address = cursor.getString(2);
	    bar.phoneNumber = cursor.getString(3);
	    bar.website = cursor.getString(4);
	    bar.logoSrc = cursor.getString(5);
	    
	    String drinksList = cursor.getString(6);
	    String specialsList = cursor.getString(7);
	    bar.drinks = getDrinksFromString(drinksList);
	    bar.specials = getSpecialsFromString(specialsList, bar.name);
	    
	    return bar;
	  }
	  
	  private ArrayList<Drink> getDrinksFromString(String drinks)
	  {
		  ArrayList <Drink> drinksList = new ArrayList<Drink>();
		  int numOfDrinkFields = 4;
		  if (drinks.length() > 2)
		  {
			  //erase the []
			  drinks = drinks.substring(1, drinks.length() - 1);
			  //put into string array
			  String[] temp = drinks.split("(?=\\{)|(?<=\\})");
			  
			  for (int x = 0; x < temp.length; x ++)
			  {
				  if (temp[x].isEmpty() || temp[x].equals(", "))
					  continue;
				  String str = temp[x];
				  JSONObject obj;
				try 
				{
					obj = new JSONObject(str);
					
					String name = obj.optString("name");
					String alcCont = obj.optString("alcoholContent");
					String size = obj.optString("size");
					String price = obj.optString("price");
					Drink drink = new Drink(name, alcCont, size, price);
					drinksList.add(drink);
				} 
				catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					Log.e("SAM", e.getMessage());
				}
				  
			  }
		  }
		  
		  return drinksList;
	  }
	  
	  private ArrayList<Special> getSpecialsFromString(String specials, String barName)
	  {
		  ArrayList <Special> specialsList = new ArrayList<Special>();
		  if (specials.length() > 2)
		  {
			  //erase the []
			  specials = specials.substring(1, specials.length() - 1);
			  //put into string array
			  String[] temp = specials.split("(?=\\{)|(?<=\\})");
			  
			  for (int x = 0; x < temp.length; x ++)
			  {
				  if (temp[x].isEmpty() || temp[x].equals(", "))
					  continue;
				  String str = temp[x];
				  JSONObject obj;
				try 
				{
					obj = new JSONObject(str);
					String name = obj.optString("name");
					String day = obj.optString("day");
					String description = obj.optString("description");
					String startTime = obj.optString("startTime");
					String endTime = obj.optString("endTime");
					Special drink = new Special(barName, name, day, description, startTime, endTime);
					specialsList.add(drink);
				} 
				catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					Log.e("SAM", e.getMessage());
				}
				  
			  }
		  }
		  return specialsList;
	  }
}
