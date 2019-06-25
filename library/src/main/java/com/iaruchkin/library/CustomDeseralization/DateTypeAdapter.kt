package com.iaruchkin.library.CustomDeseralization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.util.*

class DateTypeAdapter : JsonDeserializer<UserDate>{
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): UserDate {
        val jsonObject = json.asJsonObject
        val date = Date(
            jsonObject["year"].asInt,
            jsonObject["month"].asInt,
            jsonObject["day"].asInt
        )

        val asInt = jsonObject["check"].asInt
        val check = asInt != 0
        val enum = when(jsonObject["enum"].asString){
            "M"-> EnumType.M
            "D"-> EnumType.D
            else -> EnumType.M
        }


        return UserDate(
                jsonObject["name"].asString,
                jsonObject["email"].asString,
                jsonObject["isDeveloper"].asBoolean,
                jsonObject["age"].asInt,
                check,
                enum,
                date
        )
    }
}