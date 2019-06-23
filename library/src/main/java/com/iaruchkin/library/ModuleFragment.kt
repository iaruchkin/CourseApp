package com.iaruchkin.library

import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import android.widget.CheckBox
import android.widget.LinearLayout
import com.iaruchkin.library.exampleDTOs.Example


class ModuleFragment : Fragment(), View.OnClickListener {
    var linearLayout: LinearLayout? = null

    private var mTextResult: TextView? = null
    private var mJsonString: String? = null
    private val JSON_FILE_NAME = "example.json"

    private fun loadJson() {
        object : AsyncTask<Void, Void, Void>() {

            override fun onPreExecute() {
                super.onPreExecute()
            }

            override fun doInBackground(vararg params: Void): Void? {
                mJsonString = Utils.loadJsonFromAsset(context, JSON_FILE_NAME)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                Toast.makeText(context, "JSON load successfully", Toast.LENGTH_SHORT).show()
            }
        }.execute()
    }

    private fun parseJson() {
        if (mJsonString != null) {
            val gson = Gson()
            val employee = gson.fromJson<Any>(mJsonString, Example::class.java) as Example
//            val mapData = employee.mapData?.mapData

//            val resultBuilder = StringBuilder()
//            resultBuilder.append("Employee Details:")
//            resultBuilder.append("\n")
//            resultBuilder.append("Id: " + employee.id)
//            resultBuilder.append("\n")
//            resultBuilder.append("Name: " + mapData?.get("name"))
//            resultBuilder.append("\n")
//            resultBuilder.append("Age: " + mapData?.get("age"))
//            resultBuilder.append("\n")
//            resultBuilder.append("blood Group: " + mapData?.get("bloodGroup"))
//
//            mTextResult!!.text = resultBuilder.toString()

        } else {
            Toast.makeText(context, "Please load JSON", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_load_json -> loadJson()

            R.id.btn_parse_json -> parseJson()
        }
    }

    private fun addCheckBoxes(v: View) {

        val layout = v.findViewById(R.id.linear_layout) as LinearLayout
        val checkBoxLayout = LinearLayout(context)
        checkBoxLayout.orientation = LinearLayout.VERTICAL

        layout.addView(checkBoxLayout)

        for (i in 1..3) {
            val checkBox = CheckBox(context)
            checkBox.text = "CheckBox $i"
            checkBoxLayout.addView(checkBox)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.module_fragment, container, false)
//        linearLayout = view?.findViewById(R.id.linear_layout)
//        val layout = view.findViewById(R.id.linear_layout) as LinearLayout

        mTextResult = view.findViewById<View>(R.id.item_text) as TextView
        view.findViewById<View>(R.id.btn_load_json).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_parse_json).setOnClickListener(this)

        addCheckBoxes(view)

        return view
    }
}
