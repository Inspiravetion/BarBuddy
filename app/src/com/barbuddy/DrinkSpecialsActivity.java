package com.barbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrinkSpecialsActivity extends Activity {

	private LinearLayout viewport;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drinkspecialslayout);
		
		viewport = (LinearLayout) this.findViewById(R.id.drinks_table);
		
//		ArrayList<Drink> drinks = new ArrayList<Drink>();
//		drinks.add(new Drink("Sex on the beach", 20, 12, 3.0));
//		drinks.add(new Drink("Long Island Ice Tea", 30, 8, 4.0));
//		drinks.add(new Drink("Rum n' Coke", 25, 8, 3.0));
		
		//TODO: USE A FOR LOOP TO GO THROUGH ALL SPECIALS IN ARRAYLIST
		
		// Special(String barName, String name, String descr, String start, String finish)
				
		viewport.addView(drinkSpecialView(new Special(
			"T.O.T.S.",
			"Mixed Drink Madness!",
			"",
			"Half off your favorite mixed drinks.", 
			"7:00pm",  
			"9:00pm"
		)));
		
		viewport.addView(drinkSpecialView(new Special(
				"The Cellar",
				"HAMMER TIME",
				"",
				"$6.50 Pitchers",  
				"6:00pm",  
				"8:00pm" 	
			)));
		
		viewport.addView(drinkSpecialView(new Special(
				"El Rods",
				"Early Bird Gets the Worm", 
				"",
				"$0.50 Tequila Shots", 	
				"3:00pm",  
				"6:00pm"
			)));
		
		viewport.addView(drinkSpecialView(new Special(
				"Sampsons Bar & Grill",	
				"Sampson Shootout", 
				"",
				"$1.00 Wells Shots",
				"6:00pm",  
				"9:00pm"
			)));
		
		viewport.addView(drinkSpecialView(new Special(
				"Fake Bar",
				"Power Hour", 
				"",
				"Half off ALL drinks", 
				"8:00pm",  
				"9:00pm"
			)));
		
		viewport.addView(drinkSpecialView(new Special(
				"Sharkeys",
				"Beer-O-Rama", 
				"",
				"$2.00 off every beer on tap", 	
				"7:00pm",  
				"9:00pm"
			)));
		
		viewport.addView(drinkSpecialView(new Special(
				"Hokie House",
				"House Party", 
				"",
				"$3.00 12oz Mixed Drinks",
				"3:00pm",  
				"6:00pm" 
			)));
		
		viewport.addView(drinkSpecialView(new Special(
				"El Diablo",
				"The Bewitching Hour", 
				"",
				"$1.00 Witch's Brews", 
				"7:00pm",  
				"8:00pm"
			)));
		
		viewport.addView(drinkSpecialView(new Special(
				"Karma",
				"Beer Olympics", 
				"",
				"Free beer for winners", 
				"6:00pm",  
				"9:00pm"
			)));
		
		viewport.addView(drinkSpecialView(new Special(
				"B.O.T.S.",	
				"Darty", 
				"",
				"Day drinking at its best", 
				"4:00pm",  
				"6:00pm"
			)));
	}
	
	public View drinkSpecialView(Special ds){
		
		LinearLayout nextdrink = new LinearLayout(this);
		LayoutInflater inflater = LayoutInflater.from(this);
		nextdrink = (LinearLayout) inflater.inflate(R.layout.eachspecial, nextdrink);
		TextView drinkName = (TextView) nextdrink.findViewById(R.id.drinkName);
		TextView drinkDesc = (TextView) nextdrink.findViewById(R.id.drinkDescription);
		TextView drinkTime = (TextView) nextdrink.findViewById(R.id.drinkTime);
		TextView drinkPlace = (TextView) nextdrink.findViewById(R.id.drinkPlace);
		
		drinkName.setText(ds.name);
		drinkDesc.setText(ds.description);
		drinkTime.setText(ds.startTime + " - " + ds.endTime);
		drinkPlace.setText(ds.barName);
		
		/*RelativeLayout view = new RelativeLayout(this);
		TextView attentionText = new TextView(this);
		TextView infoText = new TextView(this);
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		infoText.setLayoutParams(params);
		
		attentionText.setText(ds.name + "\n" + ds.description);
		infoText.setText(ds.barName + "\n" + ds.startTime + " - " + ds.endTime);
		attentionText.setGravity(Gravity.CENTER);
		infoText.setGravity(Gravity.CENTER);
		
		view.addView(attentionText);
		view.addView(infoText);
		view.setBackgroundResource(android.R.drawable.btn_default);*/
		
		return nextdrink;
	}
	
}
