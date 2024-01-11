package com.test2.abc.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MyHttpClient {
    private val client = OkHttpClient()

    @Throws(IOException::class)
    suspend fun get(
        url: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): String = withContext(Dispatchers.IO) {
        try {
            val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()

            if (params.isNotEmpty()) {
                for ((key, value) in params) {
                    urlBuilder?.addQueryParameter(key, value)
                }
            }

            val request = Request.Builder()
                .url(urlBuilder?.build().toString())

            if(headers.isNotEmpty()) {
                for ((key, value) in headers) {
                    request.addHeader(key, value)
                }
            }

            val response: Response = client.newCall(request.build()).execute()

            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }

            response.body?.string() ?: throw IOException("Response body is null")
        } catch (e: Exception) {
            // Handle any exceptions and log the error
            e.printStackTrace()
            throw IOException("Error during network operation: ${e.message}")
        }
    }

    suspend fun delete(
        url: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Response = withContext(Dispatchers.IO) {
        try {
            val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()

            if (params.isNotEmpty()) {
                for ((key, value) in params) {
                    urlBuilder?.addQueryParameter(key, value)
                }
            }

            val requestBuilder = Request.Builder()
                .url(urlBuilder?.build().toString())
                .delete()

            if (headers.isNotEmpty()) {
                for ((key, value) in headers) {
                    requestBuilder.addHeader(key, value)
                }
            }

            val request = requestBuilder.build()

            val response: Response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }

            response // Return the Response object directly
        } catch (e: Exception) {
            // Handle any exceptions and log the error
            e.printStackTrace()
            throw IOException("Error during network operation: ${e.message}")
        }
    }


}
