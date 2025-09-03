package com.example.gitgo.application.di.interceptor

import com.example.gitgo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = BuildConfig.GITHUB_TOKEN
        val requestBuilder = chain.request().newBuilder()
            .addHeader("User-Agent", "GitGoApp")
        if (token.isNotBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}