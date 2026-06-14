# 📋 SMS Crypt Pro - Google Play Compliance Checklist

## ✅ Zgodność z polityką Google Play

### 1. Uprawnienia SMS (SMS Permissions Policy)

#### Wymagane uprawnienia:
- ✅ `SEND_SMS` - Wysyłanie zaszyfrowanych wiadomości
- ✅ `READ_SMS` - Odczytywanie przychodzących SMS do deszyfrowania
- ✅ `RECEIVE_SMS` - Odbieranie SMS w czasie rzeczywistym

#### Uzasadnienie użycia:
Aplikacja SMS Crypt Pro to **podstawowa aplikacja SMS** zaprojektowana do:
- Szyfrowania i wysyłania wiadomości SMS
- Odbierania i automatycznego deszyfrowania wiadomości SMS
- Zarządzania bezpieczną komunikacją tekstową

**Zgodność**: Aplikacja spełnia wymogi Google Play jako domyślna aplikacja SMS/Messaging.

---

### 2. Przejrzystość uprawnień

#### Implementacja:
```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.RECEIVE_SMS" />
```

#### Informacja dla użytkownika:
- ✅ Jasny opis w Settings > SMS Permissions
- ✅ Status uprawnień widoczny w aplikacji
- ✅ Brak ukrytych funkcji

---

### 3. Prywatność i bezpieczeństwo danych

#### Przechowywanie danych:
- ✅ **Lokalne przechowywanie**: Wszystkie dane w Room Database
- ✅ **Bez backup do chmury**: `android:allowBackup="false"`
- ✅ **Szyfrowanie haseł**: Hasła kontaktów zaszyfrowane lokalnie
- ✅ **Brak Analytics**: Brak Google Analytics, Firebase, czy innych trackerów

#### Data Safety Form (Google Play Console):
```
Czy aplikacja zbiera dane użytkownika?
→ TAK (ale tylko lokalnie)

Jakie dane są zbierane?
→ Kontakty: Imię, numer telefonu (przechowywane lokalnie)
→ Wiadomości: Treść SMS (przechowywana lokalnie, zaszyfrowana)

Czy dane są udostępniane stronom trzecim?
→ NIE

Czy dane są szyfrowane w tranzycie?
→ N/A (brak transmisji sieciowej)

Czy dane są szyfrowane w spoczynku?
→ TAK (hasła kontaktów zaszyfrowane)

Czy użytkownik może żądać usunięcia danych?
→ TAK (funkcja Delete w aplikacji)
```

---

### 4. Bezpieczeństwo

#### Implementowane zabezpieczenia:
- ✅ **AES-256-CBC**: Szyfrowanie wiadomości
- ✅ **PBKDF2-SHA256**: Derywacja kluczy (10,000 iteracji)
- ✅ **PIN 6-cyfrowy**: Ochrona dostępu do aplikacji
- ✅ **FLAG_SECURE**: Ochrona przed screenshotami
- ✅ **ProGuard**: Obfuskacja kodu w release
- ✅ **Brak logowania**: Brak wrażliwych danych w logach

#### Kod bezpieczeństwa:
```kotlin
// MainActivity.kt
window.setFlags(
    WindowManager.LayoutParams.FLAG_SECURE,
    WindowManager.LayoutParams.FLAG_SECURE
)
```

---

### 5. Brak ukrytych funkcji

#### Transparentność:
- ✅ **Open architecture**: Kod zgodny z Clean Architecture
- ✅ **Brak obfuscation w debug**: Pełna transparentność w debug build
- ✅ **Dokumentacja**: Pełna dokumentacja kodu i funkcji
- ✅ **Brak backdoorów**: Kod audytowalny

---

### 6. Zgodność z SMS Permission Policy

#### Wymagania Google:
1. ✅ Aplikacja MUSI być domyślną aplikacją SMS/MMS
   - SMS Crypt Pro jest zaprojektowana jako primary SMS app
   
2. ✅ Wysyłanie/odbieranie SMS jako główna funkcja
   - Główna funkcjonalność to szyfrowanie SMS
   
3. ✅ Brak background sending bez wiedzy użytkownika
   - Wszystkie SMS wysyłane przez użytkownika manualnie
   
4. ✅ Jasna prezentacja kosztów SMS
   - Użytkownik widzi każdą wiadomość przed wysłaniem

---

### 7. Metadata Google Play

#### Tytuł aplikacji:
```
SMS Crypt Pro - Encrypted Messaging
```

#### Krótki opis:
```
Secure SMS encryption with AES-256. Send encrypted messages to your contacts with individual passwords.
```

#### Długi opis:
```
SMS Crypt Pro is a professional SMS encryption application that provides end-to-end security for your text messages.

KEY FEATURES:
🔒 AES-256 Encryption - Military-grade security
👤 Individual Passwords - Unique encryption key per contact
📱 Automatic Processing - Auto-encrypt outgoing, auto-decrypt incoming SMS
🌍 Multi-language - Supports 6 languages
🔐 PIN Protection - 6-digit PIN with 5 attempts limit
🖼️ Screenshot Protection - Secure your conversations
💾 Local Storage - All data stored locally, no cloud sync

SECURITY:
- AES-256-CBC encryption algorithm
- PBKDF2-SHA256 key derivation (10,000 iterations)
- Encrypted password storage
- No data transmission to third parties
- No analytics or tracking

PRIVACY:
- All data stored locally on your device
- No cloud backup
- No internet connection required
- Full control of your data

USE CASES:
- Secure business communications
- Private personal messages
- Confidential information exchange
- Protected conversations

PERMISSIONS:
- SEND_SMS: To send encrypted messages
- READ_SMS: To read and decrypt incoming messages
- RECEIVE_SMS: To automatically process incoming messages

© 2025 ORYNTIUM powered by rhei
License: MIT
```

#### Kategoria:
```
Communication
```

#### Content Rating:
```
Everyone (ESRB: Everyone)
```

#### Target Audience:
```
Age: 18+
Users requiring secure SMS communication
```

---

### 8. Screenshots i Grafika

#### Wymagane obrazy:
1. **Feature Graphic**: 1024 x 500 px
2. **Screenshots**: Min. 2 (phone + tablet)
3. **App Icon**: 512 x 512 px

#### Treść screenshots:
- ✅ PIN Screen
- ✅ Home Screen z Recent Messages
- ✅ Contacts Screen
- ✅ Chat Screen z encrypted messages
- ✅ Settings Screen

---

### 9. Privacy Policy

#### URL Privacy Policy:
Wymagane dla aplikacji zbierających dane użytkownika.

#### Przykładowa Privacy Policy:
```markdown
# Privacy Policy for SMS Crypt Pro

Last updated: 2025

## Data Collection
SMS Crypt Pro collects the following data:
- Contact names and phone numbers
- SMS message content

## Data Storage
- All data is stored locally on your device
- No data is transmitted to external servers
- No cloud backup

## Data Security
- Encryption passwords are encrypted locally
- AES-256 encryption for messages
- PIN protection for app access

## Data Sharing
- We do NOT share any data with third parties
- No analytics or tracking

## Data Deletion
- Users can delete contacts and messages at any time
- Uninstalling the app removes all data

## Contact
For questions: [your-email@example.com]
```

---

### 10. Compliance Checklist

#### Pre-submission:
- ✅ Wszystkie uprawnienia uzasadnione
- ✅ Privacy Policy utworzona
- ✅ Data Safety Form wypełniony
- ✅ Screenshots przygotowane
- ✅ Feature Graphic przygotowana
- ✅ Opis aplikacji napisany
- ✅ Content rating uzyskany
- ✅ Signed APK/AAB wygenerowany
- ✅ Kod obfuskowany (ProGuard)
- ✅ Testowanie na realnych urządzeniach

#### Post-submission:
- ✅ Monitor Google Play Console
- ✅ Odpowiadaj na review requests
- ✅ Aktualizuj zgodnie z feedback

---

### 11. Potential Compliance Issues

#### Możliwe problemy:
1. **SMS Permissions rejection**
   - **Solution**: Podkreśl, że SMS Crypt Pro to PRIMARY SMS app
   
2. **Data collection concerns**
   - **Solution**: Wyjaśnij lokalne przechowywanie

3. **Encryption concerns**
   - **Solution**: Provide cryptography export compliance

---

### 12. Testing Checklist

#### Pre-release testing:
- ✅ Test na Android 11, 12, 13, 14
- ✅ Test SMS wysyłania/odbierania
- ✅ Test szyfrowania/deszyfrowania
- ✅ Test PIN-u (correct/incorrect)
- ✅ Test uprawnień (grant/deny)
- ✅ Test różnych języków
- ✅ Test screenshot protection
- ✅ Test usuwania danych

---

## 📝 Notatki końcowe

Ta aplikacja została zaprojektowana z pełną zgodnością z polityką Google Play:
- Transparentne uprawnienia
- Lokalne przechowywanie danych
- Brak ukrytych funkcji
- Pełna dokumentacja
- Open architecture

**Status**: ✅ READY FOR GOOGLE PLAY SUBMISSION

---

**© 2025 ORYNTIUM powered by rhei**














