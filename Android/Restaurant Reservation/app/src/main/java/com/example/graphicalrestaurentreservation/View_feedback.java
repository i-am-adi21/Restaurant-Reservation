package com.example.graphicalrestaurentreservation;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.graphicalrestaurentreservation.JsonReq;
import com.example.graphicalrestaurentreservation.JsonResponse;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class View_feedback extends Activity implements JsonResponse{
	
	ListView lv1;
	Button b1;
	EditText e1;
	SharedPreferences sh1;
	String desc,logid;
	String[] feed_date,feed_id,feed_des,reply_des,all;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_feedback);
		
		lv1=(ListView)findViewById(R.id.lvfeedtable);
		b1=(Button)findViewById(R.id.btaddfeed);
		e1=(EditText)findViewById(R.id.etfeed);
		
		sh1=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh1.getString("logid", "");
		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_feedback.this;
        String q = "View_feedback?logid="+Login.log_id;
        q=q.replace(" ","%20");
        JR.execute(q);
        
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				desc=e1.getText().toString();
				
				JsonReq JR=new JsonReq();
		        JR.json_response=(JsonResponse) View_feedback.this;
		        String q = "addfeedback?desc="+desc+"&logid="+Login.log_id;
		        q=q.replace(" ","%20");
		        JR.execute(q);}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_feedback, menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String method=jo.getString("method");
			
			if(method.equalsIgnoreCase("View_feedback")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					feed_id=new String[ja1.length()];
					feed_des=new String[ja1.length()];
					feed_date=new String[ja1.length()];
					reply_des=new String[ja1.length()];
					all=new String[ja1.length()];
					for(int i = 0;i<ja1.length();i++)
					{	
						feed_id[i]=ja1.getJSONObject(i).getString("feedback_id");
						feed_des[i]=ja1.getJSONObject(i).getString("feedback_description");
						reply_des[i]=ja1.getJSONObject(i).getString("reply_descr");
						feed_date[i]=ja1.getJSONObject(i).getString("date_time");
						all[i]="\n\nFeedback :"+feed_des[i]+"\n\nDate:"+feed_date[i]+"\n\nReply:"+reply_des[i];
					}
					ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list,all);
					lv1.setAdapter(ar);
				}
				else
				{
					Toast.makeText(getApplicationContext(), " FAILED TO LOAD DATA.....TRY AGAIN!!", Toast.LENGTH_LONG).show();
				}
			
			}
			else if(method.equalsIgnoreCase("addfeedback")){
				
				String status=jo.getString("status");
				Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
				
				
				if(status.equalsIgnoreCase("success")){
					Toast.makeText(getApplicationContext(), "Feedback added successfully..", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),View_feedback.class));
				}
				else{
					Toast.makeText(getApplicationContext(), "Failed. Try again after sometime..", Toast.LENGTH_LONG).show();
				}
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
