package com.arnyminerz.simplelauncher.data

import android.net.Uri
import com.arnyminerz.simplelauncher.serialization.UriSerializer
import kotlinx.serialization.Serializable

@Serializable
class Contact(
    val id: Long,
    val name: String,
    val phone: String,
    val starred: Boolean,
    @Serializable(UriSerializer::class) val thumb: Uri?,
)
