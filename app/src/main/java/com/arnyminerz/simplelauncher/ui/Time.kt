package com.arnyminerz.simplelauncher.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Remembers the current time as a [State] that updates every [refreshTime] milliseconds.
 * @param refreshTime The time in milliseconds between each update. Default is `100 ms`.
 * @param format The format of the time string. Default is `"HH:mm"`.
 * @param locale The locale to use for formatting the time. Default is the device's default locale.
 * @return A [State] containing the current time as a formatted string.
 */
@Composable
fun rememberCurrentTime(
    refreshTime: Long = 100,
    format: String = "HH:mm",
    locale: Locale = Locale.getDefault()
): State<String> {
    val time = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val currentTime = SimpleDateFormat(format, locale)
                .format(Date())

            if (currentTime != time.value) {
                time.value = currentTime
            }

            delay(refreshTime)
        }
    }

    return time
}
