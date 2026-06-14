package com.smscrypt.pro.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Different theme colors for app disguise
 */
enum class AppTheme(
    val themeName: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val backgroundColor: Color,
    val surfaceColor: Color
) {
    ORYNTIUM(
        themeName = "ORYNTIUM",
        primaryColor = Color(0xFF7B68EE),  // Purple
        secondaryColor = Color(0xFF4A90E2), // Blue
        backgroundColor = Color(0xFF1A1A2E),
        surfaceColor = Color(0xFF16213E)
    ),
    
    CALCULATOR(
        themeName = "Calculator",
        primaryColor = Color(0xFFFF9500),  // Orange
        secondaryColor = Color(0xFFFF6B00),
        backgroundColor = Color(0xFF000000),
        surfaceColor = Color(0xFF1C1C1E)
    ),
    
    NOTES(
        themeName = "Notes",
        primaryColor = Color(0xFFFFD60A),  // Yellow
        secondaryColor = Color(0xFFFFB800),
        backgroundColor = Color(0xFFFFFBF0),
        surfaceColor = Color(0xFFFFF8E1)
    ),
    
    BANK(
        themeName = "Banking",
        primaryColor = Color(0xFF004E89),  // Navy Blue
        secondaryColor = Color(0xFF006BA6),
        backgroundColor = Color(0xFFF8F9FA),
        surfaceColor = Color(0xFFFFFFFF)
    ),
    
    WEATHER(
        themeName = "Weather",
        primaryColor = Color(0xFF00B4D8),  // Sky Blue
        secondaryColor = Color(0xFF0096C7),
        backgroundColor = Color(0xFF90E0EF),
        surfaceColor = Color(0xFFCAF0F8)
    ),
    
    GAME(
        themeName = "Game",
        primaryColor = Color(0xFFE63946),  // Red
        secondaryColor = Color(0xFFD62828),
        backgroundColor = Color(0xFF1D3557),
        surfaceColor = Color(0xFF457B9D)
    ),
    
    COMPASS(
        themeName = "Compass",
        primaryColor = Color(0xFF2D6A4F),  // Green
        secondaryColor = Color(0xFF40916C),
        backgroundColor = Color(0xFFD8F3DC),
        surfaceColor = Color(0xFFB7E4C7)
    ),
    
    FLASHLIGHT(
        themeName = "Flashlight",
        primaryColor = Color(0xFFF77F00),  // Orange
        secondaryColor = Color(0xFFFFBA08),
        backgroundColor = Color(0xFF212529),
        surfaceColor = Color(0xFF343A40)
    ),
    
    CALENDAR(
        themeName = "Calendar",
        primaryColor = Color(0xFF6A4C93),  // Purple
        secondaryColor = Color(0xFF8B5CF6),
        backgroundColor = Color(0xFFFFFFF),
        surfaceColor = Color(0xFFF5F3F5)
    ),
    
    MUSIC(
        themeName = "Music",
        primaryColor = Color(0xFFFF006E),  // Pink
        secondaryColor = Color(0xFFFB5607),
        backgroundColor = Color(0xFF03071E),
        surfaceColor = Color(0xFF370617)
    );
    
    companion object {
        fun fromName(name: String): AppTheme {
            return values().find { it.themeName == name } ?: ORYNTIUM
        }
    }
}












