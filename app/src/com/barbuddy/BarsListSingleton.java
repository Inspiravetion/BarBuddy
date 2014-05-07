package com.barbuddy;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

public class BarsListSingleton 
{
	private static BarsListSingleton instance = null;
	private Context context;
	private ArrayList<Bar> bars = new ArrayList<Bar>();
	private Location loc;
	
	private BarsListSingleton(Context context)
	{
		this.context = context;
	}

	public static BarsListSingleton getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new BarsListSingleton(context);
		}
		return instance;
	}
	
	public void setData(ArrayList<Bar> bars)
	{
		this.bars = bars;
	}
	
	public ArrayList<Bar> getData()
	{
		return bars;
	}
}
