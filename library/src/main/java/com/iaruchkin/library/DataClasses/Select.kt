package com.iaruchkin.library.DataClasses


import com.google.gson.annotations.SerializedName

data class Select(
        @SerializedName("caption")
        val caption: String,
        @SerializedName("value")
        val value: String
)