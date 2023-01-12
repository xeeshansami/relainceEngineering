package com.fyp.fragments.Home

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.fyp.R
import com.fyp.activities.ActivityHome
import com.fyp.activities.ActivitySetting
import com.fyp.activities.ActivitySortBy
import com.fyp.adapters.ViewPagerAdapter
import com.fyp.db.MyDB
import com.fyp.models.Branch
import com.fyp.utils.SessionManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_logs.*
import kotlinx.android.synthetic.main.app_layout.*
import kotlinx.android.synthetic.main.app_layout.heading1
import kotlinx.android.synthetic.main.fragment_sortby.tabLayout
import kotlinx.android.synthetic.main.fragment_sortby.viewPager
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class FragmentHome() : Fragment(), View.OnClickListener {

    //    private var firebaseAuth: FirebaseAuth? = null
    private var sessionManager: SessionManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    class background(context: Context) :
        AsyncTask<Void, Void, java.util.ArrayList<String>>() {
        private var listPNo = java.util.ArrayList<String>()
        private var listLineNo = java.util.ArrayList<String>()
        private var listProductForm = java.util.ArrayList<String>()
        private var listSpecNo = java.util.ArrayList<String>()
        private var listUNS = java.util.ArrayList<String>()
        var listBranch = ArrayList<Branch>()
        var context: Context? = null
        var spinnerValue: TextView? = null
        var spinner: Spinner? = null
        var sessionManager: SessionManager? = null
        var progressData: ProgressDialog? = null
        var db: MyDB? = null

        init {
            this.spinnerValue = spinnerValue
            this.context = context
            this.spinner = spinner
            listPNo.clear()
            listLineNo.clear()
            listProductForm.clear()
            listUNS.clear()
            progressData = ProgressDialog(this.context as ActivityHome)
            progressData!!.setMessage("Please wait...")
            progressData!!.setCancelable(false)
            progressData!!.show()
            sessionManager = SessionManager(this.context as ActivityHome)
            db = MyDB(this.context as ActivityHome)
            db!!.createDatabase()
            db!!.open()
        }

        override fun onPreExecute() {
            super.onPreExecute()

        }

        override fun onPostExecute(result: java.util.ArrayList<String>?) {
            super.onPostExecute(result)
            progressData!!.dismiss()

        }

        override fun doInBackground(vararg params: Void?): java.util.ArrayList<String>? {
            var line = db!!.lineNo
            var specCusors = db!!.specNo
            var pNoCusors = db!!.pNo
            var prodNameCusors = db!!.productName
            var UNSCusors = db!!.uns
            while (line.moveToNext()) {
                listLineNo.add(line.getString(line.getColumnIndex("LineNo")))
            }
            line.close();
            while (specCusors.moveToNext()) {
                listSpecNo.add(specCusors.getString(specCusors.getColumnIndex("SpecNo")))
            }
            specCusors.close();
            while (UNSCusors.moveToNext()) {
                listUNS.add(UNSCusors.getString(UNSCusors.getColumnIndex("UNS")))
            }
            UNSCusors.close();
            while (pNoCusors.moveToNext()) {
                listPNo.add(pNoCusors.getString(pNoCusors.getColumnIndex("PNo")))
            }
            pNoCusors.close();
            while (prodNameCusors.moveToNext()) {
                listProductForm.add(prodNameCusors.getString(prodNameCusors.getColumnIndex("ProductForm")))
            }
            prodNameCusors.close();
//            val aSet1: Set<String> = HashSet(listSpecNo)
//            val aSet2: Set<String> = HashSet(listPNo)
//            val aSet3: Set<String> = HashSet(listUNS)
//            val aSet4: Set<String> = HashSet(listLineNo)
//            val aSet5: Set<String> = HashSet(listProductForm)
//            aSet1.reversed()
//            aSet2.reversed()
//            aSet3.reversed()
//            aSet4.reversed()
//            aSet5.reversed()
            sessionManager!!.setSpecNO(listSpecNo!!)
            sessionManager!!.setPNO(listPNo!!)
            sessionManager!!.setUNS(listUNS!!)
            sessionManager!!.setLineNO(listLineNo!!)
            sessionManager!!.setProdForm(listProductForm!!)
            return null
        }

    }

    fun init() {
        background(activity as ActivityHome).execute()
        (activity as ActivityHome).menu.setOnClickListener(this)
        (activity as ActivityHome).heading1.setOnClickListener(this)
        (activity as ActivityHome).menu.visibility = View.VISIBLE
        (activity as ActivityHome).heading1.visibility = View.GONE
        (activity as ActivityHome).heading.text = getString(R.string.app_name)
        sessionManager = SessionManager(this.context as ActivityHome)
        viewPager.adapter = ViewPagerAdapter((activity as ActivityHome).supportFragmentManager);
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
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(state: Int) {
                if (state == 0) {
                    (activity as ActivityHome).selectedPage = 0
                    var adapter = ArrayAdapter<String>(
                        activity as ActivityHome,
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1,
                        sessionManager!!.getSpecNO()!!
                    )
                    (activity as ActivityHome).edittext1.setHint("Enter Material Specification No.")
                    (activity as ActivityHome).edittext1.setOnKeyListener { _, keyCode, event ->
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                            //react to enter press here
                            var intent =
                                Intent((activity as ActivityHome), ActivitySortBy::class.java)
                            intent.putExtra(
                                "whereClouse",
                                (activity as ActivityHome).edittext1?.text.toString().trim()
                            )
                            intent.putExtra("columnName", "SpecNo")
                            intent.putExtra("value", 0)
                            (activity as ActivityHome).startActivity(intent)
                        }
                        true
                    }
                } else if (state == 1) {
                    (activity as ActivityHome).selectedPage = 1
                    (activity as ActivityHome).edittext1.hint = "Enter UNS No."
                    (activity as ActivityHome).edittext1.setOnKeyListener { _, keyCode, event ->
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                            //react to enter press here
                            var intent =
                                Intent((activity as ActivityHome), ActivitySortBy::class.java)
                            intent.putExtra(
                                "whereClouse",
                                (activity as ActivityHome).edittext1?.text.toString().trim()
                            )
                            intent.putExtra("columnName", "UNS")
                            intent.putExtra("value", 1)
                            (activity as ActivityHome).startActivity(intent)
                        }
                        true
                    }
                } else if (state == 2) {
                    (activity as ActivityHome).selectedPage = 2
                    (activity as ActivityHome).edittext1.setHint("Enter Product Name.")
                    (activity as ActivityHome).edittext1.setOnKeyListener { _, keyCode, event ->
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                            //react to enter press here
                            var intent =
                                Intent((activity as ActivityHome), ActivitySortBy::class.java)
                            intent.putExtra(
                                "whereClouse",
                                (activity as ActivityHome).edittext1?.text.toString().trim()
                            )
                            intent.putExtra("columnName", "ProductForm")
                            intent.putExtra("value", 2)
                            (activity as ActivityHome).startActivity(intent)
                        }
                        true
                    }
                } else if (state == 3) {
                    (activity as ActivityHome).selectedPage = 3
                    (activity as ActivityHome).edittext1.setHint("Enter Product No.")
                    (activity as ActivityHome).edittext1.setOnKeyListener { _, keyCode, event ->
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                            //react to enter press here
                            var intent =
                                Intent((activity as ActivityHome), ActivitySortBy::class.java)
                            intent.putExtra(
                                "whereClouse",
                                (activity as ActivityHome).edittext1?.text.toString().trim()
                            )
                            intent.putExtra("columnName", "PNo")
                            intent.putExtra("value", 3)
                            (activity as ActivityHome).startActivity(intent)
                        }
                        true
                    }
                } else if (state == 4) {
                    (activity as ActivityHome).selectedPage = 4
                    (activity as ActivityHome).edittext1.setHint("Enter Code No.")
                    (activity as ActivityHome).edittext1.setOnKeyListener { _, keyCode, event ->
                        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                            //react to enter press here
                            var intent =
                                Intent((activity as ActivityHome), ActivitySortBy::class.java)
                            intent.putExtra(
                                "whereClouse",
                                (activity as ActivityHome).edittext1?.text.toString().trim()
                            )
                            intent.putExtra("columnName", "LineNo")
                            intent.putExtra("value", 4)
                            (activity as ActivityHome).startActivity(intent)
                        }
                        true
                    }
                }
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
                var i = Intent((activity as ActivityHome), ActivitySetting::class.java)
                // set the new task and clear flags
                startActivity(i)
            }
            R.id.search -> {
                if (viewPager.currentItem == 0) {
                    var intent = Intent((activity as ActivityHome), ActivitySortBy::class.java)
                    intent.putExtra(
                        "whereClouse",
                        (activity as ActivityHome).edittext1?.text.toString().trim()
                    )
                    intent.putExtra("columnName", "SpecNo")
                    intent.putExtra("value", 0)
                    (activity as ActivityHome).startActivity(intent)
                } else if (viewPager.currentItem == 1) {
                    var intent = Intent((activity as ActivityHome), ActivitySortBy::class.java)
                    intent.putExtra(
                        "whereClouse",
                        (activity as ActivityHome).edittext1?.text.toString().trim()
                    )
                    intent.putExtra("columnName", "UNS")
                    intent.putExtra("value", 1)
                    (activity as ActivityHome).startActivity(intent)
                } else if (viewPager.currentItem == 2) {
                    var intent = Intent((activity as ActivityHome), ActivitySortBy::class.java)
                    intent.putExtra(
                        "whereClouse",
                        (activity as ActivityHome).edittext1?.text.toString().trim()
                    )
                    intent.putExtra("columnName", "ProductForm")
                    intent.putExtra("value", 2)
                    (activity as ActivityHome).startActivity(intent)
                } else if (viewPager.currentItem == 3) {
                    var intent = Intent((activity as ActivityHome), ActivitySortBy::class.java)
                    intent.putExtra(
                        "whereClouse",
                        (activity as ActivityHome).edittext1?.text.toString().trim()
                    )
                    intent.putExtra("columnName", "PNo")
                    intent.putExtra("value", 3)
                    (activity as ActivityHome).startActivity(intent)
                } else if (viewPager.currentItem == 4) {
                    var intent = Intent((activity as ActivityHome), ActivitySortBy::class.java)
                    intent.putExtra(
                        "whereClouse",
                        (activity as ActivityHome).edittext1?.text.toString().trim()
                    )
                    intent.putExtra("columnName", "LineNo")
                    intent.putExtra("value", 4)
                    (activity as ActivityHome).startActivity(intent)
                }
            }
        }
    }
}