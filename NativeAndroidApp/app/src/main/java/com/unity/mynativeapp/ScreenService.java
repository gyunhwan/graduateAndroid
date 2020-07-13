package com.unity.mynativeapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.unity3d.player.UnityPlayerActivity;

public class ScreenService extends Service {
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override

        public void onReceive(Context context, Intent intent) {

            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {

                Intent i = new Intent(context, UnityPlayerActivity.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);

            }

        }

    };



    @Override

    public IBinder onBind(Intent intent) {

        return null;

    }



    @Override

    public void onCreate() {

        super.onCreate();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);

        registerReceiver(receiver, filter);

    }



    @Override

    public void onDestroy() {

        super.onDestroy();

        unregisterReceiver(receiver);

    }



}


