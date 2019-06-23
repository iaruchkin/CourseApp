package com.iaruchkin.library.DataClasses

import com.google.gson.annotations.SerializedName

data class CtxData(
        @SerializedName("age")
        val age: Int,
        @SerializedName("bloodGroup")
        val bloodGroup: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("type_list")
        val typeList: List<Select>
)