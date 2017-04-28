package com.example.duyanhtang.projectonlinepharmacy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by dani1covi on 4/26/17.
 */

public class CartListView extends BaseAdapter {
    Context activity;
    Vector<Item> item;
    HashMap<String, Integer> cart;


    public CartListView(Context ctx, Vector<Item> item, HashMap<String,Integer> hm) {
        activity = ctx;
        this.item = item;
        cart=hm;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = null;
        LayoutInflater inf = LayoutInflater.from(activity);

        if (convertView == null)
            newView = inf.inflate(R.layout.cartlay, null, true);
        else
            newView = convertView;

        TextView name = (TextView) newView.findViewById(R.id.name_cart);
        TextView desc = (TextView) newView.findViewById(R.id.desc);
        TextView stock = (TextView) newView.findViewById(R.id.totalnum);
        TextView totalprice=(TextView) newView.findViewById(R.id.totalprice);
        ImageView image= (ImageView) newView.findViewById(R.id.img_cart);
        String itemname=item.get(position).getName();
        name.setText(itemname);
        desc.setText(item.get(position).getDescription());


        Log.d("Just checking "+itemname,cart.get(itemname)+"");
        int quantity=cart.get(itemname);
        stock.setText(quantity+"");
        totalprice.setText(quantity*item.get(position).getPrice()+"$");
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


        return newView;
    }
}


