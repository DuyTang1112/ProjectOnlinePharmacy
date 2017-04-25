package com.example.duyanhtang.projectonlinepharmacy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginPage extends Activity {
    SQL sql;
    SQLiteDatabase db;
    Button login,newuser;
    TextView status;
    String[] categories={"Medicine","Personal Care","Vitamins","Beauty"};
    AssetManager asm;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Login Page");
        login=(Button) findViewById(R.id.login);
        newuser=(Button) findViewById(R.id.newuser);
        status=(TextView) findViewById(R.id.status);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginPage.this,NewUser.class);

                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean deleted = LoginPage.this.deleteDatabase("hw5.db");
                if (deleted) {
                    Toast.makeText(LoginPage.this, "Database deleted", Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(LoginPage.this, "Database not deleted", Toast.LENGTH_LONG).show();*/
            }
        });
        Log.d("Reading","");
        sharedPref=getSharedPreferences("check", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("checker",false)){
            readData();
        }
    }

    private void readData() {
        asm = LoginPage.this.getResources().getAssets();
        try {
            InputStreamReader isr=new InputStreamReader(asm.open("data.txt"));
            BufferedReader buf = new BufferedReader(isr);
            String s;
            int i=1;
            while((s=buf.readLine())!=null){
                i++;
                String[] arr=s.split(";");
                //status.setText(status.getText()+"\nLength "+s.split(";").length+"");

            }
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
