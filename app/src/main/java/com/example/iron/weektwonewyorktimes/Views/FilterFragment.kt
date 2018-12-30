package com.example.iron.weektwonewyorktimes.Views


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.iron.weektwonewyorktimes.MainActivity
import com.example.iron.weektwonewyorktimes.Models.*
import com.example.iron.weektwonewyorktimes.R
import kotlinx.android.synthetic.main.fragment_filter.*
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FilterFragment : DialogFragment(){
    val cal = Calendar.getInstance()
    var day_begin: TextView? = null
    var spiner : Spinner? = null
    var buttonSave : Button? = null
    private lateinit var listCheckbox : ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        day_begin = view.findViewById(R.id.edt_begindate)
        spiner = view.findViewById(R.id.spinner_filter)
        buttonSave = view.findViewById(R.id.buttonSave_filter)

        getBeginDate()
        getSpiner()
        saveFilter()

        return view
    }

    private fun saveFilter() {
        listCheckbox = ArrayList()
        buttonSave?.setOnClickListener{

            listCheckbox.add(day_begin?.text.toString())
            listCheckbox.add(spiner?.selectedItem.toString())
            if (checkbox_Arts.isChecked){
                listCheckbox.add(CHECKBOX_ART)
            }
            if (checkbox_Fashion.isChecked){
                listCheckbox.add(CHECKBOX_FASHION)
            }
            if (checkbox_Sport.isChecked){
                listCheckbox.add(CHECKBOX_SPORT)
            }
            (activity as MainActivity).filterPresenter.getFilter(listCheckbox)
            this.dismiss()
        }
    }


    private fun getSpiner() {
        val valueSpiner : ArrayList<String> = ArrayList()
        valueSpiner.add(NEWEST)
        valueSpiner.add(OLDEST)
        val adapterSpine = ArrayAdapter(context,android.R.layout.simple_spinner_item,valueSpiner)
        spiner?.adapter= adapterSpine


    }

    @SuppressLint("SetTextI18n")
    private fun getBeginDate() {
        // create an OnDateSetListener
        day_begin?.text="20180101"
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
             updateDateInView()
            }
        }
        day_begin!!.setOnClickListener {
            DatePickerDialog(
                context,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
    private fun updateDateInView() {
        val myFormat = "yyyyMMdd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        day_begin?.text = sdf.format(cal.getTime())
    }
}