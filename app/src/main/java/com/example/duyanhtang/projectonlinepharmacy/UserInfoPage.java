package com.example.duyanhtang.projectonlinepharmacy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class UserInfoPage extends AppCompatActivity {

    TextView fname,lname,mname,agetxt,gender,address,mail,user;
    Intent intent;
    SQL sql;
    SQLiteDatabase db;
    Cursor cur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        sql= SQL.getInstance(UserInfoPage.this);
        db=sql.getReadableDatabase();
        setTitle("User information");

        fname=(TextView)findViewById(R.id.firstnameinfo);
        lname=(TextView)findViewById(R.id.lastinfo);
        mname=(TextView)findViewById(R.id.midleinfo);
        agetxt=(TextView)findViewById(R.id.ageinfo);
        mail=(TextView)findViewById(R.id.mailinfo);
        address=(TextView)findViewById(R.id.addressinfo);
        gender=(TextView)findViewById(R.id.genderinfo);
        user=(TextView)findViewById(R.id.useridinfo);
        intent=getIntent();
        String userid=intent.getStringExtra("userid");
        Log.d("Userinfo",userid);
        cur=db.rawQuery("select fname,mname,lname,age,gender,email,address from client_info where _id=?"
                ,new String[]{userid});
        Log.e("cursor works",cur.getCount()+" ");

      cur.moveToFirst();
        String first=cur.getString(0);
        fname.setText(first);
        String middle=cur.getString(1);
        mname.setText(middle);
        String last=cur.getString(2);
        lname.setText(last);
        String age=cur.getString(3);
        agetxt.setText(age);
        String gendr=cur.getString(4);
        gender.setText(gendr);
        String eml=cur.getString(5);
        mail.setText(eml);
        String adrss=cur.getString(6);
        address.setText(adrss);
        user.setText(userid);

    }
}
