package com.fyp.fragments.Setting.Page

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
import com.fyp.adapters.List1Adapter
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.Branch
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment5.*


class InAppPurchase : Fragment(), View.OnClickListener, iOnBackPressed {
    var list= arrayOf("Purchase Subscription","Verify Subscription Purhcase","Restore Purchase")
    var list2= arrayOf(R.drawable.purchase_order,R.drawable.video_editing,R.drawable.request_money)
    var listBranch=ArrayList<Branch>()
    var myView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment5, container, false)
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
        listBranch.clear()
        for( x in list.indices){
            var branch= Branch()
            branch.branchId=list[x]
            branch.branchLat=list2[x]
            listBranch.add(branch)
        }

        rv1.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = List1Adapter(activity,listBranch){ view, position ->
                when (position) {
                    0 -> {
                        findNavController().navigate(R.id.action_setting_to_subscription)
                    }
                    1 -> {
                      ToastUtils.showToastWith(activity,"Verify Subscription Purchase")
                    }
                    2 -> {
                        ToastUtils.showToastWith(activity,"Restore Purchase")
                    }
                }
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