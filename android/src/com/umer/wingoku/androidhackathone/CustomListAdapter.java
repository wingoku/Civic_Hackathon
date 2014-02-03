package com.umer.wingoku.androidhackathone;


import com.umer.androidhackathone.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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


public class CustomListAdapter extends ArrayAdapter<String>{

	Context context;
	String[] stringList, dateList;
	TextView medium, small;
	
	
	public CustomListAdapter (Context con, String[] sL, String[] date)
	{
		super(con, R.layout.my_list, R.id.hospName, sL); 
		context = con;
		
		stringList = sL;
		dateList = date;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
	
		LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		
		View newRowView = inflator.inflate(R.layout.my_list, parent,false);
		medium = (TextView) newRowView.findViewById(R.id.hospName);
		small = (TextView) newRowView.findViewById(R.id.address);
		
		medium.setText(stringList[position]);
		
		if(dateList != null)
			small.setText(dateList[position]);
		
		
		return newRowView; 
	}
	
	
	
}
