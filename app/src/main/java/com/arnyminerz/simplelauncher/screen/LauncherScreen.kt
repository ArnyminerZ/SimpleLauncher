package com.arnyminerz.simplelauncher.screen

import android.os.BatteryManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.arnyminerz.simplelauncher.data.AppInfo
import com.arnyminerz.simplelauncher.icons.BatteryAndroidBolt
import com.arnyminerz.simplelauncher.icons.BatteryAndroidFrame0
import com.arnyminerz.simplelauncher.icons.BatteryAndroidFrame1
import com.arnyminerz.simplelauncher.icons.BatteryAndroidFrame2
import com.arnyminerz.simplelauncher.icons.BatteryAndroidFrame3
import com.arnyminerz.simplelauncher.icons.BatteryAndroidFrame4
import com.arnyminerz.simplelauncher.icons.BatteryAndroidFrame5
import com.arnyminerz.simplelauncher.icons.BatteryAndroidFrameBolt
import com.arnyminerz.simplelauncher.icons.BatteryAndroidFrameFull
import com.arnyminerz.simplelauncher.icons.Call
import com.arnyminerz.simplelauncher.icons.CrisisAlert
import com.arnyminerz.simplelauncher.icons.MaterialSymbols
import com.arnyminerz.simplelauncher.toImageBitmap
import com.arnyminerz.simplelauncher.ui.ActionRow
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LauncherScreen(
    apps: List<AppInfo>?,
    columns: Int,
    onCallRequest: () -> Unit,
    onLaunchApp: (AppInfo) -> Unit
) {
    val appsColumns = apps?.chunked(columns)

    var time by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        while (true) {
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(Date())

            if (currentTime != time) {
                time = currentTime
            }

            delay(100)
        }
    }

    Scaffold(containerColor = Color.Transparent) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = time,
                    fontSize = 96.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                BatteryIndicator()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
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

@Composable
fun BatteryIndicator(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val batteryManager = remember(context) {
        ContextCompat.getSystemService(context, BatteryManager::class.java)
    } ?: return

    var isCharging by remember { mutableStateOf(false) }
    var batteryLevel by remember { mutableIntStateOf(0) }

    LaunchedEffect(batteryManager) {
        while (true) {
            isCharging = batteryManager.isCharging
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

            delay(30_000) // Update every 30 seconds
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            when {
                isCharging -> if (batteryLevel > 50) MaterialSymbols.BatteryAndroidFrameBolt else MaterialSymbols.BatteryAndroidBolt
                batteryLevel >= 90 -> MaterialSymbols.BatteryAndroidFrameFull
                batteryLevel >= 80 -> MaterialSymbols.BatteryAndroidFrame5
                batteryLevel >= 60 -> MaterialSymbols.BatteryAndroidFrame4
                batteryLevel >= 40 -> MaterialSymbols.BatteryAndroidFrame3
                batteryLevel >= 20 -> MaterialSymbols.BatteryAndroidFrame2
                batteryLevel >= 10 -> MaterialSymbols.BatteryAndroidFrame1
                else -> MaterialSymbols.BatteryAndroidFrame0
            },
            null,
            tint = when {
                isCharging -> Color(0xff26ba26)
                batteryLevel > 20 -> Color.White
                else -> Color(0xffdc2b2b)
            }
        )
        Text(
            text = "$batteryLevel%",
            modifier = Modifier.padding(start = 4.dp),
            color = Color.White
        )
    }
}
