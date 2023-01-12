package com.fyp.fragments.Setting.Page

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp.R
import com.fyp.adapters.List1Adapter
import com.fyp.models.Branch
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment4.*


class Social : Fragment(), View.OnClickListener {
    var list = arrayOf("Facebook", "Linkedin", "Rate this App!", "Contact Us via E-mail")
    var list2 = arrayOf(R.drawable.fb, R.drawable.linkedin, R.drawable.rate, R.drawable.email)
    var list1 = arrayOf(
        "Share on Facebook",
        "Share on Linkedin",
        "Share on Twitter",
        "Share on  via E-mail"
    )
    var list3 = arrayOf(R.drawable.fb, R.drawable.linkedin, R.drawable.twitter, R.drawable.email)
    var listBranch = ArrayList<Branch>()
    var listBranch2 = ArrayList<Branch>()
    var myView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment4, container, false)
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
        init()
    }

    fun init() {
        listBranch.clear()
        listBranch2.clear()
        for (x in list1.indices) {
            var branch = Branch()
            branch.branchId = list[x]
            branch.branchLat = list2[x]
            listBranch.add(branch)
        }
        for (x in list.indices) {
            var branch = Branch()
            branch.branchId = list1[x]
            branch.branchLat = list3[x]
            listBranch2.add(branch)
        }
        rv1.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = List1Adapter(activity, listBranch) { view, position ->
                when (position) {
                    0 -> {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/relianceengineeringandservices/"))
                        browserIntent.setPackage("com.android.chrome") // Whatever browser you are using
                        startActivity(browserIntent)
                    }
                    1 -> {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/relianceengineering"))
                        browserIntent.setPackage("com.android.chrome") // Whatever browser you are using
                        startActivity(browserIntent)
                    }
                    2 -> {
                       ToastUtils.showToastWith(activity,"This feature is not available")
                    }
                    3 -> {
                        ToastUtils.showToastWith(activity,"Email is not configured")
                    }
                }
            }
        }
        rv2.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = List1Adapter(activity, listBranch2) { view, position ->
                when (position) {
                    0 -> {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/relianceengineeringandservices/"))
                        browserIntent.setPackage("com.android.chrome") // Whatever browser you are using
                        startActivity(browserIntent)
                    }
                    1 -> {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/relianceengineering"))
                        browserIntent.setPackage("com.android.chrome") // Whatever browser you are using
                        startActivity(browserIntent)
                    }
                    2 -> {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"))
                        browserIntent.setPackage("com.android.chrome") // Whatever browser you are using
                        startActivity(browserIntent)
                    }
                    3 -> {
                        ToastUtils.showToastWith(activity,"Email is not configured")
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


}