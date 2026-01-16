package com.arnyminerz.simplelauncher

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap

fun Drawable.toImageBitmap() = this.toBitmap().asImageBitmap()
