package com.unity.mynativeapp;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.company.product.OverrideUnityActivity;
import com.unity.model.TimeVO;
import com.unity.model.UsersVO;
import com.unity.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainUnityActivity extends OverrideUnityActivity {
    // Setup activity layout
    private Button onBtn, offBtn;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addControlsToUnityFrame();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );
        Intent intent = new Intent(getApplication(), ScreenService.class);
        startService(intent);
        handleIntent(intent);
        dbHelper = new DatabaseHelper(MainUnityActivity.this);
        final Timer timer = new Timer();
        TimerTask TT = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                setState();
                UsersVO vo = new UsersVO();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, -1);
                db = dbHelper.getReadableDatabase();
                Cursor cur = db.rawQuery("SELECT * FROM users  ", null);
                while (cur.moveToNext()) {
                    vo.setItem(cur.getInt(2));
                    vo.setCount(cur.getInt(3));
                    vo.setCountFriend(cur.getInt(4));
                }
                mUnityPlayer.UnitySendMessage("Ch_Main", "hideObject", String.valueOf(vo.getCountFriend()));
            }
        };
        timer.schedule(TT, 0, 10000);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
    }

    void handleIntent(Intent intent) {
        if (intent == null || intent.getExtras() == null) return;

        if (intent.getExtras().containsKey("doQuit"))
            if (mUnityPlayer != null) {
                finish();
            }
    }

    @Override
    protected void showMainActivity(String setToColor) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("setColor", setToColor);
        startActivity(intent);
    }

    @Override
    public void onUnityPlayerUnloaded() {
        showMainActivity("");
    }

    public void addControlsToUnityFrame() {
        FrameLayout layout = mUnityPlayer;
        {
            dbHelper = new DatabaseHelper(MainUnityActivity.this);
            db = dbHelper.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT item,countFriends FROM users", null);
            int item = 0;
            int count = 9;
            if (!cur.moveToNext()) {
                while (cur.moveToNext()) {
                    item = cur.getInt(0);
                    count = cur.getInt(1);
                }
                db.close();
            } else {
                db.close();
            }
            final Button myButton = new Button(this);
            myButton.setText("use item");
            myButton.setX(10);
            myButton.setY(500);

            final int finalCount = count;

            myButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                public void onClick(View v) {
                    mUnityPlayer.UnitySendMessage("Ch_Main", "onObject", String.valueOf(finalCount));
                    setState();
                    setState2();
                }
            });
            layout.addView(myButton, 300, 200);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setState2() {
        UsageStatsManager manager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        DatabaseHelper dbHelper=new DatabaseHelper(MainUnityActivity.this);
        SQLiteDatabase db;
        db=dbHelper.getReadableDatabase();
        Cursor cur=db.rawQuery("SELECT * FROM users",null);
        db.close();
        if(!cur.moveToNext()){
            db=dbHelper.getWritableDatabase();

            db.execSQL("INSERT INTO users(userName,addctionRate,item,count,countFriends) VALUES ('rbsghks2',1,0,9)");
            db.close();
        }else {
            db.execSQL("UPDATE users SET countFriends=countFriends-1 WHERE userName='rbsghks'");
            db.close();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setState() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        int time = 0;
        UsageStatsManager manager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> list = manager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, cal.getTimeInMillis(), System.currentTimeMillis());
        List<UsageStats> list2 = new ArrayList<UsageStats>();
        db=dbHelper.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS users (userName CHAR(40) PRIMARY KEY, addictionRate CHAR(40),item INTEGER,count INTEGER,countFriends INTEGER)");
        for (UsageStats stats : list) {
            if (String.valueOf(stats.getPackageName()).equals("com.ss.android.ugc.trill")) {
//                list2.add(stats);
                time += stats.getTotalTimeInForeground() / 30000;
            } else if (String.valueOf(stats.getPackageName()).equals("com.google.android.youtube")) {
//                list2.add(stats);
                time += stats.getTotalTimeInForeground() / 30000;
            } else if (String.valueOf(stats.getPackageName()).equals("com.facebook.katana")) {
//                list2.add(stats);
                time += stats.getTotalTimeInForeground() / 30000;
            } else if (String.valueOf(stats.getPackageName()).equals("com.instagram.android")) {
//                list.add(stats);
                time += stats.getTotalTimeInForeground() / 30000;
            }
        }
        Log.d("빡세긴하네:", String.valueOf(time));
        db = dbHelper.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS apta (id INTEGER PRIMARY KEY AUTOINCREMENT, time INTEGER,preTime INTEGER,accTime)");
        db.close();
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
            if ((vo.getAccTime() + time - vo.getPretime()) > 2) {
                db = dbHelper.getWritableDatabase();
                int count = (time - vo.getPretime() + vo.getAccTime()) / 2;
                db.execSQL("UPDATE apta SET time=time+'" + Integer.valueOf(time - vo.getPretime()) + "', preTime='" + time
                        + "',accTime='" + Integer.valueOf((time - vo.getPretime() + vo.getAccTime())%2) + "' WHERE id='" + vo.getId() + "'");
                db.close();
                db = dbHelper.getReadableDatabase();
                cur = db.rawQuery("SELECT * FROM users", null);
                if (!cur.moveToNext()) {
                    db.close();
                    db = dbHelper.getWritableDatabase();
                    db.execSQL("INSERT INTO  users(userName,addictionRate,item,count,countFriends) VALUES('" + "rbsghks2" + "',1,0,1,9)");
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
                    if (uvo.getCountFriend() - count <= 1) {
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE users SET count=count+'" + count + "',countFriends=1 WHERE userName='" + "rbsghks2" + "'");
                        db.close();

                    } else {
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE users SET count=count+'" + count + "',countFriends = countFriends-'" + 1 + "'  WHERE userName='" + "rbsghks2" + "'");
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

