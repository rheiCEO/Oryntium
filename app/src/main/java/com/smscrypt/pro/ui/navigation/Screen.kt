package com.smscrypt.pro.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Pin : Screen("pin")
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Contacts : Screen("contacts")
    object Chat : Screen("chat/{phoneNumber}") {
        fun createRoute(phoneNumber: String) = "chat/$phoneNumber"
    }
    object Settings : Screen("settings")
    object AppAppearance : Screen("app_appearance")
    object Language : Screen("language")
    object Storage : Screen("storage")
    object Subscription : Screen("subscription")
    object Info : Screen("info")
}


