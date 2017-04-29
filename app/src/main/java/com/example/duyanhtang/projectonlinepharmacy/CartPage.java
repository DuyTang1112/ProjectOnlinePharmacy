package com.example.duyanhtang.projectonlinepharmacy;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CartPage extends AppCompatActivity {
    ListView lv;
    TextView totalPriceCart;
    Button finish;
    HashMap<String, Integer> cart;
    SQL sql;
    SQLiteDatabase db;
    Vector<Item> items;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        setTitle("Cart");
        sql = SQL.getInstance(CartPage.this);
        db = sql.getReadableDatabase();
        lv = (ListView) findViewById(R.id.cartList);
        totalPriceCart = (TextView) findViewById(R.id.totalCart);
        finish = (Button) findViewById(R.id.purchase);
        cart = (HashMap<String, Integer>) getIntent().getSerializableExtra("cart");
        userid=getIntent().getStringExtra("userid");
        items = new Vector<>();
       /* for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Log.d("Cart in cart page:",key+" / "+value);
            // ...
        }*/
        double sum=0;
        int i=0;
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String itemName = entry.getKey();
            Integer itemQuantity = entry.getValue();
            Cursor cur = db.rawQuery("select name, description, quantity, category, price from item_info where name=?", new String[]{itemName});
            cur.moveToFirst();
            items.add(new Item(cur.getString(0), cur.getString(1),
                    Integer.parseInt(cur.getString(2)), cur.getString(3),
                    Double.parseDouble(cur.getString(4))));
            Log.d("Item in cart",items.get(i).toString());
            sum+=itemQuantity*items.get(i).getPrice();
            i++;
        }

        Log.d("Size items",items.size()+"");
        totalPriceCart.setText("Total: "+String.format("%.2f", sum)+" $");

        BaseAdapter adapter=new CartListView(CartPage.this, items, cart,totalPriceCart);
        lv.setAdapter(adapter);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.size()==0) return;
                db = sql.getWritableDatabase();
                for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                    String itemName = entry.getKey();
                    Integer itemQuantity = entry.getValue();
                    Cursor cur = db.rawQuery("select _id from item_info where name=?", new String[]{itemName});
                    cur.moveToFirst();
                    int id=Integer.parseInt(cur.getString(0));
                    ContentValues values = new ContentValues();
                    values.put("userid",userid);
                    values.put("itemid",id);
                    values.put("quantity",itemQuantity);
                    String total=totalPriceCart.getText().toString();
                    values.put("total",Double.parseDouble(total.substring(7,total.length()-2)));
                    db.insert("transactions",null,values);
                    Log.i("Purchased",itemName+" "+ itemQuantity);
                    String updateString="UPDATE item_info SET quantity = quantity - "+itemQuantity+" where name = '"+itemName+"' ;";
                    Log.d("Update syntax",updateString);
                    db.execSQL(updateString);
                }
                Cursor cur = db.rawQuery("select * from transactions", null);
                Log.d("Purchased length",cur.getCount()+" ");
                // updating the cart and the list view once purchased button pressed
                cart=new HashMap<String, Integer>();
                items=new Vector<Item>();
                BaseAdapter adapter=new CartListView(CartPage.this, items, cart,totalPriceCart);
                lv.setAdapter(adapter);
                totalPriceCart.setText("Total: "+String.format("%.2f", (float)0)+" $");

                //show dialog
                AlertDialog.Builder builder=new AlertDialog.Builder(CartPage.this);
                builder.setTitle("Purchase completed");
                builder.setMessage("Your order has been sent to one of salesman. " +
                        "The person will contact you in order to finalize the process. Thanks for using our app!");
                builder.setPositiveButton("Continue my shopping", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                builder.create().show();
            }
        });
    }
    //override the back button to update the cart
    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("cart",cart);
        setResult(1,intent);
        finish();
    }
}
