package com.iaruchkin.library

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.iaruchkin.library.DataClasses.ExampleDataClass


class ModuleFragment : Fragment() {

    private var mTextResult: TextView? = null
    private var mJsonString: String? = null
    private val JSON_FILE_NAME = "example.json"

    private var mAutoCompleteTextView: MultiAutoCompleteTextView? = null
    private val mCats = arrayOf("Мурзик", "Рыжик", "Барсик", "Борис", "Мурзилка", "Мурка")

    private var mList: ArrayList<String>? = null
    private var mAutoCompleteAdapter: ArrayAdapter<String>? = null
    private var mAutoListTextView: TextView? = null

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

        val layout = view.findViewById(R.id.linear_layout) as? LinearLayout

        mAutoCompleteTextView = MultiAutoCompleteTextView(context)
        prepareList()

        mAutoCompleteAdapter = ArrayAdapter(context,
                R.layout.select_dialog_item_material, mCats)

        mAutoCompleteTextView?.setAdapter(mAutoCompleteAdapter)
        mAutoCompleteTextView!!.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        layout?.addView(mAutoCompleteTextView)

//        Log.e("", mAutoCompleteTextView!!.performValidation())

        return view
    }

    private fun prepareList() {
        // подготовим список для автозаполнения

        for (mCat in mCats) {
            mList?.add(mCat)
        }
    }

    fun onClick(view: View) {
        val newAdd = mAutoCompleteTextView?.getText().toString()

        if (!mList!!.contains(newAdd)) {
            mList?.add(newAdd)

            // update the autocomplete words
            mAutoCompleteAdapter = ArrayAdapter(
                    context,
                    android.R.layout.simple_dropdown_item_1line, mList)

            mAutoCompleteTextView?.setAdapter(mAutoCompleteAdapter)
        }

        // display the words in mList for your reference
        var s = ""
        for (i in 0 until mList!!.size) {
            s += mList!!.get(i) + "\n"
        }
        mAutoListTextView?.setText(s)
    }
}
