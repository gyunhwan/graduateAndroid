package com.unity.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String tableName = "users";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void createTable(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE IF NOT EXIST" + tableName + "(user_name text primary key ," +
                "addiction_degree text," +
                "item integer" +
                ")";
        try
        {
            db.execSQL(sql);
        }
        catch (SQLException e)
        {
        }
    }
    public void insertName(SQLiteDatabase db, String sql)
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
