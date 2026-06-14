# Release Build Guide

How to create a signed release APK/AAB for Google Play or sideloading.

---

## 1. Generate a keystore (one-time)

```powershell
keytool -genkey -v -keystore oryntium-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias oryntium
```

**Back up** `oryntium-release-key.jks` and passwords securely. Losing the keystore means you cannot update the app on Play Store.

---

## 2. Configure signing

Copy the example file:

```powershell
copy keystore.properties.example keystore.properties
```

Edit `keystore.properties`:

```properties
storePassword=YOUR_STORE_PASSWORD
keyPassword=YOUR_KEY_PASSWORD
keyAlias=oryntium
storeFile=oryntium-release-key.jks
```

Never commit `keystore.properties` or `*.jks` — they are in `.gitignore`.

The project already loads this file in `app/build.gradle.kts`.

---

## 3. Build release

```powershell
./gradlew clean assembleOryntiumRelease
```

APK path:

```
app/build/outputs/apk/oryntium/release/app-oryntium-release.apk
```

For Google Play (recommended):

```powershell
./gradlew bundleOryntiumRelease
```

AAB path:

```
app/build/outputs/bundle/oryntiumRelease/
```

---

## 4. Install & verify

```powershell
adb install app\build\outputs\apk\oryntium\release\app-oryntium-release.apk
```

Optional: upload APK to [VirusTotal](https://www.virustotal.com) — release builds typically score better than debug.

---

## Debug vs release

| | Debug | Release |
|---|-------|---------|
| Size | Larger (~15–25 MB) | Optimized (~8–15 MB) |
| Signing | Debug certificate | Your release keystore |
| ProGuard/R8 | Off | On |
| Use case | Development | Play Store / distribution |

---

## Google Play submission

1. [Google Play Console](https://play.google.com/console) — developer account ($25 one-time)
2. Upload AAB (not APK preferred)
3. Store listing: screenshots, icon 512×512, feature graphic 1024×500
4. Privacy policy URL (required for SMS apps)
5. Complete **Data safety** form — see [GOOGLE_PLAY_COMPLIANCE.md](GOOGLE_PLAY_COMPLIANCE.md)

Review time: typically 1–3 days.

---

© 2025–2026 ORYNTIUM · [rheiCEO](https://github.com/rheiCEO)
