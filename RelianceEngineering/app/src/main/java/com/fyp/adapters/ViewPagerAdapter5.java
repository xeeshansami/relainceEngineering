package com.fyp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fyp.fragments.Submit.Submit1;
import com.fyp.fragments.Submit.Submit2;
import com.fyp.fragments.Submit.Submit3;
import com.fyp.fragments.Submit.Submit4;

public class ViewPagerAdapter5 extends FragmentPagerAdapter {

    public ViewPagerAdapter5(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new Submit1();
        } else if (position == 1) {
            fragment = new Submit2();
        }else if (position == 2) {
            fragment = new Submit3();
        }else if (position == 3) {
            fragment = new Submit4();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "I";
        } else if (position == 1) {
            title = "III";
        }else if (position == 2) {
            title = "VIII-1";
        }else if (position == 3) {
            title = "XII";
        }
        return title;
    }
}