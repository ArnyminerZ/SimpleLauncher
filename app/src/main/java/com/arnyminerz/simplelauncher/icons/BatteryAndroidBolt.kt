package com.arnyminerz.simplelauncher.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaterialSymbols.BatteryAndroidBolt: ImageVector
    get() {
        if (_BatteryAndroidBolt != null) {
            return _BatteryAndroidBolt!!
        }
        _BatteryAndroidBolt = ImageVector.Builder(
            name = "BatteryAndroidBolt",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(160f, 720f)
                quadToRelative(-50f, 0f, -85f, -35f)
                reflectiveQuadToRelative(-35f, -85f)
                verticalLineToRelative(-240f)
                quadToRelative(0f, -50f, 35f, -85f)
                reflectiveQuadToRelative(85f, -35f)
                horizontalLineToRelative(479f)
                quadToRelative(20f, 0f, 30f, 12.5f)
                reflectiveQuadToRelative(10f, 27.5f)
                quadToRelative(0f, 15f, -10f, 27.5f)
                reflectiveQuadTo(639f, 320f)
                lineTo(160f, 320f)
                quadToRelative(-17f, 0f, -28.5f, 11.5f)
                reflectiveQuadTo(120f, 360f)
                verticalLineToRelative(240f)
                quadToRelative(0f, 17f, 11.5f, 28.5f)
                reflectiveQuadTo(160f, 640f)
                horizontalLineToRelative(425f)
                quadToRelative(20f, 0f, 30f, 12.5f)
                reflectiveQuadToRelative(10f, 27.5f)
                quadToRelative(0f, 15f, -10f, 27.5f)
                reflectiveQuadTo(585f, 720f)
                lineTo(160f, 720f)
                close()
                moveTo(120f, 640f)
                verticalLineToRelative(-320f)
                verticalLineToRelative(320f)
                close()
                moveTo(735f, 520f)
                horizontalLineToRelative(-93f)
                quadToRelative(-13f, 0f, -18.5f, -11f)
                reflectiveQuadToRelative(2.5f, -21f)
                lineToRelative(168f, -211f)
                quadToRelative(5f, -6f, 12.5f, -3f)
                reflectiveQuadToRelative(5.5f, 11f)
                lineToRelative(-27f, 155f)
                horizontalLineToRelative(93f)
                quadToRelative(13f, 0f, 18.5f, 11f)
                reflectiveQuadToRelative(-2.5f, 21f)
                lineTo(726f, 683f)
                quadToRelative(-5f, 6f, -12.5f, 3f)
                reflectiveQuadToRelative(-5.5f, -11f)
                lineToRelative(27f, -155f)
                close()
            }
        }.build()

        return _BatteryAndroidBolt!!
    }

@Suppress("ObjectPropertyName")
private var _BatteryAndroidBolt: ImageVector? = null
