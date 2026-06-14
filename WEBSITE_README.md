# ORYNTIUM.APP - Website

Profesjonalna strona www w stylu cyberpunk dla aplikacji ORYNTIUM.

## 🎨 Styl i Design

- **Motyw**: Cyberpunk z efektami glitch
- **Kolory**: Neon purple (#BB00FF), Neon blue (#00F0FF), Cyan (#00D4FF)
- **Czcionki**: Orbitron (nagłówki), Rajdhani (treść)
- **Efekty**: Scanlines, glitch, neon glow, animacje
- **Responsywność**: Pełne wsparcie dla desktop, tablet, mobile

## 📁 Struktura plików

```
website/
├── index.html          # Główny plik HTML
├── styles.css          # Style CSS z efektami cyberpunk
├── script.js           # Interakcje JavaScript
└── WEBSITE_README.md   # Ten plik
```

## 🚀 Wdrożenie

### Opcja 1: GitHub Pages (Darmowe)

1. Utwórz repozytorium na GitHub
2. Wgraj pliki: `index.html`, `styles.css`, `script.js`
3. Przejdź do Settings > Pages
4. Wybierz branch: main
5. Ustaw Custom domain: `oryntium.app`
6. Dodaj plik CNAME z treścią: `oryntium.app`

### Opcja 2: Netlify (Darmowe)

1. Zaloguj się na [netlify.com](https://netlify.com)
2. Przeciągnij folder z plikami na stronę Netlify
3. Ustaw Custom domain: `oryntium.app`
4. Netlify automatycznie skonfiguruje HTTPS

### Opcja 3: Vercel (Darmowe)

1. Zaloguj się na [vercel.com](https://vercel.com)
2. Kliknij "New Project"
3. Importuj pliki lub połącz z GitHub
4. Ustaw Custom domain: `oryntium.app`
5. Deploy!

### Opcja 4: Tradycyjny hosting

1. Kup hosting z obsługą domeny
2. Wgraj pliki przez FTP/SFTP
3. Skonfiguruj domenę `oryntium.app` w DNS

## 🌐 Konfiguracja domeny

Aby domena `oryntium.app` wskazywała na stronę:

### Dla GitHub Pages:
```
Type: A
Name: @
Value: 185.199.108.153
```

```
Type: CNAME
Name: www
Value: twoja-nazwa.github.io
```

### Dla Netlify/Vercel:
Postępuj zgodnie z instrukcjami w panelu administracyjnym.

## ✨ Funkcje strony

### Sekcje:
1. **Hero** - Główny ekran z logo i hasłem "KEEP IT YOURS"
2. **Features** - 6 głównych funkcji aplikacji
3. **How It Works** - 4 kroki jak używać aplikacji
4. **Benefits** - 6 zalet używania ORYNTIUM
5. **Security** - Szczegóły bezpieczeństwa i szyfrowania
6. **Footer** - Logo, tagline, "Powered by rhei"

### Efekty:
- ✅ Glitch effect na tytułach
- ✅ Scanlines overlay
- ✅ Neon glow na hover
- ✅ Smooth scrolling
- ✅ Parallax effect
- ✅ Animowane karty
- ✅ Obracający się diament (logo)
- ✅ Custom cursor z neon efektem
- ✅ Particles floating effect
- ✅ Easter egg: Konami code (⬆️⬆️⬇️⬇️⬅️➡️⬅️➡️BA) dla Matrix mode

## 📱 Responsywność

- **Desktop (1920px+)**: Pełna wersja z wszystkimi efektami
- **Laptop (1024-1920px)**: Zoptymalizowana wersja
- **Tablet (768-1024px)**: Layout 1-kolumnowy
- **Mobile (320-768px)**: Uproszczona nawigacja, pionowy layout

## 🎯 SEO

W pliku `index.html` znajdują się podstawowe meta tagi:
- Title: "ORYNTIUM - Secure Messaging Platform"
- Description: "ORYNTIUM - Najlepsze szyfrowanie SMS w Polsce..."
- Viewport dla mobile
- Charset UTF-8

### Zalecane dodatki:
```html
<!-- Open Graph -->
<meta property="og:title" content="ORYNTIUM - Secure Messaging">
<meta property="og:description" content="Najnowocześniejsza platforma szyfrowania SMS">
<meta property="og:image" content="og-image.jpg">
<meta property="og:url" content="https://oryntium.app">

<!-- Twitter Card -->
<meta name="twitter:card" content="summary_large_image">
<meta name="twitter:title" content="ORYNTIUM">
<meta name="twitter:description" content="Keep it yours">
<meta name="twitter:image" content="twitter-card.jpg">
```

## 🔧 Customizacja

### Zmiana kolorów:
Edytuj zmienne CSS w `styles.css`:
```css
:root {
    --neon-purple: #BB00FF;
    --neon-blue: #00F0FF;
    --neon-cyan: #00D4FF;
    /* ... */
}
```

### Zmiana treści:
Edytuj tekst bezpośrednio w `index.html`.

### Dodanie nowych sekcji:
Użyj struktury podobnej do istniejących sekcji.

## 🐛 Debugowanie

### Problem z czcionkami:
Sprawdź połączenie z Google Fonts w sekcji `<head>`.

### Problem z animacjami:
Sprawdź konsolę przeglądarki (F12) pod kątem błędów JavaScript.

### Problem z responsywnością:
Użyj DevTools (F12) i testuj na różnych rozmiarach ekranu.

## 📊 Performance

- **Czcionki**: Ładowane z Google Fonts (można pobrać lokalnie)
- **Brak obrazów**: Wszystko zrobione w CSS/SVG
- **Minifikacja**: Zalecane przed produkcją
- **Lazy loading**: Już wbudowane dla animacji

### Minifikacja (opcjonalnie):
```bash
# HTML
npm install -g html-minifier
html-minifier --collapse-whitespace --remove-comments index.html -o index.min.html

# CSS
npm install -g clean-css-cli
cleancss -o styles.min.css styles.css

# JavaScript
npm install -g terser
terser script.js -o script.min.js
```

## 🎨 Ikony

Wszystkie ikony używają Material Design Icons (SVG inline).
Źródło: [Material Design Icons](https://materialdesignicons.com/)

## 📝 Licencja

© 2025 ORYNTIUM powered by rhei

## 🤝 Wsparcie

Jeśli masz pytania:
1. Sprawdź ten README
2. Sprawdź kod źródłowy (jest dobrze skomentowany)
3. Użyj DevTools przeglądarki do debugowania

## 🎉 Easter Eggs

1. **Konami Code**: ⬆️⬆️⬇️⬇️⬅️➡️⬅️➡️BA - Aktywuje Matrix mode
2. **Console**: Otwórz konsolę (F12) aby zobaczyć ASCII art
3. **Custom Cursor**: Zauważ neon cursor na desktop

---

**Keep it yours** 💜







