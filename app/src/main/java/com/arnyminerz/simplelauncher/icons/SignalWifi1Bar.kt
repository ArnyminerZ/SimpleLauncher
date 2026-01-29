package com.arnyminerz.simplelauncher.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaterialSymbols.SignalWifi1Bar: ImageVector
    get() {
        if (_SignalWifi1Bar != null) {
            return _SignalWifi1Bar!!
        }
        _SignalWifi1Bar = ImageVector.Builder(
            name = "NetworkWifi1Bar",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(480f, 840f)
                lineTo(0f, 360f)
                quadToRelative(96f, -98f, 220f, -149f)
                reflectiveQuadToRelative(260f, -51f)
                quadToRelative(137f, 0f, 261f, 51f)
                reflectiveQuadToRelative(219f, 149f)
                lineTo(480f, 840f)
                close()
                moveTo(361f, 607f)
                quadToRelative(25f, -18f, 55.5f, -28f)
                reflectiveQuadToRelative(63.5f, -10f)
                quadToRelative(33f, 0f, 63.5f, 10f)
                reflectiveQuadToRelative(55.5f, 28f)
                lineToRelative(245f, -245f)
                quadToRelative(-78f, -59f, -170.5f, -90.5f)
                reflectiveQuadTo(480f, 240f)
                quadToRelative(-101f, 0f, -193.5f, 31.5f)
                reflectiveQuadTo(116f, 362f)
                lineToRelative(245f, 245f)
                close()
            }
        }.build()

        return _SignalWifi1Bar!!
    }

@Suppress("ObjectPropertyName")
private var _SignalWifi1Bar: ImageVector? = null
