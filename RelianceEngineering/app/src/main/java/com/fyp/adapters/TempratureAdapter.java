package com.fyp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fyp.R;
import com.fyp.models.Branch;

import java.util.ArrayList;

public class TempratureAdapter extends RecyclerView.Adapter<TempratureAdapter.ViewHolder> {
    private int row_index;
    private ArrayList<Branch> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private String branchImage;

    // data is passed into the constructor
    public TempratureAdapter(Context context, ArrayList<Branch> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_views_1, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Branch data = mData.get(position);
        if (position == 0) {
            holder.titleLbl.setText("Section I:");
        } else if (position == 1) {
            holder.titleLbl.setText("Section III:");
        } else if (position == 2) {
            holder.titleLbl.setText("Section VIII-1:");
        } else if (position == 3) {
            holder.titleLbl.setText("Section XII:");
        }
        holder.titleValue.setText(data.getBranchName());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleLbl, titleValue;

        ViewHolder(View itemView) {
            super(itemView);
            titleLbl = itemView.findViewById(R.id.txt1);
            titleValue = itemView.findViewById(R.id.txt2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Branch getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}