package com.test.randomusers.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import com.test.randomusers.utils.Utils.convertDateToRegisteredFormat
import com.test.randomusers.utils.toSentenceCase
import kotlinx.android.parcel.Parcelize

data class UserResponse(val results: List<User> = ArrayList(), val info: InfoResponse? = null)

@JsonClass(generateAdapter = true)
@Parcelize
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val gender: String? = null,
    val name: Name? = null,
    val location: Location? = null,
    val email: String? = null,
    val login: Login? = null,
    val dob: Dob? = null,
    val registered: Registered? = null,
    val phone: String? = null,
    val cell: String? = null,
    val picture: Picture? = null,
    val nat: String? = null
) : Parcelable {

    @Ignore
    val fullName = "${name?.first} ${name?.last}"

    @Ignore
    val fullNameWithTitle = "${name?.title}. ${name?.first} ${name?.last}"

    @Ignore
    val shortLocation = "${location?.city}, ${location?.country}"

    @Ignore
    val genderAndAge = "${gender?.toSentenceCase()} | ${dob?.dob}"
}

/**
 * @JsonClass(generateAdapter = true) is an annotation processor for Moshi’s Kotlin codegen support
 * It generates an adapter to be used when saving [DataObject] in the DB
 */

@JsonClass(generateAdapter = true)
@Parcelize
data class Name(val title: String? = null, val first: String? = null, val last: String? = null) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Location(
    val street: Street? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val timezone: TimeZone? = null
) : Parcelable {

    @Ignore
    val fullLocation = "${street?.street}, $city, $state, $country"
}

@JsonClass(generateAdapter = true)
@Parcelize
data class Street(val number: Int = 0, val name: String? = null) : Parcelable {
    @Ignore
    val street = "$number, $name"
}

@JsonClass(generateAdapter = true)
@Parcelize
data class TimeZone(val offset: String? = null, val description: String? = null) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Login(val username: String? = null) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Dob(val date: String? = null, val age: Int = 0) : Parcelable {
    @Ignore
    val dob = if (age <= 1) "$age yr" else "$age yrs"
}

@JsonClass(generateAdapter = true)
@Parcelize
data class Registered(val date: String? = null, val age: Int = 0) : Parcelable {
    @Ignore
    val userSince = "${date?.let { convertDateToRegisteredFormat(it) }} (${if (age <= 1) "$age yr" else "$age yrs"})"
}

@JsonClass(generateAdapter = true)
@Parcelize
data class Picture(val large: String? = null, val medium: String? = null, val thumbnail: String? = null) : Parcelable

data class InfoResponse(val seed: String? = null, val results: Int = 0, val page: Int = 0)