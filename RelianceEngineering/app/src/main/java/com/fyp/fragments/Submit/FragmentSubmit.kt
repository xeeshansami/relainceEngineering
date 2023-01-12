package com.fyp.fragments.Submit

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.*
import com.fyp.adapters.ViewPagerAdapter5
import com.fyp.db.MyDB
import com.fyp.models.Branch
import com.fyp.utils.SessionManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.app_layout.*
import kotlinx.android.synthetic.main.app_layout.heading
import kotlinx.android.synthetic.main.fragment_submit.*
import kotlinx.android.synthetic.main.submit.*


class FragmentSubmit() : Fragment(), View.OnClickListener {

    //    private var firebaseAuth: FirebaseAuth? = null
    private var sessionManager: SessionManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_submit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    fun init() {
        (activity as ActivitySubmit).menu.setOnClickListener(this)
        (activity as ActivitySubmit).heading1.setOnClickListener(this)
        (activity as ActivitySubmit).backBtn.setOnClickListener(this)
        (activity as ActivitySubmit).menu.visibility = View.GONE
        (activity as ActivitySubmit).heading1.visibility = View.GONE
        (activity as ActivitySubmit).backBtn.visibility = View.VISIBLE
        (activity as ActivitySubmit).heading.text = "Code & Temperature"
        viewPager.adapter = ViewPagerAdapter5((activity as ActivitySubmit).supportFragmentManager);
        tabLayout.setupWithViewPager(viewPager);
        setAllCaps(tabLayout, false);
        val tab = tabLayout.getTabAt(0)
        tab!!.view.setBackground(getResources().getDrawable(R.drawable.tab));
        tabLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
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

    }

    private fun setAllCaps(view: View?, caps: Boolean) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) setAllCaps(view.getChildAt(i), caps)
        } else if (view is TextView) view.isAllCaps = caps
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.menu -> {
                var i = Intent((activity as ActivitySubmit), ActivitySetting::class.java)
                // set the new task and clear flags
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(i)

            }
            R.id.backBtn -> {
                requireActivity().let {
                    it.finish()
                }

            }
        }
    }
}