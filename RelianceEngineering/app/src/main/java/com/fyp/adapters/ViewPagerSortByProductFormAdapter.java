package com.fyp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fyp.fragments.SortBy.Composition;
import com.fyp.fragments.SortBy.ProductForm;
import com.fyp.fragments.SortBy.Specification;
import com.fyp.models.TableData;

import java.util.ArrayList;

public class ViewPagerSortByProductFormAdapter extends FragmentPagerAdapter {
    ArrayList<TableData> list;
    int tab=0;
    public ViewPagerSortByProductFormAdapter(FragmentManager fm, ArrayList<TableData> list, int tab) {
        super(fm);
        this.list=list;
        this.tab=tab;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new Specification(this.list,tab);
        } else if (position == 1) {
            fragment = new Composition(this.list,tab);
        } else if (position == 2) {
            fragment = new ProductForm(this.list,tab);
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
            title = "Specification";
        } else if (position == 1) {
            title = "Composition";
        } else if (position == 2) {
            title = "ProductForm";
        }
        return title;
    }
}