package com.fyp.fragments.Setting

import android.content.Intent
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
import com.fyp.activities.ActivityHome
import com.fyp.activities.ActivitySetting
import com.fyp.activities.ActivitySubmit
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.Branch
import com.fyp.utils.SessionManager

import kotlinx.android.synthetic.main.privacypolicy.*


class AboutApp : Fragment(), View.OnClickListener, iOnBackPressed {

    var listBranch=ArrayList<Branch>()
    var myView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.aboutus, container, false)
        return myView
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back.setOnClickListener(this)
    }
    var sessionManager: SessionManager? = null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onResume() {
        super.onResume()

    }



    override fun onClick(v: View?) {
        var bundle = Bundle()
        when (v!!.id) {
            R.id.back->{
                requireActivity().let {
                    startActivity(
                        Intent(
                            activity as ActivitySetting,
                            ActivitySetting::class.java
                        )
                    )
                    it.finish()
                }
            }
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