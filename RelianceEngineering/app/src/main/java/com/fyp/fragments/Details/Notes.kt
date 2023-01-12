package com.fyp.fragments.Details

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp.R
import com.fyp.activities.ActivityDetails
import com.fyp.adapters.NotesAdapter
import com.fyp.db.MyDB
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.Branch
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment3.*
import kotlin.collections.ArrayList


class Notes : Fragment(), View.OnClickListener, iOnBackPressed {
    var list = arrayOf("Purchase Subscription", "Verify Subscription Purhcase", "Restore Purchase")
    var list2 =
        arrayOf(R.drawable.purchase_order, R.drawable.video_editing, R.drawable.request_money)
    var listBranch = ArrayList<Branch>()
    var myView: View? = null
    var db: MyDB? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.notes, container, false)
        return myView
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    var sessionManager: SessionManager? = null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onResume() {
        super.onResume()

    }

    fun init() {
        var value = ""
        db = MyDB(this.context as ActivityDetails)
        sessionManager = SessionManager(this.context as ActivityDetails)
        db!!.createDatabase()
        db!!.open()
        if (requireActivity().intent.hasExtra("lineNo")) {
            value = requireActivity().intent.getStringExtra("lineNo").toString()
        } else {
            ToastUtils.showToastWith(activity, "Something went wrong with database")
            return
        }
        var c = db!!.getAllValues(value)
        while (c.moveToNext()) {
            value = c.getString(c.getColumnIndex("Notes"))
        }
        val list: List<String> = value.replace(" ","").split(",")
        var valueLists=ArrayList<Branch>()
        valueLists.clear()
        for (x in 0 until list.size) {
            Constant.saveNotesList().keys.forEach { key ->
                if (key == list.get(x)) {
                    var branch=Branch()
                    branch.branchName=Constant.saveNotesList().getValue(key)
                    branch.branchId=key
                    valueLists.add(branch)
                   Log.i("FINDKEYS","${key} = ${list.get(x)}")
                }
            }
        }
        c.close();
        listBranch.clear()
        if(valueLists.size!=0) {
            rvHome.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = NotesAdapter(
                    activity, valueLists
                ) { view, position ->
                    when (position) {

                    }
                }
                adapter!!.notifyDataSetChanged()
            }
            var list=ArrayList<String>()
            for(x in valueLists){
                list.add(x.branchId)
            }
            sessionManager!!.setList(Constant.NOTES,list!!)
        }else{
            var branch=Branch()
            branch.branchName=""
            branch.branchId="Notes not available"
            valueLists.add(branch)
            rvHome.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = NotesAdapter(
                    activity, valueLists
                ) { view, position ->
                    when (position) {

                    }
                }
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onClick(v: View?) {
        var bundle = Bundle()
        when (v!!.id) {
        }
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        return if (navController.currentDestination?.id != R.id.fragment) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            requireActivity().finish()
            true
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
            true
        }
    }
}