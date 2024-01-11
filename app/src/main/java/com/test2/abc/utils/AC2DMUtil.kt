package com.test2.abc.utils

import java.util.StringTokenizer
import java.util.regex.Pattern

object AC2DMUtil {
    fun parseResponse(response: String?): Map<String, String> {
        val keyValueMap: MutableMap<String, String> = HashMap()
        val st = StringTokenizer(response, "\n\r")
        while (st.hasMoreTokens()) {
            val keyValue = st.nextToken().split("=".toRegex(), limit = 2).toTypedArray()
            if (keyValue.size >= 2) {
                keyValueMap[keyValue[0]] = keyValue[1]
            }
        }
        return keyValueMap
    }

    fun parseCookieString(cookies: String?): Map<String, String> {
        val cookieList: MutableMap<String, String> = HashMap()
        val cookiePattern = Pattern.compile("([^=]+)=([^;]*);?\\s?")
        val matcher = cookiePattern.matcher(cookies)
        while (matcher.find()) {
            val cookieKey = matcher.group(1)
            val cookieValue = matcher.group(2)
            cookieList[cookieKey] = cookieValue
        }
        return cookieList
    }
}
