package com.unity.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private String query="CREATE TABLE IF NOT EXISTS  calendar (id INTEGER PRIMARY KEY AUTOINCREMENT, start_date CHAR(40) ,end_date CHAR(40), title CHAR(40), content CHAR(40))";
    private String query2="CREATE TABLE IF NOT EXISTS users (userName CHAR(40) PRIMARY KEY, addictionRate CHAR(40),item INTEGER,count INTEGER,countFriends INTEGER)";
    private String query3="CREATE TABLE IF NOT EXISTS apta (id INTEGER PRIMARY KEY AUTOINCREMENT, time INTEGER,preTime INTEGER,accTime)";
    private String query4="INSERT INTO apta(id,time,preTime,accTime) VALUES(null,0,0,0)";
    private String query5="CREATE TABLE IF NOT EXISTS archive(id INTEGER PRIMARY KEY AUTOINCREMENT,category char(10), content char(100),checkItem INTEGER,standards INTEGER)";
    public DatabaseHelper(Context context) {
        super(context, "gamificationDB", null, 1);
    }

    //새 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
    }
    //기존 테이블 삭제하고 새 테이블 생성
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS calendar");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS apptime");
        db.execSQL("DROP TABLE IF EXISTS archive");
        onCreate(db);
    }


}
