# Changelog

All notable changes to ORYNTIUM are documented here.

---

## [1.0.5] — 2025–2026

### Current release

- AES-256-CBC encryption with PBKDF2-SHA256 (10,000 iterations)
- Per-contact encryption passwords
- Automatic encrypt/decrypt via `SmsReceiver` / `SmsService`
- `SMSCRYPT:…SMSEND` message format + multi-part SMS support
- 6-digit PIN with 5-attempt wipe
- 8 languages: PL, EN, ES, DE, FR, AR, HI, ZH
- Jetpack Compose cyberpunk UI
- Product flavors: oryntium, games, bank, music
- Screenshot protection (`FLAG_SECURE`)
- WorkManager message cleanup
- Target SDK 35, min SDK 29

### Architecture

- Kotlin · MVVM · Hilt · Room · DataStore · BouncyCastle

---

## [1.0.0] — 2025-10-11

Initial public release — core SMS encryption, contacts, chat, settings.

---

## Roadmap

### v1.1
- [ ] Contact backup / restore
- [ ] Import from phone contacts
- [ ] Scheduled messages

### v1.2
- [ ] Biometric unlock
- [ ] SQLCipher for Room encryption at rest

---

## Stats (v1.0.5)

| Metric | Value |
|--------|-------|
| Kotlin source files | ~50 |
| Screens | 10+ |
| APK size (release) | ~8–15 MB |
| Min / Target SDK | 29 / 35 |

---

© 2025–2026 ORYNTIUM · [rheiCEO](https://github.com/rheiCEO)
