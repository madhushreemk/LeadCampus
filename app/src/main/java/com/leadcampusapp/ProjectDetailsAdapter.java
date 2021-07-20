package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ProjectDetailsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ProjectDetailsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ProjectStatusFragment tab1 = new ProjectStatusFragment();
                return tab1;
            case 1:
                AddProjectFragment tab2 = new AddProjectFragment();

                //CompletionProjectFragment tab2 = new CompletionProjectFragment();
                return tab2;
            case 2:
                //CompletionProjectFragment tab3 = new CompletionProjectFragment();
                CompletionProjectFragmentWPercent tab3 = new CompletionProjectFragmentWPercent();
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