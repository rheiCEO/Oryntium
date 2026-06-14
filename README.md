# ORYNTIUM — Secure SMS Encryption

**Keep it yours.** Android app for AES-256 encrypted SMS messaging with per-contact passwords, local storage, and zero cloud.

**Website:** [oryntium.app](https://oryntium.app)

## Features

- **AES-256 encryption** — AES-256-CBC with PBKDF2 key derivation
- **Per-contact passwords** — unique encryption password for each contact
- **Automatic processing** — encrypt outgoing and decrypt incoming SMS in the background
- **8 languages** — PL, EN, ES, DE, FR, AR, HI, ZH
- **6-digit PIN** — app lock with 5-attempt limit and data wipe
- **Screenshot protection** — FLAG_SECURE + Matrix-style overlay
- **Local-only storage** — no external servers, no cloud backup
- **Cyberpunk UI** — Jetpack Compose, dark theme, neon accents

## Tech stack

- Kotlin · Jetpack Compose · Material 3
- MVVM · Room · Hilt · WorkManager
- BouncyCastle (AES-256, PBKDF2)
- Min SDK 29 · Target SDK 35

## Getting started

### Requirements

- Android Studio (2022.1.1+)
- JDK 17
- Android SDK 35

### Build

```bash
git clone https://github.com/rheiCEO/Oryntium.git
cd Oryntium
```

Open the project in Android Studio, sync Gradle, then run the `oryntium` flavor.

```bash
# Debug
./gradlew assembleOryntiumDebug

# Release (requires keystore.properties — see keystore.properties.example)
./gradlew assembleOryntiumRelease
```

## Project structure

```
app/                 Android application (Kotlin/Compose)
WEBSITE/             Static marketing site (HTML/CSS/JS)
wp-theme/oryntium/   WordPress theme (live site at oryntium.app)
```

## Security

Encrypted message format:

```
SMSCRYPT:[Base64(IV + Salt + Encrypted Data)]SMSEND
```

See [ENCRYPTION_EXPLAINED.md](ENCRYPTION_EXPLAINED.md) for details.

## Documentation

- [BUILD_INSTRUCTIONS.md](BUILD_INSTRUCTIONS.md) — build guide
- [JAK_ZROBIC_RELEASE.md](JAK_ZROBIC_RELEASE.md) — release APK/AAB
- [GOOGLE_PLAY_COMPLIANCE.md](GOOGLE_PLAY_COMPLIANCE.md) — Play Store requirements
- [WEBSITE/DEPLOYMENT_GUIDE.md](WEBSITE/DEPLOYMENT_GUIDE.md) — website deployment

## License

MIT License — Copyright © 2025–2026 ORYNTIUM · powered by [rhei](https://github.com/rheiCEO)

---

**Disclaimer:** For educational and personal use. A professional security audit is recommended before production deployment.
