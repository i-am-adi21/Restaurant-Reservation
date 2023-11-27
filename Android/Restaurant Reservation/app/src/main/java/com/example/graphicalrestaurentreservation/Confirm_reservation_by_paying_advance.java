package com.example.graphicalrestaurentreservation;

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
import android.widget.Toast;

public class Confirm_reservation_by_paying_advance extends Activity implements JsonResponse{

	EditText ed_card_num, ed_cvv, ed_exp_date, ed_amount;
	Button bt_pay;
	String amount, card_no, cvv, exp_date;
	SharedPreferences sh;
	String logid,ress_id;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_reservation_by_paying_advance);

		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ed_card_num = findViewById(R.id.ed_card_num);
		ed_cvv = findViewById(R.id.ed_cvv);
		ed_exp_date = findViewById(R.id.ed_exp_date);
		ed_amount = findViewById(R.id.ed_amount);
//		ed_amount.setText(My_orders.tot);
		bt_pay = findViewById(R.id.bt_pay);


		bt_pay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				amount=ed_amount.getText().toString();
				JsonReq JR=new JsonReq();
		        JR.json_response=(JsonResponse) Confirm_reservation_by_paying_advance.this;
		        String q = "Confirm_reservation_by_paying_advance?amount="+amount+"&logid="+Login.log_id+"&ress_id="+View_reserved_table.ress_id;
		        q=q.replace(" ","%20");
		        JR.execute(q);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirm_reservation_by_paying_advance,
				menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String method=jo.getString("method");
			
			if(method.equalsIgnoreCase("Confirm_reservation_by_paying_advance")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
				Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					Toast.makeText(getApplicationContext(), "Reservation has been confirmed successfully.", Toast.LENGTH_LONG).show();
						startActivity(new Intent(getApplicationContext(),Home.class));
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
			Intent b=new Intent(getApplicationContext(),View_reserved_table.class);			
			startActivity(b);
		}

}
