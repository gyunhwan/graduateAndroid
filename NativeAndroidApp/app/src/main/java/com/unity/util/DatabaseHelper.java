package com.unity.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private String query="CREATE TABLE IF NOT EXISTS  calendar (id INTEGER PRIMARY KEY AUTOINCREMENT, start_date CHAR(40) ,end_date CHAR(40), title CHAR(40), content CHAR(40))";
    private String query2="CREATE TABLE IF NOT EXISTS user (userName TEXT PRIMARY KEY, addictionRate CHAR(40),item INTEGER )";
    public DatabaseHelper(Context context) {
        super(context, "gamificationDB", null, 1);
    }

    //새 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
        db.execSQL(query2);
    }
    //기존 테이블 삭제하고 새 테이블 생성
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS calendar");
        onCreate(db);
    }


}
