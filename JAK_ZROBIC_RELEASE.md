# 🚀 Jak zrobić wersję RELEASE APK

## 📋 PRZYGOTOWANIE - 2 KROKI:

### KROK 1: Wygeneruj Keystore (podpis)
### KROK 2: Zbuduj Release APK

---

## 🔑 KROK 1: WYGENERUJ KEYSTORE

### Co to jest Keystore?
```
Keystore = Twój "podpis cyfrowy"
- Służy do podpisania APK
- Bez tego Google Play nie przyjmie APK
- ZACHOWAJ GO NA ZAWSZE!
- Jeśli zgubisz = nie możesz aktualizować aplikacji!
```

### Wygeneruj Keystore poleceniem:

```powershell
# Uruchom w terminalu (Android Studio lub PowerShell):

keytool -genkey -v -keystore oryntium-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias oryntium

# Zostaniesz poproszony o:
# 1. Hasło keystore (min. 6 znaków) - ZAPAMIĘTAJ!
# 2. Imię i nazwisko
# 3. Jednostka organizacyjna (może być: ORYNTIUM Dev)
# 4. Organizacja (może być: ORYNTIUM)
# 5. Miasto
# 6. Województwo
# 7. Kod kraju (PL)
# 8. Hasło do alias (może być takie samo jak keystore)
```

### Po wygenerowaniu:
```
✅ Plik: oryntium-release-key.jks
✅ Lokalizacja: katalog projektu
✅ ZAPISZ HASŁA! (będą potrzebne!)
```

---

## ⚠️ WAŻNE - BACKUP KEYSTORE!

```
SKOPIUJ oryntium-release-key.jks:
→ Na pendrive
→ W chmurę (Google Drive, OneDrive)
→ Na inny dysk

ZAPISZ HASŁA:
→ Hasło keystore
→ Hasło alias
→ Nazwa alias (oryntium)

BEZ TEGO NIE BĘDZIESZ MÓC AKTUALIZOWAĆ APLIKACJI!
```

---

## 🔧 KROK 2: KONFIGURACJA BUILD.GRADLE.KTS

### Otwórz: `app/build.gradle.kts`

### Dodaj konfigurację signing (po `android {`):

```kotlin
android {
    // ... existing code ...

    signingConfigs {
        create("release") {
            storeFile = file("../oryntium-release-key.jks")
            storePassword = "TWOJE_HASLO_KEYSTORE"
            keyAlias = "oryntium"
            keyPassword = "TWOJE_HASLO_ALIAS"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        // debug { ... }
    }
}
```

### ⚠️ UWAGA - HASŁA W KODZIE!
```
NIE commituj haseł do Git!
Albo użyj zmiennych środowiskowych (poniżej)
```

---

## 🔐 OPCJA: HASŁA BEZ HARDCODE (BEZPIECZNIEJ)

### Utwórz plik: `keystore.properties` (w katalogu głównym):

```properties
storePassword=TWOJE_HASLO_KEYSTORE
keyPassword=TWOJE_HASLO_ALIAS
keyAlias=oryntium
storeFile=oryntium-release-key.jks
```

### Dodaj do `.gitignore`:
```
keystore.properties
*.jks
```

### Zmodyfikuj `app/build.gradle.kts`:

```kotlin
// Na samej górze pliku (przed android {}):
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    // ...

    signingConfigs {
        create("release") {
            if (keystorePropertiesFile.exists()) {
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

---

## 🏗️ KROK 3: ZBUDUJ RELEASE APK

### Metoda 1: Terminal (Gradle)

```powershell
# Wyczyść poprzednie buildy:
.\gradlew clean

# Zbuduj Release APK:
.\gradlew assembleRelease

# Poczekaj 2-5 minut...
```

### Metoda 2: Android Studio (GUI)

```
1. Build → Select Build Variant
2. Wybierz: "release" (zamiast "debug")
3. Build → Build Bundle(s) / APK(s) → Build APK(s)
4. Poczekaj na build
5. Kliknij "locate" w notyfikacji
```

### Gdzie znajdziesz APK:
```
📱 app/build/outputs/apk/release/app-release.apk
```

---

## 📦 OPCJA: ZBUDUJ AAB (dla Google Play)

### Co to jest AAB?
```
AAB = Android App Bundle
- Preferowany format Google Play
- Mniejszy rozmiar dla użytkowników
- Google automatycznie optymalizuje dla różnych urządzeń
```

### Zbuduj AAB:

```powershell
# Terminal:
.\gradlew bundleRelease

# Wynik:
📦 app/build/outputs/bundle/release/app-release.aab
```

### Lub w Android Studio:
```
Build → Build Bundle(s) / APK(s) → Build Bundle(s)
```

---

## 🎯 KOMPLETNY PROCES - KROK PO KROKU

### 1️⃣ Wygeneruj Keystore (raz na zawsze):
```powershell
keytool -genkey -v -keystore oryntium-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias oryntium
```

### 2️⃣ Zapisz hasła w `keystore.properties`:
```properties
storePassword=TwojeHaslo123
keyPassword=TwojeHaslo123
keyAlias=oryntium
storeFile=oryntium-release-key.jks
```

### 3️⃣ Zmodyfikuj `app/build.gradle.kts` (signing config)

### 4️⃣ Zbuduj Release:
```powershell
.\gradlew clean
.\gradlew assembleRelease
```

### 5️⃣ Znajdź APK:
```
app/build/outputs/apk/release/app-release.apk
```

### 6️⃣ Sprawdź rozmiar i test:
```powershell
# Zobacz rozmiar:
Get-Item "app\build\outputs\apk\release\app-release.apk" | Format-List Name, Length, LastWriteTime

# Zainstaluj na telefonie:
adb install app\build\outputs\apk\release\app-release.apk
```

---

## ✅ RÓŻNICE DEBUG vs RELEASE

### Debug APK:
```
📱 app-debug.apk
✅ Szybki build (30 sek - 1 min)
❌ Duży rozmiar (~15-25 MB)
❌ Nie zoptymalizowany
❌ Unsigned/debug certificate
❌ Zawiera debug info
❌ Więcej wykryć antywirusów
```

### Release APK:
```
📱 app-release.apk
✅ Zoptymalizowany (ProGuard/R8)
✅ Mniejszy rozmiar (~8-15 MB)
✅ Signed z twoim keystore
✅ Bez debug info
✅ Gotowy do Google Play
✅ Mniej wykryć antywirusów
⏱️ Dłuższy build (2-5 min)
```

---

## 📊 SPRAWDŹ RELEASE APK NA VIRUSTOTAL

### Po zbudowaniu Release:

```
1. Upload na: https://www.virustotal.com
2. Wybierz: app/build/outputs/apk/release/app-release.apk
3. Zobacz wynik

Oczekiwane:
✅ Mniej wykryć niż Debug APK
✅ 0-3 / 62 detections (idealnie)
✅ Lepszy "reputation score"
```

---

## 🚀 DEPLOY DO GOOGLE PLAY

### Potrzebujesz:

```
1️⃣ Konto Google Play Developer ($25 jednorazowo)
   https://play.google.com/console

2️⃣ Release APK lub AAB (AAB lepiej!)
   app/build/outputs/bundle/release/app-release.aab

3️⃣ Store listing:
   - Opis aplikacji (5 języków)
   - Screenshots (min. 2)
   - Icon (512x512 px)
   - Feature graphic (1024x500 px)

4️⃣ Privacy Policy (URL)
   - Wymagane dla aplikacji z SMS permissions

5️⃣ Wypełnij "Data safety" form
   - Jakie dane zbierasz
   - Jak je używasz
```

### Upload AAB:
```
Google Play Console
→ Create app
→ Production → Create release
→ Upload app-release.aab
→ Fill store listing
→ Submit for review

Czas review: 1-3 dni (średnio)
```

---

## ⚡ QUICK START - ZRÓB TO TERAZ!

### Jeśli chcesz szybko:

```powershell
# 1. Wygeneruj keystore:
keytool -genkey -v -keystore oryntium-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias oryntium

# 2. Utwórz keystore.properties z hasłami

# 3. Zmodyfikuj app/build.gradle.kts (dodaj signing config)

# 4. Zbuduj:
.\gradlew clean assembleRelease

# 5. Sprawdź:
Get-Item "app\build\outputs\apk\release\app-release.apk"
```

---

## 🎯 CO DALEJ?

### Po zbudowaniu Release APK:

1. ✅ **Test na telefonie**
   ```powershell
   adb install app\build\outputs\apk\release\app-release.apk
   ```

2. ✅ **Scan na VirusTotal**
   ```
   https://www.virustotal.com
   Upload: app-release.apk
   Sprawdź wykrycia (powinno być lepiej niż debug!)
   ```

3. ✅ **Przygotuj store listing**
   ```
   - Opisy w językach
   - Screenshots
   - Privacy policy
   ```

4. ✅ **Zbuduj AAB dla Google Play**
   ```powershell
   .\gradlew bundleRelease
   ```

5. ✅ **Upload do Google Play Console**
   ```
   https://play.google.com/console
   ```

---

## 📚 DODATKOWE ZASOBY

### ProGuard rules (już są w `app/proguard-rules.pro`):
```
Optymalizuje i obfuskuje kod
Zmniejsza rozmiar APK
Utrudnia reverse engineering
```

### Version code/name (`app/build.gradle.kts`):
```kotlin
versionCode = 1        // Zwiększaj przy każdej wersji (1, 2, 3...)
versionName = "1.0.0"  // Widoczna dla użytkowników
```

### Build variants:
```
Debug → do testów
Release → do Google Play
```

---

## 🎉 GOTOWE!

Po wykonaniu kroków będziesz miał:
✅ Signed Release APK
✅ Gotowy do Google Play
✅ Zoptymalizowany i bezpieczny
✅ Mniejszy rozmiar
✅ Mniej false positives

**Teraz wygeneruj keystore i zbuduj Release!** 🚀

---

**© 2025 ORYNTIUM powered by rhei**












