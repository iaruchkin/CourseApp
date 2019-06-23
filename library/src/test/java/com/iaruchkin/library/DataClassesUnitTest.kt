package com.iaruchkin.library

import com.iaruchkin.library.DataClasses.ExampleDataClass
import com.iaruchkin.library.DataClasses.Select
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class DataClassesUnitTest {
    lateinit var state: ExampleDataClass
    lateinit var list: List<Select>

    @Before
    fun init(){
        val json: String = Utils.loadJson("./src/test/java/com/iaruchkin/library/example.json")

        state = ExampleDataClass.getState(json)
        list = ExampleDataClass.getCtxList(state)

        print((state.address))
        println((state.ctxData?.get("type_list")))
    }

    @Test
    fun id_isCorrect() {
        assertEquals(11111, state.id)
    }

    @Test
    fun name_isCorrect() {
        assertEquals("Employee1", state.ctxData?.get("name").toString())
    }

    @Test
    fun age_isCorrect() {
        assertEquals(30.0, state.ctxData?.get("age"))
    }

    @Test
    fun bloodGroup_isCorrect() {
        assertEquals("B+", state.ctxData?.get("bloodGroup"))
    }

    @Test
    fun list_isCorrect() {
        assertEquals("323", (list.get(0).value))
        assertEquals(3, (list.size))

    }
}