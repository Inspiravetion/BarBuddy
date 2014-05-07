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
	public String alcoholContent;
	public String size;
	public String price;
	
//Constructor(s)
//------------------------------------------------------------------------------

	public Drink(JSONObject json)
	{
		name = json.optString("name");
		alcoholContent = json.optString("percent_alc");
		size = json.optString("size");
		price = json.optString("price");
	}
	
	public Drink(String name, String alcCont, String size, String price)
	{
		this.name = name;
		alcoholContent = alcCont;
		this.size = size;
		this.price = price;
	}
	
	/*@Override 
	public String toString(){
		return "{ name: " + name 
				+ ", alcoholContent: " + alcoholContent 
				+ ", size: " + size 
				+ ", price: " + price + "}";
	}*/
	
	@Override 
	public String toString()
	{
		return "{ \"name\": " + "\"" + name + "\"" 
				+ ", \"alcoholContent\": " + "\"" + alcoholContent + "\""
				+ ", \"size\": " + "\"" + size + "\""
				+ ", \"price\": " + "\"" + price + "\"" + "}";
	}
	
//Main Methods
//------------------------------------------------------------------------------

//Helper Methofd
//------------------------------------------------------------------------------

//Nested Classes
//------------------------------------------------------------------------------ 
	
}
