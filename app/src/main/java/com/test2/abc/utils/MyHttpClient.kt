package com.test2.abc.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MyHttpClient {
    private val TAG = MyHttpClient::class.java.simpleName

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

    suspend fun post(
        url: String,
        body: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap()
    ): Response = withContext(Dispatchers.IO) {
        try {
            val jsonString = convertMapToJsonString(body)
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = jsonString.toRequestBody(mediaType)

            val requestBuilder = Request.Builder()
                .url(url)
                .post(requestBody)

            if (headers.isNotEmpty()) {
                for ((key, value) in headers) {
                    Log.d(TAG, "$key : $value")
                    requestBuilder.addHeader(key, value.toString())
                }
            }

            val request = requestBuilder.build()

            // Log the details of the request
            Log.d(TAG, "Request URL: ${request.url}")
            Log.d(TAG, "Request Method: ${request.method}")
            Log.d(TAG, "Request Headers: ${request.headers}")

            // Log the request body parameters individually

                Log.d(TAG, "Request Body Parameter ${jsonString}")


            val response: Response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                // Handle error response
                throw IOException("Unexpected code ${response.code} ${response.message}")
            }

            response // Return the Response object directly
        } catch (e: IOException) {
            // Handle network-related errors
            Log.e(TAG, "Error during network operation: ${e.message}")
            throw e
        } catch (e: RuntimeException) {
            // Handle unexpected issues
            Log.e(TAG, "Unexpected error: ${e.message}")
            throw e
        }
    }

    suspend fun delete(
        url: String,
        params: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap()
    ): Response = withContext(Dispatchers.IO) {
        try {
            val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()

            if (params.isNotEmpty()) {
                for ((key, value) in params) {
                    urlBuilder?.addQueryParameter(key, value.toString())
                }
            }

            val requestBuilder = Request.Builder()
                .url(urlBuilder?.build().toString())
                .delete()

            if (headers.isNotEmpty()) {
                for ((key, value) in headers) {
                    requestBuilder.addHeader(key, value.toString())
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
    fun convertMapToJsonString(map: Map<*, *>): String {
        val jsonObject = JSONObject(map)
        return jsonObject.toString()
    }
}
