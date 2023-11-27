package com.example.graphicalrestaurentreservation;

import com.example.graphicalrestaurentreservation.R;
import com.squareup.picasso.Picasso;
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

public class Order_menu_view extends ArrayAdapter<String>{
	
	String[] item_image,item_name,item_quantity,item_price,item_availability;
	
	private Activity context;
	SharedPreferences sh;
	public Order_menu_view(Activity context,String[] item_image,String[] item_name,String[] item_quantity,String[] item_price,String[] item_availability){
		
	super(context,R.layout.order_menu_view,item_image);
	this.context=context;
	this.item_image=item_image;
	this.item_name=item_name;
	this.item_quantity=item_quantity;
	this.item_price=item_price;
	this.item_availability=item_availability;
	
	}
public View getView(int position,View convertView,ViewGroup parent)
{
LayoutInflater inflater=context.getLayoutInflater();
View listViewItem=inflater.inflate(R.layout.custom_menu_view, null, true);


TextView t1= (TextView) listViewItem.findViewById(R.id.textView1);
TextView t2= (TextView) listViewItem.findViewById(R.id.textView2);
TextView t3= (TextView) listViewItem.findViewById(R.id.textView3);

ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
t1.setText(item_name[position]);
t2.setText(item_price[position]);
t3.setText(item_availability[position]);
sh=PreferenceManager.getDefaultSharedPreferences(getContext());

String pth = "http://"+sh.getString("ip", "")+"/"+item_image[position];
pth = pth.replace("~", "");
 
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
