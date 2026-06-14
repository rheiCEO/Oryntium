package com.smscrypt.pro.utils

object PhoneNumberUtils {
    
    /**
     * Normalizes phone number by removing spaces, dashes, parentheses
     * Examples:
     * "+48 123 456 789" → "+48123456789"
     * "+48-123-456-789" → "+48123456789"
     * "(123) 456-7890" → "1234567890"
     */
    fun normalize(phoneNumber: String): String {
        return phoneNumber
            .replace(" ", "")
            .replace("-", "")
            .replace("(", "")
            .replace(")", "")
            .replace(".", "")
            .trim()
    }
    
    /**
     * Checks if two phone numbers match (after normalization)
     */
    fun matches(number1: String, number2: String): Boolean {
        val normalized1 = normalize(number1)
        val normalized2 = normalize(number2)
        
        // Direct match
        if (normalized1 == normalized2) return true
        
        // Match without country code (last 9 digits)
        val suffix1 = normalized1.takeLast(9)
        val suffix2 = normalized2.takeLast(9)
        if (suffix1 == suffix2 && suffix1.length == 9) return true
        
        return false
    }
}












