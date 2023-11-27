package com.example.graphicalrestaurentreservation;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class View_menu extends Activity implements JsonResponse{
	ListView lv1;
	String[] menu_id,item_name,item_image,item_quantity,item_price,item_availability,all;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_menu);
		
		lv1=(ListView)findViewById(R.id.lvmenu);
		
	
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_menu.this;
        String q = "View_menu?rest_id="+ViewHotels.rids;
        q=q.replace(" ","%20");
        JR.execute(q);
       	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_menu, menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String method=jo.getString("method");
			
			if(method.equalsIgnoreCase("View_menu")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					menu_id=new String[ja1.length()];
					item_name=new String[ja1.length()];
					item_image=new String[ja1.length()];
					item_quantity=new String[ja1.length()];
					item_price=new String[ja1.length()];
					item_availability=new String[ja1.length()];
					for(int i = 0;i<ja1.length();i++)
					{	
						menu_id[i]=ja1.getJSONObject(i).getString("menu_id");
						item_name[i]=ja1.getJSONObject(i).getString("item_name");
						item_image[i]=ja1.getJSONObject(i).getString("item_image");
						item_quantity[i]=ja1.getJSONObject(i).getString("item_quantity");
						item_price[i]=ja1.getJSONObject(i).getString("item_price");
						item_availability[i]=ja1.getJSONObject(i).getString("item_availability");
//						all[i]=facility_name[i]+"\n\nDesacription:"+facility_des[i]+"\n\n";
					}
					Custom_Menu_View Custom_Menu_View=new Custom_Menu_View(this,item_image,item_name,item_quantity,item_price,item_availability);
					 lv1.setAdapter(Custom_Menu_View);
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
