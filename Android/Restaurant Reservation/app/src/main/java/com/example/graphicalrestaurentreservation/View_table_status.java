package com.example.graphicalrestaurentreservation;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.graphicalrestaurentreservation.JsonReq;
import com.example.graphicalrestaurentreservation.JsonResponse;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class View_table_status extends Activity implements JsonResponse, OnItemClickListener{
	
	String[] cat_name,cat_des,table_id,table_name,no_of_seat,table_availability,all;
	ListView lv1;
	SharedPreferences sh,sh1;
	public  static String logid,tab_id,table_availabilitys;
	Button b1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_table_status);
		
		lv1=(ListView)findViewById(R.id.lvtable);
		b1=(Button)findViewById(R.id.btviewbookedtable);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),View_reserved_table.class));
			}
		});
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_table_status.this;
        String q = "View_table_status?rest_id="+ViewHotels.rids;
        q=q.replace(" ","%20");
        JR.execute(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_table_status, menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String method=jo.getString("method");
			
			if(method.equalsIgnoreCase("View_table_status")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
				Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					table_id=new String[ja1.length()];
					cat_name=new String[ja1.length()];
					cat_des=new String[ja1.length()];
					table_name=new String[ja1.length()];
					no_of_seat=new String[ja1.length()];
					table_availability=new String[ja1.length()];
					all=new String[ja1.length()];
					for(int i = 0;i<ja1.length();i++)
					{	
						table_id[i]=ja1.getJSONObject(i).getString("table_id");
						cat_name[i]=ja1.getJSONObject(i).getString("category_name");
						cat_des[i]=ja1.getJSONObject(i).getString("category_description");
						table_name[i]=ja1.getJSONObject(i).getString("table_name");
						no_of_seat[i]=ja1.getJSONObject(i).getString("number_of_seats");
						table_availability[i]=ja1.getJSONObject(i).getString("table_availability");
						
						all[i]="Category:"+cat_name[i]+"\nDescription:"+cat_des[i]+"\nTable:"+table_name[i]+"\nNo.Of.Seats:"+no_of_seat[i]+"\nAvailable tables:"+table_availability[i]+"\n\n";
					}
					ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list,all);
					lv1.setAdapter(ar);
					lv1.setOnItemClickListener(this);
				}
				else
				{
					Toast.makeText(getApplicationContext(), " FAILED TO LOAD DATA.....TRY AGAIN!!", Toast.LENGTH_LONG).show();
				}
			
			}
			if(method.equalsIgnoreCase("reserve_a_table")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
				Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					Toast.makeText(getApplicationContext(), "Reservation successful.", Toast.LENGTH_LONG).show();
				}
				else if(status.equalsIgnoreCase("request already send")){
					Toast.makeText(getApplicationContext(), " Request Already Send", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(getApplicationContext(), " FAILED.....TRY AGAIN!!", Toast.LENGTH_LONG).show();
				}
				}
				
			 if(method.equalsIgnoreCase("failed"))
				{
				Toast.makeText(getApplicationContext(), "Failed to load data...", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),Home.class));
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
	 private void selectapart() {
		 if (table_availabilitys.equalsIgnoreCase("available")) {
			 final CharSequence[] items = {"Reserve Table", "Cancel"};

			 AlertDialog.Builder builder = new AlertDialog.Builder(View_table_status.this);
			 builder.setTitle("Select Your Option!");
			 builder.setItems(items, new DialogInterface.OnClickListener() {
				 @Override
				 public void onClick(DialogInterface dialog, int item) {

					 if (items[item].equals("Reserve Table")) {

						 sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						 logid = sh.getString("logid", "");
						 Toast.makeText(getApplicationContext(), logid, Toast.LENGTH_LONG).show();
						 JsonReq JR = new JsonReq();
						 JR.json_response = (JsonResponse) View_table_status.this;
						 String q = "reserve_a_table?tab_id=" + tab_id + "&logid=" + Login.log_id;
						 q = q.replace(" ", "%20");
						 JR.execute(q);
						 Log.d("pearl", q);

					 } else if (items[item].equals("Cancel")) {
						 dialog.dismiss();
					 }
				 }
			 });
			 builder.show();
		 }
	 }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		tab_id=table_id[arg2];
		table_availabilitys=table_availability[arg2];
		selectapart();
	}

}
