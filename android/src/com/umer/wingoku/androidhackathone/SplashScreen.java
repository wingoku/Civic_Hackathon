package com.umer.wingoku.androidhackathone;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.umer.androidhackathone.R;

public class SplashScreen extends Activity{

	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(Build.VERSION.SDK_INT >= 11)
		{
			getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
			getActionBar().hide();
		}
		else
			requestWindowFeature(Window.FEATURE_NO_TITLE);  
		
		setContentView(R.layout.splash_screen);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				startActivity(new Intent(SplashScreen.this, StartingPoint.class));
				
				finish();
				
			}
		}, 5000);
	}
	
}
