package com.fyp.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fyp.R
import com.fyp.activities.ActivityHome.Extensions.hideKeyboard
import com.fyp.utils.GlobalClass
import com.fyp.utils.SessionManager
import com.fyp.utils.TransparentProgressDialog
import kotlinx.android.synthetic.main.activity_logs.*
import kotlinx.android.synthetic.main.app_layout.*

class ActivityHome : AppCompatActivity(), View.OnClickListener {
    private val hideHandler = Handler()
    private var progressDialog: TransparentProgressDialog? = null
    var selectedPage=0
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
        setContentView(R.layout.activity_logs)
        home.setOnClickListener(this)
        mainLayout.hideKeyboard()
//        if (sessionManager!!.getIntVal(Constant.LANGUAGE) == 1||sessionManager!!.getIntVal(Constant.LANGUAGE) == 0) {
//            AppLang.AppLang(this, "en")
//        } else {
//            AppLang.AppLang(this, "ur")
//        }
    }

    //hides soft keyboard anything else is tapped( screen, menu bar, buttons, etc. )
    override fun dispatchTouchEvent( ev: MotionEvent? ): Boolean {
        if ( currentFocus != null ) {
            val imm = getSystemService( Context.INPUT_METHOD_SERVICE ) as InputMethodManager
            imm.hideSoftInputFromWindow( currentFocus!!.windowToken, 0 )
        }
        return super.dispatchTouchEvent( ev )
    }
    object Extensions {
        fun View.hideKeyboard() {
            val inputMethodManager =
                context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE)
                        as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
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

//    override fun onBackPressed() {
//        val navController = findNavController(R.id.fragment1)
//        if (navController.navigateUp()) {
//            navController.navigateUp()
//        } else {
//            finish()
//        }
//    }
    fun getProgressDialogInstance(context: Context?): TransparentProgressDialog? {
        if (progressDialog == null) progressDialog = TransparentProgressDialog(
            context!!)
        return progressDialog
    }
    fun showDialog(context: Context?) {
        progressDialog = getProgressDialogInstance(context)
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    fun hideLoader() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.cancel()
            progressDialog = null
        }
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
}