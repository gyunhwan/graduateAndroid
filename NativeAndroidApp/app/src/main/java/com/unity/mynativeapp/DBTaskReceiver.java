package com.unity.mynativeapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DBTaskReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("intentAaction",intent.getAction());
        if(intent.getAction().equals("android.permission.RECEIVE_BOOT_COMPLETED")){
            Log.d("피지컬","피지컬");
        }

    }
}
