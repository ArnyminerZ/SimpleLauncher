package com.arnyminerz.simplelauncher.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaterialSymbols.SignalCellular3Bar: ImageVector
    get() {
        if (_SignalCellular3Bar != null) {
            return _SignalCellular3Bar!!
        }
        _SignalCellular3Bar = ImageVector.Builder(
            name = "SignalCellular3Bar",
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
                moveTo(600f, 800f)
                horizontalLineToRelative(200f)
                verticalLineToRelative(-526f)
                lineTo(600f, 474f)
                verticalLineToRelative(326f)
                close()
            }
        }.build()

        return _SignalCellular3Bar!!
    }

@Suppress("ObjectPropertyName")
private var _SignalCellular3Bar: ImageVector? = null
