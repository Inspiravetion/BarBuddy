package com.barbuddy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author Charlie Lipford
 * @version Apr 30, 2014
 */

public class DataBase {
	//Fields
	//------------------------------------------------------------------------------

		private String key = "?apiKey=5074a573e4b088be4c29efc4";
		private String baseUrl = "https://api.mongolab.com/api/1";
		private String dbName = "/barbuddy";

	//Constructor(s)
	//------------------------------------------------------------------------------

		public DataBase()
		{

		}

	//Main Methods
	//------------------------------------------------------------------------------

		public void getBars(BarsCallback cb)
		{
			getDocument("/bars", new BarsRequestCallback(cb));
		}

	//Helper Method
	//------------------------------------------------------------------------------

		private void getDocument(String collection, RequestCallback cb)
		{
			String url = collectionUrl() + collection + key;
			HttpRequestBase request = new HttpGet(url);

			new RequestTask(cb).execute(request);
		}

		private String collectionUrl()
		{
			return baseUrl + "/databases" + dbName + "/collections";
		}

	//Nested Classes
	//------------------------------------------------------------------------------ 

		interface RequestCallback 
		{
			abstract void call(JSONArray json) throws JSONException;
		}

		interface BarsCallback 
		{
			abstract void call(ArrayList<Bar> bars);
		}

		class BarsRequestCallback implements RequestCallback 
		{
			private BarsCallback cb;

			public BarsRequestCallback(BarsCallback cb){
				this.cb = cb;
			}

			public void call(JSONArray jsonArr) throws JSONException
			{			
				ArrayList<Bar> bars = new ArrayList<Bar>();

				for(int i = 0; i < jsonArr.length(); i++){
					bars.add(new Bar(jsonArr.getJSONObject(i)));
				}

				this.cb.call(bars);
			}
		}

		/*use like 
		 * new RequestTask(new RequestCallback(){
		 *   
		 *   @Override
		 *   public void call(JSONObject json){
		 *   	//do something with json
		 *   }
		 *   
		 * }).execute("http://stackoverflow.com");
		 */
		class RequestTask extends AsyncTask<HttpRequestBase, String, String>{

			private RequestCallback cb;

			public RequestTask(RequestCallback cb){
				super();
				this.cb = cb;
			}

		    @Override
		    protected String doInBackground(HttpRequestBase... req) {
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpResponse response;
		        String responseString = null;
		        try {
		            response = httpclient.execute(req[0]);

		            StatusLine statusLine = response.getStatusLine();
		            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
		                ByteArrayOutputStream out = new ByteArrayOutputStream();
		                response.getEntity().writeTo(out);
		                out.close();
		                responseString = out.toString();
		            } else{
		                //Closes the connection.
		                response.getEntity().getContent().close();
		                throw new IOException(statusLine.getReasonPhrase());
		            }
		        } catch (ClientProtocolException e) {
		            //TODO Handle problems..
		        	Log.i("charlie", e.toString());
		        } catch (IOException e) {
		            //TODO Handle problems..
		        	Log.i("charlie", e.toString());
		        }
		        return responseString;
		    }

		    @Override
		    protected void onPostExecute(String result) {
		        super.onPostExecute(result);
		        try {
					cb.call(new JSONArray(result)); //this wont work as result is not proper json
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}

	}
