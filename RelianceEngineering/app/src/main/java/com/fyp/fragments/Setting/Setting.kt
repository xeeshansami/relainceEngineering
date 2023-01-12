package com.fyp.fragments.Setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivitySetting
import com.fyp.adapters.ViewPagerAdapter3
import com.fyp.interfaces.iOnBackPressed
import kotlinx.android.synthetic.main.app_layout.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlin.collections.ArrayList


class Setting() : Fragment(), View.OnClickListener, iOnBackPressed {
    private var list = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
    }
    fun init() {
        (activity as ActivitySetting).menu.setOnClickListener(this)
        (activity as ActivitySetting).backBtn.setOnClickListener(this)
        (activity as ActivitySetting).heading1.setOnClickListener(this)
        (activity as ActivitySetting).menu.visibility = View.GONE
        (activity as ActivitySetting).backBtn.visibility = View.GONE
        (activity as ActivitySetting).heading1.visibility = View.VISIBLE
        (activity as ActivitySetting).heading.text = "Main Menu"

        requireActivity().runOnUiThread(Runnable {
            var pager= ViewPagerAdapter3((activity as ActivitySetting).supportFragmentManager);
            viewPager.adapter =pager
            tabLayout.setupWithViewPager(viewPager);
            pager.notifyDataSetChanged()
        })
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.heading1 -> {
                findNavController().navigate(R.id.action_setting_to_home)
            }
            R.id.backBtn -> {
                findNavController().navigate(R.id.action_setting_to_home)
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