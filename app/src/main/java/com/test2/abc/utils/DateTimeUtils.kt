package com.test2.abc.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeUtils {
    fun getTimestamp(calendar: Calendar): Long {
        // Get the current date and time
        val dateTime = calendar.time

        // Get the timestamp in milliseconds
        return dateTime.time
    }

    fun dateToTimestamp(dateString: String): Long {
        val pattern = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(pattern)
        val date = dateFormat.parse(dateString)

        // Check for null, although it's unlikely to be null with a valid date string
        return date?.time ?: 0L
    }

    fun get7DaysBeforeToday(): Calendar {
        // Create a Calendar object
        val calendar = Calendar.getInstance()

        // Subtract 7 days from the current date
        calendar.add(Calendar.DAY_OF_MONTH, -7)

        return calendar
    }

    fun getToday() : Calendar{
        return Calendar.getInstance()
    }

    fun formatCalendarToDateString(calendar: Calendar, datePattern: String): String {
        // Create a SimpleDateFormat with the desired format
        val sdf = SimpleDateFormat(datePattern)

        // Format the Calendar's time to a string
        return sdf.format(calendar.time)
    }
    fun convertMsTimestampToDateTime(timestamp: Long): String {
        try {
            // Convert nanoseconds to milliseconds
            val timestampInMilliseconds = timestamp / 1_000_000

            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val date = Date(timestampInMilliseconds)
            return dateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return "Invalid Date"
        }
    }

}