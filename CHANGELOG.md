# Changelog

All notable changes to ORYNTIUM are documented here.

---

## [1.0.6] — 2026-07-10

### Security

- Device key wrapped with **Android Keystore** (AES-GCM) instead of plaintext DataStore; automatic migration from legacy format
- PIN hashing upgraded from plain SHA-256 to **PBKDF2-HMAC-SHA256** (120k iterations + random salt); migration on next successful login
- Full wipe after 5 failed PIN attempts now clears Keystore, Room DB, storage/theme/language prefs
- SMS message content no longer logged in release builds (`BuildConfig.DEBUG` only)
- ProGuard rules for Hilt Worker — message cleanup works in release builds

### Fixes

- Multi-part SMS buffer moved to static `companion` — parts no longer lost between broadcasts
- `SmsService` refactored to `suspend` — send errors propagate to UI (was silently lost in background `launch`)
- `SEND_SMS` permission checked before sending
- WorkManager periodic cleanup uses `ExistingPeriodicWorkPolicy.UPDATE`

### UX

- Error Snackbars on Home, Chat, and Contacts screens
- SMS permissions card in Settings (status + grant button)
- Fixed bottom navigation tab highlighting
- Navigation labels via `stringResource` (PL translations updated)
- Password field masking on contact form; delete dialog shows contact name
- Avatar crash fix for empty contact names
- Removed dead `SubscriptionPlanScreen` and unused `READ_CONTACTS` permission

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

## Stats (v1.0.6)

| Metric | Value |
|--------|-------|
| Kotlin source files | ~50 |
| Screens | 10+ |
| APK size (debug) | ~55 MB |
| Min / Target SDK | 29 / 35 |

---

© 2025–2026 ORYNTIUM · [rheiCEO](https://github.com/rheiCEO)
