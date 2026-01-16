package com.arnyminerz.simplelauncher.nav

sealed interface Destination {
    object Launcher : Destination
    object Call : Destination
    object Settings : Destination
}
