package com.iaruchkin.library.DataClasses


import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import com.google.gson.annotations.SerializedName
import com.iaruchkin.library.R

data class Select(
        @SerializedName("caption")
        val caption: String,
        @SerializedName("value")
        val value: String
) {
        fun addCheckBox(v: View, context: Context?) {

                val layout = v.findViewById(R.id.linear_layout) as? LinearLayout
                val checkBoxLayout = LinearLayout(context)
                checkBoxLayout.orientation = LinearLayout.VERTICAL

                layout?.addView(checkBoxLayout)

                val checkBox = CheckBox(context)
                checkBox.text = "CheckBox $caption"
                checkBoxLayout.addView(checkBox)
        }
}