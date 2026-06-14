# 🛠️ SMS Crypt Pro - Instrukcja Budowania

## 📋 Wymagania wstępne

### Wymagane narzędzia:
1. **Android Studio** Electric Eel (2022.1.1) lub nowszy
   - Pobierz z: https://developer.android.com/studio
   
2. **JDK 17**
   - Android Studio zawiera wbudowane JDK
   - Lub pobierz z: https://www.oracle.com/java/technologies/downloads/

3. **Android SDK**
   - SDK Platform 34 (Android 14)
   - Build Tools 34.0.0 lub nowszy
   - Android SDK Platform-Tools

### Sprawdzenie instalacji:
```bash
# Sprawdź wersję Java
java -version  # Powinno pokazać Java 17

# Sprawdź Android SDK
cd %ANDROID_HOME%
dir platforms  # Powinien być android-34
```

---

## 🚀 Krok 1: Otwórz projekt w Android Studio

1. Uruchom Android Studio
2. Kliknij **"Open"**
3. Wskaż katalog: `D:\APLIKACJA oryntium.app`
4. Kliknij **"OK"**

Android Studio automatycznie:
- Zsynchronizuje pliki Gradle
- Pobierze zależności
- Skonfiguruje projekt

---

## 🔧 Krok 2: Synchronizacja Gradle

Jeśli synchronizacja się nie uruchomiła automatycznie:

1. Kliknij **File > Sync Project with Gradle Files**
2. Poczekaj na zakończenie (może trwać 2-5 minut przy pierwszym uruchomieniu)

### Możliwe błędy:
**Błąd: "SDK not found"**
```
Solution: File > Project Structure > SDK Location
Ustaw Android SDK location (zazwyczaj: C:\Users\[USER]\AppData\Local\Android\Sdk)
```

**Błąd: "Gradle version mismatch"**
```
Solution: Zaakceptuj sugerowaną wersję Gradle
```

---

## 📱 Krok 3: Konfiguracja emulatora (opcjonalnie)

### Jeśli nie masz urządzenia fizycznego:

1. **Tools > Device Manager**
2. Kliknij **"Create Device"**
3. Wybierz **"Phone" > "Pixel 7 Pro"**
4. System Image: **"Android 14 (API 34)"**
   - Kliknij "Download" jeśli nie jest pobrany
5. Kliknij **"Finish"**

### Uruchomienie emulatora:
- Kliknij ikonę zielonego trójkąta przy emulatorze w Device Manager
- Poczekaj na uruchomienie (1-2 minuty)

---

## 🏗️ Krok 4: Budowanie Debug APK

### Przez Android Studio GUI:
1. **Build > Build Bundle(s) / APK(s) > Build APK(s)**
2. Poczekaj na zakończenie (~1-3 minuty)
3. Kliknij **"locate"** w notyfikacji

### Przez Terminal (w Android Studio):
```bash
# Otwórz Terminal w Android Studio (Alt+F12)
.\gradlew assembleDebug
```

Debug APK będzie w:
```
app\build\outputs\apk\debug\app-debug.apk
```

---

## 🎯 Krok 5: Budowanie Release APK

### 5.1 Generowanie Keystore (pierwszy raz)

```bash
# W terminalu Windows
cd D:\APLIKACJA oryntium.app

# Generuj keystore
keytool -genkey -v -keystore smscrypt.keystore -alias smscrypt -keyalg RSA -keysize 2048 -validity 10000
```

Podaj dane:
- Password: [TWOJE_HASŁO]
- Name: ORYNTIUM
- Organizational Unit: Development
- Organization: ORYNTIUM
- City: [MIASTO]
- State: [WOJEWÓDZTWO]
- Country Code: PL

**⚠️ WAŻNE**: Zapisz hasło w bezpiecznym miejscu!

### 5.2 Konfiguracja signing w Gradle

Utwórz plik `keystore.properties` w katalogu głównym projektu:
```properties
storePassword=[TWOJE_HASŁO]
keyPassword=[TWOJE_HASŁO]
keyAlias=smscrypt
storeFile=smscrypt.keystore
```

### 5.3 Budowanie signed Release APK

```bash
.\gradlew assembleRelease
```

Release APK będzie w:
```
app\build\outputs\apk\release\app-release.apk
```

---

## 🧪 Krok 6: Uruchomienie aplikacji

### Na emulatorze:
1. Upewnij się, że emulator jest uruchomiony
2. Kliknij **Run > Run 'app'** (lub Shift+F10)
3. Wybierz emulator z listy
4. Poczekaj na instalację i uruchomienie

### Na prawdziwym urządzeniu:
1. **Włącz Developer Options na telefonie:**
   - Settings > About Phone
   - Kliknij 7 razy w "Build Number"
   
2. **Włącz USB Debugging:**
   - Settings > System > Developer Options
   - Włącz "USB Debugging"
   
3. **Podłącz telefon USB**
4. Zaakceptuj prompt "Allow USB Debugging"
5. Kliknij **Run > Run 'app'**
6. Wybierz swoje urządzenie z listy

---

## 📦 Krok 7: Generowanie AAB (Android App Bundle)

### Dla Google Play Store:

```bash
.\gradlew bundleRelease
```

AAB będzie w:
```
app\build\outputs\bundle\release\app-release.aab
```

**⚠️ Google Play preferuje AAB zamiast APK!**

---

## 🔍 Krok 8: Testowanie

### Test funkcjonalności:
1. **PIN Screen:**
   - Utwórz PIN 6-cyfrowy
   - Przetestuj błędne PIN-y (max 5 prób)
   
2. **Dodaj kontakt:**
   - Nazwa: "Test"
   - Numer: "+48123456789"
   - Hasło: "TestPassword123"
   
3. **Wyślij zaszyfrowany SMS:**
   - Otwórz czat z kontaktem
   - Włącz Encryption
   - Wyślij testową wiadomość
   
4. **Przetestuj języki:**
   - Settings > Language Settings
   - Zmień na różne języki

### Test na prawdziwym urządzeniu:
- ⚠️ Wysyłanie SMS generuje koszty!
- Użyj drugiego telefonu do testowania
- Sprawdź, czy SMS przychodzą zaszyfrowane z prefiksem "SCRYPT:"

---

## 🐛 Troubleshooting

### Problem: "Could not resolve dependencies"
```
Solution: 
- Sprawdź połączenie internetowe
- File > Invalidate Caches / Restart
- .\gradlew clean
```

### Problem: "Execution failed for task ':app:processDebugManifest'"
```
Solution: 
- Clean project: Build > Clean Project
- Rebuild: Build > Rebuild Project
```

### Problem: "SDK location not found"
```
Solution:
- File > Project Structure > SDK Location
- Wskaż prawidłową lokalizację Android SDK
```

### Problem: "Insufficient permissions"
```
Solution:
- Upewnij się, że na urządzeniu/emulatorze przyznano uprawnienia SMS
- Settings > Apps > SMS Crypt Pro > Permissions
```

---

## 📊 Weryfikacja APK

### Sprawdź rozmiar APK:
```bash
cd app\build\outputs\apk\release
dir app-release.apk
```
Powinien być ~5-7 MB

### Sprawdź podpis APK:
```bash
jarsigner -verify -verbose -certs app-release.apk
```

### Sprawdź zawartość APK:
```bash
aapt dump badging app-release.apk
```

---

## 🚢 Przygotowanie do Google Play

### Checklist przed submission:
- ✅ Signed Release AAB wygenerowany
- ✅ Wersja code i name zaktualizowane w `build.gradle.kts`
- ✅ ProGuard włączony
- ✅ Screenshots przygotowane (min. 2)
- ✅ Feature Graphic przygotowana (1024x500)
- ✅ Privacy Policy URL gotowy
- ✅ Opis aplikacji napisany
- ✅ Testowanie na realnych urządzeniach Android 11+

### Upload do Google Play Console:
1. Wejdź na https://play.google.com/console
2. Utwórz nową aplikację
3. Wypełnij wszystkie wymagane informacje
4. Upload AAB: Production > Create new release
5. Wypełnij "What's new"
6. Kliknij "Review release"
7. Kliknij "Start rollout to Production"

---

## 📝 Notatki końcowe

### Zmiana wersji przed release:
```kotlin
// app/build.gradle.kts
defaultConfig {
    versionCode = 1  // Zwiększ o 1 przy każdym update
    versionName = "1.0.0"  // Wersja widoczna dla użytkownika
}
```

### Logi i debugging:
```bash
# Wyświetl logi w czasie rzeczywistym
adb logcat | findstr "SmsCrypt"
```

---

## ✅ Podsumowanie

Gratulacje! Masz teraz w pełni funkcjonalną aplikację SMS Crypt Pro gotową do publikacji!

**Kolejne kroki:**
1. Testuj aplikację intensywnie
2. Zbierz feedback od beta testerów
3. Przygotuj marketing materials
4. Submit do Google Play
5. Monitoruj reviews i crash reports

---

**🎉 Powodzenia z publikacją!**

© 2025 ORYNTIUM powered by rhei














