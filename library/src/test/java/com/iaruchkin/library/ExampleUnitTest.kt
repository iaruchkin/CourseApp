package com.iaruchkin.library

import com.iaruchkin.library.exampleDTOs.Example
import com.iaruchkin.library.exampleDTOs.MapData
import com.iaruchkin.library.exampleDTOs.Select
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {
    lateinit var state: Example
    lateinit var mapData: MapData
    lateinit var list: List<Select>

    @Before
    fun init(){
        val json: String = Utils.loadJson("./src/test/java/com/iaruchkin/library/example.json")

        state = Example(json)
        mapData = MapData(state)
        list = mapData.ctxList!!

//        print((state.address))
    }

    @Test
    fun id_isCorrect() {
        assertEquals(11111, state.id)
    }

    @Test
    fun name_isCorrect() {
        assertEquals("Employee1", state.mapData?.get("name").toString())
    }

    @Test
    fun age_isCorrect() {
        assertEquals(30.0, state.mapData?.get("age"))
    }

    @Test
    fun bloodGroup_isCorrect() {
        assertEquals("B+", state.mapData?.get("bloodGroup"))
    }

    @Test
    fun list_isCorrect() {
        println((state.mapData?.get("type_list")))

        assertEquals("323", (list.get(0).value))
        assertEquals(3, (list.size))
    }

    @Test
    fun mapping_isCorrect() {
        println((state.mapData?.get("type_list")))
    }

}