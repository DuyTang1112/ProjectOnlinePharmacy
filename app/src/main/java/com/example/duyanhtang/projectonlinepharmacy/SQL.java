package com.example.duyanhtang.projectonlinepharmacy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Duy Anh Tang on 4/24/2017.
 */

public class SQL  extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "project.db";
    public SQL(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE login_info ("
                + " _id TEXT PRIMARY KEY , "
                + "password TEXT NOT NULL  "
                + ");"

        );
        db.execSQL("CREATE TABLE client_info ("
                + " _id TEXT PRIMARY KEY , "
                + "fname TEXT NOT NULL, "
                + "mname TEXT NOT NULL, "
                + "lname TEXT NOT NULL, "
                + "age INTEGER NOT NULL DEFAULT '0', "
                + "gender TEXT NOT NULL, "
                + "email TEXT NOT NULL, "
                + "address TEXT NOT NULL); "

        );
        db.execSQL("CREATE TABLE item_info ("
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT , "
                + "name TEXT NOT NULL, "
                + "description TEXT NOT NULL, "
                + "quantity INTEGER NOT NULL, "
                + "category TEXT NOT NULL, "
                +  "price REAL NOT NULL, "
                +  "image BLOB); "
        );
        db.execSQL("CREATE TABLE transactions ("
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userid TEXT, "
                + "itemid INTEGER NOT NULL, "
                + "quantity INTEGER NOT NULL,"
                + "date DATETIME, "
                + "FOREIGN KEY(userid) REFERENCES client_info(_id), "
                + "FOREIGN KEY(itemid) REFERENCES item_info(_id) ); "

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
