package com.fyp.fragments.Submit

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp.R
import com.fyp.activities.ActivitySortBy
import com.fyp.activities.ActivityHome
import com.fyp.activities.ActivitySubmit
import com.fyp.activities.ActivitySummary
import com.fyp.adapters.TempratureAdapter
import com.fyp.db.MyDB
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.Branch
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_submit.*
import kotlinx.android.synthetic.main.submit.*
import org.apache.commons.lang3.Range


class Submit4 : Fragment(), View.OnClickListener, iOnBackPressed {
    var list = arrayOf("Section I :", "Section III :", "Section VIII-1 :", "Section XII:")
    var list2 = arrayOf("650", "650 (SPT)", "900", "650")
    var listBranch = ArrayList<Branch>()
    var value = ""
    var myView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.submit, container, false)
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

    @SuppressLint("ResourceAsColor")
    fun init() {
       submitBtn.setOnClickListener(this)
        if (requireActivity().intent.hasExtra("TemperatureList")) {
            listBranch.clear()
            var list = requireActivity().intent.getStringArrayListExtra("TemperatureList")
            value = requireActivity().intent.getStringExtra("LineNo").toString()
            value1.setText(requireActivity().intent.getStringExtra("material").toString())
            value2.setText(requireActivity().intent.getStringExtra("typeGrade").toString())
            for (x in list?.indices!!) {
                var branch = Branch()
                branch.branchName = list?.get(x)
                listBranch.add(branch)
            }
            rvHome.apply {
                layoutManager = LinearLayoutManager(requireContext())
                var adapterV = TempratureAdapter(activity, listBranch)
                adapter = adapterV
                adapterV.notifyDataSetChanged()
            }
        }
        if (listBranch[3].branchName == "NP") {
            edittext1.isEnabled = false
            edittext1.background = resources!!.getDrawable(R.drawable.shape_border_disbaled);
            edittext1.setTextColor(R.color.black)
            edittext1.setText("Section XII - Not Permitted")
        } else {
            edittext1.isEnabled = true
            edittext1.hint = "Section XII : Range "+resources.getString(R.string.special_symbol)+" ${listBranch.get(3).branchName}"
        }
        edittext1.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                //react to enter press here
                var intent = Intent((activity as ActivityHome), ActivitySortBy::class.java)
                intent.putExtra("whereClouse", edittext1?.text.toString().trim())
                intent.putExtra("columnName", "SpecNo")
                intent.putExtra("value", 1)
                (activity as ActivityHome).startActivity(intent)
            }
            true
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.submitBtn -> {
                if (validation()) {
                    Log.i("Case","Result = "+ String.format("%.3f", modify()))
                    var intent=Intent(activity as ActivitySubmit, ActivitySummary::class.java)
                    intent.putExtra("Section", "XII")
                    intent.putExtra("Temperature", listBranch.get(3).branchName)
                    intent.putExtra("InputTemperature", edittext1.text.toString().trim())
                    intent.putExtra("LineNo", value)
                    intent.putExtra("CalculatedValue",String.format("%.3f", modify()))
                    startActivity(intent)
                }
            }
        }
    }

    var db: MyDB? = null
    var cursor: Cursor? = null
    var cursor2: Cursor? = null
    fun modify(): Double {
        var y1 = 0.0
        var y2 = 0.0
        var x1 = 0.0
        var x2 = 0.0
        var result = 0.0
        var tempVal = edittext1.text.toString().toInt()
        db = MyDB(this.context as ActivitySubmit)
        db!!.createDatabase()
        db!!.open()
        if (getCalcValue().size == 1) {
            var col1 = getCalcValue()[0]
            cursor = db!!.getValueCol(value)
            while (cursor!!.moveToNext()) {
                result = cursor!!.getString(cursor!!.getColumnIndex(col1.toString())).toDouble()
                Log.i("Case", "Case1 => Col1 = $result")
            }
            cursor!!.close();
            return result
        } else if (getCalcValue().size == 2) {
            var col1 = getCalcValue()[0]
            var col2 = getCalcValue()[1]
            x1=col1.toDouble()
            x2=col2.toDouble()
            cursor = db!!.getValueCol( value)
            cursor2 = db!!.getValueCol(value)
            while (cursor!!.moveToNext()) {
                y1 = cursor!!.getString(cursor!!.getColumnIndex(col1.toString())).toDouble()
                Log.i("Case", "Case2 => Col1 = $y1")
            }
            cursor!!.close();
            while (cursor2!!.moveToNext()) {
                y2 = cursor2!!.getString(cursor2!!.getColumnIndex(col2.toString())).toDouble()
                Log.i("Case", "Case2 => Col2 = $y2")
            }
            result = y1 + (y2 - y1) * ((tempVal - x1) / (x2 - x1))
            Log.i("Case", "Expression Values => ${y1 + (y2 - y1) * ((tempVal - x1) / (x2 - x1))}")
            cursor2!!.close();
            return result
        } else {
            return 0.0
        }
    }

    fun getCalcValue(): ArrayList<Int> {
        var pair = ArrayList<Int>()
        var tempVal = edittext1.text.toString().toInt()
        var temperaturValues = arrayListOf(
            100, 150, 200, 250, 300,
            400, 500, 600, 650, 700,
            750, 800, 850, 900, 950,
            1000, 1050, 1100, 1150,
            1200, 1250, 1300, 1350,
            1400, 1450, 1500, 1550,
            1600, 1650
        )
        if(tempVal<temperaturValues!!.get(0)){
            return pair
        }else {
            for (x in 0 until temperaturValues!!.size) {
                if (tempVal == temperaturValues[x]) {
                    pair.add(temperaturValues[x])
                    Log.i("Case", "Match Value - TempCOl1 => ${temperaturValues[x + 1]}")
                    break
                } else {
                    val validRange: Range<Int> =
                        Range.between(temperaturValues[x], temperaturValues[x + 1])
                    // Check if it's in the range
                    if (validRange.contains(tempVal)) {
                        pair.add(temperaturValues[x])
                        pair.add(temperaturValues[x + 1])
                        Log.i("Case", "Case In Between Values - ArraySize => ${pair.size}")
                        Log.i("Case", "Case In Between Values - TempCOl1 => ${temperaturValues[x]}")
                        Log.i(
                            "Case",
                            "Case In Between Values - TempCOl2 => ${temperaturValues[x + 1]}"
                        )
                        break
                    }
                }
            }
        }
        return pair
    }
    private fun validation(): Boolean {
        return when {
            !edittext1.text.toString()
                .isNullOrEmpty() && listBranch[3].branchName != "NP" && edittext1.text.toString()
                .toDouble()  > listBranch[3].branchName.toDouble() -> {
                ToastUtils.exceptionToast(
                    activity,
                    "Temperature should not be greater than Section Temperature"
                )
                false
            }
            listBranch[3].branchName == "NP" -> {
                ToastUtils.exceptionToast(
                    activity,
                    "This Material is not permitted for selected Code Section, please select another material or change Code Section. You cannot proceed with Not Permitted (NP) material."
                )
                false
            }
            edittext1.text.toString().isNullOrEmpty() -> {
                ToastUtils.exceptionToast(
                    activity,
                    "Temperature input field is empty. Please input temperature value to proceed."
                )
                false
            }
            else -> true
        }
    }

    override fun onBackPressed(): Boolean {
        requireActivity().finish()
        return true
    }
}