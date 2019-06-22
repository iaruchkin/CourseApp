package com.iaruchkin.library.exampleDTOs


import com.google.gson.annotations.SerializedName

class Example {

    @SerializedName("Id")
    var id: Int? = null

//    @SerializedName("PersonalDetails")
//    var personalDetails: PersonalDetails? = null

    @SerializedName("PersonalDetails")
    var personalDetails: Map<String, Any>? = null

}