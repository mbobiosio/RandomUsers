package com.test.randomusers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    @JvmStatic
    fun hasInternetConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    @JvmStatic
    private fun defaultServerDateFormat(): SimpleDateFormat {
        //Creating a SimpleDateFormat in api pattern
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

        //Set the timezone to the api timezone
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat
    }

    @JvmStatic
    fun convertDateToRegisteredFormat(date: String): String {
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        return dateFormat.format(defaultServerDateFormat().parse(date)!!)
    }
}