package com.vosouza.appfilmes.data.network.intercepor

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", apiKey)
            .url(request.url)
            .build()
        return chain.proceed(newRequest)
    }
}