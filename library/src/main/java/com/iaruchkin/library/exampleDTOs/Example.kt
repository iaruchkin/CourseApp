package com.iaruchkin.library.exampleDTOs

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class Example (json: String?){
    var state: Example = Gson().fromJson(json, Example::class.java)

    @SerializedName("Id")
    var id: Int? = state.id

    @SerializedName("address")
    var address: String? = state.address

    @SerializedName("MapData")
    var mapData: Map<String, Any> = state.mapData
}