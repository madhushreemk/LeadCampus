package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TShirtAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TShirtAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TShirtUnpaidFragment tab1 = new TShirtUnpaidFragment();
                return tab1;
        /*    case 1:
                ChartStud_CollgCountFragment tab2 = new ChartStud_CollgCountFragment();
                return tab2;*/
            case 1:
                TShirtPaidFragment tab3 = new TShirtPaidFragment();
                return tab3;
    /*        case 2:
                ChartFundAmtCountFragment tab4 = new ChartFundAmtCountFragment();
                return tab4;*/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}