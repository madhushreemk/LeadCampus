package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class RequestAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public RequestAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MasterRequestFragment tab1 = new MasterRequestFragment();
                return tab1;
            case 1:
                AmbassadorRequestFragment tab2 = new AmbassadorRequestFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}