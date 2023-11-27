package com.example.graphicalrestaurentreservation;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Home extends Activity {
	
	SharedPreferences sh;
	String logid;
	Button b1,b4,b5,b6,b7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		b1=(Button)findViewById(R.id.bthotels);

		b4=(Button)findViewById(R.id.btfeed);
		b5=(Button)findViewById(R.id.btlogout);
		b6=(Button)findViewById(R.id.btbooking);
		b7=(Button)findViewById(R.id.btreserved);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),ViewHotels.class));
			}
		});
		

		
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),View_feedback.class));
			}
		});
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Login.class));
			}
		});
		b6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),ViewBookings.class));
			}
		});
		b7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),View_reserved_table.class));
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	 public void onBackPressed() 
		{
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),Home.class);			
			startActivity(b);
		}
}
