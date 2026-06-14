# ❓ Czemu nie działa - Najczęstsze błędy!

## 🔴 **PROBLEM 1: DATA DELETED nie pokazuje się**

### **Co robisz:**
- Wpisujesz zły PIN 5 razy
- Pokazuje "5 remaining, 4 remaining..." w kółko
- Dialog się NIE pokazuje

### **DLACZEGO:**
Prawdopodobnie używasz **starego APK** (przed naprawką)!

### **ROZWIĄZANIE:**

```powershell
# 1. Odinstaluj starą wersję:
adb uninstall com.smscrypt.pro

# 2. Zainstaluj NOWĄ (z poprawką):
adb install app\build\outputs\apk\release\app-release.apk

# 3. Ustaw PIN: 123456

# 4. Zamknij aplikację

# 5. Otwórz ponownie

# 6. Wpisz ZŁE PIN 5 razy:
   111111
   222222
   333333
   444444
   555555 ← Po tym DIALOG!
```

---

## 🔴 **PROBLEM 2: Nie widzę przycisku "SAVE CHANGES"**

### **Co widzisz:**
- Settings → App Appearance
- Widzisz pole "App Name"
- Widzisz siatk kolorów
- NIE WIDZISZ przycisku

### **DLACZEGO:**
Przycisk jest **NA SAMYM DOLE** ekranu!

### **ROZWIĄZANIE:**

```
1. W ekranie App Appearance
2. SCROLLUJ PALCEM W DÓŁ!
3. Scroll przez całą siatkę kolorów
4. Scroll jeszcze niżej
5. Przycisk "SAVE CHANGES" jest na dole! ✅
```

---

## 🔴 **PROBLEM 3: Zmiana nazwy/motywu nie działa**

### **Co robisz:**
- App Name: "Calculator"
- Theme: Calculator (🟠)
- SAVE CHANGES
- Zamykasz aplikację normalnie (Home button)
- Otwierasz ponownie
- Dalej "ORYNTIUM" i fioletowy PIN

### **DLACZEGO:**
**NIE ZROBIŁEŚ FORCE STOP!**

Android cache nazwę i alias - trzeba wymusić zatrzymanie!

### **ROZWIĄZANIE:**

```powershell
# Po zmianie nazwy/motywu i SAVE:

# Metoda 1 (najlepsza):
adb shell am force-stop com.smscrypt.pro

# Metoda 2 (telefon):
Settings → Apps → ORYNTIUM → Force Stop

# Metoda 3 (recent apps):
Recent apps → swipe up (całkowicie zamknij)

# Poczekaj 5 sekund

# Wróć do Home screen

# Szukaj nowej nazwy (np. "Calculator")

# Uruchom aplikację
```

---

## 🔴 **PROBLEM 4: Język się nie zmienia**

### **Co robisz:**
- Language Settings → Polski → OK - SAVE
- Wraca do Settings
- Dalej angielski

### **DLACZEGO:**
1. Być może używasz starego APK
2. Lub zmiana wymaga Force Stop

### **ROZWIĄZANIE:**

```powershell
# 1. Zainstaluj nowy APK:
adb uninstall com.smscrypt.pro
adb install app\build\outputs\apk\release\app-release.apk

# 2. Settings → Language Settings → Polski → OK - SAVE

# 3. Force Stop:
adb shell am force-stop com.smscrypt.pro

# 4. Otwórz aplikację ponownie

# 5. Sprawdź czy po polsku
```

---

## 🔴 **PROBLEM 5: Nie da się zmienić ikony**

### **UWAGA:**
Ikona zmienia się **AUTOMATYCZNIE** gdy zmienisz nazwę!

### **Przykład:**

```
Wpisz nazwę "Calculator"
→ Save
→ Force Stop
→ W systemie nazwa zmieni się na "Calculator" ✅
→ (Ale ikona jeszcze ta sama - można dodać różne ikony)
```

### **Obecnie:**
- ✅ Nazwa w systemie - zmienia się
- ⏳ Ikona - jeszcze ta sama dla wszystkich
- ⏳ Różne ikony - do zrobienia (wymaga grafiki)

---

## 📋 **CHECKLIST - CO MUSISZ ZROBIĆ:**

### **Dla zmiany nazwy/motywu:**
```
✅ 1. Settings → App Appearance
✅ 2. Wpisz nazwę (np. "Calculator")
✅ 3. Wybierz motyw (np. Calculator 🟠)
✅ 4. SCROLL W DÓŁ!
✅ 5. Kliknij "SAVE CHANGES"
✅ 6. FORCE STOP aplikacji (BARDZO WAŻNE!)
   adb shell am force-stop com.smscrypt.pro
✅ 7. Poczekaj 5 sekund
✅ 8. Szukaj NOWEJ nazwy w systemie
✅ 9. Uruchom
✅ 10. Ekran PIN w NOWYCH kolorach!
```

### **Dla zmiany języka:**
```
✅ 1. Language Settings
✅ 2. Wybierz język
✅ 3. SCROLL W DÓŁ!
✅ 4. Kliknij "OK - SAVE LANGUAGE"
✅ 5. Force Stop
✅ 6. Otwórz ponownie
✅ 7. Sprawdź język
```

### **Dla DATA DELETED:**
```
✅ 1. Ustaw PIN: 123456
✅ 2. Zamknij aplikację (całkowicie)
✅ 3. Otwórz ponownie
✅ 4. Wpisz ZŁE PIN 5 RAZY (różne numery!)
✅ 5. Po 5. próbie → DIALOG!
```

---

## 🧪 **DEBUG - Sprawdź logi:**

```powershell
# Otwórz terminal i zostaw otwarty:
adb logcat -c  # Wyczyść stare logi
adb logcat | Select-String "Pin|Theme|Language|CLEAR|DATA DELET"

# Teraz testuj aplikację - logi będą się pokazywać na żywo!
```

---

## 🎯 **NAJPROSTSZA METODA TESTU:**

### **Test DATA DELETED:**

```powershell
# 1. Odinstaluj starą:
adb uninstall com.smscrypt.pro

# 2. Zainstaluj nową:
adb install app\build\outputs\apk\release\app-release.apk

# 3. Uruchom aplikację na telefonie

# 4. Ustaw PIN: 123456

# 5. Zamknij aplikację (swipe up w recent apps)

# 6. Otwórz ponownie

# 7. Wpisz 5 RAZY zły PIN:
#    111111 → 4 remaining
#    222222 → 3 remaining
#    333333 → 2 remaining
#    444444 → 1 remaining
#    555555 → DIALOG "DATA DELETED"!

# 8. Jeśli NIE pokazał się dialog - wyślij mi logi:
adb logcat | grep "PinManager"
```

---

## 📱 **KTÓRE APK MASZ ZAINSTALOWANE?**

```powershell
# Sprawdź wersję:
adb shell dumpsys package com.smscrypt.pro | Select-String "versionName|lastUpdateTime"

# Jeśli lastUpdateTime nie jest z dzisiaj (23:39) - zainstaluj nowe!
```

---

## 🆘 **JEŚLI NADAL NIE DZIAŁA:**

**Wyślij mi:**

1. **Logi z DATA DELETED testu:**
   ```powershell
   adb logcat -c
   # Potem wpisz 5 złych PIN
   adb logcat | grep "Pin"
   ```

2. **Screenshot** ekranu App Appearance
   - Czy widzisz przycisk SAVE?

3. **Dokładny opis:**
   - Klikasz SAVE → co się dzieje?
   - Wpisujesz 5 złych PIN → co się dzieje?
   - Wybierasz język → co się dzieje?

---

## 📦 **NOWE APK (z naprawkami):**

```
Release: app/build/outputs/apk/release/app-release.apk (20.16 MB)
Debug: app/build/outputs/apk/debug/app-debug.apk (z logami)

Data build: 11.10.2025 23:39-23:42

Instalacja (WAŻNE - uninstall najpierw!):
adb uninstall com.smscrypt.pro
adb install app\build\outputs\apk\release\app-release.apk
```

---

**Zainstaluj NOWY APK i przetestuj!** 
**Jeśli nie działa - wyślij logi!** 🔍

---

**© 2025 ORYNTIUM powered by rhei**












