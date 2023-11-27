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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Place_an_order extends Activity implements JsonResponse{

	EditText e1;
	Button b1;
	public static String men_id,ress_id;
	SharedPreferences sh,sh1;
	TextView t1;
	String qty;
	public static	int no,item_quantity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_an_order);
		
		e1=(EditText)findViewById(R.id.etquatity);
		b1=(Button)findViewById(R.id.btorder);
		t1=(TextView)findViewById(R.id.mrp);
		t1.setText(View_menu_to_order_foods.price);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		men_id=sh.getString("men_id", "");
		
		sh1=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ress_id=sh1.getString("ress_id", "");
		Toast.makeText(getApplicationContext(), e1.getText().toString(), Toast.LENGTH_LONG).show();

			
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				qty=e1.getText().toString();

				if(qty.equalsIgnoreCase("")){
					e1.setError("Enter Quantity");
					e1.setFocusable(true);
				}else{
					JsonReq jr = new JsonReq();
					jr.json_response = (JsonResponse) Place_an_order.this;
					String q = "/Place_an_order?logid=" + sh.getString("logid", "") + "&qty="+qty
							+ "&pid=" + View_menu_to_order_foods.mids + "&amount=" + View_menu_to_order_foods.price +"&men_id=" + men_id+"&ress_id=" + ress_id;
					q = q.replace(" ", "%20");

					jr.execute(q);
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.place_an_order, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		try {
			String status = jo.getString("status");
			String method = jo.getString("method");
			if (method.equalsIgnoreCase("Place_an_order")) {
				if (status.equalsIgnoreCase("success")) {
					Toast.makeText(getApplicationContext(), "success...",
							Toast.LENGTH_LONG).show();

					startActivity(new Intent(getApplicationContext(), ViewBookings.class));
				} else {
					Toast.makeText(getApplicationContext(), "Failed",
							Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(), Home.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : " + e,
					Toast.LENGTH_LONG).show();
		}

	}

}
