# 🎨 ORYNTIUM.APP - Przewodnik Grafik

Jak stworzyć wszystkie potrzebne grafiki dla strony ORYNTIUM.APP.

## 📋 Lista potrzebnych grafik

- [ ] `icon-192.png` - Ikona PWA (192x192px)
- [ ] `icon-512.png` - Ikona PWA (512x512px)
- [ ] `og-image.jpg` - Open Graph image (1200x630px)
- [ ] `twitter-card.jpg` - Twitter Card (1200x675px)
- [ ] `screenshot-desktop.png` - Screenshot desktop (1920x1080px)
- [ ] `screenshot-mobile.png` - Screenshot mobile (750x1334px)

## 🎯 Ikony PWA

### Źródło: favicon.svg

Masz już plik `favicon.svg` - to Twoje logo ORYNTIUM.

### Metoda 1: Online (NAJPROŚCIEJ)

**Użyj Cloudconvert:**
1. Otwórz [cloudconvert.com/svg-to-png](https://cloudconvert.com/svg-to-png)
2. Upload `favicon.svg`
3. Kliknij "Adjust options"
4. Width: 192, Height: 192
5. Convert i pobierz jako `icon-192.png`
6. Powtórz dla 512x512 → `icon-512.png`

**Użyj RealFaviconGenerator:**
1. Otwórz [realfavicongenerator.net](https://realfavicongenerator.net/)
2. Upload `favicon.svg`
3. Dostosuj kolory (używaj #BB00FF i #00F0FF)
4. Pobierz cały pakiet

### Metoda 2: GIMP (Darmowy)

1. **Otwórz GIMP**
2. **File > Open** → wybierz `favicon.svg`
3. **Ustaw rozdzielczość**: 192x192 (dla pierwszej ikony)
4. **Image > Scale Image** → 192x192
5. **File > Export As** → `icon-192.png`
6. Powtórz dla 512x512

### Metoda 3: Inkscape (dla SVG)

1. **Otwórz Inkscape**
2. **File > Open** → `favicon.svg`
3. **File > Export PNG Image**
4. **Image size**: 192x192
5. **Export** → `icon-192.png`
6. Powtórz dla 512x512

### Metoda 4: ImageMagick (CLI)

```bash
# Zainstaluj ImageMagick
# Windows: https://imagemagick.org/script/download.php
# Mac: brew install imagemagick
# Linux: sudo apt install imagemagick

# Konwersja
magick convert favicon.svg -resize 192x192 icon-192.png
magick convert favicon.svg -resize 512x512 icon-512.png
```

### Metoda 5: Online SVG to PNG

- [ezgif.com/svg-to-png](https://ezgif.com/svg-to-png)
- [svgtopng.com](https://svgtopng.com/)
- [convertio.co/svg-png](https://convertio.co/svg-png/)

## 🖼️ Open Graph Image (Social Media)

### Wymiary: 1200x630px

To obraz, który pojawi się gdy ktoś udostępni Twoją stronę na Facebook, LinkedIn, etc.

### Opcja A: Canva (REKOMENDOWANE)

1. **Otwórz [canva.com](https://www.canva.com/)**
2. **Create a design** → "Facebook Post" (1200x630)
3. **Design:**
   - Tło: Czarne (#0A0A0A)
   - Logo: Wklej diament (lub screenshot logo ze strony)
   - Tekst: "ORYNTIUM"
   - Podtytuł: "KEEP IT YOURS"
   - Tagline: "Secure Messaging Platform"
   - Kolory: Purple (#BB00FF) + Cyan (#00F0FF)
   - Font: Orbitron (lub podobny cyberpunk)
4. **Download** → PNG → `og-image.png`

### Opcja B: Figma (dla designerów)

```
1. Nowy file: 1200x630px
2. Background: #0A0A0A
3. Add logo (diament)
4. Add text:
   - "ORYNTIUM" - Orbitron Bold, 120px, gradient purple-cyan
   - "Keep it yours" - Rajdhani, 60px, #00F0FF
5. Export: PNG, 1200x630px
```

### Opcja C: Screenshot + Edit

1. **Zrób screenshot** strony (hero sekcja)
2. **Otwórz w Photoshop/GIMP**
3. **Crop** do 1200x630px
4. **Dodaj overlay** (tekst, blur tła, etc.)
5. **Export** → `og-image.jpg` (quality 90%)

### Opcja D: HTML to Image (automatycznie)

```html
<!-- Stwórz og-template.html -->
<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            width: 1200px;
            height: 630px;
            background: #0A0A0A;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            font-family: 'Orbitron', sans-serif;
        }
        h1 {
            font-size: 100px;
            background: linear-gradient(135deg, #BB00FF, #00F0FF);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
        p { color: #00F0FF; font-size: 40px; }
    </style>
</head>
<body>
    <h1>ORYNTIUM</h1>
    <p>Keep it yours</p>
</body>
</html>
```

Potem użyj:
- [htmlcsstoimage.com](https://htmlcsstoimage.com/)
- Screenshot w przeglądarce (Ctrl+Shift+S w Firefox)

## 📱 Twitter Card Image

### Wymiary: 1200x675px (optional - możesz użyć tego samego co OG)

Podobnie jak Open Graph, ale proporcje 16:9.

Użyj tej samej metody co dla OG image, tylko zmień wymiary na 1200x675px.

## 📸 Screenshoty

### Desktop Screenshot (1920x1080px)

1. **Otwórz stronę** w przeglądarce (full screen)
2. **F11** (pełny ekran) lub ustaw window na 1920x1080
3. **Screenshot całej strony:**
   - **Firefox**: Ctrl+Shift+S → "Save full page"
   - **Chrome**: F12 → Ctrl+Shift+P → "Capture full size screenshot"
   - **Extension**: [GoFullPage](https://gofullpage.com/)
4. **Crop** do interesującej części (jeśli potrzeba)
5. **Save** → `screenshot-desktop.png`

### Mobile Screenshot (750x1334px - iPhone 8)

1. **F12** (DevTools)
2. **Toggle device toolbar** (Ctrl+Shift+M)
3. **Wybierz:** iPhone 8 (750x1334) lub iPhone X (1125x2436)
4. **Screenshot:**
   - **Chrome**: F12 → Ctrl+Shift+P → "Capture screenshot"
   - **Firefox**: Ctrl+Shift+M → Screenshot icon
5. **Save** → `screenshot-mobile.png`

## 🎨 Szablon graficzny (Canva Template)

### Layout Open Graph:

```
┌─────────────────────────────────────────────┐
│                                             │
│           [Background: #0A0A0A]             │
│                                             │
│              🔷 [Logo Diamond]              │
│                                             │
│                ORYNTIUM                     │
│           [Gradient: Purple → Cyan]         │
│                                             │
│              Keep it yours                  │
│         [Color: Cyan #00F0FF]               │
│                                             │
│       Secure Messaging Platform             │
│         [Color: Gray #B0B0B0]               │
│                                             │
│                                             │
│  [Subtle scanlines overlay]                 │
│                                             │
└─────────────────────────────────────────────┘
       1200px x 630px
```

## 🔧 Narzędzia online

### Tworzenie grafik:
- **Canva** - [canva.com](https://www.canva.com/) - Najłatwiejsze
- **Figma** - [figma.com](https://www.figma.com/) - Profesjonalne
- **Photopea** - [photopea.com](https://www.photopea.com/) - Darmowy Photoshop

### Konwersja SVG:
- **CloudConvert** - [cloudconvert.com](https://cloudconvert.com/)
- **RealFaviconGenerator** - [realfavicongenerator.net](https://realfavicongenerator.net/)
- **EZGIF** - [ezgif.com/svg-to-png](https://ezgif.com/svg-to-png)

### Optymalizacja:
- **TinyPNG** - [tinypng.com](https://tinypng.com/) - Kompresja PNG
- **Squoosh** - [squoosh.app](https://squoosh.app/) - Google image optimizer
- **ImageOptim** - [imageoptim.com](https://imageoptim.com/) - Mac app

### Screenshoty:
- **GoFullPage** - [gofullpage.com](https://gofullpage.com/) - Chrome/Firefox
- **Awesome Screenshot** - Extension
- **Nimbus Screenshot** - Extension

## ✅ Checklist

Po stworzeniu wszystkich grafik:

- [ ] `icon-192.png` - 192x192px, PNG, <50KB
- [ ] `icon-512.png` - 512x512px, PNG, <200KB
- [ ] `og-image.jpg` - 1200x630px, JPG, <300KB
- [ ] `twitter-card.jpg` - 1200x675px, JPG, <300KB (optional)
- [ ] `screenshot-desktop.png` - 1920x1080px, PNG, <500KB
- [ ] `screenshot-mobile.png` - 750x1334px, PNG, <300KB

### Aktualizuj pliki:

1. **Dodaj ikony do folderu:**
```
WEBSITE/
├── icon-192.png
├── icon-512.png
└── ...
```

2. **Zaktualizuj index.html:**
```html
<!-- Dodaj w <head> -->
<meta property="og:image" content="https://oryntium.app/og-image.jpg">
<meta name="twitter:image" content="https://oryntium.app/twitter-card.jpg">
```

3. **Zaktualizuj manifest.json** (już zrobione, tylko dodaj pliki)

## 🎯 Wskazówki

### Kolory ORYNTIUM:
```
Purple: #BB00FF
Cyan: #00F0FF
Blue: #00D4FF
Pink: #FF00FF
Background: #0A0A0A
```

### Czcionki:
- **Nagłówki**: Orbitron (Google Fonts)
- **Tekst**: Rajdhani (Google Fonts)
- **Alternatywa**: Exo 2, Audiowide, Black Ops One

### Styl:
- Cyberpunk / Neon noir
- Dark background
- Neon glow effects
- Geometric shapes (diamond logo)
- Minimalistyczny

## 💡 Pro Tips

1. **Używaj gradientów** - Purple → Cyan
2. **Dodaj glow** - Outer glow w Photoshop/Figma
3. **Zachowaj spójność** - Te same kolory i fonty
4. **Testuj na mobile** - Sprawdź czytelność na małych ekranach
5. **Optymalizuj** - Używaj TinyPNG przed uploadem

## 🧪 Test grafik

### Open Graph Debugger:
- **Facebook**: [developers.facebook.com/tools/debug](https://developers.facebook.com/tools/debug/)
- **Twitter**: [cards-dev.twitter.com/validator](https://cards-dev.twitter.com/validator)
- **LinkedIn**: [linkedin.com/post-inspector](https://www.linkedin.com/post-inspector/)

Wklej URL: `https://oryntium.app` i sprawdź preview.

## 📦 Gotowe szablony

Jeśli chcesz, mogę wygenerować dla Ciebie:
- SVG template w Figma/Canva
- HTML template do automatycznego generowania
- Python script do batch conversion

---

## ✨ Przykład (Canva)

1. Otwórz [canva.com/create/facebook-posts](https://www.canva.com/create/facebook-posts/)
2. Wybierz pusty template (1200x630)
3. Tło: #0A0A0A
4. Text: "ORYNTIUM" → Effects → Gradient → Custom (#BB00FF → #00F0FF)
5. Add element: Shapes → Diamond → Rotate 45° → Border: gradient
6. Add text: "Keep it yours" → Rajdhani → #00F0FF
7. Download → PNG → og-image.png

**DONE!** 🎉

---

© 2025 ORYNTIUM powered by rhei







