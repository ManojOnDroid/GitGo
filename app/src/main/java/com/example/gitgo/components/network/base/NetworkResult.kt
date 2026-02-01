package com.example.gitgo.components.network.base


sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, errorCode: Int? = null, data: T? = null) : NetworkResult<T>(data, message, errorCode)
    class Loading<T> : NetworkResult<T>()
}