KMP-Simple-JWT-Parser

A lightweight Kotlin Multiplatform (KMP) library for parsing the payload section of a JWT token on both Android and iOS.

It provides a simple API to decode JWT payloads into a Kotlin object using kotlinx.serialization.

⸻

Features

* Kotlin Multiplatform support (Android / iOS)
* Simple JWT payload parsing
* Built with kotlinx.serialization
* Access standard JWT claims easily
* Safely read custom claims with helper extensions

⸻

Installation

The library is published on Maven Central.

Version Catalog (libs.versions.toml)

[versions]
simpleJwtParser = "2.0.0"
[libraries]
simpleJwtParser = { module = "io.github.jmseb3:simpleJwtParser", version.ref = "simpleJwtParser" }

Then add the dependency:

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.simpleJwtParser)
            }
        }
    }
}

⸻

Direct Dependency

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.jmseb3:simpleJwtParser:2.0.0")
            }
        }
    }
}

⸻

Usage

Parse JWT Payload

Use JwtParser.parseToJsonObject() to parse the payload section of a JWT token into a Payload object.

val payload: Payload = JwtParser.parseToJsonObject(jwtToken)

⸻

Supported Standard Claims

The Payload class supports the default JWT claims:

* iss — Issuer
* sub — Subject
* aud — Audience
* exp — Expiration Time
* nbf — Not Before
* iat — Issued At
* jti — JWT ID

⸻

Access Custom Claims

You can also access custom claims using the get() function.

val loginType = payload.get(
    key = "member_login_type",
    defaultValue = ""
)

⸻

Safe JSON Access Helpers

The library provides safe extension methods for JsonObject.

Example

val typeOfLogin: String? =
    jsonObject
        .safeGet("member_login_type")
        ?.jsonPrimitive
        ?.content

With Default Value

val typeOfLogin: String =
    jsonObject
        .safeGet(
            key = "member_login_type",
            defaultValue = ""
        )
        .jsonPrimitive
        .content

⸻

Requirements

* Kotlin Multiplatform
* kotlinx.serialization

⸻

License

Licensed under the Apache License 2.0.

See the LICENSE file for details.
