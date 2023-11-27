package com.example.graphicalrestaurentreservation;



import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class View_hotel_facilities extends Activity implements JsonResponse{
	
	ListView lv1;
	SharedPreferences sh1;
	String logid;
	String[] facility_des,facility_name,image,all;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_hotel_facilities);
		
		lv1=(ListView)findViewById(R.id.lvhotelfacilities);
		
		sh1=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh1.getString("logid", "");
		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_hotel_facilities.this;
        String q = "View_hotel_facilities?rest_id="+ViewHotels.rids;
        q=q.replace(" ","%20");
        JR.execute(q);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_hotel_facilities, menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String method=jo.getString("method");
			
			if(method.equalsIgnoreCase("View_hotel_facilities")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");

					facility_des=new String[ja1.length()];
					facility_name=new String[ja1.length()];
					image=new String[ja1.length()];
					all=new String[ja1.length()];
					for(int i = 0;i<ja1.length();i++)
					{	

						facility_des[i]=ja1.getJSONObject(i).getString("facility_description");
						facility_name[i]=ja1.getJSONObject(i).getString("facility_name");
						image[i]=ja1.getJSONObject(i).getString("image");
						all[i]="\n\nFacility Name:"+facility_name[i]+"\n\nDescription:"+facility_des[i]+"\n\nImage:"+image[i];
					}
//					ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,all);
//					lv1.setAdapter(ar);
					Customimage ci=new Customimage(View_hotel_facilities.this, image,facility_name,facility_des);

					lv1.setAdapter(ci);
				}
				else
				{
					Toast.makeText(getApplicationContext(), " FAILED TO LOAD DATA.....TRY AGAIN!!", Toast.LENGTH_LONG).show();
				}
			}
			else if(method.equalsIgnoreCase("failed"))
				{
				Toast.makeText(getApplicationContext(), "Failed to load data...", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),View_feedback.class));
				}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			  e.printStackTrace();
			  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	 public void onBackPressed() 
		{
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),Home.class);			
			startActivity(b);
		}
}
