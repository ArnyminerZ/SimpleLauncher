package com.arnyminerz.simplelauncher.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaterialSymbols.SignalCellular4Bar: ImageVector
    get() {
        if (_SignalCellular4Bar != null) {
            return _SignalCellular4Bar!!
        }
        _SignalCellular4Bar = ImageVector.Builder(
            name = "SignalCellular4Bar",
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
            }
        }.build()

        return _SignalCellular4Bar!!
    }

@Suppress("ObjectPropertyName")
private var _SignalCellular4Bar: ImageVector? = null
