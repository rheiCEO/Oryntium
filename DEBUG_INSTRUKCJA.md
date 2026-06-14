# 🐛 Jak debugować SMS Crypt Pro

## 📱 JAK SPRAWDZIĆ DLACZEGO SMS SIĘ NIE DESZYFRUJĄ

### Krok 1: Otwórz Logcat w Android Studio

```
1. Uruchom aplikację w Android Studio (Run > Run 'app')
2. View > Tool Windows > Logcat
3. W filtrze wpisz: "SmsReceiver"
```

### Krok 2: Wyślij zaszyfrowany SMS

Z drugiego telefonu wyślij SMS z aplikacji

### Krok 3: Zobacz logi w Logcat

Powinieneś zobaczyć:
```
📱 Processing SMS from: +48123456789
📝 Message starts with: SMSCRYPT:xK9mN3pQ...
🔐 Is encrypted: true
🔍 Looking for contact: +48123456789 (normalized: +48123456789)
✅ Contact found: Paweł
✅ Message decrypted successfully: Hello world...
💾 Encrypted SMS saved
```

### Krok 4: Jeśli widzisz błędy:

#### Błąd A: "⚠️ Contact not found"
```
PROBLEM: Numer telefonu w SMS nie pasuje do numeru w kontakcie
ROZWIĄZANIE:
1. Sprawdź numer nadawcy w logu
2. Sprawdź numer w kontakcie (Contacts)
3. Upewnij się że są identyczne (z +48 lub bez)
```

#### Błąd B: "❌ Decryption failed"
```
PROBLEM: Hasło jest inne niż przy szyfrowaniu
MOŻLIWE PRZYCZYNY:
1. Nadawca użył innego hasła
2. Hasło w kontakcie jest błędne
3. SMS jest uszkodzony

ROZWIĄZANIE:
1. Upewnij się że nadawca i odbiorca mają TO SAMO hasło
2. Sprawdź czy nie ma literówki w haśle
```

#### Błąd C: "❌ Could not load encryption key"
```
PROBLEM: Nie można odszyfrować hasła kontaktu z bazy
ROZWIĄZANIE:
1. Usuń kontakt i dodaj ponownie
2. Upewnij się że hasło jest poprawnie zapisane
```

---

## 🔍 JAK PRZETESTOWAĆ KROK PO KROKU

### Test 1: Dodaj kontakt z hasłem

```
1. Otwórz aplikację
2. Contacts → kliknij "+"
3. Nazwa: "Test"
4. Numer: "+48123456789" ← DOKŁADNIE ten format!
5. Hasło: "TestPassword123" ← Zapamiętaj!
6. Save
```

### Test 2: Wyślij zaszyfrowany SMS (z drugiego telefonu)

**OPCJA A: Jeśli masz drugi telefon z aplikacją:**
```
1. Zainstaluj aplikację na telefonie B
2. Dodaj kontakt telefonu A:
   - Numer: "+48123456789" ← Ten sam format!
   - Hasło: "TestPassword123" ← To SAMO hasło!
3. Wyślij wiadomość: "Hello encrypted"
4. Sprawdź czy na telefonie A się odszyfruje
```

**OPCJA B: Test Quick SMS (sam do siebie):**
```
1. Home → Quick SMS
2. Numer: Twój własny numer
3. Wiadomość: "Test message"
4. Encryption Key: "TestPassword123"
5. Send
6. Sprawdź w zwykłej aplikacji SMS czy przyszło: "SMSCRYPT:..."
```

### Test 3: Zobacz logi

W Logcat szukaj:
```
SmsReceiver: 📱 Processing SMS from...
SmsReceiver: 🔐 Is encrypted: true
SmsReceiver: ✅ Contact found...
SmsReceiver: ✅ Message decrypted successfully...
```

Jeśli widzisz **❌** to tam jest problem!

---

## 🔧 NAJCZĘSTSZE PROBLEMY I ROZWIĄZANIA

### Problem 1: "Encrypted message from unknown contact"

**Dlaczego:**
Numer telefonu nadawcy nie pasuje do numeru w kontakcie

**Przykład:**
- W kontakcie: `+48123456789`
- SMS przychodzi od: `48123456789` (bez +)
- Lub: `+48 123 456 789` (ze spacjami)

**Rozwiązanie:**
```
Teraz aplikacja normalizuje numery!
- Usuwa spacje, myślniki, nawiasy
- Porównuje ostatnie 9 cyfr
- Powinno działać!
```

**Jeśli nadal nie działa:**
1. Zobacz w logu jaki numer przychodzi
2. Dodaj kontakt z DOKŁADNIE tym numerem

### Problem 2: "Decryption failed - wrong password"

**Dlaczego:**
Hasło u nadawcy i odbiorcy jest INNE!

**Rozwiązanie:**
```
1. Upewnij się że OBA telefony mają TO SAMO hasło dla kontaktu!
2. Sprawdź wielkość liter (case-sensitive!)
3. Sprawdź czy nie ma spacji przed/po haśle
```

### Problem 3: "Could not load encryption key"

**Dlaczego:**
Hasło kontaktu nie może być odszyfrowane z bazy

**Rozwiązanie:**
```
1. Usuń kontakt
2. Dodaj ponownie z nowym hasłem
3. Wyślij testowy SMS
```

---

## 📊 CHECKLIST PRZED WYSŁANIEM SMS

### Nadawca (telefon A):
- ✅ Ma kontakt z numerem odbiorcy
- ✅ Hasło ustawione: np. "TestPassword123"
- ✅ Encryption toggle: ON

### Odbiorca (telefon B):
- ✅ Ma kontakt z numerem nadawcy
- ✅ To SAMO hasło: "TestPassword123"
- ✅ Aplikacja zainstalowana i działa

### Format numeru:
```
✅ DOBRZE: +48123456789
✅ DOBRZE: 48123456789
✅ DOBRZE: +48 123 456 789 (normalizacja zadziała)

❌ ŹLE: Numery są różne na obu telefonach
❌ ŹLE: Hasła są różne
```

---

## 🧪 TEST DESZYFROWANIA (MANUAL)

### Możesz przetestować w kodzie:

```kotlin
// Zaszyfruj
val encrypted = encryptionManager.encrypt("Test message", "Password123")
println("Encrypted: $encrypted")
// Wynik: SMSCRYPT:xK9mN3pQ...

// Odszyfruj
val decrypted = encryptionManager.decrypt(encrypted, "Password123")
println("Decrypted: $decrypted")
// Wynik: Test message
```

Jeśli to działa w kodzie, problem jest w:
- Dopasowaniu numeru telefonu
- Lub różnych hasłach między telefonami

---

## 🗑️ TEST KASOWANIA DANYCH (po 5 błędnych PIN)

### Krok po kroku:

```
1. Restart aplikacji
2. Wpisz BŁĘDNY PIN: 111111
3. Powinieneś zobaczyć: "Incorrect PIN. 4 attempts remaining"
4. Wpisz BŁĘDNY PIN 4 razy więcej
5. Po 5. próbie → wszystkie dane kasują się
6. Aplikacja pokazuje: "Create a 6-digit PIN" (od nowa)
7. Sprawdź Contacts → powinno być PUSTE!
8. Sprawdź Messages → powinno być PUSTE!
```

### W Logcat zobaczysz:
```
PinManager: 🗑️ CLEARING ALL DATA - Too many failed PIN attempts!
PinManager: ✅ Database cleared
PinManager: ✅ PIN cleared
PinManager: ✅ Encryption keys cleared
PinManager: ✅ Language preferences cleared
PinManager: 🎉 ALL DATA CLEARED SUCCESSFULLY!
```

---

## 💡 PORADY DEBUGOWANIA

### Zobacz WSZYSTKIE logi aplikacji:
```
Logcat filter: "com.smscrypt.pro"
```

### Zobacz tylko błędy:
```
Logcat filter: "Error|Failed|Exception"
Level: Error
```

### Zobacz szyfrowanie:
```
Logcat filter: "EncryptionManager|SmsReceiver"
```

### Zobacz PIN:
```
Logcat filter: "PinManager|PinViewModel"
```

---

## 🎯 CO NAPRAWIŁEM:

### 1. Kasowanie danych ✅
```kotlin
// Teraz faktycznie kasuje:
database.clearAllData() → usuwa kontakty i SMS
pinDataStore.clear() → usuwa PIN
encryptionDataStore.clear() → usuwa klucze
```

### 2. Dopasowanie numerów ✅
```kotlin
// Próbuje 3 sposoby:
1. Exact match: "+48123456789"
2. Normalized: "48123456789"
3. Last 9 digits match: "123456789"
```

### 3. Lepsze logi ✅
```
Teraz widać w Logcat:
- Który numer przyszedł
- Czy kontakt znaleziony
- Czy deszyfrowanie udane
- Jakie błędy wystąpiły
```

---

## 🚀 ZAINSTALUJ NOWY APK I TESTUJ!

```powershell
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

Potem:
1. Otwórz Logcat w Android Studio
2. Wyślij zaszyfrowany SMS
3. Zobacz logi - zobaczysz dokładnie co się dzieje!

**W logach zobaczysz dlaczego deszyfrowanie nie działa!** 🔍

---

**© 2025 ORYNTIUM powered by rhei**












