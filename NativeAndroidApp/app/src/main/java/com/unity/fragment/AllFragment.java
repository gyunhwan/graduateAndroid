package com.unity.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.unity.model.CalendarVO;
import com.unity.mynativeapp.CalendarActivity;
import com.unity.mynativeapp.MaterialSheetFabActivity;
import com.unity.mynativeapp.R;
import com.unity.util.DatabaseHelper;

public class AllFragment extends NoteListFragment {

    public static AllFragment newInstance() {
        return new AllFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_all;
    }

    @Override
    protected int getNumColumns() {
        return 2;
    }

    @Override
    protected int getNumItems() {
        return 20;
    }
}