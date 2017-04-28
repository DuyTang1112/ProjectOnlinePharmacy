package com.example.duyanhtang.projectonlinepharmacy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Duy Anh Tang on 4/24/2017.
 */
//This is a singleton class
public class SQL  extends SQLiteOpenHelper {
    public static SQL sqlInstance=null;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "project.db";
    public SQL(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized SQL getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sqlInstance == null) {
            sqlInstance = new SQL(context.getApplicationContext());
        }
        return sqlInstance;
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
                + "quantity INTEGER NOT NULL, "
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "total REAL, "
                + "FOREIGN KEY(userid) REFERENCES client_info(_id), "
                + "FOREIGN KEY(itemid) REFERENCES item_info(_id) ); "

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS login_info" );
        db.execSQL("DROP TABLE IF EXISTS item_info");
        db.execSQL("DROP TABLE IF EXISTS client_info");
        db.execSQL("DROP TABLE IF EXISTS Transactions_activity");

        // create new tables
        onCreate(db);
    }
}
