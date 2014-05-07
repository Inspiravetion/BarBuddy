package com.barbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.location.Address;

public class MapBarsActivity extends Activity implements OnItemSelectedListener, OnMarkerClickListener
{

	//Views
	private GoogleMap mMap;
	private Spinner mSpinner;
	private Geocoder coder;
	
	//Markers on Map
	Map<LatLng, Marker> mMarkers;
	
	//Bar objects 
	ArrayList <Bar> mBars;
	
	private static String DEFAULT_ADDRESS = "622 N Main St, Blacksburg, VA 24060";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		if (!isNetworkAvailable())
		{
			setContentView(R.layout.network_not_available);
			return;
		}
		
		setContentView(R.layout.mapbarslayout);
		coder = new Geocoder(this);

		mMarkers = new HashMap<LatLng, Marker>();
		
		//Get the bar objects
		//mBars = populateBars();
		mBars = BarsListSingleton.getInstance(getApplicationContext()).getData();
		
		setUpMapIfNeeded();
		
		// Create a LatLng from a default address
		
		LatLng defaultLatLong = MapCalculations.getLatLongFromAddress(coder, DEFAULT_ADDRESS);
		
		//configure the map to zoom to the address
		configureMap(mMap, defaultLatLong, "622 North");
		
		//Set up with names of bars from database
		setUpSpinner();
		
		mMap.setOnMarkerClickListener(this);
		
	}
	
	//https://developers.google.com/maps/documentation/android/map
	private void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) 
	    {
	    	mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    }
	}
	
	private void configureMap(GoogleMap map, LatLng latLng, String markerTitle)
	{
	    if (map == null)
	        return; // Google Maps not available
	    try 
	    {
	        MapsInitializer.initialize(getApplicationContext());
	    }
	    catch (Exception e) 
	    {
	        Log.e("Google Play Services", "Have GoogleMap but then error", e);
	        return;
	    }
	    CameraUpdate coordinates = CameraUpdateFactory.newLatLng(latLng);
	    map.moveCamera(coordinates);
	    
	    CameraUpdate zoom = CameraUpdateFactory.zoomTo(17.0f);
	    map.moveCamera(zoom);
	    
	    Marker marker = mMarkers.get(latLng);
	    if (marker == null) 
	    {
	    	mMap.addMarker(new MarkerOptions()
	        .position(latLng)
	        .title(markerTitle));
	    }
	    
	    
	}
	
	
	
	//TODO: will get from database
	private ArrayList<Bar> populateBars()
	{
		ArrayList<Bar> bars = new ArrayList<Bar>();
		
		Bar bar = new Bar("622 North", "622 N Main St, Blacksburg, VA 24060",
				"(540) 951-1033", "www.622north.com");
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
				"(540) 552-2030", "www.sharkeyswingandribjoint.com‎");
		bars.add(bar8);
		
		Bar bar9 = new Bar("Sycamore Deli", "211 Draper Rd SW, Blacksburg, VA 24060",
				"(540) 951-9817", "www.sycamoredeli.com");
		bars.add(bar9);
		
		Bar bar10 = new Bar("Top Of The Stairs", "217 College Ave, Blacksburg, VA 24060",
				"(540) 953-2837", "www.topofthestairs.com");
		bars.add(bar10);
		
		return bars;
	}
	
	private void setUpSpinner()
	{
		mSpinner = (Spinner) findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		for (Bar bar : mBars)
		{
			list.add(bar.name);
		}
		/*
		Bar bar1 = new Bar("Big Al's", "201 N Main St, Blacksburg, VA 24060",
				"(540) 951-3300", "www.bigalssportsbar.com");
		list.add(0, bar1.name);
		mBars.add(0, bar1);*/
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
        (this, android.R.layout.simple_spinner_item, list);
		
		dataAdapter.setDropDownViewResource
        (android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(dataAdapter);
		
		//Add Listener for spinner
		mSpinner.setOnItemSelectedListener(this);
	}
	
	private Bar getBarByName(String name)
	{
		//TODO query db
		for (Bar bar : mBars)
		{
			if (bar.name.equals(name))
				return bar;
		}
		//Should never happen! App data needs to match db
		return null;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) 
	{
		String barName = parent.getItemAtPosition(pos).toString();
		Bar selectedBar = getBarByName(barName);
		configureMap(mMap, MapCalculations.getLatLongFromAddress(coder, selectedBar.address), selectedBar.name);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) 
	{
		//Nothing selected??
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) 
	{
		try
		{
			LatLng position = marker.getPosition();
			String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", 
								(float)position.latitude, (float)position.longitude, marker.getTitle());
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.getApplication().startActivity(intent);
			return true;
		}
		catch (Exception e)
		{
			Log.e("Google Maps App", "Google Maps cannot open", e);
			return false;
		}
	}
	
	private boolean isNetworkAvailable() 
	{
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}
