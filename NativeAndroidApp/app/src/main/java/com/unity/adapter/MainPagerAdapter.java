package com.unity.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.unity.fragment.AllFragment;
import com.unity.fragment.FavoritesFragment;
import com.unity.fragment.SharedFragment;
import com.unity.mynativeapp.MaterialSheetFabActivity;
import com.unity.mynativeapp.R;

public class MainPagerAdapter extends FragmentPagerAdapter {
    public static final int NUM_ITEMS = 3;
    public static final int ALL_POS = 0;
    public static final int SHARED_POS = 1;
    private Context context;
    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context=context;
    }

    public static final int FAVORITES_POS = 2;


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case ALL_POS:
                return AllFragment.newInstance();
            case SHARED_POS:
                return SharedFragment.newInstance();
            case FAVORITES_POS:
                return FavoritesFragment.newInstance();
            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case ALL_POS:
                return context.getString(R.string.all);
            case SHARED_POS:
                return context.getString(R.string.shared);
            case FAVORITES_POS:
                return context.getString(R.string.favorites);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
