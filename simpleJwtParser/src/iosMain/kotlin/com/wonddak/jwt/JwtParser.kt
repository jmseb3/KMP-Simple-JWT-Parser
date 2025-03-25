package com.wonddak.jwt

import com.wonddak.jwt.model.Payload
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.*
import platform.Foundation.NSData
import platform.Foundation.NSJSONSerialization
import platform.Foundation.NSString
import platform.Foundation.componentsSeparatedByString
import platform.Foundation.create
import platform.Foundation.stringByPaddingToLength

/**
 * Parse JWT Token For Payload
 *
 */
actual object JwtParser {
    /**
     * @param[jwtToken] : JWT Token
     * @return JsonObject
     * @see JsonObject
     */
    actual fun parseToJsonObject(jwtToken: String): Payload? {
        return getPayload(jwtToken as NSString)?.let { Payload(it) }
    }

    private fun getPayload(jwt: NSString): JsonObject? {
        val jwtParts = jwt.componentsSeparatedByString(".") as List<NSString>
        try {
            if (jwtParts.size != JWT.PARTS) {
                throw Exception()
            }
            return decodeJWTPart(jwtParts[JWT.PAYLOAD]).toJsonObject()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun decodeJWTPart(value: NSString): Map<String, Any> {
        val bodyData = base64Decode(value) ?: return mapOf()
        return NSJSONSerialization.JSONObjectWithData(bodyData, 0u, null) as Map<String, Any>
    }

    @OptIn(BetaInteropApi::class)
    private fun base64Decode(base64: NSString): NSData? {
        val padding = base64.stringByPaddingToLength(
            newLength = (((base64.length + 3u) / 4u) * 4u),
            withString = "=",
            startingAtIndex = 0u
        )
        return NSData.create(base64Encoding = padding)
    }
}