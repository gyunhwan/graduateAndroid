package com.unity.mynativeapp;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.unity.model.TimeVO;
import com.unity.model.UsersVO;
import com.unity.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TestActivity extends Activity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(TestActivity.this);
        getAppUsageStats();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAppUsageStats() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        int time = 0;
        UsageStatsManager manager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> list = manager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, cal.getTimeInMillis(), System.currentTimeMillis());
        List<UsageStats> list2 = new ArrayList<UsageStats>();

        for (UsageStats stats : list) {
            if (String.valueOf(stats.getPackageName()).equals("com.ss.android.ugc.trill")) {
//                list2.add(stats);
                time += stats.getTotalTimeInForeground() / 60000;
            } else if (String.valueOf(stats.getPackageName()).equals("com.google.android.youtube")) {
//                list2.add(stats);
                time += stats.getTotalTimeInForeground() / 60000;
            } else if (String.valueOf(stats.getPackageName()).equals("com.facebook.katana")) {
//                list2.add(stats);
                time += stats.getTotalTimeInForeground() / 60000;
            } else if (String.valueOf(stats.getPackageName()).equals("com.instagram.android")) {
//                list.add(stats);
                time += stats.getTotalTimeInForeground() / 60000;
            }
        }
        Log.d("빡세긴하네:", String.valueOf(time));
        db = dbHelper.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS apta (id INTEGER PRIMARY KEY AUTOINCREMENT, time INTEGER,preTime INTEGER,accTime)");
        db = dbHelper.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM apta ", null);
        TimeVO vo = new TimeVO();
        UsersVO uvo = new UsersVO();
        if (!cur.moveToNext()) {
            db.close();
            db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO apta(id,time,preTime,accTime) VALUES(null,'" + time + "','" + time + "',0)");
            db.close();
        } else {
            cur = db.rawQuery("SELECT * FROM apta", null);
            while (cur.moveToNext()) {
                vo.setId(Integer.valueOf(cur.getString(0)));
                vo.setTime(Integer.valueOf(cur.getString(1)));
                vo.setPretime(Integer.valueOf(cur.getString(2)));
                vo.setAccTime(Integer.valueOf(cur.getString(3)));
            }
            db.close();
            if ((vo.getAccTime() + time - vo.getPretime()) > 1) {
                db = dbHelper.getWritableDatabase();
                int count = (time - vo.getPretime() + vo.getAccTime()) / 1;
                db.execSQL("UPDATE apta SET time=time+'" + Integer.valueOf(time - vo.getPretime()) + "', preTime='" + time
                        + "',accTime='" + Integer.valueOf((time - vo.getPretime() + vo.getAccTime()) % 1) + "' WHERE id='" + vo.getId() + "'");
                db.close();
                db = dbHelper.getReadableDatabase();
                cur = db.rawQuery("SELECT * FROM users", null);
                if (!cur.moveToNext()) {
                    db.close();
                    db = dbHelper.getWritableDatabase();
                    db.execSQL("INSERT INTO  users(userName,addictionRate,item,count,countFriends) VALUES('" + "rbsghks2" + "',1,0,0,9)");
                    db.close();
                } else {
                    db = dbHelper.getReadableDatabase();
                    cur = db.rawQuery("SELECT * FROM users", null);
                    while (cur.moveToNext()) {
                        uvo.setId(cur.getString(0));
                        uvo.setAddictionRate(cur.getString(1));
                        uvo.setItem(cur.getInt(2));
                        uvo.setCount(cur.getInt(3));
                        uvo.setCountFriend(cur.getInt(4));
                    }
                    db.close();
                    if(uvo.getCountFriend()-count<=1){
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE users SET count=count+'" + count + "',countFriends=1 WHERE userName='" + "rbsghks2" + "'");
                        db.close();

                    }
                    else {
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE users SET count=count+'" + count + "',countFriends = countFriends-'"+count+"'  WHERE userName='" + "rbsghks2" + "'");
                        db.close();
                    }
                }
            } else {
                db = dbHelper.getReadableDatabase();
                cur = db.rawQuery("SELECT * FROM users WHERE userName ='rbsghks2'", null);
                while (cur.moveToNext()) {
                    Log.d("앙 귀모리", String.valueOf(cur.getString(0)));
                    Log.d("앙 귀모리", String.valueOf(cur.getString(1)));
                    Log.d("앙 귀모리", String.valueOf(cur.getString(2)));
                    Log.d("앙 귀모리", String.valueOf(cur.getString(3)));
                    Log.d("앙 귀모리", String.valueOf(cur.getString(4)));
                }
            }


        }
    }
}



