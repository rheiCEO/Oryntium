# 🚀 ORYNTIUM - Instalacja na Hostinger

Kompletny przewodnik instalacji motywu ORYNTIUM na hostingu Hostinger.

## 📋 Wymagania

- ✅ Hosting Hostinger z WordPress
- ✅ Panel hPanel
- ✅ WordPress 5.8+ zainstalowany

---

## 🎯 Metoda 1: Przez Panel WordPress (NAJŁATWIEJSZE - 5 minut)

### Krok 1: Spakuj motyw do ZIP

**Jeśli masz folder `oryntium/`:**

1. Zaznacz cały folder `oryntium`
2. Kliknij prawy przycisk myszy → Wyślij do → Folder skompresowany (ZIP)
3. Nazwij: `oryntium.zip`

### Krok 2: Zaloguj się do WordPress

1. Otwórz przeglądarkę
2. Przejdź na: `https://twoja-domena.pl/wp-admin`
3. Zaloguj się (login i hasło od Hostingera)

### Krok 3: Zainstaluj motyw

1. W menu po lewej: **Wygląd** → **Motywy**
2. Kliknij: **Dodaj nowy** (na górze)
3. Kliknij: **Wyślij motyw na serwer**
4. Wybierz plik: `oryntium.zip`
5. Kliknij: **Zainstaluj teraz**
6. Poczekaj na upload (10-30 sekund)
7. Kliknij: **Aktywuj**

### Krok 4: Sprawdź stronę

1. Otwórz: `https://twoja-domena.pl`
2. Powinnaś zobaczyć stronę ORYNTIUM! 🎉

**GOTOWE!** Motyw zainstalowany!

---

## 🗂️ Metoda 2: Przez File Manager Hostinger (Alternatywa)

### Krok 1: Zaloguj się do hPanel

1. Przejdź na: [hpanel.hostinger.com](https://hpanel.hostinger.com)
2. Zaloguj się swoimi danymi
3. Wybierz swoją domenę/hosting

### Krok 2: Otwórz File Manager

1. W menu po lewej kliknij: **Files** → **File Manager**
2. Otworzy się menadżer plików

### Krok 3: Przejdź do folderu motywów

1. Kliknij na folder: `public_html`
2. Potem: `wp-content`
3. Potem: `themes`

Teraz jesteś w: `public_html/wp-content/themes/`

### Krok 4: Wgraj motyw

**Opcja A: Upload całego folderu oryntium/**

1. Kliknij przycisk: **Upload** (na górze)
2. Przeciągnij cały folder `oryntium` lub kliknij "Select files"
3. Poczekaj na upload wszystkich plików
4. Sprawdź czy folder `oryntium` jest w `themes/`

**Opcja B: Upload ZIP**

1. Spakuj folder `oryntium` do `oryntium.zip`
2. Kliknij przycisk: **Upload**
3. Wybierz `oryntium.zip`
4. Po uploadzie, kliknij prawym na `oryntium.zip`
5. Wybierz: **Extract** (rozpakuj)
6. Usuń `oryntium.zip` (opcjonalnie)

### Krok 5: Aktywuj motyw

1. Zaloguj się do WordPress: `https://twoja-domena.pl/wp-admin`
2. Przejdź: **Wygląd** → **Motywy**
3. Znajdź motyw **ORYNTIUM**
4. Kliknij: **Aktywuj**

**GOTOWE!**

---

## ⚙️ Metoda 3: Przez FTP (Dla zaawansowanych)

### Krok 1: Pobierz dane FTP

1. Zaloguj się do hPanel
2. Files → FTP Accounts
3. Zapisz:
   - **Hostname**: ftp.twoja-domena.pl
   - **Username**: [twój username]
   - **Password**: [twoje hasło]
   - **Port**: 21

### Krok 2: Połącz się przez FileZilla

1. Pobierz [FileZilla](https://filezilla-project.org/)
2. Otwórz FileZilla
3. Wprowadź dane FTP:
   - Host: `ftp.twoja-domena.pl`
   - Nazwa użytkownika: [z hPanel]
   - Hasło: [z hPanel]
   - Port: 21
4. Kliknij: **Szybkie połączenie**

### Krok 3: Wgraj folder motywu

1. Po lewej stronie (lokalnie): Znajdź folder `oryntium`
2. Po prawej stronie (serwer): Przejdź do `/public_html/wp-content/themes/`
3. Przeciągnij folder `oryntium` z lewej na prawą stronę
4. Poczekaj na upload wszystkich plików (może potrwać 1-2 minuty)

### Krok 4: Aktywuj motyw

1. Zaloguj się do WordPress
2. Wygląd → Motywy → Aktywuj ORYNTIUM

---

## 🌍 Konfiguracja wielojęzyczności

### Instalacja Polylang (REKOMENDOWANE)

1. W WordPress: **Wtyczki** → **Dodaj nową**
2. Wyszukaj: **"Polylang"**
3. **Zainstaluj** i **Aktywuj**
4. Przejdź: **Ustawienia** → **Języki**
5. Dodaj języki:
   - Polski (PL) - domyślny
   - English (EN)
   - Español (ES)
   - Deutsch (DE)
   - Русский (RU)
   - 中文 (ZH)
6. Wybierz format URL:
   - **Subfolder** (rekomendowane): `oryntium.app/en/`, `oryntium.app/pl/`
   - **Subdomena**: `en.oryntium.app`, `pl.oryntium.app`

### Tłumaczenie strony

1. **Strony** → **Wszystkie strony**
2. Przy każdej stronie zobaczysz flagi języków
3. Kliknij **"+"** przy fladze (np. EN)
4. Przetłumacz treść
5. **Opublikuj**

Polylang automatycznie wykryje funkcje `_e()` i `__()` z motywu!

---

## 🎨 Dostosowanie

### Zmiana logo

1. **Wygląd** → **Dostosuj** → **Tożsamość witryny**
2. **Wybierz logo** → Wgraj plik SVG/PNG (60x60px)
3. **Opublikuj**

### Zmiana tytułu i motto

1. **Ustawienia** → **Ogólne**
2. **Tytuł witryny**: ORYNTIUM
3. **Motto**: Keep it yours
4. **Zapisz zmiany**

### Zmiana kolorów (zaawansowane)

1. W File Manager przejdź do: `/public_html/wp-content/themes/oryntium/assets/css/main.css`
2. Kliknij prawym → **Edit**
3. Znajdź sekcję `:root {`
4. Zmień kolory:
```css
--neon-purple: #BB00FF;  /* Twój kolor */
--neon-blue: #00F0FF;    /* Twój kolor */
```
5. **Save File**

---

## 📊 Wtyczki rekomendowane

### SEO:
- **Yoast SEO** - Optymalizacja wyszukiwarek
- **Rank Math** - Alternatywa dla Yoast

### Wydajność:
- **LiteSpeed Cache** (Hostinger ma LiteSpeed!) - Cache i optymalizacja
- **WP Rocket** - Płatny, ale świetny cache

### Bezpieczeństwo:
- **Wordfence** - Ochrona przed atakami
- **iThemes Security** - Zabezpieczenie WP

### Wielojęzyczność:
- **Polylang** (darmowy) - REKOMENDOWANE
- **WPML** (płatny) - Profesjonalny

### Formularze (opcjonalnie):
- **Contact Form 7** - Prosty formularz kontaktowy
- **WPForms** - Zaawansowane formularze

---

## 🐛 Troubleshooting

### Problem: Motyw się nie pojawia w WP

**Rozwiązanie:**
1. Sprawdź czy folder to `oryntium` (nie `oryntium-master` czy `wp-theme-oryntium`)
2. Sprawdź czy ścieżka to: `/public_html/wp-content/themes/oryntium/`
3. Sprawdź czy plik `style.css` jest w głównym folderze `oryntium/`

### Problem: Strona wygląda źle / brak stylów

**Rozwiązanie:**
1. Wyczyść cache przeglądarki (Ctrl+Shift+Del)
2. W WP: Ustawienia → Permalinki → Zapisz zmiany
3. Sprawdź czy folder `assets` istnieje: `/themes/oryntium/assets/`
4. Sprawdź uprawnienia plików (powinny być 755)

### Problem: Brak plików CSS/JS

**Rozwiązanie:**
1. Sprawdź czy pliki istnieją:
   - `/themes/oryntium/assets/css/main.css`
   - `/themes/oryntium/assets/js/main.js`
2. Jeśli brakuje, wgraj je ponownie przez File Manager

### Problem: Błąd 500

**Rozwiązanie:**
1. Sprawdź wersję PHP (powinna być 7.4+)
   - hPanel → PHP Configuration → Wybierz PHP 8.0+
2. Sprawdź error logi: hPanel → Error Logs
3. Zwiększ limity PHP: hPanel → PHP Configuration

### Problem: Brak tłumaczeń

**Rozwiązanie:**
1. Zainstaluj plugin **Loco Translate**
2. Wtyczki → Loco Translate → Motywy → ORYNTIUM
3. Kliknij: **New language** → Wybierz język
4. Przetłumacz teksty
5. **Save**

---

## ✅ Checklist po instalacji

- [ ] Motyw zainstalowany i aktywowany
- [ ] Strona główna działa (otwórz w przeglądarce)
- [ ] Responsywność OK (sprawdź na mobile F12)
- [ ] Zainstalowano Polylang (jeśli wielojęzyczność)
- [ ] Dodano języki (PL, EN, ES, DE, RU, ZH)
- [ ] Przetłumaczono podstawowe teksty
- [ ] Zainstalowano wtyczki SEO (Yoast/Rank Math)
- [ ] Zainstalowano cache (LiteSpeed Cache)
- [ ] Logo zmienione (opcjonalnie)
- [ ] Tytuł i motto ustawione
- [ ] Sprawdzono na PageSpeed Insights

---

## 🎉 Gratulacje!

Twoja strona ORYNTIUM.APP jest gotowa!

### Co dalej?

1. **Dodaj treść** - Edytuj sekcje przez Polylang
2. **Optymalizuj SEO** - Yoast SEO → Ustawienia
3. **Włącz cache** - LiteSpeed Cache → Enable
4. **Zabezpiecz** - Wordfence → Scan
5. **Promuj** - Social media, Google Search Console

---

## 📞 Wsparcie

**Problem z instalacją?**

1. Sprawdź dokumentację: `README.md`
2. Przeczytaj ten przewodnik ponownie
3. Kontakt: support@oryntium.app

---

## 💜 Keep it yours!

**© 2025 ORYNTIUM powered by rhei**

---

**Powodzenia! 🚀**







