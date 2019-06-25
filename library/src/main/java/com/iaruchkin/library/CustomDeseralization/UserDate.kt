package com.iaruchkin.library.CustomDeseralization


import com.google.gson.annotations.SerializedName
import java.util.*

data class UserDate(

        @SerializedName("name")
        val name: String,

        @SerializedName("email")
        val email: String,

        @SerializedName("isDeveloper")
        val isDeveloper: Boolean,

        @SerializedName("age")
        val age: Int,

        @SerializedName("check")
        val check: Boolean,

        @SerializedName("enum")
        val enum: EnumType,

        val registerDate: Date
)