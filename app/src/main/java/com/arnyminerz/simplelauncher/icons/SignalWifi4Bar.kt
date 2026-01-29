package com.arnyminerz.simplelauncher.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaterialSymbols.SignalWifi4Bar: ImageVector
    get() {
        if (_SignalWifi4Bar != null) {
            return _SignalWifi4Bar!!
        }
        _SignalWifi4Bar = ImageVector.Builder(
            name = "SignalWifi4Bar",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(480f, 840f)
                lineTo(0f, 360f)
                quadToRelative(95f, -97f, 219.5f, -148.5f)
                reflectiveQuadTo(480f, 160f)
                quadToRelative(136f, 0f, 260.5f, 51.5f)
                reflectiveQuadTo(960f, 360f)
                lineTo(480f, 840f)
                close()
            }
        }.build()

        return _SignalWifi4Bar!!
    }

@Suppress("ObjectPropertyName")
private var _SignalWifi4Bar: ImageVector? = null
