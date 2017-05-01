package com.example.duyanhtang.projectonlinepharmacy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class LoginPage extends Activity {
    final int NEW_USER=0,MAIN=221;
    SQL sql;
    SQLiteDatabase db;
    Button login,newuser;
    TextView status;
    //String[] categories={"Medicine","Personal Care","Vitamins","Beauty"};
    AssetManager asm;
    SharedPreferences sharedPref;
    EditText user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Login Page");
        //LoginPage.this.deleteDatabase("project.db");
        login=(Button) findViewById(R.id.login);
        newuser=(Button) findViewById(R.id.newuser);
        status=(TextView) findViewById(R.id.status);
        user=(EditText) findViewById(R.id.userlogin);
        pass=(EditText) findViewById(R.id.passwordlogin);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginPage.this,NewUser.class);

                startActivityForResult(intent,NEW_USER);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().length()*pass.getText().toString().length()==0){
                    Toast.makeText(LoginPage.this,"All fields should be filled",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("Login user",user.getText().toString());
                Log.d("Login pass",pass.getText().toString());
                db=sql.getReadableDatabase();
                Cursor cur=db.rawQuery("select password from login_info where _id=?",new String[]{user.getText().toString()});
                if (cur.getCount()==0){
                    Toast.makeText(LoginPage.this,"No such user is found",Toast.LENGTH_LONG).show();

                }
                else{
                    cur.moveToFirst();
                    if (cur.getString(0).equals(pass.getText().toString())){
                        Toast.makeText(LoginPage.this,"Login successfully",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(LoginPage.this,Main.class);
                        intent.putExtra("user",user.getText().toString());
                        startActivityForResult(intent,MAIN);
                    }
                    else{
                        Toast.makeText(LoginPage.this,"Password does not match",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        Log.d("Reading","");
        sharedPref=getSharedPreferences("check", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("checker",false)){
            readData();
        }
    }

    private void readData() {
        sql= SQL.getInstance(LoginPage.this);
        db=sql.getWritableDatabase();
        asm = LoginPage.this.getResources().getAssets();
        try {
            InputStreamReader isr=new InputStreamReader(asm.open("data.txt"));
            BufferedReader buf = new BufferedReader(isr);
            String s;
            while((s=buf.readLine())!=null){
                String[] arr=s.split(";");
                //Log.d("Array", Arrays.toString( arr));
               // Log.d("Length",""+arr.length);
                //check for existing item
                Cursor cur=db.rawQuery("SELECT * from item_info WHERE name=?",new String[]{arr[0]});
                if (cur.getCount()==0){ // if item does not exist then add
                    ContentValues values = new ContentValues();
                    values.put("name", arr[0]);
                    values.put("description", arr[1]);
                    values.put("quantity", Integer.parseInt(arr[2]));
                    values.put("category",arr[3]);
                    values.put("price",Float.parseFloat(arr[4]));
                    if (db.insert("item_info", null, values)==-1){
                        Log.d("Status sql read","read not successful");
                    }
                    else{
                        Log.d("Status sql read","read successful");
                    }
                }
                else{
                    Log.d("Status sql read","already read this");
                }
            }

            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==NEW_USER ){
            if (data!=null){
                if (data.getIntExtra("result",-1)==0) Toast.makeText(LoginPage.this,"Cannot register new user",Toast.LENGTH_LONG).show();
                else if (data.getIntExtra("result",-1)==1) Toast.makeText(LoginPage.this,"New user created successfully ",Toast.LENGTH_LONG).show();
            }
            if (resultCode==2) Toast.makeText(LoginPage.this,"New user created successfully ",Toast.LENGTH_LONG).show();
            pass.setText("");
            //if (resultCode==2)Toast.makeText(LoginPage.this,"New user created successfully ",Toast.LENGTH_LONG).show();
            //else if (resultCode==1) Toast.makeText(LoginPage.this,"Cannot register new user",Toast.LENGTH_LONG).show();
        }
        else if (requestCode==MAIN){
            pass.setText("");
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
