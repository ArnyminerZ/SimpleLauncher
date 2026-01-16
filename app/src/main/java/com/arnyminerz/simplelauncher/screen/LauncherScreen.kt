package com.arnyminerz.simplelauncher.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnyminerz.simplelauncher.data.AppInfo
import com.arnyminerz.simplelauncher.icons.Call
import com.arnyminerz.simplelauncher.icons.CrisisAlert
import com.arnyminerz.simplelauncher.icons.MaterialSymbols
import com.arnyminerz.simplelauncher.toImageBitmap
import com.arnyminerz.simplelauncher.ui.ActionRow

@Composable
fun LauncherScreen(
    apps: List<AppInfo>?,
    columns: Int,
    onCallRequest: () -> Unit,
    onLaunchApp: (AppInfo) -> Unit
) {
    val appsColumns = apps?.chunked(columns)

    Scaffold(containerColor = Color.Transparent) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                if (appsColumns != null) {
                    for (row in appsColumns) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            for (app in row) {
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(8.dp)
                                        .clickable { onLaunchApp(app) },
                                    color = LocalContentColor.current.copy(alpha = .3f),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(
                                            bitmap = app.launchIconProvider().toImageBitmap(),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .aspectRatio(1f)
                                                .fillMaxWidth()
                                        )
                                        Text(
                                            text = app.appName,
                                            modifier = Modifier.padding(top = 12.dp),
                                            textAlign = TextAlign.Center,
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                        )
                                    }
                                }
                            }
                            row.size.until(columns).forEach { _ ->
                                // Empty space for missing apps in the row
                                Box(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                } else {
                    Text("Loading apps...")
                }
            }

            ActionRow(
                icon = MaterialSymbols.Call,
                text = "Call",
                color = Color(0xff26ba26),
                onClick = onCallRequest,
            )
            ActionRow(
                icon = MaterialSymbols.CrisisAlert,
                text = "Emergency",
                color = Color(0xffdc2b2b),
                onClick = { /* TODO */ }
            )
        }
    }
}
