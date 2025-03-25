package com.wonddak.jwt

import android.util.Base64
import com.wonddak.jwt.model.Payload
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.nio.charset.StandardCharsets

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
        return getPayload(jwtToken)?.let { Payload(it) }
    }

    private fun getPayload(jwt: String): JsonObject? {
        val jwtParts = jwt.split("\\.".toRegex()).toTypedArray()
        try {
            if (jwtParts.size != JWT.PARTS) {
                throw Exception()
            }
            val payload = jwt.split("\\.".toRegex()).toTypedArray()[JWT.PAYLOAD]
            val sectionDecoded = Base64.decode(payload.toByteArray(), Base64.DEFAULT)
            val jwtSection = String(sectionDecoded, StandardCharsets.UTF_8)
            return Json.decodeFromString<JsonObject>(jwtSection)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}