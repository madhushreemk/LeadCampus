package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FundAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public FundAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Funding_Fragment tab = new Funding_Fragment();
                return tab;
            case 1:
                Fund_TicketStatus_Fragment tab1 = new Fund_TicketStatus_Fragment();
                return tab1;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}