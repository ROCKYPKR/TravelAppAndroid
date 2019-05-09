package com.eshna.travelapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.eshna.travelapp.fragment.AboutHotelFragment;
import com.eshna.travelapp.fragment.MapFragment;
import com.eshna.travelapp.fragment.HotelReviewFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public SimpleFragmentPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }


    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new AboutHotelFragment();
            case 1:
                return new HotelReviewFragment();
            case 2:
                return new MapFragment();
            default:
                return null;
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return numOfTabs;
    }

}