package com.arnyminerz.simplelauncher.screen

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.arnyminerz.simplelauncher.R
import com.arnyminerz.simplelauncher.data.AppInfo
import com.arnyminerz.simplelauncher.data.Contact
import com.arnyminerz.simplelauncher.icons.Call
import com.arnyminerz.simplelauncher.icons.Close
import com.arnyminerz.simplelauncher.icons.ContactPhone
import com.arnyminerz.simplelauncher.icons.CrisisAlert
import com.arnyminerz.simplelauncher.icons.Face
import com.arnyminerz.simplelauncher.icons.MaterialSymbols
import com.arnyminerz.simplelauncher.toImageBitmap
import com.arnyminerz.simplelauncher.ui.ActionRow
import com.arnyminerz.simplelauncher.ui.BigButton

@Composable
fun CallScreen(
    contacts: List<Contact>,
    password: String,
    onSettingsRequest: () -> Unit,
    onAddressBookRequest: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var phone by remember { mutableStateOf("") }

    BackHandler(onBack = onBack)

    Scaffold(containerColor = Color.Transparent) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = phone.takeIf { it.isNotBlank() } ?: " ",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = LocalContentColor.current,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    NumberButton("7") { phone += it }
                    NumberButton("8") { phone += it }
                    NumberButton("9") { phone += it }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    NumberButton("4") { phone += it }
                    NumberButton("5") { phone += it }
                    NumberButton("6") { phone += it }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    NumberButton("1") { phone += it }
                    NumberButton("2") { phone += it }
                    NumberButton("3") { phone += it }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    NumberButton("+") { phone += it }
                    NumberButton("0") { phone += it }
                    NumberButton("<") { phone = phone.dropLast(1) }
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BigButton(
                        icon = MaterialSymbols.Call,
                        color = Color(0xff26ba26),
                        tintColor = Color.White,
                        enabled = phone.isNotBlank(),
                    ) {
                        if (phone == "+$password") onSettingsRequest()
                        else {
                            val intent = Intent(Intent.ACTION_CALL)
                                .setData("tel:$phone".toUri())
                            context.startActivity(intent)
                        }
                    }
                }

                val contact = if (phone.length < 3) null
                else contacts.firstOrNull { it.phone.contains(phone) }
                if (contact != null) {
                    ActionRow(
                        icon = MaterialSymbols.Face,
                        text = contact.name,
                        color = Color(0xffb18f0e),
                    ) {
                        val intent = Intent(Intent.ACTION_CALL)
                            .setData("tel:${contact.phone}".toUri())
                        context.startActivity(intent)
                    }
                }
            }

            ActionRow(
                icon = MaterialSymbols.ContactPhone,
                text = stringResource(R.string.address_book_title),
                color = Color(0xff4285f4),
                onClick = onAddressBookRequest,
            )

            ActionRow(
                icon = MaterialSymbols.Close,
                text = stringResource(R.string.close),
                color = Color(0xffdc2b2b),
                onClick = onBack
            )
        }
    }
}

@Composable
fun RowScope.NumberButton(number: String, onPhoneChange: (String) -> Unit) {
    BigButton(
        number,
        Modifier
            .weight(1f)
            .padding(horizontal = 8.dp)
    ) { onPhoneChange(number) }
}
