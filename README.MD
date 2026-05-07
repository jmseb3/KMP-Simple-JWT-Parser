# KMP-Simple-JWT-Parser

A lightweight Kotlin Multiplatform (KMP) library for parsing the **payload section** of a JWT token on both Android and iOS.

It provides a simple API to decode JWT payloads into a Kotlin object using `kotlinx.serialization`.

![Maven Central Version](https://img.shields.io/maven-central/v/io.github.jmseb3/simpleJwtParser)

---

# Features

- Kotlin Multiplatform support (Android / iOS)
- Simple JWT payload parsing
- Built with `kotlinx.serialization`
- Access standard JWT claims easily
- Safely read custom claims with helper extensions

---

# Installation

The library is published on Maven Central.

Add the dependency to your `commonMain` source set:

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.jmseb3:simpleJwtParser:2.0.0")
            }
        }
    }
}
```

---

# Usage

## Parse JWT Payload

Use `JwtParser.parseToJsonObject()` to parse the payload section of a JWT token into a `Payload` object.

```kotlin
val payload: Payload = JwtParser.parseToJsonObject(jwtToken)
```

---

# Supported Standard Claims

The `Payload` class supports the default JWT claims:

- `iss` — Issuer
- `sub` — Subject
- `aud` — Audience
- `exp` — Expiration Time
- `nbf` — Not Before
- `iat` — Issued At
- `jti` — JWT ID

---

# Access Custom Claims

You can also access custom claims using the `get()` function.

```kotlin
val loginType = payload.get(
    key = "member_login_type",
    defaultValue = ""
)
```

---

# Safe JSON Access Helpers

The library provides safe extension methods for `JsonObject`.

## Example

```kotlin
val typeOfLogin: String? =
    jsonObject
        .safeGet("member_login_type")
        ?.jsonPrimitive
        ?.content
```

## With Default Value

```kotlin
val typeOfLogin: String =
    jsonObject
        .safeGet(
            key = "member_login_type",
            defaultValue = ""
        )
        .jsonPrimitive
        .content
```

---

# Requirements

- Kotlin Multiplatform
- `kotlinx.serialization`

---

# License

MIT License

