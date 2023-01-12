package com.fyp.mail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.hbl.hblaccountopeningapp.utils.mail.GMailSender
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException


object SendEmail {
    var subject = ""

    init {
        subject = "Reliance Engineering - Final Summary Report"
    }

    @SuppressLint("WrongConstant")
    @JvmStatic
    fun sendAttachmentEmail(
        context: Context, e: File, messageSubject: String, messageBody: String
    ) {
        Thread {
            try {
                val sender = GMailSender("app.bugreport1@gmail.com", "hbl@1234",context)
                sender.sendMail(
                    context, e, subject, messageBody,
                    "app.bugreport1@gmail.com",
                    "shabbirahmedpk@gmail.com", "info.xeeshan@gmail.com"
                )
                Log.i("SystemExceptions", "Attachment Email successfully sent!")
            } catch (e: java.lang.Exception) {
                Handler(Looper.getMainLooper()).post(Runnable {
                })
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("WrongConstant")
    @JvmStatic
    fun sendEmail(
        context: Context, body: String
    ) {
        Log.i("EMAIL", "messageBody$body")
        Thread {
            try {
                val sender = GMailSender("app.bugreport1@gmail.com", "hbl@1234",context)
                sender.sendMail(
                    subject,
                    body,
                    "app.bugreport1@gmail.com",
                    "shabbirahmedpk@gmail.com"
                )
                Log.i("SystemExceptions", "Email successfully sent!")
            } catch (e: java.lang.Exception) {
                Log.i(
                    "SystemExceptions",
                            "----------ExceptionInfo----------\n" +
                            "1).Why CRASH: ${e.javaClass.simpleName}\n" +
                            "2).Method Name: ${e.stackTrace[0].methodName}\n" +
                            "3).Class/Fragment/Activity: ${e.stackTrace.get(0).fileName}\n" +
                            "4).Line Number: ${e.stackTrace[0].lineNumber}\n" +
                            "5).Crash Message: ${e.message}\n" +
                            "7).Stack Trace Message: ${e.stackTrace}\n" +
                            "8).Localize Message: ${e.localizedMessage}\n" +
                            "9).Suppressed: ${e.suppressed}" +
                            "10).Full Detail: ${e.stackTrace.get(0)}}"
                )
                Handler(Looper.getMainLooper()).post(Runnable {
                })
            }
        }.start()
    }


}