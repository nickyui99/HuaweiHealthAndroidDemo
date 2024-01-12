package com.test2.abc.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.test2.abc.Constants
import com.test2.abc.model.HeartRate
import com.test2.abc.utils.DateTimeUtils
import com.test2.abc.utils.MyHttpClient
import com.test2.abc.utils.Preferences
import com.test2.abc.view.HeartRateFragment
import kotlinx.coroutines.launch
import java.io.IOException

class HeartRateViewModel : ViewModel() {

    private val TAG = HeartRateFragment::class.java.simpleName

    private val _intantaneousHRData = MutableLiveData<HeartRate?>()
    val intantaneousHRData: LiveData<HeartRate?>
        get() = _intantaneousHRData


    //Currently insufficient permissions need to apply through huawei developer health kit
    fun createDataCollector(context: Context, collectorDataType: String) {
        viewModelScope.launch {
            try {
                val headers = mapOf(
                    "Content-Type" to "application/json; charset=UTF-8",
                    "Authorization" to "Bearer ${
                        Preferences.getString(
                            context,
                            Constants.SP_ACCESS_TOKEN
                        )
                    }"
                )

                val body = mapOf(
                    "collectorType" to "raw",
                    "appInfo" to listOf(
                        mapOf(
                            "appPackageName" to "com.test2.abc"
                        )
                    ),
                    "collectorDataType" to listOf(
                        mapOf(
                            "name" to collectorDataType
                        )
                    )
                )

                val response = MyHttpClient().post(
                    Constants.HUAWEI_DATA_COLLECTOR_URL,
                    body,
                    headers
                )

                Log.d(TAG, response.toString())
            } catch (err: IOException) {
                // Handle the exception
                err.printStackTrace()
            }
        }
    }

    fun fetchInstantaneousHeartData(context: Context) {
        viewModelScope.launch {
            var heartRate: HeartRate? = null

            try {
                val headers = mapOf(
                    "Content-Type" to "application/json; charset=UTF-8",
                    "Authorization" to "Bearer ${
                        Preferences.getString(
                            context,
                            Constants.SP_ACCESS_TOKEN
                        )
                    }"
                )

                val body = mapOf(
                    "startTime" to DateTimeUtils.getTimestamp(DateTimeUtils.get7DaysBeforeToday()),
                    "endTime" to DateTimeUtils.getTimestamp(DateTimeUtils.getToday()),
                    "polymerizeWith" to listOf(
                        mapOf("dataTypeName" to Constants.HUAWEI_DT_INSTANTANEOUS_HEART_RATE)
                    )
                )

                val response = MyHttpClient().post(Constants.HUAWEI_POLYMERIZE_SAMPLE_URL, body, headers)

                if (response.isSuccessful) {
                    val responseBody = response.body?.string() ?: ""
                    Log.d(TAG, responseBody)

                    // Create a Gson instance
                    val gson = Gson()

                    responseBody.let {
                        // Parse JSON string to HeartRate object
                        heartRate = gson.fromJson(it, HeartRate::class.java)

                        _intantaneousHRData.value= heartRate
                    }
                } else {
                    // Handle unsuccessful response (e.g., server error)
                    Log.e(TAG, "Unsuccessful response: ${response.code}")
                }

            } catch (err: IOException) {
                // Handle the exception
                err.printStackTrace()
                // Handle network-related errors
                // viewModel.handleNetworkError()
            } catch (e: Exception) {
                // Handle other unexpected errors
                e.printStackTrace()
            }
        }
    }

    fun fetchContinuousHeartData(context: Context) {
        viewModelScope.launch {
            try {
                val headers = mapOf(
                    "Content-Type" to "application/json; charset=UTF-8",
                    "Authorization" to "Bearer ${
                        Preferences.getString(
                            context,
                            Constants.SP_ACCESS_TOKEN
                        )
                    }"
                )

                val body = mapOf(
                    "startDay" to DateTimeUtils.formatCalendarToyyyyMMdd(DateTimeUtils.get7DaysBeforeToday()),
                    "endDay" to DateTimeUtils.formatCalendarToyyyyMMdd(DateTimeUtils.getToday()),
                    "dataTypes" to listOf(
                        Constants.HUAWEI_DT_CONTINUOUS_HEART_RATE
                    ),
                    "timeZone" to "+0800"
                )

                val response = MyHttpClient().post(
                    Constants.HUAWEI_DAILY_POLYMERIZE_SAMPLE_URL,
                    body,
                    headers
                )

                Log.d(TAG, response.toString())
            } catch (err: IOException) {
                // Handle the exception
                err.printStackTrace()
            }
        }
    }
}