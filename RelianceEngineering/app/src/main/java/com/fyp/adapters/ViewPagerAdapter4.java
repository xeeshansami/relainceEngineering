package com.fyp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fyp.fragments.Details.Details;
import com.fyp.fragments.Details.Notes;

public class ViewPagerAdapter4 extends FragmentPagerAdapter {

    public ViewPagerAdapter4(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new Details();
        } else if (position == 1) {
            fragment = new Notes();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Details";
        } else if (position == 1) {
            title = "Notes";
        }
        return title;
    }
}