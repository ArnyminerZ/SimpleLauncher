package com.arnyminerz.simplelauncher.data

import android.net.Uri

class Contact(
    val id: Long,
    val name: String,
    val phone: String,
    val starred: Boolean,
    val thumb: Uri?,
)
