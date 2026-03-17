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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun AddressBookScreen(
    contacts: List<Contact>,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    BackHandler(onBack = onBack)

    Scaffold(containerColor = Color.Transparent) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = stringResource(R.string.address_book_title),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(contacts) { contact ->
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
                icon = MaterialSymbols.Close,
                text = stringResource(R.string.close),
                color = Color(0xffdc2b2b),
                onClick = onBack
            )
        }
    }
}
