package com.barbuddy;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrinksListActivity extends Activity 
{

	private LinearLayout ll;
	private  Bar currentBar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//TODO: HAVE THIS CLASS TAKE A BAR AS A PARAMETER(WHEN CALLED IN BarsListActivity) SO WE CAN GET THE LIST OF ALL DRINKS AND THE BAR INFO
		setContentView(R.layout.drinkslistlayout);
		
		//Get the bar objects
		//mBars = populateBars();
		ArrayList<Bar> allBars = BarsListSingleton.getInstance(getApplicationContext()).getData();
		Intent myIntent = getIntent();
		String barNameStr = myIntent.getStringExtra("barName");
		currentBar = getBarByName(barNameStr, allBars);
		
		ll = (LinearLayout) findViewById(R.id.drinks_table);
		TextView barName = (TextView) findViewById(R.id.editbarname);
		TextView barPhone = (TextView) findViewById(R.id.editbarphone);
		TextView barWebsite = (TextView) findViewById(R.id.editbarwebsite);
		TextView barAddress = (TextView) findViewById(R.id.editbaraddress);
		
		//TODO: UPDATE THESE VALUES FROM THE BAR PARAMETER
		barName.setText(currentBar.name);
		barPhone.setText(currentBar.phoneNumber);
		barWebsite.setText(currentBar.website);
		barAddress.setText(currentBar.address);
		
		//Intent callIntent = new Intent(Intent.ACTION_CALL);
		//callIntent.setData(Uri.parse("tel:+"+barPhone.getText().toString().trim()));
		//startActivity(callIntent );
		
		int dbsize = 15;
		
		for (int i = 0; i < dbsize; i++) 
		{
			LinearLayout nextdrink = new LinearLayout(this);
			LayoutInflater inflater = LayoutInflater.from(this);
			nextdrink = (LinearLayout) inflater.inflate(R.layout.eachdrink, nextdrink);
			TextView drinkName = (TextView) nextdrink.findViewById(R.id.drinkName);
			TextView drinkPrice = (TextView) nextdrink.findViewById(R.id.drinkPrice);
			TextView drinkAlcohol = (TextView) nextdrink.findViewById(R.id.drinkAlcohol);
			TextView drinkSize = (TextView) nextdrink.findViewById(R.id.drinkSize);
			
			//TODO: UPDATE THESE VALUES FROM THE BAR PARAMETER
			drinkName.setText("Cold Beverage");
			drinkPrice.setText("$" + i);
			drinkAlcohol.setText(i +"% Alc");
			drinkSize.setText(i + "  oz");
			
			ll.addView(nextdrink);
		}
		
	}


	private Bar getBarByName(String barNameStr, ArrayList<Bar> allBars) 
	{
		for (Bar bar : allBars)
		{
			if (bar.name.equals(barNameStr))
				return bar;
		}
		return null;
	}
	
}
