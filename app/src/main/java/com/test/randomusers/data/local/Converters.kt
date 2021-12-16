package com.test.randomusers.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.test.randomusers.data.model.*

class Converters {

    @TypeConverter
    fun fromUsers(users: List<User>): String {
        val listType = Types.newParameterizedType(List::class.java, User::class.java)
        val adapter: JsonAdapter<List<User>> = Moshi.Builder().build().adapter(listType)
        return adapter.toJson(users)
    }

    @TypeConverter
    fun toUsers(users: String): List<User> {
        val listType = Types.newParameterizedType(List::class.java, User::class.java)
        val adapter: JsonAdapter<List<User>> = Moshi.Builder().build().adapter(listType)
        return adapter.fromJson(users)!!
    }

    @TypeConverter
    fun fromName(name: Name): String {
        return Moshi.Builder().build().adapter(Name::class.java).toJson(name)
    }

    @TypeConverter
    fun toName(string: String): Name {
        return Moshi.Builder().build().adapter(Name::class.java).fromJson(string)!!
    }

    @TypeConverter
    fun fromLocation(location: Location): String {
        return Moshi.Builder().build().adapter(Location::class.java).toJson(location)
    }

    @TypeConverter
    fun toLocation(string: String): Location {
        return Moshi.Builder().build().adapter(Location::class.java).fromJson(string)!!
    }

    @TypeConverter
    fun fromLogin(login: Login): String {
        return Moshi.Builder().build().adapter(Login::class.java).toJson(login)
    }

    @TypeConverter
    fun toLogin(string: String): Login {
        return Moshi.Builder().build().adapter(Login::class.java).fromJson(string)!!
    }

    @TypeConverter
    fun fromDob(dob: Dob): String {
        return Moshi.Builder().build().adapter(Dob::class.java).toJson(dob)
    }

    @TypeConverter
    fun toDob(string: String): Dob {
        return Moshi.Builder().build().adapter(Dob::class.java).fromJson(string)!!
    }

    @TypeConverter
    fun fromRegistered(registered: Registered): String {
        return Moshi.Builder().build().adapter(Registered::class.java).toJson(registered)
    }

    @TypeConverter
    fun toRegistered(string: String): Registered {
        return Moshi.Builder().build().adapter(Registered::class.java).fromJson(string)!!
    }

    @TypeConverter
    fun fromId(id: Id): String {
        return Moshi.Builder().build().adapter(Id::class.java).toJson(id)
    }

    @TypeConverter
    fun toId(string: String): Id {
        return Moshi.Builder().build().adapter(Id::class.java).fromJson(string)!!
    }

    @TypeConverter
    fun fromPicture(picture: Picture): String {
        return Moshi.Builder().build().adapter(Picture::class.java).toJson(picture)
    }

    @TypeConverter
    fun toPicture(string: String): Picture {
        return Moshi.Builder().build().adapter(Picture::class.java).fromJson(string)!!
    }
}