# 🌐 ORYNTIUM WordPress Theme

Profesjonalny motyw WordPress w stylu cyberpunk dla aplikacji ORYNTIUM - najbezpieczniejszej platformy szyfrowania SMS w Polsce.

## 🎨 Demo

![ORYNTIUM WordPress Theme](https://img.shields.io/badge/Status-Ready%20to%20Deploy-success?style=for-the-badge&logo=wordpress&logoColor=white)
![Tech Stack](https://img.shields.io/badge/Tech-WordPress%2BPHP-blue?style=for-the-badge)
![Style](https://img.shields.io/badge/Style-Cyberpunk-purple?style=for-the-badge)
![Languages](https://img.shields.io/badge/Languages-8%20Supported-green?style=for-the-badge)

**Live Preview**: [oryntium.app](https://oryntium.app) *(po wdrożeniu)*

## ✨ Cechy

- 🎭 **Cyberpunk Design** - Neon kolory, glitch efekty, scanlines
- 📱 **Fully Responsive** - Desktop, Tablet, Mobile
- ⚡ **WordPress Optimized** - Pełna integracja z WordPress
- 🌍 **Wielojęzyczność** - 8 języków (PL, EN, ES, DE, HI, AR, FR, ZH)
- 🔒 **PWA Ready** - Manifest.json, service worker ready
- 🎨 **Animated** - Smooth scrolling, parallax, hover effects
- ♿ **Accessible** - Semantic HTML, ARIA labels
- 🔍 **SEO Optimized** - Meta tags, sitemap, robots.txt
- 🛡️ **Security Headers** - Bezpieczne nagłówki HTTP

## 📁 Struktura

```
wordpress-theme/
├── style.css              # Główny plik CSS (3500+ linii)
├── index.php              # Template głównej strony
├── header.php             # Header template
├── footer.php             # Footer template
├── functions.php          # Funkcje WordPress
├── favicon.svg            # Ikona SVG
├── manifest.json          # PWA manifest
│
├── languages/             # Pliki językowe
│   ├── oryntium.pot      # Template tłumaczeń
│   ├── oryntium-pl_PL.po # Polski
│   ├── oryntium-en_US.po # Angielski
│   ├── oryntium-es_ES.po # Hiszpański
│   ├── oryntium-de_DE.po # Niemiecki
│   ├── oryntium-hi_IN.po # Hindi
│   ├── oryntium-ar.po    # Arabski
│   ├── oryntium-fr_FR.po # Francuski
│   └── oryntium-zh_CN.po # Chiński
│
├── css/                   # Dodatkowe style
├── js/                    # JavaScript
└── README.md             # Ten plik
```

## 🚀 Instalacja

### Krok 1: Instalacja WordPress
```bash
# Pobierz najnowszą wersję WordPress
wget https://wordpress.org/latest.zip
unzip latest.zip

# Skonfiguruj bazę danych
# Edytuj wp-config.php
```

### Krok 2: Instalacja motywu
```bash
# Skopiuj folder theme do wp-content/themes/
cp -r wordpress-theme/ /path/to/wordpress/wp-content/themes/oryntium/

# Lub użyj WordPress Admin:
# Wygląd → Motywy → Dodaj nowy → Wgraj motyw
```

### Krok 3: Aktywacja
1. Przejdź do **Wygląd → Motywy**
2. Kliknij **Aktywuj** przy motywie ORYNTIUM
3. Skonfiguruj menu w **Wygląd → Menu**

### Krok 4: Konfiguracja wielojęzyczności

#### Opcja A: Polylang (ZALECANE - darmowe)
```bash
# Zainstaluj plugin Polylang
# Pluginy → Dodaj nowy → "Polylang"
```

#### Opcja B: WPML (płatne)
```bash
# Zainstaluj plugin WPML
# Pluginy → Dodaj nowy → "WPML"
```

### Krok 5: Konfiguracja języków
1. **Polylang**: Ustawienia → Języki → Dodaj język
2. **WPML**: WPML → Języki → Dodaj język

## 🎨 Sekcje strony

1. **Hero** - "KEEP IT YOURS" z animowanym logo
2. **Features** - 6 głównych funkcji (AES-256, hasła, automation, etc.)
3. **How It Works** - 4 kroki używania aplikacji
4. **Benefits** - 6 zalet (prywatność, bezpieczeństwo, kontrola)
5. **Security** - Algorytmy i technologie
6. **Footer** - Logo, tagline, "powered by rhei"

## 🎯 Tech Stack

- **WordPress** - CMS
- **PHP 7.4+** - Backend
- **HTML5** - Semantic markup
- **CSS3** - Custom properties, animations, grid/flexbox
- **JavaScript (ES6+)** - Vanilla JS, no frameworks
- **SVG** - Inline icons i logo
- **Google Fonts** - Orbitron + Rajdhani

## 🎨 Kolorystyka

```css
/* Neon colors */
--neon-purple: #BB00FF
--neon-blue: #00F0FF
--neon-cyan: #00D4FF
--neon-pink: #FF00FF
--neon-green: #00FF41

/* Base */
--bg-primary: #0A0A0A
--bg-secondary: #1A1A1A
--text-primary: #FFFFFF
--text-secondary: #B0B0B0
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

## 🌍 Obsługiwane języki

| Język | Kod | Status |
|-------|-----|--------|
| Polski | pl_PL | ✅ Domyślny |
| Angielski | en_US | ✅ |
| Hiszpański | es_ES | ✅ |
| Niemiecki | de_DE | ✅ |
| Hindi | hi_IN | ✅ |
| Arabski | ar | ✅ |
| Francuski | fr_FR | ✅ |
| Chiński | zh_CN | ✅ |

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
- ✅ Semantic HTML
- ✅ Fast loading
- ✅ Mobile-first
- ✅ Security headers

## 📊 Performance

- **CSS**: ~30KB (uncompressed)
- **JavaScript**: ~8KB (uncompressed)
- **Load time**: <1s (po cache)
- **PageSpeed Score**: 95+ (expected)

## 🛠️ Customizacja

### Zmień kolory:
Edytuj w `style.css`:
```css
:root {
    --neon-purple: #TWOJ_KOLOR;
    --neon-blue: #TWOJ_KOLOR;
    /* ... */
}
```

### Zmień treść:
Użyj WordPress Customizer:
- **Wygląd → Dostosuj → Opcje ORYNTIUM**

### Dodaj sekcję:
Edytuj `index.php` lub stwórz nowy template

## 🔧 Konfiguracja WordPress

### Wymagania systemowe:
- **WordPress**: 5.0+
- **PHP**: 7.4+
- **MySQL**: 5.6+
- **Memory**: 128MB+

### Zalecane pluginy:
- **Polylang** - Wielojęzyczność
- **Yoast SEO** - SEO
- **WP Rocket** - Cache
- **UpdraftPlus** - Backup

## 🐛 Troubleshooting

### Motyw nie aktywuje się?
Sprawdź czy wszystkie pliki są w folderze `wp-content/themes/oryntium/`

### Kolory nie działają?
Sprawdź czy `style.css` jest poprawnie załadowany

### Języki nie działają?
Zainstaluj plugin Polylang lub WPML

### Animacje nie działają?
Sprawdź czy JavaScript jest włączony

## 🚢 Deployment Options

- **Shared Hosting** 🏠 - Najłatwiejsze (cPanel)
- **VPS** ⚡ - Najszybsze (własna kontrola)
- **Managed WordPress** 📦 - Najwygodniejsze
- **Cloud** ☁️ - Najskalowalniejsze

## 🎯 Roadmap

- [x] WordPress theme struktura
- [x] CSS styling + animacje
- [x] JavaScript interakcje
- [x] Responsywność
- [x] Wielojęzyczność (8 języków)
- [x] PWA manifest
- [x] SEO optimization
- [x] Security headers
- [ ] Custom post types
- [ ] Advanced customizer
- [ ] Child theme support
- [ ] WooCommerce integration

## 📝 Licencja

© 2025 ORYNTIUM powered by rhei

Motyw stworzony dla aplikacji ORYNTIUM - Secure Messaging Platform.

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

1. **WordPress**: Sprawdź dokumentację WordPress
2. **Motyw**: Sprawdź ten README
3. **Języki**: Sprawdź folder `languages/`
4. **DevTools**: F12 → Console (sprawdź błędy)

## 🎉 Acknowledgments

- **WordPress** - Najlepszy CMS na świecie
- **Polylang** - Doskonały plugin wielojęzyczności
- **Google Fonts** - Orbitron + Rajdhani
- **Material Icons** - Ikony SVG

---

**Made with 💜 for ORYNTIUM**

**Keep it yours!**

---

[![Deploy to WordPress](https://img.shields.io/badge/Deploy-WordPress-blue?style=for-the-badge&logo=wordpress)](https://wordpress.org)




