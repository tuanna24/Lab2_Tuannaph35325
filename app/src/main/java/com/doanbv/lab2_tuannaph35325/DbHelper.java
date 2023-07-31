package com.doanbv.lab2_tuannaph35325;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){super(context,"ToDo.db",null,3);}
    String TodoDB = "CREATE TABLE Todo (ID Integer primary key autoincrement, Title text, Content text, Date text,Type text,Status int)";

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(TodoDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS Todo");
            onCreate(db);
        }
    }
}
