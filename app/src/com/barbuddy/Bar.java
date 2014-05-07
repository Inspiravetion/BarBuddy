package com.barbuddy;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Charlie Lipford
 * @version Apr 30, 2014
 */

public class Bar 
{
//Fields
//------------------------------------------------------------------------------

	public ArrayList<Drink> drinks;
	public ArrayList<Special> specials;
	public String username;
	public String address;
	public String phoneNumber;
	public String website;
	public String logoSrc;
	public String name;
	
	// location?, drink specials
	
//Constructor(s)
//------------------------------------------------------------------------------

	public Bar(JSONObject json) throws JSONException
	{
		drinks = new ArrayList<Drink>();
		specials = new ArrayList<Special>();
		address = json.optString("address");
		phoneNumber = json.optString("phone_number");
		website = json.optString("website");
		logoSrc = json.optString("logo_src"); 
		username = json.optString("username"); 
		name = json.optString("name");

		JSONArray drinkArr = json.optJSONArray("drinks");
		if(drinkArr != null){
			for(int i = 0; i < drinkArr.length(); i++){
				JSONObject drinkJSON = drinkArr.getJSONObject(i);
				drinks.add(new Drink(drinkJSON));
			}
		}

		JSONArray specialsArr = json.optJSONArray("specials");
		if(specialsArr != null){
			for(int i = 0; i < specialsArr.length(); i++){
				JSONObject specialJSON = specialsArr.getJSONObject(i);
				specials.add(new Special(name, specialJSON));
			}
		}
	}
	
	public Bar()
	{
		drinks = new ArrayList<Drink>();
		address = "";
		phoneNumber = "";
		website = "";
	}
	
	public Bar(String newName, String newAddress, String newPhoneNumber, String newWebsite)
	{
		drinks = new ArrayList<Drink>();
		specials = new ArrayList<Special>();
		name = newName;
		address = newAddress;
		phoneNumber = newPhoneNumber;
		website = newWebsite;
	}
	
	@Override
	public String toString() {
		return "{ name: " + this.name 
				+ ", drinks: " + drinks 
				+ ", specials:" + this.specials + " }";
	}
	
//Main Methods
//------------------------------------------------------------------------------

//Helper Methofd
//------------------------------------------------------------------------------

//Nested Classes
//------------------------------------------------------------------------------ 

}
