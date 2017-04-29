package com.example.duyanhtang.projectonlinepharmacy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class Transactions_activity extends AppCompatActivity {
   ListView lv;
    SQL sql;
    SQLiteDatabase db;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactions_page);
        setTitle("Transactions history");
        sql=SQL.getInstance(Transactions_activity.this);
        db=sql.getReadableDatabase();
        lv=(ListView)findViewById(R.id.transactions_lv);
        userid=getIntent().getStringExtra("userid");
        Cursor cur=db.rawQuery("select i.name, i.category, t._id, t.quantity, t.date, t.total " +
                "from transactions t JOIN item_info i on t.itemid=i._id where t.userid=?",new String[]{userid});
        Log.d("Trans length",cur.getCount()+"");
        cur.moveToFirst();
        Transaction[] trans=new Transaction[cur.getCount()];
        for (int i=0;i<trans.length;i++){
            trans[i]=new Transaction(cur.getString(0), Integer.parseInt(cur.getString(3)), cur.getString(4),
                    Integer.parseInt(cur.getString(2)), Double.parseDouble(cur.getString(5)),cur.getString(1));
            cur.moveToNext();
        }
        TransactionListView tlv=new TransactionListView(Transactions_activity.this,trans);
        lv.setAdapter(tlv);
    }
}
