package com.unity.mynativeapp;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

public class TestActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAppUsageStats();
    }
    private void getAppUsageStats(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Calendar cal =Calendar.getInstance();
            cal.add(Calendar.YEAR,-1);
            UsageStatsManager manager=(UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            List<UsageStats> list=manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,System.currentTimeMillis(),System.currentTimeMillis());
            for(UsageStats stats: list){
                Log.d("싸발적이구만", String.valueOf(stats.getTotalTimeInForeground()));
            }
        }

    }
}
