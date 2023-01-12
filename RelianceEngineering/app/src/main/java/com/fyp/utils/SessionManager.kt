package com.fyp.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SessionManager(context: Context) {
    var context: Context? = null
    var sp: SharedPreferences? = null
    init {
        this.context = context
        getSharedPref()
    }
    private fun getSharedPref() {
        val PREFS_NAME = this.context!!.packageName
        this.sp= context!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    fun setStringVal(key: String?, value: String?) {
        if (key != null) {
            val edit = this.sp!!.edit()
            edit.putString(key, value)
            edit.commit()
        }
    }
    fun getStringVal(key: String?): String? {
        return this.sp!!.getString(key, "")
    }
    fun setIntVal(key: String?, value: Int?) {
        if (key != null) {
            val edit = this.sp!!.edit()
            edit.putInt(key, value!!)
            edit.commit()
        }
    }
    fun getIntVal(key: String?): Int? {
        return this.sp!!.getInt(key, 0)
    }

    fun setList(key: String?,value: ArrayList<String>) {
        val gson = Gson()
        val json = gson.toJson(value)
        val editor: SharedPreferences.Editor = this.sp!!.edit()
        editor.putString(key, json)
        editor.commit()
    }

    fun getList(key: String?): ArrayList<String>? {
        var companyList = ArrayList<String>()
        val json = Gson().toJson(companyList)
        if ( this.sp!! != null) {
            val gson = Gson()
            val string: String = this.sp!!.getString(key, json).toString()
            val type = object : TypeToken<ArrayList<String>?>() {}.type
            companyList = gson.fromJson(string, type)
            return companyList
        }
        return companyList
    }


    fun setPNO(value: ArrayList<String>) {
        val gson = Gson()
        val json = gson.toJson(value)
        val editor: SharedPreferences.Editor = this.sp!!.edit()
        editor.putString(Constant.pNO, json)
        editor.commit()
    }

    fun getPNO(): ArrayList<String>? {
        var companyList = ArrayList<String>()
        val json = Gson().toJson(companyList)
        if ( this.sp!! != null) {
            val gson = Gson()
            val string: String = this.sp!!.getString(Constant.pNO, json).toString()
            val type = object : TypeToken<ArrayList<String>?>() {}.type
            companyList = gson.fromJson(string, type)
            return companyList
        }
        return companyList
    }

    fun setLineNO(value: ArrayList<String>) {
        val gson = Gson()
        val json = gson.toJson(value)
        val editor: SharedPreferences.Editor = this.sp!!.edit()
        editor.putString(Constant.lineNo, json)
        editor.commit()
    }

    fun getLineNO(): ArrayList<String>? {
        var companyList = ArrayList<String>()
        val json = Gson().toJson(companyList)
        if ( this.sp!! != null) {
            val gson = Gson()
            val string: String = this.sp!!.getString(Constant.lineNo, json).toString()
            val type = object : TypeToken<ArrayList<String>?>() {}.type
            companyList = gson.fromJson(string, type)
            return companyList
        }
        return companyList
    }


    fun setSpecNO(value: ArrayList<String>) {
        val gson = Gson()
        val json = gson.toJson(value)
        val editor: SharedPreferences.Editor = this.sp!!.edit()
        editor.putString(Constant.specNo, json)
        editor.commit()
    }

    fun getSpecNO(): ArrayList<String>? {
        var companyList = ArrayList<String>()
        val json = Gson().toJson(companyList)
        if ( this.sp!! != null) {
            val gson = Gson()
            val string: String = this.sp!!.getString(Constant.specNo, json).toString()
            val type = object : TypeToken<ArrayList<String>?>() {}.type
            companyList = gson.fromJson(string, type)
            return companyList
        }
        return companyList
    }


    fun setProdForm(value: ArrayList<String>) {
        val gson = Gson()
        val json = gson.toJson(value)
        val editor: SharedPreferences.Editor = this.sp!!.edit()
        editor.putString(Constant.prodForm, json)
        editor.commit()
    }

    fun getProdForm(): ArrayList<String>? {
        var companyList = ArrayList<String>()
        val json = Gson().toJson(companyList)
        if ( this.sp!! != null) {
            val gson = Gson()
            val string: String = this.sp!!.getString(Constant.prodForm, json).toString()
            val type = object : TypeToken<ArrayList<String>?>() {}.type
            companyList = gson.fromJson(string, type)
            return companyList
        }
        return companyList
    }

    fun setUNS(value: ArrayList<String>) {
        val gson = Gson()
        val json = gson.toJson(value)
        val editor: SharedPreferences.Editor = this.sp!!.edit()
        editor.putString(Constant.UNS, json)
        editor.commit()
    }

    fun getUNS(): ArrayList<String>? {
        var companyList = ArrayList<String>()
        val json = Gson().toJson(companyList)
        if ( this.sp!! != null) {
            val gson = Gson()
            val string: String = this.sp!!.getString(Constant.UNS, json).toString()
            val type = object : TypeToken<ArrayList<String>?>() {}.type
            companyList = gson.fromJson(string, type)
            return companyList
        }
        return companyList
    }
}