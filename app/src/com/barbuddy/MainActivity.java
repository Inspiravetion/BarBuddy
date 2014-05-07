package com.barbuddy;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.barbuddy.DataBase.BarsCallback;
import com.barbuddy.DataBase.RequestCallback;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity 
{

	private Button listBarsButton, drinksButton, mapButton;
	private BarsDataSource datasource;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listBarsButton = (Button) findViewById(R.id.button1);
		mapButton = (Button) findViewById(R.id.button2);
		drinksButton = (Button) findViewById(R.id.button3);
		
		
		
		listBarsButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(getApplicationContext(),
						BarsListActivity.class);
				
				startActivity(intent);
			}
		});
		
		mapButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(getApplicationContext(),
						MapBarsActivity.class));
			}
		});
		
		drinksButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						DrinkSpecialsActivity.class));
			}
		});
		
		//Set Up SQLite DB
		updateDataToDatabase();
	}

	private void updateDataToDatabase() 
	{
		datasource = new BarsDataSource(this);
		datasource.open();
		ArrayList<Bar> bars;
		if (isNetworkAvailable())
		{
			//get data from mongo and add to sqlite
			//overwrite populateBars to call mongoDB
			new DataBase().getBars(new BarsCallback()
			{

				public void call(ArrayList<Bar> bars)
				{
					BarsListSingleton.getInstance(getApplicationContext()).setData(bars);
					for (Bar bar : bars)
					{
						datasource.createBar(bar);
					}
				}

			});
		}
		else
		{
			bars = datasource.getAllBars();
			BarsListSingleton.getInstance(getApplicationContext()).setData(bars);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private boolean isNetworkAvailable() 
	{
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	private ArrayList<Bar> populateBars()
	{
		ArrayList<Bar> bars = new ArrayList<Bar>();
		
		Bar bar = new Bar("622 North", "622 N Main St, Blacksburg, VA 24060",
				"(540) 951-1033", "www.622north.com");
		Drink drink = new Drink("Long Island", "9", "2", "4");
		bar.drinks.add(drink);
		Special special = new Special("622 North", "Pint Night", "Monday", "Pints are $1.75", "6", "9");
		Special special1 = new Special("622 North", "Special", "Monday", "1.75$ for doubles", "6", "9");
		bar.specials.add(special);
		bar.specials.add(special1);
		
		bars.add(bar);
		
		Bar bar1 = new Bar("Big Al's", "201 N Main St, Blacksburg, VA 24060",
				"(540) 951-3300", "www.bigalssportsbar.com");
		bars.add(bar1);
		
		Bar bar2 = new Bar("The Cellar", "302 N Main St, Blacksburg, VA 24060",
				"(540) 953-0651", "www.the-cellar.com");
		bars.add(bar2);
		
		Bar bar3 = new Bar("Champs", "111 N Main St, Blacksburg, VA 24060",
				"(540) 951-2222", "www.champssportsbar.com");
		bars.add(bar3);
		
		Bar bar4 = new Bar("El Rodeo", "623 N Main St, Blacksburg, VA 24060",
				"(540) 552-7807", "www.elrodeova.com");
		bars.add(bar4);
		
		Bar bar5 = new Bar("Hokie House", "322 N Main St, Blacksburg, VA 24060",
				"(540) 552-0280", "www.hokiehouse.com");
		bars.add(bar5);
		
		Bar bar6 = new Bar("PK's", "432 N Main St, Blacksburg, VA 24060",
				"(540) 552-1577", "www.pksbarandgrill.com");
		bars.add(bar6);
		
		Bar bar7 = new Bar("River Mill", "212 Draper Rd NW, Blacksburg, VA 24060",
				"(540) 951-2483", "");
		bars.add(bar7);
		
		Bar bar8 = new Bar("Sharkey's", "220 N Main St, Blacksburg, VA 24060",
				"(540) 552-2030", "www.sharkeyswingandribjoint.com");
		bars.add(bar8);
		
		Bar bar9 = new Bar("Sycamore Deli", "211 Draper Rd SW, Blacksburg, VA 24060",
				"(540) 951-9817", "www.sycamoredeli.com");
		bars.add(bar9);
		
		Bar bar10 = new Bar("Top Of The Stairs", "217 College Ave, Blacksburg, VA 24060",
				"(540) 953-2837", "www.topofthestairs.com");
		bars.add(bar10);
		
		return bars;
	}

	

}
