package com.example.graphicalrestaurentreservation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Customimage extends ArrayAdapter<String> {
    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] image,facility_name,facility_des;


    public Customimage(Activity context, String[] image, String[] facility_name, String[] facility_des) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.customimage, image);
        this.context = context;
        this.image = image;
        this.facility_name = facility_name;
        this.facility_des=facility_des;

    }


    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.customimage, null, true);
        //cust_list_view is xml file of layout created in step no.2

        ImageView im = (ImageView) listViewItem.findViewById(R.id.ib1);

        TextView t2=(TextView)listViewItem.findViewById(R.id.tv2);
        TextView t3=(TextView)listViewItem.findViewById(R.id.tv3);




        t2.setText("Facility Name : "+ facility_name[position]);
        t3.setText("Facility Description : "+ facility_des[position]);




        sh= PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+image[position];
        pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

        Log.d("-------------", pth);
        Picasso.with(context)
                .load(pth)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher).into(im);

        return  listViewItem;
    }

    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}