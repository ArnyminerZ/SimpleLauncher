package com.arnyminerz.simplelauncher.data

import android.content.Context
import android.content.Intent
import kotlinx.serialization.Serializable

@Serializable
class AppInfoSimple(
    val appName: String,
    val packageName: String,
) {
    /**
     * Converts this [AppInfoSimple] into a full [AppInfo] object.
     * @param context The context to use to access the package manager.
     * @return The converted [AppInfo] object, or `null` if the launch intent could not be found.
     */
    fun toAppInfo(context: Context): AppInfo? {
        val pm = context.packageManager
        val intent: Intent = pm.getLaunchIntentForPackage(packageName) ?: return null

        return AppInfo(
            appName = appName,
            packageName = packageName,
            intent = intent,
            appIconProvider = { pm.getApplicationIcon(packageName) },
            launchIconProvider = { pm.getActivityIcon(intent) },
        )
    }
}
