package com.fyp.fragments.Details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDetails
import com.fyp.activities.ActivitySortBy
import com.fyp.activities.ActivitySubmit
import com.fyp.adapters.ViewPagerAdapter4
import com.fyp.db.MyDB
import com.fyp.interfaces.iOnBackPressed
import com.fyp.utils.ToastUtils
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.app_layout.*
import kotlinx.android.synthetic.main.app_layout.heading
import kotlinx.android.synthetic.main.fragment_details.*


class FragmentDetails() : Fragment(), View.OnClickListener, iOnBackPressed {
    private var list = ArrayList<String>()
    var db: MyDB? = null
    var value = ""
    var tabValue = 0
    var material = ""
    var typeGrade = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
    }

    fun init() {
        (activity as ActivityDetails).menu.setOnClickListener(this)
        (activity as ActivityDetails).heading1.setOnClickListener(this)
        submitBtn.setOnClickListener(this)
        (activity as ActivityDetails).backBtn.setOnClickListener(this)
        (activity as ActivityDetails).menu.visibility = View.GONE
        (activity as ActivityDetails).heading1.visibility = View.GONE
        (activity as ActivityDetails).backBtn.visibility = View.VISIBLE
        (activity as ActivityDetails).heading.text = "Material Selector"

        requireActivity().runOnUiThread(Runnable {
            var pager = ViewPagerAdapter4((activity as ActivityDetails).supportFragmentManager);
            viewPager.adapter = pager
            tabLayout.setupWithViewPager(viewPager);
            pager.notifyDataSetChanged()
        })
        setAllCaps(tabLayout, false);
        val tab = tabLayout.getTabAt(0)
        tab!!.view.setBackground(getResources().getDrawable(R.drawable.tab));
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.isSelected) {
                    tab.view.setBackground(getResources().getDrawable(R.drawable.tab));
                } else {
                    tab.view.setBackground(getResources().getDrawable(R.drawable.tab_bg));
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.view.setBackground(getResources().getDrawable(R.drawable.tab_bg));
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        db = MyDB(this.context as ActivityDetails)
        db!!.createDatabase()
        db!!.open()
        if (requireActivity().intent.hasExtra("lineNo")) {
            value = requireActivity().intent.getStringExtra("lineNo").toString()
            tabValue = requireActivity().intent.getIntExtra("tab", 0)
        } else {
            ToastUtils.showToastWith(activity, "Something went wrong with database")
            return
        }
        var c = db!!.getAllValues(value)
        while (c.moveToNext()) {
            list.add(c.getString(c.getColumnIndex("I")))
            list.add(c.getString(c.getColumnIndex("III")))
            list.add(c.getString(c.getColumnIndex("VIII")))
            list.add(c.getString(c.getColumnIndex("XII")))
            typeGrade=c!!.getString(c!!.getColumnIndex("TypeGrade"))
            if (tabValue == 0) {
                material=c!!.getString(c!!.getColumnIndex("SpecNo"))
            } else if (tabValue == 1) {
                material=c!!.getString(c!!.getColumnIndex("UNS"))
            } else if (tabValue == 2) {
                material=c!!.getString(c!!.getColumnIndex("ProductForm"))
            } else if (tabValue == 3) {
                material=c!!.getString(c!!.getColumnIndex("PNo"))
            } else if (tabValue == 4) {
                material=c!!.getString(c!!.getColumnIndex("LineNo"))
            }
        }

    }

    private fun setAllCaps(view: View?, caps: Boolean) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) setAllCaps(view.getChildAt(i), caps)
        } else if (view is TextView) view.isAllCaps = caps
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.backBtn -> {
                requireActivity().let {
                    it.finish()
                }
            }

            R.id.submitBtn -> {
                var intent = Intent((activity as ActivityDetails), ActivitySubmit::class.java)
                intent.putExtra("TemperatureList", list)
                intent.putExtra("LineNo", value)
                intent.putExtra("material", material)
                intent.putExtra("typeGrade", typeGrade)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment1)
        if (navController.currentDestination?.id != R.id.fragment1) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            requireActivity().finish()
            return true
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
            return true
        }
    }


}