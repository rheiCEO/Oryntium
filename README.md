# 🔐 SMS Crypt Pro

Profesjonalna aplikacja Android do szyfrowania wiadomości SMS z pełnym bezpieczeństwem AES-256.

## ✨ Funkcje

- **🔒 Szyfrowanie End-to-End**: Wykorzystuje AES-256-CBC z PBKDF2 do generowania kluczy
- **👤 Indywidualne hasła**: Każdy kontakt ma własne unikalne hasło szyfrujące
- **📱 Automatyczne przetwarzanie**: Automatycznie szyfruje i deszyfruje przychodzące/wychodzące SMS-y
- **🌍 Wielojęzyczność**: Wsparcie dla Polski, Angielskiego, Hiszpańskiego, Niemieckiego, Rosyjskiego i Chińskiego
- **🔐 PIN 6-cyfrowy**: Zabezpieczenie dostępu do aplikacji z limitem 5 prób
- **🖼️ Ochrona przed screenshotami**: Blokada screenshotów z efektem Matrix
- **🎨 Cyberpunk UI**: Ciemny motyw z neonowymi kolorami (cyan, purple)
- **💾 Lokalne przechowywanie**: Wszystkie dane zapisywane lokalnie, brak transmisji do zewnętrznych serwerów
- **📊 Room Database**: Szyfrowana lokalna baza danych

## 🛠️ Stack technologiczny

- **Język**: Kotlin
- **UI**: Jetpack Compose + Material Design 3
- **Architektura**: MVVM + Clean Architecture
- **Database**: Room (szyfrowana)
- **Dependency Injection**: Hilt
- **Kryptografia**: BouncyCastle (AES-256, PBKDF2)
- **Minimum SDK**: 30 (Android 11)
- **Target SDK**: 34 (Android 14)

## 📱 Wymagane uprawnienia

```xml
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.RECEIVE_SMS" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

## 🚀 Instalacja i uruchomienie

### Wymagania
- Android Studio Electric Eel (2022.1.1) lub nowszy
- JDK 17
- Android SDK 34

### Kroki instalacji

1. **Klonowanie repozytorium**
```bash
cd "D:\APLIKACJA oryntium.app"
```

2. **Otwórz projekt w Android Studio**
```
File > Open > Wybierz katalog projektu
```

3. **Synchronizacja Gradle**
```
File > Sync Project with Gradle Files
```

4. **Uruchomienie na emulatorze/urządzeniu**
```
Run > Run 'app'
```

## 🔐 Bezpieczeństwo

### Algorytmy szyfrowania
- **AES-256-CBC**: Szyfrowanie wiadomości
- **PBKDF2-SHA256**: Derywacja kluczy (10,000 iteracji)
- **Base64**: Kodowanie dla SMS

### Przechowywanie danych
- Hasła kontaktów są szyfrowane kluczem specyficznym dla urządzenia
- Baza danych Room przechowywana lokalnie
- Wyłączony backup do chmury (`allowBackup="false"`)
- Ochrona przed root access

### Format zaszyfrowanej wiadomości
```
SCRYPT:[Base64(IV + Salt + Encrypted Data)]
```

## 📂 Struktura projektu

```
app/src/main/java/com/smscrypt/pro/
├── crypto/
│   └── EncryptionManager.kt          # Moduł szyfrowania
├── data/
│   ├── database/
│   │   ├── AppDatabase.kt
│   │   ├── ContactDao.kt
│   │   └── SmsDao.kt
│   ├── model/
│   │   ├── Contact.kt
│   │   └── SmsMessage.kt
│   └── preferences/
│       ├── PinManager.kt
│       └── LanguageManager.kt
├── di/                                # Dependency Injection
├── receiver/
│   └── SmsReceiver.kt                # Odbieranie SMS
├── service/
│   └── SmsService.kt                 # Wysyłanie SMS
├── ui/
│   ├── components/                    # Reusable UI components
│   ├── screens/                       # Ekrany aplikacji
│   ├── theme/                         # Motyw i kolory
│   └── navigation/                    # Nawigacja
├── utils/
│   └── ScreenshotProtection.kt       # Ochrona przed screenshotami
├── MainActivity.kt
└── SmsCryptApplication.kt
```

## 🎨 UI/UX

### Paleta kolorów (Cyberpunk Theme)
- **Primary**: `#00D4FF` (Cyan)
- **Secondary**: `#8B5CF6` (Purple)
- **Background**: `#0A0A0A` (Almost black)
- **Surface**: `#1A1A1A` (Dark gray)

### Ekrany
1. **PIN Screen**: Zabezpieczenie 6-cyfrowym PIN-em
2. **Home Screen**: Ostatnie wiadomości i szybkie akcje
3. **Contacts Screen**: Zarządzanie kontaktami
4. **Chat Screen**: Konwersacja z szyfrowaniem
5. **Settings Screen**: Ustawienia i konfiguracja

## 📖 Użytkowanie

### 1. Pierwsze uruchomienie
- Utwórz 6-cyfrowy PIN
- Przyznaj uprawnienia SMS

### 2. Dodawanie kontaktu
1. Przejdź do zakładki "Contacts"
2. Kliknij przycisk "+"
3. Wprowadź nazwę, numer telefonu i hasło szyfrujące
4. Zapisz

### 3. Wysyłanie zaszyfrowanej wiadomości
1. Wybierz kontakt
2. Włącz przełącznik "Encryption"
3. Wpisz wiadomość
4. Wyślij

### 4. Odbieranie zaszyfrowanej wiadomości
- Aplikacja automatycznie odszyfruje wiadomość z prefiksem "SCRYPT:"
- Wiadomość pojawi się w czacie

## 🔧 Kompilacja APK

### Debug APK
```bash
./gradlew assembleDebug
```

### Release APK (signed)
```bash
./gradlew assembleRelease
```

APK będzie w: `app/build/outputs/apk/release/app-release.apk`

## 📜 Licencja

MIT License

Copyright © 2025 ORYNTIUM powered by rhei

## 🤝 Wkład w projekt

Projekt stworzony na potrzeby bezpiecznej komunikacji SMS.

## 📞 Kontakt

- **Firma**: ORYNTIUM
- **Powered by**: rhei
- **Rok**: 2025

## ⚠️ Zastrzeżenia

Ta aplikacja jest tworzona wyłącznie w celach edukacyjnych i osobistych. 
Przed użyciem w produkcji zaleca się audyt bezpieczeństwa przez specjalistów.

## 🔒 Zgodność z Google Play

Aplikacja spełnia następujące wymagania Google Play:
- ✅ Brak ukrytych kodów
- ✅ Brak wysyłania danych do firm trzecich
- ✅ Przejrzyste uprawnienia
- ✅ Ochrona prywatności użytkownika
- ✅ Zaszyfrowane przechowywanie danych
- ✅ Zgodność z polityką prywatności

## 📊 Statystyki

- **Wersja**: 1.0.0
- **Min SDK**: 30 (Android 11)
- **Target SDK**: 34 (Android 14)
- **Języki**: 6 (PL, EN, ES, DE, RU, ZH)
- **Rozmiar APK**: ~5-7 MB (po kompilacji)

---

**Zbudowano z ❤️ przez ORYNTIUM powered by rhei**














