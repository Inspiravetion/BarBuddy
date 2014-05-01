package com.barbuddy;

import org.json.JSONObject;

/**
 * 
 * @author Charlie Lipford
 * @version Apr 30, 2014
 */

public class Drink {
//Fields
//------------------------------------------------------------------------------

	public String name;
	public int alcoholContent;
	public int size;
	public long price;
	
//Constructor(s)
//------------------------------------------------------------------------------

	public Drink(JSONObject json){
		name = json.optString("name");
		alcoholContent = json.optInt("percent_alc");
		size = json.optInt("size");
		price = json.optLong("price");
	}
	
//Main Methods
//------------------------------------------------------------------------------

//Helper Methofd
//------------------------------------------------------------------------------

//Nested Classes
//------------------------------------------------------------------------------ 
	
}
