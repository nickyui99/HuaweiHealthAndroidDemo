package com.test2.abc.utils

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log

object Preferences {

    private val TAG = Preferences::class.java.simpleName

    fun getPrefs(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    fun putString(context: Context, key: String, value: String) {
        getPrefs(context).edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String, default: String = ""): String {
        return getPrefs(context).getString(key, default).toString()
    }

    fun clearPref(context: Context, key: String) {
        try {
            Log.d(TAG, "Clear shared prefs: $key")
            getPrefs(context).edit().remove(key).apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing shared preferences: ${e.message}")
        }
    }
}
