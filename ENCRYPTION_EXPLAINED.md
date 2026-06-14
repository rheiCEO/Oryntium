# Encryption Explained

How ORYNTIUM encrypts and decrypts SMS messages.

---

## Overview

| Component | Algorithm |
|-----------|-----------|
| Message encryption | AES-256-CBC |
| Key derivation | PBKDF2-HMAC-SHA256 (10,000 iterations) |
| Encoding | Base64 (for SMS transport) |
| Prefix / suffix | `SMSCRYPT:` … `SMSEND` |

Each contact has a **shared password** agreed out-of-band (not sent in SMS).

---

## Encryption (outgoing)

Example:

```
Plaintext:  "Meet at 3pm"
Password:   "Krakow2021POLAND"
```

**Step 1 — Random material**

- IV: 16 random bytes
- Salt: 16 random bytes

**Step 2 — Derive AES key**

```
Key = PBKDF2(password, salt, iterations=10000, keyLen=256)
```

**Step 3 — Encrypt**

```
Ciphertext = AES-256-CBC(plaintext, key, iv)
```

**Step 4 — Package**

```
Payload = IV || Salt || Ciphertext
SMS body = "SMSCRYPT:" + Base64(Payload) + "SMSEND"
```

---

## Decryption (incoming)

**Step 1** — Strip `SMSCRYPT:` prefix and `SMSEND` suffix  
**Step 2** — Base64-decode  
**Step 3** — Split: bytes `[0:16]` = IV, `[16:32]` = Salt, rest = ciphertext  
**Step 4** — Re-derive key with same password + Salt from message  
**Step 5** — AES-256-CBC decrypt → plaintext

If the message is incomplete (multi-part SMS), the app waits for all parts before decrypting.

---

## Why IV and Salt are sent in the SMS

| Field | Secret? | Purpose |
|-------|---------|---------|
| IV | No | Unique ciphertext per message (same text → different SMS) |
| Salt | No | Unique key per encryption (rainbow-table resistance) |
| Password | **Yes** | Never transmitted — must be pre-shared with contact |
| AES key | **Yes** | Derived locally, never sent |

Without the contact password, IV and Salt alone cannot decrypt the message.

---

## Local storage

Contact passwords are encrypted with a **device-specific key** (DataStore) before being saved in Room.

---

## Message format diagram

```
SMSCRYPT:[ Base64( IV(16) + Salt(16) + AES-CBC ciphertext ) ]SMSEND
```

---

## FAQ

**Is it safe to send IV and Salt in SMS?**  
Yes — standard practice for password-based encryption. Secrecy comes from the password.

**Can someone decrypt without the password?**  
No — PBKDF2 + AES require the shared contact password.

**Legacy prefix `SCRYPT:`**  
Older docs may reference `SCRYPT:` — current app uses `SMSCRYPT:` + `SMSEND` markers.

---

© 2025–2026 ORYNTIUM · [rheiCEO](https://github.com/rheiCEO)
