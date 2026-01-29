package com.arnyminerz.simplelauncher.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaterialSymbols.SignalCellular0Bar: ImageVector
    get() {
        if (_SignalCellular0Bar != null) {
            return _SignalCellular0Bar!!
        }
        _SignalCellular0Bar = ImageVector.Builder(
            name = "SignalCellular0Bar",
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
                moveTo(273f, 800f)
                horizontalLineToRelative(527f)
                verticalLineToRelative(-526f)
                lineTo(273f, 800f)
                close()
            }
        }.build()

        return _SignalCellular0Bar!!
    }

@Suppress("ObjectPropertyName")
private var _SignalCellular0Bar: ImageVector? = null
