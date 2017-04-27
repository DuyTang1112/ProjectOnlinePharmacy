package com.example.duyanhtang.projectonlinepharmacy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class listView extends BaseAdapter {
    Context activity;
    Item [] item;
    HashMap<String,Integer> cart;


    public listView(Context ctx, Item [] item,HashMap<String,Integer> cart){
        activity=ctx;
        this.item=item;
        this.cart=cart;
    }

    @Override
    public int getCount() {
        return item.length;
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
            newView = inf.inflate(R.layout.row, null, true);
        else
            newView = convertView;

        TextView name=(TextView)newView.findViewById(R.id.name);
        TextView desc=(TextView)newView.findViewById(R.id.description);
        TextView stock=(TextView)newView.findViewById(R.id.stock);
        TextView price=(TextView)newView.findViewById(R.id.price);
       // ImageButton cart=(ImageButton)newView.findViewById(R.id.cart);
        //ImageButton search= (ImageButton)newView.findViewById(R.id.find);
        ImageView image=(ImageView) newView.findViewById(R.id.itemimage);

        name.setText(item[position].getName());
        desc.setText(item[position].getDescription());
        stock.setText("#: "+item[position].getStock());
        price.setText(item[position].getPrice()+"$");
        switch (item[position].category){
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
        }

        return newView;
    }
}
