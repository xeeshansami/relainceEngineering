package com.hbl.hblaccountopeningapp.utils.mail

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.fyp.mail.ByteArrayDataSource
import com.fyp.mail.JSSEProvider
import com.fyp.utils.ToastUtils
import com.sun.mail.smtp.SMTPTransport
import java.io.File
import java.security.Security
import java.util.*
import javax.activation.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

class GMailSender(private val user: String, private val password: String,context: Context) : Authenticator() {
    private val mailhost = "smtp.gmail.com"
    private val session: Session
     var context: Context?=null
    init {
        this.context=context
    }
    companion object {
        init {
            Security.addProvider(JSSEProvider())

        }
    }

    /*
    protected PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(user, password);
    }
*/
    @Synchronized
    @Throws(Exception::class)
    fun sendMail(
        subject: String?, body: String,
        sender: String?, recipients: String
    ) {
        val message = MimeMessage(session)
        val handler =
            DataHandler(ByteArrayDataSource(body.toByteArray(), "text/plain") as DataSource)
        message.sender = InternetAddress(sender)
        message.subject = subject
        message.dataHandler = handler
        if (recipients.indexOf(',') > 0) message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(recipients)) else message.setRecipient(
            Message.RecipientType.TO, InternetAddress(recipients))
        Transport.send(message)
    }

    @Synchronized
    @Throws(Exception::class)
    fun sendMail(
        context: Context?, file: File, subject: String?, body: String?,
        sender: String?, recipients: String?
    ) {
        try {
            // Create a default MimeMessage object.
            val message = MimeMessage(session)

            // Set From: header field of the header.
            message.setFrom(InternetAddress(sender))
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, InternetAddress(recipients))
            // Set Subject: header field
            message.subject = subject
            // Create the message part
            var messageBodyPart: BodyPart = MimeBodyPart()
            // Fill the message
            messageBodyPart.setText(body)
            // Create a multipar message
            val multipart: Multipart = MimeMultipart()
            // Set text message part
            multipart.addBodyPart(messageBodyPart)
            // Part two is attachment
            messageBodyPart = MimeBodyPart()
            val source: DataSource = FileDataSource(file)
            messageBodyPart.setDataHandler(DataHandler(source))
            messageBodyPart.setFileName(file.name)
            multipart.addBodyPart(messageBodyPart)
            val mc = CommandMap.getDefaultCommandMap() as MailcapCommandMap
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html")
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml")
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain")
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed")
            mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822")

            // Send the complete message parts
            message.setContent(multipart)
            // Send message
            val transport = session.getTransport("smtp") as SMTPTransport
            transport.connect(mailhost, 465, user, password)
            transport.sendMessage(message, message.allRecipients)
            // you can get SMTP return code here
            val code = transport.lastReturnCode
            if (code == 250) {
                ToastUtils.showToastWith(this.context,"Succeed: Email has been sent successfully!")
            }else{
                ToastUtils.showToastWith(this.context,"Error: Email has not been sent successfully!")
            }
        } catch (mex: MessagingException) {
            mex.printStackTrace()
        }
    }

    @Synchronized
    @Throws(Exception::class)
    fun sendMail(
        context: Context?, file: File, subject: String?, body: String?,
        sender: String?, recipients: String?,recipients2: String?
    ) {
        try {
            // Create a default MimeMessage object.
            val message = MimeMessage(session)

            // Set From: header field of the header.
            message.setFrom(InternetAddress(sender))
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, InternetAddress(recipients))
            message.addRecipient(Message.RecipientType.TO, InternetAddress(recipients2))
            // Set Subject: header field
            message.subject = subject
            // Create the message part
            var messageBodyPart: BodyPart = MimeBodyPart()
            // Fill the message
            messageBodyPart.setText(body)
            // Create a multipar message
            val multipart: Multipart = MimeMultipart()
            // Set text message part
            multipart.addBodyPart(messageBodyPart)
            // Part two is attachment
            messageBodyPart = MimeBodyPart()
            val source: DataSource = FileDataSource(file)
            messageBodyPart.setDataHandler(DataHandler(source))
            messageBodyPart.setFileName(file.name)
            multipart.addBodyPart(messageBodyPart)
            val mc = CommandMap.getDefaultCommandMap() as MailcapCommandMap
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html")
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml")
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain")
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed")
            mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822")

            // Send the complete message parts
            message.setContent(multipart)
            // Send message
            val transport = session.getTransport("smtp") as SMTPTransport
            transport.connect(mailhost, 465, user, password)
            transport.sendMessage(message, message.allRecipients)
            // you can get SMTP return code here
            val code = transport.lastReturnCode
            if (code == 250) {
                sendStatus()
            }else{
            }
        } catch (mex: MessagingException) {
            mex.printStackTrace()
        }
    }


    init {
        val props = Properties()
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        session = Session.getDefaultInstance(props,
            object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(
                        user, password) // Specify the Username and the PassWord
                }
            })
    }
    private fun sendStatus() {
//        val intent = Intent(Constants.STATUS_EMAIL_SENT)
//        // You can also include some extra data.
//        LocalBroadcastManager.getInstance(globalClass!!).sendBroadcast(intent)
    }
}