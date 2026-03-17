package com.arnyminerz.simplelauncher.data

import android.content.pm.ShortcutInfo
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.Serializable

@Serializable
data class AppShortcut(
    val id: String,
    val packageName: String
) {
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    constructor(info: ShortcutInfo): this(info.id, info.`package`)
}
