package com.fyp.fragments.SortBy

import android.app.ProgressDialog
import android.content.Context
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.fyp.R
import com.fyp.activities.ActivitySortBy
import com.fyp.adapters.ViewPagerSortByProductFormAdapter
import com.fyp.adapters.ViewPagerSortByProductNoAdapter
import com.fyp.db.MyDB
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.TableData
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.app_layout.*
import kotlinx.android.synthetic.main.app_layout.heading
import kotlinx.android.synthetic.main.app_layout.heading1
import kotlinx.android.synthetic.main.fragment_home.tabLayout
import kotlinx.android.synthetic.main.fragment_home.viewPager


class FragmentSortBy() : Fragment(), View.OnClickListener, iOnBackPressed {
    var list = ArrayList<TableData>()
    var listBranch = ArrayList<TableData>()
    var db: MyDB? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sortby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
    }

    fun init() {
        (activity as ActivitySortBy).menu.setOnClickListener(this)
        (activity as ActivitySortBy).backBtn.setOnClickListener(this)
        (activity as ActivitySortBy).heading1.setOnClickListener(this)
        (activity as ActivitySortBy).menu.visibility = View.GONE
        (activity as ActivitySortBy).heading1.visibility = View.GONE
        (activity as ActivitySortBy).backBtn.visibility = View.VISIBLE
        (activity as ActivitySortBy).heading.text = "Material Selector"
        listBranch.clear()
        var value = ""
        var colName = ""
        var tab1 = 0
        db = MyDB(this.context as ActivitySortBy)
        db!!.createDatabase()
        db!!.open()
        if (requireActivity().intent.hasExtra("whereClouse")) {
            value = requireActivity().intent.getStringExtra("whereClouse").toString()
            colName = requireActivity().intent.getStringExtra("columnName").toString()
            tab1 = requireActivity().intent.getIntExtra("value", 0)
            background(
                activity as ActivitySortBy,
                colName,
                value,
                tab1,
                viewPager,
                tabLayout
            ).execute()
        } else {
            ToastUtils.showToastWith(activity, "Something went wrong with database")
            return
        }


    }


    class background(
        context: Context,
        colName: String,
        value: String,
        tab: Int,
        viewPager: ViewPager,
        tabLayout: TabLayout
    ) :
        AsyncTask<Void, Void, java.util.ArrayList<TableData>>() {
        var listBranch = ArrayList<TableData>()
        var context: Context? = null
        var spinnerValue: TextView? = null
        var viewPager: ViewPager? = null
        var tabLayout: TabLayout? = null
        var sessionManager: SessionManager? = null
        var progressData: ProgressDialog? = null
        var db: MyDB? = null
        var tab = 0
        var colName = ""
        var value = ""
        var cursor: Cursor? = null

        init {
            this.tabLayout = tabLayout
            this.viewPager = viewPager
            this.tab = tab
            this.spinnerValue = spinnerValue
            this.colName = colName
            this.value = value
            this.context = context
            progressData = ProgressDialog(this.context as ActivitySortBy)
            progressData!!.setMessage("Please wait...")
            progressData!!.setCancelable(false)
            progressData!!.show()
            sessionManager = SessionManager(this.context as ActivitySortBy)
            db = MyDB(this.context as ActivitySortBy)
            db!!.createDatabase()
            db!!.open()
        }

        private fun setAllCaps(view: View?, caps: Boolean) {
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) setAllCaps(view.getChildAt(i), caps)
            } else if (view is TextView) view.isAllCaps = caps
        }

        override fun onPreExecute() {
            super.onPreExecute()
            cursor = db!!.getValues(colName, value)
        }

        override fun onPostExecute(result: java.util.ArrayList<TableData>?) {
            super.onPostExecute(result)
            (this.context as ActivitySortBy).runOnUiThread(Runnable {
                if (tab == 0 || tab == 1 || tab == 3 || tab == 4) {
                    var pager =
                        ViewPagerSortByProductFormAdapter(
                            (this.context as ActivitySortBy).supportFragmentManager,
                            result!!,
                            tab!!
                        );
                    viewPager!!.adapter = pager
                    tabLayout!!.setupWithViewPager(viewPager);
                    pager.notifyDataSetChanged()
                }else  if (tab == 2 ){
                    var pager = ViewPagerSortByProductNoAdapter(
                        (this.context as ActivitySortBy).supportFragmentManager,
                        result!!,
                        tab!!
                    );
                    viewPager!!.adapter = pager
                    tabLayout!!.setupWithViewPager(viewPager);
                    pager.notifyDataSetChanged()
                }
            })
            val tab = tabLayout!!.getTabAt(0)
            tab!!.view.setBackground(
                (this.context as ActivitySortBy).getResources().getDrawable(R.drawable.tab)
            );
            tabLayout!!.setOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    if (tab.isSelected) {
                        tab.view.setBackground(
                            (context as ActivitySortBy).getResources().getDrawable(R.drawable.tab)
                        );
                    } else {
                        tab.view.setBackground(
                            (context as ActivitySortBy).getResources()
                                .getDrawable(R.drawable.tab_bg)
                        );
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    tab.view.setBackground(
                        (context as ActivitySortBy).getResources().getDrawable(R.drawable.tab_bg)
                    );
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }
            })
            setAllCaps(tabLayout, false);
            progressData!!.dismiss()

        }

        override fun doInBackground(vararg params: Void?): java.util.ArrayList<TableData>? {
            while (cursor!!.moveToNext()) {
                var table = TableData()
                table.specNo = cursor!!.getString(cursor!!.getColumnIndex("SpecNo"))
                table.lineNo = cursor!!.getString(cursor!!.getColumnIndex("LineNo"))
                table.pNo = cursor!!.getString(cursor!!.getColumnIndex("PNo"))
                table.uns = cursor!!.getString(cursor!!.getColumnIndex("UNS"))
                table.typeGrade = cursor!!.getString(cursor!!.getColumnIndex("TypeGrade"))
                table.nominalComposition =
                    cursor!!.getString(cursor!!.getColumnIndex("NominalComposition"))
                table.productForm = cursor!!.getString(cursor!!.getColumnIndex("ProductForm"))

                table.groupNo = cursor!!.getString(cursor!!.getColumnIndex("GroupNo"))
                table.constructionI =
                    cursor!!.getString(cursor!!.getColumnIndex("ConstructionI")) + ", " +
                            cursor!!.getString(cursor!!.getColumnIndex("ConstructionIII")) + ", " +
                            cursor!!.getString(cursor!!.getColumnIndex("ConstructionVIII")) + ", " +
                            cursor!!.getString(cursor!!.getColumnIndex("ConstructionXII"))
                table.bookPage = cursor!!.getString(cursor!!.getColumnIndex("BookPage"))

                listBranch.add(table)
            }
            cursor!!.close();
            return listBranch
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.backBtn -> {
                requireActivity().let {
                    it.finish()
                }
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