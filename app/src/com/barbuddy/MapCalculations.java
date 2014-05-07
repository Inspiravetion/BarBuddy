package com.barbuddy;

import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

//METHODS FROM GOOGLE UTIL API

public class MapCalculations 
{
	static final double EARTH_RADIUS = 6371009;
	static double computeDistanceBetween(LatLng from, LatLng to) 
	{
        return computeAngleBetween(from, to) * EARTH_RADIUS;
    }
	
	static double computeAngleBetween(LatLng from, LatLng to) 
	{
        return distanceRadians(Math.toRadians(from.latitude), Math.toRadians(from.longitude),
        		Math.toRadians(to.latitude), Math.toRadians(to.longitude));
    }
	
	static double distanceRadians(double lat1, double lng1, double lat2, double lng2) 
	{
        return arcHav(havDistance(lat1, lat2, lng1 - lng2));
    }
	
	static double havDistance(double lat1, double lat2, double dLng) 
	{
        return hav(lat1 - lat2) + hav(dLng) * Math.cos(lat1) * Math.cos(lat2);
    }
	
	static double hav(double x) 
	{
        double sinHalf = Math.sin(x * 0.5);
        return sinHalf * sinHalf;
    }
	
	static double arcHav(double x) 
	{
        return 2 * Math.asin(Math.sqrt(x));
    }
	
	static LatLng getLatLongFromAddress(Geocoder coder, String straddr)
	{
		//Geocoder coder = new Geocoder(this);
		List<Address> address;

		try 
		{
		    address = coder.getFromLocationName(straddr, 5);
		    if (address == null) 
		    {
		        return null;
		    }
		    Address location = address.get(0);
		    LatLng latLong = new LatLng(location.getLatitude(), location.getLongitude());

		     return latLong;
		}
		catch (Exception e)
		{
			Log.e("Address Error", e.getMessage());
			return null;
		}
	}
}
