package com.iaruchkin.library

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.iaruchkin.library.DataClasses.ExampleDataClass


class ModuleFragment : Fragment() {
    
    private var mTextResult: TextView? = null
    private var mJsonString: String? = null
    private val JSON_FILE_NAME = "example.json"

    private fun parseJson(v: View) {
        mJsonString = Utils.loadJsonFromAsset(context, JSON_FILE_NAME)
        if (mJsonString != null) {
            val employee = ExampleDataClass.getState(mJsonString)

            val resultBuilder = StringBuilder()
            resultBuilder.append("Employee Details:")
            resultBuilder.append("\n")
            resultBuilder.append("Id: " + employee.id)
            resultBuilder.append("\n")

            mTextResult!!.text = resultBuilder.toString()
            addCheckBoxes(v, employee)

        } else {
            Toast.makeText(context, "Please load JSON", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addCheckBoxes(v: View, employee: ExampleDataClass) {

        val mapData = ExampleDataClass.getCtxList(employee)
        mapData.forEach {
            it.addCheckBox(v, context)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.module_fragment, container, false)
        mTextResult = view.findViewById<View>(R.id.item_text) as TextView
        parseJson(view)

        return view
    }
}
