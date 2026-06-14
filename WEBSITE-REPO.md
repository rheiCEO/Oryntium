# ORYNTIUM — powiązanie strony z repozytorium

**Live:** [https://oryntium.app](https://oryntium.app)

Repozytorium zawiera kod strony i aplikacji Android w jednym miejscu.

## Struktura WWW w repo

| Ścieżka | Opis |
|---------|------|
| `wp-theme/oryntium/` | **Motyw WordPress na produkcji** — aktualna strona oryntium.app (Hostinger) |
| `WEBSITE/` | Wersja statyczna HTML/CSS/JS (backup / lokalny podgląd) |
| `WEBSITE/wordpress-theme/` | Alternatywny motyw WordPress |

## Hosting produkcyjny

- **Domena:** oryntium.app (DNS → Hostinger)
- **Motyw:** `wp-theme/oryntium/`
- **Instalacja:** `wp-theme/oryntium/HOSTINGER_INSTALL.md`

## Wdrożenie z GitHub (opcjonalne)

Workflow `.github/workflows/deploy-website.yml` wgrywa motyw na Hostinger przez FTP.

Sekrety w GitHub → Settings → Secrets → Actions:

- `HOSTINGER_FTP_HOST`
- `HOSTINGER_FTP_USER`
- `HOSTINGER_FTP_PASSWORD`

---

© 2026 [oryntium.app](https://oryntium.app) · [github.com/rheiCEO/Oryntium](https://github.com/rheiCEO/Oryntium)
