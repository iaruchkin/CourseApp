package com.iaruchkin.library.exampleDTOs

import com.google.gson.annotations.SerializedName

class Select {

    @SerializedName("value")
    var value: String? = null

    @SerializedName("caption")
    var caption: String? = null

}