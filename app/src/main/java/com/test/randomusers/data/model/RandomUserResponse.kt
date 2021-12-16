package com.test.randomusers.data.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

data class UserResponse(val results: List<User> = ArrayList(), val info: InfoResponse? = null)

@Parcelize
@Entity(tableName = "user")
data class User(
    val gender: String? = null,
    val name: Name? = null,
    val location: Location? = null,
    val email: String? = null,
    val login: Login? = null,
    val dob: Dob? = null,
    val registered: Registered? = null,
    val phone: String? = null,
    val cell: String? = null,
    val id: Id? = null,
    val picture: Picture? = null,
    val nat: String? = null
) : Parcelable {

    val fullName = "${name?.first} ${name?.last}"
    val fullNameWithTitle = "${name?.title}. ${name?.first} ${name?.last}"
    val shortLocation = "${location?.city}, ${location?.country}"
}

@Parcelize
data class Name(val title: String? = null, val first: String? = null, val last: String? = null) : Parcelable

@Parcelize
data class Location(
    val street: Street? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val timezone: TimeZone? = null
) : Parcelable

@Parcelize
data class Street(val number: Int = 0, val name: String? = null) : Parcelable

@Parcelize
data class TimeZone(val offset: String? = null, val description: String? = null) : Parcelable

@Parcelize
data class Login(val username: String? = null) : Parcelable

@Parcelize
data class Dob(val date: String? = null, val age: Int = 0) : Parcelable

@Parcelize
data class Registered(val date: String? = null, val age: Int = 0) : Parcelable

@Parcelize
data class Id(val name: String? = null, val value: String? = null) : Parcelable

@Parcelize
data class Picture(val large: String? = null, val medium: String? = null, val thumbnail: String? = null) : Parcelable

data class InfoResponse(val seed: String? = null, val results: Int = 0, val page: Int = 0)