package com.unity.mynativeapp;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.unity.model.DBVO;
import com.unity.model.TimeVO;
import com.unity.model.UsersVO;
import com.unity.util.DatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DashBoardActivity extends Activity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    TextView archive, money, battery, timed, friend;
    DBVO dbVO;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_layout);
        Timer timer = new Timer();
        timed = findViewById(R.id.dashTime);
        timed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ArchivementActivity.class);
                intent.putExtra("title", "timed");
                DBVO vo=setDB();
                intent.putExtra("timed",vo.getCount());
                startActivity(intent);
            }
        });
        friend = findViewById(R.id.dashFriend);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ArchivementActivity.class);
                intent.putExtra("title", "friend");
                DBVO vo = setDB();
                intent.putExtra("friend",vo.getUsersVO().getCount());
                startActivity(intent);
            }
        });
        battery = findViewById(R.id.dashBattery);
        battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ArchivementActivity.class);
                intent.putExtra("title", "battery");
                DBVO vo= setDB();
                Double battery=Double.valueOf(vo.getCount())*0.3;
                intent.putExtra("battery",battery);
                startActivity(intent);
            }
        });
        money = findViewById(R.id.dashMoney);
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ArchivementActivity.class);
                intent.putExtra("title", "money");
                DBVO vo = setDB();
                intent.putExtra("money",vo.getCount()*8590/60);
                startActivity(intent);
            }
        });
        archive = findViewById(R.id.dashArchive);
        archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ArchivementActivity.class);
                intent.putExtra("title", "archive");
                intent.putExtra("archive", dbVO.getArchive());
                startActivity(intent);
            }
        });

        dbHelper = new DatabaseHelper(DashBoardActivity.this);
        TimerTask TT = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                DBVO vo = setDB();
                setLayout(vo);
            }

        };
        timer.schedule(TT, 0, 60000);
    }

    public void setLayout(DBVO vo) {
        archive.setText("Archive:" + String.valueOf(vo.getArchive()));
        int minMoney = vo.getCount() * 8590 / 60;
        money.setText("Money:" + String.valueOf(minMoney) + "원");
        timed.setText("Time:" + vo.getCount() + "분");
        battery.setText("Battery:" + vo.getCount() * 0.3 + "%");
        friend.setText("Friend:-" + vo.getUsersVO().getCount() + "명");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private DBVO setDB() {
        dbVO = new DBVO();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        int time = 0;
        UsageStatsManager manager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> list = manager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, cal.getTimeInMillis(), System.currentTimeMillis());

        for (UsageStats stats : list) {
            if (String.valueOf(stats.getPackageName()).equals("com.ss.android.ugc.trill")) {

                time += stats.getTotalTimeInForeground() / 30000;
            } else if (String.valueOf(stats.getPackageName()).equals("com.google.android.youtube")) {

                time += stats.getTotalTimeInForeground() / 30000;
            } else if (String.valueOf(stats.getPackageName()).equals("com.facebook.katana")) {

                time += stats.getTotalTimeInForeground() / 30000;
            } else if (String.valueOf(stats.getPackageName()).equals("com.instagram.android")) {

                time += stats.getTotalTimeInForeground() / 30000;
            }
        }
        dbHelper = new DatabaseHelper(DashBoardActivity.this);
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
            db=dbHelper.getReadableDatabase();
            cur = db.rawQuery("SELECT * FROM apta", null);
            while (cur.moveToNext()) {
                vo.setId(Integer.valueOf(cur.getString(0)));
                vo.setTime(Integer.valueOf(cur.getString(1)));
                vo.setPretime(Integer.valueOf(cur.getString(2)));
                vo.setAccTime(Integer.valueOf(cur.getString(3)));
            }
            db.close();
            if ((vo.getAccTime() + time - vo.getPretime()) >2){
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
                    db.execSQL("INSERT INTO users(userName,addictionRate,item,count,countFriends) VALUES('" + "rbsghks2" + "',1,0,1,9)");
                    db.close();
                }
                else {
                    db.close();
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
                        Log.d("hello mo","ther fucker");
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE users SET count=count+'" + count + "',countFriends=1 WHERE userName='" + "rbsghks2" + "'");
                        db.close();

                    } else {
                        Log.d("hello mo","ther fucker333");
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE users SET count=count+'" + count + "',countFriends=countFriends-'" + count + "'  WHERE userName='" + "rbsghks2" + "'");
                        db.close();
                    }
                }
            }
        }
        dbVO.setCount(time);
        db = dbHelper.getReadableDatabase();
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd-HH:mm");

        cur = db.rawQuery("SELECT count(id) FROM calendar WHERE end_date<'" + format.format(today) + "'", null);
        while (cur.moveToNext()) {
            dbVO.setCalendar(cur.getInt(0));
        }
        cur = db.rawQuery("SELECT * FROM users", null);
        if(!cur.moveToNext()){
            db.close();
            db=dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO users(userName,addictionRate,item,count,countFriends) VALUES('" + "rbsghks2" + "',1,0,1,9)");
        }
        cur.moveToPosition(0);
        while (cur.moveToNext()) {
            Log.d("네임:",cur.getColumnName(4));
            Log.d("네임233:",cur.getColumnName(3));
            uvo.setId(cur.getString(0));
            uvo.setAddictionRate(cur.getString(1));
            uvo.setItem(cur.getInt(2));
            uvo.setCount(cur.getInt(3));
            uvo.setCountFriend(cur.getInt(4));

        }

        cur=db.rawQuery("SELECT count(id) FROM calendar WHERE end_date < strftime('%Y/%m/%d-%H/%M')  ",null);
        while(cur.moveToNext()){
            dbVO.setArchive(cur.getInt(0));
        }
        dbVO.setUsersVO(uvo);

        return dbVO;
    }
}
