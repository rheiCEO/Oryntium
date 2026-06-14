# 📋 NOWE FUNKCJE - Plan Implementacji

## ✅ NAPRAWIONE:

### 1. **Język - przycisk OK** ✅
```
- Dodano przycisk "OK - SAVE" na dole ekranu
- Pokazuje się po wybraniu języka
- Zapisuje i wraca do Settings
```

---

## 🔍 DO PRZETESTOWANIA:

### **Dialog DATA DELETED (po 5 złych PIN)**

#### Jak przetestować:
```
1. Zainstaluj APK:
   adb install app\build\outputs\apk\release\app-release.apk

2. Ustaw PIN (np. 123456)

3. Zrestartuj aplikację (zamknij i otwórz)

4. Wpisz ZŁY PIN 5 razy (np. 111111, 222222, 333333, 444444, 555555)

5. Po 5 próbie powinien się pokazać:
   ✓ Dialog z czerwonym krzyżykiem (pulsuje)
   ✓ Tekst "SECURITY BREACH"
   ✓ Tekst "DATA DELETED"
   ✓ Przycisk OK
   ✓ Auto-zamknięcie po 10 sekundach

6. Po zamknięciu dialogu:
   ✓ Aplikacja jak nowa (wszystkie dane skasowane)
   ✓ Ekran "Create a 6-digit PIN"
```

#### Jeśli NIE działa:
```
Możliwe przyczyny:
- PIN nie jest ustawiany poprawnie
- PinManager nie śledzi failed attempts
- Dialog nie jest wywoływany

Wyślij mi info co dokładnie się dzieje!
```

---

## 🆕 NOWE FUNKCJE DO ZROBIENIA:

### **Funkcja 1: Wybór ikony aplikacji (10 opcji)** 🎨

#### Opis:
```
Użytkownik może wybrać ikonę aplikacji z 10 opcji:
- Kalkulator
- Notatnik
- Gra (np. Tetris)
- Bank
- Pogoda
- Kompas
- Latarka
- Kalendarz
- Muzyka
- Standard (ORYNTIUM)

Zmiana ikony działa natychmiast (bez restartowania)
```

#### Plan implementacji:
```
1. Stworzyć 10 ikon (mipmap-xxxhdpi/xxhdpi/xhdpi/hdpi/mdpi)
2. Dodać activity-alias w AndroidManifest.xml dla każdej ikony
3. Dodać ekran wyboru ikony w Settings
4. PackageManager.setComponentEnabledSetting() do zmiany aktywnej ikony
5. Zapisać wybór w DataStore
```

#### Lokalizacja w UI:
```
Settings → App Appearance → Change Icon
→ Siatka 10 ikon
→ Kliknij → Preview
→ Przycisk "Apply"
```

---

### **Funkcja 2: Zmiana nazwy aplikacji** ✏️

#### Opis:
```
Użytkownik może zmienić nazwę aplikacji wyświetlaną w:
- Liście aplikacji (launcher)
- Górnym pasku aplikacji
- Recent apps

Przykłady:
- "Calculator" (wygląda jak kalkulator)
- "Notes" (wygląda jak notatnik)
- "Banking" (wygląda jak bank)
- "Weather" (wygląda jak pogoda)
- Własna nazwa (wpisana przez użytkownika)
```

#### Plan implementacji:
```
1. Lista predefiniowanych nazw (10 opcji)
2. Pole "Custom name" (własna nazwa)
3. Zapisać w DataStore
4. Użyć activity-alias z różnymi label
5. Zmienić dynamicznie TopAppBar title w całej aplikacji
```

#### Lokalizacja w UI:
```
Settings → App Appearance → Change Name
→ Lista gotowych nazw
→ Lub pole "Custom name"
→ Przycisk "Save"
```

---

### **Funkcja 3: Ekran blokady po zminimalizowaniu** 🔒

#### Opis:
```
Gdy użytkownik:
- Minimalizuje aplikację (Home button)
- Przełącza się na inną aplikację
- Telefon zgaśnie (screen off)

Po powrocie do aplikacji:
→ Ekran PIN (ten sam co główny)
→ Musi wpisać PIN żeby odblokować
→ Bez tego nie widać treści aplikacji
```

#### Plan implementacji:
```
1. W MainActivity:
   - onPause() → zapisz timestamp wyjścia
   - onResume() → sprawdź czy minęło X sekund

2. Jeśli minęło > 5 sekund:
   - Przekieruj na ekran PIN
   - Dodaj parametr "isUnlock = true"
   - Po poprawnym PIN → wróć gdzie był

3. Overlay podczas przełączania:
   - WindowManager overlay
   - Czarny ekran z logo
   - Ukrywa treść w recent apps
```

#### Opcje konfiguracji:
```
Settings → Security → Auto-lock
- Immediately (0s)
- After 5 seconds
- After 30 seconds
- After 1 minute
- Never
```

---

## 🎯 PLAN DZIAŁANIA:

### **Priorytet 1: Test DATA DELETED** 
```
Najpierw przetestuj czy działa!
Wyślij feedback jak się zachowuje.
```

### **Priorytet 2: Ekran blokady**
```
Najbardziej użyteczna funkcja bezpieczeństwa.
Implementacja: ~1-2 godziny
```

### **Priorytet 3: Zmiana nazwy**
```
Łatwiejsza do zrobienia niż ikony.
Implementacja: ~30 minut
```

### **Priorytet 4: Zmiana ikony**
```
Wymaga stworzenia 10 zestawów ikon.
Implementacja: ~2-3 godziny (głównie grafika)
```

---

## 📝 SZCZEGÓŁY TECHNICZNE:

### **Zmiana ikony (activity-alias):**

```xml
<!-- AndroidManifest.xml -->
<activity-alias
    android:name=".CalculatorAlias"
    android:enabled="false"
    android:exported="true"
    android:icon="@mipmap/ic_calculator"
    android:label="Calculator"
    android:targetActivity=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity-alias>

<activity-alias
    android:name=".NotesAlias"
    android:enabled="false"
    android:exported="true"
    android:icon="@mipmap/ic_notes"
    android:label="Notes"
    android:targetActivity=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity-alias>

<!-- ... 8 więcej alias ... -->
```

```kotlin
// Kotlin kod do zmiany:
fun changeAppIcon(alias: String) {
    val packageManager = context.packageManager
    
    // Disable all aliases
    listOf("CalculatorAlias", "NotesAlias", /* ... */).forEach { 
        packageManager.setComponentEnabledSetting(
            ComponentName(context, "$packageName.$it"),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
    
    // Enable selected alias
    packageManager.setComponentEnabledSetting(
        ComponentName(context, "$packageName.$alias"),
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )
}
```

---

### **Ekran blokady (MainActivity):**

```kotlin
class MainActivity : ComponentActivity() {
    private var pauseTimestamp = 0L
    private val AUTO_LOCK_DELAY_MS = 5000L // 5 sekund
    
    override fun onPause() {
        super.onPause()
        pauseTimestamp = System.currentTimeMillis()
        
        // Optional: Overlay do ukrycia treści w Recent apps
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
    
    override fun onResume() {
        super.onResume()
        
        val now = System.currentTimeMillis()
        val timePassed = now - pauseTimestamp
        
        if (timePassed > AUTO_LOCK_DELAY_MS && pauseTimestamp > 0) {
            // Przekieruj na ekran PIN
            // navController.navigate(Screen.Pin.route) {
            //     popUpTo(0) { inclusive = false }
            // }
        }
    }
}
```

---

## 🎨 MOCKUP UI:

### **Settings → App Appearance:**
```
╔════════════════════════════════════════╗
║  Settings                              ║
╠════════════════════════════════════════╣
║                                        ║
║  🎨 APP APPEARANCE                     ║
║  ┌──────────────────────────────────┐ ║
║  │ Change Icon         📱 →         │ ║
║  │ Currently: ORYNTIUM              │ ║
║  └──────────────────────────────────┘ ║
║                                        ║
║  ┌──────────────────────────────────┐ ║
║  │ Change Name         ✏️ →         │ ║
║  │ Currently: ORYNTIUM              │ ║
║  └──────────────────────────────────┘ ║
║                                        ║
║  🔒 SECURITY                           ║
║  ┌──────────────────────────────────┐ ║
║  │ Auto-lock           🔐 →         │ ║
║  │ Lock after 5 seconds             │ ║
║  └──────────────────────────────────┘ ║
║                                        ║
╚════════════════════════════════════════╝
```

### **Change Icon Screen:**
```
╔════════════════════════════════════════╗
║  ← Change Icon                         ║
╠════════════════════════════════════════╣
║                                        ║
║  Select app icon:                      ║
║                                        ║
║  ┌────┬────┬────┬────┬────┐          ║
║  │ 🧮 │ 📝 │ 🎮 │ 🏦 │ 🌤️ │          ║
║  └────┴────┴────┴────┴────┘          ║
║  Calc Notes Game Bank Weather         ║
║                                        ║
║  ┌────┬────┬────┬────┬────┐          ║
║  │ 🧭 │ 🔦 │ 📅 │ 🎵 │ 🟣 │          ║
║  └────┴────┴────┴────┴────┘          ║
║  Comp Torch Cal Music ORYN            ║
║                                        ║
║  ┌──────────────────────────────────┐ ║
║  │         APPLY CHANGES            │ ║
║  └──────────────────────────────────┘ ║
║                                        ║
╚════════════════════════════════════════╝
```

---

## ❓ PYTANIA DO CIEBIE:

### 1. **DATA DELETED - działa czy nie?**
```
Przetestuj z instrukcją powyżej i daj znać!
```

### 2. **Priorytet funkcji?**
```
Którą funkcję chcesz NAJPIERW?
a) Ekran blokady (najbardziej użyteczne)
b) Zmiana nazwy (łatwe)
c) Zmiana ikony (długie - grafika)
```

### 3. **Auto-lock timing?**
```
Po ilu sekundach blokować?
- 5 sekund (bezpieczne)
- 30 sekund (wygodne)
- Natychmiast (paranoja mode)
- Użytkownik wybiera w Settings
```

### 4. **Ikony - jakie style?**
```
- Realistyczne (wyglądają jak prawdziwe aplikacje)
- Minimalistyczne (proste, czytelne)
- Material Design (zgodne z Android)

Mogę wygenerować lub ty dostarczysz?
```

---

## 🚀 NASTĘPNE KROKI:

1. **TY:** Przetestuj DATA DELETED
2. **TY:** Powiedz którą funkcję najpierw
3. **JA:** Implementuję wybraną funkcję
4. **JA:** Buduję nowy APK do testów

---

**APK do testów (język + DATA DELETED):**
```
app/build/outputs/apk/release/app-release.apk
20.13 MB
Data: 11.10.2025 22:02

Instalacja:
adb install app\build\outputs\apk\release\app-release.apk
```

---

**Czekam na feedback! 🎯**

---

**© 2025 ORYNTIUM powered by rhei**












