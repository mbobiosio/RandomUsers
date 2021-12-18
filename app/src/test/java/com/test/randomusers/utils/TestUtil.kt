package com.test.randomusers.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.test.randomusers.data.model.*
import okio.buffer
import okio.source
import java.lang.reflect.Type

object TestUtil {
    fun <T> parseJsonFileToObject(fileName: String, type: Type): T? {
        val json = getJsonStringFromFile(fileName)
        val adapter: JsonAdapter<T> = Moshi.Builder().build().adapter(type)
        return adapter.fromJson(json)
    }

    fun getJsonStringFromFile(fileName: String): String {
        val inputStream = this::class.java.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream!!.source().buffer()
        return source.readString(Charsets.UTF_8)
    }

    private val name = Name("Ms", "Jessika", "Nickel")
    private val street = Street(8270, "Schützenstraße")
    private val location = Location(street = street, city = "Bretten", state = "Bayern", country = "Germany")
    private val dob = Dob("1955-09-28T09:24:00.170Z", 66)
    private val registered = Registered("2002-06-07T10:00:37.688Z", 19)
    private val picture = Picture(
        "https://randomuser.me/api/portraits/women/50.jpg",
        "https://randomuser.me/api/portraits/med/women/50.jpg", "https://randomuser.me/api/portraits/thumb/women/50.jpg"
    )

    fun createUser() = User(
        _id = 0,
        gender = "female",
        name = name,
        location = location,
        email = "jessika.nickel@example.com",
        dob = dob,
        registered = registered,
        phone = "0330-4420083",
        picture = picture
    )
}