package com.fyp.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.utils.GlobalClass
import com.fyp.utils.SessionManager
import com.fyp.utils.TransparentProgressDialog
import kotlinx.android.synthetic.main.app_layout.*

class ActivitySortBy : AppCompatActivity(), View.OnClickListener {
    private val hideHandler = Handler()
    private var progressDialog: TransparentProgressDialog? = null
    @JvmField
    public var globalClass: GlobalClass? = null
    @Suppress("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        val flags =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window?.decorView?.systemUiVisibility = flags
        supportActionBar?.hide()
    }
    private var sessionManager: SessionManager? = null
    private var dummyButton: Button? = null
    private var fullscreenContent: View? = null
    private var fullscreenContentControls: View? = null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sortby)
        switchFragment(R.id.sortBy)
        home.setOnClickListener(this)
    }
    private fun switchFragment(startDestId: Int) {
//        val fragmentContainer = view?.findViewById<View>(R.id.nav_host)
//        val navController = Navigation.findNavController(fragmentContainer!!)
        val navController = findNavController(R.id.fragment1)
        val inflater = navController.navInflater
        val graph = navController.graph
        graph.startDestination = startDestId
        navController.graph = graph
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    @Suppress("InlinedApi")
    private fun show() {
        // Show the system bar
        fullscreenContent?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        // Schedule a runnable to display UI elements after a delay
        hideHandler.removeCallbacks(hidePart2Runnable)
        supportActionBar?.show()
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.home -> {
                var i=Intent(this, ActivityHome::class.java)
                // set the new task and clear flags
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(i)
            }
        }
    }
//    override fun onBackPressed() {
//        val navController = findNavController(R.id.fragment1)
//        if (navController.navigateUp()) {
//            navController.navigateUp()
//        } else {
//            finish()
//        }
//    }

}