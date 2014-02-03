package com.umer.wingoku.androidhackathone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class OptionsMenu extends Activity{

	
	private String Shared_Pref_File_Name = "UmerHackathonPrefs";
	private String SHARED_PREF_RATING = "RatingEligible";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflator = getMenuInflater();
		
		inflator.inflate(R.menu.umer_hackathon_options, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		switch(item.getItemId())
		{
		
		case R.id.rateDoctor:
			
			SharedPreferences sharedPref = getSharedPreferences(Shared_Pref_File_Name, 0); // 0 means mode private
			
			if(sharedPref.getBoolean(SHARED_PREF_RATING, false))
			{
				startActivity(new Intent(this, RateDoctor.class));	
			}
			else
				Toast.makeText(this, "Please Rate Doctor after meeting", Toast.LENGTH_SHORT).show();
			//startActivity(new Intent(this, RateDoctor.class));	
			
					
			break;
					
		case R.id.aboutDev:
			
			AlertDialog.Builder dialog = new Builder(OptionsMenu.this);
			
			dialog.setTitle("About Developers");
			dialog.setMessage("Umer Farooq - Android Developer\n" +
					"Ibrahim - Web Develper\n" +
					"Asif - Web Developer");
			
			dialog.setPositiveButton("OK", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
			
				}
			});
			
			dialog.show();
			
			break;
			
		case R.id.exitWingokuHackathon:
			
			finish();
					
			break;
		}
		return false;
	}
	
	
}
