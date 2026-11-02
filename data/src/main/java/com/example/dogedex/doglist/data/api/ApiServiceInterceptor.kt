package com.example.dogedex.doglist.data.api

import com.example.dogedex.auth.data.UserLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response

const val NEEDS_AUTH_HEADER_KEY = "needs_authentication"

class ApiServiceInterceptor(
    private val userLocalDataSource: UserLocalDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.header(NEEDS_AUTH_HEADER_KEY) != null) {
            val user = userLocalDataSource.cachedUser.value
            val requestBuilder = request.newBuilder().removeHeader(NEEDS_AUTH_HEADER_KEY)

            if (user != null) {
                requestBuilder.addHeader("AUTH-TOKEN", user.authenticationToken)
            }

            return chain.proceed(requestBuilder.build())
        }

        return chain.proceed(request)
    }
}
