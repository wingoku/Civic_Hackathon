package com.umer.wingoku.androidhackathone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
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

public class ReadingInternetData {
 
	 
	BufferedReader bufReader = null;
	
	InputStream is;
	InputStreamReader reader;
	Context con;
	
	public ReadingInternetData(URI address, Context context) throws ClientProtocolException, IOException
	{
		con = context;	
		
		HttpClient client = new DefaultHttpClient();
		
		HttpGet request = new HttpGet(); 
		request.setURI(address);
		
		HttpResponse response = client.execute(request);
		

			
		HttpEntity entity = response.getEntity();
		
		is = entity.getContent();
		
		reader = new InputStreamReader(is, "iso-8859-1");
		
		bufReader = new BufferedReader(reader,8);

				
	}
	
	
	public String divideStrings() throws IOException
	{
		StringBuilder sb = new StringBuilder();
		
		String line = ""; 
		
		
		while((line = bufReader.readLine() ) != null)
		{
			sb.append(line);
		}
		
		reader.close();
		is.close();
		
		if(sb == null)
			Toast.makeText(con, "unable to get response", Toast.LENGTH_SHORT).show();
		
		Log.e("haktathone", sb.toString());
		
		return sb.toString();
	}
}
