package com.unity.mynativeapp;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

public class MainActivity extends AppCompatActivity {

    boolean isUnityLoaded = false;
    private Button onBtn, offBtn;
    public static final String EEG_DB = "EEG_DB";
    public static final String EEG_TABLE = "EEG";
    public static SQLiteDatabase eegDB = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean granted = false;
        boolean granted2=false;
        AppOpsManager appOps = (AppOpsManager) getBaseContext().getSystemService(Context.APP_OPS_SERVICE);
        PowerManager pm=(PowerManager) getBaseContext().getSystemService(Context.POWER_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getBaseContext().getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (getBaseContext().checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }
        if (granted == false) {
            // 권한이 없을 경우 권한 요구 페이지 이동
            Intent intent = new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }

        if(pm.isIgnoringBatteryOptimizations(getPackageName())){

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
    }

    void handleIntent(Intent intent) {
        if (intent == null || intent.getExtras() == null) return;

        if (intent.getExtras().containsKey("setColor")) {
            View v = findViewById(R.id.button2);
            switch (intent.getExtras().getString("setColor")) {
                case "yellow":
                    v.setBackgroundColor(Color.YELLOW);
                    break;
                case "red":
                    v.setBackgroundColor(Color.RED);
                    break;
                case "blue":
                    v.setBackgroundColor(Color.BLUE);
                    break;
                default:
                    break;
            }
        }
    }

    public void btnLoadUnity(View v) {
        isUnityLoaded = true;
        Intent intent = new Intent(this, MainUnityActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) isUnityLoaded = false;
    }

    public void unloadUnity(Boolean doShowToast) {
        if (isUnityLoaded) {
            Intent intent = new Intent(this, MainUnityActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("doQuit", true);
            startActivity(intent);
            isUnityLoaded = false;
        } else if (doShowToast) showToast("Show Unity First");
    }

    public void btnUnloadUnity(View v) {
        unloadUnity(true);
    }

    public void showToast(String message) {
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

    public void goCalendar(View v) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void showCalendar(View v) {
        Intent intent = new Intent(this, MaterialSheetFabActivity.class);
        startActivity(intent);
    }

    public void goTest(View v) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
