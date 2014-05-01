package com.barbuddy;

import org.json.JSONObject;

import com.barbuddy.DataBase.RequestCallback;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button listBarsButton, drinksButton, mapButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listBarsButton = (Button) findViewById(R.id.button1);
		mapButton = (Button) findViewById(R.id.button2);
		drinksButton = (Button) findViewById(R.id.button3);
		
		listBarsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DataBase().getBars(new RequestCallback(){
					
					public void call(JSONObject json){
						Log.i("charlie", json.toString());
					}
					
				});
//				startActivity(new Intent(getApplicationContext(),
//						BarsListActivity.class));
			}
		});
		
		mapButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						MapBarsActivity.class));
			}
		});
		
		drinksButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						DrinksListActivity.class));
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
