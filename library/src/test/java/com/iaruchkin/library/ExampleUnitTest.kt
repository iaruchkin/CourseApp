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
class ExampleUnitTest {
    lateinit var json: String
    lateinit var employee: Example
    lateinit var list: List<Select>

    @Before
    fun init(){
        json = Utils.loadJson("./src/test/java/com/iaruchkin/library/example.json")
        employee = Gson().fromJson(json, Example::class.java) as Example

//        print(Gson().toJson(employee.personalDetails.toString()))
        print((employee.personalDetails?.get("type_list")))

        val itemsListType = object : TypeToken<List<Select>>() {}.type
        list = Gson().fromJson(employee.personalDetails?.get("type_list").toString(), itemsListType)
    }

    @Test
    fun id_isCorrect() {
        assertEquals(11111, employee.id)
    }

    @Test
    fun name_isCorrect() {
        assertEquals("Employee1", employee.personalDetails?.get("name").toString())
    }

    @Test
    fun age_isCorrect() {
        assertEquals(30.0, employee.personalDetails?.get("age"))
    }

    @Test
    fun bloodGroup_isCorrect() {
        assertEquals("B+", employee.personalDetails?.get("bloodGroup"))
    }

    @Test
    fun list_isCorrect() {
//        assertEquals("worker", (employee.personalDetails?.get("type_list") as List<*>?)?.get(0)?.toString())
//        assertEquals("323", (employee.personalDetails?.get("type_list") as List<Select>?)?.get(0)?.value)
        assertEquals("323", (list.get(0).value))

    }
}