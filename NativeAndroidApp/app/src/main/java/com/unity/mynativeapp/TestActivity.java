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
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.unity.util.DatabaseHelper;

import java.util.Calendar;
import java.util.List;

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
        UsageStatsManager manager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> list = manager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, cal.getTimeInMillis(), System.currentTimeMillis());
        Log.d("기록:", String.valueOf(list.size()));
        for (UsageStats stats : list) {
            boolean tag = String.valueOf(stats.getPackageName()).equals("com.google.android.youtube");
            db = dbHelper.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM apta ", null);

            if(!cur.moveToNext()){
                Log.d("삽입삽입삽입","삽입");
                db.close();
                db=dbHelper.getWritableDatabase();
                db.execSQL("INSERT INTO apta(id,time,preTime) VALUES(null,0,0)");
                db.close();
            }
            else {
                Log.d("cur컬껄",String.valueOf(cur.moveToNext()));
                cur.moveToFirst();
                while (cur.moveToNext()) {
                    Log.d("씨발", cur.getString(0));
                    Log.d("씨발", cur.getString(1));
                    Log.d("씨발", cur.getString(2));
                }
                db.close();
            }
            if (tag && stats.getTotalTimeInForeground() != 0) {
                Log.d("시간시간시간시간",String.valueOf(stats.getTotalTimeInForeground()));
                db = dbHelper.getReadableDatabase();

                db.close();
                while(cur.moveToNext()){
                    Log.d("time",cur.getString(0)+cur.getString(1));
                }

                db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE apta SET time=time" + stats.getTotalTimeInForeground() + "WHERE id = 1");
                db.close();

            }
        }

    }
}
