# 🔐 SMS CRYPT PRO - SZCZEGÓŁOWY PROMPT APLIKACJI

## 📋 SPIS TREŚCI
1. [Ogólny Opis Aplikacji](#1-ogólny-opis-aplikacji)
2. [Architektura Techniczna](#2-architektura-techniczna)
3. [Funkcjonalności - Szczegółowy Opis](#3-funkcjonalności-szczegółowy-opis)
4. [System Design - Kolorystyka i UI](#4-system-design-kolorystyka-i-ui)
5. [Ekrany Aplikacji](#5-ekrany-aplikacji)
6. [Komponenty UI](#6-komponenty-ui)
7. [Bezpieczeństwo](#7-bezpieczeństwo)
8. [Przepływ Danych](#8-przepływ-danych)

---

## 1. OGÓLNY OPIS APLIKACJI

### 1.1 Nazwa i Cel
**Nazwa:** SMS Crypt Pro  
**Przeznaczenie:** Profesjonalna aplikacja Android do szyfrowania wiadomości SMS

### 1.2 Główna Funkcjonalność
Aplikacja umożliwia bezpieczną komunikację SMS poprzez szyfrowanie wiadomości przy użyciu wspólnych haseł z kontaktami. Każdy kontakt może mieć unikalne hasło szyfrujące (np. "Krakow2021POLSKA" z Pawłem, "KochamCie*$#@!" z Justyną).

### 1.3 Grupa Docelowa
- Użytkownicy potrzebujący bezpiecznej komunikacji SMS
- Osoby wymagające prywatności w komunikacji
- Użytkownicy Android 11+ (API 30+)

---

## 2. ARCHITEKTURA TECHNICZNA

### 2.1 Stack Technologiczny
- **Język:** Kotlin
- **UI Framework:** Jetpack Compose
- **Design System:** Material Design 3 z motywem cyberpunk
- **Database:** Room Database (lokalna, szyfrowana)
- **Dependency Injection:** Hilt
- **Biblioteka kryptograficzna:** BouncyCastle
- **API SMS:** Android SMS Manager API

### 2.2 Architektura Aplikacji
```
MVVM (Model-View-ViewModel) + Clean Architecture

┌─────────────────────────────────────┐
│         UI Layer (Compose)          │
│  - Screens (Home, Chat, Contacts)   │
│  - Components (Cards, Buttons)      │
│  - Theme (Colors, Typography)       │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      ViewModel Layer (Hilt)         │
│  - ChatViewModel                    │
│  - ContactsViewModel                │
│  - HomeViewModel                    │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         Data Layer                  │
│  - Room Database (AppDatabase)      │
│  - DAO (ContactDao, SmsDao)         │
│  - Models (Contact, SmsMessage)     │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│       Services & Managers           │
│  - EncryptionManager (AES-256)      │
│  - SmsManager (SMS handling)        │
│  - SmsReceiver (BroadcastReceiver)  │
└─────────────────────────────────────┘
```

### 2.3 Struktura Pakietów
```
com.smscrypt.pro/
├── crypto/
│   └── EncryptionManager.kt          # Moduł szyfrowania
├── data/
│   ├── database/
│   │   ├── AppDatabase.kt            # Baza danych Room
│   │   ├── ContactDao.kt             # DAO kontaktów
│   │   └── SmsDao.kt                 # DAO wiadomości
│   ├── model/
│   │   ├── Contact.kt                # Model kontaktu
│   │   └── SmsMessage.kt             # Model wiadomości
│   └── preferences/
│       ├── LanguageManager.kt        # Zarządzanie językiem
│       └── PasswordManager.kt        # Zarządzanie hasłami
├── di/
│   ├── DatabaseModule.kt             # DI dla bazy danych
│   └── ContextModule.kt              # DI dla kontekstu
├── receiver/
│   └── SmsReceiver.kt                # Przechwytywanie SMS
├── service/
│   └── SmsManager.kt                 # Zarządzanie SMS
├── ui/
│   ├── components/
│   │   ├── AnimatedCard.kt           # Animowane karty
│   │   └── GradientButton.kt         # Przyciski z gradientem
│   ├── screens/
│   │   ├── home/
│   │   │   ├── HomeScreen.kt         # Ekran główny
│   │   │   └── HomeViewModel.kt
│   │   ├── chat/
│   │   │   ├── ChatScreen.kt         # Ekran czatu
│   │   │   └── ChatViewModel.kt
│   │   ├── contacts/
│   │   │   ├── ContactsScreen.kt     # Lista kontaktów
│   │   │   └── ContactsViewModel.kt
│   │   ├── settings/
│   │   │   ├── SettingsScreen.kt     # Ustawienia
│   │   │   ├── LanguageSettings.kt
│   │   │   ├── MessageStorage.kt
│   │   │   ├── InfoScreen.kt
│   │   │   └── SubscriptionPlan.kt
│   │   ├── permissions/
│   │   │   └── SmsPermissionScreen.kt
│   │   └── startup/
│   │       └── StartupScreen.kt      # Ekran powitalny
│   ├── navigation/
│   │   └── SmsCryptNavigation.kt     # Nawigacja
│   └── theme/
│       ├── Color.kt                  # Paleta kolorów
│       ├── Theme.kt                  # Motyw aplikacji
│       └── Type.kt                   # Typografia
└── MainActivity.kt                   # Activity główna
```

---

## 3. FUNKCJONALNOŚCI - SZCZEGÓŁOWY OPIS

### 3.1 Zarządzanie Kontaktami

#### 3.1.1 Dodawanie Kontaktu
**Funkcja:** Użytkownik może dodać nowy kontakt z unikalnym hasłem szyfrującym

**Proces:**
1. Użytkownik klika przycisk "+" (FloatingActionButton) na ekranie Contacts
2. Otwiera się dialog `AddContactDialog`
3. Użytkownik wypełnia pola:
   - **Nazwa kontaktu** (np. "Paweł")
   - **Numer telefonu** (np. "+48123456789")
   - **Hasło szyfrujące** (np. "Krakow2021POLSKA")
4. Po kliknięciu "Save":
   - Hasło jest szyfrowane lokalnie (EncryptionManager.encryptForLocalStorage)
   - Kontakt jest zapisywany w Room Database
   - Dialog się zamyka
   - Lista kontaktów się odświeża

**Walidacja:**
- Wszystkie pola muszą być wypełnione
- Przycisk "Save" jest nieaktywny gdy pola są puste

**Wynik:**
- Kontakt pojawia się na liście z:
  - Awatarem (pierwsza litera imienia)
  - Imieniem i numerem telefonu
  - Ikoną kłódki (🔒) oznaczającą aktywne szyfrowanie
  - Przyciskami edycji i usuwania

#### 3.1.2 Edycja Kontaktu
**Funkcja:** Możliwość zmiany danych kontaktu i hasła

**Proces:**
1. Użytkownik klika ikonę edycji (✏️) przy kontakcie
2. Otwiera się dialog `EditContactDialog` z wypełnionymi danymi
3. Użytkownik może zmienić:
   - Nazwę
   - Numer telefonu
   - Hasło (pole opcjonalne - jeśli puste, stare hasło pozostaje)
4. Po kliknięciu "Save":
   - Nowe hasło (jeśli podane) jest szyfrowane
   - Dane są aktualizowane w bazie
   - Lista się odświeża

#### 3.1.3 Usuwanie Kontaktu
**Funkcja:** Usunięcie kontaktu z bazy

**Proces:**
1. Użytkownik klika ikonę kosza (🗑️) przy kontakcie
2. Kontakt jest usuwany z bazy danych
3. Lista się aktualizuje

#### 3.1.4 Wyszukiwanie Kontaktów
**Funkcja:** Filtrowanie listy kontaktów

**Proces:**
1. Użytkownik wpisuje tekst w pole wyszukiwania
2. Lista filtruje się w czasie rzeczywistym po:
   - Nazwie kontaktu (case-insensitive)
   - Numerze telefonu

### 3.2 Wysyłanie Wiadomości

#### 3.2.1 Quick SMS (Szybka wiadomość)
**Funkcja:** Wysłanie jednorazowej wiadomości bez zapisywania kontaktu

**Proces:**
1. Na ekranie Home użytkownik klika "Quick SMS"
2. Otwiera się dialog `QuickSmsDialog`
3. Wypełnia pola:
   - **Numer telefonu** (np. "+48123456789")
   - **Wiadomość** (np. "Spotkajmy się o 15:00")
   - **Klucz szyfrowania** (opcjonalnie, np. "TajneHaslo123")
4. Po kliknięciu "Send":
   - Jeśli podano klucz: wiadomość jest szyfrowana i wysyłana
   - Jeśli nie podano klucza: wiadomość jest wysyłana normalnie
   - Dialog się zamyka

#### 3.2.2 Wysyłanie z Czatu
**Funkcja:** Wysyłanie wiadomości do zapisanego kontaktu

**Proces:**
1. Użytkownik otwiera czat z kontaktem
2. U dołu ekranu widzi:
   - **Przełącznik "Encryption"** (Switch)
     - Włączony (zielony): wiadomości będą szyfrowane
     - Wyłączony (szary): wiadomości zwykłe
   - **Pole tekstowe** z placeholderem:
     - "Encrypted message..." gdy szyfrowanie włączone
     - "Type message..." gdy szyfrowanie wyłączone
   - **Przycisk Send**:
     - Ikona kłódki (🔒) gdy szyfrowanie włączone
     - Ikona samolotu (✈️) gdy szyfrowanie wyłączone
3. Po wpisaniu wiadomości i kliknięciu Send:
   - Wiadomość jest szyfrowana (jeśli włączone) hasłem kontaktu
   - Wysyłana przez SMS Manager
   - Zapisywana w bazie danych
   - Pojawia się w czacie jako wysłana (po prawej)
   - Pole tekstowe się czyści
   - Lista automatycznie przewija się do dołu

**Szczegóły techniczne:**
- Szyfrowanie: `EncryptionManager.encrypt(message, password)`
- Format wysyłanej wiadomości: `"SCRYPT:" + Base64(encrypted)`
- Prefix "SCRYPT:" oznacza zaszyfrowaną wiadomość

### 3.3 Odbieranie Wiadomości

#### 3.3.1 Automatyczne Przechwytywanie
**Funkcja:** SmsReceiver automatycznie przechwytuje przychodzące SMS

**Proces:**
1. Przychodzi SMS
2. `SmsReceiver` (BroadcastReceiver) przechwytuje wiadomość
3. System sprawdza prefix:
   - **Jeśli "SCRYPT:":**
     - Szuka kontaktu po numerze telefonu w bazie
     - Odszyfrowuje hasłem kontaktu
     - Zapisuje odszyfrowaną wiadomość
   - **Jeśli brak "SCRYPT:":**
     - Zapisuje jako zwykłą wiadomość
4. Wiadomość pojawia się w czacie (po lewej stronie)
5. Jeśli użytkownik ma otwarty czat, lista się automatycznie przewija

#### 3.3.2 Wyświetlanie w Czacie
**Format wiadomości:**
- **Przychodzące:** po lewej stronie, szare tło
- **Wysłane:** po prawej stronie, niebieskie tło
- **Ikona kłódki:** dla zaszyfrowanych wiadomości
- **Timestamp:** godzina wysłania (HH:mm)

### 3.4 Bezpieczeństwo Wiadomości

#### 3.4.1 Algorytmy Szyfrowania
```
Szczegóły implementacji:

1. AES-256-CBC:
   - Algorytm: Advanced Encryption Standard
   - Długość klucza: 256 bitów
   - Tryb: Cipher Block Chaining (CBC)
   - IV (Initialization Vector): 16 bajtów losowych

2. PBKDF2-SHA256:
   - Key Derivation: Password-Based Key Derivation Function 2
   - Hash: SHA-256
   - Iteracje: 10,000
   - Cel: Przekształcenie hasła tekstowego w klucz 256-bitowy

3. Base64 Encoding:
   - Kodowanie binarnych danych zaszyfrowanych do tekstu
   - Umożliwia wysyłanie przez SMS (tylko znaki ASCII)
```

#### 3.4.2 Przechowywanie Haseł
- Hasła kontaktów są szyfrowane lokalnie kluczem specyficznym dla urządzenia
- Klucz lokalny: generowany przez EncryptionManager
- Migracja: przy pierwszym uruchomieniu hasła są migrowane do nowego systemu szyfrowania

#### 3.4.3 Usuwanie Wiadomości
**Funkcja:** Automatyczne lub manualne usuwanie historii czatu

**Opcje usuwania:**
1. **Ostatnia 1 godzina**
2. **Ostatnie 24 godziny**
3. **Ostatnie 7 dni**
4. **Ostatnie 30 dni**
5. **Wszystkie wiadomości** (czerwony przycisk)

**Proces:**
1. Użytkownik klika ikonę kosza w górnym pasku czatu
2. Otwiera się dialog `DeleteMessagesDialog`
3. Wybiera opcję usuwania
4. Potwierdzenie
5. Wiadomości są trwale usuwane z bazy

### 3.5 Ustawienia Aplikacji

#### 3.5.1 Ustawienia Języka
**Dostępne języki:**
- 🇬🇧 English (en)
- 🇪🇸 Español (es)
- 🇩🇪 Deutsch (de)
- 🇷🇺 Русский (ru)
- 🇨🇳 中文 (zh)

**Proces zmiany:**
1. Kliknięcie na kartę "Language Settings"
2. Wybór języka z listy
3. Aplikacja zapisuje wybór w SharedPreferences
4. Restart Activity z nowym językiem

#### 3.5.2 Zarządzanie Przechowywaniem Wiadomości
**Opcje:**
- 7 dni
- 30 dni
- 90 dni
- 1 rok
- Bez limitu (Unlimited)

**Funkcja:** Automatyczne usuwanie starych wiadomości po upływie czasu

#### 3.5.3 Status Bezpieczeństwa
**Wyświetlane informacje:**
- ✅ **AES-256:** Status szyfrowania
- ✅ **PBKDF2:** Status generowania kluczy
- ✅ **Base64:** Status kodowania
- Status uprawnień SMS (SEND, READ, RECEIVE)

#### 3.5.4 Plan Subskrypcji
**Wyświetlane informacje:**
- Aktualny plan: **Free**
- Możliwość upgrade'u (przyszła funkcjonalność)

#### 3.5.5 Informacje o Aplikacji
**Zawartość:**
- Wersja aplikacji
- O aplikacji
- Licencja (MIT)
- Copyright: © 2025 ORYNTIUM powered by rhei

---

## 4. SYSTEM DESIGN - KOLORYSTYKA I UI

### 4.1 Paleta Kolorów (Cyberpunk Dark Theme)

#### 4.1.1 Kolory Główne
```kotlin
// Primary Colors - Cyberpunk Blue
Primary:        #00D4FF (Jasny niebieski cyan)
PrimaryVariant: #0099CC (Ciemniejszy niebieski)
OnPrimary:      #000000 (Czarny tekst na primary)

// Secondary Colors - Electric Purple
Secondary:        #8B5CF6 (Elektryczny fiolet)
SecondaryVariant: #6D28D9 (Ciemny fiolet)
OnSecondary:      #FFFFFF (Biały tekst na secondary)

// Background Colors - Dark Theme
Background: #0A0A0A (Prawie czarny)
Surface:    #1A1A1A (Ciemny szary)
OnBackground: #FFFFFF (Biały tekst)
OnSurface:    #FFFFFF (Biały tekst)

// Accent Colors
AccentGreen:  #00D4FF (używany jako Primary)
AccentOrange: #8B5CF6 (używany jako Secondary)
AccentPink:   #8B5CF6 (używany jako Secondary)
AccentYellow: #00D4FF (używany jako Primary)
AccentBlue:   #00D4FF (używany jako Primary)

// Status Colors
Success: #00D4FF (Cyan - sukces)
Warning: #8B5CF6 (Fiolet - ostrzeżenie)
Error:   #FF4444 (Czerwony - błąd)
Info:    #00D4FF (Cyan - informacja)

// Surface Variants
SurfaceVariant:   #2A2A2A (Jaśniejszy szary)
OnSurfaceVariant: #CCCCCC (Jasny szary tekst)

// Outline
Outline:        #404040 (Ciemny szary border)
OutlineVariant: #606060 (Jaśniejszy border)
```

#### 4.1.2 Zastosowanie Kolorów

**Tła:**
- Główne tło aplikacji: `#0A0A0A` (prawie czarne)
- Karty/Surface: `#1A1A1A` (ciemny szary)
- Pola tekstowe: `#1A1A1A`

**Akcenty:**
- Przyciski główne: Gradient `#00D4FF → #8B5CF6`
- Ikony aktywne: `#00D4FF` (cyan)
- Status szyfrowania: `#00D4FF` (kłódka)
- Przełączniki ON: `#00D4FF`

**Tekst:**
- Tytuły: `#FFFFFF` (biały)
- Podtytuły: `#FFFFFF` z alpha 0.7
- Tekst pomocniczy: `#FFFFFF` z alpha 0.5
- Placeholdery: `#FFFFFF` z alpha 0.6

**Stany:**
- Sukces/Aktywne: `#00D4FF` (cyan)
- Błąd: `#FF4444` (czerwony)
- Wyłączone: `#FFFFFF` z alpha 0.5

### 4.2 Typografia

#### 4.2.1 Hierarchia Tekstu
```kotlin
h4: 34sp, Bold              // Tytuły główne (Home, Contacts, Settings)
h5: 24sp, Bold              // Tytuły sekcji
h6: 20sp, Bold              // Tytuły kart, nazwy kontaktów
body1: 16sp, Regular        // Tekst podstawowy, wiadomości
body2: 14sp, Regular        // Tekst pomocniczy, opisy
caption: 12sp, Regular      // Timestampy, małe etykiety
```

#### 4.2.2 Fonty
- **Podstawowy:** System default (Roboto na Android)
- **Styl:** Modern, clean, czytelny w ciemnym motywie

### 4.3 Ikony i Symbole

#### 4.3.1 Ikony Nawigacji
```
🏠 Home:       Icons.Default.Home
📱 Contacts:   Icons.Default.Contacts
⚙️ Settings:    Icons.Default.Settings
💬 Chat:        Icons.AutoMirrored.Filled.Message
```

#### 4.3.2 Ikony Akcji
```
➕ Dodaj:       Icons.Default.Add
✏️ Edytuj:      Icons.Default.Edit
🗑️ Usuń:       Icons.Default.Delete
🔍 Szukaj:     Icons.Default.Search
↻ Odśwież:    Icons.Default.Refresh
✈️ Wyślij:     Icons.AutoMirrored.Filled.Send
🔒 Szyfruj:    Icons.Default.Lock
ℹ️ Info:       Icons.Default.Info
⚠️ Ostrzeżenie: Icons.Default.Warning
✓ Sukces:     Icons.Default.CheckCircle
```

#### 4.3.3 Ikony Statusu
```
🔒 Zaszyfrowane:    Icons.Default.Lock (kolor: #00D4FF)
↑ Wysłane:         Icons.Default.ArrowUpward (#8B5CF6)
↓ Odebrane:        Icons.Default.ArrowDownward (#00D4FF)
✓ Aktywne:         Icons.Default.CheckCircle (#00D4FF)
```

### 4.4 Komponenty UI

#### 4.4.1 Kształty i Zaokrąglenia
```kotlin
Card:           RoundedCornerShape(12.dp)
Button:         RoundedCornerShape(8.dp)
TextField:      RoundedCornerShape(8.dp)
Avatar:         CircleShape (okrągły)
MessageBubble:  RoundedCornerShape(16.dp z asymetrią)
```

#### 4.4.2 Cienie i Elevation
```kotlin
Card elevation:     4.dp
FAB elevation:      6.dp
TopAppBar elevation: 0.dp (flat design)
```

#### 4.4.3 Spacing (Odstępy)
```kotlin
Padding małe:       8.dp
Padding standardowe: 16.dp
Padding duże:       24.dp
Spacing między elementami: 12.dp
Spacing w liście:   12.dp
```

### 4.5 Animacje

#### 4.5.1 AnimatedCard
**Efekt:** Karta reaguje na kliknięcie

**Animacja:**
- Scale: 1.0 → 0.98 (100ms)
- Glow effect: Alpha 0 → 0.3 (200ms)
- Efekt radialny gradient (Primary → Secondary)

**Trigger:** onPress

#### 4.5.2 Logo Animacja
**Efekt:** Obracający się diament (diamond shape)

**Animacja:**
- Rotation: 0° → 360° (3000ms)
- Loop: Infinite
- Easing: Linear

**Lokalizacja:** Ekran Home, obok tytułu

#### 4.5.3 Scroll Animations
**Auto-scroll do dołu:**
- Gdy nowa wiadomość przychodzi
- Gdy użytkownik wysyła wiadomość
- Smooth animation: `animateScrollToItem()`

### 4.6 Gradient Buttons

#### 4.6.1 GradientButton Główny
**Wygląd:**
- Gradient: `Primary → Secondary` lub `Secondary → Primary`
- Wysokość: 48.dp
- Zaokrąglenie: 8.dp
- Tekst: Bold, biały

**Zastosowanie:**
- "Quick SMS" button
- "Add Contacts" button
- Przyciski akcji głównych

#### 4.6.2 Przykładowe Użycie
```kotlin
GradientButton(
    onClick = { /* akcja */ },
    modifier = Modifier.weight(1f),
    colors = listOf(Primary, Secondary)
) {
    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
    Spacer(modifier = Modifier.width(8.dp))
    Text("Quick SMS")
}
```

---

## 5. EKRANY APLIKACJI

### 5.1 Startup Screen (Ekran Powitalny)

**Cel:** Pierwsze uruchomienie, animacja powitalna

**Zawartość:**
- Logo aplikacji (animowany diament)
- Nazwa "SMS Crypt Pro"
- Loading indicator
- Czas wyświetlania: 2-3 sekundy

**Przejście:** Automatycznie do SmsPermissionScreen lub Home

---

### 5.2 SMS Permission Screen

**Cel:** Zapytanie o uprawnienia SMS

**Wymagane uprawnienia:**
1. READ_SMS
2. SEND_SMS
3. RECEIVE_SMS
4. READ_CONTACTS (opcjonalne)

**Layout:**
```
┌─────────────────────────────────┐
│                                 │
│    🔒 Ikona kłódki (duża)       │
│                                 │
│    "SMS Permissions Required"   │
│                                 │
│    Opis: "To send encrypted..."│
│                                 │
│    [Lista uprawnień z ikonami]  │
│    ✓ Send SMS                   │
│    ✓ Read SMS                   │
│    ✓ Receive SMS                │
│                                 │
│    [Grant Permissions - Button] │
│                                 │
└─────────────────────────────────┘
```

**Przejście:** Po przyznaniu uprawnień → Home Screen

---

### 5.3 Home Screen (Ekran Główny)

**Layout:**
```
┌─────────────────────────────────────────┐
│ ◇ SMS Crypt Pro              ↻         │ ← Animowany diament + Refresh
│ Secure messaging                        │
│                                         │
│ ┌──────────────┐ ┌──────────────┐     │
│ │ Quick SMS ✈️ │ │ Contacts 👤 │     │ ← Gradient buttons
│ └──────────────┘ └──────────────┘     │
│                                         │
│ Recent Messages                         │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ 👤 Paweł            🔒↓          │  │ ← Message card
│ │ 2 minutes ago                     │  │
│ │ "Spotkajmy się..."               │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ 👤 Justyna          🔒↑          │  │
│ │ 1 hour ago                        │  │
│ │ "OK, będę..."                     │  │
│ └───────────────────────────────────┘  │
│                                         │
│                                         │
│ © 2025 ORYNTIUM powered by rhei        │ ← Copyright footer
│                                         │
│ [🏠]  [📱]  [⚙️]                       │ ← Bottom navigation
└─────────────────────────────────────────┘
```

**Elementy:**
1. **Header:**
   - Obracający się diament (logo)
   - Tytuł "SMS Crypt Pro"
   - Podtytuł "Secure messaging"
   - Przycisk Refresh

2. **Quick Actions:**
   - "Quick SMS" button (gradient cyan→purple)
   - "Contacts" button (gradient purple→cyan)

3. **Recent Messages (3 ostatnie):**
   - Avatar z pierwszą literą
   - Nazwa kontaktu / numer
   - Timestamp (relatywny: "2 minutes ago", "1 hour ago")
   - Fragment wiadomości (max 2 linie)
   - Ikony statusu:
     - 🔒 = zaszyfrowana
     - ↓ = odebrana (cyan)
     - ↑ = wysłana (purple)

4. **Empty State:**
   - Jeśli brak wiadomości:
     - Ikona 💬 (duża, szara)
     - "No messages yet"
     - "Send your first encrypted message"

5. **Footer:**
   - Copyright: "© 2025 ORYNTIUM powered by rhei"

6. **Bottom Navigation:**
   - 🏠 Home (aktywny: cyan)
   - 📱 Contacts
   - ⚙️ Settings

---

### 5.4 Contacts Screen (Lista Kontaktów)

**Layout:**
```
┌─────────────────────────────────────────┐
│ Contacts                          ➕    │ ← FAB (Floating Action)
│                                         │
│ ┌─────────────────────────────────────┐│
│ │ 🔍 Search contacts...               ││ ← Search field
│ └─────────────────────────────────────┘│
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ [P] Paweł                         │  │ ← Contact card
│ │     +48123456789                  │  │
│ │     🔒 Encryption active     ✏️🗑️ │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ [J] Justyna                       │  │
│ │     +48987654321                  │  │
│ │     🔒 Encryption active     ✏️🗑️ │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ [M] Marek                         │  │
│ │     +48555666777                  │  │
│ │     🔒 Encryption active     ✏️🗑️ │  │
│ └───────────────────────────────────┘  │
│                                         │
│ [🏠]  [📱]  [⚙️]                       │
└─────────────────────────────────────────┘
```

**Elementy:**

1. **Header:**
   - Tytuł "Contacts" (bold, cyan)
   - FloatingActionButton "+" (cyan, po prawej)

2. **Search Bar:**
   - Ikona 🔍 po lewej
   - Placeholder: "Search contacts..."
   - Border: cyan gdy fokus

3. **Contact Card:**
   - **Avatar:** Okrąg z gradientem (cyan→purple)
     - Pierwsza litera imienia (biała, bold)
   - **Nazwa kontaktu** (bold, biały)
   - **Numer telefonu** (szary)
   - **Status:** 🔒 "Encryption active" (cyan)
   - **Akcje:**
     - ✏️ Edit (cyan)
     - 🗑️ Delete (red)

4. **Empty State:**
   - Jeśli brak kontaktów:
     - Ikona 👤 (duża, szara)
     - "No contacts yet"
     - "Add your first contact to start secure messaging"

5. **Kliknięcie karty:**
   - Otwiera Chat Screen z tym kontaktem

---

### 5.5 Chat Screen (Ekran Czatu)

**Layout:**
```
┌─────────────────────────────────────────┐
│ ← Paweł                         ℹ️ 🗑️  │ ← Top bar
│   🔒 Encryption active                  │
├─────────────────────────────────────────┤
│                                         │
│         ┌─────────────────┐            │
│         │ Hi Paweł!       │            │ ← Wysłana (prawa)
│         │ 🔒 Encrypted    │            │
│         │ 14:30           │            │
│         └─────────────────┘            │
│                                         │
│ ┌─────────────────┐                    │
│ │ Hello! How are  │                    │ ← Odebrana (lewa)
│ │ you?            │                    │
│ │ 🔒 Encrypted    │                    │
│ │ 14:32           │                    │
│ └─────────────────┘                    │
│                                         │
│         ┌─────────────────┐            │
│         │ I'm good, thanks│            │ ← Wysłana
│         │ 14:35           │            │
│         └─────────────────┘            │
│                                         │
├─────────────────────────────────────────┤
│ Encryption             [ON ●]           │ ← Przełącznik
│                                         │
│ [Type message...]              [🔒]    │ ← Input field + Send
└─────────────────────────────────────────┘
```

**Elementy:**

1. **Top App Bar:**
   - **← Przycisk powrotu**
   - **Nazwa kontaktu** (bold) / numer telefonu
   - **Status:** 🔒 "Encryption active" (jeśli kontakt ma hasło)
   - **Akcje:**
     - ℹ️ Info (przyszła funkcjonalność)
     - 🗑️ Delete messages

2. **Message List:**
   - **Wiadomości wysłane (po prawej):**
     - Tło: `Primary` (#00D4FF)
     - Tekst: biały
     - Zaokrąglenie: asymetryczne (prawy dolny róg mniejszy)
   - **Wiadomości odebrane (po lewej):**
     - Tło: `Surface` (#1A1A1A)
     - Tekst: biały
     - Zaokrąglenie: asymetryczne (lewy dolny róg mniejszy)
   - **Ikona szyfrowania:** 🔒 "Encrypted" dla zaszyfrowanych
   - **Timestamp:** HH:mm (np. 14:30)

3. **Empty State:**
   - Jeśli brak wiadomości:
     - Ikona 💬 (szara)
     - "Start conversation"
     - "Send your first message"

4. **Message Input (dół ekranu):**
   - **Przełącznik Encryption:**
     - Label: "Encryption"
     - Switch: ON (cyan) / OFF (szary)
   - **Pole tekstowe:**
     - Placeholder: "Encrypted message..." (gdy ON)
     - Placeholder: "Type message..." (gdy OFF)
     - Border: cyan gdy fokus
   - **Przycisk Send:**
     - Ikona: 🔒 (gdy szyfrowanie ON, tło cyan)
     - Ikona: ✈️ (gdy szyfrowanie OFF, tło cyan)
     - Disabled: gdy pole puste (szary)

5. **Auto-scroll:**
   - Automatyczne przewinięcie do najnowszej wiadomości
   - Smooth animation

---

### 5.6 Settings Screen (Ustawienia)

**Layout:**
```
┌─────────────────────────────────────────┐
│ Settings                                │
│ Configure your app                      │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ 🛡️ Security Status                │  │ ← Security card
│ │ All systems active                 │  │
│ │                                    │  │
│ │ AES-256      PBKDF2      Base64   │  │
│ │ Encryption   Keys        Encoding  │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ 📱 SMS Permissions                │  │ ← Permissions card
│ │ All permissions granted            │  │
│ │ ✓ SEND_SMS                         │  │
│ │ ✓ READ_SMS                         │  │
│ │ ✓ RECEIVE_SMS                      │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ 🌐 Language Settings         →    │  │ ← Clickable cards
│ │ Change app language                │  │
│ │ Current: English                   │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ 💾 Message Storage           →    │  │
│ │ Auto-delete messages               │  │
│ │ Duration: Unlimited                │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ⭐ Subscription Plan          →    │  │
│ │ Manage subscription                │  │
│ │ Current: Free                      │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ℹ️ About App                  →    │  │
│ │                                    │  │
│ └───────────────────────────────────┘  │
│                                         │
│ [🏠]  [📱]  [⚙️]                       │
└─────────────────────────────────────────┘
```

**Karty:**

1. **Security Status Card:**
   - Ikona 🛡️ Security (cyan)
   - Status: "All systems active" (cyan)
   - Metryki bezpieczeństwa:
     - **AES-256** (Primary color)
     - **PBKDF2** (Secondary color)
     - **Base64** (AccentOrange)

2. **SMS Permissions Card:**
   - Status uprawnień
   - Lista z checkmarkami (✓ cyan / ✗ red)

3. **Language Settings Card:**
   - Ikona 🌐 (Primary)
   - "Change app language"
   - Aktualny język: np. "English"
   - Strzałka → po prawej

4. **Message Storage Card:**
   - Ikona 💾 (AccentBlue)
   - "Auto-delete messages"
   - Aktualny czas: np. "Unlimited", "7 days"
   - Strzałka → po prawej

5. **Subscription Plan Card:**
   - Ikona ⭐ (AccentYellow)
   - "Manage subscription"
   - Plan: "Free" (cyan)
   - Strzałka → po prawej

6. **About App Card:**
   - Ikona ℹ️ (Primary)
   - Strzałka → po prawej

---

### 5.7 Language Settings Screen

**Layout:**
```
┌─────────────────────────────────────────┐
│ ← Language                              │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ● English                         ✓│  │ ← Selected
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ○ Español                          │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ○ Deutsch                          │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ○ Русский                          │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ○ 中文                             │  │
│ └───────────────────────────────────┘  │
│                                         │
└─────────────────────────────────────────┘
```

**Funkcjonalność:**
- Radio buttons dla każdego języka
- Checkmark ✓ przy aktywnym
- Kliknięcie zmienia język i zapisuje w SharedPreferences

---

### 5.8 Message Storage Settings Screen

**Layout:**
```
┌─────────────────────────────────────────┐
│ ← Message Storage                       │
│                                         │
│ Auto-delete messages older than:       │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ○ 7 days                           │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ○ 30 days                          │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ○ 90 days                          │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ○ 1 year                           │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ● Unlimited                       ✓│  │ ← Default
│ └───────────────────────────────────┘  │
│                                         │
└─────────────────────────────────────────┘
```

---

### 5.9 Subscription Plan Screen

**Layout:**
```
┌─────────────────────────────────────────┐
│ ← Subscription                          │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ 🆓 FREE PLAN              [ACTIVE]│  │
│ │                                    │  │
│ │ ✓ Unlimited contacts               │  │
│ │ ✓ AES-256 encryption               │  │
│ │ ✓ Local storage                    │  │
│ │ ✓ Basic features                   │  │
│ │                                    │  │
│ └───────────────────────────────────┘  │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ ⭐ PREMIUM PLAN       $4.99/month │  │
│ │                                    │  │
│ │ ✓ Everything in Free               │  │
│ │ ✓ Cloud backup                     │  │
│ │ ✓ Multi-device sync                │  │
│ │ ✓ Priority support                 │  │
│ │                                    │  │
│ │        [Upgrade to Premium]        │  │
│ └───────────────────────────────────┘  │
│                                         │
└─────────────────────────────────────────┘
```

---

### 5.10 Info Screen

**Layout:**
```
┌─────────────────────────────────────────┐
│ ← About                                 │
│                                         │
│         ◇ Logo (duże)                   │
│                                         │
│      SMS Crypt Pro                      │
│      Version 1.0.0                      │
│                                         │
│ ┌───────────────────────────────────┐  │
│ │ Professional SMS encryption app    │  │
│ │ for Android with AES-256 security. │  │
│ └───────────────────────────────────┘  │
│                                         │
│ 🔒 Features:                            │
│ • End-to-end encryption                 │
│ • Individual passwords per contact      │
│ • Local secure storage                  │
│ • Auto message cleanup                  │
│ • Multi-language support                │
│                                         │
│ 📜 License: MIT                         │
│                                         │
│ © 2025 ORYNTIUM powered by rhei        │
│                                         │
└─────────────────────────────────────────┘
```

---

## 6. KOMPONENTY UI

### 6.1 AnimatedCard

**Opis:** Karta z efektem animacji przy kliknięciu

**Parametry:**
```kotlin
@Composable
fun AnimatedCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    content: @Composable ColumnScope.() -> Unit
)
```

**Animacje:**
- **Scale:** 1.0 → 0.98 (100ms) przy naciśnięciu
- **Glow:** Radial gradient z alpha 0 → 0.3 (200ms)
- **Kolory glow:** Primary → Secondary → Transparent

**Wygląd:**
- Zaokrąglenie: 12.dp
- Elevation: 4.dp
- Tło: Surface color (#1A1A1A)

**Użycie:**
- Contact cards
- Message preview cards
- Settings action cards

---

### 6.2 GradientButton

**Opis:** Przycisk z gradientowym tłem

**Parametry:**
```kotlin
@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Primary, Secondary),
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
)
```

**Wygląd:**
- Gradient: Horizontal, od lewej do prawej
- Zaokrąglenie: 8.dp
- Wysokość: 48.dp
- Padding: 16.dp horizontal

**Stany:**
- **Enabled:** Pełne kolory gradientu
- **Disabled:** Alpha 0.5

**Użycie:**
- "Quick SMS" button
- "Add Contacts" button
- "Send Message" button (opcjonalnie)

---

### 6.3 MessageBubble

**Opis:** Bąbelek wiadomości w czacie

**Parametry:**
```kotlin
@Composable
fun MessageBubble(
    message: SmsMessage,
    isFromMe: Boolean
)
```

**Wygląd wiadomości wysłanej (isFromMe = true):**
- Pozycja: po prawej
- Tło: Primary (#00D4FF)
- Tekst: biały
- Zaokrąglenie: topStart=16dp, topEnd=16dp, bottomStart=16dp, bottomEnd=4dp

**Wygląd wiadomości odebranej (isFromMe = false):**
- Pozycja: po lewej
- Tło: Surface (#1A1A1A)
- Tekst: biały
- Zaokrąglenie: topStart=16dp, topEnd=16dp, bottomStart=4dp, bottomEnd=16dp

**Zawartość:**
1. Ikona 🔒 + "Encrypted" (jeśli zaszyfrowana)
2. Treść wiadomości
3. Timestamp (HH:mm)

**Max szerokość:** 280.dp

---

### 6.4 ContactCard

**Opis:** Karta kontaktu na liście

**Zawartość:**
```
┌────────────────────────────────┐
│ [P]  Paweł                     │
│      +48123456789              │
│      🔒 Encryption active  ✏️🗑️│
└────────────────────────────────┘
```

**Elementy:**
1. **Avatar:**
   - Okrąg 48.dp
   - Gradient tło (Primary → Secondary)
   - Pierwsza litera imienia (bold, biała)

2. **Informacje:**
   - Nazwa (h6, bold, biała)
   - Numer telefonu (body1, szary)
   - Status: 🔒 "Encryption active" (cyan)

3. **Akcje:**
   - ✏️ Edit button (cyan)
   - 🗑️ Delete button (red)

**Interakcja:**
- Kliknięcie karty → otwiera Chat Screen
- Kliknięcie Edit → otwiera Edit Dialog
- Kliknięcie Delete → usuwa kontakt

---

### 6.5 Dialog Komponenty

#### 6.5.1 AddContactDialog
**Pola:**
- Name (TextField)
- Phone Number (TextField)
- Password (TextField, PasswordVisualTransformation)
- Info text

**Przyciski:**
- Save (primary)
- Cancel (text button)

#### 6.5.2 EditContactDialog
**Pola:**
- Name (wypełnione)
- Phone Number (wypełniony)
- New Password (opcjonalne)
- Info text

**Przyciski:**
- Save (primary)
- Cancel (text button)

#### 6.5.3 QuickSmsDialog
**Pola:**
- Phone Number (TextField)
- Message (TextField, maxLines=3)
- Encryption Key (TextField, PasswordVisualTransformation, opcjonalne)
- Info text

**Przyciski:**
- Send (primary, enabled gdy phone i message wypełnione)
- Cancel (text button)

#### 6.5.4 DeleteMessagesDialog
**Opcje (przyciski):**
- Delete last 1 hour
- Delete last 24 hours
- Delete last 7 days
- Delete last 30 days
- Delete all messages (red, destructive)

**Przycisk:**
- Cancel (text button)

---

### 6.6 Empty State Components

#### 6.6.1 EmptyStateCard (Home)
```
┌────────────────────────────────┐
│         💬                     │
│    No messages yet             │
│ Send your first encrypted...  │
└────────────────────────────────┘
```

#### 6.6.2 EmptyContactsCard
```
┌────────────────────────────────┐
│         👤                     │
│    No contacts yet             │
│ Add your first contact to...  │
└────────────────────────────────┘
```

#### 6.6.3 EmptyChatCard
```
┌────────────────────────────────┐
│         💬                     │
│    Start conversation          │
│ Send your first message        │
└────────────────────────────────┘
```

---

## 7. BEZPIECZEŃSTWO

### 7.1 Algorytmy Szyfrowania

#### 7.1.1 AES-256-CBC
**Szczegóły:**
- Algorithm: Advanced Encryption Standard
- Key size: 256 bits
- Mode: Cipher Block Chaining (CBC)
- IV: 16 bytes random (generowany dla każdej wiadomości)
- Padding: PKCS5Padding

**Implementacja:**
```kotlin
// Pseudo-kod
fun encrypt(plaintext: String, password: String): String {
    val key = deriveKey(password) // PBKDF2
    val iv = generateRandomIV(16) // 16 bajtów losowych
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key, iv)
    val encrypted = cipher.doFinal(plaintext.toByteArray())
    val combined = iv + encrypted // IV + dane zaszyfrowane
    return Base64.encode(combined) // Kodowanie Base64
}
```

#### 7.1.2 PBKDF2-SHA256
**Szczegóły:**
- Algorithm: Password-Based Key Derivation Function 2
- Hash function: SHA-256
- Iterations: 10,000
- Salt: 16 bytes random (przechowywany z kluczem)
- Output: 256-bit key

**Implementacja:**
```kotlin
// Pseudo-kod
fun deriveKey(password: String): SecretKey {
    val salt = generateRandomSalt(16)
    val spec = PBEKeySpec(
        password.toCharArray(),
        salt,
        10000, // iterations
        256    // key length
    )
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
    return factory.generateSecret(spec)
}
```

#### 7.1.3 Base64 Encoding
**Cel:** Konwersja danych binarnych do tekstu ASCII dla SMS

**Format wiadomości SMS:**
```
SCRYPT:[Base64 encoded data]

Przykład:
SCRYPT:4k3j5h6g7f8d9s0a1b2c3d4e5f6g7h8i9j0k...
```

### 7.2 Przechowywanie Haseł

#### 7.2.1 Lokalne Szyfrowanie Haseł
**Proces:**
1. Użytkownik wprowadza hasło dla kontaktu (np. "Krakow2021")
2. Hasło jest szyfrowane kluczem specyficznym dla urządzenia
3. Zaszyfrowane hasło jest zapisywane w Room Database
4. Klucz urządzenia jest generowany przy pierwszym uruchomieniu

**Implementacja:**
```kotlin
// Pseudo-kod
fun encryptForLocalStorage(password: String): String {
    val deviceKey = getOrCreateDeviceKey()
    return encrypt(password, deviceKey)
}

fun decryptFromLocalStorage(encryptedPassword: String): String {
    val deviceKey = getDeviceKey()
    return decrypt(encryptedPassword, deviceKey)
}
```

#### 7.2.2 Migracja Haseł
**Cel:** Aktualizacja haseł do nowego systemu szyfrowania

**Proces przy starcie aplikacji:**
1. MainActivity.onCreate() uruchamia migrację
2. Wszystkie kontakty są wczytywane z bazy
3. Każde hasło jest migrowane do nowego formatu
4. Zaktualizowane kontakty są zapisywane

### 7.3 Format Wiadomości

#### 7.3.1 Zaszyfrowana Wiadomość
**Struktura SMS:**
```
SCRYPT:[IV + Encrypted Data] w Base64

Breakdown:
1. Prefix: "SCRYPT:" (identyfikacja zaszyfrowanej wiadomości)
2. IV: 16 bajtów (Initialization Vector)
3. Encrypted Data: Dane zaszyfrowane AES-256-CBC
4. Całość zakodowana Base64
```

**Przykład:**
```
Oryginalna wiadomość: "Spotkajmy się o 15:00"
Hasło: "Krakow2021POLSKA"

Zaszyfrowany SMS:
SCRYPT:xK9mN3pQ7rS2tU5vW8xY1zA3bC6dE9fG2hJ4kL7mN0pQ3rS6tU9vW2xY5zA8bC1dE4fG7hJ0kL3mN6pQ9rS2tU...
```

#### 7.3.2 Niezaszyfrowana Wiadomość
**Struktura:** Zwykły tekst bez prefiksu
```
Przykład:
Spotkajmy się o 15:00
```

### 7.4 Odbieranie i Deszyfrowanie

#### 7.4.1 Proces odbierania SMS
```kotlin
// Pseudo-kod
fun onSmsReceived(sender: String, message: String) {
    if (message.startsWith("SCRYPT:")) {
        // Zaszyfrowana wiadomość
        val contact = database.getContactByPhone(sender)
        if (contact != null) {
            val password = decryptFromLocalStorage(contact.encryptedPassword)
            val decrypted = decrypt(message.removePrefix("SCRYPT:"), password)
            saveSmsToDatabase(sender, decrypted, isEncrypted=true, isIncoming=true)
        } else {
            // Kontakt nie istnieje - nie można odszyfrować
            saveSmsToDatabase(sender, "[Encrypted - Unknown contact]", isEncrypted=true, isIncoming=true)
        }
    } else {
        // Niezaszyfrowana wiadomość
        saveSmsToDatabase(sender, message, isEncrypted=false, isIncoming=true)
    }
}
```

### 7.5 Zabezpieczenia Bazy Danych

**Room Database Security:**
- Baza danych jest przechowywana lokalnie
- Wyłączona backup do chmury (android:allowBackup="false")
- Hasła kontaktów zaszyfrowane
- Baza nie jest dostępna bez root

**Lokalizacja:**
```
/data/data/com.smscrypt.pro/databases/sms_crypt.db
```

---

## 8. PRZEPŁYW DANYCH

### 8.1 Architektura MVVM

```
┌─────────────┐
│   View      │ (Compose UI)
│  (Screen)   │
└──────┬──────┘
       │ observe State
       │ call Actions
┌──────▼──────┐
│  ViewModel  │ (Hilt)
│             │
└──────┬──────┘
       │ access data
       │ via Repository/DAO
┌──────▼──────┐
│    Model    │ (Room Database)
│   (Data)    │
└─────────────┘
```

### 8.2 Flow Danych - Dodawanie Kontaktu

```
1. User clicks "+"
   └─> ContactsScreen shows AddContactDialog

2. User fills form and clicks "Save"
   └─> ContactsViewModel.addContact(name, phone, password)
       └─> EncryptionManager.encryptForLocalStorage(password)
           └─> returns encryptedPassword
       └─> Create Contact object
       └─> ContactDao.insertContact(contact)
           └─> Room Database saves

3. ContactDao triggers Flow update
   └─> ContactsViewModel observes Flow
       └─> Updates uiState.contacts
           └─> ContactsScreen recomposes
               └─> New contact appears in list
```

### 8.3 Flow Danych - Wysyłanie Zaszyfrowanej Wiadomości

```
1. User types message in ChatScreen
   └─> ChatViewModel.updateMessageText(text)
       └─> Updates uiState.messageText

2. User toggles Encryption ON
   └─> ChatViewModel.toggleEncryption()
       └─> Updates uiState.isEncrypted = true

3. User clicks Send button
   └─> ChatViewModel.sendMessage(phoneNumber)
       └─> Load contact from database
           └─> ContactDao.getContactByPhone(phoneNumber)
       └─> Decrypt password from storage
           └─> EncryptionManager.decryptFromLocalStorage(contact.encryptedPassword)
       └─> Encrypt message
           └─> EncryptionManager.encrypt(messageText, password)
               └─> Derive key using PBKDF2
               └─> Generate random IV
               └─> Encrypt using AES-256-CBC
               └─> Combine IV + encrypted data
               └─> Encode Base64
               └─> Add prefix "SCRYPT:"
       └─> Send SMS
           └─> SmsManager.sendEncryptedSms(phoneNumber, encryptedMessage, password)
               └─> Android SMS API sends
       └─> Save to database
           └─> SmsDao.insertMessage(smsMessage)
       └─> Clear input field
           └─> Updates uiState.messageText = ""
       └─> Scroll to bottom

4. ChatScreen recomposes
   └─> New message appears in chat bubble (right side, blue)
```

### 8.4 Flow Danych - Odbieranie Zaszyfrowanej Wiadomości

```
1. SMS arrives on device
   └─> Android broadcasts SMS_RECEIVED
       └─> SmsReceiver.onReceive()
           └─> Extract sender and message
           └─> Check if message starts with "SCRYPT:"

2. If encrypted:
   └─> SmsReceiver processes
       └─> Load contact from database
           └─> ContactDao.getContactByPhone(sender)
       └─> Decrypt password
           └─> EncryptionManager.decryptFromLocalStorage(contact.encryptedPassword)
       └─> Decrypt message
           └─> Remove "SCRYPT:" prefix
           └─> Decode Base64
           └─> Extract IV (first 16 bytes)
           └─> Extract encrypted data
           └─> Derive key using PBKDF2
           └─> Decrypt using AES-256-CBC
       └─> Save to database
           └─> SmsDao.insertMessage(
                   id = UUID.random(),
                   phoneNumber = sender,
                   message = decryptedText,
                   isEncrypted = true,
                   isIncoming = true,
                   timestamp = now()
               )

3. If ChatScreen is open:
   └─> ChatViewModel observes Flow
       └─> SmsDao.getMessagesForContact(phoneNumber).collect()
           └─> Updates uiState.messages
               └─> ChatScreen recomposes
                   └─> New message appears (left side, gray)
                   └─> Auto-scroll to bottom

4. If Home screen is open:
   └─> HomeViewModel observes Flow
       └─> SmsDao.getRecentMessages(3).collect()
           └─> Updates uiState.recentMessages
               └─> HomeScreen recomposes
                   └─> Recent message preview updated
```

### 8.5 Flow Danych - Wyszukiwanie Kontaktów

```
1. User types in search field
   └─> ContactsScreen calls viewModel.updateSearchQuery(query)
       └─> Updates uiState.searchQuery

2. ContactsViewModel observes:
   └─> combine(
           database.contactDao().getAllActiveContacts(),
           _uiState
       )
       └─> Filters contacts based on searchQuery
           if (query.isBlank()) {
               return all contacts
           } else {
               return contacts.filter {
                   name.contains(query, ignoreCase) ||
                   phoneNumber.contains(query)
               }
           }
       └─> Updates uiState.filteredContacts

3. ContactsScreen recomposes
   └─> Displays filtered list
```

### 8.6 State Management

#### 8.6.1 ChatUiState
```kotlin
data class ChatUiState(
    val messages: List<SmsMessage> = emptyList(),
    val messageText: String = "",
    val contactName: String? = null,
    val isEncrypted: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
```

#### 8.6.2 ContactsUiState
```kotlin
data class ContactsUiState(
    val contacts: List<Contact> = emptyList(),
    val filteredContacts: List<Contact> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val showAddContactDialog: Boolean = false,
    val showEditContactDialog: Boolean = false,
    val editingContact: Contact? = null,
    val error: String? = null
)
```

#### 8.6.3 HomeUiState
```kotlin
data class HomeUiState(
    val recentMessages: List<SmsMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
```

---

## 9. PRZYCISKI I INTERAKCJE

### 9.1 Główne Przyciski

#### 9.1.1 FloatingActionButton "+"
- **Lokalizacja:** Contacts Screen, prawy górny róg
- **Wygląd:** Okrągły, cyan (#00D4FF)
- **Ikona:** "+" (biały)
- **Akcja:** Otwiera AddContactDialog
- **Elevation:** 6.dp

#### 9.1.2 "Quick SMS" Button
- **Lokalizacja:** Home Screen, lewa strona
- **Wygląd:** Gradient (cyan → purple)
- **Ikona:** ✈️ (biały)
- **Tekst:** "Quick SMS" (biały, bold)
- **Akcja:** Otwiera QuickSmsDialog
- **Wysokość:** 48.dp

#### 9.1.3 "Contacts" Button (Quick Action)
- **Lokalizacja:** Home Screen, prawa strona
- **Wygląd:** Gradient (purple → cyan)
- **Ikona:** 👤 (biały)
- **Tekst:** "Contacts" (biały, bold)
- **Akcja:** Nawigacja do Contacts Screen
- **Wysokość:** 48.dp

#### 9.1.4 "Send" Button (Chat)
- **Lokalizacja:** Chat Screen, prawy dolny róg przy input
- **Wygląd:** 
  - Encryption ON: cyan tło, ikona 🔒
  - Encryption OFF: cyan tło, ikona ✈️
- **Stan disabled:** Szary, gdy pole puste
- **Akcja:** Wysyła wiadomość
- **Rozmiar:** 48x48.dp

#### 9.1.5 "Save" Button (Dialogs)
- **Lokalizacja:** Dialogi (AddContact, EditContact)
- **Wygląd:** Text button, cyan tekst
- **Stan disabled:** Szary, gdy wymagane pola puste
- **Akcja:** Zapisuje dane i zamyka dialog

#### 9.1.6 "Cancel" Button (Dialogs)
- **Lokalizacja:** Wszystkie dialogi
- **Wygląd:** Text button, szary tekst
- **Akcja:** Zamyka dialog bez zapisywania

### 9.2 Przyciski Akcji

#### 9.2.1 Edit Button (Ikona)
- **Lokalizacja:** Contact Card, prawa strona
- **Ikona:** ✏️ Edit (cyan)
- **Rozmiar:** 24.dp
- **Akcja:** Otwiera EditContactDialog

#### 9.2.2 Delete Button (Ikona)
- **Lokalizacja:** Contact Card, prawa strona
- **Ikona:** 🗑️ Delete (red #FF4444)
- **Rozmiar:** 24.dp
- **Akcja:** Usuwa kontakt

#### 9.2.3 Refresh Button
- **Lokalizacja:** Home Screen, prawy górny róg
- **Ikona:** ↻ Refresh (cyan)
- **Rozmiar:** 24.dp
- **Akcja:** Odświeża listę recent messages

#### 9.2.4 Info Button
- **Lokalizacja:** Chat Screen, top bar
- **Ikona:** ℹ️ Info (biały)
- **Rozmiar:** 24.dp
- **Akcja:** Pokaż info kontaktu (przyszła funkcjonalność)

#### 9.2.5 Delete Messages Button
- **Lokalizacja:** Chat Screen, top bar
- **Ikona:** 🗑️ Delete (biały)
- **Rozmiar:** 24.dp
- **Akcja:** Otwiera DeleteMessagesDialog

#### 9.2.6 Back Button
- **Lokalizacja:** Top bar wszystkich sub-screenów
- **Ikona:** ← ArrowBack (biały)
- **Rozmiar:** 24.dp
- **Akcja:** Nawigacja wstecz

### 9.3 Przełączniki

#### 9.3.1 Encryption Switch
- **Lokalizacja:** Chat Screen, nad message input
- **Wygląd:**
  - ON: Cyan thumb (#00D4FF), cyan track (alpha 0.5)
  - OFF: Szary thumb, szary track
- **Label:** "Encryption" (biały tekst)
- **Akcja:** Włącza/wyłącza szyfrowanie dla nowej wiadomości

### 9.4 Karty Klikalne

#### 9.4.1 Message Preview Card
- **Lokalizacja:** Home Screen
- **Wygląd:** AnimatedCard z efektem scale + glow
- **Akcja:** Otwiera Chat Screen z tym kontaktem
- **Animacja:** Scale 1.0 → 0.98, glow 0 → 0.3

#### 9.4.2 Contact Card
- **Lokalizacja:** Contacts Screen
- **Wygląd:** AnimatedCard
- **Akcja:** Otwiera Chat Screen z tym kontaktem
- **Animacja:** Scale + glow effect

#### 9.4.3 Settings Cards
- **Lokalizacja:** Settings Screen
- **Wygląd:** AnimatedCard ze strzałką →
- **Akcja:** Nawigacja do pod-ekranu (Language, Storage, etc.)
- **Animacja:** Scale + glow effect

### 9.5 Bottom Navigation

#### 9.5.1 Home Button
- **Ikona:** 🏠 Home
- **Aktywny:** Cyan (#00D4FF)
- **Nieaktywny:** Szary (#CCCCCC)
- **Akcja:** Nawigacja do Home Screen

#### 9.5.2 Contacts Button
- **Ikona:** 📱 Contacts
- **Aktywny:** Cyan
- **Nieaktywny:** Szary
- **Akcja:** Nawigacja do Contacts Screen

#### 9.5.3 Settings Button
- **Ikona:** ⚙️ Settings
- **Aktywny:** Cyan
- **Nieaktywny:** Szary
- **Akcja:** Nawigacja do Settings Screen

---

## 10. SZCZEGÓŁOWE WYMAGANIA FUNKCJONALNE

### 10.1 Wymagania Bezpieczeństwa

1. **Szyfrowanie wiadomości:**
   - MUSI używać AES-256-CBC
   - MUSI generować losowy IV dla każdej wiadomości
   - MUSI używać PBKDF2 z 10,000 iteracjami
   - MUSI dodawać prefix "SCRYPT:" do zaszyfrowanych SMS

2. **Przechowywanie haseł:**
   - Hasła kontaktów MUSZĄ być zaszyfrowane lokalnie
   - Klucz urządzenia MUSI być unikalny dla każdego urządzenia
   - Baza danych NIE MOŻE być backupowana do chmury

3. **Bezpieczeństwo komunikacji:**
   - Aplikacja NIE MOŻE wysyłać danych przez internet
   - Wszystkie dane MUSZĄ być przechowywane lokalnie
   - Hasła NIE MOGĄ być wyświetlane w logach

### 10.2 Wymagania Wydajnościowe

1. **Czas szyfrowania:**
   - Szyfrowanie wiadomości MUSI trwać < 100ms
   - Deszyfrowanie MUSI trwać < 100ms

2. **UI Responsiveness:**
   - Wszystkie animacje MUSZĄ działać płynnie (60 FPS)
   - Lista wiadomości MUSI przewijać się płynnie (LazyColumn)
   - Wyszukiwanie kontaktów MUSI działać w czasie rzeczywistym

3. **Baza danych:**
   - Operacje CRUD MUSZĄ być asynchroniczne (coroutines)
   - Flow MUSI automatycznie aktualizować UI

### 10.3 Wymagania UI/UX

1. **Dark Theme:**
   - Aplikacja MUSI używać ciemnego motywu
   - Wszystkie teksty MUSZĄ być czytelne na ciemnym tle
   - Kolory akcentowe MUSZĄ być zgodne z paletą cyberpunk

2. **Animacje:**
   - Karty MUSZĄ mieć efekt scale + glow przy kliknięciu
   - Logo MUSI się obracać na ekranie głównym
   - Lista wiadomości MUSI auto-scrollować do dołu

3. **Feedback dla użytkownika:**
   - Błędy MUSZĄ być wyświetlane jako Snackbar
   - Ładowanie MUSI pokazywać CircularProgressIndicator
   - Przyciski MUSZĄ być disabled gdy dane niepełne

### 10.4 Wymagania Dostępności

1. **Content Descriptions:**
   - Wszystkie ikony MUSZĄ mieć contentDescription
   - Screen readers MUSZĄ móc odczytać wszystkie elementy

2. **Wsparcie języków:**
   - Aplikacja MUSI wspierać 5 języków (EN, ES, DE, RU, ZH)
   - Zmiana języka MUSI działać bez restartu aplikacji (tylko Activity)

### 10.5 Wymagania Techniczne

1. **Android:**
   - Minimum API 30 (Android 11)
   - Target API 34 (Android 14)

2. **Uprawnienia:**
   - SEND_SMS (obowiązkowe)
   - READ_SMS (obowiązkowe)
   - RECEIVE_SMS (obowiązkowe)
   - READ_CONTACTS (opcjonalne)

3. **Zależności:**
   - Kotlin 1.9+
   - Jetpack Compose 1.5+
   - Room 2.6+
   - Hilt 2.48+
   - BouncyCastle 1.70+

---

## 11. PODSUMOWANIE

### 11.1 Kluczowe Cechy Aplikacji

✅ **Bezpieczeństwo:**
- AES-256-CBC encryption
- PBKDF2 key derivation
- Lokalne przechowywanie danych
- Indywidualne hasła per kontakt

✅ **User Experience:**
- Intuicyjny interface
- Ciemny motyw cyberpunk
- Płynne animacje
- Responsywne UI

✅ **Funkcjonalność:**
- Automatyczne szyfrowanie/deszyfrowanie
- Zarządzanie kontaktami
- Historia wiadomości
- Multi-language support

✅ **Technologia:**
- Modern Kotlin + Compose
- Clean Architecture (MVVM)
- Room Database
- Dependency Injection (Hilt)

### 11.2 Unikalność Aplikacji

1. **Indywidualne hasła:** Każdy kontakt ma własne hasło szyfrujące
2. **Automatyzacja:** Automatyczne przechwytywanie i deszyfrowanie SMS
3. **Cyberpunk Design:** Unikalny ciemny motyw z neonowymi kolorami
4. **Bezpieczeństwo:** Wszystkie dane lokalne, brak komunikacji z internetem
5. **Open Source:** Kod dostępny, możliwość audytu bezpieczeństwa

---

## 12. COPYRIGHT

**© 2025 ORYNTIUM powered by rhei**

**Licencja:** MIT License  
**Przeznaczenie:** Educational and personal use  
**Security Notice:** Testować przed użyciem produkcyjnym

---

# KONIEC DOKUMENTACJI

