package com.iaruchkin.library

import com.google.gson.Gson
import com.iaruchkin.library.exampleDTOs.Example
import com.iaruchkin.library.exampleDTOs.Select
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import com.google.gson.reflect.TypeToken



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class SecondTest {
    lateinit var json: String
    lateinit var employee: Example
    lateinit var list: List<Select>

    @Before
    fun init(){
        json = Utils.loadJson("./src/test/java/com/iaruchkin/library/example2.json")
        employee = Gson().fromJson(json, Example::class.java) as Example

//        print(Gson().toJson(state.mapData.toString()))
//        print((employee.mapData?.get("type_list")))

        val itemsListType = object : TypeToken<List<Select>>() {}.type
        list = Gson().fromJson(employee.mapData?.get(employee.address).toString(), itemsListType)
    }

    @Test
    fun id_isCorrect() {
        assertEquals(11111, employee.id)
    }

    @Test
    fun name_isCorrect() {
        assertEquals("Employee1", employee.mapData?.get("name").toString())
    }

    @Test
    fun age_isCorrect() {
        assertEquals(30.0, employee.mapData?.get("age"))
    }

    @Test
    fun bloodGroup_isCorrect() {
        assertEquals("B+", employee.mapData?.get("bloodGroup"))
    }

    @Test
    fun list_isCorrect() {
//        assertEquals("worker", (state.mapData?.get("type_list") as List<*>?)?.get(0)?.toString())
//        assertEquals("323", (state.mapData?.get("type_list") as List<Select>?)?.get(0)?.value)
        assertEquals("323", (list.get(0).value))

    }

    @Test
    fun list2_isCorrect() {
//        assertEquals("worker", (state.mapData?.get("type_list") as List<*>?)?.get(0)?.toString())
//        assertEquals("323", (state.mapData?.get("type_list") as List<Select>?)?.get(0)?.value)
        assertEquals("worker2", (list.get(1).caption))

    }
}