package com.unity.fragment;

import com.unity.mynativeapp.R;

public class FavoritesFragment extends NoteListFragment {
    public static FavoritesFragment newInstance(){
        return new FavoritesFragment();
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_favorites;
    }

    @Override
    protected int getNumColumns() {
        return 1;
    }

    @Override
    protected int getNumItems() {
        return 1;
    }
}
