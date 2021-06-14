package com.karine.common

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {

    /**
     * Displays a SnackBar in current view with provided message
     */
    fun displayMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * Converts Time in Milliseconds to Date-String
     */
    fun longToDateString(timeInMillis: Long): String {
        val dateFormat = "dd MMM yyyy hh:mm a"
        val outputDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())

        return outputDateFormat.format(Date(timeInMillis))
    }

}