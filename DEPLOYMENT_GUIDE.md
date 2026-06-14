# 🚀 ORYNTIUM.APP - Przewodnik Wdrożenia

Kompletny przewodnik krok po kroku do wdrożenia strony ORYNTIUM.APP.

## 📋 Checklist przed wdrożeniem

- [x] Wszystkie pliki gotowe (HTML, CSS, JS)
- [ ] Domena `oryntium.app` zakupiona
- [ ] Hosting wybrany (GitHub Pages / Netlify / Vercel / własny)
- [ ] DNS skonfigurowane
- [ ] HTTPS włączone
- [ ] Przetestowane na różnych urządzeniach

## 🎯 Najszybsza metoda: Netlify (5 minut)

### Krok 1: Przygotuj pliki
```bash
# Upewnij się że masz wszystkie pliki:
index.html
styles.css
script.js
favicon.svg
manifest.json
robots.txt
sitemap.xml
404.html
```

### Krok 2: Netlify Drop
1. Przejdź do [netlify.com](https://www.netlify.com/)
2. Zaloguj się (lub załóż konto - za darmo)
3. Przeciągnij cały folder na stronę Netlify (Drag & Drop)
4. Poczekaj 30 sekund - gotowe!

### Krok 3: Custom Domain
1. Kliknij "Domain settings"
2. Kliknij "Add custom domain"
3. Wpisz: `oryntium.app`
4. Netlify poda Ci DNS settings

### Krok 4: DNS Configuration
Przejdź do panelu swojej domeny i dodaj:

```
Type: A
Name: @
Value: 75.2.60.5

Type: CNAME
Name: www
Value: twoja-strona.netlify.app
```

### Krok 5: HTTPS
Netlify automatycznie włączy HTTPS (Let's Encrypt).
Poczekaj 5-10 minut na propagację DNS.

**GOTOWE! Strona działa na https://oryntium.app**

---

## 🐙 Metoda GitHub Pages (darmowa, z kontrolą wersji)

### Krok 1: Utwórz repozytorium
```bash
# Inicjalizuj git w folderze ze stroną
git init
git add .
git commit -m "Initial commit - ORYNTIUM website"

# Utwórz repo na GitHub i podepnij
git remote add origin https://github.com/TWOJA_NAZWA/oryntium-website.git
git branch -M main
git push -u origin main
```

### Krok 2: Włącz GitHub Pages
1. Przejdź do Settings > Pages
2. Source: wybierz `main` branch
3. Folder: `/ (root)`
4. Save

### Krok 3: Custom Domain
1. W Settings > Pages > Custom domain wpisz: `oryntium.app`
2. Zaznacz "Enforce HTTPS"
3. Utwórz plik `CNAME` w repo:
```bash
echo "oryntium.app" > CNAME
git add CNAME
git commit -m "Add CNAME"
git push
```

### Krok 4: DNS Configuration
```
Type: A
Name: @
Value: 185.199.108.153

Type: A
Name: @
Value: 185.199.109.153

Type: A
Name: @
Value: 185.199.110.153

Type: A
Name: @
Value: 185.199.111.153

Type: CNAME
Name: www
Value: TWOJA_NAZWA.github.io
```

---

## ⚡ Metoda Vercel (najszybsza dla developerów)

### Krok 1: Instalacja CLI
```bash
npm install -g vercel
```

### Krok 2: Deploy
```bash
cd folder-ze-strona
vercel

# Odpowiedz na pytania:
# - Set up and deploy? [Y]
# - Which scope? [your account]
# - Link to existing project? [N]
# - Project name? [oryntium-website]
# - Directory? [./]
```

### Krok 3: Custom Domain
```bash
vercel domains add oryntium.app
```

Vercel poda Ci DNS settings do skonfigurowania.

### Krok 4: Production Deploy
```bash
vercel --prod
```

**GOTOWE!**

---

## 🏠 Metoda: Własny hosting (np. home.pl, nazwa.pl)

### Krok 1: Zakup hostingu
- Wybierz hosting z PHP/HTML (najtańszy wystarczy)
- Upewnij się że ma SSL (HTTPS)

### Krok 2: Upload plików przez FTP
```
Host: ftp.oryntium.app (lub IP od providera)
User: [username]
Password: [password]
Port: 21 (lub 22 dla SFTP)
```

Wgraj wszystkie pliki do folderu `public_html` lub `www`.

### Krok 3: Skonfiguruj domenę
W panelu hostingu:
1. Dodaj domenę `oryntium.app`
2. Wskaż na folder z plikami
3. Włącz SSL/HTTPS

### Krok 4: Test
Odwiedź: `https://oryntium.app`

---

## 🔧 Konfiguracja DNS (Universal)

Jeśli kupiłeś domenę na:

### OVH:
1. Panel klienta > Domeny > oryntium.app > Strefa DNS
2. Dodaj rekordy A/CNAME zgodnie z wybranym hostingiem

### Cloudflare:
1. DNS > Add record
2. Type: A lub CNAME
3. Name: @ lub www
4. Content: [IP lub hostname]
5. Proxy status: Proxied (pomarańczowa chmurka)

### GoDaddy:
1. My Products > Domains > oryntium.app > Manage DNS
2. Add > A lub CNAME
3. Wprowadź dane

### Nazwa.pl / Home.pl:
1. Panel klienta > Domeny > DNS
2. Zarządzanie strefą DNS
3. Dodaj rekordy

---

## ✅ Weryfikacja po wdrożeniu

### 1. Sprawdź czy strona się ładuje
```bash
# Test głównej strony
curl -I https://oryntium.app

# Oczekiwany wynik: HTTP/2 200
```

### 2. Sprawdź HTTPS
- Otwórz https://oryntium.app
- Kliknij na kłódkę w pasku adresu
- Sprawdź certyfikat SSL

### 3. Test responsywności
- Otwórz DevTools (F12)
- Toggle Device Toolbar (Ctrl+Shift+M)
- Przetestuj: Mobile, Tablet, Desktop

### 4. Test wydajności
1. Otwórz [PageSpeed Insights](https://pagespeed.web.dev/)
2. Wklej: https://oryntium.app
3. Sprawdź wyniki (powinno być 90+)

### 5. Test SEO
1. Otwórz [Google Search Console](https://search.google.com/search-console)
2. Dodaj właściwość: oryntium.app
3. Zweryfikuj domenę
4. Prześlij sitemap: https://oryntium.app/sitemap.xml

---

## 🎨 Opcjonalne ulepszenia

### 1. Dodaj Google Analytics
```html
<!-- Dodaj przed </head> w index.html -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-XXXXXXXXXX"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'G-XXXXXXXXXX');
</script>
```

### 2. Dodaj Facebook Pixel
```html
<!-- Dodaj przed </head> w index.html -->
<script>
  !function(f,b,e,v,n,t,s)
  {if(f.fbq)return;n=f.fbq=function(){n.callMethod?
  n.callMethod.apply(n,arguments):n.queue.push(arguments)};
  if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
  n.queue=[];t=b.createElement(e);t.async=!0;
  t.src=v;s=b.getElementsByTagName(e)[0];
  s.parentNode.insertBefore(t,s)}(window, document,'script',
  'https://connect.facebook.net/en_US/fbevents.js');
  fbq('init', 'YOUR_PIXEL_ID');
  fbq('track', 'PageView');
</script>
```

### 3. Minifikacja (produkcja)
```bash
# HTML
npm install -g html-minifier
html-minifier --collapse-whitespace --remove-comments index.html -o index.min.html

# CSS
npm install -g clean-css-cli
cleancss -o styles.min.css styles.css

# JavaScript
npm install -g terser
terser script.js -o script.min.js -c -m

# Zamień w index.html:
# styles.css -> styles.min.css
# script.js -> script.min.js
```

---

## 🐛 Troubleshooting

### Problem: Strona nie ładuje się
- Sprawdź DNS: [whatsmydns.net](https://www.whatsmydns.net/)
- Propagacja DNS trwa 4-48h (zazwyczaj 15 minut)
- Wyczyść cache przeglądarki (Ctrl+Shift+Del)

### Problem: Brak HTTPS
- Poczekaj 10-15 minut na certyfikat
- Sprawdź czy hosting obsługuje Let's Encrypt
- Wymuś HTTPS w ustawieniach hostingu

### Problem: 404 na podstronach
- Sprawdź czy plik `.htaccess` jest wgrany (Apache)
- Dla Nginx dodaj `try_files $uri $uri/ /index.html;`

### Problem: Wolne ładowanie
- Włącz kompresję GZIP (w `.htaccess` już jest)
- Użyj CDN (np. Cloudflare)
- Zminifikuj pliki CSS/JS

### Problem: Nie działa na mobile
- Sprawdź viewport tag: `<meta name="viewport">`
- Test w DevTools na różnych rozdzielczościach
- Sprawdź media queries w CSS

---

## 📊 Monitoring

### 1. Uptime monitoring
- [UptimeRobot](https://uptimerobot.com/) - darmowy
- Monitoruje czy strona działa 24/7

### 2. Analytics
- Google Analytics - ruch na stronie
- Hotjar - nagrania sesji użytkowników

### 3. Error tracking
- Sentry - monitorowanie błędów JS

---

## 🔄 Aktualizacje

### Jak zaktualizować stronę?

#### Netlify:
1. Wyedytuj pliki lokalnie
2. Przeciągnij folder ponownie na Netlify
3. Lub: podepnij GitHub i używaj `git push`

#### GitHub Pages:
```bash
git add .
git commit -m "Update: opis zmian"
git push
```

#### Vercel:
```bash
vercel --prod
```

#### FTP:
1. Połącz się przez FileZilla
2. Wgraj zaktualizowane pliki

---

## 📞 Wsparcie

Jeśli coś nie działa:
1. Sprawdź ten guide ponownie
2. Przeczytaj WEBSITE_README.md
3. Sprawdź DevTools Console (F12)
4. Sprawdź DNS propagation
5. Wyczyść cache

---

## ✨ Gratulacje!

Jeśli dotarłeś tutaj i strona działa, to jesteś mistrzem! 🎉

**Keep it yours! 💜**

---

© 2025 ORYNTIUM powered by rhei







