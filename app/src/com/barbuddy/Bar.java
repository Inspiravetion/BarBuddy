package com.barbuddy;

import java.util.ArrayList;

import org.json.JSONObject;

/**
 * 
 * @author Charlie Lipford
 * @version Apr 30, 2014
 */

public class Bar {
//Fields
//------------------------------------------------------------------------------

	public ArrayList<Drink> drinks;
	public String address;
	public String phoneNumber;
	public String website;
	public String logoSrc;
	
	// location?, drink specials
	
//Constructor(s)
//------------------------------------------------------------------------------

	public Bar(JSONObject json){
		drinks = new ArrayList<Drink>();
		address = json.optString("address");
		phoneNumber = json.optString("phone_number");
		website = json.optString("website");
		logoSrc = json.optString("logo_src"); 
	}
	
	public Bar(){
		drinks = new ArrayList<Drink>();
		address = "";
		phoneNumber = "";
		website = "";
	}
	
//Main Methods
//------------------------------------------------------------------------------

//Helper Methofd
//------------------------------------------------------------------------------

//Nested Classes
//------------------------------------------------------------------------------ 

}
