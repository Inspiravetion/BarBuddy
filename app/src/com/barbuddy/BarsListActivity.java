package com.barbuddy;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

public class BarsListActivity extends Activity implements OnClickListener
{
	private LinearLayout ll;
	private ArrayList<Bar> mBars;
	private TextView barName;
	LatLng currentLoc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.barslistlayout);
		
		if (isNetworkAvailable())
		{
			setUpLocation();
		}
		
		

        
		//Get the bar objects
		//mBars = populateBars();
		mBars = BarsListSingleton.getInstance(getApplicationContext()).getData();
		
		ll = (LinearLayout) findViewById(R.id.bars_table);
		setUpLocation();
		
		for (Bar bar : mBars) 
		{
			LinearLayout nextbar = new LinearLayout(this);
			LayoutInflater inflater = LayoutInflater.from(this);
			nextbar = (LinearLayout) inflater.inflate(R.layout.eachbar, nextbar);
			barName = (TextView) nextbar.findViewById(R.id.barName);
			barName.setText(bar.name);
			
			Geocoder coder = new Geocoder(this);
			LatLng destinationBar = MapCalculations.getLatLongFromAddress(coder, bar.address);
			TextView barDistance = (TextView) nextbar.findViewById(R.id.barDistance);
			
			if (currentLoc != null)
			{
				double distance = MapCalculations.computeDistanceBetween(currentLoc, destinationBar);
				barDistance.setText(distance + " Meters");
			}
			nextbar.setOnClickListener(this);
			ll.addView(nextbar);
		}

	}
	
	private void setUpLocation()
	{
		Location location = getLocation();
		if (location != null)
			currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
	}
	
	public Location getLocation() 
	{
		Location location = null;
        try 
        {
        	LocationManager locationManager = (LocationManager) this
                    .getSystemService(LOCATION_SERVICE);
            Log.d("Network", "Network");
            if (locationManager != null) 
            {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
 
        return location;
    }

	@Override
	public void onClick(View v) 
	{
		Intent myIntent = new Intent(getApplicationContext(), DrinksListActivity.class);
		myIntent.putExtra("barName", barName.getText());
		startActivity(myIntent);
	}
	
	private boolean isNetworkAvailable() 
	{
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}
