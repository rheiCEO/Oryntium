# ORYNTIUM WordPress Theme

## Opis

Profesjonalny motyw WordPress w stylu cyberpunk dla ORYNTIUM - najbezpieczniejszej platformy szyfrowania SMS w Polsce.

## Funkcje

- ✅ **Cyberpunk Design** - Neon kolory, glitch efekty, scanlines
- ✅ **Fully Responsive** - Desktop, Tablet, Mobile
- ✅ **Multi-language Ready** - Wsparcie dla Polylang/WPML
- ✅ **One-page Layout** - Scrollujące sekcje
- ✅ **SEO Optimized** - Semantyczny HTML5
- ✅ **Fast Loading** - Zoptymalizowane CSS/JS
- ✅ **Animations** - Smooth scroll, parallax, glitch effects
- ✅ **Security Headers** - Built-in security headers

## Wymagania

- **WordPress**: 5.8 lub nowszy
- **PHP**: 7.4 lub nowszy
- **MySQL**: 5.6 lub nowszy

## Instalacja

### Metoda 1: Przez Panel WordPress (NAJŁATWIEJSZE)

1. **Pobierz motyw** jako ZIP
2. **Zaloguj się** do panelu WordPress: `https://twojastrona.pl/wp-admin`
3. **Przejdź do**: Wygląd → Motywy → Dodaj nowy
4. **Kliknij**: "Wyślij motyw na serwer"
5. **Wybierz plik**: `oryntium.zip`
6. **Kliknij**: "Zainstaluj teraz"
7. **Aktywuj motyw**

### Metoda 2: Przez FTP (Hostinger)

1. **Zaloguj się** do Hostinger File Manager
2. **Przejdź do**: `public_html/wp-content/themes/`
3. **Wgraj folder** `oryntium/`
4. **Zaloguj się** do WordPress: Wygląd → Motywy
5. **Aktywuj** motyw ORYNTIUM

### Metoda 3: Przez WP-CLI

```bash
wp theme install oryntium.zip
wp theme activate oryntium
```

## Konfiguracja

### 1. Podstawowa konfiguracja

Po aktywacji motywu:

1. **Przejdź do**: Ustawienia → Ogólne
2. **Ustaw tytuł strony**: ORYNTIUM
3. **Ustaw motto**: Keep it yours
4. **Zapisz zmiany**

### 2. Menu (opcjonalne)

Motyw ma wbudowane menu nawigacji:
- Funkcje
- Jak działa  
- Zalety
- Bezpieczeństwo

Jeśli chcesz niestandardowe menu:
1. Przejdź do: Wygląd → Menu
2. Utwórz nowe menu
3. Przypisz do lokalizacji: "Primary Menu"

### 3. Wielojęzyczność

#### Opcja A: Polylang (REKOMENDOWANE)

1. **Zainstaluj plugin**: Polylang
   - Wtyczki → Dodaj nową → Szukaj "Polylang"
   - Zainstaluj i aktywuj

2. **Konfiguracja**:
   - Ustawienia → Języki
   - Dodaj języki: Polski (domyślny), English, Español, Deutsch, Русский, 中文
   - Wybierz format URL (subdomena / subfolder)

3. **Tłumaczenia**:
   - Strony → Wszystkie strony
   - Kliknij "+" przy każdym języku
   - Przetłumacz treść

#### Opcja B: WPML

1. **Zainstaluj WPML** (płatny plugin)
2. **Konfiguracja**: Setup wizard
3. **Dodaj języki**: PL, EN, ES, DE, RU, ZH
4. **Tłumacz** treść przez Translation Management

### 4. Logo (opcjonalne)

Aby zmienić logo:
1. Wygląd → Dostosuj → Tożsamość witryny
2. Kliknij "Wybierz logo"
3. Wgraj plik (najlepiej SVG 60x60px)

### 5. Favicon

Domyślnie motyw używa wbudowanego SVG favicon.

Aby zmienić:
1. Wygląd → Dostosuj → Tożsamość witryny
2. Ikona witryny → Wybierz obraz (512x512px)

## Treść strony

### Edycja treści

Wszystkie teksty są przetłumaczalne przez:

1. **Plugin Loco Translate** (NAJŁATWIEJSZE):
   - Zainstaluj plugin "Loco Translate"
   - Wtyczki → Loco Translate → Motywy → ORYNTIUM
   - Edytuj tłumaczenia

2. **Pliki PO/POT**:
   - Pliki w: `wp-content/themes/oryntium/languages/`
   - Edytuj przez Poedit
   - Prześlij z powrotem przez FTP

### Sekcje strony

Motyw zawiera:
1. **Hero** - Główny nagłówek "KEEP IT YOURS"
2. **Features** - 6 funkcji aplikacji
3. **How It Works** - 4 kroki
4. **Benefits** - 6 zalet
5. **Security** - Bezpieczeństwo
6. **Footer** - Stopka

## Dostosowanie

### Kolory

Główne kolory można zmienić w `assets/css/main.css`:

```css
:root {
    --neon-purple: #BB00FF;  /* Zmień ten kolor */
    --neon-blue: #00F0FF;    /* Zmień ten kolor */
    --neon-cyan: #00D4FF;    /* Zmień ten kolor */
}
```

### Czcionki

Motyw używa Google Fonts:
- **Orbitron** - Nagłówki
- **Rajdhani** - Treść

Aby zmienić, edytuj `functions.php`:
```php
wp_enqueue_style('oryntium-fonts', 'https://fonts.googleapis.com/css2?family=TWOJA-CZCIONKA');
```

## Wtyczki rekomendowane

### Niezbędne:
- **Polylang** lub **WPML** - Wielojęzyczność

### Przydatne:
- **Yoast SEO** - Optymalizacja SEO
- **W3 Total Cache** - Cachowanie
- **Wordfence** - Bezpieczeństwo
- **Contact Form 7** - Formularze kontaktowe (jeśli potrzeba)

## Troubleshooting

### CSS/JS się nie ładują?

1. Wyczyść cache: WP-Admin → W3 Total Cache → Empty All Caches
2. Regeneruj permalinki: Ustawienia → Perman

entne odnośniki → Zapisz zmiany
3. Sprawdź uprawnienia plików (775)

### Motyw wygląda źle?

1. Sprawdź czy używasz PHP 7.4+
2. Wyłącz inne wtyczki (konflikt)
3. Przełącz na domyślny motyw i z powrotem

### Brak tłumaczeń?

1. Zainstaluj plugin "Loco Translate"
2. Wygeneruj pliki MO: Loco Translate → Generate
3. Sprawdź czy język WordPress jest ustawiony: Ustawienia → Ogólne → Język witryny

## Wsparcie

- **Email**: support@oryntium.app
- **Website**: https://oryntium.app

## Licencja

GNU General Public License v2 or later

## Credits

- **Autor**: ORYNTIUM powered by rhei
- **Wersja**: 1.0.0
- **Rok**: 2025

---

**© 2025 ORYNTIUM powered by rhei**

**Keep it yours! 💜**







