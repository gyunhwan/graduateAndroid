package com.unity.mynativeapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unity.util.DatabaseHelper;

public class ArchivementActivity extends Activity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    LinearLayout archiveLay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archive_layout);
        archiveLay = findViewById(R.id.archiveLayout);
        dbHelper = new DatabaseHelper(ArchivementActivity.this);
        db = dbHelper.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS archive(id INTEGER PRIMARY KEY AUTOINCREMENT,category char(10), content char(100),checkItem INTEGER,standards INTEGER)");
        db.close();
        db = dbHelper.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM archive", null);
        if (!cur.moveToNext()) {
            setFirst();
            Log.d("앙 기모리", "모귀앙띠");
        }
        switch (getIntent().getStringExtra("title")) {
            case "money":
                setMoney(Integer.valueOf(getIntent().getIntExtra("money", 0)));
                break;
            case "friend":
                setFriend(Integer.valueOf(getIntent().getIntExtra("friend", 0)));
                break;
            case "timed":
                setTimed(getIntent().getIntExtra("timed",0));
                break;
            case "battery":
                setBattery(Double.valueOf(getIntent().getDoubleExtra("battery", 0)));
                break;
            case "archive":
                setArchive(getIntent().getIntExtra("archive", 0));
                break;
        }

    }

    private void setFirst() {
        db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "SNS 배터리 100% 안 쓰기" + "','battrery',0,100)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "SNS 배터리 90% 안 쓰기" + "','battrery',0,90)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "SNS 배터리 80% 안 쓰기" + "','battrery',0,80)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "SNS 배터리 70% 안 쓰기" + "','battrery',0,70)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "SNS 배터리 60% 안 쓰기" + "','battrery',0,60)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "SNS 배터리 50% 안 쓰기" + "','battrery',0,50)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "목표 1개 세우기" + "','archive',0,1)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "목표 10개 세우기" + "','archive',0,10)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "목표 30개 세우기" + "','archive',0,30)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "목표 50개 세우기" + "','archive',0,50)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "목표 100개 세우기" + "','archive',0,100)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "시간 5분 줄이기" + "','timed',0,5)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "시간 10분 줄이기" + "','timed',0,10)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "시간 20분 줄이기" + "','timed',0,20)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "시간 30분 줄이기" + "','timed',0,30)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "시간 60분 줄이기" + "','timed',0,60)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "돈 10000원 이하로 사용하기 " + "','money',0,10000)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "돈 5000원 이하로 사용하기 " + "','money',0,5000)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "돈 3000원 이하로 사용하기 " + "','money',0,3000)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "돈 2000원 이하로 사용하기 " + "','money',0,2000)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "돈 1000원 이하로 사용하기 " + "','money',0,1000)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "친구 1명 이하로 죽이기 " + "','friend',0,1)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "친구 5명 이하로 죽이기 " + "','friend',0,10)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "친구 10명 이하로 죽이기 " + "','friend',0,5)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "친구 20명 이하로 죽이기 " + "','friend',0,20)");
        db.execSQL("INSERT INTO archive(id,content,category,checkItem,standards) VALUES(null,'" + "친구 30명 이하로 죽이기 " + "','friend',0,30)");
        db.close();
    }

    private void setMoney(final int time) {
        db = dbHelper.getReadableDatabase();
        int count = 0;
        final Cursor cur = db.rawQuery("SELECT * FROM archive WHERE category='money'", null);
        while (cur.moveToNext()) {
            final int c = count;
            LinearLayout layout = new LinearLayout(ArchivementActivity.this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.HORIZONTAL);
            TextView textview = new TextView(ArchivementActivity.this);
            textview.setText(cur.getString(2));
            final Button btn = new Button(ArchivementActivity.this);
            btn.setText("받기");
            if (cur.getInt(3) == 1) {
                btn.setClickable(false);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cur.moveToPosition(c);
                    Log.d("앙 기모링", cur.getString(3) + cur.getString(4));
                    if (cur.getInt(3) == 0 && cur.getInt(4) > time) {
                        Log.d("해윙", "햄윙");
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE archive SET checkItem=1 WHERE id=" + cur.getInt(0));
                        db.execSQL("UPDATE users SET item=item+1");
                        btn.setClickable(false);
                    }
                }
            });
            layout.addView(textview);
            layout.addView(btn);
            archiveLay.addView(layout);
            count++;
        }

    }

    private void setFriend(final Integer friend) {
        db = dbHelper.getReadableDatabase();
        int count = 0;
        final Cursor cur = db.rawQuery("SELECT * FROM archive WHERE category='friend'", null);
        while (cur.moveToNext()) {
            final int c = count;
            LinearLayout layout = new LinearLayout(ArchivementActivity.this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.HORIZONTAL);
            TextView textview = new TextView(ArchivementActivity.this);
            textview.setText(cur.getString(2));
            final Button btn = new Button(ArchivementActivity.this);
            btn.setText("받기");
            if (cur.getInt(3) == 1) {
                btn.setClickable(false);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cur.moveToPosition(c);
                    Log.d("앙 기모링", cur.getString(3) + cur.getString(4));
                    if (cur.getInt(3) == 0 && cur.getInt(4) < friend) {
                        Log.d("해윙", "햄윙");
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE archive SET checkItem=1 WHERE id=" + cur.getInt(0));
                        db.execSQL("UPDATE users SET item=item+1");
                        btn.setClickable(false);
                    }
                }
            });
            layout.addView(textview);
            layout.addView(btn);
            archiveLay.addView(layout);
            count++;
        }

    }

    private void setTimed(final int timed) {

        db = dbHelper.getReadableDatabase();
        int count = 0;
        final Cursor cur = db.rawQuery("SELECT * FROM archive WHERE category='timed'", null);
        while (cur.moveToNext()) {
            final int c = count;
            LinearLayout layout = new LinearLayout(ArchivementActivity.this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.HORIZONTAL);
            TextView textview = new TextView(ArchivementActivity.this);
            textview.setText(cur.getString(2));
            final Button btn = new Button(ArchivementActivity.this);
            btn.setText("받기");
            if (cur.getInt(3) == 1) {
                btn.setClickable(false);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cur.moveToPosition(c);
                    Log.d("앙 기모링", cur.getString(3) + cur.getString(4));
                    if (cur.getInt(3) == 0 && cur.getInt(4) < timed) {
                        Log.d("해윙", "햄윙");
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE archive SET checkItem=1 WHERE id=" + cur.getInt(0));
                        db.execSQL("UPDATE users SET item=item+1");
                        btn.setClickable(false);
                    }
                }
            });
            layout.addView(textview);
            layout.addView(btn);
            archiveLay.addView(layout);
            count++;
        }
    }

    private void setBattery(final Double battery) {

        db = dbHelper.getReadableDatabase();
        int count = 0;
        final Cursor cur = db.rawQuery("SELECT * FROM archive WHERE category='battrery'", null);
        while (cur.moveToNext()) {
            final int c = count;
            LinearLayout layout = new LinearLayout(ArchivementActivity.this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.HORIZONTAL);
            TextView textview = new TextView(ArchivementActivity.this);
            textview.setText(cur.getString(2));
            final Button btn = new Button(ArchivementActivity.this);
            btn.setText("받기");
            if (cur.getInt(3) == 1) {
                btn.setClickable(false);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cur.moveToPosition(c);
                    Log.d("앙 기모링", cur.getString(3) + cur.getString(4));
                    if (cur.getInt(3) == 0 && cur.getInt(4) > 100 - battery) {
                        Log.d("해윙", "햄윙");
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE archive SET checkItem=1 WHERE id=" + cur.getInt(0));
                        db.execSQL("UPDATE users SET item=item+1");
                        btn.setClickable(false);
                    }
                }
            });
            layout.addView(textview);
            layout.addView(btn);
            archiveLay.addView(layout);
            count++;
        }

    }

    private void setArchive(final int archive) {

        db = dbHelper.getReadableDatabase();
        int count = 0;
        final Cursor cur = db.rawQuery("SELECT * FROM archive WHERE category='archive'", null);
        while (cur.moveToNext()) {
            final int c = count;
            LinearLayout layout = new LinearLayout(ArchivementActivity.this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.HORIZONTAL);
            TextView textview = new TextView(ArchivementActivity.this);
            textview.setText(cur.getString(2));
            final Button btn = new Button(ArchivementActivity.this);
            btn.setText("받기");
            if (cur.getInt(3) == 1) {
                btn.setClickable(false);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cur.moveToPosition(c);
                    Log.d("앙 기모링", cur.getString(3) + cur.getString(4));
                    if (cur.getInt(3) == 0 && cur.getInt(4) >archive) {
                        Log.d("해윙", "햄윙");
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE archive SET checkItem=1 WHERE id=" + cur.getInt(0));
                        db.execSQL("UPDATE users SET item=item+1");
                        btn.setClickable(false);
                    }
                }
            });
            layout.addView(textview);
            layout.addView(btn);
            archiveLay.addView(layout);
            count++;
        }
    }

}
