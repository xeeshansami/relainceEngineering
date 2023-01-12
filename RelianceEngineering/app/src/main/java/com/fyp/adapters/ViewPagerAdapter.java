package com.fyp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fyp.fragments.Home.FragmentSpecs;
import com.fyp.fragments.Home.FragmentUNS;
import com.fyp.fragments.Home.FragmentProductForm;
import com.fyp.fragments.Home.FragmentPNo;
import com.fyp.fragments.Home.FragmentCode;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new FragmentSpecs();
        } else if (position == 1) {
            fragment = new FragmentUNS();
        } else if (position == 2) {
            fragment = new FragmentProductForm();
        } else if (position == 3) {
            fragment = new FragmentPNo();
        }else if (position == 4) {
            fragment = new FragmentCode();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Spec.";
        } else if (position == 1) {
            title = "UNS #";
        } else if (position == 2) {
            title = "ProdForm";
        } else if (position == 3) {
            title = "P #";
        }else if (position == 4) {
            title = "Code";
        }
        return title;
    }
}