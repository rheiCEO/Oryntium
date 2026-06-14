package com.smscrypt.pro

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.CompositionLocalProvider
import com.smscrypt.pro.data.database.AppDatabase
import com.smscrypt.pro.data.preferences.PinManager
import com.smscrypt.pro.data.preferences.LanguageManager
import com.smscrypt.pro.data.preferences.ThemeManager
import kotlinx.coroutines.flow.first
import com.smscrypt.pro.ui.navigation.Screen
import com.smscrypt.pro.ui.navigation.SmsCryptNavigation
import com.smscrypt.pro.ui.theme.SmsCryptProTheme
import com.smscrypt.pro.utils.ScreenshotProtection
import com.smscrypt.pro.utils.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun attachBaseContext(newBase: Context) {
        // Ustaw wybrany język
        val languageCode = LanguageManager.getLanguageSync(newBase)
        val context = LocaleHelper.setLocale(newBase, languageCode)
        super.attachBaseContext(context)
    }
    
    @Inject
    lateinit var database: AppDatabase
    
    @Inject
    lateinit var pinManager: PinManager
    
    @Inject
    lateinit var themeManager: ThemeManager
    
    private var backgroundTimestamp: Long = 0L
    private var requiresPinCheck = mutableStateOf(false)
    
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle permissions result
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable screenshot protection
        ScreenshotProtection.enableScreenshotProtection(this)
        
        // Start directly with PIN screen (no onboarding)
        setContent {
            SmsCryptProTheme {
                MainScreen(
                    startDestination = Screen.Pin.route,
                    requiresPinCheck = requiresPinCheck
                )
            }
        }
        
        // Request SMS permissions if not granted
        checkAndRequestPermissions()
    }
    
    override fun onPause() {
        super.onPause()
        backgroundTimestamp = System.currentTimeMillis()
    }
    
    override fun onResume() {
        super.onResume()
        ScreenshotProtection.enableScreenshotProtection(this)
        
        // Check if app was in background for more than 20 seconds
        if (backgroundTimestamp > 0) {
            val timeInBackground = System.currentTimeMillis() - backgroundTimestamp
            if (timeInBackground > 20_000) { // 20 seconds
                requiresPinCheck.value = true
            }
        }
    }
    
    private fun checkAndRequestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE
        )
        
        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        
        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}

@Composable
fun MainScreen(
    startDestination: String,
    requiresPinCheck: MutableState<Boolean> = mutableStateOf(false)
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    // Define screens that should show bottom navigation (all except PIN)
    val showBottomBar = currentDestination?.route != Screen.Pin.route
    
    // Inactivity timer - 20 seconds bez aktywności = blokada
    val lastInteractionTime = remember { mutableLongStateOf(System.currentTimeMillis()) }
    
    // Timer sprawdzający bezczynność co sekundę
    LaunchedEffect(currentDestination?.route, requiresPinCheck.value) {
        // Timer działa tylko gdy:
        // 1. Nie jesteśmy na ekranie PIN
        // 2. PIN NIE jest aktualnie wymagany (nie ma już blokady)
        if (currentDestination?.route != Screen.Pin.route && !requiresPinCheck.value) {
            while (!requiresPinCheck.value) {
                kotlinx.coroutines.delay(1000L) // Sprawdzaj co sekundę
                val inactiveTime = System.currentTimeMillis() - lastInteractionTime.longValue
                if (inactiveTime > 20_000L) { // 20 sekund bezczynności
                    requiresPinCheck.value = true
                    break // ZATRZYMAJ timer po włączeniu blokady
                }
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(requiresPinCheck.value) { // Restartuj gdy zmienia się stan blokady
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                        // Reset timer przy KAŻDEJ interakcji (dotknięcie, scroll, swipe)
                        if (!requiresPinCheck.value) {
                            lastInteractionTime.longValue = System.currentTimeMillis()
                        }
                    }
                }
            }
    ) {
        // Main content
        Scaffold(
            bottomBar = {
                if (showBottomBar && !requiresPinCheck.value) {
                    BottomNavigationBar(
                        currentRoute = currentDestination?.route,
                        onNavigate = { route ->
                            // Clear entire back stack and navigate to selected route
                            navController.navigate(route) {
                                // Pop everything up to and including the current route
                                popUpTo(0) {
                                    inclusive = false
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                // Don't restore state - start fresh
                                restoreState = false
                            }
                        }
                    )
                }
            }
        ) { paddingValues ->
            SmsCryptNavigation(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(paddingValues)
            )
        }
        
        // PIN Lock Overlay - shows on top of everything when required
        if (requiresPinCheck.value && currentDestination?.route != Screen.Pin.route) {
            com.smscrypt.pro.ui.screens.pin.PinScreen(
                onPinVerified = {
                    requiresPinCheck.value = false
                    lastInteractionTime.longValue = System.currentTimeMillis() // Reset timer po odblokowaniu
                }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem(Screen.Home.route, "Home", Icons.Default.Home),
        BottomNavItem(Screen.Contacts.route, "Contacts", Icons.Default.Person),
        BottomNavItem(Screen.Settings.route, "Settings", Icons.Default.Settings)
    )
    
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route || 
                          (item.route == Screen.Home.route && currentRoute?.contains("home") == true) ||
                          (item.route == Screen.Contacts.route && currentRoute?.contains("contact") == true) ||
                          (item.route == Screen.Settings.route && currentRoute?.contains("settings|language|storage|subscription|info") == true),
                onClick = { 
                    // Always navigate to route and clear back stack
                    onNavigate(item.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

