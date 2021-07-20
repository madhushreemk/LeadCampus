package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class EventsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public EventsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                CurrentEventsFragment tab1 = new CurrentEventsFragment();
                return tab1;
            case 1:
                PastEventsFragment tab2 = new PastEventsFragment();
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