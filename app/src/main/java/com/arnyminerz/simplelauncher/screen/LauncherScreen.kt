package com.arnyminerz.simplelauncher.screen

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.arnyminerz.simplelauncher.R
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
import com.arnyminerz.simplelauncher.icons.SignalCellular0Bar
import com.arnyminerz.simplelauncher.icons.SignalCellular1Bar
import com.arnyminerz.simplelauncher.icons.SignalCellular2Bar
import com.arnyminerz.simplelauncher.icons.SignalCellular3Bar
import com.arnyminerz.simplelauncher.icons.SignalCellular4Bar
import com.arnyminerz.simplelauncher.icons.SignalWifi0Bar
import com.arnyminerz.simplelauncher.icons.SignalWifi1Bar
import com.arnyminerz.simplelauncher.icons.SignalWifi2Bar
import com.arnyminerz.simplelauncher.icons.SignalWifi3Bar
import com.arnyminerz.simplelauncher.icons.SignalWifi4Bar
import com.arnyminerz.simplelauncher.icons.SignalWifiOff
import com.arnyminerz.simplelauncher.toImageBitmap
import com.arnyminerz.simplelauncher.ui.ActionRow
import com.arnyminerz.simplelauncher.ui.rememberCurrentTime
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.sign

@Composable
fun LauncherScreen(
    apps: List<AppInfo>?,
    columns: Int,
    onCallRequest: () -> Unit,
    onLaunchApp: (AppInfo) -> Unit
) {
    val appsColumns = apps?.chunked(columns)

    val time by rememberCurrentTime()

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
                Column {
                    BatteryIndicator()
                    Row {
                        CellularIndicator()
                        Spacer(Modifier.width(4.dp))
                        WifiIndicator()
                    }
                }
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
                    Text(stringResource(R.string.apps_loading))
                }
            }

            ActionRow(
                icon = MaterialSymbols.Call,
                text = stringResource(R.string.call),
                color = Color(0xff26ba26),
                onClick = onCallRequest,
            )
            ActionRow(
                icon = MaterialSymbols.CrisisAlert,
                text = stringResource(R.string.emergency),
                color = Color(0xffdc2b2b),
                onClick = { /* TODO */ }
            )
        }
    }
}

@Composable
fun BatteryIndicator(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val resources = LocalResources.current
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
        modifier = modifier.semantics {
            text = AnnotatedString(
                if (isCharging)
                    resources.getString(R.string.battery_charging, batteryLevel)
                else
                    resources.getString(R.string.battery_discharging, batteryLevel)
            )
        },
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
            },
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = "$batteryLevel%",
            modifier = Modifier.padding(start = 4.dp),
            color = Color.White
        )
    }
}

@Composable
fun SignalIndicator(
    transportType: Int,
    icon0Bar: ImageVector,
    icon1Bar: ImageVector,
    icon2Bar: ImageVector,
    icon3Bar: ImageVector,
    icon4Bar: ImageVector,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val connectivityManager = remember(context) {
        ContextCompat.getSystemService(context, ConnectivityManager::class.java)
    } ?: return
    val wifiManager = remember(context) {
        ContextCompat.getSystemService(context, WifiManager::class.java)
    }

    // 0-4 signal bars, -1 = no connection, -2 = adapter off
    var signalBars by remember { mutableIntStateOf(-2) }

    DisposableEffect(connectivityManager) {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                wifiManager ?: return

                val rssi = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val transportInfo = networkCapabilities.transportInfo as? WifiInfo ?: return
                    transportInfo.rssi
                } else {
                    @Suppress("DEPRECATION")
                    val wifiInfo = wifiManager.connectionInfo ?: return
                    wifiInfo.rssi
                }

                signalBars = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    wifiManager.calculateSignalLevel(rssi)
                } else {
                    when (rssi) {
                        in -100..-80 -> 0
                        in -79..-70 -> 1
                        in -69..-60 -> 2
                        in -59..-50 -> 3
                        in -49..0 -> 4
                        else -> 0
                    }
                }
            }

            override fun onLost(network: Network) {
                signalBars = -1
            }
        }
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(transportType)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    // Do not display indicator if adapter is off
    if (signalBars <= -2) return

    Icon(
        when (signalBars) {
            -1 -> icon0Bar
            0 -> icon0Bar
            1 -> icon1Bar
            2 -> icon2Bar
            3 -> icon3Bar
            4 -> icon4Bar
            else -> icon4Bar
        },
        null,
        tint = Color.White,
        modifier = Modifier.size(24.dp).then(modifier),
    )
}

@Composable
fun WifiIndicator(modifier: Modifier = Modifier) {
    SignalIndicator(
        transportType = NetworkCapabilities.TRANSPORT_WIFI,
        icon0Bar = MaterialSymbols.SignalWifi0Bar,
        icon1Bar = MaterialSymbols.SignalWifi1Bar,
        icon2Bar = MaterialSymbols.SignalWifi2Bar,
        icon3Bar = MaterialSymbols.SignalWifi3Bar,
        icon4Bar = MaterialSymbols.SignalWifi4Bar,
        modifier = modifier,
    )
}

@Composable
fun CellularIndicator(modifier: Modifier = Modifier) {
    SignalIndicator(
        transportType = NetworkCapabilities.TRANSPORT_CELLULAR,
        icon0Bar = MaterialSymbols.SignalCellular0Bar,
        icon1Bar = MaterialSymbols.SignalCellular1Bar,
        icon2Bar = MaterialSymbols.SignalCellular2Bar,
        icon3Bar = MaterialSymbols.SignalCellular3Bar,
        icon4Bar = MaterialSymbols.SignalCellular4Bar,
        modifier = modifier,
    )
}
