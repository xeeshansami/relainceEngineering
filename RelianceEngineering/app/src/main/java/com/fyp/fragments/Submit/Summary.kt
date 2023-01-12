package com.fyp.fragments.Submit

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.fyp.R
import com.fyp.activities.ActivitySummary
import com.fyp.db.MyDB
import com.fyp.interfaces.iOnBackPressed
import com.fyp.mail.SendEmail
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.nabinbhandari.android.permissions.PermissionHandler
import kotlinx.android.synthetic.main.app_layout.*
import kotlinx.android.synthetic.main.summary.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Summary : Fragment(), View.OnClickListener, iOnBackPressed {
    protected var fontHeader: Font? = null
    protected var fontCell: Font? = null
    protected var fontSelected: Font? = null
    protected var fontCustomer: Font? = null
    var bfheader: BaseFont? = null
    var bfCell: BaseFont? = null
    var permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var hashMap = HashMap<String, String>()
    var stringList = ArrayList<String>()
    var stringList2 = ArrayList<String>()
    var db: MyDB? = null
    var myView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.summary, container, false)
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

    fun init() {

        emailBtn.setOnClickListener(this)
        pdfBtn.setOnClickListener(this)
        (activity as ActivitySummary).menu.setOnClickListener(this)
        (activity as ActivitySummary).backBtn.setOnClickListener(this)
        (activity as ActivitySummary).heading1.setOnClickListener(this)
        (activity as ActivitySummary).menu.visibility = View.GONE
        (activity as ActivitySummary).backBtn.visibility = View.VISIBLE
        (activity as ActivitySummary).heading1.visibility = View.GONE
        (activity as ActivitySummary).heading.text = "Final Report Summary"
        var value = ""
        db = MyDB(this.context as ActivitySummary)
        sessionManager = SessionManager(this.context as ActivitySummary)
        db!!.createDatabase()
        db!!.open()
        if (requireActivity().intent.hasExtra("LineNo")) {
            value = requireActivity().intent.getStringExtra("LineNo").toString()
        } else {
            ToastUtils.showToastWith(activity, "Something went wrong with database")
            return
        }
        var c = db!!.getAllValues(value)
        while (c.moveToNext()) {
            txt1.text = c.getString(c.getColumnIndex("SpecNo"))
            txt2.text = c.getString(c.getColumnIndex("TypeGrade"))
            txt3.text = c.getString(c.getColumnIndex("BookPage"))
            txt4.text = requireActivity().intent.getStringExtra("Section")
            txt5.text =
                resources.getString(R.string.special_symbol) + " " + requireActivity().intent.getStringExtra(
                    "Temperature"
                )
            txt6.text = requireActivity().intent.getStringExtra("InputTemperature")
            txt7.text = requireActivity().intent.getStringExtra("CalculatedValue")
            txt8.text = c.getString(c.getColumnIndex("PNo"))
            txt9.text = c.getString(c.getColumnIndex("GroupNo"))
            txt10.text = c.getString(c.getColumnIndex("ClassTemper"))
            txt11.text = c.getString(c.getColumnIndex("UNS"))
            txt12.text = c.getString(c.getColumnIndex("NominalComposition"))
            txt13.text = c.getString(c.getColumnIndex("ProductForm"))
            var listString = ""
            var value = sessionManager!!.getList(Constant.NOTES)
            for (s in value!!) {
                listString += "$s, "
            }
            txt14.text = c.getString(c.getColumnIndex("SizeThickness"))
            txt15.text = listString!!
            txt16.text = c.getString(c.getColumnIndex("InternalPressure"))
            txt17.text = c.getString(c.getColumnIndex("MinTensileStrength"))
            txt18.text = c.getString(c.getColumnIndex("MinYieldStrength"))

            stringList.add("SpecNo")
            stringList.add("TypeGrade")
            stringList.add("BookPage")
            stringList.add("Section")
            stringList.add("TemperatureLimits")
            stringList.add("InputTemperature")
            stringList.add("StressValue")
            stringList.add("PNo")
            stringList.add("GroupNo")
            stringList.add("ClassTemper")
            stringList.add("UNS")
            stringList.add("NominalComposition")
            stringList.add("ProductForm")
            stringList.add("SizeThickness")
            stringList.add("Notes")
            stringList.add("InternalPressure")
            stringList.add("MinTensileStrength")
            stringList.add("MinYieldStrength")
            stringList2.add(txt1.text.toString().trim())
            stringList2.add(txt2.text.toString().trim())
            stringList2.add(txt3.text.toString().trim())
            stringList2.add(txt4.text.toString().trim())
            stringList2.add(txt5.text.toString().trim())
            stringList2.add(txt6.text.toString().trim())
            stringList2.add(txt7.text.toString().trim())
            stringList2.add(txt8.text.toString().trim())
            stringList2.add(txt9.text.toString().trim())
            stringList2.add(txt10.text.toString().trim())
            stringList2.add(txt11.text.toString().trim())
            stringList2.add(txt12.text.toString().trim())
            stringList2.add(txt13.text.toString().trim())
            stringList2.add(txt14.text.toString().trim())
            stringList2.add(txt15.text.toString().trim())
            stringList2.add(txt16.text.toString().trim())
            stringList2.add(txt17.text.toString().trim())
            stringList2.add(txt18.text.toString().trim())
            for (x in stringList2.indices) {
                hashMap.put(stringList[x], stringList2[x])
            }
        }
        c.close();

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun sendEmail() {
        val builder = AlertDialog.Builder((activity as ActivitySummary))
        builder.setTitle("SEND EMAIL")
        builder.setCancelable(false)
        builder.setMessage("Do you want to send Email.")
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            permission(false)
            dialog.dismiss()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun permission(check: Boolean) {
        com.nabinbhandari.android.permissions.Permissions.check(
            activity as ActivitySummary,
            permissions,
            null,
            null,
            object : PermissionHandler() {
                @RequiresApi(Build.VERSION_CODES.KITKAT)
                override fun onGranted() {
                    createPdf(hashMap, "SummaryReport", check)
                }

                @SuppressLint("WrongConstant")
                override fun onDenied(
                    context: Context,
                    deniedPermissions: ArrayList<String>,
                ) {
                    ToastUtils.normalShowToast(
                        activity,
                        "Permission denied, please enabled the permissions from setting", 1
                    )
                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun generatePDF() {
        val builder = AlertDialog.Builder((activity as ActivitySummary))
        builder.setTitle("Generate PDF")
        builder.setCancelable(false)
        builder.setMessage("Do you want to generate report as PDF?")
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            permission(true)
            dialog.dismiss()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun sharePdf(file: File, filename: String) {
        val builder = AlertDialog.Builder((activity as ActivitySummary))
        builder.setTitle("File Generate Successfully!!")
        builder.setCancelable(false)
        builder.setMessage("Do you want to share/export pdf report?")
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            val shareIntent = shareFile(file, "$filename.pdf")
            startActivity(shareIntent)
            dialog.dismiss()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }


    fun messageBody(): String {
        return "<h3 style=\\\"text-align: center;\\\">Stress Table App Product Summary</h3>\" +\n" +
                "                \"<p>Material Specification : \\${
                    txt1.text.toString().trim()
                }<br>\" +\n" +
                "                \"Type \\\\ Grade : \\${txt2.text.toString().trim()}<br>\" +\n" +
                "                \"Location Reference : \\${
                    txt3.text.toString().trim()
                }<br>\" +\n" +
                "                \"Selected Section : \\${txt4.text.toString().trim()}<br>\" +\n" +
                "                \"Temperature Limits (°F) : ≤ \\${
                    txt5.text.toString().trim()
                }<br>\" +\n" +
                "                \"Input Temperature (°F) : \\${
                    txt6.text.toString().trim()
                } <br>\" +\n" +
                "                \"<font color=\\\"red\\\">Calculated Stress (KSI) : \\${
                    txt7.text.toString().trim()
                }</font> <br>\" +\n" +
                "                \"P - Number : \\${txt8.text.toString().trim()}<br>\" +\n" +
                "                \"Group Number : \\${txt9.text.toString().trim()}<br>\" +\n" +
                "                \"Class \\\\ Condition \\\\ Temper : \\${
                    txt10.text.toString().trim()
                }<br>\" +\n" +
                "                \"Alloy Design \\\\ UNS Number : \\${
                    txt11.text.toString().trim()
                }<br>\" +\n" +
                "                \"Nominal Composition : \\${
                    txt12.text.toString().trim()
                }<br>\" +\n" +
                "                \"Product Form : \\${txt13.text.toString().trim()}<br>\" +\n" +
                "                \"Size \\\\ Thickness (INCH) : \\${
                    txt14.text.toString().trim()
                }<br>\" +\n" +
                "                \"Notes : \\${txt15.text.toString().trim()}<br>\" +\n" +
                "                \"External Pressure Chart Number : \\${
                    txt16.text.toString().trim()
                }<br>\" +\n" +
                "                \"Minimum Tensile Strength (KSI) : \\${
                    txt17.text.toString().trim()
                }<br>\" +\n" +
                "                \"Minimum Yield Strength (KSI) : \\${
                    txt18.text.toString().trim()
                }<br>\" +\n" +
                "                \"</p>\" +\n" +
                "        \"<h5 style=\\\"text-align: center;\\\">This product summary report has been generated by Stress Table US Customary. Copyright ⓒ Reliance Engineering &amp; Services.</h5>"
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.backBtn -> {
                requireActivity().let {
                    it.finish()
                }
            }
            R.id.emailBtn -> {
                sendEmail()
            }
            R.id.pdfBtn -> {
                generatePDF()
            }
        }
    }

    private fun addMetaData(document: Document) {
        document.addTitle("My first PDF")
        document.addSubject("Using iText")
        document.addKeywords("Java, PDF, iText")
        document.addAuthor("Lars Vogel")
        document.addCreator("Lars Vogel")
    }

    private fun addEmptyLine(paragraph: Paragraph, number: Int) {
        for (i in 0 until number) {
            paragraph.add(Paragraph(" "))
        }
    }


    @Throws(DocumentException::class)
    private fun addContent(document: Document) {
        fontCell = FontFactory.getFont(FontFactory.HELVETICA, 10f)
        var anchor = Anchor("ESTIMATING APP", fontCell)
        anchor.name = "ESTIMATING APP"

        // Second parameter is the number of the chapter
        var catPart = Chapter(Paragraph(anchor), 1)
        var subPara = Paragraph("Subcategory 1", fontCell)
        var subCatPart = catPart.addSection(subPara)
        subCatPart.add(Paragraph("Hello"))
        subPara = Paragraph("Subcategory 2", fontCell)
        subCatPart = catPart.addSection(subPara)
        subCatPart.add(Paragraph("Paragraph 1"))
        subCatPart.add(Paragraph("Paragraph 2"))
        subCatPart.add(Paragraph("Paragraph 3"))

        // Add a list
        val paragraph = Paragraph()
        addEmptyLine(paragraph, 5)
        subCatPart.add(paragraph)

        // Add a table

        // Now add all this to the document
        document.add(catPart)

        // Next section
        anchor = Anchor("Second Chapter", fontCell)
        anchor.name = "Second Chapter"

        // Second parameter is the number of the chapter
        catPart = Chapter(Paragraph(anchor), 1)
        subPara = Paragraph("Subcategory", fontCell)
        subCatPart = catPart.addSection(subPara)
        subCatPart.add(Paragraph("This is a very important message"))

        // Now add all this to the document
        document.add(catPart)
    }

    @Throws(DocumentException::class)
    private fun addTitlePage(document: Document) {
        fontCell = FontFactory.getFont(FontFactory.HELVETICA, 10f)
        val preface = Paragraph()
        // We add one empty line
//        addEmptyLine(preface, 1)
        // Lets write a big header
//        preface.add(Paragraph("Title of the document", fontCell))
//        addEmptyLine(preface, 1)
        // Will create: Report generated by: _name, _date
//        preface.add(
//            Paragraph(
//                "Report generated by: " + System.getProperty("user.name") + ", " + Date(),  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//                fontCell
//            )
//        )
//        addEmptyLine(preface, 3)
//        preface.add(
//            Paragraph(
//                "This document describes something which is very important ",
//                fontCell
//            )
//        )
//        addEmptyLine(preface, 8)
//        preface.add(
//            Paragraph(
//                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.de ;-).",
//                fontCell
//            )
//        )
//        document.add(preface)
        // Start a new page
//        document.newPage()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun createPdf(list: HashMap<String, String>, filename: String, check: Boolean) {
        try {
            val dir = Environment.getExternalStorageDirectory()
                .toString() + File.separator + resources.getString(R.string.app_name)
            var file = File(dir)
            if (!file.exists()) {
                file.mkdirs()
            }
            val file2 = File(dir, "$filename.pdf")
            val document = Document() // create the document
            PdfWriter.getInstance(document, FileOutputStream(file2))
            document.open()
//            addTitlePage(document)
//            addContent(document)
            val head1Font: Font = FontFactory.getFont(FontFactory.TIMES_BOLD, 23f)
            val head2Font: Font = FontFactory.getFont(FontFactory.HELVETICA, 12f)
            val p1 = Paragraph()
            val p2 = Paragraph()
            val p3 = Paragraph()
            val p4 = Paragraph()
            val p5 = Paragraph()
            p1.alignment = Element.ALIGN_CENTER
            p2.alignment = Element.ALIGN_CENTER
            p3.alignment = Element.ALIGN_CENTER
            p4.alignment = Element.ALIGN_CENTER
            p5.alignment = Element.ALIGN_CENTER
            fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 13f)
            fontCell = FontFactory.getFont(FontFactory.HELVETICA, 10f)
            p1.font = head1Font
            p3.font = head2Font
            val date = Date()
            val stringDate: String = DateFormat.getDateTimeInstance().format(date)
            p1.add(resources.getString(R.string.app_name))
            p2.add("----------------------------------------")
            p3.add("Final Summary Report | Created Date: $stringDate")
            p5.add(" ")
            document.add(p1)
            document.add(p2)
            document.add(p3)
            document.add(p5)
            val table = PdfPTable(2)
            table.widthPercentage = 100f
            val widths = floatArrayOf(40f, 40f)
            table.setWidths(widths)
            table.addCell(createCellForHeader("Entities", 1, 1, 1, fontCell, Element.ALIGN_LEFT))
            table.addCell(createCellForHeader("Value", 1, 1, 1, fontCell, Element.ALIGN_LEFT))
            for ((key, value) in list.entries) {
                if(key=="StressValue"){
                    fontCell!!.color = BaseColor.WHITE
                    table.addCell(createSpecificCellForHeader(key, 1, 1, 1, fontCell, Element.ALIGN_LEFT))
                    table.addCell(createSpecificCellForHeader(value, 1, 1, 1, fontCell, Element.ALIGN_LEFT))
                }else{
                    table.addCell(createCellForInvoice(key, 1, 1, 1, fontCell, Element.ALIGN_LEFT))
                    table.addCell(createCellForInvoice(value, 1, 1, 1, fontCell, Element.ALIGN_LEFT))
                }
            }
            document.add(table)
            document.addCreationDate()
            document.close()
            ToastUtils.exceptionToast(activity, "Saved your pdf file in AGS folder")
            if (check) {
                sharePdf(file, "SummaryReport")
            } else {
                SendEmail.sendAttachmentEmail(
                    (activity as ActivitySummary),
                    file2,
                    getString(R.string.app_name) + " Report",
                    "This report is sending by Zeeshan Sami"
                )
            }
        } catch (e: FileNotFoundException) {
            ToastUtils.exceptionToast(activity, e.message)
            e.message?.let { Log.i("TableError", it) }
            return
        } catch (e: DocumentException) {
            ToastUtils.exceptionToast(activity, e.message)
            e.message?.let { Log.i("TableError", it) }
            return
        } catch (e: Exception) {
            return
        }
    }

    fun createCellForHeader(
        content: String?,
        colspan: Int,
        rowspan: Int,
        border: Int,
        font: Font?,
        Align: Int
    ): PdfPCell? {
        val cell = PdfPCell(Phrase(content, font))
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.border = border
        cell.horizontalAlignment = Element.ALIGN_LEFT
        cell.border = Rectangle.BOX
        cell.horizontalAlignment = Align
        cell.borderColor =
            BaseColor(
                ContextCompat.getColor(
                    activity as ActivitySummary,
                    R.color.black
                )
            )
        cell.backgroundColor =
            BaseColor(ContextCompat.getColor(activity as ActivitySummary, R.color.light_blue_600))
        return cell
    }

    fun createSpecificCellForHeader(
        content: String?,
        colspan: Int,
        rowspan: Int,
        border: Int,
        font: Font?,
        Align: Int
    ): PdfPCell? {
        val cell = PdfPCell(Phrase(content, font))
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.border = border
        cell.horizontalAlignment = Element.ALIGN_LEFT
        cell.border = Rectangle.BOX
        cell.horizontalAlignment = Align
        cell.borderColor =
            BaseColor(
                ContextCompat.getColor(
                    activity as ActivitySummary,
                    R.color.black
                )
            )
        cell.backgroundColor =
            BaseColor(ContextCompat.getColor(activity as ActivitySummary, android.R.color.holo_red_dark))
        return cell
    }

    private fun shareFile(file: File, filename: String): Intent? {
        val uri: Uri = FileProvider.getUriForFile(
            activity as ActivitySummary,
            "com.fyp.provider",
            File(file.path + "/" + filename)
        )
        val share = Intent()
        share.action = Intent.ACTION_SEND
        share.type = "*/*"
        share.putExtra(Intent.EXTRA_STREAM, uri)
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        Intent.createChooser(share, "Share $filename")
        //        share.setPackage("com.whatsapp");
        return share
    }

    @Throws(IOException::class, DocumentException::class)
    fun createCellForInvoice(
        content: String?,
        colspan: Int,
        rowspan: Int,
        border: Int,
        font: Font?,
        Align: Int
    ): PdfPCell? {
        bfheader = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED)
        fontHeader = FontFactory.getFont(FontFactory.HELVETICA, 10f)
        val cell = PdfPCell(Phrase(content, fontHeader))
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.border = border
        cell.horizontalAlignment = Element.ALIGN_LEFT
        cell.border = Rectangle.BOX
        cell.horizontalAlignment = Align
        cell.borderColor =
            BaseColor(ContextCompat.getColor(activity as ActivitySummary, R.color.black))
//        cell.backgroundColor = BaseColor(ContextCompat.getColor(activity as ActivitySummary, R.color.themeColor))
        return cell
    }

    fun createCell(
        content: String?,
        colspan: Int,
        rowspan: Int,
        border: Int,
        font: Font?,
        Align: Int
    ): PdfPCell? {
        val cell = PdfPCell(Phrase(content, font))
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.border = border
        cell.border = Rectangle.BOX
        cell.borderColor =
            BaseColor(ContextCompat.getColor(activity as ActivitySummary, R.color.black))
        cell.horizontalAlignment = Align
        cell.backgroundColor =
            BaseColor(ContextCompat.getColor(activity as ActivitySummary, R.color.white))
        return cell
    }


    override fun onBackPressed(): Boolean {
        requireActivity().finish()
        return true
    }
}