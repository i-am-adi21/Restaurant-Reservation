package com.example.graphicalrestaurentreservation;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class View_reserved_table extends Activity implements JsonResponse,OnItemClickListener{
	
	ListView lv1;
	String[] rid,cat_name,cat_des,table_id,table_name,no_of_seat,table_availability,res_date_time,res_status,res_id,all;
	public  static String logid,ress_id;
	SharedPreferences sh,sh1,sh2;
	public static String rids;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_reserved_table);
		
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");



		lv1=(ListView)findViewById(R.id.lvreservedtables);
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_reserved_table.this;
        String q = "View_reserved_table?logid="+Login.log_id;
        q=q.replace(" ","%20");
        JR.execute(q);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_reserved_table, menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String method=jo.getString("method");
			
			if(method.equalsIgnoreCase("View_reserved_table")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
				Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					rid=new String[ja1.length()];
					table_id=new String[ja1.length()];
					cat_name=new String[ja1.length()];
					cat_des=new String[ja1.length()];
					table_name=new String[ja1.length()];
					no_of_seat=new String[ja1.length()];
					table_availability=new String[ja1.length()];
					res_date_time=new String[ja1.length()];
					res_status=new String[ja1.length()];
					res_id=new String[ja1.length()];
					all=new String[ja1.length()];
					for(int i = 0;i<ja1.length();i++)
					{
						rid[i]=ja1.getJSONObject(i).getString("restaurant_id");
						res_id[i]=ja1.getJSONObject(i).getString("reservation_id");
						table_id[i]=ja1.getJSONObject(i).getString("table_id");
						cat_name[i]=ja1.getJSONObject(i).getString("category_name");
						cat_des[i]=ja1.getJSONObject(i).getString("category_description");
						table_name[i]=ja1.getJSONObject(i).getString("table_name");
						no_of_seat[i]=ja1.getJSONObject(i).getString("number_of_seats");
						table_availability[i]=ja1.getJSONObject(i).getString("table_availability");
						res_date_time[i]=ja1.getJSONObject(i).getString("reservation_date_time");
						res_status[i]=ja1.getJSONObject(i).getString("reservation_status");
						all[i]="Category:"+cat_name[i]+"\nDescription:"+cat_des[i]+"\nTable:"+table_name[i]+"\nDate:"+res_date_time[i]+"\nStatus:"+res_status[i]+"\n\n";
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
				else{
					Toast.makeText(getApplicationContext(), " FAILED.....TRY AGAIN!!", Toast.LENGTH_LONG).show();
				}
				}
				
			 if(method.equalsIgnoreCase("failed"))
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
	 private void selectapart() {
	        final CharSequence[] items = { "Order Foods","Pay advance and Confirm", "Cancel"};

	        AlertDialog.Builder builder = new AlertDialog.Builder(View_reserved_table.this);
	        builder.setTitle("Select Your Option!");
	        builder.setItems(items, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int item) {

	                if (items[item].equals("Pay advance and Confirm")) {
	                	sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	            		logid=sh.getString("logid", "");
	            		startActivity(new Intent(getApplicationContext(),Confirm_reservation_by_paying_advance.class));
	      
	                }else if(items[item].equals("Order Foods"))
	                {
	                	startActivity(new Intent(getApplicationContext(),View_menu_to_order_foods.class));
	                }
	                else if (items[item].equals("Cancel")) {
	                    dialog.dismiss();
	                }
	            }
	        });
	        builder.show();
	    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		ress_id=res_id[arg2];
		sh2=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Editor e1=sh2.edit();
        e1.putString("ress_id", ress_id);
        e1.commit();
		rids=rid[arg2];
		selectapart();
	}

}
