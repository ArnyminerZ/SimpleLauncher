package com.arnyminerz.simplelauncher.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaterialSymbols.SignalCellular2Bar: ImageVector
    get() {
        if (_SignalCellular2Bar != null) {
            return _SignalCellular2Bar!!
        }
        _SignalCellular2Bar = ImageVector.Builder(
            name = "SignalCellular2Bar",
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
                moveTo(520f, 800f)
                horizontalLineToRelative(280f)
                verticalLineToRelative(-526f)
                lineTo(520f, 554f)
                verticalLineToRelative(246f)
                close()
            }
        }.build()

        return _SignalCellular2Bar!!
    }

@Suppress("ObjectPropertyName")
private var _SignalCellular2Bar: ImageVector? = null
