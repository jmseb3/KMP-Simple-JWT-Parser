package com.wonddak.jwt

import kotlinx.serialization.json.JsonObject

/**
 * Parse JWT Token For Payload
 *
 */
expect class JwtParser() {

    /**
     * @param[jwtToken] : JWT Token
     * @return JsonObject
     * @see JsonObject
     */
    fun parseToJsonObject(jwtToken: String): JsonObject?
}

object JWT {
    const val HEADER = 0
    const val PAYLOAD = 1
    const val SIGNATURE = 2
    const val PARTS = 3
}
