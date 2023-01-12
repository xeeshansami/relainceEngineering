package com.fyp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fyp.fragments.Setting.Page.General;
import com.fyp.fragments.Setting.Page.Social;
import com.fyp.fragments.Setting.Page.InAppPurchase;

public class ViewPagerAdapter3 extends FragmentPagerAdapter {

    public ViewPagerAdapter3(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new General();
        } else if (position == 1) {
            fragment = new Social();
        } else if (position == 2) {
            fragment = new InAppPurchase();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Generals";
        } else if (position == 1) {
            title = "Social";
        } else if (position == 2) {
            title = "In-App-Purchases";
        }
        return title;
    }
}