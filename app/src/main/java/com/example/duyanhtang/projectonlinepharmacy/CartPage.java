package com.example.duyanhtang.projectonlinepharmacy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        BaseAdapter adapter=new CartListView(CartPage.this, items, cart);
        lv.setAdapter(adapter);
    }
}
