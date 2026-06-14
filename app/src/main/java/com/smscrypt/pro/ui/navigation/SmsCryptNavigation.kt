package com.smscrypt.pro.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.smscrypt.pro.ui.screens.chat.ChatScreen
import com.smscrypt.pro.ui.screens.contacts.ContactsScreen
import com.smscrypt.pro.ui.screens.home.HomeScreen
import com.smscrypt.pro.ui.screens.onboarding.OnboardingScreen
import com.smscrypt.pro.ui.screens.pin.PinScreen
import com.smscrypt.pro.ui.screens.splash.SplashScreen
import com.smscrypt.pro.ui.screens.settings.*

@Composable
fun SmsCryptNavigation(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onComplete = { appName, theme ->
                    navController.navigate(Screen.Pin.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Pin.route) {
            PinScreen(
                onPinVerified = {
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(Screen.Pin.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Splash.route) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToContacts = {
                    navController.navigate(Screen.Contacts.route)
                },
                onNavigateToChat = { phoneNumber ->
                    navController.navigate(Screen.Chat.createRoute(phoneNumber))
                }
            )
        }
        
        composable(Screen.Contacts.route) {
            ContactsScreen(
                onNavigateToChat = { phoneNumber ->
                    navController.navigate(Screen.Chat.createRoute(phoneNumber))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument("phoneNumber") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            ChatScreen(
                phoneNumber = phoneNumber,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateToLanguage = {
                    navController.navigate(Screen.Language.route)
                },
                onNavigateToStorage = {
                    navController.navigate(Screen.Storage.route)
                },
                onNavigateToInfo = {
                    navController.navigate(Screen.Info.route)
                }
            )
        }
        
        composable(Screen.Language.route) {
            LanguageSettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Storage.route) {
            MessageStorageScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Subscription.route) {
            SubscriptionPlanScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Info.route) {
            InfoScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


