package com.test.randomusers.data.networkresource

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.test.randomusers.utils.AppConstants.CONNECT_EXCEPTION
import com.test.randomusers.utils.AppConstants.SOCKET_TIME_OUT_EXCEPTION
import com.test.randomusers.utils.AppConstants.SSL_EXCEPTION
import com.test.randomusers.utils.AppConstants.UNKNOWN_HOST_EXCEPTION
import com.test.randomusers.utils.AppConstants.UNKNOWN_NETWORK_EXCEPTION
import org.json.JSONException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkStatus<T> {
    try {
        val response = call.invoke()
        if (response.isSuccessful) {
            if (response.body() != null) {
                return NetworkStatus.Success(response.body())
            }
        }
        return NetworkStatus.Error(response.message())
    } catch (e: Exception) {
        return when (e) {
            is ConnectException -> {
                NetworkStatus.Error(CONNECT_EXCEPTION)
            }
            is UnknownHostException -> {
                NetworkStatus.Error(UNKNOWN_HOST_EXCEPTION)
            }
            is SocketTimeoutException -> {
                NetworkStatus.Error(SOCKET_TIME_OUT_EXCEPTION)
            }
            is HttpException -> {
                try {
                    val type = Types.newParameterizedType(ApiResponse::class.java)
                    val adapter: JsonAdapter<ApiResponse<Response<T>>> =
                        Moshi.Builder().build().adapter(type)
                    val errorMessage = adapter.fromJson(e.response()?.errorBody()?.string()!!)

                    NetworkStatus.Error(errorMessage?.message)
                } catch (err: JSONException) {
                    NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
                } catch (err: IOException) {
                    NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
                } catch (err: SSLException) {
                    NetworkStatus.Error(SSL_EXCEPTION)
                }
            }
            else -> {
                NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
            }
        }
    }
}