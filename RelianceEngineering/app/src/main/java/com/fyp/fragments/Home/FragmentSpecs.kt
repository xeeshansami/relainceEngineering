package com.fyp.fragments.Home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityHome
import com.fyp.activities.ActivitySortBy
import com.fyp.adapters.SearchableAdapter
import com.fyp.interfaces.iOnBackPressed
import com.fyp.utils.SessionManager
import kotlinx.android.synthetic.main.activity_logs.*
import kotlinx.android.synthetic.main.fragmentspinner.spinner
import kotlinx.android.synthetic.main.fragmentspinner.spinnerValue
import kotlinx.android.synthetic.main.fragmentspinner.submitBtn
import kotlinx.android.synthetic.main.fragmentspinner2.*
import java.util.*
import kotlin.collections.HashSet


class FragmentSpecs : Fragment(), View.OnClickListener, iOnBackPressed {
    var myView: View? = null
    var TAG = "FragmentSpecs"
    var sessionManager: SessionManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragmentspinner, container, false)
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
//        spinner.animateChange(true)
        val hashSet = HashSet<String>()
        hashSet.addAll(sessionManager!!.getSpecNO()!!)
        sessionManager!!.getSpecNO()!!.clear()
        sessionManager!!.getSpecNO()!!.addAll(hashSet)
        Collections.sort(sessionManager!!.getSpecNO()!!, Collections.reverseOrder());
        spinner.maxValue = sessionManager!!.getSpecNO()!!.size - 1
        spinnerValue!!.text = sessionManager!!.getSpecNO()!![spinner.value]
        val stringArray: Array<String> =
            sessionManager!!.getSpecNO()!!.toArray(arrayOfNulls<String>(0))
        spinner.displayedValues = stringArray
        spinner.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal -> //Display the newly selected value from picker
//            tv.setText("Selected value : " + spinner.get(newVal))
//            if((activity as ActivityHome).edittext1.text.isNullOrEmpty()) {
            spinnerValue!!.text = sessionManager!!.getSpecNO()!![newVal]
//            }
//            if(oldVal!=newVal){
//                (activity as ActivityHome).edittext1!!.setText("")
//            }
        })
        var adapter = SearchableAdapter(
            activity as ActivityHome,
            sessionManager!!.getSpecNO()!!
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
                if( (activity as ActivityHome).selectedPage == 0) {
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
                        spinner.maxValue =sessionManager!!.getSpecNO()!!.size - 1
                        spinner.displayedValues = stringArray
                        adapter.notifyDataSetChanged()
                        spinnerValue!!.text = spinner.displayedValues.get(spinner.value)
                        Log.i("Valuessss",spinner.displayedValues.get(spinner.value))
                    }
                }
            }
        })
    }


    fun NumberPicker.animateChange(isIncrement: Boolean) {
        Handler().post {
            try {
                javaClass.getDeclaredMethod("changeValueByOne", Boolean::class.javaPrimitiveType)
                    .also { function ->
                        function.isAccessible = true
                        function.invoke(this, isIncrement)
                    }
            } catch (e: Exception) {
                e.message?.let { Log.e(javaClass.simpleName, it) }
            }
        }
    }

    override fun onClick(v: View?) {
        var bundle = Bundle()
        when (v!!.id) {
            R.id.submitBtn -> {
//                if ((activity as ActivityHome).edittext1.text.isNullOrEmpty()) {
                    var intent = Intent((activity as ActivityHome), ActivitySortBy::class.java)
                    intent.putExtra("whereClouse", spinnerValue!!.text)
                    intent.putExtra("columnName", "SpecNo")
                    intent.putExtra("value", 0)
                    (activity as ActivityHome).startActivity(intent)
//                }
//                } else {
//                    var intent = Intent((activity as ActivityHome), ActivitySortBy::class.java)
//                    intent.putExtra(
//                        "whereClouse",
//                        (activity as ActivityHome).edittext1!!.text.toString().trim()
//                    )
//                    intent.putExtra("columnName", "SpecNo")
//                    intent.putExtra("value", 0)
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