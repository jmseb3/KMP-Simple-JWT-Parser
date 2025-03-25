# KMP-Simple-JWT-Parser

This library has a Simple function to import the payload part of jwt. for ios and android(kmp)

![](https://img.shields.io/badge/mavencentral-1.0.0-green)
# Setup

this library upload on **mavenCentral**

add dependencies for commonMain in shared Module build.gradle.kts

~~~kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.jmseb3:simpleJwtParser:2.0.0")
            }
        }
    }
}
~~~

# how to use

parse payload part of JWTToken to **[Payload Class](./simpleJwtParser/src/commonMain/kotlin/com/wonddak/jwt/model/Payload.kt)** by **[jsonObject](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json-object/)**

~~~kotlin
val payload : Payload= JwtParser.parseToJsonObject(jwtToken)
~~~

**Payload class**  support default  key `iss`,`sub`,`aud`,`exp`,`nbf`,`iat`,`jti`
also if u need any key use `payload.get(key,defaultValue)`

also you use **safeGetMethod** like under

### Support JsonObject Extension

~~~kotlin
val typeOfLogin: String? = jsonObject.safeGet(key = "member_login_type")?.jsonPrimitive?.content
// or use defaultValue
val typeOfLogin: String = jsonObject.safeGet(key = "member_login_type", defaultValue = "").jsonPrimitive.content
~~~