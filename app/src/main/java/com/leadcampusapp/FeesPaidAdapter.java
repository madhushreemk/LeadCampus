package com.leadcampusapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class FeesPaidAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public FeesPaidAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FeesUnpaidFragment tab1 = new FeesUnpaidFragment();
                return tab1;
            case 1:
                ChartThemeProjCountFragment tab3 = new ChartThemeProjCountFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}