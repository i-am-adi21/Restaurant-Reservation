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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class View_menu_to_order_foods extends Activity implements JsonResponse,OnItemClickListener{

	ListView lv1;
	String amount,logid,ress_id;

	String[] menu_id,item_name,item_image,item_quantity,item_price,item_availability,all;
	SharedPreferences sh,sh1,sh2;
	String men_id;
	public static String mids,price;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_menu_to_order_foods);
		
		lv1=(ListView)findViewById(R.id.lvmenu);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ress_id=sh.getString("ress_id", "");
		
		sh1=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh1.getString("logid", "");


		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_menu_to_order_foods.this;
        String q = "View_menu_to_order_foods?logid="+Login.log_id+"&rest_id="+View_reserved_table.rids;
        q=q.replace(" ","%20");
        JR.execute(q);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_menu_to_order_foods, menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String method=jo.getString("method");
			
			if(method.equalsIgnoreCase("View_menu_to_order_foods")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
				Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					menu_id=new String[ja1.length()];
					item_name=new String[ja1.length()];
					item_image=new String[ja1.length()];
					item_quantity=new String[ja1.length()];
					item_price=new String[ja1.length()];
					item_availability=new String[ja1.length()];
					all=new String[ja1.length()];
					for(int i = 0;i<ja1.length();i++)
					{	
						menu_id[i]=ja1.getJSONObject(i).getString("menu_id");
						item_name[i]=ja1.getJSONObject(i).getString("item_name");
						item_image[i]=ja1.getJSONObject(i).getString("item_image");
						item_quantity[i]=ja1.getJSONObject(i).getString("item_quantity");
						item_price[i]=ja1.getJSONObject(i).getString("item_price");
						item_availability[i]=ja1.getJSONObject(i).getString("item_availability");
//						all[i]=item_name[i]+"\n\nRS."+item_price[i]+"\nAvailability:"+item_availability[i]+"\n\n";
					}
					Order_menu_view Order_menu_view=new Order_menu_view(this,item_image,item_name,item_quantity,item_price,item_availability);
					 lv1.setAdapter(Order_menu_view);
					lv1.setOnItemClickListener(this);
				}
				else
				{
					Toast.makeText(getApplicationContext(), " FAILED TO LOAD DATA.....TRY AGAIN!!", Toast.LENGTH_LONG).show();
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
			Intent b=new Intent(getApplicationContext(),View_table_status.class);			
			startActivity(b);
		}
	 private void selectapart() {
	        final CharSequence[] items = { "Place order", "Cancel"};

	        AlertDialog.Builder builder = new AlertDialog.Builder(View_menu_to_order_foods.this);
	        builder.setTitle("Select Your Option!");
	        builder.setItems(items, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int item) {

	                if (items[item].equals("Place order")) {
	                	sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	            		logid=sh.getString("logid", "");
	            		startActivity(new Intent(getApplicationContext(),Place_an_order.class));
	      
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
		men_id=menu_id[arg2];
		sh2=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Editor e1=sh2.edit();
        e1.putString("men_id", men_id);
        e1.commit();
        mids=menu_id[arg2];
		price=item_price[arg2];
		selectapart();
	}

}
