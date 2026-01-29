package com.arnyminerz.simplelauncher.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaterialSymbols.SignalWifiOff: ImageVector
    get() {
        if (_SignalWifiOff != null) {
            return _SignalWifiOff!!
        }
        _SignalWifiOff = ImageVector.Builder(
            name = "SignalWifiOff",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(717f, 603f)
                lineToRelative(-57f, -57f)
                lineToRelative(184f, -184f)
                quadToRelative(-79f, -60f, -172f, -91f)
                reflectiveQuadToRelative(-192f, -31f)
                quadToRelative(-29f, 0f, -58f, 3f)
                reflectiveQuadToRelative(-58f, 8f)
                lineToRelative(-66f, -66f)
                quadToRelative(45f, -12f, 90f, -18.5f)
                reflectiveQuadToRelative(92f, -6.5f)
                quadToRelative(136f, 0f, 260.5f, 51.5f)
                reflectiveQuadTo(960f, 360f)
                lineTo(717f, 603f)
                close()
                moveTo(480f, 726f)
                lineToRelative(67f, -66f)
                lineToRelative(-350f, -350f)
                quadToRelative(-21f, 11f, -41f, 24.5f)
                reflectiveQuadTo(116f, 362f)
                lineToRelative(364f, 364f)
                close()
                moveTo(819f, 932f)
                lineTo(604f, 716f)
                lineTo(480f, 840f)
                lineTo(0f, 360f)
                quadToRelative(32f, -32f, 66.5f, -59f)
                reflectiveQuadToRelative(72.5f, -49f)
                lineTo(27f, 140f)
                lineToRelative(57f, -57f)
                lineTo(876f, 875f)
                lineToRelative(-57f, 57f)
                close()
                moveTo(512f, 398f)
                close()
                moveTo(372f, 485f)
                close()
            }
        }.build()

        return _SignalWifiOff!!
    }

@Suppress("ObjectPropertyName")
private var _SignalWifiOff: ImageVector? = null
