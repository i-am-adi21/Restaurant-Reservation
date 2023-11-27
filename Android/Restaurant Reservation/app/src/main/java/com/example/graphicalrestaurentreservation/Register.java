package com.example.graphicalrestaurentreservation;

import org.json.JSONObject;

import com.example.graphicalrestaurentreservation.Login;
import com.example.graphicalrestaurentreservation.JsonReq;
import com.example.graphicalrestaurentreservation.JsonResponse;
import com.example.graphicalrestaurentreservation.Register;
import com.example.graphicalrestaurentreservation.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Register extends Activity implements JsonResponse{
	
	
	Button b1;
	EditText e1,e2,e3,e4,e5,e6,e7;
	RadioButton rb1,rb2,rb3;
	String fname,lname,place,phone,email,username,password;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		
		b1=(Button)findViewById(R.id.btsubmit);
		e1=(EditText)findViewById(R.id.etfname);
		e2=(EditText)findViewById(R.id.etlname);
		e3=(EditText)findViewById(R.id.etplace);
		e4=(EditText)findViewById(R.id.etphone);
		e5=(EditText)findViewById(R.id.etemail);
		e6=(EditText)findViewById(R.id.etusername);
		e7=(EditText)findViewById(R.id.etpassword);
		
b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fname=e1.getText().toString();
				lname=e2.getText().toString();
				place=e3.getText().toString();
				phone=e4.getText().toString();
				email=e5.getText().toString();
				username=e6.getText().toString();
				password=e7.getText().toString();
				if(e1.getText().toString().equals(""))
				{
					e1.setError("Enter valid information");
					e1.setFocusable(true);
				}
				else if(e2.getText().toString().equals("")){
					e2.setError("Enter valid information");
					e2.setFocusable(true);
				}
				else if(e3.getText().toString().equals("")){
					e3.setError("Enter valid information");
					e3.setFocusable(true);
				}
				else if(e4.getText().toString().equals("")){
					e4.setError("Enter valid information");
					e4.setFocusable(true);
				}
				else if(e5.getText().toString().equals("")){
					e5.setError("Enter valid information");
					e5.setFocusable(true);
				}
				
				else if(e6.getText().toString().equals("")){
					e6.setError("Enter valid information");
					e6.setFocusable(true);
				}
				else if(e7.getText().toString().equals("")){
					e7.setError("Enter valid information");
					e7.setFocusable(true);
				}else if(e7.getText().toString().length()<8)
				{
					e7.setError("Password should be a minimum length of 8 characters");
					e7.setFocusable(true);
				}
				else{
				JsonReq JR=new JsonReq();
		        JR.json_response=(JsonResponse) Register.this;
		        String q = "registration?fname="+fname+"&lname="+lname+"&place="+place+"&phone="+phone+"&email="+email+"&username="+username+"&password="+password;
		        q=q.replace(" ","%20");
		        JR.execute(q);
				}
			}
		});


		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String status=jo.getString("status");
			Log.d("pearl",status);
			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
			 
			
			if(status.equalsIgnoreCase("success")){
				
				
			 
				 startActivity(new Intent(getApplicationContext(),Login.class));	
			    
				 Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
				 
			}
			else if(status.equalsIgnoreCase("duplicate")){
				
				
				 
				 Toast.makeText(getApplicationContext(), "Username already Exist...", Toast.LENGTH_LONG).show();
				 
			}
			else
			{
				Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
	}

}
