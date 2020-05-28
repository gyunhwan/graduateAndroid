package com.unity.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String tableName = "users";
    private String query="CREATE TABLE calendar IF NOT EXISTS (start_date PRIMARY KEY text,end_date text,title text,content text)";
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db,query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void createTable(SQLiteDatabase db, String query)
    {
        try
        {
            Log.d("오우:","싸발적이고");
            db.execSQL(query);
        }
        catch (SQLException e)
        {
        }
    }
    public void insert(SQLiteDatabase db, String sql)
    {
        db.beginTransaction();
        try
        {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
        }
    }
}
