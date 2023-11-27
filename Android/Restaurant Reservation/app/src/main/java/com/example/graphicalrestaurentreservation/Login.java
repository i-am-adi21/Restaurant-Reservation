package com.example.graphicalrestaurentreservation;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.graphicalrestaurentreservation.Home;
import com.example.graphicalrestaurentreservation.JsonReq;
import com.example.graphicalrestaurentreservation.JsonResponse;
import com.example.graphicalrestaurentreservation.Login;
import com.example.graphicalrestaurentreservation.R;
import com.example.graphicalrestaurentreservation.Register;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements JsonResponse{
	
	EditText e1,e2;
	Button b1,b2;
	String uname,pass;
	SharedPreferences sh;
    public static String log_id,type;
    String logid;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		 b1=(Button)findViewById(R.id.btlogin);
		 b2=(Button)findViewById(R.id.btregister);
	        e1=(EditText)findViewById(R.id.etuname);
	        e2=(EditText)findViewById(R.id.etpass);
	        
	        
	        b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					uname=e1.getText().toString();
					pass=e2.getText().toString();
					if(e1.getText().toString().equals(""))
					{
						e1.setError("Enter Username");
						e1.setFocusable(true);
					}
					else if(e2.getText().toString().equals("")){
						e2.setError("Enter Password");
						e2.setFocusable(true);
					}
					else{
					JsonReq JR= new JsonReq();
					JR.json_response=(JsonResponse)Login.this;
					String q="login?username=" + uname + "&password=" + pass;
					JR.execute(q);
					}
					
				//startActivity(new Intent(getApplicationContext(),Customer_Home.class));	
				}
			});
			b2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getApplicationContext(),Register.class));
					
				}
			});

		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	@Override
	  public void response(JSONObject jo) {
// TODO Auto-generated method stub
try {
	String method=jo.getString("method");
	if (method.equalsIgnoreCase("login")) {
    String status = jo.getString("status");
    Log.d("result", status);
    
    if (status.equalsIgnoreCase("success")) {

        JSONArray ja = (JSONArray) jo.getJSONArray("data");
        

        log_id=ja.getJSONObject(0).getString("login_id");
        type = ja.getJSONObject(0).getString("user_type");
        Toast.makeText(getApplicationContext(), logid, Toast.LENGTH_LONG).show();

        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Editor e1=sh.edit();
        e1.putString("logid", logid);
        e1.commit();

//        Toast.makeText(getApplicationContext(), "Log : " + logid, Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),Home.class));

    } 
    else 
    {
        Toast.makeText(getApplicationContext(), "Login failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
    }
	}
	if (method.equalsIgnoreCase("failed")) {
        Toast.makeText(getApplicationContext(), "You are not a user...", Toast.LENGTH_LONG).show();

	}
	

	
} catch (Exception e) {
    e.printStackTrace();
    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
}
}
	public void onBackPressed()
{
  // TODO Auto-generated method stub
  new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
          .setTitle("Exit  :")
          .setMessage("Are you sure you want to exit..?")
          .setPositiveButton("Yes",new DialogInterface.OnClickListener()
          {

              @Override
              public void onClick(DialogInterface arg0, int arg1)
              {
                  // TODO Auto-generated method stub
                  Intent i=new Intent(Intent.ACTION_MAIN);
                  i.addCategory(Intent.CATEGORY_HOME);
                  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(i);
                  finish();
              }
          }).setNegativeButton("No",null).show();

}


}
