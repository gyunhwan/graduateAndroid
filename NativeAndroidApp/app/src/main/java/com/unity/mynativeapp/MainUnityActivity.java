package com.unity.mynativeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.company.product.OverrideUnityActivity;


public class MainUnityActivity extends OverrideUnityActivity {
    // Setup activity layout
    private Button onBtn,offBtn;
    private static int count=1;
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
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
    }

    void handleIntent(Intent intent) {
        if(intent == null || intent.getExtras() == null) return;

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

    @Override public void onUnityPlayerUnloaded() {
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
            myButton.setText("Send Msg");
            myButton.setX(320);
            myButton.setY(500);
            myButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.UnitySendMessage("Ch_Main","hideObject","Ch_0"+count);
                    count++;
                }
            });
            layout.addView(myButton, 300, 200);
        }
        {
            Button myButton = new Button(this);
            myButton.setText(" on Obj");
            myButton.setX(600);
            myButton.setY(500);
            myButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.UnitySendMessage("Ch_Main","onObject","Ch_0"+(count-1));
                    count--;
                }
            });
            layout.addView(myButton, 300, 200);
        }
        }



}
