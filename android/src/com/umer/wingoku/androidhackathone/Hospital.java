package com.umer.wingoku.androidhackathone;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.umer.androidhackathone.R;

/* ========================================================================
 * Author: Umer Farooq 
 * Website: www.wingoku.com
 * 
 * Contributers: Ibrahim Ahmed
 * 				 Asif Ali Khan
 * 
 * Website: http://www.wingoku.com
 * 
 */

public class Hospital extends OptionsMenu{

	
	String latitude[];
	String longitude[];
	String hospitalName[];
	String hospitalAddress[];
	
	
	double userLocationLat, userLocationLongt;
	
	ListView hospitalsList;
	
	String dept;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hospitals_list);
		
		new ClinicGpsLocationFromDB().execute();
		
		if(Build.VERSION.SDK_INT >= 11)
		{
			ActionBar actionBar = getActionBar();
			
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff33b5e5"))); // setting color of actionbar
			actionBar.setTitle("WinGoku Health");
		}
		
		hospitalsList = (ListView) findViewById(R.id.listView1);
		
		hospitalsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				startGpsMoniteringService(position);
				
			}
		});
		
		dept = getIntent().getExtras().getString("DEPT_NAME");
		
	}
	
	
	
	public void startGpsMoniteringService(int position)
	{
		
		Intent intent = new Intent(Hospital.this, LocationMoniteringService.class);
		
		intent.putExtra("hospitalName", hospitalName[position]);

		intent.putExtra("hospitalLat", latitude[position]);
		intent.putExtra("hospitalLong", longitude[position]);
		
		
//		intent.putExtra("userLat", userLocationLat);
//		intent.putExtra("userLong", userLocationLongt);
		
		
		startService(intent);
	}
		
	
	
	
	public class ClinicGpsLocationFromDB extends AsyncTask<Void, String, String>
	{
		
		boolean noInternet = false;
		
		ReadingInternetData internetDB;
		
		final static String PHP_SCRIPT_ADDRESS = "http://www.rjsdesigners.com/apps/hackathon/hospital.php?";
		
		//final static String UPLOAD_PHP_SCRIPT_ADDRESS = "http://www.dreams-soft.com/apps/hackathon/hospital.php";
		
		final static String H_LATITUDE = "lat";
		final static String H_LONGITUDE = "long";
		final static String H_NAME = "name";
		final static String H_ADDRESS = "address";
		
		
		
		
		@Override
		protected String doInBackground(Void... cred) {
		
//			userLocationLat = 29.011778;
//			userLocationLongt = 50.568886;
	
			//Toast.makeText(getApplicationContext(), "hellow", Toast.LENGTH_SHORT).show();
			
			try
			{
				Looper.myLooper().prepare(); // for gps locatoin manager
				
				Log.e("Hackathon", "inside");
//				
				
				GPSLocation location = new GPSLocation(Hospital.this);
				
				location.locationSingleUpdate();
				
				Log.e("Hackathon", "getting");
				
				Thread.sleep(2000);
				
				final double lat = location.getUserLat();
				final double longt = location.getUserLongt();

				userLocationLat = lat;
				userLocationLongt = longt;
				
				
				
				
				Log.e("Hackathon", "GPS "+ lat + "    "+ longt);
//				String latitude = String.valueOf(userLocationLat);
//				String longitude = String.valueOf(userLocationLongt);
				
				readSubjectsFromInternet(lat, longt);
				
				
				
			}
			catch(Exception e)
			{
				Log.e("UMER HACKTHONE", e.toString());
			}
			
			return null;
		}
		

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if(hospitalName != null)
			{			
				hospitalsList.setAdapter(new CustomListAdapter(Hospital.this, hospitalName, hospitalAddress));
			
				hospitalsList.invalidate();
			}
			else
				Toast.makeText(getApplicationContext(), "no data returned", Toast.LENGTH_SHORT).show();
			

		} 
		
		
		
		public void readSubjectsFromInternet(double lat, double longt) throws ClientProtocolException, IOException, URISyntaxException, JSONException
		{
			String and = "&";
			
			//if(internetAvailable.checkAvailabilityOfInternet(Hospital.this))
			{
				String address = PHP_SCRIPT_ADDRESS+"dept="+dept+and+"lat="+lat+and+"long="+longt;
				
				ReadingInternetData internet = new ReadingInternetData(new URI(address), Hospital.this); //"http://wingoku.bugs3.com/uetapp/getassignmentslist.php"
				
				//Toast.makeText(Hospital.this, address, Toast.LENGTH_LONG).show();
				
				Log.e("Testing", address);
				
				String sb = internet.divideStrings();
				
				JSONArray array = new JSONArray(sb);
				
//				latitude = new String[array.length()];
//				longitude = new String[array.length()];
				hospitalName = new String[array.length()];
				hospitalAddress = new String[array.length()];
				
				//System.out.println("length is "+array.length());
				
				int length = array.length();
				
				for(int i=0;i< length; i++)
				{
					JSONObject temp = array.getJSONObject(i);
					
//					latitude[i]= temp.getString(H_LATITUDE); // json tag
//					
//					longitude[i] = temp.getString(H_LONGITUDE);
//					
					hospitalName[i] = temp.getString(H_NAME); 
					
					hospitalAddress[i] = temp.getString(H_ADDRESS);
				}
			}
//			else
//			{//Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_SHORT).show();
//
//			noInternet=true;
//			}
			

		}
	
		
	
		
	}
	
	
}


		

