package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class NewsFeedsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public NewsFeedsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

           /* case 0:
                LeadStoryFragment tab0 = new LeadStoryFragment();
                return tab0;*/
            case 0:
                LeadFBFragment tab1 = new LeadFBFragment();
                return tab1;
            case 1:
                LeadInFragment tab2= new LeadInFragment();
                return tab2;
            case 2:
                LeadTWFragment tab3 = new LeadTWFragment();
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