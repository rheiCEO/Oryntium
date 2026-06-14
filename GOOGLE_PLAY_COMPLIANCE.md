# Google Play Compliance

Checklist for publishing ORYNTIUM on Google Play.

---

## SMS permissions policy

ORYNTIUM is a **default SMS handler** app. Required permissions:

| Permission | Purpose |
|------------|---------|
| `SEND_SMS` | Send encrypted messages |
| `READ_SMS` | Read incoming messages for decryption |
| `RECEIVE_SMS` | Process SMS in real time |
| `READ_PHONE_STATE` | SMS stack compatibility |
| `READ_CONTACTS` | Optional contact integration |

All SMS are user-initiated or auto-decrypted locally — no background bulk sending.

---

## Privacy & data

| Topic | Implementation |
|-------|----------------|
| Data storage | Local Room database only |
| Cloud backup | Disabled (`allowBackup="false"`) |
| Third-party servers | None — no analytics, no Firebase |
| Contact passwords | Encrypted with device key before storage |
| User deletion | Delete contacts/messages in-app; uninstall wipes data |

### Data safety form (summary)

- **Collects:** contact names, phone numbers, SMS content — **local only**
- **Shares with third parties:** No
- **Encrypted at rest:** Contact passwords yes; Room DB is standard SQLite
- **User can request deletion:** Yes (in-app + uninstall)

---

## Security features

- AES-256-CBC + PBKDF2-SHA256
- 6-digit PIN, 5 attempts → full data wipe
- `FLAG_SECURE` screenshot protection
- ProGuard/R8 in release builds
- No sensitive data in production logs

---

## Store listing (English)

**Title:** ORYNTIUM — Secure SMS Encryption

**Short description:**

```
AES-256 encrypted SMS with per-contact passwords. Local storage, zero cloud.
```

**Category:** Communication  
**Content rating:** Everyone (18+ recommended for SMS apps)

Required assets:

- Feature graphic 1024×500
- Screenshots (min. 2)
- App icon 512×512
- Privacy policy URL

---

## Privacy policy template

Host at `https://oryntium.app/privacy` or similar:

- What data is stored (contacts, messages — locally)
- No third-party sharing
- Encryption methods (AES-256)
- How to delete data
- Contact email

---

## Pre-submission checklist

- [ ] Signed AAB built (`bundleOryntiumRelease`)
- [ ] `versionCode` incremented
- [ ] Privacy policy URL live
- [ ] Data safety form completed
- [ ] SMS permission declaration filled
- [ ] Tested on Android 11–14
- [ ] Screenshots uploaded

---

## Common review issues

| Issue | Mitigation |
|-------|------------|
| SMS permission rejection | Document app as primary SMS / encryption tool; complete declaration form |
| Data collection questions | Emphasize local-only storage |
| Encryption export | Standard AES — declare in Play Console if prompted |

---

**Status:** Ready for submission after store assets and privacy URL are prepared.

© 2025–2026 ORYNTIUM · [rheiCEO](https://github.com/rheiCEO)
