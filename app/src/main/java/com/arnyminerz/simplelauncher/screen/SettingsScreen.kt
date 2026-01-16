package com.arnyminerz.simplelauncher.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnyminerz.simplelauncher.data.AppInfo
import com.arnyminerz.simplelauncher.icons.ArrowBack
import com.arnyminerz.simplelauncher.icons.ChevronRight
import com.arnyminerz.simplelauncher.icons.MaterialSymbols
import com.arnyminerz.simplelauncher.toImageBitmap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    installedApps: List<AppInfo>,
    selectedAppPackageNames: Set<String>?,
    onToggleApp: (String) -> Unit,

    columns: Int,
    onColumnsChange: (Int) -> Unit,

    password: String,
    onPasswordChange: (String) -> Unit,

    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onBack) { Icon(MaterialSymbols.ArrowBack, "Go Back") }
                },
                title = { Text("Settings") },
            )
        }
    ) { paddingValues ->
        var showingAppsSelectionDialog by remember { mutableStateOf(false) }
        if (showingAppsSelectionDialog) {
            SettingsDialog(
                title = "Select apps",
                onDismissRequest = { showingAppsSelectionDialog = false }
            ) {
                for (appInfo in installedApps) {
                    ListItem(
                        headlineContent = { Text(appInfo.appName) },
                        leadingContent = {
                            Image(
                                appInfo.launchIconProvider().toImageBitmap(),
                                null,
                                Modifier.size(32.dp)
                            )
                        },
                        trailingContent = {
                            Checkbox(
                                checked = selectedAppPackageNames.orEmpty()
                                    .contains(appInfo.packageName),
                                onCheckedChange = {
                                    onToggleApp(appInfo.packageName)
                                }
                            )
                        },
                        modifier = Modifier.clickable { onToggleApp(appInfo.packageName) }
                    )
                }
            }
        }

        var showingColumnsSelectionDialog by remember { mutableStateOf(false) }
        if (showingColumnsSelectionDialog) {
            SettingsDialog(
                title = "Select number of columns",
                onDismissRequest = { showingColumnsSelectionDialog = false }
            ) {
                for (i in 1..4) {
                    ListItem(
                        headlineContent = { Text("$i columns") },
                        trailingContent = {
                            if (columns == i)
                                Icon(MaterialSymbols.ChevronRight, null)
                        },
                        modifier = Modifier.clickable {
                            onColumnsChange(i)
                            showingColumnsSelectionDialog = false
                        }
                    )
                }
            }
        }

        var showingPasswordDialog by remember { mutableStateOf(false) }
        if (showingPasswordDialog) {
            var changedPassword by remember { mutableStateOf(password) }

            SettingsDialog(
                title = "Set password",
                onDismissRequest = { showingPasswordDialog = false },
                onConfirm = {
                    onPasswordChange(changedPassword)
                    showingPasswordDialog = false
                }
            ) {
                OutlinedTextField(
                    value = changedPassword,
                    onValueChange = {
                        changedPassword = it.takeIf { it.toIntOrNull() != null } ?: changedPassword
                    },
                    label = { Text("Password") },
                    supportingText = { Text("Must be a number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                )
            }
        }

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                ListItem(
                    headlineContent = { Text("Selected apps") },
                    supportingContent = { Text("Apps that will be displayed in the launcher") },
                    trailingContent = { Icon(MaterialSymbols.ChevronRight, null) },
                    modifier = Modifier.clickable { showingAppsSelectionDialog = true }
                )
            }
            item {
                ListItem(
                    headlineContent = { Text("Columns") },
                    supportingContent = { Text("Number of columns in the launcher") },
                    trailingContent = { Text("$columns", fontSize = 16.sp) },
                    modifier = Modifier.clickable { showingColumnsSelectionDialog = true }
                )
            }
            item {
                ListItem(
                    headlineContent = { Text("Password") },
                    supportingContent = { Text("Password required to access the settings screen") },
                    modifier = Modifier.clickable { showingPasswordDialog = true }
                )
            }
        }
    }
}

@Composable
fun SettingsDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit = onDismissRequest,
    content: @Composable ColumnScope.() -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(title) },
        text = {
            Column {
                content()
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) { Text("Confirm") }
        }
    )
}
