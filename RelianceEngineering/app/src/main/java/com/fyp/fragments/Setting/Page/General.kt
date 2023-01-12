package com.fyp.fragments.Setting.Page

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp.R
import com.fyp.adapters.List1Adapter
import com.fyp.models.Branch
import com.fyp.utils.SessionManager
import kotlinx.android.synthetic.main.fragment3.*


class General : Fragment(), View.OnClickListener {
    var list = arrayOf(
        "About Us :",
        "About this app",
        "How it works?",
        "Credits",
        "Disclaimer",
        "PrivacyPolicy"
    )
    var list2 = arrayOf(
        R.drawable.about,
        R.drawable.privacy_policy,
        R.drawable.video_editing,
        R.drawable.creadits,
        R.drawable.disclaimer,
        R.drawable.privacy_policy
    )
    var listBranch = ArrayList<Branch>()
    var myView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment3, container, false)
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
        listBranch.clear()
        for (x in list.indices) {
            var branch = Branch()
            branch.branchId = list[x]
            branch.branchLat = list2[x]
            listBranch.add(branch)
        }
        rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = List1Adapter(
                activity, listBranch
            ) { view, position ->
                when (position) {
                    0 -> {
                        findNavController().navigate(R.id.action_setting_to_about)
                    }
                    1 -> {
                        findNavController().navigate(R.id.action_setting_to_aboutApp)
                    }
                    2 -> {
                        findNavController().navigate(R.id.action_setting_to_credits)
                    }
                    3 -> {
                        findNavController().navigate(R.id.action_setting_to_howItsWorks)
                    }
                    4 -> {
                        findNavController().navigate(R.id.action_setting_to_disclaimer)
                    }
                    5 -> {
                        findNavController().navigate(R.id.action_setting_to_privacyPolicy)
                    }
                }
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