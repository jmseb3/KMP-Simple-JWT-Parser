package com.wonddak.jwt

import kotlinx.serialization.json.*
import platform.Foundation.NSData
import platform.Foundation.NSJSONSerialization
import platform.Foundation.NSString
import platform.Foundation.componentsSeparatedByString
import platform.Foundation.create
import platform.Foundation.stringByPaddingToLength

actual class JwtParser {

    actual fun parseToJsonObject(jwtToken: String): JsonObject? {
        val jwt = jwtToken as NSString
        val segments = jwt.componentsSeparatedByString(".") as List<NSString>

        return decodeJWTPart(segments[1]).toJsonObject()
    }

    private fun decodeJWTPart(value: NSString): Map<String, Any> {
        val bodyData = base64Decode(value) ?: return mapOf()
        return NSJSONSerialization.JSONObjectWithData(bodyData, 0u, null) as Map<String, Any>
    }

    private fun base64Decode(base64: NSString): NSData? {
        val padding = base64.stringByPaddingToLength(
            newLength = (((base64.length + 3u) / 4u) * 4u),
            withString = "=",
            startingAtIndex = 0
        )
      return NSData.create(base64Encoding = padding)
    }
}