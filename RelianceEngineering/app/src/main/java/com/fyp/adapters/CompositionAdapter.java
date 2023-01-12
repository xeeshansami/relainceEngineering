package com.fyp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fyp.R;
import com.fyp.models.TableData;

import java.util.ArrayList;

public class CompositionAdapter extends RecyclerView.Adapter<CompositionAdapter.ViewHolder> {
    private int row_index;
    private ArrayList<TableData> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private int value;

    // data is passed into the constructor
    public CompositionAdapter(Context context, int value, ArrayList<TableData> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.value = value;
        this.context = context;
        this.mClickListener = mClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_views_4, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TableData data = mData.get(position);
        if (value == 0) {
            holder.head1.setText(position + 1 + ") " + "Specification" + " " + data.getSpecNo());
        } else if (value == 1) {
            holder.head1.setText(position + 1 + ") " + "UNS" + " " + data.getUNS());
        } else if (value == 2) {
            holder.head1.setText(position + 1 + ") " + "Product Form" + " " + data.getProductForm());
        } else if (value == 3) {
            holder.head1.setText(position + 1 + ") " + "P Number" + " " + data.getPNo());
        } else if (value == 4) {
            holder.head1.setText(position + 1 + ") " + "Code Section" + " " + data.getLineNo());
        }
        holder.txt1.setText(data.getNominalComposition());
        holder.txt2.setText(data.getSpecNo());
        holder.txt3.setText(data.getTypeGrade());
        holder.txt4.setText(data.getProductForm());
        holder.txt5.setText(data.getUNS());
        holder.txt6.setText(data.getGroupNo());
        holder.txt7.setText(data.getConstructionI());
        holder.txt8.setText(data.getBookPage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, position);
            }
        });
//        if (row_index == position) {
//            holder.img.setBackground(this.context.getResources().getDrawable(R.drawable.button_border));
//        } else {
//            holder.img.setBackground(this.context.getResources().getDrawable(android.R.color.transparent));
//        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, head1;

        ViewHolder(View itemView) {
            super(itemView);
            head1 = itemView.findViewById(R.id.head1);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            txt3 = itemView.findViewById(R.id.txt3);
            txt4 = itemView.findViewById(R.id.txt4);
            txt5 = itemView.findViewById(R.id.txt5);
            txt6 = itemView.findViewById(R.id.txt6);
            txt7 = itemView.findViewById(R.id.txt7);
            txt8 = itemView.findViewById(R.id.txt8);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    TableData getItem(int id) {
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