# 🧪 INSTRUKCJA TESTOWA - Prosta!

## 📱 **ZAINSTALUJ DEBUG APK (z logami):**

```powershell
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

---

## 🔴 **TEST 1: DATA DELETED (5 złych PIN)**

### **Krok po kroku:**

```
1. Otwórz aplikację

2. Ustaw PIN: 123456
   - Wpisz 123456
   - Potwierdź 123456
   - Splash Screen (3s)
   - Home

3. ZAMKNIJ APLIKACJĘ (całkowicie!)
   - Recent apps → swipe up

4. Otwórz ponownie

5. Wpisz ZŁY PIN 5 RAZY:
   - 111111 (Remaining: 4)
   - 222222 (Remaining: 3)
   - 333333 (Remaining: 2)
   - 444444 (Remaining: 1)
   - 555555 (Remaining: 0) ← POWINIEN POKAZAĆ DIALOG!

6. Sprawdź logi:
   adb logcat | grep "PinManager"
   
   Powinieneś zobaczyć:
   🗑️ CLEARING ALL DATA - Too many failed PIN attempts!
   ✅ Database cleared
   ✅ PIN cleared
   ✅ Encryption keys cleared
   ✅ Language preferences cleared
   🎉 ALL DATA CLEARED SUCCESSFULLY!
```

### **Co POWINNO się stać:**
```
✅ Po 5 złym PIN → Dialog "DATA DELETED"
✅ Czerwony krzyżyk pulsuje
✅ Tekst "SECURITY BREACH"
✅ Tekst "DATA DELETED"
✅ Przycisk OK
✅ Auto-zamknięcie po 10s
✅ Po zamknięciu → ekran "Create a 6-digit PIN"
✅ Wszystkie dane skasowane (kontakty, SMS)
```

### **Jeśli NIE działa:**
```
Sprawdź logi:
adb logcat | grep "Pin"

Wyślij mi output!
```

---

## 🌍 **TEST 2: ZMIANA JĘZYKA**

```
1. Settings → Language Settings

2. Kliknij "Polski"
   - Checkmark pojawi się ✅

3. SCROLL W DÓŁ!

4. Przycisk "OK - SAVE LANGUAGE" na dole

5. Kliknij przycisk

6. Sprawdź logi:
   adb logcat | grep "LanguageManager"

7. ZAMKNIJ i OTWÓRZ aplikację

8. Sprawdź czy teksty po polsku
```

### **Jeśli NIE działa - daj znać dokładnie co się dzieje!**

---

## 🎨 **TEST 3: ZMIANA MOTYWU**

```
1. Settings → App Appearance

2. Sprawdź czy widzisz:
   - Pole "App Name"
   - Siatka 10 kolorowych kart (2x5)
   
3. SCROLL W DÓŁ!
   - Czy widzisz przycisk "SAVE CHANGES"?
   - Jeśli NIE - scroll jeszcze niżej!

4. Wpisz nazwę: "Calculator"

5. Kliknij kartę Calculator (🟠 pomarańczowa)
   - Karta podświetli się ✅

6. Kliknij "SAVE CHANGES"

7. FORCE STOP aplikacji:
   adb shell am force-stop com.smscrypt.pro

8. Sprawdź logi:
   adb logcat | grep "ThemeManager"

9. Otwórz aplikację ponownie

10. SZUKAJ "Calculator" w systemie (nie ORYNTIUM!)

11. Uruchom

12. Ekran PIN powinien być POMARAŃCZOWY!
```

### **Jeśli NIE działa:**
```
Wyślij mi:
1. Screenshot ekranu App Appearance (czy widzisz przycisk?)
2. Logi: adb logcat | grep "Theme"
3. Co dokładnie się dzieje
```

---

## 📊 **SPRAWDŹ LOGI W CZASIE RZECZYWISTYM:**

```powershell
# Otwórz drugi terminal i uruchom:
adb logcat | Select-String "Pin|Theme|Language|CLEARING|DATA"

# To pokaże wszystkie ważne logi podczas testów!
```

---

## 🆘 **JEŚLI NIC NIE DZIAŁA:**

### **Wyślij mi:**

```
1. Output z:
   adb logcat | grep "PinManager"
   
2. Output z:
   adb logcat | grep "ThemeManager"
   
3. Screenshot ekranu:
   - Settings → App Appearance
   - (czy widzisz przycisk SAVE CHANGES?)

4. Co dokładnie się dzieje:
   - Wpisujesz 5 złych PIN → co się pokazuje?
   - Klikasz motyw → co się dzieje?
   - Klikasz SAVE → czy wraca do Settings?
```

---

## 📦 **DEBUG APK:**

```
Lokalizacja: app/build/outputs/apk/debug/app-debug.apk

Ma logi Debug (więcej informacji)
Łatwiej zdiagnozować problemy

Instalacja:
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

---

**Zainstaluj Debug APK i przetestuj z logami!**  
**Daj znać co dokładnie nie działa!** 🔍

---

**© 2025 ORYNTIUM powered by rhei**












