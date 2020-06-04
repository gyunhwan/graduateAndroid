package com.unity.fragment;

import com.unity.mynativeapp.R;

public class SharedFragment extends NoteListFragment {
    public static SharedFragment newInstance(){
        return new SharedFragment();
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shared;
    }

    @Override
    protected int getNumColumns() {
        return 2;
    }

    @Override
    protected int getNumItems() {
        return 10;
    }
}
