package com.umer.wingoku.androidhackathone;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

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

public class StartingPoint extends OptionsMenu implements OnCheckedChangeListener{

	GPSLocation mGPS;
	
	String hospitalDeptName = "";
	


	RadioGroup diseaseDeptGroups;
	Button findHospital;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starting_point);

		
		if(Build.VERSION.SDK_INT >= 11)
		{
			ActionBar actionBar = getActionBar();
			
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff33b5e5"))); // setting color of actionbar
			actionBar.setTitle("WinGoku Health");
		}
		
			
		diseaseDeptGroups = (RadioGroup) findViewById(R.id.healthDeptGroup);
		findHospital = (Button) findViewById(R.id.findHospitals);
		
		diseaseDeptGroups.setOnCheckedChangeListener(this);
		
		findHospital.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
		
					Intent intent = new Intent(StartingPoint.this, Hospital.class);
					
					intent.putExtra("DEPT_NAME", hospitalDeptName);
					
					startActivity(intent);
			}
		});
	
	}
	
	
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		
		RadioButton radioButton = (RadioButton) findViewById(checkedId);
		
		hospitalDeptName = radioButton.getText().toString().toLowerCase();
		
			
	}
		

}
