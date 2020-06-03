package com.unity.mynativeapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.unity.util.Fab;

public class MaterialSheetFabActivity extends Activity {
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fab fab= (Fab)findViewById(R.id.fab);
        View sheetView =findViewById(R.id.fab_sheet);
        View overlay= findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.colorPrimary);
        int fabColor= getResources().getColor(R.color.colorPrimaryDark);
        MaterialSheetFab materialSheetFab=new MaterialSheetFab(fab,sheetView,overlay,sheetColor,fabColor);
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                Log.d("onShowSheet:","hello");
                super.onShowSheet();
            }

            @Override
            public void onSheetShown() {
                Log.d("onSheetShown:","hello");
                super.onSheetShown();
            }

            @Override
            public void onHideSheet() {
                Log.d("onHideSheet:","hello");
                super.onHideSheet();
            }

            @Override
            public void onSheetHidden() {
                Log.d("onSheetHidden:","hello");
                super.onSheetHidden();
            }
        });
    }
}
