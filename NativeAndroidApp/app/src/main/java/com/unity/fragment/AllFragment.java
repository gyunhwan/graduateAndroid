package com.unity.fragment;

import com.unity.mynativeapp.R;

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