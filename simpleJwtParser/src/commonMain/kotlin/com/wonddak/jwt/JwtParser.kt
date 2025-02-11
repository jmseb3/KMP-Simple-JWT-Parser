package com.wonddak.jwt

import com.wonddak.jwt.model.Payload
import kotlinx.serialization.json.JsonObject

/**
 * Parse JWT Token For Payload
 *
 */
expect object JwtParser {

    /**
     * @param[jwtToken] : JWT Token
     * @return JsonObject
     * @see JsonObject
     */
    fun parseToJsonObject(jwtToken: String): Payload?
}

object JWT {
    const val HEADER = 0
    const val PAYLOAD = 1
    const val SIGNATURE = 2
    const val PARTS = 3
}
