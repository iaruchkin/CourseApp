package com.iaruchkin.library

import com.google.gson.GsonBuilder
import com.iaruchkin.library.CustomDeseralization.DateTypeAdapter
import com.iaruchkin.library.CustomDeseralization.EnumType
import com.iaruchkin.library.CustomDeseralization.UserDate
import com.iaruchkin.library.exampleDTOs.MapData
import com.iaruchkin.library.exampleDTOs.Example
import com.iaruchkin.library.exampleDTOs.Select
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class CustomAdapterUnitTest {
    lateinit var userDate: UserDate

    @Before
    fun init(){
        val json: String = Utils.loadJson("./src/test/java/com/iaruchkin/library/custom.json")

        val gson = GsonBuilder().registerTypeAdapter(UserDate::class.java,  DateTypeAdapter()).create()
        userDate = gson.fromJson(json, UserDate::class.java)
    }

    @Test
    fun id_isCorrect() {
        assertEquals(true, userDate.check)
    }

    @Test
    fun enum_isCorrect() {
        assertEquals(EnumType.M, userDate.enum)
    }

}