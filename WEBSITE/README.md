# 🌐 ORYNTIUM.APP - Official Website

Profesjonalna strona www w stylu cyberpunk dla aplikacji ORYNTIUM - najbezpieczniejszej platformy szyfrowania SMS w Polsce.

## 🎨 Demo

![ORYNTIUM Website](https://img.shields.io/badge/Status-Ready%20to%20Deploy-success?style=for-the-badge&logo=netlify&logoColor=white)
![Tech Stack](https://img.shields.io/badge/Tech-HTML%2FCSS%2FJS-blue?style=for-the-badge)
![Style](https://img.shields.io/badge/Style-Cyberpunk-purple?style=for-the-badge)

**Live Preview**: [oryntium.app](https://oryntium.app) *(po wdrożeniu)*

## ✨ Cechy

- 🎭 **Cyberpunk Design** - Neon kolory, glitch efekty, scanlines
- 📱 **Fully Responsive** - Desktop, Tablet, Mobile
- ⚡ **Super Fast** - Brak frameworków, czysty HTML/CSS/JS
- 🔒 **PWA Ready** - Manifest.json, service worker ready
- 🎨 **Animated** - Smooth scrolling, parallax, hover effects
- ♿ **Accessible** - Semantic HTML, ARIA labels
- 🔍 **SEO Optimized** - Meta tags, sitemap, robots.txt

## 📁 Struktura

```
WEBSITE/
├── index.html              # Główna strona
├── styles.css              # Style (3500+ linii CSS)
├── script.js               # Interakcje i efekty
├── favicon.svg             # Ikona SVG
├── manifest.json           # PWA manifest
├── robots.txt              # SEO robots
├── sitemap.xml             # Mapa strony
├── 404.html                # Custom 404
├── .htaccess               # Apache config
│
├── README.md               # Ten plik
├── QUICK_START.md          # Szybki start (5 min)
├── WEBSITE_README.md       # Pełna dokumentacja
├── DEPLOYMENT_GUIDE.md     # Przewodnik wdrożenia
└── GRAPHICS_GUIDE.md       # Jak stworzyć grafiki
```

## 🚀 Szybki Start

### Metoda 1: Netlify Drop (NAJPROŚCIEJ)
```
1. Otwórz netlify.com
2. Przeciągnij folder WEBSITE/
3. GOTOWE!
```

### Metoda 2: GitHub Pages
```bash
git init
git add .
git commit -m "ORYNTIUM website"
git push
# Włącz GitHub Pages w Settings
```

### Metoda 3: Test lokalny
```bash
python -m http.server 8000
# Otwórz: localhost:8000
```

📖 **Szczegóły**: Zobacz `QUICK_START.md`

## 🎨 Sekcje strony

1. **Hero** - "KEEP IT YOURS" z obracającym się logo
2. **Features** - 6 głównych funkcji (AES-256, hasła, automation, etc.)
3. **How It Works** - 4 kroki używania aplikacji
4. **Benefits** - 6 zalet (prywatność, bezpieczeństwo, kontrola)
5. **Security** - Algorytmy i technologie
6. **Footer** - Logo, tagline, "powered by rhei"

## 🎯 Tech Stack

- **HTML5** - Semantic markup
- **CSS3** - Custom properties, animations, grid/flexbox
- **Vanilla JS** - No frameworks, no dependencies
- **SVG** - Inline icons i logo
- **Google Fonts** - Orbitron + Rajdhani

## 🎨 Kolorystyka

```css
--neon-purple: #BB00FF
--neon-blue: #00F0FF
--neon-cyan: #00D4FF
--bg-primary: #0A0A0A
--bg-secondary: #1A1A1A
```

## ✨ Efekty specjalne

- ✅ Glitch text effect
- ✅ Scanlines overlay
- ✅ Neon glow on hover
- ✅ Smooth scroll navigation
- ✅ Parallax scrolling
- ✅ Animated cards on scroll
- ✅ Rotating diamond logo
- ✅ Custom neon cursor
- ✅ Floating particles
- ✅ Easter egg: Konami code (⬆️⬆️⬇️⬇️⬅️➡️⬅️➡️BA)

## 📱 Responsywność

| Device | Resolution | Layout |
|--------|-----------|--------|
| Desktop | 1920px+ | 2-column grid |
| Laptop | 1024-1920px | Optimized 2-column |
| Tablet | 768-1024px | 1-column |
| Mobile | 320-768px | Vertical stack |

## 🔍 SEO

- ✅ Meta tags (description, keywords, author)
- ✅ Open Graph (Facebook, LinkedIn)
- ✅ Twitter Cards
- ✅ Sitemap.xml
- ✅ Robots.txt
- ✅ Semantic HTML
- ✅ Fast loading (no images)
- ✅ Mobile-first

## 📊 Performance

- **HTML**: ~25KB (uncompressed)
- **CSS**: ~30KB (uncompressed)
- **JS**: ~8KB (uncompressed)
- **Total**: ~63KB + fonts
- **Load time**: <1s (po cache)
- **PageSpeed Score**: 95+ (expected)

## 🛠️ Customizacja

### Zmień kolory:
Edytuj w `styles.css`:
```css
:root {
    --neon-purple: #TWOJ_KOLOR;
    --neon-blue: #TWOJ_KOLOR;
    /* ... */
}
```

### Zmień treść:
Edytuj bezpośrednio w `index.html`

### Dodaj sekcję:
Kopiuj strukturę istniejącej sekcji

## 📖 Dokumentacja

- **Quick Start**: `QUICK_START.md` - Start w 5 minut
- **Full Docs**: `WEBSITE_README.md` - Wszystko o stronie
- **Deployment**: `DEPLOYMENT_GUIDE.md` - Przewodnik wdrożenia
- **Graphics**: `GRAPHICS_GUIDE.md` - Jak stworzyć grafiki

## 🐛 Troubleshooting

### CSS nie działa?
Sprawdź ścieżkę w `<link>` - musi być `styles.css`

### JS nie działa?
Otwórz konsolę (F12) i sprawdź błędy

### Fonty nie ładują się?
Sprawdź połączenie z Google Fonts

### 404 na live?
Sprawdź czy wszystkie pliki są wgrane

## 🚢 Deployment Options

- **Netlify** ⭐ - Najłatwiejsze (drag & drop)
- **Vercel** ⚡ - Najszybsze (CLI)
- **GitHub Pages** 📦 - Darmowe + git
- **Własny hosting** 🏠 - Pełna kontrola

## 🎯 Roadmap

- [x] HTML struktura
- [x] CSS styling + animacje
- [x] JavaScript interakcje
- [x] Responsywność
- [x] PWA manifest
- [x] SEO optimization
- [x] Dokumentacja
- [ ] Ikony PWA (192x192, 512x512)
- [ ] OG image (1200x630)
- [ ] Screenshoty
- [ ] Deploy

## 📝 Licencja

© 2025 ORYNTIUM powered by rhei

Strona stworzona dla aplikacji ORYNTIUM - Secure Messaging Platform.

## 🤝 Contributing

Projekt stworzony dla ORYNTIUM. 

Aby zaproponować zmiany:
1. Fork repo
2. Stwórz branch (`git checkout -b feature/amazing`)
3. Commit (`git commit -m 'Add amazing feature'`)
4. Push (`git push origin feature/amazing`)
5. Otwórz Pull Request

## 📞 Support

Potrzebujesz pomocy?
1. Przeczytaj `QUICK_START.md`
2. Sprawdź `DEPLOYMENT_GUIDE.md`
3. Otwórz DevTools (F12) i sprawdź console

## 🎉 Acknowledgments

- **Czcionki**: [Google Fonts](https://fonts.google.com/)
- **Ikony**: [Material Design Icons](https://materialdesignicons.com/)
- **Inspiracja**: Cyberpunk aesthetic, neon noir

---

**Made with 💜 for ORYNTIUM**

**Keep it yours!**

---

[![Deploy to Netlify](https://www.netlify.com/img/deploy/button.svg)](https://app.netlify.com/start)







