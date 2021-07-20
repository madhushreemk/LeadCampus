package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PMProjectDetailsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int pageCount;


    public PMProjectDetailsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
      //  this.pageCount=pageCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PMUnapprovedFragment tab1 = new PMUnapprovedFragment();
                return tab1;
            case 1:
                PMApprovedFragment tab2 = new PMApprovedFragment();
               return tab2;
            case 2:
                PMComplitionFragment tab3 = new PMComplitionFragment();
                return tab3;
            case 3:
                PMFundAmountFragment tab4 = new PMFundAmountFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}