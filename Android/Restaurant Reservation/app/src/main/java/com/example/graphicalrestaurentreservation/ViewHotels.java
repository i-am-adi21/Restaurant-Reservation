package com.example.graphicalrestaurentreservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewHotels extends Activity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView lv1;
    SharedPreferences sh1;
    String logid;
    String[] rid,name,place,landmark,street,pincode,phone,email,hstatus,val;
    public static String rids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotels);

        lv1=(ListView)findViewById(R.id.lvhotels);
        lv1.setOnItemClickListener(this);

        sh1= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid=sh1.getString("logid", "");

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewHotels.this;
        String q = "View_hotels";
        q=q.replace(" ","%20");
        JR.execute(q);
    }
    public void response(JSONObject jo) {


        try{
            String method=jo.getString("method");
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
            if(method.equalsIgnoreCase("View_hotels")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    rid=new String[ja1.length()];
                    name=new String[ja1.length()];
                    place=new String[ja1.length()];
                    landmark=new String[ja1.length()];
                    street=new String[ja1.length()];
                    pincode=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    email=new String[ja1.length()];
                    hstatus=new String[ja1.length()];

                    val=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        rid[i]=ja1.getJSONObject(i).getString("restaurant_id");
                        name[i]=ja1.getJSONObject(i).getString("name");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        landmark[i]=ja1.getJSONObject(i).getString("landmark");
                        street[i]=ja1.getJSONObject(i).getString("street");
                        pincode[i]=ja1.getJSONObject(i).getString("pincode");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        email[i]=ja1.getJSONObject(i).getString("email");
                        hstatus[i]=ja1.getJSONObject(i).getString("status");

                        val[i]="Name : "+name[i]+"\nPlace : "+place[i]+"\nLandmark : "+landmark[i]+"\nStreet : "+street[i]+"\nPincode : "+pincode[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i]+"\nStatus : "+hstatus[i];


                    }
                    ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list,val);
                    lv1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                }

                else

                {
                    Toast.makeText(getApplicationContext(), "No Restaurants!!", Toast.LENGTH_LONG).show();

                }
            }

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        rids=rid[arg2];

        final CharSequence[] items = {"View Facilities","View Menu","Table Status","Rate","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewHotels.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Facilities"))
                {
                    startActivity(new Intent(getApplicationContext(),View_hotel_facilities.class));
                }
               else if (items[item].equals("View Menu"))
                {
                    startActivity(new Intent(getApplicationContext(),View_menu.class));
                }
                else if (items[item].equals("Table Status"))
                {
                    startActivity(new Intent(getApplicationContext(),View_table_status.class));
                }
                else if (items[item].equals("Rate"))
                {
                    startActivity(new Intent(getApplicationContext(),RateRestaurant.class));
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
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Home.class);
        startActivity(b);
    }
}