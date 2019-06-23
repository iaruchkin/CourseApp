package com.iaruchkin.library.DataClasses

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class ExampleDataClass(
        @SerializedName("address")
        val address: String,
        @SerializedName("MapData")
        val ctxData: Map<String, Any>,
        @SerializedName("Id")
        val id: Int
) {
        companion object {
                fun getState(json: String?): ExampleDataClass {
                        return Gson().fromJson(json, ExampleDataClass::class.java)
                }

                fun getCtxList(dynamic: String?): List<Select> {
                        return Gson().fromJson(dynamic, object : TypeToken<List<Select>>() {}.type)
                }

                fun getCtxList(state: ExampleDataClass?): List<Select> {
                        return Gson().fromJson(state?.ctxData?.get(state?.address).toString(), object : TypeToken<List<Select>>() {}.type)
                }
        }
}