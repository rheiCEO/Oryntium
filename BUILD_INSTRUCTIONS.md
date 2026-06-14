# Build Instructions — ORYNTIUM

## Prerequisites

| Tool | Version |
|------|---------|
| Android Studio | 2022.1.1+ (Electric Eel or newer) |
| JDK | 17 |
| Android SDK | Platform 35, Build Tools 35.x |

Verify:

```bash
java -version    # Java 17
```

---

## Open the project

```bash
git clone https://github.com/rheiCEO/Oryntium.git
cd Oryntium
```

Android Studio → **Open** → select project folder → **Sync Gradle**.

If SDK path is missing: **File → Project Structure → SDK Location**.

---

## Build variants

The app uses product flavors: `oryntium`, `games`, `bank`, `music`.

### Debug APK

```bash
./gradlew assembleOryntiumDebug
```

Output: `app/build/outputs/apk/oryntium/debug/app-oryntium-debug.apk`

### Release APK

Requires `keystore.properties` (see [RELEASE.md](RELEASE.md) and `keystore.properties.example`).

```bash
./gradlew assembleOryntiumRelease
```

Output: `app/build/outputs/apk/oryntium/release/`

### Android App Bundle (Google Play)

```bash
./gradlew bundleOryntiumRelease
```

Output: `app/build/outputs/bundle/oryntiumRelease/`

---

## Run on device

1. Enable **Developer options** → **USB debugging**
2. Connect phone via USB
3. Android Studio → **Run** → select `oryntiumDebug`

Grant SMS permissions when prompted.

---

## Testing checklist

- [ ] Create 6-digit PIN
- [ ] Add contact with encryption password
- [ ] Send encrypted SMS (toggle encryption in chat)
- [ ] Receive SMS with `SMSCRYPT:` prefix
- [ ] Switch languages in Settings
- [ ] PIN lock after background timeout

> SMS tests on real devices may incur carrier charges.

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| Dependencies not resolving | Check internet; **File → Invalidate Caches / Restart**; `./gradlew clean` |
| SDK location not found | Set SDK path in Project Structure |
| Manifest merge errors | **Build → Clean Project → Rebuild** |
| SMS permissions denied | Settings → Apps → ORYNTIUM → Permissions |

---

## Version bump

Edit `app/build.gradle.kts`:

```kotlin
versionCode = 3      // increment every release
versionName = "1.0.6"
```

---

© 2025–2026 ORYNTIUM · [rheiCEO](https://github.com/rheiCEO)
