package com.iaruchkin.library.exampleDTOs

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapData (state: Example){

    private var ctxMap: Map<String, Any>? = state.mapData

    val ctxList: List<Select> = Gson().fromJson(ctxMap?.get(state?.address).toString(), object : TypeToken<List<Select>>() {}.type)

}