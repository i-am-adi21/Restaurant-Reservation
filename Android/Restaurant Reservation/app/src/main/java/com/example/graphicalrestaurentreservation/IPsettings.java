package com.example.graphicalrestaurentreservation;

import com.example.graphicalrestaurentreservation.Login;
import com.example.graphicalrestaurentreservation.R;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IPsettings extends Activity {

	EditText e1;
	Button b1;
	public static String ip;
	SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipsettings);
        
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      		e1=(EditText)findViewById(R.id.editText1);
      		b1=(Button)findViewById(R.id.btip);
      		e1.setText(sh.getString("ip", ""));
      		b1.setOnClickListener(new View.OnClickListener() {
      			
      			@Override
      			public void onClick(View arg0) {
      				// TODO Auto-generated method stub
      				ip=e1.getText().toString();
      				
      				if (ip.equals("")) {
      					e1.setError("Enter IP address");
      					e1.setFocusable(true);
      					
      				}else {
      					Editor e= sh.edit();
      					 e.putString("ip",ip);
      					 e.commit();
      					startActivity(new Intent(getApplicationContext(),Login.class));
      					
      				}
      				
      				
      				
      		}
      		});


        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ipsettings, menu);
        return true;
    }
    
}
