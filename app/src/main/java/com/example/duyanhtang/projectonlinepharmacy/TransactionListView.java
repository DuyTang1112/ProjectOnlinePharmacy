package com.example.duyanhtang.projectonlinepharmacy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duy Anh Tang on 4/28/2017.
 */

public class TransactionListView extends BaseAdapter {
    Context activity;
    Transaction [] transactions;


    public TransactionListView(Context ctx, Transaction [] item){
        activity=ctx;
        this.transactions=item;

    }

    @Override
    public int getCount() {
        return transactions.length;
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
    public View getView( int position, View convertView, ViewGroup parent) {
        View newView = null;
        LayoutInflater inf = LayoutInflater.from(activity);

        if (convertView == null)
            newView = inf.inflate(R.layout.transactions_row, null, true);
        else
            newView = convertView;

        TextView name=(TextView)newView.findViewById(R.id.product_id);
        TextView quant=(TextView)newView.findViewById(R.id.product_quantity);
        TextView date=(TextView)newView.findViewById(R.id.transaction_date);
        TextView id=(TextView)newView.findViewById(R.id.transaction_id);
        TextView amount=(TextView)newView.findViewById(R.id.transaction_amount);
        // ImageButton cart=(ImageButton)newView.findViewById(R.id.cart);
        //ImageButton search= (ImageButton)newView.findViewById(R.id.find);
        ImageView image=(ImageView) newView.findViewById(R.id.productIm);
        Log.d("Transactions item:",transactions[position].toString());

        name.setText(transactions[position].prod_name+"");
        quant.setText("Quantity: "+transactions[position].prod_quantity+"");
        date.setText("Date purchased: \n"+transactions[position].date+"");
        id.setText("Transaction ID: #"+transactions[position].id);
        amount.setText("Total price: \n"+transactions[position].price+" $");



        switch (transactions[position].category){
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



