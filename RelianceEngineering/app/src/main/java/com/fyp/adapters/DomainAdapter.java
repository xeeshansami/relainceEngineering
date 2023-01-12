package com.fyp.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class DomainAdapter extends ArrayAdapter<String> {

    private final ArrayList<String> items;

    public DomainAdapter(Context context, ArrayList<String> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.items = items;
    }

    public ArrayList<String> getItems() {
        return items;
    }
}