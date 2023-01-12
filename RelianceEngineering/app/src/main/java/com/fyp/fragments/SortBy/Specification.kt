package com.fyp.fragments.SortBy

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp.R
import com.fyp.activities.ActivityDetails
import com.fyp.activities.ActivitySortBy
import com.fyp.adapters.SpecificationAdapter
import com.fyp.db.MyDB
import com.fyp.models.TableData
import com.fyp.utils.SessionManager
import kotlinx.android.synthetic.main.specification.*


class Specification(list: java.util.ArrayList<TableData>, tab: Int) : Fragment(), View.OnClickListener {
    var db: MyDB? = null
    var listBranch = ArrayList<TableData>()
    var myView: View? = null
    var tab=0;
    init {
        this.tab=tab
        this.listBranch=list
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.specification, container, false)
        return myView
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    var sessionManager: SessionManager? = null

    override fun onResume() {
        super.onResume()
        init()
    }

    fun init() {
        rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SpecificationAdapter(
                activity, tab, listBranch
            ) { view, position ->
                var intent = Intent((activity as ActivitySortBy), ActivityDetails::class.java)
                intent.putExtra("lineNo", listBranch[position].lineNo)
                intent.putExtra("tab", tab)
                (activity as ActivitySortBy).startActivity(intent)
            }
            adapter!!.notifyDataSetChanged()
        }

    }

    override fun onClick(v: View?) {
        var bundle = Bundle()
        when (v!!.id) {
        }
    }


}