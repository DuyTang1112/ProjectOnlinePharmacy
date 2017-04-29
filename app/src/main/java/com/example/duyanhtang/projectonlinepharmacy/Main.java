package com.example.duyanhtang.projectonlinepharmacy;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Main extends AppCompatActivity {
    final int CARTPAGE_REQUESTCODE=100;
    ListView lv;
    EditText searchbar;
    ImageButton searchbutton,cartbutton;
    HashMap<String,Integer> cart;
    SQL sql;
    SQLiteDatabase db;
    String userid;
    Cursor cur;
    listView lvadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.absolut);
        setTitle("Store");
        sql=SQL.getInstance(Main.this);
        db=sql.getReadableDatabase();
        lv=(ListView) findViewById(R.id.listview);
        searchbar=(EditText) findViewById(R.id.searchbar);
        searchbutton=(ImageButton) findViewById(R.id.find);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchbar.getText().toString().length()==0) {
                    readData();
                    return;
                }
                String sql="select name, description, quantity, category, price from item_info " +
                        "where name like '%"+searchbar.getText().toString().trim().toLowerCase()+
                        "%' or category like '%"+searchbar.getText().toString().trim().toLowerCase()+"%'";
                Cursor cur=db.rawQuery(sql,null);
                cur.moveToFirst();
                Item[] items=new Item[cur.getCount()];
                for (int i=0;i<items.length;i++){
                    int quantity=Integer.parseInt(cur.getString(2));
                    if (quantity>0){
                        items[i]=new Item(cur.getString(0),cur.getString(1),
                                quantity,cur.getString(3),
                                Double.parseDouble(cur.getString(4)));
                        Log.d("Item",items[i].toString());
                    }
                    cur.moveToNext();
                }
                listView lvadapter=new listView(Main.this,items,cart);
                lv.setAdapter(lvadapter);
            }
        });
        cartbutton=(ImageButton) findViewById(R.id.cart);
        cartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* String[] itemName=new String[cart.size()];
                int[] itemCount=new int[cart.size()];
                int i=0;
                for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    itemName[i]=key;
                    itemCount[i]=value;
                    i++;
                    // ...
                }
                Log.d("Purchased items", Arrays.toString(itemName));
                Log.d("Purchased quantity", Arrays.toString(itemCount));*/
                Intent intent=new Intent(Main.this,CartPage.class);
                intent.putExtra("cart",cart);
                intent.putExtra("userid",userid);
                startActivityForResult(intent,CARTPAGE_REQUESTCODE);
               // intent.putExtra("items",itemName);
               // intent.putExtra("quantity",itemCount);

            }
        });
        cart=new HashMap<>();
        userid=getIntent().getStringExtra("user");
        Log.d("User logging in",userid);
        readData();// set list view to have all data
        checkinfo();// debug
    }

    private void checkinfo() {
        cur=db.rawQuery("select * from client_info",null);
        Log.d("count",cur.getCount()+"");
        cur.moveToFirst();
        Log.d("User logging in info:",cur.getString(0)+" "+cur.getString(1)+" "+cur.getString(2)+" "+cur.getString(3)+" "
        + cur.getString(4)+" "+cur.getString(5)+" "+ cur.getString(6)+" "+cur.getString(7)+" ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sel_category:
                return true;
            case R.id.med_category:
                cur=db.rawQuery("select name, description, quantity, category, price from item_info " +
                        "where category=? ",new String[]{"Medicine"});
                break;
            case R.id.pers_care_category:
                cur=db.rawQuery("select name, description, quantity, category, price from item_info " +
                        "where category=? ",new String[]{"Personal Care"});
                break;
            case R.id.vitamins_category:
                cur=db.rawQuery("select name, description, quantity, category, price from item_info " +
                        "where category=? ",new String[]{"Vitamins"});
                break;
            case R.id.beauty_category:
                cur=db.rawQuery("select name, description, quantity, category, price from item_info " +
                        "where category=? ",new String[]{"Beauty"});
                break;
            case R.id.sport_category:
                cur=db.rawQuery("select name, description, quantity, category, price from item_info " +
                        "where category=? ",new String[]{"Sports"});
                break;
            case R.id.user_info:
                Intent intent=new Intent(Main.this,UserInfoPage.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                return true;
            case R.id.trans_hist:
                Intent inte=new Intent(Main.this,Transactions_activity.class);
                inte.putExtra("userid",userid);
                startActivity(inte);
                return true;
            case R.id.sign_out:
                onBackPressed();
                return true;
        }
        cur.moveToFirst();
        Item[] items=new Item[cur.getCount()];
        for (int i=0;i<items.length;i++){
            int quantity=Integer.parseInt(cur.getString(2));
            if (quantity>0){
                items[i]=new Item(cur.getString(0),cur.getString(1),
                        quantity,cur.getString(3),
                        Double.parseDouble(cur.getString(4)));
                Log.d("Item",items[i].toString());
            }
            cur.moveToNext();
        }
        lvadapter=new listView(Main.this,items,cart);
        lv.setAdapter(lvadapter);
        return true;
    }

    /**
     * showing all items
     */
    private void readData() {

        cur=db.rawQuery("select name, description, quantity, category, price from item_info",null);
        cur.moveToFirst();
        Item[] items=new Item[cur.getCount()];
        for (int i=0;i<items.length;i++){
            int quantity=Integer.parseInt(cur.getString(2));
            if (quantity>0){
            items[i]=new Item(cur.getString(0),cur.getString(1),
                    quantity,cur.getString(3),
                    Double.parseDouble(cur.getString(4)));
            Log.d("Item",items[i].toString());
            }
            cur.moveToNext();

        }
        listView lvadapter=new listView(Main.this,items,cart);
        lv.setAdapter(lvadapter);
    }
    // handle when user has updated the cart page
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CARTPAGE_REQUESTCODE){
            if (data!=null){
                cart = (HashMap<String, Integer>)data.getSerializableExtra("cart");
                Log.d("Cart list updated","Size="+cart.size());
                readData();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Main.this);
        builder.setTitle("Sign Out?");
        builder.setMessage("Do you want to sign out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Main.this.finish();
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }
}
