package com.example.graphicalrestaurentreservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewBookings extends Activity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] rname,tid,rdate_time,rstatus,val,reservation_id,table_id;
    SharedPreferences sh1;
    String logid;
    public static String tids,reservation_ids,table_ids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);
        l1=(ListView)findViewById(R.id.lvbookings);
        l1.setOnItemClickListener(this);

        sh1= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid=sh1.getString("logid", "");

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewBookings.this;
        String q = "View_bookings?login_id="+Login.log_id;
        q=q.replace(" ","%20");
        JR.execute(q);

    }
    public void response(JSONObject jo) {


        try {
            String method = jo.getString("method");
//            if(method.equalsIgnoreCase("user_send_complaints")){
//                String status=jo.getString("status");
//                Log.d("pearl",status);
//                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
//                if(status.equalsIgnoreCase("success")){
//
//                    Toast.makeText(getApplicationContext(), " SENT", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(),User_send_complaints.class));
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(),Users_home.class));
//                }
//            }
            if (method.equalsIgnoreCase("View_bookings")) {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    reservation_id= new String[ja1.length()];
                    table_id = new String[ja1.length()];
                    rname = new String[ja1.length()];
                    tid = new String[ja1.length()];
                    rdate_time = new String[ja1.length()];
                    rstatus = new String[ja1.length()];


                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {

                        reservation_id[i] = ja1.getJSONObject(i).getString("reservation_id");
                        table_id[i] = ja1.getJSONObject(i).getString("table_id");
                        rname[i] = ja1.getJSONObject(i).getString("name");
                        tid[i] = ja1.getJSONObject(i).getString("table_name");
                        rdate_time[i] = ja1.getJSONObject(i).getString("reservation_date_time");
                        rstatus[i] = ja1.getJSONObject(i).getString("reservation_status");

                        val[i] = "Restaurant Name : " + rname[i] + "\nTable Name : " + tid[i] + "\nReservation Date&Time : " + rdate_time[i] + "\nReservation Status : " + rstatus[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, val);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                } else {
                    Toast.makeText(getApplicationContext(), "No Bookings!!", Toast.LENGTH_LONG).show();

                }
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            tids=tid[arg2];
            table_ids=table_id[arg2];
            reservation_ids=reservation_id[arg2];

            final CharSequence[] items = {"Scan QR","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBookings.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Scan QR"))
                    {
                        startActivity(new Intent(getApplicationContext(),AndroidBarcodeQrExample.class));
                    }
                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(i, GALLERY_CODE);
        }
    }


