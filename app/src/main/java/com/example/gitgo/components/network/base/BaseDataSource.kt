package com.example.gitgo.components.network.base

import android.util.Log
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseDataSource {

    protected suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return NetworkResult.Success(body)
                }
            }

            val code = response.code()
            // 1. Try to parse specific server message
            var errorMessage = parseError(response)

            // 2. If parsing failed or returned generic text, use friendly hardcoded messages
            if (errorMessage.isBlank() || errorMessage.equals("Not Found", ignoreCase = true)) {
                errorMessage = when (code) {
                    401 -> "Unauthorized. Please check your token."
                    403 -> "Access Forbidden. Rate limit exceeded."
                    404 -> "Resource not found. It may have been deleted."
                    500 -> "Server Error. Please try again later."
                    else -> "Something went wrong ($code)"
                }
            }

            return NetworkResult.Error(errorMessage, code)

        } catch (e: Exception) {
            return when (e) {
                is SocketTimeoutException -> NetworkResult.Error("Timeout: Slow internet connection.", 408)
                is UnknownHostException -> NetworkResult.Error("No Internet Connection", -1)
                is IOException -> NetworkResult.Error("Network Failure", -2)
                else -> {
                    Log.e("BaseDataSource", "Unknown Error: ${e.localizedMessage}")
                    NetworkResult.Error(e.message ?: "Unknown Error")
                }
            }
        }
    }

    private fun <T> parseError(response: Response<T>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            if (errorBody.isNullOrEmpty()) return "" // Return empty to trigger fallback above

            val jsonObject = JSONObject(errorBody)
            when {
                jsonObject.has("message") -> jsonObject.getString("message")
                jsonObject.has("errors") -> jsonObject.getJSONArray("errors").getJSONObject(0).getString("message")
                else -> ""
            }
        } catch (e: Exception) {
            "" // Return empty to trigger fallback above
        }
    }
}