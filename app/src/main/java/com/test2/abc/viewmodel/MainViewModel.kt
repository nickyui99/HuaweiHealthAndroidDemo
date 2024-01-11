package com.test2.abc.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test2.abc.Constants
import com.test2.abc.event.BusEvent
import com.test2.abc.utils.MyHttpClient
import com.test2.abc.utils.Preferences
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.IOException

class MainViewModel : ViewModel(){

    private val TAG = MainViewModel::class.java.simpleName

    fun unauthorizeHealthKit(context: Context) {
        viewModelScope.launch {
            try {
                val headers = mapOf(
                    "Content-Type" to "application/json",
                    "Authorization" to "Bearer ${Preferences.getString(context, Constants.SP_ACCESS_TOKEN)}",
                    "x-client-id" to Constants.APP_ID.toString(),
                    "x-version" to "1.0.0",
                )

                val response = MyHttpClient().delete(
                    Constants.HUAWEI_UNAUTHORIZE_URL,
                    emptyMap(),
                    headers
                )

                Log.d(TAG, "Response: " + response.code + response.body)

                if(response.isSuccessful) {
                    Preferences.clearPref(context, Constants.SP_ACCESS_TOKEN)
                    EventBus.getDefault().post(BusEvent.UnauthorizeHealthKitEvent(true))
                } else {
                    EventBus.getDefault().post(BusEvent.UnauthorizeHealthKitEvent(false, response.message))
                }


            } catch (err: IOException) {
                // Handle the exception
                err.printStackTrace()
                EventBus.getDefault().post(BusEvent.UnauthorizeHealthKitEvent(false, err.message))
            }
        }
    }
}