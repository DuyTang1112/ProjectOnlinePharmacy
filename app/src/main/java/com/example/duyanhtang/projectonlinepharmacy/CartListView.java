package com.example.duyanhtang.projectonlinepharmacy;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by dani1covi on 4/26/17.
 */

public class CartListView extends BaseAdapter {
    Context activity;
    Vector<Item> item;
    HashMap<String, Integer> cart;
    TextView textView;


    public CartListView(Context ctx, Vector<Item> item, HashMap<String,Integer> hm,TextView lv) {
        activity = ctx;
        this.item = item;
        cart=hm;
        this.textView=lv;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View newView = null;
        LayoutInflater inf = LayoutInflater.from(activity);

        if (convertView == null)
            newView = inf.inflate(R.layout.cartlay, null, true);
        else
            newView = convertView;

        ImageButton sub=(ImageButton) newView.findViewById(R.id.sub);
        ImageButton add=(ImageButton) newView.findViewById(R.id.add);
        TextView name = (TextView) newView.findViewById(R.id.name_cart);
        TextView desc = (TextView) newView.findViewById(R.id.desc);
        TextView stock = (TextView) newView.findViewById(R.id.totalnum);
        TextView totalprice=(TextView) newView.findViewById(R.id.totalprice);
        ImageView image= (ImageView) newView.findViewById(R.id.img_cart);
        final String itemname=item.get(position).getName();
        name.setText(itemname);
        desc.setText(item.get(position).getDescription());
        Log.d("Just checking "+itemname,cart.get(itemname)+"");
        final int quantity=cart.get(itemname);
        stock.setText(quantity+"");
        totalprice.setText(String.format("%.2f", quantity*item.get(position).getPrice())+"$");
        switch (item.get(position).category){
            case "Medicine":
                image.setImageResource(R.drawable.medicine);
                break;
            case "Personal Care":
                image.setImageResource(R.drawable.personalcare);
                break;
            case "Vitamins":
                image.setImageResource(R.drawable.vitamins);
                break;
            case "Beauty":
                image.setImageResource(R.drawable.beauty);
                break;
            case "Sports":
                image.setImageResource(R.drawable.sport);
                break;
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.get(itemname)+1>item.get(position).getStock()) {
                    Toast.makeText(activity,"Cannot add more item",Toast.LENGTH_SHORT).show();
                    return;
                }
                cart.put(itemname,cart.get(itemname)+1);
                Log.d("Update item:",itemname+": "+cart.get(itemname));
                //update total price in the cart
                int i=0;
                double sum=0;
                for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                    Integer itemQuantity = entry.getValue();
                    sum+=itemQuantity*item.get(i).getPrice();
                    i++;
                }
                textView.setText("Total: "+String.format("%.2f", sum)+" $");
                notifyDataSetChanged(); //update the list view
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.put(itemname,cart.get(itemname)-1);
                Log.d("Update item:",itemname+": "+cart.get(itemname));
                if (cart.get(itemname)==0){
                    // delete the element in item and cart
                    for (int i=0;i<item.size();i++){
                        if (item.get(i).getName().equals(itemname)){
                            item.remove(i);
                            break;
                        }
                    }
                    cart.remove(itemname);
                    Toast.makeText(activity,"Item removed successfully",Toast.LENGTH_SHORT).show();
                }
                //update total price in the cart
                int i=0;
                double sum=0;
                for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                    Integer itemQuantity = entry.getValue();
                    sum+=itemQuantity*item.get(i).getPrice();
                    i++;
                }
                textView.setText("Total: "+String.format("%.2f", sum)+" $");
                notifyDataSetChanged(); //update the list view

            }
        });
        return newView;
    }
}


