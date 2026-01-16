package com.arnyminerz.simplelauncher.data

import android.content.Intent
import android.graphics.drawable.Drawable

class AppInfo(
    val appName: String,
    val packageName: String,
    val intent: Intent,
    val appIconProvider: () -> Drawable,
    val launchIconProvider: () -> Drawable,
)
