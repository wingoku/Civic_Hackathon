package com.umer.wingoku.androidhackathone;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

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

public class RateDoctor extends Activity{

	EditText docName, docComments;
	
	RatingBar ratingBar;
	
	String ratingValue;
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.rate_doctor);
		
		if(Build.VERSION.SDK_INT >= 11)
		{
			ActionBar actionBar = getActionBar();
			
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff33b5e5"))); // setting color of actionbar
			actionBar.setTitle("WinGoku Health");
		}
		
		docName = (EditText) findViewById(R.id.doctorName);
		docComments = (EditText) findViewById(R.id.comments);
		
		
		ratingBar = (RatingBar) findViewById(R.id.ratingStarBar);
		
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
	 
				
				ratingValue = String.valueOf(rating);
			}
		});
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try
				{
					postRatingAndName();
					
				}
				catch (Exception e) {
					
					Log.e("HACKATHON", e.toString());
				}
				
			}
		}).start();
	}

	
	public void postRatingAndName() throws URISyntaxException, ClientProtocolException, IOException
	{
		final String and = "&";
		
		final String PHP_SCRIPT_ADDRESS = "http://www.rjsdesigners.com/apps/hackathon/hospital.php?" +"docName="+ docName + and + "rating="+ ratingValue ;
		
		HttpClient client = new DefaultHttpClient();
		
		HttpGet request = new HttpGet(); // HttpGet is used to read data from internet
		
		request.setURI(new URI(PHP_SCRIPT_ADDRESS));
		
		HttpResponse response = client.execute(request);
	}
	
}
