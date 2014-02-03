package com.umer.wingoku.androidhackathone;

import java.net.URI;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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

public class GPSLocation {

	LocationManager manager;
	
	private double userLat;
	private double userLongt;
	
	locationListner listener;
	
	
	
	
	
	public GPSLocation(Context con)
	{
		manager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
		
		listener = new locationListner();
		
		
		//Toast.makeText(con, "finding GPS", Toast.LENGTH_SHORT).show();
	}
	
	
	public void locationSingleUpdate()
	{
		//manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener); // minium time between location updates is 2 secs
		// minimum distance beween location updates is 10 meter
	
		manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, listener, Looper.getMainLooper());
	}
	
	
	public void locationContinousUpdate()
	{
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 500, listener); // minium time between location updates is 10 secs
		// minimum distance beween location updates is 1 KM
		
	}
	
	
	public double getUserLat()
	{
		return userLat;
	}
	
	public double getUserLongt()
	{
		return userLongt;
	}
	
	
	
	private class locationListner implements LocationListener 
	{
		
		@Override
		public void onLocationChanged(Location location) {
		
			userLat = location.getLatitude();
			userLongt = location.getLongitude();
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
	}
	

	
}
