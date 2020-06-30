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

import com.company.product.OverrideUnityActivity;
import com.unity.util.DatabaseHelper;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainUnityActivity extends OverrideUnityActivity {
    // Setup activity layout
    private Button onBtn, offBtn;
    private static int count = 1;
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
        dbHelper=new DatabaseHelper(MainUnityActivity.this);

        Timer timer = new Timer();
        TimerTask TT = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                if (count >= 1) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.YEAR, -1);
                    UsageStatsManager manager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
                    List<UsageStats> list = manager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, cal.getTimeInMillis(), System.currentTimeMillis());
                    Log.d("기록:", String.valueOf(list.size()));
                    for (UsageStats stats : list) {
                        if (stats.getPackageName() == "com.google.android.youtube"&&stats.getTotalTimeInForeground()!=0) {
                            db=dbHelper.getWritableDatabase();
                            db.execSQL("UPDATE appTime SET time=time"+stats.getTotalTimeInForeground()+"WHERE id = 1");
                            db.close();
                            db=dbHelper.getReadableDatabase();
                            Cursor cur=db.rawQuery("SELECT time FROM appTime WHERE id =1",null);

                        }
                    }
                    mUnityPlayer.UnitySendMessage("Ch_Main", "hideObject", "Ch_0" + count);
                    count++;
                }
            }
        };
        timer.schedule(TT, 0, 7000);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
    }

    void handleIntent(Intent intent) {
        if (intent == null || intent.getExtras() == null) return;

//        if(intent.getExtras().containsKey("doQuit"))
//            if(mUnityPlayer != null) {
//                finish();
//            }
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
            Button myButton = new Button(this);
            myButton.setText("Show Main");
            myButton.setX(10);
            myButton.setY(500);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showMainActivity("");
                }
            });
            layout.addView(myButton, 300, 200);
        }
        {
            Button myButton = new Button(this);
            myButton.setText("off Obj");
            myButton.setX(320);
            myButton.setY(500);
            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (count >= 1) {
                        mUnityPlayer.UnitySendMessage("Ch_Main", "hideObject", "Ch_0" + count);
                        count++;
                    }
                }
            });
            layout.addView(myButton, 300, 200);
        }
        {
            Button myButton = new Button(this);
            myButton.setText(" on Obj");
            myButton.setX(600);
            myButton.setY(500);
            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (count <= 9) {
                        mUnityPlayer.UnitySendMessage("Ch_Main", "onObject", "Ch_0" + (count - 1));
                        count--;
                    }
                }
            });
            layout.addView(myButton, 300, 200);
        }
    }


}