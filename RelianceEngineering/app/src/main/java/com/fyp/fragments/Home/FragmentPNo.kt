package com.fyp.fragments.Home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.activities.ActivitySortBy
import com.fyp.activities.ActivityHome
import com.fyp.adapters.SearchableAdapter
import com.fyp.interfaces.iOnBackPressed
import com.fyp.utils.SessionManager
import kotlinx.android.synthetic.main.activity_logs.*
import kotlinx.android.synthetic.main.fragmentspinner.*
import kotlinx.android.synthetic.main.fragmentspinner4.spinner
import kotlinx.android.synthetic.main.fragmentspinner4.spinnerValue
import kotlinx.android.synthetic.main.fragmentspinner4.submitBtn
import java.util.*
import kotlin.collections.HashSet


class FragmentPNo : Fragment(), View.OnClickListener, iOnBackPressed {
    var myView: View? = null
    var TAG = "FragmentPNo"
    var sessionManager: SessionManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragmentspinner4, container, false)
        return myView
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            addSpinner()
        }
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onResume() {
        super.onResume()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun addSpinner() {
        submitBtn.setOnClickListener(this)
        sessionManager = SessionManager(this.context as ActivityHome)
        //        val adapter = ArrayAdapter(
//            (context as ActivityHome),
//            R.layout.view_spinner_finger_list,
//            R.id.text1,
//            sessionManager!!.getSpecNO()!!
//        )
//        adapter.setDropDownViewResource(R.layout.view_spinner_finger_list)
        spinner.minValue = 0
        spinner.wrapSelectorWheel = false
        val hashSet = HashSet<String>()
        hashSet.addAll(sessionManager!!.getPNO()!!)
        sessionManager!!.getPNO()!!.clear()
        sessionManager!!.getPNO()!!.addAll(hashSet)
        sessionManager!!.getPNO()!!.sort()
        spinner.maxValue = sessionManager!!.getPNO()!!.size - 1
        val stringArray: Array<String> =
            sessionManager!!.getPNO()!!.toArray(arrayOfNulls<String>(0))
        spinner.displayedValues = stringArray
        spinnerValue!!.text = sessionManager!!.getPNO()!![spinner.value]
        spinner.setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal -> //Display the newly selected value from picker
//            tv.setText("Selected value : " + spinner.get(newVal))
//            if((activity as ActivityHome).edittext1.text.isNullOrEmpty()) {
            spinnerValue!!.text = sessionManager!!.getPNO()!![oldVal]
//            }
//            if(oldVal!=newVal){
//                (activity as ActivityHome).edittext1!!.setText("")
//            }
        })
        var adapter = SearchableAdapter(
            activity as ActivityHome,
            sessionManager!!.getPNO()!!
        )
        (activity as ActivityHome).edittext1.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if((activity as ActivityHome).selectedPage == 3) {
                    if (s.isNotEmpty()) {
                        adapter.filter.filter(s.toString())
                        spinner.displayedValues = null
                        spinner.minValue = 0
                        spinner.value = 0
                        spinner.wrapSelectorWheel = false
                        spinner.maxValue = adapter.items.size - 1
                        val stringArray: Array<String> =
                            adapter.items.toArray(arrayOfNulls<String>(0))
                        spinner.displayedValues = stringArray
                        adapter.notifyDataSetChanged()
                        for(x in 0 until spinner.displayedValues.size){
                            if(spinner.displayedValues.get(x).contains(s)){
                                spinnerValue!!.text = spinner.displayedValues.get(x)
                                spinner.value=x
                                Log.i("Valuessss", spinner.displayedValues.get(x))
                                break
                            }
                        }
                    }else{
                        spinner.displayedValues = null
                        spinner.minValue = 0
                        spinner.value = 0
                        spinner.wrapSelectorWheel = false
                        spinner.maxValue =sessionManager!!.getPNO()!!.size - 1
                        spinner.displayedValues = stringArray
                        adapter.notifyDataSetChanged()
                        spinnerValue!!.text = spinner.displayedValues.get(0)
                    }

                }
            }
        })
    }


    override fun onClick(v: View?) {
        var bundle = Bundle()
        when (v!!.id) {
            R.id.submitBtn -> {
//                if ((activity as ActivityHome).edittext1.text.isNullOrEmpty()) {
                    var intent = Intent((activity as ActivityHome), ActivitySortBy::class.java)
                    intent.putExtra("whereClouse", spinnerValue!!.text)
                    intent.putExtra("columnName", "PNo")
                    intent.putExtra("value", 3)
                    (activity as ActivityHome).startActivity(intent)
//                } else {
//                    var intent = Intent((activity as ActivityHome), ActivitySortBy::class.java)
//                    intent.putExtra(
//                        "whereClouse",
//                        (activity as ActivityHome).edittext1!!.text.toString().trim()
//                    )
//                    intent.putExtra("columnName", "PNo")
//                    intent.putExtra("value", 3)
//                    (activity as ActivityHome).startActivity(intent)
//                }

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