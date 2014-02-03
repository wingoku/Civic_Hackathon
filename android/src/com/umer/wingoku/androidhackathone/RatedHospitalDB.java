package com.umer.wingoku.androidhackathone;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
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

public class RatedHospitalDB extends SQLiteOpenHelper{

	public final static String DB_NAME = "UMER_HACKATHON";
	public final static int DB_VERSION = 1;
	public final static String DB_HOSPITAL_COL = "hospitalName";
	public final static String ID = "id";
	
	Context context;
	private final static String[] columns = {ID, DB_HOSPITAL_COL};
	
	SQLiteDatabase sqlDB;
	
	
	public RatedHospitalDB(Context con) {
		super(con, DB_NAME, null, DB_VERSION);

		context = con;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE IF NOT EXISTS "+ DB_NAME + " (" + ID + " INTEGER PRIMARY KEY, "+ DB_HOSPITAL_COL + " TEXT NOT NULL);");
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public void writeToDB(String hospitalName)
	{		
		sqlDB = this.getWritableDatabase();
		
		if(!checkDuplicateEntry(hospitalName))
		{
			ContentValues cv = new ContentValues();
			
			cv.put(DB_HOSPITAL_COL, hospitalName);
		
			
			long temp = sqlDB.insert(DB_NAME, null, cv);
			
			if(temp == -1)
				Toast.makeText(context, "Failed to write entry in DB", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(context, "Hospital added", Toast.LENGTH_SHORT).show();
			
		}
		else
			Toast.makeText(context, "Hospital already exists", Toast.LENGTH_LONG).show();
		
		sqlDB.close();
		
	}
	
	private boolean checkDuplicateEntry(String hospitalName) // I am checcking path only for avoiding duplicate shortcuts 
	{
		Cursor c = sqlDB.query(DB_NAME, columns, DB_HOSPITAL_COL + "='" + hospitalName + "'", null, null, null, null);
		
		if(c!=null && c.getCount() > 0) // if c is not null this means query method has found match in the database thus it returned cursor pointing to that matche ya mactches row
		{
			//Toast.makeText(context, "true", Toast.LENGTH_SHORT).show();
			
			c.close();
			
			return true;
		}

		return false;
	}
	
	public ArrayList<String> readDB()
	{
		sqlDB = this.getWritableDatabase();
		
		ArrayList<String> packageNames = new ArrayList<String>();

		Cursor c = sqlDB.query(DB_NAME, columns, null, null, null, null, null);

		while(c.moveToNext())
		{
			packageNames.add(c.getString(c.getColumnIndex(DB_HOSPITAL_COL)));
			
		}
		
		sqlDB.close();
		return packageNames;
	}

	
}
