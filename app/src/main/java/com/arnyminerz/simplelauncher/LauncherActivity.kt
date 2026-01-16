package com.arnyminerz.simplelauncher

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.arnyminerz.simplelauncher.data.AppInfo
import com.arnyminerz.simplelauncher.data.Contact
import com.arnyminerz.simplelauncher.nav.Destination
import com.arnyminerz.simplelauncher.screen.CallScreen
import com.arnyminerz.simplelauncher.screen.LauncherScreen
import com.arnyminerz.simplelauncher.screen.SettingsScreen
import com.arnyminerz.simplelauncher.storage.SettingsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LauncherActivity : AppCompatActivity() {
    private val model by viewModels<Model>()

    private val runtimePermissions = listOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_CONTACTS,
    )

    private val permissionRequestLauncher = registerForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { model.loadContacts() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(FLAG_SHOW_WALLPAPER)

        setContent {
            MaterialTheme {
                val backStack = remember { mutableStateListOf<Destination>(Destination.Launcher) }

                val contacts by model.contacts.collectAsState(emptyList())
                val installedApps by model.allApps.collectAsState(emptyList())
                val selectedAppPackageNames by model.selectedAppPackageNames.collectAsState(null)
                val selectedApps by model.selectedApps.collectAsState(null)
                val columns by model.columns.collectAsState(2)
                val password by model.password.collectAsState(null)

                LaunchedEffect(selectedApps) {
                    val list = selectedApps
                    if (list != null && list.isEmpty()) {
                        // No apps selected, show selector to user
                        backStack.add(Destination.Settings)
                    }
                }
                LaunchedEffect(password) {
                    if (password == "") {
                        // No password set, go to settings
                        backStack.add(Destination.Settings)
                    }
                }

                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = { key ->
                        when (key) {
                            is Destination.Launcher -> {
                                NavEntry(key) {
                                    LauncherScreen(
                                        selectedApps,
                                        columns,
                                        onCallRequest = {
                                            backStack.add(Destination.Call)
                                        },
                                        onLaunchApp = {
                                            startActivity(it.intent)
                                        }
                                    )
                                }
                            }

                            is Destination.Call -> {
                                NavEntry(key) {
                                    CallScreen(
                                        contacts = contacts,
                                        password = password.orEmpty(),
                                        onSettingsRequest = {
                                            backStack.add(Destination.Settings)
                                        },
                                        onBack = { backStack.removeLastOrNull() }
                                    )
                                }
                            }

                            is Destination.Settings -> {
                                NavEntry(key) {
                                    SettingsScreen(
                                        installedApps = installedApps,
                                        selectedAppPackageNames = selectedAppPackageNames,
                                        onToggleApp = { model.toggleSelectedApp(it) },
                                        columns = columns,
                                        onColumnsChange = { model.setColumns(it) },
                                        password = password.orEmpty(),
                                        onPasswordChange = { model.setPassword(it) },
                                        onBack = { backStack.removeLastOrNull() }
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestRuntimePermissions()
        model.loadContacts()
    }

    private fun requestRuntimePermissions() {
        val permissionsToRequest = runtimePermissions.filter { !isPermissionGranted(this, it) }
        if (permissionsToRequest.isNotEmpty()) {
            permissionRequestLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    class Model(application: Application) : AndroidViewModel(application) {
        private val context = getApplication<Application>()

        private val settingsManager = SettingsManager(context)

        /**
         * A flow that emits the settings entry for selected app package names.
         * Will be `null` until loaded. By default it's an empty set.
         * @see SettingsManager.selectedAppsFlow
         */
        val selectedAppPackageNames = settingsManager.selectedAppsFlow
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

        val columns = settingsManager.columnsFlow
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 2)

        val password = settingsManager.passwordFlow
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

        val allApps: Flow<List<AppInfo>> = flow {
            val apps = getInstalledApps()
                .sortedBy { it.appName }
            emit(apps)
        }

        private val _contacts = MutableStateFlow(emptyList<Contact>())
        val contacts: StateFlow<List<Contact>> get() = _contacts.asStateFlow()

        val selectedApps = combine(selectedAppPackageNames, allApps) { packageNames, apps ->
            if (packageNames == null) {
                null
            } else if (packageNames.isEmpty()) {
                emptyList()
            } else {
                apps.filter { it.packageName in packageNames }
            }
        }

        fun loadContacts() {
            viewModelScope.launch {
                _contacts.emit(getContacts().sortedBy { it.name })
            }
        }

        fun toggleSelectedApp(packageName: String) {
            val current = selectedAppPackageNames.value.orEmpty()
            val new = current.toMutableSet()
            if (new.contains(packageName))
                new.remove(packageName)
            else
                new.add(packageName)
            viewModelScope.launch {
                settingsManager.setSelectedApps(new)
            }
        }

        fun setColumns(columns: Int) {
            viewModelScope.launch {
                settingsManager.setColumns(columns)
            }
        }

        fun setPassword(password: String) {
            viewModelScope.launch {
                settingsManager.setPassword(password)
            }
        }

        fun getInstalledApps(): List<AppInfo> {
            val pm = context.packageManager
            return pm.getInstalledApplications(PackageManager.GET_META_DATA)
                .mapNotNull { applicationInfo ->
                    val appName = applicationInfo.loadLabel(pm).toString()
                    val packageName = applicationInfo.packageName
                    val intent: Intent? = pm.getLaunchIntentForPackage(packageName)
                    intent ?: return@mapNotNull null
                    AppInfo(
                        appName = appName,
                        packageName = packageName,
                        intent = intent,
                        appIconProvider = { applicationInfo.loadIcon(pm) },
                        launchIconProvider = { pm.getActivityIcon(intent) },
                    )
                }
        }

        fun getContacts(): List<Contact> {
            if (!isPermissionGranted(context, Manifest.permission.READ_CONTACTS)) {
                return emptyList()
            }

            val cursor = context.contentResolver.query(
                Phone.CONTENT_URI,
                null, null, null, null
            )
            val contacts = mutableListOf<Contact>()

            cursor?.use {
                val idIndex = it.getColumnIndex(Phone._ID)
                val nameIndex = it.getColumnIndex(Phone.DISPLAY_NAME)
                val numberIndex = it.getColumnIndex(Phone.NUMBER)
                val thumbIndex = it.getColumnIndex(Phone.PHOTO_THUMBNAIL_URI)
                val starredIndex = it.getColumnIndex(Phone.STARRED)

                println("Cursor has ${it.count} contacts")

                while (it.moveToNext()) {
                    val id = it.getLong(idIndex)
                    val name = it.getString(nameIndex)
                    val number = it.getString(numberIndex)
                    val thumb = if (thumbIndex != -1) it.getString(thumbIndex) else null
                    val starred = it.getInt(starredIndex) == 1

                    contacts += Contact(id, name, number, starred, thumb?.toUri())
                }
            }

            println("Loaded ${contacts.size} contacts: ${contacts.joinToString { it.name }}")

            return contacts.toList()
        }
    }

    companion object {
        fun isPermissionGranted(context: Context, permission: String): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}
