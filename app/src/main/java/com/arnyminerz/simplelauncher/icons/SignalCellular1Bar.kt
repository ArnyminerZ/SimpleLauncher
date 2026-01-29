package com.arnyminerz.simplelauncher.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaterialSymbols.SignalCellular1Bar: ImageVector
    get() {
        if (_SignalCellular1Bar != null) {
            return _SignalCellular1Bar!!
        }
        _SignalCellular1Bar = ImageVector.Builder(
            name = "SignalCellular1Bar",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(80f, 880f)
                lineToRelative(800f, -800f)
                verticalLineToRelative(800f)
                lineTo(80f, 880f)
                close()
                moveTo(400f, 800f)
                horizontalLineToRelative(400f)
                verticalLineToRelative(-526f)
                lineTo(400f, 674f)
                verticalLineToRelative(126f)
                close()
            }
        }.build()

        return _SignalCellular1Bar!!
    }

@Suppress("ObjectPropertyName")
private var _SignalCellular1Bar: ImageVector? = null
