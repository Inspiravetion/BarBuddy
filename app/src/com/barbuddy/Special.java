package com.barbuddy;

import java.util.ArrayList;

import org.json.JSONObject;

/**
 * 
 * @author Charlie Lipford
 * @version May 1, 2014
 */

public class Special 
{
//Fields
//------------------------------------------------------------------------------

	public String startTime;
	public String endTime;
	public String name;
	public String description;
	public String day;
	public String barName;
	
//Constructor(s)
//------------------------------------------------------------------------------

	public Special(String barName, String name, String day,
			String descr, String start, String finish)
	{
		this.barName = barName;
		this.name = name;
		this.description = descr;
		this.day = day;
		this.startTime = start;
		this.endTime = finish;
	}
	
	public Special(String barName, JSONObject json)
	{
		this.barName = barName;
		this.name = json.optString("name");
		this.description = json.optString("description");
		this.day = json.optString("day");
		this.startTime = json.optString("start");
		this.endTime = json.optString("end");
	}
	
	@Override
	public String toString()
	{
		return "{ \"name\" : " + "\"" + name + "\""
				+ ", \"day\" : " + "\"" + day + "\""
				+ ", \"description\" : " + "\"" + description  + "\""
				+ ", \"startTime\": " + "\"" + startTime  + "\""
				+ ", \"endTime\": " + "\"" + endTime + "\"" + " }";
	}
	
	
//Main Methods
//------------------------------------------------------------------------------

//Helper Method
//------------------------------------------------------------------------------

//Nested Classes
//------------------------------------------------------------------------------ 
	
}
