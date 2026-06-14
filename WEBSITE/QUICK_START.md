# ⚡ ORYNTIUM.APP - Szybki Start

## 📦 Co masz w pakiecie?

```
oryntium-website/
├── index.html              ← Główna strona
├── styles.css              ← Style cyberpunk
├── script.js               ← Interakcje i animacje
├── favicon.svg             ← Ikona strony
├── manifest.json           ← PWA manifest
├── robots.txt              ← SEO dla robotów
├── sitemap.xml             ← Mapa strony
├── 404.html                ← Strona błędu
├── .htaccess               ← Konfiguracja Apache
├── WEBSITE_README.md       ← Pełna dokumentacja
├── DEPLOYMENT_GUIDE.md     ← Szczegółowy przewodnik
└── QUICK_START.md          ← Ten plik
```

## 🚀 Wdróż w 3 krokach (5 minut)

### Metoda 1: Netlify Drop (NAJŁATWIEJSZA)

1. **Przejdź na [netlify.com](https://www.netlify.com/)**
2. **Zaloguj się** (darmowe konto)
3. **Przeciągnij cały folder** ze stroną na Netlify
4. **GOTOWE!** Masz link typu: `https://random-name.netlify.app`

### Dodaj swoją domenę:
- Kliknij "Domain settings"
- Dodaj `oryntium.app`
- Ustaw DNS jak poda Netlify
- HTTPS automatycznie włączone!

---

### Metoda 2: GitHub Pages (Z KONTROLĄ WERSJI)

```bash
# W folderze ze stroną:
git init
git add .
git commit -m "ORYNTIUM website"

# Utwórz repo na GitHub, potem:
git remote add origin https://github.com/TWOJA_NAZWA/oryntium.git
git push -u origin main

# Na GitHub: Settings > Pages > wybierz main branch
```

Dodaj plik `CNAME` z treścią: `oryntium.app`

---

### Metoda 3: Vercel CLI (DLA DEVELOPERÓW)

```bash
npm install -g vercel
cd folder-ze-strona
vercel
vercel --prod
```

---

## ✅ Test lokalny (przed wdrożeniem)

### Opcja A: Python (już zainstalowany na większości systemów)

```bash
# Python 3
python -m http.server 8000

# Otwórz: http://localhost:8000
```

### Opcja B: Node.js (http-server)

```bash
npm install -g http-server
http-server

# Otwórz: http://localhost:8080
```

### Opcja C: Live Server (VS Code Extension)

1. Zainstaluj: "Live Server" w VS Code
2. Kliknij prawym na `index.html`
3. Wybierz "Open with Live Server"

---

## 🎨 Ikony PWA (opcjonalnie)

Potrzebujesz obrazków:
- `icon-192.png` (192x192)
- `icon-512.png` (512x512)

### Szybko stwórz z favicon.svg:

#### Online:
1. Otwórz [cloudconvert.com](https://cloudconvert.com/svg-to-png)
2. Wgraj `favicon.svg`
3. Ustaw szerokość: 192px i 512px
4. Pobierz i zapisz jako `icon-192.png` i `icon-512.png`

#### GIMP (darmowy):
1. Otwórz `favicon.svg`
2. Export as PNG
3. Ustaw 192x192 i 512x512

#### ImageMagick (CLI):
```bash
convert favicon.svg -resize 192x192 icon-192.png
convert favicon.svg -resize 512x512 icon-512.png
```

---

## 🔍 Sprawdź czy działa

1. **Otwórz stronę** w przeglądarce
2. **Test responsywności**: F12 > Toggle Device Toolbar (Ctrl+Shift+M)
3. **Test mobilny**: Sprawdź na telefonie
4. **Sprawdź konsolę**: F12 > Console (nie powinno być błędów)

---

## 📝 Checklist

- [ ] Strona otwiera się lokalnie
- [ ] Wszystkie sekcje działają (Features, Benefits, etc.)
- [ ] Smooth scroll działa (kliknij linki w nav)
- [ ] Animacje działają (glitch, hover effects)
- [ ] Responsywna na mobile/tablet/desktop
- [ ] Brak błędów w konsoli (F12)
- [ ] Strona wdrożona online
- [ ] Domena podpięta (jeśli masz)
- [ ] HTTPS działa (zielona kłódka)

---

## 🎯 Następne kroki

1. ✅ Wdróż stronę (wybierz metodę powyżej)
2. 📊 Dodaj Google Analytics (opcjonalnie)
3. 🔍 Dodaj do Google Search Console
4. 📱 Przetestuj na różnych urządzeniach
5. 🚀 Udostępnij światu!

---

## 💡 Porady

- **Netlify** - najlepsze dla początkujących (drag & drop)
- **GitHub Pages** - dobre jeśli znasz git
- **Vercel** - najszybsze dla developerów
- **Własny hosting** - jeśli już masz hosting

---

## 🆘 Problemy?

### Strona nie ładuje CSS/JS?
- Sprawdź ścieżki w `index.html` (muszą być relatywne)
- Sprawdź czy wszystkie pliki są w tym samym folderze

### Czcionki się nie ładują?
- Sprawdź połączenie internetowe
- Google Fonts może być zablokowane (użyj VPN)

### Animacje nie działają?
- Otwórz konsolę (F12) i sprawdź błędy
- Sprawdź czy `script.js` się załadował

### 404 na live stronie?
- Sprawdź czy wszystkie pliki zostały wgrane
- Sprawdź wielkość liter (Linux rozróżnia: `Index.html` ≠ `index.html`)

---

## 📖 Więcej informacji

- **Pełna dokumentacja**: `WEBSITE_README.md`
- **Szczegółowy deployment**: `DEPLOYMENT_GUIDE.md`
- **Customizacja**: Edytuj CSS variables w `styles.css`

---

## 🎉 Gotowe!

Masz pytania? Sprawdź dokumentację lub otwórz DevTools (F12).

**Keep it yours! 💜**

---

© 2025 ORYNTIUM powered by rhei

