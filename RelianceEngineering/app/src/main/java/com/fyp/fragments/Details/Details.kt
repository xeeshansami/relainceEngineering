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
import com.fyp.R
import com.fyp.activities.ActivityDetails
import com.fyp.db.MyDB
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.Branch
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import kotlinx.android.synthetic.main.details.*


class Details : Fragment(), View.OnClickListener, iOnBackPressed {
    var db: MyDB? = null
    var list= arrayOf("Purchase Subscription","Verify Subscription Purhcase","Restore Purchase")
    var list2= arrayOf(R.drawable.purchase_order,R.drawable.video_editing,R.drawable.request_money)
    var listBranch=ArrayList<Branch>()
    var myView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.details, container, false)
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
        var value=""
        db = MyDB(this.context as ActivityDetails)
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
            txt1.text = c.getString(c.getColumnIndex("BookPage"))
            txt2.text= c.getString(c.getColumnIndex("SpecNo"))
            txt3.text= c.getString(c.getColumnIndex("NominalComposition"))
            txt4.text = c.getString(c.getColumnIndex("ProductForm"))
            txt5.text = c.getString(c.getColumnIndex("TypeGrade"))
            txt6.text = c.getString(c.getColumnIndex("UNS"))
            txt7.text = c.getString(c.getColumnIndex("ClassTemper"))
            txt8.text = c.getString(c.getColumnIndex("SizeThickness"))
            txt9.text = c.getString(c.getColumnIndex("PNo"))
            txt10.text = c.getString(c.getColumnIndex("GroupNo"))
            txt11.text = c.getString(c.getColumnIndex("MinTensileStrength"))
            txt12.text = c.getString(c.getColumnIndex("MinYieldStrength"))
            txt13.text = c.getString(c.getColumnIndex("InternalPressure"))
            txt14.text = c.getString(c.getColumnIndex("Notes"))
            txt15.text = c.getString(c.getColumnIndex("I"))
            txt16.text = c.getString(c.getColumnIndex("III"))
            txt17.text = c.getString(c.getColumnIndex("VIII"))
            txt18.text = c.getString(c.getColumnIndex("XII"))
        }
        c.close();

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