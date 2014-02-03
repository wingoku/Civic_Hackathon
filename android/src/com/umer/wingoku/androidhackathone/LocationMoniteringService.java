package com.umer.wingoku.androidhackathone;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

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

public class LocationMoniteringService extends Service{

	double userLat, userLongt, hospitalLat, hospitalLong;
	GPSLocation gpsLocation;
	
	private String Shared_Pref_File_Name = "UmerHackathonPrefs";
	private String SHARED_PREF_RATING = "RatingEligible";
	
	String hospitalName ="";
	
	SharedPreferences sharedPref;
	
	
	@Override
	public IBinder onBind(Intent arg0) {
				
		return null;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
//		userLat = intent.getDoubleExtra("userLat", 0);
//		userLongt = intent.getDoubleExtra("userLongt", 0);

		hospitalLat = intent.getDoubleExtra("hospitalLat", -1);
		hospitalLong = intent.getDoubleExtra("hospitalLong", -1);
		
		hospitalName = intent.getStringExtra("hospitalName");
		
		Log.e("Testing", "hos "+ hospitalLat + "   "+ hospitalLong);
		
		
		
		sharedPref = getSharedPreferences(Shared_Pref_File_Name, 0); // 0 means mode private
		
		if(sharedPref.getBoolean("appIsRunFirstTime", true))
		{
			SharedPreferences.Editor prefEditor = sharedPref.edit();
			
			prefEditor.putBoolean("appIsRunFirstTime", false); 
			
			prefEditor.putBoolean(SHARED_PREF_RATING, false);
			
			prefEditor.commit();
			
		}
		
		gpsLocation = new GPSLocation(getApplicationContext());
		
		gpsLocation.locationContinousUpdate();
		
		
		new Timer().scheduleAtFixedRate( new TimerTask() {
			
			@Override
			public void run() {
				
				checkUserGpsCoords();
			}
		}, 500, 30000); // call checkUserGps() after every 30secs
		
		return START_REDELIVER_INTENT;
	}

	
	
	public void checkUserGpsCoords()
	{
		Log.e("Testing", "checking");
		
		if(findNearestHospital() <= 0.500) // within 500 meters of hospital
		{
			// eligible for rating
			
			SharedPreferences.Editor prefEditor = sharedPref.edit();
			
			prefEditor.putBoolean(SHARED_PREF_RATING, true);
			
			prefEditor.commit();
		
			
			RatedHospitalDB rateDB = new RatedHospitalDB(getApplicationContext());
			
			try
			{
				rateDB.writeToDB(hospitalName);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
	
	
	public double findNearestHospital()
	{
		
		final int earthRadius = 6371; // km
		
		double h_lat,h_longt, dLat, dLongt;
		
		h_lat = h_longt = 0;
		
		double a,c;
		
		double distance;
		
		
		h_lat = gpsLocation.getUserLat();
		h_longt = gpsLocation.getUserLongt();
		
		
		dLat =  Math.toRadians((h_lat - hospitalLat));
		
		dLongt = Math.toRadians((h_longt -  hospitalLong));
		
		h_lat = Math.toRadians(h_lat);
		h_longt = Math.toRadians(h_longt);
		
		a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLongt/2) * Math.sin(dLongt/2) * Math.cos(userLongt) * Math.cos(h_lat); 
		
		c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		
		distance = earthRadius * c;
		
		Log.e("Testing", "distance "+ distance);
		
		return distance;
	}
	
}
