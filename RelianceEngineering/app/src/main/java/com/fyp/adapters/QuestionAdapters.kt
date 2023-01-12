package com.fyp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fyp.R
import com.fyp.interfaces.iOnItemClickListner
import kotlinx.android.synthetic.main.quest_list_view.view.*

class QuestionAdapters(context: Context,private val mList:ArrayList<String>,val onItemClickListner: iOnItemClickListner) :
    RecyclerView.Adapter<QuestionAdapters.MyViewHolder>() {
    var context=context
    var itemClickListner:iOnItemClickListner?=null
    init {
        itemClickListner=onItemClickListner
    }
    open inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questTv = view.text1!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.quest_list_view, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.questTv.setText(mList.get(position))
        holder.questTv.setOnClickListener {
            itemClickListner?.onItemClick(holder.questTv, mList[position], position)
        }
    }

}
