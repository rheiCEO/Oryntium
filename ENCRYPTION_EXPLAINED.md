# 🔐 Jak działa szyfrowanie w SMS Crypt Pro

## 📊 PROCES SZYFROWANIA (Wysyłanie SMS)

### Krok po kroku:

```
Wiadomość: "Spotkajmy się o 15:00"
Hasło kontaktu: "Krakow2021POLSKA"

1️⃣ GENEROWANIE LOSOWYCH DANYCH:
   IV (16 bajtów):   [A1, B2, C3, ..., P16]  ← Initialization Vector
   SALT (16 bajtów): [X1, Y2, Z3, ..., W16]  ← Salt dla PBKDF2

2️⃣ DERYWACJA KLUCZA (PBKDF2):
   Input: "Krakow2021POLSKA" + SALT
   Iteracje: 10,000 razy SHA-256
   Output: 256-bitowy klucz AES
   
   Klucz = PBKDF2("Krakow2021POLSKA", SALT, 10000, 256 bitów)

3️⃣ SZYFROWANIE (AES-256-CBC):
   Algorytm: AES-256
   Tryb: CBC (Cipher Block Chaining)
   IV: [A1, B2, C3, ..., P16]
   Klucz: [z kroku 2]
   
   Encrypted = AES_ENCRYPT("Spotkajmy się o 15:00", Klucz, IV)

4️⃣ ŁĄCZENIE WSZYSTKIEGO:
   Combined = IV + SALT + Encrypted
   [A1,B2...P16] + [X1,Y2...W16] + [zaszyfrowane dane]
   
5️⃣ KODOWANIE BASE64:
   Base64_Data = Base64.encode(Combined)
   
6️⃣ DODANIE PREFIXU:
   Final_SMS = "SMSCRYPT:" + Base64_Data
   
   Wynik: "SMSCRYPT:xK9mN3pQ7rS2tU5vW8xY1zA3bC6dE9fG2hJ4kL7..."
```

---

## 📥 PROCES DESZYFROWANIA (Odbieranie SMS)

### Krok po kroku:

```
Otrzymany SMS: "SMSCRYPT:xK9mN3pQ7rS2tU5vW8xY1zA3bC6dE9fG2hJ4kL7..."
Hasło kontaktu: "Krakow2021POLSKA"

1️⃣ USUNIĘCIE PREFIXU:
   "SMSCRYPT:xK9mN3..." → "xK9mN3..."

2️⃣ DEKODOWANIE BASE64:
   Base64_Data → Combined
   Combined = [wszystkie bajty]

3️⃣ ROZDZIELENIE DANYCH:
   IV = Combined[0:16]      ← Pierwsze 16 bajtów
   SALT = Combined[16:32]   ← Następne 16 bajtów
   Encrypted = Combined[32:] ← Reszta to zaszyfrowane dane

4️⃣ ODTWORZENIE KLUCZA (PBKDF2):
   ⚠️ WAŻNE: Używamy TEGO SAMEGO SALT co przy szyfrowaniu!
   
   Klucz = PBKDF2("Krakow2021POLSKA", SALT, 10000, 256 bitów)
   
   Dlaczego ten sam SALT?
   → Bo był w SMS! (Combined[16:32])
   → Bez tego samego SALT nie odzyskamy klucza!

5️⃣ DESZYFROWANIE (AES-256-CBC):
   Decrypted = AES_DECRYPT(Encrypted, Klucz, IV)
   
   Używamy:
   - Klucza z kroku 4 (z SALT z SMS!)
   - IV z SMS (Combined[0:16])
   
6️⃣ WYNIK:
   "Spotkajmy się o 15:00"
```

---

## 🔑 DLACZEGO SALT I IV SĄ W SMS?

### IV (Initialization Vector):
- **Musi być losowy** dla każdej wiadomości
- **Musi być znany** przy deszyfrowaniu
- **Nie jest tajny** - można go przesłać jawnie
- **Cel**: Ta sama wiadomość da inny ciphertext każdy raz

### SALT:
- **Musi być losowy** dla każdego szyfrowania
- **Musi być taki sam** przy derywacji klucza
- **Nie jest tajny** - można go przesłać jawnie
- **Cel**: To samo hasło da inny klucz każdy raz

### Format SMS:
```
SMSCRYPT:[IV + SALT + Encrypted Data] w Base64

Wszystko w SMS:
┌─────────────────────────────────────┐
│ SMSCRYPT:                           │ ← Prefix (7 znaków)
│ [16 bajtów IV]                      │ ← Losowy IV
│ [16 bajtów SALT]                    │ ← Losowy SALT
│ [N bajtów Encrypted]                │ ← Zaszyfrowana wiadomość
└─────────────────────────────────────┘
         ↓
    Base64 encode
         ↓
SMSCRYPT:xK9mN3pQ7rS2tU5vW8xY1zA3bC6dE9fG2hJ4kL7mN0pQ3rS6tU9vW...
```

---

## 🔒 BEZPIECZEŃSTWO

### Co jest tajne?
- ✅ **Hasło kontaktu**: "Krakow2021POLSKA" (NIE w SMS!)
- ✅ **Treść wiadomości**: "Spotkajmy się..." (zaszyfrowana)
- ✅ **Klucz AES**: (wygenerowany, NIE w SMS!)

### Co jest jawne (w SMS)?
- ✅ **Prefix**: "SMSCRYPT:" (żeby rozpoznać zaszyfrowane)
- ✅ **IV**: Potrzebny do deszyfrowania
- ✅ **SALT**: Potrzebny do odtworzenia klucza

### Dlaczego to bezpieczne?
**Bez hasła "Krakow2021POLSKA" nie da się odszyfrować!**
- IV i SALT są losowe
- Klucz jest generowany z hasła + SALT
- Nawet mając IV i SALT, bez hasła nie odszyfrujesz

---

## 🧪 PRZYKŁAD:

### Szyfrowanie:
```
Hasło: "SecretPass123"
Wiadomość: "Hello"

Losowe:
IV   = [12, 45, 78, ...]  ← 16 losowych bajtów
SALT = [99, 33, 67, ...]  ← 16 losowych bajtów

Klucz = PBKDF2("SecretPass123", SALT, 10000)
      = [AA, BB, CC, ...] ← 32 bajty (256 bitów)

Encrypted = AES("Hello", Klucz, IV)
          = [E1, E2, E3, ...]

SMS = "SMSCRYPT:" + Base64([IV + SALT + Encrypted])
```

### Deszyfrowanie (odbiorca):
```
Otrzymany SMS: "SMSCRYPT:xK9mN3pQ7rS..."
Hasło: "SecretPass123" ← Musi znać to samo hasło!

Base64 decode → [IV + SALT + Encrypted]

Wyciągnij:
IV   = [12, 45, 78, ...]  ← Z SMS
SALT = [99, 33, 67, ...]  ← Z SMS

Odtwórz klucz:
Klucz = PBKDF2("SecretPass123", SALT, 10000)
      = [AA, BB, CC, ...] ← Ten sam klucz co przy szyfrowaniu!

Odszyfruj:
Decrypted = AES_DECRYPT(Encrypted, Klucz, IV)
          = "Hello"
```

---

## ❓ FAQ

**Q: Czy to bezpieczne przesyłać SALT i IV w SMS?**
A: TAK! To standard AES-256. SALT i IV nie są tajne.

**Q: Czy ktoś może odszyfrować bez hasła?**
A: NIE! Bez hasła "Krakow2021POLSKA" nie odtworzysz klucza.

**Q: Dlaczego SALT jest losowy?**
A: Żeby to samo hasło dało inny klucz każdy raz (rainbow table protection).

**Q: Dlaczego IV jest losowy?**
A: Żeby ta sama wiadomość dała inny ciphertext każdy raz.

---

**© 2025 ORYNTIUM powered by rhei**













