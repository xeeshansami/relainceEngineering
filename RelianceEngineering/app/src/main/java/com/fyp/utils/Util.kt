package com.fyp.utils

import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.widget.NumberPicker

class Util {
    companion object {
        fun isValidEmail(target: String?): Boolean {
            return if (target == null) {
                false
            } else {
                //android Regex to check the email address Validation
                Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }
    }
    fun NumberPicker.animateChange(isIncrement: Boolean) {
        Handler().post {
            try {
                javaClass.getDeclaredMethod("changeValueByOne", Boolean::class.javaPrimitiveType).also { function ->
                    function.isAccessible = true
                    function.invoke(this, isIncrement)
                }
            } catch (e: Exception) {
                e.message?.let { Log.e(javaClass.simpleName, it) }
            }
        }
    }
}