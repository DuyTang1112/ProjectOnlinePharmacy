package com.example.duyanhtang.projectonlinepharmacy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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
            newView = inf.inflate(R.layout.row, null, true);
        else
            newView = convertView;

        TextView name = (TextView) newView.findViewById(R.id.name);
        TextView desc = (TextView) newView.findViewById(R.id.description);
        TextView stock = (TextView) newView.findViewById(R.id.stock);
        TextView price = (TextView) newView.findViewById(R.id.price);
        ImageButton cart = (ImageButton) newView.findViewById(R.id.cart);
        ImageButton search = (ImageButton) newView.findViewById(R.id.find);

        name.setText(item.get(position).getName());
        desc.setText(item.get(position).getDescription());
        stock.setText(item.get(position).getStock());
        price.setText(item.get(position).getPrice() + "");


        return newView;
    }
}

