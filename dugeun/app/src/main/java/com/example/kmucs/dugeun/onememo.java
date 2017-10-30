package com.example.kmucs.dugeun;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class onememo extends SQLiteOpenHelper{

    private  static final String DB_NAME="EDBTDev";
    private static final int DB_VER = 1;
    public static final String DB_TABLE = "Task";
    public static final String DB_COLUMN ="TaskName";


    public onememo(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL", DB_TABLE, DB_COLUMN);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN, task);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String t){}
}
